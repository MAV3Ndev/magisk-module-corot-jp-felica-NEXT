package com.felicanetworks.mfc.mfi;

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
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManager;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControlManagerImpl;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessController;
import com.felicanetworks.mfc.mfi.felica.access_control.AccessControllerException;
import com.felicanetworks.mfc.util.LogMgr;
import org.json.JSONArray;

/* JADX INFO: loaded from: classes.dex */
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

    synchronized void setMfiListener(MfiListener mfiListener) {
        if (mfiListener != null) {
            this.mMfiListener = mfiListener;
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

    public synchronized void setTimeout(int i) throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setTimeout(i);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized int getRetryCount() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getRetryCount();
    }

    public synchronized void setRetryCount(int i) throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setRetryCount(i);
        LogMgr.log(5, "%s", "999");
    }

    synchronized void activateFelica(String str, IFelicaEventListener iFelicaEventListener, IBinder iBinder) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s In callerPackageName = %s, listener = %s", "000", str, iFelicaEventListener);
        if (iBinder == null) {
            LogMgr.log(1, "%s binder == null", "800");
            throw new FelicaException(1, 47);
        }
        if (iFelicaEventListener == null) {
            LogMgr.log(1, "%s listener == null", "801");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        if (str == null) {
            LogMgr.log(1, "%s callerPackageName == null", "802");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        if (this.mActivatedApp != null) {
            if (this.mActivatedApp.getPid() == Binder.getCallingPid() && this.mActivatedApp.getUid() == Binder.getCallingUid()) {
                LogMgr.log(1, "%s activatedApp.getPID(UID) == Binder.getCallingPid(Uid)", "803");
                throw new FelicaException(2, 42);
            }
            if (Binder.getCallingUid() == Process.myUid() && this.mActivatedApp.getPid() == Process.myPid() && str.equals("com.felicanetworks.mfm.main")) {
                throw new FelicaException(2, 39, new AppInfo(this.mActivatedApp.getPid()), 0, 0, str);
            }
            throw new FelicaException(2, 39, new AppInfo(this.mActivatedApp.getPid()));
        }
        if (!registerBinder(iBinder)) {
            LogMgr.log(1, "%s binder == null", "804");
            throw new FelicaException(1, 47);
        }
        FelicaAppInfo felicaAppInfo = new FelicaAppInfo();
        this.mActivatedApp = felicaAppInfo;
        felicaAppInfo.setPid(Binder.getCallingPid());
        this.mActivatedApp.setUid(Binder.getCallingUid());
        ActivateThread activateThread = new ActivateThread();
        activateThread.mCallerPackageName = str;
        this.mIFelicaEventListener = iFelicaEventListener;
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
                    if (this.mIFelicaEventListener != null) {
                        this.mIFelicaEventListener.errorOccurred(1, e3.getMessage(), null);
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
        if (this.mFelica != null) {
            this.mFelica.inactivateFelica();
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
            this.mAccessController = null;
            this.mActivatedApp = null;
            this.mIFelicaEventListener = null;
            LogMgr.log(5, "%s", "999");
        } else {
            this.mAccessController = null;
            this.mActivatedApp = null;
            this.mIFelicaEventListener = null;
            LogMgr.log(5, "%s", "999");
        }
    }

    public synchronized void open() throws FelicaException {
        boolean z;
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.open();
        boolean z2 = false;
        try {
            checkAccessRight(4);
        } catch (FelicaException unused) {
            LogMgr.log(6, "%s", "001");
            try {
                checkAccessRight(5);
            } catch (FelicaException unused2) {
                LogMgr.log(6, "%s", "002");
                z = false;
            }
        }
        z = true;
        if (!z) {
            LogMgr.log(6, "%s", "003");
            try {
                this.mFelica.select(65039);
                byte[] containerIssueInformation = this.mFelica.getContainerIssueInformation();
                this.mFelica.reset();
                int i = 0;
                while (true) {
                    if (i >= containerIssueInformation.length) {
                        break;
                    }
                    if (containerIssueInformation[i] != 0) {
                        z2 = true;
                        break;
                    }
                    i++;
                }
                if (!z2) {
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
        } else {
            LogMgr.log(6, "%s", "009");
        }
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
        if (this.mFelica != null) {
            this.mFelica.close();
        }
        this.mOpened = false;
        this.mMfcOnline = null;
        this.mTarget = 0;
        this.mSystemCode = 0;
        this.mSelected = false;
        this.mNodeCodeSize = 2;
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void select(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        select(0, i);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void select(int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (i == 0) {
            try {
                checkAccessRight(1);
                checkAccessSystemCode(i2);
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "806");
                throw new FelicaException(1, 9);
            }
        }
        this.mFelica.select(i, i2);
        this.mTarget = i;
        this.mSystemCode = i2;
        this.mSelected = true;
        LogMgr.log(5, "%s", "999");
    }

    public synchronized void select(int i, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "000");
        checkFelica();
        try {
            checkAccessRight(1);
            checkAccessSystemCode(i);
            this.mFelica.select(i, str);
            this.mSystemCode = i;
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

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0022, code lost:
    
        r0 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public synchronized int getKeyVersion(int r6) throws com.felicanetworks.mfc.FelicaException, java.lang.IllegalArgumentException {
        /*
            r5 = this;
            monitor-enter(r5)
            java.lang.String r0 = "%s"
            java.lang.String r1 = "000"
            r2 = 5
            com.felicanetworks.mfc.util.LogMgr.log(r2, r0, r1)     // Catch: java.lang.Throwable -> L4f
            r5.checkFelica()     // Catch: java.lang.Throwable -> L4f
            int r0 = r5.mTarget     // Catch: java.lang.Throwable -> L4f
            if (r0 != 0) goto L40
            int r0 = r5.mNodeCodeSize     // Catch: java.lang.Throwable -> L4f
            r1 = 2
            r3 = 0
            r4 = 1
            if (r0 != r1) goto L1f
            r0 = 65535(0xffff, float:9.1834E-41)
            r1 = r6 & r0
            if (r1 != r0) goto L24
            goto L22
        L1f:
            r0 = -1
            if (r6 != r0) goto L24
        L22:
            r0 = 0
            goto L25
        L24:
            r0 = 1
        L25:
            if (r0 == 0) goto L40
            int[] r0 = new int[r4]     // Catch: java.lang.NumberFormatException -> L31 java.lang.Throwable -> L4f
            r0[r3] = r6     // Catch: java.lang.NumberFormatException -> L31 java.lang.Throwable -> L4f
            int r1 = r5.mSystemCode     // Catch: java.lang.NumberFormatException -> L31 java.lang.Throwable -> L4f
            r5.checkAccessNodeCodeList(r1, r0)     // Catch: java.lang.NumberFormatException -> L31 java.lang.Throwable -> L4f
            goto L40
        L31:
            java.lang.String r6 = "%s Exception"
            java.lang.String r0 = "805"
            com.felicanetworks.mfc.util.LogMgr.log(r4, r6, r0)     // Catch: java.lang.Throwable -> L4f
            com.felicanetworks.mfc.FelicaException r6 = new com.felicanetworks.mfc.FelicaException     // Catch: java.lang.Throwable -> L4f
            r0 = 10
            r6.<init>(r4, r0)     // Catch: java.lang.Throwable -> L4f
            throw r6     // Catch: java.lang.Throwable -> L4f
        L40:
            java.lang.String r0 = "%s"
            java.lang.String r1 = "999"
            com.felicanetworks.mfc.util.LogMgr.log(r2, r0, r1)     // Catch: java.lang.Throwable -> L4f
            com.felicanetworks.mfc.Felica r0 = r5.mFelica     // Catch: java.lang.Throwable -> L4f
            int r6 = r0.getKeyVersion(r6)     // Catch: java.lang.Throwable -> L4f
            monitor-exit(r5)
            return r6
        L4f:
            r6 = move-exception
            monitor-exit(r5)
            throw r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.FelicaWrapper.getKeyVersion(int):int");
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

    public synchronized void setNodeCodeSize(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setNodeCodeSize(i);
        this.mNodeCodeSize = i;
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

    public synchronized NodeInformation getNodeInformation(int i) throws FelicaException, IllegalArgumentException {
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
        return this.mFelica.getNodeInformation(i);
    }

    public synchronized NodeInformation getPrivacyNodeInformation(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            try {
                checkAccessNodeCodeList(this.mSystemCode, new int[]{i});
            } catch (NumberFormatException unused) {
                LogMgr.log(1, "%s Exception", "802");
                throw new FelicaException(1, 35);
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getPrivacyNodeInformation(i);
    }

    public synchronized BlockCountInformation[] getBlockCountInformation(int[] iArr) throws FelicaException, IllegalArgumentException {
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
        return this.mFelica.getBlockCountInformation(iArr);
    }

    public synchronized boolean getRFSState() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getRFSState();
    }

    public synchronized void setPrivacy(PrivacySettingData[] privacySettingDataArr) throws FelicaException, IllegalArgumentException {
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
        this.mFelica.setPrivacy(privacySettingDataArr);
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

    public synchronized void setPushNotificationListener(PushAppNotificationListener pushAppNotificationListener, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setPushNotificationListener(pushAppNotificationListener, str);
        LogMgr.log(5, "%s", "999");
    }

    public synchronized int getSelectTimeout() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getSelectTimeout();
    }

    public synchronized void setSelectTimeout(int i) throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.setSelectTimeout(i);
        LogMgr.log(5, "%s", "999");
    }

    public void cancelOffline() throws FelicaException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        this.mFelica.cancelOffline();
        LogMgr.log(5, "%s", "999");
    }

    public synchronized byte[] executeFelicaCommand(byte[] bArr) throws FelicaException, IllegalArgumentException {
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
        return this.mFelica.executeFelicaCommand(bArr);
    }

    public synchronized KeyInformation[] getKeyVersionV2(int[] iArr) throws FelicaException, IllegalArgumentException {
        LogMgr.log(5, "%s", "000");
        checkFelica();
        if (this.mTarget == 0) {
            for (int i = 0; i < iArr.length; i++) {
                if ((iArr[i] & 65535) != 65535) {
                    try {
                        checkAccessNodeCodeList(this.mSystemCode, new int[]{iArr[i]});
                    } catch (NumberFormatException unused) {
                        LogMgr.log(1, "%s Exception", "805");
                        throw new FelicaException(1, 64);
                    }
                }
            }
        }
        LogMgr.log(5, "%s", "999");
        return this.mFelica.getKeyVersionV2(iArr);
    }

    void clearTarget() {
        this.mTarget = 0;
    }

    synchronized void checkAccessRight(int i) throws FelicaException {
        if (this.mAccessController == null) {
            throw new FelicaException(12, 38);
        }
        try {
            this.mAccessController.check(i);
        } catch (AccessControllerException e) {
            throw convException(e, 38);
        }
    }

    synchronized boolean canAccessRight(int i) {
        if (this.mAccessController == null) {
            return false;
        }
        try {
            this.mAccessController.check(i);
            return true;
        } catch (AccessControllerException unused) {
            return false;
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

    public void checkMfiAccessServiceId(String str) throws FelicaException {
        try {
            this.mAccessController.checkMfiServiceId(str);
        } catch (AccessControllerException e) {
            throw convException(e, MfiClientException.TYPE_ILLEGAL_SERVICEID);
        }
    }

    private FelicaException convException(AccessControllerException accessControllerException, int i) {
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
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_ACTIVATED, null);
        }
    }

    void checkPidUid() throws FelicaException {
        checkPidUid(Binder.getCallingPid(), Binder.getCallingUid());
    }

    void checkPidUid(int i, int i2) throws FelicaException {
        LogMgr.log(5, "%s pid = %d uid = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        FelicaAppInfo felicaAppInfo = this.mActivatedApp;
        if (felicaAppInfo == null) {
            LogMgr.log(1, "%s activatedApp == null", "800");
            throw new FelicaException(2, 5);
        }
        try {
            if (felicaAppInfo.getPid() != i || this.mActivatedApp.getUid() != i2) {
                LogMgr.log(1, "%s  pid = %d, uid = %d, activatedApp.getPID() = %d, activatedApp.getUID() = %d", "801", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(this.mActivatedApp.getPid()), Integer.valueOf(this.mActivatedApp.getUid()));
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

        void checkInterrupted() throws InterruptedException {
            LogMgr.log(5, "%s", "000");
            if (isInterrupted()) {
                LogMgr.log(5, "%s", "001");
                throw new InterruptedException("Interruption is occured.");
            }
            LogMgr.log(5, "%s", "999");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            boolean z;
            String errorMessage;
            int errorType;
            LogMgr.log(5, "%s", "000");
            try {
                synchronized (FelicaWrapper.this) {
                    LogMgr.log(7, "%s", "001");
                    if (FelicaWrapper.this.mActivatedApp != null) {
                        LogMgr.log(7, "%s", "002");
                        this.mAccessControlManagerThread = new AccessControlManagerThread(FelicaWrapper.this.mContext, this.mCallerPackageName, FelicaWrapper.this.mActivatedApp.getPid(), FelicaWrapper.this.mActivatedApp.getUid());
                    }
                }
                checkInterrupted();
                this.mAccessControlManagerThread.start();
                this.mAccessControlManagerThread.join();
                synchronized (FelicaWrapper.this) {
                    checkInterrupted();
                    z = false;
                    try {
                        if (this.mAccessControlManagerThread.isSucceeded()) {
                            LogMgr.log(7, "%s", "003");
                            FelicaWrapper.this.mAccessController = this.mAccessControlManagerThread.getAccessController();
                            errorMessage = null;
                            z = true;
                            errorType = 0;
                        } else {
                            LogMgr.log(7, "%s", "004");
                            errorType = this.mAccessControlManagerThread.getErrorType();
                            errorMessage = this.mAccessControlManagerThread.getErrorMessage();
                        }
                    } catch (FelicaException unused) {
                        LogMgr.log(7, "%s", "005");
                        errorMessage = "Unknown error.";
                        errorType = 1;
                    }
                }
                if (z) {
                    LogMgr.log(7, "%s", "006");
                    synchronized (FelicaWrapper.this) {
                        FelicaWrapper.this.mActivateWorker = null;
                    }
                    FelicaWrapper.this.handleActivateFeliCa();
                    return;
                }
                LogMgr.log(7, "%s", "007");
                synchronized (FelicaWrapper.this) {
                    FelicaWrapper.this.unregisterBinder();
                    FelicaWrapper.this.mActivateWorker = null;
                    FelicaWrapper.this.mActivatedApp = null;
                    if (FelicaWrapper.this.mIFelicaEventListener != null && errorType != 2) {
                        LogMgr.log(7, "%s", "008");
                        FelicaWrapper.this.mIFelicaEventListener.errorOccurred(errorType, errorMessage, null);
                    }
                }
            } catch (RemoteException unused2) {
                LogMgr.log(1, "%s RemoteException", "800");
                synchronized (FelicaWrapper.this) {
                    FelicaWrapper.this.unregisterBinder();
                    FelicaWrapper.this.mActivateWorker = null;
                    FelicaWrapper.this.mActivatedApp = null;
                    FelicaWrapper.this.mAccessController = null;
                }
            } catch (InterruptedException unused3) {
                LogMgr.log(2, "%s InterrptedException", "801");
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

            private int convErrorType(int i) {
                int i2 = 2;
                if (i != 2) {
                    i2 = 3;
                    if (i != 3) {
                        i2 = 4;
                        if (i != 4) {
                            if (i == 5) {
                                return 6;
                            }
                            int i3 = 100;
                            if (i != 100) {
                                i3 = 101;
                                if (i != 101) {
                                    return 1;
                                }
                            }
                            return i3;
                        }
                    }
                }
                return i2;
            }

            AccessControlManagerThread(Context context, String str, int i, int i2) {
                this.mAccessControlManager = null;
                this.mCallerPackageName = str;
                this.mPid = i;
                this.mUid = i2;
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

        void setUid(int i) {
            this.mUid = i;
        }

        int getPid() {
            return this.mPid;
        }

        void setPid(int i) {
            this.mPid = i;
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
        public void errorOccurred(int i, String str, AppInfo appInfo) {
            LogMgr.log(5, "%s", "000");
            try {
                synchronized (FelicaWrapper.this) {
                    if (FelicaWrapper.this.mIFelicaEventListener != null) {
                        FelicaWrapper.this.mIFelicaEventListener.errorOccurred(i, str, appInfo);
                    }
                }
            } catch (RemoteException unused) {
                LogMgr.log(1, "RemoteException");
            }
            LogMgr.log(5, "%s", "999");
        }
    }
}
