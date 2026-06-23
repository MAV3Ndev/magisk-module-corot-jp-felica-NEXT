package com.felicanetworks.mfc.felica.offlineimpl;

import android.content.Context;
import android.os.Process;
import android.se.omapi.Channel;
import android.se.omapi.Reader;
import android.se.omapi.SEService;
import android.se.omapi.Session;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.MfcService;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.felica.omapi.GetTypeFCurrentProtocolDataResponse;
import com.felicanetworks.mfc.felica.omapi.SelectResponse;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.IOException;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes.dex */
public class FelicaGpController {
    private static final int APDU_HEADER_LENGTH = 5;
    private static final int APDU_LE_LENGTH = 1;
    private static final String EXC_INVALID_COMMAND = "Invalid Command(null).";
    private static final byte FELICA_COMMAND_APDU_LE = 0;
    private static final int GET_DATA_TIMEOUT = 1000;
    private static final int GET_INSTANCE_STATUS_P2_INDEX = 3;
    private static final int GET_INSTANCE_STATUS_SYSTEM_CODE_INDEX = 7;
    private static final int MAX_DATA_LENGTH = 255;
    private static final int MIN_DATA_LENGTH = 2;
    private static final int RESET_TIMEOUT = 1000;
    private static final int SERVICE_CONNECTION_TIME_OUT = 1000;
    private SEService mSEService;
    private static final String MFC_PACKAGE_NAME = MfcService.getInstance().getPackageName();
    private static final byte[] SW_SUCCESS_CODE = {-112, 0};
    private static final byte[] SW_ESE_BUSY = {105, -16};
    private static final byte[] RESET_COMMAND = {-127, -62, 0, 0, 2, 2, -88, 0};
    private static final byte[] RESET_RESPONSE = {2, -87, -112, 0};
    private static final byte[] SET_END_TRANSACTION_COMMAND = {IllegalStateErrorException.TYPE_ILLEGAL_STATE, -16, 0, 0, 0};
    private static final byte[] GET_TYPE_F_CURRENT_PROTOCOL_DATA = {IllegalStateErrorException.TYPE_ILLEGAL_STATE, -54, 3, 0, 0};
    private static final byte[] GET_MGMTSYS_SYS0_INSTANCE_STATUS = {IllegalStateErrorException.TYPE_ILLEGAL_STATE, -54, 4, 0, 4, IllegalStateErrorException.TYPE_ILLEGAL_STATE, 0, -127, 0, 0};
    private static final byte[] GET_TARGET_INSTANCE_STATUS = {IllegalStateErrorException.TYPE_ILLEGAL_STATE, -54, 4, 0, 7, IllegalStateErrorException.TYPE_ILLEGAL_STATE, 2, -1, -1, -127, 1, 15, 0};
    private final byte[] FELICA_COMMAND_APDU_HEADER = {-127, -62, 0, 0};
    private volatile boolean mCancel = false;
    private volatile boolean mConnected = false;
    private volatile boolean mProtectProcessKill = false;
    public SEServiceWrapper mSEServiceWrapper = new SEServiceWrapper();
    public ReaderWrapper mReaderWrapper = new ReaderWrapper();
    public SessionWrapper mSessionWrapper = new SessionWrapper();
    public ChannelWrapper mChannelWrapper = new ChannelWrapper();
    private boolean mServiceConnected = false;
    private final Object mSeServiceBoundLock = new Object();
    private ByteBuffer mByteBuffer = new ByteBuffer(261);
    private SEServiceListener mListener = new SEServiceListener();
    private PollingCommand mPollingCommand = new PollingCommand();
    private SetParameterCommand mSetParameterCommand = new SetParameterCommand();
    private RequestServiceCommand mRequestServiceCommand = new RequestServiceCommand();
    private RequestServiceV2Command mRequestServiceV2Command = new RequestServiceV2Command();
    private GetContainerIssueInformationCommand mGetContainerIssueInformationCommand = new GetContainerIssueInformationCommand();
    private ReadWithoutEncryptionCommand mReadWithoutEncryptionCommand = new ReadWithoutEncryptionCommand();
    private WriteWithoutEncryptionCommand mWriteWithoutEncryptionCommand = new WriteWithoutEncryptionCommand();
    private RequestSystemCodeCommand mRequestSystemCodeCommand = new RequestSystemCodeCommand();
    private GetContainerIdCommand mGetContainerIdCommand = new GetContainerIdCommand();
    private RequestCodeListCommand mRequestCodeListCommand = new RequestCodeListCommand();
    private RequestMaskedCodeListCommand mRequestMaskedCodeListCommand = new RequestMaskedCodeListCommand();
    private RequestBlockInformationExCommand mRequestBlockInformationExCommand = new RequestBlockInformationExCommand();
    private ExecuteFelicaCommandCommand mExecuteFelicaCommandCommand = new ExecuteFelicaCommandCommand();

    public void setPrivacyFlag(Channel channel, byte[] bArr, PrivacySettingData[] privacySettingDataArr, int i, int i2) throws OfflineException {
    }

