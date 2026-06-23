package com.felicanetworks.mfc.felica;

import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.felica.Felica;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaMockRf extends FelicaRf {
    private static FelicaMockRf sInstance;

    private FelicaMockRf() {
    }

    @Override // com.felicanetworks.mfc.felica.FelicaRf
    protected void initChipController() {
        this.mChipController = null;
    }

    public static synchronized FelicaMockRf getInstance() {
        if (sInstance == null) {
            sInstance = new FelicaMockRf();
        }
        return sInstance;
    }

    @Override // com.felicanetworks.mfc.felica.FelicaRf
    public synchronized void open(IBinder iBinder) throws FelicaException {
        LogMgr.log(4, "%s", "000");
        open(true);
        LogMgr.log(4, "%s", "001");
        this.mOpenedApp = new Felica.FelicaAppInfo();
        this.mOpenedApp.setPid(Binder.getCallingPid());
        this.mOpenedApp.setUid(Binder.getCallingUid());
        registerBinder(iBinder);
    }

    private synchronized void open(boolean z) throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (z && this.mOpenedApp != null) {
            if (this.mOpenedApp.getPid() != Binder.getCallingPid() || this.mOpenedApp.getUid() != Binder.getCallingUid()) {
                throw new FelicaException(2, 39, new AppInfo(this.mOpenedApp.getPid()));
            }
        } else {
            checkNotOnline();
            if (this.mOpened) {
                return;
            }
            this.mOpened = true;
            this.mFscAdapter = null;
            this.mSelected = false;
        }
    }

    @Override // com.felicanetworks.mfc.felica.FelicaRf, com.felicanetworks.mfc.felica.Felica
    public synchronized void close() throws FelicaException {
        doClose(true);
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.felica.FelicaRf
    public synchronized void doClose(boolean z) throws FelicaException {
        LogMgr.log(4, "%s, isCheckProcess = %s, callingPid=%d, callingUid=%d", "000", Boolean.valueOf(z), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(Binder.getCallingUid()));
        if (this.mOpened) {
            if (z) {
                checkOpenedApp();
            }
            if (this.mFscAdapter != null) {
                LogMgr.log(2, "%s, force stop online", "701");
                this.mFscAdapter.kill();
            }
            super.doClose();
            this.mFscAdapter = null;
            this.mOpenedApp = null;
            unregisterBinder();
            LogMgr.log(4, "%s", "999");
        }
    }

    @Override // com.felicanetworks.mfc.felica.FelicaRf
    public synchronized void reset() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        this.mNodeCodeSize = 2;
        this.mSelected = false;
        this.mSystemInfo = null;
    }

    @Override // com.felicanetworks.mfc.felica.FelicaRf, com.felicanetworks.mfc.felica.Felica
    public synchronized void select(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d", "000", Integer.valueOf(i));
        checkOpenedApp();
        if (i >= 0 && i <= 65535) {
            checkOpened();
            checkNotOnline();
            throw new FelicaException(1, 9);
        }
        LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(i));
        throw new IllegalArgumentException("The specified System Code is out of range.");
    }

    @Override // com.felicanetworks.mfc.felica.FelicaRf
    public synchronized void resetInner() throws FelicaException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        checkOpened();
        this.mNodeCodeSize = 2;
        this.mSelected = false;
        this.mSystemInfo = null;
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized boolean isConnected() {
        return false;
    }
}
