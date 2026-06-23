package com.felicanetworks.semc.util;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.felicanetworks.semc.sws.json.ProfileDataJson;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;

/* JADX INFO: loaded from: classes3.dex */
public class SharedPrefsProvider extends ContentProvider {
    private static final String AUTHORITY = "com.felicanetworks.mfm.main.util.SharedPrefsProvider";
    private static final String CONTENT_PROVIDER_AUTHORITY_SUFFIX = ".util.SharedPrefsProvider";
    private static final String MENU_APP_PACKAGE_NAME_DEV2 = "com.felicanetworks.mfmsemcdev";
    private static final String MENU_APP_PACKAGE_NAME_SEMC = "com.felicanetworks.semcapp";
    private static final String MENU_APP_PACKAGE_NAME_SEMC_VERIFICATION = "com.felicanetworks.semcappdev";
    private static final int NOTIFY_CLIENT_EVENT = 1;
    private static final int NOTIFY_CLIENT_LOG = 2;
    private static final int ROUTINE_WORKER_STATUS = 4;
    private static final int SEM_CLIENT_USAGE_STATUS = 3;
    private static AtomicBoolean sIsInitializedUri;
    private static volatile UriMatcher sUriMatcher = new UriMatcher(-1);

    public static class NotifyClientEventContents {
        public static Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfm.main.util.SharedPrefsProvider/NotifyClientEvent");
        public static final String PATH = "NotifyClientEvent";

        public static class Setting {
            public static final String IS_EXISTS = "is_exists";
            public static final String SAVE_DATE = "save_date";
            public static final String SP_APP_INFO_LIST_STR = "sp_app_info_list_str";
        }
    }

    public static class NotifyClientLogContents {
        public static Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfm.main.util.SharedPrefsProvider/NotifyClientLog");
        public static final String PATH = "NotifyClientLog";

        public static class Setting {
            public static final String CLIENT_ID = "client_id";
            public static final String CONTROL_INFO = "control_info";
            public static final String PROFILE_ID = "profile_id";
            public static final String URL_IN_PROFILE = "url_in_profile";
        }
    }

    public static class RoutineWorkerStatusContents {
        public static volatile Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfm.main.util.SharedPrefsProvider/RoutineWorkerStatus");
        public static final String PATH = "RoutineWorkerStatus";

        public static class Setting {
            public static final String NEEDS_EXECUTE_ROUTINE_WORK = "needsExecuteRoutineWork";
        }
    }

    public static class SemClientUsageStatusContents {
        public static Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.mfm.main.util.SharedPrefsProvider/SemClientUsageStatus");
        public static final String PATH = "SemClientUsageStatus";

        public static class Setting {
            public static final String HAS_ANDROID_ID_DATA = "hasAndroidIdData";
        }
    }

    static {
        sUriMatcher.addURI(AUTHORITY, NotifyClientEventContents.PATH, 1);
        sUriMatcher.addURI(AUTHORITY, NotifyClientLogContents.PATH, 2);
        sUriMatcher.addURI(AUTHORITY, SemClientUsageStatusContents.PATH, 3);
        sUriMatcher.addURI(AUTHORITY, RoutineWorkerStatusContents.PATH, 4);
        sIsInitializedUri = new AtomicBoolean(false);
    }

    public static Uri getNotifyClientEventContentsUri(Context context) {
        StringBuilder sb = new StringBuilder("000 context is null=");
        sb.append(context == null);
        LogMgr.log(6, sb.toString());
        if (!sIsInitializedUri.get()) {
            initializeUri(context);
            LogMgr.log(8, "Called initialize URI.");
        }
        LogMgr.log(6, "999 CONTENT_URI=" + NotifyClientEventContents.CONTENT_URI);
        return NotifyClientEventContents.CONTENT_URI;
    }

    public static Uri getNotifyClientLogContentsUri(Context context) {
        StringBuilder sb = new StringBuilder("000 context is null=");
        sb.append(context == null);
        LogMgr.log(6, sb.toString());
        if (!sIsInitializedUri.get()) {
            initializeUri(context);
            LogMgr.log(8, "Called initialize URI.");
        }
        LogMgr.log(6, "999 CONTENT_URI=" + NotifyClientLogContents.CONTENT_URI);
        return NotifyClientLogContents.CONTENT_URI;
    }

