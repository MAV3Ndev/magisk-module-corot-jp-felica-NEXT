package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class LinkageInfo {
    public LinkageApplicationInfo linkageApplicationInfo;
    public LinkageKind linkageKind;
    public String linkageName;
    public String linkageWebsiteURL;

    public enum LinkageKind {
        APP("2"),
        WEB("3");

        public final String value;

        LinkageKind(String str) {
            this.value = str;
        }
    }

    public LinkageInfo(LinkageKind linkageKind, String str, LinkageApplicationInfo linkageApplicationInfo, String str2) {
        this.linkageKind = linkageKind;
        this.linkageName = str;
        this.linkageApplicationInfo = linkageApplicationInfo;
        this.linkageWebsiteURL = str2;
    }

    public String toString() {
        return "LinkageInfo{linkageKind=" + this.linkageKind + ", linkageName='" + this.linkageName + "', linkageApplicationInfo=" + this.linkageApplicationInfo + ", linkageWebsiteURL='" + this.linkageWebsiteURL + "'}";
    }
}
