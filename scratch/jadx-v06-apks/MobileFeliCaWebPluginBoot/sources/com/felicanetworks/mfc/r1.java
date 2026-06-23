package com.felicanetworks.mfc;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: compiled from: SignatureUtil.java */
/* JADX INFO: loaded from: classes.dex */
class r1 {
    private static String a(byte[] bArr) {
        com.felicanetworks.mfc.s1.a.a(7, "000");
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
        }
        com.felicanetworks.mfc.s1.a.a(7, "999");
        return sb.toString();
    }

    static boolean b(Context context, String str, String str2) {
        com.felicanetworks.mfc.s1.a.a(7, "000 context=" + context + ", packageName=" + str2);
        boolean z = false;
        if (context == null || str == null || str2 == null) {
            com.felicanetworks.mfc.s1.a.a(2, "700 Arguments is null.");
            return false;
        }
        String strReplaceAll = str.toUpperCase().replaceAll(":", "");
        String[] strArrD = d(context, str2);
        if (strArrD != null) {
            int length = strArrD.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (strReplaceAll.equals(strArrD[i])) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return z;
    }

    private static byte[] c(byte[] bArr) {
        byte[] bArrDigest;
        com.felicanetworks.mfc.s1.a.a(7, "000");
        try {
            bArrDigest = MessageDigest.getInstance("SHA-256").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            com.felicanetworks.mfc.s1.a.a(2, "700 " + e.toString());
            bArrDigest = null;
        }
        com.felicanetworks.mfc.s1.a.a(7, "999");
        return bArrDigest;
    }

    private static String[] d(Context context, String str) {
        Signature[] apkContentsSigners;
        com.felicanetworks.mfc.s1.a.a(7, "000");
        String[] strArr = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (Build.VERSION.SDK_INT < 28) {
                apkContentsSigners = packageManager.getPackageInfo(str, 64).signatures;
            } else {
                SigningInfo signingInfo = packageManager.getPackageInfo(str, 134217728).signingInfo;
                apkContentsSigners = signingInfo.hasMultipleSigners() ? signingInfo.getApkContentsSigners() : signingInfo.getSigningCertificateHistory();
            }
        } catch (PackageManager.NameNotFoundException e) {
            com.felicanetworks.mfc.s1.a.a(2, "701 " + e.toString());
        }
        if (apkContentsSigners.length > 100) {
            com.felicanetworks.mfc.s1.a.a(2, "700 sigList exceeds max size.");
            return null;
        }
        strArr = new String[apkContentsSigners.length];
        for (int i = 0; i < apkContentsSigners.length; i++) {
            strArr[i] = a(c(apkContentsSigners[i].toByteArray()));
        }
        com.felicanetworks.mfc.s1.a.a(7, "999");
        return strArr;
    }
}
