package com.felicanetworks.tis;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.SystemClock;
import android.se.omapi.Channel;
import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.se.omapi.Session;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.FelicaEventListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.tis.Felica;
import com.felicanetworks.tis.datatype.TransactionInfoResponse;
import com.felicanetworks.tis.resolver.TransactionInfoResolver;
import com.felicanetworks.tis.util.AccessConfig;
import com.felicanetworks.tis.util.LogMgr;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
class TapInteractionManager {
    private static final long FELICA_ACTIVATE_TIME_OUT = 1000;
    private static final int NODECODE_NOT_EXISTS = 65535;
    private static final long OMAPI_TIME_OUT = 120000;
    private static final int OPEN_SESSION_RETRY_MAX = 5;
    private static final int OPEN_SESSION_RETRY_WAIT = 100;
    private static final int RANDOM_WAIT_BASE = 3;
    private static final int RANDOM_WAIT_BOUND = 7;
    private static final long SERVICE_CONNECTION_TIME_OUT = 1000;
    private static final long TRANSMIT_TIME_OUT = 2800;
    FelicaConnection mConnection;
    private Context mContext;
    private int mEndCounter;
    private byte[] mEvent;
    Felica mFelica;
    private String mReaderName;
    private SEService mSEService = null;
    private boolean mServiceConnected = false;
    private final Object mSeServiceBoundLock = new Object();
    private Reader mReader = null;
    private Session mSession = null;
    private Channel mChannel = null;
    private long mStartTime = 0;
    private boolean mFelicaActivated = false;
    private final Object mFelicaActivatedLock = new Object();

    private static final class SEServiceExecutor implements Executor {
        private SEServiceExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    private static final class TimeOutException extends Exception {
        private TimeOutException() {
        }
    }

    private static final class TotalTimeCheckException extends Exception {
        private TotalTimeCheckException() {
        }
    }

    private final class SEListener implements SEService.OnConnectedListener {
        private SEListener() {
        }

        @Override // android.se.omapi.SEService.OnConnectedListener
        public void onConnected() {
            LogMgr.log(4, "000");
            synchronized (TapInteractionManager.this.mSeServiceBoundLock) {
                TapInteractionManager.this.mServiceConnected = true;
                TapInteractionManager.this.mSeServiceBoundLock.notify();
            }
        }
    }

