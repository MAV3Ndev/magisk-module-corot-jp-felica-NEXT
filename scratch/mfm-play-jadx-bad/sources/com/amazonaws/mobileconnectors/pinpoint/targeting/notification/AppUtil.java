package com.amazonaws.mobileconnectors.pinpoint.targeting.notification;

import android.app.ActivityManager;
import android.content.Context;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class AppUtil {
    private final Context applicationContext;

    AppUtil(Context context) {
        this.applicationContext = context;
    }

    boolean isAppInForeground() {
        List<ActivityManager.RunningAppProcessInfo> runningAppProcesses = ((ActivityManager) this.applicationContext.getSystemService("activity")).getRunningAppProcesses();
        String packageName = this.applicationContext.getApplicationContext().getPackageName();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            String str = runningAppProcessInfo.processName;
            if (100 == runningAppProcessInfo.importance && packageName.equals(str)) {
                return true;
            }
        }
        return false;
    }
}
