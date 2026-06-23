package com.felicanetworks.common.cmnctrl.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteException;
import android.provider.BaseColumns;
import com.felicanetworks.common.cmnctrl.data.ErrorLogData;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;
import com.felicanetworks.common.cmnlib.log.LogMgr;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseAccess implements FunctionCodeInterface {
    private static final String[] COLUMNS_ERRORLOG = {"Date", "LogMessage", "Idm"};
    private static final String DATABASE_NAME = "log.db";
    private static final String INSERT_ERRORLOG_TABLE = "INSERT INTO ErrorLog (Date, LogMessage, Idm) VALUES (?, ?, ?)";
    private static final String ORDER_BY = "Date ASC";
    private static final String TABLE_ERRORLOG = "ErrorLog";
    private AppContext context;
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 1;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 5;
    }

    private static final class ErrorLogColumns implements BaseColumns {
        private static final String DATE = "Date";
        private static final String IDM = "Idm";
        private static final String LOGMSG = "LogMessage";

        private ErrorLogColumns() {
        }
    }

    public DatabaseAccess(AppContext appContext) throws DatabaseAccessException {
        this.db = null;
        this.context = appContext;
        try {
            DatabaseHelper databaseHelper = DatabaseHelper.getInstance(appContext);
            this.dbHelper = databaseHelper;
            this.db = databaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            DatabaseAccessException databaseAccessException = new DatabaseAccessException(e, 2);
            appContext.logMgr.out(LogMgr.CatExp.ERR, this, databaseAccessException);
            throw databaseAccessException;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:45:0x00be A[Catch: all -> 0x00c2, TRY_ENTER, TryCatch #0 {all -> 0x00c2, blocks: (B:13:0x0046, B:22:0x0089, B:24:0x008e, B:45:0x00be, B:49:0x00c6, B:50:0x00c9, B:38:0x00b1, B:40:0x00b6), top: B:53:0x0002 }] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c6 A[Catch: all -> 0x00c2, TryCatch #0 {all -> 0x00c2, blocks: (B:13:0x0046, B:22:0x0089, B:24:0x008e, B:45:0x00be, B:49:0x00c6, B:50:0x00c9, B:38:0x00b1, B:40:0x00b6), top: B:53:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void addErrorLog(java.lang.String r13) {
        /*
            Method dump skipped, instruction units count: 204
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.common.cmnctrl.database.DatabaseAccess.addErrorLog(java.lang.String):void");
    }

    public List<ErrorLogData> getErrorLogData() {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                if (this.db == null) {
                    DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this.context);
                    this.dbHelper = databaseHelper;
                    this.db = databaseHelper.getWritableDatabase();
                }
                cursorQuery = this.db.query(TABLE_ERRORLOG, COLUMNS_ERRORLOG, null, null, null, null, ORDER_BY, this.context.sgMgr.getErrorLogLimit());
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(new ErrorLogData(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2)));
                    cursorQuery.moveToNext();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (Exception e) {
                this.context.logMgr.out(LogMgr.CatExp.WAR, this, e);
                if (e instanceof SQLiteDatabaseCorruptException) {
                    deleteLogDb();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
    }

    public void cleanErrorInfo() {
        try {
            this.db.delete(TABLE_ERRORLOG, null, null);
        } catch (Exception e) {
            this.context.logMgr.out(LogMgr.CatExp.WAR, this, e);
            if (e instanceof SQLiteDatabaseCorruptException) {
                deleteLogDb();
            }
        }
    }

    public void deleteLogDb() {
        try {
            this.db.close();
        } catch (Exception e) {
            this.context.logMgr.out(LogMgr.CatExp.WAR, this, e);
        }
        this.context.androidContext.deleteDatabase(DATABASE_NAME);
        this.db = null;
    }

    public static void clearInstance() {
        DatabaseHelper.clearInstance();
    }
}
