package com.codex.felicaprobe;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public final class ProbeActivity extends Activity {
    private static final String TAG = "FelicaProbe";
    private static final String MFI_DESCRIPTOR = "com.felicanetworks.mfc.mfi.IMfiFelica";
    private static final String EVENT_DESCRIPTOR = "com.felicanetworks.mfc.IFelicaEventListener";
    private static final String SERVER_CALLBACK_DESCRIPTOR = "com.felicanetworks.mfc.mfi.IServerOperationEventCallback";
    private static final String SILENT_CALLBACK_DESCRIPTOR = "com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback";

    private final CountDownLatch activateLatch = new CountDownLatch(1);
    private final CountDownLatch serverLatch = new CountDownLatch(1);
    private final CountDownLatch silentLatch = new CountDownLatch(1);

    private final ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            note("connected " + name.flattenToShortString());
            final IBinder remote = service;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    runProbe(remote);
                }
            }, "felica-probe").start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            note("disconnected " + name.flattenToShortString());
        }
    };

    @Override
    protected void onCreate(Bundle state) {
        super.onCreate(state);
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(
                "com.felicanetworks.mfm.main",
                "com.felicanetworks.mfc.mfi.FelicaAdapter"));
        boolean bound = bindService(intent, connection, Context.BIND_AUTO_CREATE);
        note("bindMfi=" + bound);
        if (!bound) {
            finish();
        }
    }

    private void runProbe(IBinder binder) {
        try {
            logResult("activate(self)", call(binder, 1, new Writer() {
                @Override
                public void write(Parcel p) {
                    p.writeString(getPackageName());
                    p.writeStrongBinder(new FelicaEventCallback());
                }
            }, Value.NONE));
            await("activate", activateLatch, 8);

            logResult("open", call(binder, 16, null, Value.NONE));
            logResult("select(FE0F)", call(binder, 20, new Writer() {
                @Override
                public void write(Parcel p) {
                    p.writeInt(65039);
                }
            }, Value.NONE));
            logResult("getIDm", call(binder, 7, null, Value.BYTE_ARRAY));
            logResult("getContainerIssueInformation", call(binder, 5, new Writer() {
                @Override
                public void write(Parcel p) {
                    p.writeInt(1000);
                    p.writeInt(0);
                }
            }, Value.BYTE_ARRAY));
            logResult("getICCode", call(binder, 6, null, Value.BYTE_ARRAY));

            logResult("provisionServerOperation", call(binder, 74, new Writer() {
                @Override
                public void write(Parcel p) {
                    p.writeStrongBinder(new ServerCallback());
                }
            }, Value.NONE));
            await("server", serverLatch, 8);

            logResult("silentStartForMfiAdminV2", call(binder, 67, new Writer() {
                @Override
                public void write(Parcel p) {
                    p.writeString("Google");
                    p.writeString("");
                    p.writeInt(0);
                    p.writeInt(0);
                    p.writeInt(3);
                    p.writeStrongBinder(new SilentCallback());
                }
            }, Value.NONE));
            await("silent", silentLatch, 20);

            logResult("isChipInitialized", call(binder, 83, null, Value.BOOLEAN));
            logResult("close", call(binder, 2, null, Value.NONE));
            logResult("inactivate", call(binder, 15, null, Value.NONE));
        } catch (Throwable t) {
            note("probe failed " + stack(t));
        } finally {
            try {
                unbindService(connection);
            } catch (Throwable ignored) {
            }
            finish();
        }
    }

    private static void await(String label, CountDownLatch latch, int seconds) {
        try {
            boolean ok = latch.await(seconds, TimeUnit.SECONDS);
            Log.i(TAG, label + " callbackWait=" + ok);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            Log.w(TAG, label + " interrupted");
        }
    }

    private void logResult(String label, Result result) {
        note(label + " => " + result);
    }

    private void note(String message) {
        Log.i(TAG, message);
        try {
            FileWriter writer = new FileWriter(getFileStreamPath("probe.log"), true);
            writer.write(System.currentTimeMillis() + " " + message + "\n");
            writer.close();
        } catch (Throwable ignored) {
        }
    }

    private static String stack(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    private static Result call(IBinder binder, int code, Writer writer, Value value) throws RemoteException {
        Parcel data = Parcel.obtain();
        Parcel reply = Parcel.obtain();
        try {
            data.writeInterfaceToken(MFI_DESCRIPTOR);
            if (writer != null) {
                writer.write(data);
            }
            boolean transacted = binder.transact(code, data, reply, 0);
            if (!transacted) {
                return Result.transport("transact returned false");
            }
            reply.readException();
            if (reply.readInt() == 0) {
                return Result.transport("null typed result");
            }
            Result result = readBase(reply);
            if (value == Value.BYTE_ARRAY) {
                int n = reply.readInt();
                if (n >= 0) {
                    byte[] bytes = new byte[n];
                    reply.readByteArray(bytes);
                    result.value = hex(bytes);
                } else {
                    result.value = "null";
                }
            } else if (value == Value.BOOLEAN) {
                int v = reply.readInt();
                result.value = v < 0 ? "null" : String.valueOf(v != 0);
            } else if (value == Value.INT) {
                result.value = String.valueOf(reply.readInt());
            }
            return result;
        } finally {
            reply.recycle();
            data.recycle();
        }
    }

    private static Result readBase(Parcel p) {
        Result result = new Result();
        result.exceptionType = p.readInt();
        result.message = p.readString();
        result.id = p.readInt();
        result.type = p.readInt();
        result.statusFlag1 = p.readInt();
        result.statusFlag2 = p.readInt();
        int otherPresent = p.readInt();
        result.otherAppInfo = otherPresent != 0 ? "present" : "null";
        return result;
    }

    private static String hex(byte[] bytes) {
        StringBuilder out = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            out.append(String.format("%02X", b & 0xff));
        }
        return out.toString();
    }

    private final class FelicaEventCallback extends Binder {
        FelicaEventCallback() {
            attachInterface(null, EVENT_DESCRIPTOR);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            data.enforceInterface(EVENT_DESCRIPTOR);
            if (code == 1) {
                note("activateCallback.finished");
                activateLatch.countDown();
                return true;
            }
            if (code == 2) {
                int id = data.readInt();
                String msg = data.readString();
                int hasOther = data.readInt();
                note("activateCallback.error id=" + id + " msg=" + msg + " other=" + hasOther);
                activateLatch.countDown();
                return true;
            }
            return false;
        }
    }

    private final class ServerCallback extends Binder {
        ServerCallback() {
            attachInterface(null, SERVER_CALLBACK_DESCRIPTOR);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            data.enforceInterface(SERVER_CALLBACK_DESCRIPTOR);
            if (code == 1) {
                note("serverCallback.success");
                reply.writeNoException();
                serverLatch.countDown();
                return true;
            }
            if (code == 2) {
                note("serverCallback.error id=" + data.readInt() + " msg=" + data.readString());
                reply.writeNoException();
                serverLatch.countDown();
                return true;
            }
            return false;
        }
    }

    private final class SilentCallback extends Binder {
        SilentCallback() {
            attachInterface(null, SILENT_CALLBACK_DESCRIPTOR);
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
            data.enforceInterface(SILENT_CALLBACK_DESCRIPTOR);
            if (code == 1) {
                note("silentCallback.success");
                silentLatch.countDown();
                return true;
            }
            if (code == 2) {
                note("silentCallback.successNoLogin");
                silentLatch.countDown();
                return true;
            }
            if (code == 3) {
                note("silentCallback.requestActivity present=" + data.readInt());
                silentLatch.countDown();
                return true;
            }
            if (code == 4) {
                note("silentCallback.error id=" + data.readInt() + " msg=" + data.readString());
                silentLatch.countDown();
                return true;
            }
            return false;
        }
    }

    private interface Writer {
        void write(Parcel parcel);
    }

    private enum Value {
        NONE,
        BYTE_ARRAY,
        BOOLEAN,
        INT
    }

    private static final class Result {
        int exceptionType;
        String message;
        int id;
        int type;
        int statusFlag1;
        int statusFlag2;
        String otherAppInfo;
        String value;
        String transportError;

        static Result transport(String error) {
            Result result = new Result();
            result.transportError = error;
            return result;
        }

        @Override
        public String toString() {
            if (transportError != null) {
                return "transportError=" + transportError;
            }
            return "exceptionType=" + exceptionType
                    + " id=" + id
                    + " type=" + type
                    + " status=(" + statusFlag1 + "," + statusFlag2 + ")"
                    + " msg=" + message
                    + " other=" + otherAppInfo
                    + " value=" + value;
        }
    }
}
