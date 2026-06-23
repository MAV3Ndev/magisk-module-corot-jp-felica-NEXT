package com.amazonaws.async;

/* JADX INFO: loaded from: classes.dex */
public interface Callback<R> {
    void onError(Exception exc);

    void onResult(R r);
}
