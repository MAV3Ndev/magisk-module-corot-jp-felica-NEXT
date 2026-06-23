package com.felicanetworks.mfc.mfi;

/* JADX INFO: loaded from: classes3.dex */
public class WalletAppIdentifiableInfo {
    private static WalletAppIdentifiableInfo sInstance;
    private String mPackageName = null;

    private WalletAppIdentifiableInfo() {
    }

    public static WalletAppIdentifiableInfo getInstance() {
        if (sInstance == null) {
            sInstance = new WalletAppIdentifiableInfo();
        }
        return sInstance;
    }

    public void discard() {
        this.mPackageName = null;
    }

    public String getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(String packageName) {
        this.mPackageName = packageName;
    }

    public boolean hasPackageName() {
        return this.mPackageName != null;
    }
}