    private class SEServiceListener implements SEService.OnConnectedListener {
        private SEServiceListener() {
        }

        @Override // android.se.omapi.SEService.OnConnectedListener
        public void onConnected() {
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService.OnConnectedListener", "onConnected");
            synchronized (FelicaGpController.this.mSeServiceBoundLock) {
                FelicaGpController.this.mServiceConnected = true;
                FelicaGpController.this.mSeServiceBoundLock.notify();
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

    public void setSEService(SEService sEService) {
        this.mSEService = sEService;
    }

    public class SEServiceWrapper {
        public SEServiceWrapper() {
        }

        public SEService initSEService(Context context) throws InterruptedException, NullPointerException {
            LogMgr.log(3, "%s", "001");
            LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService", "$init");
            FelicaGpController.this.mSEService = new SEService(context, new SEServiceExecutor(), FelicaGpController.this.mListener);
            LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService", "$init");
            LogMgr.log(3, "%s", "002");
            synchronized (FelicaGpController.this.mSeServiceBoundLock) {
                if (!FelicaGpController.this.mServiceConnected) {
                    try {
                        FelicaGpController.this.mSeServiceBoundLock.wait(1000L);
                    } catch (InterruptedException e) {
                        LogMgr.log(1, "%s", "700");
                        throw e;
                    }
                }
            }
            return FelicaGpController.this.mSEService;
        }

        public String getVersion() {
            if (FelicaGpController.this.mSEService == null) {
                return null;
            }
            LogMgr.log(3, "%s", "001");
            return FelicaGpController.this.mSEService.getVersion();
        }

        public boolean isConnected() {
            if (FelicaGpController.this.mSEService != null) {
                LogMgr.log(3, "%s", "001");
                return FelicaGpController.this.mSEService.isConnected();
            }
            LogMgr.log(1, "%s", "700");
            return false;
        }

        public void shutdown() {
            FelicaGpController.this.disconnect();
            if (FelicaGpController.this.mSEService != null) {
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService", "shutdown");
                FelicaGpController.this.mSEService.shutdown();
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService", "shutdown");
                LogMgr.log(3, "%s", "002");
                FelicaGpController.this.mServiceConnected = false;
                FelicaGpController.this.mSEService = null;
            }
        }

        public Reader[] getReaders() {
            Reader[] readers = null;
            if (FelicaGpController.this.mSEService == null) {
                return null;
            }
            try {
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "SEService", "getReaders");
                readers = FelicaGpController.this.mSEService.getReaders();
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "SEService", "getReaders");
                LogMgr.log(3, "%s", "002");
                return readers;
            } catch (IllegalStateException unused) {
                FelicaGpController.this.processKillOnServiceDisconnected();
                return readers;
            }
        }
    }

    public class ReaderWrapper {
        public ReaderWrapper() {
        }

        public String getName(Reader reader) {
            if (reader == null) {
                return null;
            }
            LogMgr.log(3, "%s", "001");
            return reader.getName();
        }

        public boolean isSecureElementPresent(Reader reader) {
            boolean zIsSecureElementPresent = false;
            if (reader != null) {
                try {
                    LogMgr.log(3, "%s", "001");
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Reader", "isSecureElementPresent");
                    zIsSecureElementPresent = reader.isSecureElementPresent();
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Reader", "isSecureElementPresent");
                    LogMgr.log(3, "%s", "002");
                    return zIsSecureElementPresent;
                } catch (IllegalStateException unused) {
                    FelicaGpController.this.processKillOnServiceDisconnected();
                    return zIsSecureElementPresent;
                }
            }
            LogMgr.log(1, "%s", "700");
            return false;
        }

        public Session openSession(Reader reader) throws OfflineException {
            Session sessionOpenSession = null;
            try {
                if (reader != null) {
                    try {
                        LogMgr.log(3, "%s", "001");
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Reader", "openSession");
                        sessionOpenSession = reader.openSession();
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Reader", "openSession");
                        LogMgr.log(3, "%s", "002");
                    } catch (IllegalStateException unused) {
                        FelicaGpController.this.processKillOnServiceDisconnected();
                    }
                }
                return sessionOpenSession;
            } catch (IOException unused2) {
                LogMgr.log(1, "%s", "700");
                throw new OfflineException(4);
            }
        }

        public void closeSessions(Reader reader) {
            if (reader != null) {
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Reader", "closeSessions");
                reader.closeSessions();
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Reader", "closeSessions");
            }
        }
    }

    public class SessionWrapper {
        public SessionWrapper() {
        }

        public boolean isClosed(Session session) throws OfflineException {
            if (session != null) {
                LogMgr.log(3, "%s", "001");
                return session.isClosed();
            }
            LogMgr.log(1, "%s %s", "700", "Session is null");
            throw new OfflineException(0);
        }

        public void close(Session session) throws OfflineException {
            if (session != null) {
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Session", "close");
                session.close();
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Session", "close");
                LogMgr.log(3, "%s", "002");
                return;
            }
            LogMgr.log(1, "%s %s", "700", "Invalid Session");
            throw new OfflineException(0);
        }

