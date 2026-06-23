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

/* JADX INFO: loaded from: classes.dex */
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
    public FelicaResultInfoDataArray read(BlockList blockList, int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            Data[] dataArr = this.mMfcOffline.read(blockList, i, i2);
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
    public FelicaResultInfo select(int i) throws RemoteException {
        return selectWithTarget(0, i);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo selectWithTarget(int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.select(i, i2);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setPrivacy(privacySettingDataArr, i, i2);
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
    public FelicaResultInfo write(BlockDataList blockDataList, int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.write(blockDataList, i, i2);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setPushNotificationListener(iFelicaPushAppNotificationListener, str);
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
    public FelicaResultInfo setNodeCodeSize(int i, int i2, int i3) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setNodeCodeSize(i, i2, i3);
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
    public FelicaResultInfo setSelectTimeout(int i) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.setSelectTimeout(i);
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
    public FelicaResultInfoByteArray executeFelicaCommand(byte[] bArr, int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] bArrExecuteFelicaCommand = this.mMfcOffline.executeFelicaCommand(bArr, i, i2);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(bArrExecuteFelicaCommand);
        } catch (FelicaException e) {
            LogMgr.log(2, "catch FelicaException id = %d type = %d", Integer.valueOf(e.getID()), Integer.valueOf(e.getType()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] iArr, int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            return new FelicaResultInfoKeyInformationArray(this.mMfcOffline.getKeyVersionV2(iArr, i, i2));
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoKeyInformationArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            return new FelicaResultInfoKeyInformationArray(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo login(String str, String str2, ILoginEventCallback iLoginEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN);
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.login(str, str2, iLoginEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", LogSender.EXTRA_VALUE_EVENT_NAME_LOGIN);
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
    public FelicaResultInfo logout(boolean z, ILogoutEventCallback iLogoutEventCallback) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        FelicaResultInfo felicaResultInfoDoLogout = doLogout(z, iLogoutEventCallback, false);
        LogMgr.log(4, "%s", "999");
        return felicaResultInfoDoLogout;
    }

    FelicaResultInfo doLogout(boolean z, ILogoutEventCallback iLogoutEventCallback, boolean z2) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "logout");
        LogMgr.log(6, "%s", "000");
        try {
            this.mMfiOnline.logout(z, iLogoutEventCallback, z2);
            LogMgr.log(6, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "logout");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            if (!z2) {
                LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_STOP, e);
            }
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        } catch (IllegalArgumentException e2) {
            LogMgr.log(2, "%s : Catch IllegalArgumentException message = %s", "700", e2.getMessage());
            if (!z2) {
                LogMgr.exLogException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_STOP, e2);
            }
            return new FelicaResultInfo(32, e2.getMessage());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoString getCurrentAccountHash() throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCurrentAccountHash");
        LogMgr.log(4, "%s", "000");
        try {
            String currentAccountHash = this.mMfiOnline.getCurrentAccountHash();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCurrentAccountHash");
            return new FelicaResultInfoString(currentAccountHash);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_CURRENT_ACCOUNT_HASH, e);
            return new FelicaResultInfoString(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo clearMfiAccount() throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "clearMfiAccount");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.clearMfiAccount();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "clearMfiAccount");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_CLEAR_MFI_ACCOUNT, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoSeInfo getSeInfomation() throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getSeInfomation");
        LogMgr.log(4, "%s", "000");
        try {
            SeInfo seInfomation = this.mMfiOnline.getSeInfomation();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getSeInfomation");
            return new FelicaResultInfoSeInfo(seInfomation);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "701", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_SE_INFORMATION, e);
            return new FelicaResultInfoSeInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardList(ICardListEventCallback iCardListEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardList");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.getCardList(iCardListEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardList");
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
    public FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback iCardAdditionalInfoListEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardAdditionalInfoList");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.getCardAdditionalInfoList(iCardAdditionalInfoListEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardAdditionalInfoList");
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
    public FelicaResultInfo issueCard(String str, ICardIssueEventCallback iCardIssueEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "issueCard");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.issueCard(str, iCardIssueEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "issueCard");
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
    public FelicaResultInfo enable(CardInfo cardInfo, ICardEnableEventCallback iCardEnableEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "enable");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.enable(cardInfo, iCardEnableEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "enable");
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
    public FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback iCardDisableEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "disable");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.disable(cardInfo, iCardDisableEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "disable");
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
    public FelicaResultInfo delete(CardInfo cardInfo, String str, ICardDeleteEventCallback iCardDeleteEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "delete");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.delete(cardInfo, str, iCardDeleteEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "delete");
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
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "cancelCardOperation");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.cancelCardOperation();
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "cancelCardOperation");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "70", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_CANCEL_CARD_OPERATION, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo start(String str, DeviceList deviceList, IFSCEventListener iFSCEventListener, IMfiFelica iMfiFelica) throws RemoteException {
        return this.mMfcOnline.start(str, deviceList, iFSCEventListener, iMfiFelica);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public void stop() throws RemoteException {
        this.mMfcOnline.stop();
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public void notifyResult(byte[] bArr) throws RemoteException {
        this.mMfcOnline.notifyResult(bArr);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public void notifyError(String str) throws RemoteException {
        this.mMfcOnline.notifyError(str);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo activateFelica(String str, IFelicaEventListener iFelicaEventListener) throws RemoteException {
        LogMgr.log(3, "%s", "000");
        return activateFelica(str, iFelicaEventListener, iFelicaEventListener.asBinder());
    }

    public synchronized FelicaResultInfo activateFelica(String str, IFelicaEventListener iFelicaEventListener, IBinder iBinder) throws RemoteException {
        LogMgr.log(3, "%s", "000");
        try {
            try {
                if (iBinder == null) {
                    throw new FelicaException(1, 47);
                }
                if (iFelicaEventListener == null) {
                    throw new IllegalArgumentException("The specified parameter is invalid.");
                }
                this.mMfcOffline.activateFelica(str, iFelicaEventListener, iBinder);
                LogMgr.log(3, "%s", "999");
            } catch (FelicaException e) {
                if (e.getType() == 39) {
                    LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d otherAppPID = %s", "801", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), e.getOtherAppInfo());
                    return new FelicaResultInfo(1, e.getMessage(), e.getID(), 39, e.getOtherAppInfo());
                }
                LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "802", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), e.getOtherAppInfo());
                return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
            }
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
    public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] iArr, int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            BlockCountInformation[] blockCountInformation = this.mMfcOffline.getBlockCountInformation(iArr, i, i2);
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
    public FelicaResultInfoByteArray getContainerId(int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] containerId = this.mMfcOffline.getContainerId(i, i2);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(containerId);
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfoByteArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoByteArray getContainerIssueInformation(int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            byte[] containerIssueInformation = this.mMfcOffline.getContainerIssueInformation(i, i2);
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
    public FelicaResultInfoInt getKeyVersion(int i, int i2, int i3) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            int keyVersion = this.mMfcOffline.getKeyVersion(i, i2, i3);
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
    public FelicaResultInfoNodeInformation getNodeInformation(int i, int i2, int i3) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            NodeInformation nodeInformation = this.mMfcOffline.getNodeInformation(i, i2, i3);
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
    public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int i, int i2, int i3) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            NodeInformation privacyNodeInformation = this.mMfcOffline.getPrivacyNodeInformation(i, i2, i3);
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
    public FelicaResultInfoIntArray getSystemCodeList(int i, int i2) throws RemoteException {
        LogMgr.log(4, "%s", "000");
        try {
            int[] systemCodeList = this.mMfcOffline.getSystemCodeList(i, i2);
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

    FelicaResultInfo doInactivateFelica(boolean z) {
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfcOffline.doInactivateFelica(z);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "%s : catch FelicaException id = %d message = %s type = %d statusFlag1 = %d statusFlag2 = %d", "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo silentStart(String str, String str2, int i, ISilentStartEventCallback iSilentStartEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", LogSender.EXTRA_VALUE_EVENT_NAME_SILENT_START);
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.silentStart(str, str2, i, iSilentStartEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", LogSender.EXTRA_VALUE_EVENT_NAME_SILENT_START);
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
    public FelicaResultInfo silentStartForMfiAdmin(String str, String str2, boolean z, int i, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException {
        LogMgr.log(4, "000");
        LogMgr.log(4, "999");
        return silentStartForMfiAdminV2(str, str2, z, i, 1, iSilentStartForMfiAdminEventCallback);
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo silentStartForMfiAdminV2(String str, String str2, boolean z, int i, int i2, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "silentStartForMfiAdminV2");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.silentStartForMfiAdmin(str, str2, z, i, i2, iSilentStartForMfiAdminEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "silentStartForMfiAdminV2");
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
    public FelicaResultInfo getLinkageDataList(int i, String[] strArr, ILinkageDataListEventCallback iLinkageDataListEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getLinkageDataList");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getLinkageDataList(i, strArr, iLinkageDataListEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getLinkageDataList");
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
    public FelicaResultInfo initialize(String str, IInitializedEventCallback iInitializedEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "initialize");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.initialize(str, iInitializedEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "initialize");
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
    public FelicaResultInfo getCardListV2(IPipeEventCallback iPipeEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardListV2");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardListV2(iPipeEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardListV2");
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
    public FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback iPipeEventCallback) throws RemoteException {
        LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.USER_GET_CARD_ADDITIONAL_INFO_LIST, 1, MfiClientException.TYPE_MFICLIENT_REMOTE_ACCESS_FAILED);
        throw new RemoteException();
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo issueCardV2(String str, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "issueCardV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.issueCardV2(str, iCardIssueV2EventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "issueCardV2");
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
    public FelicaResultInfo enableV2(String str, ICardEnableV2EventCallback iCardEnableV2EventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "enableV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.enableV2(str, iCardEnableV2EventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "enableV2");
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
    public FelicaResultInfo disableV2(String str, ICardDisableV2EventCallback iCardDisableV2EventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "disableV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.disableV2(str, iCardDisableV2EventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "disableV2");
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
    public FelicaResultInfo deleteV2(String str, String str2, ICardDeleteV2EventCallback iCardDeleteV2EventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "deleteV2");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.deleteV2(str, str2, iCardDeleteV2EventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "deleteV2");
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
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "checkChipFormatting");
        LogMgr.log(4, "000");
        try {
            int iCheckChipFormatting = this.mMfiOnline.checkChipFormatting();
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "checkChipFormatting");
            return new FelicaResultInfoInt(Integer.valueOf(iCheckChipFormatting));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_ADMIN_CHECK_CHIP_FORMATTING, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardInfoListWithSpStatus(String str, IPipeEventCallback iPipeEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardInfoListWithSpStatus");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardInfoListWithSpStatus(str, iPipeEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardInfoListWithSpStatus");
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
    public FelicaResultInfo getCardAdditionalInfoListWithCidList(String[] strArr, IPipeEventCallback iPipeEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardAdditionalInfoListWithCidList");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardAdditionalInfoListWithCidList(strArr, iPipeEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardAdditionalInfoListWithCidList");
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
    public FelicaResultInfo saveDelete(String str, String str2, ICardReissuableDeleteEventCallback iCardReissuableDeleteEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "saveDelete");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.saveDelete(str, str2, iCardReissuableDeleteEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "saveDelete");
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
    public FelicaResultInfo selectWithCid(int i, String str) throws RemoteException {
        LogMgr.log(4, "000");
        try {
            this.mMfcOffline.select(i, str);
            LogMgr.log(4, "999");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "700 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + "statusFlag2 = " + e.getStatusFlag2(), "700", Integer.valueOf(e.getID()), e.getMessage(), Integer.valueOf(e.getType()), Integer.valueOf(e.getStatusFlag1()), Integer.valueOf(e.getStatusFlag2()));
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo issueCardWithOtp(String str, String str2, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "issueCardWithOtp");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.issueCardWithOtp(str, str2, iCardIssueV2EventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "issueCardWithOtp");
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
    public FelicaResultInfo access(String str, String str2, ICardAccessEventCallback iCardAccessEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "access");
        LogMgr.log(4, "%s", "000");
        try {
            this.mMfiOnline.access(str, str2, iCardAccessEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "access");
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
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOffline", "getLocalCidListV2");
        LogMgr.log(4, "000");
        try {
            String[] localCidList = this.mMfiOffline.getLocalCidList();
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOffline", "getLocalCidListV2");
            return new FelicaResultInfoStringArray(localCidList);
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_LOCAL_CID_LIST, e);
            return new FelicaResultInfoStringArray(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt identifyService() throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "identifyService");
        LogMgr.log(4, "000");
        try {
            int iIdentifyService = this.mMfiOnline.identifyService();
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "identifyService");
            return new FelicaResultInfoInt(Integer.valueOf(iIdentifyService));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_IDENTIFY_SERVICE, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfoInt getUnsupportMfiService1CardPosition() {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOffline", "getUnsupportMfiService1CardPosition");
        LogMgr.log(4, "000");
        try {
            int unsupportMfiService1CardPosition = this.mMfiOffline.getUnsupportMfiService1CardPosition();
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOffline", "getUnsupportMfiService1CardPosition");
            return new FelicaResultInfoInt(Integer.valueOf(unsupportMfiService1CardPosition));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_GET_UNSUPPORT_MFI_SERVICE1_CARD_POSITION, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo cancelMfiOffline() {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOffline", "cancelMfiOffline");
        LogMgr.log(4, "000");
        try {
            this.mMfiOffline.cancelMfiOffline();
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOffline", "cancelMfiOffline");
            return new FelicaResultInfo();
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_CANCEL_MFI_OFFLINE, e);
            return new FelicaResultInfo(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo getCardListV3(IDataListEventCallback iDataListEventCallback) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardListV3");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardListV3(iDataListEventCallback);
            LogMgr.log(4, "%s", "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardListV3");
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
    public FelicaResultInfo getCardInfoListWithSpStatusV3(String str, IDataListEventCallback iDataListEventCallback) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardInfoListWithSpStatusV3");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardInfoListWithSpStatusV3(str, iDataListEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardInfoListWithSpStatusV3");
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
    public FelicaResultInfo getCardAdditionalInfoListV3(String[] strArr, IDataListEventCallback iDataListEventCallback) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardAdditionalInfoListV3");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCardAdditionalInfoListV3(strArr, iDataListEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCardAdditionalInfoListV3");
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

    FelicaResultInfo executeServerOperation(String str, String str2, ExecuteServerOperationEventCallback executeServerOperationEventCallback) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "executeServerOperation");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.executeServerOperation(str, str2, executeServerOperationEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "executeServerOperation");
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
    public FelicaResultInfo provisionServerOperation(IServerOperationEventCallback iServerOperationEventCallback) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "provisionServerOperation");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.provisionServerOperation(iServerOperationEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "provisionServerOperation");
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
    public FelicaResultInfoStringArray getLocalPartialCardInfoList(String str) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOffline", "getLocalPartialCardInfoList");
        LogMgr.log(4, "000");
        try {
            String[] localPartialCardInfoList = this.mMfiOffline.getLocalPartialCardInfoList(str);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOffline", "getLocalPartialCardInfoList");
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
    public FelicaResultInfo existUnsupportMfiService1Card(String str, IUnsupportMfiService1CardExistEventCallback iUnsupportMfiService1CardExistEventCallback) {
        try {
            this.mMfiOnline.existUnsupportMfiService1Card(str, iUnsupportMfiService1CardExistEventCallback);
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
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOffline", "existEmptySlot");
        LogMgr.log(4, "000");
        try {
            int iExistEmptySlot = this.mMfiOffline.existEmptySlot();
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOffline", "existEmptySlot");
            return new FelicaResultInfoInt(Integer.valueOf(iExistEmptySlot));
        } catch (FelicaException e) {
            LogMgr.log(2, "701 : catch FelicaException id = " + e.getID() + " message = " + e.getMessage() + " type = " + e.getType() + " statusFlag1 = " + e.getStatusFlag1() + " statusFlag2 = " + e.getStatusFlag2());
            LogMgr.exLogMfiClientException(MfiClientExternalLogConst.MficApi.MFI_CLIENT_EXIST_EMPTY_SLOT, e);
            return new FelicaResultInfoInt(1, e.getMessage(), e.getID(), e.getType(), e.getStatusFlag1(), e.getStatusFlag2());
        }
    }

    @Override // com.felicanetworks.mfc.mfi.IMfiFelica
    public FelicaResultInfo deleteUnsupportMfiService1Card(String str, IUnsupportMfiService1CardDeleteEventCallback iUnsupportMfiService1CardDeleteEventCallback) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "deleteUnsupportMfiService1Card");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.deleteUnsupportMfiService1Card(str, iUnsupportMfiService1CardDeleteEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "deleteUnsupportMfiService1Card");
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
    public FelicaResultInfo getCachedCardList(IDataListEventCallback iDataListEventCallback) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "getCachedCardList");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.getCachedCardList(iDataListEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "getCachedCardList");
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
    public FelicaResultInfo enableCachedCard(String str, ICachedCardEnableEventCallback iCachedCardEnableEventCallback) {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "enableCachedCard");
        LogMgr.log(4, "000");
        try {
            this.mMfiOnline.enableCachedCard(str, iCachedCardEnableEventCallback);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "enableCachedCard");
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
    public FelicaResultInfoBoolean isLoginedAccount(String str) throws RemoteException {
        LogMgr.performanceIn(LogMgr.PERFORMANCE_API, "MfiOnline", "isLoginedAccount");
        LogMgr.log(4, "000");
        try {
            boolean zIsLoginedAccount = this.mMfiOnline.isLoginedAccount(str);
            LogMgr.log(4, "999");
            LogMgr.performanceOut(LogMgr.PERFORMANCE_API, "MfiOnline", "isLoginedAccount");
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
}
