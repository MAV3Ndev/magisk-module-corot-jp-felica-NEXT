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
import com.felicanetworks.mfm.main.model.internal.main.OrderImageWorker;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CardFuncUtility {
    public static MyCardAdditionalInfo convertCardAdditionalDbToInfo(ModelContext context, MfiCardAdditionalInfo dbInfo, TransitInfo transitInfoo) {
        return new MyCardAdditionalInfo(dbInfo.cardID, context, new AdditionalInfo(dbInfo.languageCode, convertLinkageDbToInfo(dbInfo.linkageInfoList), convertCommonDbToInfo(dbInfo.commonInformation), transitInfoo), dbInfo.cardAdditionalInfoHashValue);
    }

    private static List<LinkageInfo> convertLinkageDbToInfo(List<LinkageInfo> dbList) {
        LinkageInfo.LinkageKind linkageKind;
        ArrayList arrayList = new ArrayList();
        for (LinkageInfo linkageInfo : dbList) {
            if (LinkageInfo.LinkageKind.APP.equals(linkageInfo.linkageKind)) {
                linkageKind = LinkageInfo.LinkageKind.APP;
            } else {
                linkageKind = LinkageInfo.LinkageKind.WEB;
            }
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

    private static CommonInfo convertCommonDbToInfo(CommonInfo db) {
        return new CommonInfo(new GeneralInfo(db.generalInformation.cardTypeName, db.generalInformation.cardArtUrl, db.generalInformation.contactName, db.generalInformation.specialCardArtUrl), db.contact == null ? null : new ContactInfo(db.contact.name, db.contact.phoneNumber, db.contact.url, db.contact.email));
    }

    public static void putServiceCardMap(Map<Service, List<MyCardInfo>> map, Service service, MyCardInfo cardInfo) {
        List<MyCardInfo> list;
        if (!map.containsKey(service)) {
            map.put(service, new ArrayList());
        }
        if (cardInfo == null || (list = map.get(service)) == null) {
            return;
        }
        list.add(cardInfo);
    }

    public static List<OrderImageWorker.Request> putCardImageRequestsArrayList(String cardId, Map<Integer, String> urls) {
        String str;
        ArrayList arrayList = new ArrayList();
        for (Map.Entry<Integer, String> entry : urls.entrySet()) {
            if (!entry.getKey().equals(Integer.valueOf(GeneralInfo.CARD_ART_PRIORITIZATION.SPECIAL.ordinal()))) {
                str = "";
            } else {
                str = GeneralInfo.SPECIAL_CARD_FACE_PREFIX;
            }
            arrayList.add(new OrderImageWorker.Request(cardId, str, entry.getValue()));
        }
        return arrayList;
    }
}
