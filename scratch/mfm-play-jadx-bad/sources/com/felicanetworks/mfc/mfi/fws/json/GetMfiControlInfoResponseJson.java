package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.NotifyEventTargetAppInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetMfiControlInfoResponseJson extends ResponseJson {
    private static final int DEFAULT_TRANSMITTABLE_CARDS = 10;
    private static final int LEN_CARD_CATEGORY = 8;
    private static final int LEN_SERVICE_ID = 8;
    private static final int LEN_SYSTEM_CODE = 4;
    private static final int LEN_WALLET_APP_ID = 8;
    private static final int MAX_CLASS_NAME = 1024;
    private static final int MAX_TRANSMITTABLE_CARDS = 250;
    private static final int MAX_WALLET_APP_CALLER_INFO = 1024;
    private static final int MAX_WALLET_APP_IDENTIFIABLE_INFO = 1024;
    private static final int MIN_CLASS_NAME = 1;
    private static final int MIN_TRANSMITTABLE_CARDS = 1;
    private static final int MIN_WALLET_APP_CALLER_INFO = 1;
    private static final int MIN_WALLET_APP_IDENTIFIABLE_INFO = 1;
    private static final String NAME_CARD_CATEGORY = "cardCategory";
    private static final String NAME_CARD_CATEGORY_LIST = "cardCategoryList";
    private static final String NAME_CLASS_NAME = "className";
    private static final String NAME_DELAY_MILLIS_LIST = "delayMillisList";
    private static final String NAME_GET_CACHED_CARD_LIST = "getCachedCardList";
    private static final String NAME_GET_CARD_ADDITIONAL_INFO_LIST = "getCardAdditionalInfoList";
    private static final String NAME_GET_CARD_INFO_LIST_WITH_SP_STATUS = "getCardInfoListWithSpStatus";
    private static final String NAME_GET_CARD_LIST = "getCardList";
    private static final String NAME_GET_LIST_UNLIMITED = "getCardListUnlimited";
    private static final String NAME_INDIVIDUAL_SP_SUPPORT = "individualSpSupport";
    private static final String NAME_NOTIFY_EVENT_APP_INFO_LIST = "notifyEventTargetAppInfoList";
    private static final String NAME_OPERATE_APP_INFO_LIST = "operateAppInfoList";
    private static final String NAME_RETRY = "retry";
    private static final String NAME_SERVICE1_CARD_LIST = "service1CardList";
    private static final String NAME_SERVICE_ID = "serviceId";
    private static final String NAME_SYSTEM_CODE = "systemCode";
    private static final String NAME_TARGET_SERVICE_ID_LIST = "targetServiceIdList";
    private static final String NAME_TRANSMITTABLE_CARDS = "transmittableCards";
    private static final String NAME_WALLET_APP_CALLER_INFO = "walletAppCallerInfo";
    private static final String NAME_WALLET_APP_ID = "walletAppId";
    private static final String NAME_WALLET_APP_IDENTIFIABLE_INFO = "walletAppIdentifiableInfo";

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    public boolean isSuccessType() {
        return true;
    }

    public GetMfiControlInfoResponseJson(String json) throws JSONException {
        super(json);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    public void checkMembers() throws JSONException {
        getWalletAppIdAppIdList();
        getCardCategoryMap();
        getRetryTimesDelayMillisList();
        getTransmittableGetCardListSize();
        getTransmittableGetCardAdditionalInfoListSize();
        getTransmittableGetCardInfoListWithSpStatusSize();
    }

    public List<String> getWalletAppIdAppIdList() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(JsonUtil.checkObject((JSONObject) this, NAME_INDIVIDUAL_SP_SUPPORT, true), NAME_OPERATE_APP_INFO_LIST, true);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
            arrayList.add(JsonUtil.checkString(JsonUtil.checkObject(jSONArrayCheckArray, i, true), NAME_WALLET_APP_ID, true, 8));
        }
        return arrayList;
    }

    public Map<Integer, String> getCardCategoryMap() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, NAME_CARD_CATEGORY_LIST, true);
        HashMap map = new HashMap();
        for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject(jSONArrayCheckArray, i, true);
            String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_SYSTEM_CODE, true, 4);
            map.put(Integer.valueOf(StringUtil.hexToInteger(strCheckString)), JsonUtil.checkString(jSONObjectCheckObject, NAME_CARD_CATEGORY, true, 8));
        }
        return map;
    }

    public int[] getRetryTimesDelayMillisList() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(JsonUtil.checkObject((JSONObject) this, NAME_RETRY, true), NAME_DELAY_MILLIS_LIST, true);
        int length = jSONArrayCheckArray.length();
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            iArr[i] = jSONArrayCheckArray.getInt(i);
        }
        return iArr;
    }

    public int getTransmittableGetCardListSize() throws JSONException {
        return JsonUtil.checkInt(JsonUtil.checkObject((JSONObject) this, NAME_TRANSMITTABLE_CARDS, true), NAME_GET_CARD_LIST, true, 10, true, 1, false, 250);
    }

    public int getTransmittableGetCardAdditionalInfoListSize() throws JSONException {
        return JsonUtil.checkInt(JsonUtil.checkObject((JSONObject) this, NAME_TRANSMITTABLE_CARDS, true), NAME_GET_CARD_ADDITIONAL_INFO_LIST, true, 10, true, 1, false, 250);
    }

    public int getTransmittableGetCardInfoListWithSpStatusSize() throws JSONException {
        return JsonUtil.checkInt(JsonUtil.checkObject((JSONObject) this, NAME_TRANSMITTABLE_CARDS, true), NAME_GET_CARD_INFO_LIST_WITH_SP_STATUS, true, 10, true, 1, false, 250);
    }

    public int getTransmittableGetCachedCardListSize() throws JSONException {
        return JsonUtil.checkInt(JsonUtil.checkObject((JSONObject) this, NAME_TRANSMITTABLE_CARDS, true), NAME_GET_CACHED_CARD_LIST, true, 10, true, 1, false, 250);
    }

    public List<String> getService1ServiceIdList() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, NAME_SERVICE1_CARD_LIST, true);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
            arrayList.add(JsonUtil.checkString(JsonUtil.checkObject(jSONArrayCheckArray, i, true), "serviceId", true, 8));
        }
        return arrayList;
    }

    public List<NotifyEventTargetAppInfo> getNotifyEventTargetAppInfoList() throws JSONException {
        List<String> targetServiceIdList;
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, NAME_NOTIFY_EVENT_APP_INFO_LIST, true);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
            JSONObject jSONObjectCheckObject = JsonUtil.checkObject(jSONArrayCheckArray, i, true);
            String strCheckString = JsonUtil.checkString(jSONObjectCheckObject, NAME_WALLET_APP_IDENTIFIABLE_INFO, true, 1, 1024);
            String strCheckString2 = JsonUtil.checkString(jSONObjectCheckObject, NAME_CLASS_NAME, true, 1, 1024);
            String strCheckString3 = JsonUtil.checkString(jSONObjectCheckObject, NAME_WALLET_APP_CALLER_INFO, true, 1, 1024);
            boolean zCheckBoolean = JsonUtil.checkBoolean(jSONObjectCheckObject, NAME_GET_LIST_UNLIMITED, true);
            if (zCheckBoolean) {
                targetServiceIdList = new ArrayList<>();
            } else {
                targetServiceIdList = getTargetServiceIdList(jSONObjectCheckObject);
            }
            arrayList.add(new NotifyEventTargetAppInfo(strCheckString, strCheckString2, strCheckString3, zCheckBoolean, targetServiceIdList));
        }
        return arrayList;
    }

    private List<String> getTargetServiceIdList(JSONObject notifyEventTargetAppInfo) throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(notifyEventTargetAppInfo, NAME_TARGET_SERVICE_ID_LIST, true);
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < jSONArrayCheckArray.length(); i++) {
            arrayList.add(JsonUtil.checkString(JsonUtil.checkObject(jSONArrayCheckArray, i, true), "serviceId", true, 8));
        }
        return arrayList;
    }
}
