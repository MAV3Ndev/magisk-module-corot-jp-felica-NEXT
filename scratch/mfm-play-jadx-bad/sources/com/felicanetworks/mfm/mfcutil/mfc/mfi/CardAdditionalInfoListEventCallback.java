package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardAdditionalInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface CardAdditionalInfoListEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onSuccess(CardAdditionalInfo[] cards);
}
