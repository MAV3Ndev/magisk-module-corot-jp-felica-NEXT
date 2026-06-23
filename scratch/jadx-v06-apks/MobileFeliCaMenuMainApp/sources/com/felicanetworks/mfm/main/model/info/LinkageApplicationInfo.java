package com.felicanetworks.mfm.main.model.info;

import android.graphics.Bitmap;

/* JADX INFO: loaded from: classes.dex */
public class LinkageApplicationInfo {
    public String appCallerInfo;
    public String appGetKind;
    public String appGetUrl;
    public Bitmap appIcon;
    public String appIconUrl;
    public String appIdentifiableInfo;

    public LinkageApplicationInfo(String str, String str2, String str3, String str4, String str5) {
        this.appIconUrl = str;
        this.appIdentifiableInfo = str2;
        this.appCallerInfo = str3;
        this.appGetKind = str4;
        this.appGetUrl = str5;
        this.appIcon = null;
    }

    public LinkageApplicationInfo(Bitmap bitmap, String str, String str2, String str3, String str4) {
        this.appIconUrl = "";
        this.appIdentifiableInfo = str;
        this.appCallerInfo = str2;
        this.appGetKind = str3;
        this.appGetUrl = str4;
        this.appIcon = bitmap;
    }

    public String toString() {
        return "LinkageApplicationInfo{appIconUrl='" + this.appIconUrl + "', appIdentifiableInfo='" + this.appIdentifiableInfo + "', appCallerInfo='" + this.appCallerInfo + "', appGetKind='" + this.appGetKind + "', appGetUrl='" + this.appGetUrl + "', appIcon='" + this.appIcon + "'}";
    }
}