    public static Uri getSemClientUsageStatusContentsUri(Context context) {
        StringBuilder sb = new StringBuilder("000 context is null=");
        sb.append(context == null);
        LogMgr.log(6, sb.toString());
        if (!sIsInitializedUri.get()) {
            initializeUri(context);
            LogMgr.log(8, "Called initialize URI.");
        }
        LogMgr.log(6, "999 CONTENT_URI=" + SemClientUsageStatusContents.CONTENT_URI);
        return SemClientUsageStatusContents.CONTENT_URI;
    }

    public static Uri getRoutineWorkerStatusContentsUri(Context context) {
        StringBuilder sb = new StringBuilder("000 context is null=");
        sb.append(context == null);
        LogMgr.log(6, sb.toString());
        if (!sIsInitializedUri.get()) {
            initializeUri(context);
            LogMgr.log(8, "Called initialize URI.");
        }
        LogMgr.log(6, "999 CONTENT_URI=" + RoutineWorkerStatusContents.CONTENT_URI);
        return RoutineWorkerStatusContents.CONTENT_URI;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:6|7|(3:(1:(1:32)(6:27|50|28|29|48|34))(6:19|52|20|21|48|34)|46|47)(4:11|54|12|13)|33|48|34|46|47) */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x014f, code lost:
    
        r7 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0150, code lost:
    
        r13 = r5;
        r5 = r14;
        r14 = r7;
        r7 = r6;
        r6 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x01a2, code lost:
    
