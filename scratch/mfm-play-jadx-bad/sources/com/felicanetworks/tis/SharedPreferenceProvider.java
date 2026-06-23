package com.felicanetworks.tis;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

/* JADX INFO: loaded from: classes3.dex */
public class SharedPreferenceProvider extends ContentProvider {
    private static final String AUTHORITY = "com.felicanetworks.tis.SharedPreferenceProvider";
    private static final int NOTIFICATION_DISPLAY_CODE = 1;
    private static final int STOP_CHIP_ACCESS_CODE = 2;
    private static UriMatcher sUriMatcher;

    public interface Contents {
        public static final String PATH = "NotificationDisplay";
        public static final String SETTING_PATH = "TapInteractionSetting";
        public static final Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.tis.SharedPreferenceProvider/NotificationDisplay");
        public static final Uri SETTING_CONTENT_URI = Uri.parse("content://com.felicanetworks.tis.SharedPreferenceProvider/TapInteractionSetting");

        public interface Setting {
            public static final String NOTIFICATION_DISPLAY = "notification_display";
            public static final String NOTIFICATION_DISPLAY_MODE = "notification_display_mode";
            public static final String STOP_CHIP_ACCESS = "stop_chip_access";
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI("com.felicanetworks.tis.SharedPreferenceProvider", Contents.PATH, 1);
        sUriMatcher.addURI("com.felicanetworks.tis.SharedPreferenceProvider", Contents.SETTING_PATH, 2);
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("");
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        int iMatch = sUriMatcher.match(uri);
        if (iMatch == 1) {
            return getNotificationDisplay();
        }
        if (iMatch == 2) {
            return getStopChipAccessSetting();
        }
        throw new IllegalArgumentException("Invalid URI：" + uri);
    }

    private Cursor getNotificationDisplay() {
        boolean z;
        SharedPreferences sharedPreferences;
        Context context = getContext();
        String string = TapInteractionConst.NOTIFICATION_DISPLAY_MODE_DEFAULT;
        if (context == null || (sharedPreferences = context.getSharedPreferences(TapInteractionConst.PREF_FILE_NAME, 0)) == null) {
            z = true;
        } else {
            z = sharedPreferences.getBoolean(TapInteractionConst.PREF_KEY_NOTIFICATION_DISPLAY, true);
            string = sharedPreferences.getString(TapInteractionConst.PREF_KEY_NOTIFICATION_DISPLAY_MODE, TapInteractionConst.NOTIFICATION_DISPLAY_MODE_DEFAULT);
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{Contents.Setting.NOTIFICATION_DISPLAY, Contents.Setting.NOTIFICATION_DISPLAY_MODE});
        matrixCursor.addRow(new Object[]{String.valueOf(z), string});
        return matrixCursor;
    }

    private Cursor getStopChipAccessSetting() {
        SharedPreferences sharedPreferences;
        Context context = getContext();
        boolean z = (context == null || (sharedPreferences = context.getSharedPreferences(TapInteractionConst.PREF_FILE_NAME, 0)) == null) ? false : sharedPreferences.getBoolean(TapInteractionConst.PREF_KEY_STOP_CHIP_ACCESS, false);
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{Contents.Setting.STOP_CHIP_ACCESS});
        matrixCursor.addRow(new Object[]{String.valueOf(z)});
        return matrixCursor;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("");
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        throw new UnsupportedOperationException("");
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        throw new UnsupportedOperationException("");
    }
}
