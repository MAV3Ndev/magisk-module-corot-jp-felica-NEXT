package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class GeneralInfo {
    public static String SPECIAL_CARD_FACE_PREFIX = "SPECIAL";
    public String cardArtUrl;
    public String cardTypeName;
    public String contactName;
    public String specialCardArtUrl;

    public enum CARD_ART_PRIORITIZATION {
        SPECIAL,
        DEFAULT
    }

    public GeneralInfo(String cardTypeName, String cardArtUrl, String contactName, String specialCardArtUrl) {
        this.cardTypeName = cardTypeName;
        this.cardArtUrl = cardArtUrl;
        this.contactName = contactName;
        this.specialCardArtUrl = specialCardArtUrl;
    }

    public String toString() {
        return "GeneralInformation{cardTypeName='" + this.cardTypeName + "', cardArtUrl='" + this.cardArtUrl + "', contactName='" + this.contactName + "', specialCardArtUrl='" + this.specialCardArtUrl + "'}";
    }
}
