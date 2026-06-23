package com.felicanetworks.mfm.main.model.internal.main.net;

import android.os.Build;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.info.DetailLinkageApp;
import com.felicanetworks.mfm.main.model.info.DetailLinkageWeb;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.net.BinaryBlock;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public class ServiceProtocol extends Protocol {
    private static final int SERVICE_VERSION_WITH_ADDITIONAL = 10000;
    private int fixDataLength;

    public static class Parameter {
        public static final String INTERFACE_VERSION = "03";
        public String apiCodeBeta;
        public String apiCodeVersion;
        public String idm;
        public List<ServiceData> itemList;
        public String plKind = (String) Sg.getValue(Sg.Key.SETTING_PLATFORM_TYPE);
        public String pfVersion = Build.VERSION.RELEASE;
        public String isCode = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
        public String appId = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
        public String appVersion = Information.simpleVersionName();
        public String languageCode = Locale.getDefault().getLanguage();
        public String countryCode = Locale.getDefault().getCountry();

        public static class ServiceData implements Comparable<ServiceData> {
            public String serviceId;
            public String serviceVersion;

            public ServiceData(String str, String str2) {
                this.serviceId = str;
                this.serviceVersion = str2;
            }

            @Override // java.lang.Comparable
            public int compareTo(ServiceData serviceData) {
                int iCompareTo = this.serviceId.compareTo(serviceData.serviceId);
                if (iCompareTo > 0) {
                    return 1;
                }
                return iCompareTo < 0 ? -1 : 0;
            }

            public String toString() {
                return "ServiceData{serviceId='" + this.serviceId + "', serviceVersion='" + this.serviceVersion + "'}";
            }
        }

        public Parameter(String str, String str2, String str3, List<ServiceData> list) {
            this.idm = str;
            this.itemList = list;
            this.apiCodeBeta = str2;
            this.apiCodeVersion = str3;
        }

        public String toString() {
            return "Parameter{plKind='" + this.plKind + "', pfVersion='" + this.pfVersion + "', isCode='" + this.isCode + "', languageCode='" + this.languageCode + "', countryCode='" + this.countryCode + "', appId='" + this.appId + "', appVersion='" + this.appVersion + "', idm='" + this.idm + "', apiCodeBeta='" + this.apiCodeBeta + "', apiCodeVersion='" + this.apiCodeVersion + "', itemList=" + this.itemList + '}';
        }
    }

    public static class Result {
        static final String APP_KIND_BROWSER = "1";
        static final String APP_KIND_MARKET = "2";
        static final String DETAIL_FALSE = "0";
        static final String DETAIL_TRUE = "1";
        static final String SERVICE_KIND_APP = "2";
        static final String SERVICE_KIND_INFOSITE = "1";
        static final String SERVICE_KIND_SRVSITE = "3";
        public List<ServiceItem> result;

        private Result(List<ServiceItem> list) {
            this.result = list;
        }

        public String toString() {
            return "Result{result=" + this.result + '}';
        }

        public static class ServiceItem implements ServiceIdPolicy.ServiceIdHolder {
            public final String applicationKind;
            public final String applicationUrl;
            public final String cardCategory;
            public final byte[] cardFaceImage;
            public final String color;
            public final String contactEmail;
            public final String contactName;
            public final String contactNumber;
            public final String contactUrl;
            public final String cooperativeKind;
            public final String detailImagePresence;
            public final String displayPriority;
            public final byte[] extensionIconData1;
            public final byte[] extensionIconData2;
            public final String hashValue;
            public final byte[] iconData;
            public final List<DetailLinkageApp> linkageAppInfoList = new ArrayList();
            public final List<DetailLinkageWeb> linkageWebInfoList = new ArrayList();
            public final String packageName;
            public final String serviceId;
            public final String serviceName;
            public final String serviceProviderName;
            public final String serviceType;
            public final String serviceVersion;
            public final String webUrl;

            public ServiceItem(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, String str11, String str12, byte[] bArr, byte[] bArr2, byte[] bArr3, String str13, String str14, String str15, byte[] bArr4, String str16, String str17, String str18, String str19, List<DetailLinkageApp> list, List<DetailLinkageWeb> list2) {
                this.serviceId = str;
                this.serviceVersion = str2;
                this.displayPriority = str3;
                this.color = str4;
                this.serviceName = str5;
                this.serviceProviderName = str6;
                this.cooperativeKind = str7;
                this.packageName = str8;
                this.hashValue = str9;
                this.applicationKind = str10;
                this.applicationUrl = str11;
                this.webUrl = str12;
                this.iconData = (byte[]) bArr.clone();
                this.extensionIconData1 = (byte[]) bArr2.clone();
                this.extensionIconData2 = (byte[]) bArr3.clone();
                this.serviceType = str13;
                this.cardCategory = str14;
                this.detailImagePresence = str15;
                this.cardFaceImage = bArr4;
                this.contactName = str16;
                this.contactNumber = str17;
                this.contactUrl = str18;
                this.contactEmail = str19;
                this.linkageAppInfoList.addAll(list);
                this.linkageWebInfoList.addAll(list2);
            }

            public String toString() {
                return "ServiceItem{serviceId='" + this.serviceId + "', serviceVersion='" + this.serviceVersion + "', color='" + this.color + "', serviceName='" + this.serviceName + "', serviceProviderName='" + this.serviceProviderName + "', cooperativeKind='" + this.cooperativeKind + "', packageName='" + this.packageName + "', hashValue='" + this.hashValue + "', applicationKind='" + this.applicationKind + "', applicationUrl='" + this.applicationUrl + "', webUrl='" + this.webUrl + "', displayPriority='" + this.displayPriority + "', iconData=[length]" + this.iconData.length + ", extensionIconData1=[length]" + this.extensionIconData1.length + ", extensionIconData2=[length]" + this.extensionIconData2.length + ", serviceType='" + this.serviceType + "', cardCategory='" + this.cardCategory + "', detailImagePresence='" + this.detailImagePresence + "', cardFaceImage=[length]" + this.cardFaceImage.length + ", contactName='" + this.contactName + "', contactNumber='" + this.contactNumber + "', contactUrl='" + this.contactUrl + "', contactEmail='" + this.contactEmail + "', linkageAppInfoList=[length]" + this.linkageAppInfoList.size() + ", linkageWebInfoList=[length]" + this.linkageWebInfoList.size() + '}';
            }

            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
            public String sid() {
                return this.serviceId;
            }
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_SIM_SID_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_SIM_SID_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_SIM_SID_READ_TIMEOUT)).intValue();
            parameter.pfVersion = URLEncoder.encode(parameter.pfVersion, StringUtil.UTF_8);
            DataCheckerUtil.checkLessEqualLength(parameter.appVersion.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(parameter.appVersion);
            Collections.sort(parameter.itemList);
            StringBuilder sb = new StringBuilder();
            sb.append(parameter.itemList.size());
            sb.append(";");
            for (int i = 0; i < parameter.itemList.size(); i++) {
                sb.append(parameter.itemList.get(i).serviceId);
                sb.append(",");
                sb.append(parameter.itemList.get(i).serviceVersion);
                sb.append(";");
            }
            try {
                DataCheckerUtil.checkAlphaNumberFormat(parameter.languageCode);
                DataCheckerUtil.checkAlphaNumberFormat(parameter.countryCode);
                DataCheckerUtil.checkLessEqualLength(parameter.languageCode.length(), 3);
                DataCheckerUtil.checkLessEqualLength(parameter.countryCode.length(), 3);
            } catch (DataCheckerException unused) {
                parameter.languageCode = "ja";
                parameter.countryCode = "JP";
            }
            byte[] bytes = ("pt=" + parameter.plKind + "&pv=" + parameter.pfVersion + "&i=" + parameter.isCode + "&l=" + parameter.languageCode + "&c=" + parameter.countryCode + "&ai=" + parameter.appId + "&av=" + parameter.appVersion + "&idm=" + parameter.idm + "&acd=" + parameter.apiCodeBeta + "&acv=" + parameter.apiCodeVersion + "&data=" + sb.toString() + "&ifv=03").getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            map.put("content-length", Integer.toString(bytes.length));
            map.put("content-type", "application/x-www-form-urlencoded");
            return new NetworkExpert.Request(str, "POST", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "felica corresponding service information request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:109:0x03a3  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x03bd  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.net.ServiceProtocol.Result parse(com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert.Response r49) throws com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException {
        /*
            Method dump skipped, instruction units count: 979
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.net.ServiceProtocol.parse(com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert$Response):com.felicanetworks.mfm.main.model.internal.main.net.ServiceProtocol$Result");
    }

    protected ServiceProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
        this.fixDataLength = 0;
    }

    private List<DetailLinkageApp> parseLinkageAppInfoList(BinaryBlock binaryBlock) throws DataCheckerException, UnsupportedEncodingException {
        BinaryBlock binaryBlock2 = binaryBlock;
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int digitValue = binaryBlock2.getDigitValue(1);
        if (digitValue > 0) {
            int i2 = 0;
            while (i2 < digitValue) {
                int digitValue2 = binaryBlock2.getDigitValue(4);
                String stringValue = binaryBlock2.getStringValue(digitValue2);
                DataCheckerUtil.checkLessEqualLength(stringValue.length(), 1024);
                DataCheckerUtil.checkAlphaSignCommaFormat(stringValue);
                String stringValue2 = binaryBlock2.getStringValue(64);
                DataCheckerUtil.checkEqualLength(stringValue2.length(), 64);
                DataCheckerUtil.checkHexNumberFormat(stringValue2);
                int digitValue3 = binaryBlock2.getDigitValue(i);
                int digitValue4 = binaryBlock2.getDigitValue(4);
                String stringValue3 = binaryBlock2.getStringValue(digitValue4);
                DataCheckerUtil.checkLessEqualLength(stringValue3.length(), 2048);
                DataCheckerUtil.checkUrlCharFormat(stringValue3);
                int digitValue5 = binaryBlock2.getDigitValue(6);
                byte[] byteArray = binaryBlock2.getByteArray(digitValue5);
                int digitValue6 = binaryBlock2.getDigitValue(3);
                String stringValue4 = binaryBlock2.getStringValue(digitValue6);
                if (stringValue4.length() != 0) {
                    DataCheckerUtil.checkLessEqualLength(stringValue4.length(), 256);
                    this.fixDataLength += digitValue2 + 82 + digitValue4 + digitValue5 + digitValue6;
                    arrayList.add(new DetailLinkageApp(stringValue, stringValue2, digitValue3, stringValue3, byteArray, stringValue4));
                    i2++;
                    binaryBlock2 = binaryBlock;
                    i = 1;
                } else {
                    throw new DataCheckerException(DataCheckerUtil.class, 1795, 1, stringValue4);
                }
            }
        }
        return arrayList;
    }

    private List<DetailLinkageWeb> parseLinkageWebInfoList(BinaryBlock binaryBlock) throws DataCheckerException, UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        int digitValue = binaryBlock.getDigitValue(1);
        if (digitValue > 0) {
            for (int i = 0; i < digitValue; i++) {
                int digitValue2 = binaryBlock.getDigitValue(4);
                String stringValue = binaryBlock.getStringValue(digitValue2);
                DataCheckerUtil.checkLessEqualLength(stringValue.length(), 2048);
                DataCheckerUtil.checkUrlCharFormat(stringValue);
                int digitValue3 = binaryBlock.getDigitValue(3);
                String stringValue2 = binaryBlock.getStringValue(digitValue3);
                DataCheckerUtil.checkLessEqualLength(stringValue2.length(), 64);
                this.fixDataLength += digitValue3 + 7 + digitValue2;
                arrayList.add(new DetailLinkageWeb(stringValue, stringValue2));
            }
        }
        return arrayList;
    }
}
