package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfm.main.model.info.AccountChangeHistoryInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.MfiContentProvider;
import com.felicanetworks.tis.LogSender$$ExternalSyntheticBackport0;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class ServicePreference {
    private static final String ACCOUNT_CHANGE_HISTORY_LIST = "ChangeAccountHistoryInfoList";
    public static final String ACCOUNT_CHANGE_HISTORY_LIST_DEFAULT = "";
    private static final int ACCOUNT_CHANGE_HISTORY_LIST_MAX_COUNT = 10;
    private static final String ACCOUNT_CHANGE_HISTORY_NOTIFICATION = "ChangeAccountNotificationSetting";
    public static final String ACCOUNT_CHANGE_HISTORY_NOTIFICATION_DEFAULT = "Y";
    public static final String APP_CODE_GOOGLE_PAYMENTS_APP = "02";
    public static final String APP_CODE_OSAIFU = "01";
    public static final String APP_CODE_OTHER_APP = "03";
    public static final String ICCODE_DEFAULT = "    ";
    private static final String ICLIST_PREFKEY_ICCODE = "ICCode";
    private static final String ICLIST_PREFKEY_IDM = "IDm";
    private static final String ICLIST_PREFKEY_MFI_ACCOUNT_HASH = "MfiAccountHash";
    private static final String ICLIST_PREFKEY_PREFERENCE_VERSION = "PreferenceVersion";
    private static final String ICLIST_PREFKEY_SHOW_TUTORIAL = "ShowTutorial";
    public static final String IDM_DEFAULT = "                ";
    public static final String MFI_ACCOUNT_HASH_DEFAULT = null;
    public static final String OFF = "N";
    public static final String ON = "Y";
    public static final int PREFERENCE_VERSION_CURRENT = 4;
    public static final int PREFERENCE_VERSION_DEFAULT = 0;
    public static final String PREF_NAME = "FeliCaMenuApp";
    private static final String RESTRICT_CARD_READ_SETTING = "RestrictCardReadSetting";
    public static final String RESTRICT_CARD_READ_SETTING_DEFAULT = "N";
    public static final String SEID = "SEID";
    public static final String SEID_DEFAULT = "";
    public static final String SHOWED_TUTORIAL_DEFAULT = "Y";
    private static final String SHOW_LOGIN_CONDUCTOR = "ShowLoginConductor";
    private static final String SHOW_LOGIN_CONDUCTOR_DEFAULT = "Y";
    private static ServicePreference _instance = new ServicePreference();

    public static ServicePreference getInstance() {
        return _instance;
    }

    private ServicePreference() {
    }

    public String loadIDm(Context context) {
        return getSharedPreferences(context).getString(ICLIST_PREFKEY_IDM, IDM_DEFAULT);
    }

    public void saveIDm(Context context, String val) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_IDM, val);
        check(editorEdit.commit());
    }

    public String loadIcCode(Context context) {
        return getSharedPreferences(context).getString(ICLIST_PREFKEY_ICCODE, ICCODE_DEFAULT);
    }

    public void saveIcCode(Context context, String val) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_ICCODE, val);
        check(editorEdit.commit());
    }

    public boolean loadTutorial(Context context) {
        return !"N".equals(getSharedPreferences(context).getString(ICLIST_PREFKEY_SHOW_TUTORIAL, "Y"));
    }

    public void saveTutorial(Context context, boolean b) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_SHOW_TUTORIAL, b ? "Y" : "N");
        check(editorEdit.commit());
    }

    public int loadPreferenceVersion(Context context) {
        return getSharedPreferences(context).getInt(ICLIST_PREFKEY_PREFERENCE_VERSION, 0);
    }

    public void savePreferenceVersion(Context context, int val) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putInt(ICLIST_PREFKEY_PREFERENCE_VERSION, val);
        check(editorEdit.commit());
    }

    public String loadMfiAccountHash(Context context) {
        return getSharedPreferences(context).getString(ICLIST_PREFKEY_MFI_ACCOUNT_HASH, MFI_ACCOUNT_HASH_DEFAULT);
    }

    public void saveMfiAccountHash(Context context, String val) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_MFI_ACCOUNT_HASH, val);
        check(editorEdit.commit());
    }

    public boolean loadShowLoginConductor(Context context) {
        return !"N".equals(getSharedPreferences(context).getString(SHOW_LOGIN_CONDUCTOR, "Y"));
    }

    public void saveShowLoginConductor(Context context, boolean b) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(SHOW_LOGIN_CONDUCTOR, b ? "Y" : "N");
        check(editorEdit.commit());
    }

    private SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, 0);
    }

    private void clear(Context context) throws IOException {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.clear();
        if (!editorEdit.commit()) {
            throw new IOException("failed to clear shared preference.");
        }
    }

    public boolean setup(Context context) {
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(context);
            SharedPreferences.Editor editorEdit = sharedPreferences.edit();
            int i = sharedPreferences.getInt(ICLIST_PREFKEY_PREFERENCE_VERSION, 0);
            if (i == 4) {
                return true;
            }
            if (i == 0) {
                String string = sharedPreferences.getString(ICLIST_PREFKEY_IDM, IDM_DEFAULT);
                String string2 = sharedPreferences.getString(ICLIST_PREFKEY_ICCODE, ICCODE_DEFAULT);
                clear(context);
                editorEdit.putString(ICLIST_PREFKEY_IDM, string);
                editorEdit.putString(ICLIST_PREFKEY_ICCODE, string2);
            } else if (i == 1) {
                editorEdit.remove("CreateShortcut");
                editorEdit.putString(ICLIST_PREFKEY_SHOW_TUTORIAL, "Y");
            } else if (i == 3) {
                editorEdit.remove("ChangeAccountEventReceiveCount");
            }
            editorEdit.putInt(ICLIST_PREFKEY_PREFERENCE_VERSION, 4);
            return editorEdit.commit();
        } catch (Exception e) {
            LogUtil.warning(e);
            return false;
        }
    }

    private void check(boolean ret) {
        if (ret) {
            return;
        }
        LogUtil.warning(new Exception("commit failed."));
    }

    public boolean loadAccountChangeHistoryNotificationSetting(Context context) {
        return !"N".equals(getSharedPreferences(context).getString(ACCOUNT_CHANGE_HISTORY_NOTIFICATION, "Y"));
    }

    public void saveRestrictCardReadSetting(Context context, boolean b) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(RESTRICT_CARD_READ_SETTING, b ? "Y" : "N");
        check(editorEdit.commit());
    }

    public boolean loadRestrictCardReadSetting(Context context) {
        return !"N".equals(getSharedPreferences(context).getString(RESTRICT_CARD_READ_SETTING, "N"));
    }

    public void saveAccountChangeHistoryNotificationSetting(Context context, boolean b) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ACCOUNT_CHANGE_HISTORY_NOTIFICATION, b ? "Y" : "N");
        check(editorEdit.commit());
    }

    public String loadAccountChangeHistoryList(Context context) {
        return getSharedPreferences(context).getString(ACCOUNT_CHANGE_HISTORY_LIST, "");
    }

    public void saveAccountChangeHistoryList(Context context, String val) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ACCOUNT_CHANGE_HISTORY_LIST, addAccountChangeHistoryListData(context, val));
        check(editorEdit.commit());
    }

    private String addAccountChangeHistoryListData(Context context, String newChangeAppId) throws Throwable {
        String strLoadAccountChangeHistoryList = getInstance().loadAccountChangeHistoryList(context);
        String strLoadMfiAccountName = MfiPreference.getInstance(context).loadMfiAccountName();
        if (LogSender$$ExternalSyntheticBackport0.m(strLoadMfiAccountName) || strLoadMfiAccountName.isEmpty()) {
            return strLoadAccountChangeHistoryList;
        }
        ArrayList arrayList = new ArrayList();
        if (!strLoadAccountChangeHistoryList.isEmpty()) {
            try {
                JSONArray jSONArray = new JSONArray(strLoadAccountChangeHistoryList);
                for (int i = 0; i < 10; i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    arrayList.add(new AccountChangeHistoryInfo(jSONObject.getString(MfiContentProvider.Accounts.PATH), jSONObject.getString("changeDate"), jSONObject.getString("changeAppId")));
                }
            } catch (JSONException unused) {
            }
        }
        arrayList.add(0, new AccountChangeHistoryInfo(strLoadMfiAccountName, getDate(), newChangeAppId));
        while (arrayList.size() > 10) {
            arrayList.remove(arrayList.size() - 1);
        }
        try {
            JSONArray jSONArray2 = new JSONArray();
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(MfiContentProvider.Accounts.PATH, ((AccountChangeHistoryInfo) arrayList.get(i2)).getAccount());
                jSONObject2.put("changeDate", ((AccountChangeHistoryInfo) arrayList.get(i2)).getChangeDate());
                jSONObject2.put("changeAppId", ((AccountChangeHistoryInfo) arrayList.get(i2)).getChangeAppId());
                jSONArray2.put(jSONObject2);
            }
            return jSONArray2.toString();
        } catch (JSONException unused2) {
            return "";
        }
    }

    private String getDate() {
        return new SimpleDateFormat("yyyy/MM/dd HH:mm").format(new Date(System.currentTimeMillis()));
    }

    public String loadSeid(Context context) {
        return getSharedPreferences(context).getString(SEID, "");
    }

    public void saveSeid(Context context, String val) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(SEID, val);
        check(editorEdit.commit());
    }
}
