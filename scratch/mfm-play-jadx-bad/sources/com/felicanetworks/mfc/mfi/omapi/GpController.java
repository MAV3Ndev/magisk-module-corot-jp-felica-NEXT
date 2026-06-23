package com.felicanetworks.mfc.mfi.omapi;

import android.content.Context;
import android.os.Build;
import android.os.Process;
import android.se.omapi.Channel;
import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.se.omapi.SEService$OnConnectedListener;
import android.se.omapi.Session;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class GpController {
    private static final int RETRY_COUNT_FOR_OPEN_CHANNEL = 3;
    private static final long SERVICE_WAIT_TIMEOUT = 1000;
    private static final int SLEEP_BEFORE_RETRY_OPEN_CHANNEL = 100;
    private Channel mChannel;
    private SEServiceListener mListener;
    private Reader mReader;
    private SEService mService;
    private Session mSession;
    private AtomicBoolean mIsCancelled = new AtomicBoolean(false);
    private boolean mServiceConnected = false;
    private boolean mInitialized = false;
    private final Object mLock = new Object();

    private class SEServiceListener implements SEService$OnConnectedListener {
        private SEServiceListener() {
        }

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
        public void execute(Runnable r) {
            r.run();
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

    /* JADX DEBUG: Multi-variable search result rejected for r4v13, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    public void init(Context context) throws InterruptedException, GpException {
        LogMgr.log(3, "000");
        try {
            checkCancelFlag();
            Reader[] readers = null;
            Object[] objArr = 0;
            if (this.mService == null) {
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService", "$init");
                FSC$$ExternalSyntheticApiModelOutline0.m405m$1();
                this.mService = FSC$$ExternalSyntheticApiModelOutline0.m(context.getApplicationContext(), new SEServiceExecutor(), this.mListener);
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService", "$init");
                synchronized (this.mLock) {
                    if (!getServiceConnectedFlag()) {
                        try {
                            this.mLock.wait(SERVICE_WAIT_TIMEOUT);
                        } catch (InterruptedException unused) {
                            LogMgr.log(1, "800 : InterruptedException");
                            throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", "UnknownError"));
                        }
                    }
                }
            }
            checkCancelFlag();
            LogMgr.log(6, "001");
            if (!isSEServiceConnected()) {
                LogMgr.log(1, "801 not connected.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", "TimeOut"));
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
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e3), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", e3.getClass().getSimpleName()));
                } catch (Exception e4) {
                    LogMgr.log(1, "807 : " + e4.getClass().getSimpleName());
                    close();
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", "UnknownError"));
                }
                if (readers == null) {
                    LogMgr.log(1, "804 Failed to get Readers.");
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", "Null"));
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
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#getReaders", "NoReader"));
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
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e7), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#isSecureElementPresent", "UnknownError"));
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
                    Session session = this.mSession;
                    if (session == null || session.isClosed()) {
                        LogMgr.log(1, "811 Cannot open Session.");
                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", "NoSession"));
                    }
                } catch (GpException e8) {
                    close();
                    throw e8;
                } catch (IOException e9) {
                    LogMgr.log(1, "812 : IOException");
                    close();
                    throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e9), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", e9.getClass().getSimpleName()));
                } catch (IllegalStateException e10) {
                    LogMgr.log(1, "813 : IllegalStateException");
                    LogMgr.printStackTrace(7, e10);
                    close();
                    Process.killProcess(Process.myPid());
                } catch (Exception e11) {
                    LogMgr.log(1, "814 : " + e11.getClass().getSimpleName());
                    close();
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e11), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", "UnknownError"));
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
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e13), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", e13.getClass().getSimpleName()));
        } catch (Exception e14) {
            LogMgr.log(1, "803 : " + e14.getClass().getSimpleName());
            close();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e14), ObfuscatedMsgUtil.omapiExecutionPoint("SEService#SEService", "UnknownError"));
        }
    }

    private boolean isSEServiceConnected() {
        SEService sEService = this.mService;
        return sEService != null && sEService.isConnected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void setServiceConnectedFlag(boolean serviceConnectState) {
        LogMgr.log(4, "000");
        this.mServiceConnected = serviceConnectState;
        LogMgr.log(4, "999");
    }

    private synchronized boolean getServiceConnectedFlag() {
        return this.mServiceConnected;
    }

    public boolean isInitialized() {
        return isSEServiceConnected() && this.mInitialized;
    }

    public synchronized byte[] select(byte[] aid) throws InterruptedException, GpException {
        byte[] bArrDoSelect;
        LogMgr.log(3, "000 aid: ");
        LogMgr.logArray(3, aid);
        bArrDoSelect = doSelect(aid);
        LogMgr.log(3, "999 result: ");
        LogMgr.logArray(3, bArrDoSelect);
        return bArrDoSelect;
    }

    /* JADX WARN: Removed duplicated region for block: B:173:0x015c A[EDGE_INSN: B:173:0x015c->B:79:0x015c BREAK  A[LOOP:0: B:44:0x00d3->B:64:0x0135], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0128  */
    /* JADX WARN: Unreachable blocks removed: 1, instructions: 1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private byte[] doSelect(byte[] aid) throws InterruptedException, GpException {
        String str;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        Channel channelOpenLogicalChannel;
        LogMgr.log(4, "000 aid: ");
        LogMgr.logArray(4, aid);
        try {
            try {
                try {
                    try {
                        checkCancelFlag();
                        try {
                            Channel channel = this.mChannel;
                            if (channel != null) {
                                if (channel.isOpen()) {
                                    try {
                                        try {
                                            try {
                                                LogMgr.log(3, "001 [access-omapi] Channel.getSelectResponse() in");
                                                byte[] selectResponse = this.mChannel.getSelectResponse();
                                                LogMgr.log(3, "002 [access-omapi] Channel.getSelectResponse() out");
                                                if (selectResponse == null) {
                                                    LogMgr.log(1, "800 getSelectResponse is Null.");
                                                    closeChannel();
                                                    throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "Null"));
                                                }
                                                if (aid == null) {
                                                    LogMgr.logArray(6, selectResponse);
                                                    LogMgr.log(4, "997");
                                                    return selectResponse;
                                                }
                                                try {
                                                    if (Arrays.equals(new SelectResponse(selectResponse).getAid(), aid)) {
                                                        LogMgr.logArray(6, selectResponse);
                                                        LogMgr.log(4, "998");
                                                        return selectResponse;
                                                    }
                                                } catch (IllegalArgumentException unused) {
                                                    LogMgr.log(2, "700 getSelectResponse format error.");
                                                }
                                                LogMgr.log(3, "003 [access-omapi] Channel.close() in");
                                                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                                this.mChannel.close();
                                                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                                LogMgr.log(3, "004 [access-omapi] Channel.close() out");
                                                this.mChannel = null;
                                                str = "Channel#getSelectResponse";
                                            } catch (IOException e) {
                                                e = e;
                                                str = "Channel#getSelectResponse";
                                                i3 = 7;
                                                i5 = 0;
                                                i4 = 1;
                                                LogMgr.log(i4, "808 Unable to open Channel for specified AID.");
                                                LogMgr.printStackTrace(i3, e);
                                                closeChannel();
                                                throw new GpException(i5, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                            }
                                        } catch (IllegalArgumentException e2) {
                                            e = e2;
                                            str = "Channel#getSelectResponse";
                                            LogMgr.log(1, "810 Invalid AID length.");
                                            LogMgr.printStackTrace(7, e);
                                            closeChannel();
                                            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                        }
                                    } catch (SecurityException e3) {
                                        e = e3;
                                        str = "Channel#getSelectResponse";
                                        LogMgr.log(1, "811 Denied access to specified AID.");
                                        LogMgr.printStackTrace(7, e);
                                        close();
                                        throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                    } catch (UnsupportedOperationException e4) {
                                        e = e4;
                                        str = "Channel#getSelectResponse";
                                        i = 7;
                                        i2 = 1;
                                        LogMgr.log(i2, "813 P2 parameter is not supported.");
                                        LogMgr.printStackTrace(i, e);
                                        closeChannel();
                                        throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                    } catch (NoSuchElementException e5) {
                                        e = e5;
                                        str = "Channel#getSelectResponse";
                                        LogMgr.log(1, "812 AID is unavailable.");
                                        LogMgr.printStackTrace(7, e);
                                        closeChannel();
                                        throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                    } catch (Exception e6) {
                                        e = e6;
                                        str = "Channel#getSelectResponse";
                                        LogMgr.log(1, "815 Unexpected Exception occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                                        LogMgr.printStackTrace(7, e);
                                        closeChannel();
                                        throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, "UnknownError"));
                                    }
                                } else {
                                    str = null;
                                }
                                try {
                                    try {
                                        LogMgr.log(6, "005");
                                        checkCancelFlag();
                                    } catch (IOException e7) {
                                        e = e7;
                                        i3 = 7;
                                        i5 = 0;
                                    }
                                    if (aid == null || aid.length == 0) {
                                        closeChannel();
                                        LogMgr.log(1, "801 Invalid AID length.");
                                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Session#openLogicalChannel", "UnknownError"));
                                    }
                                    if (this.mSession == null) {
                                        close();
                                        LogMgr.log(1, "802 Unable to establish connection to Secure Element.");
                                        throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Reader#openSession", "UnknownError"));
                                    }
                                    LogMgr.log(6, "006");
                                    checkCancelFlag();
                                    int i7 = 0;
                                    int i8 = 0;
                                    Channel channel2 = null;
                                    for (int i9 = 3; i7 < i9; i9 = 3) {
                                        try {
                                            if (Arrays.equals(aid, FlavorConst.CRS_AID)) {
                                                str = "Session#openBasicChannel";
                                                try {
                                                    i6 = i7;
                                                    LogMgr.log(3, "007 [access-omapi] Session.openBasicChannel(byte[]) in");
                                                    LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Session", "openBasicChannel", "aid = ", aid);
                                                    channelOpenLogicalChannel = this.mSession.openBasicChannel(aid);
                                                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Session", "openBasicChannel");
                                                    LogMgr.log(3, "008 [access-omapi] Session.openBasicChannel(byte[]) out");
                                                    i8 = 200;
                                                    channel2 = channelOpenLogicalChannel;
                                                    if (channel2 == null) {
                                                        break;
                                                    }
                                                    try {
                                                        LogMgr.log(2, "701 Access contention occurred, do retry.");
                                                        Thread.sleep(100L);
                                                    } catch (InterruptedException unused2) {
                                                    }
                                                    i7 = i6 + 1;
                                                } catch (IOException e8) {
                                                    e = e8;
                                                    i3 = 7;
                                                    i5 = 200;
                                                }
                                            } else {
                                                i6 = i7;
                                                try {
                                                    LogMgr.log(3, "009 [access-omapi] Session.openLogicalChannel(byte[]) in");
                                                    LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Session", "openLogicalChannel", "aid = ", aid);
                                                    channelOpenLogicalChannel = this.mSession.openLogicalChannel(aid);
                                                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Session", "openLogicalChannel");
                                                    LogMgr.log(3, "010 [access-omapi] Session.openLogicalChannel(byte[]) out");
                                                    str = "Session#openLogicalChannel";
                                                    i8 = 225;
                                                    channel2 = channelOpenLogicalChannel;
                                                    if (channel2 == null) {
                                                    }
                                                } catch (IOException e9) {
                                                    e = e9;
                                                    str = "Session#openLogicalChannel";
                                                    i3 = 7;
                                                    i5 = 225;
                                                } catch (IllegalArgumentException e10) {
                                                    e = e10;
                                                    str = "Session#openLogicalChannel";
                                                    LogMgr.log(1, "810 Invalid AID length.");
                                                    LogMgr.printStackTrace(7, e);
                                                    closeChannel();
                                                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                                } catch (SecurityException e11) {
                                                    e = e11;
                                                    str = "Session#openLogicalChannel";
                                                    LogMgr.log(1, "811 Denied access to specified AID.");
                                                    LogMgr.printStackTrace(7, e);
                                                    close();
                                                    throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                                } catch (UnsupportedOperationException e12) {
                                                    e = e12;
                                                    str = "Session#openLogicalChannel";
                                                    i = 7;
                                                    i2 = 1;
                                                    LogMgr.log(i2, "813 P2 parameter is not supported.");
                                                    LogMgr.printStackTrace(i, e);
                                                    closeChannel();
                                                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                                } catch (NoSuchElementException e13) {
                                                    e = e13;
                                                    str = "Session#openLogicalChannel";
                                                    LogMgr.log(1, "812 AID is unavailable.");
                                                    LogMgr.printStackTrace(7, e);
                                                    closeChannel();
                                                    throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
                                                } catch (Exception e14) {
                                                    e = e14;
                                                    str = "Session#openLogicalChannel";
                                                    LogMgr.log(1, "815 Unexpected Exception occurred: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                                                    LogMgr.printStackTrace(7, e);
                                                    closeChannel();
                                                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, "UnknownError"));
                                                }
                                            }
                                        } catch (IOException e15) {
                                            e = e15;
                                        }
                                        i5 = i8;
                                        i3 = 7;
                                        i4 = 1;
                                    }
                                    Channel channel3 = channel2;
                                    if (channel3 == null) {
                                        LogMgr.log(1, "803 Channel is null.");
                                        throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(str, "Null"));
                                    }
                                    if (!channel3.isOpen()) {
                                        LogMgr.log(1, "804 Unable to open Channel for specified AID.");
                                        LogMgr.log(3, "011 [access-omapi] Channel.close() in");
                                        LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                        channel3.close();
                                        LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                        LogMgr.log(3, "012 [access-omapi] Channel.close() out");
                                        throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint(str, "Null"));
                                    }
                                    try {
                                        LogMgr.log(3, "013 [access-omapi] Channel.getSelectResponse() in");
                                        byte[] selectResponse2 = channel3.getSelectResponse();
                                        LogMgr.log(3, "014 [access-omapi] Channel.getSelectResponse() out");
                                        if (selectResponse2 == null) {
                                            LogMgr.log(1, "805 getSelectResponse is Null.");
                                            LogMgr.log(3, "015 [access-omapi] Channel.close() in");
                                            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                            channel3.close();
                                            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                            LogMgr.log(3, "016 [access-omapi] Channel.close() out");
                                            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "Null"));
                                        }
                                        try {
                                            SelectResponse selectResponse3 = new SelectResponse(selectResponse2);
                                            if (!selectResponse3.isStatusSuccess()) {
                                                LogMgr.log(1, "806 Unable to complete SELECT for specified AID.");
                                                LogMgr.log(3, "017 [access-omapi] Channel.close() in");
                                                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                                channel3.close();
                                                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                                LogMgr.log(3, "018 [access-omapi] Channel.close() out");
                                                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(selectResponse3.getSw())), null, selectResponse2);
                                            }
                                            LogMgr.log(6, "021");
                                            checkCancelFlag();
                                            this.mChannel = channel3;
                                            LogMgr.logArray(6, selectResponse2);
                                            LogMgr.log(6, "022 response length=" + selectResponse2.length);
                                            LogMgr.log(4, "999");
                                            return selectResponse2;
                                        } catch (IllegalArgumentException unused3) {
                                            LogMgr.log(1, "807 getSelectResponse format error.");
                                            LogMgr.log(3, "019 [access-omapi] Channel.close() in");
                                            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                            channel3.close();
                                            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                                            LogMgr.log(3, "020 [access-omapi] Channel.close() out");
                                            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "FormatError"));
                                        }
                                    } catch (IOException e16) {
                                        e = e16;
                                        str = "Channel#getSelectResponse";
                                    }
                                } catch (IllegalArgumentException e17) {
                                    e = e17;
                                } catch (SecurityException e18) {
                                    e = e18;
                                } catch (UnsupportedOperationException e19) {
                                    e = e19;
                                } catch (NoSuchElementException e20) {
                                    e = e20;
                                } catch (Exception e21) {
                                    e = e21;
                                }
                            }
                        } catch (IOException e22) {
                            e = e22;
                            i3 = 7;
                            i5 = 0;
                            i4 = 1;
                            str = null;
                        }
                    } catch (InterruptedException e23) {
                        LogMgr.log(1, "814 Cancelled.");
                        LogMgr.printStackTrace(7, e23);
                        closeChannel();
                        cancelOccurred();
                        return null;
                    }
                } catch (IOException e24) {
                    e = e24;
                    i3 = 7;
                    i4 = 1;
                    str = null;
                    i5 = 0;
                }
                LogMgr.log(i4, "808 Unable to open Channel for specified AID.");
                LogMgr.printStackTrace(i3, e);
                closeChannel();
                throw new GpException(i5, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint(str, e.getClass().getSimpleName()));
            } catch (GpException e25) {
                closeChannel();
                throw e25;
            } catch (IllegalStateException e26) {
                LogMgr.log(1, "809 Unable to establish connection to Secure Element.");
                LogMgr.printStackTrace(7, e26);
                close();
                Process.killProcess(Process.myPid());
                return null;
            }
        } catch (IllegalArgumentException e27) {
            e = e27;
            str = null;
        } catch (SecurityException e28) {
            e = e28;
            str = null;
        } catch (UnsupportedOperationException e29) {
            e = e29;
            i = 7;
            i2 = 1;
            str = null;
        } catch (NoSuchElementException e30) {
            e = e30;
            str = null;
        } catch (Exception e31) {
            e = e31;
            str = null;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public boolean selectNext() throws InterruptedException, GpException {
        LogMgr.log(3, "000");
        boolean zSelectNext = false;
        try {
            Channel channel = this.mChannel;
            if (channel != null && channel.isOpen()) {
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
            throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#selectNext", e.getClass().getSimpleName()));
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
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e4), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#selectNext", e4.getClass().getSimpleName()));
        } catch (Exception e5) {
            LogMgr.log(1, "804 Unexpected Exception occurred: " + e5.getClass().getSimpleName() + ": " + e5.getMessage());
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e5), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#selectNext", "UnknownError"));
        }
        LogMgr.log(3, "999");
        return zSelectNext;
    }

    public synchronized byte[] transmit(byte[] command) throws InterruptedException, GpException {
        byte[] bArrDoTransmit;
        LogMgr.log(3, "000 command: ");
        LogMgr.logArray(3, command);
        bArrDoTransmit = doTransmit(command);
        LogMgr.log(3, "999 result: ");
        LogMgr.logArray(3, bArrDoTransmit);
        return bArrDoTransmit;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    private byte[] doTransmit(byte[] command) throws InterruptedException, GpException {
        LogMgr.log(4, "000 command: ");
        LogMgr.logArray(4, command);
        try {
            if (command == null) {
                closeChannel();
                LogMgr.log(1, "800 Parameter(s) must not be null.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "UnknownError"));
            }
            if (this.mChannel == null) {
                LogMgr.log(1, "801 No opened Channel.");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Session#openLogicalChannel", "UnknownError"));
            }
            checkCancelFlag();
            LogMgr.log(3, "001 [access-omapi] Channel.transmit(byte[]) in");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "transmit", "command len = " + command.length);
            byte[] bArrTransmit = this.mChannel.transmit(command);
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "transmit", "response len = " + bArrTransmit.length);
            LogMgr.log(3, "002 [access-omapi] Channel.transmit(byte[]) out");
            checkCancelFlag();
            if (bArrTransmit == null) {
                closeChannel();
                LogMgr.log(1, "801 Reponse is null.");
                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "Null"));
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
            throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e2), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e2.getClass().getSimpleName()));
        } catch (IllegalArgumentException e3) {
            LogMgr.log(1, "804 Invalid AID length.");
            LogMgr.printStackTrace(7, e3);
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e3), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e3.getClass().getSimpleName()));
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
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e6), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e6.getClass().getSimpleName()));
        } catch (SecurityException e7) {
            LogMgr.log(1, "805 Denied access to specified AID.");
            LogMgr.printStackTrace(7, e7);
            closeChannel();
            throw new GpException(225, ObfuscatedMsgUtil.exExecutionPoint(e7), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", e7.getClass().getSimpleName()));
        } catch (Exception e8) {
            LogMgr.log(1, "808 Unexpected Exception occurred: " + e8.getClass().getSimpleName() + ": " + e8.getMessage());
            closeChannel();
            throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e8), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "UnknownError"));
        }
    }

    public synchronized byte[] getCurrentSelectResponse() throws GpException {
        byte[] selectResponse;
        LogMgr.log(3, "000");
        Channel channel = this.mChannel;
        selectResponse = null;
        if (channel != null && channel.isOpen()) {
            try {
                try {
                    try {
                        try {
                            LogMgr.log(3, "001 [access-omapi] Channel.getSelectResponse() in");
                            selectResponse = this.mChannel.getSelectResponse();
                            LogMgr.log(3, "002 [access-omapi] Channel.getSelectResponse() out");
                            if (selectResponse == null) {
                                LogMgr.log(1, "800 getSelectResponse is Null.");
                                closeChannel();
                                throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "Null"));
                            }
                            SelectResponse selectResponse2 = new SelectResponse(selectResponse);
                            if (!selectResponse2.isStatusSuccess()) {
                                LogMgr.log(1, "801 Unable to complete SELECT for specified AID.");
                                closeChannel();
                                throw new GpException(225, ObfuscatedMsgUtil.swExecutionPoint(StringUtil.bytesToHexString(selectResponse2.getSw())), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "SW=" + StringUtil.bytesToHexString(selectResponse2.getSw())));
                            }
                        } catch (IllegalArgumentException e) {
                            LogMgr.log(1, "802 : Response format error");
                            closeChannel();
                            LogMgr.printStackTrace(7, e);
                            throw new GpException(225, ObfuscatedMsgUtil.executionPoint(), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#getSelectResponse", "FormatError"));
                        }
                    } catch (IllegalStateException e2) {
                        LogMgr.log(1, "803 Unable to establish connection to Secure Element.");
                        LogMgr.printStackTrace(7, e2);
                        close();
                        Process.killProcess(Process.myPid());
                    }
                } catch (Exception e3) {
                    LogMgr.log(1, "804 Unexpected Exception occurred: " + e3.getClass().getSimpleName() + ": " + e3.getMessage());
                    closeChannel();
                    throw new GpException(200, ObfuscatedMsgUtil.exExecutionPoint(e3), ObfuscatedMsgUtil.omapiExecutionPoint("Channel#transmit", "UnknownError"));
                }
            } catch (GpException e4) {
                closeChannel();
                throw e4;
            }
        }
        LogMgr.log(3, "999");
        return selectResponse;
    }

    public synchronized void closeChannel() {
        LogMgr.log(3, "000");
        Channel channel = this.mChannel;
        if (channel != null && channel.isOpen()) {
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
