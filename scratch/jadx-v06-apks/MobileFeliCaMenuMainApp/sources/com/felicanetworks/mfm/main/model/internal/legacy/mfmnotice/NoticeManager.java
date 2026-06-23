package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice;

import android.content.Context;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.TagInfo;
import com.felicanetworks.mfm.main.model.internal.main.ModelContext;
import com.felicanetworks.mfm.main.presenter.internal.ServicePreference;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class NoticeManager {
    public static final String APP_ID = "7553";
    public static final String ENVIRONMENT = "production";
    private static final String INITIAL_NOTIFICATION_ID1 = "1";
    private static final String LOCALPUSH_NOTICE_ID = "0";
    public static final int PUSHINFOSENDING_MODE_ON = 1;
    public static final String PUSH_LAUNCH_EVENT_SUFFIX = "_push_launch";
    public static final String PUSH_OPEN_EVENT_SUFFIX = "_push_open";
    private static final String PUSH_RECEIVE_STATUS_OFF = "N";
    private static final String PUSH_RECEIVE_STATUS_ON = "Y";
    public static final String SECRET_KEY = "ggdyqhxuqF8YYH6Nu2QxMe7fYcdJK29u";
    private static final String SENDER_ID = "206879382952";
    private static final String STATUS_DEFAULT = "0";
    private static final String STATUS_OFF = "N";
    private static final String STATUS_ON = "Y";
    private static final String TAG_ACCEPT_NAME = "accept";
    private static final String TAG_ACCEPT_OFF = "off";
    private static final String TAG_ACCEPT_ON = "on";
    private static NoticeManager _self;
    private PushManager _PMgr;
    private Context _context;
    private ModelContext _modelContext;

    private NoticeManager(Context context) {
        ModelContext modelContext = new ModelContext(context);
        this._modelContext = modelContext;
        this._context = context;
        this._PMgr = PushManager.getInstance(modelContext);
    }

    private void setContext(Context context) {
        this._context = context;
    }

    public static NoticeManager getInstance(Context context) {
        NoticeManager noticeManager = _self;
        if (noticeManager == null) {
            _self = new NoticeManager(context);
        } else {
            noticeManager.setContext(context);
        }
        return _self;
    }

    public NoticeResult setClient() {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            if (NoticeDataManager.getInstance(this._context).getPushInfoSendingMode() == 1) {
                new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.1
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            PushManager unused = NoticeManager.this._PMgr;
                            PushManager.getInstance(NoticeManager.this._modelContext).initialize(NoticeManager.this._context).register(NoticeManager.SENDER_ID);
                            PushManager unused2 = NoticeManager.this._PMgr;
                            PushManager.getInstance(NoticeManager.this._modelContext).setDeviceTags();
                        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException | Exception unused3) {
                        }
                    }
                }).start();
            } else {
                noticeResult = NoticeResult.ERROR_MODE;
            }
            return noticeResult;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public NoticeResult setAccept() {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this._context);
            if (noticeDataManager.getPushInfoSendingMode() == 1) {
                String str = noticeDataManager.getTagInfo().get(TAG_ACCEPT_NAME);
                String pushReceiveStatus = getPushReceiveStatus();
                if (str.equals(pushReceiveStatus)) {
                    return noticeResult;
                }
                byte b = -1;
                int iHashCode = pushReceiveStatus.hashCode();
                if (iHashCode != 78) {
                    if (iHashCode == 89 && pushReceiveStatus.equals("Y")) {
                        b = 0;
                    }
                } else if (pushReceiveStatus.equals(ServicePreference.OFF)) {
                    b = 1;
                }
                if (b == 0) {
                    new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.2
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                NoticeManager.this._PMgr.setTag(new TagInfo(null, null, NoticeManager.TAG_ACCEPT_ON, null, null));
                            } catch (Exception unused) {
                            }
                        }
                    }).start();
                    return noticeResult;
                }
                if (b != 1) {
                    return noticeResult;
                }
                new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.3
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            NoticeManager.this._PMgr.setTag(new TagInfo(null, null, NoticeManager.TAG_ACCEPT_OFF, null, null));
                        } catch (Exception unused) {
                        }
                    }
                }).start();
                return noticeResult;
            }
            return NoticeResult.ERROR_MODE;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public NoticeData getNoticeData(String str) {
        try {
            NoticeData noticeData = NoticeDataManager.getInstance(this._context).getNoticeData(str);
            if (noticeData == null) {
                return null;
            }
            return noticeData;
        } catch (Exception unused) {
            return null;
        }
    }

    public List<NoticeData> getNoticeDataList() {
        try {
            return NoticeDataManager.getInstance(this._context).getNoticeDataList();
        } catch (Exception unused) {
            return null;
        }
    }

    public int unreadCount() {
        try {
            return NoticeDataManager.getInstance(this._context).unreadCount();
        } catch (Exception unused) {
            return 0;
        }
    }

    public NoticeResult setReadStatus(final String str) throws Throwable {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this._context);
            noticeDataManager.setReadStatus(str);
            if (!str.equals("0") && !str.equals("1")) {
                if (noticeDataManager.getPushInfoSendingMode() == 1) {
                    new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.4
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                NoticeManager.this._PMgr.setEvent(str + NoticeManager.PUSH_OPEN_EVENT_SUFFIX);
                            } catch (Exception unused) {
                            }
                        }
                    }).start();
                } else {
                    noticeResult = NoticeResult.ERROR_MODE;
                }
            }
            return noticeResult;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public NoticeResult setDetailAccess(final String str) throws Throwable {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this._context);
            noticeDataManager.setDetailAccess(str);
            if (!str.equals("0") && !str.equals("1")) {
                if (noticeDataManager.getPushInfoSendingMode() == 1) {
                    new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.5
                        @Override // java.lang.Runnable
                        public void run() {
                            try {
                                NoticeManager.this._PMgr.setEvent(str + NoticeManager.PUSH_LAUNCH_EVENT_SUFFIX);
                            } catch (Exception unused) {
                            }
                        }
                    }).start();
                } else {
                    noticeResult = NoticeResult.ERROR_MODE;
                }
            }
            return noticeResult;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public NoticeResult setPushReceiveStatus(String str) {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            if (str != null) {
                if (str.equals("Y") || str.equals(ServicePreference.OFF)) {
                    NoticeDataManager.getInstance(this._context).setPushReceiveStatus(str);
                } else {
                    noticeResult = NoticeResult.ERROR_BAD_PARAM;
                }
            } else {
                noticeResult = NoticeResult.ERROR_BAD_PARAM;
            }
            return noticeResult;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public String getPushReceiveStatus() {
        try {
            return NoticeDataManager.getInstance(this._context).getPushReceiveStatus();
        } catch (Exception unused) {
            return ServicePreference.OFF;
        }
    }

    public void deleteRegisteredServiceItem(List<String> list) {
        try {
            NoticeDataManager.getInstance(this._context).deleteRegisteredServiceItem(list);
        } catch (Exception unused) {
        }
    }
}
