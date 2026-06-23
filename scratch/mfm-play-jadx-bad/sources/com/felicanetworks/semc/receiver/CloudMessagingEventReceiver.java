package com.felicanetworks.semc.receiver;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.credentials.provider.CredentialEntry;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.felicanetworks.mfm.messenger.MessageProtocol;
import com.felicanetworks.mfm.messenger.MessageReceiver;
import com.felicanetworks.semc.ISemClientImpl;
import com.felicanetworks.semc.fcm.CloudMessagingWorker;
import com.felicanetworks.semc.sws.LogUploader;
import com.felicanetworks.semc.util.LogMgr;
import com.felicanetworks.semc.util.ObfuscatedMsgUtil;
import com.felicanetworks.semc.util.SharedPrefsUtil;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
public class CloudMessagingEventReceiver {

    public static class ChannelMessenger extends MessageReceiver {
        private LogUploader mLogUploader;

        public ChannelMessenger() {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
        }

        /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected boolean isAccepted(Context context, MessageProtocol.Action action) {
            String string;
            LogMgr.log(6, "000");
            boolean z = false;
            Cursor cursorQuery = null;
            try {
                LogMgr.log(9, "001 mContext.getContentResolver.query() in.");
                cursorQuery = context.getContentResolver().query(ConnectStatusProvider.getGetConnectStatusContentsUri(context), null, null, null, null);
                LogMgr.log(9, "002 mContext.getContentResolver.query() out.");
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    int columnIndex = cursorQuery.getColumnIndex(ConnectStatusProvider.GetConnectStatusContents.Setting.HAS_CONNECTED_BY_USER);
                    String string2 = CredentialEntry.FALSE_STRING;
                    if (columnIndex < 0) {
                        string = CredentialEntry.FALSE_STRING;
                    } else {
                        string = cursorQuery.getString(columnIndex);
                    }
                    LogMgr.log(9, "003 : hasConnectedByUser = " + string);
                    int columnIndex2 = cursorQuery.getColumnIndex(ConnectStatusProvider.GetConnectStatusContents.Setting.IS_CONNECTING);
                    if (columnIndex2 >= 0) {
                        string2 = cursorQuery.getString(columnIndex2);
                    }
                    LogMgr.log(9, "004 : isConnecting = " + string2);
                    if (Boolean.parseBoolean(string) && !Boolean.parseBoolean(string2)) {
                        LogMgr.log(9, "005");
                        z = true;
                    }
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
            LogMgr.log(6, "999 ret=" + z);
            return z;
        }

        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected void onReceiveMessage(Context context, MessageProtocol.Action action, Map<String, String> map) {
            LogMgr.log(5, "000 action=" + action.name());
            if (Build.VERSION.SDK_INT >= 28) {
                Bundle bundle = new Bundle();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    LogMgr.log(9, "001 messages:" + entry.getKey() + "=" + entry.getValue());
                    bundle.putString(entry.getKey(), entry.getValue());
                }
                onNewMessageReceived(context, bundle);
            }
            LogMgr.log(5, "999");
        }

        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected void onReceiveToken(Context context, String str) {
            LogMgr.log(5, "000 token=" + str);
            if (Build.VERSION.SDK_INT >= 28) {
                onNewTokenReceived(context);
            }
            LogMgr.log(5, "999");
        }

