package com.felicanetworks.mfc.felica;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.se.omapi.Channel;
import android.se.omapi.Reader;
import android.se.omapi.Session;
import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FSCAdapter;
import com.felicanetworks.mfc.FelicaConst;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.MfcListener;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.ServiceUtil;
import com.felicanetworks.mfc.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.felica.access_control.AccessControlManagerImpl;
import com.felicanetworks.mfc.felica.access_control.AccessController;
import com.felicanetworks.mfc.felica.access_control.AccessControllerException;
import com.felicanetworks.mfc.felica.offlineimpl.FelicaGpController;
import com.felicanetworks.mfc.felica.offlineimpl.OfflineException;
import com.felicanetworks.mfc.felica.offlineimpl.SystemInfo;
import com.felicanetworks.mfc.felica.omapi.GetInstanceStatusResponse;
import com.felicanetworks.mfc.felica.omapi.InstanceStatus;
import com.felicanetworks.mfc.felica.omapi.SelectResponse;
import com.felicanetworks.mfc.tcap.impl.IllegalStateErrorException;
import com.felicanetworks.mfc.util.LogMgr;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

/* JADX INFO: loaded from: classes.dex */
public class FelicaGp {
    private static final String ACTION_TRANSACTION_FINISHED = "com.felicanetworks.mfc.action.TRANSACTION_FINISHED";
    private static final String ALL_00_CID = "ALL_00";
    private static final String ALL_FF_CID = "ALL_FF";
    private static final int CID_LENGTH = 63;
    public static final int DEFAULT_RETRY_COUNT = 0;
    public static final int DEFAULT_TIMEOUT = 1000;
    private static final String EXC_INVALID_BLOCK_DATA_LIST = "The specified BlockDataList is null or empty.";
    private static final String EXC_INVALID_BLOCK_LIST = "The specified BlockList is null or empty.";
    private static final String EXC_INVALID_CID = "The specified CID is null or invalid value.";
    private static final String EXC_INVALID_COMMAND = "The specified Command is null or invalid size.";
    public static final String EXC_INVALID_LISTENER = "The specified parameter is invalid.";
    private static final String EXC_INVALID_NODECODESIZE = "The specified NodeCodeSize is invalid value.";
    private static final String EXC_INVALID_NODE_CODE_LIST = "The specified parameter is invalid.";
    private static final String EXC_INVALID_PRIVACY_SETTING_DATA_LIST = "The specified parameter is invalid.";
    private static final String EXC_INVALID_SET_PUSH_LISTENER_PRM = "The specified parameter is invalid.";
    private static final String EXC_INVALID_SYSTEM_CODE = "The specified System Code is out of range.";
    private static final String EXC_INVALID_TARGET = "The specified Target is invalid value.";
    private static final String EXC_MAX_SIZE_PERMIT_LIST = "The size of permit list exceeds the maximum value.";
    private static final String EXTRA_AID = "com.felicanetworks.mfc.EXTRA_AID";
    private static final String EXTRA_DATA = "com.felicanetworks.mfc.EXTRA_DATA";
    private static final String EXTRA_SECURE_ELEMENT_NAME = "com.felicanetworks.mfc.EXTRA_SECURE_ELEMENT_NAME";
    private static final String EXTRA_TIME = "com.felicanetworks.mfc.EXTRA_TIME";
    private static final String EXTRA_UUID = "com.felicanetworks.mfc.EXTRA_UUID";
    private static final int INSTANCE_STATUS_LIST_SIZE = 2;
    private static final String INVALID_CID_0 = "000000000000000000000000000000000000000000000000000000000000000";
    private static final String INVALID_CID_F = "fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff";
    private static final int MANAGEMENT_SYSTEM_CODE = 65039;
    private static final int MANAGEMENT_SYSTEM_INSTANCE_STATUS_INDEX = 0;
    public static final int MAX_PERMIT_LIST_SIZE = 50;
    public static final int MAX_RETRY_COUNT = 10;
    public static final int MAX_TIMEOUT = 60000;
    private static final String MENU_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    public static final int MIN_RETRY_COUNT = 0;
    public static final int MIN_TIMEOUT = 0;
    private static final String RECEIVER_CLASS_NAME = "com.felicanetworks.tis.TapInteractionReceiver";
    private static final int SYSTEM0_INSTANCE_STATUS_INDEX = 1;
    private static final String TAG = "MfcService";
    private static final String TIS_CACHE_FILE = "tisCache";
    private static final int TIS_EXTRA_CACHE_MAX = 35;
    private static FelicaGp sInstance;
    private ActivateThread mActivateWorker;
    private FelicaAppInfo mActivatedApp;
    private Channel mChannel;
    private Context mContext;
    private boolean mCrsActivated;
    private LocalDeathRecipient mDeathRecipient;
    private boolean mFelicaCloseInFscStarting;
    private FSCAdapter mFscAdapter;
    private boolean mFscStarting;
    private MfcListener mMfcListener;
    private boolean mOpened;
    private boolean mPersonalized;
    private Reader mReader;
    private boolean mSelected;
    private Session mSession;
    private SystemInfo mSystemInfo;
    protected int mTimeout;
    private Channel mTransactionChannel;
    private static String sEseReaderName = AccessConfig.getEseReaderName();
    private static final byte[] TRANSACTION_MANAGEMENT_AID = {-96, 0, 0, 6, IllegalStateErrorException.TYPE_ILLEGAL_STATE, 2, 0, 0, 0, 3, 0, 0, 0, 2, 0, 0};
    private static final byte[] MANAGEMENT_SYSTEM_AID = {-96, 0, 0, 6, IllegalStateErrorException.TYPE_ILLEGAL_STATE, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0};
    private static final byte[] SYSTEM0_AID = {-96, 0, 0, 6, IllegalStateErrorException.TYPE_ILLEGAL_STATE, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0, 1};
    private static final byte[] CONTAINER_ISSUE_ALL0 = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
    private static final byte[] IDM_NOT_PERSONALIZED = {2, -2, 0, 0, 0, 0, 0, 0};
    private static final byte[] PMM_NOT_PERSONALIZED = {-1, -1, -1, -1, -1, -1, -1, -1};
    private int mSelectTimeout = 1000;
    private AccessController mAccessController = null;
    private String mUserAgent = null;
    private final byte[] FELICA_AID_PARTIAL = {-96, 0, 0, 6, IllegalStateErrorException.TYPE_ILLEGAL_STATE, 2, 0, 0, 0, 3, 0, 0, 0, 0, 0};
    private byte[] mSelectedAid = null;
    private String mSelectedCid = null;
    private String mReconnectCid = null;
    private int mSystem0InstanceCode = 65536;
    protected int mRetryCount = 0;
    private int mNodeCodeSize = 2;
    private FelicaGpController mChipController = new FelicaGpController();

    void checkSelectedInterfaceWired() throws FelicaException {
    }

    public class FelicaAppInfo {
        int mPid;
        int mUid;

        public FelicaAppInfo() {
        }

        public int getUid() {
            return this.mUid;
        }

        public void setUid(int i) {
            this.mUid = i;
        }

        public int getPid() {
            return this.mPid;
        }

