package com.felicanetworks.mfm.mfcutil.mfc;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.FSCEventListener;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.mfi.FlavorConst;
import com.felicanetworks.mfc.mfi.IMfiFelica;
import com.felicanetworks.mfc.mfi.MfiClientException;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.BaseMfiEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.MfiAdmin;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.StartEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.StopEventCallback;
import com.felicanetworks.mfm.mfcutil.mfc.mfi.User;
import com.felicanetworks.mfm.mfcutil.mfc.util.LogMgr;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
public class MfiClientAccess {
    static final int DEFAULT_BIND_TIMEOUT = 10000;
    public static final String EXC_DATA_SIZE_ERROR = "Invalid data size.";
    private static final String EXC_INVALID_LISTENER = "The specified Listener is null.";
    public static final String EXC_IO_ERROR = "IO error.";
    public static final String EXC_JSON_PARSE_ERROR = "Json parse error.";
    public static final String EXC_UNKNOWN_ERROR = "Unknown error.";
    private static final int MAJOR_VERSION = 0;
    private static final String MFI_ADAPTER_CLASS_NAME = "com.felicanetworks.mfc.mfi.FelicaAdapter";
    protected static final String MFI_SERVICE_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    private static final int MINOR_VERSION = 1;
    private static final int SUPPORTED_MAJOR_VERSION_MIN = 3;
    private static final int SUPPORTED_MINOR_VERSION_MIN = 2;
    private static final String VERSION_DELIMITER = "\\.";
    private FelicaEventListener mCallback;
    private FSCEventListener mFSCEventListener;
    private WeakReference<Felica> mFelica;
    private User mLoginUser;
    private MfiAdmin mMfiAdmin;
    private BaseMfiEventCallback mMfiLogInOutEventCallback;
    private BaseMfiEventCallback mMfiOnlineEventCallback;
    private String mPackageName;
    private MfiConnection mMfiConnectionHooker = new MfiConnection();
    private IMfiFelica mMfiFelica = null;
    private IFelicaEventListener mIFelicaEventListener = new FelicaEventListenerStub();
    private BindTimerHandler mBindTimerHandler = new BindTimerHandler(Looper.myLooper());
    private boolean mLoggingInOut = false;
    private boolean mOnline = false;

    MfiClientAccess(Felica felica) {
        this.mFelica = null;
        LogMgr.log(7, "%s", "000");
        this.mFelica = new WeakReference<>(felica);
        LogMgr.log(7, "%s", "999");
    }

    void finish() {
        LogMgr.log(7, "%s", "000");
        try {
            synchronized (this) {
                LogMgr.log(6, "%s", "001");
                try {
                    if (this.mMfiFelica != null) {
                        LogMgr.log(6, "%s", "002");
                        this.mMfiFelica.close();
                        this.mMfiFelica.inactivateFelica();
                    }
                } catch (Exception e) {
                    LogMgr.log(6, "%s %s", "003", e.getMessage());
                }
                unbindMfiClient();
            }
        } catch (Exception e2) {
            LogMgr.log(6, "%s %s", "004", e2.getMessage());
        }
        LogMgr.log(7, "%s", "999");
    }

    protected void bindMfiClientService() throws FelicaException {
        LogMgr.log(7, "%s", "000");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.felicanetworks.mfm.main", MFI_ADAPTER_CLASS_NAME));
        Felica felica = this.mFelica.get();
        if (felica == null) {
            LogMgr.log(3, "%s %s", "700", "Failed to connect for MFI Client Service. felica is null.");
            throw new FelicaException(1, 47);
        }
        if (!SignatureUtil.checkAppCertHash(felica, FlavorConst.MENU_APP_SIGNATURE_HASH, "com.felicanetworks.mfm.main")) {
            LogMgr.log(3, "%s %s", "701", "Failed to connect for MFI Client Service. AppCertHash check failed.");
            throw new FelicaException(1, 47);
        }
        if (!felica.bindService(intent, this.mMfiConnectionHooker, 1)) {
            LogMgr.log(3, "%s %s", "702", "Failed to connect for MFI Client Service");
            felica.unbindService(this.mMfiConnectionHooker);
            throw new FelicaException(1, 47);
        }
        this.mBindTimerHandler.startTimer(DEFAULT_BIND_TIMEOUT);
        LogMgr.log(7, "%s", "999");
    }

