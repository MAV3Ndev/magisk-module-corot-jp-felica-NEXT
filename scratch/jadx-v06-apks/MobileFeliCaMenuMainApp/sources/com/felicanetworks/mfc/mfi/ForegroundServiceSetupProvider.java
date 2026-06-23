package com.felicanetworks.mfc.mfi;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class ForegroundServiceSetupProvider {
    private static final String CHANNEL_ID = "mfi-channel";
    private static final int REQUEST_CODE_FOR_INTENT = 0;
    private static final String URL_SCHEME_FOR_PACKAGE = "package";

    enum Type {
        FELICA_ADAPTER(100),
        CLOUD_MESSAGING_SERVICE(110);

        int notificationId;

        Type(int i) {
            this.notificationId = i;
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
            ((NotificationManager) service.getSystemService("notification")).createNotificationChannel(new NotificationChannel(CHANNEL_ID, service.getString(R.string.mfi_app_running_notification_channel_name), 2));
            priority = new Notification.Builder(service.getApplicationContext(), CHANNEL_ID);
        } else {
            priority = new Notification.Builder(service.getApplicationContext()).setPriority(-2);
        }
        service.startForeground(type.notificationId, priority.setContentTitle(service.getString(R.string.mfi_app_running_notification_title)).setContentText(service.getString(R.string.mfi_app_running_notification_text)).setSmallIcon(R.drawable.ic_notification).setLargeIcon(BitmapFactory.decodeResource(service.getResources(), R.drawable.app_icon)).setContentIntent(PendingIntent.getActivity(service.getApplicationContext(), 0, intent, 134217728)).build());
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x006c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean shouldStartServiceForeground(android.content.Context r4) {
        /*
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r1 = "000 SDK_INT="
            r0.append(r1)
            int r1 = android.os.Build.VERSION.SDK_INT
            r0.append(r1)
            java.lang.String r0 = r0.toString()
            r1 = 6
            com.felicanetworks.mfc.util.LogMgr.log(r1, r0)
            int r0 = android.os.Build.VERSION.SDK_INT
            r2 = 1
            r3 = 23
            if (r0 < r3) goto L6c
            java.lang.String r0 = "001"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r0)
            java.lang.String r0 = "power"
            java.lang.Object r0 = r4.getSystemService(r0)
            android.os.PowerManager r0 = (android.os.PowerManager) r0
            java.lang.String r3 = r4.getPackageName()
            boolean r0 = r0.isIgnoringBatteryOptimizations(r3)
            if (r0 != 0) goto L3b
            java.lang.String r4 = "002 isIgnoringBatteryOptimizations is false."
            com.felicanetworks.mfc.util.LogMgr.log(r1, r4)
            goto L6d
        L3b:
            int r0 = android.os.Build.VERSION.SDK_INT
            r3 = 24
            if (r0 < r3) goto L6c
            java.lang.String r0 = "003"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r0)
            java.lang.String r0 = "connectivity"
            java.lang.Object r4 = r4.getSystemService(r0)
            android.net.ConnectivityManager r4 = (android.net.ConnectivityManager) r4
            int r4 = r4.getRestrictBackgroundStatus()
            if (r4 == r2) goto L67
            r0 = 2
            if (r4 == r0) goto L61
            r0 = 3
            if (r4 == r0) goto L5b
            goto L6c
        L5b:
            java.lang.String r4 = "004 DataSaver is ON (not whitelisted)."
            com.felicanetworks.mfc.util.LogMgr.log(r1, r4)
            goto L6d
        L61:
            java.lang.String r4 = "005 DataSaver is ON (whitelisted)."
            com.felicanetworks.mfc.util.LogMgr.log(r1, r4)
            goto L6c
        L67:
            java.lang.String r4 = "006 DataSaver is OFF."
            com.felicanetworks.mfc.util.LogMgr.log(r1, r4)
        L6c:
            r2 = 0
        L6d:
            java.lang.String r4 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r1, r4)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.ForegroundServiceSetupProvider.shouldStartServiceForeground(android.content.Context):boolean");
    }
}
