package com.felicanetworks.mfm.main.model.internal.main.net;

import android.os.Build;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
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

        public Parameter(String str, String str2, String str3) {
            this.idm = str;
            this.apiCodeBeta = str2;
            this.apiCodeVersion = str3;
        }

        public String toString() {
            return "Parameter{plKind='" + this.plKind + "', pfVersion='" + this.pfVersion + "', isCode='" + this.isCode + "', appId='" + this.appId + "', appVersion='" + this.appVersion + "', idm='" + this.idm + "', apiCodeBeta='" + this.apiCodeBeta + "', apiCodeVersion='" + this.apiCodeVersion + "'}";
        }
    }

    public static class Result {
        public List<ApplicationItem> apliList;
        public List<AreaItem> areaList;
        public List<MultiPurposeIdentifierItem> multiPurposeList;

        private Result(List<AreaItem> list, List<ApplicationItem> list2, List<MultiPurposeIdentifierItem> list3) {
            this.areaList = list;
            this.apliList = list2;
            this.multiPurposeList = list3;
        }

        public static class AreaItem implements ServiceIdPolicy.ServiceIdHolder {
            public String areaCode;
            public String cacheFlg;
            public String serviceId;
            public String serviceVersion;
            public String systemCode;

            public AreaItem(String str, String str2, String str3, String str4, String str5) {
                this.systemCode = str;
                this.areaCode = str2;
                this.serviceId = str3;
                this.serviceVersion = str4;
                this.cacheFlg = str5;
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

            public ApplicationItem(String str, String str2, String str3, String str4) {
                this.packageName = str;
                this.hashvalue = str2;
                this.serviceId = str3;
                this.serviceVersion = str4;
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

            public MultiPurposeIdentifierItem(String str, String str2, String str3, String str4, String str5) {
                this.serviceId = str3;
                this.serviceVersion = str4;
                this.multipurposeServiceKind = str;
                this.multipurposeIdentifierCode = str2;
                this.cacheFlg = str5;
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
            return new NetworkExpert.Request(str, "GET", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "identification information request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:?, code lost:
    
        return new com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.Result(com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.filter(r8), com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.filter(r9), com.felicanetworks.mfm.main.policy.service.ServiceIdPolicy.filter(r4), null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x011c, code lost:
    
        r9 = new java.util.ArrayList();
        r5 = r5 + 3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0125, code lost:
    
        if (r5 >= (r7 + 3)) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0127, code lost:
    
        r15 = r1[r5].split(",");
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x012e, code lost:
    
        if (r15.length != r14) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0130, code lost:
    
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkLessEqualLength(r15[0].length(), 64);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r15[r4].length(), 64);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r15[2].length(), r6);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r15[3].length(), r6);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaSignFormat(r15[0]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkHexNumberFormat(r15[r4]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r15[2]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkDecNumberFormat(r15[3]);
        r9.add(new com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.Result.ApplicationItem(r15[0], r15[r4], r15[2], r15[3]));
        r5 = r5 + 1;
        r4 = 1;
        r6 = 8;
        r14 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0197, code lost:
    
        throw new com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException(getClass(), 517, "Number of the data injustice", com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0198, code lost:
    
        r4 = new java.util.ArrayList();
        r7 = r7 + 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x01a0, code lost:
    
        if (r7 >= r1.length) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x01a2, code lost:
    
        r5 = r1[r7].split(",");
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x01aa, code lost:
    
        if (r5.length != 5) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x01ac, code lost:
    
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r5[0].length(), 3);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkLessEqualLength(r5[1].length(), 128);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r5[2].length(), 8);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkEqualLength(r5[3].length(), 8);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkDecNumberFormat(r5[0]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r5[1]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkAlphaNumberFormat(r5[2]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkDecNumberFormat(r5[3]);
        com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil.checkFixValue(r5[4], new java.lang.String[]{"0", "1"});
        r4.add(new com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.Result.MultiPurposeIdentifierItem(r5[0], r5[1], r5[2], r5[3], r5[4]));
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0223, code lost:
    
        throw new com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException(getClass(), 518, "Number of the data injustice", com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x022a, code lost:
    
        if (isServiceVersionCheck(r8, r9, r4) == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0251, code lost:
    
        throw new com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException(getClass(), 519, "service version error", com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException.Type.ID_OTHER_ERROR);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0252, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0253, code lost:
    
        r6 = 0;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v5, types: [com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol$Result] */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v14 */
    /* JADX WARN: Type inference failed for: r6v17 */
    /* JADX WARN: Type inference failed for: r6v38 */
    /* JADX WARN: Type inference failed for: r6v39 */
    /* JADX WARN: Type inference failed for: r6v4 */
    /* JADX WARN: Type inference failed for: r6v5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.Result parse(com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert.Response r28) throws com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException {
        /*
            Method dump skipped, instruction units count: 756
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol.parse(com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert$Response):com.felicanetworks.mfm.main.model.internal.main.net.ServiceIdsProtocol$Result");
    }

    protected ServiceIdsProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }

    private boolean isServiceVersionCheck(List<Result.AreaItem> list, List<Result.ApplicationItem> list2, List<Result.MultiPurposeIdentifierItem> list3) {
        for (int i = 0; i < list.size(); i++) {
            String str = list.get(i).serviceId;
            String str2 = list.get(i).serviceVersion;
            if (!isAreaListVersionCheck(str, str2, list) || !isAppListVersionCheck(str, str2, list2) || !isMultiPurposeListVersionCheck(str, str2, list3)) {
                return false;
            }
        }
        for (int i2 = 0; i2 < list2.size(); i2++) {
            String str3 = list2.get(i2).serviceId;
            String str4 = list2.get(i2).serviceVersion;
            if (!isAppListVersionCheck(str3, str4, list2) || !isMultiPurposeListVersionCheck(str3, str4, list3)) {
                return false;
            }
        }
        for (int i3 = 0; i3 < list3.size(); i3++) {
            if (!isMultiPurposeListVersionCheck(list3.get(i3).serviceId, list3.get(i3).serviceVersion, list3)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAreaListVersionCheck(String str, String str2, List<Result.AreaItem> list) {
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i).serviceId) && !str2.equals(list.get(i).serviceVersion)) {
                return false;
            }
        }
        return true;
    }

    private boolean isAppListVersionCheck(String str, String str2, List<Result.ApplicationItem> list) {
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i).serviceId) && !str2.equals(list.get(i).serviceVersion)) {
                return false;
            }
        }
        return true;
    }

    private boolean isMultiPurposeListVersionCheck(String str, String str2, List<Result.MultiPurposeIdentifierItem> list) {
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i).serviceId) && !str2.equals(list.get(i).serviceVersion)) {
                return false;
            }
        }
        return true;
    }
}
