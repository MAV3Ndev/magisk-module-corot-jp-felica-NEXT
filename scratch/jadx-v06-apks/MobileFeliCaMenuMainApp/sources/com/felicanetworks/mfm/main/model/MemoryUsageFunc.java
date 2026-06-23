package com.felicanetworks.mfm.main.model;

import com.felicanetworks.mfm.main.model.info.MemoryUsageInfos;
import com.felicanetworks.mfm.main.model.info.ModelErrorInfo;

/* JADX INFO: loaded from: classes.dex */
public interface MemoryUsageFunc {

    public interface MemoryUsageListener {
        void onFailure(ModelErrorInfo modelErrorInfo);

        void onSuccess(MemoryUsageInfos memoryUsageInfos);
    }

    void cancel();

    void orderInfoList(MemoryUsageListener memoryUsageListener);
}
