package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class MigratedServiceCache {
    private static final String PREF_FILE_NAME = "migrated_service1_data";

    public static synchronized void cacheMigratedService(Context context, String serviceId) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(2, "800 Context is null.");
            return;
        }
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) {
            return;
        }
        SharedPreferences.Editor editorEdit = prefs.edit();
        editorEdit.putBoolean(serviceId, true);
        editorEdit.commit();
        LogMgr.log(5, "999");
    }

    public static synchronized boolean isMigrated(Context context, String serviceId) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(2, "800 Context is null.");
            return false;
        }
        SharedPreferences prefs = getPrefs(context);
        if (prefs == null) {
            return false;
        }
        LogMgr.log(5, "999");
        return prefs.getBoolean(serviceId, false);
    }

    private static SharedPreferences getPrefs(Context context) {
        String prefsName = getPrefsName(context);
        if (prefsName == null) {
            LogMgr.log(2, "701 getPrefsName is null.");
            return null;
        }
        return context.getSharedPreferences(prefsName, 0);
    }

    private static String getPrefsName(Context context) {
        if (context == null) {
            LogMgr.log(2, "700 context is null.");
            return null;
        }
        return PREF_FILE_NAME;
    }
}
