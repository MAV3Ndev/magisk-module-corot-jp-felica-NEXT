package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.EventBroadcastSender;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.ReadCiaBlockListTask;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.GetAccessScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetAccessScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.SsdAppletInfo;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class SEAccessTask extends AsyncParentTaskBase<CompleteCardInfo> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT;
    private EventBroadcastSender.CardAccessEvent mCardAccessEvent;
    private CompleteCardInfo mCardInfo;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final CardIdentifiableInfo.Cache mIdentifiableInfo;
    private final String mLinkageData;
    private final String mLoginTokenId;
    private String mOperationId;
    private String mSequenceCounter;
    private final String mServiceType;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT = arrayList;
        arrayList.add("0000");
        arrayList.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList.add("1000");
        arrayList.add(FwsConst.RESULT_CODE_TIMEDRETRYREQUEST);
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
        arrayList.add(FwsConst.RESULT_READ_CONDITION_ERROR);
        arrayList.add(FwsConst.RESULT_ACCESS_FAILED);
        arrayList.add(FwsConst.RESULT_INVALID_KEY_VERSION);
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    SEAccessTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, CardIdentifiableInfo.Cache identifiableInfo, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceType) {
        super(taskId, executor, listener);
        this.mSequenceCounter = null;
        this.mLoginTokenId = loginTokenId;
        this.mIdentifiableInfo = identifiableInfo;
        this.mLinkageData = linkageData;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mServiceType = serviceType;
    }

    SEAccessTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, CardIdentifiableInfo.Cache identifiableInfo, String linkageData, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceType, String operationId) {
        this(taskId, executor, listener, loginTokenId, identifiableInfo, linkageData, fwsClient, chipHolder, dataManager, serviceType);
        this.mOperationId = operationId;
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:19:0x008a */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:21:0x008c */
    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:44:0x0065 */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v2, types: [com.felicanetworks.mfc.mfi.omapi.GpController] */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v4, types: [com.felicanetworks.mfc.mfi.omapi.GpController] */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        boolean z;
        List<CardIdentifiableBlockData> readCiaBlockList;
        FwsSEAccessSubTask fwsSEAccessSubTask;
        GpController gpController;
        MfiChipHolder mfiChipHolder;
        LogMgr.log(6, "000");
        this.mCardAccessEvent = new EventBroadcastSender.CardAccessEvent(this.mIdentifiableInfo.serviceId);
        EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardAccessEvent);
        ?? r3 = 0;
        r3 = 0;
        if (ServiceTypeInfoUtil.SysType.isCommon(this.mServiceType)) {
            CardIdentifiableInfo.Cache cache = this.mIdentifiableInfo;
            mfiChipHolder = this.mChipHolder;
            ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, cache, mfiChipHolder);
            LogMgr.log(6, "001");
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
            LogMgr.log(6, "002");
            try {
                try {
                    try {
                        gpController = this.mChipHolder.getGpController();
                    } catch (Throwable th) {
                        th = th;
                        r3 = mfiChipHolder;
                        if (r3 != 0) {
                            r3.closeChannel();
                        }
                        throw th;
                    }
                } catch (GpException e) {
                    e = e;
                } catch (InterruptedException unused) {
                    gpController = null;
                }
                try {
                    this.mSequenceCounter = StringUtil.bytesToHexString(((SsdAppletInfo) new AppletManager(gpController).getAppletInfo(5)).getSequenceCount());
                    if (gpController != null) {
                        gpController.closeChannel();
                    }
                } catch (GpException e2) {
                    e = e2;
                    r3 = gpController;
                    LogMgr.log(1, "801 : Secure Element access error.");
                    onFinished(false, e.getType(), e.getMessage());
                    if (r3 != 0) {
                        r3.closeChannel();
                        return;
                    }
                    return;
                } catch (InterruptedException unused2) {
                    LogMgr.log(1, "800 : cancel occured.");
                    onFinished(false, 215, null);
                    if (gpController != null) {
                        gpController.closeChannel();
                        return;
                    }
                    return;
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        if (this.mOperationId == null) {
            fwsSEAccessSubTask = new FwsSEAccessSubTask(0, this.mFwsClient, this.mChipHolder, z, readCiaBlockList);
        } else {
            fwsSEAccessSubTask = new FwsSEAccessSubTask(0, this.mFwsClient, this.mChipHolder, z, readCiaBlockList, this.mOperationId);
        }
        setStoppableSubTask(fwsSEAccessSubTask);
        fwsSEAccessSubTask.start();
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(CompleteCardInfo cardInfo) {
        this.mCardInfo = cardInfo;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized CompleteCardInfo getResult2() {
        return this.mCardInfo;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(6, "000");
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardAccessEvent, isSuccess);
        super.onFinished(isSuccess, errType, errMsg);
        LogMgr.log(6, "999");
    }

    private class FwsSEAccessSubTask extends DoScriptTask<GetAccessScriptResponseJson> {
        private final boolean mAvailableArea;
        private final List<CardIdentifiableBlockData> mBlockDataList;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return true;
        }

        FwsSEAccessSubTask(int taskId, FwsClient fwsClient, MfiChipHolder felicaHolder, boolean availableArea, List<CardIdentifiableBlockData> blockDataList) {
            super(taskId, fwsClient, felicaHolder, SEAccessTask.this.mExecutor);
            this.mAvailableArea = availableArea;
            this.mBlockDataList = blockDataList;
        }

        FwsSEAccessSubTask(int taskId, FwsClient fwsClient, MfiChipHolder felicaHolder, boolean availableArea, List<CardIdentifiableBlockData> blockDataList, String operationId) {
            super(taskId, fwsClient, felicaHolder, SEAccessTask.this.mExecutor, operationId);
            this.mAvailableArea = availableArea;
            this.mBlockDataList = blockDataList;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetAccessScriptRequestJson getAccessScriptRequestJson = new GetAccessScriptRequestJson();
            getAccessScriptRequestJson.setRequestId(createRequestId());
            getAccessScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mAccessToken != null) {
                getAccessScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            getAccessScriptRequestJson.setLoginTokenId(SEAccessTask.this.mLoginTokenId);
            if (this.mSeqNum == 1) {
                getAccessScriptRequestJson.setLinkageData(SEAccessTask.this.mLinkageData);
                if (Property.isChipGP() && SEAccessTask.this.mSequenceCounter != null) {
                    getAccessScriptRequestJson.setSequenceCounter(SEAccessTask.this.mSequenceCounter);
                }
                if (ServiceTypeInfoUtil.SysType.isCommon(SEAccessTask.this.mServiceType)) {
                    if (ServiceTypeInfoUtil.MultiCardType.isServersMultiple(SEAccessTask.this.mServiceType)) {
                        getAccessScriptRequestJson.setReadSeResult(this.mAvailableArea, this.mBlockDataList);
                    } else {
                        getAccessScriptRequestJson.setReadSeResult(this.mAvailableArea, null);
                    }
                }
            }
            if (this.mTcapResult != null) {
                getAccessScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            if (this.mApduResult != null) {
                getAccessScriptRequestJson.setApduResult(this.mApduResult);
                this.mApduResult = null;
            }
            return getAccessScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getAccessScript(request, SEAccessTask.this.mIdentifiableInfo.serviceId, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetAccessScriptResponseJson convertResponse(String json) throws JSONException {
            return new GetAccessScriptResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return SEAccessTask.VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetAccessScriptResponseJson response) {
            try {
                SEAccessTask.this.setResult(response.getAccessCard().getCardInfo(CardJson.CheckType.FWS_GET_ACCESS_SCRIPT, SEAccessTask.this.mDataManager.getSeInfo()));
                SEAccessTask.this.onFinished(true, 0, null);
            } catch (JSONException e) {
                LogMgr.log(1, "800 JSONException:" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                SEAccessTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            SEAccessTask.this.onFinished(false, type, msg);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return ServiceTypeInfoUtil.IfType.isFullIf(SEAccessTask.this.mServiceType);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_ACCESS_SCRIPT.msg;
        }
    }
}
