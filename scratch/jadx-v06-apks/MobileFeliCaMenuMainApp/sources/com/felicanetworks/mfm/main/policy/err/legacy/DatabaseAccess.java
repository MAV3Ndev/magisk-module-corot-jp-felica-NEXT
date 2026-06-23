package com.felicanetworks.mfm.main.policy.err.legacy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import com.amazonaws.http.HttpHeader;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseAccess {
    private static final String[] COLUMNS_ERRORLOG = {HttpHeader.DATE, "LogMessage", "Idm"};
    private static final String DATABASE_NAME = "log.db";
    private static final String INSERT_ERRORLOG_TABLE = "INSERT INTO ErrorLog (Date, LogMessage, Idm) VALUES (?, ?, ?)";
    private static final String ORDER_BY = "Date ASC";
    private static final String TABLE_ERRORLOG = "ErrorLog";
    private Context context;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    private static final class ErrorLogColumns implements BaseColumns {
        private static final String DATE = "Date";
        private static final String IDM = "Idm";
        private static final String LOGMSG = "LogMessage";

        private ErrorLogColumns() {
        }
    }

    public DatabaseAccess(Context context) {
        this.db = null;
        this.context = context;
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
            this.dbHelper = databaseHelper;
            this.db = databaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            LogUtil.warning(e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x00ac A[Catch: all -> 0x00a8, PHI: r0 r1
  0x00ac: PHI (r0v5 android.database.Cursor) = (r0v3 android.database.Cursor), (r0v6 android.database.Cursor) binds: [B:41:0x00aa, B:49:0x00bd] A[DONT_GENERATE, DONT_INLINE]
  0x00ac: PHI (r1v7 android.database.sqlite.SQLiteStatement) = (r1v5 android.database.sqlite.SQLiteStatement), (r1v8 android.database.sqlite.SQLiteStatement) binds: [B:41:0x00aa, B:49:0x00bd] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #3 {all -> 0x00a8, blocks: (B:13:0x0046, B:22:0x0085, B:24:0x008a, B:55:0x00c5, B:57:0x00ca, B:58:0x00cd, B:38:0x00a4, B:42:0x00ac, B:48:0x00ba), top: B:64:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void addErrorLog(java.lang.String r13) {
        /*
            Method dump skipped, instruction units count: 208
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.policy.err.legacy.DatabaseAccess.addErrorLog(java.lang.String):void");
    }

    public synchronized List<ErrorLogData> getErrorLogData() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                try {
                    if (this.db == null) {
                        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.context);
                        this.dbHelper = databaseHelper;
                        this.db = databaseHelper.getWritableDatabase();
                    }
                    cursorQuery = this.db.query(TABLE_ERRORLOG, COLUMNS_ERRORLOG, null, null, null, null, ORDER_BY, String.valueOf(((Integer) Sg.getValue(Sg.Key.SETTING_MAX_STOCK_COUNT_TROUBLE_LOG)).intValue()));
                    cursorQuery.moveToFirst();
                    int count = cursorQuery.getCount();
                    for (int i = 0; i < count; i++) {
                        arrayList.add(new ErrorLogData(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2)));
                        cursorQuery.moveToNext();
                    }
                } catch (Exception e) {
                    LogUtil.warning(e);
                    if (cursorQuery != null) {
                    }
                }
            } catch (SQLiteDatabaseCorruptException e2) {
                LogUtil.warning(e2);
                deleteLogDb();
                if (cursorQuery != null) {
                }
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
        return arrayList;
    }

    public synchronized void cleanErrorInfo() {
        try {
            this.db.delete(TABLE_ERRORLOG, null, null);
        } catch (SQLiteDatabaseCorruptException e) {
            LogUtil.warning(e);
            deleteLogDb();
        } catch (Exception e2) {
            LogUtil.warning(e2);
        }
    }

    private synchronized void deleteLogDb() {
        try {
            this.db.close();
        } catch (Exception e) {
            LogUtil.warning(e);
        }
        this.context.deleteDatabase(DATABASE_NAME);
        this.db = null;
    }
}
