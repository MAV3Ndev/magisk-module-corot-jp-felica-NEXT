package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.util.LogMgr;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class LinkageDataResponseJson extends GetScriptResponseJson {
    private static final String DELETE_CARDS = "DeleteCards";
    private static final String NAME_ACTION_TYPE = "actionType";
    private static final String NAME_ACTION_TYPE_LINKAGE_DATA_LIST = "actionTypeLinkageDataList";
    private static final String NAME_LINKAGE_DATA_LIST = "linkageDataList";
    private static final String INITIALIZE_CHIP = "InitializeChip";
    private static final String REISSUE_CARDS = "ReissueCards";
    private static final String DELETE_SYSTEM = "DeleteSystem";
    private static final String[] VALID_ACTION_TYPE_LIST = {INITIALIZE_CHIP, REISSUE_CARDS, "DeleteCards", DELETE_SYSTEM};

    public LinkageDataResponseJson(String str) throws JSONException {
        super(str);
    }

    @Override // com.felicanetworks.mfc.mfi.fws.json.GetScriptResponseJson, com.felicanetworks.mfc.mfi.fws.json.ResponseJson
    protected void checkPayloadMembers() throws JSONException {
        super.checkPayloadMembers();
        if (isSuccess()) {
            JsonUtil.checkObject((JSONObject) this, "payload", true);
        }
    }

    public String[] optFirstLinkageDataList() throws JSONException {
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(JsonUtil.checkObject((JSONObject) this, "payload", true), NAME_ACTION_TYPE_LINKAGE_DATA_LIST, true);
        if (jSONArrayCheckArray.length() <= 0) {
            LogMgr.log(2, "700 actionTypeLinkageDataList is empty");
            throw new JSONException("actionTypeLinkageDataList is empty.");
        }
        JSONObject jSONObject = jSONArrayCheckArray.getJSONObject(0);
        String strCheckString = JsonUtil.checkString(jSONObject, NAME_ACTION_TYPE, true, 0);
        if (!Arrays.asList(VALID_ACTION_TYPE_LIST).contains(strCheckString)) {
            LogMgr.log(2, "701 actionType is invalid : " + strCheckString);
            throw new JSONException("actionType is invalid : " + strCheckString);
        }
        if (!jSONObject.has(NAME_LINKAGE_DATA_LIST)) {
            LogMgr.log(2, "702 linkageDataList is not exists");
            throw new JSONException("linkageDataList is not exists");
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(NAME_LINKAGE_DATA_LIST);
        if (jSONArrayOptJSONArray == null) {
            if (strCheckString.equals("DeleteCards")) {
                return null;
            }
            LogMgr.log(2, "703 linkageDataList is null");
            throw new JSONException("linkageDataList is null");
        }
        int length = jSONArrayOptJSONArray.length();
        if (length <= 0) {
            LogMgr.log(2, "704 linkageDataList is empty");
            throw new JSONException("linkageDataList is empty.");
        }
        String[] strArr = new String[length];
        for (int i = 0; i < length; i++) {
            strArr[i] = jSONArrayOptJSONArray.getString(i);
            if (strArr[i].equals("null") || strArr[i].equals("")) {
                LogMgr.log(2, "705 linkageDataList is invalid");
                throw new JSONException("linkageDataList is invalid");
            }
        }
        return strArr;
    }
}
