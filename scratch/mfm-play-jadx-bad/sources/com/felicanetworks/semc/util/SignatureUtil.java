package com.felicanetworks.semc.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes3.dex */
public class SignatureUtil {
    private static final int MAX_SIGNER_SIZE = 100;

    private SignatureUtil() {
    }

    public static boolean checkAppCertHash(Context context, String str, String str2) {
        LogMgrUtil.log(9, "000 context=" + context + ", packageName=" + str2);
        boolean z = false;
        if (context == null || str == null || str2 == null) {
            LogMgrUtil.log(2, "700 Arguments is null.");
            return false;
        }
        String strReplaceAll = str.toUpperCase().replaceAll(":", "");
        String[] strArrHashes = hashes(context, str2);
        if (strArrHashes != null) {
            int length = strArrHashes.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (strReplaceAll.equals(strArrHashes[i])) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        LogMgrUtil.log(9, "999");
        return z;
    }

    private static String[] hashes(Context context, String str) {
        PackageInfo packageInfo;
        Signature[] signingCertificateHistory;
        LogMgrUtil.log(9, "000");
        String[] strArr = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (Build.VERSION.SDK_INT < 28) {
                signingCertificateHistory = packageManager.getPackageInfo(str, 64).signatures;
            } else {
                if (Build.VERSION.SDK_INT >= 33) {
                    LogMgrUtil.log(9, "002 called PackageManager.getPackageInfo for 33 and over");
                    packageInfo = packageManager.getPackageInfo(str, PackageManager.PackageInfoFlags.of(134217728L));
                } else {
                    LogMgrUtil.log(9, "003 called PackageManager.getPackageInfo for less than 33");
                    packageInfo = packageManager.getPackageInfo(str, 134217728);
                }
                SigningInfo signingInfo = packageInfo.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    LogMgrUtil.log(9, "004 SigningInfo.getApkContentsSigners");
                    signingCertificateHistory = signingInfo.getApkContentsSigners();
                } else {
                    LogMgrUtil.log(9, "005 SigningInfo.getSigningCertificateHistory");
                    signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogMgrUtil.log(2, "701 " + e.toString());
        }
        if (signingCertificateHistory.length > 100) {
            LogMgrUtil.log(2, "700 sigList exceeds max size.");
            return null;
        }
        strArr = new String[signingCertificateHistory.length];
        for (int i = 0; i < signingCertificateHistory.length; i++) {
            strArr[i] = byte2hex(computeSha256(signingCertificateHistory[i].toByteArray()));
        }
        LogMgrUtil.log(9, "999");
        return strArr;
    }

    private static byte[] computeSha256(byte[] bArr) {
        byte[] bArrDigest;
        LogMgrUtil.log(9, "000");
        try {
            bArrDigest = MessageDigest.getInstance("SHA-256").digest(bArr);
        } catch (NoSuchAlgorithmException e) {
            LogMgrUtil.log(2, "700 " + e.toString());
            bArrDigest = null;
        }
        LogMgrUtil.log(9, "999");
        return bArrDigest;
    }

    private static String byte2hex(byte[] bArr) {
        LogMgrUtil.log(9, "000");
        if (bArr == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
        }
        LogMgrUtil.log(9, "999");
        return sb.toString();
    }
}
