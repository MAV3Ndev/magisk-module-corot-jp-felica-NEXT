package com.felicanetworks.tis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.felicanetworks.tis.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PreferenceChangedReceiver extends BroadcastReceiver {
    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        LogMgr.log(4, "000");
        if (Build.VERSION.SDK_INT >= 28) {
            String action = intent.getAction();
            if (action == null) {
                LogMgr.log(1, "800 : intent action is null");
                return;
            }
            LogMgr.log(6, "001 : intent action = " + action);
            action.hashCode();
            switch (action) {
                case "com.felicanetworks.tis.action.NOTIFICATION_SETTING":
                case "com.felicanetworks.tis.action.STOP_CHIP_ACCESS_SETTING":
                case "android.intent.action.MY_PACKAGE_REPLACED":
                    LogMgr.log(6, "002 : enqueueWork");
                    TapInteractionWorker.requestWork(context, intent);
                    break;
            }
        } else {
            LogMgr.log(6, "003 : sdk version is under Android P");
        }
        LogMgr.log(4, "999");
    }
}
