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
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.MfcListener;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PushSegment;
import com.felicanetworks.mfc.ServiceUtil;
import com.felicanetworks.mfc.felica.Felica;
import com.felicanetworks.mfc.felica.offlineimpl.ChipController;
import com.felicanetworks.mfc.felica.offlineimpl.FelicaRfController;
import com.felicanetworks.mfc.felica.offlineimpl.OfflineException;
import com.felicanetworks.mfc.felica.offlineimpl.SystemInfo;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaRf extends Felica {
    private static FelicaRf sInstance;
    private String mAppIdentifyCode;
    protected LocalDeathRecipient mDeathRecipient;
    protected Felica.FelicaAppInfo mOpenedApp;

    protected FelicaRf() {
        initChipController();
    }

    public static synchronized FelicaRf getInstance() {
        if (sInstance == null) {
            sInstance = new FelicaRf();
        }
        return sInstance;
    }

    protected void initChipController() {
        this.mChipController = new FelicaRfController();
    }

    public ChipController getChipController() {
        return this.mChipController;
    }

    public synchronized void open(IBinder iBinder) throws FelicaException {
        LogMgr.log(4, "%s", "000");
        open(true);
        LogMgr.log(4, "%s", "001");
        this.mOpenedApp = new Felica.FelicaAppInfo();
        this.mOpenedApp.setPid(Binder.getCallingPid());
        this.mOpenedApp.setUid(Binder.getCallingUid());
        registerBinder(iBinder);
    }

    private synchronized void open(boolean z) throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (z && this.mOpenedApp != null) {
            if (this.mOpenedApp.getPid() != Binder.getCallingPid() || this.mOpenedApp.getUid() != Binder.getCallingUid()) {
                throw new FelicaException(2, 39, new AppInfo(this.mOpenedApp.getPid()));
            }
        } else {
            checkNotOnline();
            if (this.mOpened) {
                return;
            }
            super.doOpen();
            this.mOpened = true;
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void close() throws FelicaException {
        doClose(true);
        LogMgr.log(4, "%s", "999");
    }

    public synchronized void doClose(boolean z) throws FelicaException {
        LogMgr.log(4, "%s, isCheckProcess = %s, callingPid=%d, callingUid=%d", "000", Boolean.valueOf(z), Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(Binder.getCallingUid()));
        if (this.mOpened) {
            if (z) {
                checkOpenedApp();
            }
            if (this.mFscAdapter != null) {
                LogMgr.log(2, "%s, force stop online", "701");
                this.mFscAdapter.kill();
            }
            this.mChipController.close();
            super.doClose();
            this.mFscAdapter = null;
            this.mAppIdentifyCode = null;
            this.mOpenedApp = null;
            unregisterBinder();
            LogMgr.log(4, "%s", "999");
        }
    }

    public synchronized void reset() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        try {
            this.mChipController.reset();
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
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void select(int i) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d", "000", Integer.valueOf(i));
        checkOpenedApp();
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(i));
            throw new IllegalArgumentException("The specified System Code is out of range.");
        }
        checkOpened();
        checkNotOnline();
        int iCurrentTimeMillis = this.mSelectTimeout;
        if (!isConnected()) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            try {
                this.mChipController.connect(iCurrentTimeMillis);
                iCurrentTimeMillis -= (int) (System.currentTimeMillis() - jCurrentTimeMillis);
                if (iCurrentTimeMillis <= 0) {
                    try {
                        this.mChipController.disconnect();
                    } catch (Exception unused) {
                    }
                    throw new FelicaException(3, 7);
                }
                try {
                    SystemInfo systemInfoPolling = this.mChipController.polling(i, iCurrentTimeMillis, 0, (byte) 1);
                    this.mSelected = true;
                    this.mSystemInfo = systemInfoPolling;
                } catch (OfflineException e) {
                    LogMgr.log(1, "%s OfflineException.TYPE_SELECT_FAILED", "801");
                    if (!this.mSelected) {
                        try {
                            this.mChipController.disconnect();
                        } catch (Exception unused2) {
                        }
                    }
                    throw convException(e, 9);
                } catch (Exception unused3) {
                    LogMgr.log(1, "%s Exception", "804");
                    if (!this.mSelected) {
                        try {
                            this.mChipController.disconnect();
                        } catch (Exception unused4) {
                        }
                    }
                    throw new FelicaException(1, 9);
                }
            } catch (OfflineException e2) {
                if (e2.getType() == 5) {
                    throw makeRWStopMsgFelicaException(e2.getMessage());
                }
                LogMgr.log(1, "%s OfflineException", "802");
                throw convException(e2, 9);
            }
        } else {
            SystemInfo systemInfoPolling2 = this.mChipController.polling(i, iCurrentTimeMillis, 0, (byte) 1);
            this.mSelected = true;
            this.mSystemInfo = systemInfoPolling2;
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int getSystemCode() throws FelicaException {
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getSystemCode();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getIdm() throws FelicaException {
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getIdm();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getIcCode() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getIcCode();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int getKeyVersion(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        int i4;
        LogMgr.log(4, "%s In serviceCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkOpenedApp();
        ServiceUtil.getInstance().checkServiceCode(i, this.mNodeCodeSize);
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
            i4 = this.mChipController.requestService(this.mNodeCodeSize, this.mSystemInfo.getIdm(), new int[]{i}, getTimeout(), getRetryCount())[0];
            if (i4 == 65535) {
                LogMgr.log(1, "%s FelicaException.TYPE_SERVICE_NOT_FOUND", "800");
                throw new FelicaException(4, 11);
            }
        } catch (FelicaException e) {
            LogMgr.log(1, "%s FelicaException", "800");
            throw e;
        } catch (OfflineException e2) {
            if (e2.getType() == 5) {
                throw makeRWStopMsgFelicaException(e2.getMessage());
            }
            LogMgr.log(1, "%s OfflineException.TYPE_GET_KEY_VERSION_FAILED", "801");
            throw convException(e2, 10);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "804");
            throw new FelicaException(1, 10);
        }
        return i4;
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized KeyInformation[] getKeyVersionV2(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCode = %d timeout = %d retryCount = %d", "000", iArr, Integer.valueOf(i), Integer.valueOf(i2));
        checkOpenedApp();
        for (int i3 : iArr) {
            ServiceUtil.getInstance().checkServiceCode(i3, 2);
        }
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
        } catch (OfflineException e) {
            if (e.getType() == 5) {
                throw makeRWStopMsgFelicaException(e.getMessage());
            }
            LogMgr.log(1, "%s OfflineException.TYPE_GET_KEY_VERSION_V2_FAILED", "801");
            throw convException(e, 28, 64, new int[0]);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "804");
            throw new FelicaException(1, 64);
        }
        return this.mChipController.requestServiceV2(this.mSystemInfo.getIdm(), iArr, getTimeout(), getRetryCount());
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getContainerIssueInformation(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkOpenedApp();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
        } catch (OfflineException e) {
            if (e.getType() == 5) {
                throw makeRWStopMsgFelicaException(e.getMessage());
            }
            LogMgr.log(1, "%s OfflineException.TYPE_GET_CONTAINER_ISSUE_INFORMATION_FAILED", "800");
            throw convException(e, 29);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "803");
            throw new FelicaException(1, 29);
        }
        return this.mChipController.getContainerIssueInfo(this.mSystemInfo.getIdm(), getTimeout(), getRetryCount());
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void setNodeCodeSize(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCodeSize = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkOpenedApp();
        if (i != 2 && i != 4) {
            LogMgr.log(1, "%s invalid nodeCodeSize", "800");
            throw new IllegalArgumentException("The specified NodeCodeSize is invalid value.");
        }
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        try {
            this.mChipController.setParameter(this.mSystemInfo.getIdm(), i, getTimeout(), getRetryCount());
            this.mNodeCodeSize = i;
        } catch (OfflineException e) {
            if (e.getType() == 5) {
                throw makeRWStopMsgFelicaException(e.getMessage());
            }
            LogMgr.log(1, "%s OfflineException.TYPE_SET_NODECODESIZE_FAILED", "801");
            throw convException(e, 7, 28, new int[0]);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "805");
            throw new FelicaException(1, 28);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized Data[] read(BlockList blockList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockList = %s timeout = %d retryCount = %d", "000", blockList, Integer.valueOf(i), Integer.valueOf(i2));
        checkOpenedApp();
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
        try {
        } catch (OfflineException e) {
            if (e.getType() == 2) {
                throw new IllegalArgumentException();
            }
            if (e.getType() == 5) {
                throw makeRWStopMsgFelicaException(e.getMessage());
            }
            int[] iArr = {83, 13, 166, 11, 168, 12};
            LogMgr.log(1, "%s OfflineException", "801");
            throw convException(e, 5, 14, iArr);
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "809");
            throw new FelicaException(1, 14);
        }
        return this.mChipController.readWithoutEncryption(this.mNodeCodeSize, this.mSystemInfo.getIdm(), blockList, getTimeout(), getRetryCount());
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void write(BlockDataList blockDataList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockDataList = %s timeout = %d retryCount = %d", "000", blockDataList, Integer.valueOf(i), Integer.valueOf(i2));
        checkOpenedApp();
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
        try {
            this.mChipController.writeWithoutEncryption(this.mNodeCodeSize, this.mSystemInfo.getIdm(), blockDataList, getTimeout(), getRetryCount());
        } catch (OfflineException e) {
            if (e.getType() == 6 && e.getStatusFlag2() == 113 && e.getStatusFlag1() == 255) {
                LogMgr.log(1, "%s OfflineException.TYPE_INVALID_STATUS_FLAG(INTERFACE_WIRELESS)", "813");
            } else {
                if (e.getType() == 2) {
                    throw new IllegalArgumentException();
                }
                if (e.getType() == 5) {
                    throw makeRWStopMsgFelicaException(e.getMessage());
                }
                int[] iArr = {1, 15, 2, 16, 80, 17, 81, 18, 82, 19, 83, 13, 165, 21, 166, 11, 168, 12};
                LogMgr.log(1, "%s OfflineException", "801");
                throw convException(e, 6, 20, iArr);
            }
        } catch (Exception unused) {
            LogMgr.log(1, "%s Exception", "815");
            throw new FelicaException(1, 20);
        }
    }

    protected void checkNotOnline() throws FelicaException {
        if (this.mFscAdapter != null) {
            throw new FelicaException(2, 2);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int[] getSystemCodeList(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkOpenedApp();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getSystemCodeList(i, i2);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized NodeInformation getNodeInformation(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        checkOpenedApp();
        ServiceUtil.getInstance().checkAreaCode(i);
        setTimeout(i2);
        setRetryCount(i3);
        checkOpened();
        checkNotOnline();
        checkSelected();
        return super.getNodeInformation(i, i2, i3);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized BlockCountInformation[] getBlockCountInformation(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        checkOpenedApp();
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
        return super.getBlockCountInformation(iArr, i, i2);
    }

    public synchronized boolean getRfsState() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        checkOpenedApp();
        checkNotOnline();
        throw new FelicaException(1, 51);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized byte[] getContainerId(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        checkOpenedApp();
        setTimeout(i);
        setRetryCount(i2);
        checkOpened();
        checkNotOnline();
        return super.getContainerId(i, i2);
    }

    public synchronized void push(PushSegment pushSegment) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In pushSegment = %s", "000", pushSegment);
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        throw new FelicaException(1, 40);
    }

    public synchronized void setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In Listener = %s appIdentificationCode = %s", "000", iFelicaPushAppNotificationListener, str);
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        throw new FelicaException(1, 47);
    }

    public synchronized String getAppIdentificationCode() {
        return this.mAppIdentifyCode;
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    protected FelicaException convException(OfflineException offlineException, int i) {
        return convException(offlineException, 1, i, null);
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    protected FelicaException convException(OfflineException offlineException, int i, int i2, int[] iArr) {
        LogMgr.log(4, "In oe = %s, id = %d, type = %d, supportedCode = %d", offlineException, Integer.valueOf(i), Integer.valueOf(i2), iArr);
        switch (offlineException.getType()) {
            case 4:
                return new FelicaException(3, 6, offlineException.getMessage());
            case 5:
                return makeRWStopMsgFelicaException(offlineException.getMessage());
            case 6:
                if (iArr != null) {
                    return new FelicaException(i, i2, offlineException.getStatusFlag1(), offlineException.getStatusFlag2());
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
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void setMfcListener(MfcListener mfcListener) {
        if (mfcListener != null) {
            this.mMfcListener = mfcListener;
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized void setSelectTimeout(int i) throws FelicaException {
        doSetSelectTimeout(i, true);
    }

    public synchronized void doSetSelectTimeout(int i, boolean z) throws FelicaException {
        if (z) {
            checkOpenedApp();
            super.setSelectTimeout(i);
        } else {
            super.setSelectTimeout(i);
        }
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public synchronized int getSelectTimeout() throws FelicaException {
        checkOpenedApp();
        return super.getSelectTimeout();
    }

    @Override // com.felicanetworks.mfc.felica.Felica
    public void cancelOffline() throws FelicaException {
        doCancelOffline(true);
    }

    public void doCancelOffline(boolean z) throws FelicaException {
        if (z) {
            checkOpenedApp();
        }
        try {
            checkOpenedNosync();
            checkNotOnline();
            super.cancelOffline();
        } catch (Exception unused) {
        }
    }

    public synchronized boolean isOpen() {
        return this.mOpened;
    }

    public synchronized void resetInner() throws FelicaException {
        if (Binder.getCallingPid() != Process.myPid()) {
            throw new FelicaException(1, 47);
        }
        checkOpened();
        super.resetInner(false, false, true);
    }

    public void cancelOfflineInner() throws FelicaException {
        try {
            checkOpenedApp();
        } catch (FelicaException unused) {
            if (Binder.getCallingPid() != Process.myPid()) {
                throw new FelicaException(1, 47);
            }
        }
        checkOpenedNosync();
        super.cancelOffline();
    }

    protected synchronized boolean registerBinder(IBinder iBinder) {
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

    protected synchronized void unregisterBinder() {
        IBinder binder;
        LogMgr.log(3, "%s", "000");
        if (this.mDeathRecipient != null && (binder = this.mDeathRecipient.getBinder()) != null) {
            binder.unlinkToDeath(this.mDeathRecipient, 0);
        }
        this.mDeathRecipient = null;
    }

    protected class LocalDeathRecipient implements IBinder.DeathRecipient {
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
            synchronized (FelicaRf.this) {
                FelicaRf.this.mDeathRecipient = null;
            }
            FelicaRf.this.mMfcListener.mfcCancel();
        }
    }

    protected void checkOpenedApp() throws FelicaException {
        checkPidUid();
    }

    public synchronized void checkPidUidSync() throws FelicaException {
        checkPidUid();
    }

    public void checkPidUid() throws FelicaException {
        checkPidUid(Binder.getCallingPid(), Binder.getCallingUid());
    }

    void checkPidUid(int i, int i2) throws FelicaException {
        Felica.FelicaAppInfo felicaAppInfo = this.mOpenedApp;
        if (felicaAppInfo == null) {
            LogMgr.log(1, "%s openedApp == null", "800");
            throw new FelicaException(2, 1);
        }
        try {
            if (felicaAppInfo.getPid() == i && this.mOpenedApp.getUid() == i2) {
                return;
            }
            LogMgr.log(1, "%s openedApp.getPID() = %d, openedApp.getUID() = %d", "801", Integer.valueOf(this.mOpenedApp.getPid()), Integer.valueOf(this.mOpenedApp.getUid()));
            throw new FelicaException(2, 1);
        } catch (NullPointerException unused) {
            LogMgr.log(1, "%s NullPointerException", "802");
            throw new FelicaException(2, 1);
        }
    }

    public synchronized void checkStatus() throws FelicaException {
        checkOpenedApp();
        checkOpened();
        checkNotOnline();
        checkSelected();
    }

    private FelicaException makeRWStopMsgFelicaException(String str) {
        if (str != null && str.equalsIgnoreCase(FeliCaChipException.FELICA_CHIP_RW_STOP_MESSAGE)) {
            return new FelicaException(3, 7, FelicaException.RW_STOP_MESSAGE);
        }
        return new FelicaException(3, 7);
    }
}
