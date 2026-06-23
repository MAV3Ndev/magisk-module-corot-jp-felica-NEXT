package com.felicanetworks.mfc.mfi.db;

import androidx.room.EntityInsertAdapter;
import androidx.room.RoomDatabase;
import androidx.room.util.DBUtil;
import androidx.room.util.SQLiteStatementUtil;
import androidx.sqlite.SQLiteConnection;
import androidx.sqlite.SQLiteStatement;
import java.util.Collections;
import java.util.List;
import kotlin.jvm.functions.Function1;

/* JADX INFO: loaded from: classes3.dex */
public final class DaoCardCache_Impl extends DaoCardCache {
    private final RoomDatabase __db;
    private final EntityInsertAdapter<EntityCardCache> __insertAdapterOfEntityCardCache = new EntityInsertAdapter<EntityCardCache>() { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl.1
        @Override // androidx.room.EntityInsertAdapter
        protected String createQuery() {
            return "INSERT OR REPLACE INTO `card_caches` (`id`,`version`,`cached_time`,`serialized_data`) VALUES (?,?,?,?)";
        }

        /* JADX DEBUG: Method merged with bridge method: bind(Landroidx/sqlite/SQLiteStatement;Ljava/lang/Object;)V */
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // androidx.room.EntityInsertAdapter
        public void bind(final SQLiteStatement statement, final EntityCardCache entity) {
            if (entity.id == null) {
                statement.mo325bindNull(1);
            } else {
                statement.mo326bindText(1, entity.id);
            }
            if (entity.version == null) {
                statement.mo325bindNull(2);
            } else {
                statement.mo326bindText(2, entity.version);
            }
            statement.mo324bindLong(3, entity.cached_time);
            if (entity.serialized_data == null) {
                statement.mo325bindNull(4);
            } else {
                statement.mo322bindBlob(4, entity.serialized_data);
            }
        }
    };

    public DaoCardCache_Impl(final RoomDatabase __db) {
        this.__db = __db;
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public void insert(final EntityCardCache cardCache) {
        DBUtil.performBlocking(this.__db, false, true, new Function1() { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl$$ExternalSyntheticLambda2
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return this.f$0.m406lambda$insert$0$comfelicanetworksmfcmfidbDaoCardCache_Impl(cardCache, (SQLiteConnection) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$insert$0$com-felicanetworks-mfc-mfi-db-DaoCardCache_Impl, reason: not valid java name */
    /* synthetic */ Object m406lambda$insert$0$comfelicanetworksmfcmfidbDaoCardCache_Impl(EntityCardCache entityCardCache, SQLiteConnection sQLiteConnection) throws Exception {
        this.__insertAdapterOfEntityCardCache.insert(sQLiteConnection, entityCardCache);
        return null;
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public EntityCardCache select(final String id) {
        return (EntityCardCache) DBUtil.performBlocking(this.__db, true, false, new Function1() { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl$$ExternalSyntheticLambda0
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DaoCardCache_Impl.lambda$select$1(id, (SQLiteConnection) obj);
            }
        });
    }

    static /* synthetic */ EntityCardCache lambda$select$1(String str, SQLiteConnection sQLiteConnection) {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("SELECT * FROM card_caches WHERE id = ?");
        try {
            if (str == null) {
                sQLiteStatementPrepare.mo325bindNull(1);
            } else {
                sQLiteStatementPrepare.mo326bindText(1, str);
            }
            int columnIndexOrThrow = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "id");
            int columnIndexOrThrow2 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "version");
            int columnIndexOrThrow3 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "cached_time");
            int columnIndexOrThrow4 = SQLiteStatementUtil.getColumnIndexOrThrow(sQLiteStatementPrepare, "serialized_data");
            EntityCardCache entityCardCache = null;
            if (sQLiteStatementPrepare.step()) {
                EntityCardCache entityCardCache2 = new EntityCardCache();
                if (sQLiteStatementPrepare.isNull(columnIndexOrThrow)) {
                    entityCardCache2.id = null;
                } else {
                    entityCardCache2.id = sQLiteStatementPrepare.getText(columnIndexOrThrow);
                }
                if (sQLiteStatementPrepare.isNull(columnIndexOrThrow2)) {
                    entityCardCache2.version = null;
                } else {
                    entityCardCache2.version = sQLiteStatementPrepare.getText(columnIndexOrThrow2);
                }
                entityCardCache2.cached_time = sQLiteStatementPrepare.getLong(columnIndexOrThrow3);
                if (sQLiteStatementPrepare.isNull(columnIndexOrThrow4)) {
                    entityCardCache2.serialized_data = null;
                } else {
                    entityCardCache2.serialized_data = sQLiteStatementPrepare.getBlob(columnIndexOrThrow4);
                }
                entityCardCache = entityCardCache2;
            }
            return entityCardCache;
        } finally {
            sQLiteStatementPrepare.close();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public void delete(final String id) {
        DBUtil.performBlocking(this.__db, false, true, new Function1() { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl$$ExternalSyntheticLambda1
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DaoCardCache_Impl.lambda$delete$2(id, (SQLiteConnection) obj);
            }
        });
    }

    static /* synthetic */ Object lambda$delete$2(String str, SQLiteConnection sQLiteConnection) {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("DELETE FROM card_caches WHERE id = ?");
        try {
            if (str == null) {
                sQLiteStatementPrepare.mo325bindNull(1);
            } else {
                sQLiteStatementPrepare.mo326bindText(1, str);
            }
            sQLiteStatementPrepare.step();
            sQLiteStatementPrepare.close();
            return null;
        } catch (Throwable th) {
            sQLiteStatementPrepare.close();
            throw th;
        }
    }

    @Override // com.felicanetworks.mfc.mfi.db.DaoCardCache
    public void cutOffOverLimitRecords() {
        DBUtil.performBlocking(this.__db, false, true, new Function1() { // from class: com.felicanetworks.mfc.mfi.db.DaoCardCache_Impl$$ExternalSyntheticLambda3
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return DaoCardCache_Impl.lambda$cutOffOverLimitRecords$3((SQLiteConnection) obj);
            }
        });
    }

    static /* synthetic */ Object lambda$cutOffOverLimitRecords$3(SQLiteConnection sQLiteConnection) {
        SQLiteStatement sQLiteStatementPrepare = sQLiteConnection.prepare("DELETE FROM card_caches WHERE cached_time IN(SELECT cached_time FROM card_caches ORDER BY cached_time DESC LIMIT 3 OFFSET 30)");
        try {
            sQLiteStatementPrepare.step();
            sQLiteStatementPrepare.close();
            return null;
        } catch (Throwable th) {
            sQLiteStatementPrepare.close();
            throw th;
        }
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.EMPTY_LIST;
    }
}
