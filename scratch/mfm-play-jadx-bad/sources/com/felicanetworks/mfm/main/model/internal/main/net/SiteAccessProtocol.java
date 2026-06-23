package com.felicanetworks.mfm.main.model.internal.main.net;

import android.os.Build;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.fix.Information;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.net.URLEncoder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class SiteAccessProtocol extends Protocol {

    public static class Parameter {
        public static final String siteAccessKind = "01";
        public String appId;
        public String appVersion;
        public String isCode;
        public String pfVersion;
        public String plKind;
        public String sai;
        public String serviceId;
        public String serviceVersion;

        public Parameter(String serviceId, String serviceVersion, String categoryId, String status, boolean compatible) {
            this.serviceId = serviceId;
            this.serviceVersion = serviceVersion;
            StringBuilder sb = new StringBuilder();
            sb.append(categoryId);
            sb.append(status);
            sb.append(compatible ? "" : Sg.getValue(Sg.Key.NET_SIM_SA_SAI_SIGN_OF_UNIDENTIFIED));
            this.sai = sb.toString();
            this.plKind = (String) Sg.getValue(Sg.Key.SETTING_PLATFORM_TYPE);
            this.pfVersion = Build.VERSION.RELEASE;
            this.isCode = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
            this.appId = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
            this.appVersion = Information.simpleVersionName();
        }

        public String toString() {
            return "Parameter{plKind='" + this.plKind + "', pfVersion='" + this.pfVersion + "', isCode='" + this.isCode + "', appId='" + this.appId + "', appVersion='" + this.appVersion + "', serviceId='" + this.serviceId + "', serviceVersion='" + this.serviceVersion + "', sai='" + this.sai + "'}";
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_SIM_SA_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_SIM_SA_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_SIM_SA_READ_TIMEOUT)).intValue();
            parameter.pfVersion = URLEncoder.encode(parameter.pfVersion, StringUtil.UTF_8);
            DataCheckerUtil.checkLessEqualLength(parameter.appVersion.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(parameter.appVersion);
            byte[] bytes = ("pt=" + parameter.plKind + "&pv=" + parameter.pfVersion + "&i=" + parameter.isCode + "&ai=" + parameter.appId + "&av=" + parameter.appVersion + "&sat=01&si=" + parameter.serviceId + "&sv=" + parameter.serviceVersion + "&sai=" + parameter.sai).getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            map.put("content-length", Integer.toString(bytes.length));
            return new NetworkExpert.Request(str, SwsRequest.HTTP_METHOD_GET, map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "site access request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    SiteAccessProtocol(NetworkExpert net) {
        super(net);
    }
}
