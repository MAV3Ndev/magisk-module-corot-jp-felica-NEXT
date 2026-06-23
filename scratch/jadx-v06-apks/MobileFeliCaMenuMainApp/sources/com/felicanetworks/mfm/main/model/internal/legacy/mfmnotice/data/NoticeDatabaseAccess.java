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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: loaded from: classes.dex */
public class NoticeDatabaseAccess {
    private static final String COLUMN_SERVICEID = "ServiceID";
    private static final String INITIAL_NOTIFICATION_ID = "0";
    private static final String INITIAL_NOTIFICATION_ID1 = "1";
    private static final String INSERT_DISPSERVICELIST_TABLE = "INSERT INTO DispServiceList VALUES ( ?, ? )";
    private static final int MAX_STORAGE_NUM = 40;
    private static final String NOTICE_DATABASE_NAME = "mfm_push.db";
    private static final String SELECT_FROMDISPSERVICELIST_SQL = " SELECT ServiceID FROM DispServiceList WHERE SEAreaIdentifiedResult = ? OR SEAreaIdentifiedResult = ?";
    private static final String TABLE_DISPSERVICELIST = "DispServiceList";
    private static final String TABLE_NOTICELIST = "NoticeList";
    private static final String insertNoticeListSql = "INSERT INTO NoticeList VALUES ( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String selectNoticeItemSql = "SELECT NotificationID, PatternID, MessageType, SendDate, ExpirationDate, Title, Message, IconURL, IconName, ImageName, Scheme, Status, ButtonLabel, DetailAccess FROM NoticeList WHERE NotificationID = ?";
    private static final String selectNoticeListSql = "SELECT NotificationID, PatternID, MessageType, SendDate, ExpirationDate, Title, Message, IconURL, IconName, ImageName, Scheme, Status, ButtonLabel, DetailAccess FROM NoticeList WHERE ExpirationDate >= ? ORDER BY SendDate DESC, NotificationID DESC";
    private static final String unreadCountSql = "SELECT NotificationID FROM NoticeList WHERE Status = '0' AND ExpirationDate >= ?";
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
        private static final String COLUMN_PATTERNID = "PatternID";
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
            try {
                if (this.db != null && this.db.isOpen()) {
                    this.db.close();
                }
            } catch (Exception e) {
                LogUtil.warning(e);
            }
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

    /* JADX WARN: Removed duplicated region for block: B:23:0x00ad  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public java.util.List<com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData> getNoticeDataList() throws java.lang.Throwable {
        /*
            r24 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r1 = 0
            java.util.Date r2 = new java.util.Date     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L98
            r2.<init>()     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L98
            com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter r3 = new com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util.DateFormatter     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L98
            java.lang.String r4 = "yyyyMMddHHmm"
            java.lang.String r5 = "Asia/Tokyo"
            r3.<init>(r4, r5)     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L98
            java.lang.String r2 = r3.format(r2)     // Catch: java.lang.Throwable -> L94 java.lang.Exception -> L98
            java.lang.String r3 = "SELECT NotificationID, PatternID, MessageType, SendDate, ExpirationDate, Title, Message, IconURL, IconName, ImageName, Scheme, Status, ButtonLabel, DetailAccess FROM NoticeList WHERE ExpirationDate >= ? ORDER BY SendDate DESC, NotificationID DESC"
            r4 = r24
            android.database.sqlite.SQLiteDatabase r5 = r4.db     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r6 = 1
            java.lang.String[] r7 = new java.lang.String[r6]     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r8 = 0
            r7[r8] = r2     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            android.database.Cursor r1 = r5.rawQuery(r3, r7)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r1.moveToFirst()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r2 = 0
        L2d:
            int r3 = r1.getCount()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            if (r2 >= r3) goto L8c
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData r3 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            java.lang.String r10 = r1.getString(r8)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            java.lang.String r11 = r1.getString(r6)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 2
            java.lang.String r12 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 3
            java.lang.String r13 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 4
            java.lang.String r14 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 5
            java.lang.String r15 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 6
            java.lang.String r16 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 7
            java.lang.String r17 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 8
            java.lang.String r18 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 9
            java.lang.String r19 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 10
            java.lang.String r20 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 11
            java.lang.String r21 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 12
            java.lang.String r22 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r5 = 13
            java.lang.String r23 = r1.getString(r5)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r9 = r3
            r9.<init>(r10, r11, r12, r13, r14, r15, r16, r17, r18, r19, r20, r21, r22, r23)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r0.add(r3)     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            r1.moveToNext()     // Catch: java.lang.Exception -> L92 java.lang.Throwable -> Laa
            int r2 = r2 + 1
            goto L2d
        L8c:
            if (r1 == 0) goto L91
            r1.close()
        L91:
            return r0
        L92:
            r0 = move-exception
            goto L9b
        L94:
            r0 = move-exception
            r4 = r24
            goto Lab
        L98:
            r0 = move-exception
            r4 = r24
        L9b:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r0)     // Catch: java.lang.Throwable -> Laa
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException r2 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException     // Catch: java.lang.Throwable -> Laa
            java.lang.Class r3 = r24.getClass()     // Catch: java.lang.Throwable -> Laa
            r5 = 1025(0x401, float:1.436E-42)
            r2.<init>(r3, r5, r0)     // Catch: java.lang.Throwable -> Laa
            throw r2     // Catch: java.lang.Throwable -> Laa
        Laa:
            r0 = move-exception
        Lab:
            if (r1 == 0) goto Lb0
            r1.close()
        Lb0:
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess.getNoticeDataList():java.util.List");
    }

    /* JADX WARN: Unreachable blocks removed: 2, instructions: 2 */
    public NoticeData getNoticeItem(String str) throws Throwable {
        Cursor cursorRawQuery;
        Cursor cursor = null;
        NoticeData noticeData = null;
        cursor = null;
        try {
            try {
                cursorRawQuery = this.db.rawQuery(selectNoticeItemSql, new String[]{str});
            } catch (Exception e) {
                e = e;
            }
        } catch (Throwable th) {
            th = th;
        }
        try {
            cursorRawQuery.moveToFirst();
            if (cursorRawQuery.getCount() > 0) {
                noticeData = new NoticeData(cursorRawQuery.getString(0), cursorRawQuery.getString(1), cursorRawQuery.getString(2), cursorRawQuery.getString(3), cursorRawQuery.getString(4), cursorRawQuery.getString(5), cursorRawQuery.getString(6), cursorRawQuery.getString(7), cursorRawQuery.getString(8), cursorRawQuery.getString(9), cursorRawQuery.getString(10), cursorRawQuery.getString(11), cursorRawQuery.getString(12), cursorRawQuery.getString(13));
                cursorRawQuery.moveToNext();
            }
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
            return noticeData;
        } catch (Exception e2) {
            e = e2;
            cursor = cursorRawQuery;
            LogUtil.warning(e);
            throw new DatabaseAccessException(getClass(), 1281, e);
        } catch (Throwable th2) {
            th = th2;
            cursor = cursorRawQuery;
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:73:0x01de  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01e8  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean updateNoticeList(com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData r21) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 492
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess.updateNoticeList(com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData):boolean");
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

    public void setReadStatus(String str) throws DatabaseAccessException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put(NotificationCompat.CATEGORY_STATUS, "1");
            this.db.update(TABLE_NOTICELIST, contentValues, "NotificationID = ?", new String[]{str});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseAccessException(getClass(), 1793, e);
        }
    }

