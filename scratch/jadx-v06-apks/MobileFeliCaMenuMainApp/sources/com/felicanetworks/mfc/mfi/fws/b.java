package com.felicanetworks.mfc.mfi.fws;

import android.util.Base64;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private static final String a = "typ";
    private static final String b = "alg";
    private static final String c = "kid";
    private static final String d = "exp";
    private static final String e = "JOSE";
    private static final String f = "RS256";
    private static final long g = 60;

    public String a(JSONObject jSONObject) {
        try {
            PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(FlavorConst.DEFINE_5, 0)));
            String str = Base64Util.encode(a().toString().getBytes()) + "." + Base64Util.encode(jSONObject.toString().getBytes());
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyGeneratePrivate);
            signature.update(str.getBytes());
            return str + "." + Base64Util.encode(signature.sign());
        } catch (Exception e2) {
            LogMgr.printStackTrace(2, e2);
            return null;
        }
    }

    private JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) + g;
            jSONObject.put(a, e);
            jSONObject.put(b, f);
            jSONObject.put(c, FlavorConst.DEFINE_4);
            jSONObject.put(d, jCurrentTimeMillis);
        } catch (JSONException e2) {
            LogMgr.printStackTrace(2, e2);
        }
        return jSONObject;
    }
}
