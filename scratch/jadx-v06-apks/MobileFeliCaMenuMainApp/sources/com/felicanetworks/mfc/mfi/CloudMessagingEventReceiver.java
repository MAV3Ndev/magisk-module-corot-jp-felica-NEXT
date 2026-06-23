package com.felicanetworks.mfc.mfi;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import androidx.constraintlayout.core.widgets.analyzer.BasicMeasure;
import androidx.core.app.NotificationCompat;
import com.felicanetworks.mfc.mfi.CloudMessagingService;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.mfm.messenger.MessageProtocol;
import com.felicanetworks.mfm.messenger.MessageReceiver;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class CloudMessagingEventReceiver {

    public static class ChannelMessenger extends MessageReceiver {
        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected boolean isAccepted(Context context, MessageProtocol.Action action) {
            return CloudMessagingCache.getInstance(context).hasCachedToken();
        }

        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected void onReceiveMessage(Context context, MessageProtocol.Action action, Map<String, String> map) {
            LogMgr.log(3, "000 action=" + action.name());
            Bundle bundle = new Bundle();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                LogMgr.log(7, "001 messages:" + entry.getKey() + "=" + entry.getValue());
                bundle.putString(entry.getKey(), entry.getValue());
            }
            Intent intent = new Intent();
            intent.putExtras(bundle);
            intent.setAction(CloudMessagingService.Action.FCM_ON_MESSAGE_RECEIVED.value);
            intent.setClass(context, CloudMessagingService.class);
            context.startService(intent);
            LogMgr.log(3, "999");
        }

        @Override // com.felicanetworks.mfm.messenger.MessageReceiver
        protected void onReceiveToken(Context context, String str) {
            LogMgr.log(3, "000 token=" + str);
            Intent intent = new Intent();
            intent.setAction(CloudMessagingService.Action.FCM_ON_NEW_TOKEN_RECEIVED.value);
            intent.setClass(context, CloudMessagingService.class);
            context.startService(intent);
            LogMgr.log(3, "999");
        }
    }

    public static class ChannelAlarm extends BroadcastReceiver {
        static boolean setup(Context context, Bundle bundle, String str, long j) {
            LogMgr.log(6, "000 interval=" + j);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(NotificationCompat.CATEGORY_ALARM);
            if (alarmManager == null) {
                LogMgr.log(6, "700 AlarmManager cannot be acquired");
                return false;
            }
            Intent intent = new Intent(context, (Class<?>) ChannelAlarm.class);
            intent.putExtras(bundle);
            intent.setAction(str);
            PendingIntent broadcast = PendingIntent.getBroadcast(context, 0, intent, BasicMeasure.EXACTLY);
            long jElapsedRealtime = SystemClock.elapsedRealtime() + j;
            if (Build.VERSION.SDK_INT >= 23) {
                alarmManager.setAndAllowWhileIdle(2, jElapsedRealtime, broadcast);
            } else {
                alarmManager.set(2, jElapsedRealtime, broadcast);
            }
            LogMgr.log(6, "999");
            return true;
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            LogMgr.log(3, "000 action=" + intent.getAction());
            Bundle extras = intent.getExtras();
            if (extras != null) {
                for (String str : extras.keySet()) {
                    LogMgr.log(7, "001 extras:" + str + "=" + extras.get(str));
                }
            } else {
                LogMgr.log(7, "002 extras is none.");
            }
            Intent intent2 = new Intent(intent);
            intent2.setClass(context, CloudMessagingService.class);
            context.startService(intent2);
            LogMgr.log(3, "999");
        }
    }
}
