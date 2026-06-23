package com.felicanetworks.semc.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import androidx.credentials.provider.CredentialEntry;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.felicanetworks.semc.sws.LogUploader;
import com.felicanetworks.semc.sws.NotifyClientEventWorker;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SharedPrefsProvider;

/* JADX INFO: loaded from: classes3.dex */
public class NotifyClientEventReceiver extends BroadcastReceiver {
    private LogUploader mLogUploader;

    public NotifyClientEventReceiver() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX DEBUG: Multi-variable search result rejected for r8v5, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        LogMgr.log(7, "000");
        if (Build.VERSION.SDK_INT >= 28) {
            try {
                if (!hasConnectedByUser(context)) {
                    LogMgr.log(7, "997");
                    return;
                }
                String schemeSpecificPart = null;
                Object[] objArr = 0;
                if (intent == null) {
                    LogMgr.log(2, "700 Param error. intent is null.");
                    try {
                        LogUploader.Request requestBuild = LogUploader.Request.build(context, LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.UNKNOWN, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.NOTIFY_CLIENT_EVENT_SERVICE, LogUploader.MessageCode.Process.SERVER_PROCESS, LogUploader.MessageCode.ErrorInfo.INTENT_IS_NULL, context, LogUploader.DUMMY_SE_ID));
                        LogUploader logUploader = new LogUploader(new OnUploadFinishListenerImpl());
                        this.mLogUploader = logUploader;
                        logUploader.start();
                        this.mLogUploader.request(requestBuild);
                        return;
                    } catch (Exception e) {
                        LogMgr.log(8, "998 " + e.getMessage());
                        return;
                    }
                }
                LogMgr.log(8, "001 BroadcastIntent received. act[" + intent.getAction() + "]");
                if (!"android.intent.action.PACKAGE_FULLY_REMOVED".equals(intent.getAction())) {
                    LogMgr.log(2, "701 no expected action:" + intent.getAction());
                    return;
                }
                Uri data = intent.getData();
                if (data != null) {
                    schemeSpecificPart = data.getSchemeSpecificPart();
                }
                LogMgr.log(7, "002 INTENT_PACKAGE_NAME: packageName[" + schemeSpecificPart + "]");
                WorkManager.getInstance(context).enqueue(new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) NotifyClientEventWorker.class).setInputData(new Data.Builder().putInt(NotifyClientEventWorker.EXT_KEY_EVENT_TYPE, 0).putString(NotifyClientEventWorker.EXT_KEY_PACKAGE_NAME, schemeSpecificPart).putInt("retryCount", 0).build()).build());
            } catch (Exception e2) {
                LogMgr.log(2, "702 Failed to start the service. e=" + e2);
            }
        }
        LogMgr.log(7, "999");
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private boolean hasConnectedByUser(Context context) {
        String string;
        LogMgr.log(8, "000");
        boolean z = false;
        Cursor cursorQuery = null;
        try {
            LogMgr.log(9, "001 context.getContentResolver.query() in.");
            cursorQuery = context.getContentResolver().query(SharedPrefsProvider.getSemClientUsageStatusContentsUri(context), null, null, null, null);
            LogMgr.log(9, "002 context.getContentResolver.query() out.");
            if (cursorQuery != null && cursorQuery.moveToFirst()) {
                int columnIndex = cursorQuery.getColumnIndex(SharedPrefsProvider.SemClientUsageStatusContents.Setting.HAS_ANDROID_ID_DATA);
                if (columnIndex < 0) {
                    string = CredentialEntry.FALSE_STRING;
                } else {
                    string = cursorQuery.getString(columnIndex);
                }
                LogMgr.log(9, "003 : hasAndroidIdData = " + string);
                z = Boolean.parseBoolean(string);
            }
        } catch (Exception unused) {
            if (cursorQuery != null) {
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
                throw th;
            }
            throw th;
        }
        if (cursorQuery != null) {
            cursorQuery.close();
        }
        LogMgr.log(8, "999 hasConnectedByUser=" + z);
        return z;
    }

    private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
        private OnUploadFinishListenerImpl() {
        }

        @Override // com.felicanetworks.semc.sws.LogUploader.OnUploadFinishListener
        public void onFinished(String str, String str2) {
            LogMgr.log(8, "000 requestId=" + str + " result=" + str2);
            NotifyClientEventReceiver.this.mLogUploader.shutdown();
            LogMgr.log(8, "999");
        }
    }
}
