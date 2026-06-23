package com.felicanetworks.mfc.mfi;

import android.os.RemoteException;
import com.felicanetworks.mfc.DeviceList;
import com.felicanetworks.mfc.FSC;
import com.felicanetworks.mfc.FSCEventListener;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.IFSCEventListener;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class MfcOnline {
    private static final String KILLED_BY_USER = "java.lang.Exception: killed by user in MfcOnline.";
    private static MfcOnline sInstance;
    private boolean mAborted;
    private FelicaWrapper mFelicaWrapper;
    private boolean mKilled;
    private boolean mOnline;
    private boolean mOperationRequestSuccessed;
    private MyFSCEventListener mFscEventListener = new MyFSCEventListener();
    private IFSCEventListener mIFSCEventListener = null;
    private Object mOperationRequestLock = new Object();
    private byte[] mOperationRequestResult = null;
    private String mOperationRequestErrMsg = null;

    private MfcOnline() {
        LogMgr.log(4, "%s", "000");
        LogMgr.log(4, "%s", "999");
    }

    public static synchronized MfcOnline getInstance() {
        LogMgr.log(4, "%s", "000");
        if (sInstance == null) {
            LogMgr.log(4, "%s", "001");
            sInstance = new MfcOnline();
        }
        LogMgr.log(4, "%s", "999");
        return sInstance;
    }

    void setFelicaWrapper(FelicaWrapper felicaWrapper) {
        this.mFelicaWrapper = felicaWrapper;
    }

    FelicaResultInfo start(String str, DeviceList deviceList, IFSCEventListener iFSCEventListener, IMfiFelica iMfiFelica) throws RemoteException {
        LogMgr.log(4, "%s, url=%s, deviceList=%s, fscEventListener=%s, felica=%s", "000", str, deviceList, iFSCEventListener, iMfiFelica);
        new FelicaResultInfo(1, (String) null);
        if (iMfiFelica == null) {
            LogMgr.log(2, "%s : felica == null", "700");
            return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() felica==null", 2, 24);
        }
        if (iFSCEventListener == null) {
            LogMgr.log(2, "%s : fscEventListener == null", "701");
            return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() fscEventListener==null", 2, 26);
        }
        if (!(iMfiFelica.asBinder() instanceof MfiServiceImpl)) {
            LogMgr.log(2, "%s : binder != IFelica.Stub", "702");
            return new FelicaResultInfo(1, "MfcOnline.start() binder != IFelica.Stub", 1, 47);
        }
        LogMgr.log(7, "%s", "001");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    try {
                        try {
                            if (this.mOnline) {
                                throw new FelicaException(2, 2);
                            }
                            this.mFelicaWrapper.checkPidUid();
                            this.mFelicaWrapper.checkNotLoggedIn();
                            this.mFelicaWrapper.checkOpened();
                            this.mFelicaWrapper.checkClosedinStarting();
                            FelicaWrapper felicaWrapper = FelicaAdapter.getInstance().getFelicaWrapper();
                            felicaWrapper.checkAccessRight(2);
                            this.mFelicaWrapper.setMfcOnline(this);
                            LogMgr.log(7, "%s", "002");
                            this.mIFSCEventListener = iFSCEventListener;
                            FSC fsc = FelicaAdapter.getInstance().getFSC();
                            fsc.setFelica(FelicaAdapter.getInstance().getFelicaWrapper().getFelica());
                            fsc.setDeviceList(deviceList);
                            fsc.setFSCEventListener(this.mFscEventListener);
                            fsc.start(str);
                            this.mOnline = true;
                            felicaWrapper.clearTarget();
                        } finally {
                            if (!this.mOnline) {
                                this.mFelicaWrapper.setMfcOnline(null);
                            }
                        }
                    } catch (IllegalArgumentException e) {
                        LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "706", e.getMessage());
                        FelicaResultInfo felicaResultInfo = new FelicaResultInfo(32, e.getMessage());
                        if (!this.mOnline) {
                            this.mFelicaWrapper.setMfcOnline(null);
                        }
                        return felicaResultInfo;
                    }
                } catch (FelicaException e2) {
                    LogMgr.log(2, "%s : Catch FelicaException message = %s, ID = %s", "703", e2.getMessage(), Integer.valueOf(e2.getID()));
                    FelicaResultInfo felicaResultInfo2 = new FelicaResultInfo(1, e2.getMessage(), e2.getID(), e2.getType());
                    if (!this.mOnline) {
                        this.mFelicaWrapper.setMfcOnline(null);
                    }
                    return felicaResultInfo2;
                } catch (NumberFormatException unused) {
                    LogMgr.log(2, "%s : Catch NumberFormatException message = %s", "705");
                    FelicaResultInfo felicaResultInfo3 = new FelicaResultInfo(1, null, 1, 27);
                    if (!this.mOnline) {
                        this.mFelicaWrapper.setMfcOnline(null);
                    }
                    return felicaResultInfo3;
                }
            }
        }
        LogMgr.log(4, "%s", "999");
        return new FelicaResultInfo();
    }

    public void stop() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
        } catch (Exception e) {
            LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
        }
        if (!this.mOnline) {
            LogMgr.log(4, "%s", "001");
            return;
        }
        this.mFelicaWrapper.checkPidUid();
        FelicaAdapter.getInstance().getFSC().stop();
        LogMgr.log(4, "%s", "999");
    }

    synchronized void notifyResult(byte[] bArr) throws RemoteException {
        try {
            try {
                this.mFelicaWrapper.checkPidUid();
                synchronized (this.mOperationRequestLock) {
                    this.mOperationRequestSuccessed = true;
                    this.mOperationRequestResult = bArr;
                    this.mKilled = true;
                    this.mOperationRequestLock.notifyAll();
                }
            } catch (FelicaException e) {
                LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    synchronized void notifyError(String str) throws RemoteException {
        try {
            try {
                this.mFelicaWrapper.checkPidUid();
                synchronized (this.mOperationRequestLock) {
                    this.mOperationRequestSuccessed = false;
                    this.mOperationRequestErrMsg = str;
                    this.mKilled = true;
                    this.mOperationRequestLock.notifyAll();
                }
            } catch (FelicaException e) {
                LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
            }
        } catch (Throwable th) {
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void terminate() throws Exception {
        LogMgr.log(6, "%s", "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this) {
                try {
                    if (!this.mAborted) {
                        LogMgr.log(7, "%s", "001");
                        this.mFelicaWrapper.setMfcOnline(null);
                        this.mFelicaWrapper.resetInner();
                    }
                } finally {
                    LogMgr.log(7, "%s", "002");
                    this.mAborted = false;
                    this.mOnline = false;
                }
            }
        }
        LogMgr.log(6, "%s", "999");
    }

    synchronized void kill() {
        LogMgr.log(4, "%s", "000");
        if (!this.mOnline) {
            LogMgr.log(7, "%s", "001");
            return;
        }
        synchronized (this.mOperationRequestLock) {
            this.mOperationRequestSuccessed = false;
            this.mOperationRequestErrMsg = KILLED_BY_USER;
            this.mKilled = true;
            this.mOperationRequestLock.notifyAll();
        }
        this.mAborted = true;
        this.mOnline = false;
        LogMgr.log(4, "%s", "999");
    }

    private class MyFSCEventListener implements FSCEventListener {
        private MyFSCEventListener() {
        }

        @Override // com.felicanetworks.mfc.FSCEventListener
        public void finished(int i) {
            LogMgr.log(4, "%s", "000");
            IFSCEventListener iFSCEventListener = MfcOnline.this.mIFSCEventListener;
            MfcOnline.this.mIFSCEventListener = null;
            try {
                MfcOnline.this.terminate();
                try {
                    iFSCEventListener.finished(i);
                } catch (Exception unused) {
                    LogMgr.log(1, "%s", "800");
                }
            } catch (Exception unused2) {
                LogMgr.log(1, "%s", "801");
                try {
                    iFSCEventListener.errorOccurred(1, MfiClientConst.EXC_RUNTIME_IN_FSC);
                } catch (RemoteException unused3) {
                    LogMgr.log(1, "%s", "802");
                }
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.FSCEventListener
        public void errorOccurred(int i, String str) {
            LogMgr.log(4, "%s", "000");
            IFSCEventListener iFSCEventListener = MfcOnline.this.mIFSCEventListener;
            MfcOnline.this.mIFSCEventListener = null;
            try {
                MfcOnline.this.terminate();
                try {
                    iFSCEventListener.errorOccurred(i, str);
                } catch (Exception unused) {
                    LogMgr.log(1, "%s", "800");
                }
            } catch (Exception unused2) {
                LogMgr.log(1, "%s", "801");
                try {
                    iFSCEventListener.errorOccurred(1, MfiClientConst.EXC_RUNTIME_IN_FSC);
                } catch (RemoteException unused3) {
                    LogMgr.log(1, "%s", "802");
                }
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.FSCEventListener
        public byte[] operationRequested(int i, String str, byte[] bArr) throws Exception {
            LogMgr.log(4, "%s", "000");
            try {
                MfcOnline.this.mOperationRequestSuccessed = false;
                MfcOnline.this.mOperationRequestResult = null;
                MfcOnline.this.mOperationRequestErrMsg = null;
                MfcOnline.this.mIFSCEventListener.operationRequested(i, str, bArr);
            } catch (RemoteException unused) {
                LogMgr.log(1, "%s", "800");
            }
            synchronized (MfcOnline.this.mOperationRequestLock) {
                while (!MfcOnline.this.mKilled) {
                    try {
                        MfcOnline.this.mOperationRequestLock.wait();
                    } catch (InterruptedException unused2) {
                        LogMgr.log(1, "%s", "801");
                    }
                }
                MfcOnline.this.mKilled = false;
            }
            if (MfcOnline.this.mOperationRequestSuccessed) {
                LogMgr.log(4, "%s", "999");
                return MfcOnline.this.mOperationRequestResult;
            }
            LogMgr.log(1, "%s", "802");
            throw new Exception(MfcOnline.this.mOperationRequestErrMsg);
        }
    }
}
