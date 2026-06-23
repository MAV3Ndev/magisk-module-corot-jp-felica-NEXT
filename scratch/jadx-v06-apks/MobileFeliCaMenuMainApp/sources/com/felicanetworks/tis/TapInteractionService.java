package com.felicanetworks.tis;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import androidx.core.app.JobIntentService;
import com.felicanetworks.tis.SharedPreferenceProvider;
import com.felicanetworks.tis.util.AccessConfig;
import com.felicanetworks.tis.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class TapInteractionService extends JobIntentService {
    private static final int JOB_ID = 1000;
    private static final int START_WAIT = 100;

    private interface Contents {
        public static final Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfc.provider.FelicaContentProvider/Mfc");

        public interface Columns {
            public static final String READER_NAME = "READER_NAME";
        }
    }

    static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, (Class<?>) TapInteractionService.class, 1000, intent);
    }

    @Override // androidx.core.app.JobIntentService
    protected void onHandleWork(Intent intent) {
        LogMgr.log(4, "000");
        if (AccessConfig.isTisUnavailable()) {
            LogMgr.log(2, "702 : TapInteraction is unavailable in This Device");
            return;
        }
        Context applicationContext = getApplicationContext();
        String action = intent.getAction();
        byte b = -1;
        int iHashCode = action.hashCode();
        if (iHashCode != -1046269374) {
            if (iHashCode != -600795559) {
                if (iHashCode == 1737074039 && action.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                    b = 2;
                }
            } else if (action.equals("android.nfc.action.TRANSACTION_DETECTED")) {
                b = 0;
            }
        } else if (action.equals("com.felicanetworks.tis.action.NOTIFICATION_SETTING")) {
            b = 1;
        }
        if (b == 0) {
            LogMgr.log(6, "001 " + action);
            if (!getNotificationDisplayFromAPP(applicationContext)) {
                LogMgr.log(6, "002 : SharedPreference NOTIFICATION_DISPLAY is false");
                return;
            }
            String stringExtra = intent.getStringExtra("android.nfc.extra.SECURE_ELEMENT_NAME");
            LogMgr.log(6, "002 : READER_NAME = " + stringExtra);
            byte[] byteArrayExtra = intent.getByteArrayExtra("android.nfc.extra.DATA");
            LogMgr.log(6, "003 : EXTRA_DATA");
            LogMgr.logArray(6, byteArrayExtra);
            byte[] byteArrayExtra2 = intent.getByteArrayExtra("android.nfc.extra.AID");
            LogMgr.log(6, "004 : EXTRA_AID");
            LogMgr.logArray(6, byteArrayExtra2);
            if (stringExtra == null || !stringExtra.equals(getReaderNameFromMFC(applicationContext)) || byteArrayExtra == null) {
                LogMgr.log(2, "701 : Something wrong with reader name or event");
                return;
            } else {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException unused) {
                }
                LogMgr.log(6, "005 : TapInteractionManager executeTask normal");
                new TapInteractionManager(this, stringExtra, byteArrayExtra).executeTask(true);
            }
        } else if (b == 1) {
            LogMgr.log(6, "001 " + action);
            boolean booleanExtra = intent.getBooleanExtra("com.felicanetworks.tis.EXTRA_SETTING", false);
            new LogSender().sendSettingLog(applicationContext, booleanExtra);
            if (booleanExtra) {
                LogMgr.log(6, "002 : TapInteractionManager executeTask clear");
                new TapInteractionManager(this, getReaderNameFromMFC(applicationContext), null).executeTask(false);
            }
        } else if (b == 2) {
            LogMgr.log(6, "001 " + action);
            SharedPreferences sharedPreferences = applicationContext.getSharedPreferences("mfi_ti_setting", 0);
            if (AccessConfig.isGPDevice()) {
                if (!sharedPreferences.contains("ti_notification_display")) {
                    LogMgr.log(6, "002 : TapInteractionManager executeTask clear GP");
                    new TapInteractionManager(this, getReaderNameFromMFC(applicationContext), null).executeTask(false);
                }
            } else {
                sharedPreferences.contains("ti_notification_display");
            }
        }
        LogMgr.log(4, "999");
    }

    private boolean getNotificationDisplayFromAPP(Context context) {
        LogMgr.log(6, "000");
        String strValueOf = String.valueOf(false);
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(SharedPreferenceProvider.Contents.CONTENT_URI, null, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    strValueOf = cursorQuery.getString(cursorQuery.getColumnIndex(SharedPreferenceProvider.Contents.Setting.NOTIFICATION_DISPLAY));
                    LogMgr.log(6, "001 : display = " + strValueOf);
                }
            } catch (Exception e) {
                LogMgr.log(2, "700" + e.getClass().getSimpleName() + " " + e.getMessage());
                if (cursorQuery != null) {
                }
            }
            LogMgr.log(6, "999");
            return Boolean.valueOf(strValueOf).booleanValue();
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    private String getReaderNameFromMFC(Context context) throws Throwable {
        String str;
        LogMgr.log(6, "000");
        Cursor cursor = null;
        string = null;
        string = null;
        String string = null;
        cursor = null;
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(Contents.CONTENT_URI, null, null, null, null);
                if (cursorQuery != null) {
                    try {
                        if (cursorQuery.moveToFirst()) {
                            string = cursorQuery.getString(cursorQuery.getColumnIndex("READER_NAME"));
                            LogMgr.log(6, "001 : readerName = " + string);
                        }
                    } catch (Exception e) {
                        e = e;
                        String str2 = string;
                        cursor = cursorQuery;
                        str = str2;
                        LogMgr.log(1, "800" + e.getClass().getSimpleName() + " " + e.getMessage());
                        if (cursor != null) {
                            cursor.close();
                        }
                        string = str;
                    } catch (Throwable th) {
                        th = th;
                        cursor = cursorQuery;
                        if (cursor != null) {
                            cursor.close();
                        }
                        throw th;
                    }
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
            } catch (Exception e2) {
                e = e2;
                str = null;
            }
            LogMgr.log(6, "999");
            return string;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
