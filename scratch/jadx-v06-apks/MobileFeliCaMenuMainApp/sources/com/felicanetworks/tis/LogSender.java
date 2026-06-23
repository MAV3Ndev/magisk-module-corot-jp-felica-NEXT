package com.felicanetworks.tis;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import com.felicanetworks.tis.SharedPreferenceProvider;
import com.felicanetworks.tis.datatype.LogInfo;
import com.felicanetworks.tis.util.ByteBufferMgr;
import com.felicanetworks.tis.util.LogMgr;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class LogSender {
    private static final String EXTRA_KEY_API_LEVEL = "api_level";
    private static final String EXTRA_KEY_DISPLAY_MODE = "display_mode";
    private static final String EXTRA_KEY_EVENT_NAME = "eventName";
    private static final String EXTRA_KEY_IDM = "idm";
    private static final String EXTRA_KEY_LAUNCH_ID = "launchId";
    private static final String EXTRA_KEY_LOG_TYPE = "logType";
    private static final String EXTRA_KEY_PAY_AMT = "pay_amt";
    private static final String EXTRA_KEY_PAY_BAL = "pay_bal";
    private static final String EXTRA_KEY_PAY_TYP = "pay_typ";
    private static final String EXTRA_KEY_PREFIX = "prefix";
    private static final String EXTRA_KEY_SERVICE_PROVIDER_ID = "service_provider_id";
    private static final String EXTRA_KEY_SETTING_VALUE = "setting_value";
    private static final String EXTRA_KEY_TI_VERSION = "ti_app_ver";
    private static final String EXTRA_KEY_USAGE_DATA = "usage_data";
    private static final String EXTRA_VALUE_EVENT_NAME_LAUNCH = "osaifu_app_launch";
    private static final String EXTRA_VALUE_EVENT_NAME_NOTIFICATION = "notify";
    private static final String EXTRA_VALUE_EVENT_NAME_SETTING = "setting";
    private static final String EXTRA_VALUE_LOG_TYPE_LAUNCH = "action";
    private static final String EXTRA_VALUE_LOG_TYPE_NOTIFICATION = "dump";
    private static final String EXTRA_VALUE_LOG_TYPE_SETTING = "action";
    private static final String EXTRA_VALUE_PREFIX_LAUNCH = "tis";
    private static final String EXTRA_VALUE_PREFIX_NOTIFICATION = "tis";
    private static final String EXTRA_VALUE_PREFIX_SETTING = "tis";
    private static final String EXTRA_VALUE_SETTING_VALUE_OFF = "off";
    private static final String EXTRA_VALUE_SETTING_VALUE_ON = "on";
    private static final String RECEIVER_CLASS_NAME = "com.felicanetworks.mfm.AppMovementReceiver";
    private static final String RECEIVER_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    private String mUUID;

    public LogSender() {
        this.mUUID = null;
        if (0 == 0) {
            this.mUUID = UUID.randomUUID().toString();
        }
    }

    public void sendSettingLog(Context context, boolean z) {
        LogMgr.log(4, "000");
        if (context == null) {
            LogMgr.log(2, "700 Context is null.");
            return;
        }
        try {
            context.sendBroadcast(new Intent().setComponent(new ComponentName("com.felicanetworks.mfm.main", RECEIVER_CLASS_NAME)).putExtra(EXTRA_KEY_EVENT_NAME, EXTRA_VALUE_EVENT_NAME_SETTING).putExtra(EXTRA_KEY_PREFIX, "tis").putExtra(EXTRA_KEY_LOG_TYPE, com.felicanetworks.mfc.mfi.LogSender.EXTRA_VALUE_LOG_TYPE_ACTION).putExtra(EXTRA_KEY_LAUNCH_ID, UUID.randomUUID().toString()).putExtra("api_level", String.valueOf(Build.VERSION.SDK_INT)).putExtra(EXTRA_KEY_TI_VERSION, context.getString(R.string.tis_version)).putExtra(EXTRA_KEY_SETTING_VALUE, z ? EXTRA_VALUE_SETTING_VALUE_ON : EXTRA_VALUE_SETTING_VALUE_OFF));
        } catch (Exception unused) {
            LogMgr.log(2, "700 Send setting log failed.");
        }
        LogMgr.log(4, "999");
    }

    public void sendNotificationLog(Context context, byte[] bArr, String str, LogInfo logInfo) {
        LogMgr.log(4, "000");
        if (context == null) {
            LogMgr.log(2, "700 Context is null.");
            return;
        }
        if (this.mUUID == null) {
            this.mUUID = UUID.randomUUID().toString();
        }
        try {
            Intent intentPutExtra = new Intent().setComponent(new ComponentName("com.felicanetworks.mfm.main", RECEIVER_CLASS_NAME)).putExtra(EXTRA_KEY_EVENT_NAME, EXTRA_VALUE_EVENT_NAME_NOTIFICATION).putExtra(EXTRA_KEY_PREFIX, "tis").putExtra(EXTRA_KEY_LOG_TYPE, "dump").putExtra(EXTRA_KEY_LAUNCH_ID, this.mUUID).putExtra("api_level", String.valueOf(Build.VERSION.SDK_INT)).putExtra(EXTRA_KEY_IDM, ByteBufferMgr.bytesToHex(bArr)).putExtra(EXTRA_KEY_TI_VERSION, context.getString(R.string.tis_version)).putExtra(EXTRA_KEY_SERVICE_PROVIDER_ID, str).putExtra(EXTRA_KEY_DISPLAY_MODE, getDisplayMode(context));
            intentPutExtra.putExtra(EXTRA_KEY_PAY_TYP, logInfo.getPayType()).putExtra(EXTRA_KEY_PAY_AMT, logInfo.getPayAmount()).putExtra(EXTRA_KEY_PAY_BAL, logInfo.getPayBalance());
            intentPutExtra.putExtra(EXTRA_KEY_USAGE_DATA, logInfo.getUsageData());
            context.sendBroadcast(intentPutExtra);
            LogMgr.log(6, "001 " + intentPutExtra.toString());
        } catch (Exception unused) {
            LogMgr.log(2, "700 Send notification log failed.");
        }
        LogMgr.log(4, "999");
    }

    public void sendAppLaunchLog(Context context, String str, String str2) {
        LogMgr.log(4, "000");
        if (context == null) {
            LogMgr.log(2, "700 Context is null.");
            return;
        }
        try {
            Intent intentPutExtra = new Intent().setComponent(new ComponentName("com.felicanetworks.mfm.main", RECEIVER_CLASS_NAME)).putExtra(EXTRA_KEY_EVENT_NAME, EXTRA_VALUE_EVENT_NAME_LAUNCH).putExtra(EXTRA_KEY_PREFIX, "tis").putExtra(EXTRA_KEY_LOG_TYPE, com.felicanetworks.mfc.mfi.LogSender.EXTRA_VALUE_LOG_TYPE_ACTION).putExtra(EXTRA_KEY_LAUNCH_ID, str2).putExtra(EXTRA_KEY_SERVICE_PROVIDER_ID, str);
            context.sendBroadcast(intentPutExtra);
            LogMgr.log(6, "001 " + intentPutExtra.toString());
        } catch (Exception unused) {
            LogMgr.log(2, "700 Send app launch log failed.");
        }
        LogMgr.log(4, "999");
    }

    public String getUuid() {
        return this.mUUID;
    }

    private String getDisplayMode(Context context) {
        LogMgr.log(6, "000");
        String string = "notice";
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(SharedPreferenceProvider.Contents.CONTENT_URI, null, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    string = cursorQuery.getString(cursorQuery.getColumnIndex(SharedPreferenceProvider.Contents.Setting.NOTIFICATION_DISPLAY_MODE));
                    LogMgr.log(6, "001 : mode = " + string);
                }
            } catch (Exception e) {
                LogMgr.log(2, "700" + e.getClass().getSimpleName() + " " + e.getMessage());
                if (cursorQuery != null) {
                }
            }
            LogMgr.log(6, "999");
            return string;
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }
}
