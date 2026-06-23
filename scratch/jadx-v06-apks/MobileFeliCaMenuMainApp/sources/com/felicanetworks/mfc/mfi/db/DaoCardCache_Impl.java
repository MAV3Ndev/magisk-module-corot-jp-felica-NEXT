package com.felicanetworks.mfc.mfi.db;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class DaoCardCache_Impl extends DaoCardCache {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<EntityCardCache> __insertionAdapterOfEntityCardCache;
    private final SharedSQLiteStatement __preparedStmtOfCutOffOverLimitRecords;
    private final SharedSQLiteStatement __preparedStmtOfDelete;

    public DaoCardCache_Impl(RoomDatabase roomDatabase) {
        this.__db = roomDatabase;
        this.__insertionAdapterOfEntityCardCache = new EntityInsertionAdapter<EntityCardCache>(roomDatabase) { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "INSERT OR REPLACE INTO `card_caches` (`id`,`version`,`cached_time`,`serialized_data`) VALUES (?,?,?,?)";
            }

            @Override // androidx.room.EntityInsertionAdapter
            public void bind(SupportSQLiteStatement supportSQLiteStatement, EntityCardCache entityCardCache) {
                if (entityCardCache.id == null) {
                    supportSQLiteStatement.bindNull(1);
                } else {
                    supportSQLiteStatement.bindString(1, entityCardCache.id);
                }
                if (entityCardCache.version == null) {
                    supportSQLiteStatement.bindNull(2);
                } else {
                    supportSQLiteStatement.bindString(2, entityCardCache.version);
                }
                supportSQLiteStatement.bindLong(3, entityCardCache.cached_time);
                if (entityCardCache.serialized_data == null) {
                    supportSQLiteStatement.bindNull(4);
                } else {
                    supportSQLiteStatement.bindBlob(4, entityCardCache.serialized_data);
                }
            }
        };
        this.__preparedStmtOfDelete = new SharedSQLiteStatement(roomDatabase) { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl.2
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM card_caches WHERE id = ?";
            }
        };
        this.__preparedStmtOfCutOffOverLimitRecords = new SharedSQLiteStatement(roomDatabase) { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl.3
            @Override // androidx.room.SharedSQLiteStatement
            public String createQuery() {
                return "DELETE FROM card_caches WHERE cached_time IN(SELECT cached_time FROM card_caches ORDER BY cached_time DESC LIMIT 3 OFFSET 30)";
            }
        };
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public void insert(EntityCardCache entityCardCache) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfEntityCardCache.insert(entityCardCache);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public void delete(String str) {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfDelete.acquire();
        if (str == null) {
            supportSQLiteStatementAcquire.bindNull(1);
        } else {
            supportSQLiteStatementAcquire.bindString(1, str);
        }
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfDelete.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public void cutOffOverLimitRecords() {
        this.__db.assertNotSuspendingTransaction();
        SupportSQLiteStatement supportSQLiteStatementAcquire = this.__preparedStmtOfCutOffOverLimitRecords.acquire();
        this.__db.beginTransaction();
        try {
            supportSQLiteStatementAcquire.executeUpdateDelete();
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
            this.__preparedStmtOfCutOffOverLimitRecords.release(supportSQLiteStatementAcquire);
        }
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public EntityCardCache select(String str) {
        RoomSQLiteQuery roomSQLiteQueryAcquire = RoomSQLiteQuery.acquire("SELECT * FROM card_caches WHERE id = ?", 1);
        if (str == null) {
            roomSQLiteQueryAcquire.bindNull(1);
        } else {
            roomSQLiteQueryAcquire.bindString(1, str);
        }
        this.__db.assertNotSuspendingTransaction();
        EntityCardCache entityCardCache = null;
        Cursor cursorQuery = DBUtil.query(this.__db, roomSQLiteQueryAcquire, false, null);
        try {
            int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(cursorQuery, "id");
            int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "version");
            int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "cached_time");
            int columnIndexOrThrow4 = CursorUtil.getColumnIndexOrThrow(cursorQuery, "serialized_data");
            if (cursorQuery.moveToFirst()) {
                EntityCardCache entityCardCache2 = new EntityCardCache();
                if (cursorQuery.isNull(columnIndexOrThrow)) {
                    entityCardCache2.id = null;
                } else {
                    entityCardCache2.id = cursorQuery.getString(columnIndexOrThrow);
                }
                if (cursorQuery.isNull(columnIndexOrThrow2)) {
                    entityCardCache2.version = null;
                } else {
                    entityCardCache2.version = cursorQuery.getString(columnIndexOrThrow2);
                }
                entityCardCache2.cached_time = cursorQuery.getLong(columnIndexOrThrow3);
                if (cursorQuery.isNull(columnIndexOrThrow4)) {
                    entityCardCache2.serialized_data = null;
                } else {
                    entityCardCache2.serialized_data = cursorQuery.getBlob(columnIndexOrThrow4);
                }
                entityCardCache = entityCardCache2;
            }
            return entityCardCache;
        } finally {
            cursorQuery.close();
            roomSQLiteQueryAcquire.release();
        }
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}