        r3 = false;
     */
    /* JADX WARN: Removed duplicated region for block: B:44:0x01a2  */
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
        boolean z7 = true;
        try {
        } catch (Exception e) {
            e = e;
            z = false;
            z2 = false;
        }
        if (context == null) {
            LogMgr.log(2, "700 Context is null. ContentProvider's AUTHORITY is set with [com.felicanetworks.mfm.main.util.SharedPrefsProvider]");
            return;
        }
        String packageName = context.getPackageName();
        if (packageName != null && packageName.equals(MENU_APP_PACKAGE_NAME_DEV2)) {
            LogMgr.log(9, "001");
            try {
                NotifyClientEventContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider/NotifyClientEvent");
                NotifyClientLogContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider/NotifyClientLog");
                SemClientUsageStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider/SemClientUsageStatus");
                RoutineWorkerStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider/RoutineWorkerStatus");
                LogMgr.log(9, "002 set CONTENT_URI for dev2.");
                sUriMatcher = new UriMatcher(-1);
                sUriMatcher.addURI("com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider", NotifyClientEventContents.PATH, 1);
                sUriMatcher.addURI("com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider", NotifyClientLogContents.PATH, 2);
                sUriMatcher.addURI("com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider", SemClientUsageStatusContents.PATH, 3);
                sUriMatcher.addURI("com.felicanetworks.mfmsemcdev.util.SharedPrefsProvider", RoutineWorkerStatusContents.PATH, 4);
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
                if (sUriMatcher.match(RoutineWorkerStatusContents.CONTENT_URI) != 4) {
                }
                sb.append(z7);
                sb.append("]");
                LogMgr.log(2, sb.toString());
                LogMgr.log(6, "999");
            }
        } else {
            if (packageName != null && packageName.equals("com.felicanetworks.semcapp")) {
                LogMgr.log(9, "004");
                try {
                    NotifyClientEventContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcapp.util.SharedPrefsProvider/NotifyClientEvent");
                    NotifyClientLogContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcapp.util.SharedPrefsProvider/NotifyClientLog");
                    SemClientUsageStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcapp.util.SharedPrefsProvider/SemClientUsageStatus");
                    RoutineWorkerStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcapp.util.SharedPrefsProvider/RoutineWorkerStatus");
                    LogMgr.log(9, "005 set CONTENT_URI for semc.");
                    sUriMatcher = new UriMatcher(-1);
                    sUriMatcher.addURI("com.felicanetworks.semcapp.util.SharedPrefsProvider", NotifyClientEventContents.PATH, 1);
                    sUriMatcher.addURI("com.felicanetworks.semcapp.util.SharedPrefsProvider", NotifyClientLogContents.PATH, 2);
                    sUriMatcher.addURI("com.felicanetworks.semcapp.util.SharedPrefsProvider", SemClientUsageStatusContents.PATH, 3);
                    sUriMatcher.addURI("com.felicanetworks.semcapp.util.SharedPrefsProvider", RoutineWorkerStatusContents.PATH, 4);
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
                    if (sUriMatcher.match(RoutineWorkerStatusContents.CONTENT_URI) != 4) {
                    }
                    sb2.append(z7);
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
                    NotifyClientEventContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcappdev.util.SharedPrefsProvider/NotifyClientEvent");
                    NotifyClientLogContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcappdev.util.SharedPrefsProvider/NotifyClientLog");
                    SemClientUsageStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcappdev.util.SharedPrefsProvider/SemClientUsageStatus");
                    RoutineWorkerStatusContents.CONTENT_URI = Uri.parse("content://com.felicanetworks.semcappdev.util.SharedPrefsProvider/RoutineWorkerStatus");
                    LogMgr.log(9, "008 set CONTENT_URI for semc verification.");
                    sUriMatcher = new UriMatcher(-1);
                    sUriMatcher.addURI("com.felicanetworks.semcappdev.util.SharedPrefsProvider", NotifyClientEventContents.PATH, 1);
                    sUriMatcher.addURI("com.felicanetworks.semcappdev.util.SharedPrefsProvider", NotifyClientLogContents.PATH, 2);
                    sUriMatcher.addURI("com.felicanetworks.semcappdev.util.SharedPrefsProvider", SemClientUsageStatusContents.PATH, 3);
                    sUriMatcher.addURI("com.felicanetworks.semcappdev.util.SharedPrefsProvider", RoutineWorkerStatusContents.PATH, 4);
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
                    if (sUriMatcher.match(RoutineWorkerStatusContents.CONTENT_URI) != 4) {
                    }
                    sb22.append(z7);
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
        int iMatch = sUriMatcher.match(uri);
        if (iMatch == 1) {
            LogMgr.log(6, "001 uri is NotifyClientEventContents");
            LogMgr.log(6, "995");
            return getNotifyClientEventData();
        }
        if (iMatch == 2) {
            LogMgr.log(6, "002 uri is NotifyClientLogContents");
            LogMgr.log(6, "996");
            return getNotifyClientLogData();
        }
        if (iMatch == 3) {
            LogMgr.log(6, "003 uri is SemClientUsageStatusContents");
            LogMgr.log(6, "997");
            return getSemClientUsageStatusData();
        }
        if (iMatch == 4) {
            LogMgr.log(6, "004 uri is RoutineWorkerContents");
            LogMgr.log(6, "998");
            return getRoutineWorkerData();
        }
        LogMgr.log(6, "999");
        throw new IllegalArgumentException("Invalid URI：" + uri);
    }

    private Cursor getNotifyClientEventData() {
        String spAppInfoList;
        long saveDate;
        LogMgr.log(9, "000");
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getContext());
        boolean zHasSpAppInfoListData = sharedPrefsUtil.hasSpAppInfoListData();
        if (zHasSpAppInfoListData) {
            LogMgr.log(9, "001 exists SpAppInfo in SharedPreference.");
            spAppInfoList = sharedPrefsUtil.readSpAppInfoList();
            saveDate = sharedPrefsUtil.readSaveDate();
        } else {
            spAppInfoList = null;
            saveDate = 0;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{NotifyClientEventContents.Setting.IS_EXISTS, NotifyClientEventContents.Setting.SP_APP_INFO_LIST_STR, NotifyClientEventContents.Setting.SAVE_DATE});
        matrixCursor.addRow(new Object[]{String.valueOf(zHasSpAppInfoListData), spAppInfoList, Long.valueOf(saveDate)});
        LogMgr.log(9, "999");
        return matrixCursor;
    }

    private Cursor getNotifyClientLogData() {
        String str;
        String strCheckAndGetProfileId;
        ProfileDataJson profileDataJson;
        LogMgr.log(9, "000");
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getContext());
        String controlInfo = sharedPrefsUtil.readControlInfo();
        String clientId = sharedPrefsUtil.readClientId();
        String strCheckAndGetServerConnectionUrl = null;
        if (sharedPrefsUtil.hasExistence() && sharedPrefsUtil.hasProfileInfo()) {
            LogMgr.log(9, "001 profile data exists in SharedPreference.");
            try {
                profileDataJson = new ProfileDataJson(sharedPrefsUtil.readProfileInfo());
                strCheckAndGetProfileId = profileDataJson.checkAndGetProfileId();
            } catch (JSONException unused) {
                strCheckAndGetProfileId = null;
            }
            try {
                strCheckAndGetServerConnectionUrl = profileDataJson.checkAndGetServerConnectionUrl();
            } catch (JSONException unused2) {
                LogMgr.log(2, "700 JSONException occurred.");
            }
            String str2 = strCheckAndGetServerConnectionUrl;
            strCheckAndGetServerConnectionUrl = strCheckAndGetProfileId;
            str = str2;
        } else {
            str = null;
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{NotifyClientLogContents.Setting.CONTROL_INFO, NotifyClientLogContents.Setting.CLIENT_ID, NotifyClientLogContents.Setting.PROFILE_ID, NotifyClientLogContents.Setting.URL_IN_PROFILE});
        matrixCursor.addRow(new Object[]{controlInfo, clientId, strCheckAndGetServerConnectionUrl, str});
        LogMgr.log(9, "999");
        return matrixCursor;
    }

