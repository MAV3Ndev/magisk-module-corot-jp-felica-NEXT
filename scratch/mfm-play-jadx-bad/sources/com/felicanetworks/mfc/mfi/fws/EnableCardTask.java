package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableBlockData;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.EventBroadcastSender;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfc.mfi.MfiControlInfoCache;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.ReadCiaBlockListTask;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.GetEnableScriptRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetEnableScriptResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.omapi.CrsManager;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.InstanceStatus;
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
class EnableCardTask extends AsyncParentTaskBase<List<CompleteCardInfo>> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_ENABLE_SCRIPT;
    private final String mAid;
    private final String mCardCategory;
    private EventBroadcastSender.CardChangeEvent mCardChangeEvent;
    private final CardInfo mCardInfo;
    private List<CompleteCardInfo> mCardInfoList;
    private final MfiChipHolder mChipHolder;
    private final String mCid;
    private final CompleteCardInfo.Cache mCompleteCardInfo;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final CardIdentifiableInfo.Cache mIdentifiableInfo;
    private final String mLoginTokenId;
    private boolean mNeedRetry;
    private final String mServiceType;

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_ENABLE_SCRIPT = arrayList;
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
        arrayList.add(FwsConst.RESULT_INSUFFICIENT_CHIP_SPACE);
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    EnableCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, CardInfo cardInfo, CompleteCardInfo.Cache cache, CardIdentifiableInfo.Cache identifiableInfo, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, boolean needRetry) throws MfiClientException {
        this(taskId, executor, listener, loginTokenId, cardInfo, cache, identifiableInfo, fwsClient, chipHolder, dataManager);
        this.mNeedRetry = needRetry;
    }

    EnableCardTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, CardInfo cardInfo, CompleteCardInfo.Cache cache, CardIdentifiableInfo.Cache identifiableInfo, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager) throws MfiClientException {
        super(taskId, executor, listener);
        this.mNeedRetry = false;
        this.mLoginTokenId = loginTokenId;
        this.mCid = cardInfo.getCid();
        this.mCardInfo = cardInfo;
        this.mIdentifiableInfo = identifiableInfo;
        this.mCompleteCardInfo = cache;
        String str = cache.serviceType;
        this.mServiceType = str;
        this.mCardCategory = cache.cardCategory;
        this.mAid = cache.appletInstanceAid;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        if (!ServiceTypeInfoUtil.MultiCardType.isLocalMultiple(str) || cache.state == 1) {
            return;
        }
        LogMgr.log(1, "800 : Card status is not Active.");
        throw new MfiClientException(102, 158, null);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        this.mCardChangeEvent = new EventBroadcastSender.CardChangeEvent(this.mIdentifiableInfo.serviceId);
        EventBroadcastSender.sendStartEventBroadcast(FelicaAdapter.getInstance(), this.mCardChangeEvent);
        if (ServiceTypeInfoUtil.SysType.isCommon(this.mServiceType) && ServiceTypeInfoUtil.MultiCardType.isServersMultiple(this.mServiceType)) {
            LogMgr.log(6, "001");
            ReadCiaBlockListTask readCiaBlockListTask = new ReadCiaBlockListTask(0, this.mIdentifiableInfo, this.mChipHolder);
            setStoppableSubTask(readCiaBlockListTask);
            readCiaBlockListTask.start();
            ReadCiaBlockListTask.Result result2 = readCiaBlockListTask.getResult2();
            if (!result2.isSuccess) {
                LogMgr.log(1, "800 : FwsException");
                onFinished(false, result2.errType, result2.errMsg);
                return;
            } else {
                LogMgr.log(6, "002");
                FwsEnableSubTask fwsEnableSubTask = new FwsEnableSubTask(0, this.mFwsClient, this.mChipHolder, result2.readSeResult.isAvailableArea(), result2.readSeResult.getReadCiaBlockList());
                setStoppableSubTask(fwsEnableSubTask);
                fwsEnableSubTask.start();
            }
        } else if (ServiceTypeInfoUtil.SysType.isPrivate(this.mServiceType) && ServiceTypeInfoUtil.MultiCardType.isLocalMultiple(this.mServiceType)) {
            LogMgr.log(6, "003");
            try {
                if (!new CardIdentifiableInfoChecker().checkIntegrityForGpPrivate(this.mChipHolder, this.mCompleteCardInfo)) {
                    LogMgr.log(1, "801 : Not exist card.");
                    onFinished(false, 208, null);
                    return;
                }
                try {
                    Map<Integer, String> cardCategoryMap = new GetMfiControlInfoResponseJson(MfiControlInfoCache.getInstance().getInfoCache()).getCardCategoryMap();
                    try {
                        CrsManager crsManager = new CrsManager(this.mChipHolder.getGpController());
                        Map<Integer, Map<String, InstanceStatus>> mapCreateCrsStatusTable = crsManager.createCrsStatusTable();
                        ArrayList arrayList = new ArrayList();
                        for (Map.Entry<Integer, String> entry : cardCategoryMap.entrySet()) {
                            if (entry.getValue().equals(this.mCardCategory)) {
                                arrayList.add(entry.getKey());
                            }
                        }
                        ArrayList arrayList2 = new ArrayList();
                        Iterator it = arrayList.iterator();
                        boolean z = false;
                        while (it.hasNext()) {
                            Map<String, InstanceStatus> map = mapCreateCrsStatusTable.get((Integer) it.next());
                            if (map != null) {
                                for (Map.Entry<String, InstanceStatus> entry2 : map.entrySet()) {
                                    if (entry2.getValue().isActivated()) {
                                        byte[] aid = entry2.getValue().getAid();
                                        if (StringUtil.bytesToHexString(aid).equals(this.mAid)) {
                                            z = true;
                                        } else {
                                            arrayList2.add(aid);
                                        }
                                    }
                                }
                            }
                        }
                        if (arrayList2.size() != 0) {
                            LogMgr.log(6, "004 Exist activated Applet.");
                            crsManager.deactivateApplet(arrayList2);
                        }
                        if (!z) {
                            crsManager.activateApplet(StringUtil.hexToByteArray(this.mAid));
                        }
                        LogMgr.log(6, "005");
                        ArrayList arrayList3 = new ArrayList();
                        this.mCompleteCardInfo.state = 1;
                        this.mCompleteCardInfo.position = 0;
                        CompleteCardInfo completeCardInfo = new CompleteCardInfo(this.mCardInfo, this.mCompleteCardInfo);
                        completeCardInfo.setFixStatus(1);
                        completeCardInfo.setPosition(0);
                        completeCardInfo.setAdditionalInfoHash(null);
                        arrayList3.add(completeCardInfo);
                        arrayList3.add(null);
                        setResult((List<CompleteCardInfo>) arrayList3);
                        onFinished(true, 0, null);
                    } catch (GpException e) {
                        LogMgr.log(1, "806 GpException.");
                        onFinished(false, e.getType(), e.getMessage());
                        return;
                    } catch (InterruptedException unused) {
                        LogMgr.log(1, "805 cancel occured.");
                        onFinished(false, 215, null);
                        return;
                    }
                } catch (JSONException unused2) {
                    LogMgr.log(1, "804 failed to parse MfiControlInfoCache data.");
                    onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
                    return;
                }
            } catch (FwsException e2) {
                LogMgr.log(1, "802 : FwsException");
                onFinished(false, e2.getType(), e2.getMessage());
                return;
            } catch (GpException e3) {
                LogMgr.log(1, "803 : GpException");
                onFinished(false, e3.getType(), e3.getMessage());
                return;
            }
        }
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void onFinished(boolean isSuccess, int errType, String errMsg) {
        LogMgr.log(6, "000");
        EventBroadcastSender.sendFinishEventBroadcast(FelicaAdapter.getInstance(), this.mCardChangeEvent, isSuccess);
        super.onFinished(isSuccess, errType, errMsg);
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(List<CompleteCardInfo> cardInfoList) {
        this.mCardInfoList = cardInfoList;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized List<CompleteCardInfo> getResult2() {
        return this.mCardInfoList;
    }

    private class FwsEnableSubTask extends DoScriptTask<GetEnableScriptResponseJson> {
        private final boolean mAvailableArea;
        private final List<CardIdentifiableBlockData> mBlockDataList;

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isTimedRetrievable() {
            return false;
        }

        FwsEnableSubTask(int taskId, FwsClient fwsClient, MfiChipHolder chipHolder, boolean availableArea, List<CardIdentifiableBlockData> blockDataList) {
            super(taskId, fwsClient, chipHolder, EnableCardTask.this.mExecutor);
            this.mAvailableArea = availableArea;
            this.mBlockDataList = blockDataList;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetEnableScriptRequestJson getEnableScriptRequestJson = new GetEnableScriptRequestJson();
            getEnableScriptRequestJson.setRequestId(createRequestId());
            getEnableScriptRequestJson.setOperationId(this.mOperationId);
            if (this.mAccessToken != null) {
                getEnableScriptRequestJson.setAccessToken(this.mAccessToken);
                this.mAccessToken = null;
            }
            getEnableScriptRequestJson.setLoginTokenId(EnableCardTask.this.mLoginTokenId);
            if (this.mSeqNum == 1 && ServiceTypeInfoUtil.SysType.isCommon(EnableCardTask.this.mServiceType)) {
                getEnableScriptRequestJson.setCid(EnableCardTask.this.mCid);
                if (ServiceTypeInfoUtil.MultiCardType.isServersMultiple(EnableCardTask.this.mServiceType)) {
                    getEnableScriptRequestJson.setReadSeResult(this.mAvailableArea, this.mBlockDataList);
                } else {
                    getEnableScriptRequestJson.setReadSeResult(this.mAvailableArea, null);
                }
            }
            if (this.mTcapResult != null) {
                getEnableScriptRequestJson.setTcapResult(this.mTcapResult);
                this.mTcapResult = null;
            }
            return getEnableScriptRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getEnableScript(request, EnableCardTask.this.mIdentifiableInfo.serviceId, this.mSeqNum);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetEnableScriptResponseJson convertResponse(String json) throws JSONException {
            return new GetEnableScriptResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return EnableCardTask.VALID_RESULT_CODE_LIST_GET_ENABLE_SCRIPT;
        }

        /* JADX DEBUG: Method merged with bridge method: onSuccessScript(Lcom/felicanetworks/mfc/mfi/fws/json/GetScriptResponseJson;)V */
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        public void onSuccessScript(GetEnableScriptResponseJson response) {
            ArrayList arrayList = new ArrayList();
            try {
                CardJson enableCard = response.getEnableCard();
                CardJson disableCard = response.hasDisableCard() ? response.getDisableCard() : null;
                arrayList.add(enableCard.getCardInfo(CardJson.CheckType.FWS_GET_ENABLE_SCRIPT, EnableCardTask.this.mDataManager.getSeInfo()));
                if (disableCard != null) {
                    arrayList.add(disableCard.getCardInfo(CardJson.CheckType.FWS_GET_ENABLE_SCRIPT, EnableCardTask.this.mDataManager.getSeInfo()));
                } else {
                    arrayList.add(null);
                }
                EnableCardTask.this.setResult((List<CompleteCardInfo>) arrayList);
                EnableCardTask.this.onFinished(true, 0, null);
            } catch (JSONException e) {
                LogMgr.log(1, "800 JSONException:" + e.getMessage());
                LogMgr.printStackTrace(7, e);
                EnableCardTask.this.onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            } catch (Exception e2) {
                LogMgr.log(1, "801 Exception:" + e2.getClass().getSimpleName(), e2.getMessage());
                LogMgr.printStackTrace(7, e2);
                EnableCardTask.this.onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
            }
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        void onErrorScript(int type, String msg) {
            EnableCardTask.this.onFinished(false, type, msg);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.DoScriptTask
        protected boolean isEnableRetryHttpAccess() {
            return EnableCardTask.this.mNeedRetry;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_ENABLE_SCRIPT.msg;
        }
    }
}
