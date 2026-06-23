package com.felicanetworks.mfc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFSC;
import com.felicanetworks.mfc.felica.FelicaGp;
import com.felicanetworks.mfc.felica.FelicaRf;
import com.felicanetworks.mfc.felica.FelicaSe;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.util.LogMgr;
import com.felicanetworks.tcap.FelicaDeviceList;
import com.felicanetworks.tcap.ITcapClientEventListener;
import com.felicanetworks.tcap.ITcapClientImpl;
import java.util.Enumeration;

/* JADX INFO: loaded from: classes.dex */
public class FSCAdapter extends Service {
    private static final String EXC_RUNTIME = "java.lang.RuntimeException: A runtime exception was thrown within FSC.";
    private ITcapClientImpl fscEntity;
    private boolean mAborted;
    private FelicaGp mFelicaGp;
    private FelicaRf mFelicaRf;
    private FelicaSe mFelicaSe;
    private IFSCEventListener mFscEventListener;
    private boolean mIsGpDevice;
    private boolean mOnline;
    private MyTcapClientEventListener mMyTcapClientEventListener = new MyTcapClientEventListener();
    private final IFSCConnecter mBinder = new IFSCConnecter();

    private class MyTcapClientEventListener extends ITcapClientEventListener.Stub {
        private MyTcapClientEventListener() {
        }

        @Override // com.felicanetworks.tcap.ITcapClientEventListener
        public void errorOccurred(int i, String str) {
            IFSCEventListener iFSCEventListener = FSCAdapter.this.mFscEventListener;
            FSCAdapter.this.mFscEventListener = null;
            try {
                FSCAdapter.this.terminate();
                try {
                    iFSCEventListener.errorOccurred(i, str);
                } catch (Exception unused) {
                    LogMgr.log(1, "%s", "800");
                }
            } catch (Exception unused2) {
                LogMgr.log(1, "%s", "801");
                try {
                    iFSCEventListener.errorOccurred(1, FSCAdapter.EXC_RUNTIME);
                } catch (RemoteException unused3) {
                    LogMgr.log(1, "%s", "802");
                }
            }
        }

        @Override // com.felicanetworks.tcap.ITcapClientEventListener
        public void finished(int i) {
            IFSCEventListener iFSCEventListener = FSCAdapter.this.mFscEventListener;
            FSCAdapter.this.mFscEventListener = null;
            try {
                FSCAdapter.this.terminate();
                try {
                    iFSCEventListener.finished(i);
                } catch (Exception unused) {
                    LogMgr.log(1, "%s", "800");
                }
            } catch (Exception unused2) {
                LogMgr.log(1, "%s", "801");
                try {
                    iFSCEventListener.errorOccurred(1, FSCAdapter.EXC_RUNTIME);
                } catch (RemoteException unused3) {
                    LogMgr.log(1, "%s", "802");
                }
            }
        }

        @Override // com.felicanetworks.tcap.ITcapClientEventListener
        public void operationRequested(int i, String str, byte[] bArr) {
            try {
                FSCAdapter.this.mFscEventListener.operationRequested(i, str, bArr);
            } catch (RemoteException unused) {
                LogMgr.log(1, "%s", "800");
            }
        }
    }

    public FSCAdapter() {
        this.mIsGpDevice = false;
        LogMgr.log(4, "%s", "000");
        this.fscEntity = ITcapClientImpl.getInstance();
        this.mIsGpDevice = AccessConfig.isGpDevice();
        LogMgr.log(4, "%s : fscEntry = %s", "999", this.fscEntity);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        LogMgr.log(4, "%s : intent = %s", "000", intent);
        LogMgr.log(4, "%s", "999");
        return this.mBinder;
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(4, "%s : intent = %s", "000", intent);
        this.fscEntity.doStop();
        LogMgr.log(4, "%s", "999");
        return super.onUnbind(intent);
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(4, "%s", "000");
        super.onDestroy();
        this.fscEntity.doStop();
        LogMgr.log(4, "%s", "999");
    }

