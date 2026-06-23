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

/* JADX INFO: loaded from: classes.dex */
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
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_CODE_CONTINUE);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_CODE_TIMEDRETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_INVALID_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add("4000");
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_UNKNOWN);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_NOT_EXIST_CARD);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_READ_CONDITION_ERROR);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_ACCESS_FAILED);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_INVALID_KEY_VERSION);
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add("9000");
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add("9001");
        VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT.add(FwsConst.RESULT_CONGESTED);
    }

    SEAccessTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, CardIdentifiableInfo.Cache cache, String str2, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, String str3) {
        super(i, executorService, listener);
        this.mSequenceCounter = null;
        this.mLoginTokenId = str;
        this.mIdentifiableInfo = cache;
        this.mLinkageData = str2;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mServiceType = str3;
    }

    SEAccessTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, CardIdentifiableInfo.Cache cache, String str2, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, String str3, String str4) {
        this(i, executorService, listener, str, cache, str2, fwsClient, mfiChipHolder, dataManager, str3);
        this.mOperationId = str4;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v1, types: [boolean] */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r2v3 */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [com.felicanetworks.mfc.mfi.omapi.GpController] */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.felicanetworks.mfc.mfi.omapi.GpController] */
    /* JADX WARN: Type inference failed for: r2v8 */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r4v10, types: [boolean] */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() throws Throwable {
        List<CardIdentifiableBlockData> readCiaBlockList;
        ?? r10;
        FwsSEAccessSubTask fwsSEAccessSubTask;
        GpController gpController;
        ?? IsAvailableArea;
        LogMgr.log(6, "000");
        this.mCardAccessEvent = new EventBroadcastSender.CardAccessEvent(this.mIdentifiableInfo.serviceId);
        EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardAccessEvent);
        ?? r2 = 0;
        r2 = 0;
        if (ServiceTypeInfoUtil.SysType.isCommon(this.mServiceType)) {
            ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, this.mIdentifiableInfo, this.mChipHolder);
            LogMgr.log(6, "001");
            setStoppableSubTask(readCiaBlockListTask);
            readCiaBlockListTask.start();
            ReadCiaBlockListTask.Result result2 = readCiaBlockListTask.getResult2();
            if (!result2.isSuccess) {
                onFinished(false, result2.errType, result2.errMsg);
                return;
            } else {
                IsAvailableArea = result2.readSeResult.isAvailableArea();
                readCiaBlockList = result2.readSeResult.getReadCiaBlockList();
                r10 = IsAvailableArea;
            }
        } else {
            readCiaBlockList = null;
            r10 = 0;
        }
        if (Property.isChipGP()) {
            LogMgr.log(6, "002");
            try {
                try {
                    try {
                        gpController = this.mChipHolder.getGpController();
                    } catch (Throwable th) {
                        th = th;
                        r2 = IsAvailableArea;
                        if (r2 != 0) {
                            r2.closeChannel();
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
                    r2 = gpController;
                    LogMgr.log(1, "801 : Secure Element access error.");
                    onFinished(false, e.getType(), e.getMessage());
                    if (r2 != 0) {
                        r2.closeChannel();
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
            fwsSEAccessSubTask = new FwsSEAccessSubTask(0, this.mFwsClient, this.mChipHolder, r10, readCiaBlockList);
        } else {
            fwsSEAccessSubTask = new FwsSEAccessSubTask(0, this.mFwsClient, this.mChipHolder, r10, readCiaBlockList, this.mOperationId);
        }
        setStoppableSubTask(fwsSEAccessSubTask);
        fwsSEAccessSubTask.start();
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public void setResult(CompleteCardInfo completeCardInfo) {
        this.mCardInfo = completeCardInfo;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized CompleteCardInfo getResult2() {
        return this.mCardInfo;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean z, int i, String str) {
        LogMgr.log(6, "000");
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardAccessEvent, z);
        super.onFinished(z, i, str);
        LogMgr.log(6, "999");
    }

    private class FwsSEAccessSubTask extends DoScriptTask<GetAccessScriptResponseJson> {
        private final boolean mAvailableArea;
        private final List<CardIdentifiableBlockData> mBlockDataList;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return true;
        }

        FwsSEAccessSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, boolean z, List<CardIdentifiableBlockData> list) {
            super(i, fwsClient, mfiChipHolder, SEAccessTask.this.mExecutor);
            this.mAvailableArea = z;
            this.mBlockDataList = list;
        }

        FwsSEAccessSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, boolean z, List<CardIdentifiableBlockData> list, String str) {
            super(i, fwsClient, mfiChipHolder, SEAccessTask.this.mExecutor, str);
            this.mAvailableArea = z;
            this.mBlockDataList = list;
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
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.getAccessScript(str, SEAccessTask.this.mIdentifiableInfo.serviceId, this.mSeqNum);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetAccessScriptResponseJson convertResponse(String str) throws JSONException {
            return new GetAccessScriptResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return SEAccessTask.VALID_RESULT_CODE_LIST_GET_ACCESS_SCRIPT;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetAccessScriptResponseJson getAccessScriptResponseJson) {
            try {
                SEAccessTask.this.setResult(getAccessScriptResponseJson.getAccessCard().getCardInfo(CardJson.CheckType.FWS_GET_ACCESS_SCRIPT, SEAccessTask.this.mDataManager.getSeInfo()));
                SEAccessTask.this.onFinished(true, 0, null);
            } catch (JSONException e) {
                LogMgr.log(1, "800 JSONException:" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                SEAccessTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int i, String str) {
            SEAccessTask.this.onFinished(false, i, str);
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
