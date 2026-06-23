package com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice;

import android.content.Context;
import com.felicanetworks.mfm.main.model.info.MyServiceInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.TagInfo;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class NoticeManager {
    public static final String APP_ID = "7553";
    public static final String ENVIRONMENT = "production";
    public static final int PUSHINFOSENDING_MODE_ON = 1;
    private static final String PUSH_RECEIVE_STATUS_OFF = "N";
    private static final String PUSH_RECEIVE_STATUS_ON = "Y";
    public static final String SECRET_KEY = "ggdyqhxuqF8YYH6Nu2QxMe7fYcdJK29u";
    private static final String SENDER_ID = "206879382952";
    private static NoticeManager _self;
    private PushManager _PMgr = PushManager.getInstance();
    private Context _context;

    private NoticeManager(Context context) {
        this._context = context;
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
                            NoticeManager.this._PMgr.initialize(NoticeManager.this._context).register(NoticeManager.SENDER_ID);
                            NoticeManager.this._PMgr.setDeviceTags();
                        } catch (GooglePlayServicesNotAvailableException | GooglePlayServicesRepairableException | IOException | Exception unused) {
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

    /* JADX WARN: Removed duplicated region for block: B:18:0x003e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0052 A[Catch: Exception -> 0x0063, TryCatch #0 {Exception -> 0x0063, blocks: (B:3:0x0002, B:5:0x000f, B:7:0x001d, B:22:0x0044, B:24:0x0052, B:12:0x002a, B:15:0x0034, B:26:0x0060), top: B:30:0x0002 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public NoticeResult setAccept() {
        byte b;
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            NoticeDataManager noticeDataManager = NoticeDataManager.getInstance(this._context);
            if (noticeDataManager.getPushInfoSendingMode() == 1) {
                String preferenceDataKeyAccept = noticeDataManager.getPreferenceDataKeyAccept();
                String pushReceiveStatus = getPushReceiveStatus();
                if (!preferenceDataKeyAccept.equals(pushReceiveStatus)) {
                    int iHashCode = pushReceiveStatus.hashCode();
                    if (iHashCode != 78) {
                        b = (iHashCode == 89 && pushReceiveStatus.equals("Y")) ? (byte) 0 : (byte) -1;
                        if (b != 0) {
                            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.2
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        NoticeManager.this._PMgr.setTag(new TagInfo(null, "Y", null, null, null));
                                    } catch (Exception unused) {
                                    }
                                }
                            }).start();
                        } else if (b == 1) {
                            new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.3
                                @Override // java.lang.Runnable
                                public void run() {
                                    try {
                                        NoticeManager.this._PMgr.setTag(new TagInfo(null, "N", null, null, null));
                                    } catch (Exception unused) {
                                    }
                                }
                            }).start();
                            return noticeResult;
                        }
                    } else {
                        if (pushReceiveStatus.equals("N")) {
                            b = 1;
                        }
                        if (b != 0) {
                        }
                    }
                }
                return noticeResult;
            }
            return NoticeResult.ERROR_MODE;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public void setRegisterState(final Map<String, MyServiceInfo.RegisterState> registerStateMap) {
        if (registerStateMap == null || registerStateMap.isEmpty()) {
            return;
        }
        try {
            if (NoticeDataManager.getInstance(this._context).getPushInfoSendingMode() == 1) {
                new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeManager.4
                    @Override // java.lang.Runnable
                    public void run() {
                        try {
                            NoticeManager.this._PMgr.setRegisterState(registerStateMap);
                        } catch (Exception unused) {
                        }
                    }
                }).start();
            }
        } catch (Exception unused) {
        }
    }

    public NoticeData getNoticeData(String nid) {
        try {
            NoticeData noticeData = NoticeDataManager.getInstance(this._context).getNoticeData(nid);
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

    public NoticeResult setReadStatus(final String nid) throws Throwable {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            NoticeDataManager.getInstance(this._context).setReadStatus(nid);
            return noticeResult;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public NoticeResult setDetailAccess(final String nid) throws Throwable {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            NoticeDataManager.getInstance(this._context).setDetailAccess(nid);
            return noticeResult;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public NoticeResult setPushReceiveStatus(String setting) {
        NoticeResult noticeResult = NoticeResult.SUCCESS;
        try {
            if (setting != null) {
                if (!setting.equals("Y") && !setting.equals("N")) {
                    return NoticeResult.ERROR_BAD_PARAM;
                }
                NoticeDataManager.getInstance(this._context).setPushReceiveStatus(setting);
                return noticeResult;
            }
            return NoticeResult.ERROR_BAD_PARAM;
        } catch (Exception unused) {
            return NoticeResult.ERROR_UNKNOWN;
        }
    }

    public String getPushReceiveStatus() {
        try {
            return NoticeDataManager.getInstance(this._context).getPushReceiveStatus();
        } catch (Exception unused) {
            return "N";
        }
    }

    public void deleteRegisteredServiceItem(List<String> serviceIds) {
        try {
            NoticeDataManager.getInstance(this._context).deleteRegisteredServiceItem(serviceIds);
        } catch (Exception unused) {
        }
    }
}
