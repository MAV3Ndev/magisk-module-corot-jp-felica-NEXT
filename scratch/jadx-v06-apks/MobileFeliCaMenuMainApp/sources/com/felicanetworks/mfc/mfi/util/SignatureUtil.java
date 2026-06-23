package com.felicanetworks.mfc.mfi.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Build;
import com.felicanetworks.mfc.util.LogMgr;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public class SignatureUtil {
    private static final int MAX_SIGNER_SIZE = 100;

    private SignatureUtil() {
    }

    public static boolean checkAppCertHashBase64(Context context, String str, String str2) {
        LogMgr.log(7, "000 context=" + context + ", packageName=" + str2);
        boolean z = false;
        if (context == null || str == null || str2 == null) {
            LogMgr.log(2, "700 Arguments is null.");
            return false;
        }
        String[] signerHashBase64List = getSignerHashBase64List(context, str2);
        if (signerHashBase64List != null) {
            int length = signerHashBase64List.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (str.equals(signerHashBase64List[i])) {
                    z = true;
                    break;
                }
                i++;
            }
        }
        LogMgr.log(7, "999");
        return z;
    }

    private static String[] getSignerHashBase64List(Context context, String str) {
        Signature[] signingCertificateHistory;
        LogMgr.log(7, "000");
        String[] strArr = null;
        try {
            PackageManager packageManager = context.getPackageManager();
            if (Build.VERSION.SDK_INT < 28) {
                signingCertificateHistory = packageManager.getPackageInfo(str, 64).signatures;
            } else {
                SigningInfo signingInfo = packageManager.getPackageInfo(str, 134217728).signingInfo;
                if (signingInfo.hasMultipleSigners()) {
                    signingCertificateHistory = signingInfo.getApkContentsSigners();
                } else {
                    signingCertificateHistory = signingInfo.getSigningCertificateHistory();
                }
            }
            if (signingCertificateHistory.length > 100) {
                LogMgr.log(2, "700 sigList exceeds max size.");
                return null;
            }
            strArr = new String[signingCertificateHistory.length];
            MessageDigest messageDigest = MessageDigest.getInstance("SHA256");
            synchronized (messageDigest) {
                for (int i = 0; i < signingCertificateHistory.length; i++) {
                    messageDigest.update(signingCertificateHistory[i].toByteArray());
                    byte[] bArrDigest = messageDigest.digest();
                    messageDigest.reset();
                    strArr[i] = Base64Util.encode(bArrDigest);
                }
            }
        } catch (Exception e) {
            LogMgr.log(2, "701 " + e.toString());
        }
        LogMgr.log(7, "999");
        return strArr;
    }
}
