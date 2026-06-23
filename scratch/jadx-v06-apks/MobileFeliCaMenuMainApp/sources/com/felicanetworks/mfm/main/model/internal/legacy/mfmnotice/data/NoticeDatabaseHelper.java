package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.felicanetworks.mfm.main.presenter.internal.notification.NotificationController;
import java.sql.SQLException;

/* JADX INFO: loaded from: classes.dex */
public class NoticeDatabaseHelper extends SQLiteOpenHelper {
    private static final String ALTER_TABLE_ACCESS = "ALTER TABLE NoticeList ADD DetailAccess TEXT DEFAULT '1' NOT NULL";
    private static final String ALTER_TABLE_BTN = "ALTER TABLE NoticeList ADD ButtonLabel TEXT";
    private static final String CREATE_DISPSERVICELIST = "CREATE TABLE DispServiceList(ServiceID TEXT NOT NULL, SEAreaIdentifiedResult TEXT NOT NULL, CONSTRAINT service_pky PRIMARY KEY (ServiceID))";
    private static final String CREATE_NOTICEDATALIST = "CREATE TABLE NoticeList(NotificationID TEXT NOT NULL,PatternID TEXT NOT NULL,MessageType TEXT NOT NULL,SendDate TEXT NOT NULL,ExpirationDate TEXT NOT NULL,Title TEXT NOT NULL,Message TEXT NOT NULL,IconURL TEXT NOT NULL,IconName TEXT NOT NULL,ImageName TEXT NOT NULL,Scheme TEXT NOT NULL,Status TEXT NOT NULL, ButtonLabel TEXT, DetailAccess TEXT NOT NULL, CONSTRAINT notice_pky PRIMARY KEY (NotificationID))";
    private static final String DATABASE_NAME = "mfm_push.db";
    private static final int DATABASE_VERSION = 5;
    private static final String INITIAL_NOTIFICATION_ID = "0";
    private static final String INITIAL_NOTIFICATION_ID1 = "1";
    private static final String INITIAL_ONE_DATA = "1";
    private static final String INITIAL_SERVICE_ID_DATA = "0";
    private static final String INITIAL_SE_AREA_IDENTIFIED_RESULT_DATA = "0";
    private static final String INITIAL_ZERO_DATA = "0";
    private static final String JSON_BUTTON = "btn";
    private static final String JSON_EXPIREDATE = "ed";
    private static final String JSON_GROWTHPUSH = "growthpush";
    private static final String JSON_ICON = "icon";
    private static final String JSON_IMAGE = "img";
    private static final String JSON_IMAGEPATH = "ipath";
    private static final String JSON_LINKSCHEME = "ls";
    private static final String JSON_MESSAGE = "message";
    private static final String JSON_NOTIFICATIONID = "notificationId";
    private static final String JSON_PATTERNID = "pid";
    private static final String JSON_SENDDATE = "sd";
    private static final String JSON_TEXT = "txt";
    private static final String JSON_TITLE = "hd";
    private static final String JSON_TYPE = "tp";
    private static final String TABLE_DISPSERVICELIST = "DispServiceList";
    private static final String TABLE_NOTICELIST = "NoticeList";
    private static final String UPDATE_TABLE_BTN_DEFAULT = "UPDATE NoticeList SET ButtonLabel='詳細'";
    private static volatile SQLiteDatabase db = null;
    private static volatile NoticeDatabaseHelper dbHelper = null;
    private static final String insertDispServiceListSql = "INSERT INTO DispServiceList VALUES ( ?, ?)";
    private static final String insertNoticeListSql = "INSERT INTO NoticeList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static synchronized NoticeDatabaseHelper getInstance(Context context) throws SQLException {
        if (dbHelper == null) {
            dbHelper = new NoticeDatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
            db.close();
        }
        return dbHelper;
    }

    private NoticeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 5);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL(CREATE_NOTICEDATALIST);
        sQLiteDatabase.execSQL(CREATE_DISPSERVICELIST);
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            setInitNoticeData(sQLiteDatabase, "0", "0");
            setInitNoticeData(sQLiteDatabase, "1", "0");
            sQLiteStatementCompileStatement = sQLiteDatabase.compileStatement(insertDispServiceListSql);
            sQLiteStatementCompileStatement.bindString(1, "0");
            sQLiteStatementCompileStatement.bindString(2, "0");
            sQLiteStatementCompileStatement.executeInsert();
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Exception unused) {
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
        sQLiteStatementCompileStatement.close();
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        if (i == 1) {
            sQLiteDatabase.execSQL(ALTER_TABLE_BTN);
            sQLiteDatabase.execSQL(ALTER_TABLE_ACCESS);
            sQLiteDatabase.execSQL(UPDATE_TABLE_BTN_DEFAULT);
        } else {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        if (i == 5) {
                            return;
                        }
                        throw new SQLiteDatabaseCorruptException("DB Version Injustice :" + i);
                    }
                }
                upgradeToCurrentFromV4(sQLiteDatabase);
            }
            upgradeToCurrentFromV3(sQLiteDatabase);
            upgradeToCurrentFromV4(sQLiteDatabase);
        }
        upgradeToCurrentFromV2(sQLiteDatabase);
        upgradeToCurrentFromV3(sQLiteDatabase);
        upgradeToCurrentFromV4(sQLiteDatabase);
    }

    private void upgradeToCurrentFromV2(SQLiteDatabase sQLiteDatabase) {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            sQLiteDatabase.execSQL(CREATE_DISPSERVICELIST);
            sQLiteStatementCompileStatement = sQLiteDatabase.compileStatement(insertDispServiceListSql);
            sQLiteStatementCompileStatement.bindString(1, "0");
            sQLiteStatementCompileStatement.bindString(2, "0");
            sQLiteStatementCompileStatement.executeInsert();
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Exception unused) {
            if (sQLiteStatementCompileStatement == null) {
                return;
            }
        } catch (Throwable th) {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
            throw th;
        }
        sQLiteStatementCompileStatement.close();
    }

    private void upgradeToCurrentFromV3(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = sQLiteDatabase.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY}, "NotificationID = ?", new String[]{"0"}, null, null, null, null);
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() == 0) {
                setInitNoticeData(sQLiteDatabase, "0", "1");
            }
            if (cursorQuery == null) {
                return;
            }
        } catch (Exception unused) {
            if (cursorQuery == null) {
                return;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
        cursorQuery.close();
    }

    private void upgradeToCurrentFromV4(SQLiteDatabase sQLiteDatabase) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = sQLiteDatabase.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY}, "NotificationID = ?", new String[]{"1"}, null, null, null, null);
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() == 0) {
                setInitNoticeData(sQLiteDatabase, "1", "0");
            }
            if (cursorQuery == null) {
                return;
            }
        } catch (Exception unused) {
            if (cursorQuery == null) {
                return;
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            throw th;
        }
        cursorQuery.close();
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0138  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void setInitNoticeData(android.database.sqlite.SQLiteDatabase r17, java.lang.String r18, java.lang.String r19) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 319
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseHelper.setInitNoticeData(android.database.sqlite.SQLiteDatabase, java.lang.String, java.lang.String):void");
    }

    public static void clearInstance() {
        if (db != null && db.isOpen()) {
            try {
                db.close();
            } catch (Exception unused) {
            }
        }
        if (dbHelper != null) {
            try {
                dbHelper.close();
            } catch (Exception unused2) {
            }
            db = null;
            dbHelper = null;
        }
    }
}
