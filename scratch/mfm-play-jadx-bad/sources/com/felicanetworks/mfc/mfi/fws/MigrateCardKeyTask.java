package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiClientConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.WalletAppIdentifiableInfo;
import com.felicanetworks.mfc.mfi.fws.AccessFwsTask;
import com.felicanetworks.mfc.mfi.fws.AsyncTaskBase;
import com.felicanetworks.mfc.mfi.fws.json.CardIdentifiableInfoJson;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.fws.json.GetCardListRequestJson;
import com.felicanetworks.mfc.mfi.fws.json.GetCardListResponseJson;
import com.felicanetworks.mfc.mfi.fws.json.RequestJson;
import com.felicanetworks.mfc.mfi.http.HttpException;
import com.felicanetworks.mfc.mfi.http.ProtocolException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.mfi.util.ServiceTypeInfoUtil;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class MigrateCardKeyTask extends AsyncParentTaskBase<CompleteCardInfo> {
    private static final List<String> VALID_RESULT_CODE_LIST_GET_CARD_LIST;
    private final Map<Integer, Integer> mAreaCodeToKeyVersionMap;
    private final JSONArray mCallingAppInfoArray;
    private CardList mCardListForCacheUpdate;
    private final MfiChipHolder mChipHolder;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final String mLoginTokenId;
    private final String mServiceId;
    private CompleteCardInfo mTargetCardInfo;

    interface FwsDisableAndEnableSubTaskFinishListener {
        void onError(final int type, final String msg, final int taskId, final CompleteCardInfo unfinishedCardInfo);

        void onSuccess(final CompleteCardInfo migratedCardInfo);
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_CARD_LIST = arrayList;
        arrayList.add("0000");
        arrayList.add("1000");
        arrayList.add("2000");
        arrayList.add(FwsConst.RESULT_ILLEGAL_URL);
        arrayList.add("3001");
        arrayList.add("4000");
        arrayList.add("9000");
        arrayList.add("9001");
        arrayList.add("9005");
    }

    static class CardList {
        Map<String, CardIdentifiableInfo> cardIdInfoMap;
        LinkedHashMap<String, CompleteCardInfo> cardInfoMap;

        CardList() {
        }
    }

    MigrateCardKeyTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, JSONArray callingAppInfoArray, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, String serviceId) {
        super(taskId, executor, listener);
        this.mAreaCodeToKeyVersionMap = new HashMap();
        this.mLoginTokenId = loginTokenId;
        this.mCallingAppInfoArray = callingAppInfoArray;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mServiceId = serviceId;
    }

    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    protected void run() {
        LogMgr.log(6, "000");
        try {
        } catch (MfiFelicaException e) {
            e = e;
        } catch (FwsException e2) {
            e = e2;
        } catch (GpException e3) {
            e = e3;
        } catch (JSONException e4) {
            e = e4;
        } catch (Exception e5) {
            e = e5;
        }
        if (StringUtil.versionCompare(new MfiFelicaWrapper(this.mChipHolder).getMFCVersion(FelicaAdapter.getInstance()), MfiClientConst.MFC_VERSION_FAVER3) == -1) {
            LogMgr.log(1, "700 : Not supported chip.");
            onFinished(false, 223, MfiClientCallbackConst.MSG_NOT_SUPPORTED_CHIP_ERROR);
            return;
        }
        if (!checkMigratingCardExistence()) {
            LogMgr.log(7, "997");
            onFinished(true, 0, null);
            return;
        }
        if (isStopped()) {
            LogMgr.log(2, "701 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        LogMgr.log(7, "100");
        FwsGetCardListSubTask fwsGetCardListSubTask = new FwsGetCardListSubTask(0, this.mFwsClient);
        setStoppableSubTask(fwsGetCardListSubTask);
        fwsGetCardListSubTask.start();
        AccessFwsTask.Result result = fwsGetCardListSubTask.getResult2();
        if (!result.isSuccess) {
            LogMgr.log(2, "702 getCardList failed.");
            onFinished(false, result.errType, result.errMsg);
            return;
        }
        LogMgr.log(7, "101");
        CardList cardListCreateCardList = createCardList((GetCardListResponseJson) result.response);
        if (isStopped()) {
            LogMgr.log(2, "703 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        LogMgr.log(7, "102");
        cardListCreateCardList.cardInfoMap = new CardIdentifiableInfoChecker().updateCardByCidMap(this.mChipHolder, this.mDataManager, cardListCreateCardList.cardInfoMap, cardListCreateCardList.cardIdInfoMap);
        final CompleteCardInfo targetCardInfo = getTargetCardInfo(cardListCreateCardList);
        if (targetCardInfo == null) {
            LogMgr.log(7, "998");
            onFinished(true, 0, null);
            return;
        }
        if (isStopped()) {
            LogMgr.log(2, "704 Already has stopped.");
            onFinished(false, 215, null);
            return;
        }
        setCardListForCacheUpdate(cardListCreateCardList);
        final CardIdentifiableInfo cardIdentifiableInfo = cardListCreateCardList.cardIdInfoMap.get(targetCardInfo.getServiceId());
        try {
            FwsDisableAndEnableSubTask fwsDisableAndEnableSubTask = new FwsDisableAndEnableSubTask(0, new FwsDisableAndEnableSubTaskFinishListener() { // from class: com.felicanetworks.mfc.mfi.fws.MigrateCardKeyTask.1
                @Override // com.felicanetworks.mfc.mfi.fws.MigrateCardKeyTask.FwsDisableAndEnableSubTaskFinishListener
                public void onSuccess(CompleteCardInfo migratedCardInfo) {
                    LogMgr.log(6, "000");
                    MigrateCardKeyTask.this.setResult(migratedCardInfo);
                    MigrateCardKeyTask.this.updateCardListForCacheUpdate(migratedCardInfo);
                    MigrateCardKeyTask.this.onFinished(true, 0, null);
                    LogMgr.log(6, "999");
                }

                @Override // com.felicanetworks.mfc.mfi.fws.MigrateCardKeyTask.FwsDisableAndEnableSubTaskFinishListener
                public void onError(int type, String msg, int taskId, CompleteCardInfo unfinishedCardInfo) throws Throwable {
                    LogMgr.log(6, "000 taskId = " + taskId);
                    HashMap map = new HashMap();
                    map.put(targetCardInfo.getServiceId(), cardIdentifiableInfo);
                    LinkedHashMap<String, CompleteCardInfo> linkedHashMap = new LinkedHashMap<>();
                    linkedHashMap.put(unfinishedCardInfo.getCid(), unfinishedCardInfo);
                    try {
                        linkedHashMap = new CardIdentifiableInfoChecker().updateCardByCidMap(MigrateCardKeyTask.this.mChipHolder, MigrateCardKeyTask.this.mDataManager, linkedHashMap, map);
                    } catch (Exception e6) {
                        LogMgr.log(2, "700 Exception:" + e6.toString());
                    }
                    MigrateCardKeyTask.this.setResult(linkedHashMap.get(unfinishedCardInfo.getCid()));
                    MigrateCardKeyTask.this.updateCardListForCacheUpdate(linkedHashMap.get(unfinishedCardInfo.getCid()));
                    MigrateCardKeyTask.this.onFinished(false, type, msg);
                    LogMgr.log(6, "999");
                }
            }, targetCardInfo, cardIdentifiableInfo.getCacheableData());
            setStoppableSubTask(fwsDisableAndEnableSubTask);
            fwsDisableAndEnableSubTask.start();
        } catch (MfiFelicaException e6) {
            e = e6;
            LogMgr.log(2, "705 MfiFelicaException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            onFinished(false, e.getType(), e.getMessage());
            return;
        } catch (FwsException e7) {
            e = e7;
            LogMgr.log(2, "707 FwsException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            onFinished(false, e.getType(), e.getMessage());
        } catch (GpException e8) {
            e = e8;
            LogMgr.log(2, "708 GpException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            onFinished(false, e.getType(), e.getMessage());
        } catch (JSONException e9) {
            e = e9;
            LogMgr.log(2, "706 JSONException:" + e.getMessage());
            LogMgr.printStackTrace(7, e);
            onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
            return;
        } catch (Exception e10) {
            e = e10;
            LogMgr.log(2, "709 Exception:" + e.toString());
            LogMgr.printStackTrace(7, e);
            onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
        LogMgr.log(6, "999");
    }

    private CardList createCardList(GetCardListResponseJson json) throws JSONException {
        LogMgr.log(6, "000");
        CardList cardList = new CardList();
        cardList.cardInfoMap = new LinkedHashMap<>();
        for (CardJson cardJson : json.getCardList()) {
            cardList.cardInfoMap.put(cardJson.getCid(), cardJson.getCardInfo(CardJson.CheckType.FWS_GET_CARD_LIST, this.mDataManager.getSeInfo()));
        }
        cardList.cardIdInfoMap = new HashMap();
        for (CardIdentifiableInfoJson cardIdentifiableInfoJson : json.getCardIdentifiableInfoList()) {
            cardList.cardIdInfoMap.put(cardIdentifiableInfoJson.getServiceId(), cardIdentifiableInfoJson.getCardIdentifiableInfo());
        }
        LogMgr.log(6, "999");
        return cardList;
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(CompleteCardInfo targetCardInfo) {
        this.mTargetCardInfo = targetCardInfo;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized CompleteCardInfo getResult2() {
        return this.mTargetCardInfo;
    }

    private synchronized void setCardListForCacheUpdate(CardList cardList) {
        this.mCardListForCacheUpdate = cardList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void updateCardListForCacheUpdate(CompleteCardInfo updateCardInfo) {
        CardList cardList = this.mCardListForCacheUpdate;
        if (cardList != null) {
            cardList.cardInfoMap.put(updateCardInfo.getCid(), updateCardInfo);
        }
    }

    public synchronized CardList getCardListForCacheUpdate() {
        return this.mCardListForCacheUpdate;
    }

    protected boolean checkMigratingCardExistence() throws MfiFelicaException {
        LogMgr.log(6, "000");
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(this.mChipHolder);
        try {
            try {
                mfiFelicaWrapper.open();
                mfiFelicaWrapper.select(65024);
                FlavorConst.MigratingDesCardParam migratingDesCardParam = FlavorConst.MIGRATING_DES_CARD_SERVICE_ID_PREFERENCE_MAP.get(this.mServiceId);
                boolean z = false;
                if (migratingDesCardParam != null) {
                    KeyInformation[] keyVersionV2 = mfiFelicaWrapper.getKeyVersionV2(new int[]{migratingDesCardParam.areaCode});
                    Integer desVersion = keyVersionV2[0].getDesVersion();
                    LogMgr.log(7, "100 desVersion = " + desVersion);
                    Integer aesVersion = keyVersionV2[0].getAesVersion();
                    LogMgr.log(7, "101 aesVersion = " + aesVersion);
                    if ((aesVersion == null || aesVersion.intValue() == 65535) && desVersion != null && desVersion.intValue() != 65535) {
                        LogMgr.log(7, "102");
                        this.mAreaCodeToKeyVersionMap.put(Integer.valueOf(migratingDesCardParam.areaCode), desVersion);
                        z = true;
                    }
                }
                mfiFelicaWrapper.close();
                mfiFelicaWrapper.closeSilently();
                LogMgr.log(6, "999 result = " + z);
                return z;
            } catch (MfiFelicaException e) {
                LogMgr.log(2, "700 MFC API failed");
                throw e;
            }
        } catch (Throwable th) {
            mfiFelicaWrapper.closeSilently();
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x014b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected CompleteCardInfo getTargetCardInfo(CardList cardList) throws FwsException {
        CompleteCardInfo completeCardInfo;
        LogMgr.log(6, "000");
        try {
            LinkedHashMap<String, CompleteCardInfo> linkedHashMap = cardList.cardInfoMap;
            ArrayList arrayList = new ArrayList();
            if (!linkedHashMap.isEmpty()) {
                for (CompleteCardInfo completeCardInfo2 : linkedHashMap.values()) {
                    LogMgr.log(7, "100 cid = " + completeCardInfo2.getCid());
                    LogMgr.log(7, "101 serviceID = " + completeCardInfo2.getServiceId());
                    if (completeCardInfo2.getServiceId().equalsIgnoreCase(this.mServiceId)) {
                        LogMgr.log(7, "102 SEID = " + completeCardInfo2.getSeId());
                        if (completeCardInfo2.getSeId().equalsIgnoreCase(this.mDataManager.getSeInfo().getSeId())) {
                            try {
                                LogMgr.log(7, "103 ServiceType = " + completeCardInfo2.getServiceType());
                                ServiceTypeInfoUtil.disablesServiceTypeCheck(completeCardInfo2.getServiceType());
                                LogMgr.log(7, "104 status = " + completeCardInfo2.getCardStatus() + " / potision = " + completeCardInfo2.getCardPosition());
                                if (completeCardInfo2.statusIs(1, 0) || completeCardInfo2.statusIs(1, 2)) {
                                    CardIdentifiableInfo cardIdentifiableInfo = cardList.cardIdInfoMap.get(completeCardInfo2.getServiceId());
                                    LogMgr.log(7, "105 areaCode = " + cardIdentifiableInfo.areaCode);
                                    if (this.mAreaCodeToKeyVersionMap.containsKey(Integer.valueOf(cardIdentifiableInfo.areaCode))) {
                                        LogMgr.log(7, "106");
                                        arrayList.add(completeCardInfo2);
                                        if (arrayList.size() > 1) {
                                            LogMgr.log(2, "700");
                                            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                                        }
                                    }
                                }
                            } catch (Exception unused) {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                }
                if (arrayList.size() == 1) {
                    LogMgr.log(7, "107");
                    completeCardInfo = (CompleteCardInfo) arrayList.get(0);
                } else {
                    completeCardInfo = null;
                }
            }
            LogMgr.log(6, "999");
            return completeCardInfo;
        } catch (Exception unused2) {
            LogMgr.log(2, "701");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
    }

    private class FwsGetCardListSubTask extends AccessFwsTask<GetCardListResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;

        FwsGetCardListSubTask(int taskId, FwsClient fwsClient) {
            super(taskId, fwsClient);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected RequestJson createRequestJson() throws JSONException {
            GetCardListRequestJson getCardListRequestJson = new GetCardListRequestJson();
            getCardListRequestJson.setRequestId(createRequestId());
            getCardListRequestJson.setOperationId(FwsParamCreator.createOperationId());
            getCardListRequestJson.setLoginTokenId(MigrateCardKeyTask.this.mLoginTokenId);
            if (MigrateCardKeyTask.this.mCallingAppInfoArray != null) {
                getCardListRequestJson.setPlatformType(MigrateCardKeyTask.this.mDataManager.getSeInfo().getPlatformType());
                getCardListRequestJson.setWalletAppCallerInfoList(MigrateCardKeyTask.this.mCallingAppInfoArray);
            }
            WalletAppIdentifiableInfo walletAppIdentifiableInfo = WalletAppIdentifiableInfo.getInstance();
            if (!walletAppIdentifiableInfo.hasPackageName()) {
                throw new JSONException("packageName is null");
            }
            getCardListRequestJson.setWalletAppIdentifiableInfo(walletAppIdentifiableInfo.getPackageName());
            getCardListRequestJson.setSeInfo(MigrateCardKeyTask.this.mDataManager.getSeInfo(), Property.getSeType());
            getCardListRequestJson.setAdditionalInfoHashParameter(FwsParamCreator.getLanguageCode());
            getCardListRequestJson.setSpSync(false);
            return getCardListRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String request) throws ProtocolException, HttpException {
            return this.mFwsClient.getCardList(request, 1);
        }

        /* JADX DEBUG: Method merged with bridge method: convertResponse(Ljava/lang/String;)Lcom/felicanetworks/mfc/mfi/fws/json/ResponseJson; */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetCardListResponseJson convertResponse(String json) throws JSONException {
            return new GetCardListResponseJson(json);
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected List<String> getValidResultCodeList() {
            return MigrateCardKeyTask.VALID_RESULT_CODE_LIST_GET_CARD_LIST;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_CARD_LIST.msg;
        }
    }

    protected class FwsDisableAndEnableSubTask extends StoppableTaskBase<Void> implements AsyncTaskBase.Listener {
        public static final int INNER_TASK_ID_DISABLE_CARD = 1;
        public static final int INNER_TASK_ID_ENABLE_CARD = 2;
        private CompleteCardInfo mCurrentTargetCardInfo;
        private int mCurrentTaskId;
        private final CardIdentifiableInfo.Cache mIdentifiableInfo;
        private final FwsDisableAndEnableSubTaskFinishListener mListener;

        /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
        /* JADX INFO: renamed from: getResult, reason: avoid collision after fix types in other method */
        public Void getResult2() {
            return null;
        }

        FwsDisableAndEnableSubTask(int taskId, FwsDisableAndEnableSubTaskFinishListener listener, CompleteCardInfo targetCardInfo, CardIdentifiableInfo.Cache identifiableInfo) {
            super(taskId);
            this.mListener = listener;
            this.mCurrentTargetCardInfo = targetCardInfo;
            this.mIdentifiableInfo = identifiableInfo;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase, com.felicanetworks.mfc.mfi.fws.TaskBase
        public void start() {
            FwsDisableAndEnableSubTask fwsDisableAndEnableSubTask;
            LogMgr.log(6, "000");
            try {
                this.mCurrentTaskId = 1;
                fwsDisableAndEnableSubTask = this;
                try {
                    DisableCardTask disableCardTask = new DisableCardTask(this.mCurrentTaskId, MigrateCardKeyTask.this.mExecutor, fwsDisableAndEnableSubTask, MigrateCardKeyTask.this.mLoginTokenId, this.mCurrentTargetCardInfo.getCid(), this.mIdentifiableInfo, MigrateCardKeyTask.this.mFwsClient, MigrateCardKeyTask.this.mChipHolder, MigrateCardKeyTask.this.mDataManager, true, true);
                    MigrateCardKeyTask.this.setStoppableSubTask(disableCardTask);
                    disableCardTask.start();
                } catch (Exception e) {
                    e = e;
                    LogMgr.log(1, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                    fwsDisableAndEnableSubTask.mListener.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e), fwsDisableAndEnableSubTask.mCurrentTaskId, fwsDisableAndEnableSubTask.mCurrentTargetCardInfo);
                }
            } catch (Exception e2) {
                e = e2;
                fwsDisableAndEnableSubTask = this;
            }
            LogMgr.log(6, "999");
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
            LogMgr.log(6, "000");
            try {
                if (isSuccess) {
                    int taskId = task.getTaskId();
                    if (taskId == 1) {
                        LogMgr.log(7, "100");
                        CompleteCardInfo result2 = ((DisableCardTask) task).getResult2();
                        if (result2 != null) {
                            this.mCurrentTaskId = 2;
                            this.mCurrentTargetCardInfo = result2;
                            ExecutorService executorService = MigrateCardKeyTask.this.mExecutor;
                            String str = MigrateCardKeyTask.this.mLoginTokenId;
                            CompleteCardInfo completeCardInfo = this.mCurrentTargetCardInfo;
                            EnableCardTask enableCardTask = new EnableCardTask(2, executorService, this, str, completeCardInfo, completeCardInfo.getCacheableData(), this.mIdentifiableInfo, MigrateCardKeyTask.this.mFwsClient, MigrateCardKeyTask.this.mChipHolder, MigrateCardKeyTask.this.mDataManager, true);
                            MigrateCardKeyTask.this.setStoppableSubTask(enableCardTask);
                            enableCardTask.start();
                        } else {
                            LogMgr.log(2, "700");
                            this.mListener.onError(200, ObfuscatedMsgUtil.executionPoint(), this.mCurrentTaskId, this.mCurrentTargetCardInfo);
                        }
                    } else if (taskId == 2) {
                        LogMgr.log(7, "101");
                        List<CompleteCardInfo> result22 = ((EnableCardTask) task).getResult2();
                        if (result22.get(0) != null) {
                            CompleteCardInfo completeCardInfo2 = result22.get(0);
                            this.mCurrentTargetCardInfo = completeCardInfo2;
                            this.mListener.onSuccess(completeCardInfo2);
                        } else {
                            LogMgr.log(2, "701");
                            this.mListener.onError(200, ObfuscatedMsgUtil.executionPoint(), this.mCurrentTaskId, this.mCurrentTargetCardInfo);
                        }
                    } else {
                        LogMgr.log(2, "702");
                        this.mListener.onError(200, ObfuscatedMsgUtil.executionPoint(), this.mCurrentTaskId, this.mCurrentTargetCardInfo);
                    }
                } else {
                    LogMgr.log(2, "703");
                    this.mListener.onError(errType, errMsg, this.mCurrentTaskId, this.mCurrentTargetCardInfo);
                }
            } catch (Exception e) {
                LogMgr.log(1, "704 " + e.getClass().getSimpleName() + ":" + e.getMessage());
                this.mListener.onError(200, ObfuscatedMsgUtil.exExecutionPoint(e), this.mCurrentTaskId, this.mCurrentTargetCardInfo);
            }
            LogMgr.log(6, "999");
        }
    }
}
