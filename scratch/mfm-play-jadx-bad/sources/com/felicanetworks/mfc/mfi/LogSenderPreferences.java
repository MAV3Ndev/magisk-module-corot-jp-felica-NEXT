package com.felicanetworks.mfc.mfi;

import android.content.SharedPreferences;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class LogSenderPreferences {
    private static final String PREF_FILE_NAME = "log_sender";
    private static final String PREF_KEY_LATEST_UUID_DISPLAY = "latest_uuid_display";
    private static LogSenderPreferences sInstance;
    private String mLatestUuidDisplay = null;
    private boolean mNoNeedLoad;

    private LogSenderPreferences() {
    }

    public static synchronized LogSenderPreferences getInstance() {
        if (sInstance == null) {
            sInstance = new LogSenderPreferences();
        }
        return sInstance;
    }

    public synchronized void putLatestUuidDisplay(String uuid) {
        LogMgr.log(5, "000");
        this.mLatestUuidDisplay = uuid;
        writePreferences();
        LogMgr.log(5, "999");
    }

    public synchronized String getLatestUuidDisplay() {
        LogMgr.log(5, "000");
        loadPreferences();
        LogMgr.log(5, "999");
        return this.mLatestUuidDisplay;
    }

    private void writePreferences() {
        LogMgr.log(6, "000");
        try {
            LogMgr.log(6, "001 latest_uuid_display=" + this.mLatestUuidDisplay);
            SharedPreferences.Editor editorEdit = getPrefs().edit();
            editorEdit.putString(PREF_KEY_LATEST_UUID_DISPLAY, this.mLatestUuidDisplay);
            LogMgr.log(6, "002 success commit ? " + editorEdit.commit());
            this.mNoNeedLoad = true;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    private void loadPreferences() {
        LogMgr.log(6, "000 mNoNeedLoad ? " + this.mNoNeedLoad);
        if (this.mNoNeedLoad) {
            LogMgr.log(6, "998");
            return;
        }
        try {
            this.mLatestUuidDisplay = getPrefs().getString(PREF_KEY_LATEST_UUID_DISPLAY, null);
            this.mNoNeedLoad = true;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    private SharedPreferences getPrefs() {
        return FelicaAdapter.getInstance().getSharedPreferences(PREF_FILE_NAME, 0);
    }
}
