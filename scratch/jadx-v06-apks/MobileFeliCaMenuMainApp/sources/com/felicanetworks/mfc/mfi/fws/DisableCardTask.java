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
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
class DisableCardTask extends AsyncParentTaskBase<CompleteCardInfo> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT;
    private EventBroadcastSender.CardChangeEvent mCardChangeEvent;
    private CompleteCardInfo mCardInfo;
    private final MfiChipHolder mChipHolder;
    private final String mCid;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final CardIdentifiableInfo.Cache mIdentifiableInfo;
    private final String mLoginTokenId;
    private final boolean mNeedNotifyEvent;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_CODE_CONTINUE);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_ILLEGAL_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_INVALID_ACCESS_TOKEN);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add("4000");
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_UNKNOWN);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_NOT_ACTIVE_CARD);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_NOT_EXIST_CARD);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_EXIST_UNKNOWN_CARD);
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add("9000");
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add("9001");
        VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT.add(FwsConst.RESULT_CONGESTED);
    }

    DisableCardTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, String str2, CardIdentifiableInfo.Cache cache, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, boolean z) {
        super(i, executorService, listener);
        this.mLoginTokenId = str;
        this.mCid = str2;
        this.mIdentifiableInfo = cache;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mNeedNotifyEvent = z;
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
    protected void onFinished(boolean z, int i, String str) {
        LogMgr.log(6, "000");
        if (this.mNeedNotifyEvent) {
            EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardChangeEvent, z);
        }
        super.onFinished(z, i, str);
        LogMgr.log(6, "999");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(CompleteCardInfo completeCardInfo) {
        this.mCardInfo = completeCardInfo;
    }

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

        FwsDisableSubTask(int i, FwsClient fwsClient, MfiChipHolder mfiChipHolder, boolean z, List<CardIdentifiableBlockData> list) {
            super(i, fwsClient, mfiChipHolder, DisableCardTask.this.mExecutor);
            this.mAvailableArea = z;
            this.mBlockDataList = list;
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
                getDisableScriptRequestJson.setReadSeResult(this.mAvailableArea, this.mBlockDataList);
            }
            if (this.mTcapResult != null) {
                getDisableScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            return getDisableScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.getDisableScript(str, DisableCardTask.this.mIdentifiableInfo.serviceId, this.mSeqNum);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetDisableScriptResponseJson convertResponse(String str) throws JSONException {
            return new GetDisableScriptResponseJson(str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return DisableCardTask.VALID_RESULT_CODE_LIST_GET_DISABLE_SCRIPT;
        }

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetDisableScriptResponseJson getDisableScriptResponseJson) {
            CompleteCardInfo cardInfo;
            try {
                try {
                    cardInfo = getDisableScriptResponseJson.getDisableCard().getCardInfo(CardJson.CheckType.FWS_GET_DISABLE_SCRIPT, DisableCardTask.this.mDataManager.getSeInfo());
                } catch (JSONException e) {
                    LogMgr.log(2, "%s JSONException:%s", "701", e.getMessage());
                    LogMgr.printStackTrace(7, e);
                    cardInfo = null;
                }
                if (cardInfo != null) {
                    DisableCardTask.this.setResult(cardInfo);
                    DisableCardTask.this.onFinished(true, 0, null);
                } else {
                    DisableCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
                }
            } catch (JSONException e2) {
                LogMgr.log(2, "%s JSONException:%s", "700", e2.getMessage());
                LogMgr.printStackTrace(7, e2);
                DisableCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int i, String str) {
            DisableCardTask.this.onFinished(false, i, str);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_DISABLE_SCRIPT.msg;
        }
    }
}
