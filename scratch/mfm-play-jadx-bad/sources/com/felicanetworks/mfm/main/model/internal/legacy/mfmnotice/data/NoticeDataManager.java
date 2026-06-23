package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data;

import android.content.Context;
import android.content.SharedPreferences;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult;
import com.felicanetworks.mfm.main.policy.log.LogUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeDataManager {
    private static final String ACCEPT_DEFAULT = "0";
    private static final String API_LEVEL_DEFAULT = "0";
    private static final String APP_VERSION_DEFAULT = "0";
    public static final String DEVICE_TOKEN_DEFAULT = "0";
    private static final String DISTRIBUTION_GROUP_DEFAULT = "0";
    private static final String ISSUER_DEFAULT = "0";
    private static final String PREFERENCE_DATA_KEY_ACCEPT = "Accept";
    private static final String PREFERENCE_DATA_KEY_API_LEVEL = "ApiLevel";
    private static final String PREFERENCE_DATA_KEY_APP_VERSION = "AppVersion";
    private static final String PREFERENCE_DATA_KEY_CLIENT_ID = "ClientID";
    private static final String PREFERENCE_DATA_KEY_CLIENT_KEY = "ClientKey";
    private static final String PREFERENCE_DATA_KEY_DEVICE_TOKEN = "DeviceToken";
    private static final String PREFERENCE_DATA_KEY_DISTRIBUTION_GROUP = "DistributionGroup";
    private static final String PREFERENCE_DATA_KEY_ISSUER = "Issuer";
    private static final String PREFERENCE_DATA_KEY_PREFERENCE_VERSION = "PreferenceVersion";
    private static final String PREFERENCE_DATA_KEY_PUSH_INFO_SENDING = "PushInfoSending";
    private static final String PREFERENCE_DATA_KEY_PUSH_RECEIVE_STATUS = "PushReceiveStatus";
    private static final String PREFERENCE_DATA_KEY_UID = "Uid";
    private static final String PREFERENCE_NAME = "MfmNoticeStorage";
    public static final int PREFERENCE_VERSION_CURRENT = 1;
    public static final int PREFERENCE_VERSION_DEFAULT = 0;
    private static final int PUSH_INFO_SENDING_DEFAULT = 0;
    private static final String PUSH_RECEIVE_STATUS_DEFAULT = "Y";
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

    public boolean setupPreference() {
        try {
            SharedPreferences mgrPref = getMgrPref();
            SharedPreferences.Editor editorEdit = mgrPref.edit();
            int i = mgrPref.getInt(PREFERENCE_DATA_KEY_PREFERENCE_VERSION, 0);
            if (i == 1) {
                return true;
            }
            if (i == 0) {
                editorEdit.remove(PREFERENCE_DATA_KEY_CLIENT_ID);
                editorEdit.remove(PREFERENCE_DATA_KEY_CLIENT_KEY);
                editorEdit.remove(PREFERENCE_DATA_KEY_UID);
                editorEdit.putString(PREFERENCE_DATA_KEY_DEVICE_TOKEN, "0");
                editorEdit.putString(PREFERENCE_DATA_KEY_APP_VERSION, "0");
                editorEdit.putString("Accept", "0");
                editorEdit.putString(PREFERENCE_DATA_KEY_API_LEVEL, "0");
                editorEdit.putString(PREFERENCE_DATA_KEY_ISSUER, "0");
                editorEdit.putString(PREFERENCE_DATA_KEY_DISTRIBUTION_GROUP, "0");
            }
            editorEdit.putInt(PREFERENCE_DATA_KEY_PREFERENCE_VERSION, 1);
            return editorEdit.commit();
        } catch (Exception e) {
            LogUtil.warning(e);
            return false;
        }
    }

    public void closeDatabase() {
        NoticeDatabaseAccess.clearInstance();
    }

    public List<NoticeData> getNoticeDataList() throws DatabaseAccessException {
        ArrayList arrayList = new ArrayList();
        try {
            try {
                arrayList.addAll(new NoticeDatabaseAccess(this._context).getNoticeDataList());
                return arrayList;
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            arrayList.addAll(new NoticeDatabaseAccess(this._context).getNoticeDataList());
            return arrayList;
        }
    }

    public NoticeData getNoticeData(String nid) throws DatabaseAccessException {
        try {
            try {
                return new NoticeDatabaseAccess(this._context).getNoticeItem(nid);
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            return new NoticeDatabaseAccess(this._context).getNoticeItem(nid);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:47:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0074  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NoticeResult setNoticeData(NoticeData data) throws Throwable {
        NoticeDatabaseAccess noticeDatabaseAccess;
        NoticeResult noticeResult = NoticeResult.ERROR_BAD_PARAM;
        if (data == null || data.noticeId == null || data.sendDate == null || data.expirationDate == null || data.title == null || data.message == null || data.scheme == null) {
            return noticeResult;
        }
        NoticeDatabaseAccess noticeDatabaseAccess2 = new NoticeDatabaseAccess(this._context);
        noticeDatabaseAccess2.beginTransaction();
        NoticeDatabaseAccess noticeDatabaseAccess3 = null;
        try {
            try {
                NoticeResult noticeResult2 = noticeDatabaseAccess2.updateNoticeList(data) ? NoticeResult.SET_NOTICE_NEW : NoticeResult.SET_NOTICE_UPDATE;
                noticeDatabaseAccess2.setTransactionSuccessful();
                noticeDatabaseAccess2.endTransaction();
                return noticeResult2;
            } catch (DatabaseAccessException unused) {
                noticeDatabaseAccess2.endTransaction();
                try {
                    initializeDB();
                    noticeDatabaseAccess = new NoticeDatabaseAccess(this._context);
                    try {
                        try {
                            noticeDatabaseAccess.beginTransaction();
                            NoticeResult noticeResult3 = noticeDatabaseAccess.updateNoticeList(data) ? NoticeResult.SET_NOTICE_NEW : NoticeResult.SET_NOTICE_UPDATE;
                            noticeDatabaseAccess.setTransactionSuccessful();
                            noticeDatabaseAccess.endTransaction();
                            return noticeResult3;
                        } catch (DatabaseAccessException e) {
                            e = e;
                            LogUtil.warning(e);
                            throw e;
                        }
                    } catch (Throwable th) {
                        th = th;
                        noticeDatabaseAccess3 = noticeDatabaseAccess;
                        noticeDatabaseAccess2 = null;
                        if (noticeDatabaseAccess2 != null) {
                            noticeDatabaseAccess2.endTransaction();
                        }
                        if (noticeDatabaseAccess3 != null) {
                            noticeDatabaseAccess3.endTransaction();
                        }
                        throw th;
                    }
                } catch (DatabaseAccessException e2) {
                    e = e2;
                    noticeDatabaseAccess = null;
                } catch (Throwable th2) {
                    th = th2;
                    noticeDatabaseAccess2 = null;
                    if (noticeDatabaseAccess2 != null) {
                    }
                    if (noticeDatabaseAccess3 != null) {
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            if (noticeDatabaseAccess2 != null) {
            }
            if (noticeDatabaseAccess3 != null) {
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setReadStatus(String nid) throws Throwable {
        NoticeDatabaseAccess noticeDatabaseAccess;
        NoticeDatabaseAccess noticeDatabaseAccess2 = new NoticeDatabaseAccess(this._context);
        noticeDatabaseAccess2.beginTransaction();
        NoticeDatabaseAccess noticeDatabaseAccess3 = null;
        try {
            try {
                noticeDatabaseAccess2.setReadStatus(nid);
                noticeDatabaseAccess2.setTransactionSuccessful();
                noticeDatabaseAccess2.endTransaction();
            } catch (DatabaseAccessException unused) {
                noticeDatabaseAccess2.endTransaction();
                try {
                    initializeDB();
                    noticeDatabaseAccess = new NoticeDatabaseAccess(this._context);
                    try {
                        try {
                            noticeDatabaseAccess.beginTransaction();
                            noticeDatabaseAccess.setReadStatus(nid);
                            noticeDatabaseAccess.setTransactionSuccessful();
                            noticeDatabaseAccess.endTransaction();
                        } catch (DatabaseAccessException e) {
                            e = e;
                            LogUtil.warning(e);
                            throw e;
                        }
                    } catch (Throwable th) {
                        th = th;
                        noticeDatabaseAccess3 = noticeDatabaseAccess;
                        noticeDatabaseAccess2 = null;
                        if (noticeDatabaseAccess2 != null) {
                        }
                        if (noticeDatabaseAccess3 != null) {
                        }
                        throw th;
                    }
                } catch (DatabaseAccessException e2) {
                    e = e2;
                    noticeDatabaseAccess = null;
                } catch (Throwable th2) {
                    th = th2;
                    noticeDatabaseAccess2 = null;
                    if (noticeDatabaseAccess2 != null) {
                    }
                    if (noticeDatabaseAccess3 != null) {
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            if (noticeDatabaseAccess2 != null) {
                noticeDatabaseAccess2.endTransaction();
            }
            if (noticeDatabaseAccess3 != null) {
                noticeDatabaseAccess3.endTransaction();
            }
            throw th;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x0042  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0047  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void setDetailAccess(String nid) throws Throwable {
        NoticeDatabaseAccess noticeDatabaseAccess;
        NoticeDatabaseAccess noticeDatabaseAccess2 = new NoticeDatabaseAccess(this._context);
        noticeDatabaseAccess2.beginTransaction();
        NoticeDatabaseAccess noticeDatabaseAccess3 = null;
        try {
            try {
                noticeDatabaseAccess2.setDetailAccess(nid);
                noticeDatabaseAccess2.setTransactionSuccessful();
                noticeDatabaseAccess2.endTransaction();
            } catch (DatabaseAccessException unused) {
                noticeDatabaseAccess2.endTransaction();
                try {
                    initializeDB();
                    noticeDatabaseAccess = new NoticeDatabaseAccess(this._context);
                    try {
                        try {
                            noticeDatabaseAccess.beginTransaction();
                            noticeDatabaseAccess.setReadStatus(nid);
                            noticeDatabaseAccess.setTransactionSuccessful();
                            noticeDatabaseAccess.endTransaction();
                        } catch (DatabaseAccessException e) {
                            e = e;
                            LogUtil.warning(e);
                            throw e;
                        }
                    } catch (Throwable th) {
                        th = th;
                        noticeDatabaseAccess3 = noticeDatabaseAccess;
                        noticeDatabaseAccess2 = null;
                        if (noticeDatabaseAccess2 != null) {
                        }
                        if (noticeDatabaseAccess3 != null) {
                        }
                        throw th;
                    }
                } catch (DatabaseAccessException e2) {
                    e = e2;
                    noticeDatabaseAccess = null;
                } catch (Throwable th2) {
                    th = th2;
                    noticeDatabaseAccess2 = null;
                    if (noticeDatabaseAccess2 != null) {
                    }
                    if (noticeDatabaseAccess3 != null) {
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            if (noticeDatabaseAccess2 != null) {
                noticeDatabaseAccess2.endTransaction();
            }
            if (noticeDatabaseAccess3 != null) {
                noticeDatabaseAccess3.endTransaction();
            }
            throw th;
        }
    }

    public void setDeviceToken(String dt) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        editorEdit.putString(PREFERENCE_DATA_KEY_DEVICE_TOKEN, dt);
        editorEdit.apply();
    }

    public String getDeviceToken() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_DEVICE_TOKEN, "0");
    }

    public void setTagInfo(TagInfo ti) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        if (ti.app_version != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_APP_VERSION, ti.app_version);
        }
        if (ti.accept != null) {
            editorEdit.putString("Accept", ti.accept);
        }
        if (ti.api_level != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_API_LEVEL, ti.api_level);
        }
        if (ti.issuer != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_ISSUER, ti.issuer);
        }
        if (ti.distribution_group != null) {
            editorEdit.putString(PREFERENCE_DATA_KEY_DISTRIBUTION_GROUP, ti.distribution_group);
        }
        editorEdit.apply();
    }

    public String getPreferenceDataKeyAppVersion() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_APP_VERSION, "0");
    }

    public String getPreferenceDataKeyAccept() {
        return getMgrPref().getString("Accept", "0");
    }

    public String getPreferenceDataKeyApiLevel() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_API_LEVEL, "0");
    }

    public String getPreferenceDataKeyIssuer() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_ISSUER, "0");
    }

    public String getPreferenceDataKeyDistributionGroup() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_DISTRIBUTION_GROUP, "0");
    }

    public void setPushInfoSendingMode(int mode) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        editorEdit.putInt(PREFERENCE_DATA_KEY_PUSH_INFO_SENDING, mode);
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

    public void setPushReceiveStatus(String prs) {
        SharedPreferences.Editor editorEdit = getMgrPref().edit();
        editorEdit.putString(PREFERENCE_DATA_KEY_PUSH_RECEIVE_STATUS, prs);
        editorEdit.apply();
    }

    public String getPushReceiveStatus() {
        return getMgrPref().getString(PREFERENCE_DATA_KEY_PUSH_RECEIVE_STATUS, "Y");
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0044 A[Catch: all -> 0x004d, TRY_ENTER, TryCatch #5 {, blocks: (B:3:0x0001, B:6:0x0012, B:27:0x0044, B:29:0x0049, B:30:0x004c, B:13:0x002e), top: B:39:0x0001 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0049 A[Catch: all -> 0x004d, TryCatch #5 {, blocks: (B:3:0x0001, B:6:0x0012, B:27:0x0044, B:29:0x0049, B:30:0x004c, B:13:0x002e), top: B:39:0x0001 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public synchronized void setDisplayServiceList(Map<String, MyServiceInfo.RegisterState> registerStateMap) throws DatabaseAccessException {
        NoticeDatabaseAccess noticeDatabaseAccess;
        NoticeDatabaseAccess noticeDatabaseAccess2 = new NoticeDatabaseAccess(this._context);
        noticeDatabaseAccess2.beginTransaction();
        NoticeDatabaseAccess noticeDatabaseAccess3 = null;
        try {
            try {
                noticeDatabaseAccess2.updateDispServiceItem(registerStateMap);
                noticeDatabaseAccess2.setTransactionSuccessful();
                noticeDatabaseAccess2.endTransaction();
            } catch (DatabaseAccessException unused) {
                noticeDatabaseAccess2.endTransaction();
                try {
                    initializeDB();
                    noticeDatabaseAccess = new NoticeDatabaseAccess(this._context);
                    try {
                        try {
                            noticeDatabaseAccess.beginTransaction();
                            noticeDatabaseAccess.updateDispServiceItem(registerStateMap);
                            noticeDatabaseAccess.setTransactionSuccessful();
                            noticeDatabaseAccess.endTransaction();
                        } catch (DatabaseAccessException e) {
                            e = e;
                            LogUtil.warning(e);
                            throw e;
                        }
                    } catch (Throwable th) {
                        th = th;
                        noticeDatabaseAccess3 = noticeDatabaseAccess;
                        noticeDatabaseAccess2 = null;
                        if (noticeDatabaseAccess2 != null) {
                        }
                        if (noticeDatabaseAccess3 != null) {
                        }
                        throw th;
                    }
                } catch (DatabaseAccessException e2) {
                    e = e2;
                    noticeDatabaseAccess = null;
                } catch (Throwable th2) {
                    th = th2;
                    noticeDatabaseAccess2 = null;
                    if (noticeDatabaseAccess2 != null) {
                    }
                    if (noticeDatabaseAccess3 != null) {
                    }
                    throw th;
                }
            }
        } catch (Throwable th3) {
            th = th3;
            if (noticeDatabaseAccess2 != null) {
                noticeDatabaseAccess2.endTransaction();
            }
            if (noticeDatabaseAccess3 != null) {
                noticeDatabaseAccess3.endTransaction();
            }
            throw th;
        }
    }

    public synchronized List<String> getDispService(String[] identifiedResult) throws DatabaseAccessException {
        List<String> dispServiceItem;
        try {
            try {
                dispServiceItem = new NoticeDatabaseAccess(this._context).getDispServiceItem(identifiedResult);
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            dispServiceItem = new NoticeDatabaseAccess(this._context).getDispServiceItem(identifiedResult);
        }
        return dispServiceItem;
    }

    public synchronized Map<String, MyServiceInfo.RegisterState> getDispServiceMap() throws DatabaseAccessException {
        Map<String, MyServiceInfo.RegisterState> dispServiceMap;
        try {
            try {
                dispServiceMap = new NoticeDatabaseAccess(this._context).getDispServiceMap();
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            dispServiceMap = new NoticeDatabaseAccess(this._context).getDispServiceMap();
        }
        return dispServiceMap;
    }

    public synchronized void invalidateDispService() {
        try {
            setDisplayServiceList(Collections.singletonMap("0", MyServiceInfo.RegisterState.NONE));
        } catch (DatabaseAccessException e) {
            LogUtil.warning(e);
        }
    }

    public synchronized void deleteRegisteredServiceItem(List<String> serviceIds) throws DatabaseAccessException {
        try {
            try {
                new NoticeDatabaseAccess(this._context).deleteRegisteredServiceItem(serviceIds);
            } catch (DatabaseAccessException e) {
                LogUtil.warning(e);
                throw e;
            }
        } catch (DatabaseAccessException unused) {
            initializeDB();
            new NoticeDatabaseAccess(this._context).deleteRegisteredServiceItem(serviceIds);
        }
    }
}
