package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class GetCardListRequestJson extends RequestJson {
    private static final String NAME_ADDITIONAL_INFO = "additionalInfoParameter";
    private static final String NAME_ADDITIONAL_INFO_HASH_PARAMETER = "additionalInfoHashParameter";
    private static final String NAME_ADDITIONAL_INFO_HASH_PARAMETER_LANGUAGE_CODE = "languageCode";
    private static final String NAME_CID_LIST = "cidList";
    private static final String NAME_CONDITIONS = "conditions";
    private static final String NAME_LANGUAGE_CODE = "languageCode";
    private static final String NAME_PLATFORM_TYPE = "platformType";
    private static final String NAME_SERVICE_ID = "serviceId";
    private static final String NAME_SP_SYNC = "spSync";
    private static final String NAME_WALLET_APP_IDENTIFIABLE_INFO = "walletAppIdentifiableInfo";
    private static final String NAME_WALLET_APP_INFO_LIST = "walletAppCallerInfoList";

    public void setPlatformType(String platformType) throws JSONException {
        putConditionsMember(NAME_PLATFORM_TYPE, platformType);
    }

    public void setWalletAppCallerInfoList(JSONArray walletAppCallerInfoList) throws JSONException {
        putConditionsMember(NAME_WALLET_APP_INFO_LIST, walletAppCallerInfoList);
    }

    public void setWalletAppIdentifiableInfo(String walletAppIdentifiableInfo) throws JSONException {
        putConditionsMember(NAME_WALLET_APP_IDENTIFIABLE_INFO, walletAppIdentifiableInfo);
    }

    public void setSeInfo(SeInfo seInfo, String seType) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, seType);
        putConditionsMember(seInfoJson.getName(), seInfoJson);
    }

    public void setAdditionalInfoParameter(String languageCode) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("languageCode", languageCode);
        putPayloadMember(NAME_ADDITIONAL_INFO, jSONObject);
    }

    public void setServiceId(String serviceId) throws JSONException {
        putConditionsMember("serviceId", serviceId);
    }

    public void setCidList(String[] cidList) throws JSONException {
        if (cidList != null) {
            JSONArray jSONArray = new JSONArray();
            for (String str : cidList) {
                jSONArray.put(str);
            }
            putConditionsMember(NAME_CID_LIST, jSONArray);
        }
    }

    public void setAdditionalInfoHashParameter(String languageCode) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("languageCode", languageCode);
        putPayloadMember(NAME_ADDITIONAL_INFO_HASH_PARAMETER, jSONObject);
    }

    public void setSpSync(boolean spSync) throws JSONException {
        putPayloadMember(NAME_SP_SYNC, spSync);
    }

    private void putConditionsMember(String name, JSONObject value) throws JSONException {
        JSONObject jSONObjectOptPayloadJSONObjectMember = optPayloadJSONObjectMember(NAME_CONDITIONS);
        if (jSONObjectOptPayloadJSONObjectMember == null) {
            jSONObjectOptPayloadJSONObjectMember = new JSONObject();
        }
        jSONObjectOptPayloadJSONObjectMember.put(name, value);
        putPayloadMember(NAME_CONDITIONS, jSONObjectOptPayloadJSONObjectMember);
    }

    private void putConditionsMember(String name, JSONArray value) throws JSONException {
        JSONObject jSONObjectOptPayloadJSONObjectMember = optPayloadJSONObjectMember(NAME_CONDITIONS);
        if (jSONObjectOptPayloadJSONObjectMember == null) {
            jSONObjectOptPayloadJSONObjectMember = new JSONObject();
        }
        jSONObjectOptPayloadJSONObjectMember.put(name, value);
        putPayloadMember(NAME_CONDITIONS, jSONObjectOptPayloadJSONObjectMember);
    }

    private void putConditionsMember(String name, String value) throws JSONException {
        JSONObject jSONObjectOptPayloadJSONObjectMember = optPayloadJSONObjectMember(NAME_CONDITIONS);
        if (jSONObjectOptPayloadJSONObjectMember == null) {
            jSONObjectOptPayloadJSONObjectMember = new JSONObject();
        }
        jSONObjectOptPayloadJSONObjectMember.put(name, value);
        putPayloadMember(NAME_CONDITIONS, jSONObjectOptPayloadJSONObjectMember);
    }
}