    class IFSCConnecter extends IFSC.Stub {
        IFSCConnecter() {
        }

        @Override // com.felicanetworks.mfc.IFSC
        public FelicaResultInfo start(String str, DeviceList deviceList, IFSCEventListener iFSCEventListener, IFelica iFelica) throws RemoteException {
            FelicaResultInfo felicaResultInfoStartFelica;
            LogMgr.log(4, "%s, url=%s, deviceList=%s, fscEventListener=%s, felica=%s", "000", str, deviceList, iFSCEventListener, iFelica);
            new FelicaResultInfo(1, (String) null);
            if (iFelica == null) {
                LogMgr.log(2, "%s : felica == null", "700");
                return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() felica==null", 2, 24);
            }
            if (iFSCEventListener != null) {
                if (FSCAdapter.this.mIsGpDevice) {
                    felicaResultInfoStartFelica = startFelicaGp(str, deviceList, iFSCEventListener, iFelica);
                } else {
                    felicaResultInfoStartFelica = startFelica(str, deviceList, iFSCEventListener, iFelica);
                }
                LogMgr.log(4, "%s", "999");
                return felicaResultInfoStartFelica;
            }
            LogMgr.log(2, "%s : fscEventListener == null", "701");
            return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() fscEventListener==null", 2, 26);
        }

