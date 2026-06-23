package com.felicanetworks.semc.sws.json;

import com.felicanetworks.semc.util.LogMgr;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public abstract class ResponseJson extends JSONObject {
    private static final int LEN_RESULT_CODE = 4;
    protected static final String NAME_HEADER = "header";
    protected static final String NAME_PAYLOAD = "payload";
    protected static final String NAME_RESULT_CODE = "resultCode";
    protected static final String NAME_RESULT_MESSAGE = "resultMessage";

    protected abstract void checkPayloadMembers() throws JSONException;

    public ResponseJson(String str) throws JSONException {
        super(str);
        LogMgr.log(8, "000");
        LogMgr.log(8, "999");
    }

    public void checkHeaderMembers() throws JSONException {
        LogMgr.log(8, "000");
        checkHeaderMembers(JsonUtil.checkObject(this, NAME_HEADER, true));
        LogMgr.log(8, "999");
    }

    protected void checkHeaderMembers(JSONObject jSONObject) throws JSONException {
        LogMgr.log(8, "000");
        JsonUtil.checkString(jSONObject, NAME_RESULT_CODE, true, 4);
        JsonUtil.checkString(jSONObject, NAME_RESULT_MESSAGE, false, 0);
        LogMgr.log(8, "999");
    }

    public void checkMembers() throws JSONException {
        LogMgr.log(8, "000");
        checkPayloadMembers();
        LogMgr.log(8, "999");
    }

    public String getResultCode() throws JSONException {
        LogMgr.log(8, "000");
        String resultCode = getResultCode(JsonUtil.checkObject(this, NAME_HEADER, true));
        LogMgr.log(8, "999 ret[" + resultCode + "]");
        return resultCode;
    }

    private String getResultCode(JSONObject jSONObject) throws JSONException {
        LogMgr.log(8, "000");
        String strCheckString = JsonUtil.checkString(jSONObject, NAME_RESULT_CODE, true, 4);
        LogMgr.log(8, "999 ret[" + strCheckString + "]");
        return strCheckString;
    }

    public boolean isSuccessType() throws JSONException {
        LogMgr.log(8, "000");
        boolean zFind = Pattern.compile("^0...$").matcher(getResultCode()).find();
        LogMgr.log(8, "999 ret[" + zFind + "]");
        return zFind;
    }

    public boolean isSuccess() throws JSONException {
        LogMgr.log(8, "000");
        boolean zEquals = "0000".equals(getResultCode());
        LogMgr.log(8, "999 ret[" + zEquals + "]");
        return zEquals;
    }
}
