package com.felicanetworks.semc.util;

import android.content.Context;
import android.content.SharedPreferences;
import java.io.File;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class SharedPrefsUtil {
    public static final String EX_XML = ".xml";
    public static final String FILE_NAME_ANDROID_ID_DATA = "semc_android_id_data";
    public static final String FILE_NAME_APPLET_STATUS_DATA = "semc_applet_status_cache_info_01";
    public static final String FILE_NAME_CLIENT_CONFIG_INFO = "semc_client_config_info_02";
    public static final String FILE_NAME_CLIENT_CONFIG_INFO_OLD = "semc_client_config_info";
    public static final String FILE_NAME_CLIENT_ID_DATA = "semc_client_id_data";
    public static final String FILE_NAME_CLOUD_MESSAGING_DATA = "semc_cloud_messaging_data";
    public static final String FILE_NAME_PROFILE_INFO = "semc_profile_info";
    public static final String FILE_NAME_ROUTINE_WORKER_DATA = "semc_routine_worker_data";
    public static final String FILE_NAME_SEID_DATA = "semc_seid_data_02";
    public static final String FILE_NAME_SEID_DATA_OLD = "semc_seid_data";
    public static final String KEY_ANDROID_ID = "androidId";
    public static final String KEY_ANDROID_ID_DATA_VERSION = "ver";
    public static final String KEY_APPLET_STATUS_CACHE_LIST = "applet_status_cache_list";
    public static final String KEY_CLIENT_CONFIG_INFO_VERSION = "ver";
    public static final String KEY_CLIENT_ID = "clientId";
    public static final String KEY_CLOUD_MESSAGING_DATA_VERSION = "ver";
    public static final String KEY_CONTROL_INFO = "controlInfo";
    public static final String KEY_EXISTENCE = "existence";
    public static final String KEY_PROFILE_INFO = "profileInfo";
    public static final String KEY_PROFILE_NAME = "profileName";
    public static final String KEY_READER_NAME = "reader_name";
    public static final String KEY_ROUTINE_WORKER_DATA_VERSION = "ver";
    public static final String KEY_ROUTINE_WORKER_EXECUTION_DATE = "executionDate";
    public static final String KEY_SAVEDATE = "savedate";
    public static final String KEY_SEID = "seid";
    public static final String KEY_SEID_DATA_VERSION = "ver";
    public static final String KEY_SP_APPLET_INFO_LIST = "spAppletInfoList";
    public static final String KEY_SP_APP_INFO_LIST = "spAppInfoList";
    public static final String KEY_TOKEN = "token";
    private Context mContext;

    public SharedPrefsUtil(Context context) {
        LogMgr.log(7, "000");
        this.mContext = context;
        LogMgr.log(7, "999");
    }

    public boolean hasSeIdData() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_SEID_DATA, KEY_SEID);
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public boolean hasReaderNameData() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_SEID_DATA, KEY_READER_NAME);
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public boolean hasControlInfoData() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_CONTROL_INFO);
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public boolean hasSaveDateData() {
        LogMgr.log(7, "000");
        boolean zHasLongData = hasLongData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SAVEDATE);
        LogMgr.log(7, "999 ret=[" + zHasLongData + "]");
        return zHasLongData;
    }

    public boolean hasSpAppInfoListData() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SP_APP_INFO_LIST);
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public boolean hasTokenData() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_CLOUD_MESSAGING_DATA, "token");
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public boolean hasAndroidIdData() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_ANDROID_ID_DATA, KEY_ANDROID_ID);
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public boolean hasExistence() {
        LogMgr.log(7, "000");
        boolean zHasBooleanData = hasBooleanData(FILE_NAME_PROFILE_INFO, KEY_EXISTENCE);
        LogMgr.log(7, "999 ret=[" + zHasBooleanData + "]");
        return zHasBooleanData;
    }

    public boolean hasProfileInfo() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_PROFILE_INFO, KEY_PROFILE_INFO);
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public boolean hasSaveDate() {
        LogMgr.log(7, "000");
        boolean zHasLongData = hasLongData(FILE_NAME_SEID_DATA, KEY_SAVEDATE);
        LogMgr.log(7, "999 ret=[" + zHasLongData + "]");
        return zHasLongData;
    }

    private boolean hasStringData(String str, String str2) {
        String string;
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]");
        boolean z = false;
        SharedPreferences sharedPreferences = this.mContext.getSharedPreferences(str, 0);
        if (sharedPreferences.contains(str2) && (string = sharedPreferences.getString(str2, null)) != null && !string.isEmpty()) {
            z = true;
        }
        LogMgr.log(8, "001 key=[" + str2 + "] ret=[" + z + "]");
        StringBuilder sb = new StringBuilder("999 ret=[");
        sb.append(z);
        sb.append("]");
        LogMgr.log(7, sb.toString());
        return z;
    }

    private boolean hasBooleanData(String str, String str2) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]");
        boolean zContains = this.mContext.getSharedPreferences(str, 0).contains(str2);
        LogMgr.log(8, "001 key=[" + str2 + "] ret=[" + zContains + "]");
        StringBuilder sb = new StringBuilder("999 ret=[");
        sb.append(zContains);
        sb.append("]");
        LogMgr.log(7, sb.toString());
        return zContains;
    }

    private boolean hasLongData(String str, String str2) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]");
        boolean zContains = this.mContext.getSharedPreferences(str, 0).contains(str2);
        LogMgr.log(8, "001 key=[" + str2 + "] ret=[" + zContains + "]");
        StringBuilder sb = new StringBuilder("999 ret=[");
        sb.append(zContains);
        sb.append("]");
        LogMgr.log(7, sb.toString());
        return zContains;
    }

    private String getStringValue(String str, String str2) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]");
        String string = this.mContext.getSharedPreferences(str, 0).getString(str2, null);
        LogMgr.log(8, "001 key[" + str2 + "] val=[" + string + "]");
        LogMgr.log(7, "999");
        return string;
    }

    private boolean getBooleanValue(String str, String str2) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]");
        boolean z = this.mContext.getSharedPreferences(str, 0).getBoolean(str2, false);
        LogMgr.log(8, "001 key[" + str2 + "] val=[" + z + "]");
        LogMgr.log(7, "999");
        return z;
    }

    private long getLongValue(String str, String str2) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]");
        long j = this.mContext.getSharedPreferences(str, 0).getLong(str2, 0L);
        LogMgr.log(8, "001 key[" + str2 + "] val=[" + j + "]");
        LogMgr.log(7, "999");
        return j;
    }

    public String readSeId() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_SEID_DATA, KEY_SEID);
        LogMgr.log(7, "999 seId=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeSeId(String str) {
        LogMgr.log(7, "000 seId=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_SEID_DATA, KEY_SEID, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public void removeSeId() {
        LogMgr.log(7, "000");
        removeData(FILE_NAME_SEID_DATA, KEY_SEID);
        LogMgr.log(7, "999");
    }

    public String readReaderName() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_SEID_DATA, KEY_READER_NAME);
        LogMgr.log(7, "999 readerName=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeReaderName(String str) {
        LogMgr.log(7, "000 readerName=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_SEID_DATA, KEY_READER_NAME, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public void removeReaderName() {
        LogMgr.log(7, "000");
        removeData(FILE_NAME_SEID_DATA, KEY_READER_NAME);
        LogMgr.log(7, "999");
    }

    public String readControlInfo() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_CLIENT_CONFIG_INFO, KEY_CONTROL_INFO);
        LogMgr.log(7, "999 controlInfo=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeControlInfo(String str) {
        LogMgr.log(7, "000 controlInfo=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_CONTROL_INFO, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public long readSaveDate() {
        LogMgr.log(7, "000");
        long longValue = getLongValue(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SAVEDATE);
        LogMgr.log(7, "999 saveDate=[" + longValue + "]");
        return longValue;
    }

    public boolean writeSaveDate(long j) {
        LogMgr.log(7, "000 saveDate=[" + j + "]");
        boolean zWriteLongData = writeLongData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SAVEDATE, j);
        LogMgr.log(7, "999 ret=[" + zWriteLongData + "]");
        return zWriteLongData;
    }

    public String readSpAppInfoList() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SP_APP_INFO_LIST);
        LogMgr.log(7, "999 spAppInfoList=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeSpAppInfoList(String str) {
        LogMgr.log(7, "000 spAppInfoList=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SP_APP_INFO_LIST, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public String readDeviceToken() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_CLOUD_MESSAGING_DATA, "token");
        LogMgr.log(7, "999 deviceToken=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeDeviceToken(String str) {
        LogMgr.log(7, "000 deviceToken=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_CLOUD_MESSAGING_DATA, "token", str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public void removeDeviceToken() {
        LogMgr.log(7, "000");
        removeData(FILE_NAME_CLOUD_MESSAGING_DATA, "token");
        LogMgr.log(7, "999");
    }

    public String readAndroidId() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_ANDROID_ID_DATA, KEY_ANDROID_ID);
        LogMgr.log(7, "999 androidId=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeAndroidId(String str) {
        LogMgr.log(7, "000 androidId=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_ANDROID_ID_DATA, KEY_ANDROID_ID, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public String readClientId() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_CLIENT_ID_DATA, KEY_CLIENT_ID);
        LogMgr.log(7, "999 clientId=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeClientId(String str) {
        LogMgr.log(7, "000 clientId=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_CLIENT_ID_DATA, KEY_CLIENT_ID, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    private void clearData(String str) {
        LogMgr.log(9, "000 fileName[" + str + "]");
        try {
            this.mContext.getSharedPreferences(str, 0).edit().clear().apply();
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to clear sharedPrefs data");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(9, "999");
    }

    public void clearAllData() {
        LogMgr.log(9, "000");
        try {
            clearData(FILE_NAME_SEID_DATA);
            clearData(FILE_NAME_CLIENT_CONFIG_INFO);
            clearData(FILE_NAME_CLOUD_MESSAGING_DATA);
            clearData(FILE_NAME_ANDROID_ID_DATA);
            clearData(FILE_NAME_CLIENT_ID_DATA);
            clearData(FILE_NAME_PROFILE_INFO);
            clearData(FILE_NAME_ROUTINE_WORKER_DATA);
            clearData(FILE_NAME_APPLET_STATUS_DATA);
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to clear all sharedPrefs data");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(9, "999");
    }

    public void clearClientConfigInfo() {
        LogMgr.log(9, "000");
        try {
            clearData(FILE_NAME_CLIENT_CONFIG_INFO);
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to clear all sharedPrefs data");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(9, "999");
    }

    private boolean writeStringData(String str, String str2, String str3) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]val =[" + str3 + "]");
        boolean zCommit = false;
        try {
            SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(str, 0).edit();
            editorEdit.putString(str2, str3);
            zCommit = editorEdit.commit();
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to save sharedPrefs key[" + str2 + "]");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(8, "001 Saved ShredPrefs result[" + zCommit + "]");
        LogMgr.log(7, "999");
        return zCommit;
    }

    private boolean writeBooleanData(String str, String str2, boolean z) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]val =[" + z + "]");
        boolean zCommit = false;
        try {
            SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(str, 0).edit();
            editorEdit.putBoolean(str2, z);
            zCommit = editorEdit.commit();
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to save sharedPrefs key[" + str2 + "]");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(8, "001 Saved ShredPrefs result[" + zCommit + "]");
        LogMgr.log(7, "999");
        return zCommit;
    }

    private boolean writeLongData(String str, String str2, long j) {
        LogMgr.log(7, "000 fileName=[" + str + "] key=[" + str2 + "]val =[" + j + "]");
        boolean zCommit = false;
        try {
            SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(str, 0).edit();
            editorEdit.putLong(str2, j);
            zCommit = editorEdit.commit();
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to save sharedPrefs key[" + str2 + "]");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(8, "001 Saved ShredPrefs result[" + zCommit + "]");
        LogMgr.log(7, "999");
        return zCommit;
    }

    private void removeData(String str, String str2) {
        LogMgr.log(9, "000");
        try {
            SharedPreferences.Editor editorEdit = this.mContext.getSharedPreferences(str, 0).edit();
            editorEdit.remove(str2);
            editorEdit.apply();
            LogMgr.log(9, "999");
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to remove sharedPrefs data");
            LogMgr.printStackTrace(8, e);
        }
    }

    public String readSeidDataVersion() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_SEID_DATA, "ver");
        LogMgr.log(7, "999 version=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeSeidDataVersion(String str) {
        LogMgr.log(7, "000 version=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_SEID_DATA, "ver", str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public String readClientConfigInfoVersion() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_CLIENT_CONFIG_INFO, "ver");
        LogMgr.log(7, "999 version=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeClientConfigInfoVersion(String str) {
        LogMgr.log(7, "000 version=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_CLIENT_CONFIG_INFO, "ver", str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public String readCloudMessagingDataVersion() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_CLOUD_MESSAGING_DATA, "ver");
        LogMgr.log(7, "999 version=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeCloudMessagingDataVersion(String str) {
        LogMgr.log(7, "000 version=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_CLOUD_MESSAGING_DATA, "ver", str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public String readAndroidIdDataVersion() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_ANDROID_ID_DATA, "ver");
        LogMgr.log(7, "999 version=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeAndroidIdDataVersion(String str) {
        LogMgr.log(7, "000 version=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_ANDROID_ID_DATA, "ver", str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public String readRoutineWorkerDataVersion() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_ROUTINE_WORKER_DATA, "ver");
        LogMgr.log(7, "999 version=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeRoutineWorkerDataVersion(String str) {
        LogMgr.log(7, "000 version=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_ROUTINE_WORKER_DATA, "ver", str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public boolean readExistence() {
        LogMgr.log(7, "000");
        boolean booleanValue = getBooleanValue(FILE_NAME_PROFILE_INFO, KEY_EXISTENCE);
        LogMgr.log(7, "999 existence=[" + booleanValue + "]");
        return booleanValue;
    }

    public boolean writeExistence(boolean z) {
        LogMgr.log(7, "000 existence=[" + z + "]");
        boolean zWriteBooleanData = writeBooleanData(FILE_NAME_PROFILE_INFO, KEY_EXISTENCE, z);
        LogMgr.log(7, "999 ret=[" + zWriteBooleanData + "]");
        return zWriteBooleanData;
    }

    public String readProfileName() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_PROFILE_INFO, KEY_PROFILE_NAME);
        LogMgr.log(7, "999 profileName=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeProfileName(String str) {
        LogMgr.log(7, "000 profileName=[" + str + "]");
        boolean zWriteStringData = writeStringData(FILE_NAME_PROFILE_INFO, KEY_PROFILE_NAME, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public String readProfileInfo() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_PROFILE_INFO, KEY_PROFILE_INFO);
        LogMgr.log(7, "999 profileInfo=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeProfileInfo(String str) {
        LogMgr.log(7, "000 profileInfo=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_PROFILE_INFO, KEY_PROFILE_INFO, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public long readSeIdSaveDate() {
        LogMgr.log(7, "000");
        long longValue = getLongValue(FILE_NAME_SEID_DATA, KEY_SAVEDATE);
        LogMgr.log(7, "999 seIdSaveDate=[" + longValue + "]");
        return longValue;
    }

    public boolean writeSeIdSaveDate(long j) {
        LogMgr.log(7, "000 saveDate=[" + j + "]");
        boolean zWriteLongData = writeLongData(FILE_NAME_SEID_DATA, KEY_SAVEDATE, j);
        LogMgr.log(7, "999 ret=[" + zWriteLongData + "]");
        return zWriteLongData;
    }

    public void removeSeIdSaveDate() {
        LogMgr.log(7, "000");
        removeData(FILE_NAME_SEID_DATA, KEY_SAVEDATE);
        LogMgr.log(7, "999");
    }

    public long readRoutineWorkerExecutionDate() {
        LogMgr.log(7, "000");
        long longValue = getLongValue(FILE_NAME_ROUTINE_WORKER_DATA, KEY_ROUTINE_WORKER_EXECUTION_DATE);
        LogMgr.log(7, "999 routineWorkerExecutionDate=[" + longValue + "]");
        return longValue;
    }

    public boolean writeRoutineWorkerExecutionDate(long j) {
        LogMgr.log(7, "000 routineWorkerExecutionDate=[" + j + "]");
        boolean zWriteLongData = writeLongData(FILE_NAME_ROUTINE_WORKER_DATA, KEY_ROUTINE_WORKER_EXECUTION_DATE, j);
        LogMgr.log(7, "999 ret=[" + zWriteLongData + "]");
        return zWriteLongData;
    }

    public boolean hasRoutineWorkerExecutionDate() {
        LogMgr.log(7, "000");
        boolean zHasLongData = hasLongData(FILE_NAME_ROUTINE_WORKER_DATA, KEY_ROUTINE_WORKER_EXECUTION_DATE);
        LogMgr.log(7, "999 ret=[" + zHasLongData + "]");
        return zHasLongData;
    }

    public void removeRoutineWorkerExecutionDate() {
        LogMgr.log(7, "000");
        removeData(FILE_NAME_ROUTINE_WORKER_DATA, KEY_ROUTINE_WORKER_EXECUTION_DATE);
        LogMgr.log(7, "999");
    }

    public void deleteOtherSeIdDataFiles() {
        LogMgr.log(7, "000");
        try {
            if (hasSharedPrefsFile(FILE_NAME_SEID_DATA)) {
                LogMgr.log(8, "001");
                deleteFiles(new File(this.mContext.getApplicationInfo().dataDir, "shared_prefs"), FILE_NAME_SEID_DATA_OLD, "semc_seid_data_02.xml");
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to delete SeId Data File");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(7, "999");
    }

    public void deleteOtherClientConfigInfoFiles() {
        LogMgr.log(7, "000");
        try {
            if (hasSharedPrefsFile(FILE_NAME_CLIENT_CONFIG_INFO)) {
                LogMgr.log(8, "001");
                deleteFiles(new File(this.mContext.getApplicationInfo().dataDir, "shared_prefs"), FILE_NAME_CLIENT_CONFIG_INFO_OLD, "semc_client_config_info_02.xml");
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 Failed to delete Client Config Info File");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(7, "999");
    }

    public boolean hasSharedPrefsFile(String str) {
        boolean z;
        LogMgr.log(7, "000");
        if (new File(this.mContext.getApplicationInfo().dataDir + "/shared_prefs/" + str + ".xml").exists()) {
            LogMgr.log(8, "001");
            z = true;
        } else {
            LogMgr.log(8, "002 " + str + " does not exists.");
            z = false;
        }
        LogMgr.log(7, "999 ret=[" + z + "]");
        return z;
    }

    public static boolean deleteFile(File file) {
        LogMgr.log(7, "000");
        if (file == null) {
            LogMgr.log(2, "700 File is null.");
            return false;
        }
        boolean zDelete = file.delete();
        if (!zDelete) {
            LogMgr.log(2, "701 Failed to delete " + file.getName() + ".");
        }
        LogMgr.log(7, "999");
        return zDelete;
    }

    public static boolean deleteFiles(File file, String str, String str2) {
        LogMgr.log(7, "000");
        if (file == null) {
            LogMgr.log(2, "700 dir is null.");
            return false;
        }
        if (str == null) {
            LogMgr.log(2, "701 prefix is null.");
            return false;
        }
        if (!file.exists()) {
            LogMgr.log(2, "702 dir does not exists.");
            return false;
        }
        if (!file.isDirectory()) {
            LogMgr.log(2, "703 dir is not directory.");
            return false;
        }
        File[] fileArrListFiles = file.listFiles();
        if (fileArrListFiles == null) {
            LogMgr.log(2, "704 can not get file list.");
            return false;
        }
        boolean zDeleteFile = true;
        for (File file2 : fileArrListFiles) {
            if (file2.isFile()) {
                String name = file2.getName();
                if (name.startsWith(str) && !name.equals(str2)) {
                    LogMgr.log(8, "001 files = " + file2.getName());
                    zDeleteFile &= deleteFile(file2);
                }
            }
        }
        LogMgr.log(7, "999");
        return zDeleteFile;
    }

    public boolean hasSpAppletInfoList() {
        LogMgr.log(7, "000");
        boolean zHasStringData = hasStringData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SP_APPLET_INFO_LIST);
        LogMgr.log(7, "999 ret=[" + zHasStringData + "]");
        return zHasStringData;
    }

    public String readSpAppletInfoList() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SP_APPLET_INFO_LIST);
        LogMgr.log(7, "999 spAppletInfoList=[" + stringValue + "]");
        return stringValue;
    }

    public boolean writeSpAppletInfoList(String str) {
        LogMgr.log(7, "000 spAppInfoList=[" + str + "]");
        boolean zWriteStringData = (str == null || str.isEmpty()) ? false : writeStringData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SP_APPLET_INFO_LIST, str);
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public void removeSpAppletInfoList() {
        LogMgr.log(7, "000");
        removeData(FILE_NAME_CLIENT_CONFIG_INFO, KEY_SP_APPLET_INFO_LIST);
        LogMgr.log(7, "999");
    }

    public boolean hasAppletStatusCacheList(String str) {
        LogMgr.log(7, "000");
        boolean z = readAppletStatusCache(str) != null;
        LogMgr.log(7, "999 ret=[" + z + "]");
        return z;
    }

    public String readAppletStatusCache(String str) {
        LogMgr.log(7, "000");
        String string = null;
        if (str == null || str.isEmpty()) {
            LogMgr.log(7, "997  appletId is null or empty");
            return null;
        }
        JSONObject appletStatusCacheListJson = getAppletStatusCacheListJson();
        if (appletStatusCacheListJson != null) {
            try {
                string = appletStatusCacheListJson.getString(str);
            } catch (JSONException e) {
                LogMgr.log(2, "700 Failed to read sharedPrefs key[" + str + " in applet_status_cache_list]");
                LogMgr.printStackTrace(8, e);
            }
        }
        LogMgr.log(7, "999 appletStatusCache=[" + string + "]");
        return string;
    }

    public JSONObject getAppletStatusCacheListJson() {
        LogMgr.log(7, "000");
        String stringValue = getStringValue(FILE_NAME_APPLET_STATUS_DATA, KEY_APPLET_STATUS_CACHE_LIST);
        JSONObject jSONObject = null;
        try {
            if (stringValue != null) {
                jSONObject = new JSONObject(stringValue);
            } else {
                LogMgr.log(7, "998 not exist at [applet_status_cache_list]");
            }
        } catch (JSONException e) {
            LogMgr.log(2, "700 Failed to add sharedPrefs key[applet_status_cache_list]");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(7, "999");
        return jSONObject;
    }

    public boolean writeAppletStatusCache(String str, String str2) {
        LogMgr.log(7, "000 appletId=[" + str + "]");
        boolean zWriteStringData = false;
        if (str == null || str.isEmpty()) {
            LogMgr.log(7, "997  appletId is null or empty");
            return false;
        }
        if (str2 == null || str2.isEmpty()) {
            LogMgr.log(7, "998  appletStatusCacheList is null or Empty");
            return false;
        }
        JSONObject appletStatusCacheListJson = getAppletStatusCacheListJson();
        if (appletStatusCacheListJson == null) {
            appletStatusCacheListJson = new JSONObject();
        }
        try {
            appletStatusCacheListJson.put(str, str2);
            zWriteStringData = writeStringData(FILE_NAME_APPLET_STATUS_DATA, KEY_APPLET_STATUS_CACHE_LIST, appletStatusCacheListJson.toString());
        } catch (JSONException e) {
            LogMgr.log(2, "700 Failed to add sharedPrefs key[applet_status_cache_list]");
            LogMgr.printStackTrace(8, e);
        }
        LogMgr.log(7, "999 ret=[" + zWriteStringData + "]");
        return zWriteStringData;
    }

    public void removeAppletStatusCache(String str) {
        LogMgr.log(7, "000");
        if (str == null || str.isEmpty()) {
            LogMgr.log(7, "996  appletId is null or empty");
            return;
        }
        JSONObject appletStatusCacheListJson = getAppletStatusCacheListJson();
        if (appletStatusCacheListJson == null) {
            LogMgr.log(7, "997  appletStatusCacheList is null");
            return;
        }
        if (appletStatusCacheListJson.remove(str) != null) {
            writeStringData(FILE_NAME_APPLET_STATUS_DATA, KEY_APPLET_STATUS_CACHE_LIST, appletStatusCacheListJson.toString());
        } else {
            LogMgr.log(7, "998  appletId is not existed");
        }
        LogMgr.log(7, "999");
    }

    public void removeAppletStatusCacheList() {
        LogMgr.log(7, "000");
        removeData(FILE_NAME_APPLET_STATUS_DATA, KEY_APPLET_STATUS_CACHE_LIST);
        LogMgr.log(7, "999");
    }
}
