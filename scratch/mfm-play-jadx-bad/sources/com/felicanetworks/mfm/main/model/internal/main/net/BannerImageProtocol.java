package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class BannerImageProtocol extends Protocol {

    public static class Parameter {
        public String fileName;
        public Type type;

        public enum Type {
            MY_SERVICE,
            NOTIFICATION
        }

        public Parameter(Type type, String fileName) {
            this.type = type;
            this.fileName = fileName;
        }

        public String toString() {
            return "BannerImageProtocol$Parameter{pos:" + this.type + ",imgName:" + this.fileName + "}";
        }
    }

    public static class Result {
        public byte[] binary;

        private Result(byte[] binary) {
            this.binary = (byte[]) binary.clone();
        }

        public String toString() {
            return "BannerImageProtocol$Result{binary size:" + this.binary.length + "}";
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
        int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_IMAGE_SESSION_TIMEOUT)).intValue();
        int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_AIM_BANNER_IMAGE_READ_TIMEOUT)).intValue();
        HashMap map = new HashMap();
        map.put("user-agent", getUserAgent());
        NetworkExpert.Request request = new NetworkExpert.Request(str2, SwsRequest.HTTP_METHOD_GET, map, iIntValue, iIntValue2, null);
        return BasicAuthentication.isNeedBasicAuthentication() ? BasicAuthentication.addBasicAuthorization(request) : request;
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        try {
            if (response.code != 200) {
                throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "A response cord is unjust :", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
            }
            if (Integer.parseInt(response.header.get("content-length")) != response.data.length) {
                throw new NetworkExpertException(getClass(), 514, "Data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
            }
            return new Result(response.data);
        } catch (NetworkExpertException e) {
            LogUtil.warning(e);
            throw e;
        } catch (Exception e2) {
            LogUtil.warning(e2);
            throw new NetworkExpertException(getClass(), 515, e2, "banner information file response message analysis other error", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    BannerImageProtocol(NetworkExpert net) {
        super(net);
    }
}
