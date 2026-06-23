package com.felicanetworks.mfc.mfi.openidconnect;

import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public interface IAccountIssuerClient {

    public interface OnGetAuthCodeListener {
        void onError(String str, String str2, int i, String str3);

        void onGetAuthCode(String str, String str2, String str3);
    }

    String getIssuerName();

    void handleMessage(Message message);

    void requestAuthCode(String str, boolean z, OnGetAuthCodeListener onGetAuthCodeListener);
}
