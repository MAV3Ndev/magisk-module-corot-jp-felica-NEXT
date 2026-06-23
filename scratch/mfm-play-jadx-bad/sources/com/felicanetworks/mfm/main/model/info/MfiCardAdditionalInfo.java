package com.felicanetworks.mfm.main.model.info;

import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MfiCardAdditionalInfo {
    public String cardAdditionalInfoHashValue;
    public String cardID;
    public CommonInfo commonInformation;
    public String languageCode;
    public List<LinkageInfo> linkageInfoList;
    public TransitInfo transitInfo;

    public MfiCardAdditionalInfo(String cardID, String languageCode, List<LinkageInfo> linkageInfoList, CommonInfo commonInformation, String cardAdditionalInfoHashValue, TransitInfo transitInfo) {
        this.cardID = cardID;
        this.languageCode = languageCode;
        this.linkageInfoList = linkageInfoList;
        this.commonInformation = commonInformation;
        this.cardAdditionalInfoHashValue = cardAdditionalInfoHashValue;
        this.transitInfo = transitInfo;
    }

    public String toString() {
        return "MfiCardAdditionalInfo{cardID='" + this.cardID + "', languageCode='" + this.languageCode + "', linkageInfoList=" + this.linkageInfoList + ", commonInformation=" + this.commonInformation + ", cardAdditionalInfoHashValue='" + this.cardAdditionalInfoHashValue + "', transitInfo=" + this.transitInfo + '}';
    }
}
