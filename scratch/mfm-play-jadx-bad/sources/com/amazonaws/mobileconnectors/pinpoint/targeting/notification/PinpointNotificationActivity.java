package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/* JADX INFO: loaded from: classes.dex */
public class PinpointNotificationActivity extends Activity {
    private static String TAG = "PinpointNotificationActivity";
    private static volatile NotificationClient notificationClient;

    public static void setNotificationClient(NotificationClient notificationClient2) {
        notificationClient = notificationClient2;
    }

    public static void setNotificationClient(NotificationClientBase notificationClientBase) {
        notificationClient = new NotificationClient(notificationClientBase);
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (notificationClient != null) {
            notificationClient.handleNotificationOpen(EventSourceType.getEventSourceType(extras).getAttributeParser().parseAttributes(extras), extras);
        } else {
            Intent launchIntentForPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
            launchIntentForPackage.putExtras(extras);
            startActivity(launchIntentForPackage);
        }
        finish();
    }
}
