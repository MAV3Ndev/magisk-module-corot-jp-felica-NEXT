package com.felicanetworks.mfc.mfi.db;

import androidx.room.InvalidationTracker;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenDelegate;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.SQLite;
import androidx.sqlite.SQLiteConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* JADX INFO: loaded from: classes3.dex */
public final class MfiDatabase_Impl extends MfiDatabase {
    private volatile DaoCardCache _daoCardCache;

    /* JADX DEBUG: Method merged with bridge method: createOpenDelegate()Landroidx/room/RoomOpenDelegateMarker; */
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // androidx.room.RoomDatabase
    public RoomOpenDelegate createOpenDelegate() {
        return new RoomOpenDelegate(1, "7cb5aba2186c3d2bab2ee7562c9856d6", "02afc9badaf894d263d019c0572a7ff7") { // from class: com.felicanetworks.mfc.mfi.db.MfiDatabase_Impl.1
            @Override // androidx.room.RoomOpenDelegate
            public void onCreate(final SQLiteConnection connection) {
            }

            @Override // androidx.room.RoomOpenDelegate
            public void onPostMigrate(final SQLiteConnection connection) {
            }

            @Override // androidx.room.RoomOpenDelegate
            public void createAllTables(final SQLiteConnection connection) throws Exception {
                SQLite.execSQL(connection, "CREATE TABLE IF NOT EXISTS `card_caches` (`id` TEXT NOT NULL, `version` TEXT NOT NULL, `cached_time` INTEGER NOT NULL, `serialized_data` BLOB, PRIMARY KEY(`id`))");
                SQLite.execSQL(connection, RoomMasterTable.CREATE_QUERY);
                SQLite.execSQL(connection, "INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '7cb5aba2186c3d2bab2ee7562c9856d6')");
            }

            @Override // androidx.room.RoomOpenDelegate
            public void dropAllTables(final SQLiteConnection connection) throws Exception {
                SQLite.execSQL(connection, "DROP TABLE IF EXISTS `card_caches`");
            }

            @Override // androidx.room.RoomOpenDelegate
            public void onOpen(final SQLiteConnection connection) {
                MfiDatabase_Impl.this.internalInitInvalidationTracker(connection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public void onPreMigrate(final SQLiteConnection connection) {
                DBUtil.dropFtsSyncTriggers(connection);
            }

            @Override // androidx.room.RoomOpenDelegate
            public RoomOpenDelegate.ValidationResult onValidateSchema(final SQLiteConnection connection) {
                HashMap map = new HashMap(4);
                map.put("id", new TableInfo.Column("id", "TEXT", true, 1, null, 1));
                map.put("version", new TableInfo.Column("version", "TEXT", true, 0, null, 1));
                map.put("cached_time", new TableInfo.Column("cached_time", "INTEGER", true, 0, null, 1));
                map.put("serialized_data", new TableInfo.Column("serialized_data", "BLOB", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo("card_caches", map, new HashSet(0), new HashSet(0));
                TableInfo tableInfo2 = TableInfo.read(connection, "card_caches");
                if (!tableInfo.equals(tableInfo2)) {
                    return new RoomOpenDelegate.ValidationResult(false, "card_caches(com.felicanetworks.mfc.mfi.db.EntityCardCache).\n Expected:\n" + tableInfo + "\n Found:\n" + tableInfo2);
                }
                return new RoomOpenDelegate.ValidationResult(true, null);
            }
        };
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "card_caches");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.performClear(false, "card_caches");
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap map = new HashMap();
        map.put(DaoCardCache.class, DaoCardCache_Impl.getRequiredConverters());
        return map;
    }

    @Override // androidx.room.RoomDatabase
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override // androidx.room.RoomDatabase
    public List<Migration> getAutoMigrations(final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
        return new ArrayList();
    }

    @Override // com.felicanetworks.mfc.mfi.db.MfiDatabase
    public DaoCardCache cardCacheDao() {
        DaoCardCache daoCardCache;
        if (this._daoCardCache != null) {
            return this._daoCardCache;
        }
        synchronized (this) {
            if (this._daoCardCache == null) {
                this._daoCardCache = new DaoCardCache_Impl(this);
            }
            daoCardCache = this._daoCardCache;
        }
        return daoCardCache;
    }
}
