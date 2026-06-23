package com.felicanetworks.mfc.mfi.omapi;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.se.omapi.Channel;
import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.se.omapi.Session;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
public class GpController {
    private static final long SERVICE_WAIT_TIMEOUT = 1000;
    private static final byte[] SW_SUCCESS_CODE = {-112, 0};
    private Channel mChannel;
    private SEServiceListener mListener;
    private Reader mReader;
    private SEService mService;
    private Session mSession;
    private AtomicBoolean mIsCancelled = new AtomicBoolean(false);
    private boolean mServiceConnected = false;
    private boolean mInitialized = false;
    private final Object mLock = new Object();

    private class SEServiceListener implements SEService.OnConnectedListener {
        private SEServiceListener() {
        }

        @Override // android.se.omapi.SEService.OnConnectedListener
        public void onConnected() {
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService.OnConnectedListener", "onConnected");
            LogMgr.log(3, "000");
            synchronized (GpController.this.mLock) {
                GpController.this.setServiceConnectedFlag(true);
                GpController.this.mLock.notifyAll();
            }
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService.OnConnectedListener", "onConnected");
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

    public GpController() throws UnsupportedOperationException {
        LogMgr.log(3, "000");
        if (Build.VERSION.SDK_INT < 28) {
            throw new UnsupportedOperationException("API is insufficient. API 28+ is needed.");
        }
        this.mListener = new SEServiceListener();
        LogMgr.log(3, "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void init(Context context) throws InterruptedException, GpException {
        LogMgr.log(3, "000");
        try {
            checkCancelFlag();
            Reader[] readers = null;
            Object[] objArr = 0;
            if (this.mService == null) {
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService", "$init");
                this.mService = new SEService(context.getApplicationContext(), new SEServiceExecutor(), this.mListener);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService", "$init");
                synchronized (this.mLock) {
                    if (!getServiceConnectedFlag()) {
                        try {
                            this.mLock.wait(SERVICE_WAIT_TIMEOUT);
                        } catch (InterruptedException unused) {
                            LogMgr.log(1, "800 : InterruptedException");
                            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_SESERVICE, GpException.FwsErrorMsg.UNKNOWN_ERROR));
                        }
                    }
                }
            }
            checkCancelFlag();
            LogMgr.log(6, "001");
            if (!isSEServiceConnected()) {
                LogMgr.log(1, "801 not connected.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_SESERVICE, GpException.FwsErrorMsg.TIMEOUT));
            }
            LogMgr.log(6, "002");
            if (this.mReader == null) {
                String gpEseReaderName = Property.getGpEseReaderName();
                try {
                    LogMgr.log(3, "003 [access-omapi] SEService.getReaders() in");
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService", "getReaders");
                    readers = this.mService.getReaders();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService", "getReaders");
                    LogMgr.log(3, "004 [access-omapi] SEService.getReaders() out");
                } catch (GpException e) {
                    close();
                    throw e;
                } catch (IllegalStateException e2) {
                    LogMgr.log(1, "805 Unable to communicate with Secure Element.");
                    LogMgr.printStackTrace(7, e2);
                    close();
                    Process.killProcess(Process.myPid());
                } catch (RuntimeException e3) {
                    LogMgr.log(1, "806 Unable to communicate with Secure Element.");
                    close();
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e3), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_GETREADERS, e3.getClass().getSimpleName()));
                } catch (Exception e4) {
                    LogMgr.log(1, "807 : " + e4.getClass().getSimpleName());
                    close();
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_GETREADERS, GpException.FwsErrorMsg.UNKNOWN_ERROR));
                }
                if (readers == null) {
                    LogMgr.log(1, "804 Failed to get Readers.");
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_GETREADERS, GpException.FwsErrorMsg.NULL));
                }
                LogMgr.log(6, "005");
                try {
                    int length = readers.length;
                    int i = 0;
                    while (true) {
                        if (i >= length) {
                            break;
                        }
                        Reader reader = readers[i];
                        if (reader.getName().equals(gpEseReaderName) && reader.isSecureElementPresent()) {
                            this.mReader = reader;
                            break;
                        }
                        i++;
                    }
                    if (this.mReader == null) {
                        LogMgr.log(1, "808 SecureElement not available.");
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_GETREADERS, GpException.FwsErrorMsg.NO_READER));
                    }
                } catch (GpException e5) {
                    close();
                    throw e5;
                } catch (IllegalStateException e6) {
                    LogMgr.log(1, "809 Unable to communicate with Secure Element.");
                    LogMgr.printStackTrace(7, e6);
                    close();
                    Process.killProcess(Process.myPid());
                } catch (Exception e7) {
                    LogMgr.log(1, "810 : " + e7.getClass().getSimpleName());
                    close();
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e7), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.READER_IS_SECUREELEMENT_PRESENT, GpException.FwsErrorMsg.UNKNOWN_ERROR));
                }
            }
            LogMgr.log(6, "006");
            if (this.mSession == null) {
                try {
                    LogMgr.log(3, "007 [access-omapi] Reader.openSession() in");
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Reader", "openSession");
                    this.mSession = this.mReader.openSession();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Reader", "openSession");
                    LogMgr.log(3, "008 [access-omapi] Reader.openSession() out");
                    if (this.mSession == null || this.mSession.isClosed()) {
                        LogMgr.log(1, "811 Cannot open Session.");
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.READER_OPENSESSION, GpException.FwsErrorMsg.NO_SESSION));
                    }
                } catch (GpException e8) {
                    close();
                    throw e8;
                } catch (IOException e9) {
                    LogMgr.log(1, "812 : IOException");
                    close();
                    throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e9), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.READER_OPENSESSION, e9.getClass().getSimpleName()));
                } catch (IllegalStateException e10) {
                    LogMgr.log(1, "813 : IllegalStateException");
                    LogMgr.printStackTrace(7, e10);
                    close();
                    Process.killProcess(Process.myPid());
                } catch (Exception e11) {
                    LogMgr.log(1, "814 : " + e11.getClass().getSimpleName());
                    close();
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e11), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.READER_OPENSESSION, GpException.FwsErrorMsg.UNKNOWN_ERROR));
                }
            }
            this.mInitialized = true;
            LogMgr.log(3, "999");
        } catch (GpException e12) {
            close();
            throw e12;
        } catch (NullPointerException e13) {
            LogMgr.log(1, "802 : Parameter(s) must not be null.");
            close();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e13), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_SESERVICE, e13.getClass().getSimpleName()));
        } catch (Exception e14) {
            LogMgr.log(1, "803 : " + e14.getClass().getSimpleName());
            close();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e14), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESERVICE_SESERVICE, GpException.FwsErrorMsg.UNKNOWN_ERROR));
        }
    }

    private boolean isSEServiceConnected() {
        SEService sEService = this.mService;
        return sEService != null && sEService.isConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setServiceConnectedFlag(boolean z) {
        LogMgr.log(4, "000");
        this.mServiceConnected = z;
        LogMgr.log(4, "999");
    }

    private synchronized boolean getServiceConnectedFlag() {
        return this.mServiceConnected;
    }

    public boolean isInitialized() {
        return isSEServiceConnected() && this.mInitialized;
    }

    public synchronized byte[] select(byte[] bArr) throws InterruptedException, GpException {
        byte[] bArrDoSelect;
        LogMgr.log(3, "000 aid: ");
        LogMgr.logArray(3, bArr);
        bArrDoSelect = doSelect(bArr);
        LogMgr.log(3, "999 result: ");
        LogMgr.logArray(3, bArrDoSelect);
        return bArrDoSelect;
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0128 A[Catch: IOException -> 0x022c, Exception -> 0x0254, UnsupportedOperationException -> 0x0258, NoSuchElementException -> 0x025b, SecurityException -> 0x025f, IllegalArgumentException -> 0x0263, GpException -> 0x0313, InterruptedException -> 0x0319, IllegalStateException -> 0x03cd, TRY_ENTER, TRY_LEAVE, TryCatch #4 {IOException -> 0x022c, blocks: (B:53:0x0128, B:69:0x0201, B:70:0x022b, B:77:0x0239, B:78:0x0248), top: B:153:0x0126 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0233  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private byte[] doSelect(byte[] r17) throws java.lang.InterruptedException, com.felicanetworks.mfc.mfi.omapi.GpException {
        /*
            Method dump skipped, instruction units count: 1037
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.omapi.GpController.doSelect(byte[]):byte[]");
    }

    public boolean selectNext() throws InterruptedException, GpException {
        LogMgr.log(3, "000");
        boolean zSelectNext = false;
        try {
            if (this.mChannel != null && this.mChannel.isOpen()) {
                checkCancelFlag();
                LogMgr.log(3, "001 [access-omapi] Channel.selectNext() in");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "selectNext");
                zSelectNext = this.mChannel.selectNext();
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "selectNext", "result = " + zSelectNext);
                LogMgr.log(3, "002 [access-omapi] Channel.selectNext() out");
                checkCancelFlag();
            }
        } catch (IOException e) {
            LogMgr.log(1, "800 Unable to open Channel for specified AID.");
            LogMgr.printStackTrace(7, e);
            closeChannel();
            throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_SELECT_NEXT, e.getClass().getSimpleName()));
        } catch (IllegalStateException e2) {
            LogMgr.log(1, "801 Unable to establish connection to Secure Element.");
            LogMgr.printStackTrace(7, e2);
            close();
            Process.killProcess(Process.myPid());
        } catch (InterruptedException e3) {
            LogMgr.log(1, "803 Cancelled.");
            LogMgr.printStackTrace(7, e3);
            closeChannel();
            cancelOccurred();
        } catch (UnsupportedOperationException e4) {
            LogMgr.log(1, "802 Not supported.");
            LogMgr.printStackTrace(7, e4);
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_SELECT_NEXT, e4.getClass().getSimpleName()));
        } catch (Exception e5) {
            LogMgr.log(1, "804 Unexpected Exception occurred: " + e5.getClass().getSimpleName() + ": " + e5.getMessage());
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e5), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_SELECT_NEXT, GpException.FwsErrorMsg.UNKNOWN_ERROR));
        }
        LogMgr.log(3, "999");
        return zSelectNext;
    }

    public synchronized byte[] transmit(byte[] bArr) throws InterruptedException, GpException {
        byte[] bArrDoTransmit;
        LogMgr.log(3, "000 command: ");
        LogMgr.logArray(3, bArr);
        bArrDoTransmit = doTransmit(bArr);
        LogMgr.log(3, "999 result: ");
        LogMgr.logArray(3, bArrDoTransmit);
        return bArrDoTransmit;
    }

    private byte[] doTransmit(byte[] bArr) throws InterruptedException, GpException {
        LogMgr.log(4, "000 command: ");
        LogMgr.logArray(4, bArr);
        try {
            if (bArr == null) {
                closeChannel();
                LogMgr.log(1, "800 Parameter(s) must not be null.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, GpException.FwsErrorMsg.UNKNOWN_ERROR));
            }
            if (this.mChannel == null) {
                LogMgr.log(1, "801 No opened Channel.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.SESSION_OPEN_LOGICAL_CHANNEL, GpException.FwsErrorMsg.UNKNOWN_ERROR));
            }
            checkCancelFlag();
            LogMgr.log(3, "001 [access-omapi] Channel.transmit(byte[]) in");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "transmit", "command len = " + bArr.length);
            byte[] bArrTransmit = this.mChannel.transmit(bArr);
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "transmit", "response len = " + bArrTransmit.length);
            LogMgr.log(3, "002 [access-omapi] Channel.transmit(byte[]) out");
            checkCancelFlag();
            if (bArrTransmit == null) {
                closeChannel();
                LogMgr.log(1, "801 Reponse is null.");
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, GpException.FwsErrorMsg.NULL));
            }
            LogMgr.logArray(6, bArrTransmit);
            LogMgr.log(6, "004 response length=" + bArrTransmit.length);
            LogMgr.log(4, "999");
            return bArrTransmit;
        } catch (GpException e) {
            closeChannel();
            throw e;
        } catch (IOException e2) {
            LogMgr.log(1, "802 Unable to open Channel for specified AID.");
            LogMgr.printStackTrace(7, e2);
            closeChannel();
            throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e2), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, e2.getClass().getSimpleName()));
        } catch (IllegalArgumentException e3) {
            LogMgr.log(1, "804 Invalid AID length.");
            LogMgr.printStackTrace(7, e3);
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e3), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, e3.getClass().getSimpleName()));
        } catch (IllegalStateException e4) {
            LogMgr.log(1, "803 Unable to establish connection to Secure Element.");
            LogMgr.printStackTrace(7, e4);
            close();
            Process.killProcess(Process.myPid());
            return null;
        } catch (InterruptedException e5) {
            LogMgr.log(1, "807 Cancelled.");
            LogMgr.printStackTrace(7, e5);
            closeChannel();
            cancelOccurred();
            return null;
        } catch (NullPointerException e6) {
            LogMgr.log(1, "806 command is null.");
            LogMgr.printStackTrace(7, e6);
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e6), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, e6.getClass().getSimpleName()));
        } catch (SecurityException e7) {
            LogMgr.log(1, "805 Denied access to specified AID.");
            LogMgr.printStackTrace(7, e7);
            closeChannel();
            throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e7), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, e7.getClass().getSimpleName()));
        } catch (Exception e8) {
            LogMgr.log(1, "808 Unexpected Exception occurred: " + e8.getClass().getSimpleName() + ": " + e8.getMessage());
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e8), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, GpException.FwsErrorMsg.UNKNOWN_ERROR));
        }
    }

    public synchronized byte[] getCurrentSelectResponse() throws GpException {
        byte[] selectResponse;
        LogMgr.log(3, "000");
        selectResponse = null;
        if (this.mChannel != null && this.mChannel.isOpen()) {
            try {
                try {
                    LogMgr.log(3, "001 [access-omapi] Channel.getSelectResponse() in");
                    selectResponse = this.mChannel.getSelectResponse();
                    LogMgr.log(3, "002 [access-omapi] Channel.getSelectResponse() out");
                    if (selectResponse == null) {
                        LogMgr.log(1, "800 getSelectResponse is Null.");
                        closeChannel();
                        throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_GET_SELECT_RESPONSE, GpException.FwsErrorMsg.NULL));
                    }
                    SelectResponse selectResponse2 = new SelectResponse(selectResponse);
                    if (!selectResponse2.isStatusSuccess()) {
                        LogMgr.log(1, "801 Unable to complete SELECT for specified AID.");
                        closeChannel();
                        throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(selectResponse2.getSw())), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_GET_SELECT_RESPONSE, GpException.FwsErrorMsg.SW + StringUtil.bytesToHexString(selectResponse2.getSw())));
                    }
                } catch (IllegalArgumentException e) {
                    LogMgr.log(1, "802 : Response format error");
                    closeChannel();
                    LogMgr.printStackTrace(7, e);
                    throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_GET_SELECT_RESPONSE, GpException.FwsErrorMsg.FORMAT_ERROR));
                } catch (IllegalStateException e2) {
                    LogMgr.log(1, "803 Unable to establish connection to Secure Element.");
                    LogMgr.printStackTrace(7, e2);
                    close();
                    Process.killProcess(Process.myPid());
                }
            } catch (GpException e3) {
                closeChannel();
                throw e3;
            } catch (Exception e4) {
                LogMgr.log(1, "804 Unexpected Exception occurred: " + e4.getClass().getSimpleName() + ": " + e4.getMessage());
                closeChannel();
                throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint(GpException.OmapiName.CHANNEL_TRANSMIT, GpException.FwsErrorMsg.UNKNOWN_ERROR));
            }
        }
        LogMgr.log(3, "999");
        return selectResponse;
    }

    public synchronized void closeChannel() {
        LogMgr.log(3, "000");
        if (this.mChannel != null && this.mChannel.isOpen()) {
            LogMgr.log(3, "001 [access-omapi] Channel.close() in");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
            this.mChannel.close();
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
            LogMgr.log(3, "002 [access-omapi] Channel.close() out");
        }
        this.mChannel = null;
        LogMgr.log(3, "999");
    }

    public boolean cancel() {
        LogMgr.log(3, "000");
        boolean zCompareAndSet = this.mIsCancelled.compareAndSet(false, true);
        LogMgr.log(3, "999 result: " + zCompareAndSet);
        return zCompareAndSet;
    }

    public void clearCancelFlag() {
        LogMgr.log(3, "000");
        LogMgr.log(3, "999 result: " + this.mIsCancelled.compareAndSet(true, false));
    }

    private void cancelOccurred() throws InterruptedException {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 result: " + this.mIsCancelled.compareAndSet(true, false));
        throw new InterruptedException();
    }

    private void checkCancelFlag() throws InterruptedException {
        LogMgr.log(6, "000");
        if (isCancelled()) {
            LogMgr.log(2, "700 Cancelled.");
            throw new InterruptedException();
        }
        LogMgr.log(6, "999");
    }

    public boolean isCancelled() {
        LogMgr.log(6, "000 = " + this.mIsCancelled.get());
        return this.mIsCancelled.get();
    }

    public void close() {
        LogMgr.log(3, "000");
        shutdown();
        LogMgr.log(3, "999");
    }

    private void shutdown() {
        LogMgr.log(6, "000");
        if (this.mChannel != null) {
            LogMgr.log(3, "001 [access-omapi] Channel.close() in");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
            this.mChannel.close();
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
            LogMgr.log(3, "002 [access-omapi] Channel.close() out");
            this.mChannel = null;
        }
        if (this.mSession != null) {
            LogMgr.log(3, "003 [access-omapi] Session.close() in");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Session", "close");
            this.mSession.close();
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Session", "close");
            LogMgr.log(3, "004 [access-omapi] Session.close() out");
            this.mSession = null;
        }
        this.mReader = null;
        if (this.mService != null) {
            LogMgr.log(3, "005 [access-omapi] SEService.shutdown() in");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService", "shutdown");
            this.mService.shutdown();
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService", "shutdown");
            LogMgr.log(3, "006 [access-omapi] SEService.shutdown() out");
            this.mService = null;
        }
        setServiceConnectedFlag(false);
        this.mInitialized = false;
        LogMgr.log(3, "999");
    }

    public Session getOmapiSession() {
        LogMgr.log(3, "000");
        return this.mSession;
    }
}
