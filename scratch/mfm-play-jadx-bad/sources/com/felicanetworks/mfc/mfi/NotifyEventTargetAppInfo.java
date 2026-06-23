package com.felicanetworks.mfc.mfi;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyEventTargetAppInfo {
    private final String mClassName;
    private final boolean mGetCardListUnlimited;
    private final List<String> mTargetServiceIdList;
    private final String mWalletAppCallerInfo;
    private final String mWalletAppIdentifiableInfo;

    public NotifyEventTargetAppInfo(String walletAppIdentifiableInfo, String className, String walletAppCallerInfo, boolean getCardListUnlimited, List<String> targetServiceIdList) {
        this.mWalletAppIdentifiableInfo = walletAppIdentifiableInfo;
        this.mClassName = className;
        this.mWalletAppCallerInfo = walletAppCallerInfo;
        this.mGetCardListUnlimited = getCardListUnlimited;
        this.mTargetServiceIdList = targetServiceIdList;
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
