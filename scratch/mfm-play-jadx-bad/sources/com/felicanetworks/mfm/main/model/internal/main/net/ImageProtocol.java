package com.felicanetworks.mfm.main.model.internal.main.net;

import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.semc.sws.SwsRequest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class ImageProtocol extends Protocol {

    public static class Parameter {
        public String url;

        public Parameter(String url) {
            this.url = url;
        }

        public String toString() {
            return "ImageProtocol$Parameter{url:" + this.url + "}";
        }
    }

    public static class Result {
        public byte[] bytes;

        private Result(byte[] bytes) {
            this.bytes = bytes != null ? (byte[]) bytes.clone() : new byte[0];
        }

        public String toString() {
            return "ImageProtocol$Result{bytes size:" + this.bytes.length + "}";
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            int iIntValue = ((Integer) Sg.getValue(Sg.Key.NET_EXTRA_GET_IMAGE_SESSION_TIMEOUT)).intValue();
            int iIntValue2 = ((Integer) Sg.getValue(Sg.Key.NET_EXTRA_GET_IMAGE_READ_TIMEOUT)).intValue();
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            return new NetworkExpert.Request(parameter.url, SwsRequest.HTTP_METHOD_GET, map, iIntValue, iIntValue2, null);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 257, e, "image request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    public Result parse(NetworkExpert.Response response) throws NetworkExpertException {
        if (response.code != 200) {
            throw new NetworkExpertException(getClass(), InputDeviceCompat.SOURCE_DPAD, "", NetworkExpertException.Type.ID_COMMUNICATION_ERROR);
        }
        if (response.header.get("content-length") != null && Integer.parseInt(response.header.get("content-length")) != response.data.length) {
            throw new NetworkExpertException(getClass(), 514, "data number of bytes injustice", NetworkExpertException.Type.ID_LENGTH_ERROR);
        }
        return new Result(response.data);
    }

    ImageProtocol(NetworkExpert net) {
        super(net);
    }
}
