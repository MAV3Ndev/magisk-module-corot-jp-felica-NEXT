package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import org.json.JSONException;

/* JADX INFO: loaded from: classes.dex */
public class LoginRequestJson extends RequestJson {
    private static final String NAME_ACCOUNT_ISSUER = "accountIssuer";
    private static final String NAME_AGREED_TOS_VER = "agreedTosVer";
    private static final String NAME_AUTH_CODE = "authCode";

    public void setAccountIssuer(String str) throws JSONException {
        putPayloadMember(NAME_ACCOUNT_ISSUER, str);
    }

    public void setAuthCode(String str) throws JSONException {
        putPayloadMember(NAME_AUTH_CODE, str);
    }

    public void setSeInfo(SeInfo seInfo, String str) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, str);
        putPayloadMember(seInfoJson.getName(), seInfoJson);
    }

    public void setAgreedTosVer(int i) throws JSONException {
        putPayloadMember(NAME_AGREED_TOS_VER, i);
    }
}
