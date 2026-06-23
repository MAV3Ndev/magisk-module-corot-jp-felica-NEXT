package com.felicanetworks.mfm.main.model.info;

/* JADX INFO: loaded from: classes3.dex */
public class CommonInfo {
    public ContactInfo contact;
    public GeneralInfo generalInformation;

    public CommonInfo(GeneralInfo generalInformation, ContactInfo contact) {
        this.generalInformation = generalInformation;
        this.contact = contact;
    }

    public String toString() {
        return "CommonInformation{generalInformation=" + this.generalInformation + ", contact=" + this.contact + '}';
    }
}
