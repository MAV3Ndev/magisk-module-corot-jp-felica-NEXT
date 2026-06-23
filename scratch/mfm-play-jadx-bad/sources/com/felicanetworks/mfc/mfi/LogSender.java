package com.felicanetworks.mfc.mfi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes3.dex */
public class LogSender {
    public static final String EXTRA_KEY_API_LEVEL = "api_level";
    public static final String EXTRA_KEY_APP_PKG = "app_pkg";
    public static final String EXTRA_KEY_CACHED = "cached";
    public static final String EXTRA_KEY_DATE = "date";
    private static final String EXTRA_KEY_EVENT_NAME = "eventName";
    private static final String EXTRA_KEY_LAUNCH_ID = "launchId";
    private static final String EXTRA_KEY_LOG_TYPE = "logType";
    public static final String EXTRA_KEY_MFIC_APP_VER = "mfic_app_ver";
    public static final String EXTRA_KEY_MODEL = "model";
    private static final String EXTRA_KEY_PREFIX = "prefix";
    public static final String EXTRA_KEY_SILENT_START_CODE = "code";
    public static final String EXTRA_VALUE_EVENT_NAME_ACCOUNT_CLEAR = "account_clear";
    public static final String EXTRA_VALUE_EVENT_NAME_ACCOUNT_FIX = "account_fix";
    public static final String EXTRA_VALUE_EVENT_NAME_APP_HASH_NULL = "permission_app_hash_null";
    public static final String EXTRA_VALUE_EVENT_NAME_CARD_DELETE = "card_delete";
    public static final String EXTRA_VALUE_EVENT_NAME_CARD_DISABLE = "card_disable";
    public static final String EXTRA_VALUE_EVENT_NAME_CHANGED_ACCOUNT_ID = "changed_account_id";
    public static final String EXTRA_VALUE_EVENT_NAME_LOGIN = "login";
    public static final String EXTRA_VALUE_EVENT_NAME_LOGIN_LATER = "skip_login";
    public static final String EXTRA_VALUE_EVENT_NAME_LOGIN_ON_SIGN_IN_ONLY = "login-04";
    public static final String EXTRA_VALUE_EVENT_NAME_LOGIN_ON_SKIPPABLE_SIGN_IN = "login-03";
    public static final String EXTRA_VALUE_EVENT_NAME_SHOW_AGREEMENT = "w2-01-01";
    public static final String EXTRA_VALUE_EVENT_NAME_SHOW_DETAIL = "link_detail";
    public static final String EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN = "w2-01-02";
    public static final String EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN_ONLY = "w2-01-04";
    public static final String EXTRA_VALUE_EVENT_NAME_SHOW_SKIPPABLE_SIGN_IN = "w2-01-03";
    public static final String EXTRA_VALUE_EVENT_NAME_SILENT_START = "silentStart";
    public static final String EXTRA_VALUE_EVENT_NAME_START = "start";
    public static final String EXTRA_VALUE_LOG_TYPE_ACTION = "action";
    public static final String EXTRA_VALUE_LOG_TYPE_DUMP = "dump";
    public static final String EXTRA_VALUE_LOG_TYPE_SCREEN = "screen";
    private static final String EXTRA_VALUE_PREFIX_MFI_CLIENT = "mfic";
    private static final String RECEIVER_CLASS_NAME = "com.felicanetworks.mfm.AppMovementReceiver";
    private static final String RECEIVER_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    private static List<String> NEW_UUID_EVENT_LIST = new ArrayList<String>() { // from class: com.felicanetworks.mfc.mfi.LogSender.1
        {
            add(LogSender.EXTRA_VALUE_EVENT_NAME_APP_HASH_NULL);
            add(LogSender.EXTRA_VALUE_EVENT_NAME_START);
            add(LogSender.EXTRA_VALUE_EVENT_NAME_SILENT_START);
            add(LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_AGREEMENT);
            add(LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN);
            add(LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SKIPPABLE_SIGN_IN);
            add(LogSender.EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN_ONLY);
            add(LogSender.EXTRA_VALUE_EVENT_NAME_ACCOUNT_CLEAR);
        }
    };
    private static final Object LOCK_UUID = new Object();
    private static String sUUID = "";

