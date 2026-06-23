package com.felicanetworks.mfc.mfi.openidconnect;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class OpenIdConnectSharedPreferences {
    private static final String PREF_FILE_NAME = "openidconnect_google_v2";
    private static final String PREF_KEY_CONFIRMED = "confirmed";
    private static final String PREF_KEY_CONFIRMED_V2 = "confirmed_v2";

    public static boolean isAgreeCooperateAccount(Context context) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(2, "800 Context is null.");
            return false;
        }
        boolean z = getPrefs(context).getBoolean(PREF_KEY_CONFIRMED, false);
        LogMgr.log(5, "999 confirmed=" + z);
        return z;
    }

    public static void agreeCooperateAccount(Context context) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(2, "800 Context is null.");
            return;
        }
        SharedPreferences.Editor editorEdit = getPrefs(context).edit();
        editorEdit.putBoolean(PREF_KEY_CONFIRMED, true);
        editorEdit.commit();
        LogMgr.log(5, "999");
    }

    public static boolean isAgreeCooperateAccountV2(Context context) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(2, "700 Context is null.");
            return false;
        }
        boolean z = getPrefs(context).getBoolean(PREF_KEY_CONFIRMED_V2, false);
        LogMgr.log(5, "999 confirmed_v2=" + z);
        return z;
    }

    public static void agreeCooperateAccountAll(Context context) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(2, "700 Context is null.");
            return;
        }
        SharedPreferences.Editor editorEdit = getPrefs(context).edit();
        editorEdit.putBoolean(PREF_KEY_CONFIRMED, true);
        editorEdit.putBoolean(PREF_KEY_CONFIRMED_V2, true);
        editorEdit.commit();
        LogMgr.log(5, "999");
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(PREF_FILE_NAME, 0);
    }
}
