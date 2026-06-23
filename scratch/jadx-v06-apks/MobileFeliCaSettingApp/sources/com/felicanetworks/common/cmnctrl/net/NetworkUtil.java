package com.felicanetworks.common.cmnctrl.net;

import android.os.Build;
import com.felicanetworks.common.cmnctrl.packages.PackageAccess;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.sg.SgMgr;

/* JADX INFO: loaded from: classes.dex */
public final class NetworkUtil {
    public static void setUserAgent(AppContext appContext, String str) throws NetworkAccessException {
        try {
            String str2 = Build.VERSION.RELEASE;
            String strSubstring = Build.MODEL;
            if (strSubstring.length() > 30) {
                strSubstring = strSubstring.substring(0, 30);
            }
            String strSubstring2 = new PackageAccess(appContext).getAppVersionInfo().versionName;
            if (strSubstring2.length() > 8) {
                strSubstring2 = strSubstring2.substring(0, 8);
            }
            appContext.userAgent = str + "/" + strSubstring2 + " (Android " + str2 + "; " + ((String) appContext.sgMgr.getSgValue(SgMgr.KEY_MFC_ISSUER_CODE)) + "; " + strSubstring + ")";
        } catch (Exception e) {
            throw new NetworkAccessException("User agent generation failure", e);
        }
    }
}
