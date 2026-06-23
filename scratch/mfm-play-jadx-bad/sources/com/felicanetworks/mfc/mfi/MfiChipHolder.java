package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.FSC;
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.ObfuscatedMsgUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class MfiChipHolder {
    private Felica mFelica = FelicaAdapter.getInstance().getFelica();
    private GpController mGpController;

    public MfiChipHolder() {
        this.mGpController = null;
        if (Property.isChipGP()) {
            this.mGpController = new GpController();
        }
    }

    public void init() throws GpException {
        LogMgr.log(5, "000");
        if (Property.isChipGP() && !isConnected()) {
            try {
                try {
                    this.mGpController.close();
                    this.mGpController.init(FelicaAdapter.getInstance().getApplicationContext());
                } catch (GpException e) {
                    LogMgr.log(1, "801 : Failed access to chip");
                    throw e;
                } catch (InterruptedException unused) {
                    LogMgr.log(1, "800 : cancel occured.");
                    throw new GpException(215, null, null);
                } catch (Exception e2) {
                    LogMgr.log(1, "802 : Unexpected Exception occurred: " + e2.getClass().getSimpleName() + " " + e2.getMessage());
                    throw new GpException(200, ObfuscatedMsgUtil.executionPoint(), "UnknownError");
                }
            } catch (Throwable th) {
                GpController gpController = this.mGpController;
                if (gpController != null) {
                    gpController.close();
                }
                throw th;
            }
        }
        LogMgr.log(5, "999");
    }

    public GpController getGpController() throws GpException {
        if (!isConnected()) {
            init();
        }
        return this.mGpController;
    }

    private boolean isConnected() {
        GpController gpController = this.mGpController;
        if (gpController != null) {
            return gpController.isInitialized();
        }
        return false;
    }

    public void cancel() {
        GpController gpController;
        LogMgr.log(5, "000");
        if (Property.isChipGP() && (gpController = this.mGpController) != null) {
            gpController.cancel();
        }
        LogMgr.log(5, "999");
    }

    public void reset() {
        GpController gpController;
        LogMgr.log(5, "000");
        if (Property.isChipGP() && (gpController = this.mGpController) != null) {
            gpController.closeChannel();
            this.mGpController.clearCancelFlag();
        }
        LogMgr.log(5, "999");
    }

    public Felica getFelica() throws MfiFelicaException {
        Felica felica;
        LogMgr.log(5, "000");
        synchronized (this) {
            if (this.mFelica == null) {
                LogMgr.log(2, "%s cleared", "700");
                throw new MfiFelicaException(215, null);
            }
            LogMgr.log(5, "999");
            felica = this.mFelica;
        }
        return felica;
    }

    public FSC getFSC() throws MfiFelicaException {
        FSC fsc;
        LogMgr.log(5, "000");
        synchronized (this) {
            if (this.mFelica == null) {
                LogMgr.log(2, "%s cleared", "700");
                throw new MfiFelicaException(215, null);
            }
            LogMgr.log(5, "999");
            fsc = FelicaAdapter.getInstance().getFSC();
        }
        return fsc;
    }

    public void open() throws MfiFelicaException, FelicaException {
        LogMgr.log(5, "000");
        synchronized (this) {
            LogMgr.log(3, "001 [access-felica] Felica.open() in");
            getFelica().open();
            LogMgr.log(3, "002 [access-felica] Felica.open() out");
        }
        LogMgr.log(5, "999");
    }

    public void discard() {
        GpController gpController;
        LogMgr.log(5, "000");
        synchronized (this) {
            if (this.mFelica != null) {
                try {
                    LogMgr.log(3, "001 [access-felica] Felica.close() in");
                    this.mFelica.close();
                    LogMgr.log(3, "002 [access-felica] Felica.close() out");
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s FelicaException:%s", "700", e.getMessage());
                    LogMgr.printStackTrace(7, e);
                }
                this.mFelica = null;
                if (Property.isChipGP() && (gpController = this.mGpController) != null) {
                    gpController.close();
                }
            } else if (Property.isChipGP()) {
                gpController.close();
            }
        }
        LogMgr.log(5, "999");
    }
}
