package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes.dex */
public interface CardEnableEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfc.mfi.BaseMfiEventCallback
    void onError(int i, String str);

    void onSuccess(Card card, Card card2);
}
