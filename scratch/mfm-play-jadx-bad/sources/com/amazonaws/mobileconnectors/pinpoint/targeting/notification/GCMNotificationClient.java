package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.PendingIntent;
import android.os.Bundle;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.services.pinpoint.model.ChannelType;

/* JADX INFO: loaded from: classes.dex */
final class GCMNotificationClient extends NotificationClientBase {
    protected GCMNotificationClient(PinpointContext pinpointContext) {
        super(pinpointContext);
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClientBase
    public String getChannelType() {
        return ChannelType.GCM.toString();
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClientBase
    protected PendingIntent createOpenAppPendingIntent(Bundle bundle, Class<?> cls, String str, int i, String str2) {
        String str3 = NotificationClient.GCM_INTENT_ACTION;
        if (!str2.equals(NotificationClient.GCM_INTENT_ACTION)) {
            str3 = NotificationClient.FCM_INTENT_ACTION;
        }
        PendingIntent activity = PendingIntent.getActivity(this.pinpointContext.getApplicationContext(), i, notificationIntent(bundle, str, i, str3, cls), 1140850688);
        PinpointNotificationActivity.setNotificationClient(this);
        return activity;
    }
}
