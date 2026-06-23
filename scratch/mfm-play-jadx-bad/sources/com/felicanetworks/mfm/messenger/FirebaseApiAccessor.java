package com.felicanetworks.mfm.messenger;

import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import com.felicanetworks.mfm.messenger.Logger;

/* JADX INFO: loaded from: classes3.dex */
public class FirebaseApiAccessor extends Service {
    private FirebaseApiBinder binder;

    interface AccessTransaction {
        void transaction(FirebaseApi firebaseApi);
    }

    @Override // android.app.Service
    public void onCreate() {
        Logger.debug(this);
        this.binder = new FirebaseApiBinder(this);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        Logger.debug(this, intent);
        return this.binder;
    }

    @Override // android.app.Service
    public void onDestroy() {
        Logger.debug(this);
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        Logger.debug(this, intent);
        return true;
    }

    @Override // android.app.Service
    public void onRebind(Intent intent) {
        Logger.debug(this, intent);
    }

    static void access(Context context, AccessTransaction accessTransaction) {
        Logger.debug(context);
        new Connector().connect(context.getApplicationContext(), accessTransaction);
    }

    private static final class Connector implements ServiceConnection {
        private static final long WAIT_COUNT = 1000;
        private static final long WAIT_INTERVAL = 10;
        private IBinder binder;

        private Connector() {
        }

        public void connect(final Context context, final AccessTransaction accessTransaction) {
            Logger.debug(new Object[0]);
            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.messenger.FirebaseApiAccessor.Connector.1
                /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [163=5] */
                /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
                /* JADX WARN: Code restructure failed: missing block: B:18:0x003d, code lost:
                
                    com.felicanetworks.mfm.messenger.Logger.stop(r0);
                 */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public void run() {
                    try {
                        if (Connector.this.bind(context)) {
                            Logger.Stopwatch stopwatchStart = Logger.start();
                            int i = 0;
                            while (true) {
                                if (i >= Connector.WAIT_COUNT) {
                                    break;
                                }
                                if (Connector.this.binder != null) {
                                    break;
                                }
                                SystemClock.sleep(Connector.WAIT_INTERVAL);
                                i++;
                            }
                            synchronized (Connector.this) {
                                accessTransaction.transaction(new FirebaseApi(Connector.this.binder));
                            }
                        } else {
                            synchronized (Connector.this) {
                                accessTransaction.transaction(new FirebaseApi(Connector.this.binder));
                            }
                        }
                        Connector.this.unbind(context);
                    } catch (Throwable th) {
                        synchronized (Connector.this) {
                            accessTransaction.transaction(new FirebaseApi(Connector.this.binder));
                            Connector.this.unbind(context);
                            throw th;
                        }
                    }
                }
            }).start();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean bind(Context context) {
            Logger.debug(new Object[0]);
            try {
                return context.bindService(new Intent(context, (Class<?>) FirebaseApiAccessor.class), this, 1);
            } catch (Exception e) {
                Logger.warning(e);
                return false;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void unbind(Context context) {
            Logger.debug(new Object[0]);
            try {
                context.unbindService(this);
            } catch (Exception e) {
                Logger.warning(e);
            }
        }

        @Override // android.content.ServiceConnection
        public synchronized void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Logger.debug(componentName, iBinder);
            this.binder = iBinder;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Logger.debug(componentName);
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            Logger.debug(componentName);
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            Logger.debug(componentName);
        }
    }
}
