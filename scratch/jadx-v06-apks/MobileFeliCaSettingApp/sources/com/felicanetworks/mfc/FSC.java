package com.felicanetworks.mfc;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFSC;
import com.felicanetworks.mfc.IFSCEventListener;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FSC extends Service {
    static final int DEFAULT_BIND_TIMEOUT = 10000;
    private static final String EXC_CURRENTLY_ONLINE = "Currently online.";
    private static final String EXC_DEVICE_LIST_NOT_SET = "Device list not set.";
    private static final String EXC_ERR_UNKNOWN = "Unknown error.";
    private static final String EXC_FELICA_NOT_OPEND = "FeliCa chip is not opened yet.";
    private static final String EXC_FELICA_NOT_SET = "Felica not set.";
    private static final String EXC_INTERRUPTED_BY_USER = "Interrupted.";
    private static final String EXC_INVALID_URL = "The specified URL is null.";
    private static final String EXC_LISTENER_NOT_SET = "Listener not set.";
    private static final String MFC_ADAPTER_CLASS_NAME = "com.felicanetworks.mfc.FSCAdapter";
    private static final String MFC_PACKAGE_NAME = "com.felicanetworks.mfc";
    static int bindTimeout = 10000;
    private DeviceList deviceList;
    private Felica felica;
    private FSCEventListener fscEventListener;
    private String url;
    private BindTimerHandler bindTimerHandler = new BindTimerHandler(Looper.myLooper());
    private boolean online = false;
    private boolean canceled = false;
    private IFSCEventListener ifscEventHooker = new IFSCEventListener.Stub() { // from class: com.felicanetworks.mfc.FSC.1
        @Override // com.felicanetworks.mfc.IFSCEventListener
        public void finished(int i) {
            FSCEventListener fSCEventListener;
            LogMgr.log(3, "%s status:%d", "000", Integer.valueOf(i));
            try {
                synchronized (FSC.this) {
                    if (FSC.this.fscEventListener != null) {
                        LogMgr.log(7, "%s", "001");
                        fSCEventListener = FSC.this.fscEventListener;
                    } else {
                        fSCEventListener = null;
                    }
                    FSC.this.terminate();
                }
                if (fSCEventListener == null) {
                    LogMgr.log(7, "%s", "002");
                } else {
                    LogMgr.log(7, "%s", "003");
                    fSCEventListener.finished(i);
                }
            } catch (Exception e) {
                LogMgr.log(2, "%s msg:%s", "700", e.getMessage());
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.IFSCEventListener
        public void errorOccurred(int i, String str) {
            FSCEventListener fSCEventListener;
            LogMgr.log(3, "%s type:%d, message:%s", "000", Integer.valueOf(i), str);
            try {
                synchronized (FSC.this) {
                    if (FSC.this.fscEventListener != null) {
                        LogMgr.log(7, "%s", "001");
                        fSCEventListener = FSC.this.fscEventListener;
                    } else {
                        fSCEventListener = null;
                    }
                    FSC.this.terminate();
                }
                if (fSCEventListener == null) {
                    LogMgr.log(7, "%s", "002");
                } else {
                    LogMgr.log(7, "%s", "003");
                    if (i == 100) {
                        LogMgr.log(7, "%s", "004");
                    } else {
                        LogMgr.log(7, "%s", "005");
                        fSCEventListener.errorOccurred(i, str);
                    }
                }
            } catch (Exception e) {
                LogMgr.log(2, "%s msg:%s", "700", e.getMessage());
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.IFSCEventListener
        public void operationRequested(int i, String str, byte[] bArr) throws RemoteException {
            LogMgr.log(3, "%s DIB:%d param:%s data:%s", "020", Integer.valueOf(i), str, bArr);
            try {
                byte[] bArrOperationRequested = FSC.this.fscEventListener.operationRequested(i, str, bArr);
                LogMgr.log(3, "%s %s:%s", "020", "Client operation is completed", String.valueOf(bArrOperationRequested));
                try {
                    if (FSC.this.isMfiFscOnline()) {
                        FSC.this.felica.getMfiClientAccess().getIMfiFelica().notifyResult(bArrOperationRequested);
                    } else {
                        FSC.this.fsc.notifyResult(bArrOperationRequested);
                    }
                } catch (Exception unused) {
                    LogMgr.log(2, "%s %s", "700", "Remote Access failed");
                }
            } catch (Exception e) {
                LogMgr.log(3, "%s %s:%s", "020", "Client operation is failed", e.getMessage());
                try {
                    if (!FSC.this.isMfiFscOnline()) {
                        FSC.this.fsc.notifyError(e.getMessage());
                    } else {
                        FSC.this.felica.getMfiClientAccess().getIMfiFelica().notifyError(e.getMessage());
                    }
                } catch (Exception unused2) {
                    LogMgr.log(2, "%s %s", "701", "Remote Access failed");
                }
            }
            LogMgr.log(3, "%s", "999");
        }
    };
    private IFSC fsc = null;
    private MfcConnection connectionHooker = new MfcConnection();
    private final IBinder mBinder = new LocalBinder();

    public FSC() {
        LogMgr.log(3, "%s", "000");
        LogMgr.log(3, "%s", "999");
    }

    public synchronized void setFelica(Felica felica) throws FelicaException {
        LogMgr.log(3, "%s", "000");
        checkNotOnline();
        this.felica = felica;
        LogMgr.log(3, "%s", "999");
    }

    public synchronized void setFSCEventListener(FSCEventListener fSCEventListener) throws FelicaException {
        LogMgr.log(3, "%s", "000");
        checkNotOnline();
        this.fscEventListener = fSCEventListener;
        LogMgr.log(3, "%s", "999");
    }

    public synchronized void setDeviceList(DeviceList deviceList) throws FelicaException {
        LogMgr.log(3, "%s", "000");
        checkNotOnline();
        this.deviceList = deviceList;
        LogMgr.log(3, "%s", "999");
    }

    public synchronized void start(String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(3, "%s", "000");
        if (str == null) {
            LogMgr.log(2, "%s %s", "700", EXC_INVALID_URL);
            throw new IllegalArgumentException(EXC_INVALID_URL);
        }
        checkNotOnline();
        if (this.felica == null) {
            LogMgr.log(2, "%s %s", "701", "Felica is null");
            throw new FelicaException(2, 24);
        }
        if (this.deviceList == null) {
            LogMgr.log(2, "%s %s", "702", "DeviceList is null");
            throw new FelicaException(2, 25);
        }
        if (this.fscEventListener == null) {
            LogMgr.log(2, "%s %s", "703", "FSCEventListener is null");
            throw new FelicaException(2, 26);
        }
        if (this.felica.isMfiActivated()) {
            try {
                MfcUtil.checkMfcResult(this.felica.getMfiClientAccess().getIMfiFelica().checkOnlineAccess());
                try {
                    this.felica.getMfiClientAccess().setFSCEventListener(this.fscEventListener);
                    MfcUtil.checkMfcResult(this.felica.getMfiClientAccess().getIMfiFelica().start(str, this.deviceList, this.ifscEventHooker, this.felica.getMfiClientAccess().getIMfiFelica()));
                    return;
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s %s id:%s type:%s", "723", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                    this.felica.getMfiClientAccess().clearFSCEventListener();
                    throw e;
                } catch (IllegalArgumentException e2) {
                    LogMgr.log(2, "%s %s msg:%s", "724", "IllegalArgumentException", e2.getMessage());
                    this.felica.getMfiClientAccess().clearFSCEventListener();
                    throw e2;
                } catch (Exception e3) {
                    LogMgr.log(2, "%s %s msg:%s", "725", "Exception", e3.getMessage());
                    this.felica.getMfiClientAccess().clearFSCEventListener();
                    throw new FelicaException(1, 47);
                }
            } catch (FelicaException e4) {
                LogMgr.log(2, "%s %s id:%s type:%s", "721", "FelicaException", Integer.valueOf(e4.getID()), Integer.valueOf(e4.getType()));
                throw e4;
            } catch (Exception e5) {
                LogMgr.log(2, "%s %s msg:%s", "722", "Exception", e5.getMessage());
                throw new FelicaException(1, 47);
            }
        }
        try {
            this.felica.checkOnlineAccess();
            try {
                if (this.fsc == null) {
                    this.canceled = false;
                    this.url = str;
                    bindMfc();
                } else {
                    MfcUtil.checkMfcResult(this.fsc.start(str, this.deviceList, this.ifscEventHooker, this.felica.getIFelica()));
                }
                this.online = true;
                LogMgr.log(3, "%s", "999");
                return;
            } catch (FelicaException e6) {
                LogMgr.log(2, "%s %s id:%s type:%s", "710", "FelicaException", Integer.valueOf(e6.getID()), Integer.valueOf(e6.getType()));
                throw e6;
            } catch (IllegalArgumentException e7) {
                LogMgr.log(2, "%s %s msg:%s", "711", "IllegalArgumentException", e7.getMessage());
                throw e7;
            } catch (Exception e8) {
                LogMgr.log(2, "%s %s msg:%s", "799", "Exception", e8.getMessage());
                throw new FelicaException(1, 47);
            }
        } catch (FelicaException e9) {
            LogMgr.log(2, "%s %s id:%s type:%s", "712", "FelicaException", Integer.valueOf(e9.getID()), Integer.valueOf(e9.getType()));
            throw e9;
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "%s %s", "711", "NumberFormatException");
            throw new FelicaException(1, 27);
        }
    }

    public synchronized void stop() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        if (!this.online && !isMfiFscOnline()) {
            LogMgr.log(3, "%s %s", "997", "Not online");
            return;
        }
        if (isMfiFscOnline()) {
            try {
                this.felica.getMfiClientAccess().getIMfiFelica().stop();
            } catch (RemoteException e) {
                LogMgr.log(1, "%s %s msg:%s", "700", "RemoteException", e.getMessage());
                throw new FelicaException(1, 47);
            } catch (Exception e2) {
                LogMgr.log(1, "%s %s msg:%s", "799", "Exception", e2.getMessage());
            }
            return;
        }
        if (this.fsc == null) {
            LogMgr.log(3, "%s %s", "998", "Connecting now. canceled flag On");
            this.canceled = true;
            return;
        }
        try {
            this.fsc.stop();
        } catch (RemoteException e3) {
            LogMgr.log(1, "%s %s msg:%s", "700", "RemoteException", e3.getMessage());
            throw new FelicaException(1, 47);
        } catch (Exception e4) {
            LogMgr.log(1, "%s %s msg:%s", "799", "Exception", e4.getMessage());
        }
        LogMgr.log(3, "%s", "999");
        return;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void terminate() {
        LogMgr.log(7, "%s", "000");
        if (this.felica != null) {
            this.felica.getMfiClientAccess().clearFSCEventListener();
        }
        this.canceled = false;
        this.online = false;
        LogMgr.log(7, "%s", "999");
    }

    private void checkNotOnline() throws FelicaException {
        LogMgr.log(7, "%s", "000");
        if (this.online || isMfiFscOnline()) {
            LogMgr.log(2, "%s", "700", "online processing");
            throw new FelicaException(2, 2);
        }
        LogMgr.log(7, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized boolean isMfiFscOnline() {
        if (this.felica != null && this.felica.getMfiClientAccess() != null) {
            if (this.felica.getMfiClientAccess().isFSCStarted()) {
                return true;
            }
        }
        return false;
    }

    class ListenerParameter {
        String msg;
        int type;

        public ListenerParameter(int i, String str) {
            LogMgr.log(6, "%s %d msg:%s", "000", Integer.valueOf(i), str);
            this.type = i;
            this.msg = str;
            LogMgr.log(7, "%s", "999");
        }

        public int getType() {
            LogMgr.log(7, "%s", "000");
            LogMgr.log(7, "%s", "999");
            return this.type;
        }

        public String getMsg() {
            LogMgr.log(7, "%s", "000");
            LogMgr.log(7, "%s", "999");
            return this.msg;
        }
    }

    protected ListenerParameter convExceptionToListenerParameter(IllegalArgumentException illegalArgumentException) {
        LogMgr.log(6, "%s %s msg:%s", "000", "IllegalArgumentException", illegalArgumentException.getMessage());
        LogMgr.log(7, "%s", "999");
        return new ListenerParameter(1, illegalArgumentException.getMessage());
    }

    protected ListenerParameter convExceptionToListenerParameter(FelicaException felicaException) {
        LogMgr.log(7, "%s", "000");
        int type = felicaException.getType();
        String str = "Unknown error.";
        if (type == 1) {
            LogMgr.log(7, "%s", "005");
            str = EXC_FELICA_NOT_OPEND;
        } else if (type == 2) {
            LogMgr.log(7, "%s", "001");
            str = EXC_CURRENTLY_ONLINE;
        } else {
            switch (type) {
                case 24:
                    LogMgr.log(7, "%s", "002");
                    str = EXC_FELICA_NOT_SET;
                    break;
                case 25:
                    LogMgr.log(7, "%s", "003");
                    str = EXC_DEVICE_LIST_NOT_SET;
                    break;
                case 26:
                    LogMgr.log(7, "%s", "004");
                    str = EXC_LISTENER_NOT_SET;
                    break;
                case 27:
                    LogMgr.log(7, "%s", "006");
                    break;
                default:
                    LogMgr.log(2, "%s id:%d type:%d", "700", Integer.valueOf(felicaException.getID()), Integer.valueOf(felicaException.getType()));
                    break;
            }
        }
        LogMgr.log(7, "%s", "999");
        return new ListenerParameter(1, str);
    }

    protected ListenerParameter convExceptionToListenerParameter(Exception exc) {
        LogMgr.log(6, "%s %s msg:%s", "000", "Exception", exc.getMessage());
        LogMgr.log(7, "%s", "999");
        return new ListenerParameter(1, exc.getMessage());
    }

    protected void bindMfc() throws FelicaException {
        LogMgr.log(3, "%s", "000");
        if (!SignatureUtil.checkAppCertHash(this, FlavorConst.MFC_SIGNATURE_HASH, "com.felicanetworks.mfc")) {
            LogMgr.log(3, "700 Failed to connect for FSC Service. AppCertHash check failed.");
            throw new FelicaException(1, 47);
        }
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.felicanetworks.mfc", MFC_ADAPTER_CLASS_NAME));
        if (!bindService(intent, this.connectionHooker, 1)) {
            LogMgr.log(3, "701 Failed to connect for MFC Service");
            throw new FelicaException(1, 47);
        }
        this.bindTimerHandler.startTimer(bindTimeout);
        LogMgr.log(3, "%s", "999");
    }

    protected void unbindMfc() {
        LogMgr.log(7, "%s", "000");
        LogMgr.log(3, "%s", "001");
        try {
            unbindService(this.connectionHooker);
        } catch (Exception e) {
            LogMgr.log(7, "%s %s msg:", "002", "Exception", e.getMessage());
        }
        this.fsc = null;
        this.online = false;
        this.canceled = false;
        this.bindTimerHandler.stopTimer();
        LogMgr.log(7, "%s", "999");
    }

    class BindTimerHandler extends Handler {
        static final int MSG_BIND_TIMER = 1;

        BindTimerHandler(Looper looper) {
            super(looper);
        }

        void startTimer(int i) {
            LogMgr.log(3, "%s timeout=%d", "000", Integer.valueOf(i));
            if (i > 0) {
                LogMgr.log(7, "%s", "001");
                sendMessageDelayed(FSC.this.bindTimerHandler.obtainMessage(1), i);
            }
            LogMgr.log(3, "%s", "999");
        }

        void stopTimer() {
            LogMgr.log(3, "%s", "000");
            removeMessages(1);
            LogMgr.log(3, "%s", "999");
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            LogMgr.log(3, "%s what=%d", "000", Integer.valueOf(message.what));
            if (message.what == 1) {
                LogMgr.log(2, "%s bind timeout online=%b", "800", Boolean.valueOf(FSC.this.online));
                FSCEventListener fSCEventListener = null;
                synchronized (FSC.this) {
                    if (FSC.this.online && FSC.this.fsc == null) {
                        LogMgr.log(7, "%s", "001");
                        fSCEventListener = FSC.this.fscEventListener;
                        FSC.this.canceled = true;
                        FSC.this.online = false;
                    }
                }
                if (fSCEventListener != null) {
                    LogMgr.log(3, "%s Do the callback", "010");
                    fSCEventListener.errorOccurred(1, "Bind timeout.");
                }
            }
            super.handleMessage(message);
            LogMgr.log(3, "%s", "999");
        }
    }

    class MfcConnection implements ServiceConnection {
        MfcConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            IFelica iFelica;
            ListenerParameter listenerParameterConvExceptionToListenerParameter;
            FSCEventListener fSCEventListener;
            String str;
            String str2;
            LogMgr.log(3, "%s %s", "000", componentName.getClassName());
            synchronized (FSC.this) {
                FSC.this.fsc = IFSC.Stub.asInterface(iBinder);
                FSC.this.bindTimerHandler.stopTimer();
                try {
                    if (!FSC.this.canceled) {
                        try {
                            LogMgr.log(7, "%s", "010");
                            String str3 = FSC.this.url;
                            if (FSC.this.felica != null) {
                                iFelica = FSC.this.felica.getIFelica();
                                try {
                                    if (iFelica == null) {
                                        LogMgr.log(2, "%s %s", "703", "IFelica instance is not found.");
                                        throw new FelicaException(2, 1);
                                    }
                                    MfcUtil.checkMfcResult(FSC.this.fsc.start(str3, FSC.this.deviceList, FSC.this.ifscEventHooker, iFelica));
                                    listenerParameterConvExceptionToListenerParameter = null;
                                    fSCEventListener = null;
                                } catch (FelicaException e) {
                                    e = e;
                                    LogMgr.log(2, "%s %s id:%d type:%d", "702", "FelicaException", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                                    listenerParameterConvExceptionToListenerParameter = FSC.this.convExceptionToListenerParameter(e);
                                    if (listenerParameterConvExceptionToListenerParameter != null) {
                                        LogMgr.log(7, "%s", "014");
                                        fSCEventListener = FSC.this.fscEventListener;
                                        FSC.this.online = false;
                                    } else {
                                        fSCEventListener = null;
                                    }
                                    str = "%s";
                                    str2 = "010";
                                    LogMgr.log(7, str, str2);
                                } catch (IllegalArgumentException e2) {
                                    e = e2;
                                    LogMgr.log(2, "%s %s msg:%s", "701", "IllegalArgumentException", e.getMessage());
                                    listenerParameterConvExceptionToListenerParameter = FSC.this.convExceptionToListenerParameter(e);
                                    if (listenerParameterConvExceptionToListenerParameter != null) {
                                        LogMgr.log(7, "%s", "014");
                                        fSCEventListener = FSC.this.fscEventListener;
                                        FSC.this.online = false;
                                    } else {
                                        fSCEventListener = null;
                                    }
                                    str = "%s";
                                    str2 = "010";
                                    LogMgr.log(7, str, str2);
                                } catch (Exception e3) {
                                    e = e3;
                                    LogMgr.log(2, "%s %s msg:%s", "709", "Exception", e.getMessage());
                                    listenerParameterConvExceptionToListenerParameter = FSC.this.convExceptionToListenerParameter(e);
                                    if (listenerParameterConvExceptionToListenerParameter != null) {
                                        LogMgr.log(7, "%s", "014");
                                        fSCEventListener = FSC.this.fscEventListener;
                                        FSC.this.online = false;
                                    } else {
                                        fSCEventListener = null;
                                    }
                                    str = "%s";
                                    str2 = "010";
                                    LogMgr.log(7, str, str2);
                                }
                            } else {
                                LogMgr.log(1, "%s %s", "800", "Felica hasn't been set.");
                                throw new FelicaException(2, 24);
                            }
                        } catch (FelicaException e4) {
                            e = e4;
                            iFelica = null;
                        } catch (IllegalArgumentException e5) {
                            e = e5;
                            iFelica = null;
                        } catch (Exception e6) {
                            e = e6;
                            iFelica = null;
                        }
                    } else {
                        LogMgr.log(7, "%s", "020");
                        FSCEventListener fSCEventListener2 = FSC.this.fscEventListener;
                        ListenerParameter listenerParameter = FSC.this.new ListenerParameter(2, FSC.EXC_INTERRUPTED_BY_USER);
                        FSC.this.canceled = false;
                        FSC.this.online = false;
                        fSCEventListener = fSCEventListener2;
                        listenerParameterConvExceptionToListenerParameter = listenerParameter;
                        iFelica = null;
                    }
                    FSC.this.url = null;
                } finally {
                    LogMgr.log(7, "%s", "010");
                }
            }
            LogMgr.log(7, "%s", "030");
            if (fSCEventListener != null) {
                if (iFelica != null && listenerParameterConvExceptionToListenerParameter.getType() == 1 && listenerParameterConvExceptionToListenerParameter.getMsg() != null && listenerParameterConvExceptionToListenerParameter.getMsg().equals(FSC.EXC_FELICA_NOT_OPEND)) {
                    LogMgr.log(7, "%s break call back.", "011");
                } else {
                    LogMgr.log(3, "%s Doing the callback. type:%d, msg:%s", "031", Integer.valueOf(listenerParameterConvExceptionToListenerParameter.getType()), listenerParameterConvExceptionToListenerParameter.getMsg());
                    fSCEventListener.errorOccurred(listenerParameterConvExceptionToListenerParameter.getType(), listenerParameterConvExceptionToListenerParameter.getMsg());
                }
            }
            LogMgr.log(3, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            FSCEventListener fSCEventListener;
            LogMgr.log(3, "%s %s", "000", componentName);
            synchronized (FSC.this) {
                if (FSC.this.online) {
                    LogMgr.log(7, "%s", "001");
                    fSCEventListener = FSC.this.fscEventListener;
                } else {
                    fSCEventListener = null;
                }
                FSC.this.unbindMfc();
            }
            if (fSCEventListener != null) {
                LogMgr.log(3, "%s %s id:%d msg:%s", "002", "Client Listener Call", 1, "Unknown error.");
                fSCEventListener.errorOccurred(1, "Unknown error.");
            }
            LogMgr.log(3, "%s", "999");
        }
    }

    public class LocalBinder extends Binder {
        public LocalBinder() {
        }

        public FSC getInstance() {
            LogMgr.log(3, "%s", "000");
            LogMgr.log(3, "%s", "999");
            return FSC.this;
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        ServiceInfo serviceInfo;
        LogMgr.log(3, "%s", "000");
        try {
            ServiceInfo[] serviceInfoArr = getPackageManager().getPackageInfo(getPackageName(), 4).services;
            int length = serviceInfoArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    serviceInfo = null;
                    break;
                }
                serviceInfo = serviceInfoArr[i];
                if (serviceInfo.name.equals(getClass().getName())) {
                    LogMgr.log(7, "%s", "001");
                    break;
                }
                i++;
            }
            if (serviceInfo == null) {
                LogMgr.log(1, "%s", "800 service tag is not found.");
                return null;
            }
            if (serviceInfo.exported) {
                LogMgr.log(1, "%s", "801 exported tag is enable.");
                return null;
            }
            LogMgr.log(3, "%s", "999");
            return this.mBinder;
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "802");
            return null;
        }
    }

    @Override // android.app.Service
    public void onDestroy() {
        LogMgr.log(3, "%s", "000");
        try {
            synchronized (this) {
                LogMgr.log(7, "%s", "001");
                try {
                    if (this.fsc != null) {
                        LogMgr.log(7, "%s", "002");
                        this.fsc.stop();
                    }
                } catch (Exception e) {
                    LogMgr.log(6, "%s %s", "003", e.getMessage());
                }
                unbindMfc();
                this.fscEventListener = null;
            }
        } catch (Exception e2) {
            LogMgr.log(6, "%s %s", "004", e2.getMessage());
        }
        super.onDestroy();
        LogMgr.log(3, "%s", "999");
    }

    @Override // android.app.Service
    public boolean onUnbind(Intent intent) {
        LogMgr.log(3, "%s", "000");
        try {
            synchronized (this) {
                LogMgr.log(7, "%s", "001");
                try {
                    if (this.fsc != null) {
                        LogMgr.log(7, "%s", "002");
                        this.fsc.stop();
                    }
                } catch (Exception e) {
                    LogMgr.log(6, "%s %s", "003", e.getMessage());
                }
                unbindMfc();
                this.fscEventListener = null;
            }
        } catch (Exception e2) {
            LogMgr.log(6, "%s %s", "004", e2.getMessage());
        }
        LogMgr.log(3, "%s", "999");
        return super.onUnbind(intent);
    }
}
