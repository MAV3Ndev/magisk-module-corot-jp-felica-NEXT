package com.felicanetworks.mfc.mfi;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NotifyEventTargetAppInfo {
    private final String mClassName;
    private final boolean mGetCardListUnlimited;
    private final List<String> mTargetServiceIdList;
    private final String mWalletAppCallerInfo;
    private final String mWalletAppIdentifiableInfo;

    public NotifyEventTargetAppInfo(String str, String str2, String str3, boolean z, List<String> list) {
        this.mWalletAppIdentifiableInfo = str;
        this.mClassName = str2;
        this.mWalletAppCallerInfo = str3;
        this.mGetCardListUnlimited = z;
        this.mTargetServiceIdList = list;
    }

    public String getWalletAppIdentifiableInfo() {
        return this.mWalletAppIdentifiableInfo;
    }

    public String getClassName() {
        return this.mClassName;
    }

    public String getWalletAppCallerInfo() {
        return this.mWalletAppCallerInfo;
    }

    public boolean getGetCardListUnlimited() {
        return this.mGetCardListUnlimited;
    }

    public List<String> getTargetServiceIdList() {
        return this.mTargetServiceIdList;
    }
}
