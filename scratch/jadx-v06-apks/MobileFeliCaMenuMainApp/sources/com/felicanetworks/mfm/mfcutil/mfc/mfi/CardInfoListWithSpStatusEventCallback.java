package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;

/* JADX INFO: loaded from: classes.dex */
public interface CardInfoListWithSpStatusEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int i, String str);

    void onSuccess(CardInfoWithSpStatus[] cardInfoWithSpStatusArr);
}
