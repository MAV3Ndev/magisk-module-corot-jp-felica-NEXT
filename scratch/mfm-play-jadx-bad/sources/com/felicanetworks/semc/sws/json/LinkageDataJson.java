package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageDataJson extends JSONObject {
    private static final String NAME_ACCESS_TOKEN = "accessToken";
    private static final String NAME_APDU_COMMAND_INFO_LIST = "apduCommandInfoList";
    private static final String NAME_PROCESS_TRACE_INFO = "processTraceInfo";
    private static final String NAME_SEID = "seId";
    private static final String NAME_SERVICE_ID = "serviceId";
    private static final String NAME_SP_APPLET_ID = "spAppletId";
    private static final String NAME_SP_APP_ID = "spAppId";

    public LinkageDataJson(String str) throws JSONException {
        super(str);
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    public String getServiceId() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, "serviceId", true, 8);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String getSpAppId() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_SP_APP_ID, true, 8);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public String getSeid() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_SEID, true, 2, 128);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public JSONObject getProcessTraceInfo() throws JSONException {
        LogMgr.log(6, "000");
        JSONObject jSONObject = getJSONObject(NAME_PROCESS_TRACE_INFO);
        ProcessTraceInfoJson processTraceInfoJson = new ProcessTraceInfoJson(jSONObject.toString());
        processTraceInfoJson.checkOperationType();
        processTraceInfoJson.checkOperationId();
        processTraceInfoJson.checkProcessId();
        LogMgr.log(6, "999");
        return jSONObject;
    }

    public String getAccessToken() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, NAME_ACCESS_TOKEN, true, 32);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }

    public JSONArray optApduCommandInfoList() throws JSONException {
        LogMgr.log(6, "000");
        JSONArray jSONArrayOptJSONArray = optJSONArray(NAME_APDU_COMMAND_INFO_LIST);
        if (jSONArrayOptJSONArray != null) {
            if (jSONArrayOptJSONArray.length() == 0) {
                jSONArrayOptJSONArray = null;
            } else {
                new ApduCommandInfoJsonArray(jSONArrayOptJSONArray.toString()).checkMembers();
            }
        }
        LogMgr.log(6, "999");
        return jSONArrayOptJSONArray;
    }

    public String getSpAppletId() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, "spAppletId", false, 8);
        LogMgr.log(6, "999 ret=[" + strCheckString + "]");
        return strCheckString;
    }
}
