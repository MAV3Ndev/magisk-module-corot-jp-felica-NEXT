package com.felicanetworks.mfc.mfi.openidconnect;

/* JADX INFO: loaded from: classes3.dex */
public interface IAccountIssuerClient {

    public interface OnGetAuthCodeListener {
        void onError(String issuer, String account, int type, String msg);

        void onGetAuthCode(String authCode, String issuer, String name);

        void onShowingAuthorizeScreen(String name);
    }

    String getIssuerName();

    void requestAuthCode(String accountName, boolean force, OnGetAuthCodeListener listener);
}
