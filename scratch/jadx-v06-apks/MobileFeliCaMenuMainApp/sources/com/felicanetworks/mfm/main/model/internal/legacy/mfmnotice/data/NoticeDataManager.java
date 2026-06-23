package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.policy.device.Settings;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NoticeDataManager {
    private static final String ACCEPT_DEFAULT = "0";
    private static final String API_LEVEL_DEFAULT = "0";
    private static final String APP_VERSION_DEFAULT = "0";
    private static final String CLIENT_ID_DEFAULT = "0";
    private static final String CLIENT_KEY_DEFAULT = "0";
    public static final String DEVICE_TOKEN_DEFAULT = "0";
    private static final String ISSUER_DEFAULT = "0";
    private static final String PREFERENCE_DATA_KEY_ACCEPT = "Accept";
    private static final String PREFERENCE_DATA_KEY_API_LEVEL = "ApiLevel";
    private static final String PREFERENCE_DATA_KEY_APP_VERSION = "AppVersion";
    private static final String PREFERENCE_DATA_KEY_CLIENT_ID = "ClientID";
    private static final String PREFERENCE_DATA_KEY_CLIENT_KEY = "ClientKey";
    private static final String PREFERENCE_DATA_KEY_DEVICE_TOKEN = "DeviceToken";
    private static final String PREFERENCE_DATA_KEY_ISSUER = "Issuer";
    private static final String PREFERENCE_DATA_KEY_PUSH_INFO_SENDING = "PushInfoSending";
    private static final String PREFERENCE_DATA_KEY_PUSH_RECEIVE_STATUS = "PushReceiveStatus";
    private static final String PREFERENCE_DATA_KEY_UID = "Uid";
    private static final String PREFERENCE_NAME = "MfmNoticeStorage";
    private static final int PUSH_INFO_SENDING_DEFAULT = 0;
    private static final String PUSH_RECEIVE_STATUS_DEFAULT = "Y";
    private static final String PUSH_RECEIVE_STATUS_DEFAULT_P18 = "N";
    private static final String UID_DEFAULT = "0";
    private static NoticeDataManager _self;
    private Context _context;

    private NoticeDataManager(Context context) {
        this._context = context;
    }

    private void setContext(Context context) {
        this._context = context;
    }

    public static NoticeDataManager getInstance(Context context) {
        NoticeDataManager noticeDataManager = _self;
        if (noticeDataManager == null) {
            _self = new NoticeDataManager(context);
        } else {
            noticeDataManager.setContext(context);
        }
        return _self;
    }

    private SharedPreferences getMgrPref() {
        return this._context.getSharedPreferences(PREFERENCE_NAME, 0);
    }

    public List<NoticeData> getNoticeDataList() throws DatabaseAccessException {
        ArrayList arrayList = new ArrayList();
        try {
            try {
                arrayList.addAll(new NoticeDatabaseAccess(this._context).getNoticeDataList());
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            arrayList.addAll(new NoticeDatabaseAccess(this._context).getNoticeDataList());
        }
        return arrayList;
    }

    public NoticeData getNoticeData(String str) throws DatabaseAccessException {
        try {
            try {
                return new NoticeDatabaseAccess(this._context).getNoticeItem(str);
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            return new NoticeDatabaseAccess(this._context).getNoticeItem(str);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0074  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0079  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult setNoticeData(com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData r5) throws java.lang.Throwable {
        /*
            r4 = this;
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult r0 = com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult.ERROR_BAD_PARAM
            if (r5 == 0) goto L7d
            java.lang.String r1 = r5.noticeId
            if (r1 == 0) goto L7d
            java.lang.String r1 = r5.messageType
            if (r1 == 0) goto L7d
            java.lang.String r1 = r5.sendDate
            if (r1 == 0) goto L7d
            java.lang.String r1 = r5.expirationDate
            if (r1 == 0) goto L7d
            java.lang.String r1 = r5.title
            if (r1 == 0) goto L7d
            java.lang.String r1 = r5.message
            if (r1 == 0) goto L7d
            java.lang.String r1 = r5.scheme
            if (r1 != 0) goto L21
            goto L7d
        L21:
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess
            android.content.Context r1 = r4._context
            r0.<init>(r1)
            r0.beginTransaction()
            r1 = 0
            boolean r2 = r0.updateNoticeList(r5)     // Catch: java.lang.Throwable -> L3e com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L40
            if (r2 == 0) goto L35
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult r2 = com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult.SET_NOTICE_NEW     // Catch: java.lang.Throwable -> L3e com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L40
            goto L37
        L35:
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult r2 = com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult.SET_NOTICE_UPDATE     // Catch: java.lang.Throwable -> L3e com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L40
        L37:
            r0.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L3e com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L40
            r0.endTransaction()
            goto L62
        L3e:
            r5 = move-exception
            goto L72
        L40:
            r0.endTransaction()     // Catch: java.lang.Throwable -> L3e
            r4.initializeDB()     // Catch: java.lang.Throwable -> L65 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L68
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess     // Catch: java.lang.Throwable -> L65 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L68
            android.content.Context r2 = r4._context     // Catch: java.lang.Throwable -> L65 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L68
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L65 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L68
            r0.beginTransaction()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L63 java.lang.Throwable -> L6e
            boolean r5 = r0.updateNoticeList(r5)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L63 java.lang.Throwable -> L6e
            if (r5 == 0) goto L59
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult r5 = com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult.SET_NOTICE_NEW     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L63 java.lang.Throwable -> L6e
            goto L5b
        L59:
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult r5 = com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult.SET_NOTICE_UPDATE     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L63 java.lang.Throwable -> L6e
        L5b:
            r2 = r5
            r0.setTransactionSuccessful()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L63 java.lang.Throwable -> L6e
            r0.endTransaction()
        L62:
            return r2
        L63:
            r5 = move-exception
            goto L6a
        L65:
            r5 = move-exception
            r0 = r1
            goto L72
        L68:
            r5 = move-exception
            r0 = r1
        L6a:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r5)     // Catch: java.lang.Throwable -> L6e
            throw r5     // Catch: java.lang.Throwable -> L6e
        L6e:
            r5 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
        L72:
            if (r0 == 0) goto L77
            r0.endTransaction()
        L77:
            if (r1 == 0) goto L7c
            r1.endTransaction()
        L7c:
            throw r5
        L7d:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager.setNoticeData(com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData):com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setReadStatus(java.lang.String r5) throws java.lang.Throwable {
        /*
            r4 = this;
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess
            android.content.Context r1 = r4._context
            r0.<init>(r1)
            r0.beginTransaction()
            r1 = 0
            r0.setReadStatus(r5)     // Catch: java.lang.Throwable -> L15 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L17
            r0.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L15 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L17
            r0.endTransaction()
            goto L30
        L15:
            r5 = move-exception
            goto L40
        L17:
            r0.endTransaction()     // Catch: java.lang.Throwable -> L15
            r4.initializeDB()     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            android.content.Context r2 = r4._context     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            r0.beginTransaction()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L31 java.lang.Throwable -> L3c
            r0.setReadStatus(r5)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L31 java.lang.Throwable -> L3c
            r0.setTransactionSuccessful()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L31 java.lang.Throwable -> L3c
            r0.endTransaction()
        L30:
            return
        L31:
            r5 = move-exception
            goto L38
        L33:
            r5 = move-exception
            r0 = r1
            goto L40
        L36:
            r5 = move-exception
            r0 = r1
        L38:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r5)     // Catch: java.lang.Throwable -> L3c
            throw r5     // Catch: java.lang.Throwable -> L3c
        L3c:
            r5 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
        L40:
            if (r0 == 0) goto L45
            r0.endTransaction()
        L45:
            if (r1 == 0) goto L4a
            r1.endTransaction()
        L4a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager.setReadStatus(java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void setDetailAccess(java.lang.String r5) throws java.lang.Throwable {
        /*
            r4 = this;
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess
            android.content.Context r1 = r4._context
            r0.<init>(r1)
            r0.beginTransaction()
            r1 = 0
            r0.setDetailAccess(r5)     // Catch: java.lang.Throwable -> L15 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L17
            r0.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L15 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L17
            r0.endTransaction()
            goto L30
        L15:
            r5 = move-exception
            goto L40
        L17:
            r0.endTransaction()     // Catch: java.lang.Throwable -> L15
            r4.initializeDB()     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            android.content.Context r2 = r4._context     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L33 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L36
            r0.beginTransaction()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L31 java.lang.Throwable -> L3c
            r0.setReadStatus(r5)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L31 java.lang.Throwable -> L3c
            r0.setTransactionSuccessful()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L31 java.lang.Throwable -> L3c
            r0.endTransaction()
        L30:
            return
        L31:
            r5 = move-exception
            goto L38
        L33:
            r5 = move-exception
            r0 = r1
            goto L40
        L36:
            r5 = move-exception
            r0 = r1
        L38:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r5)     // Catch: java.lang.Throwable -> L3c
            throw r5     // Catch: java.lang.Throwable -> L3c
        L3c:
            r5 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
        L40:
            if (r0 == 0) goto L45
            r0.endTransaction()
        L45:
            if (r1 == 0) goto L4a
            r1.endTransaction()
        L4a:
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager.setDetailAccess(java.lang.String):void");
    }

    public void setDeviceToken(String str) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        editorEdit.putString(PREFERENCE_DATA_KEY_DEVICE_TOKEN, str);
        editorEdit.apply();
    }

    public String getDeviceToken() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_DEVICE_TOKEN, "0");
    }

    public void setClientInfo(String str, String str2) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        if (str != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_CLIENT_ID, str);
        }
        if (str2 != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_CLIENT_KEY, str2);
        }
        editorEdit.apply();
    }

    public ClientInfo getClientInfo() {
        SharedPreferences mgrPref = getMgrPref();
        return new ClientInfo(mgrPref.getString(PREFERENCE_DATA_KEY_CLIENT_ID, "0"), mgrPref.getString(PREFERENCE_DATA_KEY_CLIENT_KEY, "0"));
    }

    public void setTagInfo(TagInfo tagInfo) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        if (tagInfo.uid != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_UID, tagInfo.uid);
        }
        if (tagInfo.app_version != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_APP_VERSION, tagInfo.app_version);
        }
        if (tagInfo.accept != null) {
            editorEdit.putString("Accept", tagInfo.accept);
        }
        if (tagInfo.api_level != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_API_LEVEL, tagInfo.api_level);
        }
        if (tagInfo.issuer != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_ISSUER, tagInfo.issuer);
        }
        editorEdit.apply();
    }

    public TagInfo getTagInfo() {
        SharedPreferences mgrPref = getMgrPref();
        return new TagInfo(mgrPref.getString(PREFERENCE_DATA_KEY_UID, "0"), mgrPref.getString(PREFERENCE_DATA_KEY_APP_VERSION, "0"), mgrPref.getString("Accept", "0"), mgrPref.getString(PREFERENCE_DATA_KEY_API_LEVEL, "0"), mgrPref.getString(PREFERENCE_DATA_KEY_ISSUER, "0"));
    }

    public void setPushInfoSendingMode(int i) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        editorEdit.putInt(PREFERENCE_DATA_KEY_PUSH_INFO_SENDING, i);
        editorEdit.apply();
    }

    public int getPushInfoSendingMode() {
        return getMgrPref().getInt(PREFERENCE_DATA_KEY_PUSH_INFO_SENDING, 0);
    }

    public void initializeDB() throws DatabaseAccessException {
        try {
            NoticeDatabaseAccess.deleteNoticeDb(this._context);
        } catch (DatabaseAccessException e) {
            LogUtil.warning(e);
            throw e;
        }
    }

    public int unreadCount() throws DatabaseAccessException {
        try {
            return new NoticeDatabaseAccess(this._context).getUnreadCount();
        } catch (DatabaseAccessException e) {
            LogUtil.warning(e);
            try {
                initializeDB();
                return new NoticeDatabaseAccess(this._context).getUnreadCount();
            } catch (DatabaseAccessException e2) {
                LogUtil.warning(e2);
                throw e2;
            }
        }
    }

    public void setPushReceiveStatus(String str) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        editorEdit.putString(PREFERENCE_DATA_KEY_PUSH_RECEIVE_STATUS, str);
        editorEdit.apply();
    }

    public String getPushReceiveStatus() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_PUSH_RECEIVE_STATUS, getDefaultPushReceiveStatus());
    }

    public String getDefaultPushReceiveStatus() {
        return Settings.DeviceType.PIXEL == Settings.getDeviceType() ? "N" : "Y";
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0044 A[Catch: all -> 0x004d, TRY_ENTER, TryCatch #5 {, blocks: (B:3:0x0001, B:6:0x0012, B:27:0x0044, B:29:0x0049, B:30:0x004c, B:13:0x002e), top: B:39:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0049 A[Catch: all -> 0x004d, TryCatch #5 {, blocks: (B:3:0x0001, B:6:0x0012, B:27:0x0044, B:29:0x0049, B:30:0x004c, B:13:0x002e), top: B:39:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void setDisplayServiceList(java.util.Map<java.lang.String, com.felicanetworks.mfm.main.model.info.MyServiceInfo.RegisterState> r5) throws com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException {
        /*
            r4 = this;
            monitor-enter(r4)
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess     // Catch: java.lang.Throwable -> L4d
            android.content.Context r1 = r4._context     // Catch: java.lang.Throwable -> L4d
            r0.<init>(r1)     // Catch: java.lang.Throwable -> L4d
            r0.beginTransaction()     // Catch: java.lang.Throwable -> L4d
            r1 = 0
            r0.updateDispServiceItem(r5)     // Catch: java.lang.Throwable -> L16 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L18
            r0.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L16 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L18
            r0.endTransaction()     // Catch: java.lang.Throwable -> L4d
            goto L31
        L16:
            r5 = move-exception
            goto L42
        L18:
            r0.endTransaction()     // Catch: java.lang.Throwable -> L16
            r4.initializeDB()     // Catch: java.lang.Throwable -> L35 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L38
            com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess r0 = new com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDatabaseAccess     // Catch: java.lang.Throwable -> L35 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L38
            android.content.Context r2 = r4._context     // Catch: java.lang.Throwable -> L35 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L38
            r0.<init>(r2)     // Catch: java.lang.Throwable -> L35 com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L38
            r0.beginTransaction()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L33 java.lang.Throwable -> L3e
            r0.updateDispServiceItem(r5)     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L33 java.lang.Throwable -> L3e
            r0.setTransactionSuccessful()     // Catch: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException -> L33 java.lang.Throwable -> L3e
            r0.endTransaction()     // Catch: java.lang.Throwable -> L4d
        L31:
            monitor-exit(r4)
            return
        L33:
            r5 = move-exception
            goto L3a
        L35:
            r5 = move-exception
            r0 = r1
            goto L42
        L38:
            r5 = move-exception
            r0 = r1
        L3a:
            com.felicanetworks.mfm.main.policy.log.LogUtil.warning(r5)     // Catch: java.lang.Throwable -> L3e
            throw r5     // Catch: java.lang.Throwable -> L3e
        L3e:
            r5 = move-exception
            r3 = r1
            r1 = r0
            r0 = r3
        L42:
            if (r0 == 0) goto L47
            r0.endTransaction()     // Catch: java.lang.Throwable -> L4d
        L47:
            if (r1 == 0) goto L4c
            r1.endTransaction()     // Catch: java.lang.Throwable -> L4d
        L4c:
            throw r5     // Catch: java.lang.Throwable -> L4d
        L4d:
            r5 = move-exception
            monitor-exit(r4)
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager.setDisplayServiceList(java.util.Map):void");
    }

    public synchronized List<String> getDispService(String[] strArr) throws DatabaseAccessException {
        List<String> dispServiceItem;
        try {
            try {
                dispServiceItem = new NoticeDatabaseAccess(this._context).getDispServiceItem(strArr);
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            dispServiceItem = new NoticeDatabaseAccess(this._context).getDispServiceItem(strArr);
        }
        return dispServiceItem;
    }

    public synchronized void invalidateDispService() {
        try {
            setDisplayServiceList(Collections.singletonMap("0", MyServiceInfo.RegisterState.NONE));
        } catch (DatabaseAccessException e) {
            LogUtil.warning(e);
        }
    }

    public synchronized void deleteRegisteredServiceItem(List<String> list) throws DatabaseAccessException {
        try {
            try {
                new NoticeDatabaseAccess(this._context).deleteRegisteredServiceItem(list);
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            new NoticeDatabaseAccess(this._context).deleteRegisteredServiceItem(list);
        }
    }
}
