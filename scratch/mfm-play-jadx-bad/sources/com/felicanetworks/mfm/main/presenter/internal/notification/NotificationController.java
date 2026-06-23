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
import android.os.Build;
import android.os.SystemClock;
import com.felicanetworks.mfc.FSC$$ExternalSyntheticApiModelOutline0;
import com.felicanetworks.mfm.main.R;
import com.felicanetworks.mfm.main.ServiceListActivity;
import com.felicanetworks.mfm.main.model.info.NoticeInfo;

/* JADX INFO: loaded from: classes3.dex */
public class NotificationController {
    private static final String NOTIFICATION_CHANNEL_ID = "mfmmain-channel";
    private static final int NOTIFICATION_DEFAULT_ID = 1;
    public static final String NOTIFICATION_ID_KEY = "NotificationID";
    private static NotificationController _self;
    private Context _context;

    private NotificationController() {
    }

    private void setContext(Context context) {
        this._context = context;
    }

    public static NotificationController getInstance(Context context) {
        if (_self == null) {
            _self = new NotificationController();
        }
        _self.setContext(context);
        return _self;
    }

    public void createNotificationChannel() {
        NotificationManager notificationManager = (NotificationManager) this._context.getSystemService("notification");
        if (notificationManager == null || notificationManager.getNotificationChannel(NOTIFICATION_CHANNEL_ID) != null) {
            return;
        }
        NotificationChannel notificationChannelM = FSC$$ExternalSyntheticApiModelOutline0.m(NOTIFICATION_CHANNEL_ID, this._context.getResources().getString(R.string.notification_text_channel_name), 3);
        notificationChannelM.enableVibration(true);
        notificationChannelM.enableLights(true);
        notificationManager.createNotificationChannel(notificationChannelM);
    }

    public boolean notifyNotification(NoticeInfo info) {
        Notification.Builder builder;
        Intent intent = new Intent(this._context, (Class<?>) ServiceListActivity.class);
        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");
        intent.addFlags(268435456);
        intent.addFlags(32768);
        intent.putExtra(NOTIFICATION_ID_KEY, info.getId());
        PendingIntent activity = PendingIntent.getActivity(this._context, (int) SystemClock.elapsedRealtime(), intent, Build.VERSION.SDK_INT >= 31 ? 335544320 : 268435456);
        Resources resources = this._context.getResources();
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(resources, R.drawable.app_icon);
        String string = resources.getString(R.string.text_msg_058);
        NotificationManager notificationManager = (NotificationManager) this._context.getSystemService("notification");
        if (Build.VERSION.SDK_INT >= 26) {
            createNotificationChannel();
            FSC$$ExternalSyntheticApiModelOutline0.m394m();
            builder = FSC$$ExternalSyntheticApiModelOutline0.m(this._context, NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(this._context);
            builder.setDefaults(7);
        }
        builder.setSmallIcon(R.drawable.ic_notification);
        builder.setLargeIcon(bitmapDecodeResource);
        builder.setContentTitle(string);
        builder.setContentText(info.getTitle());
        builder.setStyle(new Notification.BigTextStyle().bigText(info.getTitle()));
        builder.setWhen(System.currentTimeMillis());
        builder.setShowWhen(true);
        builder.setContentIntent(activity);
        builder.setAutoCancel(true);
        Notification notificationBuild = builder.build();
        if (notificationManager != null) {
            notificationManager.notify(info.getId(), 1, notificationBuild);
        }
        return true;
    }
}
