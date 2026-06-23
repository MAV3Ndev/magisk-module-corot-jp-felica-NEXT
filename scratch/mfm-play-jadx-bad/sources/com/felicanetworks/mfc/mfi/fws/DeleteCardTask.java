package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.Block;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.CyclicData;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.EventBroadcastSender;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.FwsConst;
import com.felicanetworks.mfc.mfi.fws.ReadCiaBlockListTask;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.GetDeleteScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetDeleteScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.JwsObject;
import com.felicanetworks.mfc.mfi.fws.json.LinkageDataJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.CrsManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.InstanceStatus;
import com.felicanetworks.mfc.mfi.omapi.SelectResponse;
import com.felicanetworks.mfc.mfi.omapi.SsdAppletInfo;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class DeleteCardTask extends AsyncParentTaskBase<String> {
    private static final String SERVICE_TYPE_FULL_PRIVATE_LOCAL = "MFPL0000";
    private static final List<String> VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT;
    private byte[] mAppletInstanceAid;
    private EventBroadcastSender.CardDeleteEvent mCardDeleteEvent;
    private CompleteCardInfo mCardInfo;
    private final MfiChipHolder mChipHolder;
    private final String mCid;
    private final DataManager mDataManager;
    private byte[] mDeactivateAid;
    private boolean mExecuteCheckOutsideTransitGate;
    private final FwsClient mFwsClient;
    private final CardIdentifiableInfo.Cache mIdentifiableInfo;
    private final String mLinkageData;
    private final String mLoginTokenId;
    private final boolean mNeedNotifyEvent;
    private boolean mNeedRetry;
    private String mOperationId;
    private int mPosition;
    private String mSequenceCounter;
    private final String mServiceType;
    private int mStatus;

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(String cid) {
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT = arrayList;
        arrayList.add("0000");
        arrayList.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        arrayList.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        arrayList.add("3000");
        arrayList.add("3001");
        arrayList.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        arrayList.add("4000");
        arrayList.add("4001");
        arrayList.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        arrayList.add(FwsConst.RESULT_NOT_EXIST_CARD);
        arrayList.add(FwsConst.RESULT_INVALID_KEY_VERSION);
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    private enum RecoveryTargetCardCondition {
        DELETED_PENDING(3, 2),
        ACTIVE_PENDING(1, 2),
        IN_PROCESS_NOT_APPLICABLE(0, 3),
        LOST_NOT_APPLICABLE(2, 3);

        private final int position;
        private final int status;

        RecoveryTargetCardCondition(int status, int position) {
            this.status = status;
            this.position = position;
        }

        public static boolean isContain(int status, int position) {
            for (RecoveryTargetCardCondition recoveryTargetCardCondition : values()) {
                if (recoveryTargetCardCondition.status == status && recoveryTargetCardCondition.position == position) {
                    return true;
                }
            }
            return false;
        }
    }

    DeleteCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, String cid, CardIdentifiableInfo.Cache identifiableInfo, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceType, boolean needNotifyEvent) {
        super(taskId, executor, listener);
        this.mExecuteCheckOutsideTransitGate = false;
        this.mStatus = -1;
        this.mPosition = -1;
        this.mNeedRetry = true;
        this.mLoginTokenId = loginTokenId;
        this.mCid = cid;
        this.mIdentifiableInfo = identifiableInfo;
        this.mLinkageData = linkageData;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mServiceType = serviceType;
        this.mNeedNotifyEvent = needNotifyEvent;
    }

    DeleteCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, String cid, CardIdentifiableInfo.Cache identifiableInfo, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceType, String operationId) {
        this(taskId, executor, listener, loginTokenId, cid, identifiableInfo, linkageData, fwsClient, chipHolder, dataManager, serviceType, true);
        this.mOperationId = operationId;
    }

    DeleteCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, byte[] aid) {
        this(taskId, executor, listener, loginTokenId, (String) null, (CardIdentifiableInfo.Cache) null, linkageData, fwsClient, chipHolder, dataManager, SERVICE_TYPE_FULL_PRIVATE_LOCAL, false);
        this.mDeactivateAid = aid;
    }

    DeleteCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, String cid, CardIdentifiableInfo.Cache identifiableInfo, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceType, boolean executeCheckOutsideTransitGate, byte[] aid, int status, int position) {
        this(taskId, executor, listener, loginTokenId, cid, identifiableInfo, linkageData, fwsClient, chipHolder, dataManager, serviceType, true);
        this.mExecuteCheckOutsideTransitGate = executeCheckOutsideTransitGate;
        this.mAppletInstanceAid = aid;
        this.mStatus = status;
        this.mPosition = position;
    }

    DeleteCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String cid, CardIdentifiableInfo.Cache identifiableInfo, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceType) {
        this(taskId, executor, listener, (String) null, cid, identifiableInfo, linkageData, fwsClient, chipHolder, dataManager, serviceType, false);
        this.mNeedRetry = false;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        boolean z;
        List<CardIdentifiableBlockData> readCiaBlockList;
        DeleteCardTask deleteCardTask;
        FwsDeleteSubTask fwsDeleteSubTask;
        int i;
        LogMgr.log(6, "000");
        if (this.mNeedNotifyEvent) {
            this.mCardDeleteEvent = new EventBroadcastSender.CardDeleteEvent(this.mIdentifiableInfo.serviceId);
            EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardDeleteEvent);
        }
        try {
            String actionType = new LinkageDataJson(new JwsObject(this.mLinkageData).getPayload()).getActionType();
            try {
                if (this.mExecuteCheckOutsideTransitGate && FwsConst.ActionType.DELETE_CARDS.equals(actionType) && !RecoveryTargetCardCondition.isContain(this.mStatus, this.mPosition) && (i = this.mIdentifiableInfo.systemCode) == 3) {
                    MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
                    if (checkExistTargetSystem(i, mfiFelicaWrapper)) {
                        if (isStopped()) {
                            LogMgr.log(2, "700 Already has stopped.");
                            onFinished(false, 215, null);
                            return;
                        } else if (!checkOutsideTransitGate(i, mfiFelicaWrapper)) {
                            return;
                        }
                    }
                }
                if (isStopped()) {
                    LogMgr.log(2, "701 Already has stopped.");
                    onFinished(false, 215, null);
                    return;
                }
                ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, this.mIdentifiableInfo, this.mChipHolder);
                try {
                    if (Property.isChipGP() && ServiceTypeInfoUtil.SupportMfi.isMfiSupported(this.mServiceType) && ServiceTypeInfoUtil.IfType.isFullIf(this.mServiceType) && ServiceTypeInfoUtil.SysType.isPrivate(this.mServiceType)) {
                        LogMgr.log(6, "001");
                        byte[] bArr = this.mDeactivateAid;
                        if (bArr != null) {
                            deactivate(bArr);
                        } else {
                            deactivate(this.mCid);
                        }
                    }
                    if (ServiceTypeInfoUtil.SysType.isCommon(this.mServiceType)) {
                        LogMgr.log(6, "002");
                        setStoppableSubTask(readCiaBlockListTask);
                        readCiaBlockListTask.start();
                        ReadCiaBlockListTask.Result result2 = readCiaBlockListTask.getResult2();
                        if (!result2.isSuccess) {
                            onFinished(false, result2.errType, result2.errMsg);
                            return;
                        } else {
                            boolean zIsAvailableArea = result2.readSeResult.isAvailableArea();
                            readCiaBlockList = result2.readSeResult.getReadCiaBlockList();
                            z = zIsAvailableArea;
                        }
                    } else {
                        z = false;
                        readCiaBlockList = null;
                    }
                    if (Property.isChipGP()) {
                        this.mSequenceCounter = getSequenceCounter();
                    }
                    if (this.mOperationId == null) {
                        deleteCardTask = this;
                        fwsDeleteSubTask = deleteCardTask.new FwsDeleteSubTask(0, this.mFwsClient, this.mChipHolder, z, readCiaBlockList);
                    } else {
                        deleteCardTask = this;
                        fwsDeleteSubTask = deleteCardTask.new FwsDeleteSubTask(0, deleteCardTask.mFwsClient, deleteCardTask.mChipHolder, z, readCiaBlockList, deleteCardTask.mOperationId);
                    }
                    setStoppableSubTask(fwsDeleteSubTask);
                    fwsDeleteSubTask.start();
                    LogMgr.log(6, "999");
                } catch (GpException e) {
                    LogMgr.log(1, "805 : GpException");
                    onFinished(false, e.getType(), e.getMessage());
                } catch (InterruptedException unused) {
                    LogMgr.log(1, "804 : InterruptedException");
                    onFinished(false, 215, null);
                } catch (Exception unused2) {
                    LogMgr.log(1, "806 : Exception");
                    onFinished(false, 200, "Unknown error.");
                }
            } catch (MfiFelicaException e2) {
                LogMgr.log(1, "801 : MfiFelicaException");
                onFinished(false, e2.getType(), e2.getMessage());
            } catch (GpException e3) {
                LogMgr.log(1, "803 : GpException");
                onFinished(false, e3.getType(), e3.getMessage());
            } catch (InterruptedException unused3) {
                LogMgr.log(1, "802 : InterruptedException");
                onFinished(false, 215, null);
            }
        } catch (Exception e4) {
            LogMgr.log(1, "800 : Exception");
            LogMgr.printStackTrace(7, e4);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e4));
        }
    }

    private void deactivate(String str) throws Throwable {
        GpController gpController;
        LogMgr.log(6, "000");
        GpController gpController2 = null;
        InstanceStatus value = null;
        try {
            try {
                gpController = this.mChipHolder.getGpController();
            } catch (Throwable th) {
                th = th;
            }
        } catch (GpException e) {
            e = e;
        } catch (InterruptedException e2) {
            e = e2;
        }
        try {
            CrsManager crsManager = new CrsManager(gpController);
            Iterator<Map.Entry<Integer, Map<String, InstanceStatus>>> it = crsManager.createCrsStatusTable().entrySet().iterator();
            loop0: while (true) {
                if (!it.hasNext()) {
                    break;
                }
                for (Map.Entry<String, InstanceStatus> entry : it.next().getValue().entrySet()) {
                    if (entry.getKey().equals(str)) {
                        value = entry.getValue();
                        break loop0;
                    }
                }
            }
            if (value != null) {
                if (value.isActivated()) {
                    crsManager.deactivateApplet(value.getAid());
                }
            } else {
                LogMgr.log(2, "700 : Not exist card.");
            }
            if (gpController != null) {
                gpController.closeChannel();
            }
            LogMgr.log(6, "999");
        } catch (GpException e3) {
            e = e3;
            LogMgr.log(1, "801 : Secure Element access error.");
            throw e;
        } catch (InterruptedException e4) {
            e = e4;
            LogMgr.log(1, "800 : cancel occured.");
            throw e;
        } catch (Throwable th2) {
            th = th2;
            gpController2 = gpController;
            if (gpController2 != null) {
                gpController2.closeChannel();
            }
            throw th;
        }
    }

    private void deactivate(byte[] aid) throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        GpController gpController = null;
        try {
            try {
                try {
                    gpController = this.mChipHolder.getGpController();
                    CrsManager crsManager = new CrsManager(gpController);
                    if (new SelectResponse(gpController.select(aid)).isActivated()) {
                        crsManager.deactivateApplet(aid);
                    }
                    LogMgr.log(6, "999");
                } catch (GpException e) {
                    LogMgr.log(1, "801 : Secure Element access error.");
                    throw e;
                }
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : cancel occured.");
                throw e2;
            }
        } finally {
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    private String getSequenceCounter() throws InterruptedException, GpException {
        LogMgr.log(6, "000");
        GpController gpController = null;
        try {
            try {
                gpController = this.mChipHolder.getGpController();
                String strBytesToHexString = StringUtil.bytesToHexString(((SsdAppletInfo) new AppletManager(gpController).getAppletInfo(5)).getSequenceCount());
                LogMgr.log(6, "999");
                return strBytesToHexString;
            } catch (GpException e) {
                LogMgr.log(1, "801 : Secure Element access error.");
                throw e;
            } catch (InterruptedException e2) {
                LogMgr.log(1, "800 : cancel occured.");
                throw e2;
            }
        } finally {
            if (gpController != null) {
                gpController.closeChannel();
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [744=4] */
    private boolean checkOutsideTransitGate(int systemCode, MfiFelicaWrapper felica) {
        Data[] dataArr;
        int type;
        String str;
        Data[] withCheckServiceAndBlock;
        boolean z;
        LogMgr.log(6, "000");
        try {
            try {
                try {
                    if (Property.isChipGP()) {
                        felica.open();
                        felica.selectWritableSystemWithCid(systemCode, this.mCid);
                    } else {
                        felica.select(systemCode);
                    }
                    BlockList blockList = new BlockList();
                    blockList.add(new Block(FlavorConst.INDIVIDUAL_SP_SERVICE_CODE_TICKET_PUNCHING_STATUS, 0));
                    withCheckServiceAndBlock = felica.readWithCheckServiceAndBlock(blockList);
                } catch (Throwable th) {
                    felica.closeSilently();
                    throw th;
                }
            } catch (MfiFelicaException e) {
                e = e;
                dataArr = null;
            }
            try {
                felica.close();
                felica.closeSilently();
                str = null;
                z = false;
                type = 200;
            } catch (MfiFelicaException e2) {
                dataArr = withCheckServiceAndBlock;
                e = e2;
                LogMgr.log(1, "800 : MfiFelicaException");
                type = e.getType();
                String message = e.getMessage();
                felica.closeSilently();
                str = message;
                withCheckServiceAndBlock = dataArr;
                z = true;
            }
            if (z) {
                onFinished(false, type, str);
                return false;
            }
            if (withCheckServiceAndBlock == null || withCheckServiceAndBlock.length == 0) {
                LogMgr.log(2, "701 : dataList is null or empty.");
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return false;
            }
            LogMgr.log(6, "001 : dataList.length : " + withCheckServiceAndBlock.length);
            Data data = withCheckServiceAndBlock[0];
            if (!(data instanceof CyclicData)) {
                LogMgr.log(2, "702 : Data type is invalid. dataType= " + withCheckServiceAndBlock[0].getType());
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return false;
            }
            byte[] bytes = ((CyclicData) data).getBytes();
            LogMgr.log(6, "002 : dataList[0].getBytes() : ");
            LogMgr.logArray(6, bytes);
            if (bytes == null) {
                LogMgr.log(2, "703 : Fail to get bytes.");
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return false;
            }
            if (bytes.length < 2) {
                LogMgr.log(2, "704 : bytes length is " + bytes.length);
                onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                return false;
            }
            boolean z2 = ((bytes[0] >> 7) & 1) == 1;
            LogMgr.log(6, "003 : inside transit gate flag :  " + z2);
            boolean z3 = ((bytes[1] >> 6) & 1) == 1;
            LogMgr.log(6, "004 : inside super express transit gate flag : " + z3);
            boolean z4 = z2 || z3;
            if (z4) {
                LogMgr.log(2, "705 : Card is inside of the ticket gate.");
                onFinished(false, 245, null);
            }
            LogMgr.log(6, "999");
            return !z4;
        } catch (FelicaException e3) {
            LogMgr.log(2, "700 : FelicaException(id : " + e3.getID() + " type : " + e3.getType() + ")");
            felica.closeSilently();
            return true;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00a4 A[Catch: MfiFelicaException -> 0x00ad, TRY_LEAVE, TryCatch #2 {MfiFelicaException -> 0x00ad, blocks: (B:14:0x0060, B:16:0x0070, B:18:0x0084, B:24:0x00a4, B:21:0x009e), top: B:30:0x0060 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean checkExistTargetSystem(int targetSystemCode, MfiFelicaWrapper felica) throws InterruptedException, MfiFelicaException, GpException {
        boolean zCheckExistTargetSystem;
        LogMgr.log(6, "000");
        if (Property.isChipGP()) {
            try {
                AppletManager appletManager = new AppletManager(this.mChipHolder.getGpController());
                LogMgr.log(6, "001 : mCid:" + this.mCid);
                LogMgr.log(6, "002 : mSystemExistCheckAid:");
                LogMgr.logArray(6, this.mAppletInstanceAid);
                zCheckExistTargetSystem = appletManager.checkExistTargetSystem(targetSystemCode, this.mCid, this.mAppletInstanceAid);
            } catch (GpException e) {
                LogMgr.log(1, "802 : GpException");
                throw e;
            } catch (IllegalArgumentException unused) {
                LogMgr.log(1, "800 : IllegalArgumentException");
                throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), null);
            } catch (InterruptedException e2) {
                LogMgr.log(1, "801 : InterruptedException");
                throw e2;
            }
        } else {
            try {
                felica.open();
                felica.select(65039);
                int[] systemCodeList = felica.getSystemCodeList();
                if (systemCodeList != null) {
                    LogMgr.log(6, "003 : systemCodeList.length:" + systemCodeList.length);
                    int length = systemCodeList.length;
                    for (int i = 0; i < length; i++) {
                        int i2 = systemCodeList[i];
                        LogMgr.log(6, "004 : systemCode:" + i2);
                        if (i2 == targetSystemCode) {
                            zCheckExistTargetSystem = true;
                            break;
                        }
                    }
                    zCheckExistTargetSystem = false;
                    if (!zCheckExistTargetSystem) {
                        felica.close();
                    }
                } else {
                    zCheckExistTargetSystem = false;
                    if (!zCheckExistTargetSystem) {
                    }
                }
            } catch (MfiFelicaException e3) {
                LogMgr.log(1, "803 : MfiFelicaException");
                felica.closeSilently();
                throw e3;
            }
        }
        LogMgr.log(6, "999");
        return zCheckExistTargetSystem;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized String getResult2() {
        return this.mCid;
    }

    protected void setCardInfo(CompleteCardInfo cardInfo) {
        this.mCardInfo = cardInfo;
    }

    public synchronized CompleteCardInfo getCardInfo() {
        return this.mCardInfo;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(6, "000");
        if (this.mNeedNotifyEvent) {
            EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardDeleteEvent, isSuccess);
        }
        super.onFinished(isSuccess, errType, errMsg);
        LogMgr.log(6, "999");
    }

    private class FwsDeleteSubTask extends DoScriptTask<GetDeleteScriptResponseJson> {
        private final boolean mAvailableArea;
        private final List<CardIdentifiableBlockData> mBlockDataList;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsDeleteSubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder, boolean availableArea, List<CardIdentifiableBlockData> blockDataList) {
            super(taskId, fwsClient, chipHolder, DeleteCardTask.this.mExecutor);
            this.mAvailableArea = availableArea;
            this.mBlockDataList = blockDataList;
        }

        FwsDeleteSubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder, boolean availableArea, List<CardIdentifiableBlockData> blockDataList, String operationId) {
            super(taskId, fwsClient, chipHolder, DeleteCardTask.this.mExecutor, operationId);
            this.mAvailableArea = availableArea;
            this.mBlockDataList = blockDataList;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetDeleteScriptRequestJson getDeleteScriptRequestJson = new GetDeleteScriptRequestJson();
            getDeleteScriptRequestJson.setRequestId(createRequestId());
            getDeleteScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mAccessToken != null) {
                getDeleteScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            getDeleteScriptRequestJson.setLoginTokenId(DeleteCardTask.this.mLoginTokenId);
            if (this.mSeqNum == 1) {
                getDeleteScriptRequestJson.setCid(DeleteCardTask.this.mCid);
                getDeleteScriptRequestJson.setLinkageData(DeleteCardTask.this.mLinkageData);
                if (DeleteCardTask.this.mSequenceCounter != null && Property.isChipGP()) {
                    getDeleteScriptRequestJson.setSequenceCounter(DeleteCardTask.this.mSequenceCounter);
                }
                if (ServiceTypeInfoUtil.SysType.isCommon(DeleteCardTask.this.mServiceType)) {
                    if (ServiceTypeInfoUtil.MultiCardType.isServersMultiple(DeleteCardTask.this.mServiceType)) {
                        getDeleteScriptRequestJson.setReadSeResult(this.mAvailableArea, this.mBlockDataList);
                    } else {
                        getDeleteScriptRequestJson.setReadSeResult(this.mAvailableArea, null);
                    }
                }
            }
            if (this.mTcapResult != null) {
                getDeleteScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            if (this.mApduResult != null) {
                getDeleteScriptRequestJson.setApduResult(this.mApduResult);
                this.mApduResult = null;
            }
            return getDeleteScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            if (DeleteCardTask.this.mIdentifiableInfo != null) {
                return this.mFwsClient.getDeleteScript(request, DeleteCardTask.this.mIdentifiableInfo.serviceId, this.mSeqNum);
            }
            return this.mFwsClient.getDeleteScript(request, null, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetDeleteScriptResponseJson convertResponse(String json) throws JSONException {
            return new GetDeleteScriptResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return DeleteCardTask.VALID_RESULT_CODE_LIST_GET_DELETE_SCRIPT;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetDeleteScriptResponseJson response) {
            CompleteCardInfo cardInfo;
            try {
                CardJson deletedCard = response.getDeletedCard();
                if (deletedCard != null) {
                    cardInfo = deletedCard.getCardInfo(CardJson.CheckType.FWS_GET_DELETE_SCRIPT, DeleteCardTask.this.mDataManager.getSeInfo());
                    cardInfo.setPosition(4);
                } else {
                    cardInfo = null;
                }
                DeleteCardTask.this.setCardInfo(cardInfo);
                DeleteCardTask.this.onFinished(true, 0, null);
            } catch (JSONException e) {
                LogMgr.log(1, "800 JSONException:" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                DeleteCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            } catch (Exception e2) {
                LogMgr.log(1, "801 Exception:" + e2.getClass().getSimpleName(), e2.getMessage());
                LogMgr.printStackTrace(7, e2);
                DeleteCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            DeleteCardTask.this.onFinished(false, type, msg);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return ServiceTypeInfoUtil.IfType.isFullIf(DeleteCardTask.this.mServiceType) && DeleteCardTask.this.mNeedRetry;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_DELETE_SCRIPT.msg;
        }
    }
}