    public int getUnreadCount() throws DatabaseAccessException {
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = this.db.rawQuery(unreadCountSql, new String[]{new DateFormatter(DateFormatter.DATE_MINUTE, "Asia/Tokyo").format(new Date())});
                cursorRawQuery.moveToFirst();
                return cursorRawQuery.getCount();
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseAccessException(getClass(), 2049, e);
            }
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }

    public void setDetailAccess(String str) throws DatabaseAccessException {
        try {
            ContentValues contentValues = new ContentValues();
            contentValues.put("DetailAccess", "1");
            this.db.update(TABLE_NOTICELIST, contentValues, "NotificationID = ?", new String[]{str});
        } catch (Exception e) {
            LogUtil.warning(e);
            throw new DatabaseAccessException(getClass(), 2305, e);
        }
    }

    public static void clearInstance() {
        NoticeDatabaseHelper.clearInstance();
    }

    public void updateDispServiceItem(Map<String, MyServiceInfo.RegisterState> map) throws DatabaseAccessException {
        SQLiteStatement sQLiteStatementCompileStatement = null;
        try {
            try {
                cleanRegisteredServiceItem();
                sQLiteStatementCompileStatement = this.db.compileStatement(INSERT_DISPSERVICELIST_TABLE);
                for (String str : map.keySet()) {
                    sQLiteStatementCompileStatement.bindString(1, str);
                    sQLiteStatementCompileStatement.bindString(2, String.valueOf(((MyServiceInfo.RegisterState) Objects.requireNonNull(map.get(str))).ordinal()));
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

    public void deleteRegisteredServiceItem(List<String> list) {
        String where = StatementUtils.getWhere(COLUMN_SERVICEID, list);
        if (where == null) {
            return;
        }
        this.db.delete(TABLE_DISPSERVICELIST, where, (String[]) list.toArray(new String[0]));
    }

    public List<String> getDispServiceItem(String[] strArr) throws DatabaseAccessException {
        ArrayList arrayList = new ArrayList();
        Cursor cursorRawQuery = null;
        try {
            try {
                cursorRawQuery = this.db.rawQuery(SELECT_FROMDISPSERVICELIST_SQL, strArr);
                cursorRawQuery.moveToFirst();
                int count = cursorRawQuery.getCount();
                for (int i = 0; i < count; i++) {
                    arrayList.add(cursorRawQuery.getString(0));
                    cursorRawQuery.moveToNext();
                }
                return arrayList;
            } catch (Exception e) {
                LogUtil.warning(e);
                throw new DatabaseAccessException(getClass(), 272, e);
            }
        } finally {
            if (cursorRawQuery != null) {
                cursorRawQuery.close();
            }
        }
    }
}
