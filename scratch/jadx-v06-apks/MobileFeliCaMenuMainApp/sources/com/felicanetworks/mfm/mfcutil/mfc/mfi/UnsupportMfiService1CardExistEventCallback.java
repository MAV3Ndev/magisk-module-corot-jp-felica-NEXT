package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.mfi.LocalPartialCardInfo;

/* JADX INFO: loaded from: classes.dex */
public interface UnsupportMfiService1CardExistEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int i, String str);

    void onSuccess(boolean z, LocalPartialCardInfo localPartialCardInfo);
}
