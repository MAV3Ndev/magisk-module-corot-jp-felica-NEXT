package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class TcapResultJson extends JSONObject implements IJsonMember {
    private static final String NAME = "tcapResult";
    private static final String NAME_RESULT_CODE = "resultCode";
    private static final String NAME_RETURN_CODE = "returnCode";
    public static final String TCAP_RESULT_CODE_SUCCESS = "0000";
    public static final String TCAP_RESULT_HTTP_ERR = "9001";
    public static final String TCAP_RESULT_PROTOCOL_ERR = "4000";
    public static final String TCAP_RESULT_UNKNOWN_ERR = "9000";

    @Override // com.felicanetworks.mfc.mfi.fws.json.IJsonMember
    public String getName() {
        return NAME;
    }

    public void setResultCode(String resultCode) throws JSONException {
        put(NAME_RESULT_CODE, resultCode);
    }

    public void setReturnCode(int returnCode) throws JSONException {
        put(NAME_RETURN_CODE, returnCode);
    }
}
