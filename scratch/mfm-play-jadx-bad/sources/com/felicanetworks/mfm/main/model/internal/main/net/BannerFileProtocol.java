package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfc.mfi.LogSender;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerException;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DataCheckerUtil;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class BannerFileProtocol extends Protocol {

    public static class Parameter {
        public String fileName = (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_FILE_NAME);
        public Type type;

        public enum Type {
            MY_SERVICE,
            NOTIFICATION;

            String posId() {
                if (equals(MY_SERVICE)) {
                    return "0";
                }
                return "1";
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

        private Result(String update, List<Banner> banners) {
            this.update = update;
            this.banners = banners;
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

            private Banner(String id, String imgName, String scheme, String start, String end, String updated) {
                this.id = id;
                this.imgName = imgName;
                this.scheme = scheme;
                this.start = start;
                this.end = end;
                this.updated = updated;
            }

            public String toString() {
                return "Banner[id:" + this.id + ",imgName:" + this.imgName + ",scheme:" + this.scheme + ",start:" + this.start + ",end:" + this.end + ",updated:" + this.updated + "]";
            }
        }
    }

    public NetworkExpert.Request create(Parameter parameter) {
        String str;
        int iOrdinal = parameter.type.ordinal();
        if (iOrdinal == 0) {
            str = (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_SERVICE_URL);
        } else {
            str = iOrdinal != 1 ? null : (String) Sg.getValue(Sg.Key.NET_AIM_BANNER_NOTICE_URL);
        }
        String str2 = str + parameter.fileName;
        int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_SESSION_TIMEOUT)).intValue();
        int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_READ_TIMEOUT)).intValue();
        HashMap map = new HashMap();
        map.put("user-agent", getUserAgent());
        NetworkExpert.Request request = new NetworkExpert.Request(str2, SwsRequest.HTTP_METHOD_GET, map, iIntValue, iIntValue2, null);
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
            String string4 = jSONObject2.getString(NoticeInfo.INTENT_EXTRAS_KEY_LINKAGE_SCHEME);
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
            arrayList.add(new Result.Banner(string2, string3, string4, string5, string6, string7));
        }
        return new Result(string, arrayList);
    }

    BannerFileProtocol(NetworkExpert net) {
        super(net);
    }
}
