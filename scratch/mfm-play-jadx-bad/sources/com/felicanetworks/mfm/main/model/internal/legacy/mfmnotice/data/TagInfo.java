package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

/* JADX INFO: loaded from: classes3.dex */
public class TagInfo {
    public String accept;
    public String api_level;
    public String app_version;
    public String distribution_group;
    public String issuer;

    public TagInfo(String app_version, String accept, String api_level, String issuer, String distribution_group) {
        this.app_version = app_version;
        this.accept = accept;
        this.api_level = api_level;
        this.issuer = issuer;
        this.distribution_group = distribution_group;
    }
}
