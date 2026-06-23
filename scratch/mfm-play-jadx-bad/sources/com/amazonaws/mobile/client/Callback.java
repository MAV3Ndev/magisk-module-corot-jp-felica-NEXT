package com.amazonaws.mobile.client;

/* JADX INFO: loaded from: classes.dex */
public interface Callback<R> {
    void onError(Exception exc);

    void onResult(R r);
}
