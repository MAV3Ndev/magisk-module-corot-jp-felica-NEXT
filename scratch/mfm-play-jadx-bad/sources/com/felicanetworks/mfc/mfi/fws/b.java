package com.felicanetworks.mfc.mfi.fws;

import android.util.Base64;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.android.gms.fido.u2f.api.common.ClientData;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class b {
    public String a(JSONObject var1) {
        try {
            PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(FlavorConst.DEFINE_5, 0)));
            String str = Base64Util.encode(a().toString().getBytes()) + "." + Base64Util.encode(var1.toString().getBytes());
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyGeneratePrivate);
            signature.update(str.getBytes());
            return str + "." + Base64Util.encode(signature.sign());
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
            return null;
        }
    }

    private JSONObject a() {
        JSONObject jSONObject = new JSONObject();
        try {
            long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) + 60;
            jSONObject.put(ClientData.KEY_TYPE, "JOSE");
            jSONObject.put("alg", "RS256");
            jSONObject.put("kid", FlavorConst.DEFINE_4);
            jSONObject.put("exp", jCurrentTimeMillis);
            return jSONObject;
        } catch (JSONException e) {
            LogMgr.printStackTrace(2, e);
            return jSONObject;
        }
    }
}
