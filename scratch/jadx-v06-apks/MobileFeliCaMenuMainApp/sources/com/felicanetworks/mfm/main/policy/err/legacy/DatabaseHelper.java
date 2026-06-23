package com.felicanetworks.mfm.main.policy.err.legacy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: classes.dex */
public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String CREATE_ERRORLOG_TABLE = "CREATE TABLE ErrorLog(Date TEXT NOT NULL, LogMessage TEXT NOT NULL, Idm TEXT NOT NULL)";
    private static final String DATABASE_NAME = "log.db";
    private static final int DATABASE_VERSION = 1;
    private static volatile DatabaseHelper dbHelper;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public static synchronized DatabaseHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DatabaseHelper(context);
            dbHelper.getWritableDatabase().close();
        }
        return dbHelper;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    public static void clearInstance() {
        if (dbHelper != null) {
            try {
                dbHelper.close();
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
