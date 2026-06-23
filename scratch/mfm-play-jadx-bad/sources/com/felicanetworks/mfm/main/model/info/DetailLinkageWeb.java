package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class DetailLinkageWeb {
    private String linkageWebName;
    private String linkageWebUrl;

    public DetailLinkageWeb(String linkageWebUrl, String linkageWebName) {
        this.linkageWebUrl = linkageWebUrl;
        this.linkageWebName = linkageWebName;
    }

    public void setLinkageWebUrl(String linkageWebUrl) {
        this.linkageWebUrl = linkageWebUrl;
    }

    public void setLinkageWebName(String linkageWebName) {
        this.linkageWebName = linkageWebName;
    }

    public String getLinkageWebUrl() {
        return this.linkageWebUrl;
    }

    public String getLinkageWebName() {
        return this.linkageWebName;
    }
}
