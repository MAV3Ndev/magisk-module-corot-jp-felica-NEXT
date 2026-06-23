package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes.dex */
public class CommonInfo {
    public ContactInfo contact;
    public GeneralInfo generalInformation;

    public CommonInfo(GeneralInfo generalInfo, ContactInfo contactInfo) {
        this.generalInformation = generalInfo;
        this.contact = contactInfo;
    }

    public String toString() {
        return "CommonInformation{generalInformation=" + this.generalInformation + ", contact=" + this.contact + '}';
    }
}
