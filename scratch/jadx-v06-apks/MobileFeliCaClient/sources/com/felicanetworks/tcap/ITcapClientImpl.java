package com.felicanetworks.tcap;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.MfcListener;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.tcap.TcapClient;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.tcap.ITcapClient;

/* JADX INFO: loaded from: classes.dex */
public class ITcapClientImpl extends ITcapClient.Stub {
    private static ITcapClientImpl sMe = new ITcapClientImpl();
    private Context mContext;
    private TcapClient mTcapClientEntity;

    public static ITcapClientImpl getInstance() {
        return sMe;
    }

    private ITcapClientImpl() {
    }

    public synchronized void init(Context context, MfcListener mfcListener) throws IllegalArgumentException {
        if (context == null || mfcListener == null) {
            throw new IllegalArgumentException();
        }
        this.mContext = context;
        this.mTcapClientEntity = TcapClient.getInstance();
        this.mTcapClientEntity.setMfcListener(mfcListener);
    }

    @Override // com.felicanetworks.tcap.ITcapClient
    public FelicaResultInfo start(String str, DeviceList deviceList, ITcapClientEventListener iTcapClientEventListener, FelicaDeviceList felicaDeviceList, IBinder iBinder) throws RemoteException {
        return start(str, deviceList, iTcapClientEventListener, felicaDeviceList, iBinder, AccessConfig.getUserAgent(this.mContext));
    }

    public FelicaResultInfo start(String str, DeviceList deviceList, ITcapClientEventListener iTcapClientEventListener, FelicaDeviceList felicaDeviceList, IBinder iBinder, String str2) {
        if (felicaDeviceList == null) {
            LogMgr.log(2, "%s : felica == null", "700");
            return new FelicaResultInfo(1, "ITcapClientImpl.start() felica==null", 2, 24);
        }
        try {
            if (iBinder == null) {
                throw new FelicaException(1, 47);
            }
            if (iTcapClientEventListener == null) {
                throw new FelicaException(2, 26);
            }
            if (str2 == null) {
                throw new IllegalArgumentException();
            }
            this.mTcapClientEntity.start(str, felicaDeviceList, iTcapClientEventListener, deviceList, iBinder, str2);
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : Catch FelicaException message = %s, ID = %s", "703", e.getMessage(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()), Integer.valueOf(e.getType()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "704", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.tcap.ITcapClient
    public void stop() throws RemoteException {
        try {
            this.mTcapClientEntity.stop();
        } catch (Exception e) {
            LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
        }
    }

    public void doStop() {
        try {
            this.mTcapClientEntity.doStop();
        } catch (Exception e) {
            LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
        }
    }

    @Override // com.felicanetworks.tcap.ITcapClient
    public void notifyResult(byte[] bArr) throws RemoteException {
        try {
            this.mTcapClientEntity.notifyResult(bArr);
        } catch (Exception e) {
            LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
        }
    }

    @Override // com.felicanetworks.tcap.ITcapClient
    public void notifyError(String str) throws RemoteException {
        try {
            this.mTcapClientEntity.notifyError(str);
        } catch (Exception e) {
            LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
        }
    }

    public TcapClient getTcapClient() {
        return this.mTcapClientEntity;
    }
}
