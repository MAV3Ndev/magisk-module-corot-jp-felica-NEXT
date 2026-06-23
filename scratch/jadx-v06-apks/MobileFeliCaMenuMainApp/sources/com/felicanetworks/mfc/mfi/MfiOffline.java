package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes.dex */
class MfiOffline {
    private static MfiOffline sInstance;
    private FelicaWrapper mFelicaWrapper;
    private GpController mGpController = null;
    private final Object mCanceledLock = new Object();
    private final AtomicBoolean mIsCanceled = new AtomicBoolean(false);
    private final Object mGpControllerLock = new Object();
    private final LocalPartialCardInfoChecker mLocalPartialCardInfoChecker = new LocalPartialCardInfoChecker();

    private MfiOffline() {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999");
    }

    static synchronized MfiOffline getInstance() {
        LogMgr.log(4, "000");
        if (sInstance == null) {
            LogMgr.log(4, "001");
            sInstance = new MfiOffline();
        }
        LogMgr.log(4, "999");
        return sInstance;
    }

    void setFelicaWrapper(FelicaWrapper felicaWrapper) {
        LogMgr.log(4, "000");
        this.mFelicaWrapper = felicaWrapper;
        LogMgr.log(4, "999");
    }

    private void setGpController(GpController gpController, int i) throws FelicaException {
        synchronized (this.mGpControllerLock) {
            if (this.mGpController == null) {
                this.mGpController = gpController;
            } else {
                throw new MfiClientException(1, i, null);
            }
        }
    }

    private void resetGpController() {
        synchronized (this.mGpControllerLock) {
            this.mGpController = null;
        }
    }

    private GpController getGpController() {
        GpController gpController;
        synchronized (this.mGpControllerLock) {
            gpController = this.mGpController;
        }
        return gpController;
    }

