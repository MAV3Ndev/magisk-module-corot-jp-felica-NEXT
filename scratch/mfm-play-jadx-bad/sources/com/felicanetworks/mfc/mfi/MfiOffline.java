package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.KeyInformation;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.mfi.fws.FwsException;
import com.felicanetworks.mfc.mfi.fws.IndividualSpChecker;
import com.felicanetworks.mfc.mfi.mfw.i.fbl.Property;
import com.felicanetworks.mfc.mfi.omapi.AppletManager;
import com.felicanetworks.mfc.mfi.omapi.CrsManager;
import com.felicanetworks.mfc.mfi.omapi.GpController;
import com.felicanetworks.mfc.mfi.omapi.GpException;
import com.felicanetworks.mfc.mfi.omapi.SeAppletInfo;
import com.felicanetworks.mfc.mfi.util.StringUtil;
import com.felicanetworks.mfc.util.LogMgr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes3.dex */
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

    private void setGpController(GpController gpController, int errorType) throws FelicaException {
        synchronized (this.mGpControllerLock) {
            if (this.mGpController == null) {
                this.mGpController = gpController;
            } else {
                throw new MfiClientException(1, errorType, null);
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

    String[] getLocalPartialCardInfoList(String serviceId) throws FelicaException, IllegalArgumentException {
        String[] strArr;
        LogMgr.log(4, "000");
        if (serviceId == null) {
            LogMgr.log(2, "700 serviceId is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (!serviceId.matches("[0-9a-zA-Z]*")) {
            LogMgr.log(2, "701 serviceId involves invalid character.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (serviceId.length() != 8) {
            LogMgr.log(2, "702 serviceId length is invalid.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                this.mFelicaWrapper.checkNotOpened();
                checkNotRunningTask();
                this.mFelicaWrapper.checkMfiAccessServiceId(serviceId);
                try {
                    String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                    if (StringUtil.versionCompare(mFCVersion, MfiClientConst.MFC_VERSION_FAVER3) == -1) {
                        LogMgr.log(2, "704 : MFC Version : " + mFCVersion);
                        throw new MfiClientException(103, MfiClientException.TYPE_NOT_SUPPORTED_CHIP_ERROR, null);
                    }
                    if (FlavorConst.SERVICE_ID_PREFERENCE_MAP_OLD.get(serviceId) == null) {
                        LogMgr.log(2, "706 : serviceId : " + serviceId);
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
                        LocalPartialCardInfoJson[] localPartialCardInfoList = this.mLocalPartialCardInfoChecker.getLocalPartialCardInfoList(serviceId, mfiChipHolder);
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

    String[] getLocalPartialCardInfoList(String[] serviceIdList) throws FelicaException, IllegalArgumentException {
        String[] strArr;
        LogMgr.log(4, "000");
        if (serviceIdList == null) {
            LogMgr.log(2, "700 serviceIdList is null.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        if (serviceIdList.length == 0) {
            LogMgr.log(2, "701 serviceIdList is empty.");
            throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
        }
        HashSet hashSet = new HashSet();
        Collections.addAll(hashSet, serviceIdList);
        String[] strArr2 = (String[]) hashSet.toArray(new String[0]);
        for (String str : strArr2) {
            if (str == null) {
                LogMgr.log(2, "702 serviceId is null.");
                throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
            }
            if (!str.matches("[0-9a-zA-Z]*")) {
                LogMgr.log(2, "703 serviceId involves invalid character.");
                throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
            }
            if (str.length() != 8) {
                LogMgr.log(2, "704 serviceId length is invalid.");
                throw new IllegalArgumentException("The specified ServiceId is null or invalid.");
            }
        }
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                this.mFelicaWrapper.checkNotOpened();
                checkNotRunningTask();
                for (String str2 : strArr2) {
                    this.mFelicaWrapper.checkMfiAccessServiceId(str2);
                }
                try {
                    String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                    if (StringUtil.versionCompare(mFCVersion, MfiClientConst.MFC_VERSION_FAVER3) == -1) {
                        LogMgr.log(2, "706 : MFC Version : " + mFCVersion);
                        throw new MfiClientException(103, MfiClientException.TYPE_NOT_SUPPORTED_CHIP_ERROR, null);
                    }
                    for (String str3 : strArr2) {
                        if (FlavorConst.SERVICE_ID_PREFERENCE_MAP.get(str3) == null) {
                            LogMgr.log(2, "707 : serviceId : " + str3);
                            throw new MfiClientException(105, 171, null);
                        }
                    }
                    checkCancel();
                    MfiChipHolder mfiChipHolder = new MfiChipHolder();
                    try {
                        checkPrimaryIssue(mfiChipHolder, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED);
                        if (Property.isChipGP()) {
                            this.mFelicaWrapper.close();
                        }
                        checkCancel();
                        LocalPartialCardInfoJson[] localPartialCardInfoList = this.mLocalPartialCardInfoChecker.getLocalPartialCardInfoList(strArr2, mfiChipHolder);
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
                    LogMgr.log(2, "705 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                    throw new MfiClientException(1, MfiClientException.TYPE_GET_LOCAL_PARTIAL_CARD_INFO_LIST_FAILED, null);
                }
            }
        }
        return strArr;
    }

    private void checkPrimaryIssue(MfiChipHolder chipHolder, int errorType) throws FelicaException {
        int i;
        MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(chipHolder);
        try {
            mfiFelicaWrapper.open();
            mfiFelicaWrapper.select(65039);
            if (errorType == 174) {
                mfiFelicaWrapper.getContainerIssueInformationWithCheckIssuedDetail();
            } else {
                mfiFelicaWrapper.getContainerIssueInformationWithCheckIssued();
            }
        } catch (MfiFelicaException e) {
            int type = e.getType();
            String str = null;
            if (type != 6) {
                i = 8;
                if (type != 8) {
                    int i2 = 31;
                    if (type != 31) {
                        i2 = 55;
                        if (type != 55) {
                            i = 106;
                            i2 = 177;
                            if (type != 177) {
                                i2 = 178;
                                if (type != 178) {
                                }
                            }
                        }
                    }
                    errorType = i2;
                } else {
                    str = FelicaException.NFC_RW_USED_MESSAGE;
                    errorType = 8;
                }
                i = 1;
            } else {
                i = 3;
                errorType = 6;
            }
            chipHolder.discard();
            LogMgr.log(2, "702 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
            throw new MfiClientException(i, errorType, str);
        }
    }

    int existEmptySlot() throws FelicaException {
        MfiChipHolder mfiChipHolder;
        MfiFelicaWrapper mfiFelicaWrapper;
        int i;
        int iExistEmptySlotForFaver;
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
                    int i2 = MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED;
                    checkPrimaryIssue(mfiChipHolder, MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED);
                    try {
                        checkCancel();
                        if (Property.isChipGP()) {
                            iExistEmptySlotForFaver = existEmptySlotForGP(mfiFelicaWrapper, mfiChipHolder);
                        } else {
                            iExistEmptySlotForFaver = existEmptySlotForFaver(mfiFelicaWrapper);
                            mfiFelicaWrapper.closeSilently();
                            mfiChipHolder.discard();
                            LogMgr.log(4, "999");
                        }
                    } catch (MfiFelicaException e) {
                        int type = e.getType();
                        String str = null;
                        if (type != 6) {
                            i = 8;
                            if (type != 8) {
                                int i3 = 31;
                                if (type != 31) {
                                    i3 = 55;
                                    if (type != 55) {
                                    }
                                }
                                i2 = i3;
                            } else {
                                str = FelicaException.NFC_RW_USED_MESSAGE;
                                i2 = 8;
                            }
                            i = 1;
                        } else {
                            i = 3;
                            i2 = 6;
                        }
                        LogMgr.log(2, "710 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                        throw new MfiClientException(i, i2, str);
                    }
                } finally {
                    mfiFelicaWrapper.closeSilently();
                    mfiChipHolder.discard();
                    LogMgr.log(4, "999");
                }
            }
        }
        return iExistEmptySlotForFaver;
    }

    /* JADX WARN: Code restructure failed: missing block: B:9:0x0069, code lost:
    
        if (r6 != 0) goto L10;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private int existEmptySlotForGP(MfiFelicaWrapper felica, MfiChipHolder chipHolder) throws MfiFelicaException, FelicaException {
        felica.close();
        this.mFelicaWrapper.close();
        GpController gpController = new GpController();
        FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
        int i = 6;
        int i2 = 3;
        String str = null;
        try {
            try {
                try {
                    setGpController(gpController, MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED);
                    checkCancel();
                    gpController.init(felicaAdapter);
                    AppletManager appletManager = new AppletManager(gpController);
                    if (appletManager.getEmptyInstanceAid() == null) {
                        checkCancel();
                        int size = appletManager.getPersonalizedNoCidInstanceInfoList(3).size();
                        if (size == 0) {
                            LogMgr.log(4, "898");
                        } else if (size == 1) {
                            checkCancel();
                            gpController.close();
                            int iIdentifyService = IndividualSpChecker.identifyService(chipHolder, 3, 72, 74, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE1, FlavorConst.INDIVIDUAL_SP_NODE_CODE_SERVICE2);
                            LogMgr.log(6, "002 identifyService=" + iIdentifyService);
                        } else {
                            throw new MfiClientException(1, MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED, null);
                        }
                        return 1;
                    }
                    checkCancel();
                    gpController.close();
                    resetGpController();
                    return 0;
                } catch (FwsException e) {
                    int type = e.getType();
                    if (type != 6) {
                        if (type != 8) {
                            int i3 = 31;
                            if (type != 31) {
                                i3 = 55;
                                i = type != 55 ? 173 : 8;
                            }
                            i2 = 8;
                            i = i3;
                        } else {
                            str = FelicaException.NFC_RW_USED_MESSAGE;
                        }
                        i2 = 1;
                    }
                    LogMgr.log(2, "703 : Catch FwsException message = " + str);
                    throw new MfiClientException(i2, i, str);
                }
            } catch (GpException e2) {
                LogMgr.log(2, "701 : Catch GpException message =" + e2.getMessage());
                if (e2.getType() == 225) {
                    throw new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, null);
                }
                throw new MfiClientException(1, MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED, null);
            } catch (InterruptedException e3) {
                LogMgr.log(2, "702 : Catch InterruptedException message = " + e3.getMessage());
                throw new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
            }
        } finally {
            gpController.close();
            resetGpController();
        }
    }

    private int existEmptySlotForFaver(MfiFelicaWrapper felica) throws MfiFelicaException, FelicaException {
        try {
            felica.open();
            felica.select(65039);
            int[] systemCodeList = felica.getSystemCodeList();
            if (systemCodeList != null) {
                for (int i : systemCodeList) {
                    if (i == 3) {
                        checkCancel();
                        felica.select(3);
                        NodeInformation nodeInformation = felica.getNodeInformation(0);
                        if (nodeInformation.getAreaInformationList().length == 1 && nodeInformation.getAreaInformationList()[0].getAreaCode() == 0 && nodeInformation.getServiceCodeList().length == 0) {
                            checkCancel();
                            String mFCVersion = FelicaWrapper.getMFCVersion(FelicaAdapter.getInstance());
                            int[] iArr = {65535, 0};
                            checkCancel();
                            if (StringUtil.versionCompare(mFCVersion, MfiClientConst.MFC_VERSION_FAVER3) == -1) {
                                if (felica.getKeyVersion(iArr[0]) == 3 && felica.getKeyVersion(iArr[1]) == 3) {
                                    LogMgr.log(4, "997");
                                    return 1;
                                }
                                checkCancel();
                            } else {
                                for (KeyInformation keyInformation : felica.getKeyVersionV2(iArr)) {
                                    Integer desVersion = keyInformation.getDesVersion();
                                    Integer aesVersion = keyInformation.getAesVersion();
                                    if (desVersion == null || aesVersion == null || desVersion.intValue() != 3 || !(aesVersion.intValue() == 3 || aesVersion.intValue() == 4160)) {
                                        checkCancel();
                                        BlockCountInformation[] blockCountInformation = felica.getBlockCountInformation(new int[]{0});
                                        if (blockCountInformation.length > 0 && blockCountInformation[0].getAssignedBlocks() < 345) {
                                            return 2;
                                        }
                                    }
                                }
                                LogMgr.log(4, "998");
                                return 1;
                            }
                            checkCancel();
                            return 0;
                        }
                        LogMgr.log(4, "996");
                        return 1;
                    }
                }
            }
            LogMgr.log(4, "995");
            UnsupportedMfiService1CardCache.getInstance().cacheNotExistUnsupportedMfiService1Card();
            return 0;
        } catch (FelicaException e) {
            if (e.getType() == 169) {
                throw e;
            }
            LogMgr.log(2, "709 : Catch MfiClientException message =" + e.getMessage());
            throw new MfiClientException(1, MfiClientException.TYPE_EXIST_EMPTY_SLOT_FAILED, null);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Repeating lines: [1445=4, 1446=4, 1447=4] */
    boolean existService() throws FelicaException {
        int i;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                this.mFelicaWrapper.checkNotOpened();
                checkNotRunningTask();
                int i2 = 6;
                this.mFelicaWrapper.checkAccessRight(6);
                checkCancel();
                MfiChipHolder mfiChipHolder = new MfiChipHolder();
                MfiFelicaWrapper mfiFelicaWrapper = new MfiFelicaWrapper(mfiChipHolder);
                checkPrimaryIssue(mfiChipHolder, MfiClientException.TYPE_EXIST_SERVICE_FAILED);
                try {
                    try {
                        try {
                            checkCancel();
                            for (int i3 : mfiFelicaWrapper.getSystemCodeList()) {
                                checkCancel();
                                if (i3 != 65039) {
                                    mfiFelicaWrapper.select(i3);
                                    NodeInformation nodeInformation = mfiFelicaWrapper.getNodeInformation(0);
                                    if (nodeInformation.getAreaInformationList().length == 1 && nodeInformation.getAreaInformationList()[0].getAreaCode() == 0 && nodeInformation.getServiceCodeList().length == 0) {
                                    }
                                    mfiFelicaWrapper.close();
                                    break;
                                }
                            }
                            mfiFelicaWrapper.close();
                            checkCancel();
                            if (Property.isChipGP()) {
                                if (getLocalCidListForExistService().length != 0) {
                                    mfiFelicaWrapper.closeSilently();
                                    mfiChipHolder.discard();
                                    LogMgr.log(4, "999");
                                    return true;
                                }
                                checkCancel();
                            }
                            return false;
                        } catch (MfiFelicaException e) {
                            int type = e.getType();
                            if (type == 6) {
                                i = 3;
                            } else if (type != 225) {
                                i = 1;
                                i2 = MfiClientException.TYPE_EXIST_SERVICE_FAILED;
                            } else {
                                i2 = MfiClientException.TYPE_SE_ACCESS_ERROR;
                                i = 3;
                            }
                            LogMgr.log(2, "701 : " + e.getClass().getSimpleName() + " : " + e.getMessage());
                            throw new MfiClientException(i, i2, null);
                        }
                    } catch (FelicaException e2) {
                        if (e2.getType() == 169) {
                            throw e2;
                        }
                        LogMgr.log(2, "700 : Catch MfiClientException message =" + e2.getMessage());
                        if (e2.getType() == 161) {
                            throw new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, null);
                        }
                        throw new MfiClientException(1, MfiClientException.TYPE_EXIST_SERVICE_FAILED, null);
                    }
                } finally {
                    mfiFelicaWrapper.closeSilently();
                    mfiChipHolder.discard();
                    LogMgr.log(4, "999");
                }
            }
        }
    }

    private String[] getLocalCidListForExistService() throws FelicaException {
        String[] localCidList;
        LogMgr.log(6, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                GpController gpController = new GpController();
                FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
                try {
                    try {
                        try {
                            setGpController(gpController, MfiClientException.TYPE_EXIST_SERVICE_FAILED);
                            checkCancel();
                            gpController.init(felicaAdapter);
                            localCidList = new AppletManager(gpController).getLocalCidList();
                            checkCancel();
                        } catch (InterruptedException e) {
                            LogMgr.log(2, "701 : Catch InterruptedException message = " + e.getMessage());
                            throw new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
                        }
                    } catch (GpException e2) {
                        LogMgr.log(2, "700 : Catch GpException message = " + e2.getMessage());
                        if (e2.getType() == 225) {
                            throw new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, null);
                        }
                        throw new MfiClientException(1, MfiClientException.TYPE_EXIST_SERVICE_FAILED, null);
                    }
                } finally {
                    gpController.close();
                    resetGpController();
                }
            }
        }
        LogMgr.log(6, "999");
        return localCidList;
    }

    boolean isChipInitialized() throws FelicaException {
        boolean z;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                checkStartedBy(2);
                checkNotRunningTask();
                MfiChipHolder mfiChipHolder = new MfiChipHolder();
                try {
                    try {
                        checkCancel();
                        checkPrimaryIssue(mfiChipHolder, MfiClientException.TYPE_IS_CHIP_INITIALIZED_FAILED);
                        checkCancel();
                        z = true;
                    } catch (FelicaException e) {
                        if (e.getType() == 31) {
                            checkCancel();
                            mfiChipHolder.discard();
                            z = false;
                        } else {
                            LogMgr.log(2, "700 : " + e.getClass().getSimpleName() + ":" + e.getMessage());
                            LogMgr.printStackTrace(7, e);
                            throw e;
                        }
                    }
                } finally {
                    mfiChipHolder.discard();
                }
            }
        }
        LogMgr.log(4, "999");
        return z;
    }

    private void checkStartedBy(int condition) throws MfiClientException {
        MfiOnline mfiOnline = this.mFelicaWrapper.getMfiOnline();
        if (mfiOnline == null) {
            throw new MfiClientException(2, MfiClientException.TYPE_MFICLIENT_NOT_STARTED, null);
        }
        mfiOnline.checkStartedBy(condition);
    }

    boolean checkAndRecoverCrsState() throws FelicaException {
        boolean zCheckAndRecoverCrsState;
        LogMgr.log(4, "000");
        synchronized (this.mFelicaWrapper) {
            synchronized (this.mCanceledLock) {
                this.mFelicaWrapper.checkMfiActivated();
                checkStartedBy(2);
                checkNotRunningTask();
                if (Property.isChipGP()) {
                    GpController gpController = new GpController();
                    FelicaAdapter felicaAdapter = FelicaAdapter.getInstance();
                    try {
                        try {
                            setGpController(gpController, MfiClientException.TYPE_CHECK_AND_RECOVER_CRS_STATE_FAILED);
                            checkCancel();
                            gpController.init(felicaAdapter);
                            if (!((SeAppletInfo) new AppletManager(gpController).getAppletInfo(1)).isPersonalized()) {
                                LogMgr.log(2, "700 Chip is not formatting.");
                                throw new MfiClientException(107, 31, null);
                            }
                            zCheckAndRecoverCrsState = new CrsManager(gpController).checkAndRecoverCrsState();
                            checkCancel();
                        } catch (GpException e) {
                            LogMgr.log(2, "701 : Catch GpException message = " + e.getMessage());
                            if (e.getType() == 225) {
                                throw new MfiClientException(3, MfiClientException.TYPE_SE_ACCESS_ERROR, null);
                            }
                            throw new MfiClientException(1, MfiClientException.TYPE_CHECK_AND_RECOVER_CRS_STATE_FAILED, null);
                        } catch (InterruptedException e2) {
                            LogMgr.log(2, "702 : Catch InterruptedException message = " + e2.getMessage());
                            throw new MfiClientException(3, MfiClientException.TYPE_MFI_OFFLINE_CANCELED, null);
                        }
                    } finally {
                        gpController.close();
                        resetGpController();
                    }
                } else {
                    zCheckAndRecoverCrsState = false;
                }
            }
        }
        LogMgr.log(4, "999");
        return zCheckAndRecoverCrsState;
    }
}
