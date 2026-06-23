package com.felicanetworks.mfc;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes3.dex */
class SignatureUtil {
    private static final int MAX_SIGNER_SIZE = 100;

    private SignatureUtil() {
    }

    static boolean checkAppCertHash(Context context, String expectedHash, String packageName) {
        LogMgr.log(7, "000 context=" + context + ", packageName=" + packageName);
        boolean z = false;
        if (context == null || expectedHash == null || packageName == null) {
            LogMgr.log(2, "700 Arguments is null.");
            return false;
        }
        String strReplaceAll = expectedHash.toUpperCase().replaceAll(":", "");
        String[] strArrHashes = hashes(context, packageName);
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
        LogMgr.log(7, "%s", "999");
        return z;
    }

    private static String[] hashes(Context context, String packageName) {
        PackageInfo packageInfo;
        Signature[] signingCertificateHistory;
        LogMgr.log(7, "000");
        String[] strArr = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (Build.VERSION.SDK_INT < 28) {
                signingCertificateHistory = packageManager.getPackageInfo(packageName, 64).signatures;
            } else {
                if (Build.VERSION.SDK_INT >= 33) {
                    packageInfo = packageManager.getPackageInfo(packageName, PackageManager.PackageInfoFlags.of(134217728L));
                } else {
                    packageInfo = packageManager.getPackageInfo(packageName, 134217728);
                }
                SigningInfo signingInfo = packageInfo.signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    signingCertificateHistory = signingInfo.getApkContentsSigners();
                } else {
                    signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogMgr.log(2, "701 " + e.toString());
        }
        if (signingCertificateHistory.length > 100) {
            LogMgr.log(2, "700 sigList exceeds max size.");
            return null;
        }
        strArr = new String[signingCertificateHistory.length];
        for (int i = 0; i < signingCertificateHistory.length; i++) {
            strArr[i] = byte2hex(computeSha256(signingCertificateHistory[i].toByteArray()));
        }
        LogMgr.log(7, "999");
        return strArr;
    }

    private static byte[] computeSha256(byte[] data) {
        byte[] bArrDigest;
        LogMgr.log(7, "000");
        try {
            bArrDigest = MessageDigest.getInstance("SHA-256").digest(data);
        } catch (NoSuchAlgorithmException e) {
            LogMgr.log(2, "700 " + e.toString());
            bArrDigest = null;
        }
        LogMgr.log(7, "999");
        return bArrDigest;
    }

    private static String byte2hex(byte[] data) {
        LogMgr.log(7, "000");
        if (data == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : data) {
            sb.append(String.format("%02X", Byte.valueOf(b)));
        }
        LogMgr.log(7, "999");
        return sb.toString();
    }
}
