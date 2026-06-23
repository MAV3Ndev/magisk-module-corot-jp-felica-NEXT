package com.felicanetworks.semc.sws;

import android.util.Base64;
import com.felicanetworks.semc.FlavorConst;
import com.felicanetworks.semc.util.Base64Util;
import com.felicanetworks.semc.util.LogMgr;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class LogProcessor {
    private static final long EXTRA = 600;
    private static final String HEADER_PARAM_1 = "typ";
    private static final String HEADER_PARAM_2 = "alg";
    private static final String HEADER_PARAM_3 = "kid";
    private static final String HEADER_PARAM_4 = "exp";
    private static final String HEADER_VALUE_1 = "JOSE";
    private static final String HEADER_VALUE_2 = "RS256";
    private static final String WORD = "NbBJhdJ2yWtkKKJzqs54NwHXHH2juWVh";
    private byte[] mPreProcessData;

    public void loadWithCheck() {
        LogMgr.log(8, "000");
        load();
        LogMgr.log(8, "999");
    }

    public String process(JSONObject jSONObject) {
        LogMgr.log(8, "000");
        byte[] bArr = this.mPreProcessData;
        if (bArr == null) {
            LogMgr.log(2, "700 mPreProcessData is null");
            return null;
        }
        try {
            PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(bArr, 0)));
            String str = Base64Util.encode(createHeader().toString().getBytes(Charset.defaultCharset())) + "." + Base64Util.encode(jSONObject.toString().getBytes(Charset.defaultCharset()));
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyGeneratePrivate);
            signature.update(str.getBytes(Charset.defaultCharset()));
            LogMgr.log(8, "998");
            return str + "." + Base64Util.encode(signature.sign());
        } catch (Exception e) {
            LogMgr.log(2, "701 Exception occurred. e[" + e + "]");
            LogMgr.log(8, "999");
            return null;
        }
    }

    private byte[][] prepare() throws Exception {
        LogMgr.log(8, "000");
        byte[] bArr = new byte[16];
        byte[] bArr2 = new byte[16];
        byte[] bArrDigest = MessageDigest.getInstance("SHA-256").digest(WORD.getBytes(Charset.defaultCharset()));
        System.arraycopy(bArrDigest, 0, bArr2, 0, 16);
        System.arraycopy(bArrDigest, 16, bArr, 0, 16);
        LogMgr.log(8, "999");
        return new byte[][]{bArr, bArr2};
    }

    private void load() {
        LogMgr.log(8, "000");
        try {
            byte[][] bArrPrepare = prepare();
            this.mPreProcessData = preprocess(bArrPrepare[0], bArrPrepare[1], FlavorConst.DEFINE_4);
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
        }
        LogMgr.log(8, "999");
    }

    private byte[] preprocess(byte[] bArr, byte[] bArr2, String str) {
        LogMgr.log(8, "000");
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(Base64Util.decode(str));
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
            LogMgr.log(8, "999");
            return null;
        }
    }

    private JSONObject createHeader() {
        LogMgr.log(8, "000");
        JSONObject jSONObject = new JSONObject();
        try {
            long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) + EXTRA;
            jSONObject.put("typ", HEADER_VALUE_1);
            jSONObject.put(HEADER_PARAM_2, HEADER_VALUE_2);
            jSONObject.put(HEADER_PARAM_3, FlavorConst.DEFINE_3);
            jSONObject.put(HEADER_PARAM_4, jCurrentTimeMillis);
        } catch (JSONException e) {
            LogMgr.printStackTrace(2, e);
        }
        LogMgr.log(8, "999");
        return jSONObject;
    }
}
