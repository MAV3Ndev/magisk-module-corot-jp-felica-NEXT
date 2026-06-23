package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import android.content.Intent;

/* JADX INFO: loaded from: classes3.dex */
public interface SilentStartEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onRequestActivity(Intent intent);

    void onSuccess(User user);
}
