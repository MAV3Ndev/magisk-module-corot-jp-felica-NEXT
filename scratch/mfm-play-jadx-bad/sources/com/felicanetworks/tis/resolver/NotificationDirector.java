package com.felicanetworks.tis.resolver;

import android.app.KeyguardManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.PowerManager;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.tis.LogSender;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.SharedPreferenceProvider;
import com.felicanetworks.tis.SignatureUtil;
import com.felicanetworks.tis.TapInteractionConst;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.datatype.NotificationJsonInfo;
import com.felicanetworks.tis.util.LogMgr;
import com.google.android.gms.pay.Pay;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
class NotificationDirector {
    private static final String CHANNEL_ID = "Tap Notice";
    private static final int CHANNEL_IMPORTANCE = 4;
    private static final String GOOGLE_WALLET_APP_PACKAGE_NAME = "google";
    private static final String NOTIFICATION_DISPLAY_MODE_CARD = "card";
    private static final int NOTIFICATION_ID = 100;
    private final Context mContext;

    NotificationDirector(Context context) {
        this.mContext = context;
    }

    void showNotification(List<NotificationInfo> list) {
        LogMgr.log(4, "000");
        LogMgr.log(7, "[PFM]NotificationDirector#showNotification()");
        LogSender logSender = new LogSender();
        boolean notificationDisplayFromAPP = getNotificationDisplayFromAPP(this.mContext);
        for (NotificationInfo notificationInfo : list) {
            if (notificationDisplayFromAPP) {
                notificationInfo.setExtraUuid(logSender.getUuid());
                createNotification(this.mContext, notificationInfo);
                logSender.sendNotificationLog(this.mContext, notificationInfo.getIdm(), notificationInfo.getServiceId(), notificationInfo.getLogInfo());
                startCardDialogActivity(this.mContext, notificationInfo);
            }
            JSONObject jSONObjectCreateNotificationJson = notificationInfo.createNotificationJson(notificationDisplayFromAPP);
            ArrayList arrayList = new ArrayList();
            if (jSONObjectCreateNotificationJson != null) {
                if (notifyGoogleWallet(jSONObjectCreateNotificationJson)) {
                    arrayList.add(GOOGLE_WALLET_APP_PACKAGE_NAME);
                }
                List<String> listSendBroadcastSpApplication = sendBroadcastSpApplication(jSONObjectCreateNotificationJson);
                if (!listSendBroadcastSpApplication.isEmpty()) {
                    arrayList.addAll(listSendBroadcastSpApplication);
                }
            }
            if (!arrayList.isEmpty()) {
                logSender.sendNotifyExternalAppLog(this.mContext, notificationInfo.getIdm(), notificationInfo.getServiceId(), arrayList);
            }
        }
        LogMgr.log(4, "999");
    }

    private void createNotification(Context context, NotificationInfo notificationInfo) {
        LogMgr.log(6, "000");
        LogMgr.log(7, "[PFM]NotificationDirector#createNotification()");
        int smallIcon = notificationInfo.getSmallIcon();
        Bitmap largeIcon = notificationInfo.getLargeIcon();
        String title = notificationInfo.getTitle();
        String message = notificationInfo.getMessage();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        FSC$$ExternalSyntheticApiModelOutline0.m$4();
        NotificationChannel notificationChannelM = FSC$$ExternalSyntheticApiModelOutline0.m(CHANNEL_ID, context.getResources().getString(R.string.tis_channel_name), 4);
        notificationChannelM.enableVibration(true);
        notificationChannelM.setSound(null, null);
        notificationManager.createNotificationChannel(notificationChannelM);
        FSC$$ExternalSyntheticApiModelOutline0.m394m();
        notificationManager.notify(100, FSC$$ExternalSyntheticApiModelOutline0.m(context.getApplicationContext(), CHANNEL_ID).setSmallIcon(smallIcon).setContentTitle(title).setContentText(message).setShowWhen(true).setLargeIcon(largeIcon).setContentIntent(notificationInfo.getContextIntent(this.mContext)).build());
        LogMgr.log(6, "001 show " + notificationInfo.getServiceId() + " " + notificationInfo.getMessage());
        LogMgr.log(6, "999");
    }

