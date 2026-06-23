package com.amazonaws.mobileconnectors.pinpoint.internal.event;

import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
public class PinpointDBBase {
    private static final String BASE_PATH = "events";
    private static final int EVENTS = 10;
    private static final int EVENT_ID = 20;
    private final Uri contentUri;
    private final Context context;
    private final PinpointDatabaseHelper databaseHelper;
    private long totalSize = -1;
    private final UriMatcher uriMatcher;

    public PinpointDBBase(Context context) {
        this.context = context;
        String packageName = context.getApplicationContext().getPackageName();
        this.databaseHelper = new PinpointDatabaseHelper(context);
        this.contentUri = Uri.parse("content://" + packageName + "/events");
        UriMatcher uriMatcher = new UriMatcher(-1);
        this.uriMatcher = uriMatcher;
        uriMatcher.addURI(packageName, BASE_PATH, 10);
        uriMatcher.addURI(packageName, "events/#", 20);
    }

    public void closeDBHelper() {
        this.databaseHelper.close();
    }

    public Uri getContentUri() {
        return this.contentUri;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        int iMatch = this.uriMatcher.match(uri);
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        if (iMatch == 10) {
            long totalSize = getTotalSize();
            long jInsertOrThrow = writableDatabase.insertOrThrow(EventTable.TABLE_EVENT, null, contentValues);
            this.totalSize = totalSize + contentValues.getAsLong(EventTable.COLUMN_SIZE).longValue();
            return Uri.parse("events/" + jInsertOrThrow);
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }

    public long getTotalSize() {
        Cursor cursorRawQuery = null;
        try {
            if (this.totalSize < 0) {
                cursorRawQuery = this.databaseHelper.getReadableDatabase().rawQuery("SELECT SUM(event_size) FROM pinpointevent", null);
                if (!cursorRawQuery.moveToNext() || cursorRawQuery.isNull(0)) {
                    this.totalSize = 0L;
                } else {
                    this.totalSize = cursorRawQuery.getLong(0);
                }
            }
            return this.totalSize;
        } finally {
            if (0 != 0) {
                cursorRawQuery.close();
            }
        }
    }

    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2, String str3) {
        SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
        sQLiteQueryBuilder.setTables(EventTable.TABLE_EVENT);
        int iMatch = this.uriMatcher.match(uri);
        if (iMatch != 10) {
            if (iMatch == 20) {
                sQLiteQueryBuilder.appendWhere("event_id=" + uri.getLastPathSegment());
            } else {
                throw new IllegalArgumentException("Unknown URI: " + uri);
            }
        }
        return sQLiteQueryBuilder.query(this.databaseHelper.getWritableDatabase(), strArr, str, strArr2, null, null, str2, str3);
    }

    public synchronized int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        int iUpdate;
        int iMatch = this.uriMatcher.match(uri);
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        if (iMatch == 10) {
            iUpdate = writableDatabase.update(EventTable.TABLE_EVENT, contentValues, str, strArr);
        } else if (iMatch == 20) {
            String lastPathSegment = uri.getLastPathSegment();
            if (TextUtils.isEmpty(str)) {
                iUpdate = writableDatabase.update(EventTable.TABLE_EVENT, contentValues, "event_id=" + lastPathSegment, null);
            } else {
                iUpdate = writableDatabase.update(EventTable.TABLE_EVENT, contentValues, "event_id=" + lastPathSegment + " and " + str, strArr);
            }
        } else {
            throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        return iUpdate;
    }

    public int delete(Uri uri, String str, String[] strArr, Integer num) {
        int iDelete;
        int iMatch = this.uriMatcher.match(uri);
        SQLiteDatabase writableDatabase = this.databaseHelper.getWritableDatabase();
        if (iMatch == 10) {
            int iDelete2 = writableDatabase.delete(EventTable.TABLE_EVENT, str, strArr);
            this.totalSize = -1L;
            return iDelete2;
        }
        if (iMatch == 20) {
            String lastPathSegment = uri.getLastPathSegment();
            long totalSize = getTotalSize();
            if (TextUtils.isEmpty(str)) {
                iDelete = writableDatabase.delete(EventTable.TABLE_EVENT, "event_id=" + lastPathSegment, null);
            } else {
                iDelete = writableDatabase.delete(EventTable.TABLE_EVENT, "event_id=" + lastPathSegment + " and " + str, strArr);
            }
            if (iDelete != 1) {
                this.totalSize = -1L;
                return iDelete;
            }
            if (num != null) {
                this.totalSize = totalSize - ((long) num.intValue());
                return iDelete;
            }
            this.totalSize = -1L;
            return iDelete;
        }
        throw new IllegalArgumentException("Unknown URI: " + uri);
    }
}
