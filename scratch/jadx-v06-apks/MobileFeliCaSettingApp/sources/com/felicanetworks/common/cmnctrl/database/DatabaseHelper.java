package com.felicanetworks.common.cmnctrl.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.felicanetworks.common.cmnlib.AppContext;
import com.felicanetworks.common.cmnlib.FunctionCodeInterface;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseHelper extends SQLiteOpenHelper implements FunctionCodeInterface {
    private static final String CREATE_ERRORLOG_TABLE = "CREATE TABLE ErrorLog(Date TEXT NOT NULL, LogMessage TEXT NOT NULL, Idm TEXT NOT NULL)";
    private static final String DATABASE_NAME = "log.db";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper dbHelper;

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getClassCode() {
        return 2;
    }

    @Override // com.felicanetworks.common.cmnlib.FunctionCodeInterface
    public int getFunctionCode() {
        return 5;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public static synchronized DatabaseHelper getInstance(AppContext appContext) {
        if (dbHelper == null) {
            DatabaseHelper databaseHelper = new DatabaseHelper(appContext);
            dbHelper = databaseHelper;
            databaseHelper.getWritableDatabase().close();
        }
        return dbHelper;
    }

    private DatabaseHelper(AppContext appContext) {
        super(appContext.androidContext, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public static void clearInstance() {
        DatabaseHelper databaseHelper = dbHelper;
        if (databaseHelper != null) {
            try {
                databaseHelper.close();
            } catch (Exception unused) {
            }
            dbHelper = null;
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_ERRORLOG_TABLE);
    }
}
