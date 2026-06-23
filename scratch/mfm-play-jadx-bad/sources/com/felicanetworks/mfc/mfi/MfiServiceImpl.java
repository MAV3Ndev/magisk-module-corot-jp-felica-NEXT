package com.felicanetworks.mfc.mfi;

import android.os.IBinder;
import android.os.RemoteException;
import com.felicanetworks.mfc.BlockCountInformation;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.Data;
import com.felicanetworks.mfc.DeviceList;
import com.felicanetworks.mfc.FelicaException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.FelicaResultInfoBlockCountInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoBoolean;
import com.felicanetworks.mfc.FelicaResultInfoByteArray;
import com.felicanetworks.mfc.FelicaResultInfoDataArray;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.FelicaResultInfoIntArray;
import com.felicanetworks.mfc.FelicaResultInfoKeyInformationArray;
import com.felicanetworks.mfc.FelicaResultInfoNodeInformation;
import com.felicanetworks.mfc.IFSCEventListener;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;
import com.felicanetworks.mfc.NodeInformation;
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;
import com.felicanetworks.mfc.mfi.IMfiFelica;
import com.felicanetworks.mfc.mfi.MfiClientExternalLogConst;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class MfiServiceImpl extends IMfiFelica.Stub {
    public static final int INTERFACE_WIRED = 0;
    private static MfiServiceImpl sMe = new MfiServiceImpl();
    private MfcOffline mMfcOffline;
    private MfcOnline mMfcOnline;
    private MfiOffline mMfiOffline;
    private MfiOnline mMfiOnline;

    public static MfiServiceImpl getInstance() {
        return sMe;
    }

    private MfiServiceImpl() {
    }

    public synchronized void init(FelicaWrapper felicaWrapper) {
        LogMgr.log(4, "%s", "000");
        MfcOffline mfcOffline = MfcOffline.getInstance();
        this.mMfcOffline = mfcOffline;
        mfcOffline.setFelicaWrapper(felicaWrapper);
        MfcOnline mfcOnline = MfcOnline.getInstance();
        this.mMfcOnline = mfcOnline;
        mfcOnline.setFelicaWrapper(felicaWrapper);
        MfiOnline mfiOnline = MfiOnline.getInstance();
        this.mMfiOnline = mfiOnline;
        mfiOnline.setFelicaWrapper(felicaWrapper);
        MfiOffline mfiOffline = MfiOffline.getInstance();
        this.mMfiOffline = mfiOffline;
        mfiOffline.setFelicaWrapper(felicaWrapper);
        LogMgr.log(4, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo open() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.open();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            if (pushSegmentParcelableWrapper == null) {
                throw new IllegalArgumentException();
            }
            this.mMfcOffline.push(pushSegmentParcelableWrapper.getPushSegment());
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoDataArray read(BlockList blockList, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            Data[] dataArr = this.mMfcOffline.read(blockList, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoDataArray(dataArr);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoDataArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo reset() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.reset();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo select(int systemCode) throws RemoteException {
        return selectWithTarget(0, systemCode);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo selectWithTarget(int target, int systemCode) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.select(target, systemCode);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setPrivacy(privacySettingData, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo write(BlockDataList blockDataList, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.write(blockDataList, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setPushNotificationListener(listener, appIdentificationCode);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setNodeCodeSize(nodeCodeSize, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo checkOnlineAccess() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.checkOnlineAccess();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo setSelectTimeout(int timeout) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setSelectTimeout(timeout);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt getSelectTimeout() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            FelicaResultInfoInt felicaResultInfoInt = new FelicaResultInfoInt(Integer.valueOf(this.mMfcOffline.getSelectTimeout()));
            LogMgr.log(4, "%s", "999");
            return felicaResultInfoInt;
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo cancelOffline() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.cancelOffline();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoByteArray executeFelicaCommand(byte[] commandPacket, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] bArrExecuteFelicaCommand = this.mMfcOffline.executeFelicaCommand(commandPacket, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(bArrExecuteFelicaCommand);
        } catch (FelicaException e) {
            LogMgr.log(2, "catch FelicaException id = %d type = %d", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            return new FelicaResultInfoKeyInformationArray(this.mMfcOffline.getKeyVersionV2(nodeCodeList, timeout, retry));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoKeyInformationArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoKeyInformationArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo login(String accountIssuer, String accountName, ILoginEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "login");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.login(accountIssuer, accountName, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "login");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_START, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_START, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo logout(boolean autoMfiServerLogout, ILogoutEventCallback callback) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        FelicaResultInfo felicaResultInfoDoLogout = doLogout(autoMfiServerLogout, callback, false);
        LogMgr.log(4, "%s", "999");
        return felicaResultInfoDoLogout;
    }

    FelicaResultInfo doLogout(boolean autoMfiServerLogout, ILogoutEventCallback callback, boolean isForce) {
        LogMgr.performanceIn("API", "MfiOnline", "logout");
        LogMgr.log(6, "%s", "000");
        try {
            this.mMfiOnline.logout(autoMfiServerLogout, callback, isForce);
            LogMgr.log(6, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "logout");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            if (!isForce) {
                LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_STOP, e);
            }
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            if (!isForce) {
                LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_STOP, e2);
            }
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoString getCurrentAccountHash() throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getCurrentAccountHash");
        LogMgr.log(4, "%s", "000");
        try {
            String currentAccountHash = this.mMfiOnline.getCurrentAccountHash();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCurrentAccountHash");
            return new FelicaResultInfoString(currentAccountHash);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CURRENT_ACCOUNT_HASH, e);
            return new FelicaResultInfoString(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo clearMfiAccount() throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "clearMfiAccount");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.clearMfiAccount();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "clearMfiAccount");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_CLEAR_MFI_ACCOUNT, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoSeInfo getSeInfomation() throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getSeInfomation");
        LogMgr.log(4, "%s", "000");
        try {
            SeInfo seInfomation = this.mMfiOnline.getSeInfomation();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "getSeInfomation");
            return new FelicaResultInfoSeInfo(seInfomation);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_SE_INFORMATION, e);
            return new FelicaResultInfoSeInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardList(ICardListEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getCardList");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.getCardList(callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardList");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getCardAdditionalInfoList");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.getCardAdditionalInfoList(callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardAdditionalInfoList");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo issueCard(String linkageData, ICardIssueEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "issueCard");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.issueCard(linkageData, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "issueCard");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo enable(CardInfo cardInfo, ICardEnableEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "enable");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.enable(cardInfo, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "enable");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_ENABLE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_ENABLE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "disable");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.disable(cardInfo, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "disable");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_DISABLE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_DISABLE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo delete(CardInfo cardInfo, String linkageData, ICardDeleteEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "delete");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.delete(cardInfo, linkageData, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "delete");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_DELETE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_DELETE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo cancelCardOperation() throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "cancelCardOperation");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.cancelCardOperation();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "cancelCardOperation");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "70", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_CANCEL_CARD_OPERATION, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo start(String url, DeviceList deviceList, IFSCEventListener fscEventListener, IMfiFelica felica) throws RemoteException {
        return this.mMfcOnline.start(url, deviceList, fscEventListener, felica);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public void stop() throws RemoteException {
        this.mMfcOnline.stop();
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public void notifyResult(byte[] result) throws RemoteException {
        this.mMfcOnline.notifyResult(result);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public void notifyError(String msg) throws RemoteException {
        this.mMfcOnline.notifyError(msg);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo activateFelica(String packageName, IFelicaEventListener listener) throws RemoteException {
        LogMgr.log(3, "%s", "000");
        return activateFelica(packageName, listener, listener.asBinder());
    }

    public synchronized FelicaResultInfo activateFelica(String callerPackageName, IFelicaEventListener listener, IBinder binder) throws RemoteException {
        LogMgr.log(3, "%s", "000");
        try {
            if (binder == null) {
                throw new FelicaException(1, 47);
            }
            if (listener == null) {
                throw new IllegalArgumentException("The specified parameter is invalid.");
            }
            this.mMfcOffline.activateFelica(callerPackageName, listener, binder);
            LogMgr.log(3, "%s", "999");
        } catch (FelicaException e) {
            if (e.getType() == 39) {
                LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d otherAppPID = %s", "801", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), e.getOtherAppInfo());
                return new FelicaResultInfo(1, e.getMessage(), e.getID(), 39, e.getOtherAppInfo());
            }
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "802", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), e.getOtherAppInfo());
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
        return new FelicaResultInfo();
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo close() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.close();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    public FelicaResultInfo doClose() {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.doClose();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            BlockCountInformation[] blockCountInformation = this.mMfcOffline.getBlockCountInformation(nodeCodeList, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoBlockCountInformationArray(blockCountInformation);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoBlockCountInformationArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoBlockCountInformationArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoByteArray getContainerId(int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] containerId = this.mMfcOffline.getContainerId(timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(containerId);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] containerIssueInformation = this.mMfcOffline.getContainerIssueInformation(timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(containerIssueInformation);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoByteArray getICCode() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] icCode = this.mMfcOffline.getIcCode();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(icCode);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoByteArray getIDm() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] idm = this.mMfcOffline.getIdm();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(idm);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt getInterface() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            int i = this.mMfcOffline.getInterface();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoInt(Integer.valueOf(i));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            int keyVersion = this.mMfcOffline.getKeyVersion(serviceCode, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoInt(Integer.valueOf(keyVersion));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoInt(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            NodeInformation nodeInformation = this.mMfcOffline.getNodeInformation(parentAreaCode, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoNodeInformation(nodeInformation);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoNodeInformation(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoNodeInformation(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            NodeInformation privacyNodeInformation = this.mMfcOffline.getPrivacyNodeInformation(parentAreaCode, timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoNodeInformation(privacyNodeInformation);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoNodeInformation(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoNodeInformation(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoBoolean getRFSState() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            boolean rfsState = this.mMfcOffline.getRfsState();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoBoolean(Boolean.valueOf(rfsState));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoBoolean(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt getSystemCode() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            int systemCode = this.mMfcOffline.getSystemCode();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoInt(Integer.valueOf(systemCode));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            int[] systemCodeList = this.mMfcOffline.getSystemCodeList(timeout, retry);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoIntArray(systemCodeList);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoIntArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo inactivateFelica() throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.inactivateFelica();
            this.mMfiOnline.clearSeInfo();
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    FelicaResultInfo doInactivateFelica(boolean isCheckProcess) {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.doInactivateFelica(isCheckProcess);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo silentStart(String accountIssuer, String accountName, int code, ISilentStartEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", LogSender.EXTRA_VALUE_EVENT_NAME_SILENT_START);
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.silentStart(accountIssuer, accountName, code, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", LogSender.EXTRA_VALUE_EVENT_NAME_SILENT_START);
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo silentStartForMfiAdmin(String accountIssuer, String accountName, boolean login, int code, ISilentStartForMfiAdminEventCallback callback) throws RemoteException {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999");
        return silentStartForMfiAdminV2(accountIssuer, accountName, login, code, 1, callback);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo silentStartForMfiAdminV2(String accountIssuer, String accountName, boolean login, int code, int layoutType, ISilentStartForMfiAdminEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "silentStartForMfiAdminV2");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.silentStartForMfiAdmin(accountIssuer, accountName, login, false, code, layoutType, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "silentStartForMfiAdminV2");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo silentStartForMfiAdminV3(String accountIssuer, String accountName, boolean login, boolean skipCheckChipInitialized, int code, ISilentStartForMfiAdminEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "silentStartForMfiAdminV3");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.silentStartForMfiAdmin(accountIssuer, accountName, login, skipCheckChipInitialized, code, 1, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "silentStartForMfiAdminV3");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SILENT_START, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getLinkageDataList(int actionType, String[] cidList, ILinkageDataListEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getLinkageDataList");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getLinkageDataList(actionType, cidList, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "getLinkageDataList");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_LINKAGE_DATA_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_LINKAGE_DATA_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo initialize(String linkageData, IInitializedEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "initialize");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.initialize(linkageData, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "initialize");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_INITIALIZE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_INITIALIZE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardListV2(IPipeEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getCardListV2");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardListV2(callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardListV2");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback callback) throws RemoteException {
        LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
        throw new RemoteException();
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo issueCardV2(String linkageData, ICardIssueV2EventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "issueCardV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.issueCardV2(linkageData, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "issueCardV2");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo enableV2(String cardInfoJson, ICardEnableV2EventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "enableV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.enableV2(cardInfoJson, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "enableV2");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_ENABLE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_ENABLE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo disableV2(String cardInfoJson, ICardDisableV2EventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "disableV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.disableV2(cardInfoJson, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "disableV2");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_DISABLE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_DISABLE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo deleteV2(String cardInfoJson, String linkageData, ICardDeleteV2EventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "deleteV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.deleteV2(cardInfoJson, linkageData, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "deleteV2");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_DELETE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_DELETE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt checkChipFormatting() throws RemoteException {
        LogMgr.log(4, "800 Unsupported.");
        LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CHECK_CHIP_FORMATTING, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
        return new FelicaResultInfoInt(1, null, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoBoolean isChipInitialized() throws RemoteException {
        LogMgr.performanceIn("API", "MfiOffline", "isChipInitialized");
        LogMgr.log(4, "000");
        try {
            boolean zIsChipInitialized = this.mMfiOffline.isChipInitialized();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "isChipInitialized");
            return new FelicaResultInfoBoolean(Boolean.valueOf(zIsChipInitialized));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_IS_CHIP_INITIALIZED, e);
            return new FelicaResultInfoBoolean(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardInfoListWithSpStatus(String serviceId, IPipeEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getCardInfoListWithSpStatus");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardInfoListWithSpStatus(serviceId, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardInfoListWithSpStatus");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardAdditionalInfoListWithCidList(String[] cidList, IPipeEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "getCardAdditionalInfoListWithCidList");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardAdditionalInfoListWithCidList(cidList, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardAdditionalInfoListWithCidList");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo saveDelete(String cardInfoJson, String linkageData, ICardReissuableDeleteEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "saveDelete");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.saveDelete(cardInfoJson, linkageData, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "saveDelete");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_DELETE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_DELETE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo selectWithCid(int systemCode, String cid) throws RemoteException {
        LogMgr.log(4, "000");
        try {
            this.mMfcOffline.select(systemCode, cid);
            LogMgr.log(4, "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "700 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2(), "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo issueCardWithOtp(String linkageData, String otp, ICardIssueV2EventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "issueCardWithOtp");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.issueCardWithOtp(linkageData, otp, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "issueCardWithOtp");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_ISSUE_CARD, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo access(String cardInfoJson, String linkageData, ICardAccessEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "access");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.access(cardInfoJson, linkageData, callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "access");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CARD_ACCESS, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CARD_ACCESS, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoStringArray getLocalCidList() throws RemoteException {
        LogMgr.log(4, "800 Unsupported.");
        LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_LOCAL_CID_LIST, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
        return new FelicaResultInfoStringArray(1, null, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoStringArray getLocalCidListV2() {
        LogMgr.performanceIn("API", "MfiOffline", "getLocalCidListV2");
        LogMgr.log(4, "000");
        try {
            String[] localCidList = this.mMfiOffline.getLocalCidList();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "getLocalCidListV2");
            return new FelicaResultInfoStringArray(localCidList);
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_LOCAL_CID_LIST, e);
            return new FelicaResultInfoStringArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt identifyService() throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "identifyService");
        LogMgr.log(4, "000");
        try {
            int iIdentifyService = this.mMfiOnline.identifyService();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "identifyService");
            return new FelicaResultInfoInt(Integer.valueOf(iIdentifyService));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_IDENTIFY_SERVICE, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt getUnsupportMfiService1CardPosition() {
        LogMgr.performanceIn("API", "MfiOffline", "getUnsupportMfiService1CardPosition");
        LogMgr.log(4, "000");
        try {
            int unsupportMfiService1CardPosition = this.mMfiOffline.getUnsupportMfiService1CardPosition();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "getUnsupportMfiService1CardPosition");
            return new FelicaResultInfoInt(Integer.valueOf(unsupportMfiService1CardPosition));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_UNSUPPORT_MFI_SERVICE1_CARD_POSITION, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo cancelMfiOffline() {
        LogMgr.performanceIn("API", "MfiOffline", "cancelMfiOffline");
        LogMgr.log(4, "000");
        try {
            this.mMfiOffline.cancelMfiOffline();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "cancelMfiOffline");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_CANCEL_MFI_OFFLINE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardListV3(IDataListEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getCardListV3");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardListV3(callback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardListV3");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardInfoListWithSpStatusV3(String serviceId, IDataListEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getCardInfoListWithSpStatusV3");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardInfoListWithSpStatusV3(serviceId, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardInfoListWithSpStatusV3");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_INFO_LIST_WITH_SP_STATUS, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardAdditionalInfoListV3(String[] cidList, IDataListEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getCardAdditionalInfoListV3");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardAdditionalInfoListV3(cidList, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCardAdditionalInfoListV3");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    MfcOffline getMfcOffline() {
        return this.mMfcOffline;
    }

    MfcOnline getMfcOnline() {
        return this.mMfcOnline;
    }

    FelicaResultInfo executeServerOperation(String operationId, String messageId, ExecuteServerOperationEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "executeServerOperation");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.executeServerOperation(operationId, messageId, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "executeServerOperation");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo provisionServerOperation(IServerOperationEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "provisionServerOperation");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.provisionServerOperation(callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "provisionServerOperation");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_PROVISION_SERVER_OPERATION, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_PROVISION_SERVER_OPERATION, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoStringArray getLocalPartialCardInfoList(String serviceId) {
        LogMgr.performanceIn("API", "MfiOffline", "getLocalPartialCardInfoList");
        LogMgr.log(4, "000");
        try {
            String[] localPartialCardInfoList = this.mMfiOffline.getLocalPartialCardInfoList(serviceId);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "getLocalPartialCardInfoList");
            return new FelicaResultInfoStringArray(localPartialCardInfoList);
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_LOCAL_PARTIAL_CARD_INFO_LIST, e);
            return new FelicaResultInfoStringArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_LOCAL_PARTIAL_CARD_INFO_LIST, e2);
            return new FelicaResultInfoStringArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoStringArray getLocalPartialCardInfoListForList(String[] serviceIdList) {
        LogMgr.performanceIn("API", "MfiOffline", "getLocalPartialCardInfoList");
        LogMgr.log(4, "000");
        try {
            String[] localPartialCardInfoList = this.mMfiOffline.getLocalPartialCardInfoList(serviceIdList);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "getLocalPartialCardInfoList");
            return new FelicaResultInfoStringArray(localPartialCardInfoList);
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_LOCAL_PARTIAL_CARD_INFO_LIST, e);
            return new FelicaResultInfoStringArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_LOCAL_PARTIAL_CARD_INFO_LIST, e2);
            return new FelicaResultInfoStringArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo existUnsupportMfiService1Card(String serviceId, final IUnsupportMfiService1CardExistEventCallback callback) {
        try {
            this.mMfiOnline.existUnsupportMfiService1Card(serviceId, callback);
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_UNSUPPORT_MFI_SERVICE1_CARD, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_UNSUPPORT_MFI_SERVICE1_CARD, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt existEmptySlot() {
        LogMgr.performanceIn("API", "MfiOffline", "existEmptySlot");
        LogMgr.log(4, "000");
        try {
            int iExistEmptySlot = this.mMfiOffline.existEmptySlot();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "existEmptySlot");
            return new FelicaResultInfoInt(Integer.valueOf(iExistEmptySlot));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_EMPTY_SLOT, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo deleteUnsupportMfiService1Card(String linkageData, IUnsupportMfiService1CardDeleteEventCallback callback) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "deleteUnsupportMfiService1Card");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.deleteUnsupportMfiService1Card(linkageData, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "deleteUnsupportMfiService1Card");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_DELETE_UNSUPPORT_MFI_SERVICE1_CARD, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_DELETE_UNSUPPORT_MFI_SERVICE1_CARD, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCachedCardList(IDataListEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getCachedCardList");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCachedCardList(callback, true);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCachedCardList");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo enableCachedCard(String cachedCardInfoJson, ICachedCardEnableEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "enableCachedCard");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.enableCachedCard(cachedCardInfoJson, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "enableCachedCard");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.CACHED_CARD_ENABLE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.CACHED_CARD_ENABLE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoBoolean isLoginedAccount(String accountName) throws RemoteException {
        LogMgr.performanceIn("API", "MfiOnline", "isLoginedAccount");
        LogMgr.log(4, "000");
        try {
            boolean zIsLoginedAccount = this.mMfiOnline.isLoginedAccount(accountName);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "isLoginedAccount");
            return new FelicaResultInfoBoolean(Boolean.valueOf(zIsLoginedAccount));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_IS_LOGINED_ACCOUNT, e);
            return new FelicaResultInfoBoolean(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_IS_LOGINED_ACCOUNT, e2);
            return new FelicaResultInfoBoolean(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoBoolean existService() {
        LogMgr.performanceIn("API", "MfiOffline", "existService");
        LogMgr.log(4, "000");
        try {
            boolean zExistService = this.mMfiOffline.existService();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "existService");
            return new FelicaResultInfoBoolean(Boolean.valueOf(zExistService));
        } catch (FelicaException e) {
            LogMgr.log(2, "700 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_SERVICE, e);
            return new FelicaResultInfoBoolean(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getMfiTosData(int code, IMfiTosDataGetEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getMfiTosData");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getMfiTosData(code, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getMfiTosData");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_MFI_TOS_DATA, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_MFI_TOS_DATA, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo setMfiTosAgreement(int code) {
        LogMgr.performanceIn("API", "MfiOffline", "setMfiTosAgreement");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.setMfiTosAgreement(code);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "setMfiTosAgreement");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_SET_MFI_TOS_AGREEMENT, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getGoogleTos(int code, IGoogleTosGetEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getGoogleTos");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getGoogleTos(code, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getGoogleTos");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_GOOGLE_TOS, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_GOOGLE_TOS, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getRemainedCards(IRemainedCardsEventCallback callback) {
        LogMgr.log(4, "800 Unsupported.");
        LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_REMAINED_CARDS, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
        return new FelicaResultInfo(1, null, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getRemainedCardsV2(IRemainedCardsEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getRemainedCards");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getRemainedCards(callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getRemainedCards");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_REMAINED_CARDS, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_GET_REMAINED_CARDS, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo uploadCardsToDelete(ICardsUploadEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "uploadCardsToDelete");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.uploadCardsToDelete(callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "uploadCardsToDelete");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_DELETE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_DELETE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo uploadCardsToDisable(ICardsUploadEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "uploadCardsToDisable");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.uploadCardsToDisable(callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "uploadCardsToDisable");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_DISABLE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_DISABLE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo uploadCardsToPermanentDelete(ICardsUploadEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "uploadCardsToPermanentDelete");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.uploadCardsToPermanentDelete(callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "uploadCardsToPermanentDelete");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_PERMANENT_DELETE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.REMAINED_CARDS_UPLOAD_CARDS_TO_PERMANENT_DELETE, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo clearMemory(IMemoryClearEventCallback callback) {
        LogMgr.log(4, "800 Unsupported.");
        LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CLEAR_MEMORY, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
        return new FelicaResultInfo(1, null, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo clearMemoryV2(IMemoryClearEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "clearMemory");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.clearMemory(callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "clearMemory");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + "message = " + e.getMessage() + "type = " + e.getType() + "statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CLEAR_MEMORY, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CLEAR_MEMORY, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoBoolean checkAndRecoverCrsState() {
        LogMgr.performanceIn("API", "MfiOffline", "checkAndRecoverCrsState");
        LogMgr.log(4, "000");
        try {
            boolean zCheckAndRecoverCrsState = this.mMfiOffline.checkAndRecoverCrsState();
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOffline", "checkAndRecoverCrsState");
            return new FelicaResultInfoBoolean(Boolean.valueOf(zCheckAndRecoverCrsState));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CHECK_AND_RECOVER_CRS_STATE, e);
            return new FelicaResultInfoBoolean(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCachedCardListWithIntegrityCheck(IDataListEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "getCachedCardListWithIntegrityCheck");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCachedCardList(callback, false);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "getCachedCardListWithIntegrityCheck");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST_WITH_INTEGRITY_CHECK, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CACHED_CARD_LIST_WITH_INTEGRITY_CHECK, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo migrateCardKey(String serviceId, ICardKeyMigrateEventCallback callback) {
        LogMgr.performanceIn("API", "MfiOnline", "migrateCardKey");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.migrateCardKey(serviceId, callback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut("API", "MfiOnline", "migrateCardKey");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_MIGRATE_CARD_KEY, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "700 : Catch IllegalArgumentException message = " + e2.getMessage());
            LogMgr.exLogException(MfiClientExternalLogConst.MficApi.USER_MIGRATE_CARD_KEY, e2);
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }
}
