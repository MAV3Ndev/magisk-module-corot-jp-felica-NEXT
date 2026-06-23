package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfc.util.LogMgr;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes3.dex */
public class UnsupportedMfiService1CardCache {
    private static final String PREF_FILE_NAME = "unsupported_mfiservice1card_existence";
    private static final String PREF_KEY_NAME = "unsupportedmfiservice1card_existence";
    private static UnsupportedMfiService1CardCache sInstance;
    private boolean mExistence;
    private boolean mLoaded;
    private final WeakReference<Context> mWeakReference;

    private UnsupportedMfiService1CardCache(Context context) {
        this.mWeakReference = new WeakReference<>(context);
    }

    public static synchronized UnsupportedMfiService1CardCache getInstance() {
        LogMgr.log(5, "000");
        FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
        UnsupportedMfiService1CardCache unsupportedMfiService1CardCache = sInstance;
        if (unsupportedMfiService1CardCache == null || unsupportedMfiService1CardCache.mWeakReference.get() != felicaAdapter) {
            sInstance = new UnsupportedMfiService1CardCache(felicaAdapter);
            LogMgr.log(6, "001");
        }
        LogMgr.log(5, "999");
        return sInstance;
    }

    public synchronized void cacheNotExistUnsupportedMfiService1Card() {
        LogMgr.log(5, "000");
        this.mExistence = false;
        this.mLoaded = true;
        SharedPreferences prefs = getPrefs();
        if (prefs == null) {
            LogMgr.log(5, "998");
            return;
        }
        SharedPreferences.Editor editorEdit = prefs.edit();
        editorEdit.putBoolean(PREF_KEY_NAME, false);
        if (!editorEdit.commit()) {
            LogMgr.log(2, "700 cacheNotExistUnsupportedMfiService1Card is failed");
        }
        LogMgr.log(5, "999");
    }

    public synchronized boolean existUnsupportedMfiService1Card() {
        LogMgr.log(5, "000");
        if (!this.mLoaded) {
            LogMgr.log(6, "001");
            loadCacheFile();
        }
        LogMgr.log(5, "999 existence = " + this.mExistence);
        return this.mExistence;
    }

    private void loadCacheFile() {
        boolean z;
        LogMgr.log(6, "000");
        SharedPreferences prefs = getPrefs();
        if (prefs != null) {
            LogMgr.log(6, "001");
            z = prefs.getBoolean(PREF_KEY_NAME, true);
        } else {
            z = true;
        }
        if (!z) {
            this.mLoaded = true;
        }
        this.mExistence = z;
        LogMgr.log(6, "999");
    }

    private SharedPreferences getPrefs() {
        Context context = this.mWeakReference.get();
        if (context == null) {
            LogMgr.log(2, "700 context is not exists.");
            return null;
        }
        try {
            return context.getSharedPreferences(PREF_FILE_NAME, 0);
        } catch (Exception unused) {
            LogMgr.log(2, "701 failed to get SharedPreferences.");
            return null;
        }
    }
}