        private void onNewMessageReceived(Context context, Bundle bundle) {
            LogMgr.log(7, "000");
            if (bundle.isEmpty()) {
                LogMgr.log(2, "700 Param error. Bundle is empty.");
                LogUploader logUploader = new LogUploader(new OnUploadFinishListenerImpl());
                this.mLogUploader = logUploader;
                logUploader.start();
                try {
                    this.mLogUploader.request(LogUploader.Request.build(context, LogUploader.DUMMY_SE_ID, LogUploader.createLogInfoContent(LogUploader.Message.Api.UNKNOWN, LogUploader.Message.ErrorType.OTHER_ERROR, null, ObfuscatedMsgUtil.backgroundExecutionPoint(), LogUploader.MessageCode.SendTiming.ON_MESSAGE_RECEIVED, LogUploader.MessageCode.Process.SERVER_PROCESS, LogUploader.MessageCode.ErrorInfo.BUNDLE_IS_EMPTY, context, LogUploader.DUMMY_SE_ID)));
                    LogMgr.log(7, "998");
                    return;
                } catch (Exception e) {
                    LogMgr.log(8, "997 " + e.getMessage());
                    return;
                }
            }
            WorkManager.getInstance(context).enqueue(new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) CloudMessagingWorker.class).setInputData(new Data.Builder().putInt("action", 0).putString(CloudMessagingWorker.EXT_KEY_ADDRESS, bundle.getString(CloudMessagingWorker.EXT_KEY_ADDRESS)).putString(CloudMessagingWorker.EXT_KEY_MESSAGE_TYPE, bundle.getString(CloudMessagingWorker.EXT_KEY_MESSAGE_TYPE)).putString(CloudMessagingWorker.EXT_KEY_LINKAGE_DATA, bundle.getString(CloudMessagingWorker.EXT_KEY_LINKAGE_DATA)).putInt("retryCount", 0).build()).build());
            LogMgr.log(7, "999");
        }

        private void onNewTokenReceived(Context context) {
            LogMgr.log(7, "000");
            WorkManager.getInstance(context).enqueue(new OneTimeWorkRequest.Builder((Class<? extends ListenableWorker>) CloudMessagingWorker.class).setInputData(new Data.Builder().putInt("action", 1).build()).build());
            LogMgr.log(7, "999");
        }

        private class OnUploadFinishListenerImpl implements LogUploader.OnUploadFinishListener {
            private OnUploadFinishListenerImpl() {
            }

            @Override // com.felicanetworks.semc.sws.LogUploader.OnUploadFinishListener
            public void onFinished(String str, String str2) {
                LogMgr.log(8, "000 requestId=" + str + " result=" + str2);
                ChannelMessenger.this.mLogUploader.shutdown();
                LogMgr.log(8, "999");
            }
        }
    }

    public static class ConnectStatusProvider extends ContentProvider {
        private static final String AUTHORITY = "com.felicanetworks.mfm.main.receiver.CloudMessagingEventReceiver$ConnectStatusProvider";
        private static final String CONTENT_PROVIDER_AUTHORITY_SUFFIX = ".receiver.CloudMessagingEventReceiver$ConnectStatusProvider";
        private static final int GET_CONNECT_STATUS = 1;
        private static final String MENU_APP_PACKAGE_NAME_DEV2 = "com.felicanetworks.mfmsemcdev";
        private static final String MENU_APP_PACKAGE_NAME_SEMC = "com.felicanetworks.semcapp";
        private static final String MENU_APP_PACKAGE_NAME_SEMC_VERIFICATION = "com.felicanetworks.semcappdev";
        private static AtomicBoolean sIsInitializedUri;
        private static volatile UriMatcher sUriMatcher = new UriMatcher(-1);

        public static class GetConnectStatusContents {
            public static volatile Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfm.main.receiver.CloudMessagingEventReceiver$ConnectStatusProvider/GetConnectStatus");
            public static final String PATH = "GetConnectStatus";

            public static class Setting {
                public static final String HAS_CONNECTED_BY_USER = "has_connected_by_user";
                public static final String IS_CONNECTING = "is_connecting";
            }
        }

        static {
            sUriMatcher.addURI(AUTHORITY, GetConnectStatusContents.PATH, 1);
            sIsInitializedUri = new AtomicBoolean(false);
        }

        public static Uri getGetConnectStatusContentsUri(Context context) {
            StringBuilder sb = new StringBuilder("000 context is null=");
            sb.append(context == null);
            LogMgr.log(6, sb.toString());
            if (!sIsInitializedUri.get()) {
                initializeUri(context);
                LogMgr.log(8, "Called initialize URI.");
            }
            LogMgr.log(6, "999 CONTENT_URI=" + GetConnectStatusContents.CONTENT_URI);
            return GetConnectStatusContents.CONTENT_URI;
        }

        /* JADX WARN: Can't wrap try/catch for region: R(8:6|7|(3:(1:(1:32)(6:27|48|28|29|55|34))(6:19|53|20|21|55|34)|46|47)(4:11|50|12|13)|33|55|34|46|47) */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x00d1, code lost:
        
            r6 = move-exception;
         */
        /* JADX WARN: Code restructure failed: missing block: B:37:0x00d2, code lost:
        
            r9 = r4;
            r4 = r10;
            r10 = r6;
            r6 = r5;
            r5 = r9;
         */
        /* JADX WARN: Removed duplicated region for block: B:44:0x0124  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public static void initializeUri(Context context) {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            boolean z6;
            LogMgr.log(6, "000");
            try {
            } catch (Exception e) {
                e = e;
                z = false;
                z2 = false;
            }
            if (context == null) {
                LogMgr.log(2, "700 Content is null. ContentProvider's AUTHORITY is set with [com.felicanetworks.mfm.main.receiver.CloudMessagingEventReceiver$ConnectStatusProvider]");
                return;
            }
            String packageName = context.getPackageName();
            if (packageName != null && packageName.equals(MENU_APP_PACKAGE_NAME_DEV2)) {
                LogMgr.log(9, "001");
                try {
                    GetConnectStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.mfmsemcdev.receiver.CloudMessagingEventReceiver$ConnectStatusProvider/GetConnectStatus");
                    LogMgr.log(9, "002 set CONTENT_URI for dev2.");
                    sUriMatcher = new UriMatcher(-1);
                    sUriMatcher.addURI("com.felicanetworks.mfmsemcdev.receiver.CloudMessagingEventReceiver$ConnectStatusProvider", GetConnectStatusContents.PATH, 1);
                    LogMgr.log(9, "003 added uri for dev2.");
                    z4 = true;
                    z5 = false;
                } catch (Exception e2) {
                    e = e2;
                    z = true;
                    z2 = false;
                    z3 = z2;
                    LogMgr.log(2, "701 Failed to set AUTHORITY. err[" + e.getClass().getSimpleName() + "]");
                    StringBuilder sb = new StringBuilder("702 isDev2MenuApp[");
                    sb.append(z);
                    sb.append("], isSemcMenuApp[");
                    sb.append(z2);
                    sb.append("], isSemcVerificationApp[");
                    sb.append(z3);
                    sb.append("], is success sUriMatcher#addURI [");
                    sb.append(sUriMatcher.match(GetConnectStatusContents.CONTENT_URI) == 1);
                    sb.append("]");
                    LogMgr.log(2, sb.toString());
                    LogMgr.log(6, "999");
                }
            } else {
                if (packageName != null && packageName.equals("com.felicanetworks.semcapp")) {
                    LogMgr.log(9, "004");
                    try {
                        GetConnectStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcapp.receiver.CloudMessagingEventReceiver$ConnectStatusProvider/GetConnectStatus");
                        LogMgr.log(9, "005 set CONTENT_URI for semc.");
                        sUriMatcher = new UriMatcher(-1);
                        sUriMatcher.addURI("com.felicanetworks.semcapp.receiver.CloudMessagingEventReceiver$ConnectStatusProvider", GetConnectStatusContents.PATH, 1);
                        LogMgr.log(9, "006 added uri for semc.");
                        z5 = true;
                        z4 = false;
                        z6 = false;
                        sIsInitializedUri.set(true);
                        LogMgr.log(9, "010 Set initialized Uri Flag to true.");
                    } catch (Exception e3) {
                        e = e3;
                        z2 = true;
                        z = false;
                        z3 = false;
                        LogMgr.log(2, "701 Failed to set AUTHORITY. err[" + e.getClass().getSimpleName() + "]");
                        StringBuilder sb2 = new StringBuilder("702 isDev2MenuApp[");
                        sb2.append(z);
                        sb2.append("], isSemcMenuApp[");
                        sb2.append(z2);
                        sb2.append("], isSemcVerificationApp[");
                        sb2.append(z3);
                        sb2.append("], is success sUriMatcher#addURI [");
                        sb2.append(sUriMatcher.match(GetConnectStatusContents.CONTENT_URI) == 1);
                        sb2.append("]");
                        LogMgr.log(2, sb2.toString());
                        LogMgr.log(6, "999");
                    }
                } else if (packageName == null || !packageName.equals(MENU_APP_PACKAGE_NAME_SEMC_VERIFICATION)) {
                    z4 = false;
                    z5 = false;
                } else {
                    LogMgr.log(9, "007");
                    try {
                        GetConnectStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcappdev.receiver.CloudMessagingEventReceiver$ConnectStatusProvider/GetConnectStatus");
                        LogMgr.log(9, "008 set CONTENT_URI for semc verification.");
                        sUriMatcher = new UriMatcher(-1);
                        sUriMatcher.addURI("com.felicanetworks.semcappdev.receiver.CloudMessagingEventReceiver$ConnectStatusProvider", GetConnectStatusContents.PATH, 1);
                        LogMgr.log(9, "009 added uri for semc verification.");
                        z6 = true;
                        z4 = false;
                        z5 = false;
                        sIsInitializedUri.set(true);
                        LogMgr.log(9, "010 Set initialized Uri Flag to true.");
                    } catch (Exception e4) {
                        e = e4;
                        z3 = true;
                        z = false;
                        z2 = false;
                        LogMgr.log(2, "701 Failed to set AUTHORITY. err[" + e.getClass().getSimpleName() + "]");
                        StringBuilder sb22 = new StringBuilder("702 isDev2MenuApp[");
                        sb22.append(z);
                        sb22.append("], isSemcMenuApp[");
                        sb22.append(z2);
                        sb22.append("], isSemcVerificationApp[");
                        sb22.append(z3);
                        sb22.append("], is success sUriMatcher#addURI [");
                        sb22.append(sUriMatcher.match(GetConnectStatusContents.CONTENT_URI) == 1);
                        sb22.append("]");
                        LogMgr.log(2, sb22.toString());
                        LogMgr.log(6, "999");
                    }
                }
                LogMgr.log(6, "999");
            }
            z6 = z5;
            sIsInitializedUri.set(true);
            LogMgr.log(9, "010 Set initialized Uri Flag to true.");
            LogMgr.log(6, "999");
        }

        @Override // android.content.ContentProvider
        public boolean onCreate() {
            LogMgr.log(6, "000");
            if (!sIsInitializedUri.get()) {
                initializeUri(getContext());
                LogMgr.log(8, "Called initialize URI.");
            }
            LogMgr.log(6, "999");
            return true;
        }

        @Override // android.content.ContentProvider
        public String getType(Uri uri) {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
            throw new UnsupportedOperationException("");
        }

        @Override // android.content.ContentProvider
        public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
            LogMgr.log(6, "000");
            if (sUriMatcher.match(uri) == 1) {
                LogMgr.log(6, "001 uri is GetConnectStatusContents");
                LogMgr.log(6, "997");
                return getConnectStatus();
            }
            LogMgr.log(6, "999");
            throw new IllegalArgumentException("Invalid URI：" + uri);
        }

        private Cursor getConnectStatus() {
            LogMgr.log(9, "000");
            ISemClientImpl iSemClientImpl = ISemClientImpl.getInstance();
            SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getContext());
            boolean zIsConnecting = iSemClientImpl.isConnecting();
            boolean zHasAndroidIdData = sharedPrefsUtil.hasAndroidIdData();
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{GetConnectStatusContents.Setting.IS_CONNECTING, GetConnectStatusContents.Setting.HAS_CONNECTED_BY_USER});
            matrixCursor.addRow(new Object[]{String.valueOf(zIsConnecting), String.valueOf(zHasAndroidIdData)});
            LogMgr.log(9, "999");
            return matrixCursor;
        }

        @Override // android.content.ContentProvider
        public Uri insert(Uri uri, ContentValues contentValues) {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
            throw new IllegalArgumentException("Invalid URI：" + uri);
        }

        @Override // android.content.ContentProvider
        public int delete(Uri uri, String str, String[] strArr) {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
            throw new UnsupportedOperationException("");
        }

        @Override // android.content.ContentProvider
        public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
            LogMgr.log(6, "000");
            LogMgr.log(6, "999");
            throw new UnsupportedOperationException("");
        }
    }
}