        public void closeChannels(Session session) {
            if (session != null) {
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Session", "closeChannels");
                session.closeChannels();
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Session", "closeChannels");
                LogMgr.log(3, "%s", "002");
            }
        }

        public Reader getReader(Session session) {
            if (session == null) {
                return null;
            }
            LogMgr.log(3, "%s", "001");
            Reader reader = session.getReader();
            LogMgr.log(3, "%s", "002");
            return reader;
        }

        public Channel openBasicChannel(Session session, byte[] bArr) throws OfflineException {
            Channel channelOpenBasicChannel = null;
            if (session == null) {
                return null;
            }
            try {
                try {
                    LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Session", "openBasicChannel", "aid = ", bArr);
                    channelOpenBasicChannel = session.openBasicChannel(bArr);
                    LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Session", "openBasicChannel");
                    return channelOpenBasicChannel;
                } catch (IllegalStateException unused) {
                    FelicaGpController.this.processKillOnServiceDisconnected();
                    return channelOpenBasicChannel;
                }
            } catch (IOException unused2) {
                LogMgr.log(1, "%s", "705");
                throw new OfflineException(4);
            } catch (SecurityException unused3) {
                LogMgr.log(1, "%s", "703");
                throw new OfflineException(0);
            } catch (UnsupportedOperationException unused4) {
                LogMgr.log(1, "%s", "704");
                throw new OfflineException(0);
            } catch (NoSuchElementException unused5) {
                LogMgr.log(1, "%s", "702");
                throw new OfflineException(0);
            } catch (Exception unused6) {
                LogMgr.log(1, "%s", "700");
                throw new OfflineException(0);
            }
        }

        public Channel openLogicalChannel(Session session, byte[] bArr) throws OfflineException {
            Channel channelOpenLogicalChannel;
            if (session != null) {
                try {
                    try {
                        LogMgr.log(6, "%s opening AID:", "001");
                        LogMgr.logArray(6, bArr);
                        LogMgr.log(3, "%s", "002");
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Session", "openLogicalChannel", "aid = ", bArr);
                        channelOpenLogicalChannel = session.openLogicalChannel(bArr);
                    } catch (IllegalStateException unused) {
                        channelOpenLogicalChannel = null;
                    }
                    try {
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Session", "openLogicalChannel");
                        LogMgr.log(3, "%s", "003");
                    } catch (IllegalStateException unused2) {
                        FelicaGpController.this.processKillOnServiceDisconnected();
                    }
                } catch (IOException unused3) {
                    LogMgr.log(1, "%s", "705");
                    throw new OfflineException(4);
                } catch (SecurityException unused4) {
                    LogMgr.log(1, "%s", "703");
                    throw new OfflineException(0);
                } catch (UnsupportedOperationException unused5) {
                    LogMgr.log(1, "%s", "704");
                    throw new OfflineException(0);
                } catch (NoSuchElementException unused6) {
                    LogMgr.log(1, "%s", "702");
                    throw new OfflineException(0);
                } catch (Exception unused7) {
                    LogMgr.log(1, "%s", "700");
                    throw new OfflineException(0);
                }
            } else {
                channelOpenLogicalChannel = null;
            }
            if (!Arrays.equals(FelicaGpController.this.mChannelWrapper.getSelectResponse(channelOpenLogicalChannel), FelicaGpController.SW_SUCCESS_CODE)) {
                return channelOpenLogicalChannel;
            }
            FelicaGpController.this.mChannelWrapper.close(channelOpenLogicalChannel);
            return null;
        }
    }

    public class ChannelWrapper {
        public ChannelWrapper() {
        }

        public boolean isClosed(Channel channel) throws OfflineException {
            if (channel != null) {
                LogMgr.log(3, "%s", "001");
                return true ^ channel.isOpen();
            }
            LogMgr.log(1, "%s %s", "700", "Channel is null");
            throw new OfflineException(0);
        }

        public void close(Channel channel) throws OfflineException {
            if (channel != null) {
                LogMgr.log(3, "%s", "001");
                LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                channel.close();
                LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "close");
                LogMgr.log(3, "%s", "002");
                return;
            }
            LogMgr.log(1, "%s %s", "700", "Channel is null");
            throw new OfflineException(0);
        }

        public byte[] getSelectResponse(Channel channel) throws OfflineException {
            if (channel != null) {
                byte[] selectResponse = null;
                try {
                    LogMgr.log(3, "%s", "001");
                    selectResponse = channel.getSelectResponse();
                    LogMgr.log(3, "%s", "002");
                } catch (IllegalStateException unused) {
                    FelicaGpController.this.processKillOnServiceDisconnected();
                }
                if (selectResponse != null) {
                    return selectResponse;
                }
                LogMgr.log(1, "%s %s", "701", "selectResponse is null");
                throw new OfflineException(0);
            }
            LogMgr.log(1, "%s %s", "700", "Invalid Channel");
            throw new OfflineException(0);
        }

