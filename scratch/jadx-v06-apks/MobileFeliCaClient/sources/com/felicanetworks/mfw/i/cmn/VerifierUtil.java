package com.felicanetworks.mfw.i.cmn;

import android.util.Base64;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfw.i.fbl.Property;
import com.felicanetworks.mfw.i.fbl.VeriferUtilListener;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/* JADX INFO: loaded from: classes.dex */
public class VerifierUtil {
    public static void verify(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, int i3, VeriferUtilListener veriferUtilListener) {
        PublicKey publicKeyGeneratePublic;
        boolean zVerify;
        LogMgr.log(4, "%s : target = %s, targetLength = %d, sign = %s, signLength = %d, key = %s, keyLength = %d, listener = %s", "000", bArr, Integer.valueOf(i), bArr2, Integer.valueOf(i2), bArr3, Integer.valueOf(i3), veriferUtilListener);
        if (bArr == null || bArr2 == null || bArr3 == null || veriferUtilListener == null) {
            LogMgr.log(1, "%s : target = %s, sign = %s, key = %s, listener = %s", "800", bArr, bArr2, bArr3, veriferUtilListener);
            throw new SysException((Class<?>) VerifierUtil.class, "verify", "invalid parameter");
        }
        if (bArr.length == i && bArr2.length == i2) {
            if (bArr3.length == i3) {
                try {
                    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(bArr3);
                    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
                    synchronized (keyFactory) {
                        publicKeyGeneratePublic = keyFactory.generatePublic(x509EncodedKeySpec);
                    }
                    Signature signature = Signature.getInstance("SHA256withRSA");
                    synchronized (signature) {
                        signature.initVerify(publicKeyGeneratePublic);
                        signature.update(bArr, 0, i);
                        zVerify = signature.verify(bArr2);
                    }
                    if (zVerify) {
                        LogMgr.log(7, "%s", "001");
                        veriferUtilListener.ntfyVerificationEnd(0);
                    } else {
                        LogMgr.log(7, "%s", "002");
                        veriferUtilListener.ntfyVerificationEnd(1);
                    }
                    LogMgr.log(4, "%s", "999");
                    return;
                } catch (InvalidKeyException unused) {
                    LogMgr.log(1, "%s InvalidKeyException", "804");
                    throw new SysException((Class<?>) VerifierUtil.class, "verify", "invalid pub key.");
                } catch (NoSuchAlgorithmException e) {
                    LogMgr.log(1, "%s %s", "802", e.toString());
                    throw new SysException((Class<?>) VerifierUtil.class, "verify", "key factory generation failed.");
                } catch (SignatureException unused2) {
                    LogMgr.log(1, "%s SignatureException", "805");
                    throw new SysException((Class<?>) VerifierUtil.class, "verify", "instance sig initialization failed.");
                } catch (InvalidKeySpecException unused3) {
                    LogMgr.log(1, "%s InvalidKeySpecException", "803");
                    throw new SysException((Class<?>) VerifierUtil.class, "verify", "pub key extraction failed.");
                }
            }
        }
        LogMgr.log(1, "%s : target.length = %d, targetLength = %d, sign.length = %d, signLength = %d, key.length = %d, keyLength = %d", "801", Integer.valueOf(bArr.length), Integer.valueOf(i), Integer.valueOf(bArr2.length), Integer.valueOf(i2), Integer.valueOf(bArr3.length), Integer.valueOf(i3));
        throw new SysException((Class<?>) VerifierUtil.class, "verify", "invalid parameter");
    }

    public static void getKeyValue(String str, String str2, VeriferUtilListener veriferUtilListener) {
        LogMgr.log(4, "%s : issuerId = %s, keyId = %s, listener = %s", "000", str, str2, veriferUtilListener);
        if (str == null || str2 == null || veriferUtilListener == null) {
            LogMgr.log(1, "800 issuerId = %s, keyId = %s, listener = %s", str, str2, veriferUtilListener);
            throw new SysException((Class<?>) VerifierUtil.class, "getKeyValue", "invalid parameter");
        }
        String str3 = Property.KEY_SET.get(str + str2);
        if (str3 == null) {
            LogMgr.log(7, "%s", "001");
            veriferUtilListener.resKeyValue(null, 0);
        } else {
            try {
                byte[] bArrDecode = Base64.decode(str3, 0);
                veriferUtilListener.resKeyValue(bArrDecode, bArrDecode.length);
                LogMgr.log(4, "%s", "999");
            } catch (IllegalArgumentException unused) {
                throw new SysException((Class<?>) VerifierUtil.class, "getKeyValue", "invalid parameter");
            }
        }
    }
}
