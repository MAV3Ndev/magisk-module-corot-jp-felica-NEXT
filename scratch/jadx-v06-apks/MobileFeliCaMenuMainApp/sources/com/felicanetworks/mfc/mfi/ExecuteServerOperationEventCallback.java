package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback;

/* JADX INFO: loaded from: classes.dex */
public interface ExecuteServerOperationEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int i, String str);

    void onRetryRequired(int i, String str);

    void onSuccess();
}
