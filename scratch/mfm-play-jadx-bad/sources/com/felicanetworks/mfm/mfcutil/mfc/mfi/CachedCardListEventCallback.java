package com.felicanetworks.mfm.mfcutil.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public interface CachedCardListEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onSuccess(CachedCard[] cards);
}
