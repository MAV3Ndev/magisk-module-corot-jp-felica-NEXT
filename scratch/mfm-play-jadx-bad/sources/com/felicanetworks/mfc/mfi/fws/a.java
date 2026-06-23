package com.felicanetworks.mfc.mfi.fws;

import android.se.omapi.Channel;
import android.se.omapi.Session;
import android.util.Base64;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.util.StringUtil;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.util.Base64Util;
import com.felicanetworks.mfc.mfi.util.SettingInfo;
import com.felicanetworks.mfc.util.LogMgr;
import com.google.android.gms.fido.u2f.api.common.ClientData;
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

/* JADX INFO: loaded from: classes3.dex */
public class a {
    private static final int RETRY_COUNT_FOR_OPEN_CHANNEL = 3;
    private static final int SLEEP_BEFORE_RETRY_OPEN_CHANNEL = 100;
    public static final byte[] a = {-96, 0, 0, 6, -128, 2, 0, 0, 127, 0, 0, 0, 0, 1, 0, 0};
    private byte[] j;

    public void a(GpController var1) throws Exception {
        b(var1);
        c();
    }

    public void a(Felica var1) throws Exception {
        b(var1);
        c();
    }

    public String a(JSONObject var1) {
        byte[] bArr = this.j;
        if (bArr == null) {
            return null;
        }
        try {
            PrivateKey privateKeyGeneratePrivate = KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(Base64.decode(bArr, 0)));
            String str = Base64Util.encode(d().toString().getBytes()) + "." + Base64Util.encode(var1.toString().getBytes());
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initSign(privateKeyGeneratePrivate);
            signature.update(str.getBytes());
            return str + "." + Base64Util.encode(signature.sign());
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
            return null;
        }
    }

    public String a(byte[] var1, GpController var2) {
        try {
            b(var2);
            byte[][] bArrB = b();
            byte[] bArrA = a(bArrB[0], bArrB[1], SettingInfo.getJwsSignatureKeyForControlInfo());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, new SecretKeySpec(bArrA, "AES"), new IvParameterSpec(bArrB[1]));
            return new String(cipher.doFinal(var1), StringUtil.UTF_8);
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
            return null;
        }
    }

    public String a(byte[] var1, Felica var2) {
        try {
            b(var2);
            byte[][] bArrB = b();
            byte[] bArrA = a(bArrB[0], bArrB[1], SettingInfo.getJwsSignatureKeyForControlInfo());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, new SecretKeySpec(bArrA, "AES"), new IvParameterSpec(bArrB[1]));
            return new String(cipher.doFinal(var1), StringUtil.UTF_8);
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
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

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private void b(GpController var1) throws Exception {
        Channel channelOpenLogicalChannel = null;
        try {
            Session omapiSession = var1.getOmapiSession();
            for (int i = 0; i < 3; i++) {
                channelOpenLogicalChannel = omapiSession.openLogicalChannel(a);
                if (channelOpenLogicalChannel != null) {
                    break;
                }
                try {
                    LogMgr.log(2, "700 Access contention occurred, do retry.");
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                }
            }
        } finally {
            if (0 != 0) {
                ((Channel) null).close();
            }
        }
    }

    private void b(Felica var1) throws Exception {
        try {
            var1.open();
            var1.select(65039);
            var1.getContainerId();
        } finally {
            if (var1 != null) {
                var1.close();
            }
        }
    }

    private byte[][] b() throws Exception {
        byte[] bArr = new byte[16];
        byte[] bArr2 = new byte[16];
        byte[] bArrDigest = MessageDigest.getInstance("SHA-256").digest("WrQyNte4x38DOX59VDYHuY86vZ2QIVh7".getBytes());
        System.arraycopy(bArrDigest, 0, bArr2, 0, 16);
        System.arraycopy(bArrDigest, 16, bArr, 0, 16);
        return new byte[][]{bArr, bArr2};
    }

    private void c() {
        try {
            byte[][] bArrB = b();
            this.j = a(bArrB[0], bArrB[1], FlavorConst.DEFINE_2);
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
        }
    }

    private byte[] a(byte[] var1, byte[] var2, String var3) {
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(var2);
            SecretKeySpec secretKeySpec = new SecretKeySpec(var1, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            return cipher.doFinal(Base64Util.decode(var3));
        } catch (Exception e) {
            LogMgr.printStackTrace(2, e);
            return null;
        }
    }

    private JSONObject d() {
        JSONObject jSONObject = new JSONObject();
        try {
            long jCurrentTimeMillis = (System.currentTimeMillis() / 1000) + 600;
            jSONObject.put(ClientData.KEY_TYPE, "JOSE");
            jSONObject.put("alg", "RS256");
            jSONObject.put("kid", FlavorConst.DEFINE_1);
            jSONObject.put("exp", jCurrentTimeMillis);
            return jSONObject;
        } catch (JSONException e) {
            LogMgr.printStackTrace(2, e);
            return jSONObject;
        }
    }
}
