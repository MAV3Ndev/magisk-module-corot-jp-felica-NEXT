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

/* JADX INFO: loaded from: classes.dex */
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

        static Options createCardListOptions(String str) {
            Options options = new Options();
            options.cardListType = CardListType.CARD_INFO;
            options.applicationId = str;
            return options;
        }

        static Options createCardAdditionalInfoListOptions(String[] strArr) {
            Options options = new Options();
            options.cardListType = CardListType.CARD_ADDITIONAL_INFO;
            options.cidList = strArr;
            return options;
        }

        static Options createCardInfoListWithSpStatusOptions(String str, String str2) {
            Options options = new Options();
            options.cardListType = CardListType.CARD_INFO_WITH_SP_STATUS;
            options.applicationId = str;
            options.serviceId = str2;
            return options;
        }
    }

    static {
        ArrayList arrayList = new ArrayList();
        VALID_RESULT_CODE_LIST_GET_CARD_LIST = arrayList;
        arrayList.add("0000");
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add(FwsConst.RESULT_CODE_RETRYREQUEST);
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add(FwsConst.RESULT_ILLEGAL_ARGUMENTS);
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add(FwsConst.RESULT_ILLEGAL_URL);
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add(FwsConst.RESULT_EXPIRED_LOGIN_TOKEN_ID);
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add("4000");
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add("9000");
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add("9001");
        VALID_RESULT_CODE_LIST_GET_CARD_LIST.add(FwsConst.RESULT_CONGESTED);
    }

    static class CardList {
        List<CardAdditionalInfo> cardAdditionalInfoList;
        Map<String, CardIdentifiableInfo> cardIdInfoMap;
        LinkedHashMap<String, CompleteCardInfo> cardInfoMap;
        List<CardInfoWithSpStatus> cardInfoWithSpStatusList;

        CardList() {
        }
    }

    GetCardListTask(int i, ExecutorService executorService, AsyncTaskBase.Listener listener, String str, JSONArray jSONArray, FwsClient fwsClient, MfiChipHolder mfiChipHolder, DataManager dataManager, Options options, a aVar) {
        super(i, executorService, listener);
        this.mCardListType = CardListType.CARD_INFO;
        this.mLoginTokenId = str;
        this.mCallingAppInfos = jSONArray;
        this.mFwsClient = fwsClient;
        this.mChipHolder = mfiChipHolder;
        this.mDataManager = dataManager;
        this.mCardListType = options.cardListType;
        this.mServiceId = options.serviceId;
        this.mApplicationId = options.applicationId;
        this.mCidList = options.cidList;
        this.mJwsCreator = aVar;
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x008e A[Catch: Exception -> 0x00e7, GpException -> 0x00f5, FwsException -> 0x0105, TryCatch #4 {FwsException -> 0x0105, GpException -> 0x00f5, Exception -> 0x00e7, blocks: (B:16:0x0048, B:26:0x008e, B:28:0x0096, B:30:0x00b6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ce, B:36:0x00d4, B:19:0x0068, B:21:0x006e), top: B:56:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00b6 A[Catch: Exception -> 0x00e7, GpException -> 0x00f5, FwsException -> 0x0105, TryCatch #4 {FwsException -> 0x0105, GpException -> 0x00f5, Exception -> 0x00e7, blocks: (B:16:0x0048, B:26:0x008e, B:28:0x0096, B:30:0x00b6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ce, B:36:0x00d4, B:19:0x0068, B:21:0x006e), top: B:56:0x0048 }] */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d4 A[Catch: Exception -> 0x00e7, GpException -> 0x00f5, FwsException -> 0x0105, TRY_LEAVE, TryCatch #4 {FwsException -> 0x0105, GpException -> 0x00f5, Exception -> 0x00e7, blocks: (B:16:0x0048, B:26:0x008e, B:28:0x0096, B:30:0x00b6, B:31:0x00ba, B:33:0x00c0, B:34:0x00ce, B:36:0x00d4, B:19:0x0068, B:21:0x006e), top: B:56:0x0048 }] */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    protected void run() throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 310
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.fws.GetCardListTask.run():void");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onAppliedNeedForIndividualSpCards(CardList cardList) {
        CardList cardList2 = cardList;
        if (CardListType.CARD_INFO_WITH_SP_STATUS == this.mCardListType) {
            cardList2.cardInfoWithSpStatusList = new ArrayList(cardList2.cardInfoMap.size());
            for (CompleteCardInfo completeCardInfo : cardList2.cardInfoMap.values()) {
                cardList2.cardInfoWithSpStatusList.add(new CardInfoWithSpStatus(completeCardInfo.getCid(), completeCardInfo.getServiceId(), completeCardInfo.getWalletAppId(), completeCardInfo.getCardStatus(), completeCardInfo.getCardPosition(), completeCardInfo.getTask(), completeCardInfo.getReissuePossibility(), completeCardInfo.getServiceType(), completeCardInfo.getAdditionalInfoHash(), completeCardInfo.getCardCategory(), completeCardInfo.getSpStatus(), completeCardInfo.getSpAdditionalInfo()));
                cardList2 = cardList;
            }
        }
        if (isStopped()) {
            LogMgr.log(2, "700 Already has stopped.");
            onFinished(false, 215, null);
        } else {
            setResult(cardList);
            onFinished(true, 0, null);
        }
    }

    private CardList createCardList(GetCardListResponseJson getCardListResponseJson, CardJson.CheckType checkType) throws JSONException, FwsException {
        CardList cardList = new CardList();
        cardList.cardInfoMap = new LinkedHashMap<>();
        List<CardJson> cardList2 = getCardListResponseJson.getCardList();
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
            for (CardIdentifiableInfoJson cardIdentifiableInfoJson : getCardListResponseJson.getCardIdentifiableInfoList()) {
                cardList.cardIdInfoMap.put(cardIdentifiableInfoJson.getServiceId(), cardIdentifiableInfoJson.getCardIdentifiableInfo());
            }
        }
        return cardList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.felicanetworks.mfc.mfi.fws.AsyncTaskBase
    public synchronized void setResult(CardList cardList) {
        this.mCardList = cardList;
    }

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
            LogMgr.log(2, "702 failed to load assets individual_cards.json");
            throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
        } catch (JSONException e) {
            LogMgr.log(2, "701 failed to parse assets individual_cards.json");
            throw new FwsException(200, ObfuscatedMsgUtil.exExecutionPoint(e));
        }
    }

    private class FwsGetCardListSubTask extends AccessFwsTask<GetCardListResponseJson> {
        private static final int FWS_SEQUENCE_NUMBER = 1;

        FwsGetCardListSubTask(int i, FwsClient fwsClient) {
            super(i, fwsClient);
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
            getCardListRequestJson.setSeInfo(GetCardListTask.this.mDataManager.getSeInfo(), Property.getSeType());
            String languageCode = FwsParamCreator.getLanguageCode();
            if (GetCardListTask.this.mCardListType != CardListType.CARD_INFO) {
                if (GetCardListTask.this.mCardListType != CardListType.CARD_ADDITIONAL_INFO) {
                    if (GetCardListTask.this.mCardListType == CardListType.CARD_INFO_WITH_SP_STATUS) {
                        getCardListRequestJson.setAdditionalInfoHashParameter(languageCode);
                        getCardListRequestJson.setServiceId(GetCardListTask.this.mServiceId);
                        getCardListRequestJson.setSpSync(true);
                    }
                } else {
                    getCardListRequestJson.setAdditionalInfoParameter(languageCode);
                    getCardListRequestJson.setAdditionalInfoHashParameter(languageCode);
                    getCardListRequestJson.setCidList(GetCardListTask.this.mCidList);
                    getCardListRequestJson.setSpSync(false);
                }
            } else {
                getCardListRequestJson.setAdditionalInfoHashParameter(languageCode);
                getCardListRequestJson.setSpSync(false);
            }
            return getCardListRequestJson;
        }

        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        protected String callFws(String str) throws ProtocolException, HttpException {
            return this.mFwsClient.getCardList(str, 1);
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.felicanetworks.mfc.mfi.fws.AccessFwsTask
        public GetCardListResponseJson convertResponse(String str) throws JSONException {
            return new GetCardListResponseJson(str);
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

        IndividualSpCardsAcquisitionTask(String str, List<String> list, CardList cardList, CardList cardList2) {
            this.mSeId = str;
            this.mCidList = list;
            this.mFromFws = cardList;
            this.mFromAssets = cardList2;
            for (CompleteCardInfo completeCardInfo : this.mFromAssets.cardInfoMap.values()) {
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
        public void onFinishTask(TaskBase taskBase, boolean z, int i, String str) {
            LogMgr.log(6, "000");
            if (!z) {
                LogMgr.log(2, "700");
                GetCardListTask.this.onFinished(false, i, str);
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
