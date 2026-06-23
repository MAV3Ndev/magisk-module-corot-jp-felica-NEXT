package com.felicanetworks.mfc.mfi.fws;

import com.felicanetworks.mfc.mfi.CardIdentifiableInfo;
import com.felicanetworks.mfc.mfi.CardInfo;
import com.felicanetworks.mfc.mfi.CompleteCardInfo;
import com.felicanetworks.mfc.mfi.DataManager;
import com.felicanetworks.mfc.mfi.MfiChipHolder;
import com.felicanetworks.mfc.mfi.SeInfo;
import com.felicanetworks.mfc.mfi.fws.json.CardIdentifiableInfoJson;
import com.felicanetworks.mfc.mfi.fws.json.CardIdentifiableInfoJsonArray;
import com.felicanetworks.mfc.mfi.fws.json.CardJson;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
class RemainedCardsCache {
    private RemainedCardDetail mDetailForDelete;
    private RemainedCardDetail mDetailForPermanentDelete;

    static class UploadCardInfo {
        CardIdentifiableInfo.Cache cardIdInfo;
        String cid;
        String serviceType;

        UploadCardInfo() {
        }
    }

    RemainedCardsCache(final JSONArray cardInfoListJsonToDelete, final JSONArray cardIdInfoListJsonToDelete, final JSONArray cardInfoListJsonToPermanentDelete, final JSONArray cardIdInfoListJsonToPermanentDelete, final SeInfo seInfo) throws JSONException {
        this.mDetailForDelete = new RemainedCardDetail(cardInfoListJsonToDelete, cardIdInfoListJsonToDelete, seInfo);
        this.mDetailForPermanentDelete = new RemainedCardDetail(cardInfoListJsonToPermanentDelete, cardIdInfoListJsonToPermanentDelete, seInfo);
    }

    void checkConsistencyWithChip(final MfiChipHolder chipHolder, final DataManager dataManager) throws Throwable {
        this.mDetailForDelete.checkConsistencyWithChip(chipHolder, dataManager);
        this.mDetailForPermanentDelete.checkConsistencyWithChip(chipHolder, dataManager);
    }

    CardInfo[] getCardInfoListToDelete() {
        try {
            return (CardInfo[]) this.mDetailForDelete.getCardInfoMap().values().toArray(new CardInfo[0]);
        } finally {
            this.mDetailForDelete.clearNonCacheableData();
        }
    }

    CardInfo[] getCardInfoListToPermanentDelete() {
        try {
            return (CardInfo[]) this.mDetailForPermanentDelete.getCardInfoMap().values().toArray(new CardInfo[0]);
        } finally {
            this.mDetailForPermanentDelete.clearNonCacheableData();
        }
    }

    boolean existInfoToDelete() {
        return this.mDetailForDelete != null;
    }

    boolean existInfoToPermanentDelete() {
        return this.mDetailForPermanentDelete != null;
    }

    void clearInfoToDelete() {
        this.mDetailForDelete = null;
    }

    void clearInfoToPermanentDelete() {
        this.mDetailForPermanentDelete = null;
    }

