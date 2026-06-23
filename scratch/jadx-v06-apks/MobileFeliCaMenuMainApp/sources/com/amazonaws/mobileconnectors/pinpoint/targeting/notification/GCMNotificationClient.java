package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.PendingIntent;
import android.os.Bundle;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
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
        if (str2.equals(NotificationClient.GCM_INTENT_ACTION)) {
            return PendingIntent.getService(this.pinpointContext.getApplicationContext(), i, notificationIntent(bundle, str, i, NotificationClient.GCM_INTENT_ACTION, cls), BasicMeasure.EXACTLY);
        }
        PendingIntent broadcast = PendingIntent.getBroadcast(this.pinpointContext.getApplicationContext(), i, notificationIntent(bundle, str, i, NotificationClient.FCM_INTENT_ACTION, cls), BasicMeasure.EXACTLY);
        PinpointNotificationReceiver.setNotificationClient(this);
        return broadcast;
    }
}
