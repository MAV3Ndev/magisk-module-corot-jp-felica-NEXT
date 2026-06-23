package com.amazonaws.mobile.auth.core;

/* JADX INFO: loaded from: classes.dex */
public interface IdentityHandler {
    void handleError(Exception exc);

    void onIdentityId(String str);
}
