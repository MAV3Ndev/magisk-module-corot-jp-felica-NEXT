package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.felicanetworks.mfm.main.policy.sg.Sg;
import com.felicanetworks.mfm.main.presenter.internal.notification.NotificationController;
import java.sql.SQLException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeDatabaseHelper extends SQLiteOpenHelper {
    private static final String ALTER_TABLE_ACCESS = "ALTER TABLE NoticeList ADD DetailAccess TEXT DEFAULT '1' NOT NULL";
    private static final String ALTER_TABLE_BTN = "ALTER TABLE NoticeList ADD ButtonLabel TEXT";
    private static final String ALTER_TABLE_NOTICELIST_RENAME = "ALTER TABLE NoticeList RENAME TO NoticeList_TEMP";
    private static final String COLUMN_NOTICELIST = "NotificationID, MessageType, SendDate, ExpirationDate, Title, Message, IconURL, IconName, ImageName, Scheme, Status, ButtonLabel, DetailAccess";
    private static final String CREATE_DISPSERVICELIST = "CREATE TABLE DispServiceList(ServiceID TEXT NOT NULL, SEAreaIdentifiedResult TEXT NOT NULL, CONSTRAINT service_pky PRIMARY KEY (ServiceID))";
    private static final String CREATE_NOTICEDATALIST = "CREATE TABLE NoticeList(NotificationID TEXT NOT NULL,MessageType TEXT NOT NULL,SendDate TEXT NOT NULL,ExpirationDate TEXT NOT NULL,Title TEXT NOT NULL,Message TEXT NOT NULL,IconURL TEXT NOT NULL,IconName TEXT NOT NULL,ImageName TEXT NOT NULL,Scheme TEXT NOT NULL,Status TEXT NOT NULL, ButtonLabel TEXT, DetailAccess TEXT NOT NULL, CONSTRAINT notice_pky PRIMARY KEY (NotificationID))";
    private static final String DATABASE_NAME = "mfm_push.db";
    private static final int DATABASE_VERSION = 7;
    private static final String DELETE_TABLE_NOTICELIST = "DELETE FROM NoticeList WHERE NotificationID = '1'";
    private static final String DROP_TABLE_TEMP = "DROP TABLE NoticeList_TEMP";
    private static final String INITIAL_NOTIFICATION_ID = "0";
    private static final String INITIAL_ONE_DATA = "1";
    private static final String INITIAL_SERVICE_ID_DATA = "0";
    private static final String INITIAL_SE_AREA_IDENTIFIED_RESULT_DATA = "0";
    private static final String INITIAL_ZERO_DATA = "0";
    private static final String INSERT_NOTICELIST_FROM_TEMP = "INSERT INTO NoticeList(NotificationID, MessageType, SendDate, ExpirationDate, Title, Message, IconURL, IconName, ImageName, Scheme, Status, ButtonLabel, DetailAccess) SELECT NotificationID, MessageType, SendDate, ExpirationDate, Title, Message, IconURL, IconName, ImageName, Scheme, Status, ButtonLabel, DetailAccess FROM NoticeList_TEMP";
    private static final String JSON_BUTTON = "btn";
    private static final String JSON_EXPIREDATE = "ed";
    private static final String JSON_GROWTHPUSH = "growthpush";
    private static final String JSON_ICON = "icon";
    private static final String JSON_IMAGE = "img";
    private static final String JSON_IMAGEPATH = "ipath";
    private static final String JSON_LINKSCHEME = "ls";
    private static final String JSON_MESSAGE = "message";
    private static final String JSON_NOTIFICATIONID = "notificationId";
    private static final String JSON_SENDDATE = "sd";
    private static final String JSON_TEXT = "txt";
    private static final String JSON_TITLE = "hd";
    private static final String JSON_TYPE = "tp";
    private static final String TABLE_DISPSERVICELIST = "DispServiceList";
    private static final String TABLE_NOTICELIST = "NoticeList";
    private static final String TABLE_NOTICELIST_TEMP = "NoticeList_TEMP";
    private static final String UPDATE_TABLE_BTN_DEFAULT = "UPDATE NoticeList SET ButtonLabel='詳細'";
    private static volatile SQLiteDatabase db = null;
    private static volatile NoticeDatabaseHelper dbHelper = null;
    private static final String insertDispServiceListSql = "INSERT INTO DispServiceList VALUES ( ?, ?)";
    private static final String insertNoticeListSql = "INSERT INTO NoticeList VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    public static synchronized NoticeDatabaseHelper getInstance(Context context) throws SQLException {
        if (dbHelper == null) {
            dbHelper = new NoticeDatabaseHelper(context);
            db = dbHelper.getWritableDatabase();
            db.close();
        }
        return dbHelper;
    }

    private NoticeDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, (SQLiteDatabase.CursorFactory) null, 7);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase db2) {
        db2.execSQL(CREATE_NOTICEDATALIST);
        db2.execSQL(CREATE_DISPSERVICELIST);
        try {
            setInitNoticeData(db2, "0", "0");
            setInitDispServiceData(db2);
        } catch (Exception unused) {
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase db2, int oldVersion, int newVersion) {
        if (oldVersion == 1) {
            db2.execSQL(ALTER_TABLE_BTN);
            db2.execSQL(ALTER_TABLE_ACCESS);
            db2.execSQL(UPDATE_TABLE_BTN_DEFAULT);
        } else {
            if (oldVersion != 2) {
                if (oldVersion != 3) {
                    if (oldVersion != 5) {
                        if (oldVersion != 6) {
                            if (oldVersion == 7) {
                                return;
                            }
                            throw new SQLiteDatabaseCorruptException("DB Version Injustice :" + oldVersion);
                        }
                    }
                    upgradeToCurrentFromV6(db2);
                }
                upgradeToCurrentFromV5(db2);
                upgradeToCurrentFromV6(db2);
            }
            upgradeToCurrentFromV3(db2);
            upgradeToCurrentFromV5(db2);
            upgradeToCurrentFromV6(db2);
        }
        upgradeToCurrentFromV2(db2);
        upgradeToCurrentFromV3(db2);
        upgradeToCurrentFromV5(db2);
        upgradeToCurrentFromV6(db2);
    }

    private void upgradeToCurrentFromV2(SQLiteDatabase db2) {
        try {
            db2.execSQL(CREATE_DISPSERVICELIST);
            setInitDispServiceData(db2);
        } catch (Exception unused) {
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private void upgradeToCurrentFromV3(SQLiteDatabase db2) {
        Cursor cursorQuery = null;
        try {
            cursorQuery = db2.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY}, "NotificationID = ?", new String[]{"0"}, null, null, null, null);
            cursorQuery.moveToFirst();
            if (cursorQuery.getCount() == 0) {
                dropColumnFromNoticeList(db2);
                setInitNoticeData(db2, "0", "1");
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Exception unused) {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        } catch (Throwable th) {
            if (cursorQuery != null) {
                cursorQuery.close();
                throw th;
            }
            throw th;
        }
    }

    private void upgradeToCurrentFromV5(SQLiteDatabase db2) {
        try {
            db2.execSQL(DELETE_TABLE_NOTICELIST);
        } catch (Exception unused) {
        }
    }

    private void upgradeToCurrentFromV6(SQLiteDatabase db2) {
        try {
            dropColumnFromNoticeList(db2);
            db2.delete(TABLE_DISPSERVICELIST, null, null);
            setInitDispServiceData(db2);
        } catch (Exception unused) {
        }
    }

    private void dropColumnFromNoticeList(SQLiteDatabase db2) {
        db2.execSQL(ALTER_TABLE_NOTICELIST_RENAME);
        db2.execSQL(CREATE_NOTICEDATALIST);
        db2.execSQL(INSERT_NOTICELIST_FROM_TEMP);
        db2.execSQL(DROP_TABLE_TEMP);
    }

    /* JADX DEBUG: Another duplicated slice has different insns count: {[IF]}, finally: {[IF, INVOKE] complete} */
    private void setInitNoticeData(SQLiteDatabase db2, String id, String status) {
        id.hashCode();
        if (id.equals("0")) {
            String str = (String) Sg.getValue(Sg.Key.SETTING_NOTICE_PREINSTALL_DATA);
            SQLiteStatement sQLiteStatementCompileStatement = null;
            if (str != null) {
                try {
                    if (!str.isEmpty()) {
                        JSONObject jSONObject = new JSONObject(str);
                        if (jSONObject.length() > 0) {
                            JSONObject jSONObject2 = new JSONObject(jSONObject.getString(JSON_MESSAGE));
                            String string = new JSONObject(jSONObject.getString(JSON_GROWTHPUSH)).getString(JSON_NOTIFICATIONID);
                            String string2 = jSONObject2.getString(JSON_TYPE);
                            String string3 = jSONObject2.getString(JSON_SENDDATE);
                            String string4 = jSONObject2.getString(JSON_EXPIREDATE);
                            String string5 = jSONObject2.getString(JSON_TITLE);
                            String string6 = jSONObject2.getString(JSON_TEXT);
                            String string7 = !jSONObject.isNull("ipath") ? jSONObject.getString("ipath") : "";
                            String string8 = !jSONObject.isNull(JSON_ICON) ? jSONObject.getString(JSON_ICON) : "";
                            String string9 = jSONObject.isNull(JSON_IMAGE) ? "" : jSONObject.getString(JSON_IMAGE);
                            String string10 = jSONObject.getString("ls");
                            String string11 = jSONObject.getString(JSON_BUTTON);
                            sQLiteStatementCompileStatement = db2.compileStatement(insertNoticeListSql);
                            sQLiteStatementCompileStatement.bindString(1, string);
                            sQLiteStatementCompileStatement.bindString(2, string2);
                            sQLiteStatementCompileStatement.bindString(3, string3);
                            sQLiteStatementCompileStatement.bindString(4, string4);
                            sQLiteStatementCompileStatement.bindString(5, string5);
                            sQLiteStatementCompileStatement.bindString(6, string6);
                            sQLiteStatementCompileStatement.bindString(7, string7);
                            sQLiteStatementCompileStatement.bindString(8, string8);
                            sQLiteStatementCompileStatement.bindString(9, string9);
                            sQLiteStatementCompileStatement.bindString(10, string10);
                            sQLiteStatementCompileStatement.bindString(11, status);
                            sQLiteStatementCompileStatement.bindString(12, string11);
                            sQLiteStatementCompileStatement.bindString(13, "1");
                            sQLiteStatementCompileStatement.executeInsert();
                        }
                    }
                } catch (Exception unused) {
                    if (sQLiteStatementCompileStatement != null) {
                        sQLiteStatementCompileStatement.close();
                        return;
                    }
                    return;
                } catch (Throwable th) {
                    if (sQLiteStatementCompileStatement != null) {
                        sQLiteStatementCompileStatement.close();
                    }
                    throw th;
                }
            }
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    private void setInitDispServiceData(SQLiteDatabase db2) {
        try {
            SQLiteStatement sQLiteStatementCompileStatement = db2.compileStatement(insertDispServiceListSql);
            try {
                sQLiteStatementCompileStatement.bindString(1, "0");
                sQLiteStatementCompileStatement.bindString(2, "0");
                sQLiteStatementCompileStatement.executeInsert();
                if (sQLiteStatementCompileStatement != null) {
                    sQLiteStatementCompileStatement.close();
                }
            } finally {
            }
        } catch (Exception unused) {
        }
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
