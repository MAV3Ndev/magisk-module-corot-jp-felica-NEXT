package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageInfo {
    public LinkageApplicationInfo linkageApplicationInfo;
    public LinkageKind linkageKind;
    public String linkageName;
    public String linkageWebsiteURL;

    public enum LinkageKind {
        APP("2"),
        WEB("3");

        public final String value;

        LinkageKind(String value) {
            this.value = value;
        }
    }

    public LinkageInfo(LinkageKind linkageKind, String linkageName, LinkageApplicationInfo linkageApplicationInfo, String linkageWebsiteURL) {
        this.linkageKind = linkageKind;
        this.linkageName = linkageName;
        this.linkageApplicationInfo = linkageApplicationInfo;
        this.linkageWebsiteURL = linkageWebsiteURL;
    }

    public String toString() {
        return "LinkageInfo{linkageKind=" + this.linkageKind + ", linkageName='" + this.linkageName + "', linkageApplicationInfo=" + this.linkageApplicationInfo + ", linkageWebsiteURL='" + this.linkageWebsiteURL + "'}";
    }
}
