package com.felicanetworks.mfm.main.presenter.internal.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.policy.sg.Sg;

/* JADX INFO: loaded from: classes3.dex */
public class AccountSwitchingNotificationController {
    private static final String NOTIFICATION_CHANNEL_ID = "mfmmain-accountswitching-channel";
    private static final int NOTIFICATION_DEFAULT_ID = 2;
    private static AccountSwitchingNotificationController _self;
    private Context _context;

    private AccountSwitchingNotificationController() {
    }

    private void setContext(Context context) {
        this._context = context;
    }

    public static AccountSwitchingNotificationController getInstance(Context context) {
        if (_self == null) {
            _self = new AccountSwitchingNotificationController();
        }
        _self.setContext(context);
        return _self;
    }

    public boolean notifyNotification() {
        Notification.Builder builder;
        PendingIntent activity = PendingIntent.getActivity(this._context, 1000, new Intent("android.intent.action.VIEW", Uri.parse((String) Sg.getValue(Sg.Key.NET_ACCOUNT_CHANGE_GUIDANCE_PAGE_URL))), Build.VERSION.SDK_INT >= 31 ? 201326592 : 134217728);
        Resources resources = this._context.getResources();
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(resources, R.drawable.app_icon);
        NotificationManager notificationManager = (NotificationManager) this._context.getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel notificationChannelM = FSC$$ExternalSyntheticApiModelOutline0.m(NOTIFICATION_CHANNEL_ID, resources.getString(R.string.text_account_change_notificaion_channel_name), 3);
            FSC$$ExternalSyntheticApiModelOutline0.m394m();
            builder = FSC$$ExternalSyntheticApiModelOutline0.m(this._context, NOTIFICATION_CHANNEL_ID);
            notificationChannelM.enableVibration(true);
            notificationChannelM.enableLights(true);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(notificationChannelM);
            }
        } else {
            builder = new Notification.Builder(this._context);
            builder.setDefaults(7);
        }
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setLargeIcon(bitmapDecodeResource);
        builder.setContentTitle(resources.getString(R.string.app_name));
        builder.setContentText(resources.getString(R.string.text_account_change_notificaion_description));
        builder.setStyle(new Notification.BigTextStyle().bigText(resources.getString(R.string.text_account_change_notificaion_description)));
        builder.setWhen(System.currentTimeMillis());
        builder.setShowWhen(true);
        builder.setContentIntent(activity);
        builder.setAutoCancel(true);
        Notification notificationBuild = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(2, notificationBuild);
        }
        return true;
    }
}
