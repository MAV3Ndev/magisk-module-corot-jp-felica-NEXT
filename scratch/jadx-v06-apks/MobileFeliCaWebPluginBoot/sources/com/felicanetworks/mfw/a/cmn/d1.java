package com.felicanetworks.mfw.a.cmn;

import a.a.a.a.c.t1;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Locale;

/* JADX INFO: compiled from: VerifierUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class d1 {
    private static PublicKey a(byte[] bArr, String str) {
        return KeyFactory.getInstance(str).generatePublic(new X509EncodedKeySpec(bArr));
    }

    private static byte[] b(String str) {
        int iH = w0.h(str);
        byte[] bArr = new byte[iH];
        w0.j(str, 0, iH, bArr);
        return bArr;
    }

    private static String c(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (String str : b1.d(new String(bArr))) {
            if (!str.contains("BEGIN PUBLIC KEY") && !str.contains("END PUBLIC KEY")) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static void d(String str, String str2, t1 t1Var) {
        try {
            byte[] bArrA = i.a(c(b(str.toLowerCase(Locale.US) + str2.toLowerCase(Locale.US) + ".pub")));
            t1Var.a(bArrA, bArrA.length);
        } catch (c1 unused) {
            t1Var.a(null, 0);
        }
    }

    public static void e(byte[] bArr, int i, byte[] bArr2, int i2, byte[] bArr3, int i3, t1 t1Var) {
        try {
            Signature signature = Signature.getInstance("SHA256withRSA");
            signature.initVerify(a(bArr3, "RSA"));
            signature.update(bArr);
            if (signature.verify(bArr2)) {
                t1Var.b(0);
            } else {
                t1Var.b(1);
            }
        } catch (InvalidKeyException e) {
            throw new c1(d1.class, "verify", e);
        } catch (NoSuchAlgorithmException e2) {
            throw new c1(d1.class, "verify", e2);
        } catch (SignatureException e3) {
            throw new c1(d1.class, "verify", e3);
        } catch (InvalidKeySpecException e4) {
            throw new c1(d1.class, "verify", e4);
        }
    }

    public static boolean f(PackageManager packageManager) {
        try {
            String strC = p0.a().c("format.felica.package.name");
            ArrayList arrayList = new ArrayList();
            PackageInfo packageInfo = Build.VERSION.SDK_INT >= 28 ? packageManager.getPackageInfo(strC, 134217728) : packageManager.getPackageInfo(strC, 64);
            android.content.pm.Signature[] signingCertificateHistory = Build.VERSION.SDK_INT >= 28 ? !packageInfo.signingInfo.hasMultipleSigners() ? packageInfo.signingInfo.getSigningCertificateHistory() : packageInfo.signingInfo.getApkContentsSigners() : packageInfo.signatures;
            if (signingCertificateHistory != null) {
                for (android.content.pm.Signature signature : signingCertificateHistory) {
                    arrayList.add(signature.toByteArray());
                }
            }
            byte[] bArrI = b1.I(((String) z0.a(strC).get(0)).toUpperCase(Locale.US));
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                messageDigest.update((byte[]) it.next());
                if (!Arrays.equals(bArrI, messageDigest.digest())) {
                    return false;
                }
            }
            return true;
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException unused) {
            return false;
        }
    }
}
