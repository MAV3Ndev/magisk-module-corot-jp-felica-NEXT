package com.felicanetworks.mfm.main.model.info;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MfiCardAdditionalInfo {
    public String cardAdditionalInfoHashValue;
    public String cardID;
    public CommonInfo commonInformation;
    public String languageCode;
    public List<LinkageInfo> linkageInfoList;
    public TransitInfo transitInfo;

    public MfiCardAdditionalInfo(String str, String str2, List<LinkageInfo> list, CommonInfo commonInfo, TransitInfo transitInfo) {
        this.cardID = str;
        this.languageCode = str2;
        this.linkageInfoList = list;
        this.commonInformation = commonInfo;
        this.transitInfo = transitInfo;
    }

    public MfiCardAdditionalInfo(String str, String str2, List<LinkageInfo> list, CommonInfo commonInfo, String str3, TransitInfo transitInfo) {
        this.cardID = str;
        this.languageCode = str2;
        this.linkageInfoList = list;
        this.commonInformation = commonInfo;
        this.cardAdditionalInfoHashValue = str3;
        this.transitInfo = transitInfo;
    }

    public String toString() {
        return "MfiCardAdditionalInfo{cardID='" + this.cardID + "', languageCode='" + this.languageCode + "', linkageInfoList=" + this.linkageInfoList + ", commonInformation=" + this.commonInformation + ", cardAdditionalInfoHashValue='" + this.cardAdditionalInfoHashValue + "', transitInfo=" + this.transitInfo + '}';
    }
}