    private Cursor getSemClientUsageStatusData() {
        LogMgr.log(9, "000");
        boolean zHasAndroidIdData = new SharedPrefsUtil(getContext()).hasAndroidIdData();
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{SemClientUsageStatusContents.Setting.HAS_ANDROID_ID_DATA});
        matrixCursor.addRow(new Object[]{String.valueOf(zHasAndroidIdData)});
        LogMgr.log(9, "999");
        return matrixCursor;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Cursor getRoutineWorkerData() {
        boolean z;
        LogMgr.log(9, "000");
        SharedPrefsUtil sharedPrefsUtil = new SharedPrefsUtil(getContext());
        try {
        } catch (Exception unused) {
            LogMgr.log(1, "800 RoutineWorkerExecutionDateCache was corrupted.");
        }
        if (sharedPrefsUtil.hasRoutineWorkerExecutionDate()) {
            LogMgr.log(9, "001 RoutineWorkerExecutionDate exists in sharedPrefs");
            z = !DateUtil.isTodayInJapan(sharedPrefsUtil.readRoutineWorkerExecutionDate());
            if (z) {
                LogMgr.log(9, "003 update RoutineWorkerExecutionDate");
                sharedPrefsUtil.writeRoutineWorkerExecutionDate(System.currentTimeMillis() / 1000);
            }
            MatrixCursor matrixCursor = new MatrixCursor(new String[]{RoutineWorkerStatusContents.Setting.NEEDS_EXECUTE_ROUTINE_WORK});
            matrixCursor.addRow(new Object[]{String.valueOf(z)});
            LogMgr.log(9, "999");
            return matrixCursor;
        }
        LogMgr.log(9, "002 RoutineWorkerExecutionDate dose not exist in sharedPrefs");
        z = true;
        if (z) {
        }
        MatrixCursor matrixCursor2 = new MatrixCursor(new String[]{RoutineWorkerStatusContents.Setting.NEEDS_EXECUTE_ROUTINE_WORK});
        matrixCursor2.addRow(new Object[]{String.valueOf(z)});
        LogMgr.log(9, "999");
        return matrixCursor2;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        LogMgr.log(6, "000");
        if (sUriMatcher.match(uri) == 2) {
            LogMgr.log(6, "001 uri is NotifyClientLogContents");
            LogMgr.log(6, "998");
            setNotifyClientLogData(contentValues);
            return uri;
        }
        LogMgr.log(6, "999");
        throw new IllegalArgumentException("Invalid URI：" + uri);
    }

    private void setNotifyClientLogData(ContentValues contentValues) {
        LogMgr.log(9, "000");
        if (contentValues == null) {
            LogMgr.log(9, "001 ContentValues were null, so will not set Data");
            LogMgr.log(9, "998");
            return;
        }
        String strValueOf = String.valueOf(contentValues.get(NotifyClientLogContents.Setting.CLIENT_ID));
        LogMgr.log(9, "002 clientId=" + strValueOf);
        new SharedPrefsUtil(getContext()).writeClientId(strValueOf);
        LogMgr.log(9, "999");
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
