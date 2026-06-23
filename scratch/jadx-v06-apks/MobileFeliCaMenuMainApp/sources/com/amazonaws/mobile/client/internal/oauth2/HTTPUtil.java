package com.amazonaws.mobile.client.internal.oauth2;

import com.amazonaws.mobile.client.internal.oauth2.OAuth2Constants;
import com.amazonaws.util.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: compiled from: OAuth2Client.java */
/* JADX INFO: loaded from: classes.dex */
class HTTPUtil {
    HTTPUtil() {
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: com.amazonaws.mobileconnectors.cognitoauth.exceptions.AuthClientException */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x012b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.String httpPost(java.net.URL r5, java.util.Map<java.lang.String, java.lang.String> r6, java.util.Map<java.lang.String, java.lang.String> r7, java.lang.String r8) throws java.lang.Exception {
        /*
            Method dump skipped, instruction units count: 311
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.amazonaws.mobile.client.internal.oauth2.HTTPUtil.httpPost(java.net.URL, java.util.Map, java.util.Map, java.lang.String):java.lang.String");
    }

    public static OAuth2Tokens parseHttpResponse(String str) throws JSONException {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Invalid (null) response from Amazon Cognito Auth endpoint");
        }
        JSONObject jSONObject = new JSONObject(str);
        String strOptString = jSONObject.optString(OAuth2Constants.TokenResponseFields.ERROR.toString(), null);
        String strOptString2 = jSONObject.optString(OAuth2Constants.TokenResponseFields.ERROR_DESCRIPTION.toString(), null);
        String strOptString3 = jSONObject.optString(OAuth2Constants.TokenResponseFields.ERROR_URI.toString(), null);
        if (strOptString != null) {
            throw new OAuth2Exception("Failed to exchange code for tokens", strOptString, strOptString2, strOptString3);
        }
        String strOptString4 = jSONObject.optString(OAuth2Constants.TokenResponseFields.EXPIRES_IN.toString());
        return new OAuth2Tokens(jSONObject.getString(OAuth2Constants.TokenResponseFields.ACCESS_TOKEN.toString()), jSONObject.optString(OAuth2Constants.TokenResponseFields.ID_TOKEN.toString(), null), jSONObject.optString(OAuth2Constants.TokenResponseFields.REFRESH_TOKEN.toString(), null), jSONObject.getString(OAuth2Constants.TokenResponseFields.TOKEN_TYPE.toString()), !StringUtils.isBlank(strOptString4) ? Long.valueOf(Long.parseLong(strOptString4)) : null, Long.valueOf(System.currentTimeMillis() / 1000), jSONObject.optString(OAuth2Constants.SCOPES, null));
    }
}
