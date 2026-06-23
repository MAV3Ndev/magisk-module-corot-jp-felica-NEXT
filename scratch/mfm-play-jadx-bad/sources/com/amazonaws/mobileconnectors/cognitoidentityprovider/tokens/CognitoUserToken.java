package com.amazonaws.mobileconnectors.cognitoidentityprovider.tokens;

/* JADX INFO: loaded from: classes.dex */
public class CognitoUserToken {
    private final String token;

    public CognitoUserToken(String str) {
        this.token = str;
    }

    protected String getToken() {
        return this.token;
    }
}
