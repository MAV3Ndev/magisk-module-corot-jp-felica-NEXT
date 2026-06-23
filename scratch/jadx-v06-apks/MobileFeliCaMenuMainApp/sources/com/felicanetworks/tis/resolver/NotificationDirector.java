package com.felicanetworks.tis.resolver;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.PowerManager;
import com.felicanetworks.tis.LogSender;
import com.felicanetworks.tis.R;
import com.felicanetworks.tis.datatype.NotificationInfo;
import com.felicanetworks.tis.util.LogMgr;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class NotificationDirector {
    private static final String CHANNEL_ID = "Tap Notice";
    private static final int CHANNEL_IMPORTANCE = 4;
    private static final String NOTIFICATION_DISPLAY_MODE_CARD = "card";
    private static final int NOTIFICATION_ID = 100;
    private Context mContext;

    NotificationDirector(Context context) {
        this.mContext = context;
    }

    void showNotification(List<NotificationInfo> list) {
        LogMgr.log(4, "000");
        LogSender logSender = new LogSender();
        for (NotificationInfo notificationInfo : list) {
            notificationInfo.setExtraUuid(logSender.getUuid());
            createNotification(this.mContext, notificationInfo);
            logSender.sendNotificationLog(this.mContext, notificationInfo.getIdm(), notificationInfo.getServiceId(), notificationInfo.getLogInfo());
            startCardDialogActivity(this.mContext, notificationInfo);
        }
        LogMgr.log(4, "999");
    }

    private void createNotification(Context context, NotificationInfo notificationInfo) {
        LogMgr.log(6, "000");
        int smallIcon = notificationInfo.getSmallIcon();
        Bitmap largeIcon = notificationInfo.getLargeIcon();
        String title = notificationInfo.getTitle();
        String message = notificationInfo.getMessage();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, context.getResources().getString(R.string.tis_channel_name), 4);
        notificationChannel.enableVibration(true);
        notificationChannel.setSound(null, null);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(100, new Notification.Builder(context.getApplicationContext(), CHANNEL_ID).setSmallIcon(smallIcon).setContentTitle(title).setContentText(message).setShowWhen(true).setLargeIcon(largeIcon).setContentIntent(notificationInfo.getContextIntent(this.mContext)).build());
        LogMgr.log(6, "001 show " + notificationInfo.getServiceId() + " " + notificationInfo.getMessage());
        LogMgr.log(6, "999");
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
            } else {
                PowerManager powerManager = (PowerManager) context.getSystemService("power");
                if (powerManager != null && !powerManager.isInteractive()) {
                    LogMgr.log(6, "002 show Dialog on display off");
                    context.startActivity(startIntent);
                }
            }
        } catch (Exception unused) {
            LogMgr.log(2, "700 failed to show card dialog");
        }
    }

    private boolean isShowCardDialogFromApp(Context context) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return false;
    }
}
