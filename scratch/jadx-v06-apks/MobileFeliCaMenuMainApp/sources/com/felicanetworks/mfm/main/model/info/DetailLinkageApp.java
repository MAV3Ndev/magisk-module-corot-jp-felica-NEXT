package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class DetailLinkageApp {
    private int linkageAppGetType;
    private String linkageAppHash;
    private byte[] linkageAppIcon;
    private String linkageAppName;
    private String linkageAppPkg;
    private String linkageAppUrl;

    public DetailLinkageApp(String str, String str2, int i, String str3, byte[] bArr, String str4) {
        this.linkageAppPkg = str;
        this.linkageAppHash = str2;
        this.linkageAppGetType = i;
        this.linkageAppUrl = str3;
        this.linkageAppIcon = bArr;
        this.linkageAppName = str4;
    }

    public void setLinkageAppPkg(String str) {
        this.linkageAppPkg = str;
    }

    public void setLinkageAppHash(String str) {
        this.linkageAppHash = str;
    }

    public void setLinkageAppGetType(int i) {
        this.linkageAppGetType = i;
    }

    public void setLinkageAppUrl(String str) {
        this.linkageAppUrl = str;
    }

    public void setLinkageAppIcon(byte[] bArr) {
        this.linkageAppIcon = bArr;
    }

    public void setLinkageAppName(String str) {
        this.linkageAppName = str;
    }

    public String getLinkageAppPkg() {
        return this.linkageAppPkg;
    }

    public String getLinkageAppHash() {
        return this.linkageAppHash;
    }

    public int getLinkageAppGetType() {
        return this.linkageAppGetType;
    }

    public String getLinkageAppUrl() {
        return this.linkageAppUrl;
    }

    public byte[] getLinkageAppIcon() {
        return this.linkageAppIcon;
    }

    public String getLinkageAppName() {
        return this.linkageAppName;
    }
}