    protected void unbindMfiClient() {
        LogMgr.log(7, "%s", "000");
        try {
            try {
                Felica felica = this.mFelica.get();
                if (felica != null) {
                    LogMgr.log(6, "%s", "001");
                    felica.unbindService(this.mMfiConnectionHooker);
                } else {
                    LogMgr.log(2, "%s %s", "701", "Failed to unbindMfiClient. felica is null.");
                }
            } catch (Exception unused) {
                LogMgr.log(6, "%s", "002");
            }
            cleanUp();
            LogMgr.log(7, "%s", "999");
        } catch (Throwable th) {
            cleanUp();
            throw th;
        }
    }

    synchronized void activate(String str, FelicaEventListener felicaEventListener) throws FelicaException, IllegalArgumentException {
        LogMgr.log(7, "%s", "000");
        if (felicaEventListener == null) {
            throw new IllegalArgumentException(EXC_INVALID_LISTENER);
        }
        checkNotActivated();
        this.mPackageName = str;
        this.mCallback = felicaEventListener;
        try {
            bindMfiClientService();
            LogMgr.log(7, "%s", "999");
        } catch (Exception unused) {
            LogMgr.log(2, "%s can not bind to MFI", "700");
            this.mCallback = null;
            throw new FelicaException(1, 47, "can not bind to MFI");
        }
    }

