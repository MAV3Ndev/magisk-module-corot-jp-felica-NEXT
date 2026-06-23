package com.felicanetworks.mfm.main.model.internal.main.net;

import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpert;
import com.felicanetworks.mfm.main.model.internal.main.net.NetworkExpertException;
import java.util.HashMap;

/* JADX INFO: loaded from: classes.dex */
public class OsaifulifePlusImageProtocol extends ImageProtocol {

    public static class Parameter {
        public int connectTimeout;
        public int readTimeOut;
        public String url;

        public Parameter(String str, int i, int i2) {
            this.url = str;
            this.connectTimeout = i;
            this.readTimeOut = i2;
        }

        public String toString() {
            return "ImageProtocol$Parameter{url:" + this.url + "'connectTimeout:" + this.connectTimeout + "'readTimeOut:" + this.readTimeOut + "'}";
        }
    }

    public NetworkExpert.Request create(Parameter parameter) throws NetworkExpertException {
        try {
            HashMap map = new HashMap();
            map.put("user-agent", getUserAgent());
            return new NetworkExpert.Request(parameter.url, "GET", map, parameter.connectTimeout, parameter.readTimeOut, null);
        } catch (Exception e) {
            throw new NetworkExpertException(getClass(), 258, e, "image request message generation failed.", NetworkExpertException.Type.ID_OTHER_ERROR);
        }
    }

    OsaifulifePlusImageProtocol(NetworkExpert networkExpert) {
        super(networkExpert);
    }
}
