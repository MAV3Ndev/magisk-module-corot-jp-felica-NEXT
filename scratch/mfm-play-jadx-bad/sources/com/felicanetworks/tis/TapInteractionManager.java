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
import android.se.omapi.SEService$OnConnectedListener;
import android.se.omapi.Session;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.mfc.FelicaEventListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.tis.Felica;
import com.felicanetworks.tis.datatype.TransactionDetectingEvent;
import com.felicanetworks.tis.datatype.TransactionInfo;
import com.felicanetworks.tis.datatype.TransactionInfoResponse;
import com.felicanetworks.tis.resolver.TransactionInfoResolver;
import com.felicanetworks.tis.util.AccessConfig;
import com.felicanetworks.tis.util.LogMgr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes3.dex */
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

    private final class SEListener implements SEService$OnConnectedListener {
        private SEListener() {
        }

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

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, CONST_STR, INVOKE] complete} */
    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [167=4] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0065 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    void executeTask(boolean z) {
        TransactionDetectingEvent transactionDetectingEvent;
        LogMgr.log(4, "000");
        LogMgr.log(7, "[PFM]TapInteractionManager#executeTask()");
        try {
            if (z) {
                try {
                    LogMgr.log(6, "001 FeliCa connect");
                    this.mConnection.connect();
                    transactionDetectingEvent = new TransactionDetectingEvent(this.mEvent);
                } catch (Exception e) {
                    LogMgr.log(1, "802 " + e.getClass().getSimpleName() + " " + e.getMessage());
                    if (z) {
                        LogMgr.log(6, "003 Felica disconnect");
                    }
                    LogMgr.log(4, "999");
                }
                if (!transactionDetectingEvent.isEventValid()) {
                    LogMgr.log(2, "700 : Something wrong with event id or end counter");
                    if (z) {
                        LogMgr.log(6, "003 Felica disconnect");
                        try {
                            this.mConnection.disconnect();
                            return;
                        } catch (Exception unused) {
                            return;
                        }
                    }
                    return;
                }
                LogMgr.log(6, "002");
                this.mEndCounter = transactionDetectingEvent.getEndTransactionCounter();
                this.mStartTime = System.currentTimeMillis();
                FSC$$ExternalSyntheticApiModelOutline0.m405m$1();
                this.mSEService = FSC$$ExternalSyntheticApiModelOutline0.m(this.mContext, new SEServiceExecutor(), new SEListener());
                synchronized (this.mSeServiceBoundLock) {
                    if (!this.mServiceConnected) {
                        try {
                            this.mSeServiceBoundLock.wait(1000L);
                        } catch (InterruptedException unused2) {
                            LogMgr.log(1, "800");
                        }
                    }
                }
                if (this.mServiceConnected) {
                    onServiceConnected(z);
                } else {
                    LogMgr.log(1, "801 :  Service could not be connected");
                    SEService sEService = this.mSEService;
                    if (sEService != null) {
                        sEService.shutdown();
                        this.mSEService = null;
                    }
                    this.mServiceConnected = false;
                }
                if (z) {
                    LogMgr.log(6, "003 Felica disconnect");
                    try {
                        this.mConnection.disconnect();
                    } catch (Exception unused3) {
                    }
                }
            } else {
                this.mStartTime = System.currentTimeMillis();
                FSC$$ExternalSyntheticApiModelOutline0.m405m$1();
                this.mSEService = FSC$$ExternalSyntheticApiModelOutline0.m(this.mContext, new SEServiceExecutor(), new SEListener());
                synchronized (this.mSeServiceBoundLock) {
                }
            }
            LogMgr.log(4, "999");
        } catch (Throwable th) {
            if (z) {
                LogMgr.log(6, "003 Felica disconnect");
                try {
                    this.mConnection.disconnect();
                } catch (Exception unused4) {
                }
            }
            throw th;
        }
    }

    private void onServiceConnected(boolean z) {
        LogMgr.log(6, "000");
        LogMgr.log(7, "[PFM]TapInteractionManager#onServiceConnected()");
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
            Session session = this.mSession;
            if (session != null && !session.isClosed()) {
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
            StringBuilder sb = new StringBuilder("001 retryCount = ");
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

    /* JADX DEBUG: Multi-variable search result rejected for r9v1, resolved type: com.felicanetworks.tis.datatype.TransactionInfoResponse */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00bc A[Catch: all -> 0x00fb, Exception -> 0x00fd, TRY_ENTER, TryCatch #0 {Exception -> 0x00fd, blocks: (B:3:0x0023, B:5:0x002a, B:6:0x002f, B:9:0x0037, B:11:0x0054, B:13:0x0057, B:14:0x005c, B:16:0x0062, B:18:0x0068, B:20:0x0071, B:21:0x0076, B:23:0x0078, B:24:0x007d, B:26:0x009a, B:28:0x009d, B:30:0x00a8, B:32:0x00ae, B:33:0x00b4, B:34:0x00b9, B:45:0x00f2, B:36:0x00bc, B:38:0x00cb, B:40:0x00df, B:41:0x00e5, B:43:0x00eb, B:44:0x00ee), top: B:57:0x0023, outer: #3 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00ee A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r9v0, types: [com.felicanetworks.tis.TapInteractionManager$1] */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private List<TransactionInfo> getTransactionInfo() {
        TransactionInfoResponse transactionInfoResponse;
        byte[] bArrTransmit;
        byte[] bArrTransmit2;
        LogMgr.log(6, "000");
        ArrayList arrayList = new ArrayList();
        LogMgr.log(6, "001 : endCounter = " + this.mEndCounter);
        try {
            try {
                fetchOmapiChannel();
                if (this.mChannel != null) {
                    long jCurrentTimeMillis = System.currentTimeMillis();
                    int i = 0;
                    while (true) {
                        if (isGetInfoFinished(i)) {
                            break;
                        }
                        TransactionInfoResponse transactionInfoResponse2 = 0;
                        transactionInfoResponse2 = 0;
                        try {
                            randomWaitBeforeTransmit();
                            bArrTransmit2 = this.mChannel.transmit(TapInteractionConst.GET_TRANSACTION_INFO_COMMAND_NORMAL);
                            LogMgr.logArray(6, "Sent : ", TapInteractionConst.GET_TRANSACTION_INFO_COMMAND_NORMAL);
                            LogMgr.logArray(6, "Received : ", bArrTransmit2);
                            checkTotalTime(this.mStartTime);
                            checkTransmitTime(jCurrentTimeMillis);
                        } catch (TimeOutException | IOException unused) {
                            transactionInfoResponse = null;
                        }
                        if (bArrTransmit2 == null || bArrTransmit2.length == 0) {
                            if (transactionInfoResponse2 == 0) {
                                LogMgr.log(6, "002 : response is valid");
                                TransactionInfo transactionInfo = transactionInfoResponse2.getTransactionInfo();
                                if (transactionInfo.isTransactionCounterValid()) {
                                    LogMgr.log(6, "003 : transaction counter is valid");
                                    arrayList.add(transactionInfo);
                                    if (isGetInfoFinished(transactionInfo.getTransactionCounter(), this.mEndCounter)) {
                                        LogMgr.log(6, "004 : reach end counter");
                                        break;
                                    }
                                    if (transactionInfo.isTransactionDetectingInfo()) {
                                        arrayList.clear();
                                    }
                                } else {
                                    continue;
                                }
                            }
                            i++;
                        } else {
                            transactionInfoResponse = new TransactionInfoResponse(bArrTransmit2);
                            try {
                            } catch (TimeOutException | IOException unused2) {
                                LogMgr.log(2, "701 : get transaction info fail 1st");
                                try {
                                    randomWaitBeforeTransmit();
                                    bArrTransmit = this.mChannel.transmit(TapInteractionConst.GET_TRANSACTION_INFO_COMMAND_RETRY);
                                    LogMgr.logArray(6, "Sent : ", TapInteractionConst.GET_TRANSACTION_INFO_COMMAND_RETRY);
                                    LogMgr.logArray(6, "Received : ", bArrTransmit);
                                    checkTotalTime(this.mStartTime);
                                    checkTransmitTime(jCurrentTimeMillis);
                                } catch (TimeOutException | IOException unused3) {
                                    LogMgr.log(1, "801 get transaction info fail 2nd");
                                }
                                if (bArrTransmit != null && bArrTransmit.length != 0) {
                                    transactionInfoResponse = new TransactionInfoResponse(bArrTransmit);
                                    if (transactionInfoResponse.isTimeOut()) {
                                        throw new TimeOutException();
                                    }
                                    if (!transactionInfoResponse.isDataExist()) {
                                        LogMgr.log(2, "702 : have no more data");
                                        LogMgr.log(6, "999");
                                        return arrayList;
                                    }
                                }
                            }
                            if (transactionInfoResponse.isTimeOut()) {
                                throw new TimeOutException();
                            }
                            if (!transactionInfoResponse.isDataExist()) {
                                LogMgr.log(2, "700 : have no more data");
                                break;
                            }
                            transactionInfoResponse2 = transactionInfoResponse;
                            if (transactionInfoResponse2 == 0) {
                            }
                            i++;
                        }
                    }
                }
            } catch (Exception e) {
                LogMgr.log(1, "802 " + e.getClass().getSimpleName() + " " + e.getMessage());
            }
            LogMgr.log(6, "999");
            return arrayList;
        } finally {
            closeOmapi();
        }
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
                Channel channel = this.mChannel;
                if (channel != null) {
                    byte[] bArrTransmit = channel.transmit(TapInteractionConst.GET_TRANSACTION_INFO_COMMAND_CLEAR);
                    LogMgr.logArray(6, "Sent : ", TapInteractionConst.GET_TRANSACTION_INFO_COMMAND_CLEAR);
                    LogMgr.logArray(6, "Received : ", bArrTransmit);
                    if (!new TransactionInfoResponse(bArrTransmit).isDataExist()) {
                        LogMgr.log(2, "700 : Clear failed");
                    }
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
                        sb = new StringBuilder("701 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(" ");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                }
            } catch (Throwable unused2) {
                if (this.mFelica != null) {
                    try {
                        LogMgr.log(6, "009 close");
                        this.mFelica.close();
                    } catch (Exception e4) {
                        LogMgr.log(2, "700 " + e4.getClass().getSimpleName() + " " + e4.getMessage());
                    }
                    try {
                        LogMgr.log(6, "010 inactivateFelica");
                        this.mFelica.inactivateFelica();
                    } catch (Exception e5) {
                        e = e5;
                        sb = new StringBuilder("701 ");
                        sb.append(e.getClass().getSimpleName());
                        sb.append(" ");
                        sb.append(e.getMessage());
                        LogMgr.log(2, sb.toString());
                    }
                }
            }
        } catch (Exception e6) {
            LogMgr.log(1, "803 " + e6.getClass().getSimpleName() + " " + e6.getMessage());
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
                    sb = new StringBuilder("701 ");
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
