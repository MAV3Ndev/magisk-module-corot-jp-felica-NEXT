package com.felicanetworks.mfm.mfcutil.mfc.mfi;

/* JADX INFO: loaded from: classes.dex */
public interface InitializedEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int i, String str);

    void onSuccess();
}
