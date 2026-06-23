package com.felicanetworks.mfc.mfi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.core.app.NotificationCompat;
import com.felicanetworks.mfc.mfi.CloudMessagingService;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfm.messenger.MessageProtocol;
import com.felicanetworks.mfm.messenger.MessageReceiver;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public class CloudMessagingEventReceiver {
    private static final String EXT_KEY_RETRY_REMAINING = "retry-remaining";

    public static class ChannelMessenger extends MessageReceiver {
        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected boolean isAccepted(Context context, MessageProtocol.Action action) {
            return CloudMessagingCache.getInstance(context).hasCachedToken();
        }

        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected void onReceiveMessage(Context context, MessageProtocol.Action action, Map<String, String> messages) {
            LogMgr.log(3, "000 action=" + action.name());
            Bundle bundle = new Bundle();
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                LogMgr.log(7, "001 messages:" + entry.getKey() + "=" + entry.getValue());
                bundle.putString(entry.getKey(), entry.getValue());
            }
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setAction(CloudMessagingService.Action.FCM_ON_MESSAGE_RECEIVED.value);
            intent.setClass(context, CloudMessagingService.class);
            try {
                context.startService(intent);
            } catch (IllegalStateException unused) {
                LogMgr.log(1, "800 Not allowed to start service in background");
            }
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected void onReceiveToken(Context context, String token) {
            LogMgr.log(3, "000 token=" + token);
            Intent intent = new Intent();
            intent.setAction(CloudMessagingService.Action.FCM_ON_NEW_TOKEN_RECEIVED.value);
            intent.setClass(context, CloudMessagingService.class);
            try {
                context.startService(intent);
            } catch (IllegalStateException unused) {
                LogMgr.log(1, "800 Not allowed to start service in background");
            }
            LogMgr.log(3, "999");
        }
    }

    public static class ChannelAlarm extends BroadcastReceiver {
        static boolean setup(Context context, Bundle extras, String action, long interval) {
            LogMgr.log(6, "000 interval=" + interval);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (alarmManager == null) {
                LogMgr.log(6, "700 AlarmManager cannot be acquired");
                return false;
            }
            Intent intent = new Intent(context, (Class<?>) ChannelAlarm.class);
            intent.putExtras(extras);
            intent.setAction(action);
            alarmManager.setAndAllowWhileIdle(2, SystemClock.elapsedRealtime() + interval, PendingIntent.getBroadcast(context, 0, intent, 1140850688));
            LogMgr.log(6, "999");
            return true;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogMgr.log(3, "000 action=" + intent.getAction());
            Intent intent2 = new Intent(intent);
            intent2.setClass(context, CloudMessagingService.class);
            try {
                context.startService(intent2);
            } catch (IllegalStateException unused) {
                LogMgr.log(1, "800 Not allowed to start service in background");
            }
            LogMgr.log(3, "999");
        }
    }
}
