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

/* JADX INFO: loaded from: classes.dex */
public class MyServiceDatabaseAccess {
    private static final String COLUMN_SERVICEID = "ServiceID";
    private static final int RETRY_COUNT = 2;
    private static MyServiceDatabaseAccess self = new MyServiceDatabaseAccess();
    private final MyServiceDatabaseHelper helper = new MyServiceDatabaseHelper();

    private interface Transaction<T> {
        T run(SQLiteDatabase sQLiteDatabase);
    }

    public static MyServiceDatabaseAccess getInstance() {
        return self;
    }

    private MyServiceDatabaseAccess() {
    }

    public List<String> getSortOrderGroupIds() {
        try {
            return (List) read(new Transaction<List<String>>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.1
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public List<String> run(SQLiteDatabase sQLiteDatabase) {
                    ArrayList arrayList = new ArrayList();
                    Cursor cursorRawQuery = sQLiteDatabase.rawQuery(GroupSortOrderTable.Statement.SELECT_ALL_ORDER_BY_PRIORITY, null);
                    cursorRawQuery.moveToFirst();
                    while (!cursorRawQuery.isAfterLast()) {
                        arrayList.add(GroupSortOrderTable.Selector.findGroupId(cursorRawQuery));
                        cursorRawQuery.moveToNext();
                    }
                    cursorRawQuery.close();
                    return arrayList;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
            return Collections.emptyList();
        }
    }

    public void setSortOrderGroupIds(final List<String> list) {
        if (list == null) {
            return;
        }
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.2
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase sQLiteDatabase) {
                    sQLiteDatabase.delete(GroupSortOrderTable.TABLE_NAME, null, null);
                    for (int i = 0; i < list.size(); i++) {
                        sQLiteDatabase.insert(GroupSortOrderTable.TABLE_NAME, null, new GroupSortOrderTable.RecordBuilder().setGroupId((String) list.get(i)).setSortPriority(i).build());
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
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public List<String> run(SQLiteDatabase sQLiteDatabase) {
                    ArrayList arrayList = new ArrayList();
                    Cursor cursorRawQuery = sQLiteDatabase.rawQuery(HiddenUnregisteredServiceTable.Statement.SELECT_ALL, null);
                    cursorRawQuery.moveToFirst();
                    while (!cursorRawQuery.isAfterLast()) {
                        arrayList.add(HiddenUnregisteredServiceTable.Selector.findServiceId(cursorRawQuery));
                        cursorRawQuery.moveToNext();
                    }
                    cursorRawQuery.close();
                    return arrayList;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
            return Collections.emptyList();
        }
    }

    public void addHiddenServiceId(final String str) {
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.4
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase sQLiteDatabase) {
                    sQLiteDatabase.replaceOrThrow(HiddenUnregisteredServiceTable.TABLE_NAME, null, new HiddenUnregisteredServiceTable.RecordBuilder().setServiceId(str).build());
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public void removeHiddenServiceId(final String str) {
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.5
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase sQLiteDatabase) {
                    sQLiteDatabase.delete(HiddenUnregisteredServiceTable.TABLE_NAME, String.format("%s=?", HiddenUnregisteredServiceTable.Column.SERVICE_ID.title()), new String[]{str});
                    return null;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
        }
    }

    public MyDcardInfo.DcardType getDcardType(final String str) {
        try {
            return (MyDcardInfo.DcardType) read(new Transaction<MyDcardInfo.DcardType>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.6
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public MyDcardInfo.DcardType run(SQLiteDatabase sQLiteDatabase) {
                    Integer numFindServiceType;
                    MyDcardInfo.DcardType dcardTypeResoleve = MyDcardInfo.DcardType.UNKNOWN;
                    Cursor cursorRawQuery = sQLiteDatabase.rawQuery(IdServiceTypeTable.Statement.SELECT_SERVICE_TYPE_WITH_CID, new String[]{str});
                    cursorRawQuery.moveToFirst();
                    if (!cursorRawQuery.isAfterLast() && (numFindServiceType = IdServiceTypeTable.Selector.findServiceType(cursorRawQuery)) != null) {
                        dcardTypeResoleve = MyDcardInfo.DcardType.resoleve(numFindServiceType.intValue());
                    }
                    cursorRawQuery.close();
                    return dcardTypeResoleve;
                }
            });
        } catch (Exception e) {
            LogUtil.error(e);
            return MyDcardInfo.DcardType.UNKNOWN;
        }
    }

    public void setDcardType(final String str, final MyDcardInfo.DcardType dcardType) {
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.7
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase sQLiteDatabase) {
                    sQLiteDatabase.replaceOrThrow(IdServiceTypeTable.TABLE_NAME, null, new IdServiceTypeTable.RecordBuilder().setCardId(str).setServiceType(dcardType.getValue()).build());
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
                @Override // com.felicanetworks.mfm.main.presenter.internal.db.MyServiceDatabaseAccess.Transaction
                public Void run(SQLiteDatabase sQLiteDatabase) {
                    sQLiteDatabase.delete(GroupSortOrderTable.TABLE_NAME, null, null);
                    sQLiteDatabase.delete(HiddenUnregisteredServiceTable.TABLE_NAME, null, null);
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
            try {
                throw th;
            } catch (Throwable th2) {
                if (readableDatabase != null) {
                    try {
                        readableDatabase.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                }
                throw th2;
            }
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
