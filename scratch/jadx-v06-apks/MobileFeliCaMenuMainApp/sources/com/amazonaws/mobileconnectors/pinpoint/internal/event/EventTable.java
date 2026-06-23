package com.amazonaws.mobileconnectors.pinpoint.internal.event;

import android.database.sqlite.SQLiteDatabase;

/* JADX INFO: loaded from: classes.dex */
public class EventTable {
    public static final String COLUMN_ID = "event_id";
    public static final String COLUMN_JSON = "event_json";
    public static final String COLUMN_SIZE = "event_size";
    private static final String DATABASE_CREATE = "create table if not exists pinpointevent(event_id integer primary key autoincrement, event_size INTEGER NOT NULL,event_json TEXT NOT NULL);";
    public static final String TABLE_EVENT = "pinpointevent";

    public static void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    public static void onCreate(SQLiteDatabase sQLiteDatabase, int i) {
        sQLiteDatabase.execSQL(DATABASE_CREATE);
        onUpgrade(sQLiteDatabase, 1, i);
    }

    public enum COLUMN_INDEX {
        ID(0),
        SIZE(1),
        JSON(2);

        private final int value;

        COLUMN_INDEX(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }
}
