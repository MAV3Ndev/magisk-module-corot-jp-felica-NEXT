package com.felicanetworks.tis;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import com.felicanetworks.tis.ITapInteractionService;
import com.felicanetworks.tis.SharedPreferenceProvider;
import com.felicanetworks.tis.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class TapInteractionService extends Service {
    private static final Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfc.provider.FelicaContentProvider/Mfc");
    private static final String READER_NAME = "READER_NAME";
    private static final int START_WAIT = 100;
    private Context mContext = null;
    private final ITapInteractionService.Stub binder = new ITapInteractionService.Stub() { // from class: com.felicanetworks.tis.TapInteractionService.1
        /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
        /* JADX WARN: Removed duplicated region for block: B:24:0x0062  */
        @Override // com.felicanetworks.tis.ITapInteractionService
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public synchronized void executeProcessing(ProcessingInfo processingInfo) {
            byte b;
            LogMgr.log(5, "000");
            LogMgr.log(7, "[PFM]TapInteractionService#executeProcessing()");
            String action = processingInfo.getAction();
            LogMgr.log(6, "001 " + action);
            if (action == null) {
                LogMgr.log(2, "700 : action is null");
                return;
            }
            switch (action.hashCode()) {
                case -1046269374:
                    b = !action.equals(TapInteractionConst.ACTION_NOTIFICATION_SETTING) ? (byte) -1 : (byte) 1;
                    break;
                case -600795559:
                    if (action.equals("android.nfc.action.TRANSACTION_DETECTED")) {
                        b = 0;
                        break;
                    }
                    break;
                case 431322961:
                    if (action.equals(TapInteractionConst.ACTION_STOP_CHIP_ACCESS_SETTING)) {
                        b = 2;
                        break;
                    }
                    break;
                case 1737074039:
                    if (action.equals("android.intent.action.MY_PACKAGE_REPLACED")) {
                        b = 3;
                        break;
                    }
                    break;
                default:
                    break;
            }
            if (b == 0) {
                TapInteractionService tapInteractionService = TapInteractionService.this;
                if (tapInteractionService.isStopChipAccessFromAPP(tapInteractionService.mContext)) {
                    LogMgr.log(6, "002 : SharedPreference STOP_CHIP_ACCESS is true");
                    return;
                }
                String readerName = processingInfo.getReaderName();
                LogMgr.log(6, "003 : READER_NAME = " + readerName);
                byte[] event = processingInfo.getEvent();
                LogMgr.log(6, "004 : EXTRA_DATA");
                LogMgr.logArray(6, event);
                byte[] aid = processingInfo.getAid();
                LogMgr.log(6, "005 : EXTRA_AID");
                LogMgr.logArray(6, aid);
                if (readerName != null) {
                    TapInteractionService tapInteractionService2 = TapInteractionService.this;
                    if (readerName.equals(tapInteractionService2.getReaderNameFromMFC(tapInteractionService2.mContext)) && event != null) {
                        try {
                            Thread.sleep(100L);
                        } catch (InterruptedException unused) {
                        }
                        LogMgr.log(6, "006 : TapInteractionManager executeTask normal");
                        new TapInteractionManager(TapInteractionService.this.mContext, readerName, event).executeTask(true);
                    }
                }
                LogMgr.log(2, "701 : Something wrong with reader name or event");
                return;
            }
            if (b == 1) {
                new LogSender().sendSettingLog(TapInteractionService.this.mContext, processingInfo.getSetting());
            } else if (b != 2) {
                if (b == 3) {
                    if (!TapInteractionService.this.mContext.getSharedPreferences(TapInteractionConst.PREF_FILE_NAME, 0).contains(TapInteractionConst.PREF_KEY_STOP_CHIP_ACCESS)) {
                        LogMgr.log(6, "002 : TapInteractionManager executeTask clear");
                        Context context = TapInteractionService.this.mContext;
                        TapInteractionService tapInteractionService3 = TapInteractionService.this;
                        new TapInteractionManager(context, tapInteractionService3.getReaderNameFromMFC(tapInteractionService3.mContext), null).executeTask(false);
                    }
                } else {
                    LogMgr.log(2, "702 : unknown action");
                }
            } else if (!processingInfo.getSetting()) {
                LogMgr.log(6, "002 : TapInteractionManager executeTask clear");
                Context context2 = TapInteractionService.this.mContext;
                TapInteractionService tapInteractionService4 = TapInteractionService.this;
                new TapInteractionManager(context2, tapInteractionService4.getReaderNameFromMFC(tapInteractionService4.mContext), null).executeTask(false);
            }
        }
    };

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]TapInteractionService#onCreate()");
        this.mContext = getApplicationContext();
        LogMgr.log(5, "999");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]TapInteractionService#onBind()");
        LogMgr.log(5, "999");
        return this.binder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]TapInteractionService#onUnbind()");
        LogMgr.log(5, "999");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]TapInteractionService#onDestroy()");
        super.onDestroy();
        LogMgr.log(5, "999");
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    /* JADX INFO: Access modifiers changed from: private */
    public boolean isStopChipAccessFromAPP(Context context) {
        LogMgr.log(5, "000");
        String strValueOf = String.valueOf(false);
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(SharedPreferenceProvider.Contents.SETTING_CONTENT_URI, null, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    strValueOf = cursorQuery.getString(cursorQuery.getColumnIndex(SharedPreferenceProvider.Contents.Setting.STOP_CHIP_ACCESS));
                    LogMgr.log(6, "001 : isStopChipAccess = " + strValueOf);
                }
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " " + e.getMessage());
                if (cursorQuery != null) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            LogMgr.log(5, "999");
            return Boolean.parseBoolean(strValueOf);
        } finally {
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String getReaderNameFromMFC(Context context) throws Throwable {
        String str;
        LogMgr.log(5, "000");
        Cursor cursor = null;
        string = null;
        string = null;
        String string = null;
        cursor = null;
        try {
            try {
                Cursor cursorQuery = context.getContentResolver().query(CONTENT_URI, null, null, null, null);
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
                        LogMgr.log(1, "800 " + e.getClass().getSimpleName() + " " + e.getMessage());
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
            LogMgr.log(5, "999");
            return string;
        } catch (Throwable th2) {
            th = th2;
        }
    }
}
