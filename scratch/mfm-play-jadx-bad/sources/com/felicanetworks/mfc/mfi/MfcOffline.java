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

/* JADX INFO: loaded from: classes3.dex */
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

    synchronized void activateFelica(String callerPackageName, IFelicaEventListener listener, IBinder binder) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In callerPackageName = %s, listener = %s", "000", callerPackageName, listener);
        this.mFelicaWrapper.activateFelica(callerPackageName, listener, binder);
        LogMgr.log(4, "%s", "999");
    }

    synchronized void inactivateFelica() throws FelicaException {
        LogMgr.log(4, "%s", "000");
        doInactivateFelica(true);
    }

    synchronized void doInactivateFelica(boolean isCheckProcess) throws FelicaException {
        LogMgr.log(4, "%s In isCheckProcess = %s", "000", Boolean.valueOf(isCheckProcess));
        if (isCheckProcess) {
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

    synchronized void open(boolean checkActivate) throws FelicaException {
        LogMgr.log(4, "%s", "000");
        if (checkActivate) {
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

    synchronized void select(int target, int systemCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In systemCode = %d", "000", Integer.valueOf(systemCode));
        if (target != 0 && target != 1) {
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_TARGET);
        }
        if (target == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (systemCode < 0 || systemCode > 65535) {
            LogMgr.log(1, "%s systemCode = %d", "800", Integer.valueOf(systemCode));
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        if (target == 0 && (systemCode == 65535 || (systemCode & 255) == 255 || (systemCode & 65280) == 65280)) {
            LogMgr.log(1, "%s systemCode = %d", "805", Integer.valueOf(systemCode));
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.select(target, systemCode);
    }

    synchronized void select(int systemCode, String cid) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "000 In systemCode = " + systemCode);
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (systemCode < 0 || systemCode > 65535) {
            LogMgr.log(1, "800 systemCode = " + systemCode);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        if (systemCode == 65535 || (systemCode & 255) == 255 || (systemCode & 65280) == 65280) {
            LogMgr.log(1, "805 systemCode = " + systemCode);
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_SYSTEM_CODE);
        }
        if (cid == null || cid.length() != 63 || cid.equals(FelicaConst.INVALID_CID_0) || cid.equalsIgnoreCase(FelicaConst.INVALID_CID_F)) {
            LogMgr.log(1, "800");
            throw new IllegalArgumentException(FelicaConst.EXC_INVALID_CID);
        }
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.select(systemCode, cid);
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

    synchronized int getKeyVersion(int serviceCode, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In serviceCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(serviceCode), Integer.valueOf(timeout), Integer.valueOf(retryCount));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        ServiceUtil.getInstance().checkServiceCode(serviceCode, this.mFelicaWrapper.getNodeCodeSize());
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getKeyVersion(serviceCode);
    }

    synchronized KeyInformation[] getKeyVersionV2(int[] nodeCode, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCode = %d timeout = %d retryCount = %d", "000", nodeCode, Integer.valueOf(timeout), Integer.valueOf(retryCount));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        for (int i : nodeCode) {
            ServiceUtil.getInstance().checkServiceCode(i, 2);
        }
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getKeyVersionV2(nodeCode);
    }

    synchronized byte[] getContainerIssueInformation(int timeout, int retryCount) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(timeout), Integer.valueOf(retryCount));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getContainerIssueInformation();
    }

    synchronized void setNodeCodeSize(int nodeCodeSize, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In nodeCodeSize = %d timeout = %d retryCount = %d", "000", Integer.valueOf(nodeCodeSize), Integer.valueOf(timeout), Integer.valueOf(retryCount));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (nodeCodeSize != 2 && nodeCodeSize != 4) {
            LogMgr.log(1, "%s invalid nodeCodeSize", "800");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_NODECODESIZE);
        }
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        this.mFelicaWrapper.setNodeCodeSize(nodeCodeSize);
    }

    synchronized Data[] read(BlockList blockList, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockList = %s timeout = %d retryCount = %d", "000", blockList, Integer.valueOf(timeout), Integer.valueOf(retryCount));
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
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.read(blockList);
    }

    synchronized void write(BlockDataList blockDataList, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In blockDataList = %s timeout = %d retryCount = %d", "000", blockDataList, Integer.valueOf(timeout), Integer.valueOf(retryCount));
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
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
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

    synchronized int[] getSystemCodeList(int timeout, int retryCount) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(timeout), Integer.valueOf(retryCount));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getSystemCodeList();
    }

    synchronized NodeInformation getNodeInformation(int parentAreaCode, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(parentAreaCode), Integer.valueOf(timeout), Integer.valueOf(retryCount));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        ServiceUtil.getInstance().checkAreaCode(parentAreaCode);
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getNodeInformation(parentAreaCode);
    }

    synchronized NodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In parentAreaCode = %d timeout = %d retryCount = %d", "000", Integer.valueOf(parentAreaCode), Integer.valueOf(timeout), Integer.valueOf(retryCount));
        this.mFelicaWrapper.checkInterfaceWired();
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        ServiceUtil.getInstance().checkAreaCode(parentAreaCode);
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getPrivacyNodeInformation(parentAreaCode);
    }

    synchronized BlockCountInformation[] getBlockCountInformation(int[] nodeCodeList, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        if (nodeCodeList == null || nodeCodeList.length < 1 || nodeCodeList.length > 32) {
            LogMgr.log(1, "%s invalid NodeCodeList", "800");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        for (int i : nodeCodeList) {
            ServiceUtil.getInstance().checkServiceCode(i, this.mFelicaWrapper.getNodeCodeSize());
        }
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        return this.mFelicaWrapper.getBlockCountInformation(nodeCodeList);
    }

    public synchronized void setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        this.mFelicaWrapper.checkInterfaceWired();
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (privacySettingData == null || privacySettingData.length < 1 || privacySettingData.length > 15) {
            LogMgr.log(1, "%s invalid privacySettingData", "800");
            throw new IllegalArgumentException("The specified parameter is invalid.");
        }
        for (PrivacySettingData privacySettingData2 : privacySettingData) {
            privacySettingData2.checkFormat();
        }
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        this.mFelicaWrapper.checkSelected();
        this.mFelicaWrapper.setPrivacy(privacySettingData);
    }

    synchronized byte[] getContainerId(int timeout, int retryCount) throws FelicaException {
        LogMgr.log(4, "%s In timeout = %d retryCount = %d", "000", Integer.valueOf(timeout), Integer.valueOf(retryCount));
        if (this.mFelicaWrapper.getTarget() == 0) {
            this.mFelicaWrapper.checkActivated();
        } else {
            this.mFelicaWrapper.checkOpenedApp();
        }
        this.mFelicaWrapper.checkNotLoggedIn();
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
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

    synchronized void setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws FelicaException, IllegalArgumentException {
        LogMgr.log(4, "%s In Listener = %s appIdentificationCode = %s", "000", listener, appIdentificationCode);
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

    synchronized void setSelectTimeout(int timeout) throws FelicaException {
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.setSelectTimeout(timeout);
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

    synchronized byte[] executeFelicaCommand(byte[] commandPacket, int timeout, int retryCount) throws FelicaException, IllegalArgumentException {
        this.mFelicaWrapper.checkInterfaceWired();
        this.mFelicaWrapper.checkActivated();
        this.mFelicaWrapper.checkNotLoggedIn();
        if (commandPacket == null || commandPacket.length <= 0 || commandPacket.length > 254) {
            LogMgr.log(1, "invalid Command");
            throw new IllegalArgumentException(MfiClientConst.EXC_INVALID_COMMAND);
        }
        this.mFelicaWrapper.setTimeout(timeout);
        this.mFelicaWrapper.setRetryCount(retryCount);
        this.mFelicaWrapper.checkOpened();
        this.mFelicaWrapper.checkNotOnline();
        return this.mFelicaWrapper.executeFelicaCommand(commandPacket);
    }
}
