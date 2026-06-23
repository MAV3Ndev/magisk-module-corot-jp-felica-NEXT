package com.felicanetworks.mfm.main.model.internal.main.net;

import android.os.Build;
import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.info.DetailLinkageApp;
import com.felicanetworks.mfm.main.model.info.DetailLinkageWeb;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnctrl.net.BinaryBlock;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
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

            public ServiceData(String serviceId, String serviceVersion) {
                this.serviceId = serviceId;
                this.serviceVersion = serviceVersion;
            }

            /* JADX DEBUG: Method merged with bridge method: compareTo(Ljava/lang/Object;)I */
            @Override // java.lang.Comparable
            public int compareTo(ServiceData another) {
                int iCompareTo = this.serviceId.compareTo(another.serviceId);
                if (iCompareTo > 0) {
                    return 1;
                }
                return iCompareTo < 0 ? -1 : 0;
            }

            public String toString() {
                return "ServiceData{serviceId='" + this.serviceId + "', serviceVersion='" + this.serviceVersion + "'}";
            }
        }

        public Parameter(String idm, String apiCodeBeta, String apiCodeVersion, List<ServiceData> itemList) {
            this.idm = idm;
            this.itemList = itemList;
            this.apiCodeBeta = apiCodeBeta;
            this.apiCodeVersion = apiCodeVersion;
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

        private Result(List<ServiceItem> result) {
            this.result = result;
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
            public final List<DetailLinkageApp> linkageAppInfoList;
            public final List<DetailLinkageWeb> linkageWebInfoList;
            public final String packageName;
            public final String serviceId;
            public final String serviceName;
            public final String serviceProviderName;
            public final String serviceType;
            public final String serviceVersion;
            public final String webUrl;

            public ServiceItem(String serviceId, String serviceVersion, String displayPriority, String color, String serviceName, String serviceProviderName, String cooperativeKind, String packageName, String hashValue, String applicationKind, String applicationUrl, String webUrl, byte[] iconData, byte[] extensionIconData1, byte[] extensionIconData2, String serviceType, String cardCategory, String detailImagePresence, byte[] cardFaceImage, String contactName, String contactNumber, String contactUrl, String contactEmail, List<DetailLinkageApp> linkageAppInfoList, List<DetailLinkageWeb> linkageWebInfoList) {
                ArrayList arrayList = new ArrayList();
                this.linkageAppInfoList = arrayList;
                ArrayList arrayList2 = new ArrayList();
                this.linkageWebInfoList = arrayList2;
                this.serviceId = serviceId;
                this.serviceVersion = serviceVersion;
                this.displayPriority = displayPriority;
                this.color = color;
                this.serviceName = serviceName;
                this.serviceProviderName = serviceProviderName;
                this.cooperativeKind = cooperativeKind;
                this.packageName = packageName;
                this.hashValue = hashValue;
                this.applicationKind = applicationKind;
                this.applicationUrl = applicationUrl;
                this.webUrl = webUrl;
                this.iconData = (byte[]) iconData.clone();
                this.extensionIconData1 = (byte[]) extensionIconData1.clone();
                this.extensionIconData2 = (byte[]) extensionIconData2.clone();
                this.serviceType = serviceType;
                this.cardCategory = cardCategory;
                this.detailImagePresence = detailImagePresence;
                this.cardFaceImage = cardFaceImage;
                this.contactName = contactName;
                this.contactNumber = contactNumber;
                this.contactUrl = contactUrl;
                this.contactEmail = contactEmail;
                arrayList.addAll(linkageAppInfoList);
                arrayList2.addAll(linkageWebInfoList);
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
            return new NetworkExpert.Request(str, SwsRequest.HTTP_METHOD_POST, map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "felica corresponding service information request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        int i;
        int i2;
        String str;
        int i3;
        int i4;
        String str2;
        int i5;
        int i6;
        ArrayList arrayList = new ArrayList();
        response.header.put("content-length", Integer.toString(response.data.length));
        BinaryBlock binaryBlock = new BinaryBlock(response.data, StringUtil.UTF_8);
        try {
            if (response.code != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (Integer.parseInt((String) Objects.requireNonNull(response.header.get("content-length"))) != response.data.length) {
                throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            int i7 = 8;
            int digitValue = binaryBlock.getDigitValue(8);
            int i8 = 0;
            while (i8 < digitValue) {
                int digitValue2 = binaryBlock.getDigitValue(i7);
                String stringValue = binaryBlock.getStringValue(i7);
                DataCheckerUtil.checkAlphaNumberFormat(stringValue);
                String stringValue2 = binaryBlock.getStringValue(i7);
                DataCheckerUtil.checkDecNumberFormat(stringValue2);
                int iIntValue = Integer.valueOf(stringValue2).intValue();
                String stringValue3 = binaryBlock.getStringValue(2);
                DataCheckerUtil.checkDecNumberFormat(stringValue3);
                String stringValue4 = binaryBlock.getStringValue(6);
                DataCheckerUtil.checkHexNumberFormat(stringValue4);
                int digitValue3 = binaryBlock.getDigitValue(3);
                String stringValue5 = binaryBlock.getStringValue(digitValue3);
                DataCheckerUtil.checkLessEqualLength(stringValue5.length(), 64);
                int digitValue4 = binaryBlock.getDigitValue(3);
                String stringValue6 = binaryBlock.getStringValue(digitValue4);
                DataCheckerUtil.checkLessEqualLength(stringValue6.length(), 64);
                int digitValue5 = binaryBlock.getDigitValue(6);
                byte[] byteArray = binaryBlock.getByteArray(digitValue5);
                int digitValue6 = binaryBlock.getDigitValue(6);
                byte[] byteArray2 = binaryBlock.getByteArray(digitValue6);
                int digitValue7 = binaryBlock.getDigitValue(6);
                byte[] byteArray3 = binaryBlock.getByteArray(digitValue7);
                int digitValue8 = binaryBlock.getDigitValue(1);
                String stringValue7 = binaryBlock.getStringValue(digitValue8);
                if (SERVICE_VERSION_WITH_ADDITIONAL <= iIntValue) {
                    i = digitValue8;
                    DataCheckerUtil.checkEqualLength(stringValue7.length(), 8);
                    DataCheckerUtil.checkAlphaNumberFormat(stringValue7);
                } else {
                    i = digitValue8;
                    DataCheckerUtil.checkEqualLength(stringValue7.length(), 0);
                }
                int digitValue9 = binaryBlock.getDigitValue(1);
                String stringValue8 = binaryBlock.getStringValue(digitValue9);
                if (SERVICE_VERSION_WITH_ADDITIONAL > iIntValue) {
                    i2 = digitValue6;
                    DataCheckerUtil.checkEqualLength(stringValue8.length(), 0);
                } else if (digitValue9 > 0) {
                    i2 = digitValue6;
                    DataCheckerUtil.checkEqualLength(stringValue8.length(), 8);
                    DataCheckerUtil.checkAlphaNumberFormat(stringValue8);
                } else {
                    i2 = digitValue6;
                }
                int digitValue10 = binaryBlock.getDigitValue(1);
                String stringValue9 = binaryBlock.getStringValue(digitValue10);
                int i9 = digitValue;
                if (SERVICE_VERSION_WITH_ADDITIONAL <= iIntValue) {
                    str = stringValue8;
                    DataCheckerUtil.checkEqualLength(stringValue9.length(), 1);
                    DataCheckerUtil.checkFixValue(stringValue9, new String[]{"1", "0"});
                } else {
                    str = stringValue8;
                    DataCheckerUtil.checkEqualLength(stringValue9.length(), 0);
                }
                int digitValue11 = binaryBlock.getDigitValue(6);
                byte[] byteArray4 = binaryBlock.getByteArray(digitValue11);
                int digitValue12 = binaryBlock.getDigitValue(3);
                int i10 = i2;
                String stringValue10 = binaryBlock.getStringValue(digitValue12);
                if (SERVICE_VERSION_WITH_ADDITIONAL <= iIntValue) {
                    i3 = digitValue12;
                    DataCheckerUtil.checkLessEqualLength(stringValue10.length(), 256);
                } else {
                    i3 = digitValue12;
                    DataCheckerUtil.checkEqualLength(stringValue10.length(), 0);
                }
                int digitValue13 = binaryBlock.getDigitValue(2);
                String stringValue11 = binaryBlock.getStringValue(digitValue13);
                if (SERVICE_VERSION_WITH_ADDITIONAL > iIntValue) {
                    i4 = digitValue13;
                    DataCheckerUtil.checkEqualLength(stringValue11.length(), 0);
                } else if (digitValue13 > 0) {
                    i4 = digitValue13;
                    DataCheckerUtil.checkLessEqualLength(stringValue11.length(), 64);
                    DataCheckerUtil.checkAlphaSignFormat(stringValue11);
                } else {
                    i4 = digitValue13;
                }
                int digitValue14 = binaryBlock.getDigitValue(4);
                String str3 = str;
                String stringValue12 = binaryBlock.getStringValue(digitValue14);
                if (SERVICE_VERSION_WITH_ADDITIONAL > iIntValue) {
                    str2 = str3;
                    DataCheckerUtil.checkEqualLength(stringValue12.length(), 0);
                } else if (digitValue14 > 0) {
                    str2 = str3;
                    DataCheckerUtil.checkLessEqualLength(stringValue12.length(), 2048);
                    DataCheckerUtil.checkAlphaSignFormat(stringValue12);
                } else {
                    str2 = str3;
                }
                int digitValue15 = binaryBlock.getDigitValue(3);
                String stringValue13 = binaryBlock.getStringValue(digitValue15);
                if (SERVICE_VERSION_WITH_ADDITIONAL > iIntValue) {
                    DataCheckerUtil.checkEqualLength(stringValue13.length(), 0);
                } else if (digitValue15 > 0) {
                    DataCheckerUtil.checkLessEqualLength(stringValue13.length(), 256);
                    DataCheckerUtil.checkAlphaSignFormat(stringValue13);
                }
                if (stringValue10.isEmpty()) {
                    i5 = digitValue15;
                    i6 = 1;
                } else {
                    int i11 = i4 + digitValue14 + digitValue15;
                    i5 = digitValue15;
                    i6 = 1;
                    DataCheckerUtil.checkMidwayLength(i11, 1, Integer.MAX_VALUE);
                }
                String stringValue14 = binaryBlock.getStringValue(i6);
                DataCheckerUtil.checkFixValue(stringValue14, new String[]{"1", "2", "3"});
                if (stringValue14.equals("2")) {
                    int digitValue16 = binaryBlock.getDigitValue(2);
                    DataCheckerUtil.checkLessEqualLength(digitValue16, 64);
                    String stringValue15 = binaryBlock.getStringValue(digitValue16);
                    DataCheckerUtil.checkAlphaSignCommaFormat(stringValue15);
                    String stringValue16 = binaryBlock.getStringValue(64);
                    DataCheckerUtil.checkHexNumberFormat(stringValue16);
                    String stringValue17 = binaryBlock.getStringValue(1);
                    DataCheckerUtil.checkFixValue(stringValue17, new String[]{"1", "2"});
                    int digitValue17 = binaryBlock.getDigitValue(3);
                    DataCheckerUtil.checkLessEqualLength(digitValue17, 999);
                    String stringValue18 = binaryBlock.getStringValue(digitValue17);
                    DataCheckerUtil.checkUrlCharFormat(stringValue18);
                    this.fixDataLength = digitValue3 + 142 + digitValue4 + digitValue5 + i10 + digitValue7 + digitValue16 + digitValue17 + i + digitValue9 + digitValue10 + digitValue11 + i3 + i4 + digitValue14 + i5;
                    List<DetailLinkageApp> linkageAppInfoList = parseLinkageAppInfoList(binaryBlock);
                    List<DetailLinkageWeb> linkageWebInfoList = parseLinkageWebInfoList(binaryBlock);
                    if (digitValue2 != this.fixDataLength) {
                        throw new NetworkExpertException(getClass(), 515, "data length disagreement", NetworkExpertException.Type.ID_LENGTH_ERROR);
                    }
                    arrayList.add(new Result.ServiceItem(stringValue, stringValue2, stringValue3, stringValue4, stringValue5, stringValue6, stringValue14, stringValue15, stringValue16, stringValue17, stringValue18, "", byteArray, byteArray2, byteArray3, stringValue7, str2, stringValue9, byteArray4, stringValue10, stringValue11, stringValue12, stringValue13, linkageAppInfoList, linkageWebInfoList));
                } else {
                    int digitValue18 = binaryBlock.getDigitValue(3);
                    DataCheckerUtil.checkLessEqualLength(digitValue18, 999);
                    String stringValue19 = binaryBlock.getStringValue(digitValue18);
                    DataCheckerUtil.checkUrlCharFormat(stringValue19);
                    this.fixDataLength = digitValue3 + 75 + digitValue4 + digitValue5 + i10 + digitValue7 + digitValue18 + i + digitValue9 + digitValue10 + digitValue11 + i3 + i4 + digitValue14 + i5;
                    List<DetailLinkageApp> linkageAppInfoList2 = parseLinkageAppInfoList(binaryBlock);
                    List<DetailLinkageWeb> linkageWebInfoList2 = parseLinkageWebInfoList(binaryBlock);
                    if (digitValue2 != this.fixDataLength) {
                        throw new NetworkExpertException(getClass(), 516, "data length disagreement", NetworkExpertException.Type.ID_LENGTH_ERROR);
                    }
                    arrayList.add(new Result.ServiceItem(stringValue, stringValue2, stringValue3, stringValue4, stringValue5, stringValue6, stringValue14, "", "", "", "", stringValue19, byteArray, byteArray2, byteArray3, stringValue7, str2, stringValue9, byteArray4, stringValue10, stringValue11, stringValue12, stringValue13, linkageAppInfoList2, linkageWebInfoList2));
                }
                i8++;
                digitValue = i9;
                i7 = 8;
            }
            return new Result(ServiceIdPolicy.filter(arrayList));
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            if (e.getErrorId() == 0) {
                throw new NetworkExpertException(getClass(), 517, e, "felica corresponding service information response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            if (1 != e.getErrorId()) {
                return null;
            }
            throw new NetworkExpertException(getClass(), 518, e, "felica corresponding service information response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
        } catch (NetworkExpertException e2) {
            LogUtil.warning(e2);
            throw e2;
        } catch (Exception e3) {
            LogUtil.warning(e3);
            throw new NetworkExpertException(getClass(), 519, e3, "felica corresponding service information response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    protected ServiceProtocol(NetworkExpert net) {
        super(net);
        this.fixDataLength = 0;
    }

    private List<DetailLinkageApp> parseLinkageAppInfoList(BinaryBlock block) throws DataCheckerException, UnsupportedEncodingException {
        BinaryBlock binaryBlock = block;
        ArrayList arrayList = new ArrayList();
        int i = 1;
        int digitValue = binaryBlock.getDigitValue(1);
        if (digitValue > 0) {
            int i2 = 0;
            while (i2 < digitValue) {
                int digitValue2 = binaryBlock.getDigitValue(4);
                String stringValue = binaryBlock.getStringValue(digitValue2);
                DataCheckerUtil.checkLessEqualLength(stringValue.length(), 1024);
                DataCheckerUtil.checkAlphaSignCommaFormat(stringValue);
                String stringValue2 = binaryBlock.getStringValue(64);
                DataCheckerUtil.checkEqualLength(stringValue2.length(), 64);
                DataCheckerUtil.checkHexNumberFormat(stringValue2);
                int digitValue3 = binaryBlock.getDigitValue(i);
                int digitValue4 = binaryBlock.getDigitValue(4);
                String stringValue3 = binaryBlock.getStringValue(digitValue4);
                DataCheckerUtil.checkLessEqualLength(stringValue3.length(), 2048);
                DataCheckerUtil.checkUrlCharFormat(stringValue3);
                int digitValue5 = binaryBlock.getDigitValue(6);
                byte[] byteArray = binaryBlock.getByteArray(digitValue5);
                int digitValue6 = binaryBlock.getDigitValue(3);
                String stringValue4 = binaryBlock.getStringValue(digitValue6);
                if (stringValue4.length() != 0) {
                    DataCheckerUtil.checkLessEqualLength(stringValue4.length(), 256);
                    this.fixDataLength += digitValue2 + 82 + digitValue4 + digitValue5 + digitValue6;
                    arrayList.add(new DetailLinkageApp(stringValue, stringValue2, digitValue3, stringValue3, byteArray, stringValue4));
                    i2++;
                    binaryBlock = block;
                    i = 1;
                } else {
                    throw new DataCheckerException(DataCheckerUtil.class, 1795, 1, stringValue4);
                }
            }
        }
        return arrayList;
    }

    private List<DetailLinkageWeb> parseLinkageWebInfoList(BinaryBlock block) throws DataCheckerException, UnsupportedEncodingException {
        ArrayList arrayList = new ArrayList();
        int digitValue = block.getDigitValue(1);
        if (digitValue > 0) {
            for (int i = 0; i < digitValue; i++) {
                int digitValue2 = block.getDigitValue(4);
                String stringValue = block.getStringValue(digitValue2);
                DataCheckerUtil.checkLessEqualLength(stringValue.length(), 2048);
                DataCheckerUtil.checkUrlCharFormat(stringValue);
                int digitValue3 = block.getDigitValue(3);
                String stringValue2 = block.getStringValue(digitValue3);
                DataCheckerUtil.checkLessEqualLength(stringValue2.length(), 64);
                this.fixDataLength += digitValue3 + 7 + digitValue2;
                arrayList.add(new DetailLinkageWeb(stringValue, stringValue2));
            }
        }
        return arrayList;
    }
}