    int getUnsupportMfiService1CardPosition() throws FelicaException {
        MfiClientException mfiClientException;
        int unsupportMfiService1CardPosition;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                this.mFelicaWrapper.checkNotOpened();
                checkNotRunningTask();
                this.mFelicaWrapper.checkAccessRight(14);
                this.mFelicaWrapper.checkAccessRight(49);
                if (Property.isChipGP()) {
                    GpController gpController = new GpController();
                    FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
                    try {
                        try {
                            try {
                                setGpController(gpController, MfiClientException.TYPE_GET_UNSUPPORT_MFI_SERVICE_1_CARD_POSITION_FAILED);
                                checkCancel();
                                gpController.init(felicaAdapter);
                                unsupportMfiService1CardPosition = new AppletManager(gpController).getUnsupportMfiService1CardPosition();
                                checkCancel();
                            } catch (GpException e) {
                                LogMgr.log(2, "700 : Catch GpException message =" + e.getMessage());
                                if (e.getType() == 225) {
                                    mfiClientException = new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, null);
                                } else if (e.getType() == 167) {
                                    mfiClientException = new MfiClientException(104, MfiClientException.TYPE_CARD_NOT_FOUND, null);
                                } else {
                                    mfiClientException = new MfiClientException(1, MfiClientException.TYPE_GET_UNSUPPORT_MFI_SERVICE_1_CARD_POSITION_FAILED, null);
                                }
                                gpController.close();
                                resetGpController();
                                LogMgr.log(4, "999");
                                throw mfiClientException;
                            }
                        } catch (InterruptedException unused) {
                            mfiClientException = new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
                            gpController.close();
                            resetGpController();
                            LogMgr.log(4, "999");
                            throw mfiClientException;
                        }
                    } finally {
                        gpController.close();
                        resetGpController();
                    }
                } else {
                    mfiClientException = new MfiClientException(103, MfiClientException.TYPE_NOT_SUPPORTED_CHIP_ERROR, null);
                }
                LogMgr.log(4, "999");
                throw mfiClientException;
            }
        }
        return unsupportMfiService1CardPosition;
    }

    public String[] getLocalCidList() throws FelicaException {
        String[] localCidList;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                this.mFelicaWrapper.checkNotOpened();
                checkNotRunningTask();
                this.mFelicaWrapper.checkAccessRight(14);
                this.mFelicaWrapper.checkAccessRight(50);
                if (Property.isChipGP()) {
                    GpController gpController = new GpController();
                    FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
                    try {
                        try {
                            setGpController(gpController, MfiClientException.TYPE_GET_LOCAL_CID_LIST_FAILED);
                            checkCancel();
                            gpController.init(felicaAdapter);
                            localCidList = new AppletManager(gpController).getLocalCidList();
                            checkCancel();
                        } catch (GpException e) {
                            LogMgr.log(2, "700 : Catch GpException message = " + e.getMessage());
                            if (e.getType() == 225) {
                                throw new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, null);
                            }
                            throw new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_CID_LIST_FAILED, null);
                        } catch (InterruptedException e2) {
                            LogMgr.log(2, "701 : Catch InterruptedException message = " + e2.getMessage());
                            throw new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
                        }
                    } finally {
                        gpController.close();
                        resetGpController();
                    }
                } else {
                    LogMgr.log(2, "702 : Not supported chip.");
                    throw new MfiClientException(103, MfiClientException.TYPE_NOT_SUPPORTED_CHIP_ERROR, null);
                }
            }
        }
        LogMgr.log(4, "999");
        return localCidList;
    }

    void cancelMfiOffline() throws FelicaException {
        LogMgr.log(4, "000");
        this.mFelicaWrapper.checkMfiActivated();
        this.mFelicaWrapper.checkNotOpenedNoSync();
        checkNotRunningTask();
        this.mIsCanceled.compareAndSet(false, true);
        this.mLocalPartialCardInfoChecker.setCancel(true);
        GpController gpController = getGpController();
        if (gpController != null) {
            gpController.cancel();
        }
        synchronized (this.mCanceledLock) {
            this.mIsCanceled.compareAndSet(true, false);
            this.mLocalPartialCardInfoChecker.setCancel(false);
            if (gpController != null) {
                synchronized (gpController) {
                    gpController.clearCancelFlag();
                }
            }
        }
        LogMgr.log(4, "999");
    }

    private void checkCancel() throws FelicaException {
        LogMgr.log(4, "000");
        if (this.mIsCanceled.get()) {
            throw new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
        }
        LogMgr.log(4, "999");
    }

    private void checkNotRunningTask() throws MfiClientException {
        MfiOnline mfiOnline = this.mFelicaWrapper.getMfiOnline();
        if (mfiOnline != null) {
            mfiOnline.checkNotRunningTask();
        }
    }

    String[] getLocalPartialCardInfoList(String str) throws FelicaException, IllegalArgumentException {
        String[] strArr;
        LogMgr.log(4, "000");
        if (str == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!str.matches(MfiClientConst.REGEX_ALPHANUMERIC)) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (str.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                this.mFelicaWrapper.checkNotOpened();
                checkNotRunningTask();
                this.mFelicaWrapper.checkMfiAccessServiceId(str);
                try {
                    String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                    if (StringUtil.versionCompare(mFCVersion, "3.0.0") == -1) {
                        LogMgr.log(2, "704 : MFC Version : " + mFCVersion);
                        throw new MfiClientException(103, MfiClientException.TYPE_NOT_SUPPORTED_CHIP_ERROR, null);
                    }
                    if (FlavorConst.SERVICE_ID_PREFERENCE_MAP.get(str) == null) {
                        LogMgr.log(2, "706 : serviceId : " + str);
                        throw new MfiClientException(105, 171, null);
                    }
                    checkCancel();
                    MfiChipHolder mfiChipHolder = new MfiChipHolder();
                    try {
                        checkPrimaryIssue(mfiChipHolder, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED);
                        if (Property.isChipGP()) {
                            this.mFelicaWrapper.close();
                        }
                        checkCancel();
                        LocalPartialCardInfoJson[] localPartialCardInfoList = this.mLocalPartialCardInfoChecker.getLocalPartialCardInfoList(str, mfiChipHolder);
                        ArrayList arrayList = new ArrayList();
                        strArr = new String[localPartialCardInfoList.length];
                        for (LocalPartialCardInfoJson localPartialCardInfoJson : localPartialCardInfoList) {
                            arrayList.add(localPartialCardInfoJson.toString());
                        }
                        if (!arrayList.isEmpty()) {
                            arrayList.toArray(strArr);
                        }
                    } finally {
                        mfiChipHolder.discard();
                        LogMgr.log(4, "999");
                    }
                } catch (FelicaException e) {
                    LogMgr.log(2, "703 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                    throw new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, null);
                }
            }
        }
        return strArr;
    }

    private void checkPrimaryIssue(MfiChipHolder mfiChipHolder, int i) throws FelicaException {
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
        try {
            mfiFelicaWrapper.open();
            mfiFelicaWrapper.select(65039);
            mfiFelicaWrapper.getContainerIssueInformationWithCheckIssued();
        } catch (MfiFelicaException e) {
            int i2 = 1;
            String str = null;
            int type = e.getType();
            if (type == 6) {
                i2 = 3;
                i = 6;
            } else if (type != 8) {
                if (type != 31) {
                    i = type == 55 ? 55 : 31;
                }
                i2 = 8;
            } else {
                str = FelicaException.NFC_RW_USED_MESSAGE;
                i = 8;
            }
            mfiChipHolder.discard();
            LogMgr.log(2, "702 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
            throw new MfiClientException(i2, i, str);
        }
    }

    int existEmptySlot() throws FelicaException {
        MfiChipHolder mfiChipHolder;
        MfiFelicaWrapper mfiFelicaWrapper;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                try {
                    this.mFelicaWrapper.checkMfiActivated();
                    this.mFelicaWrapper.checkNotOpened();
                    checkNotRunningTask();
                    this.mFelicaWrapper.checkAccessRight(51);
                    checkCancel();
                    mfiChipHolder = new MfiChipHolder();
                    mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
                    int i = MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED;
                    checkPrimaryIssue(mfiChipHolder, MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED);
                    try {
                        checkCancel();
                        if (Property.isChipGP()) {
                            return existEmptySlotForGP(mfiFelicaWrapper, mfiChipHolder);
                        }
                        return existEmptySlotForFaver(mfiFelicaWrapper);
                    } catch (MfiFelicaException e) {
                        int i2 = 1;
                        String str = null;
                        int type = e.getType();
                        if (type == 6) {
                            i2 = 3;
                            i = 6;
                        } else if (type != 8) {
                            if (type == 31) {
                                i = 31;
                            } else if (type == 55) {
                                i = 55;
                            }
                            i2 = 8;
                        } else {
                            str = FelicaException.NFC_RW_USED_MESSAGE;
                            i = 8;
                        }
                        LogMgr.log(2, "710 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                        throw new MfiClientException(i2, i, str);
                    }
                } finally {
                    mfiFelicaWrapper.closeSilently();
                    mfiChipHolder.discard();
                    LogMgr.log(4, "999");
                }
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0063, code lost:
    
        if (r15 != 0) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int existEmptySlotForGP(com.felicanetworks.mfc.mfi.MfiFelicaWrapper r14, com.felicanetworks.mfc.mfi.MfiChipHolder r15) throws com.felicanetworks.mfc.mfi.MfiFelicaException, com.felicanetworks.mfc.FelicaException {
        /*
            Method dump skipped, instruction units count: 284
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.felicanetworks.mfc.mfi.MfiOffline.existEmptySlotForGP(com.felicanetworks.mfc.mfi.MfiFelicaWrapper, com.felicanetworks.mfc.mfi.MfiChipHolder):int");
    }

    private int existEmptySlotForFaver(MfiFelicaWrapper mfiFelicaWrapper) throws MfiFelicaException, FelicaException {
        boolean z;
        boolean z2;
        try {
            mfiFelicaWrapper.open();
            mfiFelicaWrapper.select(65039);
            int[] systemCodeList = mfiFelicaWrapper.getSystemCodeList();
            if (systemCodeList != null) {
                for (int i : systemCodeList) {
                    if (i == 3) {
                        z = true;
                        break;
                    }
                }
                z = false;
            } else {
                z = false;
            }
            if (!z) {
                LogMgr.log(4, "995");
                return 0;
            }
            checkCancel();
            mfiFelicaWrapper.select(3);
            NodeInformation nodeInformation = mfiFelicaWrapper.getNodeInformation(0);
            if (nodeInformation.getAreaInformationList().length == 1 && nodeInformation.getAreaInformationList()[0].getAreaCode() == 0 && nodeInformation.getServiceCodeList().length == 0) {
                checkCancel();
                String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                int[] iArr = {65535, 0};
                checkCancel();
                if (StringUtil.versionCompare(mFCVersion, "3.0.0") == -1) {
                    if (mfiFelicaWrapper.getKeyVersion(iArr[0]) == 3 && mfiFelicaWrapper.getKeyVersion(iArr[1]) == 3) {
                        LogMgr.log(4, "997");
                        return 1;
                    }
                    checkCancel();
                } else {
                    KeyInformation[] keyVersionV2 = mfiFelicaWrapper.getKeyVersionV2(iArr);
                    int length = keyVersionV2.length;
                    int i2 = 0;
                    while (true) {
                        if (i2 >= length) {
                            z2 = true;
                            break;
                        }
                        KeyInformation keyInformation = keyVersionV2[i2];
                        Integer desVersion = keyInformation.getDesVersion();
                        Integer aesVersion = keyInformation.getAesVersion();
                        if (!(desVersion != null && aesVersion != null && desVersion.intValue() == 3 && (aesVersion.intValue() == 3 || aesVersion.intValue() == 4160))) {
                            z2 = false;
                            break;
                        }
                        i2++;
                    }
                    if (z2) {
                        LogMgr.log(4, "998");
                        return 1;
                    }
                    checkCancel();
                    BlockCountInformation[] blockCountInformation = mfiFelicaWrapper.getBlockCountInformation(new int[]{0});
                    if (blockCountInformation.length > 0 && blockCountInformation[0].getAssignedBlocks() < 345) {
                        return 2;
                    }
                }
                checkCancel();
                return 0;
            }
            LogMgr.log(4, "996");
            return 1;
        } catch (FelicaException e) {
            if (e.getType() == 169) {
                throw e;
            }
            LogMgr.log(2, "709 : Catch MfiClientException message =" + e.getMessage());
            throw new MfiClientException(1, MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED, null);
        }
    }
}
