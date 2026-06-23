package com.felicanetworks.mfc.mfi.fws;

import android.se.omapi.Channel;
import android.util.Base64;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class a {
    public static final byte[] a = {-96, 0, 0, 6, -128, 2, 0, 0, 127, 0, 0, 0, 0, 1, 0, 0};
    private static final String b = "typ";
    private static final String c = "alg";
    private static final String d = "kid";
    private static final String e = "exp";
    private static final String f = "JOSE";
    private static final String g = "RS256";
    private static final long h = 600;
    private static final String i = "WrQyNte4x38DOX59VDYHuY86vZ2QIVh7";
    private byte[] j;

    public void a(GpController gpController) throws Exception {
        b(gpController);
        c();
    }

    public void a(Felica felica) throws Exception {
        b(felica);
        c();
    }

    public String a(JSONObject jSONObject) {
        byte[] bArr = this.j;
        if (bArr == null) {
            return null;
        }
        try {
            PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(bArr, 0)));
            String str = Base64Util.encode(d().toString().getBytes()) + "." + Base64Util.encode(jSONObject.toString().getBytes());
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyGeneratePrivate);
            signature.update(str.getBytes());
            return str + "." + Base64Util.encode(signature.sign());
        } catch (Exception e2) {
            LogMgr.printStackTrace(2, e2);
            return null;
        }
    }

    public String a(byte[] bArr, GpController gpController) {
        try {
            b(gpController);
            byte[][] bArrB = b();
            byte[] bArrA = a(bArrB[0], bArrB[1], FlavorConst.DEFINE_3);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, new SecretKeySpec(bArrA, "AES"), new IvParameterSpec(bArrB[1]));
            return new String(cipher.doFinal(bArr), StringUtil.UTF_8);
        } catch (Exception e2) {
            LogMgr.printStackTrace(2, e2);
            return null;
        }
    }

    public String a(byte[] bArr, Felica felica) {
        try {
            b(felica);
            byte[][] bArrB = b();
            byte[] bArrA = a(bArrB[0], bArrB[1], FlavorConst.DEFINE_3);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, new SecretKeySpec(bArrA, "AES"), new IvParameterSpec(bArrB[1]));
            return new String(cipher.doFinal(bArr), StringUtil.UTF_8);
        } catch (Exception e2) {
            LogMgr.printStackTrace(2, e2);
            return null;
        }
    }

    public void a() {
        byte[] bArr = this.j;
        if (bArr != null) {
            Arrays.fill(bArr, (byte) 0);
            this.j = null;
        }
    }

    private void b(GpController gpController) throws Exception {
        Channel channelOpenLogicalChannel = gpController.getOmapiSession().openLogicalChannel(a);
        if (channelOpenLogicalChannel != null) {
            channelOpenLogicalChannel.close();
        }
    }

    private void b(Felica felica) throws Exception {
        try {
            felica.open();
            felica.select(65039);
            felica.getContainerId();
        } finally {
            if (felica != null) {
                felica.close();
            }
        }
    }

    private byte[][] b() throws Exception {
        byte[] bArr = new byte[16];
        byte[] bArr2 = new byte[16];
        byte[] bArrDigest = MessageDigest.getInstance("SHA-256").digest(i.getBytes());
        System.arraycopy(bArrDigest, 0, bArr2, 0, 16);
        System.arraycopy(bArrDigest, 16, bArr, 0, 16);
        return new byte[][]{bArr, bArr2};
    }

    private void c() {
        try {
            byte[][] bArrB = b();
            this.j = a(bArrB[0], bArrB[1], FlavorConst.DEFINE_2);
        } catch (Exception e2) {
            LogMgr.printStackTrace(2, e2);
        }
    }

    private byte[] a(byte[] bArr, byte[] bArr2, String str) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr2);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(Base64Util.decode(str));
        } catch (Exception e2) {
            LogMgr.printStackTrace(2, e2);
            return null;
        }
    }

    private JSONObject d() {
        JSONObject jSONObject = new JSONObject();
        try {
            long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) + h;
            jSONObject.put(b, f);
            jSONObject.put(c, g);
            jSONObject.put(d, FlavorConst.DEFINE_1);
            jSONObject.put(e, jCurrentTimeMillis);
        } catch (JSONException e2) {
            LogMgr.printStackTrace(2, e2);
        }
        return jSONObject;
    }
}
