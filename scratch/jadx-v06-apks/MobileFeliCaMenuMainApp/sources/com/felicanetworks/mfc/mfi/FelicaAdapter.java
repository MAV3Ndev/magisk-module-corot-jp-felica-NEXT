package com.felicanetworks.mfc.mfi;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.felicanetworks.mfc.FSC;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.mfi.ForegroundServiceSetupProvider;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
public class FelicaAdapter extends Service {
    private static final int DATA_SAVER_WHITELIST_CHECK_NONE = 0;
    private static final int DATA_SAVER_WHITELIST_CHECK_OFF = 3;
    private static final int DATA_SAVER_WHITELIST_CHECK_ON_LISTED = 2;
    private static final int DATA_SAVER_WHITELIST_CHECK_ON_NOT_LISTED = 1;
    private static final int DOZE_WHITELIST_CHECK_FALSE = 1;
    private static final int DOZE_WHITELIST_CHECK_NONE = 0;
    private static final int DOZE_WHITELIST_CHECK_TRUE = 2;
    private static final int SERVICE_CONNECT_TIMEOUT = 1000;
    private static FelicaAdapter sInstance;
    private FSC mFSC;
    private Felica mFelica;
    private FelicaWrapper mFelicaWrapper;
    private final CountDownLatch mBindServiceLatch = new CountDownLatch(2);
    private ServiceConnection felicaServiceConnection = new ServiceConnection() { // from class: com.felicanetworks.mfc.mfi.FelicaAdapter.1
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogMgr.log(4, "%s", "000");
            FelicaAdapter.this.mFelica = ((Felica.LocalBinder) iBinder).getInstance();
            FelicaAdapter.this.mFelicaWrapper = FelicaWrapper.getInstance();
            FelicaAdapter.this.mFelicaWrapper.setFelica(FelicaAdapter.this.mFelica);
            FelicaAdapter.this.mBindServiceLatch.countDown();
            LogMgr.log(4, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogMgr.log(4, "%s", "000");
            LogMgr.log(4, "%s", "999");
        }
    };
    private ServiceConnection fscServiceConnection = new ServiceConnection() { // from class: com.felicanetworks.mfc.mfi.FelicaAdapter.2
        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            LogMgr.log(4, "%s", "000");
            FelicaAdapter.this.mFSC = ((FSC.LocalBinder) iBinder).getInstance();
            FelicaAdapter.this.mBindServiceLatch.countDown();
            LogMgr.log(4, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            LogMgr.log(4, "%s", "000");
            LogMgr.log(4, "%s", "999");
        }
    };
    private MfiServiceImpl mIFelicaImpl = null;
    private MfiListener mMfcListener = new MfiListener() { // from class: com.felicanetworks.mfc.mfi.FelicaAdapter.3
        @Override // com.felicanetworks.mfc.mfi.MfiListener
        public void mfiCancel() {
            synchronized (FelicaAdapter.this) {
                FelicaAdapter.this._mfiCancel();
            }
        }
    };

    public FelicaAdapter() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    public static FelicaAdapter getInstance() {
        return sInstance;
    }

    @Override // android.app.Service
    public void onCreate() {
        LogMgr.log(4, "%s", "000");
        sInstance = this;
        FelicaWrapper felicaWrapper = FelicaWrapper.getInstance();
        this.mFelicaWrapper = felicaWrapper;
        felicaWrapper.setContext(getApplicationContext());
        this.mFelicaWrapper.setMfiListener(this.mMfcListener);
        MfiServiceImpl mfiServiceImpl = MfiServiceImpl.getInstance();
        this.mIFelicaImpl = mfiServiceImpl;
        mfiServiceImpl.init(this.mFelicaWrapper);
        LogMgr.log(4, "%s : mIFelicaImpl = %s", "999", this.mIFelicaImpl);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        ForegroundServiceSetupProvider.requestForegroundService(this, ForegroundServiceSetupProvider.Type.FELICA_ADAPTER);
        bindFelica();
        bindFSC();
        LogMgr.log(4, "%s : mIFelicaImpl = %s", "999", this.mIFelicaImpl);
        return this.mIFelicaImpl;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(4, "%s", "000");
        if (this.mIFelicaImpl != null) {
            LogMgr.log(7, "%s", "001");
            this.mIFelicaImpl.doLogout(true, null, true);
            this.mIFelicaImpl.doClose();
            this.mIFelicaImpl.doInactivateFelica(false);
        }
        unbindService(this.felicaServiceConnection);
        unbindService(this.fscServiceConnection);
        stopForeground(true);
        LogMgr.log(4, "%s", "999");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(4, "%s", "000");
        if (this.mIFelicaImpl != null) {
            LogMgr.log(7, "%s", "001");
            this.mIFelicaImpl.doLogout(true, null, true);
            this.mIFelicaImpl.doClose();
            this.mIFelicaImpl.doInactivateFelica(false);
        }
        LogMgr.log(4, "%s", "999");
        super.onDestroy();
    }

    public MfiServiceImpl getMfiServiceImpl() {
        return this.mIFelicaImpl;
    }

    public FelicaWrapper getFelicaWrapper() {
        return this.mFelicaWrapper;
    }

    public Felica getFelica() {
        return this.mFelica;
    }

    public FSC getFSC() {
        return this.mFSC;
    }

    boolean waitForBindService() {
        boolean zAwait;
        LogMgr.log(6, "%s", "000");
        try {
            zAwait = this.mBindServiceLatch.await(1000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            LogMgr.printStackTrace(7, e);
            zAwait = false;
        }
        LogMgr.log(6, "%s connected=%b", "999", Boolean.valueOf(zAwait));
        return zAwait;
    }

    private void bindFelica() {
        LogMgr.log(4, "%s", "000");
        if (this.mFelica != null) {
            LogMgr.log(4, "%s", "999");
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, Felica.class);
        if (!bindService(intent, this.felicaServiceConnection, 1)) {
            LogMgr.log(1, "%s Failed to connect to Felica Service.", "800");
        }
        LogMgr.log(4, "%s", "999");
    }

    private void bindFSC() {
        LogMgr.log(4, "%s", "000");
        if (this.mFSC != null) {
            LogMgr.log(4, "%s", "999");
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, FSC.class);
        if (!bindService(intent, this.fscServiceConnection, 1)) {
            LogMgr.log(1, "%s Failed to connect to FSC Service.", "800");
        }
        LogMgr.log(4, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _mfiCancel() {
        this.mIFelicaImpl.doLogout(true, null, true);
        this.mIFelicaImpl.doClose();
        this.mIFelicaImpl.doInactivateFelica(false);
    }
}
