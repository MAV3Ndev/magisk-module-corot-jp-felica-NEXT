package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.agent.TransitPassInfoAgent;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class RegisterTagProtocol extends Protocol {

    public static class Parameter {
        public String clientId;
        public String code;
        public String name;
        public String value;

        public Parameter(String str, String str2, String str3, String str4) {
            this.clientId = str;
            this.code = str2;
            this.name = str3;
            this.value = str4;
        }

        public String toString() {
            return "RegisterTagProtocol$Parameter{clientId:" + this.clientId + ",code:" + this.code + ",name:" + this.name + ",value:" + this.value + "}";
        }
    }

    public static class Result {
        public String clientId;
        public String value;

        private Result(String str, String str2) {
            this.clientId = str;
            this.value = str2;
        }

        public String toString() {
            return "RegisterTagProtocol$Result{clientId:" + this.clientId + ",value:" + this.value + "}";
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_TAGS_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_TAGS_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_TAGS_READ_TIMEOUT)).intValue();
            byte[] bytes = ("name=" + parameter.name + "&clientId=" + parameter.clientId + "&value=" + parameter.value + "&code=" + parameter.code).getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("content-length", Integer.toString(bytes.length));
            map.put("content-type", "application/x-www-form-urlencoded");
            map.put("Accept", "application/json");
            return new NetworkExpert.Request(str, "POST", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "tag Information registration necessity confirmation request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        String string;
        try {
            if (response.code < 200 || response.code >= 300) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "a response cord is unjust", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (response.header.get("content-length") != null && Integer.parseInt(response.header.get("content-length")) != response.data.length) {
                throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            JSONObject jSONObject = new JSONObject(new String(response.data, StringUtil.UTF_8));
            String str = "";
            if (jSONObject.isNull("clientId")) {
                string = null;
            } else {
                string = jSONObject.getString("clientId");
                if (string.length() == 0) {
                    string = "";
                }
            }
            if (jSONObject.isNull(TransitPassInfoAgent.OPTIONAL_INFO_VALUE)) {
                str = null;
            } else {
                String string2 = jSONObject.getString(TransitPassInfoAgent.OPTIONAL_INFO_VALUE);
                if (string2.length() != 0) {
                    str = string2;
                }
            }
            return new Result(string, str);
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new NetworkExpertException(getClass(), 515, e2, "tag Information registration response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    RegisterTagProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
