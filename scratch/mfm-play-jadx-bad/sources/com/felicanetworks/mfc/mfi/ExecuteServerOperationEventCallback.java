package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback;

/* JADX INFO: loaded from: classes3.dex */
public interface ExecuteServerOperationEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onRetryRequired(int type, String msg);

    void onSuccess();
}
