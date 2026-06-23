package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfc.util.LogMgr;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class CloudMessagingCache {
    private static final String PREF_FILE_NAME = "cloud_messaging_data";
    private static final String PREF_KEY_TOKEN = "token";
    private static CloudMessagingCache sInstance;
    private boolean mNoNeedLoad;
    private String mToken;
    private final WeakReference<Context> mWeakReference;

    private CloudMessagingCache(Context context) {
        this.mWeakReference = new WeakReference<>(context);
    }

    public static synchronized CloudMessagingCache getInstance(Context context) {
        LogMgr.log(5, "000");
        if (sInstance == null || sInstance.mWeakReference.get() != context) {
            sInstance = new CloudMessagingCache(context);
            LogMgr.log(5, "001");
        }
        LogMgr.log(5, "999");
        return sInstance;
    }

    public synchronized void cacheToken(String str) {
        LogMgr.log(5, "000");
        this.mToken = str;
        this.mNoNeedLoad = true;
        writeCacheFile();
        LogMgr.log(5, "999");
    }

    /* JADX WARN: Removed duplicated region for block: B:8:0x001d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized boolean isCachedToken(java.lang.String r3) {
        /*
            r2 = this;
            monitor-enter(r2)
            java.lang.String r0 = "000"
            r1 = 5
            com.felicanetworks.mfc.util.LogMgr.log(r1, r0)     // Catch: java.lang.Throwable -> L20
            r2.loadCacheFile()     // Catch: java.lang.Throwable -> L20
            java.lang.String r0 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r0)     // Catch: java.lang.Throwable -> L20
            java.lang.String r0 = r2.mToken     // Catch: java.lang.Throwable -> L20
            if (r0 == 0) goto L1d
            java.lang.String r0 = r2.mToken     // Catch: java.lang.Throwable -> L20
            boolean r3 = r0.equals(r3)     // Catch: java.lang.Throwable -> L20
            if (r3 == 0) goto L1d
            r3 = 1
            goto L1e
        L1d:
            r3 = 0
        L1e:
            monitor-exit(r2)
            return r3
        L20:
            r3 = move-exception
            monitor-exit(r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.CloudMessagingCache.isCachedToken(java.lang.String):boolean");
    }

    public synchronized boolean hasCachedToken() {
        boolean z;
        LogMgr.log(5, "000");
        loadCacheFile();
        z = this.mToken != null;
        LogMgr.log(5, "999 return " + z);
        return z;
    }

    private void writeCacheFile() {
        LogMgr.log(6, "000");
        try {
            LogMgr.log(6, "100 token=" + this.mToken);
            SharedPreferences prefs = getPrefs();
            if (prefs == null) {
                return;
            }
            SharedPreferences.Editor editorEdit = prefs.edit();
            editorEdit.putString("token", this.mToken);
            LogMgr.log(6, "103 success commit ? " + editorEdit.commit());
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    private void loadCacheFile() {
        LogMgr.log(6, "000 mNoNeedLoad ? " + this.mNoNeedLoad);
        if (this.mNoNeedLoad) {
            LogMgr.log(6, "999");
            return;
        }
        try {
            SharedPreferences prefs = getPrefs();
            if (prefs == null) {
                return;
            }
            this.mToken = prefs.getString("token", null);
            LogMgr.log(6, "100 token=" + this.mToken);
            this.mNoNeedLoad = true;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    private SharedPreferences getPrefs() {
        Context context = this.mWeakReference.get();
        if (context == null) {
            LogMgr.log(2, "700 context is not exists.");
            return null;
        }
        return context.getSharedPreferences(PREF_FILE_NAME, 0);
    }
}
