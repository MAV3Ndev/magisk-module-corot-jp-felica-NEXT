package com.felicanetworks.mfc.mfi;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public interface SilentStartEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfc.mfi.BaseMfiEventCallback
    void onError(int i, String str);

    void onRequestActivity(Intent intent);

    void onSuccess(User user);
}
