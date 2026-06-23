package com.amazonaws.mobileconnectors.pinpoint.internal.event;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* JADX INFO: loaded from: classes.dex */
public class PinpointDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "awspinpoint.db";
    private static final int DATABASE_VERSION = 1;
    private int version;

    public PinpointDatabaseHelper(Context context) {
        this(context, 1);
    }

    public PinpointDatabaseHelper(Context context, int i) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, i);
        this.version = i;
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onConfigure(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("PRAGMA auto_vacuum = FULL");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        EventTable.onCreate(sQLiteDatabase, this.version);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        EventTable.onUpgrade(sQLiteDatabase, i, i2);
    }
}
