package com.felicanetworks.mfm.main.model.internal.main.net;

import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import com.felicanetworks.semc.sws.SwsRequest;
import java.util.HashMap;

/* JADX INFO: loaded from: classes3.dex */
public class OsaifulifePlusImageProtocol extends ImageProtocol {

    public static class Parameter {
        public int connectTimeout;
        public int readTimeOut;
        public String url;

        public Parameter(String url, int connectTimeout, int readTimeOut) {
            this.url = url;
            this.connectTimeout = connectTimeout;
            this.readTimeOut = readTimeOut;
        }

        public String toString() {
            return "ImageProtocol$Parameter{url:" + this.url + "'connectTimeout:" + this.connectTimeout + "'readTimeOut:" + this.readTimeOut + "'}";
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            return new NetworkExpert.Request(parameter.url, SwsRequest.HTTP_METHOD_GET, map, parameter.connectTimeout, parameter.readTimeOut, null);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 258, e, "image request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    OsaifulifePlusImageProtocol(NetworkExpert net) {
        super(net);
    }
}
