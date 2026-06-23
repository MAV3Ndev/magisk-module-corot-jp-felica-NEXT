package com.felicanetworks.mfm.main.presenter.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfm.main.model.info.AccountChangeHistoryInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.MfiContentProvider;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ServicePreference {
    private static final String ACCOUNT_CHANGE_HISTORY_LIST = "ChangeAccountHistoryInfoList";
    public static final String ACCOUNT_CHANGE_HISTORY_LIST_DEFAULT = "";
    private static final int ACCOUNT_CHANGE_HISTORY_LIST_MAX_COUNT = 5;
    private static final String ACCOUNT_CHANGE_HISTORY_NOTIFICATION = "ChangeAccountNotificationSetting";
    public static final String ACCOUNT_CHANGE_HISTORY_NOTIFICATION_DEFAULT = "Y";
    private static final String ACCOUNT_CHANGE_HISTORY_RECEIVE_COUNT = "ChangeAccountEventReceiveCount";
    public static final int ACCOUNT_CHANGE_HISTORY_RECEIVE_COUNT_DEFAULT = 0;
    public static final String APP_CODE_GOOGLEPAY = "02";
    public static final String APP_CODE_OSAIFU = "01";
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
    public static final int PREFERENCE_VERSION_CURRENT = 3;
    public static final int PREFERENCE_VERSION_DEFAULT = 0;
    public static final String PREF_NAME = "FeliCaMenuApp";
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

    public void saveIDm(Context context, String str) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_IDM, str);
        check(editorEdit.commit());
    }

    public String loadIcCode(Context context) {
        return getSharedPreferences(context).getString(ICLIST_PREFKEY_ICCODE, ICCODE_DEFAULT);
    }

    public void saveIcCode(Context context, String str) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_ICCODE, str);
        check(editorEdit.commit());
    }

    public boolean loadTutorial(Context context) {
        return !OFF.equals(getSharedPreferences(context).getString(ICLIST_PREFKEY_SHOW_TUTORIAL, "Y"));
    }

    public void saveTutorial(Context context, boolean z) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_SHOW_TUTORIAL, z ? "Y" : OFF);
        check(editorEdit.commit());
    }

    public int loadPreferenceVersion(Context context) {
        return getSharedPreferences(context).getInt(ICLIST_PREFKEY_PREFERENCE_VERSION, 0);
    }

    public void savePreferenceVersion(Context context, int i) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putInt(ICLIST_PREFKEY_PREFERENCE_VERSION, i);
        check(editorEdit.commit());
    }

    public String loadMfiAccountHash(Context context) {
        return getSharedPreferences(context).getString(ICLIST_PREFKEY_MFI_ACCOUNT_HASH, MFI_ACCOUNT_HASH_DEFAULT);
    }

    public void saveMfiAccountHash(Context context, String str) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ICLIST_PREFKEY_MFI_ACCOUNT_HASH, str);
        check(editorEdit.commit());
    }

    public boolean loadShowLoginConductor(Context context) {
        return !OFF.equals(getSharedPreferences(context).getString(SHOW_LOGIN_CONDUCTOR, "Y"));
    }

    public void saveShowLoginConductor(Context context, boolean z) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(SHOW_LOGIN_CONDUCTOR, z ? "Y" : OFF);
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
            if (i == 3) {
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
            }
            editorEdit.putInt(ICLIST_PREFKEY_PREFERENCE_VERSION, 3);
            return editorEdit.commit();
        } catch (Exception e) {
            LogUtil.warning(e);
            return false;
        }
    }

    private void check(boolean z) {
        if (z) {
            return;
        }
        LogUtil.warning(new Exception("commit failed."));
    }

    public boolean loadAccountChangeHistoryNotificationSetting(Context context) {
        return !OFF.equals(getSharedPreferences(context).getString(ACCOUNT_CHANGE_HISTORY_NOTIFICATION, "Y"));
    }

    public void saveAccountChangeHistoryNotificationSetting(Context context, boolean z) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ACCOUNT_CHANGE_HISTORY_NOTIFICATION, z ? "Y" : OFF);
        check(editorEdit.commit());
    }

    public int loadAccountChangeHistoryReceiveCount(Context context) {
        return getSharedPreferences(context).getInt(ACCOUNT_CHANGE_HISTORY_RECEIVE_COUNT, 0);
    }

    public void saveAccountChangeHistoryReceiveCount(Context context, int i) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putInt(ACCOUNT_CHANGE_HISTORY_RECEIVE_COUNT, i);
        check(editorEdit.commit());
    }

    public String loadAccountChangeHistoryList(Context context) {
        return getSharedPreferences(context).getString(ACCOUNT_CHANGE_HISTORY_LIST, "");
    }

    public void saveAccountChangeHistoryList(Context context, String str) {
        SharedPreferences.Editor editorEdit = getSharedPreferences(context).edit();
        editorEdit.putString(ACCOUNT_CHANGE_HISTORY_LIST, addAccountChangeHistoryListData(context, str));
        check(editorEdit.commit());
    }

    private String addAccountChangeHistoryListData(Context context, String str) {
        String strLoadAccountChangeHistoryList = getInstance().loadAccountChangeHistoryList(context);
        ArrayList arrayList = new ArrayList();
        if (!strLoadAccountChangeHistoryList.isEmpty()) {
            try {
                JSONArray jSONArray = new JSONArray(strLoadAccountChangeHistoryList);
                for (int i = 0; i < 5; i++) {
                    JSONObject jSONObject = jSONArray.getJSONObject(i);
                    String string = jSONObject.getString(MfiContentProvider.Accounts.PATH);
                    String string2 = jSONObject.getString("changeDate");
                    String string3 = jSONObject.getString("changeAppId");
                    if (string.equals("null") || string.isEmpty()) {
                        break;
                    }
                    arrayList.add(new AccountChangeHistoryInfo(string, string2, string3));
                }
            } catch (JSONException unused) {
            }
        }
        arrayList.add(0, new AccountChangeHistoryInfo(MfiPreference.getInstance(context).loadMfiAccountName(), getDate(), str));
        while (arrayList.size() > 5) {
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
}
