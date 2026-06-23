package com.felicanetworks.mfc.mfi;

import android.content.SharedPreferences;
import com.felicanetworks.mfc.mfi.fws.json.GetMfiControlInfoResponseJson;
import com.felicanetworks.mfc.mfi.util.CacheUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.io.File;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class MfiControlInfoCache {
    private static final String DATE_PATTERN = "yyyyMMdd";
    private static final String PREF_FILE_NAME = "mfi_control_info";
    private static final String PREF_KEY_DATE = "date";
    private static final String PREF_KEY_INFO = "info";
    private static final String TIME_ZONE = "UTC";
    private static MfiControlInfoCache sInstance;
    private String mDate;
    private String mInfo;
    private boolean mNoNeedLoad;

    public static synchronized MfiControlInfoCache getInstance() {
        if (sInstance == null) {
            sInstance = new MfiControlInfoCache();
        }
        return sInstance;
    }

    public synchronized void cacheMfiControlInfo(String info, String date) {
        LogMgr.log(5, "000");
        this.mInfo = info;
        this.mDate = date;
        writeCacheFile();
        LogMgr.log(5, "999");
    }

    public synchronized String getInfoCache() {
        LogMgr.log(5, "000");
        loadCacheFile();
        LogMgr.log(5, "999");
        return this.mInfo;
    }

    public synchronized boolean needUpdateContent(String date) {
        LogMgr.log(5, "000");
        loadCacheFile();
        String str = this.mDate;
        if (str != null && str.equals(date)) {
            if (this.mInfo == null) {
                return true;
            }
            try {
                new GetMfiControlInfoResponseJson(this.mInfo).checkMembers();
                LogMgr.log(5, "999");
                return false;
            } catch (JSONException unused) {
                return true;
            }
        }
        return true;
    }

    public String getDatePattern() {
        return DATE_PATTERN;
    }

    public String getTimeZone() {
        return TIME_ZONE;
    }

    private void writeCacheFile() {
        LogMgr.log(6, "000");
        try {
            LogMgr.log(6, "100 info=" + this.mInfo);
            LogMgr.log(6, "101 date=" + this.mDate);
            SharedPreferences.Editor editorEdit = getPrefs().edit();
            editorEdit.putString(PREF_KEY_INFO, this.mInfo);
            editorEdit.putString("date", this.mDate);
            LogMgr.log(6, "103 success commit ? " + editorEdit.commit());
            this.mNoNeedLoad = true;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    private void loadCacheFile() {
        LogMgr.log(6, "100 mNoNeedLoad ? " + this.mNoNeedLoad);
        if (this.mNoNeedLoad) {
            LogMgr.log(6, "999");
            return;
        }
        try {
            SharedPreferences prefs = getPrefs();
            String string = prefs.getString(PREF_KEY_INFO, null);
            this.mInfo = string;
            if (string != null) {
                LogMgr.log(6, "100 info=" + this.mInfo);
            } else {
                LogMgr.log(6, "100 Info data is null.");
            }
            String string2 = prefs.getString("date", null);
            this.mDate = string2;
            if (string2 != null) {
                LogMgr.log(6, "102 info=" + this.mDate);
            } else {
                LogMgr.log(6, "103 Date data is null.");
            }
            this.mNoNeedLoad = true;
            CacheUtil.deleteFiles(new File(FelicaAdapter.getInstance().getApplicationInfo().dataDir, "shared_prefs"), PREF_FILE_NAME, getPrefsName() + ".xml");
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999");
    }

    private String getPrefsName() {
        return "mfi_control_info_" + FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
    }

    private SharedPreferences getPrefs() {
        return FelicaAdapter.getInstance().getSharedPreferences(getPrefsName(), 0);
    }
}
