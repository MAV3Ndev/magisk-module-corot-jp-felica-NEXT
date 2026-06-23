package com.felicanetworks.mfm.main.model.info;

import com.felicanetworks.mfm.main.model.info.LinkageInfo;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AdditionalInfo {
    public CommonInfo commonInformation;
    public String languageCode;
    public List<LinkageInfo> linkageInfoList;
    public TransitInfo transitInfo;

    public AdditionalInfo(String str, List<LinkageInfo> list, CommonInfo commonInfo, TransitInfo transitInfo) {
        this.languageCode = str;
        this.linkageInfoList = list;
        this.commonInformation = commonInfo;
        this.transitInfo = transitInfo;
    }

    public List<LinkageInfo> getAppLinkageInfoList() {
        ArrayList arrayList = new ArrayList();
        for (LinkageInfo linkageInfo : this.linkageInfoList) {
            if (linkageInfo.linkageKind == LinkageInfo.LinkageKind.APP) {
                arrayList.add(linkageInfo);
            }
        }
        return arrayList;
    }

    public List<LinkageInfo> getWebLinkageInfoList() {
        ArrayList arrayList = new ArrayList();
        for (LinkageInfo linkageInfo : this.linkageInfoList) {
            if (linkageInfo.linkageKind == LinkageInfo.LinkageKind.WEB) {
                arrayList.add(linkageInfo);
            }
        }
        return arrayList;
    }
}
