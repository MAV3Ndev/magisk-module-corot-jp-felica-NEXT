package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.File;

/* JADX INFO: loaded from: classes3.dex */
public class ClientIdCache {
    private static final String PREF_FILE_NAME = "client_id_data";
    private static final String PREF_KEY_CLIENT_ID = "client_id";

    public static String getClientId(Context context) {
        LogMgr.log(5, "000");
        if (context == null) {
            LogMgr.log(2, "800 Context is null.");
            return null;
        }
        SharedPreferences prefs = getPrefs(context);
        LogMgr.log(5, "999");
        return prefs.getString("client_id", null);
    }

    public static void saveClientId(Context context, String clientId) {
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
        editorEdit.putString("client_id", clientId);
        editorEdit.commit();
        CacheUtil.deleteFiles(new File(context.getApplicationInfo().dataDir, "shared_prefs"), PREF_FILE_NAME, getPrefsName(context) + ".xml");
        LogMgr.log(5, "999");
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
        return "client_id_data_" + context.getString(R.string.mfi_client_version);
    }
}
