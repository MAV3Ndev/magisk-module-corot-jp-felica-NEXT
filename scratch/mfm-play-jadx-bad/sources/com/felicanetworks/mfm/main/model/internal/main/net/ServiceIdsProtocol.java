package com.felicanetworks.mfm.main.model.internal.main.net;

import android.os.Build;
import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import com.felicanetworks.semc.sws.json.JsonConst;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class ServiceIdsProtocol extends Protocol {

    public static class Parameter {
        public static final String INTERFACE_VERSION = "03";
        public String apiCodeBeta;
        public String apiCodeVersion;
        public String idm;
        public String plKind = (String) Sg.getValue(Sg.Key.SETTING_PLATFORM_TYPE);
        public String isCode = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
        public String pfVersion = Build.VERSION.RELEASE;
        public String appId = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
        public String appVersion = Information.simpleVersionName();

        public Parameter(String idm, String apiCodeBeta, String apiCodeVersion) {
            this.idm = idm;
            this.apiCodeBeta = apiCodeBeta;
            this.apiCodeVersion = apiCodeVersion;
        }

        public String toString() {
            return "Parameter{plKind='" + this.plKind + "', pfVersion='" + this.pfVersion + "', isCode='" + this.isCode + "', appId='" + this.appId + "', appVersion='" + this.appVersion + "', idm='" + this.idm + "', apiCodeBeta='" + this.apiCodeBeta + "', apiCodeVersion='" + this.apiCodeVersion + "'}";
        }
    }

    public static class Result {
        public List<ApplicationItem> apliList;
        public List<AreaItem> areaList;
        public List<MultiPurposeIdentifierItem> multiPurposeList;

        private Result(List<AreaItem> areaList, List<ApplicationItem> apliList, List<MultiPurposeIdentifierItem> multiPurposeList) {
            this.areaList = areaList;
            this.apliList = apliList;
            this.multiPurposeList = multiPurposeList;
        }

        public static class AreaItem implements ServiceIdPolicy.ServiceIdHolder {
            public String areaCode;
            public String cacheFlg;
            public String serviceId;
            public String serviceVersion;
            public String systemCode;

            public AreaItem(String systemCode, String areaCode, String serviceId, String serviceVersion, String cacheFlg) {
                this.systemCode = systemCode;
                this.areaCode = areaCode;
                this.serviceId = serviceId;
                this.serviceVersion = serviceVersion;
                this.cacheFlg = cacheFlg;
            }

            public String toString() {
                return "AreaItem[" + this.systemCode + ", " + this.areaCode + ", " + this.serviceId + ", " + this.serviceVersion + ", " + this.cacheFlg + "]";
            }

            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
            public String sid() {
                return this.serviceId;
            }
        }

        public static class ApplicationItem implements ServiceIdPolicy.ServiceIdHolder {
            public String hashvalue;
            public String packageName;
            public String serviceId;
            public String serviceVersion;

            public ApplicationItem(String packageName, String hashValue, String serviceId, String serviceVersion) {
                this.packageName = packageName;
                this.hashvalue = hashValue;
                this.serviceId = serviceId;
                this.serviceVersion = serviceVersion;
            }

            public String toString() {
                return "ApplicationItem[" + this.packageName + ", " + this.hashvalue + ", " + this.serviceId + ", " + this.serviceVersion + "]";
            }

            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
            public String sid() {
                return this.serviceId;
            }
        }

        public static class MultiPurposeIdentifierItem implements ServiceIdPolicy.ServiceIdHolder {
            public String cacheFlg;
            public String multipurposeIdentifierCode;
            public String multipurposeServiceKind;
            public String serviceId;
            public String serviceVersion;

            public MultiPurposeIdentifierItem(String multipurposeServiceKind, String multipurposeIdentifierCode, String serviceId, String serviceVersion, String cacheFlg) {
                this.serviceId = serviceId;
                this.serviceVersion = serviceVersion;
                this.multipurposeServiceKind = multipurposeServiceKind;
                this.multipurposeIdentifierCode = multipurposeIdentifierCode;
                this.cacheFlg = cacheFlg;
            }

            public String toString() {
                return "MultiPurposeIdentifierItem[" + this.multipurposeServiceKind + ", " + this.multipurposeIdentifierCode + ", " + this.serviceId + ", " + this.serviceVersion + ", " + this.cacheFlg + "]";
            }

            @Override // com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.ServiceIdHolder
            public String sid() {
                return this.serviceId;
            }
        }

        public String toString() {
            return "ServiceIdsProtocol$Result{areaList:" + this.areaList + ",apliList:" + this.apliList + ",multiPurposeList:" + this.multiPurposeList + "}";
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_SIM_GID_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_SIM_GID_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_SIM_GID_READ_TIMEOUT)).intValue();
            parameter.pfVersion = URLEncoder.encode(parameter.pfVersion, StringUtil.UTF_8);
            DataCheckerUtil.checkLessEqualLength(parameter.appVersion.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(parameter.appVersion);
            byte[] bytes = ("pt=" + parameter.plKind + "&pv=" + parameter.pfVersion + "&i=" + parameter.isCode + "&ai=" + parameter.appId + "&av=" + parameter.appVersion + "&idm=" + parameter.idm + "&acd=" + parameter.apiCodeBeta + "&acv=" + parameter.apiCodeVersion + "&ifv=03").getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            map.put("content-length", Integer.toString(bytes.length));
            return new NetworkExpert.Request(str, SwsRequest.HTTP_METHOD_GET, map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "identification information request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x011f, code lost:
    
        r8 = new java.util.ArrayList();
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0124, code lost:
    
        r4 = r4 + 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0127, code lost:
    
        if (r4 >= (r6 + 3)) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0129, code lost:
    
        r13 = r0[r4].split(",");
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x012f, code lost:
    
        r17 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0132, code lost:
    
        if (r13.length != r14) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0134, code lost:
    
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkLessEqualLength(r13[r27].length(), 64);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r13[r17].length(), 64);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r13[2].length(), r5);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r13[3].length(), r5);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaSignFormat(r13[r27]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkHexNumberFormat(r13[r17]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r13[2]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkDecNumberFormat(r13[3]);
        r8.add(new com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.Result.ApplicationItem(r13[r27], r13[r17], r13[2], r13[3]));
        r4 = r4 + 1;
        r2 = r17;
        r14 = r14;
        r5 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0197, code lost:
    
        throw new com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException(getClass(), 517, "Number of the data injustice", com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0198, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0199, code lost:
    
        r17 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x019d, code lost:
    
        r17 = r2;
        r18 = r14;
        r2 = new java.util.ArrayList();
        r6 = r6 + 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x01a8, code lost:
    
        if (r6 >= r0.length) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x01aa, code lost:
    
        r3 = r0[r6].split(",");
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x01b1, code lost:
    
        if (r3.length != 5) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01b3, code lost:
    
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r3[r27].length(), 3);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkLessEqualLength(r3[r17].length(), 128);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r3[2].length(), 8);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r3[3].length(), 8);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkDecNumberFormat(r3[r27]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r3[r17]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r3[2]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkDecNumberFormat(r3[3]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkFixValue(r3[r18], new java.lang.String[]{"0", "1"});
        r2.add(new com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.Result.MultiPurposeIdentifierItem(r3[r27], r3[r17], r3[2], r3[3], r3[r18]));
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x021c, code lost:
    
        throw new com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException(getClass(), 518, "Number of the data injustice", com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0223, code lost:
    
        if (isServiceVersionCheck(r7, r8, r2) == false) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0237, code lost:
    
        return new com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.Result(com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.filter(r7), com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.filter(r8), com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.filter(r2), null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0247, code lost:
    
        throw new com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException(getClass(), 519, "service version error", com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException.Type.ID_OTHER_ERROR);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0248, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0249, code lost:
    
        r1 = r26;
     */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        int i;
        DataCheckerException dataCheckerException;
        Object obj = "\r\n";
        int i2 = 1;
        try {
            try {
            } catch (DataCheckerException e) {
                e = e;
                obj = this;
                i = 1;
            }
        } catch (NetworkExpertException e2) {
            e = e2;
        } catch (Exception e3) {
            e = e3;
            obj = this;
        }
        try {
            if (response.code != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (Integer.parseInt(response.header.get("content-length")) == response.data.length) {
                String[] strArrSplit = new String(response.data, StringUtil.UTF_8).split("\r\n");
                char c = 0;
                int i3 = 8;
                DataCheckerUtil.checkLessEqualLength(strArrSplit[0].length(), 8);
                DataCheckerUtil.checkDecNumberFormat(strArrSplit[0]);
                int i4 = Integer.parseInt(strArrSplit[0]);
                int i5 = i4 + 2;
                DataCheckerUtil.checkLessEqualLength(strArrSplit[i5].length(), 8);
                DataCheckerUtil.checkDecNumberFormat(strArrSplit[i5]);
                int i6 = Integer.parseInt(strArrSplit[i5]) + i4;
                int i7 = i6 + 4;
                DataCheckerUtil.checkLessEqualLength(strArrSplit[i7].length(), 8);
                DataCheckerUtil.checkDecNumberFormat(strArrSplit[i7]);
                if (strArrSplit.length != Integer.parseInt(strArrSplit[i7]) + i6 + 5) {
                    throw new NetworkExpertException(getClass(), 515, "number of the data injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
                }
                try {
                    if (strArrSplit.length == 5) {
                        return new Result(new ArrayList(), new ArrayList(), new ArrayList());
                    }
                    ArrayList arrayList = new ArrayList();
                    int i8 = 0;
                    while (true) {
                        int i9 = 4;
                        char c2 = c;
                        if (i8 >= i4) {
                            break;
                        }
                        i8++;
                        String[] strArrSplit2 = strArrSplit[i8].split(",");
                        if (strArrSplit2.length != 5) {
                            throw new NetworkExpertException(getClass(), 516, "Number of the data injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
                        }
                        DataCheckerUtil.checkEqualLength(strArrSplit2[c2].length(), 4);
                        DataCheckerUtil.checkEqualLength(strArrSplit2[1].length(), 8);
                        DataCheckerUtil.checkEqualLength(strArrSplit2[2].length(), 8);
                        DataCheckerUtil.checkEqualLength(strArrSplit2[3].length(), 8);
                        DataCheckerUtil.checkHexNumberFormat(strArrSplit2[c2]);
                        DataCheckerUtil.checkHexNumberFormat(strArrSplit2[1]);
                        DataCheckerUtil.checkAlphaNumberFormat(strArrSplit2[2]);
                        DataCheckerUtil.checkDecNumberFormat(strArrSplit2[3]);
                        DataCheckerUtil.checkFixValue(strArrSplit2[4], new String[]{"0", "1"});
                        arrayList.add(new Result.AreaItem(strArrSplit2[c2], strArrSplit2[1], strArrSplit2[2], strArrSplit2[3], strArrSplit2[4]));
                        c = c2;
                    }
                } catch (DataCheckerException e4) {
                    obj = this;
                    dataCheckerException = e4;
                    i = 1;
                }
            } else {
                throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
        } catch (DataCheckerException e5) {
            e = e5;
        } catch (NetworkExpertException e6) {
            e = e6;
            LogUtil.warning(e);
            throw e;
        } catch (Exception e7) {
            e = e7;
            Exception exc = e;
            LogUtil.warning(exc);
            throw new NetworkExpertException(obj.getClass(), JsonConst.LEN_APDU_COMMAND_MAX, exc, "identification information response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
        dataCheckerException = e;
        LogUtil.warning(dataCheckerException);
        if (dataCheckerException.getErrorId() != 0) {
            throw new NetworkExpertException(obj.getClass(), 520, dataCheckerException, "identification information response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
        }
        if (i != dataCheckerException.getErrorId()) {
            return null;
        }
        throw new NetworkExpertException(obj.getClass(), 521, dataCheckerException, "identification information response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
    }

    protected ServiceIdsProtocol(NetworkExpert net) {
        super(net);
    }

    private boolean isServiceVersionCheck(List<Result.AreaItem> areaList, List<Result.ApplicationItem> appList, List<Result.MultiPurposeIdentifierItem> multiPurposeList) {
        for (int i = 0; i < areaList.size(); i++) {
            String str = areaList.get(i).serviceId;
            String str2 = areaList.get(i).serviceVersion;
            if (!isAreaListVersionCheck(str, str2, areaList) || !isAppListVersionCheck(str, str2, appList) || !isMultiPurposeListVersionCheck(str, str2, multiPurposeList)) {
                return false;
            }
        }
        for (int i2 = 0; i2 < appList.size(); i2++) {
            String str3 = appList.get(i2).serviceId;
            String str4 = appList.get(i2).serviceVersion;
            if (!isAppListVersionCheck(str3, str4, appList) || !isMultiPurposeListVersionCheck(str3, str4, multiPurposeList)) {
                return false;
            }
        }
        for (int i3 = 0; i3 < multiPurposeList.size(); i3++) {
            if (!isMultiPurposeListVersionCheck(multiPurposeList.get(i3).serviceId, multiPurposeList.get(i3).serviceVersion, multiPurposeList)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAreaListVersionCheck(String targetSvId, String targetSvVersion, List<Result.AreaItem> areaList) {
        for (int i = 0; i < areaList.size(); i++) {
            if (targetSvId.equals(areaList.get(i).serviceId) && !targetSvVersion.equals(areaList.get(i).serviceVersion)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAppListVersionCheck(String targetSvId, String targetSvVersion, List<Result.ApplicationItem> appList) {
        for (int i = 0; i < appList.size(); i++) {
            if (targetSvId.equals(appList.get(i).serviceId) && !targetSvVersion.equals(appList.get(i).serviceVersion)) {
                return false;
            }
        }
        return true;
    }

    private boolean isMultiPurposeListVersionCheck(String targetSvId, String targetSvVersion, List<Result.MultiPurposeIdentifierItem> multiPurposeList) {
        for (int i = 0; i < multiPurposeList.size(); i++) {
            if (targetSvId.equals(multiPurposeList.get(i).serviceId) && !targetSvVersion.equals(multiPurposeList.get(i).serviceVersion)) {
                return false;
            }
        }
        return true;
    }
}
