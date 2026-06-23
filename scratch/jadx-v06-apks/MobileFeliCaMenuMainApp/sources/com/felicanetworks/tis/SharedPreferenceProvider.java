package com.felicanetworks.tis;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.felicanetworks.tis.util.AccessConfig;

/* JADX INFO: loaded from: classes.dex */
public class SharedPreferenceProvider extends ContentProvider {
    private static final String AUTHORITY = "com.felicanetworks.tis.SharedPreferenceProvider";
    private static final int NOTIFICATION_DISPLAY_CODE = 1;
    private static UriMatcher sUriMatcher;

    public interface Contents {
        public static final Uri CONTENT_URI = Uri.parse("content://com.felicanetworks.tis.SharedPreferenceProvider/NotificationDisplay");
        public static final String PATH = "NotificationDisplay";

        public interface Setting {
            public static final String NOTIFICATION_DISPLAY = "notification_display";
            public static final String NOTIFICATION_DISPLAY_MODE = "notification_display_mode";
        }
    }

    @Override // android.content.ContentProvider
    public boolean onCreate() {
        return true;
    }

    static {
        UriMatcher uriMatcher = new UriMatcher(-1);
        sUriMatcher = uriMatcher;
        uriMatcher.addURI(AUTHORITY, Contents.PATH, 1);
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("");
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        if (sUriMatcher.match(uri) == 1) {
            return getNotificationDisplay();
        }
        throw new IllegalArgumentException("Invalid URI：" + uri);
    }

    private Cursor getNotificationDisplay() {
        boolean z;
        SharedPreferences sharedPreferences;
        boolean zIsGPDevice = AccessConfig.isGPDevice();
        Context context = getContext();
        String string = "notice";
        if (context == null || (sharedPreferences = context.getSharedPreferences("mfi_ti_setting", 0)) == null) {
            z = false;
        } else if (zIsGPDevice) {
            z = sharedPreferences.getBoolean("ti_notification_display", true);
            string = sharedPreferences.getString("ti_notification_display_mode", "notice");
        } else {
            z = sharedPreferences.getBoolean("ti_notification_display", false);
            string = sharedPreferences.getString("ti_notification_display_mode", "notice");
        }
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{Contents.Setting.NOTIFICATION_DISPLAY, Contents.Setting.NOTIFICATION_DISPLAY_MODE});
        matrixCursor.addRow(new Object[]{String.valueOf(z), string});
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
