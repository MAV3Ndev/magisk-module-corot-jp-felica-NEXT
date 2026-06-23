package com.felicanetworks.mfm.messenger;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import java.lang.ref.WeakReference;
import java.util.Date;

/* JADX INFO: loaded from: classes3.dex */
class Storage extends SQLiteOpenHelper {
    private static final int REFRESH_DB_COUNT = 2;
    private static final int RETRY_COUNT = 3;
    private static final String STORAGE_NAME = "com.felicanetworks.mfm.messenger.Storage.db";
    private static final int STORAGE_VERSION = 1;
    private static Storage self;
    private WeakReference<Context> wrContext;

    private interface Transaction<T> {
        T run(SQLiteDatabase sQLiteDatabase);
    }

    enum Item {
        TOKEN("token", "");

        final String defaultValue;
        final String title;

        Item(String str, String str2) {
            this.title = str;
            this.defaultValue = str2;
        }
    }

    private static class SettingsTable {
        static final String TABLE_NAME = "Settings";

        private SettingsTable() {
        }

        enum Column {
            ITEM("Item", "TEXT", "NOT NULL"),
            CONTENT("Content", "TEXT", null),
            UPDATE_AT("UpdateAt", "TEXT", null);

            final String constraint;
            final String title;
            final String type;

            Column(String str, String str2, String str3) {
                this.title = str;
                this.type = str2;
                this.constraint = str3;
            }

            static String config() {
                StringBuilder sb = new StringBuilder();
                for (Column column : values()) {
                    if (!TextUtils.isEmpty(sb)) {
                        sb.append(", ");
                    }
                    sb.append(column.title);
                    sb.append(" ");
                    sb.append(column.type);
                    if (!TextUtils.isEmpty(column.constraint)) {
                        sb.append(" ");
                        sb.append(column.constraint);
                    }
                }
                return sb.toString();
            }
        }

        static class Statement {
            static final String CREATE = String.format("CREATE TABLE %s(%s, CONSTRAINT pky PRIMARY KEY(%s))", SettingsTable.TABLE_NAME, Column.config(), Column.ITEM.title);

            Statement() {
            }
        }
    }

    static Storage keeper(Context context) {
        if (self == null) {
            newSelf(context);
        }
        self.setupContext(context);
        return self;
    }

    private static synchronized void newSelf(Context context) {
        if (self != null) {
            return;
        }
        self = new Storage(context);
    }

    private void setupContext(Context context) {
        this.wrContext = new WeakReference<>(context);
    }

    private Context context() {
        WeakReference<Context> weakReference = this.wrContext;
        if (weakReference == null) {
            return null;
        }
        return weakReference.get();
    }

    private Storage(Context context) {
        super(context, STORAGE_NAME, (SQLiteDatabase.CursorFactory) null, 1);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Logger.debug(sQLiteDatabase);
        sQLiteDatabase.execSQL(SettingsTable.Statement.CREATE);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        Logger.debug(sQLiteDatabase, Integer.valueOf(i), Integer.valueOf(i2));
    }

    String refer(final Item item) {
        Logger.debug(item);
        try {
            return (String) read(new Transaction<String>() { // from class: com.felicanetworks.mfm.messenger.Storage.1
                /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.messenger.Storage.Transaction
                public String run(SQLiteDatabase sQLiteDatabase) {
                    String string = item.defaultValue;
                    Cursor cursorQuery = null;
                    try {
                        cursorQuery = sQLiteDatabase.query("Settings", new String[]{SettingsTable.Column.CONTENT.title}, SettingsTable.Column.ITEM.title + "=?", new String[]{item.title}, null, null, null, "1");
                        cursorQuery.moveToFirst();
                        if (!cursorQuery.isAfterLast()) {
                            string = cursorQuery.getString(0);
                        }
                        if (cursorQuery != null) {
                            cursorQuery.close();
                        }
                        return string;
                    } finally {
                    }
                }
            });
        } catch (Exception e) {
            Logger.error(e);
            return item.defaultValue;
        }
    }

    void store(final Item item, final String str) {
        Logger.debug(item, str);
        try {
            write(new Transaction<Void>() { // from class: com.felicanetworks.mfm.messenger.Storage.2
                /* JADX DEBUG: Method merged with bridge method: run(Landroid/database/sqlite/SQLiteDatabase;)Ljava/lang/Object; */
                @Override // com.felicanetworks.mfm.messenger.Storage.Transaction
                public Void run(SQLiteDatabase sQLiteDatabase) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(SettingsTable.Column.ITEM.title, item.title);
                    contentValues.put(SettingsTable.Column.CONTENT.title, str);
                    contentValues.put(SettingsTable.Column.UPDATE_AT.title, new Date().toString());
                    sQLiteDatabase.replaceOrThrow("Settings", null, contentValues);
                    return null;
                }
            });
        } catch (Exception e) {
            Logger.error(e);
        }
    }

    private <T> T read(Transaction<T> transaction) throws Exception {
        T t;
        Exception exc = null;
        Exception e = null;
        int i = 0;
        while (true) {
            if (i >= 3) {
                t = null;
                exc = e;
                break;
            }
            Logger.debug(Integer.valueOf(i));
            try {
                t = (T) executeRead(transaction);
                break;
            } catch (Exception e2) {
                e = e2;
                i++;
            }
        }
        if (exc == null) {
            return t;
        }
        throw exc;
    }

    private <T> T executeRead(Transaction<T> transaction) {
        SQLiteDatabase readableDatabase = getReadableDatabase();
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
        Exception e = null;
        int i = 1;
        while (true) {
            if (i > 3) {
                t = null;
                exc = e;
                break;
            }
            Logger.debug(Integer.valueOf(i));
            try {
                t = (T) executeWrite(transaction);
                break;
            } catch (Exception e2) {
                e = e2;
                if (i >= 2) {
                    deleteDatabase();
                }
                i++;
            }
        }
        if (exc == null) {
            return t;
        }
        throw exc;
    }

    private <T> T executeWrite(Transaction<T> transaction) {
        SQLiteDatabase writableDatabase = getWritableDatabase();
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

    private void deleteDatabase() {
        Logger.debug(new Object[0]);
        try {
            Context context = context();
            if (context == null) {
                return;
            }
            context.deleteDatabase(STORAGE_NAME);
        } catch (Exception unused) {
        }
    }
}
