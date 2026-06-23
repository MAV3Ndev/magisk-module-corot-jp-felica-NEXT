package com.felicanetworks.mfc.mfi.fws.json;

import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class LoginTokenJws extends JwsObject {
    private static final String NAME_ACCOUNT_ID = "accountId";
    private static final String NAME_LOGIN_TOKEN_ID = "loginTokenId";
    private final JSONObject mJson;

    public LoginTokenJws(String json) throws JSONException {
        super(json);
        this.mJson = new JSONObject(getPayload());
    }

    public void checkMembers() throws JSONException {
        JsonUtil.checkString(this.mJson, NAME_LOGIN_TOKEN_ID, true, 0);
        JsonUtil.checkString(this.mJson, NAME_ACCOUNT_ID, true, 0);
    }

    public String optLoginTokenId() {
        return this.mJson.optString(NAME_LOGIN_TOKEN_ID, null);
    }

    public String optAccountId() {
        return this.mJson.optString(NAME_ACCOUNT_ID, null);
    }
}
