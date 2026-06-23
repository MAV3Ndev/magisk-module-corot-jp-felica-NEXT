package com.felicanetworks.semc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.felicanetworks.semc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class SemClientAdapter extends Service {
    private static SemClientAdapter sInstance;
    private ISemClientImpl mISemClientImpl = null;

    public SemClientAdapter() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
    }

    public static SemClientAdapter getInstance() {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999");
        return sInstance;
    }

    @Override // android.app.Service
    public void onCreate() {
        LogMgr.log(6, "000");
        sInstance = this;
        ISemClientImpl iSemClientImpl = ISemClientImpl.getInstance();
        this.mISemClientImpl = iSemClientImpl;
        iSemClientImpl.init(getApplicationContext());
        LogMgr.log(6, "999 mISemClientImpl=[" + this.mISemClientImpl + "]");
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(6, "000");
        LogMgr.log(6, "999 mISemClientImpl=[" + this.mISemClientImpl + "]");
        return this.mISemClientImpl;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(6, "000");
        boolean zOnUnbind = super.onUnbind(intent);
        if (this.mISemClientImpl != null) {
            LogMgr.log(8, "001 mISemClientImpl is Not Null");
            this.mISemClientImpl.disconnect(false);
        }
        LogMgr.log(6, "999");
        return zOnUnbind;
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(6, "000");
        super.onDestroy();
        if (this.mISemClientImpl != null) {
            LogMgr.log(8, "001 mISemClientImpl is Not Null");
            this.mISemClientImpl.disconnect(false);
        }
        LogMgr.log(6, "999");
    }
}
