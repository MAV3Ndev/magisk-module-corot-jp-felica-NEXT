package com.felicanetworks.mfm.main.model.info;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageApplicationInfo {
    public String appCallerInfo;
    public String appGetKind;
    public String appGetUrl;
    public Bitmap appIcon;
    public String appIconUrl;
    public String appIdentifiableInfo;

    public LinkageApplicationInfo(String appIconUrl, String appIdentifiableInfo, String appCallerInfo, String appGetKind, String appGetUrl) {
        this.appIconUrl = appIconUrl;
        this.appIdentifiableInfo = appIdentifiableInfo;
        this.appCallerInfo = appCallerInfo;
        this.appGetKind = appGetKind;
        this.appGetUrl = appGetUrl;
        this.appIcon = null;
    }

    public LinkageApplicationInfo(Bitmap appIcon, String appIdentifiableInfo, String appCallerInfo, String appGetKind, String appGetUrl) {
        this.appIconUrl = "";
        this.appIdentifiableInfo = appIdentifiableInfo;
        this.appCallerInfo = appCallerInfo;
        this.appGetKind = appGetKind;
        this.appGetUrl = appGetUrl;
        this.appIcon = appIcon;
    }

    public String toString() {
        return "LinkageApplicationInfo{appIconUrl='" + this.appIconUrl + "', appIdentifiableInfo='" + this.appIdentifiableInfo + "', appCallerInfo='" + this.appCallerInfo + "', appGetKind='" + this.appGetKind + "', appGetUrl='" + this.appGetUrl + "', appIcon='" + this.appIcon + "'}";
    }
}