        public boolean selectNext(Channel channel) throws OfflineException {
            boolean zSelectNext;
            try {
                if (channel != null) {
                    try {
                        LogMgr.log(3, "%s", "001");
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "selectNext");
                        zSelectNext = channel.selectNext();
                    } catch (IllegalStateException unused) {
                        zSelectNext = false;
                    }
                    try {
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "selectNext", "result = " + zSelectNext);
                        LogMgr.log(3, "%s", "002");
                    } catch (IllegalStateException unused2) {
                        FelicaGpController.this.processKillOnServiceDisconnected();
                    }
                    return zSelectNext;
                }
                LogMgr.log(1, "%s %s", "700", "Invalid Channel");
                throw new OfflineException(0);
            } catch (IOException unused3) {
                LogMgr.log(1, "%s", "704");
                throw new OfflineException(4);
            } catch (UnsupportedOperationException unused4) {
                LogMgr.log(1, "%s", "703");
                throw new OfflineException(0);
            } catch (Exception unused5) {
                LogMgr.log(1, "%s", "701");
                throw new OfflineException(0);
            }
        }

        public byte[] transmitFelicaCommand(Channel channel, byte[] bArr, int i) throws OfflineException {
            return FelicaGpController.this.convertFelicaResponse(transmit(channel, FelicaGpController.this.encapsulateFelicaCommand(bArr), i));
        }

        public byte[] transmit(Channel channel, byte[] bArr, int i) throws OfflineException {
            long jCurrentTimeMillis = System.currentTimeMillis();
            byte[] bArrTransmit = null;
            try {
                if (channel != null) {
                    try {
                        LogMgr.log(3, "%s", "001");
                        LogMgr.performanceIn(LogMgr.PERFORMANCE_LMW, "Channel", "transmit", "command len = " + bArr.length);
                        bArrTransmit = channel.transmit(bArr);
                        LogMgr.performanceOut(LogMgr.PERFORMANCE_LMW, "Channel", "transmit", "response len = " + bArrTransmit.length);
                        LogMgr.log(3, "%s", "002");
                    } catch (IllegalStateException unused) {
                        FelicaGpController.this.processKillOnServiceDisconnected();
                    }
                    FelicaGpController.this.checkResponse(bArrTransmit, i - ((int) (System.currentTimeMillis() - jCurrentTimeMillis)));
                    return bArrTransmit;
                }
                LogMgr.log(1, "%s %s", "700", "Invalid Channel");
                throw new OfflineException(0);
            } catch (OfflineException e) {
                throw e;
            } catch (IOException unused2) {
                LogMgr.log(1, "%s", "705");
                throw new OfflineException(4);
            } catch (IllegalArgumentException unused3) {
                LogMgr.log(1, "%s", "703");
                throw new OfflineException(0);
            } catch (SecurityException unused4) {
                LogMgr.log(1, "%s", "704");
                throw new OfflineException(0);
            } catch (Exception unused5) {
                LogMgr.log(1, "%s", "701");
                throw new OfflineException(0);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void checkResponse(byte[] bArr, long j) throws OfflineException {
        if (bArr != null) {
            if (Arrays.equals(bArr, SW_SUCCESS_CODE)) {
                if (j > 0) {
                    try {
                        Thread.sleep(j);
                    } catch (InterruptedException unused) {
                        LogMgr.log(2, "%s", "700");
                    }
                }
                LogMgr.log(2, "%s", "701");
                throw new OfflineException(5);
            }
            if (bArr.length >= 2) {
                byte b = bArr[bArr.length - 2];
                byte b2 = bArr[bArr.length - 1];
                byte[] bArr2 = SW_ESE_BUSY;
                if (b == bArr2[0] && b2 == bArr2[1]) {
                    LogMgr.log(2, "%s", "702");
                    throw new OfflineException(4);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] encapsulateFelicaCommand(byte[] bArr) {
        this.mByteBuffer.setLength(0);
        this.mByteBuffer.append(this.FELICA_COMMAND_APDU_HEADER);
        this.mByteBuffer.append(bArr[0]);
        this.mByteBuffer.append(bArr);
        this.mByteBuffer.append((byte) 0);
        byte[] bArr2 = new byte[this.mByteBuffer.getLength()];
        this.mByteBuffer.copy(0, bArr2);
        LogMgr.logArray(6, bArr2);
        return bArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public byte[] convertFelicaResponse(byte[] bArr) {
        try {
            LogMgr.logArray(6, bArr);
            if (bArr.length >= 2 && bArr.length <= SW_SUCCESS_CODE.length + 255) {
                byte b = bArr[bArr.length - 2];
                byte b2 = bArr[bArr.length - 1];
                if (b == SW_SUCCESS_CODE[0] && b2 == SW_SUCCESS_CODE[1]) {
                    this.mByteBuffer.setLength(0);
                    this.mByteBuffer.append(bArr, 0, bArr.length - SW_SUCCESS_CODE.length);
                    byte[] bArr2 = new byte[bArr.length - SW_SUCCESS_CODE.length];
                    this.mByteBuffer.copy(0, bArr2);
                    return bArr2;
                }
                LogMgr.log(1, "%s", "701");
                return null;
            }
            LogMgr.log(1, "%s", "702");
            return null;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "700");
            return null;
        }
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    public SystemInfo polling(Channel channel, int i, int i2, int i3, byte b) throws OfflineException {
        try {
            PollingCommand pollingCommand = this.mPollingCommand;
            pollingCommand.setSystemCode(i);
            pollingCommand.setOption(b);
            PollingResponse pollingResponse = (PollingResponse) execute(channel, pollingCommand, i2, i3);
            return new SystemInfo(pollingResponse.getIdm(), pollingResponse.getPmm(), pollingResponse.getSystemCode());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public void setParameter(Channel channel, byte[] bArr, int i, int i2, int i3) throws OfflineException {
        try {
            SetParameterCommand setParameterCommand = this.mSetParameterCommand;
            setParameterCommand.setIdm(bArr);
            setParameterCommand.setEncryptionType(1);
            if (i == 2) {
                setParameterCommand.setPacketType(3);
            } else {
                setParameterCommand.setPacketType(4);
            }
            SetParameterResponse setParameterResponse = (SetParameterResponse) execute(channel, setParameterCommand, i2, i3);
            if (setParameterResponse.getStatusFlag1() == 0 && setParameterResponse.getStatusFlag2() == 0) {
                return;
            }
            LogMgr.log(1, "%s", "700");
            throw new OfflineException(6, setParameterResponse.getStatusFlag1(), setParameterResponse.getStatusFlag2());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "701");
            throw e;
        }
    }

    public int[] requestService(Channel channel, int i, byte[] bArr, int[] iArr, int i2, int i3) throws OfflineException {
        try {
            RequestServiceCommand requestServiceCommand = this.mRequestServiceCommand;
            requestServiceCommand.setNodeCodeSize(i);
            requestServiceCommand.setIdm(bArr);
            requestServiceCommand.setServiceCodeList(iArr);
            return ((RequestServiceResponse) execute(channel, requestServiceCommand, i2, i3)).getServiceKeyVersionList();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public KeyInformation[] requestServiceV2(Channel channel, byte[] bArr, int[] iArr, int i, int i2) throws OfflineException {
        try {
            RequestServiceV2Command requestServiceV2Command = this.mRequestServiceV2Command;
            requestServiceV2Command.setIdm(bArr);
            requestServiceV2Command.setNodeCodeList(iArr);
            RequestServiceV2Response requestServiceV2Response = (RequestServiceV2Response) execute(channel, requestServiceV2Command, i, i2);
            if (requestServiceV2Response.getStatusFlag1() != 0) {
                LogMgr.log(1, "%s", "700");
                throw new OfflineException(6, requestServiceV2Response.getStatusFlag1(), requestServiceV2Response.getStatusFlag2());
            }
            return requestServiceV2Response.getKeyInformationList();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public byte[] getContainerIssueInfo(Channel channel, byte[] bArr, int i, int i2) throws OfflineException {
        try {
            GetContainerIssueInformationCommand getContainerIssueInformationCommand = this.mGetContainerIssueInformationCommand;
            getContainerIssueInformationCommand.setIdm(bArr);
            return ((GetContainerIssueInformationResponse) execute(channel, getContainerIssueInformationCommand, i, i2)).getContainerIssueInfo();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public Data[] readWithoutEncryption(Channel channel, int i, byte[] bArr, BlockList blockList, int i2, int i3) throws OfflineException {
        try {
            ReadWithoutEncryptionCommand readWithoutEncryptionCommand = this.mReadWithoutEncryptionCommand;
            readWithoutEncryptionCommand.setNodeCodeSize(i);
            readWithoutEncryptionCommand.setIdm(bArr);
            readWithoutEncryptionCommand.setBlockList(blockList);
            ReadWithoutEncryptionResponse readWithoutEncryptionResponse = (ReadWithoutEncryptionResponse) execute(channel, readWithoutEncryptionCommand, i2, i3);
            if (readWithoutEncryptionResponse.getStatusFlag1() != 0) {
                LogMgr.log(1, "%s", "700");
                throw new OfflineException(6, readWithoutEncryptionResponse.getStatusFlag1(), readWithoutEncryptionResponse.getStatusFlag2());
            }
            return readWithoutEncryptionResponse.getDataList();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "701");
            throw e;
        }
    }

    public void writeWithoutEncryption(Channel channel, int i, byte[] bArr, BlockDataList blockDataList, int i2, int i3) throws OfflineException {
        try {
            WriteWithoutEncryptionCommand writeWithoutEncryptionCommand = this.mWriteWithoutEncryptionCommand;
            writeWithoutEncryptionCommand.setNodeCodeSize(i);
            writeWithoutEncryptionCommand.setIdm(bArr);
            writeWithoutEncryptionCommand.setBlockDataList(blockDataList);
            WriteWithoutEncryptionResponse writeWithoutEncryptionResponse = (WriteWithoutEncryptionResponse) execute(channel, writeWithoutEncryptionCommand, i2, i3);
            if (writeWithoutEncryptionResponse.getStatusFlag1() == 0) {
                return;
            }
            LogMgr.log(1, "%s", "700");
            throw new OfflineException(6, writeWithoutEncryptionResponse.getStatusFlag1(), writeWithoutEncryptionResponse.getStatusFlag2());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "701");
            throw e;
        }
    }

    public int[] requestSystemCode(Channel channel, byte[] bArr, int i, int i2) throws OfflineException {
        try {
            RequestSystemCodeCommand requestSystemCodeCommand = this.mRequestSystemCodeCommand;
            requestSystemCodeCommand.setIdm(bArr);
            return ((RequestSystemCodeResponse) execute(channel, requestSystemCodeCommand, i, i2)).getSystemCodeList();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public byte[] getContainerId(Channel channel, int i, int i2) throws OfflineException {
        try {
            return ((GetContainerIdResponse) execute(channel, this.mGetContainerIdCommand, i, i2)).getCotainerId();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x004a, code lost:
    
        r6 = r6 - r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004f, code lost:
    
        if (r0.getStatusFlag1() != 0) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0051, code lost:
    
        r4.add(r0.getNodeInformation());
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005c, code lost:
    
        if (r0.getContinueFlag() != 0) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x005f, code lost:
    
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0062, code lost:
    
        com.felicanetworks.mfc.util.LogMgr.log(1, "%s", "700");
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0075, code lost:
    
        throw new com.felicanetworks.mfc.felica.offlineimpl.OfflineException(6, r0.getStatusFlag1(), r0.getStatusFlag2());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfc.NodeInformation requestCodeList(android.se.omapi.Channel r17, int r18, byte[] r19, int r20, int r21, int r22) throws com.felicanetworks.mfc.felica.offlineimpl.OfflineException {
        /*
            Method dump skipped, instruction units count: 234
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.offlineimpl.FelicaGpController.requestCodeList(android.se.omapi.Channel, int, byte[], int, int, int):com.felicanetworks.mfc.NodeInformation");
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0037, code lost:
    
        r3 = r3 - r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003c, code lost:
    
        if (r6.getStatusFlag1() != 0) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x003e, code lost:
    
        r2.add(r6.getNodeInformation());
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0049, code lost:
    
        if (r6.getContinueFlag() != 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004c, code lost:
    
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x004f, code lost:
    
        com.felicanetworks.mfc.util.LogMgr.log(1, "%s", "700");
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0062, code lost:
    
        throw new com.felicanetworks.mfc.felica.offlineimpl.OfflineException(6, r6.getStatusFlag1(), r6.getStatusFlag2());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfc.NodeInformation requestMaskedCodeList(android.se.omapi.Channel r11, byte[] r12, int r13, int r14, int r15) throws com.felicanetworks.mfc.felica.offlineimpl.OfflineException {
        /*
            Method dump skipped, instruction units count: 215
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.offlineimpl.FelicaGpController.requestMaskedCodeList(android.se.omapi.Channel, byte[], int, int, int):com.felicanetworks.mfc.NodeInformation");
    }

    public BlockCountInformation[] requestBlockInformationEx(Channel channel, int i, byte[] bArr, int[] iArr, int i2, int i3) throws OfflineException {
        try {
            RequestBlockInformationExCommand requestBlockInformationExCommand = this.mRequestBlockInformationExCommand;
            requestBlockInformationExCommand.setNodeCodeSize(i);
            requestBlockInformationExCommand.setIdm(bArr);
            requestBlockInformationExCommand.setNodeCodeList(iArr);
            RequestBlockInformationExResponse requestBlockInformationExResponse = (RequestBlockInformationExResponse) execute(channel, requestBlockInformationExCommand, i2, i3);
            if (requestBlockInformationExResponse.getStatusFlag1() != 0) {
                LogMgr.log(1, "%s", "700");
                throw new OfflineException(6, requestBlockInformationExResponse.getStatusFlag1(), requestBlockInformationExResponse.getStatusFlag2());
            }
            return requestBlockInformationExResponse.getBlockCountInformation();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "701");
            throw e;
        }
    }

    public byte[] setEndTransaction(Channel channel) throws OfflineException {
        try {
            LogMgr.log(3, "%s", "001");
            if (channel != null) {
                byte[] bArrTransmit = this.mChannelWrapper.transmit(channel, SET_END_TRANSACTION_COMMAND, 1000);
                LogMgr.log(3, "%s", "002");
                if (bArrTransmit == null) {
                    LogMgr.log(1, "%s", "return value not found!");
                    throw new OfflineException(4);
                }
                LogMgr.logArray(4, bArrTransmit);
                return getTransactionCounter(bArrTransmit);
            }
            LogMgr.log(1, "%s", "channel is already closed");
            throw new OfflineException(0, "invalid channel");
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "701");
            throw new OfflineException(0);
        }
    }

    private byte[] getTransactionCounter(byte[] bArr) {
        return convertFelicaResponse(bArr);
    }

    public void finishCancel() {
        this.mCancel = false;
    }

    public int[] getTypeFCurrentProtocolData(Channel channel) throws OfflineException {
        LogMgr.log(5, "%s", "000");
        try {
            if (channel != null) {
                LogMgr.log(3, "%s", "001");
                byte[] bArrTransmit = this.mChannelWrapper.transmit(channel, GET_TYPE_F_CURRENT_PROTOCOL_DATA, 1000);
                LogMgr.log(3, "%s", "002");
                if (bArrTransmit == null) {
                    LogMgr.log(1, "%s", "return value not found!");
                    throw new OfflineException(4);
                }
                LogMgr.logArray(4, bArrTransmit);
                return getProtocolData(bArrTransmit);
            }
            LogMgr.log(1, "%s", "channel is already closed");
            throw new OfflineException(0, "invalid channel");
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "701");
            throw new OfflineException(0);
        }
    }

    private int[] getProtocolData(byte[] bArr) {
        LogMgr.log(6, "%s", "000");
        return new GetTypeFCurrentProtocolDataResponse(bArr).getActivatedSystemCodeList();
    }

    public byte[] getGetMgmtSysSys0InstanceStatus(Channel channel) throws OfflineException {
        LogMgr.log(5, "%s", "000");
        try {
            if (channel != null) {
                LogMgr.log(3, "%s", "001");
                byte[] bArrTransmit = this.mChannelWrapper.transmit(channel, GET_MGMTSYS_SYS0_INSTANCE_STATUS, 1000);
                LogMgr.log(3, "%s", "002");
                if (bArrTransmit == null) {
                    LogMgr.log(1, "%s", "return value not found!");
                    throw new OfflineException(4);
                }
                LogMgr.logArray(4, bArrTransmit);
                return bArrTransmit;
            }
            LogMgr.log(1, "%s", "channel is already closed");
            throw new OfflineException(0, "invalid channel");
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "701");
            throw new OfflineException(0);
        }
    }

    public byte[] getTargetInstanceStatus(Channel channel, int i, int i2, long j) throws OfflineException {
        LogMgr.log(5, "%s", "000");
        try {
            if (channel != null) {
                LogMgr.log(3, "%s", "001");
                byte[] bArrTransmit = this.mChannelWrapper.transmit(channel, getInstanceStatusCommand(i, i2), (int) j);
                LogMgr.log(3, "%s", "002");
                if (bArrTransmit == null) {
                    LogMgr.log(1, "%s", "return value not found!");
                    throw new OfflineException(4);
                }
                LogMgr.logArray(4, bArrTransmit);
                return bArrTransmit;
            }
            LogMgr.log(1, "%s", "channel is already closed");
            throw new OfflineException(0, "invalid channel");
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "701");
            throw new OfflineException(0);
        }
    }

    private byte[] getInstanceStatusCommand(int i, int i2) {
        LogMgr.log(6, "%s", "000");
        byte[] bArr = GET_TARGET_INSTANCE_STATUS;
        bArr[3] = (byte) (i & 255);
        bArr[7] = (byte) ((i2 >> 8) & 255);
        bArr[8] = (byte) (i2 & 255);
        LogMgr.log(6, "%s", "999");
        return bArr;
    }

    public byte[] executeFelicaCommand(Channel channel, byte[] bArr, int i, int i2) throws OfflineException {
        try {
            ExecuteFelicaCommandCommand executeFelicaCommandCommand = this.mExecuteFelicaCommandCommand;
            executeFelicaCommandCommand.setCommand(bArr);
            ExecuteFelicaCommandResponse executeFelicaCommandResponse = (ExecuteFelicaCommandResponse) execute(channel, executeFelicaCommandCommand, i, i2);
            LogMgr.log(6, "%s", "Responce return!!");
            return executeFelicaCommandResponse.getResponse();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "OfflineException");
            throw e;
        }
    }

    public byte[] executeFelicaCommandInner(Channel channel, byte[] bArr, int i, int i2) throws OfflineException {
        try {
            byte[] bArrExecuteInner = executeInner(channel, bArr, i, i2);
            LogMgr.log(6, "%s", "Responce return!!");
            return bArrExecuteInner;
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "OfflineException");
            throw e;
        }
    }

    public void connect() {
        this.mConnected = true;
    }

    public void disconnect() {
        this.mConnected = false;
    }

    public void reset(Channel channel) throws OfflineException {
        reset(channel, true, true, false);
    }

    public void reset(Channel channel, boolean z, boolean z2, boolean z3) throws OfflineException {
        if (z) {
            try {
                LogMgr.log(3, "%s", "001");
                if (channel != null) {
                    byte[] bArrTransmit = this.mChannelWrapper.transmit(channel, RESET_COMMAND, 1000);
                    LogMgr.log(3, "%s", "002");
                    if (bArrTransmit == null) {
                        LogMgr.log(1, "%s", "return value not found!");
                        throw new OfflineException(4);
                    }
                    LogMgr.logArray(4, bArrTransmit);
                    if (!compareData(bArrTransmit, RESET_RESPONSE)) {
                        throw new OfflineException(4);
                    }
                } else {
                    LogMgr.log(1, "%s", "channel is already closed");
                    throw new OfflineException(0, "invalid channel");
                }
            } catch (OfflineException e) {
                LogMgr.log(1, "%s", "700");
                throw e;
            } catch (Exception unused) {
                LogMgr.log(1, "%s", "701");
                throw new OfflineException(0);
            }
        }
        if (z3) {
            disconnect();
        }
    }

    protected Response execute(Channel channel, Command command, int i, int i2) throws OfflineException {
        LogMgr.log(6, "%s timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (command == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException(EXC_INVALID_COMMAND);
        }
        Response responseDoExecution = null;
        for (int i3 = 0; i3 <= i2; i3++) {
            try {
                responseDoExecution = doExecution(channel, command, i);
                break;
            } catch (OfflineException e) {
                if (this.mCancel) {
                    LogMgr.log(2, "%s", "703");
                    throw new OfflineException(8);
                }
                if (i3 == i2) {
                    LogMgr.log(1, "%s", "701");
                    throw e;
                }
            }
        }
        return responseDoExecution;
    }

    protected Response doExecution(Channel channel, Command command, int i) throws OfflineException {
        try {
            this.mByteBuffer.setLength(0);
            this.mByteBuffer.append((byte) 0);
            this.mByteBuffer.set(0, (byte) (command.set(this.mByteBuffer) + 1));
            byte[] bArr = new byte[this.mByteBuffer.getLength()];
            this.mByteBuffer.copy(0, bArr, 0, bArr.length);
            LogMgr.logArray(4, bArr);
            LogMgr.log(3, "%s", "001");
            byte[] bArrTransmitFelicaCommand = this.mChannelWrapper.transmitFelicaCommand(channel, bArr, i);
            LogMgr.log(3, "%s", "002");
            if (bArrTransmitFelicaCommand == null) {
                LogMgr.log(1, "%s", "return value not found!");
                throw new OfflineException(0);
            }
            LogMgr.logArray(4, bArrTransmitFelicaCommand);
            this.mByteBuffer.setLength(0);
            this.mByteBuffer.append(bArrTransmitFelicaCommand);
            return command.get(this.mByteBuffer);
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "701");
            throw new OfflineException(0);
        }
    }

    protected byte[] executeInner(Channel channel, byte[] bArr, int i, int i2) throws OfflineException {
        LogMgr.log(6, "%s timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (bArr == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException(EXC_INVALID_COMMAND);
        }
        byte[] bArrDoExecutionInner = null;
        for (int i3 = 0; i3 <= i2; i3++) {
            try {
                bArrDoExecutionInner = doExecutionInner(channel, bArr, i);
                break;
            } catch (OfflineException e) {
                if (this.mCancel) {
                    LogMgr.log(2, "%s", "703");
                    throw new OfflineException(8);
                }
                if (i3 == i2) {
                    LogMgr.log(1, "%s", "701");
                    throw e;
                }
            }
        }
        return bArrDoExecutionInner;
    }

    protected byte[] doExecutionInner(Channel channel, byte[] bArr, int i) throws OfflineException {
        try {
            LogMgr.logArray(4, bArr);
            LogMgr.log(3, "%s", "001");
            byte[] bArrTransmitFelicaCommand = this.mChannelWrapper.transmitFelicaCommand(channel, bArr, i);
            LogMgr.log(3, "%s", "002");
            if (bArrTransmitFelicaCommand == null) {
                LogMgr.log(1, "%s", "return value not found!");
                throw new OfflineException(0);
            }
            LogMgr.logArray(4, bArrTransmitFelicaCommand);
            if (bArrTransmitFelicaCommand.length < 2 || (bArrTransmitFelicaCommand[0] & 255) != bArrTransmitFelicaCommand.length) {
                throw new OfflineException(4);
            }
            return bArrTransmitFelicaCommand;
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public void cancelOffline() {
        this.mCancel = true;
    }

    private boolean compareData(byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null || bArr.length != bArr2.length) {
            LogMgr.log(6, "%s", "701");
            return false;
        }
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] != bArr2[i]) {
                LogMgr.log(6, "%s", "702");
                return false;
            }
        }
        return true;
    }

    public SystemInfo getSystemInfo(SelectResponse selectResponse) {
        return new SystemInfo(selectResponse.getIdm(), selectResponse.getPmm(), selectResponse.getSystemCode());
    }

    public SystemInfo getSystemInfo(byte[] bArr, byte[] bArr2, int i) {
        return new SystemInfo(bArr, bArr2, i);
    }

    public void setProtectProcessKill(boolean z) {
        this.mProtectProcessKill = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void processKillOnServiceDisconnected() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(3, "%s", "001");
        this.mSEServiceWrapper.shutdown();
        if (this.mProtectProcessKill) {
            return;
        }
        LogMgr.log(3, "%s", "002");
        Process.killProcess(Process.myPid());
    }
}
