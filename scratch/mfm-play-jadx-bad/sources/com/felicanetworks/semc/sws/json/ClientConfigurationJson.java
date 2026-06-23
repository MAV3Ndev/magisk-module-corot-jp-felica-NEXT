package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ClientConfigurationJson extends JSONObject {
    private static final String NAME_CONTROL_INFO = "controlInfo";
    private static final String NAME_SP_APPLET_INFO_LIST = "spAppletInfoList";
    private static final String NAME_SP_APP_INFO_LIST = "spAppInfoList";

    public ClientConfigurationJson(String str) throws JSONException {
        super(str);
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
    }

    public void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        JSONArray jSONArrayCheckArray = JsonUtil.checkArray(this, "spAppInfoList", true);
        if (jSONArrayCheckArray != null) {
            new SpAppInfoJsonArray(jSONArrayCheckArray.toString()).checkMembers();
        }
        JSONArray jSONArrayCheckArray2 = JsonUtil.checkArray(this, "controlInfo", true);
        if (jSONArrayCheckArray2 != null) {
            new ClientControlInfoJsonArray(jSONArrayCheckArray2.toString()).checkMembers();
        }
        JSONArray jSONArrayCheckArray3 = JsonUtil.checkArray(this, "spAppletInfoList", true);
        if (jSONArrayCheckArray3 != null) {
            new SpAppletInfoListJsonArray(jSONArrayCheckArray3.toString()).checkMembers();
        }
        LogMgr.log(8, "999");
    }

    public String getSpAppInfoList() throws JSONException {
        String strCheckString = JsonUtil.checkString(this, "spAppInfoList", true, 0);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public String getClientControlInfo() throws JSONException {
        LogMgr.log(6, "000");
        String strCheckString = JsonUtil.checkString(this, "controlInfo", true, 0);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public String getSpAppletInfoList() throws JSONException {
        String strCheckString = JsonUtil.checkString(this, "spAppletInfoList", true, 0);
        LogMgr.log(6, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }
}
