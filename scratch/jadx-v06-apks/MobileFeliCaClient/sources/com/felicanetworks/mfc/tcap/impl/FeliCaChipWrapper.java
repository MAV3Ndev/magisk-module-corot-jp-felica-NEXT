package com.felicanetworks.mfc.tcap.impl;

import android.os.RemoteException;
import com.felicanetworks.mfc.tcap.InternalDevice;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.tcap.IFelicaDevice;

/* JADX INFO: loaded from: classes.dex */
public class FeliCaChipWrapper extends DeviceWrapper {
    static final int DEFAULT_RETRY_COUNT = 3;
    static final int DEFAULT_TIMEOUT = 1000;
    private IFelicaDevice mFelicaDevice;

    FeliCaChipWrapper(int i, InternalDevice internalDevice, IFelicaDevice iFelicaDevice) {
        super(i, internalDevice);
        this.mFelicaDevice = iFelicaDevice;
        try {
            this.mFelicaDevice.setTimeout(1000L);
            this.mFelicaDevice.setRetryCount(3L);
        } catch (RemoteException unused) {
        }
    }

    public void cancel() throws RemoteException {
        try {
            this.mFelicaDevice.cancel();
        } catch (RemoteException e) {
            LogMgr.log(1, "cancel() %s", "801");
            throw e;
        }
    }

    public int close() throws RemoteException {
        try {
            int iClose = this.mFelicaDevice.close();
            if (iClose == 0) {
                return iClose;
            }
            throw new RemoteException();
        } catch (RemoteException e) {
            LogMgr.log(1, "close() %s", "801");
            throw e;
        }
    }

    public byte[] execute(byte[] bArr) throws RemoteException {
        try {
            return this.mFelicaDevice.execute(bArr);
        } catch (RemoteException e) {
            LogMgr.log(1, "execute() %s", "801");
            throw e;
        }
    }

    public byte[] executeThru(byte[] bArr) throws RemoteException {
        try {
            return this.mFelicaDevice.executeThru(bArr);
        } catch (RemoteException e) {
            LogMgr.log(1, "executeThru() %s", "801");
            throw e;
        }
    }

    public void setTimeout(long j) throws RemoteException {
        try {
            this.mFelicaDevice.setTimeout(j);
        } catch (RemoteException e) {
            LogMgr.log(1, "setTimeout() %s", "801");
            throw e;
        }
    }

    public void setRetryCount(long j) throws RemoteException {
        try {
            this.mFelicaDevice.setRetryCount(j);
        } catch (RemoteException e) {
            LogMgr.log(1, "setRetryCount() %s", "801");
            throw e;
        }
    }
}
