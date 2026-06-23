package com.felicanetworks.mfm.main.model.internal.main.net;

import android.content.Context;
import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.util.HashMap;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class RegisterEventProtocol extends Protocol {

    public static class Parameter {
        public String clientId;
        public String code;
        public Context context;
        public String name;

        public Parameter(String str, String str2, String str3, Context context) {
            this.clientId = str;
            this.code = str2;
            this.name = str3;
            this.context = context;
        }

        public String toString() {
            return "Parameter{clientId='" + this.clientId + "', code='" + this.code + "', name='" + this.name + "'}";
        }
    }

    public static class Result {
        public String clientId;

        private Result(String str) {
            this.clientId = str;
        }

        public String toString() {
            return "RegisterEventProtocol$Result{clientId:" + this.clientId + ",}";
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        String id;
        try {
            String str = (String) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_EVENTS_URL);
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_EVENTS_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_PUSH_REGISTER_EVENTS_READ_TIMEOUT)).intValue();
            try {
                id = AdvertisingIdClient.getAdvertisingIdInfo(parameter.context).getId();
            } catch (Exception unused) {
                id = "UNKNOWN";
            }
            byte[] bytes = ("name=" + parameter.name + "&clientId=" + parameter.clientId + "&code=" + parameter.code + "&value=" + id).getBytes(StringUtil.UTF_8);
            HashMap map = new HashMap();
            map.put("content-length", Integer.toString(bytes.length));
            map.put("content-type", "application/x-www-form-urlencoded");
            map.put("Accept", "application/json");
            return new NetworkExpert.Request(str, "POST", map, iIntValue, iIntValue2, bytes);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "event Information registration necessity confirmation request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
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
            if (jSONObject.isNull("clientId")) {
                string = null;
            } else {
                string = jSONObject.getString("clientId");
                if (string.length() == 0) {
                    string = "";
                }
            }
            return new Result(string);
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new NetworkExpertException(getClass(), 515, e2, "event Information registration response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    RegisterEventProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