    synchronized void inactivate() throws FelicaException {
        LogMgr.log(7, "%s", "000");
        if (checkAfterActivating()) {
            if (checkConnecting()) {
                unbindMfiClient();
                return;
            }
            try {
                try {
                    MfcUtil.checkMfcResult(this.mMfiFelica.inactivateFelica());
                    unbindMfiClient();
                    LogMgr.log(7, "%s", "999");
                } catch (FelicaException e) {
                    LogMgr.log(2, "%s %s id:%d type:%d", "700", e.toString(), Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                    if (e.getID() == 2 && e.getType() == 5) {
                        LogMgr.log(7, "%s", "001");
                        return;
                    }
                    throw e;
                }
            } catch (Exception e2) {
                LogMgr.log(2, "%s %s", "701", "Other Exception");
                LogMgr.printStackTrace(7, e2);
                throw new FelicaException(1, 47);
            }
        }
    }

    protected void cleanUp() {
        LogMgr.log(7, "%s", "000");
        this.mPackageName = null;
        this.mMfiFelica = null;
        this.mCallback = null;
        this.mFSCEventListener = null;
        this.mMfiLogInOutEventCallback = null;
        this.mMfiOnlineEventCallback = null;
        this.mBindTimerHandler.stopTimer();
        this.mLoggingInOut = false;
        this.mOnline = false;
        this.mLoginUser = null;
        LogMgr.log(7, "%s", "999");
    }

    class BindTimerHandler extends Handler {
        static final int MSG_BIND_TIMER = 1;

        public BindTimerHandler(Looper looper) {
            super(looper);
        }

        void startTimer(int i) {
            LogMgr.log(7, "%s timeout=%d", "000", Integer.valueOf(i));
            if (i > 0) {
                LogMgr.log(7, "%s", "001");
                sendMessageDelayed(MfiClientAccess.this.mBindTimerHandler.obtainMessage(1), i);
            }
            LogMgr.log(7, "%s", "999");
        }

        void stopTimer() {
            LogMgr.log(7, "%s", "000");
            removeMessages(1);
            LogMgr.log(7, "%s", "999");
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            FelicaEventListener felicaEventListener;
            LogMgr.log(7, "%s what=%d", "000", Integer.valueOf(message.what));
            if (message.what == 1) {
                LogMgr.log(2, "%s bind timeout connecting=%b", "700", Boolean.valueOf(MfiClientAccess.this.checkConnecting()));
                synchronized (MfiClientAccess.this) {
                    if (MfiClientAccess.this.checkConnecting()) {
                        LogMgr.log(7, "%s", "001");
                        felicaEventListener = MfiClientAccess.this.mCallback;
                        MfiClientAccess.this.mCallback = null;
                        MfiClientAccess.this.unbindMfiClient();
                    } else {
                        felicaEventListener = null;
                    }
                }
                if (felicaEventListener != null) {
                    LogMgr.log(7, "%s Do the callback", "002");
                    felicaEventListener.errorOccurred(1, "Bind timeout.", null);
                }
            }
            super.handleMessage(message);
            LogMgr.log(7, "%s", "999");
        }
    }

    class MfiConnection implements ServiceConnection {
        MfiConnection() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            int i;
            FelicaEventListener felicaEventListener;
            AppInfo otherAppInfo;
            String str;
            LogMgr.log(7, "%s %s", "000", componentName.getClassName());
            synchronized (MfiClientAccess.this) {
                MfiClientAccess.this.mMfiFelica = IMfiFelica.Stub.asInterface(iBinder);
                MfiClientAccess.this.mBindTimerHandler.stopTimer();
                i = 1;
                felicaEventListener = null;
                try {
                    if (MfiClientAccess.this.mCallback != null) {
                        try {
                            try {
                                MfcUtil.checkMfcResult(MfiClientAccess.this.mMfiFelica.activateFelica(MfiClientAccess.this.mPackageName, MfiClientAccess.this.mIFelicaEventListener));
                            } catch (FelicaException e) {
                                LogMgr.log(7, "%s", "001");
                                int type = e.getType();
                                if (type != 39) {
                                    if (type != 42) {
                                        LogMgr.log(2, "%s FelicaException id:%d type:%d", "702", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                                    } else {
                                        LogMgr.log(2, "%s FelicaException id:%d type:%d", "701", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
                                    }
                                    str = null;
                                    otherAppInfo = null;
                                } else {
                                    String message = e.getMessage();
                                    otherAppInfo = e.getOtherAppInfo();
                                    LogMgr.log(2, "%s FelicaException id:%d type:%d pid%d", "700", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()), message, Integer.valueOf(otherAppInfo.getPid()));
                                    str = message;
                                    i = 7;
                                }
                                LogMgr.log(7, "%s", "002");
                                LogMgr.log(7, "%s", "003");
                                FelicaEventListener felicaEventListener2 = MfiClientAccess.this.mCallback;
                                MfiClientAccess.this.mCallback = null;
                                MfiClientAccess.this.unbindMfiClient();
                                felicaEventListener = felicaEventListener2;
                            }
                        } catch (Exception e2) {
                            LogMgr.log(2, "%s Exception %s", "703", e2.getMessage());
                            LogMgr.log(7, "%s", "002");
                            LogMgr.log(7, "%s", "003");
                            FelicaEventListener felicaEventListener3 = MfiClientAccess.this.mCallback;
                            MfiClientAccess.this.mCallback = null;
                            MfiClientAccess.this.unbindMfiClient();
                            otherAppInfo = null;
                            felicaEventListener = felicaEventListener3;
                            str = null;
                        }
                    } else {
                        LogMgr.log(2, "%s", "704");
                        MfiClientAccess.this.unbindMfiClient();
                    }
                    str = null;
                    otherAppInfo = null;
                } finally {
                    LogMgr.log(7, "%s", "002");
                }
            }
            LogMgr.log(7, "%s", "004");
            if (felicaEventListener != null) {
                LogMgr.log(7, "%s Do the callback", "005");
                felicaEventListener.errorOccurred(i, str, otherAppInfo);
            }
            LogMgr.log(7, "%s", "999");
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            FelicaEventListener felicaEventListener;
            FSCEventListener fSCEventListener;
            BaseMfiEventCallback baseMfiEventCallback;
            BaseMfiEventCallback baseMfiEventCallback2;
            LogMgr.log(7, "%s %s", "000", componentName);
            synchronized (MfiClientAccess.this) {
                if (MfiClientAccess.this.mCallback != null) {
                    LogMgr.log(7, "%s", "001");
                    felicaEventListener = MfiClientAccess.this.mCallback;
                    MfiClientAccess.this.mCallback = null;
                } else {
                    felicaEventListener = null;
                }
                if (MfiClientAccess.this.mFSCEventListener != null) {
                    LogMgr.log(7, "%s", "002");
                    fSCEventListener = MfiClientAccess.this.mFSCEventListener;
                    MfiClientAccess.this.mFSCEventListener = null;
                } else {
                    fSCEventListener = null;
                }
                if (MfiClientAccess.this.mMfiLogInOutEventCallback != null) {
                    LogMgr.log(7, "%s", "003");
                    baseMfiEventCallback = MfiClientAccess.this.mMfiLogInOutEventCallback;
                    MfiClientAccess.this.mMfiLogInOutEventCallback = null;
                } else {
                    baseMfiEventCallback = null;
                }
                if (MfiClientAccess.this.mMfiOnlineEventCallback != null) {
                    LogMgr.log(7, "%s", "004");
                    baseMfiEventCallback2 = MfiClientAccess.this.mMfiOnlineEventCallback;
                    MfiClientAccess.this.mMfiOnlineEventCallback = null;
                } else {
                    baseMfiEventCallback2 = null;
                }
                MfiClientAccess.this.unbindMfiClient();
            }
            if (felicaEventListener != null) {
                LogMgr.log(7, "%s", "004");
                felicaEventListener.errorOccurred(1, "Unknown error.", null);
            }
            if (fSCEventListener != null) {
                LogMgr.log(7, "%s", "005");
                fSCEventListener.errorOccurred(1, "Unknown error.");
            }
            if (baseMfiEventCallback != null) {
                LogMgr.log(7, "%s", "006");
                baseMfiEventCallback.onError(200, "Unknown error.");
            }
            if (baseMfiEventCallback2 != null) {
                LogMgr.log(7, "%s", "007");
                baseMfiEventCallback2.onError(200, "Unknown error.");
            }
            LogMgr.log(7, "%s", "999");
        }
    }

    class FelicaEventListenerStub extends IFelicaEventListener.Stub {
        FelicaEventListenerStub() {
        }

        @Override // com.felicanetworks.mfc.IFelicaEventListener
        public void errorOccurred(int i, String str, AppInfo appInfo) throws RemoteException {
            FelicaEventListener felicaEventListener;
            LogMgr.log(7, "%s", "000");
            synchronized (MfiClientAccess.this) {
                LogMgr.log(7, "%s", "001");
                felicaEventListener = MfiClientAccess.this.mCallback;
                MfiClientAccess.this.mCallback = null;
                try {
                    MfiClientAccess.this.unbindMfiClient();
                } catch (Exception e) {
                    LogMgr.log(2, "%s %s", "700", e.getMessage());
                }
            }
            if (felicaEventListener != null) {
                try {
                    LogMgr.log(7, "%s %s %d %s", "002", "FelicaEventListener#errorOccurred", Integer.valueOf(i), str);
                    if (appInfo != null) {
                        LogMgr.log(7, "%s %s %d", "003", "FelicaEventListener#errorOccurred", Integer.valueOf(appInfo.getPid()));
                    }
                    felicaEventListener.errorOccurred(i, str, appInfo);
                } catch (Exception e2) {
                    LogMgr.log(2, "%s %s", "701", e2.getMessage());
                }
            }
            LogMgr.log(7, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.IFelicaEventListener
        public void finished() throws RemoteException {
            FelicaEventListener felicaEventListener;
            LogMgr.log(7, "%s %s", "000", "FelicaEventListener#finished");
            try {
                synchronized (MfiClientAccess.this) {
                    felicaEventListener = null;
                    if (MfiClientAccess.this.mCallback != null) {
                        LogMgr.log(7, "%s", "001");
                        FelicaEventListener felicaEventListener2 = MfiClientAccess.this.mCallback;
                        MfiClientAccess.this.mCallback = null;
                        felicaEventListener = felicaEventListener2;
                    } else {
                        LogMgr.log(7, "%s", "002");
                        MfiClientAccess.this.unbindMfiClient();
                    }
                }
                if (felicaEventListener != null) {
                    try {
                        LogMgr.log(7, "%s", "003");
                        felicaEventListener.finished();
                    } catch (Exception e) {
                        LogMgr.log(2, "%s %s", "700", e.getMessage());
                    }
                }
            } catch (Exception e2) {
                LogMgr.log(2, "%s %s", "701", e2.getMessage());
            }
            LogMgr.log(7, "%s", "999");
        }
    }

    void checkNotActivated() throws FelicaException {
        LogMgr.log(7, "%s", "000");
        if (this.mCallback != null) {
            throw new FelicaException(2, 49);
        }
        if (this.mMfiFelica != null) {
            throw new FelicaException(2, 42);
        }
        LogMgr.log(7, "%s", "999");
    }

    boolean checkAfterActivating() {
        LogMgr.log(7, "%s", "000");
        if (this.mMfiFelica != null || this.mCallback != null) {
            LogMgr.log(7, "%s", "001");
            return true;
        }
        LogMgr.log(7, "%s", "002");
        return false;
    }

    public void checkActivated() throws MfiClientException {
        LogMgr.log(7, "%s", "000");
        if (this.mFelica.get() == null || this.mMfiFelica == null || this.mCallback != null) {
            LogMgr.log(7, "%s", "001");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_ACTIVATED, null);
        }
        LogMgr.log(7, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkConnecting() {
        LogMgr.log(7, "%s", "000");
        if (this.mMfiFelica == null && this.mCallback != null) {
            LogMgr.log(7, "%s", "001");
            return true;
        }
        LogMgr.log(7, "%s", "002");
        return false;
    }

    public synchronized void startLoggingInOut(BaseMfiEventCallback baseMfiEventCallback, boolean z) throws MfiClientException {
        LogMgr.log(7, "%s", "000");
        if (this.mLoggingInOut && ((this.mMfiLogInOutEventCallback instanceof StartEventCallback) || (this.mMfiLogInOutEventCallback instanceof StopEventCallback))) {
            LogMgr.log(2, "%s", "700", "login/logout processing");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_CURRENTLY_ONLINE, null);
        }
        if (this.mOnline) {
            if (z) {
                LogMgr.log(2, "%s", "700", "online processing");
                throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_CURRENTLY_ONLINE, null);
            }
            LogMgr.log(7, "%s", "001", "online processing, but it continues");
        }
        this.mLoggingInOut = true;
        this.mOnline = true;
        this.mMfiLogInOutEventCallback = baseMfiEventCallback;
        LogMgr.log(7, "%s", "999");
    }

    public synchronized BaseMfiEventCallback stopLoggingInOut() {
        BaseMfiEventCallback baseMfiEventCallback;
        LogMgr.log(7, "%s", "000");
        baseMfiEventCallback = this.mMfiLogInOutEventCallback;
        this.mLoggingInOut = false;
        this.mOnline = false;
        this.mMfiLogInOutEventCallback = null;
        this.mMfiOnlineEventCallback = null;
        LogMgr.log(7, "%s", "999");
        return baseMfiEventCallback;
    }

    public synchronized boolean isStartRunning() {
        return this.mMfiLogInOutEventCallback instanceof StartEventCallback;
    }

    public synchronized void startOnline(BaseMfiEventCallback baseMfiEventCallback) throws MfiClientException {
        LogMgr.log(7, "000");
        onlineCheck();
        this.mOnline = true;
        this.mMfiOnlineEventCallback = baseMfiEventCallback;
        LogMgr.log(7, "999");
    }

    public synchronized void onlineCheck() throws MfiClientException {
        if (this.mOnline) {
            LogMgr.log(2, "700 online processing");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_CURRENTLY_ONLINE, null);
        }
    }

    public synchronized BaseMfiEventCallback stopOnline() {
        BaseMfiEventCallback baseMfiEventCallback;
        LogMgr.log(7, "%s", "000");
        baseMfiEventCallback = this.mMfiOnlineEventCallback;
        this.mOnline = false;
        this.mMfiOnlineEventCallback = null;
        LogMgr.log(7, "%s", "999");
        return baseMfiEventCallback;
    }

    public synchronized void startOnlineWithoutLogin(BaseMfiEventCallback baseMfiEventCallback) throws MfiClientException {
        LogMgr.log(7, "000");
        if (this.mOnline) {
            LogMgr.log(2, "700 online processing");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_STARTED, null);
        }
        this.mOnline = true;
        this.mMfiOnlineEventCallback = baseMfiEventCallback;
        LogMgr.log(7, "999");
    }