    private boolean notifyGoogleWallet(JSONObject jSONObject) {
        boolean z;
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]NotificationDirector#notifyGoogleWallet()");
        if (isNotifyGoogleWallet(jSONObject)) {
            LogMgr.log(7, "notifyCardTapEvent called.");
            try {
                Pay.getClient(this.mContext).notifyCardTapEvent(jSONObject.toString());
                z = true;
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " " + e.getMessage());
                z = false;
            }
        } else {
            z = false;
        }
        LogMgr.log(5, "999");
        return z;
    }

    private boolean isNotifyGoogleWallet(JSONObject jSONObject) {
        LogMgr.log(5, "000");
        boolean z = false;
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(NotificationJsonInfo.OBJECT_NAME);
            String[] strArr = TapInteractionConst.GOOGLE_WALLET_LINK_SERVICE_ID;
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (strArr[i].equals(jSONObject2.getString("serviceId"))) {
                    z = true;
                    break;
                }
                i++;
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        LogMgr.log(5, "999");
        return z;
    }

    private List<String> sendBroadcastSpApplication(JSONObject jSONObject) {
        LogMgr.log(5, "000");
        LogMgr.log(7, "[PFM]NotificationDirector#sendBroadcastSpApplication()");
        ArrayList arrayList = new ArrayList();
        try {
            String string = jSONObject.getJSONObject(NotificationJsonInfo.OBJECT_NAME).getString("serviceId");
            for (String[] strArr : TapInteractionConst.SP_APPLICATION_LINK_INFO) {
                if (strArr[0].equals(string) && SignatureUtil.checkAppCertHash(this.mContext, strArr[3], strArr[1])) {
                    Intent intent = new Intent();
                    intent.setAction(TapInteractionConst.ACTION_TAPINTERACTION_INFO_EVENT);
                    intent.setClassName(strArr[1], strArr[2]);
                    if (isReceiver(intent, strArr[1])) {
                        intent.putExtra(TapInteractionConst.EXTRA_KEY_TAPINTERACTION_INFO_EVENT, jSONObject.toString());
                        this.mContext.sendBroadcast(intent);
                        arrayList.add(strArr[1]);
                    }
                }
            }
        } catch (Exception e) {
            LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
        LogMgr.log(5, "999");
        return arrayList;
    }

    private boolean isReceiver(Intent intent, String str) {
        List<ResolveInfo> listQueryBroadcastReceivers;
        LogMgr.log(5, "000");
        boolean z = false;
        if (Build.VERSION.SDK_INT >= 33) {
            listQueryBroadcastReceivers = this.mContext.getPackageManager().queryBroadcastReceivers(intent, PackageManager.ResolveInfoFlags.of(0L));
        } else {
            listQueryBroadcastReceivers = this.mContext.getPackageManager().queryBroadcastReceivers(intent, 0);
        }
        Iterator<ResolveInfo> it = listQueryBroadcastReceivers.iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            if (it.next().activityInfo.applicationInfo.packageName.equals(str)) {
                z = true;
                break;
            }
        }
        LogMgr.log(5, "999");
        return z;
    }

    private void startCardDialogActivity(Context context, NotificationInfo notificationInfo) {
        LogMgr.log(6, "000");
        Intent startIntent = notificationInfo.getStartIntent();
        if (!isShowCardDialogFromApp(context) || startIntent == null) {
            return;
        }
        try {
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                LogMgr.log(6, "001 show Dialog on lock screen");
                context.startActivity(startIntent);
                return;
            }
            PowerManager powerManager = (PowerManager) context.getSystemService("power");
            if (powerManager == null || powerManager.isInteractive()) {
                return;
            }
            LogMgr.log(6, "002 show Dialog on display off");
            context.startActivity(startIntent);
        } catch (Exception unused) {
            LogMgr.log(2, "700 failed to show card dialog");
        }
    }

    /* JADX DEBUG: Finally have unexpected throw blocks count: 2, expect 1 */
    private boolean getNotificationDisplayFromAPP(Context context) {
        LogMgr.log(5, "000");
        String strValueOf = String.valueOf(true);
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = context.getContentResolver().query(SharedPreferenceProvider.Contents.CONTENT_URI, null, null, null, null);
                if (cursorQuery != null && cursorQuery.moveToFirst()) {
                    strValueOf = cursorQuery.getString(cursorQuery.getColumnIndex(SharedPreferenceProvider.Contents.Setting.NOTIFICATION_DISPLAY));
                    LogMgr.log(6, "001 : display = " + strValueOf);
                }
            } catch (Exception e) {
                LogMgr.log(2, "700 " + e.getClass().getSimpleName() + " " + e.getMessage());
                if (cursorQuery != null) {
                }
            }
            if (cursorQuery != null) {
                cursorQuery.close();
            }
            LogMgr.log(5, "999");
            return Boolean.parseBoolean(strValueOf);
        } finally {
        }
    }

    private boolean isShowCardDialogFromApp(Context context) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return false;
    }
}