    public static void prepareUUID(String eventName) {
        LogMgr.log(6, "000");
        try {
            if (NEW_UUID_EVENT_LIST.contains(eventName)) {
                if (EXTRA_VALUE_EVENT_NAME_SILENT_START.equals(eventName) || EXTRA_VALUE_EVENT_NAME_SHOW_AGREEMENT.equals(eventName) || EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN.equals(eventName) || EXTRA_VALUE_EVENT_NAME_SHOW_SKIPPABLE_SIGN_IN.equals(eventName) || EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN_ONLY.equals(eventName)) {
                    PreAccountCache preAccountCache = PreAccountCache.getInstance();
                    String uuid = preAccountCache.getUUID();
                    if (uuid != null) {
                        synchronized (LOCK_UUID) {
                            sUUID = uuid;
                        }
                    } else {
                        newUUID();
                    }
                    preAccountCache.updateUuidCache(getUUID());
                } else {
                    newUUID();
                }
            } else {
                LogMgr.log(6, "001 No preparation.");
            }
        } catch (Exception e) {
            LogMgr.log(2, "701 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(6, "999 : UUID=" + getUUID());
    }

    private static void newUUID() {
        LogMgr.log(6, "000");
        String string = UUID.randomUUID().toString();
        synchronized (LOCK_UUID) {
            sUUID = string;
        }
        LogMgr.log(6, "999 : UUID=" + string);
    }

    public static String getUUID() {
        String str;
        synchronized (LOCK_UUID) {
            str = sUUID;
        }
        return str;
    }

    public static void send(Context context, String eventName, String logType) {
        send(context, eventName, logType, null);
    }

    public static void send(Context context, String eventName, String logType, Bundle extras) {
        LogMgr.log(5, "000 eventName=" + eventName + ",logType=" + logType);
        if (context == null) {
            LogMgr.log(2, "700 : Context is null.");
            return;
        }
        try {
            prepareUUID(eventName);
            String uuid = getUUID();
            if (EXTRA_VALUE_EVENT_NAME_SHOW_AGREEMENT.equals(eventName) || EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN.equals(eventName) || EXTRA_VALUE_EVENT_NAME_SHOW_SKIPPABLE_SIGN_IN.equals(eventName) || EXTRA_VALUE_EVENT_NAME_SHOW_SIGN_IN_ONLY.equals(eventName)) {
                LogSenderPreferences.getInstance().putLatestUuidDisplay(uuid);
            } else if (EXTRA_VALUE_EVENT_NAME_ACCOUNT_FIX.equals(eventName) && uuid != null && !uuid.equals(LogSenderPreferences.getInstance().getLatestUuidDisplay())) {
                LogMgr.log(5, "998 account_fix : UUID does not match.");
                return;
            }
            Intent intent = new Intent();
            intent.setClassName("com.felicanetworks.mfm.main", RECEIVER_CLASS_NAME);
            intent.setFlags(32);
            intent.putExtra(EXTRA_KEY_EVENT_NAME, eventName);
            intent.putExtra(EXTRA_KEY_PREFIX, EXTRA_VALUE_PREFIX_MFI_CLIENT);
            intent.putExtra(EXTRA_KEY_LOG_TYPE, logType);
            intent.putExtra(EXTRA_KEY_LAUNCH_ID, uuid);
            if (extras != null) {
                intent.putExtras(extras);
            }
            context.sendBroadcast(intent);
        } catch (Exception e) {
            LogMgr.log(2, "701 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
            LogMgr.printStackTrace(7, e);
        }
        LogMgr.log(5, "999");
    }
}