        private synchronized FelicaResultInfo startFelicaGp(String str, DeviceList deviceList, IFSCEventListener iFSCEventListener, IFelica iFelica) throws RemoteException {
            FelicaResultInfo felicaResultInfoStart;
            LogMgr.log(4, "%s", "000");
            new FelicaResultInfo(1, (String) null);
            IBinder iBinderAsBinder = iFelica.asBinder();
            if (!(iBinderAsBinder instanceof IFelicaGpDeviceImpl)) {
                LogMgr.log(2, "%s : binder != IFelica.Stub", "702");
                return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() binder != IFelica.Stub", 1, 47);
            }
            LogMgr.log(7, "%s", "001");
            FelicaGp felicaGp = ((IFelicaGpDeviceImpl) iBinderAsBinder).getFelicaGp();
            FelicaRf felicaRf = ((IFelicaGpDeviceImpl) iBinderAsBinder).getFelicaRf();
            if (felicaGp == null || felicaRf == null) {
                return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() binder.getFelica() failed", 1, 47);
            }
            FSCAdapter.this.mFelicaGp = felicaGp;
            FSCAdapter.this.mFelicaRf = felicaRf;
            synchronized (FSCAdapter.this.mFelicaGp) {
                synchronized (FSCAdapter.this.mFelicaRf) {
                    synchronized (this) {
                        try {
                            try {
                                try {
                                    try {
                                        if (FSCAdapter.this.mOnline) {
                                            throw new FelicaException(2, 2);
                                        }
                                        FSCAdapter.this.mFelicaGp.checkPidUid();
                                        FSCAdapter.this.mFelicaGp.checkOpened();
                                        FSCAdapter.this.mFelicaGp.checkClosedinStarting();
                                        FSCAdapter.this.mFelicaGp.checkAccessRight(2);
                                        if (!FSCAdapter.this.mFelicaGp.isPersonalized() || !FSCAdapter.this.mFelicaGp.isCrsActivated()) {
                                            throw new FelicaException(1, 27);
                                        }
                                        FSCAdapter.this.mFelicaGp.setTcapClient(FSCAdapter.this);
                                        FSCAdapter.this.mFelicaRf.setTcapClient(FSCAdapter.this);
                                        LogMgr.log(7, "%s", "002");
                                        TcapFelicaGpDevice tcapFelicaGpDevice = new TcapFelicaGpDevice(IFelicaGpImpl.getInstance(), IFelicaRfImpl.getInstance());
                                        FelicaDeviceList felicaDeviceList = new FelicaDeviceList();
                                        felicaDeviceList.add(tcapFelicaGpDevice);
                                        FSCAdapter.this.mFscEventListener = iFSCEventListener;
                                        felicaResultInfoStart = FSCAdapter.this.fscEntity.start(str, convertDeviceList(deviceList), FSCAdapter.this.mMyTcapClientEventListener, felicaDeviceList, iFSCEventListener.asBinder(), FSCAdapter.this.mFelicaGp.getUserAgent());
                                        if (felicaResultInfoStart.getExceptionType() == 0) {
                                            FSCAdapter.this.mOnline = true;
                                            ((IFelicaGpDeviceImpl) iBinderAsBinder).clearTarget();
                                        }
                                    } catch (NumberFormatException unused) {
                                        LogMgr.log(2, "%s : Catch NumberFormatException message = %s", "705");
                                        FelicaResultInfo felicaResultInfo = new FelicaResultInfo(1, null, 1, 27);
                                        if (!FSCAdapter.this.mOnline) {
                                            FSCAdapter.this.mFelicaGp.setTcapClient(null);
                                            FSCAdapter.this.mFelicaRf.setTcapClient(null);
                                        }
                                        return felicaResultInfo;
                                    }
                                } catch (FelicaException e) {
                                    LogMgr.log(2, "%s : Catch FelicaException message = %s, ID = %s", "703", e.getMessage(), Integer.valueOf(e.getID()));
                                    FelicaResultInfo felicaResultInfo2 = new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType());
                                    if (!FSCAdapter.this.mOnline) {
                                        FSCAdapter.this.mFelicaGp.setTcapClient(null);
                                        FSCAdapter.this.mFelicaRf.setTcapClient(null);
                                    }
                                    return felicaResultInfo2;
                                }
                            } catch (IllegalArgumentException e2) {
                                LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "706", e2.getMessage());
                                FelicaResultInfo felicaResultInfo3 = new FelicaResultInfo(32, e2.getMessage());
                                if (!FSCAdapter.this.mOnline) {
                                    FSCAdapter.this.mFelicaGp.setTcapClient(null);
                                    FSCAdapter.this.mFelicaRf.setTcapClient(null);
                                }
                                return felicaResultInfo3;
                            }
                        } finally {
                            if (!FSCAdapter.this.mOnline) {
                                FSCAdapter.this.mFelicaGp.setTcapClient(null);
                                FSCAdapter.this.mFelicaRf.setTcapClient(null);
                            }
                        }
                    }
                    return felicaResultInfoStart;
                }
            }
            LogMgr.log(4, "%s", "999");
            return felicaResultInfoStart;
        }

        private synchronized FelicaResultInfo startFelica(String str, DeviceList deviceList, IFSCEventListener iFSCEventListener, IFelica iFelica) throws RemoteException {
            FelicaResultInfo felicaResultInfoStart;
            LogMgr.log(4, "%s", "000");
            new FelicaResultInfo(1, (String) null);
            IBinder iBinderAsBinder = iFelica.asBinder();
            if (!(iBinderAsBinder instanceof IFelicaImpl)) {
                LogMgr.log(2, "%s : binder != IFelica.Stub", "702");
                return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() binder != IFelica.Stub", 1, 47);
            }
            LogMgr.log(7, "%s", "001");
            FelicaSe felicaSe = ((IFelicaImpl) iBinderAsBinder).getFelicaSe();
            FelicaRf felicaRf = ((IFelicaImpl) iBinderAsBinder).getFelicaRf();
            if (felicaSe == null || felicaRf == null) {
                return new FelicaResultInfo(1, "FSCAdapter$FSCConnecter.start() binder.getFelica() failed", 1, 47);
            }
            FSCAdapter.this.mFelicaSe = felicaSe;
            FSCAdapter.this.mFelicaRf = felicaRf;
            synchronized (FSCAdapter.this.mFelicaSe) {
                synchronized (FSCAdapter.this.mFelicaRf) {
                    try {
                        synchronized (this) {
                            try {
                                try {
                                    try {
                                        if (FSCAdapter.this.mOnline) {
                                            throw new FelicaException(2, 2);
                                        }
                                        FSCAdapter.this.mFelicaSe.checkPidUid();
                                        FSCAdapter.this.mFelicaSe.checkOpened();
                                        FSCAdapter.this.mFelicaSe.checkClosedinStarting();
                                        FSCAdapter.this.mFelicaSe.checkAccessRight(2);
                                        FSCAdapter.this.mFelicaSe.setTcapClient(FSCAdapter.this);
                                        FSCAdapter.this.mFelicaRf.setTcapClient(FSCAdapter.this);
                                        LogMgr.log(7, "%s", "002");
                                        TcapFelicaDevice tcapFelicaDevice = new TcapFelicaDevice(IFelicaSeImpl.getInstance(), IFelicaRfImpl.getInstance());
                                        FelicaDeviceList felicaDeviceList = new FelicaDeviceList();
                                        felicaDeviceList.add(tcapFelicaDevice);
                                        FSCAdapter.this.mFscEventListener = iFSCEventListener;
                                        felicaResultInfoStart = FSCAdapter.this.fscEntity.start(str, convertDeviceList(deviceList), FSCAdapter.this.mMyTcapClientEventListener, felicaDeviceList, iFSCEventListener.asBinder(), FSCAdapter.this.mFelicaSe.getUserAgent());
                                        if (felicaResultInfoStart.getExceptionType() == 0) {
                                            FSCAdapter.this.mOnline = true;
                                            ((IFelicaImpl) iBinderAsBinder).clearTarget();
                                        }
                                    } catch (FelicaException e) {
                                        LogMgr.log(2, "%s : Catch FelicaException message = %s, ID = %s", "703", e.getMessage(), Integer.valueOf(e.getID()));
                                        FelicaResultInfo felicaResultInfo = new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType());
                                        if (!FSCAdapter.this.mOnline) {
                                            FSCAdapter.this.mFelicaSe.setTcapClient(null);
                                            FSCAdapter.this.mFelicaRf.setTcapClient(null);
                                        }
                                        return felicaResultInfo;
                                    }
                                } catch (IllegalArgumentException e2) {
                                    LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "706", e2.getMessage());
                                    FelicaResultInfo felicaResultInfo2 = new FelicaResultInfo(32, e2.getMessage());
                                    if (!FSCAdapter.this.mOnline) {
                                        FSCAdapter.this.mFelicaSe.setTcapClient(null);
                                        FSCAdapter.this.mFelicaRf.setTcapClient(null);
                                    }
                                    return felicaResultInfo2;
                                }
                            } catch (NumberFormatException unused) {
                                LogMgr.log(2, "%s : Catch NumberFormatException message = %s", "705");
                                FelicaResultInfo felicaResultInfo3 = new FelicaResultInfo(1, null, 1, 27);
                                if (!FSCAdapter.this.mOnline) {
                                    FSCAdapter.this.mFelicaSe.setTcapClient(null);
                                    FSCAdapter.this.mFelicaRf.setTcapClient(null);
                                }
                                return felicaResultInfo3;
                            }
                        }
                        return felicaResultInfoStart;
                    } finally {
                        if (!FSCAdapter.this.mOnline) {
                            FSCAdapter.this.mFelicaSe.setTcapClient(null);
                            FSCAdapter.this.mFelicaRf.setTcapClient(null);
                        }
                    }
                }
            }
            LogMgr.log(4, "%s", "999");
            return felicaResultInfoStart;
        }

        @Override // com.felicanetworks.mfc.IFSC
        public void stop() throws RemoteException {
            LogMgr.log(4, "%s", "000");
            try {
            } catch (Exception e) {
                LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
            }
            if (FSCAdapter.this.mOnline) {
                if (FSCAdapter.this.mIsGpDevice) {
                    FSCAdapter.this.mFelicaGp.checkPidUid();
                } else {
                    FSCAdapter.this.mFelicaSe.checkPidUid();
                }
                FSCAdapter.this.fscEntity.stop();
                LogMgr.log(4, "%s", "999");
                return;
            }
            LogMgr.log(4, "%s", "001");
        }

        @Override // com.felicanetworks.mfc.IFSC
        public void notifyResult(byte[] bArr) throws RemoteException {
            LogMgr.log(4, "%s", "000");
            try {
                if (FSCAdapter.this.mIsGpDevice) {
                    FSCAdapter.this.mFelicaGp.checkPidUid();
                } else {
                    FSCAdapter.this.mFelicaSe.checkPidUid();
                }
                FSCAdapter.this.fscEntity.notifyResult(bArr);
            } catch (Exception e) {
                LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
            }
            LogMgr.log(4, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.IFSC
        public void notifyError(String str) throws RemoteException {
            LogMgr.log(4, "%s", "000");
            try {
                if (FSCAdapter.this.mIsGpDevice) {
                    FSCAdapter.this.mFelicaGp.checkPidUid();
                } else {
                    FSCAdapter.this.mFelicaSe.checkPidUid();
                }
                FSCAdapter.this.fscEntity.notifyError(str);
            } catch (Exception e) {
                LogMgr.log(2, "%s : catch Exception = ", "700", e.getMessage());
            }
            LogMgr.log(4, "%s", "999");
        }

        private com.felicanetworks.tcap.DeviceList convertDeviceList(DeviceList deviceList) {
            if (deviceList == null) {
                return null;
            }
            com.felicanetworks.tcap.DeviceList deviceList2 = new com.felicanetworks.tcap.DeviceList();
            Enumeration<Integer> enumerationKeys = deviceList.getDevices().keys();
            while (enumerationKeys.hasMoreElements()) {
                Integer numNextElement = enumerationKeys.nextElement();
                Device device = deviceList.get(numNextElement.intValue());
                deviceList2.add(device.getType(), device.getName(), numNextElement.intValue());
            }
            return deviceList2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void terminate() throws FelicaException {
        LogMgr.log(6, "%s", "000");
        if (this.mIsGpDevice) {
            terminateFelicaGp();
        } else {
            terminateFelica();
        }
        LogMgr.log(6, "%s", "999");
    }

    private synchronized void terminateFelicaGp() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        synchronized (this.mFelicaGp) {
            synchronized (this.mFelicaRf) {
                synchronized (this) {
                    try {
                        if (!this.mAborted) {
                            LogMgr.log(7, "%s", "001");
                            this.mFelicaRf.setTcapClient(null);
                            this.mFelicaGp.setTcapClient(null);
                            this.mFelicaRf.resetInner();
                            this.mFelicaGp.resetInner(true, true, false);
                        }
                    } finally {
                        LogMgr.log(7, "%s", "002");
                        this.mAborted = false;
                        this.mOnline = false;
                    }
                }
            }
        }
    }

    private synchronized void terminateFelica() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        synchronized (this.mFelicaSe) {
            synchronized (this.mFelicaRf) {
                synchronized (this) {
                    try {
                        if (!this.mAborted) {
                            LogMgr.log(7, "%s", "003");
                            this.mFelicaRf.setTcapClient(null);
                            this.mFelicaSe.setTcapClient(null);
                            this.mFelicaRf.resetInner();
                            this.mFelicaSe.resetInner(true, true, false);
                        }
                    } finally {
                        LogMgr.log(7, "%s", "004");
                        this.mAborted = false;
                        this.mOnline = false;
                    }
                }
            }
        }
        LogMgr.log(6, "%s", "999");
        LogMgr.log(6, "%s", "999");
    }

    public synchronized void kill() {
        LogMgr.log(4, "%s", "000");
        if (!this.mOnline) {
            LogMgr.log(7, "%s", "001");
            return;
        }
        this.mAborted = true;
        this.mOnline = false;
        this.fscEntity.getTcapClient().kill();
        LogMgr.log(4, "%s", "999");
    }
}
