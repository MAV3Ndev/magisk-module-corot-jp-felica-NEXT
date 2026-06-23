package com.felicanetworks.mfm.main.presenter.internal.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.felicanetworks.mfm.main.model.info.specific.MyDcardInfo;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.db.table.GroupSortOrderTable;
import com.felicanetworks.mfm.main.presenter.internal.db.table.HiddenUnregisteredServiceTable;
import com.felicanetworks.mfm.main.presenter.internal.db.table.IdServiceTypeTable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MyServiceDatabaseAccess {
    private static final String COLUMN_SERVICEID = "ServiceID";
    private static final int RETRY_COUNT = 2;
    private static MyServiceDatabaseAccess self = new MyServiceDatabaseAccess();
    private final MyServiceDatabaseHelper helper = new MyServiceDatabaseHelper();

    private interface Transaction<T> {
        T run(SQLiteDatabase db);
    }

    public static MyServiceDatabaseAccess getInstance() {
        return self;
    }

    private MyServiceDatabaseAccess() {
    }

    public List<String> getSortOrderGroupIds() {
        try {
            return (List) read(new Transaction<List<String>>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.1
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public List<String> run(SQLiteDatabase db) {
                    ArrayList arrayList = new ArrayList();
                    Cursor cursorQuery = db.query(GroupSortOrderTable.TABLE_NAME, new String[]{GroupSortOrderTable.Column.GROUP_ID.title()}, null, null, null, null, GroupSortOrderTable.Column.SORT_PRIORITY.title() + " ASC", "1000");
                    cursorQuery.moveToFirst();
                    while (!cursorQuery.isAfterLast()) {
                        arrayList.add(GroupSortOrderTable.Selector.findGroupId(cursorQuery));
                        cursorQuery.moveToNext();
                    }
                    cursorQuery.close();
                    return arrayList;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
            return Collections.EMPTY_LIST;
        }
    }

    public void setSortOrderGroupIds(final List<String> gids) {
        if (gids == null) {
            return;
        }
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.2
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase db) {
                    db.delete(GroupSortOrderTable.TABLE_NAME, null, null);
                    for (int i = 0; i < gids.size(); i++) {
                        db.insert(GroupSortOrderTable.TABLE_NAME, null, new GroupSortOrderTable.RecordBuilder().setGroupId((String) gids.get(i)).setSortPriority(i).build());
                    }
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public List<String> getHiddenServiceIds() {
        try {
            return (List) read(new Transaction<List<String>>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.3
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public List<String> run(SQLiteDatabase db) {
                    ArrayList arrayList = new ArrayList();
                    Cursor cursorQuery = db.query(HiddenUnregisteredServiceTable.TABLE_NAME, null, null, null, null, null, null, "1000");
                    cursorQuery.moveToFirst();
                    while (!cursorQuery.isAfterLast()) {
                        arrayList.add(HiddenUnregisteredServiceTable.Selector.findServiceId(cursorQuery));
                        cursorQuery.moveToNext();
                    }
                    cursorQuery.close();
                    return arrayList;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
            return Collections.EMPTY_LIST;
        }
    }

    public void addHiddenServiceId(final String sid) {
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.4
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase db) {
                    db.replaceOrThrow(HiddenUnregisteredServiceTable.TABLE_NAME, null, new HiddenUnregisteredServiceTable.RecordBuilder().setServiceId(sid).build());
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public void removeHiddenServiceId(final String sid) {
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.5
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase db) {
                    db.delete(HiddenUnregisteredServiceTable.TABLE_NAME, String.format("%s=?", HiddenUnregisteredServiceTable.Column.SERVICE_ID.title()), new String[]{sid});
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public MyDcardInfo.DcardType getDcardType(final String cid) {
        try {
            return (MyDcardInfo.DcardType) read(new Transaction<MyDcardInfo.DcardType>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.6
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public MyDcardInfo.DcardType run(SQLiteDatabase db) {
                    Integer numFindServiceType;
                    MyDcardInfo.DcardType dcardTypeResoleve = MyDcardInfo.DcardType.UNKNOWN;
                    Cursor cursorQuery = db.query(IdServiceTypeTable.TABLE_NAME, new String[]{IdServiceTypeTable.Column.SERVICE_TYPE.title()}, IdServiceTypeTable.Column.CARD_ID.title() + "=?", new String[]{cid}, null, null, null, "1");
                    cursorQuery.moveToFirst();
                    if (!cursorQuery.isAfterLast() && (numFindServiceType = IdServiceTypeTable.Selector.findServiceType(cursorQuery)) != null) {
                        dcardTypeResoleve = MyDcardInfo.DcardType.resoleve(numFindServiceType.intValue());
                    }
                    cursorQuery.close();
                    return dcardTypeResoleve;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
            return MyDcardInfo.DcardType.UNKNOWN;
        }
    }

    public void setDcardType(final String cid, final MyDcardInfo.DcardType type) {
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.7
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase db) {
                    db.replaceOrThrow(IdServiceTypeTable.TABLE_NAME, null, new IdServiceTypeTable.RecordBuilder().setCardId(cid).setServiceType(type.getValue()).build());
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public void deleteRelatedToAccount() {
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.8
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase db) {
                    db.delete(GroupSortOrderTable.TABLE_NAME, null, null);
                    db.delete(HiddenUnregisteredServiceTable.TABLE_NAME, null, null);
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    private <T> T read(Transaction<T> transaction) throws Exception {
        T t;
        Exception exc = null;
        int i = 0;
        Exception e = null;
        while (true) {
            if (i >= 2) {
                t = null;
                exc = e;
                break;
            }
            try {
                t = (T) executeRead(transaction);
                break;
            } catch (Exception e2) {
                e = e2;
                this.helper.deleteDatabase();
                i++;
            }
        }
        if (exc == null) {
            return t;
        }
        throw exc;
    }

    private <T> T executeRead(Transaction<T> transaction) {
        SQLiteDatabase readableDatabase = this.helper.getReadableDatabase();
        try {
            T tRun = transaction.run(readableDatabase);
            if (readableDatabase != null) {
                readableDatabase.close();
            }
            return tRun;
        } catch (Throwable th) {
            if (readableDatabase != null) {
                try {
                    readableDatabase.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    private <T> T write(Transaction<T> transaction) throws Exception {
        T t;
        Exception exc = null;
        int i = 0;
        Exception e = null;
        while (true) {
            if (i >= 2) {
                t = null;
                exc = e;
                break;
            }
            try {
                t = (T) executeWrite(transaction);
                break;
            } catch (Exception e2) {
                e = e2;
                this.helper.deleteDatabase();
                i++;
            }
        }
        if (exc == null) {
            return t;
        }
        throw exc;
    }

    private <T> T executeWrite(Transaction<T> transaction) {
        SQLiteDatabase writableDatabase = this.helper.getWritableDatabase();
        try {
            writableDatabase.beginTransaction();
            T tRun = transaction.run(writableDatabase);
            writableDatabase.setTransactionSuccessful();
            return tRun;
        } finally {
            writableDatabase.endTransaction();
            writableDatabase.close();
        }
    }
}
