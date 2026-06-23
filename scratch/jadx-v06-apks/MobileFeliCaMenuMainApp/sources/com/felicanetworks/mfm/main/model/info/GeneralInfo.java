package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class GeneralInfo {
    public String cardArtUrl;
    public String cardTypeName;
    public String contactName;

    public GeneralInfo(String str, String str2, String str3) {
        this.cardTypeName = str;
        this.cardArtUrl = str2;
        this.contactName = str3;
    }

    public String toString() {
        return "GeneralInformation{cardTypeName='" + this.cardTypeName + "', cardArtUrl='" + this.cardArtUrl + "', contactName='" + this.contactName + "'}";
    }
}
