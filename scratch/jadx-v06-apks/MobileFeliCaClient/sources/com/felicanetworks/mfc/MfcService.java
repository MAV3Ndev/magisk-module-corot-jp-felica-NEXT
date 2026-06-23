package com.felicanetworks.mfc;

import android.app.Application;
import android.util.Log;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;
import com.felicanetworks.tcap.ITcapClientImpl;

/* JADX INFO: loaded from: classes.dex */
public class MfcService extends Application {
    static final String TAG = "MfcService";
    private static MfcService sInstance;
    private IFelica mIFelica;
    private ITcapClientImpl mITcapClient;
    private boolean mIsGpDevice = false;
    private MfcListener mMfcListener = new MfcListener() { // from class: com.felicanetworks.mfc.MfcService.1
        @Override // com.felicanetworks.mfc.MfcListener
        public void mfcCancel() {
            synchronized (MfcService.this) {
                MfcService.this._mfcCancel();
            }
        }
    };

    @Override // android.app.Application
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "Starting MFC service");
        sInstance = this;
        this.mIsGpDevice = AccessConfig.isGpDevice();
        if (this.mIsGpDevice) {
            this.mIFelica = IFelicaGpDeviceImpl.getInstance();
            ((IFelicaGpDeviceImpl) this.mIFelica).init(this, this.mMfcListener);
        } else {
            this.mIFelica = IFelicaImpl.getInstance();
            ((IFelicaImpl) this.mIFelica).init(this, this.mMfcListener);
        }
        this.mITcapClient = ITcapClientImpl.getInstance();
        this.mITcapClient.init(this, this.mMfcListener);
    }

    public static MfcService getInstance() {
        return sInstance;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void _mfcCancel() {
        this.mITcapClient.doStop();
        if (this.mIsGpDevice) {
            ((IFelicaGpDeviceImpl) this.mIFelica).doClose(false);
            ((IFelicaGpDeviceImpl) this.mIFelica).doInactivateFelica(false);
        } else {
            ((IFelicaImpl) this.mIFelica).doClose(false);
            ((IFelicaImpl) this.mIFelica).doInactivateFelica(false);
        }
    }
}
