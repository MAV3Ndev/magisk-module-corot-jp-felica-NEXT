package com.felicanetworks.mfm.main.model.internal.main;

import com.felicanetworks.mfm.main.model.info.AdditionalInfo;
import com.felicanetworks.mfm.main.model.info.CommonInfo;
import com.felicanetworks.mfm.main.model.info.ContactInfo;
import com.felicanetworks.mfm.main.model.info.GeneralInfo;
import com.felicanetworks.mfm.main.model.info.LinkageApplicationInfo;
import com.felicanetworks.mfm.main.model.info.LinkageInfo;
import com.felicanetworks.mfm.main.model.info.MfiCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardAdditionalInfo;
import com.felicanetworks.mfm.main.model.info.MyCardInfo;
import com.felicanetworks.mfm.main.model.info.Service;
import com.felicanetworks.mfm.main.model.info.TransitInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CardFuncUtility {
    public static MyCardAdditionalInfo convertCardAdditionalDbToInfo(ModelContext modelContext, MfiCardAdditionalInfo mfiCardAdditionalInfo, TransitInfo transitInfo) {
        return new MyCardAdditionalInfo(mfiCardAdditionalInfo.cardID, modelContext, new AdditionalInfo(mfiCardAdditionalInfo.languageCode, convertLinkageDbToInfo(mfiCardAdditionalInfo.linkageInfoList), convertCommonDbToInfo(mfiCardAdditionalInfo.commonInformation), transitInfo));
    }

    private static List<LinkageInfo> convertLinkageDbToInfo(List<LinkageInfo> list) {
        ArrayList arrayList = new ArrayList();
        for (LinkageInfo linkageInfo : list) {
            LinkageInfo.LinkageKind linkageKind = LinkageInfo.LinkageKind.APP.equals(linkageInfo.linkageKind) ? LinkageInfo.LinkageKind.APP : LinkageInfo.LinkageKind.WEB;
            String str = linkageInfo.linkageName;
            String str2 = null;
            LinkageApplicationInfo linkageApplicationInfo = LinkageInfo.LinkageKind.APP.equals(linkageInfo.linkageKind) ? new LinkageApplicationInfo(linkageInfo.linkageApplicationInfo.appIconUrl, linkageInfo.linkageApplicationInfo.appIdentifiableInfo, linkageInfo.linkageApplicationInfo.appCallerInfo, linkageInfo.linkageApplicationInfo.appGetKind, linkageInfo.linkageApplicationInfo.appGetUrl) : null;
            if (!LinkageInfo.LinkageKind.APP.equals(linkageInfo.linkageKind)) {
                str2 = linkageInfo.linkageWebsiteURL;
            }
            arrayList.add(new LinkageInfo(linkageKind, str, linkageApplicationInfo, str2));
        }
        return arrayList;
    }

    private static CommonInfo convertCommonDbToInfo(CommonInfo commonInfo) {
        return new CommonInfo(new GeneralInfo(commonInfo.generalInformation.cardTypeName, commonInfo.generalInformation.cardArtUrl, commonInfo.generalInformation.contactName), commonInfo.contact == null ? null : new ContactInfo(commonInfo.contact.name, commonInfo.contact.phoneNumber, commonInfo.contact.url, commonInfo.contact.email));
    }

    public static void putServiceCardMap(Map<Service, List<MyCardInfo>> map, Service service, MyCardInfo myCardInfo) {
        List<MyCardInfo> list;
        if (!map.containsKey(service)) {
            map.put(service, new ArrayList());
        }
        if (myCardInfo == null || (list = map.get(service)) == null) {
            return;
        }
        list.add(myCardInfo);
    }
}
