package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardAdditionalInfo;
import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.MfiClientCallbackConst;
import com.felicanetworks.mfc.mfi.MfiFelicaException;
import com.felicanetworks.mfc.mfi.MfiFelicaWrapper;
import com.felicanetworks.mfc.mfi.MigratedServiceCache;
import com.felicanetworks.mfc.mfi.UnsupportedMfiService1CardCache;
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
import com.felicanetworks.mfc.mfi.util.AssetsUtil;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class GetCardListTask extends AsyncParentTaskBase<CardList> {
    public static final String INDIVIDUAL_SP_CARD_ASSETS_NAME = "individual_cards.json";
    private static final List<String> VALID_RESULT_CODE_LIST_GET_CARD_LIST;
    private final String mApplicationId;
    private final JSONArray mCallingAppInfos;
    private CardList mCardList;
    private CardListType mCardListType;
    private final MfiChipHolder mChipHolder;
    private final String[] mCidList;
    private final DataManager mDataManager;
    private final FwsClient mFwsClient;
    private final a mJwsCreator;
    private final String mLoginTokenId;
    private final String mServiceId;

    public enum CardListType {
        CARD_INFO,
        CARD_ADDITIONAL_INFO,
        CARD_INFO_WITH_SP_STATUS
    }

    static class Options {
        private String applicationId;
        private CardListType cardListType;
        private String[] cidList;
        private String serviceId;

        Options() {
        }

        static Options createCardListOptions(String applicationId) {
            Options options = new Options();
            options.cardListType = CardListType.CARD_INFO;
            options.applicationId = applicationId;
            return options;
        }

        static Options createCardAdditionalInfoListOptions(String[] cidList) {
            Options options = new Options();
            options.cardListType = CardListType.CARD_ADDITIONAL_INFO;
            options.cidList = cidList;
            return options;
        }

        static Options createCardInfoListWithSpStatusOptions(String applicationId, String serviceId) {
            Options options = new Options();
            options.cardListType = CardListType.CARD_INFO_WITH_SP_STATUS;
            options.applicationId = applicationId;
            options.serviceId = serviceId;
            return options;
        }
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
        List<CardAdditionalInfo> cardAdditionalInfoList;
        Map<String, CardIdentifiableInfo> cardIdInfoMap;
        LinkedHashMap<String, CompleteCardInfo> cardInfoMap;
        List<CardInfoWithSpStatus> cardInfoWithSpStatusList;

        CardList() {
        }
    }

    GetCardListTask(int taskId, ExecutorService executor, AsyncTaskBase.Listener listener, String loginTokenId, JSONArray callingAppInfos, FwsClient fwsClient, MfiChipHolder chipHolder, DataManager dataManager, Options options, a jwsCreator) {
        super(taskId, executor, listener);
        this.mCardListType = CardListType.CARD_INFO;
        this.mLoginTokenId = loginTokenId;
        this.mCallingAppInfos = callingAppInfos;
        this.mFwsClient = fwsClient;
        this.mChipHolder = chipHolder;
        this.mDataManager = dataManager;
        this.mCardListType = options.cardListType;
        this.mServiceId = options.serviceId;
        this.mApplicationId = options.applicationId;
        this.mCidList = options.cidList;
        this.mJwsCreator = jwsCreator;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x004b  */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected void run() throws Throwable {
        FwsGetCardListSubTask fwsGetCardListSubTask = new FwsGetCardListSubTask(0, this.mFwsClient);
        setStoppableSubTask(fwsGetCardListSubTask);
        fwsGetCardListSubTask.start();
        AccessFwsTask.Result result = fwsGetCardListSubTask.getResult2();
        if (!result.isSuccess) {
            onFinished(false, result.errType, result.errMsg);
            return;
        }
        try {
            CardList cardListCreateCardList = createCardList((GetCardListResponseJson) result.response, CardJson.CheckType.FWS_GET_CARD_LIST);
            if (isStopped()) {
                LogMgr.log(2, "700 Already has stopped.");
                onFinished(false, 215, null);
                return;
            }
            if (CardListType.CARD_INFO == this.mCardListType || CardListType.CARD_INFO_WITH_SP_STATUS == this.mCardListType) {
                try {
                    cardListCreateCardList.cardInfoMap = new CardIdentifiableInfoChecker().updateCardByCidMap(this.mChipHolder, this.mDataManager, cardListCreateCardList.cardInfoMap, cardListCreateCardList.cardIdInfoMap);
                    CardList cardListLoadIndividualCardListFromAssets = loadIndividualCardListFromAssets();
                    if (CardListType.CARD_INFO != this.mCardListType) {
                        if (CardListType.CARD_INFO_WITH_SP_STATUS == this.mCardListType) {
                            if (this.mServiceId.equals(cardListLoadIndividualCardListFromAssets.cardInfoMap.values().iterator().next().getServiceId())) {
                            }
                        }
                    }
                    if (IndividualSpChecker.checkIndividualApplicationId(this.mApplicationId)) {
                        String seId = this.mDataManager.getSeInfo().getSeId();
                        List<String>[] listArrSeparateByMigrationState = IndividualSpChecker.separateByMigrationState(seId, cardListLoadIndividualCardListFromAssets.cardInfoMap, cardListCreateCardList.cardInfoMap.values());
                        List<String> list = listArrSeparateByMigrationState[0];
                        List<String> list2 = listArrSeparateByMigrationState[1];
                        if (!list2.isEmpty()) {
                            Iterator<String> it = list2.iterator();
                            while (it.hasNext()) {
                                MigratedServiceCache.cacheMigratedService(FelicaAdapter.getInstance(), it.next());
                            }
                        }
                        if (!list.isEmpty() && UnsupportedMfiService1CardCache.getInstance().existUnsupportedMfiService1Card()) {
                            LogMgr.log(6, "001");
                            try {
                                if (new IndividualSpCardsAcquisitionTask(seId, list, cardListCreateCardList, cardListLoadIndividualCardListFromAssets).doAcquiring()) {
                                    return;
                                }
                            } catch (FwsException e) {
                                e = e;
                                onFinished(false, e.getType(), e.getMessage());
                                return;
                            } catch (GpException e2) {
                                e = e2;
                                LogMgr.printStackTrace(7, e);
                                onFinished(false, e.getType(), e.getMessage());
                                return;
                            } catch (Exception e3) {
                                e = e3;
                                LogMgr.printStackTrace(7, e);
                                onFinished(false, 200, ObfuscatedMsgUtil.exExecutionPoint(e));
                                return;
                            }
                        }
                    }
                } catch (FwsException e4) {
                    e = e4;
                } catch (GpException e5) {
                    e = e5;
                } catch (Exception e6) {
                    e = e6;
                }
            }
            onAppliedNeedForIndividualSpCards(cardListCreateCardList);
        } catch (FwsException e7) {
            onFinished(false, e7.getType(), e7.getMessage());
        } catch (JSONException e8) {
            LogMgr.log(2, "%s JSONException:%s", "700", e8.getMessage());
            LogMgr.printStackTrace(7, e8);
            onFinished(false, 202, MfiClientCallbackConst.MSG_FORMAT_ERROR);
        } catch (Exception e9) {
            LogMgr.log(2, "701 Exception:" + e9.getClass().getSimpleName(), e9.getMessage());
            LogMgr.printStackTrace(7, e9);
            onFinished(false, 200, ObfuscatedMsgUtil.executionPoint());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppliedNeedForIndividualSpCards(CardList fromFws) {
        if (CardListType.CARD_INFO_WITH_SP_STATUS == this.mCardListType) {
            fromFws.cardInfoWithSpStatusList = new ArrayList(fromFws.cardInfoMap.size());
            for (CompleteCardInfo completeCardInfo : fromFws.cardInfoMap.values()) {
                fromFws.cardInfoWithSpStatusList.add(new CardInfoWithSpStatus(completeCardInfo.getCid(), completeCardInfo.getServiceId(), completeCardInfo.getWalletAppId(), completeCardInfo.getCardStatus(), completeCardInfo.getCardPosition(), completeCardInfo.getTask(), completeCardInfo.getReissuePossibility(), completeCardInfo.getServiceType(), completeCardInfo.getAdditionalInfoHash(), completeCardInfo.getCardCategory(), completeCardInfo.getSpStatus(), completeCardInfo.getSpAdditionalInfo(), completeCardInfo.getCardType()));
            }
        }
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
        } else {
            setResult(fromFws);
            onFinished(true, 0, null);
        }
    }

    private CardList createCardList(GetCardListResponseJson json, CardJson.CheckType checkType) throws JSONException, FwsException {
        CardList cardList = new CardList();
        cardList.cardInfoMap = new LinkedHashMap<>();
        List<CardJson> cardList2 = json.getCardList();
        if (CardListType.CARD_ADDITIONAL_INFO == this.mCardListType) {
            cardList.cardAdditionalInfoList = new ArrayList(cardList2.size());
            Iterator<CardJson> it = cardList2.iterator();
            while (it.hasNext()) {
                cardList.cardAdditionalInfoList.add(it.next().getCardAdditionalInfo());
            }
        } else {
            if (CardListType.CARD_INFO_WITH_SP_STATUS == this.mCardListType) {
                try {
                    String mFCVersion = new MfiFelicaWrapper(this.mChipHolder).getMFCVersion(FelicaAdapter.getInstance());
                    for (CardJson cardJson : cardList2) {
                        cardList.cardInfoMap.put(cardJson.getCid(), cardJson.getCardInfoWithSpStatus(checkType, mFCVersion, this.mDataManager.getSeInfo()));
                    }
                } catch (MfiFelicaException unused) {
                    LogMgr.log(2, "700 failed to get mfc version.");
                    throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
                }
            } else {
                for (CardJson cardJson2 : cardList2) {
                    cardList.cardInfoMap.put(cardJson2.getCid(), cardJson2.getCardInfo(checkType, this.mDataManager.getSeInfo()));
                }
            }
            cardList.cardIdInfoMap = new HashMap();
            for (CardIdentifiableInfoJson cardIdentifiableInfoJson : json.getCardIdentifiableInfoList()) {
                cardList.cardIdInfoMap.put(cardIdentifiableInfoJson.getServiceId(), cardIdentifiableInfoJson.getCardIdentifiableInfo());
            }
        }
        return cardList;
    }

    /* JADX DEBUG: Method merged with bridge method: setResult(Ljava/lang/Object;)V */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(CardList cardList) {
        this.mCardList = cardList;
    }

    /* JADX DEBUG: Method merged with bridge method: getResult()Ljava/lang/Object; */
    @Override // com.felicanetworks.mfc.mfi.fws.StoppableTaskBase
    /* JADX INFO: renamed from: getResult */
    public synchronized CardList getResult2() {
        return this.mCardList;
    }

    private CardList loadIndividualCardListFromAssets() throws Throwable {
        String strLoad = AssetsUtil.load(INDIVIDUAL_SP_CARD_ASSETS_NAME);
        if (strLoad == null) {
            LogMgr.log(2, "700 failed to load assets individual_cards.json");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        }
        try {
            CardList cardListCreateCardList = createCardList(new GetCardListResponseJson(strLoad), CardJson.CheckType.ASSET_INDIVIDUAL_CARD);
            if (cardListCreateCardList.cardInfoMap.size() == 1 && cardListCreateCardList.cardIdInfoMap.size() == 1) {
                return cardListCreateCardList;
            }
            LogMgr.log(2, "703 failed to load assets individual_cards.json");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        } catch (JSONException e) {
            LogMgr.log(2, "701 failed to parse assets individual_cards.json");
            throw new FwsException(200, ObfuscatedMsgUtil.exExecutionPoint(e));
        } catch (Exception e2) {
            LogMgr.log(2, "702 Exception:" + e2.getClass().getSimpleName(), e2.getMessage());
            LogMgr.printStackTrace(7, e2);
            throw new FwsException(200, ObfuscatedMsgUtil.exExecutionPoint(e2));
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
            getCardListRequestJson.setLoginTokenId(GetCardListTask.this.mLoginTokenId);
            if (GetCardListTask.this.mCallingAppInfos != null) {
                getCardListRequestJson.setPlatformType(GetCardListTask.this.mDataManager.getSeInfo().getPlatformType());
                getCardListRequestJson.setWalletAppCallerInfoList(GetCardListTask.this.mCallingAppInfos);
            }
            WalletAppIdentifiableInfo walletAppIdentifiableInfo = WalletAppIdentifiableInfo.getInstance();
            if (!walletAppIdentifiableInfo.hasPackageName()) {
                throw new JSONException("packageName is null");
            }
            getCardListRequestJson.setWalletAppIdentifiableInfo(walletAppIdentifiableInfo.getPackageName());
            getCardListRequestJson.setSeInfo(GetCardListTask.this.mDataManager.getSeInfo(), Property.getSeType());
            String languageCode = FwsParamCreator.getLanguageCode();
            if (GetCardListTask.this.mCardListType != CardListType.CARD_INFO) {
                if (GetCardListTask.this.mCardListType != CardListType.CARD_ADDITIONAL_INFO) {
                    if (GetCardListTask.this.mCardListType == CardListType.CARD_INFO_WITH_SP_STATUS) {
                        getCardListRequestJson.setAdditionalInfoHashParameter(languageCode);
                        getCardListRequestJson.setServiceId(GetCardListTask.this.mServiceId);
                        getCardListRequestJson.setSpSync(true);
                    }
                    return getCardListRequestJson;
                }
                getCardListRequestJson.setAdditionalInfoParameter(languageCode);
                getCardListRequestJson.setAdditionalInfoHashParameter(languageCode);
                getCardListRequestJson.setCidList(GetCardListTask.this.mCidList);
                getCardListRequestJson.setSpSync(false);
                return getCardListRequestJson;
            }
            getCardListRequestJson.setAdditionalInfoHashParameter(languageCode);
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
            return GetCardListTask.VALID_RESULT_CODE_LIST_GET_CARD_LIST;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String getApiHash() {
            return MfiClientCallbackConst.Api.FWS_GET_CARD_LIST.msg;
        }
    }

    private class IndividualSpCardsAcquisitionTask implements AsyncTaskBase.Listener {
        private final List<String> mCidList;
        private final CardList mFromAssets;
        private final CardList mFromFws;
        private final String mSeId;
        private final List<String> mServiceIdList = new ArrayList();
        private CheckExistsManagementCardTask mTask;

        IndividualSpCardsAcquisitionTask(String seId, List<String> cidList, CardList fromFws, CardList fromAssets) {
            this.mSeId = seId;
            this.mCidList = cidList;
            this.mFromFws = fromFws;
            this.mFromAssets = fromAssets;
            for (CompleteCardInfo completeCardInfo : fromAssets.cardInfoMap.values()) {
                if (!this.mServiceIdList.contains(completeCardInfo.getServiceId())) {
                    this.mServiceIdList.add(completeCardInfo.getServiceId());
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public boolean doAcquiring() {
            LogMgr.log(6, "000");
            if (this.mServiceIdList.isEmpty()) {
                LogMgr.log(6, "998");
                return false;
            }
            CheckExistsManagementCardTask checkExistsManagementCardTask = new CheckExistsManagementCardTask(0, GetCardListTask.this.mExecutor, this, GetCardListTask.this.mFwsClient, GetCardListTask.this.mChipHolder, GetCardListTask.this.mDataManager, GetCardListTask.this.mJwsCreator, this.mServiceIdList.remove(0));
            this.mTask = checkExistsManagementCardTask;
            GetCardListTask.this.setStoppableSubTask(checkExistsManagementCardTask);
            this.mTask.start();
            LogMgr.log(6, "999");
            return true;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase.Listener
        public void onFinishTask(TaskBase task, boolean isSuccess, int errType, String errMsg) {
            LogMgr.log(6, "000");
            if (!isSuccess) {
                LogMgr.log(2, "700");
                GetCardListTask.this.onFinished(false, errType, errMsg);
                return;
            }
            if (!this.mTask.getResult2().exist) {
                try {
                    List<CompleteCardInfo> listAcquireIndividualSpCards = IndividualSpChecker.acquireIndividualSpCards(GetCardListTask.this.mChipHolder, this.mFromAssets.cardInfoMap, this.mFromAssets.cardIdInfoMap, this.mCidList, this.mSeId, this.mTask.getResult2().serviceId);
                    if (listAcquireIndividualSpCards != null) {
                        for (CompleteCardInfo completeCardInfo : listAcquireIndividualSpCards) {
                            this.mFromFws.cardInfoMap.put(completeCardInfo.getCid(), completeCardInfo);
                            this.mFromFws.cardIdInfoMap.put(completeCardInfo.getServiceId(), this.mFromAssets.cardIdInfoMap.get(completeCardInfo.getServiceId()));
                        }
                    }
                } catch (FwsException e) {
                    LogMgr.printStackTrace(7, e);
                    GetCardListTask.this.onFinished(false, e.getType(), e.getMessage());
                    return;
                } catch (GpException e2) {
                    LogMgr.printStackTrace(7, e2);
                    GetCardListTask.this.onFinished(false, e2.getType(), e2.getMessage());
                    return;
                } catch (InterruptedException e3) {
                    LogMgr.printStackTrace(7, e3);
                    GetCardListTask.this.onFinished(false, 215, null);
                    return;
                }
            }
            if (!doAcquiring()) {
                GetCardListTask.this.onAppliedNeedForIndividualSpCards(this.mFromFws);
                LogMgr.log(6, "999");
            } else {
                LogMgr.log(6, "998 continue.");
            }
        }
    }
}
