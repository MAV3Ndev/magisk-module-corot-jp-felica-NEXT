package com.felicanetworks.semc.omapi;

import android.content.Context;
import android.se.omapi.Channel;
import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.se.omapi.SEService$OnConnectedListener;
import android.se.omapi.Session;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.semc.SemChipHolder;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.StringUtil;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class GpController {
    public static final byte[] AMSD_AID = {-96, 0, 0, 6, -128, 2, 0, 0, 127, 0, 0, 0, 0, 0, 0, 0};
    public static final byte[] CRS_AID = {-96, 0, 0, 1, 81, 67, 82, 83, 0};
    private static final int RETRY_COUNT_FOR_OPEN_CHANNEL = 3;
    private static final long SERVICE_WAIT_TIMEOUT = 1000;
    private static final int SLEEP_BEFORE_RETRY_OPEN_CHANNEL = 100;
    private Channel mChannel;
    private SEServiceListener mListener;
    private Reader mReader;
    private SEService mSEService;
    private Session mSession;
    private AtomicBoolean mIsCancelled = new AtomicBoolean(false);
    private boolean mSEServiceConnected = false;
    private boolean mInitialized = false;
    private boolean mDuringSeAccess = false;
    private final Object mLock = new Object();
    private SemChipHolder.OnCanceledListener mOnCanceledListener = null;

    private class SEServiceListener implements SEService$OnConnectedListener {
        private SEServiceListener() {
        }

        public void onConnected() {
            LogMgr.log(5, "000");
            synchronized (GpController.this.mLock) {
                GpController.this.setSEServiceConnectedFlag(true);
                GpController.this.mLock.notifyAll();
            }
        }
    }

    private static class SEServiceExecutor implements Executor {
        private SEServiceExecutor() {
        }

        @Override // java.util.concurrent.Executor
        public void execute(Runnable runnable) {
            runnable.run();
        }
    }

    public GpController() {
        LogMgr.log(5, "000");
        this.mListener = new SEServiceListener();
        LogMgr.log(5, "999");
    }

    public String getReaderName() {
        String name;
        LogMgr.log(5, "000");
        if (this.mReader == null) {
            name = "";
        } else {
            LogMgr.log(3, "001 [access-omapi] Reader.getName() in");
            name = this.mReader.getName();
            LogMgr.log(3, "002 [access-omapi] Reader.getName() out. result=" + name);
        }
        LogMgr.log(5, "999 ret=[" + name + "]");
        return name;
    }

    /* JADX WARN: Removed duplicated region for block: B:117:0x0186 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0180 A[Catch: Exception -> 0x0189, GpException -> 0x01fe, IllegalStateException -> 0x0208, TRY_LEAVE, TryCatch #4 {Exception -> 0x0189, blocks: (B:49:0x016f, B:51:0x0180), top: B:105:0x016f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void init(Context context, String str) throws GpException, InterruptedException {
        boolean z = (str == null || str.isEmpty()) ? false : true;
        LogMgr.log(5, "000");
        this.mDuringSeAccess = true;
        try {
            checkCancelFlag();
            if (this.mSEService == null) {
                LogMgr.log(3, "001 [access-omapi] SEService() in");
                FSC$$ExternalSyntheticApiModelOutline0.m405m$1();
                this.mSEService = FSC$$ExternalSyntheticApiModelOutline0.m(context.getApplicationContext(), new SEServiceExecutor(), this.mListener);
                LogMgr.log(3, "002 [access-omapi] SEService() out");
                synchronized (this.mLock) {
                    if (isNotSEServiceConnected()) {
                        try {
                            this.mLock.wait(SERVICE_WAIT_TIMEOUT);
                        } catch (InterruptedException unused) {
                            LogMgr.log(1, "800 InterruptedException");
                            onCanceled();
                            this.mDuringSeAccess = false;
                            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", "UnknownError"));
                        }
                    }
                }
            }
            checkCancelFlag();
            if (!isSEServiceConnected()) {
                LogMgr.log(1, "801 not connected.");
                this.mDuringSeAccess = false;
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", "TimeOut"));
            }
            if (this.mReader == null) {
                try {
                    LogMgr.log(3, "003 [access-omapi] SEService.getReaders() in");
                    Reader[] readers = this.mSEService.getReaders();
                    LogMgr.log(3, "004 [access-omapi] SEService.getReaders() out");
                    if (readers == null) {
                        LogMgr.log(1, "806 Failed to get Readers.");
                        throw new GpException(201, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", "Null"));
                    }
                    try {
                        try {
                            try {
                                int length = readers.length;
                                int i = 0;
                                while (true) {
                                    if (i >= length) {
                                        break;
                                    }
                                    Reader reader = readers[i];
                                    LogMgr.log(3, "005 [access-omapi] Reader.getName() in");
                                    String name = reader.getName();
                                    LogMgr.log(3, "006 [access-omapi] Reader.getName() out result=" + name);
                                    if (z) {
                                        if (name.equals(str)) {
                                            LogMgr.log(3, "007 [access-omapi] Reader.isSecureElementPresent() in");
                                            boolean zIsSecureElementPresent = reader.isSecureElementPresent();
                                            LogMgr.log(3, "008 [access-omapi] Reader.isSecureElementPresent() out result=" + zIsSecureElementPresent);
                                            if (zIsSecureElementPresent) {
                                                LogMgr.log(8, "009 Found reader. name=[" + str + "]");
                                                this.mReader = reader;
                                                break;
                                            }
                                        } else {
                                            continue;
                                        }
                                        i++;
                                    } else {
                                        LogMgr.log(3, "010 [access-omapi] Reader.isSecureElementPresent() in");
                                        boolean zIsSecureElementPresent2 = reader.isSecureElementPresent();
                                        LogMgr.log(3, "011 [access-omapi] Reader.isSecureElementPresent() out result=" + zIsSecureElementPresent2);
                                        if (zIsSecureElementPresent2) {
                                            try {
                                                LogMgr.log(3, "012 [access-omapi] Reader.openSession() in");
                                                Session sessionOpenSession = reader.openSession();
                                                LogMgr.log(3, "013 [access-omapi] Reader.openSession() out");
                                                this.mSession = sessionOpenSession;
                                                try {
                                                } catch (Exception e) {
                                                    LogMgr.log(1, "813 Failed access to chip. e[" + e + "]");
                                                }
                                            } catch (IllegalStateException e2) {
                                                LogMgr.log(1, "810 IllegalStateException");
                                                LogMgr.printStackTrace(9, e2);
                                                close();
                                                throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e2), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#isSecureElementPresent", e2.getClass().getSimpleName()));
                                            } catch (Exception e3) {
                                                LogMgr.log(1, "811 Cannot open Session. e[" + e3 + "]");
                                            }
                                            if (!new SelectResponse(select(AMSD_AID)).isStatusFailed()) {
                                                LogMgr.log(1, "812 Unable to complete SELECT for specified AID.");
                                                i++;
                                            } else {
                                                this.mReader = reader;
                                                break;
                                            }
                                        } else if (!new SelectResponse(select(AMSD_AID)).isStatusFailed()) {
                                        }
                                    }
                                }
                                if (this.mReader == null) {
                                    LogMgr.log(1, "814 SecureElement not available.");
                                    this.mSession = null;
                                    this.mDuringSeAccess = false;
                                    throw new GpException(201, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", "NoReader"));
                                }
                            } catch (Exception e4) {
                                LogMgr.log(1, "817 " + e4.getClass().getSimpleName());
                                close();
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#isSecureElementPresent", "UnknownError"));
                            }
                        } catch (GpException e5) {
                            LogMgr.log(1, "816 GpException occurred.");
                            close();
                            throw e5;
                        }
                    } catch (IllegalStateException e6) {
                        LogMgr.log(1, "815 Unable to communicate with Secure Element.");
                        LogMgr.printStackTrace(9, e6);
                        close();
                        throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e6), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#isSecureElementPresent", e6.getClass().getSimpleName()));
                    }
                } catch (GpException e7) {
                    close();
                    throw e7;
                } catch (IllegalStateException e8) {
                    LogMgr.log(1, "807 Unable to communicate with Secure Element.");
                    LogMgr.printStackTrace(9, e8);
                    close();
                    throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e8), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", e8.getClass().getSimpleName()));
                } catch (RuntimeException e9) {
                    LogMgr.log(1, "808 Unable to communicate with Secure Element.");
                    close();
                    throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e9), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", e9.getClass().getSimpleName()));
                } catch (Exception e10) {
                    LogMgr.log(1, "809 " + e10.getClass().getSimpleName());
                    close();
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e10), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", "UnknownError"));
                }
            }
            if (this.mSession == null) {
                this.mSession = openSession(this.mReader);
            }
            this.mInitialized = true;
            this.mDuringSeAccess = false;
            LogMgr.log(5, "999");
        } catch (GpException e11) {
            LogMgr.log(1, "803 GpException occurred.");
            close();
            throw e11;
        } catch (InterruptedException e12) {
            LogMgr.log(1, "804 InterruptedException occurred.");
            close();
            onCanceled();
            throw e12;
        } catch (NullPointerException e13) {
            LogMgr.log(1, "802 " + e13.getClass().getSimpleName());
            close();
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e13), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", "UnknownError"));
        } catch (Exception e14) {
            LogMgr.log(1, "805 " + e14.getClass().getSimpleName());
            close();
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e14), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", "UnknownError"));
        }
    }

    private Session openSession(Reader reader) throws GpException {
        boolean zIsClosed;
        LogMgr.log(8, "000");
        if (reader == null) {
            LogMgr.log(1, "800 Arg reader is null.");
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", "UnknownError"));
        }
        try {
            LogMgr.log(3, "001 [access-omapi] Reader.openSession() in");
            Session sessionOpenSession = reader.openSession();
            LogMgr.log(3, "002 [access-omapi] Reader.openSession() out");
            if (sessionOpenSession != null) {
                LogMgr.log(3, "003 [access-omapi] Session.isClosed() in");
                zIsClosed = sessionOpenSession.isClosed();
                LogMgr.log(3, "004 [access-omapi] Session.isClosed() out");
            } else {
                zIsClosed = true;
            }
            if (zIsClosed) {
                LogMgr.log(1, "801 Cannot open Session.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", "NoSession"));
            }
            LogMgr.log(8, "999");
            return sessionOpenSession;
        } catch (GpException e) {
            LogMgr.log(1, "804 GpException");
            close();
            throw e;
        } catch (IOException e2) {
            LogMgr.log(1, "802 IOException");
            close();
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e2), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", e2.getClass().getSimpleName()));
        } catch (IllegalStateException e3) {
            LogMgr.log(1, "803 IllegalStateException");
            LogMgr.printStackTrace(9, e3);
            close();
            throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e3), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", e3.getClass().getSimpleName()));
        } catch (Exception e4) {
            LogMgr.log(1, "805 " + e4.getClass().getSimpleName());
            close();
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", "UnknownError"));
        }
    }

    private boolean isSEServiceConnected() {
        boolean zIsConnected;
        LogMgr.log(6, "000");
        if (this.mSEService != null) {
            LogMgr.log(3, "001 [access-omapi] SEService.isConnected() in");
            zIsConnected = this.mSEService.isConnected();
            LogMgr.log(3, "002 [access-omapi] SEService.isConnected() out result=" + zIsConnected);
        } else {
            zIsConnected = false;
        }
        LogMgr.log(6, "999");
        return zIsConnected;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setSEServiceConnectedFlag(boolean z) {
        LogMgr.log(6, "000");
        this.mSEServiceConnected = z;
        LogMgr.log(6, "999");
    }

    private boolean isNotSEServiceConnected() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return !this.mSEServiceConnected;
    }

    public boolean isInitialized() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return isSEServiceConnected() && this.mInitialized;
    }

    public synchronized byte[] select(byte[] bArr) throws GpException, InterruptedException {
        byte[] bArrDoSelect;
        LogMgr.log(5, "000 aid : ");
        LogMgr.logArray(6, bArr);
        bArrDoSelect = doSelect(bArr, (byte) 0);
        LogMgr.log(5, "999 result : ");
        LogMgr.logArray(6, bArrDoSelect);
        return bArrDoSelect;
    }

    public synchronized byte[] select(byte[] bArr, byte b) throws GpException, InterruptedException {
        byte[] bArrDoSelect;
        LogMgr.log(5, "000 aid : ");
        LogMgr.logArray(6, bArr);
        bArrDoSelect = doSelect(bArr, b);
        LogMgr.log(5, "999 result : ");
        LogMgr.logArray(6, bArrDoSelect);
        return bArrDoSelect;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:263:0x02dd */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0138 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x013b  */
    /* JADX WARN: Type inference failed for: r2v39 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [boolean] */
    /* JADX WARN: Type inference failed for: r2v63 */
    /* JADX WARN: Type inference failed for: r3v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r3v35 */
    /* JADX WARN: Type inference failed for: r3v37 */
    /* JADX WARN: Type inference failed for: r3v38 */
    /* JADX WARN: Type inference failed for: r3v49 */
    /* JADX WARN: Type inference failed for: r3v6, types: [int] */
    /* JADX WARN: Type inference failed for: r3v64 */
    /* JADX WARN: Type inference failed for: r3v65 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] doSelect(byte[] bArr, byte b) throws GpException, InterruptedException {
        String str;
        int i;
        boolean z;
        boolean z2;
        int i2;
        boolean z3;
        ?? r2;
        ?? r3;
        boolean zIsOpen;
        Object obj;
        int i3;
        Channel channelOpenBasicChannel;
        String str2;
        ?? r32 = "013 [access-omapi] Channel.getSelectResponse() in";
        LogMgr.log(6, "000 aid : ");
        LogMgr.logArray(6, bArr);
        this.mDuringSeAccess = true;
        try {
            try {
                try {
                    checkCancelFlag();
                    try {
                        try {
                            try {
                                if (this.mChannel != null) {
                                    try {
                                        LogMgr.log(3, "001 [access-omapi] Channel.isOpen() in");
                                        zIsOpen = this.mChannel.isOpen();
                                        LogMgr.log(3, "002 [access-omapi] Channel.isOpen() out result=" + zIsOpen);
                                    } catch (UnsupportedOperationException e) {
                                        e = e;
                                        i = 1;
                                        z = false;
                                        str = null;
                                    }
                                } else {
                                    zIsOpen = false;
                                }
                                if (zIsOpen) {
                                    try {
                                        try {
                                            try {
                                                try {
                                                    try {
                                                        LogMgr.log(3, "003 [access-omapi] Channel.getSelectResponse() in");
                                                        byte[] selectResponse = this.mChannel.getSelectResponse();
                                                        LogMgr.log(3, "004 [access-omapi] Channel.getSelectResponse() out");
                                                        if (selectResponse == null) {
                                                            try {
                                                                LogMgr.log(1, "800 getSelectResponse is Null.");
                                                                closeChannel();
                                                                this.mDuringSeAccess = false;
                                                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "Null"), "9001");
                                                            } catch (InterruptedException unused) {
                                                                z2 = true;
                                                                r2 = 0;
                                                                r3 = z2;
                                                                LogMgr.log(r3, "816 Cancelled.");
                                                                onCanceled();
                                                                cancelOccurred();
                                                                this.mDuringSeAccess = r2;
                                                                return null;
                                                            }
                                                        }
                                                        if (bArr == null) {
                                                            LogMgr.log(6, "997 response : ");
                                                            LogMgr.logArray(8, selectResponse);
                                                            this.mDuringSeAccess = false;
                                                            return selectResponse;
                                                        }
                                                        LogMgr.log(3, "005 [access-omapi] Channel.close() in");
                                                        this.mChannel.close();
                                                        LogMgr.log(3, "006 [access-omapi] Channel.close() out");
                                                        this.mChannel = null;
                                                        str = "Channel#getSelectResponse";
                                                    } catch (InterruptedException unused2) {
                                                        obj = null;
                                                        r3 = 1;
                                                        r2 = obj;
                                                        LogMgr.log(r3, "816 Cancelled.");
                                                        onCanceled();
                                                        cancelOccurred();
                                                        this.mDuringSeAccess = r2;
                                                        return null;
                                                    }
                                                } catch (IllegalArgumentException e2) {
                                                    e = e2;
                                                    str = "Channel#getSelectResponse";
                                                    LogMgr.log(1, "812 Invalid AID length.");
                                                    LogMgr.printStackTrace(9, e);
                                                    this.mDuringSeAccess = false;
                                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                                                }
                                            } catch (Exception e3) {
                                                e = e3;
                                                str = "Channel#getSelectResponse";
                                                LogMgr.log(1, "818 Unexpected Exception occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                                                this.mDuringSeAccess = false;
                                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, "UnknownError"), "9001");
                                            }
                                        } catch (GpException e4) {
                                            e = e4;
                                            z3 = false;
                                            i2 = 1;
                                            LogMgr.log(i2, "817 GpException occurred.");
                                            this.mDuringSeAccess = z3;
                                            throw e;
                                        } catch (UnsupportedOperationException e5) {
                                            e = e5;
                                            str = "Channel#getSelectResponse";
                                            z = false;
                                            i = 1;
                                            LogMgr.log(i, "815 P2 parameter is not supported.");
                                            this.mDuringSeAccess = z;
                                            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                                        }
                                    } catch (IOException e6) {
                                        e = e6;
                                        str = "Channel#getSelectResponse";
                                        LogMgr.log(1, "810 Unable to open Channel for specified AID.");
                                        this.mDuringSeAccess = false;
                                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "4001");
                                    } catch (IllegalStateException e7) {
                                        e = e7;
                                        str = "Channel#getSelectResponse";
                                        LogMgr.log(1, "811 Unable to establish connection to Secure Element.");
                                        throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "9001");
                                    } catch (SecurityException e8) {
                                        e = e8;
                                        str = "Channel#getSelectResponse";
                                        LogMgr.log(1, "813 Denied access to specified AID.");
                                        LogMgr.printStackTrace(9, e);
                                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "3001");
                                    } catch (NoSuchElementException e9) {
                                        e = e9;
                                        str = "Channel#getSelectResponse";
                                        LogMgr.log(1, "814 AID is unavailable.");
                                        LogMgr.printStackTrace(9, e);
                                        this.mDuringSeAccess = false;
                                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                                    }
                                } else {
                                    str = null;
                                }
                                try {
                                    LogMgr.log(8, "005");
                                    checkCancelFlag();
                                    try {
                                        try {
                                        } catch (InterruptedException unused3) {
                                            obj = "013 [access-omapi] Channel.getSelectResponse() in";
                                        }
                                    } catch (InterruptedException unused4) {
                                        r32 = "014 [access-omapi] Channel.getSelectResponse() out";
                                    }
                                } catch (IOException e10) {
                                    e = e10;
                                } catch (IllegalArgumentException e11) {
                                    e = e11;
                                } catch (IllegalStateException e12) {
                                    e = e12;
                                } catch (SecurityException e13) {
                                    e = e13;
                                } catch (UnsupportedOperationException e14) {
                                    e = e14;
                                } catch (NoSuchElementException e15) {
                                    e = e15;
                                } catch (Exception e16) {
                                    e = e16;
                                }
                            } catch (GpException e17) {
                                e = e17;
                                z3 = false;
                            }
                        } catch (UnsupportedOperationException e18) {
                            e = e18;
                            z = false;
                            i = 1;
                        }
                    } catch (IOException e19) {
                        e = e19;
                        str = null;
                    } catch (IllegalArgumentException e20) {
                        e = e20;
                        str = null;
                    } catch (IllegalStateException e21) {
                        e = e21;
                        str = null;
                    } catch (SecurityException e22) {
                        e = e22;
                        str = null;
                    } catch (NoSuchElementException e23) {
                        e = e23;
                        str = null;
                    }
                } catch (GpException e24) {
                    e = e24;
                    i2 = 1;
                    z3 = false;
                }
            } catch (InterruptedException unused5) {
                z2 = true;
            } catch (Exception e25) {
                e = e25;
                str = null;
            }
        } catch (IOException e26) {
            e = e26;
            str = null;
        } catch (IllegalArgumentException e27) {
            e = e27;
            str = null;
        } catch (IllegalStateException e28) {
            e = e28;
            str = null;
        } catch (SecurityException e29) {
            e = e29;
            str = null;
        } catch (UnsupportedOperationException e30) {
            e = e30;
            i = 1;
            z = false;
            str = null;
        } catch (NoSuchElementException e31) {
            e = e31;
            str = null;
        }
        if (bArr != null) {
            try {
                if (bArr.length != 0) {
                    if (this.mSession == null) {
                        closeChannel();
                        LogMgr.log(1, "802 Unable to establish connection to Secure Element.");
                        throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", "UnknownError"), "3000");
                    }
                    LogMgr.log(8, "006");
                    checkCancelFlag();
                    int i4 = 0;
                    Channel channel = null;
                    while (true) {
                        if (i4 >= 3) {
                            break;
                        }
                        if (Arrays.equals(bArr, CRS_AID)) {
                            try {
                                LogMgr.log(3, "007 [access-omapi] Session.openBasicChannel(byte[]) in");
                                channelOpenBasicChannel = this.mSession.openBasicChannel(bArr);
                                i3 = i4;
                                LogMgr.log(3, "008 [access-omapi] Session.openBasicChannel(byte[]) out");
                                str2 = "Session#openBasicChannel";
                                if (channelOpenBasicChannel == null) {
                                    channel = channelOpenBasicChannel;
                                    str = str2;
                                    break;
                                }
                                try {
                                    Thread.sleep(100L);
                                } catch (IOException e32) {
                                    e = e32;
                                    str = str2;
                                    LogMgr.log(1, "810 Unable to open Channel for specified AID.");
                                    this.mDuringSeAccess = false;
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "4001");
                                } catch (IllegalArgumentException e33) {
                                    e = e33;
                                    str = str2;
                                    LogMgr.log(1, "812 Invalid AID length.");
                                    LogMgr.printStackTrace(9, e);
                                    this.mDuringSeAccess = false;
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                                } catch (IllegalStateException e34) {
                                    e = e34;
                                    str = str2;
                                    LogMgr.log(1, "811 Unable to establish connection to Secure Element.");
                                    throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "9001");
                                } catch (InterruptedException unused6) {
                                } catch (SecurityException e35) {
                                    e = e35;
                                    str = str2;
                                    LogMgr.log(1, "813 Denied access to specified AID.");
                                    LogMgr.printStackTrace(9, e);
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "3001");
                                } catch (UnsupportedOperationException e36) {
                                    e = e36;
                                    str = str2;
                                    z = false;
                                    i = 1;
                                    LogMgr.log(i, "815 P2 parameter is not supported.");
                                    this.mDuringSeAccess = z;
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                                } catch (NoSuchElementException e37) {
                                    e = e37;
                                    str = str2;
                                    LogMgr.log(1, "814 AID is unavailable.");
                                    LogMgr.printStackTrace(9, e);
                                    this.mDuringSeAccess = false;
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                                } catch (Exception e38) {
                                    e = e38;
                                    str = str2;
                                    LogMgr.log(1, "818 Unexpected Exception occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                                    this.mDuringSeAccess = false;
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, "UnknownError"), "9001");
                                }
                                channel = channelOpenBasicChannel;
                                str = str2;
                                i4 = i3 + 1;
                            } catch (IOException e39) {
                                e = e39;
                                str = "Session#openBasicChannel";
                                LogMgr.log(1, "810 Unable to open Channel for specified AID.");
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "4001");
                            } catch (IllegalArgumentException e40) {
                                e = e40;
                                str = "Session#openBasicChannel";
                                LogMgr.log(1, "812 Invalid AID length.");
                                LogMgr.printStackTrace(9, e);
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                            } catch (IllegalStateException e41) {
                                e = e41;
                                str = "Session#openBasicChannel";
                                LogMgr.log(1, "811 Unable to establish connection to Secure Element.");
                                throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "9001");
                            } catch (SecurityException e42) {
                                e = e42;
                                str = "Session#openBasicChannel";
                                LogMgr.log(1, "813 Denied access to specified AID.");
                                LogMgr.printStackTrace(9, e);
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "3001");
                            } catch (UnsupportedOperationException e43) {
                                e = e43;
                                str = "Session#openBasicChannel";
                                z = false;
                                i = 1;
                                LogMgr.log(i, "815 P2 parameter is not supported.");
                                this.mDuringSeAccess = z;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                            } catch (NoSuchElementException e44) {
                                e = e44;
                                str = "Session#openBasicChannel";
                                LogMgr.log(1, "814 AID is unavailable.");
                                LogMgr.printStackTrace(9, e);
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                            } catch (Exception e45) {
                                e = e45;
                                str = "Session#openBasicChannel";
                                LogMgr.log(1, "818 Unexpected Exception occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, "UnknownError"), "9001");
                            }
                        } else {
                            i3 = i4;
                            try {
                                LogMgr.log(3, "009 [access-omapi] Session.openLogicalChannel(byte[], byte) in");
                                Channel channelOpenLogicalChannel = this.mSession.openLogicalChannel(bArr, b);
                                LogMgr.log(3, "010 [access-omapi] Session.openLogicalChannel(byte[], byte) out");
                                channelOpenBasicChannel = channelOpenLogicalChannel;
                                str2 = "Session#openLogicalChannel";
                                if (channelOpenBasicChannel == null) {
                                }
                            } catch (IOException e46) {
                                e = e46;
                                str = "Session#openLogicalChannel";
                                LogMgr.log(1, "810 Unable to open Channel for specified AID.");
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "4001");
                            } catch (IllegalArgumentException e47) {
                                e = e47;
                                str = "Session#openLogicalChannel";
                                LogMgr.log(1, "812 Invalid AID length.");
                                LogMgr.printStackTrace(9, e);
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                            } catch (IllegalStateException e48) {
                                e = e48;
                                str = "Session#openLogicalChannel";
                                LogMgr.log(1, "811 Unable to establish connection to Secure Element.");
                                throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "9001");
                            } catch (SecurityException e49) {
                                e = e49;
                                str = "Session#openLogicalChannel";
                                LogMgr.log(1, "813 Denied access to specified AID.");
                                LogMgr.printStackTrace(9, e);
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "3001");
                            } catch (UnsupportedOperationException e50) {
                                e = e50;
                                str = "Session#openLogicalChannel";
                                z = false;
                                i = 1;
                                LogMgr.log(i, "815 P2 parameter is not supported.");
                                this.mDuringSeAccess = z;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                            } catch (NoSuchElementException e51) {
                                e = e51;
                                str = "Session#openLogicalChannel";
                                LogMgr.log(1, "814 AID is unavailable.");
                                LogMgr.printStackTrace(9, e);
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                            } catch (Exception e52) {
                                e = e52;
                                str = "Session#openLogicalChannel";
                                LogMgr.log(1, "818 Unexpected Exception occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                                this.mDuringSeAccess = false;
                                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, "UnknownError"), "9001");
                            }
                        }
                        LogMgr.log(i, "815 P2 parameter is not supported.");
                        this.mDuringSeAccess = z;
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                    }
                    if (channel == null) {
                        LogMgr.log(1, "803 Channel is null.");
                        this.mDuringSeAccess = false;
                        throw new GpException(202, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(str, "Null"), "3000");
                    }
                    LogMgr.log(3, "011 [access-omapi] Channel.isOpen() in");
                    boolean zIsOpen2 = channel.isOpen();
                    LogMgr.log(3, "012 [access-omapi] Channel.isOpen() out result=" + zIsOpen2);
                    if (!zIsOpen2) {
                        LogMgr.log(1, "804 Unable to open Channel for specified AID.");
                        this.mChannel = channel;
                        this.mDuringSeAccess = false;
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(str, "Null"), "3000");
                    }
                    try {
                        try {
                            LogMgr.log(3, "013 [access-omapi] Channel.getSelectResponse() in");
                            try {
                                LogMgr.log(3, "013 [access-omapi] Channel.getSelectResponse() in");
                                byte[] selectResponse2 = channel.getSelectResponse();
                                LogMgr.log(3, "014 [access-omapi] Channel.getSelectResponse() out");
                                LogMgr.log(3, "014 [access-omapi] Channel.getSelectResponse() out");
                                if (selectResponse2 == null) {
                                    LogMgr.log(1, "806 getSelectResponse is Null.");
                                    this.mChannel = channel;
                                    this.mDuringSeAccess = false;
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "Null"), "9001");
                                }
                                try {
                                    SelectResponse selectResponse3 = new SelectResponse(selectResponse2);
                                    if (selectResponse3.isStatusFailed()) {
                                        try {
                                            LogMgr.log(1, "807 Unable to complete SELECT for specified AID.");
                                            this.mDuringSeAccess = false;
                                            this.mChannel = channel;
                                            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.swExecutionPoint("Channel#getSelectResponse", StringUtil.bytesToHexString(selectResponse3.getSw())), true, selectResponse2);
                                        } catch (InterruptedException unused7) {
                                            r32 = 1;
                                            r2 = 0;
                                            r3 = r32;
                                            LogMgr.log(r3, "816 Cancelled.");
                                            onCanceled();
                                            cancelOccurred();
                                            this.mDuringSeAccess = r2;
                                            return null;
                                        }
                                    }
                                    LogMgr.log(8, "023");
                                    try {
                                        checkCancelFlag();
                                        this.mChannel = channel;
                                        LogMgr.log(8, "015 response length=[" + selectResponse2.length + "] response : ");
                                        LogMgr.logArray(8, selectResponse2);
                                        LogMgr.log(6, "999");
                                        return selectResponse2;
                                    } catch (InterruptedException e53) {
                                        LogMgr.log(1, "809 cancel occurred after getSelectResponse SW check.");
                                        this.mChannel = channel;
                                        throw e53;
                                    }
                                } catch (IllegalArgumentException unused8) {
                                    LogMgr.log(1, "808 getSelectResponse format error.");
                                    this.mChannel = channel;
                                    this.mDuringSeAccess = false;
                                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "FormatError"), "4001");
                                }
                            } catch (Exception e54) {
                                if (!(e54 instanceof IllegalStateException)) {
                                    LogMgr.log(1, "805 UnExpected Exception occurred in Channel#getSelectResponse.");
                                    this.mChannel = channel;
                                }
                                throw e54;
                            }
                        } catch (UnsupportedOperationException e55) {
                            e = e55;
                            str = "Channel#getSelectResponse";
                            z = false;
                            i = 1;
                            LogMgr.log(i, "815 P2 parameter is not supported.");
                            this.mDuringSeAccess = z;
                            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()), "2000");
                        }
                    } catch (InterruptedException unused9) {
                    }
                }
            } catch (InterruptedException unused10) {
                obj = null;
                r3 = 1;
                r2 = obj;
                LogMgr.log(r3, "816 Cancelled.");
                onCanceled();
                cancelOccurred();
                this.mDuringSeAccess = r2;
                return null;
            }
        }
        this.mDuringSeAccess = false;
        closeChannel();
        LogMgr.log(1, "801 Invalid AID length.");
        throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Session#openLogicalChannel", "UnknownError"), "2000");
    }

    public synchronized byte[] transmit(byte[] bArr) throws GpException, InterruptedException {
        byte[] bArrDoTransmit;
        LogMgr.log(5, "000 command : ");
        LogMgr.logArray(6, bArr);
        bArrDoTransmit = doTransmit(bArr);
        LogMgr.log(5, "999 result : ");
        LogMgr.logArray(6, bArrDoTransmit);
        return bArrDoTransmit;
    }

    private byte[] doTransmit(byte[] bArr) throws GpException, InterruptedException {
        LogMgr.log(6, "000 command : ");
        LogMgr.logArray(6, bArr);
        this.mDuringSeAccess = true;
        try {
            try {
                if (bArr == null) {
                    closeChannel();
                    LogMgr.log(1, "800 Parameter(s) must not be null.");
                    this.mDuringSeAccess = false;
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "UnknownError"), "2000");
                }
                if (this.mChannel == null) {
                    LogMgr.log(1, "801 No opened Channel.");
                    this.mDuringSeAccess = false;
                    throw new GpException(900, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "UnknownError"), "9001");
                }
                checkCancelFlag();
                LogMgr.log(3, "001 [access-omapi] Channel.transmit(byte[]) in");
                byte[] bArrTransmit = this.mChannel.transmit(bArr);
                LogMgr.log(3, "002 [access-omapi] Channel.transmit(byte[]) out");
                checkCancelFlag();
                if (bArrTransmit == null) {
                    LogMgr.log(1, "802 APDU response is Null.");
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "Null"), "4001");
                }
                try {
                    ResponseAnalyzer responseAnalyzer = new ResponseAnalyzer(bArrTransmit);
                    if (responseAnalyzer.isStatusFailed()) {
                        LogMgr.log(1, "803 Analyze APDU response SW error.");
                        this.mDuringSeAccess = false;
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.swExecutionPoint("Channel#transmit", StringUtil.bytesToHexString(responseAnalyzer.getSw())), true, bArrTransmit);
                    }
                    LogMgr.logArray(8, bArrTransmit);
                    LogMgr.log(8, "003 response length=" + bArrTransmit.length);
                    LogMgr.log(6, "999");
                    this.mDuringSeAccess = false;
                    return bArrTransmit;
                } catch (IllegalArgumentException unused) {
                    LogMgr.log(1, "804 transmitResponse format error.");
                    this.mDuringSeAccess = false;
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "FormatError"), "4001");
                }
            } catch (IllegalArgumentException e) {
                LogMgr.log(1, "807 Invalid AID length.");
                LogMgr.printStackTrace(9, e);
                this.mDuringSeAccess = false;
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e.getClass().getSimpleName()), "2000");
            }
        } catch (GpException e2) {
            LogMgr.log(1, "811 Gp Exception occurred");
            this.mDuringSeAccess = false;
            throw e2;
        } catch (IOException e3) {
            LogMgr.log(1, "805 Unable to open Channel for specified AID.");
            LogMgr.printStackTrace(9, e3);
            this.mDuringSeAccess = false;
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e3), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e3.getClass().getSimpleName()), "4001");
        } catch (IllegalStateException e4) {
            LogMgr.log(1, "806 Unable to establish connection to Secure Element.");
            LogMgr.printStackTrace(9, e4);
            throw new GpException(203, ObfuscatedMsgUtil.executionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e4.getClass().getSimpleName()), "9001");
        } catch (InterruptedException e5) {
            LogMgr.log(1, "810 Cancelled.");
            LogMgr.printStackTrace(9, e5);
            onCanceled();
            cancelOccurred();
            this.mDuringSeAccess = false;
            return null;
        } catch (NullPointerException e6) {
            LogMgr.log(1, "809 command is null.");
            LogMgr.printStackTrace(9, e6);
            this.mDuringSeAccess = false;
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e6), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e6.getClass().getSimpleName()), "2000");
        } catch (SecurityException e7) {
            LogMgr.log(1, "808 Denied access to specified AID.");
            LogMgr.printStackTrace(9, e7);
            this.mDuringSeAccess = false;
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e7), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e7.getClass().getSimpleName()), "3001");
        } catch (Exception e8) {
            LogMgr.log(1, "812 Unexpected Exception occurred : " + e8.getClass().getSimpleName() + " : " + e8.getMessage());
            this.mDuringSeAccess = false;
            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(e8), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "UnknownError"), "9001");
        }
    }

    public synchronized void closeChannel() {
        boolean zIsOpen;
        LogMgr.log(5, "000");
        if (this.mChannel != null) {
            LogMgr.log(3, "001 [access-omapi] Channel.isOpen() in");
            zIsOpen = this.mChannel.isOpen();
            LogMgr.log(3, "002 [access-omapi] Channel.isOpen() out result=" + zIsOpen);
        } else {
            zIsOpen = false;
        }
        if (zIsOpen) {
            LogMgr.log(3, "003 [access-omapi] Channel.close() in");
            this.mChannel.close();
            LogMgr.log(3, "004 [access-omapi] Channel.close() out");
        }
        this.mChannel = null;
        LogMgr.log(5, "999");
    }

    public boolean cancel() {
        LogMgr.log(5, "000");
        if (!this.mDuringSeAccess || isNotSEServiceConnected() || isCancelled()) {
            onCanceled();
            LogMgr.log(5, "999 result=false");
            return false;
        }
        boolean zCompareAndSet = this.mIsCancelled.compareAndSet(false, true);
        LogMgr.log(5, "999 result=[" + zCompareAndSet + "]");
        return zCompareAndSet;
    }

    private void onCanceled() {
        LogMgr.log(8, "000");
        SemChipHolder.OnCanceledListener onCanceledListener = this.mOnCanceledListener;
        if (onCanceledListener != null) {
            onCanceledListener.onCanceled();
        }
        clearCancelFlag();
        LogMgr.log(8, "999");
    }

    public void setOnCanceledListener(SemChipHolder.OnCanceledListener onCanceledListener) {
        LogMgr.log(5, "000");
        this.mOnCanceledListener = onCanceledListener;
        LogMgr.log(5, "999");
    }

    public void removeOnCanceledListener() {
        LogMgr.log(5, "000");
        if (this.mOnCanceledListener != null) {
            this.mOnCanceledListener = null;
        }
        LogMgr.log(5, "999");
    }

    public void clearCancelFlag() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999 result=[" + this.mIsCancelled.compareAndSet(true, false) + "]");
    }

    private void cancelOccurred() throws InterruptedException {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999 result=[" + this.mIsCancelled.compareAndSet(true, false) + "]");
        throw new InterruptedException(ObfuscatedMsgUtil.executionPoint());
    }

    private void checkCancelFlag() throws InterruptedException {
        LogMgr.log(8, "000");
        if (isCancelled()) {
            LogMgr.log(2, "700 Cancelled.");
            throw new InterruptedException(ObfuscatedMsgUtil.executionPoint());
        }
        LogMgr.log(8, "999");
    }

    public boolean isCancelled() {
        LogMgr.log(8, "000");
        LogMgr.log(8, "999 ret=[" + this.mIsCancelled.get() + "]");
        return this.mIsCancelled.get();
    }

    public void close() {
        LogMgr.log(5, "000");
        shutdown();
        LogMgr.log(5, "999");
    }

    private void shutdown() {
        LogMgr.log(5, "000");
        if (this.mChannel != null) {
            LogMgr.log(3, "001 [access-omapi] Channel.close() in");
            this.mChannel.close();
            LogMgr.log(3, "002 [access-omapi] Channel.close() out");
            this.mChannel = null;
        }
        if (this.mSession != null) {
            LogMgr.log(3, "003 [access-omapi] Session.close() in");
            this.mSession.close();
            LogMgr.log(3, "004 [access-omapi] Session.close() out");
            this.mSession = null;
        }
        this.mReader = null;
        if (this.mSEService != null) {
            LogMgr.log(3, "005 [access-omapi] SEService.shutdown() in");
            this.mSEService.shutdown();
            LogMgr.log(3, "006 [access-omapi] SEService.shutdown() out");
            this.mSEService = null;
        }
        setSEServiceConnectedFlag(false);
        this.mInitialized = false;
        this.mDuringSeAccess = false;
        LogMgr.log(5, "999");
    }
}
