package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
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
    private static final String NAME_WALLET_APP_INFO_LIST = "walletAppCallerInfoList";

    public void setPlatformType(String str) throws JSONException {
        putConditionsMember(NAME_PLATFORM_TYPE, str);
    }

    public void setWalletAppCallerInfoList(JSONArray jSONArray) throws JSONException {
        putConditionsMember(NAME_WALLET_APP_INFO_LIST, jSONArray);
    }

    public void setSeInfo(SeInfo seInfo, String str) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, str);
        putConditionsMember(seInfoJson.getName(), seInfoJson);
    }

    public void setAdditionalInfoParameter(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("languageCode", str);
        putPayloadMember(NAME_ADDITIONAL_INFO, jSONObject);
    }

    public void setServiceId(String str) throws JSONException {
        putConditionsMember(NAME_SERVICE_ID, str);
    }

    public void setCidList(String[] strArr) throws JSONException {
        if (strArr != null) {
            JSONArray jSONArray = new JSONArray();
            for (String str : strArr) {
                jSONArray.put(str);
            }
            putConditionsMember(NAME_CID_LIST, jSONArray);
        }
    }

    public void setAdditionalInfoHashParameter(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("languageCode", str);
        putPayloadMember(NAME_ADDITIONAL_INFO_HASH_PARAMETER, jSONObject);
    }

    public void setSpSync(boolean z) throws JSONException {
        putPayloadMember(NAME_SP_SYNC, z);
    }

    private void putConditionsMember(String str, JSONObject jSONObject) throws JSONException {
        JSONObject jSONObjectOptPayloadJSONObjectMember = optPayloadJSONObjectMember(NAME_CONDITIONS);
        if (jSONObjectOptPayloadJSONObjectMember == null) {
            jSONObjectOptPayloadJSONObjectMember = new JSONObject();
        }
        jSONObjectOptPayloadJSONObjectMember.put(str, jSONObject);
        putPayloadMember(NAME_CONDITIONS, jSONObjectOptPayloadJSONObjectMember);
    }

    private void putConditionsMember(String str, JSONArray jSONArray) throws JSONException {
        JSONObject jSONObjectOptPayloadJSONObjectMember = optPayloadJSONObjectMember(NAME_CONDITIONS);
        if (jSONObjectOptPayloadJSONObjectMember == null) {
            jSONObjectOptPayloadJSONObjectMember = new JSONObject();
        }
        jSONObjectOptPayloadJSONObjectMember.put(str, jSONArray);
        putPayloadMember(NAME_CONDITIONS, jSONObjectOptPayloadJSONObjectMember);
    }

    private void putConditionsMember(String str, String str2) throws JSONException {
        JSONObject jSONObjectOptPayloadJSONObjectMember = optPayloadJSONObjectMember(NAME_CONDITIONS);
        if (jSONObjectOptPayloadJSONObjectMember == null) {
            jSONObjectOptPayloadJSONObjectMember = new JSONObject();
        }
        jSONObjectOptPayloadJSONObjectMember.put(str, str2);
        putPayloadMember(NAME_CONDITIONS, jSONObjectOptPayloadJSONObjectMember);
    }
}
