package com.felicanetworks.mfm.main.policy.fix;

import android.os.Build;
import androidx.credentials.exceptions.publickeycredential.DomExceptionUtils;
import com.felicanetworks.mfm.main.BuildConfig;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes3.dex */
public class Information {
    public static void print() {
    }

    public static int versionCode() {
        return BuildConfig.VERSION_CODE;
    }

    public static String userAgent() {
        String str = Build.VERSION.RELEASE;
        String strSubstring = Build.MODEL;
        if (strSubstring.length() > 30) {
            strSubstring = strSubstring.substring(0, 30);
        }
        String strSimpleVersionName = simpleVersionName();
        if (strSimpleVersionName.length() > 8) {
            strSimpleVersionName = strSimpleVersionName.substring(0, 8);
        }
        return ((String) Sg.getValue(Sg.Key.SETTING_MFM_NAME)) + DomExceptionUtils.SEPARATOR + strSimpleVersionName + " (Android " + str + "; " + ((String) Sg.getValue(Sg.Key.FELICA_MFC_ISSUER_CODE)) + "; " + strSubstring + ")";
    }

    public static String fullVersionName() {
        return BuildConfig.VERSION_NAME;
    }

    public static String simpleVersionName() {
        return BuildConfig.SIMPLE_VERSION_NUMBER;
    }
}
