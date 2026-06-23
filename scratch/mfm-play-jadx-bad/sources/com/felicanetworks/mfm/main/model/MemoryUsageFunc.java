package com.felicanetworks.mfm.main.model;

import com.felicanetworks.mfm.main.model.info.MemoryUsageInfos;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;

/* JADX INFO: loaded from: classes3.dex */
public interface MemoryUsageFunc {

    public interface MemoryUsageListener {
        void onFailure(ModelErrorInfo error);

        void onSuccess(MemoryUsageInfos result);
    }

    void cancel();

    void orderInfoList(MemoryUsageListener listener);
}
