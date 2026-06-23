package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes.dex */
public class CachedCardInfo extends CardInfo {
    private final long mCachedTime;

    public CachedCardInfo(String str, String str2, String str3, int i, int i2, int i3, boolean z, String str4, String str5, String str6, long j) {
        super(str, str2, str3, i, i2, i3, z, str4, str5, str6);
        this.mCachedTime = j;
    }

    public long getCachedTime() {
        return this.mCachedTime;
    }
}
