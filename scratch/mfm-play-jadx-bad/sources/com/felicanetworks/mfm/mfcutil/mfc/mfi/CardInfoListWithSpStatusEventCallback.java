package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.mfi.CardInfoWithSpStatus;

/* JADX INFO: loaded from: classes3.dex */
public interface CardInfoListWithSpStatusEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onSuccess(CardInfoWithSpStatus[] cardInfoWithSpStatus);
}
