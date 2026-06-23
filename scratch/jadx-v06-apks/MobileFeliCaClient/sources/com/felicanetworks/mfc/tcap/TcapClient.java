package com.felicanetworks.mfc.tcap;

import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.MfcListener;
import com.felicanetworks.mfc.tcap.impl.StoppableTcapClient;
import com.felicanetworks.mfc.tcap.impl.TcapDevice;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.tcap.Device;
import com.felicanetworks.tcap.DeviceList;
import com.felicanetworks.tcap.FelicaDeviceList;
import com.felicanetworks.tcap.IFelicaDevice;
import com.felicanetworks.tcap.ITcapClientEventListener;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class TcapClient {
    private static final String EXC_INVALID_URL = "The specified URL is null.";
    private static final String EXC_RUNTIME = "java.lang.RuntimeException: A runtime exception was thrown within TcapClient.";
    private static final String KILLED_BY_USER = "java.lang.Exception: killed by user in TcapClient.";
    private static TcapClient sInstance;
    private LocalDeathRecipient mDeathRecipient;
    private DeviceList mDeviceList;
    private FelicaDeviceList mFelicaDeviceList;
    private boolean mKilled;
    private MfcListener mMfcListener;
    private boolean mOnline;
    private String mOperationRequestErrMsg;
    private byte[] mOperationRequestResult;
    private boolean mOperationRequestSuccessed;
    private int mPid;
    private ITcapClientEventListener mTcapClientEventListener;
    private int mUid;
    private Object mOperationRequestLock = new Object();
    private FeliCaClientEventListener mFelicaClientEventHooker = new FeliCaClientEventListener() { // from class: com.felicanetworks.mfc.tcap.TcapClient.1
        @Override // com.felicanetworks.mfc.tcap.FeliCaClientEventListener
        public void finished(int i) {
            try {
                TcapClient.this.terminate();
                try {
                    TcapClient.this.mTcapClientHooker.finished(i);
                } catch (Exception unused) {
                    LogMgr.log(1, "%s", "800");
                }
            } catch (Exception unused2) {
                LogMgr.log(1, "%s", "801");
                try {
                    TcapClient.this.mTcapClientHooker.errorOccurred(1, TcapClient.EXC_RUNTIME);
                } catch (Exception unused3) {
                    LogMgr.log(1, "%s", "802");
                }
            }
        }

        @Override // com.felicanetworks.mfc.tcap.FeliCaClientEventListener
        public void errorOccurred(int i, String str) {
            try {
                TcapClient.this.terminate();
                try {
                    TcapClient.this.mTcapClientHooker.errorOccurred(TcapClient.this.convertFeliCaClientEventListenerType(i), str);
                } catch (Exception unused) {
                    LogMgr.log(1, "%s", "800");
                }
            } catch (Exception unused2) {
                LogMgr.log(1, "%s", "801");
                try {
                    TcapClient.this.mTcapClientHooker.errorOccurred(1, TcapClient.EXC_RUNTIME);
                } catch (Exception unused3) {
                    LogMgr.log(1, "%s", "802");
                }
            }
        }
    };
    private TcapClientEventListener mTcapClientHooker = new TcapClientEventListener() { // from class: com.felicanetworks.mfc.tcap.TcapClient.2
        @Override // com.felicanetworks.mfc.tcap.TcapClientEventListener
        public void finished(int i) {
            ITcapClientEventListener iTcapClientEventListener = TcapClient.this.mTcapClientEventListener;
            TcapClient.this.unregisterBinder();
            TcapClient.this.mOnline = false;
            try {
                iTcapClientEventListener.finished(i);
            } catch (Exception unused) {
                LogMgr.log(1, "%s", "800");
            }
        }

        @Override // com.felicanetworks.mfc.tcap.TcapClientEventListener
        public void errorOccurred(int i, String str) {
            ITcapClientEventListener iTcapClientEventListener = TcapClient.this.mTcapClientEventListener;
            TcapClient.this.unregisterBinder();
            TcapClient.this.mOnline = false;
            try {
                iTcapClientEventListener.errorOccurred(i, str);
            } catch (Exception unused) {
                LogMgr.log(1, "%s", "800");
            }
        }

        @Override // com.felicanetworks.mfc.tcap.TcapClientEventListener
        public byte[] operationRequested(int i, String str, byte[] bArr) throws Exception {
            try {
                TcapClient.this.mTcapClientEventListener.operationRequested(i, str, bArr);
            } catch (RemoteException unused) {
                LogMgr.log(1, "%s", "800");
            }
            synchronized (TcapClient.this.mOperationRequestLock) {
                while (!TcapClient.this.mKilled) {
                    try {
                        TcapClient.this.mOperationRequestLock.wait();
                    } catch (InterruptedException unused2) {
                        LogMgr.log(1, "%s", "801");
                    }
                }
                TcapClient.this.mKilled = false;
            }
            if (TcapClient.this.mOperationRequestSuccessed) {
                return TcapClient.this.mOperationRequestResult;
            }
            throw new Exception(TcapClient.this.mOperationRequestErrMsg);
        }
    };
    private FeliCaClient mFelicaClient = new StoppableTcapClient();

    /* JADX INFO: Access modifiers changed from: private */
    public int convertFeliCaClientEventListenerType(int i) {
        if (i == 0) {
            return 1;
        }
        if (i == 1) {
            return 2;
        }
        if (i == 2) {
            return 3;
        }
        if (i != 3) {
            return i != 100 ? 1 : 100;
        }
        return 4;
    }

    private TcapClient() throws SecurityException {
    }

    public synchronized void setMfcListener(MfcListener mfcListener) {
        if (mfcListener != null) {
            this.mMfcListener = mfcListener;
        }
    }

    public static synchronized TcapClient getInstance() throws SecurityException {
        if (sInstance == null) {
            sInstance = new TcapClient();
        }
        return sInstance;
    }

    public void start(String str, FelicaDeviceList felicaDeviceList, ITcapClientEventListener iTcapClientEventListener, DeviceList deviceList, IBinder iBinder, String str2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (iBinder == null) {
            LogMgr.log(1, "%s binder == null", "800");
            throw new FelicaException(1, 47);
        }
        if (str == null) {
            LogMgr.log(1, "%s", "800");
            throw new IllegalArgumentException(EXC_INVALID_URL);
        }
        if (felicaDeviceList == null) {
            LogMgr.log(1, "%s", "801");
            throw new FelicaException(2, 24);
        }
        if (deviceList == null) {
            LogMgr.log(1, "%s", "802");
            throw new FelicaException(2, 25);
        }
        try {
            deviceList.checkFormat();
            if (iTcapClientEventListener == null) {
                LogMgr.log(1, "%s", "804");
                throw new FelicaException(2, 26);
            }
            synchronized (this) {
                checkNotOnline();
                try {
                    if (!registerBinder(iBinder)) {
                        LogMgr.log(1, "%s binder == null", "800");
                        throw new FelicaException(1, 47);
                    }
                    try {
                        this.mFelicaDeviceList = felicaDeviceList;
                        this.mTcapClientEventListener = iTcapClientEventListener;
                        this.mDeviceList = deviceList;
                        this.mFelicaClient.removeAllDevices();
                        setDevices();
                        this.mFelicaClient.setUrl(str);
                        this.mFelicaClient.setUserAgent(str2);
                        this.mFelicaClient.setEventListener(this.mFelicaClientEventHooker);
                        this.mFelicaClient.start(true, null);
                        this.mOnline = true;
                        this.mKilled = false;
                        this.mPid = Binder.getCallingPid();
                        this.mUid = Binder.getCallingUid();
                    } catch (Exception unused) {
                        LogMgr.log(1, "%s", "807");
                        throw new FelicaException(1, 27);
                    }
                } finally {
                    if (!this.mOnline) {
                        unregisterBinder();
                    }
                }
            }
        } catch (Exception unused2) {
            LogMgr.log(1, "%s", "803");
            throw new FelicaException(1, 27);
        }
    }

    public synchronized void stop() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (this.mOnline) {
            checkPidUid();
            this.mFelicaClient.stop(false);
            LogMgr.log(4, "%s", "999");
        }
    }

    public synchronized void doStop() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (this.mOnline) {
            this.mFelicaClient.stop(false);
            LogMgr.log(4, "%s", "999");
        }
    }

    public synchronized void notifyResult(byte[] bArr) throws FelicaException {
        try {
            try {
                checkPidUid();
                synchronized (this.mOperationRequestLock) {
                    this.mOperationRequestSuccessed = true;
                    this.mOperationRequestResult = bArr;
                    this.mKilled = true;
                    this.mOperationRequestLock.notifyAll();
                }
            } catch (FelicaException e) {
                LogMgr.log(1, "%s", "800");
                throw e;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void notifyError(String str) throws FelicaException {
        try {
            try {
                checkPidUid();
                synchronized (this.mOperationRequestLock) {
                    this.mOperationRequestSuccessed = false;
                    this.mOperationRequestErrMsg = str;
                    this.mKilled = true;
                    this.mOperationRequestLock.notifyAll();
                }
            } catch (FelicaException e) {
                LogMgr.log(1, "%s", "800");
                throw e;
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void kill() {
        if (this.mOnline) {
            synchronized (this.mOperationRequestLock) {
                this.mOperationRequestSuccessed = false;
                this.mOperationRequestErrMsg = KILLED_BY_USER;
                this.mKilled = true;
                this.mOperationRequestLock.notifyAll();
            }
            this.mPid = 0;
            this.mUid = 0;
            this.mOnline = false;
            this.mFelicaClient.stop(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void terminate() throws Exception {
        this.mPid = 0;
        this.mUid = 0;
        this.mOnline = false;
    }

    private void checkNotOnline() throws FelicaException {
        if (this.mOnline) {
            LogMgr.log(1, "%s", "800");
            throw new FelicaException(2, 2);
        }
    }

    private void setDevices() throws FeliCaClientException {
        Hashtable<Integer, Device> devices = this.mDeviceList.getDevices();
        int[] iArr = new int[devices.size()];
        Enumeration<Integer> enumerationKeys = devices.keys();
        int i = 0;
        while (enumerationKeys.hasMoreElements()) {
            iArr[i] = enumerationKeys.nextElement().intValue();
            i++;
        }
        int i2 = 0;
        while (i2 < i - 1) {
            int i3 = i2 + 1;
            for (int i4 = i3; i4 < i; i4++) {
                if (iArr[i2] > iArr[i4]) {
                    int i5 = iArr[i2];
                    iArr[i2] = iArr[i4];
                    iArr[i4] = i5;
                }
            }
            i2 = i3;
        }
        IFelicaDevice iFelicaDevice = null;
        for (int i6 = 0; i6 < iArr.length; i6++) {
            Device device = devices.get(new Integer(iArr[i6]));
            String type = device.getType();
            String name = device.getName();
            IFelicaDevice iFelicaDevice2 = iFelicaDevice;
            int i7 = 0;
            while (i7 < this.mFelicaDeviceList.size()) {
                iFelicaDevice2 = this.mFelicaDeviceList.get(i7);
                if (iArr[i6] == iFelicaDevice2.getId()) {
                    break;
                }
                i7++;
                iFelicaDevice2 = null;
            }
            iFelicaDevice = iFelicaDevice2;
            if (iFelicaDevice != null) {
                this.mFelicaClient.addDevice(new TcapDevice(type, name, this.mTcapClientHooker, true), iArr[i6], iFelicaDevice);
            } else if (iArr[i6] == 2) {
                this.mFelicaClient.addDevice(new TcapDevice(type, name, this.mTcapClientHooker, true), iArr[i6]);
            } else {
                this.mFelicaClient.addDevice(new TcapDevice(type, name, this.mTcapClientHooker, iArr[i6] < 4096), iArr[i6]);
            }
        }
    }

    private synchronized boolean registerBinder(IBinder iBinder) {
        if (iBinder == null) {
            return false;
        }
        try {
            this.mDeathRecipient = new LocalDeathRecipient(iBinder);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void unregisterBinder() {
        IBinder binder;
        if (this.mDeathRecipient != null && (binder = this.mDeathRecipient.getBinder()) != null) {
            binder.unlinkToDeath(this.mDeathRecipient, 0);
        }
        this.mDeathRecipient = null;
    }

    private class LocalDeathRecipient implements IBinder.DeathRecipient {
        IBinder mBinder;

        LocalDeathRecipient(IBinder iBinder) throws RemoteException {
            iBinder.linkToDeath(this, 0);
            this.mBinder = iBinder;
        }

        IBinder getBinder() {
            return this.mBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (TcapClient.this) {
                TcapClient.this.mDeathRecipient = null;
            }
            TcapClient.this.mMfcListener.mfcCancel();
        }
    }

    private void checkPidUid() throws FelicaException {
        if (this.mPid == 0 && this.mUid == 0) {
            return;
        }
        if (this.mPid != Binder.getCallingPid() || this.mUid != Binder.getCallingUid()) {
            throw new FelicaException(2, 39);
        }
    }
}
