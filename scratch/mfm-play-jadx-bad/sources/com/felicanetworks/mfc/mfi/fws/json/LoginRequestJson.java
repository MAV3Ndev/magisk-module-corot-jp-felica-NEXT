package com.felicanetworks.mfc.mfi.fws.json;

import com.felicanetworks.mfc.mfi.SeInfo;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class LoginRequestJson extends RequestJson {
    private static final String NAME_ACCOUNT_ISSUER = "accountIssuer";
    private static final String NAME_AGREED_TOS_VER = "agreedTosVer";
    private static final String NAME_AUTH_CODE = "authCode";

    public void setAccountIssuer(String accountIssuer) throws JSONException {
        putPayloadMember(NAME_ACCOUNT_ISSUER, accountIssuer);
    }

    public void setAuthCode(String authCode) throws JSONException {
        putPayloadMember(NAME_AUTH_CODE, authCode);
    }

    public void setSeInfo(SeInfo seInfo, String seType) throws JSONException {
        SeInfoJson seInfoJson = new SeInfoJson();
        seInfoJson.setSeInfo(seInfo, seType);
        putPayloadMember(seInfoJson.getName(), seInfoJson);
    }

    public void setAgreedTosVer(int agreedTosVer) throws JSONException {
        putPayloadMember(NAME_AGREED_TOS_VER, agreedTosVer);
    }
}
