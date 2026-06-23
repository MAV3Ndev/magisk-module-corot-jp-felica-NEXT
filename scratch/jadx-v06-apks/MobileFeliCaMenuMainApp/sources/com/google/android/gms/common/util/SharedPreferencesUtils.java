package com.google.android.gms.common.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class SharedPreferencesUtils {
    private SharedPreferencesUtils() {
    }

    @Deprecated
    public static void publishWorldReadableSharedPreferences(Context context, SharedPreferences.Editor editor, String str) {
        File file = new File(context.getApplicationInfo().dataDir, "shared_prefs");
        File parentFile = file.getParentFile();
        if (parentFile != null) {
            parentFile.setExecutable(true, false);
        }
        file.setExecutable(true, false);
        editor.commit();
        new File(file, String.valueOf(str).concat(CacheUtil.EX_XML)).setReadable(true, false);
    }
}