    public synchronized void setFSCEventListener(FSCEventListener fSCEventListener) {
        LogMgr.log(7, "%s", "000");
        this.mFSCEventListener = fSCEventListener;
        LogMgr.log(7, "%s", "999");
    }

    public synchronized void clearFSCEventListener() {
        LogMgr.log(7, "%s", "000");
        this.mFSCEventListener = null;
        LogMgr.log(7, "%s", "999");
    }

    public synchronized boolean isFSCStarted() {
        LogMgr.log(7, "%s", "000");
        LogMgr.log(7, "%s", "999");
        return this.mFSCEventListener != null;
    }

    public synchronized void setLoginUser(User user, MfiAdmin mfiAdmin) {
        LogMgr.log(7, "%s", "000");
        this.mLoginUser = user;
        this.mMfiAdmin = mfiAdmin;
        LogMgr.log(7, "%s", "999");
    }

    public synchronized void clearLoginUser() {
        LogMgr.log(7, "%s", "000");
        this.mLoginUser = null;
        this.mMfiAdmin = null;
        LogMgr.log(7, "%s", "999");
    }

    public void checkUserLoggedIn(User user) throws MfiClientException {
        LogMgr.log(7, "%s", "000");
        User user2 = this.mLoginUser;
        if (user2 == null || !user2.equals(user)) {
            LogMgr.log(2, "%s", "700", "logged out.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
        LogMgr.log(7, "%s", "999");
    }

    public void checkMfiAdminLoggedIn(MfiAdmin mfiAdmin) throws MfiClientException {
        LogMgr.log(7, "000 mfiAdmin = " + mfiAdmin);
        MfiAdmin mfiAdmin2 = this.mMfiAdmin;
        if (mfiAdmin2 == null || !mfiAdmin2.equals(mfiAdmin)) {
            LogMgr.log(2, "%s", "700", "logged out.");
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
        LogMgr.log(7, "999", "999");
    }

    public boolean isMfiClientStarted() {
        return (this.mLoginUser == null && this.mMfiAdmin == null) ? false : true;
    }

    public synchronized IMfiFelica getIMfiFelica() {
        LogMgr.log(7, "%s", "000");
        LogMgr.log(7, "%s", "999");
        return this.mMfiFelica;
    }

    public boolean isDataListEventSupported() throws FelicaException {
        LogMgr.log(3, "000");
        Felica felica = this.mFelica.get();
        if (felica == null) {
            LogMgr.log(3, "700 Failed to connect for MFI Client Service. felica is null.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        try {
            String mfiClientVersion = Felica.getMfiClientVersion(felica);
            LogMgr.log(3, "999");
            return isDataListEventSupportedVersion(mfiClientVersion);
        } catch (Exception unused) {
            LogMgr.log(2, "701 Invalid MfiClient Version.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }

    private static boolean isDataListEventSupportedVersion(String str) throws MfiClientException {
        if (str == null) {
            LogMgr.log(2, "700 Could not get MfiClient Version.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
        try {
            String[] strArrSplit = str.split(VERSION_DELIMITER);
            int i = Integer.parseInt(strArrSplit[0]);
            int i2 = Integer.parseInt(strArrSplit[1]);
            if (i > 3) {
                return true;
            }
            return i == 3 && i2 >= 2;
        } catch (NumberFormatException unused) {
            LogMgr.log(2, "701 Invalid MfiClient Version format.");
            throw new MfiClientException(1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED, null);
        }
    }
}
