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
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class FpServiceProtocol extends Protocol {

    public static class Parameter {
        private String apiCodeBeta;
        private String apiCodeVersion;
        private String idm;
        private String plKind = (String) Sg.getValue(Sg.Key.SETTING_PLATFORM_TYPE);
        private String appId = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
        private String isCode = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
        private String pfVersion = Build.VERSION.RELEASE;
        private String appVersion = Information.simpleVersionName();
        private String languageCode = Locale.getDefault().getLanguage();
        private String countryCode = Locale.getDefault().getCountry();

        public Parameter(String idm, String apiCodeBeta, String apiCodeVersion) {
            this.idm = idm;
            this.apiCodeBeta = apiCodeBeta;
            this.apiCodeVersion = apiCodeVersion;
        }

        public String toString() {
            return "Parameter{plKind='" + this.plKind + "', pfVersion='" + this.pfVersion + "', isCode='" + this.isCode + "', languageCode='" + this.languageCode + "', countryCode='" + this.countryCode + "', appId='" + this.appId + "', appVersion='" + this.appVersion + "', idm='" + this.idm + "', apiCodeBeta='" + this.apiCodeBeta + "', apiCodeVersion='" + this.apiCodeVersion + "'}";
        }
    }

    public static class Result {
        private static final String FP_SERVICE_KIND_POINT = "01";
        private List<FpServiceItem> result;

        public List<FpServiceItem> getResult() {
            return this.result;
        }

        public static class FpServiceItem {
            private boolean enableService;
            private String fpServiceCorporation;
            private String fpServiceIconUrl;
            private String fpServiceKind;
            private String fpServiceName;
            private String fpServiceNumber;
            private boolean needShowValue;
            private String pointUnitName;
            private String webUrl;

            public FpServiceItem(String fpServiceNumber, String fpServiceName, String fpServiceIconUrl, String fpServiceCorporation, boolean enableService, String webUrl, String fpServiceKind, boolean needShowValue, String pointUnitName) {
                this.fpServiceNumber = fpServiceNumber;
                this.fpServiceName = fpServiceName;
                this.fpServiceIconUrl = fpServiceIconUrl;
                this.fpServiceCorporation = fpServiceCorporation;
                this.enableService = enableService;
                this.webUrl = webUrl;
                this.fpServiceKind = fpServiceKind;
                this.needShowValue = needShowValue;
                this.pointUnitName = pointUnitName;
            }

            public String toString() {
                return "FpServiceItem{fpServiceNumber='" + this.fpServiceNumber + "', fpServiceName='" + this.fpServiceName + "', fpServiceIconUrl='" + this.fpServiceIconUrl + "', fpServiceCorporation='" + this.fpServiceCorporation + "', enableService=" + this.enableService + ", webUrl='" + this.webUrl + "', fpServiceKind='" + this.fpServiceKind + "', needShowValue=" + this.needShowValue + ", pointUnitName='" + this.pointUnitName + "'}";
            }

            public String getFpServiceNumber() {
                return this.fpServiceNumber;
            }

            public String getFpServiceName() {
                return this.fpServiceName;
            }

            public String getFpServiceIconUrl() {
                return this.fpServiceIconUrl;
            }

            public String getFpServiceCorporation() {
                return this.fpServiceCorporation;
            }

            public boolean isEnableService() {
                return this.enableService;
            }

            public String getWebUrl() {
                return this.webUrl;
            }

            public String getFpServiceKind() {
                return this.fpServiceKind;
            }

            public boolean isNeedShowValue() {
                return this.needShowValue;
            }

            public String getPointUnitName() {
                return this.pointUnitName;
            }
        }

        private Result(List<FpServiceItem> result) {
            this.result = result;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_FP_SERVICE_GETTING_INFO_CONNECTION_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_FP_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_FP_RECEIVING_TIMEOUT)).intValue();
            parameter.pfVersion = URLEncoder.encode(parameter.pfVersion, StringUtil.UTF_8);
            DataCheckerUtil.checkLessEqualLength(parameter.appVersion.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(parameter.appVersion);
            try {
                DataCheckerUtil.checkAlphaNumberFormat(parameter.languageCode);
                DataCheckerUtil.checkAlphaNumberFormat(parameter.countryCode);
                DataCheckerUtil.checkLessEqualLength(parameter.languageCode.length(), 3);
                DataCheckerUtil.checkLessEqualLength(parameter.countryCode.length(), 3);
            } catch (DataCheckerException unused) {
                parameter.languageCode = "ja";
                parameter.countryCode = "JP";
            }
            byte[] bytes = ("pt=" + parameter.plKind + "&pv=" + parameter.pfVersion + "&i=" + parameter.isCode + "&l=" + parameter.languageCode + "&c=" + parameter.countryCode + "&ai=" + parameter.appId + "&av=" + parameter.appVersion + "&idm=" + parameter.idm + "&acd=" + parameter.apiCodeBeta + "&acv=" + parameter.apiCodeVersion).getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            map.put("content-length", Integer.toString(bytes.length));
            return new NetworkExpert.Request(str, SwsRequest.HTTP_METHOD_POST, map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "FPService information request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        ArrayList arrayList = new ArrayList();
        try {
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            if (e.getErrorId() == 0) {
                throw new NetworkExpertException(getClass(), 514, e, "felica-pocket information response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            if (1 == e.getErrorId()) {
                throw new NetworkExpertException(getClass(), 515, e, "felica-pocket information response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
        } catch (NetworkExpertException e2) {
            LogUtil.warning(e2);
            throw e2;
        } catch (Exception e3) {
            LogUtil.warning(e3);
            throw new NetworkExpertException(getClass(), 516, e3, "felica-pocket information response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
        if (response.code != 200) {
            throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
        }
        JSONArray jSONArray = new JSONArray(new String(response.data, StringUtil.UTF_8));
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject = jSONArray.getJSONObject(i);
            String string = jSONObject.getString("fpn");
            DataCheckerUtil.checkEqualLength(string.length(), 8);
            DataCheckerUtil.checkDecNumberFormat(string);
            String string2 = jSONObject.getString("sn");
            checkEmpty(string2);
            DataCheckerUtil.checkLessEqualLength(string2.length(), 64);
            String string3 = jSONObject.getString("iu");
            checkEmpty(string3);
            DataCheckerUtil.checkLessEqualLength(string3.length(), 255);
            DataCheckerUtil.checkUrlCharFormat(string3);
            String string4 = jSONObject.getString("spn");
            checkEmpty(string4);
            DataCheckerUtil.checkLessEqualLength(string4.length(), 64);
            boolean z = jSONObject.getBoolean("se");
            String string5 = jSONObject.getString("su");
            checkEmpty(string5);
            DataCheckerUtil.checkLessEqualLength(string5.length(), 255);
            DataCheckerUtil.checkUrlCharFormat(string5);
            String string6 = jSONObject.getString("st");
            DataCheckerUtil.checkEqualLength(string6.length(), 2);
            DataCheckerUtil.checkDecNumberFormat(string6);
            if (string6.equals("01")) {
                boolean z2 = jSONObject.getBoolean("vv");
                String string7 = jSONObject.getString("pu");
                checkEmpty(string7);
                DataCheckerUtil.checkLessEqualLength(string7.length(), 5);
                arrayList.add(new Result.FpServiceItem(string, string2, string3, string4, z, string5, string6, z2, string7));
            } else {
                arrayList.add(new Result.FpServiceItem(string, string2, string3, string4, z, string5, string6, false, ""));
            }
        }
        return new Result(arrayList);
    }

    FpServiceProtocol(NetworkExpert net) {
        super(net);
    }

    private void checkEmpty(String str) throws NetworkExpertException {
        if (str == null || str.isEmpty()) {
            throw new NetworkExpertException(getClass(), 1792, "felica-pocket information response message analysis error cause by value is null", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }
}
