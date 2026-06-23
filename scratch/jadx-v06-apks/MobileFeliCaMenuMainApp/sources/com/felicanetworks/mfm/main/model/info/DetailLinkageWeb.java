package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class DetailLinkageWeb {
    private String linkageWebName;
    private String linkageWebUrl;

    public DetailLinkageWeb(String str, String str2) {
        this.linkageWebUrl = str;
        this.linkageWebName = str2;
    }

    public void setLinkageWebUrl(String str) {
        this.linkageWebUrl = str;
    }

    public void setLinkageWebName(String str) {
        this.linkageWebName = str;
    }

    public String getLinkageWebUrl() {
        return this.linkageWebUrl;
    }

    public String getLinkageWebName() {
        return this.linkageWebName;
    }
}
