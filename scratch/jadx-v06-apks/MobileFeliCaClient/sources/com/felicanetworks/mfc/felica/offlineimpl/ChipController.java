package com.felicanetworks.mfc.felica.offlineimpl;

import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.felicanetworks.felica.FelicaAdapter;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.MfcService;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.felica.util.ByteBuffer;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public abstract class ChipController {
    protected static final String ERROR_CODE_KEY = "e";
    protected static final String EXC_INVALID_COMMAND = "Invalid Command(null).";
    protected static final int MAX_DATA_LENGTH = 255;
    protected static final String MFC_PACKAGE_NAME = MfcService.getInstance().getPackageName();
    protected static final int MIN_DATA_LENGTH = 2;
    protected static final String OUT_DATA_KEY = "out";
    public static final int TYPE_NFC_CANCELED = -17;
    public static final int TYPE_NFC_CONFLICT = -12;
    public static final int TYPE_NFC_DISCOVERY_FAILED = -14;
    public static final int TYPE_NFC_ESE_BUSY = -16;
    public static final int TYPE_NFC_ESE_UNAVAILABLE = -15;
    public static final int TYPE_NFC_FELICA_RW_STOP = -20;
    public static final int TYPE_NFC_GENERIC_ERROR = -99;
    public static final int TYPE_NFC_INVALID_ACCESS = -19;
    public static final int TYPE_NFC_INVALID_PARAM = -10;
    public static final int TYPE_NFC_INVALID_STATUS = -11;
    public static final int TYPE_NFC_LOCKED = -18;
    public static final int TYPE_NFC_NONE_ERROR = 0;
    public static final int TYPE_NFC_TIMEOUT = -13;
    protected LocalDeathRecipient mDeathRecipient;
    protected volatile boolean mCancel = false;
    protected volatile boolean mProtectCancel = false;
    protected volatile boolean mConnected = false;
    protected int mOpenHandle = 0;
    protected ByteBuffer mByteBuffer = new ByteBuffer(255);
    FelicaAdapter mAdapter = FelicaAdapter.getDefaultAdapter(MfcService.getInstance());
    protected IBinder mToken = new Binder();
    private PollingCommand mPollingCommand = new PollingCommand();
    private SetParameterCommand mSetParameterCommand = new SetParameterCommand();
    private RequestServiceCommand mRequestServiceCommand = new RequestServiceCommand();
    private RequestServiceV2Command mRequestServiceV2Command = new RequestServiceV2Command();
    private GetContainerIssueInformationCommand mGetContainerIssueInformationCommand = new GetContainerIssueInformationCommand();
    private ReadWithoutEncryptionCommand mReadWithoutEncryptionCommand = new ReadWithoutEncryptionCommand();
    private WriteWithoutEncryptionCommand mWriteWithoutEncryptionCommand = new WriteWithoutEncryptionCommand();
    private RequestSystemCodeCommand mRequestSystemCodeCommand = new RequestSystemCodeCommand();
    private GetContainerIdCommand mGetContainerIdCommand = new GetContainerIdCommand();
    private SetPrivacyFlagCommand mSetPrivacyFlagCommand = new SetPrivacyFlagCommand();
    private RequestCodeListCommand mRequestCodeListCommand = new RequestCodeListCommand();
    private RequestMaskedCodeListCommand mRequestMaskedCodeListCommand = new RequestMaskedCodeListCommand();
    private RequestBlockInformationExCommand mRequestBlockInformationExCommand = new RequestBlockInformationExCommand();
    private ExecuteFelicaCommandCommand mExecuteFelicaCommandCommand = new ExecuteFelicaCommandCommand();

    public abstract void cancelOffline();

    public abstract void close(boolean z);

    public abstract void connect(int i) throws OfflineException;

    protected int convertNfcErrorToOfflineException(int i) {
        if (i != -99) {
            switch (i) {
                case TYPE_NFC_FELICA_RW_STOP /* -20 */:
                case TYPE_NFC_TIMEOUT /* -13 */:
                    return 5;
                case TYPE_NFC_LOCKED /* -18 */:
                    return 7;
                case TYPE_NFC_CANCELED /* -17 */:
                    return 8;
                case TYPE_NFC_ESE_BUSY /* -16 */:
                case TYPE_NFC_ESE_UNAVAILABLE /* -15 */:
                case TYPE_NFC_DISCOVERY_FAILED /* -14 */:
                    return 4;
                case TYPE_NFC_CONFLICT /* -12 */:
                    return 9;
            }
        }
        return 0;
    }

    public abstract void disconnect() throws OfflineException;

    protected abstract Response doExecution(Command command, int i) throws OfflineException;

    protected abstract byte[] doExecutionInner(byte[] bArr, int i) throws OfflineException;

    protected abstract Response execute(Command command, int i, int i2) throws OfflineException;

    protected abstract byte[] executeInner(byte[] bArr, int i, int i2) throws OfflineException;

    public abstract void open() throws OfflineException;

    public abstract void reset() throws OfflineException;

    public abstract void reset(boolean z, boolean z2, boolean z3) throws OfflineException;

    protected ChipController() {
    }

    public void close() {
        close(true);
    }

    public boolean isConnected() {
        return this.mConnected;
    }

    public SystemInfo polling(int i, int i2, int i3, byte b) throws OfflineException {
        try {
            PollingCommand pollingCommand = this.mPollingCommand;
            pollingCommand.setSystemCode(i);
            pollingCommand.setOption(b);
            PollingResponse pollingResponse = (PollingResponse) execute(pollingCommand, i2, i3);
            return new SystemInfo(pollingResponse.getIdm(), pollingResponse.getPmm(), pollingResponse.getSystemCode());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public void setParameter(byte[] bArr, int i, int i2, int i3) throws OfflineException {
        try {
            SetParameterCommand setParameterCommand = this.mSetParameterCommand;
            setParameterCommand.setIdm(bArr);
            setParameterCommand.setEncryptionType(1);
            if (i == 2) {
                setParameterCommand.setPacketType(3);
            } else {
                setParameterCommand.setPacketType(4);
            }
            SetParameterResponse setParameterResponse = (SetParameterResponse) execute(setParameterCommand, i2, i3);
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

    public int[] requestService(int i, byte[] bArr, int[] iArr, int i2, int i3) throws OfflineException {
        try {
            RequestServiceCommand requestServiceCommand = this.mRequestServiceCommand;
            requestServiceCommand.setNodeCodeSize(i);
            requestServiceCommand.setIdm(bArr);
            requestServiceCommand.setServiceCodeList(iArr);
            return ((RequestServiceResponse) execute(requestServiceCommand, i2, i3)).getServiceKeyVersionList();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public KeyInformation[] requestServiceV2(byte[] bArr, int[] iArr, int i, int i2) throws OfflineException {
        try {
            RequestServiceV2Command requestServiceV2Command = this.mRequestServiceV2Command;
            requestServiceV2Command.setIdm(bArr);
            requestServiceV2Command.setNodeCodeList(iArr);
            RequestServiceV2Response requestServiceV2Response = (RequestServiceV2Response) execute(requestServiceV2Command, i, i2);
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

    public byte[] getContainerIssueInfo(byte[] bArr, int i, int i2) throws OfflineException {
        try {
            GetContainerIssueInformationCommand getContainerIssueInformationCommand = this.mGetContainerIssueInformationCommand;
            getContainerIssueInformationCommand.setIdm(bArr);
            return ((GetContainerIssueInformationResponse) execute(getContainerIssueInformationCommand, i, i2)).getContainerIssueInfo();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public Data[] readWithoutEncryption(int i, byte[] bArr, BlockList blockList, int i2, int i3) throws OfflineException {
        try {
            ReadWithoutEncryptionCommand readWithoutEncryptionCommand = this.mReadWithoutEncryptionCommand;
            readWithoutEncryptionCommand.setNodeCodeSize(i);
            readWithoutEncryptionCommand.setIdm(bArr);
            readWithoutEncryptionCommand.setBlockList(blockList);
            ReadWithoutEncryptionResponse readWithoutEncryptionResponse = (ReadWithoutEncryptionResponse) execute(readWithoutEncryptionCommand, i2, i3);
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

    public void writeWithoutEncryption(int i, byte[] bArr, BlockDataList blockDataList, int i2, int i3) throws OfflineException {
        try {
            WriteWithoutEncryptionCommand writeWithoutEncryptionCommand = this.mWriteWithoutEncryptionCommand;
            writeWithoutEncryptionCommand.setNodeCodeSize(i);
            writeWithoutEncryptionCommand.setIdm(bArr);
            writeWithoutEncryptionCommand.setBlockDataList(blockDataList);
            WriteWithoutEncryptionResponse writeWithoutEncryptionResponse = (WriteWithoutEncryptionResponse) execute(writeWithoutEncryptionCommand, i2, i3);
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

    public int[] requestSystemCode(byte[] bArr, int i, int i2) throws OfflineException {
        try {
            RequestSystemCodeCommand requestSystemCodeCommand = this.mRequestSystemCodeCommand;
            requestSystemCodeCommand.setIdm(bArr);
            return ((RequestSystemCodeResponse) execute(requestSystemCodeCommand, i, i2)).getSystemCodeList();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public byte[] getContainerId(int i, int i2) throws OfflineException {
        try {
            return ((GetContainerIdResponse) execute(this.mGetContainerIdCommand, i, i2)).getCotainerId();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "700");
            throw e;
        }
    }

    public void setPrivacyFlag(byte[] bArr, PrivacySettingData[] privacySettingDataArr, int i, int i2) throws OfflineException {
        try {
            SetPrivacyFlagCommand setPrivacyFlagCommand = this.mSetPrivacyFlagCommand;
            setPrivacyFlagCommand.setIdm(bArr);
            setPrivacyFlagCommand.setPrivacySettingData(privacySettingDataArr);
            SetPrivacyFlagResponse setPrivacyFlagResponse = (SetPrivacyFlagResponse) execute(setPrivacyFlagCommand, i, i2);
            if (setPrivacyFlagResponse.getStatusFlag1() == 0) {
                return;
            }
            LogMgr.log(1, "%s", "700");
            throw new OfflineException(6, setPrivacyFlagResponse.getStatusFlag1(), setPrivacyFlagResponse.getStatusFlag2());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "701");
            throw e;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x003a, code lost:
    
        r3 = r3 - r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:18:0x003f, code lost:
    
        if (r6.getStatusFlag1() != 0) goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0041, code lost:
    
        r2.add(r6.getNodeInformation());
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x004c, code lost:
    
        if (r6.getContinueFlag() != 0) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x004f, code lost:
    
        r15 = r15 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0052, code lost:
    
        com.felicanetworks.mfc.util.LogMgr.log(1, "%s", "700");
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0065, code lost:
    
        throw new com.felicanetworks.mfc.felica.offlineimpl.OfflineException(6, r6.getStatusFlag1(), r6.getStatusFlag2());
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfc.NodeInformation requestCodeList(int r11, byte[] r12, int r13, int r14, int r15) throws com.felicanetworks.mfc.felica.offlineimpl.OfflineException {
        /*
            Method dump skipped, instruction units count: 218
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.offlineimpl.ChipController.requestCodeList(int, byte[], int, int, int):com.felicanetworks.mfc.NodeInformation");
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
    
        r14 = r14 + 1;
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
    public com.felicanetworks.mfc.NodeInformation requestMaskedCodeList(byte[] r11, int r12, int r13, int r14) throws com.felicanetworks.mfc.felica.offlineimpl.OfflineException {
        /*
            Method dump skipped, instruction units count: 215
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.offlineimpl.ChipController.requestMaskedCodeList(byte[], int, int, int):com.felicanetworks.mfc.NodeInformation");
    }

    public BlockCountInformation[] requestBlockInformationEx(int i, byte[] bArr, int[] iArr, int i2, int i3) throws OfflineException {
        try {
            RequestBlockInformationExCommand requestBlockInformationExCommand = this.mRequestBlockInformationExCommand;
            requestBlockInformationExCommand.setNodeCodeSize(i);
            requestBlockInformationExCommand.setIdm(bArr);
            requestBlockInformationExCommand.setNodeCodeList(iArr);
            RequestBlockInformationExResponse requestBlockInformationExResponse = (RequestBlockInformationExResponse) execute(requestBlockInformationExCommand, i2, i3);
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

    public void finishCancel() {
        this.mCancel = false;
    }

    public void setProtectCancel(boolean z) {
        this.mProtectCancel = z;
    }

    public byte[] executeFelicaCommand(byte[] bArr, int i, int i2) throws OfflineException {
        try {
            ExecuteFelicaCommandCommand executeFelicaCommandCommand = this.mExecuteFelicaCommandCommand;
            executeFelicaCommandCommand.setCommand(bArr);
            ExecuteFelicaCommandResponse executeFelicaCommandResponse = (ExecuteFelicaCommandResponse) execute(executeFelicaCommandCommand, i, i2);
            LogMgr.log(6, "%s", "Responce return!!");
            return executeFelicaCommandResponse.getResponse();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "OfflineException");
            throw e;
        }
    }

    public byte[] executeFelicaCommandInner(byte[] bArr, int i, int i2) throws OfflineException {
        try {
            byte[] bArrExecuteInner = executeInner(bArr, i, i2);
            LogMgr.log(6, "%s", "Responce return!!");
            return bArrExecuteInner;
        } catch (OfflineException e) {
            LogMgr.log(1, "%s", "OfflineException");
            throw e;
        }
    }

    protected synchronized void registerBinder(IBinder iBinder) throws RemoteException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        if (iBinder == null) {
            throw new IllegalArgumentException();
        }
        this.mDeathRecipient = new LocalDeathRecipient(iBinder);
    }

    protected synchronized void unregisterBinder() {
        IBinder binder;
        LogMgr.log(3, "%s", "000");
        if (this.mDeathRecipient != null && (binder = this.mDeathRecipient.getBinder()) != null) {
            binder.unlinkToDeath(this.mDeathRecipient, 0);
        }
        this.mDeathRecipient = null;
    }

    private class LocalDeathRecipient implements IBinder.DeathRecipient {
        IBinder mBinder;

        LocalDeathRecipient(IBinder iBinder) throws RemoteException {
            this.mBinder = null;
            iBinder.linkToDeath(this, 0);
            this.mBinder = iBinder;
            LogMgr.log(6, "%s", "999");
        }

        public IBinder getBinder() {
            return this.mBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Process.killProcess(Process.myPid());
        }
    }
}
