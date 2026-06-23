package com.felicanetworks.mfm.main.model.info;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class TransitPassInfo {
    private String additionalInformation;
    private String category;
    private String commuterPassName;
    private String issuerName;
    private List<OptionalInfo> optionalInfoList;
    private Section1 section1;
    private Section2 section2;
    private TermOfValidity termOfValidity;
    private String transitPassName;
    private String via;

    public TransitPassInfo(String transitPassName, String category, String commuterPassName, Section1 section1, Section2 section2, String additionalInformation, String via, String issuerName, TermOfValidity termOfValidity, List<OptionalInfo> optionalInfoList) {
        this.transitPassName = transitPassName;
        this.category = category;
        this.commuterPassName = commuterPassName;
        this.section1 = section1;
        this.section2 = section2;
        this.additionalInformation = additionalInformation;
        this.via = via;
        this.issuerName = issuerName;
        this.termOfValidity = termOfValidity;
        this.optionalInfoList = optionalInfoList;
    }

    public String toString() {
        return "TransitPassInfo{transitPassName=" + this.transitPassName + ", category=" + this.category + ", commuterPassName=" + this.commuterPassName + ", section2=" + this.section2 + ", additionalInformation=" + this.additionalInformation + ", via=" + this.via + ", issuerName=" + this.issuerName + ", termOfValidity=" + this.termOfValidity + ", optionalInfoList=" + this.optionalInfoList + '}';
    }

    public String getTransitPassName() {
        return this.transitPassName;
    }

    public String getCategory() {
        return this.category;
    }

    public String getCommuterPassName() {
        return this.commuterPassName;
    }

    public Section1 getSection1() {
        return this.section1;
    }

    public Section2 getSection2() {
        return this.section2;
    }

    public String getAdditionalInformation() {
        return this.additionalInformation;
    }

    public String getVia() {
        return this.via;
    }

    public String getIssuerName() {
        return this.issuerName;
    }

    public TermOfValidity getTermOfValidity() {
        return this.termOfValidity;
    }

    public List<OptionalInfo> getOptionalInfoList() {
        return this.optionalInfoList;
    }
}
