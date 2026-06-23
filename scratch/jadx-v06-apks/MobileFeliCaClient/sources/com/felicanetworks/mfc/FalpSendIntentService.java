package com.felicanetworks.mfc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FalpSendIntentService extends Service {
    public FalpSendIntentService() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    @Override // android.app.Service
    public void onCreate() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        return null;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
        super.onDestroy();
    }
}
