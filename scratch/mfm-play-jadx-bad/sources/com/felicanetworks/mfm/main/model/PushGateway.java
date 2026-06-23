package com.felicanetworks.mfm.main.model;

import android.content.Context;
import android.text.TextUtils;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.NoticeResult;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.PushManager;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.DatabaseAccessException;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeData;
import com.felicanetworks.mfm.main.model.internal.legacy.mfmnotice.data.NoticeDataManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class PushGateway {
    private static final String INITIAL_SERVICE_ID_DATA = "0";
    private static final int INTENT_EXTRAS_KEY_REGISTERED_SERVICE = 1;
    private static final int INTENT_EXTRAS_KEY_UNREGISTERED_SERVICE = 0;
    public static final String[] registerStatuses = {"0", "2"};

    public static boolean saveData(Context context, NoticeInfo data) {
        NoticeResult noticeData;
        try {
            noticeData = NoticeDataManager.getInstance(context).setNoticeData(new NoticeData(data));
        } catch (DatabaseAccessException unused) {
            noticeData = null;
        }
        return NoticeResult.SET_NOTICE_NEW == noticeData && !data.isExpiration();
    }

    public static boolean checkNotifyNotification(Context context, NoticeInfo data) {
        ArrayList<NoticeInfo.Ncond> ncond;
        try {
            List<String> dispService = NoticeDataManager.getInstance(context).getDispService(registerStatuses);
            if (dispService == null || ((dispService.size() == 1 && dispService.get(0).equals("0")) || (ncond = data.getNcond()) == null)) {
                return true;
            }
            for (NoticeInfo.Ncond ncond2 : ncond) {
                int key = ncond2.getKey();
                ArrayList<String> serviceId = ncond2.getServiceId();
                if (key == 0) {
                    Iterator<String> it = serviceId.iterator();
                    while (it.hasNext()) {
                        if (!dispService.contains(it.next())) {
                            break;
                        }
                    }
                    return false;
                }
                if (key == 1) {
                    Iterator<String> it2 = serviceId.iterator();
                    while (it2.hasNext()) {
                        if (dispService.contains(it2.next())) {
                            break;
                        }
                    }
                }
                return false;
            }
        } catch (Exception unused) {
        }
        return true;
    }

    public static void enableSendingMode(Context context, boolean enable) {
        NoticeDataManager.getInstance(context).setPushInfoSendingMode(enable ? 2 : 1);
    }

    public static boolean isEnableNotification(Context context) {
        return NoticeDataManager.getInstance(context).getPushReceiveStatus().equals("Y");
    }

    public static void syncRegistrationId(final Context context, final String regId) {
        new Thread(new Runnable() { // from class: com.felicanetworks.mfm.main.model.PushGateway.1
            @Override // java.lang.Runnable
            public void run() {
                try {
                    PushManager.getInstance().initialize(context).registerClient(regId);
                    PushManager.getInstance().setDeviceTags();
                } catch (Exception unused) {
                }
            }
        }).start();
    }

    public static boolean isRegisteredDeviceToken(Context context) {
        return !TextUtils.equals(NoticeDataManager.getInstance(context).getDeviceToken(), "0");
    }

    public static boolean isPermittedPush(Context context) {
        return NoticeDataManager.getInstance(context).getPushInfoSendingMode() == 1;
    }
}
