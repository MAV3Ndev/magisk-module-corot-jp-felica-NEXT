package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
public class PinpointNotificationReceiver extends BroadcastReceiver {
    private static String TAG = PinpointNotificationReceiver.class.getSimpleName();
    private static volatile NotificationClient notificationClient = null;

    public static void setNotificationClient(NotificationClient notificationClient2) {
        notificationClient = notificationClient2;
    }

    public static void setNotificationClient(NotificationClientBase notificationClientBase) {
        notificationClient = new NotificationClient(notificationClientBase);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (notificationClient != null) {
            notificationClient.handleNotificationOpen(EventSourceType.getEventSourceType(intent.getExtras()).getAttributeParser().parseAttributes(intent.getExtras()), intent.getExtras());
        } else {
            Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(intent.getPackage());
            launchIntentForPackage.putExtras(intent.getExtras());
            context.startActivity(launchIntentForPackage);
        }
    }
}
