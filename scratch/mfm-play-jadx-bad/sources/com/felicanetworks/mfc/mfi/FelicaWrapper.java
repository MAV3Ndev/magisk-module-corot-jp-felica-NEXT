package com.felicanetworks.mfc.mfi;

import android.app.ForegroundServiceStartNotAllowedException;
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
import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.FelicaEventListener;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PushAppNotificationListener;
import com.felicanetworks.mfc.PushSegment;
import com.felicanetworks.mfc.mfi.ForegroundServiceSetupProvider;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessConfig;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManagerImpl;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessController;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControllerException;
import com.felicanetworks.mfc.mfi.util.ProfileManager;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes3.dex */
class FelicaWrapper {
    private static final String MENU_APP_PACKAGE_NAME = "com.felicanetworks.mfm.main";
    private static FelicaWrapper sInstance;
    private ActivateThread mActivateWorker;
    private FelicaAppInfo mActivatedApp;
    private Context mContext;
    private LocalDeathRecipient mDeathRecipient;
    private Felica mFelica;
    private boolean mFelicaCloseInFscStarting;
    private boolean mFscStarting;
    private MfcOnline mMfcOnline;
    private MfiListener mMfiListener;
    private MfiOnline mMfiOnline;
    private int mNodeCodeSize;
    private boolean mOpened;
    private boolean mSelected;
    private int mSystemCode;
    private int mTarget;
    private AccessController mAccessController = null;
    private IFelicaEventListener mIFelicaEventListener = null;
    private LocalFelicaEventListener mFelicaEventListener = new LocalFelicaEventListener();

    private FelicaWrapper() {
        LogMgr.log(5, "%s", "000");
        this.mTarget = 0;
        this.mNodeCodeSize = 2;
        LogMgr.log(5, "%s", "999");
    }

    public static synchronized FelicaWrapper getInstance() {
        LogMgr.log(5, "%s", "000");
        if (sInstance == null) {
            sInstance = new FelicaWrapper();
        }
        LogMgr.log(5, "%s", "999");
        return sInstance;
    }

    public static String getMFCVersion(Context context) throws FelicaException {
        LogMgr.log(5, "%s", "000");
        LogMgr.log(5, "%s", "999");
        return Felica.getMFCVersion(context);
    }

    synchronized void setMfcOnline(MfcOnline mfcOnline) {
        this.mMfcOnline = mfcOnline;
        this.mFscStarting = false;
        this.mFelicaCloseInFscStarting = false;
    }

    synchronized void setMfiOnline(MfiOnline mfiOnline) {
        this.mMfiOnline = mfiOnline;
    }

