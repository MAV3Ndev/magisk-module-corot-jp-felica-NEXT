package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class BannerFileProtocol extends Protocol {

    public static class Parameter {
        public String fileName = (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_FILE_NAME);
        public Type type;

        public enum Type {
            MY_SERVICE,
            NOTIFICATION;

            String posId() {
                return equals(MY_SERVICE) ? "0" : "1";
            }
        }

        public Parameter(Type type) {
            this.type = type;
        }

        public String toString() {
            return "Parameter{fileName='" + this.fileName + "', type=" + this.type + '}';
        }
    }

    public static class Result {
        public List<Banner> banners;
        public String update;

        /* synthetic */ Result(String str, List list, AnonymousClass1 anonymousClass1) {
            this(str, list);
        }

        private Result(String str, List<Banner> list) {
            this.update = str;
            this.banners = list;
        }

        public String toString() {
            return "BannerFileProtocol$Result{update:" + this.update + ",banners:" + this.banners + "}";
        }

        public static class Banner {
            public String end;
            public String id;
            public String imgName;
            public String scheme;
            public String start;
            public String updated;

            /* synthetic */ Banner(String str, String str2, String str3, String str4, String str5, String str6, AnonymousClass1 anonymousClass1) {
                this(str, str2, str3, str4, str5, str6);
            }

            private Banner(String str, String str2, String str3, String str4, String str5, String str6) {
                this.id = str;
                this.imgName = str2;
                this.scheme = str3;
                this.start = str4;
                this.end = str5;
                this.updated = str6;
            }

            public String toString() {
                return "Banner[id:" + this.id + ",imgName:" + this.imgName + ",scheme:" + this.scheme + ",start:" + this.start + ",end:" + this.end + ",updated:" + this.updated + "]";
            }
        }
    }

    /* JADX INFO: renamed from: com.felicanetworks.mfm.main.model.internal.main.net.BannerFileProtocol$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerFileProtocol$Parameter$Type;

        static {
            int[] iArr = new int[Parameter.Type.values().length];
            $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerFileProtocol$Parameter$Type = iArr;
            try {
                iArr[Parameter.Type.MY_SERVICE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerFileProtocol$Parameter$Type[Parameter.Type.NOTIFICATION.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    public NetworkExpert.Request create(Parameter parameter) {
        String str;
        int i = AnonymousClass1.$SwitchMap$com$felicanetworks$mfm$main$model$internal$main$net$BannerFileProtocol$Parameter$Type[parameter.type.ordinal()];
        if (i == 1) {
            str = (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_SERVICE_URL);
        } else {
            str = i != 2 ? null : (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_NOTICE_URL);
        }
        String str2 = str + parameter.fileName;
        int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_SESSION_TIMEOUT)).intValue();
        int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_READ_TIMEOUT)).intValue();
        HashMap map = new HashMap();
        map.put("user-agent", getUserAgent());
        NetworkExpert.Request request = new NetworkExpert.Request(str2, "GET", map, iIntValue, iIntValue2, null);
        return BasicAuthentication.isNeedBasicAuthentication() ? BasicAuthentication.addBasicAuthorization(request) : request;
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        String string = "";
        ArrayList arrayList = new ArrayList();
        try {
        } catch (DataCheckerException e) {
            LogUtil.warning(e);
            if (e.getErrorId() == 0) {
                throw new NetworkExpertException(getClass(), 515, e, "banner information file response message analysis data length fraud", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            if (1 == e.getErrorId()) {
                throw new NetworkExpertException(getClass(), 516, e, "banner information response message analysis data Attribute fraud", NetworkExpertException.Type.ID_ATTRIBUTE_ERROR);
            }
        } catch (NetworkExpertException e2) {
            LogUtil.warning(e2);
            throw e2;
        } catch (Exception e3) {
            LogUtil.warning(e3);
            throw new NetworkExpertException(getClass(), 517, e3, "banner information file response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
        if (response.code != 200) {
            throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "A response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
        }
        if (Integer.parseInt(response.header.get("content-length")) != response.data.length) {
            throw new NetworkExpertException(getClass(), 514, "Data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
        }
        JSONObject jSONObject = new JSONObject(new String(response.data, StringUtil.UTF_8));
        string = jSONObject.getString("updated");
        DataCheckerUtil.checkEqualLength(string.length(), 12);
        DataCheckerUtil.checkDecNumberFormat(string);
        JSONArray jSONArray = new JSONArray(jSONObject.getString("banner"));
        for (int i = 0; i < jSONArray.length(); i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            String string2 = jSONObject2.getString("id");
            DataCheckerUtil.checkEqualLength(string2.getBytes(StringUtil.UTF_8).length, 10);
            DataCheckerUtil.checkAlphaNumberFormat(string2);
            String string3 = jSONObject2.getString("img");
            DataCheckerUtil.checkLessEqualLength(string3.getBytes(StringUtil.UTF_8).length, 128);
            if (!string3.matches("[0-9a-zA-Z-_.]+")) {
                throw new DataCheckerException(DataCheckerUtil.class, 518, 1, string3);
            }
            String string4 = jSONObject2.getString("ls");
            DataCheckerUtil.checkLessEqualLength(string4.getBytes(StringUtil.UTF_8).length, 1024);
            String string5 = jSONObject2.getString(LogSender.EXTRA_VALUE_EVENT_NAME_START);
            DataCheckerUtil.checkEqualLength(string5.length(), 12);
            DataCheckerUtil.checkDecNumberFormat(string5);
            String string6 = jSONObject2.getString("end");
            DataCheckerUtil.checkEqualLength(string6.length(), 12);
            DataCheckerUtil.checkDecNumberFormat(string6);
            String string7 = jSONObject2.getString("updated");
            DataCheckerUtil.checkEqualLength(string7.length(), 12);
            DataCheckerUtil.checkDecNumberFormat(string7);
            arrayList.add(new Result.Banner(string2, string3, string4, string5, string6, string7, null));
        }
        return new Result(string, arrayList, null);
    }

    BannerFileProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
