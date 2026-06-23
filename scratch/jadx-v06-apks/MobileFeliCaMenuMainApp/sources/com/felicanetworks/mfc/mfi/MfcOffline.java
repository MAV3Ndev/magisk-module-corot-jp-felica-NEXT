package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PushSegment;
import com.felicanetworks.mfc.ServiceUtil;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
class MfcOffline {
    private static MfcOffline sInstance;
    private FelicaWrapper mFelicaWrapper;

    private MfcOffline() {
    }

    static synchronized MfcOffline getInstance() {
        if (sInstance == null) {
            sInstance = new MfcOffline();
        }
        return sInstance;
    }

    void setFelicaWrapper(FelicaWrapper felicaWrapper) {
        this.mFelicaWrapper = felicaWrapper;
    }

    synchronized void activateFelica(String str, IFelicaEventListener iFelicaEventListener, IBinder iBinder) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In callerPackageName = %s, listener = %s", "000", str, iFelicaEventListener);
        this.mFelicaWrapper.activateFelica(str, iFelicaEventListener, iBinder);
        LogMgr.log(4, "%s", "999");
    }

    synchronized void inactivateFelica() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        doInactivateFelica(true);
    }

    synchronized void doInactivateFelica(boolean z) throws FelicaException {
        LogMgr.log(4, "%s In isCheckProcess = %s", "000", Boolean.valueOf(z));
        if (z) {
            this.mFelicaWrapper.checkPidUid();
            this.mFelicaWrapper.checkNotLoggedIn();
        }
        if (this.mFelicaWrapper.isOpened()) {
            LogMgr.log(1, "%s opened == true", "800");
            throw new FelicaException(2, 37);
        }
        this.mFelicaWrapper.inactivateFelica();
        LogMgr.log(4, "%s", "999");
    }

    synchronized void open() throws FelicaException {
        open(true);
    }

    synchronized void open(boolean z) throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (z) {
            this.mFelicaWrapper.checkActivated();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.checkNotOnline();
        if (this.mFelicaWrapper.isOpened()) {
            LogMgr.log(4, "%s", "001");
        } else {
            this.mFelicaWrapper.open();
        }
    }

    synchronized void close() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (this.mFelicaWrapper.isOpened()) {
            this.mFelicaWrapper.close();
            LogMgr.log(4, "%s", "999");
        }
    }

    synchronized void doClose() throws FelicaException {
        LogMgr.log(4, "%s, callingPid=%d, callingUid=%d", "000", Integer.valueOf(Binder.getCallingPid()), Integer.valueOf(Binder.getCallingUid()));
        if (this.mFelicaWrapper.isOpened()) {
            this.mFelicaWrapper.doClose();
            LogMgr.log(4, "%s", "999");
        }
    }

    synchronized void select(int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d", "000", Integer.valueOf(i2));
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_TARGET);
        }
        if (i == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (i2 < 0 || i2 > 65535) {
            LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(i2));
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        if (i == 0 && (i2 == 65535 || (i2 & 255) == 255 || (i2 & 65280) == 65280)) {
            LogMgr.log(1, "%s systemCode = %d", "805", Integer.valueOf(i2));
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.select(i, i2);
    }

    synchronized void select(int i, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000 In systemCode = " + i);
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (i < 0 || i > 65535) {
            LogMgr.log(1, "800 systemCode = " + i);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        if (i == 65535 || (i & 255) == 255 || (i & 65280) == 65280) {
            LogMgr.log(1, "805 systemCode = " + i);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        if (str == null || str.length() != 63 || str.equals(FelicaConst.INVALID_CID_0) || str.equalsIgnoreCase(FelicaConst.INVALID_CID_F)) {
            LogMgr.log(1, "800");
            throw new IllegalArgumentException(FelicaConst.EXC_INVALID_CID);
        }
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.select(i, str);
    }

    synchronized int getSystemCode() throws FelicaException {
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getSystemCode();
    }

    synchronized byte[] getIdm() throws FelicaException {
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getIDm();
    }

    synchronized byte[] getIcCode() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getICCode();
    }

    synchronized int getInterface() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        LogMgr.log(4, "%s", "999");
        return this.mFelicaWrapper.getInterface();
    }

    synchronized int getKeyVersion(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In serviceCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        ServiceUtil.getInstance().checkServiceCode(i, this.mFelicaWrapper.getNodeCodeSize());
        this.mFelicaWrapper.setTimeout(i2);
        this.mFelicaWrapper.setRetryCount(i3);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getKeyVersion(i);
    }

    synchronized KeyInformation[] getKeyVersionV2(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCode = %d timeout = %d retryCount = %d", "000", iArr, Integer.valueOf(i), Integer.valueOf(i2));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        for (int i3 : iArr) {
            ServiceUtil.getInstance().checkServiceCode(i3, 2);
        }
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getKeyVersionV2(iArr);
    }

    synchronized byte[] getContainerIssueInformation(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getContainerIssueInformation();
    }

    synchronized void setNodeCodeSize(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCodeSize = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (i != 2 && i != 4) {
            LogMgr.log(1, "%s invalid nodeCodeSize", "800");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_NODECODESIZE);
        }
        this.mFelicaWrapper.setTimeout(i2);
        this.mFelicaWrapper.setRetryCount(i3);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        this.mFelicaWrapper.setNodeCodeSize(i);
    }

    synchronized Data[] read(BlockList blockList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockList = %s timeout = %d retryCount = %d", "000", blockList, Integer.valueOf(i), Integer.valueOf(i2));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (blockList == null || blockList.size() == 0) {
            LogMgr.log(1, "%s invalid blockList", "800");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_BLOCK_LIST);
        }
        blockList.checkFormat();
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.read(blockList);
    }

    synchronized void write(BlockDataList blockDataList, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockDataList = %s timeout = %d retryCount = %d", "000", blockDataList, Integer.valueOf(i), Integer.valueOf(i2));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (blockDataList == null || blockDataList.size() == 0) {
            LogMgr.log(1, "%s invalid blockDataList", "800");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_BLOCK_DATA_LIST);
        }
        blockDataList.checkFormat();
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        this.mFelicaWrapper.write(blockDataList);
    }

    public synchronized void reset() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.reset();
    }

    synchronized int[] getSystemCodeList(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getSystemCodeList();
    }

    synchronized NodeInformation getNodeInformation(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        ServiceUtil.getInstance().checkAreaCode(i);
        this.mFelicaWrapper.setTimeout(i2);
        this.mFelicaWrapper.setRetryCount(i3);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getNodeInformation(i);
    }

    synchronized NodeInformation getPrivacyNodeInformation(int i, int i2, int i3) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3));
        this.mFelicaWrapper.checkInterfaceWired();
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        ServiceUtil.getInstance().checkAreaCode(i);
        this.mFelicaWrapper.setTimeout(i2);
        this.mFelicaWrapper.setRetryCount(i3);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getPrivacyNodeInformation(i);
    }

    synchronized BlockCountInformation[] getBlockCountInformation(int[] iArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (iArr == null || iArr.length < 1 || iArr.length > 32) {
            LogMgr.log(1, "%s invalid NodeCodeList", "800");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        for (int i3 : iArr) {
            ServiceUtil.getInstance().checkServiceCode(i3, this.mFelicaWrapper.getNodeCodeSize());
        }
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getBlockCountInformation(iArr);
    }

    public synchronized void setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        this.mFelicaWrapper.checkInterfaceWired();
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (privacySettingDataArr == null || privacySettingDataArr.length < 1 || privacySettingDataArr.length > 15) {
            LogMgr.log(1, "%s invalid privacySettingData", "800");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        for (PrivacySettingData privacySettingData : privacySettingDataArr) {
            privacySettingData.checkFormat();
        }
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        this.mFelicaWrapper.setPrivacy(privacySettingDataArr);
    }

    synchronized byte[] getContainerId(int i, int i2) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        return this.mFelicaWrapper.getContainerId();
    }

    synchronized void push(PushSegment pushSegment) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In pushSegment = %s", "000", pushSegment);
        this.mFelicaWrapper.checkOpenedApp();
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        throw new FelicaException(1, 40);
    }

    synchronized void setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In Listener = %s appIdentificationCode = %s", "000", iFelicaPushAppNotificationListener, str);
        this.mFelicaWrapper.checkOpenedApp();
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        throw new FelicaException(1, 47);
    }

    public synchronized boolean getRfsState() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        this.mFelicaWrapper.checkOpenedApp();
        this.mFelicaWrapper.checkNotOnline();
        throw new FelicaException(1, 51);
    }

    synchronized void checkOnlineAccess() throws FelicaException {
        this.mFelicaWrapper.checkPidUid();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (!this.mFelicaWrapper.isOpened()) {
            throw new FelicaException(2, 1);
        }
        this.mFelicaWrapper.checkOnlineAccess();
    }

    synchronized int getSelectTimeout() throws FelicaException {
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        return this.mFelicaWrapper.getSelectTimeout();
    }

    synchronized void setSelectTimeout(int i) throws FelicaException {
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.setSelectTimeout(i);
    }

    void cancelOffline() throws FelicaException {
        this.mFelicaWrapper.checkActivated();
        try {
            this.mFelicaWrapper.checkNotLoggedIn();
            this.mFelicaWrapper.checkOpenedNosync();
            this.mFelicaWrapper.checkNotOnline();
            this.mFelicaWrapper.cancelOffline();
        } catch (Exception unused) {
        }
    }

    synchronized byte[] executeFelicaCommand(byte[] bArr, int i, int i2) throws FelicaException, IllegalArgumentException {
        this.mFelicaWrapper.checkInterfaceWired();
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (bArr == null || bArr.length <= 0 || bArr.length > 254) {
            LogMgr.log(1, "invalid Command");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_COMMAND);
        }
        this.mFelicaWrapper.setTimeout(i);
        this.mFelicaWrapper.setRetryCount(i2);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        return this.mFelicaWrapper.executeFelicaCommand(bArr);
    }
}
