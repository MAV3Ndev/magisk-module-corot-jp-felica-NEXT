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
import com.felicanetworks.mfc.mfi.fws.json.GetDisableScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetDisableScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class DisableCardTask extends AsyncParentTaskBase<CompleteCardInfo> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT;
    private EventBroadcastSender.CardChangeEvent mCardChangeEvent;
    private CompleteCardInfo mCardInfo;
    private final MfiChipHolder mChipHolder;
    private final String mCid;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final CardIdentifiableInfo.Cache mIdentifiableInfo;
    private String mLinkageData;
    private final String mLoginTokenId;
    private final boolean mNeedNotifyEvent;
    private boolean mNeedRetry;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT = arrayList;
        arrayList.add("0000");
        arrayList.add(FwsConst.RESULT_CODE_CONTINUE);
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        arrayList.add("3000");
        arrayList.add("3001");
        arrayList.add("4000");
        arrayList.add("4001");
        arrayList.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        arrayList.add(FwsConst.RESULT_NOT_EXIST_CARD);
        arrayList.add(FwsConst.RESULT_EXIST_UNKNOWN_CARD);
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
        arrayList.add(FwsConst.RESULT_ILLEGAL_LINKAGE_DATA);
        arrayList.add(FwsConst.RESULT_EXPIRED_LINKAGE_DATA);
    }

    DisableCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, String cid, CardIdentifiableInfo.Cache identifiableInfo, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, boolean needNotifyEvent, boolean needRetry) {
        this(taskId, executor, listener, loginTokenId, cid, identifiableInfo, fwsClient, chipHolder, dataManager, needNotifyEvent);
        this.mNeedRetry = needRetry;
    }

    DisableCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, String cid, CardIdentifiableInfo.Cache identifiableInfo, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, boolean needNotifyEvent) {
        super(taskId, executor, listener);
        this.mNeedRetry = false;
        this.mLoginTokenId = loginTokenId;
        this.mCid = cid;
        this.mIdentifiableInfo = identifiableInfo;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mNeedNotifyEvent = needNotifyEvent;
    }

    DisableCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String cid, CardIdentifiableInfo.Cache identifiableInfo, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String linkageData) {
        this(taskId, executor, listener, null, cid, identifiableInfo, fwsClient, chipHolder, dataManager, false);
        this.mLinkageData = linkageData;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        if (this.mNeedNotifyEvent) {
            this.mCardChangeEvent = new EventBroadcastSender.CardChangeEvent(this.mIdentifiableInfo.serviceId);
            EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardChangeEvent);
        }
        ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, this.mIdentifiableInfo, this.mChipHolder);
        setStoppableSubTask(readCiaBlockListTask);
        readCiaBlockListTask.start();
        ReadCiaBlockListTask.Result result2 = readCiaBlockListTask.getResult2();
        if (!result2.isSuccess) {
            onFinished(false, result2.errType, result2.errMsg);
            return;
        }
        FwsDisableSubTask fwsDisableSubTask = new FwsDisableSubTask(0, this.mFwsClient, this.mChipHolder, result2.readSeResult.isAvailableArea(), result2.readSeResult.getReadCiaBlockList());
        setStoppableSubTask(fwsDisableSubTask);
        fwsDisableSubTask.start();
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(6, "000");
        if (this.mNeedNotifyEvent) {
            EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardChangeEvent, isSuccess);
        }
        super.onFinished(isSuccess, errType, errMsg);
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(CompleteCardInfo cardInfo) {
        this.mCardInfo = cardInfo;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized CompleteCardInfo getResult2() {
        return this.mCardInfo;
    }

    private class FwsDisableSubTask extends DoScriptTask<GetDisableScriptResponseJson> {
        private final boolean mAvailableArea;
        private final List<CardIdentifiableBlockData> mBlockDataList;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsDisableSubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder, boolean availableArea, List<CardIdentifiableBlockData> blockDataList) {
            super(taskId, fwsClient, chipHolder, DisableCardTask.this.mExecutor);
            this.mAvailableArea = availableArea;
            this.mBlockDataList = blockDataList;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetDisableScriptRequestJson getDisableScriptRequestJson = new GetDisableScriptRequestJson();
            getDisableScriptRequestJson.setRequestId(createRequestId());
            getDisableScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mAccessToken != null) {
                getDisableScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            getDisableScriptRequestJson.setLoginTokenId(DisableCardTask.this.mLoginTokenId);
            if (this.mSeqNum == 1) {
                getDisableScriptRequestJson.setCid(DisableCardTask.this.mCid);
                getDisableScriptRequestJson.setLinkageData(DisableCardTask.this.mLinkageData);
                getDisableScriptRequestJson.setReadSeResult(this.mAvailableArea, this.mBlockDataList);
            }
            if (this.mTcapResult != null) {
                getDisableScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            return getDisableScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getDisableScript(request, DisableCardTask.this.mIdentifiableInfo.serviceId, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetDisableScriptResponseJson convertResponse(String json) throws JSONException {
            return new GetDisableScriptResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return DisableCardTask.VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetDisableScriptResponseJson response) {
            CompleteCardInfo cardInfo;
            try {
                try {
                    cardInfo = response.getDisableCard().getCardInfo(CardJson.CheckType.FWS_GET_DISABLE_SCRIPT, DisableCardTask.this.mDataManager.getSeInfo());
                } catch (JSONException e) {
                    LogMgr.log(2, "701 JSONException:" + e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    cardInfo = null;
                } catch (Exception e2) {
                    LogMgr.log(2, "702 Exception:" + e2.getClass().getSimpleName(), e2.getMessage());
                    LogMgr.printStackTrace(7, e2);
                    DisableCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
                if (cardInfo == null) {
                    DisableCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
                } else {
                    DisableCardTask.this.setResult(cardInfo);
                    DisableCardTask.this.onFinished(true, 0, null);
                }
            } catch (JSONException e3) {
                LogMgr.log(2, "700 JSONException:" + e3.getMessage());
                LogMgr.printStackTrace(7, e3);
                DisableCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            DisableCardTask.this.onFinished(false, type, msg);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return DisableCardTask.this.mNeedRetry;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_DISABLE_SCRIPT.msg;
        }
    }
}