    List<UploadCardInfo> createDeleteCardInfoList() throws FwsException {
        LogMgr.log(6, "000");
        ArrayList arrayList = new ArrayList();
        for (String str : getCidListToDelete()) {
            UploadCardInfo uploadCardInfo = new UploadCardInfo();
            uploadCardInfo.cid = str;
            uploadCardInfo.serviceType = getCardCacheInfoToDelete(str).serviceType;
            String str2 = getCardCacheInfoToDelete(str).serviceId;
            if (getCardIdentifiableCacheInfoToDelete(str2) != null) {
                uploadCardInfo.cardIdInfo = getCardIdentifiableCacheInfoToDelete(str2);
                arrayList.add(uploadCardInfo);
            } else {
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
        }
        LogMgr.log(6, "999");
        return arrayList;
    }

    private CompleteCardInfo.Cache getCardCacheInfoToDelete(final String cid) {
        return this.mDetailForDelete.getCardCacheInfoMap().get(cid);
    }

    private CardIdentifiableInfo.Cache getCardIdentifiableCacheInfoToDelete(final String serviceId) {
        return this.mDetailForDelete.getCardIdCacheInfoMap().get(serviceId);
    }

    private List<String> getCidListToDelete() {
        return new ArrayList(this.mDetailForDelete.getCardCacheInfoMap().keySet());
    }

    List<UploadCardInfo> createPermanentDeleteCardInfoList() throws FwsException {
        LogMgr.log(6, "000");
        ArrayList arrayList = new ArrayList();
        for (String str : getCidListToPermanentDelete()) {
            UploadCardInfo uploadCardInfo = new UploadCardInfo();
            uploadCardInfo.cid = str;
            uploadCardInfo.serviceType = getCardCacheInfoToPermanentDelete(str).serviceType;
            String str2 = getCardCacheInfoToPermanentDelete(str).serviceId;
            if (getCardIdentifiableCacheInfoToPermanentDelete(str2) != null) {
                uploadCardInfo.cardIdInfo = getCardIdentifiableCacheInfoToPermanentDelete(str2);
                arrayList.add(uploadCardInfo);
            } else {
                throw new FwsException(200, ObfuscatedMsgUtil.executionPoint());
            }
        }
        LogMgr.log(6, "999");
        return arrayList;
    }

    private CompleteCardInfo.Cache getCardCacheInfoToPermanentDelete(final String cid) {
        return this.mDetailForPermanentDelete.getCardCacheInfoMap().get(cid);
    }

    private CardIdentifiableInfo.Cache getCardIdentifiableCacheInfoToPermanentDelete(final String serviceId) {
        return this.mDetailForPermanentDelete.getCardIdCacheInfoMap().get(serviceId);
    }

    private List<String> getCidListToPermanentDelete() {
        return new ArrayList(this.mDetailForPermanentDelete.getCardCacheInfoMap().keySet());
    }

    private static class RemainedCardDetail {
        private LinkedHashMap<String, CompleteCardInfo.Cache> mCardCacheInfoMap;
        private Map<String, CardIdentifiableInfo.Cache> mCardIdCacheInfoMap;
        private Map<String, CardIdentifiableInfo> mCardIdInfoMap;
        private LinkedHashMap<String, CompleteCardInfo> mCardInfoMap;

        RemainedCardDetail(final JSONArray cardInfoListJson, final JSONArray cardIdInfoListJson, final SeInfo seInfo) throws JSONException {
            createCardInfoMap(cardInfoListJson, seInfo);
            createIdInfoMap(cardIdInfoListJson);
        }

        private void createCardInfoMap(final JSONArray cardInfoJsonArray, final SeInfo seInfo) throws JSONException {
            LogMgr.log(6, "000");
            if (cardInfoJsonArray == null) {
                throw new JSONException("cardInfoJSONArray is null");
            }
            this.mCardInfoMap = new LinkedHashMap<>();
            this.mCardCacheInfoMap = new LinkedHashMap<>();
            for (int i = 0; i < cardInfoJsonArray.length(); i++) {
                String string = cardInfoJsonArray.getString(i);
                if (string == null) {
                    throw new JSONException("Card info is null. index=" + i);
                }
                CardJson cardJson = new CardJson(string);
                CompleteCardInfo cardInfo = cardJson.getCardInfo(CardJson.CheckType.FWS_GET_UPLOAD_TARGET, seInfo);
                this.mCardInfoMap.put(cardJson.getCid(), cardInfo);
                this.mCardCacheInfoMap.put(cardJson.getCid(), cardInfo.getCacheableData());
            }
            LogMgr.log(6, "999");
        }

        private void createIdInfoMap(JSONArray cardIdInfoJSONArray) throws JSONException {
            LogMgr.log(6, "000");
            if (cardIdInfoJSONArray == null) {
                throw new JSONException("cardIdInfoJSONArray is null");
            }
            this.mCardIdInfoMap = new HashMap();
            this.mCardIdCacheInfoMap = new HashMap();
            List<CardIdentifiableInfoJson> cardIdentifiableInfoList = new CardIdentifiableInfoJsonArray(cardIdInfoJSONArray.toString()).getCardIdentifiableInfoList();
            for (int i = 0; i < cardIdentifiableInfoList.size(); i++) {
                CardIdentifiableInfo cardIdentifiableInfo = cardIdentifiableInfoList.get(i).getCardIdentifiableInfo();
                this.mCardIdInfoMap.put(cardIdentifiableInfo.serviceId, cardIdentifiableInfo);
                this.mCardIdCacheInfoMap.put(cardIdentifiableInfo.serviceId, cardIdentifiableInfo.getCacheableData());
            }
            LogMgr.log(6, "999");
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearNonCacheableData() {
            this.mCardInfoMap = null;
            this.mCardIdInfoMap = null;
        }

        public LinkedHashMap<String, CompleteCardInfo> getCardInfoMap() {
            return this.mCardInfoMap;
        }

        public LinkedHashMap<String, CompleteCardInfo.Cache> getCardCacheInfoMap() {
            return this.mCardCacheInfoMap;
        }

        public Map<String, CardIdentifiableInfo.Cache> getCardIdCacheInfoMap() {
            return this.mCardIdCacheInfoMap;
        }

        void checkConsistencyWithChip(final MfiChipHolder chipHolder, final DataManager dataManager) throws Throwable {
            LogMgr.log(6, "000");
            LinkedHashMap<String, CompleteCardInfo> linkedHashMapUpdateCardByCidMap = new CardIdentifiableInfoChecker().updateCardByCidMap(chipHolder, dataManager, this.mCardInfoMap, this.mCardIdInfoMap);
            this.mCardInfoMap = linkedHashMapUpdateCardByCidMap;
            for (Map.Entry<String, CompleteCardInfo> entry : linkedHashMapUpdateCardByCidMap.entrySet()) {
                this.mCardCacheInfoMap.put(entry.getKey(), entry.getValue().getCacheableData());
            }
            LogMgr.log(6, "999");
        }
    }
}
