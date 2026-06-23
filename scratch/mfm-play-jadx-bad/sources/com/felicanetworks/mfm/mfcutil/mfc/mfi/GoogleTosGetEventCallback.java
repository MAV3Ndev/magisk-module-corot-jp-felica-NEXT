package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import android.content.Intent;

/* JADX INFO: loaded from: classes3.dex */
public interface GoogleTosGetEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onSuccess(Intent intent);
}
