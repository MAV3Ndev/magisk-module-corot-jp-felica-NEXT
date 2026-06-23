package com.felicanetworks.mfc.mfi;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.PowerManager;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class ForegroundServiceSetupProvider {
    private static final String CHANNEL_ID = "mfi-channel";
    private static final int REQUEST_CODE_FOR_INTENT = 0;
    private static final String URL_SCHEME_FOR_PACKAGE = "package";

    enum Type {
        FELICA_ADAPTER(100),
        CLOUD_MESSAGING_SERVICE(110);

        int notificationId;

        Type(int notificationId) {
            this.notificationId = notificationId;
        }
    }

    public static boolean requestForegroundService(Service service, Type type) {
        Notification.Builder priority;
        if (!shouldStartServiceForeground(service)) {
            return false;
        }
        Intent intent = new Intent();
        intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.fromParts(URL_SCHEME_FOR_PACKAGE, service.getPackageName(), null));
        if (Build.VERSION.SDK_INT >= 26) {
            ((NotificationManager) service.getSystemService("notification")).createNotificationChannel(FSC$$ExternalSyntheticApiModelOutline0.m(CHANNEL_ID, service.getString(R.string.mfi_app_running_notification_channel_name), 2));
            FSC$$ExternalSyntheticApiModelOutline0.m394m();
            priority = FSC$$ExternalSyntheticApiModelOutline0.m(service.getApplicationContext(), CHANNEL_ID);
        } else {
            priority = new Notification.Builder(service.getApplicationContext()).setPriority(-2);
        }
        service.startForeground(type.notificationId, priority.setContentTitle(service.getString(R.string.mfi_app_running_notification_title)).setContentText(service.getString(R.string.mfi_app_running_notification_text)).setSmallIcon(R.drawable.ic_notification).setLargeIcon(BitmapFactory.decodeResource(service.getResources(), R.drawable.app_icon)).setContentIntent(PendingIntent.getActivity(service.getApplicationContext(), 0, intent, 201326592)).build());
        return true;
    }

    private static boolean shouldStartServiceForeground(Context context) {
        LogMgr.log(6, "000 SDK_INT=" + Build.VERSION.SDK_INT);
        LogMgr.log(6, "001");
        boolean z = true;
        if (!((PowerManager) context.getSystemService("power")).isIgnoringBatteryOptimizations(context.getPackageName())) {
            LogMgr.log(6, "002 isIgnoringBatteryOptimizations is false.");
        } else if (Build.VERSION.SDK_INT >= 24) {
            LogMgr.log(6, "003");
            int restrictBackgroundStatus = ((ConnectivityManager) context.getSystemService("connectivity")).getRestrictBackgroundStatus();
            if (restrictBackgroundStatus == 1) {
                LogMgr.log(6, "006 DataSaver is OFF.");
            } else if (restrictBackgroundStatus == 2) {
                LogMgr.log(6, "005 DataSaver is ON (whitelisted).");
            } else if (restrictBackgroundStatus == 3) {
                LogMgr.log(6, "004 DataSaver is ON (not whitelisted).");
            }
            z = false;
        } else {
            z = false;
        }
        LogMgr.log(6, "999");
        return z;
    }
}
