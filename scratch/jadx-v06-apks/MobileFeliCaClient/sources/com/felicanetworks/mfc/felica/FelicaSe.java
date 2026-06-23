package com.felicanetworks.mfc.felica;

import android.content.Context;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.MfcListener;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.ServiceUtil;
import com.felicanetworks.mfc.felica.Felica;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.felica.access_control.AccessControlManagerImpl;
import com.felicanetworks.mfc.felica.access_control.AccessController;
import com.felicanetworks.mfc.felica.access_control.AccessControllerException;
import com.felicanetworks.mfc.felica.offlineimpl.FelicaSeController;
import com.felicanetworks.mfc.felica.offlineimpl.OfflineException;
import com.felicanetworks.mfc.felica.offlineimpl.SystemInfo;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaSe extends Felica {
    private static FelicaSe sInstance;
    private ActivateThread mActivateWorker;
    private Felica.FelicaAppInfo mActivatedApp;
    private LocalDeathRecipient mDeathRecipient;
    private AccessController mAccessController = null;
    private String mUserAgent = null;

    void checkSelectedInterfaceWired() throws FelicaException {
    }

    class ActivateThread extends Thread {
        private static final String ERROR_MESSAGE_UNKNOW = "Unknown error.";
        public AccessControlManagerThread mAccessControlManagerThread = null;
        public String[] mClientPermitList;
        public IFelicaEventListener mFelicaEventListner;

        ActivateThread() {
        }

        void checkInterrupted() throws InterruptedException {
            if (isInterrupted()) {
                throw new InterruptedException("Interruption is occured.");
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            boolean z;
            String errorMessage;
            int errorType;
            try {
                synchronized (FelicaSe.this) {
                    if (FelicaSe.this.mActivatedApp != null) {
                        this.mAccessControlManagerThread = new AccessControlManagerThread(FelicaSe.this.mContext, this.mClientPermitList, FelicaSe.this.mActivatedApp.getPid(), FelicaSe.this.mActivatedApp.getUid());
                    }
                }
                checkInterrupted();
                this.mAccessControlManagerThread.start();
                this.mAccessControlManagerThread.join();
                synchronized (FelicaSe.this) {
                    checkInterrupted();
                    z = false;
                    try {
                        if (this.mAccessControlManagerThread.isSucceeded()) {
                            FelicaSe.this.mAccessController = this.mAccessControlManagerThread.getAccessController();
                            errorMessage = null;
                            errorType = 0;
                            z = true;
                        } else {
                            errorType = this.mAccessControlManagerThread.getErrorType();
                            errorMessage = this.mAccessControlManagerThread.getErrorMessage();
                        }
                    } catch (FelicaException unused) {
                        errorMessage = ERROR_MESSAGE_UNKNOW;
                        errorType = 1;
                    }
                }
                if (z) {
                    synchronized (FelicaSe.this) {
                        FelicaSe.this.mActivateWorker = null;
                    }
                    this.mFelicaEventListner.finished();
                    return;
                }
                synchronized (FelicaSe.this) {
                    FelicaSe.this.unregisterBinder();
                    FelicaSe.this.mActivateWorker = null;
                    FelicaSe.this.mActivatedApp = null;
                }
                if (errorType != 2) {
                    this.mFelicaEventListner.errorOccurred(errorType, errorMessage, null);
                }
            } catch (RemoteException unused2) {
                LogMgr.log(1, "%s RemoteException", "800");
                synchronized (FelicaSe.this) {
                    FelicaSe.this.unregisterBinder();
                    FelicaSe.this.mActivateWorker = null;
                    FelicaSe.this.mActivatedApp = null;
                    FelicaSe.this.mAccessController = null;
                }
            } catch (InterruptedException unused3) {
                LogMgr.log(2, "%s InterrptedException", "801");
            }
        }

        class AccessControlManagerThread extends Thread {
            private AccessControlManager mAccessControlManager;
            private Context mContext;
            private String[] mPermitList;
            private int mPid;
            private int mUid;
            private boolean mSucceeded = false;
            private int mErrorType = 4;
            private String mErrorMessage = null;

            private int convErrorType(int i) {
                if (i == 2) {
                    return 2;
                }
                if (i == 3) {
                    return 3;
                }
                if (i != 4) {
                    return i != 5 ? 1 : 6;
                }
                return 4;
            }

            public AccessControlManagerThread(Context context, String[] strArr, int i, int i2) {
                this.mAccessControlManager = null;
                this.mPermitList = strArr;
                this.mPid = i;
                this.mUid = i2;
                this.mContext = context;
                AccessControlManagerImpl accessControlManagerImpl = new AccessControlManagerImpl();
                if (accessControlManagerImpl instanceof AccessControlManager) {
                    this.mAccessControlManager = accessControlManagerImpl;
                    this.mAccessControlManager.init(context);
                }
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (this.mContext == null) {
                    this.mErrorMessage = null;
                    this.mErrorType = 1;
                    return;
                }
                AccessControlManager accessControlManager = this.mAccessControlManager;
                if (accessControlManager != null) {
                    this.mSucceeded = accessControlManager.startAccessControl(this.mPermitList, this.mPid, this.mUid);
                    if (this.mSucceeded) {
                        return;
                    }
                    this.mErrorMessage = this.mAccessControlManager.getErrorMessage();
                    this.mErrorType = convErrorType(this.mAccessControlManager.getErrorType());
                    return;
                }
                this.mErrorMessage = null;
                this.mErrorType = 1;
            }

            public void stopVerification() {
                interrupt();
                AccessControlManager accessControlManager = this.mAccessControlManager;
                if (accessControlManager != null) {
                    accessControlManager.stopAccessControl();
                }
            }

            public boolean isSucceeded() {
                return this.mSucceeded;
            }

            public AccessController getAccessController() throws FelicaException {
                if (this.mAccessControlManager != null && isSucceeded()) {
                    return this.mAccessControlManager.getAccessController();
                }
                throw new FelicaException();
            }

            public int getErrorType() throws FelicaException {
                if (!isSucceeded()) {
                    return this.mErrorType;
                }
                LogMgr.log(2, "%s isScceeded() is false", "998");
                throw new FelicaException();
            }

            public String getErrorMessage() throws FelicaException {
                if (!isSucceeded()) {
                    return this.mErrorMessage;
                }
                LogMgr.log(2, "%s isScceeded() is false", "998");
                throw new FelicaException();
            }
        }
    }

    private FelicaSe() {
        this.mChipController = new FelicaSeController();
    }

    public static synchronized FelicaSe getInstance() {
        if (sInstance == null) {
            sInstance = new FelicaSe();
        }
        return sInstance;
    }

    public synchronized void activateFelica(String[] strArr, IFelicaEventListener iFelicaEventListener, IBinder iBinder) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In permitList = %s, listener = %s", "000", strArr, iFelicaEventListener);
        if (iBinder == null) {
            LogMgr.log(1, "%s binder == null", "800");
            throw new FelicaException(1, 47);
        }
        if (iFelicaEventListener == null) {
            LogMgr.log(1, "%s listener == null", "801");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        if (strArr != null && strArr.length > 50) {
            LogMgr.log(1, "%s permitList.length > MAX_PERMIT_LIST_SIZE", "801");
            throw new IllegalArgumentException("The size of permit list exceeds the maximum value.");
        }
        if (this.mActivatedApp != null) {
            if (this.mActivatedApp.getPid() == Binder.getCallingPid() && this.mActivatedApp.getUid() == Binder.getCallingUid()) {
                LogMgr.log(1, "%s activatedApp.getPID(UID) == Binder.getCallingPid(Uid)", "804");
                throw new FelicaException(2, 42);
            }
            throw new FelicaException(2, 39, new AppInfo(this.mActivatedApp.getPid()));
        }
        if (!registerBinder(iBinder)) {
            LogMgr.log(1, "%s binder == null", "800");
            throw new FelicaException(1, 47);
        }
        this.mActivatedApp = new Felica.FelicaAppInfo();
        this.mActivatedApp.setPid(Binder.getCallingPid());
        this.mActivatedApp.setUid(Binder.getCallingUid());
        ActivateThread activateThread = new ActivateThread();
        activateThread.mClientPermitList = strArr;
        activateThread.mFelicaEventListner = iFelicaEventListener;
        this.mActivateWorker = activateThread;
        this.mActivateWorker.start();
    }

    public synchronized void inactivateFelica() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        doInactivateFelica(true);
    }

    public synchronized void doInactivateFelica(boolean z) throws FelicaException {
        LogMgr.log(4, "%s In isCheckProcess = %s", "000", Boolean.valueOf(z));
        if (z) {
            checkPidUid();
        }
        if (this.mOpened) {
            LogMgr.log(1, "%s opened == true", "800");
            throw new FelicaException(2, 37);
        }
        unregisterBinder();
        if (this.mActivateWorker != null) {
            try {
                this.mActivateWorker.interrupt();
                if (this.mActivateWorker.mAccessControlManagerThread != null) {
                    this.mActivateWorker.mAccessControlManagerThread.stopVerification();
                }
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "801", e.getClass().getSimpleName());
            }
            this.mActivateWorker = null;
        }
        this.mAccessController = null;
        this.mActivatedApp = null;
        this.mSelectTimeout = 1000;
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void open() throws FelicaException {
        open(true);
    }

    private synchronized void open(boolean z) throws FelicaException {
        boolean z2;
        LogMgr.log(4, "%s", "000");
        if (z) {
            checkActivated();
        }
        checkNotOnline();
        if (this.mOpened) {
            return;
        }
        super.doOpen();
        try {
            this.mChipController.connect(1000);
            try {
                checkAccessRight(4);
            } catch (FelicaException unused) {
                try {
                    checkAccessRight(5);
                } catch (FelicaException unused2) {
                    z2 = false;
                }
            } catch (NumberFormatException unused3) {
                this.mChipController.close();
                LogMgr.log(1, "%s", "809");
                throw new FelicaException(1, 8);
            }
            z2 = true;
            if (!z2) {
                try {
                    try {
                        try {
                            try {
                                try {
                                    if (AccessConfig.isValidContainerIssueInfo(this.mChipController.getContainerIssueInfo(this.mChipController.polling(65535, 1000, 0, (byte) 1).getIdm(), 1000, 0))) {
                                        this.mOpened = true;
                                        return;
                                    } else {
                                        this.mChipController.close();
                                        LogMgr.log(1, "%s FelicaException failed not chip format== true", "804");
                                        throw new FelicaException(8, 31);
                                    }
                                } catch (Exception unused4) {
                                    LogMgr.log(1, "%s Exception failed Container Issue Information == true", "807");
                                    throw new FelicaException(1, 8);
                                }
                            } catch (OfflineException e) {
                                if (e.getType() == 8) {
                                    LogMgr.log(2, "%s OfflineException.TYPE_OFFLINE_CANCELED_OCCURRED", "702");
                                    throw new FelicaException(1, 8);
                                }
                                LogMgr.log(1, "%s FelicaException failed Container Issue Information == true", "805");
                                throw convException(e, 8);
                            }
                        } catch (FelicaException e2) {
                            LogMgr.log(1, "%s FelicaException failed not chip format== true", "806");
                            throw e2;
                        }
                    } finally {
                        if (!this.mOpened) {
                            LogMgr.log(2, "%s", "008");
                            this.mChipController.close();
                        }
                    }
                } catch (OfflineException e3) {
                    this.mChipController.close();
                    if (e3.getType() == 8) {
                        LogMgr.log(2, "%s OfflineException.TYPE_OFFLINE_CANCELED_OCCURRED", "701");
                        throw new FelicaException(1, 8);
                    }
                    LogMgr.log(1, "%s FelicaException failed polling == true", "802");
                    throw convException(e3, 8);
                } catch (Exception unused5) {
                    this.mChipController.close();
                    LogMgr.log(1, "%s Exception failed polling == true", "803");
                    throw new FelicaException(1, 8);
                }
            }
            this.mOpened = true;
        } catch (OfflineException e4) {
            this.mChipController.close(false);
            if (e4.getType() == 8) {
                LogMgr.log(2, "%s OfflineException.TYPE_OFFLINE_CANCELED_OCCURRED", "701");
                throw new FelicaException(1, 8);
            }
            LogMgr.log(1, "%s FelicaException failed polling == true", "802");
            throw convException(e4, 8);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void close() throws FelicaException {
        doClose(true);
        if (this.mFscStarting) {
            this.mFelicaCloseInFscStarting = true;
        }
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void doClose(boolean z) throws FelicaException {
        LogMgr.log(4, "%s, isCheckProcess = %s, callingPid=%d, callingUid=%d", "000", Boolean.valueOf(z), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(Binder.getCallingUid()));
        if (z) {
            checkActivated();
        }
        if (this.mOpened) {
            if (this.mFscAdapter != null) {
                LogMgr.log(2, "%s, force stop online", "701");
                this.mFscAdapter.kill();
            }
            if (!isConnected()) {
                try {
                    this.mChipController.connect(1000);
                } catch (OfflineException unused) {
                }
            }
            this.mChipController.close();
            super.doClose();
            this.mFscAdapter = null;
            LogMgr.log(4, "%s", "999");
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void select(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d", "000", Integer.valueOf(i));
        checkActivated();
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(i));
            throw new IllegalArgumentException("The specified System Code is out of range.");
        }
        if (i == 65535 || (i & 255) == 255 || (i & FelicaConst.WILD_CARD_SYSTEM_CODE3) == 65280) {
            LogMgr.log(1, "%s systemCode = %d", "805", Integer.valueOf(i));
            throw new IllegalArgumentException("The specified System Code is out of range.");
        }
        checkOpened();
        checkNotOnline();
        try {
            checkAccessRight(1);
            checkAccessSystemCode(i);
            int iCurrentTimeMillis = this.mSelectTimeout;
            if (!isConnected()) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                try {
                    this.mChipController.connect(iCurrentTimeMillis);
                    iCurrentTimeMillis -= (int) (System.currentTimeMillis() - jCurrentTimeMillis);
                    if (iCurrentTimeMillis <= 0) {
                        throw new FelicaException(3, 7);
                    }
                    try {
                        SystemInfo systemInfoPolling = this.mChipController.polling(i, iCurrentTimeMillis, 0, (byte) 1);
                        this.mSelected = true;
                        this.mSystemInfo = systemInfoPolling;
                    } catch (OfflineException e) {
                        LogMgr.log(1, "%s OfflineException.TYPE_SELECT_FAILED", "801");
                        throw convException(e, 9);
                    } catch (Exception unused) {
                        LogMgr.log(1, "%s Exception", "804");
                        throw new FelicaException(1, 9);
                    }
                } catch (OfflineException e2) {
                    LogMgr.log(1, "%s OfflineException", "802");
                    throw convException(e2, 9);
                }
            } else {
                SystemInfo systemInfoPolling2 = this.mChipController.polling(i, iCurrentTimeMillis, 0, (byte) 1);
                this.mSelected = true;
                this.mSystemInfo = systemInfoPolling2;
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "806");
            throw new FelicaException(1, 9);
        }
    }

    public synchronized void select(int i, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d", "000", Integer.valueOf(i));
        checkActivated();
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(i));
            throw new IllegalArgumentException("The specified System Code is out of range.");
        }
        if (i != 65535 && (i & 255) != 255 && (i & FelicaConst.WILD_CARD_SYSTEM_CODE3) != 65280) {
            checkOpened();
            checkNotOnline();
            try {
                checkAccessRight(1);
                checkAccessSystemCode(i);
                LogMgr.log(1, "%s Exception", "804");
                throw new FelicaException(1, 9);
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "806");
                throw new FelicaException(1, 9);
            }
        }
        LogMgr.log(1, "%s systemCode = %d", "805", Integer.valueOf(i));
        throw new IllegalArgumentException("The specified System Code is out of range.");
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int getSystemCode() throws FelicaException {
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getSystemCode();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getIdm() throws FelicaException {
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getIdm();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getIcCode() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getIcCode();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int getKeyVersion(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In serviceCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkActivated();
        ServiceUtil.getInstance().checkServiceCode(i, this.mNodeCodeSize);
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        if (this.mNodeCodeSize != 2 ? i != -1 : (i & 65535) != 65535) {
            try {
                checkAccessNodeCodeList(this.mSystemInfo.getSystemCode(), new int[]{i});
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "805");
                throw new FelicaException(1, 10);
            }
        }
        return super.getKeyVersion(i, i2, i3);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized KeyInformation[] getKeyVersionV2(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCode = %d timeout = %d retryCount = %d", "000", iArr, Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        for (int i3 : iArr) {
            ServiceUtil.getInstance().checkServiceCode(i3, 2);
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        for (int i4 = 0; i4 < iArr.length; i4++) {
            if ((iArr[i4] & 65535) != 65535) {
                try {
                    checkAccessNodeCodeList(this.mSystemInfo.getSystemCode(), new int[]{iArr[i4]});
                } catch (NumberFormatException unused) {
                    LogMgr.log(1, "%s Exception", "805");
                    throw new FelicaException(1, 64);
                }
            }
        }
        return super.getKeyVersionV2(iArr, i, i2);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getContainerIssueInformation(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getContainerIssueInformation(i, i2);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void setNodeCodeSize(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCodeSize = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkActivated();
        if (i != 2 && i != 4) {
            LogMgr.log(1, "%s invalid nodeCodeSize", "800");
            throw new IllegalArgumentException("The specified NodeCodeSize is invalid value.");
        }
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        super.setNodeCodeSize(i, i2, i3);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized Data[] read(BlockList blockList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockList = %s timeout = %d retryCount = %d", "000", blockList, Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        if (blockList == null || blockList.size() == 0) {
            LogMgr.log(1, "%s invalid blockList", "800");
            throw new IllegalArgumentException("The specified BlockList is null or empty.");
        }
        blockList.checkFormat();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        int[] iArr = new int[blockList.size()];
        for (int i3 = 0; i3 < blockList.size(); i3++) {
            iArr[i3] = blockList.get(i3).getServiceCode();
        }
        try {
            checkAccessNodeCodeList(this.mSystemInfo.getSystemCode(), iArr);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "810");
            throw new FelicaException(1, 14);
        }
        return super.read(blockList, i, i2);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void write(BlockDataList blockDataList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockDataList = %s timeout = %d retryCount = %d", "000", blockDataList, Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        if (blockDataList == null || blockDataList.size() == 0) {
            LogMgr.log(1, "%s invalid blockDataList", "800");
            throw new IllegalArgumentException("The specified BlockDataList is null or empty.");
        }
        blockDataList.checkFormat();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        int[] iArr = new int[blockDataList.size()];
        for (int i3 = 0; i3 < blockDataList.size(); i3++) {
            iArr[i3] = blockDataList.get(i3).getBlock().getServiceCode();
        }
        try {
            checkAccessNodeCodeList(this.mSystemInfo.getSystemCode(), iArr);
            try {
                try {
                    this.mChipController.writeWithoutEncryption(this.mNodeCodeSize, this.mSystemInfo.getIdm(), blockDataList, getTimeout(), getRetryCount());
                } catch (OfflineException e) {
                    if (e.getType() == 2) {
                        throw new IllegalArgumentException();
                    }
                    if (e.getType() == 6 && e.getStatusFlag1() == 255 && e.getStatusFlag2() == 113) {
                        LogMgr.log(1, "%s OfflineException.TYPE_INVALID_STATUS_FLAG(INTERFACE_WIRED)", "813");
                    } else {
                        int[] iArr2 = {1, 15, 2, 16, 80, 17, 81, 18, 82, 19, 83, 13, 165, 21, 166, 11, 168, 12};
                        LogMgr.log(1, "%s OfflineException", "801");
                        throw convException(e, 6, 20, iArr2);
                    }
                }
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "815");
                throw new FelicaException(1, 20);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s OfflineException", "816");
            throw new FelicaException(1, 20);
        }
    }

    public synchronized void reset(boolean z, boolean z2) throws FelicaException {
        LogMgr.log(4, "%s", "000");
        checkActivated();
        checkOpened();
        checkNotOnline();
        doReset(false, z, z2);
    }

    public synchronized void doReset(boolean z, boolean z2, boolean z3) throws FelicaException {
        if (z) {
            checkOpened();
            checkNotOnline();
            try {
                if (!isConnected() && z2) {
                    this.mChipController.connect(1000);
                }
                this.mChipController.reset(z2, true, z3);
                this.mNodeCodeSize = 2;
                this.mSelected = false;
                this.mSystemInfo = null;
            } catch (OfflineException e) {
                if (e.getType() == 8) {
                    throw new FelicaException(1, 44);
                }
                LogMgr.log(1, "%s OfflineException", "801");
                throw convException(e, 44);
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "802");
                throw new FelicaException(1, 44);
            }
        } else {
            if (!isConnected()) {
                this.mChipController.connect(1000);
            }
            this.mChipController.reset(z2, true, z3);
            this.mNodeCodeSize = 2;
            this.mSelected = false;
            this.mSystemInfo = null;
        }
    }

    private void checkNotOnline() throws FelicaException {
        if (this.mFscAdapter != null) {
            throw new FelicaException(2, 2);
        }
    }

    public synchronized void checkAccessRight(int i) throws NumberFormatException, FelicaException {
        if (this.mAccessController == null) {
            throw new FelicaException(12, 38);
        }
        try {
            this.mAccessController.check(i);
        } catch (AccessControllerException e) {
            throw convException(e, 38);
        }
    }

    private void checkAccessSystemCode(int i) throws NumberFormatException, FelicaException {
        try {
            this.mAccessController.checkSystemCode(i);
        } catch (AccessControllerException e) {
            throw convException(e, 50);
        }
    }

    private void checkAccessNodeCodeList(int i, int[] iArr) throws NumberFormatException, FelicaException {
        try {
            this.mAccessController.checkNodeCodeList(i, iArr);
        } catch (AccessControllerException e) {
            throw convException(e, 50);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int[] getSystemCodeList(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
            checkAccessRight(4);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 45);
        }
        return super.getSystemCodeList(i, i2);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized NodeInformation getNodeInformation(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkActivated();
        ServiceUtil.getInstance().checkAreaCode(i);
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
            checkAccessRight(4);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 34);
        }
        return super.getNodeInformation(i, i2, i3);
    }

    public synchronized NodeInformation getPrivacyNodeInformation(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkActivated();
        ServiceUtil.getInstance().checkAreaCode(i);
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        checkSelectedInterfaceWired();
        try {
            checkAccessNodeCodeList(this.mSystemInfo.getSystemCode(), new int[]{i});
            try {
                try {
                } catch (OfflineException e) {
                    int[] iArr = {166, 11, 209, 33};
                    LogMgr.log(1, "%s OfflineException", "800");
                    throw convException(e, 10, 35, iArr);
                }
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 35);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 35);
        }
        return this.mChipController.requestMaskedCodeList(this.mSystemInfo.getIdm(), i, getTimeout(), getRetryCount());
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized BlockCountInformation[] getBlockCountInformation(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        checkActivated();
        if (iArr == null || iArr.length < 1 || iArr.length > 32) {
            LogMgr.log(1, "%s invalid NodeCodeList", "800");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        for (int i3 : iArr) {
            ServiceUtil.getInstance().checkServiceCode(i3, this.mNodeCodeSize);
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
            checkAccessRight(4);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "803");
            throw new FelicaException(1, 43);
        }
        return super.getBlockCountInformation(iArr, i, i2);
    }

    public synchronized void setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        checkActivated();
        if (privacySettingDataArr == null || privacySettingDataArr.length < 1 || privacySettingDataArr.length > 15) {
            LogMgr.log(1, "%s invalid privacySettingData", "800");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        for (PrivacySettingData privacySettingData : privacySettingDataArr) {
            privacySettingData.checkFormat();
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        checkSelectedInterfaceWired();
        try {
            checkAccessRight(5);
            try {
                try {
                    this.mChipController.setPrivacyFlag(this.mSystemInfo.getIdm(), privacySettingDataArr, getTimeout(), getRetryCount());
                } catch (OfflineException e) {
                    if (e.getType() == 6 && e.getStatusFlag1() == 255 && e.getStatusFlag2() == 113) {
                        LogMgr.log(1, "%s OfflineException.TYPE_INVALID_STATUS_FLAG(INTERFACE_WIRED)", "813");
                    } else {
                        int[] iArr = {166, 11};
                        LogMgr.log(1, "%s OfflineException", "801");
                        throw convException(e, 11, 36, iArr);
                    }
                }
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "802");
                throw new FelicaException(1, 36);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "803");
            throw new FelicaException(1, 36);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getContainerId(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        try {
            checkAccessRight(4);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 46);
        }
        return super.getContainerId(i, i2);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    protected FelicaException convException(OfflineException offlineException, int i) {
        return convException(offlineException, 1, i, null);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    protected FelicaException convException(OfflineException offlineException, int i, int i2, int[] iArr) {
        FelicaException felicaException;
        LogMgr.log(4, "In oe = %s, id = %d, type = %d, supportedCode = %d", offlineException, Integer.valueOf(i), Integer.valueOf(i2), iArr);
        switch (offlineException.getType()) {
            case 4:
                return new FelicaException(3, 6, offlineException.getMessage());
            case 5:
                return new FelicaException(3, 7);
            case 6:
                if (iArr != null) {
                    if (iArr.length <= 1 || iArr.length % 2 != 0) {
                        felicaException = null;
                    } else {
                        for (int i3 = 0; i3 < iArr.length; i3 += 2) {
                            if (offlineException.getStatusFlag2() == iArr[i3]) {
                                felicaException = new FelicaException(i, iArr[i3 + 1], offlineException.getStatusFlag1(), offlineException.getStatusFlag2());
                            }
                        }
                        felicaException = null;
                    }
                    return felicaException == null ? new FelicaException(i, i2, offlineException.getStatusFlag1(), offlineException.getStatusFlag2()) : felicaException;
                }
                return new FelicaException(1, i2);
            case 7:
                if (i2 == 8) {
                    return new FelicaException(8, 55);
                }
                return new FelicaException(3, 7);
            case 8:
                return new FelicaException(3, 58);
            case 9:
                return new FelicaException(1, i2, FelicaException.NFC_RW_USED_MESSAGE);
            default:
                return new FelicaException(1, i2);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void setContext(Context context) {
        if (context != null) {
            this.mContext = context;
            this.mUserAgent = AccessConfig.getUserAgent(context);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void setMfcListener(MfcListener mfcListener) {
        if (mfcListener != null) {
            this.mMfcListener = mfcListener;
        }
    }

    public String getUserAgent() {
        return this.mUserAgent;
    }

    public synchronized void checkOnlineAccess() throws NumberFormatException, FelicaException {
        checkPidUid();
        if (!this.mOpened) {
            throw new FelicaException(2, 1);
        }
        checkAccessRight(2);
        this.mFscStarting = true;
        this.mFelicaCloseInFscStarting = false;
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void setSelectTimeout(int i) throws FelicaException {
        checkActivated();
        super.setSelectTimeout(i);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int getSelectTimeout() throws FelicaException {
        checkActivated();
        return super.getSelectTimeout();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public void cancelOffline() throws FelicaException {
        checkActivated();
        try {
            checkOpenedNosync();
            checkNotOnline();
            super.cancelOffline();
        } catch (Exception unused) {
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] executeFelicaCommand(byte[] bArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        checkActivated();
        if (bArr == null || bArr.length <= 0 || bArr.length > 254) {
            LogMgr.log(1, "invalid Command");
            throw new IllegalArgumentException("The specified Command is null or invalid size.");
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        try {
            checkAccessRight(5);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s", "NumberFormatException");
            throw new FelicaException(1, 63);
        }
        return super.executeFelicaCommand(bArr, i, i2);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void resetInner(boolean z, boolean z2, boolean z3) throws FelicaException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        checkOpened();
        if (!isConnected() && z) {
            try {
                this.mChipController.connect(1000);
            } catch (OfflineException e) {
                if (e.getType() == 8) {
                    throw new FelicaException(1, 44);
                }
                LogMgr.log(1, "%s OfflineException", "801");
                throw convException(e, 44);
            }
        }
        super.resetInner(z, z2, z3);
    }

    public void cancelOfflineInner() throws FelicaException {
        try {
            checkActivated();
        } catch (FelicaException unused) {
            if (Binder.getCallingPid() != Process.myPid()) {
                throw new FelicaException(1, 47);
            }
        }
        checkOpenedNosync();
        super.cancelOffline();
    }

    private void checkActivated() throws FelicaException {
        checkPidUid();
        checkActivateWorker();
    }

    public void checkPidUid() throws FelicaException {
        checkPidUid(Binder.getCallingPid(), Binder.getCallingUid());
    }

    void checkPidUid(int i, int i2) throws FelicaException {
        Felica.FelicaAppInfo felicaAppInfo = this.mActivatedApp;
        if (felicaAppInfo == null) {
            LogMgr.log(1, "%s activatedApp == null", "800");
            throw new FelicaException(2, 5);
        }
        try {
            if (felicaAppInfo.getPid() == i && this.mActivatedApp.getUid() == i2) {
                return;
            }
            LogMgr.log(1, "%s activatedApp.getPID() = %d, activatedApp.getUID() = %d", "801", Integer.valueOf(this.mActivatedApp.getPid()), Integer.valueOf(this.mActivatedApp.getUid()));
            throw new FelicaException(2, 5);
        } catch (NullPointerException unused) {
            LogMgr.log(1, "%s NullPointerException", "802");
            throw new FelicaException(2, 5);
        }
    }

    public synchronized void checkStatus() throws FelicaException {
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
    }

    public synchronized void reconnect(boolean z, int i) throws FelicaException, IllegalArgumentException {
        if (z) {
            select(i);
        } else {
            checkActivated();
            checkOpened();
            checkNotOnline();
            try {
                this.mChipController.connect(this.mSelectTimeout);
            } catch (OfflineException e) {
                LogMgr.log(1, "%s OfflineException", "802");
                throw convException(e, 9);
            }
        }
    }

    private void checkActivateWorker() throws FelicaException {
        if (this.mActivateWorker == null) {
            return;
        }
        LogMgr.log(1, "%s activateWorker != null", "800");
        throw new FelicaException(2, 5);
    }

    private synchronized boolean registerBinder(IBinder iBinder) {
        LogMgr.log(3, "%s", "000");
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
        LogMgr.log(3, "%s", "000");
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
            LogMgr.log(3, "%s", "999");
        }

        IBinder getBinder() {
            return this.mBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            LogMgr.log(3, "%s", "000");
            synchronized (FelicaSe.this) {
                FelicaSe.this.mDeathRecipient = null;
            }
            FelicaSe.this.mMfcListener.mfcCancel();
        }
    }
}
