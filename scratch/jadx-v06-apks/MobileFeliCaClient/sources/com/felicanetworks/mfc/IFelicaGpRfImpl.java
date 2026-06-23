package com.felicanetworks.mfc;

import android.content.Context;
import com.felicanetworks.mfc.felica.FelicaMockRf;
import com.felicanetworks.mfc.felica.FelicaRf;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;

/* JADX INFO: loaded from: classes.dex */
public class IFelicaGpRfImpl extends IFelicaRfImpl {
    private static IFelicaGpRfImpl sMe = new IFelicaGpRfImpl();

    public static IFelicaGpRfImpl getInstance() {
        return sMe;
    }

    private IFelicaGpRfImpl() {
    }

    @Override // com.felicanetworks.mfc.IFelicaRfImpl
    public synchronized void init(Context context, MfcListener mfcListener) throws IllegalArgumentException {
        if (context == null || mfcListener == null) {
            throw new IllegalArgumentException();
        }
        if (AccessConfig.isFelicaReaderWriterSupported()) {
            this.mFelicaEntity = FelicaRf.getInstance();
        } else {
            this.mFelicaEntity = FelicaMockRf.getInstance();
        }
        this.mFelicaEntity.setContext(context);
        this.mFelicaEntity.setMfcListener(mfcListener);
    }
}