    TapInteractionManager(Context context, String str, byte[] bArr) {
        LogMgr.log(4, "000");
        this.mContext = context;
        this.mReaderName = str;
        this.mEvent = bArr;
        this.mConnection = new FelicaConnection();
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x005d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    void executeTask(boolean r10) {
        /*
            Method dump skipped, instruction units count: 224
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.tis.TapInteractionManager.executeTask(boolean):void");
    }

    private void onServiceConnected(boolean z) {
        LogMgr.log(6, "000");
        if (z) {
            TransactionInfoResolver transactionInfoResolver = new TransactionInfoResolver(this.mContext, getTransactionInfo());
            if (!AccessConfig.isGP2Chip()) {
                LogMgr.log(6, "001 older chip");
                if (transactionInfoResolver.isSpecialSystemCodeExisted()) {
                    LogMgr.log(6, "002");
                    transactionInfoResolver.setSpecialServiceType(getSpecialServiceTypeViaMFC(transactionInfoResolver.getSpecialSystemCode(), transactionInfoResolver.getServiceTypeCheckNodeCodeList()));
                }
            }
            transactionInfoResolver.generateAndShowNotification();
        } else {
            sendClearCommand();
        }
        LogMgr.log(6, "999");
    }

    private void fetchOmapiChannel() {
        LogMgr.log(6, "000");
        try {
            if (!this.mSEService.isConnected()) {
                LogMgr.log(2, "700 : SEService is not connected");
                closeOmapi();
                return;
            }
            Reader[] readers = this.mSEService.getReaders();
            int length = readers.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                Reader reader = readers[i];
                if (reader.getName().equals(this.mReaderName)) {
                    this.mReader = reader;
                    break;
                }
                i++;
            }
            if (this.mReader == null) {
                LogMgr.log(2, "701 : get reader fail");
                closeOmapi();
                return;
            }
            checkTotalTime(this.mStartTime);
            try {
                this.mSession = this.mReader.openSession();
            } catch (IOException unused) {
                retryOpenSession(this.mReader);
            }
            if (this.mSession != null && !this.mSession.isClosed()) {
                checkTotalTime(this.mStartTime);
                Channel channelOpenLogicalChannel = this.mSession.openLogicalChannel(TapInteractionConst.TRANSACTION_MANAGEMENT_AID);
                this.mChannel = channelOpenLogicalChannel;
                if (channelOpenLogicalChannel != null && channelOpenLogicalChannel.isOpen()) {
                    checkTotalTime(this.mStartTime);
                }
                LogMgr.log(2, "703 : get channel fail");
                closeOmapi();
                return;
            }
            LogMgr.log(2, "702 : get session fail");
            closeOmapi();
            return;
        } catch (Exception e) {
            LogMgr.log(1, "800 " + e.getClass().getSimpleName() + " " + e.getMessage());
            closeOmapi();
        }
        LogMgr.log(6, "999");
    }

    private void retryOpenSession(Reader reader) throws Exception {
        LogMgr.log(6, "000");
        int i = 0;
        while (i < 5) {
            StringBuilder sb = new StringBuilder();
            sb.append("001 retryCount = ");
            i++;
            sb.append(i);
            LogMgr.log(6, sb.toString());
            try {
                Thread.sleep(100L);
            } catch (InterruptedException unused) {
            }
            try {
                continue;
                this.mSession = reader.openSession();
                break;
            } catch (IOException unused2) {
                LogMgr.log(6, "002 continue retry");
            }
        }
        LogMgr.log(6, "999");
    }

    private void closeOmapi() {
        LogMgr.log(6, "000");
        Channel channel = this.mChannel;
        if (channel != null && channel.isOpen()) {
            LogMgr.log(6, "001 Channel close");
            this.mChannel.close();
        }
        Session session = this.mSession;
        if (session != null && !session.isClosed()) {
            LogMgr.log(6, "002 Session close");
            this.mSession.close();
        }
        SEService sEService = this.mSEService;
        if (sEService != null) {
            if (sEService.isConnected()) {
                LogMgr.log(6, "003 SEService shutdown");
                this.mSEService.shutdown();
                this.mServiceConnected = false;
            }
            this.mSEService = null;
        }
        LogMgr.log(6, "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ab A[Catch: all -> 0x00ea, Exception -> 0x00ec, TRY_ENTER, TryCatch #2 {Exception -> 0x00ec, blocks: (B:3:0x0022, B:5:0x0029, B:6:0x002e, B:9:0x0036, B:11:0x004b, B:13:0x004e, B:14:0x0053, B:16:0x0059, B:18:0x005f, B:20:0x0068, B:21:0x006d, B:23:0x006f, B:24:0x0074, B:26:0x0089, B:28:0x008c, B:30:0x0097, B:32:0x009d, B:33:0x00a3, B:34:0x00a8, B:45:0x00e1, B:36:0x00ab, B:38:0x00ba, B:40:0x00ce, B:41:0x00d4, B:43:0x00da, B:44:0x00dd), top: B:60:0x0022, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00dd A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r7v0, types: [com.felicanetworks.tis.TapInteractionManager$1] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.util.List<com.felicanetworks.tis.datatype.TransactionInfo> getTransactionInfo() {
        /*
            Method dump skipped, instruction units count: 288
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.tis.TapInteractionManager.getTransactionInfo():java.util.List");
    }

    private boolean isGetInfoFinished(int i, int i2) {
        LogMgr.log(6, "000 : currentCounter = " + i + ", endCounter = " + i2);
        LogMgr.log(6, "999");
        return i == i2;
    }

    private boolean isGetInfoFinished(int i) {
        LogMgr.log(6, "000 : maxCounter = " + i);
        LogMgr.log(6, "999");
        return i >= 100;
    }

    private void sendClearCommand() {
        LogMgr.log(6, "000");
        try {
            try {
                fetchOmapiChannel();
                if (this.mChannel != null && !new TransactionInfoResponse(this.mChannel.transmit(TapInteractionConst.GET_TRANSACTION_INFO_COMMAND_CLEAR)).isDataExist()) {
                    LogMgr.log(2, "700 : Clear failed");
                }
            } catch (Exception e) {
                LogMgr.log(2, "701 " + e.getClass().getSimpleName() + " " + e.getMessage());
            }
            LogMgr.log(6, "999");
        } finally {
            closeOmapi();
        }
    }

    private void checkTotalTime(long j) throws TotalTimeCheckException {
        LogMgr.log(6, "000");
        if (System.currentTimeMillis() - j >= OMAPI_TIME_OUT) {
            LogMgr.log(1, "800 have been waiting for OMAPI response over 120 seconds");
            throw new TotalTimeCheckException();
        }
        LogMgr.log(6, "999");
    }

    private void checkTransmitTime(long j) throws TotalTimeCheckException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    private synchronized void randomWaitBeforeTransmit() {
        LogMgr.log(6, "000");
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        int iNextInt = 3;
        try {
            iNextInt = 3 + new Random().nextInt(7);
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        long jElapsedRealtime2 = SystemClock.elapsedRealtime();
        if (Math.abs(jElapsedRealtime - jElapsedRealtime2) < TRANSMIT_TIME_OUT) {
            while (((long) iNextInt) + jElapsedRealtime > jElapsedRealtime2) {
                jElapsedRealtime2 = SystemClock.elapsedRealtime();
            }
        }
        LogMgr.log(6, "999 waitTime = " + iNextInt + " (" + (jElapsedRealtime2 - jElapsedRealtime) + ") msec");
    }

    private int getSpecialServiceTypeViaMFC(int i, int[] iArr) {
        StringBuilder sb;
        LogMgr.log(6, "000");
        int i2 = 1;
        try {
            checkFelicaConnected();
            TisFelicaEventListener tisFelicaEventListener = new TisFelicaEventListener();
            LogMgr.log(6, "001 activateFelica");
            this.mFelica.activateFelica(TapInteractionConst.MFC_PERMITS, tisFelicaEventListener);
            synchronized (this.mFelicaActivatedLock) {
                if (!this.mFelicaActivated) {
                    try {
                        this.mFelicaActivatedLock.wait(1000L);
                    } catch (InterruptedException unused) {
                        LogMgr.log(1, "800");
                    }
                }
            }
        } catch (Exception e) {
            LogMgr.log(1, "801 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        int i3 = 0;
        try {
            try {
                if (this.mFelicaActivated) {
                    LogMgr.log(6, "002 open");
                    this.mFelica.open();
                    LogMgr.log(6, "003 select");
                    this.mFelica.select(i);
                    LogMgr.log(6, "004 getKeyVersionV2");
                    KeyInformation[] keyVersionV2 = this.mFelica.getKeyVersionV2(iArr);
                    LogMgr.log(6, "005 NodeCode check");
                    if (keyVersionV2[0].getAesVersion().intValue() != 65535 || keyVersionV2[0].getDesVersion().intValue() != 65535) {
                        LogMgr.log(6, "006 type1");
                    } else if (keyVersionV2[1].getAesVersion().intValue() != 65535 || keyVersionV2[1].getDesVersion().intValue() != 65535) {
                        LogMgr.log(6, "007 type2");
                        i2 = 2;
                    } else {
                        LogMgr.log(6, "008 type undefined");
                        i2 = 0;
                    }
                    i3 = i2;
                } else {
                    LogMgr.log(1, "802 not activated");
                    this.mFelicaActivated = false;
                }
                if (this.mFelica != null) {
                    try {
                        LogMgr.log(6, "009 close");
                        this.mFelica.close();
                    } catch (Exception e2) {
                        LogMgr.log(2, "700 " + e2.getClass().getSimpleName() + " " + e2.getMessage());
                    }
                    try {
                        LogMgr.log(6, "010 inactivateFelica");
                        this.mFelica.inactivateFelica();
                    } catch (Exception e3) {
                        e = e3;
                        sb = new StringBuilder();
                        sb.append("701 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(" ");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                }
            } catch (Exception e4) {
                LogMgr.log(1, "803 " + e4.getClass().getSimpleName() + " " + e4.getMessage());
                if (this.mFelica != null) {
                    try {
                        LogMgr.log(6, "009 close");
                        this.mFelica.close();
                    } catch (Exception e5) {
                        LogMgr.log(2, "700 " + e5.getClass().getSimpleName() + " " + e5.getMessage());
                    }
                    try {
                        LogMgr.log(6, "010 inactivateFelica");
                        this.mFelica.inactivateFelica();
                    } catch (Exception e6) {
                        e = e6;
                        sb = new StringBuilder();
                        sb.append("701 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(" ");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                }
            }
        } catch (Throwable unused2) {
            if (this.mFelica != null) {
                try {
                    LogMgr.log(6, "009 close");
                    this.mFelica.close();
                } catch (Exception e7) {
                    LogMgr.log(2, "700 " + e7.getClass().getSimpleName() + " " + e7.getMessage());
                }
                try {
                    LogMgr.log(6, "010 inactivateFelica");
                    this.mFelica.inactivateFelica();
                } catch (Exception e8) {
                    e = e8;
                    sb = new StringBuilder();
                    sb.append("701 ");
                    sb.append(e.getClass().getSimpleName());
                    sb.append(" ");
                    sb.append(e.getMessage());
                    LogMgr.log(2, sb.toString());
                }
            }
        }
        LogMgr.log(6, "999");
        return i3;
    }

    private void checkFelicaConnected() throws IllegalArgumentException {
        if (this.mFelica == null) {
            throw new IllegalArgumentException("connect error:Bind local FeliCa service failed.");
        }
    }

    private class TisFelicaEventListener implements FelicaEventListener {
        private TisFelicaEventListener() {
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void finished() {
            LogMgr.log(6, "000 FelicaEventListener finished().");
            synchronized (TapInteractionManager.this.mFelicaActivatedLock) {
                TapInteractionManager.this.mFelicaActivated = true;
                TapInteractionManager.this.mFelicaActivatedLock.notify();
            }
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void errorOccurred(int i, String str, AppInfo appInfo) {
            LogMgr.log(1, "800 FelicaEventListener errorOccurred.");
            LogMgr.log(1, "801 id = " + i + " : msg = " + str);
        }
    }

    private class FelicaConnection implements ServiceConnection {
        private FelicaConnection() {
        }

        void connect() throws Exception {
            if (TapInteractionManager.this.mFelica != null) {
                return;
            }
            Intent intent = new Intent();
            intent.setClass(TapInteractionManager.this.mContext, Felica.class);
            if (!TapInteractionManager.this.mContext.bindService(intent, this, 1)) {
                throw new Exception("connect error:Context#bindService() failed.");
            }
        }

        void disconnect() throws Exception {
            LogMgr.log(6, "000");
            if (TapInteractionManager.this.mFelica == null) {
                return;
            }
            TapInteractionManager.this.mContext.unbindService(this);
            TapInteractionManager.this.mFelica = null;
            LogMgr.log(6, "999 FeliCa unbind.");
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogMgr.log(6, "000 FelicaConnection#onServiceConnected called.");
            TapInteractionManager.this.mFelica = ((Felica.LocalBinder) iBinder).getInstance();
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogMgr.log(6, "000 FelicaConnection#onServiceDisonnected called.");
            TapInteractionManager.this.mFelica = null;
        }
    }
}
