package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.provider.BaseColumns;
import androidx.core.app.NotificationCompat;
import androidx.core.view.InputDeviceCompat;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter;
import com.felicanetworks.mfm.main.policy.helper.db.table.StatementUtils;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import com.felicanetworks.mfm.main.presenter.internal.notification.NotificationController;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeDatabaseAccess {
    private static final String COLUMN_SERVICEID = "ServiceID";
    private static final String INITIAL_NOTIFICATION_ID = "0";
    private static final String INITIAL_NOTIFICATION_ID1 = "1";
    private static final String INSERT_DISPSERVICELIST_TABLE = "INSERT INTO DispServiceList VALUES ( ?, ? )";
    private static final int MAX_STORAGE_NUM = 40;
    private static final String NOTICE_DATABASE_NAME = "mfm_push.db";
    private static final String TABLE_DISPSERVICELIST = "DispServiceList";
    private static final String TABLE_NOTICELIST = "NoticeList";
    private static final String insertNoticeListSql = "INSERT INTO NoticeList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private SQLiteDatabase db;

    private static final class TableColumns implements BaseColumns {
        private static final String COLUMN_BUTTON_LABEL = "ButtonLabel";
        private static final String COLUMN_DETAIL_ACCESS = "DetailAccess";
        private static final String COLUMN_EXPIRATIONDATE = "ExpirationDate";
        private static final String COLUMN_ICONNAME = "IconName";
        private static final String COLUMN_ICONURL = "IconURL";
        private static final String COLUMN_IMAGENAME = "ImageName";
        private static final String COLUMN_MESSAGE = "Message";
        private static final String COLUMN_MESSAGETYPE = "MessageType";
        private static final String COLUMN_NOTIFICATIONID = "NotificationID";
        private static final String COLUMN_SCHEME = "Scheme";
        private static final String COLUMN_SENDDATE = "SendDate";
        private static final String COLUMN_STATUS = "Status";
        private static final String COLUMN_TITLE = "Title";

        private TableColumns() {
        }
    }

    public NoticeDatabaseAccess(Context context) throws DatabaseAccessException {
        this.db = null;
        try {
            this.db = NoticeDatabaseHelper.getInstance(context).getWritableDatabase();
        } catch (Exception e) {
            LogUtil.warning(e);
            SQLiteDatabase sQLiteDatabase = this.db;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                this.db.close();
            }
            throw new DatabaseAccessException(getClass(), 257, e, 0);
        }
    }

    public void close() {
        try {
            SQLiteDatabase sQLiteDatabase = this.db;
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                this.db.close();
            }
        } catch (Exception e) {
            LogUtil.warning(e);
        } finally {
            this.db = null;
        }
    }

    public void beginTransaction() {
        try {
            this.db.beginTransaction();
        } catch (Exception e) {
            LogUtil.warning(e);
        }
    }

    public void setTransactionSuccessful() throws DatabaseAccessException {
        try {
            this.db.setTransactionSuccessful();
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseAccessException(getClass(), InputDeviceCompat.SOURCE_DPAD, e);
        }
    }

    public void endTransaction() throws DatabaseAccessException {
        try {
            this.db.endTransaction();
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseAccessException(getClass(), 769, e);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00cf  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public List<NoticeData> getNoticeDataList() throws Throwable {
        NoticeDatabaseAccess noticeDatabaseAccess;
        ArrayList arrayList = new ArrayList();
        Cursor cursor = null;
        try {
            try {
                noticeDatabaseAccess = this;
                try {
                    Cursor cursorQuery = noticeDatabaseAccess.db.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY, "MessageType", "SendDate", "ExpirationDate", "Title", "Message", "IconURL", "IconName", "ImageName", "Scheme", "Status", "ButtonLabel", "DetailAccess"}, "ExpirationDate >= ?", new String[]{new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo").format(new Date())}, null, null, "SendDate DESC, NotificationID DESC", null);
                    cursorQuery.moveToFirst();
                    for (int i = 0; i < cursorQuery.getCount(); i++) {
                        arrayList.add(new NoticeData(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3), cursorQuery.getString(4), cursorQuery.getString(5), cursorQuery.getString(6), cursorQuery.getString(7), cursorQuery.getString(8), cursorQuery.getString(9), cursorQuery.getString(10), cursorQuery.getString(11), cursorQuery.getString(12)));
                        cursorQuery.moveToNext();
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return arrayList;
                } catch (Exception e) {
                    e = e;
                    LogUtil.warning(e);
                    throw new DatabaseAccessException(noticeDatabaseAccess.getClass(), InputDeviceCompat.SOURCE_GAMEPAD, e);
                }
            } catch (Throwable th) {
                th = th;
                if (0 != 0) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            noticeDatabaseAccess = this;
        } catch (Throwable th2) {
            th = th2;
            if (0 != 0) {
            }
            throw th;
        }
    }

    public NoticeData getNoticeItem(String str) throws Throwable {
        NoticeDatabaseAccess noticeDatabaseAccess;
        Cursor cursorQuery;
        Cursor cursor = null;
        NoticeData noticeData = null;
        cursor = null;
        try {
            try {
                noticeDatabaseAccess = this;
                try {
                    cursorQuery = noticeDatabaseAccess.db.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY, "MessageType", "SendDate", "ExpirationDate", "Title", "Message", "IconURL", "IconName", "ImageName", "Scheme", "Status", "ButtonLabel", "DetailAccess"}, "NotificationID = ?", new String[]{str}, null, null, null, null);
                } catch (Exception e) {
                    e = e;
                }
            } catch (Throwable th) {
                th = th;
            }
            try {
                cursorQuery.moveToFirst();
                if (cursorQuery.getCount() > 0) {
                    NoticeData noticeData2 = new NoticeData(cursorQuery.getString(0), cursorQuery.getString(1), cursorQuery.getString(2), cursorQuery.getString(3), cursorQuery.getString(4), cursorQuery.getString(5), cursorQuery.getString(6), cursorQuery.getString(7), cursorQuery.getString(8), cursorQuery.getString(9), cursorQuery.getString(10), cursorQuery.getString(11), cursorQuery.getString(12));
                    cursorQuery.moveToNext();
                    noticeData = noticeData2;
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return noticeData;
            } catch (Exception e2) {
                e = e2;
                LogUtil.warning(e);
                throw new DatabaseAccessException(noticeDatabaseAccess.getClass(), 1281, e);
            } catch (Throwable th2) {
                th = th2;
                cursor = cursorQuery;
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e3) {
            e = e3;
            noticeDatabaseAccess = this;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 0 */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01f4  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01f9  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01fe  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean updateNoticeList(NoticeData noticeData) throws Throwable {
        SQLiteStatement sQLiteStatement;
        Cursor cursorQuery;
        SQLiteStatement sQLiteStatement2;
        Cursor cursor = null;
        Cursor cursor2 = null;
        sQLiteStatementCompileStatement = null;
        SQLiteStatement sQLiteStatementCompileStatement = null;
        cursor = null;
        try {
            this.db.delete(TABLE_NOTICELIST, "NotificationID <> 0 AND NotificationID <> 1 AND ExpirationDate < " + new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo").format(new Date()), null);
            Cursor cursorQuery2 = this.db.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY, "MessageType", "SendDate", "ExpirationDate", "Title", "Message", "IconURL", "IconName", "ImageName", "Scheme", "Status", "ButtonLabel", "DetailAccess"}, "NotificationID = ?", new String[]{noticeData.noticeId}, null, null, null, null);
            try {
                cursorQuery2.moveToFirst();
                boolean z = false;
                if (cursorQuery2.getCount() > 0) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("MessageType", noticeData.messageType != null ? noticeData.messageType : "2");
                    contentValues.put("SendDate", noticeData.sendDate);
                    contentValues.put("ExpirationDate", noticeData.expirationDate);
                    contentValues.put("Title", noticeData.title);
                    contentValues.put("Message", noticeData.message);
                    contentValues.put("IconURL", noticeData.iconUrl != null ? noticeData.iconUrl : "");
                    contentValues.put("IconName", noticeData.iconName != null ? noticeData.iconName : "");
                    contentValues.put("ImageName", noticeData.imageName != null ? noticeData.imageName : "");
                    contentValues.put("Scheme", noticeData.scheme);
                    contentValues.put("Status", cursorQuery2.getString(10));
                    contentValues.put("ButtonLabel", noticeData.buttonLable);
                    contentValues.put("DetailAccess", cursorQuery2.getString(12));
                    this.db.update(TABLE_NOTICELIST, contentValues, "NotificationID = ?", new String[]{noticeData.noticeId});
                    sQLiteStatement2 = null;
                } else {
                    cursorQuery = this.db.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY}, "NotificationID <> ? AND NotificationID<> ? ", new String[]{"0", "1"}, null, null, " SendDate, NotificationID ", null);
                    try {
                        cursorQuery.moveToFirst();
                        if (40 <= cursorQuery.getCount()) {
                            this.db.delete(TABLE_NOTICELIST, "NotificationID = '" + cursorQuery.getString(0) + "'", null);
                        }
                        sQLiteStatementCompileStatement = this.db.compileStatement(insertNoticeListSql);
                        z = true;
                        sQLiteStatementCompileStatement.bindString(1, noticeData.noticeId);
                        sQLiteStatementCompileStatement.bindString(2, noticeData.messageType != null ? noticeData.messageType : "2");
                        sQLiteStatementCompileStatement.bindString(3, noticeData.sendDate);
                        sQLiteStatementCompileStatement.bindString(4, noticeData.expirationDate);
                        sQLiteStatementCompileStatement.bindString(5, noticeData.title);
                        sQLiteStatementCompileStatement.bindString(6, noticeData.message);
                        sQLiteStatementCompileStatement.bindString(7, noticeData.iconUrl != null ? noticeData.iconUrl : "");
                        sQLiteStatementCompileStatement.bindString(8, noticeData.iconName != null ? noticeData.iconName : "");
                        sQLiteStatementCompileStatement.bindString(9, noticeData.imageName != null ? noticeData.imageName : "");
                        sQLiteStatementCompileStatement.bindString(10, noticeData.scheme);
                        sQLiteStatementCompileStatement.bindString(11, noticeData.status);
                        if (noticeData.buttonLable != null) {
                            sQLiteStatementCompileStatement.bindString(12, noticeData.buttonLable);
                        } else {
                            sQLiteStatementCompileStatement.bindNull(12);
                        }
                        sQLiteStatementCompileStatement.bindString(13, noticeData.detailAccess);
                        sQLiteStatementCompileStatement.executeInsert();
                        sQLiteStatement2 = sQLiteStatementCompileStatement;
                        cursor2 = cursorQuery;
                    } catch (Exception e) {
                        e = e;
                        sQLiteStatement = sQLiteStatementCompileStatement;
                        cursor = cursorQuery2;
                        try {
                            LogUtil.warning(e);
                            throw new DatabaseAccessException(getClass(), 1537, e);
                        } catch (Throwable th) {
                            th = th;
                            if (cursor != null) {
                                cursor.close();
                            }
                            if (cursorQuery != null) {
                                cursorQuery.close();
                            }
                            if (sQLiteStatement != null) {
                                sQLiteStatement.close();
                            }
                            throw th;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        sQLiteStatement = sQLiteStatementCompileStatement;
                        cursor = cursorQuery2;
                        if (cursor != null) {
                        }
                        if (cursorQuery != null) {
                        }
                        if (sQLiteStatement != null) {
                        }
                        throw th;
                    }
                }
                if (cursorQuery2 != null) {
                    cursorQuery2.close();
                }
                if (cursor2 != null) {
                    cursor2.close();
                }
                if (sQLiteStatement2 != null) {
                    sQLiteStatement2.close();
                }
                return z;
            } catch (Exception e2) {
                e = e2;
                sQLiteStatement = null;
                cursorQuery = null;
            } catch (Throwable th3) {
                th = th3;
                sQLiteStatement = null;
                cursorQuery = null;
            }
        } catch (Exception e3) {
            e = e3;
            sQLiteStatement = null;
            cursorQuery = null;
        } catch (Throwable th4) {
            th = th4;
            sQLiteStatement = null;
            cursorQuery = null;
        }
    }

    public static void deleteNoticeDb(Context context) throws DatabaseAccessException {
        for (String str : context.databaseList()) {
            if (str.equals(NOTICE_DATABASE_NAME)) {
                context.deleteDatabase(NOTICE_DATABASE_NAME);
                clearInstance();
                return;
            }
        }
    }

    public void setReadStatus(String nid) throws DatabaseAccessException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotificationCompat.CATEGORY_STATUS, "1");
            this.db.update(TABLE_NOTICELIST, contentValues, "NotificationID = ?", new String[]{nid});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseAccessException(getClass(), 1793, e);
        }
    }

    public int getUnreadCount() throws DatabaseAccessException {
        Cursor cursorQuery = null;
        try {
            try {
                String str = new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo").format(new Date());
                cursorQuery = this.db.query(TABLE_NOTICELIST, new String[]{NotificationController.NOTIFICATION_ID_KEY}, "Status = '0' AND ExpirationDate >= ?", new String[]{str}, null, null, null, null);
                cursorQuery.moveToFirst();
                return cursorQuery.getCount();
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseAccessException(getClass(), 2049, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    public void setDetailAccess(String nid) throws DatabaseAccessException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("DetailAccess", "1");
            this.db.update(TABLE_NOTICELIST, contentValues, "NotificationID = ?", new String[]{nid});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseAccessException(getClass(), 2305, e);
        }
    }

    public static void clearInstance() {
        NoticeDatabaseHelper.clearInstance();
    }

    public void updateDispServiceItem(Map<String, MyServiceInfo.RegisterState> registerStateMap) throws DatabaseAccessException {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanRegisteredServiceItem();
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_DISPSERVICELIST_TABLE);
                for (String str : registerStateMap.keySet()) {
                    sQLiteStatementCompileStatement.bindString(1, str);
                    sQLiteStatementCompileStatement.bindString(2, String.valueOf(((MyServiceInfo.RegisterState) Objects.requireNonNull(registerStateMap.get(str))).ordinal()));
                    sQLiteStatementCompileStatement.executeInsert();
                }
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseAccessException(getClass(), 145, e);
            }
        } finally {
            if (sQLiteStatementCompileStatement != null) {
                sQLiteStatementCompileStatement.close();
            }
        }
    }

    private void cleanRegisteredServiceItem() {
        this.db.delete(TABLE_DISPSERVICELIST, null, null);
    }

    public void deleteRegisteredServiceItem(List<String> serviceIds) {
        String where = StatementUtils.getWhere(COLUMN_SERVICEID, serviceIds);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_DISPSERVICELIST, where, (String[]) serviceIds.toArray(new String[0]));
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    public List<String> getDispServiceItem(String[] identifiedResult) throws DatabaseAccessException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_DISPSERVICELIST, new String[]{COLUMN_SERVICEID}, "SEAreaIdentifiedResult = ? OR SEAreaIdentifiedResult = ?", identifiedResult, null, null, null, null);
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(cursorQuery.getString(0));
                    cursorQuery.moveToNext();
                }
                if (cursorQuery != null) {
                    cursorQuery.close();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseAccessException(getClass(), 272, e);
            }
        } finally {
        }
    }

    public Map<String, MyServiceInfo.RegisterState> getDispServiceMap() throws DatabaseAccessException {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = this.db.query(TABLE_DISPSERVICELIST, new String[]{COLUMN_SERVICEID, "SEAreaIdentifiedResult"}, null, null, null, null, null, null);
                cursorQuery.moveToFirst();
                int count = cursorQuery.getCount();
                MyServiceInfo.RegisterState[] registerStateArrValues = MyServiceInfo.RegisterState.values();
                for (int i = 0; i < count; i++) {
                    linkedHashMap.put(cursorQuery.getString(0), registerStateArrValues[Integer.parseInt(cursorQuery.getString(1))]);
                    cursorQuery.moveToNext();
                }
                return linkedHashMap;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseAccessException(getClass(), 273, e);
            }
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }
}
