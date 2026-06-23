package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class RegisterDeviceProtocol extends Protocol {

    public static class Parameter {
        public static final String OS = "android";
        public String registrationId;
        public String app_id = NoticeManager.APP_ID;
        public String secret_key = NoticeManager.SECRET_KEY;
        public String environment = "production";

        public Parameter(String str) {
            this.registrationId = str;
        }

        public String toString() {
            return "RegisterDeviceProtocol$Parameter{registrationId:" + this.registrationId + "}";
        }
    }

    public static class Result {
        public String appId;
        public String code;
        public String id;
        public String registrationId;

        private Result(String str, String str2, String str3, String str4) {
            this.id = str;
            this.appId = str2;
            this.code = str3;
            this.registrationId = str4;
        }

        public String toString() {
            return "RegisterDeviceProtocol$Result{id:" + this.id + ",appId:" + this.appId + ",code:" + this.code + ",registrationId:" + this.registrationId + "}";
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_CLIENTS_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_CLIENTS_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_CLIENTS_READ_TIMEOUT)).intValue();
            byte[] bytes = ("environment=" + parameter.environment + "&secret=" + parameter.secret_key + "&applicationId=" + parameter.app_id + "&token=" + parameter.registrationId + "&os=" + Parameter.OS).getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("content-length", Integer.toString(bytes.length));
            map.put("content-type", "application/x-www-form-urlencoded");
            map.put("Accept", "application/json");
            return new NetworkExpert.Request(str, "POST", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "device Information registration necessity confirmation request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        String string;
        String string2;
        String str;
        String string3;
        try {
            if (response.code < 200 || response.code >= 300) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (response.header.get("content-length") != null && Integer.parseInt(response.header.get("content-length")) != response.data.length) {
                throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            JSONObject jSONObject = new JSONObject(new String(response.data, StringUtil.UTF_8));
            if (jSONObject.isNull("id")) {
                string = null;
            } else {
                string = jSONObject.getString("id");
                if (string.length() == 0) {
                    string = "";
                }
            }
            if (jSONObject.isNull(Protocol.CLIENT_APPID_KEY)) {
                string2 = null;
            } else {
                string2 = jSONObject.getString(Protocol.CLIENT_APPID_KEY);
                if (string2.length() == 0) {
                    string2 = "";
                }
            }
            if (jSONObject.isNull("code")) {
                str = null;
            } else {
                String string4 = jSONObject.getString("code");
                str = string4.length() == 0 ? "" : string4;
            }
            if (jSONObject.isNull(Protocol.CLIENT_TOKEN_KEY)) {
                string3 = null;
            } else {
                string3 = jSONObject.getString(Protocol.CLIENT_TOKEN_KEY);
                if (string3.length() == 0) {
                    string3 = "";
                }
            }
            return new Result(string, string2, str, string3);
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new NetworkExpertException(getClass(), 515, e2, "device Information registration response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    RegisterDeviceProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