        public void setPid(int i) {
            this.mPid = i;
        }
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
                synchronized (FelicaGp.this) {
                    if (FelicaGp.this.mActivatedApp != null) {
                        this.mAccessControlManagerThread = new AccessControlManagerThread(FelicaGp.this.mContext, this.mClientPermitList, FelicaGp.this.mActivatedApp.getPid(), FelicaGp.this.mActivatedApp.getUid());
                    }
                }
                checkInterrupted();
                this.mAccessControlManagerThread.start();
                this.mAccessControlManagerThread.join();
                synchronized (FelicaGp.this) {
                    checkInterrupted();
                    z = false;
                    try {
                        if (this.mAccessControlManagerThread.isSucceeded()) {
                            FelicaGp.this.mAccessController = this.mAccessControlManagerThread.getAccessController();
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
                    synchronized (FelicaGp.this) {
                        FelicaGp.this.mActivateWorker = null;
                    }
                    this.mFelicaEventListner.finished();
                    return;
                }
                synchronized (FelicaGp.this) {
                    FelicaGp.this.unregisterBinder();
                    FelicaGp.this.mActivateWorker = null;
                    FelicaGp.this.mActivatedApp = null;
                }
                if (errorType != 2) {
                    this.mFelicaEventListner.errorOccurred(errorType, errorMessage, null);
                }
            } catch (RemoteException unused2) {
                LogMgr.log(1, "%s RemoteException", "800");
                synchronized (FelicaGp.this) {
                    FelicaGp.this.unregisterBinder();
                    FelicaGp.this.mActivateWorker = null;
                    FelicaGp.this.mActivatedApp = null;
                    FelicaGp.this.mAccessController = null;
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

    private FelicaGp() {
    }

    public static synchronized FelicaGp getInstance() {
        if (sInstance == null) {
            sInstance = new FelicaGp();
        }
        return sInstance;
    }

    protected int getTimeout() {
        return this.mTimeout;
    }

    protected void setTimeout(int i) {
        if (i < 0) {
            this.mTimeout = 0;
        } else if (i > 60000) {
            this.mTimeout = 60000;
        } else {
            this.mTimeout = i;
        }
    }

    protected int getRetryCount() {
        return this.mRetryCount;
    }

    protected void setRetryCount(int i) {
        if (i < 0) {
            this.mRetryCount = 0;
        } else if (i > 10) {
            this.mRetryCount = 10;
        } else {
            this.mRetryCount = i;
        }
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
            throw new IllegalArgumentException(EXC_MAX_SIZE_PERMIT_LIST);
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
        this.mActivatedApp = new FelicaAppInfo();
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
        selectTransactionMgmtApplet();
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
        try {
            LogMgr.log(4, "%s", "001");
            doOpen();
            this.mChipController.connect();
            try {
                checkAccessRight(4);
            } catch (FelicaException unused) {
                try {
                    checkAccessRight(5);
                } catch (FelicaException unused2) {
                    z2 = false;
                }
            } catch (NumberFormatException unused3) {
                closeChip();
                LogMgr.log(1, "%s", "809");
                throw new FelicaException(1, 8);
            }
            z2 = true;
            if (!z2) {
                if (!this.mPersonalized || !this.mCrsActivated) {
                    closeChip();
                    LogMgr.log(1, "%s FelicaException failed not chip format== true", "807");
                    throw new FelicaException(8, 31);
                }
                try {
                    try {
                        try {
                            try {
                                byte[] containerIssueInfo = this.mChipController.getContainerIssueInfo(this.mChannel, this.mChipController.polling(this.mChannel, MANAGEMENT_SYSTEM_CODE, 1000, 0, (byte) 1).getIdm(), 1000, 0);
                                internalReset();
                                if (AccessConfig.isValidContainerIssueInfo(containerIssueInfo)) {
                                    this.mOpened = true;
                                    return;
                                } else {
                                    LogMgr.log(1, "%s FelicaException failed not chip format== true", "804");
                                    throw new FelicaException(8, 31);
                                }
                            } catch (FelicaException e) {
                                LogMgr.log(1, "%s FelicaException failed not chip format== true", "806");
                                throw e;
                            }
                        } catch (OfflineException e2) {
                            if (e2.getType() == 8) {
                                LogMgr.log(2, "%s OfflineException.TYPE_OFFLINE_CANCELED_OCCURRED", "702");
                                throw new FelicaException(1, 8);
                            }
                            LogMgr.log(1, "%s FelicaException failed Container Issue Information == true", "805");
                            throw convException(e2, 8);
                        } catch (Exception unused4) {
                            LogMgr.log(1, "%s Exception failed Container Issue Information == true", "807");
                            throw new FelicaException(1, 8);
                        }
                    } finally {
                        if (!this.mOpened) {
                            LogMgr.log(2, "%s", "008");
                            closeChip();
                        }
                    }
                } catch (OfflineException e3) {
                    closeChip();
                    if (e3.getType() == 8) {
                        LogMgr.log(2, "%s OfflineException.TYPE_OFFLINE_CANCELED_OCCURRED", "701");
                        throw new FelicaException(1, 8);
                    }
                    LogMgr.log(1, "%s FelicaException failed polling == true", "802");
                    throw convException(e3, 8);
                } catch (Exception unused5) {
                    closeChip();
                    LogMgr.log(1, "%s Exception failed polling == true", "803");
                    throw new FelicaException(1, 8);
                }
            }
            this.mOpened = true;
        } catch (FelicaException e4) {
            closeChip();
            LogMgr.log(1, "FelicaException occurred:open == true", "800");
            throw e4;
        } catch (Exception e5) {
            closeChip();
            LogMgr.log(1, "Unexpected Exception occurred: " + e5.getClass().getSimpleName() + e5.getMessage());
            throw new FelicaException(1, 8);
        }
    }

    private Reader getReader() throws FelicaException {
        Reader[] readers = this.mChipController.mSEServiceWrapper.getReaders();
        if (readers != null) {
            for (Reader reader : readers) {
                String name = this.mChipController.mReaderWrapper.getName(reader);
                if (name != null && name.equals(sEseReaderName) && this.mChipController.mReaderWrapper.isSecureElementPresent(reader)) {
                    return reader;
                }
            }
        }
        LogMgr.log(1, "%s eSE reader not found!", "800");
        throw new FelicaException(1, 8);
    }

    private synchronized void doOpen() throws FelicaException {
        this.mFscAdapter = null;
        this.mSelected = false;
        try {
            this.mChipController.mSEServiceWrapper.initSEService(this.mContext);
            if (!this.mChipController.mSEServiceWrapper.isConnected()) {
                LogMgr.log(1, "%s SEService is not connected.", "800");
                throw new FelicaException(1, 8);
            }
            this.mReader = getReader();
            try {
                this.mSession = this.mChipController.mReaderWrapper.openSession(this.mReader);
                try {
                    updateLifeCycleState();
                    if (!this.mPersonalized || !this.mCrsActivated) {
                        LogMgr.log(6, "%s", "001");
                        return;
                    }
                    LogMgr.log(6, "%s", "002");
                    try {
                        checkTypeFCurrentProtocolData();
                        return;
                    } catch (FelicaException e) {
                        LogMgr.log(1, "%s FelicaException", "802");
                        throw e;
                    } catch (Exception unused) {
                        LogMgr.log(1, "%s Exception", "803");
                        throw new FelicaException(1, 8);
                    }
                } catch (FelicaException e2) {
                    LogMgr.log(1, "%s FelicaException", "800");
                    throw e2;
                } catch (Exception unused2) {
                    LogMgr.log(1, "%s Exception", "801");
                    throw new FelicaException(1, 8);
                }
            } catch (OfflineException e3) {
                throw convException(e3, 8);
            }
        } catch (InterruptedException unused3) {
            LogMgr.log(1, "%s InterruptedException", "802");
            throw new FelicaException(1, 8);
        } catch (NullPointerException unused4) {
            LogMgr.log(1, "%s NullPointerException", "801");
            throw new FelicaException(1, 8);
        }
    }

    public synchronized void close() throws FelicaException {
        notifyWiredTransaction();
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
                this.mChipController.connect();
            }
            this.mChipController.setProtectProcessKill(true);
            LogMgr.log(6, "%s", "001");
            deselectTransactionMgmtApplet();
            closeChip();
            LogMgr.log(6, "%s", "002");
            this.mChipController.setProtectProcessKill(false);
            this.mOpened = false;
            this.mPersonalized = false;
            this.mCrsActivated = false;
            this.mSelectedCid = null;
            this.mReconnectCid = null;
            this.mSelectedAid = null;
            this.mSystemInfo = null;
            this.mSystem0InstanceCode = 65536;
            this.mFscAdapter = null;
            this.mSelected = false;
            this.mNodeCodeSize = 2;
            this.mTimeout = 1000;
            this.mRetryCount = 0;
            this.mSelectTimeout = 1000;
            LogMgr.log(4, "%s", "999");
        }
    }

    private void closeChip() {
        internalReset();
        try {
            this.mChipController.mChannelWrapper.close(this.mChannel);
        } catch (OfflineException unused) {
        }
        this.mChipController.disconnect();
        try {
            this.mChipController.mSessionWrapper.close(this.mSession);
        } catch (OfflineException unused2) {
        }
        this.mChipController.mSEServiceWrapper.shutdown();
        this.mReader = null;
        this.mSession = null;
        this.mChannel = null;
    }

    public synchronized void openPreviousChannel() {
        byte[] bArr;
        int systemCode = MANAGEMENT_SYSTEM_CODE;
        if (this.mSystemInfo == null || this.mSelectedCid == null || this.mSelectedAid == null) {
            bArr = MANAGEMENT_SYSTEM_AID;
        } else {
            systemCode = this.mSystemInfo.getSystemCode();
            bArr = this.mSelectedAid;
        }
        try {
            try {
                if (this.mChannel != null && !this.mChipController.mChannelWrapper.isClosed(this.mChannel)) {
                    internalReset();
                    try {
                        this.mChipController.mChannelWrapper.close(this.mChannel);
                    } catch (OfflineException unused) {
                    }
                    this.mChannel = null;
                }
                this.mChannel = this.mChipController.mSessionWrapper.openLogicalChannel(this.mSession, bArr);
                internalReset();
                LogMgr.log(6, "%s Channel opened. SystemCode:%x", "001", Integer.valueOf(systemCode));
                if (this.mSelected && this.mSystemInfo != null && this.mPersonalized) {
                    this.mChipController.setParameter(this.mChannel, this.mSystemInfo.getIdm(), this.mNodeCodeSize, 1000, getRetryCount());
                }
            } catch (Exception unused2) {
                LogMgr.log(2, "Exception occurred");
            }
        } catch (OfflineException unused3) {
        }
    }

    public synchronized void select(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d", "000", Integer.valueOf(i));
        checkSelectStatus(i);
        doSelect(i, (i == MANAGEMENT_SYSTEM_CODE || i == this.mSystem0InstanceCode) ? ALL_FF_CID : ALL_00_CID);
    }

    public synchronized void select(int i, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d cid = %s", "000", Integer.valueOf(i), str);
        checkSelectStatus(i);
        if (str == null || str.length() != 63 || str.equals(INVALID_CID_0) || str.equalsIgnoreCase(INVALID_CID_F)) {
            LogMgr.log(1, "%s", "800");
            throw new IllegalArgumentException(EXC_INVALID_CID);
        }
        doSelect(i, str);
    }

    private synchronized void checkSelectStatus(int i) throws FelicaException {
        LogMgr.log(6, "%s", "000");
        checkActivated();
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(i));
            throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
        }
        if (i == 65535 || (i & 255) == 255 || (i & FelicaConst.WILD_CARD_SYSTEM_CODE3) == 65280) {
            LogMgr.log(1, "%s systemCode = %d", "805", Integer.valueOf(i));
            throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
        }
        checkOpened();
        checkNotOnline();
        try {
            checkAccessRight(1);
            checkAccessSystemCode(i);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "806");
            throw new FelicaException(1, 9);
        }
    }

    private synchronized void doSelect(int i, String str) throws FelicaException {
        LogMgr.log(6, "%s", "000");
        if (this.mPersonalized && this.mCrsActivated) {
            int i2 = this.mSelectTimeout;
            try {
                try {
                    try {
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        if (!isConnected()) {
                            this.mChipController.connect();
                        }
                        appletSelect(i, str, i2);
                        int iCurrentTimeMillis = i2 - ((int) (System.currentTimeMillis() - jCurrentTimeMillis));
                        if (iCurrentTimeMillis <= 0) {
                            throw new FelicaException(3, 7);
                        }
                        long jCurrentTimeMillis2 = System.currentTimeMillis();
                        SystemInfo systemInfoPolling = this.mChipController.polling(this.mChannel, i, iCurrentTimeMillis, 0, (byte) 1);
                        this.mChipController.setParameter(this.mChannel, systemInfoPolling.getIdm(), this.mNodeCodeSize, iCurrentTimeMillis - ((int) (System.currentTimeMillis() - jCurrentTimeMillis2)), getRetryCount());
                        this.mSelected = true;
                        this.mSystemInfo = systemInfoPolling;
                        this.mSelectedCid = str;
                        this.mSelectedAid = new SelectResponse(this.mChipController.mChannelWrapper.getSelectResponse(this.mChannel)).getAid();
                        return;
                    } catch (OfflineException e) {
                        LogMgr.log(1, "%s OfflineException.TYPE_SELECT_FAILED", "801");
                        throw convException(e, 9);
                    }
                } catch (Exception unused) {
                    LogMgr.log(1, "%s Exception", "804");
                    throw new FelicaException(1, 9);
                }
            } catch (FelicaException e2) {
                if (e2.getID() == 3 && e2.getType() == 7) {
                    throw e2;
                }
                LogMgr.log(1, "%s Exception", "804");
                throw new FelicaException(1, 9);
            }
        }
        if (i == MANAGEMENT_SYSTEM_CODE) {
            LogMgr.log(6, "%s NOT PERSONALIZED select", "001");
            this.mSelected = true;
            this.mSystemInfo = this.mChipController.getSystemInfo(IDM_NOT_PERSONALIZED, PMM_NOT_PERSONALIZED, MANAGEMENT_SYSTEM_CODE);
            this.mSelectedCid = ALL_FF_CID;
            this.mSelectedAid = MANAGEMENT_SYSTEM_AID;
            return;
        }
        LogMgr.log(1, "%s Exception", "811");
        throw new FelicaException(1, 9);
    }

    private void appletSelect(int i, String str, int i2) throws FelicaException {
        byte[] bArr;
        LogMgr.log(6, "%s In systemCode = %d cid = %s timeout = %d", "000", Integer.valueOf(i), str, Integer.valueOf(i2));
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.mSelected && this.mSystemInfo != null && this.mSystemInfo.getSystemCode() == i && (this.mSystemInfo.getSystemCode() != i || this.mSelectedCid == null || this.mSelectedCid.equals(str))) {
                LogMgr.log(6, "%s systemCode-CID-AID is already selected", "001");
                return;
            }
            if ((i == MANAGEMENT_SYSTEM_CODE || i == this.mSystem0InstanceCode) && str != null && str.equals(ALL_FF_CID)) {
                if (i == MANAGEMENT_SYSTEM_CODE) {
                    bArr = MANAGEMENT_SYSTEM_AID;
                } else {
                    bArr = SYSTEM0_AID;
                }
                if (this.mChannel != null && !this.mChipController.mChannelWrapper.isClosed(this.mChannel)) {
                    if (Arrays.equals(bArr, new SelectResponse(this.mChipController.mChannelWrapper.getSelectResponse(this.mChannel)).getAid())) {
                        return;
                    }
                    internalReset();
                    this.mChipController.mChannelWrapper.close(this.mChannel);
                    this.mChannel = null;
                }
                this.mChannel = this.mChipController.mSessionWrapper.openLogicalChannel(this.mSession, bArr);
            } else if (AccessConfig.isGetInstanceStatusCommandSupported()) {
                this.mChannel = selectTargetInstance(i, str, jCurrentTimeMillis, i2);
            } else {
                if (this.mChannel != null && !this.mChipController.mChannelWrapper.isClosed(this.mChannel)) {
                    internalReset();
                    this.mChipController.mChannelWrapper.close(this.mChannel);
                    this.mChannel = null;
                }
                this.mChannel = selectNextToTargetInstance(i, str, jCurrentTimeMillis, i2);
            }
            if (this.mChannel == null || this.mChipController.mChannelWrapper.isClosed(this.mChannel)) {
                LogMgr.log(1, "%s fail to applet SELECT ", "802");
                throw new FelicaException(1, 9);
            }
            internalReset();
            LogMgr.log(6, "%s Channel opened. SystemCode:%x CID:%s", "001", Integer.valueOf(i), str);
        } catch (FelicaException e) {
            LogMgr.log(1, "%s Exception", "804");
            throw e;
        } catch (OfflineException e2) {
            LogMgr.log(1, "%s OfflineException.TYPE_SELECT_FAILED", "803");
            throw convException(e2, 9);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "805");
            throw new FelicaException(1, 9);
        }
    }

    public synchronized void selectInner(int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d timeout = %d ", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkBinderPid();
        checkSystemCodeInner(i);
        checkOpened();
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (!isConnected()) {
                this.mChipController.connect();
            }
            appletSelectInner(i, i2);
            if (i2 - ((int) (System.currentTimeMillis() - jCurrentTimeMillis)) <= 0) {
                throw new FelicaException(3, 7);
            }
        } catch (FelicaException e) {
            if (e.getID() == 3 && e.getType() == 7) {
                throw e;
            }
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 9);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "801");
            throw new FelicaException(1, 9);
        }
    }

    private void checkBinderPid() throws FelicaException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
    }

    private void checkSystemCodeInner(int i) throws IllegalArgumentException {
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(i));
            throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
        }
        if ((i & 255) == 255 || (i & FelicaConst.WILD_CARD_SYSTEM_CODE3) == 65280) {
            if (i == 65535) {
                LogMgr.log(6, "system-0 polling");
            } else {
                LogMgr.log(1, "%s systemCode = %d", "801", Integer.valueOf(i));
                throw new IllegalArgumentException(EXC_INVALID_SYSTEM_CODE);
            }
        }
    }

    private void appletSelectInner(int i, int i2) throws FelicaException {
        String str = (i == MANAGEMENT_SYSTEM_CODE || i == this.mSystem0InstanceCode || i == 65535) ? ALL_FF_CID : ALL_00_CID;
        appletSelectInner(i, str, i2);
        try {
            SelectResponse selectResponse = new SelectResponse(this.mChipController.mChannelWrapper.getSelectResponse(this.mChannel));
            this.mSystemInfo = this.mChipController.getSystemInfo(selectResponse);
            this.mSelectedCid = str;
            this.mSelectedAid = selectResponse.getAid();
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException.TYPE_SELECT_FAILED", "800");
            throw convException(e, 9);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "805");
            throw new FelicaException(1, 9);
        }
    }

    private void appletSelectInner(int i, String str, int i2) throws FelicaException {
        byte[] bArr;
        LogMgr.log(6, "%s In systemCode = %d cid = %s timeout = %d", "000", Integer.valueOf(i), str, Integer.valueOf(i2));
        try {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.mSystemInfo != null && ((i == 65535 || i == 65024) && this.mSystemInfo.getSystemCode() == this.mSystem0InstanceCode)) {
                LogMgr.log(6, "%s system-0 is already selected.", "001");
                return;
            }
            if (this.mSystemInfo != null && this.mSystemInfo.getSystemCode() == i && (this.mSystemInfo.getSystemCode() != i || this.mSelectedCid == null || this.mSelectedCid.equals(str))) {
                LogMgr.log(6, "%s systemCode-CID-AID is already selected", "001");
                return;
            }
            if ((i == MANAGEMENT_SYSTEM_CODE || i == this.mSystem0InstanceCode || i == 65535) && str != null && str.equals(ALL_FF_CID)) {
                if (i == MANAGEMENT_SYSTEM_CODE) {
                    bArr = MANAGEMENT_SYSTEM_AID;
                } else {
                    bArr = SYSTEM0_AID;
                }
                if (this.mChannel != null && !this.mChipController.mChannelWrapper.isClosed(this.mChannel)) {
                    if (Arrays.equals(bArr, new SelectResponse(this.mChipController.mChannelWrapper.getSelectResponse(this.mChannel)).getAid())) {
                        return;
                    }
                    internalReset();
                    this.mChipController.mChannelWrapper.close(this.mChannel);
                    this.mChannel = null;
                }
                this.mChannel = this.mChipController.mSessionWrapper.openLogicalChannel(this.mSession, bArr);
            } else if (AccessConfig.isGetInstanceStatusCommandSupported()) {
                this.mChannel = selectTargetInstance(i, str, jCurrentTimeMillis, i2);
            } else {
                if (this.mChannel != null && !this.mChipController.mChannelWrapper.isClosed(this.mChannel)) {
                    internalReset();
                    this.mChipController.mChannelWrapper.close(this.mChannel);
                    this.mChannel = null;
                }
                this.mChannel = selectNextToTargetInstance(i, str, jCurrentTimeMillis, i2);
            }
            if (this.mChannel == null || this.mChipController.mChannelWrapper.isClosed(this.mChannel)) {
                LogMgr.log(1, "%s fail to applet SELECT ", "802");
                throw new FelicaException(1, 9);
            }
            internalReset();
            LogMgr.log(6, "%s Channel opened. SystemCode:%x CID:%s", "001", Integer.valueOf(i), str);
        } catch (FelicaException e) {
            LogMgr.log(1, "%s Exception", "804");
            throw e;
        } catch (OfflineException e2) {
            LogMgr.log(1, "%s OfflineException.TYPE_SELECT_FAILED", "803");
            throw convException(e2, 9);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "805");
            throw new FelicaException(1, 9);
        }
    }

    public synchronized int getSystemCode() throws FelicaException {
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return this.mSystemInfo.getSystemCode();
    }

    public synchronized byte[] getIdm() throws FelicaException {
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return this.mSystemInfo.getIdm();
    }

    public synchronized byte[] getIcCode() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return new byte[]{this.mSystemInfo.getPmm()[0], this.mSystemInfo.getPmm()[1]};
    }

    public synchronized int getKeyVersion(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        int i4;
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
                if (this.mPersonalized || !this.mCrsActivated) {
                    LogMgr.log(1, "%s Not personalized", "806");
                    throw new FelicaException(1, 10);
                }
                try {
                    i4 = this.mChipController.requestService(this.mChannel, this.mNodeCodeSize, this.mSystemInfo.getIdm(), new int[]{i}, getTimeout(), getRetryCount())[0];
                    if (i4 == 65535) {
                        LogMgr.log(1, "%s FelicaException.TYPE_SERVICE_NOT_FOUND", "800");
                        throw new FelicaException(4, 11);
                    }
                } catch (FelicaException e) {
                    LogMgr.log(1, "%s FelicaException", "800");
                    throw e;
                } catch (OfflineException e2) {
                    LogMgr.log(1, "%s OfflineException.TYPE_GET_KEY_VERSION_FAILED", "801");
                    throw convException(e2, 10);
                } catch (Exception unused) {
                    LogMgr.log(1, "%s Exception", "804");
                    throw new FelicaException(1, 10);
                }
            } catch (NumberFormatException unused2) {
                LogMgr.log(1, "%s Exception", "805");
                throw new FelicaException(1, 10);
            }
        } else {
            if (this.mPersonalized) {
            }
            LogMgr.log(1, "%s Not personalized", "806");
            throw new FelicaException(1, 10);
        }
        return i4;
    }

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
        if (!this.mPersonalized || !this.mCrsActivated) {
            LogMgr.log(1, "%s Not personalized", "806");
            throw new FelicaException(1, 64);
        }
        try {
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException.TYPE_GET_KEY_VERSION_V2_FAILED", "801");
            throw convException(e, 28, 64, new int[0]);
        } catch (Exception unused2) {
            LogMgr.log(1, "%s Exception", "804");
            throw new FelicaException(1, 64);
        }
        return this.mChipController.requestServiceV2(this.mChannel, this.mSystemInfo.getIdm(), iArr, getTimeout(), getRetryCount());
    }

    public synchronized byte[] getContainerIssueInformation(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        if (!this.mPersonalized || !this.mCrsActivated) {
            return CONTAINER_ISSUE_ALL0;
        }
        try {
            return this.mChipController.getContainerIssueInfo(this.mChannel, this.mSystemInfo.getIdm(), getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException.TYPE_GET_CONTAINER_ISSUE_INFORMATION_FAILED", "800");
            throw convException(e, 29);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "803");
            throw new FelicaException(1, 29);
        }
    }

    public synchronized void setNodeCodeSize(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCodeSize = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkActivated();
        if (i != 2 && i != 4) {
            LogMgr.log(1, "%s invalid nodeCodeSize", "800");
            throw new IllegalArgumentException(EXC_INVALID_NODECODESIZE);
        }
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        if (!this.mPersonalized || !this.mCrsActivated) {
            LogMgr.log(1, "%s Not personalized", "806");
            throw new FelicaException(1, 28);
        }
        try {
            this.mChipController.setParameter(this.mChannel, this.mSystemInfo.getIdm(), i, getTimeout(), getRetryCount());
            this.mNodeCodeSize = i;
        } catch (OfflineException e) {
            LogMgr.log(1, "%s OfflineException.TYPE_SET_NODECODESIZE_FAILED", "801");
            throw convException(e, 7, 28, new int[0]);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "805");
            throw new FelicaException(1, 28);
        }
    }

    public synchronized Data[] read(BlockList blockList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockList = %s timeout = %d retryCount = %d", "000", blockList, Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        if (blockList == null || blockList.size() == 0) {
            LogMgr.log(1, "%s invalid blockList", "800");
            throw new IllegalArgumentException(EXC_INVALID_BLOCK_LIST);
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
            if (!this.mPersonalized || !this.mCrsActivated) {
                LogMgr.log(1, "%s Not personalized", "817");
                throw new FelicaException(1, 14);
            }
            try {
            } catch (OfflineException e) {
                if (e.getType() == 2) {
                    throw new IllegalArgumentException();
                }
                int[] iArr2 = {83, 13, 166, 11, 168, 12};
                LogMgr.log(1, "%s OfflineException", "801");
                throw convException(e, 5, 14, iArr2);
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "809");
                throw new FelicaException(1, 14);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "810");
            throw new FelicaException(1, 14);
        }
        return this.mChipController.readWithoutEncryption(this.mChannel, this.mNodeCodeSize, this.mSystemInfo.getIdm(), blockList, getTimeout(), getRetryCount());
    }

    public synchronized void write(BlockDataList blockDataList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockDataList = %s timeout = %d retryCount = %d", "000", blockDataList, Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        if (blockDataList == null || blockDataList.size() == 0) {
            LogMgr.log(1, "%s invalid blockDataList", "800");
            throw new IllegalArgumentException(EXC_INVALID_BLOCK_DATA_LIST);
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
            if (!this.mPersonalized || !this.mCrsActivated) {
                LogMgr.log(1, "%s Not personalized", "817");
                throw new FelicaException(1, 20);
            }
            try {
                this.mChipController.writeWithoutEncryption(this.mChannel, this.mNodeCodeSize, this.mSystemInfo.getIdm(), blockDataList, getTimeout(), getRetryCount());
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

    /* JADX WARN: Removed duplicated region for block: B:12:0x001d A[Catch: all -> 0x003b, Exception -> 0x003d, OfflineException -> 0x004a, TryCatch #3 {OfflineException -> 0x004a, Exception -> 0x003d, blocks: (B:6:0x000c, B:9:0x0014, B:10:0x0019, B:12:0x001d, B:13:0x0024, B:15:0x0031, B:16:0x0035), top: B:29:0x000c, outer: #2 }] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0031 A[Catch: all -> 0x003b, Exception -> 0x003d, OfflineException -> 0x004a, TryCatch #3 {OfflineException -> 0x004a, Exception -> 0x003d, blocks: (B:6:0x000c, B:9:0x0014, B:10:0x0019, B:12:0x001d, B:13:0x0024, B:15:0x0031, B:16:0x0035), top: B:29:0x000c, outer: #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized void doReset(boolean r4, boolean r5, boolean r6) throws com.felicanetworks.mfc.FelicaException {
        /*
            r3 = this;
            monitor-enter(r3)
            if (r4 == 0) goto L9
            r3.checkOpened()     // Catch: java.lang.Throwable -> L3b
            r3.checkNotOnline()     // Catch: java.lang.Throwable -> L3b
        L9:
            r4 = 44
            r0 = 1
            boolean r1 = r3.isConnected()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            if (r1 != 0) goto L19
            if (r5 == 0) goto L19
            com.felicanetworks.mfc.felica.offlineimpl.FelicaGpController r1 = r3.mChipController     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            r1.connect()     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
        L19:
            boolean r1 = r3.mPersonalized     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            if (r1 == 0) goto L24
            com.felicanetworks.mfc.felica.offlineimpl.FelicaGpController r1 = r3.mChipController     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            android.se.omapi.Channel r2 = r3.mChannel     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            r1.reset(r2, r5, r0, r6)     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
        L24:
            r5 = 2
            r3.mNodeCodeSize = r5     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            r5 = 0
            r3.mSelected = r5     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            r5 = 0
            r3.mSystemInfo = r5     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            java.lang.String r6 = r3.mSelectedCid     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            if (r6 == 0) goto L35
            java.lang.String r6 = r3.mSelectedCid     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            r3.mReconnectCid = r6     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
        L35:
            r3.mSelectedCid = r5     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            r3.mSelectedAid = r5     // Catch: java.lang.Throwable -> L3b java.lang.Exception -> L3d com.felicanetworks.mfc.felica.offlineimpl.OfflineException -> L4a
            monitor-exit(r3)
            return
        L3b:
            r4 = move-exception
            goto L57
        L3d:
            java.lang.String r5 = "%s Exception"
            java.lang.String r6 = "802"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r5, r6)     // Catch: java.lang.Throwable -> L3b
            com.felicanetworks.mfc.FelicaException r5 = new com.felicanetworks.mfc.FelicaException     // Catch: java.lang.Throwable -> L3b
            r5.<init>(r0, r4)     // Catch: java.lang.Throwable -> L3b
            throw r5     // Catch: java.lang.Throwable -> L3b
        L4a:
            r5 = move-exception
            java.lang.String r6 = "%s OfflineException"
            java.lang.String r1 = "801"
            com.felicanetworks.mfc.util.LogMgr.log(r0, r6, r1)     // Catch: java.lang.Throwable -> L3b
            com.felicanetworks.mfc.FelicaException r4 = r3.convException(r5, r4)     // Catch: java.lang.Throwable -> L3b
            throw r4     // Catch: java.lang.Throwable -> L3b
        L57:
            monitor-exit(r3)
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.FelicaGp.doReset(boolean, boolean, boolean):void");
    }

    private synchronized void internalReset() {
        internalReset(this.mChannel);
    }

    private synchronized void internalReset(Channel channel) {
        LogMgr.log(6, "%s", "000");
        try {
            if (this.mPersonalized) {
                this.mChipController.reset(channel);
            } else {
                LogMgr.log(6, "%s %s", "001", "skip internal reset");
            }
        } catch (OfflineException unused) {
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

    public synchronized int[] getSystemCodeList(int i, int i2) throws FelicaException {
        int[] iArrRequestSystemCode;
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
            checkAccessRight(4);
            if (!this.mPersonalized || !this.mCrsActivated) {
                LogMgr.log(1, "%s Not personalized", "803");
                throw new FelicaException(1, 45);
            }
            try {
                iArrRequestSystemCode = this.mChipController.requestSystemCode(this.mChannel, this.mSystemInfo.getIdm(), getTimeout(), getRetryCount());
                if (this.mSelectedCid != null && !this.mSelectedCid.equals(ALL_00_CID) && !this.mSelectedCid.equals(ALL_FF_CID)) {
                    LogMgr.log(4, "%s", "001");
                    boolean z = false;
                    for (int i3 : iArrRequestSystemCode) {
                        if (i3 == this.mSystemInfo.getSystemCode()) {
                            z = true;
                        }
                    }
                    if (!z) {
                        LogMgr.log(4, "%s", "002");
                        int[] iArr = new int[iArrRequestSystemCode.length + 1];
                        System.arraycopy(iArrRequestSystemCode, 0, iArr, 0, iArrRequestSystemCode.length);
                        iArr[iArr.length - 1] = this.mSystemInfo.getSystemCode();
                        iArrRequestSystemCode = iArr;
                    }
                }
            } catch (OfflineException e) {
                LogMgr.log(1, "%s OfflineException", "800");
                throw convException(e, 45);
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 45);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 45);
        }
        return iArrRequestSystemCode;
    }

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
            if (!this.mPersonalized || !this.mCrsActivated) {
                LogMgr.log(1, "%s Not personalized", "803");
                throw new FelicaException(1, 34);
            }
            try {
            } catch (OfflineException e) {
                int[] iArr = {166, 11};
                LogMgr.log(1, "%s OfflineException", "800");
                throw convException(e, 9, 34, iArr);
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 34);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 34);
        }
        return this.mChipController.requestCodeList(this.mChannel, this.mNodeCodeSize, this.mSystemInfo.getIdm(), i, getTimeout(), getRetryCount());
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
            if (!this.mPersonalized || !this.mCrsActivated) {
                LogMgr.log(1, "%s Not personalized", "803");
                throw new FelicaException(1, 35);
            }
            try {
            } catch (OfflineException e) {
                int[] iArr = {166, 11, 209, 33};
                LogMgr.log(1, "%s OfflineException", "800");
                throw convException(e, 10, 35, iArr);
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 35);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 35);
        }
        return this.mChipController.requestMaskedCodeList(this.mChannel, this.mSystemInfo.getIdm(), i, getTimeout(), getRetryCount());
    }

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
            if (!this.mPersonalized || !this.mCrsActivated) {
                LogMgr.log(1, "%s Not personalized", "804");
                throw new FelicaException(1, 43);
            }
            try {
                try {
                } catch (OfflineException e) {
                    LogMgr.log(1, "%s OfflineException", "801");
                    throw convException(e, 13, 43, new int[0]);
                }
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "802");
                throw new FelicaException(1, 43);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "803");
            throw new FelicaException(1, 43);
        }
        return this.mChipController.requestBlockInformationEx(this.mChannel, this.mNodeCodeSize, this.mSystemInfo.getIdm(), iArr, getTimeout(), getRetryCount());
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
            throw new FelicaException(1, 36);
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "801");
            throw new FelicaException(1, 36);
        }
    }

    public synchronized byte[] getContainerId(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkActivated();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        try {
            checkAccessRight(4);
            if (!this.mPersonalized || !this.mCrsActivated) {
                return IDM_NOT_PERSONALIZED;
            }
            try {
                try {
                    return this.mChipController.getContainerId(this.mChannel, getTimeout(), getRetryCount());
                } catch (OfflineException e) {
                    LogMgr.log(1, "%s OfflineException", "800");
                    throw convException(e, 46);
                }
            } catch (Exception unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 46);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s Exception", "802");
            throw new FelicaException(1, 46);
        }
    }

    protected FelicaException convException(OfflineException offlineException, int i) {
        return convException(offlineException, 1, i, null);
    }

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

    protected FelicaException convException(AccessControllerException accessControllerException, int i) {
        int type = accessControllerException.getType();
        if (type == 0) {
            return new FelicaException(12, 32);
        }
        if (type == 1) {
            return new FelicaException(12, 38);
        }
        if (type == 2) {
            return new FelicaException(12, 50);
        }
        return new FelicaException(12, i);
    }

    public synchronized void setContext(Context context) {
        if (context != null) {
            this.mContext = context;
            this.mUserAgent = AccessConfig.getUserAgent(context);
        }
    }

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

    public synchronized void setSelectTimeout(int i) throws FelicaException {
        checkActivated();
        if (i < 0) {
            this.mSelectTimeout = 0;
        } else if (i > 60000) {
            this.mSelectTimeout = 60000;
        } else {
            this.mSelectTimeout = i;
        }
    }

    public synchronized int getSelectTimeout() throws FelicaException {
        checkActivated();
        return this.mSelectTimeout;
    }

    public void cancelOffline() throws FelicaException {
        checkActivated();
        try {
            checkOpenedNosync();
            checkNotOnline();
        } catch (Exception unused) {
        }
        cancelOfflineGpDevice();
    }

    private void cancelOfflineGpDevice() {
        FelicaGpController felicaGpController = this.mChipController;
        if (felicaGpController == null) {
            return;
        }
        felicaGpController.cancelOffline();
        synchronized (this) {
            this.mChipController.finishCancel();
        }
    }

    public synchronized byte[] executeFelicaCommand(byte[] bArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        checkActivated();
        if (bArr == null || bArr.length <= 0 || bArr.length > 254) {
            LogMgr.log(1, "invalid Command");
            throw new IllegalArgumentException(EXC_INVALID_COMMAND);
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        try {
            checkAccessRight(5);
            if (!this.mPersonalized || !this.mCrsActivated) {
                LogMgr.log(1, "%s Not personalized", "800");
                throw new FelicaException(1, 63);
            }
            try {
            } catch (OfflineException e) {
                LogMgr.log(1, "%s", "OfflineException");
                throw convException(e, 63);
            } catch (Exception unused) {
                LogMgr.log(1, "%s", "Exception");
                throw new FelicaException(1, 63);
            }
        } catch (NumberFormatException unused2) {
            LogMgr.log(1, "%s", "NumberFormatException");
            throw new FelicaException(1, 63);
        }
        return this.mChipController.executeFelicaCommand(this.mChannel, bArr, getTimeout(), getRetryCount());
    }

    public synchronized void connectInner(int i) throws FelicaException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        checkOpened();
        this.mChipController.connect();
    }

    public synchronized byte[] executeFelicaCommandInner(byte[] bArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        if (bArr == null || bArr.length <= 0 || bArr.length > 255) {
            LogMgr.log(1, "invalid Command");
            throw new FelicaException(1, 63);
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        try {
            try {
            } catch (OfflineException e) {
                LogMgr.log(1, "%s", "OfflineException");
                throw convException(e, 63);
            }
        } catch (Exception unused) {
            LogMgr.log(1, "%s", "Exception");
            throw new FelicaException(1, 63);
        }
        return this.mChipController.executeFelicaCommandInner(this.mChannel, bArr, getTimeout(), getRetryCount());
    }

    public synchronized void resetInner(boolean z, boolean z2, boolean z3) throws FelicaException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        checkOpened();
        if (!isConnected() && z) {
            this.mChipController.connect();
        }
        try {
            if (this.mPersonalized) {
                this.mChipController.reset(this.mChannel, z, z2, z3);
            }
            this.mNodeCodeSize = 2;
            this.mSelected = false;
            this.mSystemInfo = null;
            this.mSelectedCid = null;
            this.mSelectedAid = null;
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
        cancelOfflineGpDevice();
    }

    public synchronized void setTcapClient(FSCAdapter fSCAdapter) {
        this.mFscAdapter = fSCAdapter;
        this.mFscStarting = false;
        this.mFelicaCloseInFscStarting = false;
    }

    private void checkActivated() throws FelicaException {
        checkPidUid();
        checkActivateWorker();
    }

    public void checkPidUid() throws FelicaException {
        checkPidUid(Binder.getCallingPid(), Binder.getCallingUid());
    }

    void checkPidUid(int i, int i2) throws FelicaException {
        FelicaAppInfo felicaAppInfo = this.mActivatedApp;
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

    public synchronized void checkOpened() throws FelicaException {
        checkOpenedNosync();
    }

    private void checkOpenedNosync() throws FelicaException {
        if (!this.mOpened) {
            throw new FelicaException(2, 1);
        }
    }

    public synchronized void checkClosedinStarting() throws FelicaException {
        if (this.mFelicaCloseInFscStarting) {
            throw new FelicaException(2, 1);
        }
    }

    public void checkSelected() throws FelicaException {
        if (!this.mSelected) {
            throw new FelicaException(2, 3);
        }
    }

    public synchronized void checkStatus() throws FelicaException {
        checkActivated();
        checkOpened();
        checkNotOnline();
        checkSelected();
    }

    public synchronized boolean isPersonalized() {
        return this.mPersonalized;
    }

    public synchronized boolean isCrsActivated() {
        return this.mCrsActivated;
    }

    public synchronized void reconnect(boolean z, int i) throws FelicaException, IllegalArgumentException {
        if (z) {
            try {
                checkSelectStatus(i);
                doSelect(i, this.mReconnectCid);
                this.mReconnectCid = null;
            } catch (FelicaException e) {
                openPreviousChannel();
                throw e;
            }
        } else {
            checkActivated();
            checkOpened();
            checkNotOnline();
            this.mChipController.connect();
        }
    }

    private void checkActivateWorker() throws FelicaException {
        if (this.mActivateWorker == null) {
            return;
        }
        LogMgr.log(1, "%s activateWorker != null", "800");
        throw new FelicaException(2, 5);
    }

    private synchronized void updateLifeCycleState() throws FelicaException {
        LogMgr.log(6, "%s", "000");
        this.mPersonalized = false;
        this.mCrsActivated = false;
        if (AccessConfig.isGetInstanceStatusCommandSupported()) {
            updateLifeCycleStateViaInstanceStatus();
        } else {
            updateLifeCycleStateViaSelectResponse();
        }
    }

    private synchronized void updateLifeCycleStateViaSelectResponse() throws FelicaException {
        Channel channelOpenLogicalChannel = null;
        try {
            try {
                channelOpenLogicalChannel = this.mChipController.mSessionWrapper.openLogicalChannel(this.mSession, SYSTEM0_AID);
                SelectResponse selectResponse = new SelectResponse(this.mChipController.mChannelWrapper.getSelectResponse(channelOpenLogicalChannel));
                this.mPersonalized = selectResponse.isPersonalized();
                this.mCrsActivated = selectResponse.isActivated();
                this.mSystem0InstanceCode = selectResponse.getSystemCode();
                try {
                    try {
                        if (this.mChannel == null) {
                            this.mChannel = this.mChipController.mSessionWrapper.openLogicalChannel(this.mSession, MANAGEMENT_SYSTEM_AID);
                        }
                        SelectResponse selectResponse2 = new SelectResponse(this.mChipController.mChannelWrapper.getSelectResponse(this.mChannel));
                        this.mPersonalized &= selectResponse2.isPersonalized();
                        this.mCrsActivated &= selectResponse2.isActivated();
                        internalReset();
                        LogMgr.log(6, "%s mPersonalized = " + this.mPersonalized + " : mCrsActivated = " + this.mCrsActivated, "999");
                    } catch (OfflineException e) {
                        LogMgr.log(1, "%s OfflineException", "804");
                        throw convException(e, 8);
                    } catch (Exception e2) {
                        LogMgr.log(1, "%s Unexpected Exception occurred: %s %s ", "805", e2.getClass().getSimpleName(), e2.getMessage());
                        throw new FelicaException(1, 8);
                    }
                } catch (Throwable th) {
                    internalReset();
                    throw th;
                }
            } catch (OfflineException e3) {
                LogMgr.log(1, "%s OfflineException", "801");
                throw convException(e3, 8);
            } catch (Exception e4) {
                LogMgr.log(1, "%s Unexpected Exception occurred: %s %s ", "802", e4.getClass().getSimpleName(), e4.getMessage());
                throw new FelicaException(1, 8);
            }
        } finally {
            try {
                this.mChipController.mChannelWrapper.close(channelOpenLogicalChannel);
            } catch (OfflineException unused) {
                LogMgr.log(1, "%s OfflineException", "803");
            }
        }
    }

    private synchronized void updateLifeCycleStateViaInstanceStatus() throws FelicaException {
        try {
            try {
                try {
                    if (this.mChannel == null) {
                        this.mChannel = this.mChipController.mSessionWrapper.openLogicalChannel(this.mSession, MANAGEMENT_SYSTEM_AID);
                    }
                    GetInstanceStatusResponse getInstanceStatusResponse = new GetInstanceStatusResponse(this.mChipController.getGetMgmtSysSys0InstanceStatus(this.mChannel));
                    if (getInstanceStatusResponse.isStatusSuccess()) {
                        List<InstanceStatus> instanceStatusList = getInstanceStatusResponse.getInstanceStatusList();
                        if (hasMgmtSysSys0InstanceStatus(instanceStatusList)) {
                            this.mPersonalized = instanceStatusList.get(0).isPersonalized() & instanceStatusList.get(1).isPersonalized();
                            this.mCrsActivated = instanceStatusList.get(0).isActivated() & instanceStatusList.get(1).isActivated();
                            this.mSystem0InstanceCode = instanceStatusList.get(1).getSystemCode();
                            internalReset();
                            LogMgr.log(6, "%s mPersonalized = " + this.mPersonalized + " : mCrsActivated = " + this.mCrsActivated, "999");
                        } else {
                            LogMgr.log(1, "%s  InstanceStatus Response is Invalid.", "800");
                            throw new FelicaException(1, 8);
                        }
                    } else {
                        LogMgr.log(1, "%s  failed to send getInstanceStatus.", "801");
                        throw new FelicaException(1, 8);
                    }
                } catch (OfflineException e) {
                    LogMgr.log(1, "%s OfflineException", "802");
                    throw convException(e, 8);
                }
            } catch (Exception e2) {
                LogMgr.log(1, "%s Unexpected Exception occurred: %s %s ", "803", e2.getClass().getSimpleName(), e2.getMessage());
                throw new FelicaException(1, 8);
            }
        } catch (Throwable th) {
            internalReset();
            throw th;
        }
    }

    private boolean hasMgmtSysSys0InstanceStatus(List<InstanceStatus> list) {
        return list != null && list.size() == 2 && Arrays.equals(list.get(0).getAid(), MANAGEMENT_SYSTEM_AID) && Arrays.equals(list.get(1).getAid(), SYSTEM0_AID);
    }

    private synchronized void checkTypeFCurrentProtocolData() throws FelicaException {
        LogMgr.log(6, "%s", "000");
        try {
            try {
                this.mCrsActivated = isValidProtocolData(this.mChipController.getTypeFCurrentProtocolData(this.mChannel));
            } catch (Exception e) {
                LogMgr.log(1, "%s Unexpected Exception occurred: %s %s ", "802", e.getClass().getSimpleName(), e.getMessage());
                throw new FelicaException(1, 8);
            }
        } catch (OfflineException e2) {
            LogMgr.log(1, "%s OfflineException", "801");
            throw convException(e2, 8);
        }
    }

    private boolean isValidProtocolData(int[] iArr) {
        LogMgr.log(6, "%s", "000");
        boolean z = iArr != null && iArr.length >= 2 && iArr[0] == this.mSystem0InstanceCode && iArr[1] == MANAGEMENT_SYSTEM_CODE;
        LogMgr.log(6, "%s isValid = " + z, "999");
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x0127 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized android.se.omapi.Channel selectNextToTargetInstance(int r10, java.lang.String r11, long r12, long r14) throws com.felicanetworks.mfc.FelicaException {
        /*
            Method dump skipped, instruction units count: 314
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.FelicaGp.selectNextToTargetInstance(int, java.lang.String, long, long):android.se.omapi.Channel");
    }

    /* JADX WARN: Code restructure failed: missing block: B:38:0x00ca, code lost:
    
        r7 = r19 - ((long) ((int) (java.lang.System.currentTimeMillis() - r17)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00d4, code lost:
    
        if (r7 <= 0) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00d6, code lost:
    
        java.lang.Thread.sleep(r7);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private synchronized android.se.omapi.Channel selectTargetInstance(int r15, java.lang.String r16, long r17, long r19) throws com.felicanetworks.mfc.FelicaException {
        /*
            Method dump skipped, instruction units count: 326
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.FelicaGp.selectTargetInstance(int, java.lang.String, long, long):android.se.omapi.Channel");
    }

    private String getCidString(byte[] bArr) {
        LogMgr.log(6, "%s", "000");
        LogMgr.logArray(6, bArr);
        if (bArr == null || bArr.length != 63) {
            return null;
        }
        boolean z = true;
        boolean z2 = true;
        for (byte b : bArr) {
            if (b != 0) {
                z = false;
            }
            if (b != -1) {
                z2 = false;
            }
        }
        return z ? ALL_00_CID : z2 ? ALL_FF_CID : new String(bArr, Charset.forName("US-ASCII"));
    }

    private synchronized void selectTransactionMgmtApplet() {
        LogMgr.log(6, "%s", "000");
        try {
            if (this.mOpened && this.mTransactionChannel == null && this.mPersonalized && this.mCrsActivated) {
                LogMgr.log(6, "%s do SELECT", "001");
                this.mTransactionChannel = this.mChipController.mSessionWrapper.openLogicalChannel(this.mSession, TRANSACTION_MANAGEMENT_AID);
            }
        } catch (OfflineException unused) {
            LogMgr.log(2, "700 failed to SELECT Transaction Management Applet ");
        }
    }

    private synchronized void deselectTransactionMgmtApplet() {
        LogMgr.log(6, "%s", "000");
        if (this.mTransactionChannel != null) {
            LogMgr.log(6, "%s", "001");
            try {
                this.mChipController.mChannelWrapper.close(this.mTransactionChannel);
            } catch (OfflineException unused) {
                LogMgr.log(2, "%s OfflineException", "701");
            }
            this.mTransactionChannel = null;
            LogMgr.log(6, "%s", "999");
        } else {
            this.mTransactionChannel = null;
            LogMgr.log(6, "%s", "999");
        }
    }

    private synchronized void notifyWiredTransaction() throws FelicaException {
        byte[] endTransaction;
        LogMgr.log(6, "%s", "000");
        checkActivated();
        if (this.mOpened) {
            this.mChipController.setProtectProcessKill(true);
            if (this.mTransactionChannel != null) {
                LogMgr.log(6, "%s", "001");
                byte[] aid = null;
                try {
                    endTransaction = this.mChipController.setEndTransaction(this.mTransactionChannel);
                    try {
                        LogMgr.logArray(6, endTransaction);
                    } catch (OfflineException unused) {
                        LogMgr.log(2, "%s OfflineException", "700");
                    }
                } catch (OfflineException unused2) {
                    endTransaction = null;
                }
                if (this.mChannel != null) {
                    LogMgr.log(6, "%s", "002");
                    try {
                        try {
                            aid = new SelectResponse(this.mChipController.mChannelWrapper.getSelectResponse(this.mChannel)).getAid();
                        } catch (OfflineException unused3) {
                            LogMgr.log(2, "%s OfflineException", "702");
                        }
                    } catch (IllegalArgumentException unused4) {
                        LogMgr.log(2, "%s IllegalArgumentException", "703");
                    }
                }
                if (endTransaction != null && aid != null) {
                    try {
                        LogMgr.log(6, "%s", "003");
                        long jCurrentTimeMillis = System.currentTimeMillis();
                        String string = UUID.randomUUID().toString();
                        storeTisExtraToCache(string);
                        Intent intent = new Intent(ACTION_TRANSACTION_FINISHED);
                        intent.addFlags(32);
                        intent.addFlags(268435456);
                        intent.putExtra(EXTRA_SECURE_ELEMENT_NAME, sEseReaderName);
                        intent.putExtra(EXTRA_AID, aid);
                        intent.putExtra(EXTRA_DATA, endTransaction);
                        intent.putExtra(EXTRA_TIME, jCurrentTimeMillis);
                        intent.putExtra("com.felicanetworks.mfc.EXTRA_UUID", string);
                        intent.setComponent(new ComponentName(MENU_PACKAGE_NAME, RECEIVER_CLASS_NAME));
                        this.mContext.sendBroadcast(intent);
                    } catch (Exception unused5) {
                        LogMgr.log(2, "%s", "704");
                    }
                }
                this.mChipController.setProtectProcessKill(true);
                LogMgr.log(6, "%s", "999");
            }
        }
    }

    private void storeTisExtraToCache(String str) throws Throwable {
        LinkedList linkedList;
        LogMgr.log(6, "%s", "000");
        if (str == null) {
            return;
        }
        try {
            linkedList = (LinkedList) readObjectFromCache(TIS_CACHE_FILE);
        } catch (Exception unused) {
            LogMgr.log(2, "%s", "700");
            linkedList = null;
        }
        if (linkedList == null) {
            linkedList = new LinkedList();
        }
        if (linkedList.size() == 35) {
            LogMgr.log(6, "%s", "001");
            linkedList.poll();
        } else if (linkedList.size() > 35) {
            LogMgr.log(2, "%s", "701");
            linkedList.clear();
        }
        LogMgr.log(6, "%s", "002");
        linkedList.offer(str);
        writeObjectToCache(linkedList, TIS_CACHE_FILE);
        LogMgr.log(6, "%s", "999");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:16:0x0040
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1182)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    private void writeObjectToCache(java.lang.Object r8, java.lang.String r9) throws java.lang.Throwable {
        /*
            r7 = this;
            java.lang.String r0 = "702"
            java.lang.String r1 = "%s"
            r2 = 6
            java.lang.String r3 = "000"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r1, r3)
            r2 = 2
            r3 = 0
            java.io.ObjectOutputStream r4 = new java.io.ObjectOutputStream     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            java.io.FileOutputStream r5 = new java.io.FileOutputStream     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            android.content.Context r7 = r7.mContext     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            java.io.File r7 = r7.getCacheDir()     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            r6.<init>(r7, r9)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L30 java.io.IOException -> L32
            r4.writeObject(r8)     // Catch: java.lang.Throwable -> L2b java.io.IOException -> L2e
            r4.flush()     // Catch: java.io.IOException -> L40
            r4.close()     // Catch: java.io.IOException -> L40
            goto L43
        L2b:
            r7 = move-exception
            r3 = r4
            goto L44
        L2e:
            r3 = r4
            goto L32
        L30:
            r7 = move-exception
            goto L44
        L32:
            java.lang.String r7 = "700"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r1, r7)     // Catch: java.lang.Throwable -> L30
            if (r3 == 0) goto L43
            r3.flush()     // Catch: java.io.IOException -> L40
            r3.close()     // Catch: java.io.IOException -> L40
            goto L43
        L40:
            com.felicanetworks.mfc.util.LogMgr.log(r2, r1, r0)
        L43:
            return
        L44:
            if (r3 == 0) goto L50
            r3.flush()     // Catch: java.io.IOException -> L4d
            r3.close()     // Catch: java.io.IOException -> L4d
            goto L50
        L4d:
            com.felicanetworks.mfc.util.LogMgr.log(r2, r1, r0)
        L50:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.FelicaGp.writeObjectToCache(java.lang.Object, java.lang.String):void");
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x0046 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private java.lang.Object readObjectFromCache(java.lang.String r8) throws java.lang.Throwable {
        /*
            r7 = this;
            java.lang.String r0 = "702"
            java.lang.String r1 = "%s"
            r2 = 6
            java.lang.String r3 = "000"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r1, r3)
            r2 = 0
            r3 = 2
            java.io.ObjectInputStream r4 = new java.io.ObjectInputStream     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            java.io.File r6 = new java.io.File     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            android.content.Context r7 = r7.mContext     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            java.io.File r7 = r7.getCacheDir()     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            r6.<init>(r7, r8)     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            r5.<init>(r6)     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            r4.<init>(r5)     // Catch: java.lang.Throwable -> L2d java.lang.ClassNotFoundException -> L2f java.io.IOException -> L38
            java.lang.Object r2 = r4.readObject()     // Catch: java.lang.ClassNotFoundException -> L30 java.io.IOException -> L39 java.lang.Throwable -> L42
        L25:
            r4.close()     // Catch: java.io.IOException -> L29
            goto L41
        L29:
            com.felicanetworks.mfc.util.LogMgr.log(r3, r1, r0)
            goto L41
        L2d:
            r7 = move-exception
            goto L44
        L2f:
            r4 = r2
        L30:
            java.lang.String r7 = "701"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r1, r7)     // Catch: java.lang.Throwable -> L42
            if (r4 == 0) goto L41
            goto L25
        L38:
            r4 = r2
        L39:
            java.lang.String r7 = "700"
            com.felicanetworks.mfc.util.LogMgr.log(r3, r1, r7)     // Catch: java.lang.Throwable -> L42
            if (r4 == 0) goto L41
            goto L25
        L41:
            return r2
        L42:
            r7 = move-exception
            r2 = r4
        L44:
            if (r2 == 0) goto L4d
            r2.close()     // Catch: java.io.IOException -> L4a
            goto L4d
        L4a:
            com.felicanetworks.mfc.util.LogMgr.log(r3, r1, r0)
        L4d:
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.felica.FelicaGp.readObjectFromCache(java.lang.String):java.lang.Object");
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
            synchronized (FelicaGp.this) {
                FelicaGp.this.mDeathRecipient = null;
            }
            FelicaGp.this.mMfcListener.mfcCancel();
        }
    }

    public boolean isConnected() {
        return this.mChipController.isConnected();
    }
}
