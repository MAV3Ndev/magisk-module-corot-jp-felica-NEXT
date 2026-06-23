package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardAdditionalInfo;

/* JADX INFO: loaded from: classes.dex */
public interface CardAdditionalInfoListEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int i, String str);

    void onSuccess(CardAdditionalInfo[] cardAdditionalInfoArr);
}
