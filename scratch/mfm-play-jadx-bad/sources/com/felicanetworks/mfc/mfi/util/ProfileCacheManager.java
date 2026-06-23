package com.felicanetworks.mfc.mfi.util;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.FelicaAdapter;
import com.felicanetworks.mfc.mfi.R;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class ProfileCacheManager {
    private static final String PREF_FILE_NAME = "mfic_profile_info";
    private static final String PREF_KEY_EXISTENCE = "existence";
    private static final String PREF_KEY_PROFILE_INFO = "profile_info";
    private static final String PREF_KEY_PROFILE_NAME = "profile_name";
    private String mProfileJsonStr;
    private String mProfileName;

    public ProfileCacheManager() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
    }

    public ProfileJson loadCacheFile(Context context, String callerPackageName) throws FelicaException {
        LogMgr.log(5, "000");
        loadCacheFile();
        try {
            ProfileJson profileJson = new ProfileJson(this.mProfileJsonStr, context, callerPackageName);
            LogMgr.log(5, "999");
            return profileJson;
        } catch (JSONException unused) {
            LogMgr.log(1, "800 JSONException occurred in reading profile data.");
            throw new FelicaException(1, 47);
        }
    }

    public synchronized void writeCacheFile(String profileJsonStr) {
        LogMgr.log(5, "000");
        try {
            SharedPreferences.Editor editorEdit = getPrefs().edit();
            editorEdit.putString(PREF_KEY_PROFILE_NAME, getProfileName());
            editorEdit.putString(PREF_KEY_PROFILE_INFO, profileJsonStr);
            editorEdit.putBoolean("existence", true);
            LogMgr.log(6, "001 success commit ? " + editorEdit.commit());
            this.mProfileJsonStr = profileJsonStr;
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " : " + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(5, "999");
    }

    public synchronized void cacheExistence(boolean existence) {
        LogMgr.log(5, "000");
        try {
            SharedPreferences.Editor editorEdit = getPrefs().edit();
            editorEdit.putBoolean("existence", existence);
            LogMgr.log(6, "001 success commit ? " + editorEdit.commit());
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " : " + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(5, "999");
    }

    private void loadCacheFile() throws FelicaException {
        LogMgr.log(6, "000 ");
        String profileInfo = readProfileInfo();
        this.mProfileJsonStr = profileInfo;
        if (profileInfo == null) {
            LogMgr.log(1, "800 Profile info is null.");
            throw new FelicaException(1, 47);
        }
        String profileName = readProfileName();
        this.mProfileName = profileName;
        if (profileName == null) {
            LogMgr.log(1, "801 Profile name is null.");
            throw new FelicaException(1, 47);
        }
        LogMgr.log(6, "999");
    }

    public boolean existsProfileCache() {
        LogMgr.log(5, "000");
        boolean zContains = getPrefs().contains("existence");
        LogMgr.log(5, "999 ret=[" + zContains + "]");
        return zContains;
    }

    private String readProfileInfo() {
        LogMgr.log(5, "000");
        String string = getPrefs().getString(PREF_KEY_PROFILE_INFO, null);
        LogMgr.log(5, "999 profileInfo=[" + string + "]");
        return string;
    }

    public boolean hasProfileData() {
        LogMgr.log(5, "000");
        boolean z = getPrefs().getBoolean("existence", false);
        LogMgr.log(5, "999 existence=[" + z + "]");
        return z;
    }

    private String readProfileName() {
        LogMgr.log(5, "000");
        String string = getPrefs().getString(PREF_KEY_PROFILE_NAME, null);
        LogMgr.log(5, "999 profileName=[" + string + "]");
        return string;
    }

    private SharedPreferences getPrefs() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return FelicaAdapter.getInstance().getSharedPreferences(getPrefsName(), 0);
    }

    private String getPrefsName() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return "mfic_profile_info_" + FelicaAdapter.getInstance().getString(R.string.mfi_client_version);
    }

    public void setProfileName(String profileName) {
        LogMgr.log(5, "000");
        this.mProfileName = profileName;
        LogMgr.log(5, "999");
    }

    public String getProfileName() throws FelicaException {
        LogMgr.log(5, "000");
        if (this.mProfileName == null) {
            LogMgr.log(1, "800 Profile name is null.");
            throw new FelicaException(1, 47);
        }
        LogMgr.log(5, "999 profileName = " + this.mProfileName);
        return this.mProfileName;
    }
}
