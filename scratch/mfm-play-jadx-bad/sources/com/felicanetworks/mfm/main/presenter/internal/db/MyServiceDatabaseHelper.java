package com.felicanetworks.mfm.main.presenter.internal.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.felicanetworks.mfm.main.presenter.PresenterData;
import com.felicanetworks.mfm.main.presenter.internal.db.table.GroupSortOrderTable;
import com.felicanetworks.mfm.main.presenter.internal.db.table.HiddenUnregisteredServiceTable;
import com.felicanetworks.mfm.main.presenter.internal.db.table.IdServiceTypeTable;

/* JADX INFO: loaded from: classes3.dex */
class MyServiceDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mfm_myservice.db";
    private static final int DB_VERSION = 1;

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    MyServiceDatabaseHelper() {
        super(PresenterData.getInstance().getContext(), DB_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    void deleteDatabase() {
        try {
            PresenterData.getInstance().getContext().deleteDatabase(DB_NAME);
        } catch (Exception unused) {
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(GroupSortOrderTable.Statement.CREATE);
        db.execSQL(HiddenUnregisteredServiceTable.Statement.CREATE);
        db.execSQL(IdServiceTypeTable.Statement.CREATE);
    }
}
