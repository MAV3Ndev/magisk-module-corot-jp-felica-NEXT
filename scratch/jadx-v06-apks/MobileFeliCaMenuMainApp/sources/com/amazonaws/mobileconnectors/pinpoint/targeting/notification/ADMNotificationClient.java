package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.PendingIntent;
import android.os.Bundle;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import com.amazonaws.mobileconnectors.pinpoint.internal.core.PinpointContext;
import com.amazonaws.services.pinpoint.model.ChannelType;

/* JADX INFO: loaded from: classes.dex */
final class ADMNotificationClient extends NotificationClientBase {
    protected ADMNotificationClient(PinpointContext pinpointContext) {
        super(pinpointContext);
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClientBase
    public String getChannelType() {
        return ChannelType.ADM.toString();
    }

    @Override // com.amazonaws.mobileconnectors.pinpoint.targeting.notification.NotificationClientBase
    protected PendingIntent createOpenAppPendingIntent(Bundle bundle, Class<?> cls, String str, int i, String str2) {
        if (str2.equals(NotificationClient.ADM_INTENT_ACTION)) {
            return PendingIntent.getService(this.pinpointContext.getApplicationContext(), i, notificationIntent(bundle, str, i, NotificationClient.ADM_INTENT_ACTION, cls), BasicMeasure.EXACTLY);
        }
        return null;
    }
}
