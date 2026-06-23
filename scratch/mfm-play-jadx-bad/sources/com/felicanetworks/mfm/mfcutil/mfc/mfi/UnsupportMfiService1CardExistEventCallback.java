package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.mfi.LocalPartialCardInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface UnsupportMfiService1CardExistEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onSuccess(boolean exist, LocalPartialCardInfo cardInfo);
}
