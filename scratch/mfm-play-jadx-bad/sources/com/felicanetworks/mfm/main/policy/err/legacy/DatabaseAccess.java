package com.felicanetworks.mfm.main.policy.err.legacy;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class DatabaseAccess {
    private static final String[] COLUMNS_ERRORLOG = {"Date", "LogMessage", "Idm"};
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

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [137=5] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00b4 A[Catch: all -> 0x00af, PHI: r1 r2
  0x00b4: PHI (r1v5 android.database.Cursor) = (r1v3 android.database.Cursor), (r1v6 android.database.Cursor) binds: [B:41:0x00b2, B:49:0x00c6] A[DONT_GENERATE, DONT_INLINE]
  0x00b4: PHI (r2v7 android.database.sqlite.SQLiteStatement) = (r2v5 android.database.sqlite.SQLiteStatement), (r2v8 android.database.sqlite.SQLiteStatement) binds: [B:41:0x00b2, B:49:0x00c6] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #6 {all -> 0x00af, blocks: (B:13:0x0046, B:22:0x0084, B:24:0x0089, B:56:0x00cf, B:58:0x00d4, B:59:0x00d7, B:38:0x00ab, B:42:0x00b4, B:48:0x00c3), top: B:65:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void addErrorLog(String str) {
        Throwable th;
        SQLiteStatement sQLiteStatement;
        Exception exc;
        SQLiteDatabaseCorruptException sQLiteDatabaseCorruptException;
        int iIntValue;
        Cursor cursorQuery;
        Cursor cursor = null;
        sQLiteStatementCompileStatement = null;
        sQLiteStatementCompileStatement = null;
        SQLiteStatement sQLiteStatementCompileStatement = null;
        cursor = null;
        cursor = null;
        cursor = null;
        try {
            try {
                try {
                    if (this.db == null) {
                        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.context);
                        this.dbHelper = databaseHelper;
                        this.db = databaseHelper.getWritableDatabase();
                    }
                    iIntValue = ((Integer) Sg.getValue(Sg.Key.SETTING_MAX_STOCK_COUNT_TROUBLE_LOG)).intValue();
                    cursorQuery = this.db.query(TABLE_ERRORLOG, new String[]{"COUNT(Date)"}, null, null, null, null, null, null);
                } catch (Throwable th2) {
                    th = th2;
                }
                try {
                    cursorQuery.moveToFirst();
                    if (iIntValue <= (cursorQuery.getCount() > 0 ? cursorQuery.getInt(0) : 0)) {
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return;
                    }
                    sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_ERRORLOG_TABLE);
                    DateFormatter dateFormatter = new DateFormatter(DateFormatter.DATE_TIME_MSEC, (String) Sg.getValue(Sg.Key.SETTING_TIMEZONE));
                    String strIdm = Settings.idm() != null ? Settings.idm() : "";
                    sQLiteStatementCompileStatement.bindString(1, dateFormatter.getSystemTime());
                    sQLiteStatementCompileStatement.bindString(2, str);
                    sQLiteStatementCompileStatement.bindString(3, strIdm);
                    sQLiteStatementCompileStatement.executeInsert();
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    if (sQLiteStatementCompileStatement != null) {
                        sQLiteStatementCompileStatement.close();
                    }
                } catch (SQLiteDatabaseCorruptException e) {
                    sQLiteDatabaseCorruptException = e;
                    sQLiteStatement = sQLiteStatementCompileStatement;
                    cursor = cursorQuery;
                    LogUtil.warning(sQLiteDatabaseCorruptException);
                    deleteLogDb();
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteStatement != null) {
                        sQLiteStatement.close();
                    }
                } catch (Exception e2) {
                    exc = e2;
                    sQLiteStatement = sQLiteStatementCompileStatement;
                    cursor = cursorQuery;
                    LogUtil.warning(exc);
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteStatement != null) {
                    }
                } catch (Throwable th3) {
                    th = th3;
                    sQLiteStatement = sQLiteStatementCompileStatement;
                    cursor = cursorQuery;
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteStatement == null) {
                        throw th;
                    }
                    sQLiteStatement.close();
                    throw th;
                }
            } catch (SQLiteDatabaseCorruptException e3) {
                sQLiteDatabaseCorruptException = e3;
                sQLiteStatement = null;
            } catch (Exception e4) {
                exc = e4;
                sQLiteStatement = null;
            } catch (Throwable th4) {
                th = th4;
                sQLiteStatement = null;
            }
        } finally {
        }
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