    MfiOnline getMfiOnline() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return this.mMfiOnline;
    }

    public Felica getFelica() {
        return this.mFelica;
    }

    public void setFelica(Felica felica) {
        LogMgr.log(5, "%s", "000");
        this.mFelica = felica;
        LogMgr.log(5, "%s", "999");
    }

    synchronized void setContext(Context context) {
        if (context != null) {
            this.mContext = context;
        }
    }

    synchronized void setMfiListener(MfiListener listener) {
        if (listener != null) {
            this.mMfiListener = listener;
        }
    }

    JSONArray getWalletAppCertHashList() {
        AccessController accessController = this.mAccessController;
        if (accessController != null) {
            return accessController.getWalletAppCertHashList();
        }
        return null;
    }

    String getWalletAppCallerInfo() {
        AccessController accessController = this.mAccessController;
        if (accessController != null) {
            return accessController.getWalletAppCallerInfo();
        }
        return null;
    }

    String getWalletAppIdentifiableInfo() {
        AccessController accessController = this.mAccessController;
        if (accessController != null) {
            return accessController.getWalletAppIdentifiableInfo();
        }
        return null;
    }

    String getWalletAppId() {
        AccessController accessController = this.mAccessController;
        if (accessController != null) {
            return accessController.getWalletAppId();
        }
        return null;
    }

    public synchronized int getTimeout() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getTimeout();
    }

    public synchronized void setTimeout(int timeout) throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setTimeout(timeout);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized int getRetryCount() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getRetryCount();
    }

    public synchronized void setRetryCount(int retryCount) throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setRetryCount(retryCount);
        LogMgr.log(5, "%s", "999");
    }

    synchronized void activateFelica(String callerPackageName, IFelicaEventListener listener, IBinder binder) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s In callerPackageName = %s, listener = %s", "000", callerPackageName, listener);
        if (binder == null) {
            LogMgr.log(1, "%s binder == null", "800");
            throw new FelicaException(1, 47);
        }
        if (listener == null) {
            LogMgr.log(1, "%s listener == null", "801");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        if (callerPackageName == null) {
            LogMgr.log(1, "%s callerPackageName == null", "802");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        FelicaAppInfo felicaAppInfo = this.mActivatedApp;
        if (felicaAppInfo != null) {
            if (felicaAppInfo.getPid() == Binder.getCallingPid() && this.mActivatedApp.getUid() == Binder.getCallingUid()) {
                LogMgr.log(1, "%s activatedApp.getPID(UID) == Binder.getCallingPid(Uid)", "803");
                throw new FelicaException(2, 42);
            }
            if (Binder.getCallingUid() == Process.myUid() && this.mActivatedApp.getPid() == Process.myPid() && callerPackageName.equals("com.felicanetworks.mfm.main")) {
                throw new FelicaException(2, 39, new AppInfo(this.mActivatedApp.getPid()), 0, 0, callerPackageName);
            }
            throw new FelicaException(2, 39, new AppInfo(this.mActivatedApp.getPid()));
        }
        if (!registerBinder(binder)) {
            LogMgr.log(1, "%s binder == null", "804");
            throw new FelicaException(1, 47);
        }
        ProfileManager.getInstance().loadProfileDataOnMemory(this.mContext, callerPackageName);
        WalletAppIdentifiableInfo.getInstance().setPackageName(callerPackageName);
        FelicaAppInfo felicaAppInfo2 = new FelicaAppInfo();
        this.mActivatedApp = felicaAppInfo2;
        felicaAppInfo2.setPid(Binder.getCallingPid());
        this.mActivatedApp.setUid(Binder.getCallingUid());
        ActivateThread activateThread = new ActivateThread();
        activateThread.mCallerPackageName = callerPackageName;
        this.mIFelicaEventListener = listener;
        this.mActivateWorker = activateThread;
        activateThread.start();
        LogMgr.log(5, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleActivateFeliCa() {
        LogMgr.log(5, "%s", "000");
        try {
        } catch (FelicaException e) {
            try {
                synchronized (this) {
                    if (this.mIFelicaEventListener != null) {
                        this.mIFelicaEventListener.errorOccurred(e.getID() == 2 ? 7 : 1, e.getMessage(), null);
                    }
                }
            } catch (RemoteException unused) {
                LogMgr.log(1, "%s %s", "801", "RemoteException");
            }
        } catch (IllegalArgumentException e2) {
            LogMgr.log(6, "%s %s", "002", e2.getMessage());
        } catch (Exception e3) {
            try {
                synchronized (this) {
                    IFelicaEventListener iFelicaEventListener = this.mIFelicaEventListener;
                    if (iFelicaEventListener != null) {
                        iFelicaEventListener.errorOccurred(1, e3.getMessage(), null);
                    }
                }
            } catch (RemoteException unused2) {
                LogMgr.log(1, "%s %s", "802", "RemoteException");
            }
        }
        if (!FelicaAdapter.getInstance().waitForBindService()) {
            LogMgr.log(1, "%s %s", "800", "Failed to bind Felica.");
            throw new FelicaException(1, 47);
        }
        checkFelica();
        this.mFelica.activateFelica(FlavorConst.MFC_PERMITS, this.mFelicaEventListener);
        LogMgr.log(6, "%s %s", "001", "Felica#activateFelica() succeeded. waiting...");
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void inactivateFelica() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        Felica felica = this.mFelica;
        if (felica != null) {
            felica.inactivateFelica();
        }
        unregisterBinder();
        ActivateThread activateThread = this.mActivateWorker;
        if (activateThread != null) {
            try {
                activateThread.interrupt();
                if (this.mActivateWorker.mAccessControlManagerThread != null) {
                    this.mActivateWorker.mAccessControlManagerThread.stopVerification();
                }
            } catch (Exception e) {
                LogMgr.log(1, "%s %s", "801", e.getClass().getSimpleName());
            }
            this.mActivateWorker = null;
            this.mAccessController = null;
            this.mActivatedApp = null;
            this.mIFelicaEventListener = null;
            WalletAppIdentifiableInfo.getInstance().discard();
            LogMgr.log(5, "%s", "999");
        } else {
            this.mAccessController = null;
            this.mActivatedApp = null;
            this.mIFelicaEventListener = null;
            WalletAppIdentifiableInfo.getInstance().discard();
            LogMgr.log(5, "%s", "999");
        }
    }

    public synchronized void open() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.open();
        try {
            checkAccessRight(4);
        } catch (FelicaException unused) {
            LogMgr.log(6, "%s", "001");
            try {
                checkAccessRight(5);
            } catch (FelicaException unused2) {
                LogMgr.log(6, "%s", "002");
                LogMgr.log(6, "%s", "003");
                try {
                    this.mFelica.select(65039);
                    byte[] containerIssueInformation = this.mFelica.getContainerIssueInformation();
                    this.mFelica.reset();
                    if (!AccessConfig.isValidContainerIssueInfo(containerIssueInformation)) {
                        LogMgr.log(1, "%s", "008");
                        try {
                            this.mFelica.close();
                        } catch (FelicaException e) {
                            LogMgr.log(2, "%s %s", "701", e.getClass().getSimpleName());
                        }
                        throw new FelicaException(8, 31);
                    }
                } catch (FelicaException e2) {
                    LogMgr.log(1, "%s", "004");
                    try {
                        this.mFelica.close();
                    } catch (FelicaException unused3) {
                        LogMgr.log(2, "%s %s", "700", e2.getClass().getSimpleName());
                    }
                    if (e2.getID() == 12) {
                        LogMgr.log(1, "%s", "005");
                        throw new FelicaException(1, 8);
                    }
                    if (e2.getID() == 1 && (e2.getType() == 9 || e2.getType() == 29 || e2.getType() == 44)) {
                        LogMgr.log(1, "%s", "006");
                        throw new FelicaException(1, 8);
                    }
                    LogMgr.log(1, "%s", "007");
                    throw e2;
                }
            }
        }
        LogMgr.log(6, "%s", "009");
        this.mOpened = true;
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void close() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        doClose();
        if (this.mFscStarting) {
            this.mFelicaCloseInFscStarting = true;
        }
        LogMgr.log(5, "%s", "999");
    }

    synchronized void doClose() throws FelicaException {
        LogMgr.log(5, "%s, callingPid=%d, callingUid=%d", "000", Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(Binder.getCallingUid()));
        if (this.mMfcOnline != null) {
            LogMgr.log(2, "%s, force stop online", "701");
            this.mMfcOnline.kill();
        }
        Felica felica = this.mFelica;
        if (felica != null) {
            felica.close();
        }
        this.mOpened = false;
        this.mMfcOnline = null;
        this.mTarget = 0;
        this.mSystemCode = 0;
        this.mSelected = false;
        this.mNodeCodeSize = 2;
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void select(int systemCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        select(0, systemCode);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void select(int target, int systemCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (target == 0) {
            try {
                checkAccessRight(1);
                checkAccessSystemCode(systemCode);
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "806");
                throw new FelicaException(1, 9);
            }
        }
        this.mFelica.select(target, systemCode);
        this.mTarget = target;
        this.mSystemCode = systemCode;
        this.mSelected = true;
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void select(int systemCode, String cid) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "000");
        checkFelica();
        try {
            checkAccessRight(1);
            checkAccessSystemCode(systemCode);
            this.mFelica.select(systemCode, cid);
            this.mSystemCode = systemCode;
            this.mSelected = true;
            LogMgr.log(5, "999");
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "806 Exception");
            throw new FelicaException(1, 9);
        }
    }

    public synchronized int getSystemCode() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getSystemCode();
    }

    public synchronized int getInterface() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getInterface();
    }

    public synchronized byte[] getIDm() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getIDm();
    }

    public synchronized byte[] getICCode() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getICCode();
    }

    public synchronized int getKeyVersion(int serviceCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0 && (this.mNodeCodeSize != 2 ? serviceCode != -1 : (serviceCode & 65535) != 65535)) {
            try {
                checkAccessNodeCodeList(this.mSystemCode, new int[]{serviceCode});
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "805");
                throw new FelicaException(1, 10);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getKeyVersion(serviceCode);
    }

    public synchronized byte[] getContainerIssueInformation() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getContainerIssueInformation();
    }

    synchronized int getNodeCodeSize() {
        return this.mNodeCodeSize;
    }

    public synchronized void setNodeCodeSize(int nodeCodeSize) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setNodeCodeSize(nodeCodeSize);
        this.mNodeCodeSize = nodeCodeSize;
        LogMgr.log(5, "%s", "999");
    }

    public synchronized Data[] read(BlockList blockList) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            int[] iArr = new int[blockList.size()];
            for (int i = 0; i < blockList.size(); i++) {
                iArr[i] = blockList.get(i).getServiceCode();
            }
            try {
                checkAccessNodeCodeList(this.mSystemCode, iArr);
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "810");
                throw new FelicaException(1, 14);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.read(blockList);
    }

    public synchronized void write(BlockDataList blockDataList) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        if (this.mTarget == 0) {
            int[] iArr = new int[blockDataList.size()];
            for (int i = 0; i < blockDataList.size(); i++) {
                iArr[i] = blockDataList.get(i).getBlock().getServiceCode();
            }
            try {
                checkAccessNodeCodeList(this.mSystemCode, iArr);
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s OfflineException", "816");
                throw new FelicaException(1, 20);
            }
        }
        this.mFelica.write(blockDataList);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void reset() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        this.mFelica.reset();
        resetInner();
        LogMgr.log(5, "%s", "999");
    }

    synchronized void resetInner() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkOpened();
        this.mNodeCodeSize = 2;
        this.mSystemCode = 0;
        this.mTarget = 0;
        this.mSelected = false;
        LogMgr.log(5, "%s", "999");
    }

    public synchronized int[] getSystemCodeList() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            try {
                checkAccessRight(4);
            } catch (FelicaException e) {
                LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE", "802");
                throw e;
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 45);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getSystemCodeList();
    }

    public synchronized NodeInformation getNodeInformation(int parentAreaCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            try {
                checkAccessRight(4);
            } catch (FelicaException e) {
                LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE", "802");
                throw e;
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 34);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getNodeInformation(parentAreaCode);
    }

    public synchronized NodeInformation getPrivacyNodeInformation(int parentAreaCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            try {
                checkAccessNodeCodeList(this.mSystemCode, new int[]{parentAreaCode});
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "802");
                throw new FelicaException(1, 35);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getPrivacyNodeInformation(parentAreaCode);
    }

    public synchronized BlockCountInformation[] getBlockCountInformation(int[] nodeCodeList) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            try {
                checkAccessRight(4);
            } catch (FelicaException e) {
                LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE", "802");
                throw e;
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 43);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getBlockCountInformation(nodeCodeList);
    }

    public synchronized boolean getRFSState() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getRFSState();
    }

    public synchronized void setPrivacy(PrivacySettingData[] privacySettingData) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            try {
                checkAccessRight(5);
            } catch (FelicaException e) {
                LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_2_ATTRIBUTE", "802");
                throw e;
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 36);
            }
        }
        this.mFelica.setPrivacy(privacySettingData);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized byte[] getContainerId() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            try {
                checkAccessRight(4);
            } catch (FelicaException e) {
                LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_1_ATTRIBUTE", "802");
                throw e;
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "801");
                throw new FelicaException(1, 46);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getContainerId();
    }

    public synchronized void push(PushSegment pushSegment) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        this.mFelica.push(pushSegment);
    }

    public synchronized void setPushNotificationListener(PushAppNotificationListener listener, String appIdentificationCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setPushNotificationListener(listener, appIdentificationCode);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized int getSelectTimeout() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getSelectTimeout();
    }

    public synchronized void setSelectTimeout(int timeout) throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setSelectTimeout(timeout);
        LogMgr.log(5, "%s", "999");
    }

    public void cancelOffline() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.cancelOffline();
        LogMgr.log(5, "%s", "999");
    }

    public synchronized byte[] executeFelicaCommand(byte[] commandPacket) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        try {
            checkAccessRight(5);
            LogMgr.log(5, "%s", "999");
        } catch (FelicaException e) {
            LogMgr.log(1, "%s category != CATEGORY_PRIVILEGED_ACCESS_2_ATTRIBUTE", "802");
            throw e;
        } catch (NumberFormatException unused) {
            LogMgr.log(1, "%s Exception", "801");
            throw new FelicaException(1, 63);
        }
        return this.mFelica.executeFelicaCommand(commandPacket);
    }

    public synchronized KeyInformation[] getKeyVersionV2(int[] nodeCodeList) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            for (int i : nodeCodeList) {
                if ((i & 65535) != 65535) {
                    try {
                        checkAccessNodeCodeList(this.mSystemCode, new int[]{i});
                    } catch (NumberFormatException unused) {
                        LogMgr.log(1, "%s Exception", "805");
                        throw new FelicaException(1, 64);
                    }
                }
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getKeyVersionV2(nodeCodeList);
    }

    void clearTarget() {
        this.mTarget = 0;
    }

    synchronized void checkAccessRight(int category) throws FelicaException {
        AccessController accessController = this.mAccessController;
        if (accessController == null) {
            throw new FelicaException(12, 38);
        }
        try {
            accessController.check(category);
        } catch (AccessControllerException e) {
            throw convException(e, 38);
        }
    }

    synchronized boolean canAccessRight(int category) {
        AccessController accessController = this.mAccessController;
        if (accessController == null) {
            return false;
        }
        try {
            accessController.check(category);
            return true;
        } catch (AccessControllerException unused) {
            return false;
        }
    }

    private void checkAccessSystemCode(int systemCode) throws NumberFormatException, FelicaException {
        try {
            this.mAccessController.checkSystemCode(systemCode);
        } catch (AccessControllerException e) {
            throw convException(e, 50);
        }
    }

    private void checkAccessNodeCodeList(int currentSystemCode, int[] nodeCodeList) throws NumberFormatException, FelicaException {
        try {
            this.mAccessController.checkNodeCodeList(currentSystemCode, nodeCodeList);
        } catch (AccessControllerException e) {
            throw convException(e, 50);
        }
    }

    public void checkMfiAccessServiceId(String serviceId) throws FelicaException {
        try {
            this.mAccessController.checkMfiServiceId(serviceId);
        } catch (AccessControllerException e) {
            throw convException(e, MfiClientException.TYPE_ILLEGAL_SERVICEID);
        }
    }

    private FelicaException convException(AccessControllerException ace, int defaultErrorType) {
        int type = ace.getType();
        if (type == 0) {
            return new FelicaException(12, 32);
        }
        if (type == 1) {
            return new FelicaException(12, 38);
        }
        if (type == 2) {
            return new FelicaException(12, 50);
        }
        return new FelicaException(12, defaultErrorType);
    }

    private synchronized boolean registerBinder(IBinder binder) {
        LogMgr.log(3, "%s", "000");
        if (binder == null) {
            return false;
        }
        try {
            this.mDeathRecipient = new LocalDeathRecipient(binder);
            return true;
        } catch (RemoteException unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void unregisterBinder() {
        IBinder binder;
        LogMgr.log(3, "%s", "000");
        LocalDeathRecipient localDeathRecipient = this.mDeathRecipient;
        if (localDeathRecipient != null && (binder = localDeathRecipient.getBinder()) != null) {
            binder.unlinkToDeath(this.mDeathRecipient, 0);
        }
        this.mDeathRecipient = null;
    }

    int getTarget() {
        return this.mTarget;
    }

    void checkActivated() throws FelicaException {
        checkPidUid();
        checkActivateWorker();
    }

    void checkOpenedApp() throws FelicaException {
        try {
            checkActivated();
        } catch (FelicaException unused) {
            throw new FelicaException(2, 1);
        }
    }

    void checkMfiActivated() throws MfiClientException {
        try {
            checkActivated();
        } catch (FelicaException unused) {
            throw new MfiClientException(2, 152, null);
        }
    }

    void checkPidUid() throws FelicaException {
        checkPidUid(Binder.getCallingPid(), Binder.getCallingUid());
    }

    void checkPidUid(int pid, int uid) throws FelicaException {
        LogMgr.log(5, "%s pid = %d uid = %d", "000", Integer.valueOf(pid), Integer.valueOf(uid));
        FelicaAppInfo felicaAppInfo = this.mActivatedApp;
        if (felicaAppInfo == null) {
            LogMgr.log(1, "%s activatedApp == null", "800");
            throw new FelicaException(2, 5);
        }
        try {
            if (felicaAppInfo.getPid() != pid || this.mActivatedApp.getUid() != uid) {
                LogMgr.log(1, "%s  pid = %d, uid = %d, activatedApp.getPID() = %d, activatedApp.getUID() = %d", "801", Integer.valueOf(pid), Integer.valueOf(uid), Integer.valueOf(this.mActivatedApp.getPid()), Integer.valueOf(this.mActivatedApp.getUid()));
                throw new FelicaException(2, 5);
            }
            LogMgr.log(5, "%s", "999");
        } catch (NullPointerException unused) {
            LogMgr.log(1, "%s NullPointerException", "802");
            throw new FelicaException(2, 5);
        }
    }

    private void checkActivateWorker() throws FelicaException {
        if (this.mActivateWorker == null) {
            return;
        }
        LogMgr.log(1, "%s activateWorker != null", "800");
        throw new FelicaException(2, 5);
    }

    synchronized void checkOpened() throws FelicaException {
        checkOpenedNosync();
    }

    void checkOpenedNosync() throws FelicaException {
        if (!this.mOpened) {
            throw new FelicaException(2, 1);
        }
    }

    synchronized void checkNotOpened() throws FelicaException {
        checkNotOpenedNoSync();
    }

    void checkNotOpenedNoSync() throws FelicaException {
        if (this.mOpened) {
            throw new FelicaException(2, 37);
        }
    }

    synchronized boolean isOpened() {
        return this.mOpened;
    }

    void checkSelected() throws FelicaException {
        if (!this.mSelected) {
            throw new FelicaException(2, 3);
        }
    }

    synchronized void checkOnlineAccess() throws FelicaException {
        checkAccessRight(2);
        this.mFscStarting = true;
        this.mFelicaCloseInFscStarting = false;
    }

    synchronized void checkClosedinStarting() throws FelicaException {
        if (this.mFelicaCloseInFscStarting) {
            throw new FelicaException(2, 1);
        }
    }

    void checkNotOnline() throws FelicaException {
        if (this.mMfcOnline != null) {
            throw new FelicaException(2, 2);
        }
    }

    void checkNotLoggedIn() throws MfiClientException {
        if (this.mMfiOnline != null) {
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_STARTED, null);
        }
    }

    void checkInterfaceWired() throws FelicaException {
        if (this.mTarget != 0) {
            throw new FelicaException(2, 54);
        }
    }

    private void checkFelica() throws FelicaException {
        if (this.mFelica != null) {
            return;
        }
        LogMgr.log(1, "%s %s", "800", "mFelica is null.");
        throw new FelicaException(1, 47);
    }

    private class ActivateThread extends Thread {
        private static final String ERROR_MESSAGE_UNKNOW = "Unknown error.";
        AccessControlManagerThread mAccessControlManagerThread;
        String mCallerPackageName;

        private ActivateThread() {
            this.mAccessControlManagerThread = null;
        }

        private class ActivateException extends RuntimeException {
            private static final long serialVersionUID = 4651486544932413838L;
            final int mErrorType;

            ActivateException(int type, String message) {
                super(message);
                this.mErrorType = type;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public int getErrorType() {
                return this.mErrorType;
            }
        }

        void checkInterrupted() throws InterruptedException {
            LogMgr.log(5, "%s", "000");
            if (isInterrupted()) {
                LogMgr.log(5, "%s", "001");
                throw new InterruptedException("Interruption is occured.");
            }
            LogMgr.log(5, "%s", "999");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws Throwable {
            LogMgr.log(5, "%s", "000");
            try {
                try {
                    try {
                        synchronized (FelicaWrapper.this) {
                            try {
                                LogMgr.log(7, "%s", "001");
                                if (FelicaWrapper.this.mActivatedApp != null) {
                                    LogMgr.log(7, "%s", "002");
                                    this.mAccessControlManagerThread = new AccessControlManagerThread(FelicaWrapper.this.mContext, this.mCallerPackageName, FelicaWrapper.this.mActivatedApp.getPid(), FelicaWrapper.this.mActivatedApp.getUid());
                                    try {
                                        ForegroundServiceSetupProvider.requestForegroundService(FelicaAdapter.getInstance(), ForegroundServiceSetupProvider.Type.FELICA_ADAPTER);
                                        synchronized (FelicaWrapper.this) {
                                            OtherDeviceDataCleaner.doClean(FelicaWrapper.this.mContext);
                                        }
                                        checkInterrupted();
                                        this.mAccessControlManagerThread.start();
                                        this.mAccessControlManagerThread.join();
                                        synchronized (FelicaWrapper.this) {
                                            checkInterrupted();
                                            try {
                                                if (this.mAccessControlManagerThread.isSucceeded()) {
                                                    LogMgr.log(7, "%s", "003");
                                                    FelicaWrapper.this.mAccessController = this.mAccessControlManagerThread.getAccessController();
                                                } else {
                                                    LogMgr.log(7, "%s", "004");
                                                    throw new ActivateException(this.mAccessControlManagerThread.getErrorType(), this.mAccessControlManagerThread.getErrorMessage());
                                                }
                                            } catch (FelicaException unused) {
                                                LogMgr.log(7, "%s", "005");
                                                throw new ActivateException(1, "Unknown error.");
                                            }
                                        }
                                        LogMgr.log(7, "%s", "006");
                                        synchronized (FelicaWrapper.this) {
                                            FelicaWrapper.this.mActivateWorker = null;
                                        }
                                        FelicaWrapper.this.handleActivateFeliCa();
                                    } catch (ForegroundServiceStartNotAllowedException unused2) {
                                        LogMgr.log(1, "800 ForegroundServiceStartNotAllowedException");
                                        throw new ActivateException(1, FelicaException.FOREGROUND_SERVICE_NOT_ALLOWED_MESSAGE);
                                    }
                                }
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (ActivateException e) {
                    e = e;
                    LogMgr.log(1, "802 ActivateException");
                    synchronized (FelicaWrapper.this) {
                        FelicaWrapper.this.unregisterBinder();
                        FelicaWrapper.this.mActivateWorker = null;
                        FelicaWrapper.this.mActivatedApp = null;
                        FelicaWrapper.this.mAccessController = null;
                        WalletAppIdentifiableInfo.getInstance().discard();
                        if (FelicaWrapper.this.mIFelicaEventListener != null && e.getErrorType() != 2) {
                            LogMgr.log(7, "007");
                            try {
                                FelicaWrapper.this.mIFelicaEventListener.errorOccurred(e.getErrorType(), e.getMessage(), null);
                            } catch (RemoteException unused3) {
                                LogMgr.log(1, "803 RemoteException");
                            }
                        }
                    }
                } catch (InterruptedException unused4) {
                    LogMgr.log(2, "%s InterrptedException", "801");
                    WalletAppIdentifiableInfo.getInstance().discard();
                }
            } catch (ActivateException e2) {
                e = e2;
            } catch (InterruptedException unused5) {
            }
        }

        class AccessControlManagerThread extends Thread {
            private AccessControlManager mAccessControlManager;
            private String mCallerPackageName;
            private int mPid;
            private int mUid;
            private boolean mSucceeded = false;
            private int mErrorType = 4;
            private String mErrorMessage = null;

            private int convErrorType(int errorType) {
                int i = 2;
                if (errorType != 2) {
                    i = 3;
                    if (errorType != 3) {
                        i = 4;
                        if (errorType != 4) {
                            if (errorType == 5) {
                                return 6;
                            }
                            int i2 = 100;
                            if (errorType != 100) {
                                i2 = 101;
                                if (errorType != 101) {
                                    return 1;
                                }
                            }
                            return i2;
                        }
                    }
                }
                return i;
            }

            AccessControlManagerThread(Context context, String callerPackageName, int pid, int uid) {
                this.mAccessControlManager = null;
                this.mCallerPackageName = callerPackageName;
                this.mPid = pid;
                this.mUid = uid;
                AccessControlManagerImpl accessControlManagerImpl = new AccessControlManagerImpl();
                if (accessControlManagerImpl instanceof AccessControlManager) {
                    this.mAccessControlManager = accessControlManagerImpl;
                    accessControlManagerImpl.init(context);
                }
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                AccessControlManager accessControlManager = this.mAccessControlManager;
                if (accessControlManager != null) {
                    boolean zStartAccessControl = accessControlManager.startAccessControl(this.mCallerPackageName, this.mPid, this.mUid);
                    this.mSucceeded = zStartAccessControl;
                    if (zStartAccessControl) {
                        return;
                    }
                    this.mErrorMessage = this.mAccessControlManager.getErrorMessage();
                    this.mErrorType = convErrorType(this.mAccessControlManager.getErrorType());
                    return;
                }
                this.mErrorMessage = null;
                this.mErrorType = 1;
            }

            void stopVerification() {
                interrupt();
                AccessControlManager accessControlManager = this.mAccessControlManager;
                if (accessControlManager != null) {
                    accessControlManager.stopAccessControl();
                }
            }

            boolean isSucceeded() {
                return this.mSucceeded;
            }

            AccessController getAccessController() throws FelicaException {
                if (this.mAccessControlManager != null && isSucceeded()) {
                    return this.mAccessControlManager.getAccessController();
                }
                throw new FelicaException();
            }

            int getErrorType() throws FelicaException {
                if (!isSucceeded()) {
                    return this.mErrorType;
                }
                LogMgr.log(2, "%s isScceeded() is false", "998");
                throw new FelicaException();
            }

            String getErrorMessage() throws FelicaException {
                if (!isSucceeded()) {
                    return this.mErrorMessage;
                }
                LogMgr.log(2, "%s isScceeded() is false", "998");
                throw new FelicaException();
            }
        }
    }

    private class LocalDeathRecipient implements IBinder.DeathRecipient {
        IBinder mBinder;

        LocalDeathRecipient(IBinder binder) throws RemoteException {
            binder.linkToDeath(this, 0);
            this.mBinder = binder;
            LogMgr.log(3, "%s", "999");
        }

        IBinder getBinder() {
            return this.mBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            LogMgr.log(3, "%s", "000");
            synchronized (FelicaWrapper.this) {
                FelicaWrapper.this.mDeathRecipient = null;
            }
            FelicaWrapper.this.mMfiListener.mfiCancel();
        }
    }

    private class FelicaAppInfo {
        int mPid;
        int mUid;

        private FelicaAppInfo() {
        }

        int getUid() {
            return this.mUid;
        }

        void setUid(int uid) {
            this.mUid = uid;
        }

        int getPid() {
            return this.mPid;
        }

        void setPid(int pid) {
            this.mPid = pid;
        }
    }

    private class LocalFelicaEventListener implements FelicaEventListener {
        private LocalFelicaEventListener() {
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void finished() {
            LogMgr.log(5, "%s", "000");
            try {
                synchronized (FelicaWrapper.this) {
                    if (FelicaWrapper.this.mIFelicaEventListener != null) {
                        FelicaWrapper.this.mIFelicaEventListener.finished();
                    }
                }
            } catch (RemoteException unused) {
                LogMgr.log(1, "RemoteException");
            }
            LogMgr.log(5, "%s", "999");
        }

        @Override // com.felicanetworks.mfc.FelicaEventListener
        public void errorOccurred(int id, String msg, AppInfo otherAppInfo) {
            LogMgr.log(5, "%s", "000");
            try {
                synchronized (FelicaWrapper.this) {
                    if (FelicaWrapper.this.mIFelicaEventListener != null) {
                        FelicaWrapper.this.mIFelicaEventListener.errorOccurred(id, msg, otherAppInfo);
                    }
                }
            } catch (RemoteException unused) {
                LogMgr.log(1, "RemoteException");
            }
            LogMgr.log(5, "%s", "999");
        }
    }
}
