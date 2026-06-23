package com.felicanetworks.mfm.mfcutil.mfc.mfi;

import com.felicanetworks.mfc.mfi.MfiTosData;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface MfiTosDataGetEventCallback extends BaseMfiEventCallback {
    @Override // com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback
    void onError(int type, String msg);

    void onSuccess(Map<String, MfiTosData> mfiTosDataMap, boolean isMfiTosAgreed);
}
