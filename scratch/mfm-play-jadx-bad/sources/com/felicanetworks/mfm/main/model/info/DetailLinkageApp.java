package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class DetailLinkageApp {
    private int linkageAppGetType;
    private String linkageAppHash;
    private byte[] linkageAppIcon;
    private String linkageAppName;
    private String linkageAppPkg;
    private String linkageAppUrl;

    public DetailLinkageApp(String linkageAppPkg, String linkageAppHash, int linkageAppGetType, String linkageAppUrl, byte[] linkageAppIcon, String linkageAppName) {
        this.linkageAppPkg = linkageAppPkg;
        this.linkageAppHash = linkageAppHash;
        this.linkageAppGetType = linkageAppGetType;
        this.linkageAppUrl = linkageAppUrl;
        this.linkageAppIcon = linkageAppIcon;
        this.linkageAppName = linkageAppName;
    }

    public void setLinkageAppPkg(String linkageAppPkg) {
        this.linkageAppPkg = linkageAppPkg;
    }

    public void setLinkageAppHash(String linkageAppHash) {
        this.linkageAppHash = linkageAppHash;
    }

    public void setLinkageAppGetType(int linkageAppGetType) {
        this.linkageAppGetType = linkageAppGetType;
    }

    public void setLinkageAppUrl(String linkageAppUrl) {
        this.linkageAppUrl = linkageAppUrl;
    }

    public void setLinkageAppIcon(byte[] linkageAppIcon) {
        this.linkageAppIcon = linkageAppIcon;
    }

    public void setLinkageAppName(String linkageAppName) {
        this.linkageAppName = linkageAppName;
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
