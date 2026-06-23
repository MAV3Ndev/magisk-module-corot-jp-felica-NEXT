package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.mfi.CompleteCardInfo;

/* JADX INFO: loaded from: classes3.dex */
public class CachedCardInfo extends CardInfo {
    private final long mCachedTime;

    public CachedCardInfo(String cid, String serviceId, String walletAppId, int state, int position, int task, boolean reissuePossibility, String serviceType, String additionalInfoHash, String cardCategory, long cachedTime, String cardType) {
        super(cid, serviceId, walletAppId, state, position, task, reissuePossibility, serviceType, additionalInfoHash, cardCategory, cardType);
        this.mCachedTime = cachedTime;
    }

    public CachedCardInfo(CompleteCardInfo.Cache cache, long cachedTime) {
        super(cache.cid, cache.serviceId, cache.walletAppId, cache.state, cache.position, cache.task, cache.reissuePossibility, cache.serviceType, cache.additionalInfoHash, cache.cardCategory, cache.cardType);
        this.mCachedTime = cachedTime;
    }

    public long getCachedTime() {
        return this.mCachedTime;
    }
}
