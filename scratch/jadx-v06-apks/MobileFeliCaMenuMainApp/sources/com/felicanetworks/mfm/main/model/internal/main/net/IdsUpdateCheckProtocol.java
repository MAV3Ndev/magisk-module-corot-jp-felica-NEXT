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
import java.net.URLEncoder;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class IdsUpdateCheckProtocol extends Protocol {

    public static class Parameter {
        public String idm;
        public String lud;
        public String plKind = (String) Sg.getValue(Sg.Key.SETTING_PLATFORM_TYPE);
        public String appId = (String) Sg.getValue(Sg.Key.SETTING_MFM_ID);
        public String isCode = (String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE);
        public String pfVersion = Build.VERSION.RELEASE;
        public String appVersion = Information.simpleVersionName();

        public Parameter(String str, String str2) {
            this.idm = str;
            this.lud = str2;
        }

        public String toString() {
            return "Parameter{plKind='" + this.plKind + "', pfVersion='" + this.pfVersion + "', isCode='" + this.isCode + "', appId='" + this.appId + "', appVersion='" + this.appVersion + "', idm='" + this.idm + "', lud='" + this.lud + "'}";
        }
    }

    public static class Result {
        public static final String UPDATE_NECESSITY_NO = "0";
        public static final String UPDATE_NECESSITY_YES = "1";
        public String updateInterval;
        public String updateNecessity;

        private Result(String str, String str2) {
            this.updateInterval = str;
            this.updateNecessity = str2;
        }

        public boolean isNecessity() {
            return "1".equals(this.updateNecessity);
        }

        public String toString() {
            return "IdsUpdateCheckProtocol$Result{updateInterval:" + this.updateInterval + ",updateNecessity:" + this.updateNecessity + "}";
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_SIM_UC_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_SIM_UC_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_SIM_UC_READ_TIMEOUT)).intValue();
            parameter.pfVersion = URLEncoder.encode(parameter.pfVersion, StringUtil.UTF_8);
            DataCheckerUtil.checkLessEqualLength(parameter.pfVersion.length(), 8);
            DataCheckerUtil.checkAlphaSignFormat(parameter.pfVersion);
            byte[] bytes = ("pt=" + parameter.plKind + "&pv=" + parameter.pfVersion + "&i=" + parameter.isCode + "&ai=" + parameter.appId + "&av=" + parameter.appVersion + "&idm=" + parameter.idm + "&lud=" + parameter.lud).getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            map.put("content-length", Integer.toString(bytes.length));
            return new NetworkExpert.Request(str, "GET", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "identification information update necessity confirmation request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        try {
            if (response.code != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (Integer.parseInt(response.header.get("content-length")) != response.data.length) {
                throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            String[] strArrSplit = new String(response.data, StringUtil.UTF_8).split("\r\n");
            if (strArrSplit.length != 2) {
                throw new NetworkExpertException(getClass(), 515, "number of the data injustice", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
            DataCheckerUtil.checkLessEqualLength(strArrSplit[1].length(), 6);
            DataCheckerUtil.checkDecNumberFormat(strArrSplit[1]);
            DataCheckerUtil.checkFixValue(strArrSplit[0], new String[]{"0", "1"});
            return new Result(strArrSplit[1], strArrSplit[0]);
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            if (e.getErrorId() == 0) {
                throw new NetworkExpertException(getClass(), 516, e, "identification information update necessity confirmation response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            if (1 != e.getErrorId()) {
                return null;
            }
            throw new NetworkExpertException(getClass(), 517, e, "identification information update necessity confirmation response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
        } catch (NetworkExpertException e2) {
            LogUtil.warning(e2);
            throw e2;
        } catch (Exception e3) {
            LogUtil.warning(e3);
            throw new NetworkExpertException(getClass(), 518, e3, "identification information update necessity confirmation response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    protected IdsUpdateCheckProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
