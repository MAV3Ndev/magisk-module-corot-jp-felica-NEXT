package com.felicanetworks.mfc;

import android.os.RemoteException;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.tcap.IFelicaDevice;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public class TcapFelicaDevice extends IFelicaDevice.Stub {
    private static final int DEFAULT_RETRY_COUNT = 3;
    private static final int DEFAULT_TIMEOUT = 1000;
    private static final int DEVICE_ID = 1;
    private static final long MAX_RETRY_COUNT = 10;
    private static final long MAX_TIMEOUT = 60000;
    private static final long MIN_RETRY_COUNT = 0;
    private static final long MIN_TIMEOUT = 0;
    private int mRetryCount;
    private IFelicaRfImpl mRf;
    private IFelicaSeImpl mSe;
    private int mTimeout;
    private static final byte[] CODE_TURNOFF_RF_POWER_COMMAND = {3, -52, 18};
    private static final byte[] SUCCESS_TURNOFF_RF_POWER_COMMAND = {5, -51, 18, 0, 0};
    private static final byte[] FAIL_TURNOFF_RF_POWER_COMMAND = {5, -51, 18, -1, -3};

    @Override // com.felicanetworks.tcap.IFelicaDevice
    public int getId() throws RemoteException {
        return 1;
    }

    public TcapFelicaDevice(IFelicaSeImpl iFelicaSeImpl, IFelicaRfImpl iFelicaRfImpl) throws IllegalArgumentException {
        if (iFelicaSeImpl == null || iFelicaRfImpl == null) {
            throw new IllegalArgumentException();
        }
        this.mSe = iFelicaSeImpl;
        this.mRf = iFelicaRfImpl;
        this.mRetryCount = 3;
        this.mTimeout = 1000;
    }

    @Override // com.felicanetworks.tcap.IFelicaDevice
    public void cancel() throws RemoteException {
        this.mSe.cancelOfflineInner();
        this.mRf.cancelOfflineInner();
    }

    @Override // com.felicanetworks.tcap.IFelicaDevice
    public int close() throws RemoteException {
        return (this.mRf.resetInner().getExceptionType() == 0 && this.mSe.resetInner(true, false, false).getExceptionType() == 0) ? 0 : -1;
    }

    @Override // com.felicanetworks.tcap.IFelicaDevice
    public byte[] execute(byte[] bArr) throws RemoteException {
        if (Arrays.equals(bArr, CODE_TURNOFF_RF_POWER_COMMAND)) {
            if (this.mRf.resetInner().getExceptionType() == 0) {
                return SUCCESS_TURNOFF_RF_POWER_COMMAND;
            }
            return FAIL_TURNOFF_RF_POWER_COMMAND;
        }
        if (this.mRf.isConnected() && this.mRf.resetInner().getExceptionType() != 0) {
            LogMgr.log(1, "RF disconnect failed");
            return null;
        }
        int iCurrentTimeMillis = this.mTimeout;
        if (!this.mSe.isConnected()) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.mSe.connectInner(iCurrentTimeMillis).getExceptionType() != 0) {
                LogMgr.log(1, "selectInner failed!!");
                return null;
            }
            iCurrentTimeMillis -= (int) (System.currentTimeMillis() - jCurrentTimeMillis);
        }
        FelicaResultInfoByteArray felicaResultInfoByteArrayExecuteFelicaCommandInner = this.mSe.executeFelicaCommandInner(bArr, iCurrentTimeMillis, this.mRetryCount);
        if (felicaResultInfoByteArrayExecuteFelicaCommandInner.getValue() == null) {
            LogMgr.log(2, "executeFelicaCommandInner return null!!");
            return null;
        }
        LogMgr.log(6, "executeFelicaCommandInner return len=" + felicaResultInfoByteArrayExecuteFelicaCommandInner.getValue().length);
        return felicaResultInfoByteArrayExecuteFelicaCommandInner.getValue();
    }

    @Override // com.felicanetworks.tcap.IFelicaDevice
    public byte[] executeThru(byte[] bArr) throws RemoteException {
        FelicaResultInfo felicaResultInfoResetInner;
        if (this.mSe.isConnected()) {
            felicaResultInfoResetInner = this.mSe.resetInner(false, false, true);
            if (felicaResultInfoResetInner.getExceptionType() != 0) {
                LogMgr.log(1, "RF disconnect failed");
                return null;
            }
        } else {
            felicaResultInfoResetInner = null;
        }
        FelicaResultInfo felicaResultInfoConnectInner = felicaResultInfoResetInner;
        FelicaResultInfoByteArray felicaResultInfoByteArrayExecuteFelicaCommandInner = null;
        for (int i = 0; i <= this.mRetryCount; i++) {
            int iCurrentTimeMillis = this.mTimeout;
            if (!this.mRf.isConnected()) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                felicaResultInfoConnectInner = this.mRf.connectInner(iCurrentTimeMillis);
                if (felicaResultInfoConnectInner.getExceptionType() != 0) {
                    LogMgr.log(2, "selectInner failed!! retry=" + i);
                } else {
                    iCurrentTimeMillis -= (int) (System.currentTimeMillis() - jCurrentTimeMillis);
                }
            }
            felicaResultInfoByteArrayExecuteFelicaCommandInner = this.mRf.executeFelicaCommandInner(bArr, iCurrentTimeMillis, 0);
            if (felicaResultInfoByteArrayExecuteFelicaCommandInner.getValue() != null) {
                break;
            }
            LogMgr.log(2, "executeFelicaCommandInner return null!! retry=" + i);
        }
        if (felicaResultInfoConnectInner != null && felicaResultInfoConnectInner.getExceptionType() != 0) {
            LogMgr.log(1, "selectInner failed!!");
            return null;
        }
        if (felicaResultInfoByteArrayExecuteFelicaCommandInner.getValue() == null) {
            LogMgr.log(1, "executeFelicaCommandInner return null!!");
            return null;
        }
        LogMgr.log(6, "executeFelicaCommandInner return len=" + felicaResultInfoByteArrayExecuteFelicaCommandInner.getValue().length);
        return felicaResultInfoByteArrayExecuteFelicaCommandInner.getValue();
    }

    @Override // com.felicanetworks.tcap.IFelicaDevice
    public void setRetryCount(long j) throws RemoteException {
        this.mRetryCount = (int) Math.max(Math.min(j, MAX_RETRY_COUNT), 0L);
    }

    @Override // com.felicanetworks.tcap.IFelicaDevice
    public void setTimeout(long j) throws RemoteException {
        this.mTimeout = (int) Math.max(Math.min(j, MAX_TIMEOUT), 0L);
    }
}
