package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.DeviceList;
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
import com.felicanetworks.mfc.PrivacySettingData;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;
import com.felicanetworks.mfc.mfi.ICachedCardEnableEventCallback;
import com.felicanetworks.mfc.mfi.ICardAccessEventCallback;
import com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteEventCallback;
import com.felicanetworks.mfc.mfi.ICardDeleteV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableEventCallback;
import com.felicanetworks.mfc.mfi.ICardDisableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableEventCallback;
import com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardIssueEventCallback;
import com.felicanetworks.mfc.mfi.ICardIssueV2EventCallback;
import com.felicanetworks.mfc.mfi.ICardListEventCallback;
import com.felicanetworks.mfc.mfi.ICardReissuableDeleteEventCallback;
import com.felicanetworks.mfc.mfi.IDataListEventCallback;
import com.felicanetworks.mfc.mfi.IInitializedEventCallback;
import com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback;
import com.felicanetworks.mfc.mfi.ILoginEventCallback;
import com.felicanetworks.mfc.mfi.ILogoutEventCallback;
import com.felicanetworks.mfc.mfi.IPipeEventCallback;
import com.felicanetworks.mfc.mfi.IServerOperationEventCallback;
import com.felicanetworks.mfc.mfi.ISilentStartEventCallback;
import com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback;
import com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback;

/* JADX INFO: loaded from: classes.dex */
public interface IMfiFelica extends IInterface {
    FelicaResultInfo access(String str, String str2, ICardAccessEventCallback iCardAccessEventCallback) throws RemoteException;

    FelicaResultInfo activateFelica(String str, IFelicaEventListener iFelicaEventListener) throws RemoteException;

    FelicaResultInfo cancelCardOperation() throws RemoteException;

    FelicaResultInfo cancelMfiOffline() throws RemoteException;

    FelicaResultInfo cancelOffline() throws RemoteException;

    FelicaResultInfoInt checkChipFormatting() throws RemoteException;

    FelicaResultInfo checkOnlineAccess() throws RemoteException;

    FelicaResultInfo clearMfiAccount() throws RemoteException;

    FelicaResultInfo close() throws RemoteException;

    FelicaResultInfo delete(CardInfo cardInfo, String str, ICardDeleteEventCallback iCardDeleteEventCallback) throws RemoteException;

    FelicaResultInfo deleteUnsupportMfiService1Card(String str, IUnsupportMfiService1CardDeleteEventCallback iUnsupportMfiService1CardDeleteEventCallback) throws RemoteException;

    FelicaResultInfo deleteV2(String str, String str2, ICardDeleteV2EventCallback iCardDeleteV2EventCallback) throws RemoteException;

    FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback iCardDisableEventCallback) throws RemoteException;

    FelicaResultInfo disableV2(String str, ICardDisableV2EventCallback iCardDisableV2EventCallback) throws RemoteException;

    FelicaResultInfo enable(CardInfo cardInfo, ICardEnableEventCallback iCardEnableEventCallback) throws RemoteException;

    FelicaResultInfo enableCachedCard(String str, ICachedCardEnableEventCallback iCachedCardEnableEventCallback) throws RemoteException;

    FelicaResultInfo enableV2(String str, ICardEnableV2EventCallback iCardEnableV2EventCallback) throws RemoteException;

    FelicaResultInfoByteArray executeFelicaCommand(byte[] bArr, int i, int i2) throws RemoteException;

    FelicaResultInfoInt existEmptySlot() throws RemoteException;

    FelicaResultInfo existUnsupportMfiService1Card(String str, IUnsupportMfiService1CardExistEventCallback iUnsupportMfiService1CardExistEventCallback) throws RemoteException;

    FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] iArr, int i, int i2) throws RemoteException;

    FelicaResultInfo getCachedCardList(IDataListEventCallback iDataListEventCallback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback iCardAdditionalInfoListEventCallback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback iPipeEventCallback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoListV3(String[] strArr, IDataListEventCallback iDataListEventCallback) throws RemoteException;

    FelicaResultInfo getCardAdditionalInfoListWithCidList(String[] strArr, IPipeEventCallback iPipeEventCallback) throws RemoteException;

    FelicaResultInfo getCardInfoListWithSpStatus(String str, IPipeEventCallback iPipeEventCallback) throws RemoteException;

    FelicaResultInfo getCardInfoListWithSpStatusV3(String str, IDataListEventCallback iDataListEventCallback) throws RemoteException;

    FelicaResultInfo getCardList(ICardListEventCallback iCardListEventCallback) throws RemoteException;

    FelicaResultInfo getCardListV2(IPipeEventCallback iPipeEventCallback) throws RemoteException;

    FelicaResultInfo getCardListV3(IDataListEventCallback iDataListEventCallback) throws RemoteException;

    FelicaResultInfoByteArray getContainerId(int i, int i2) throws RemoteException;

    FelicaResultInfoByteArray getContainerIssueInformation(int i, int i2) throws RemoteException;

    FelicaResultInfoString getCurrentAccountHash() throws RemoteException;

    FelicaResultInfoByteArray getICCode() throws RemoteException;

    FelicaResultInfoByteArray getIDm() throws RemoteException;

    FelicaResultInfoInt getInterface() throws RemoteException;

    FelicaResultInfoInt getKeyVersion(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] iArr, int i, int i2) throws RemoteException;

    FelicaResultInfo getLinkageDataList(int i, String[] strArr, ILinkageDataListEventCallback iLinkageDataListEventCallback) throws RemoteException;

    FelicaResultInfoStringArray getLocalCidList() throws RemoteException;

    FelicaResultInfoStringArray getLocalCidListV2() throws RemoteException;

    FelicaResultInfoStringArray getLocalPartialCardInfoList(String str) throws RemoteException;

    FelicaResultInfoNodeInformation getNodeInformation(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfoNodeInformation getPrivacyNodeInformation(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfoBoolean getRFSState() throws RemoteException;

    FelicaResultInfoSeInfo getSeInfomation() throws RemoteException;

    FelicaResultInfoInt getSelectTimeout() throws RemoteException;

    FelicaResultInfoInt getSystemCode() throws RemoteException;

    FelicaResultInfoIntArray getSystemCodeList(int i, int i2) throws RemoteException;

    FelicaResultInfoInt getUnsupportMfiService1CardPosition() throws RemoteException;

    FelicaResultInfoInt identifyService() throws RemoteException;

    FelicaResultInfo inactivateFelica() throws RemoteException;

    FelicaResultInfo initialize(String str, IInitializedEventCallback iInitializedEventCallback) throws RemoteException;

    FelicaResultInfo issueCard(String str, ICardIssueEventCallback iCardIssueEventCallback) throws RemoteException;

    FelicaResultInfo issueCardV2(String str, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws RemoteException;

    FelicaResultInfo issueCardWithOtp(String str, String str2, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws RemoteException;

    FelicaResultInfo login(String str, String str2, ILoginEventCallback iLoginEventCallback) throws RemoteException;

    FelicaResultInfo logout(boolean z, ILogoutEventCallback iLogoutEventCallback) throws RemoteException;

    void notifyError(String str) throws RemoteException;

    void notifyResult(byte[] bArr) throws RemoteException;

    FelicaResultInfo open() throws RemoteException;

    FelicaResultInfo provisionServerOperation(IServerOperationEventCallback iServerOperationEventCallback) throws RemoteException;

    FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException;

    FelicaResultInfoDataArray read(BlockList blockList, int i, int i2) throws RemoteException;

    FelicaResultInfo reset() throws RemoteException;

    FelicaResultInfo saveDelete(String str, String str2, ICardReissuableDeleteEventCallback iCardReissuableDeleteEventCallback) throws RemoteException;

    FelicaResultInfo select(int i) throws RemoteException;

    FelicaResultInfo selectWithCid(int i, String str) throws RemoteException;

    FelicaResultInfo selectWithTarget(int i, int i2) throws RemoteException;

    FelicaResultInfo setNodeCodeSize(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) throws RemoteException;

    FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws RemoteException;

    FelicaResultInfo setSelectTimeout(int i) throws RemoteException;

    FelicaResultInfo silentStart(String str, String str2, int i, ISilentStartEventCallback iSilentStartEventCallback) throws RemoteException;

    FelicaResultInfo silentStartForMfiAdmin(String str, String str2, boolean z, int i, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException;

    FelicaResultInfo silentStartForMfiAdminV2(String str, String str2, boolean z, int i, int i2, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException;

    FelicaResultInfo start(String str, DeviceList deviceList, IFSCEventListener iFSCEventListener, IMfiFelica iMfiFelica) throws RemoteException;

    void stop() throws RemoteException;

    FelicaResultInfo write(BlockDataList blockDataList, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IMfiFelica {
        private static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IMfiFelica";
        static final int TRANSACTION_access = 60;
        static final int TRANSACTION_activateFelica = 1;
        static final int TRANSACTION_cancelCardOperation = 43;
        static final int TRANSACTION_cancelMfiOffline = 69;
        static final int TRANSACTION_cancelOffline = 29;
        static final int TRANSACTION_checkChipFormatting = 54;
        static final int TRANSACTION_checkOnlineAccess = 26;
        static final int TRANSACTION_clearMfiAccount = 34;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_delete = 42;
        static final int TRANSACTION_deleteUnsupportMfiService1Card = 77;
        static final int TRANSACTION_deleteV2 = 58;
        static final int TRANSACTION_disable = 41;
        static final int TRANSACTION_disableV2 = 57;
        static final int TRANSACTION_enable = 40;
        static final int TRANSACTION_enableCachedCard = 80;
        static final int TRANSACTION_enableV2 = 56;
        static final int TRANSACTION_executeFelicaCommand = 30;
        static final int TRANSACTION_existEmptySlot = 78;
        static final int TRANSACTION_existUnsupportMfiService1Card = 76;
        static final int TRANSACTION_getBlockCountInformation = 3;
        static final int TRANSACTION_getCachedCardList = 79;
        static final int TRANSACTION_getCardAdditionalInfoList = 38;
        static final int TRANSACTION_getCardAdditionalInfoListV2 = 52;
        static final int TRANSACTION_getCardAdditionalInfoListV3 = 73;
        static final int TRANSACTION_getCardAdditionalInfoListWithCidList = 62;
        static final int TRANSACTION_getCardInfoListWithSpStatus = 61;
        static final int TRANSACTION_getCardInfoListWithSpStatusV3 = 72;
        static final int TRANSACTION_getCardList = 37;
        static final int TRANSACTION_getCardListV2 = 51;
        static final int TRANSACTION_getCardListV3 = 71;
        static final int TRANSACTION_getContainerId = 4;
        static final int TRANSACTION_getContainerIssueInformation = 5;
        static final int TRANSACTION_getCurrentAccountHash = 33;
        static final int TRANSACTION_getICCode = 6;
        static final int TRANSACTION_getIDm = 7;
        static final int TRANSACTION_getInterface = 8;
        static final int TRANSACTION_getKeyVersion = 9;
        static final int TRANSACTION_getKeyVersionV2 = 31;
        static final int TRANSACTION_getLinkageDataList = 49;
        static final int TRANSACTION_getLocalCidList = 65;
        static final int TRANSACTION_getLocalCidListV2 = 70;
        static final int TRANSACTION_getLocalPartialCardInfoList = 75;
        static final int TRANSACTION_getNodeInformation = 10;
        static final int TRANSACTION_getPrivacyNodeInformation = 11;
        static final int TRANSACTION_getRFSState = 12;
        static final int TRANSACTION_getSeInfomation = 35;
        static final int TRANSACTION_getSelectTimeout = 28;
        static final int TRANSACTION_getSystemCode = 13;
        static final int TRANSACTION_getSystemCodeList = 14;
        static final int TRANSACTION_getUnsupportMfiService1CardPosition = 68;
        static final int TRANSACTION_identifyService = 66;
        static final int TRANSACTION_inactivateFelica = 15;
        static final int TRANSACTION_initialize = 50;
        static final int TRANSACTION_issueCard = 39;
        static final int TRANSACTION_issueCardV2 = 55;
        static final int TRANSACTION_issueCardWithOtp = 59;
        static final int TRANSACTION_login = 32;
        static final int TRANSACTION_logout = 36;
        static final int TRANSACTION_notifyError = 47;
        static final int TRANSACTION_notifyResult = 46;
        static final int TRANSACTION_open = 16;
        static final int TRANSACTION_provisionServerOperation = 74;
        static final int TRANSACTION_push = 17;
        static final int TRANSACTION_read = 18;
        static final int TRANSACTION_reset = 19;
        static final int TRANSACTION_saveDelete = 63;
        static final int TRANSACTION_select = 20;
        static final int TRANSACTION_selectWithCid = 64;
        static final int TRANSACTION_selectWithTarget = 21;
        static final int TRANSACTION_setNodeCodeSize = 25;
        static final int TRANSACTION_setPrivacy = 22;
        static final int TRANSACTION_setPushNotificationListener = 24;
        static final int TRANSACTION_setSelectTimeout = 27;
        static final int TRANSACTION_silentStart = 53;
        static final int TRANSACTION_silentStartForMfiAdmin = 48;
        static final int TRANSACTION_silentStartForMfiAdminV2 = 67;
        static final int TRANSACTION_start = 44;
        static final int TRANSACTION_stop = 45;
        static final int TRANSACTION_write = 23;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IMfiFelica asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMfiFelica)) {
                return (IMfiFelica) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoActivateFelica = activateFelica(parcel.readString(), IFelicaEventListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoActivateFelica != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoActivateFelica.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 2:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoClose = close();
                    parcel2.writeNoException();
                    if (felicaResultInfoClose != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoClose.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 3:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoBlockCountInformationArray blockCountInformation = getBlockCountInformation(parcel.createIntArray(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (blockCountInformation != null) {
                        parcel2.writeInt(1);
                        blockCountInformation.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 4:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoByteArray containerId = getContainerId(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (containerId != null) {
                        parcel2.writeInt(1);
                        containerId.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 5:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoByteArray containerIssueInformation = getContainerIssueInformation(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (containerIssueInformation != null) {
                        parcel2.writeInt(1);
                        containerIssueInformation.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 6:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoByteArray iCCode = getICCode();
                    parcel2.writeNoException();
                    if (iCCode != null) {
                        parcel2.writeInt(1);
                        iCCode.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 7:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoByteArray iDm = getIDm();
                    parcel2.writeNoException();
                    if (iDm != null) {
                        parcel2.writeInt(1);
                        iDm.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 8:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt felicaResultInfoInt = getInterface();
                    parcel2.writeNoException();
                    if (felicaResultInfoInt != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoInt.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 9:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt keyVersion = getKeyVersion(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (keyVersion != null) {
                        parcel2.writeInt(1);
                        keyVersion.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 10:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoNodeInformation nodeInformation = getNodeInformation(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (nodeInformation != null) {
                        parcel2.writeInt(1);
                        nodeInformation.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 11:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoNodeInformation privacyNodeInformation = getPrivacyNodeInformation(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (privacyNodeInformation != null) {
                        parcel2.writeInt(1);
                        privacyNodeInformation.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 12:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoBoolean rFSState = getRFSState();
                    parcel2.writeNoException();
                    if (rFSState != null) {
                        parcel2.writeInt(1);
                        rFSState.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 13:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt systemCode = getSystemCode();
                    parcel2.writeNoException();
                    if (systemCode != null) {
                        parcel2.writeInt(1);
                        systemCode.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 14:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoIntArray systemCodeList = getSystemCodeList(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (systemCodeList != null) {
                        parcel2.writeInt(1);
                        systemCodeList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 15:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoInactivateFelica = inactivateFelica();
                    parcel2.writeNoException();
                    if (felicaResultInfoInactivateFelica != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoInactivateFelica.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 16:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoOpen = open();
                    parcel2.writeNoException();
                    if (felicaResultInfoOpen != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoOpen.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 17:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoPush = push(parcel.readInt() != 0 ? PushSegmentParcelableWrapper.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (felicaResultInfoPush != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoPush.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 18:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoDataArray felicaResultInfoDataArray = read(parcel.readInt() != 0 ? BlockList.CREATOR.createFromParcel(parcel) : null, parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (felicaResultInfoDataArray != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoDataArray.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 19:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoReset = reset();
                    parcel2.writeNoException();
                    if (felicaResultInfoReset != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoReset.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 20:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoSelect = select(parcel.readInt());
                    parcel2.writeNoException();
                    if (felicaResultInfoSelect != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSelect.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 21:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoSelectWithTarget = selectWithTarget(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (felicaResultInfoSelectWithTarget != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSelectWithTarget.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 22:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo privacy = setPrivacy((PrivacySettingData[]) parcel.createTypedArray(PrivacySettingData.CREATOR), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (privacy != null) {
                        parcel2.writeInt(1);
                        privacy.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 23:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoWrite = write(parcel.readInt() != 0 ? BlockDataList.CREATOR.createFromParcel(parcel) : null, parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (felicaResultInfoWrite != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoWrite.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 24:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo pushNotificationListener = setPushNotificationListener(IFelicaPushAppNotificationListener.Stub.asInterface(parcel.readStrongBinder()), parcel.readString());
                    parcel2.writeNoException();
                    if (pushNotificationListener != null) {
                        parcel2.writeInt(1);
                        pushNotificationListener.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 25:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo nodeCodeSize = setNodeCodeSize(parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (nodeCodeSize != null) {
                        parcel2.writeInt(1);
                        nodeCodeSize.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 26:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoCheckOnlineAccess = checkOnlineAccess();
                    parcel2.writeNoException();
                    if (felicaResultInfoCheckOnlineAccess != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoCheckOnlineAccess.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 27:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo selectTimeout = setSelectTimeout(parcel.readInt());
                    parcel2.writeNoException();
                    if (selectTimeout != null) {
                        parcel2.writeInt(1);
                        selectTimeout.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 28:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt selectTimeout2 = getSelectTimeout();
                    parcel2.writeNoException();
                    if (selectTimeout2 != null) {
                        parcel2.writeInt(1);
                        selectTimeout2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 29:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoCancelOffline = cancelOffline();
                    parcel2.writeNoException();
                    if (felicaResultInfoCancelOffline != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoCancelOffline.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 30:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoByteArray felicaResultInfoByteArrayExecuteFelicaCommand = executeFelicaCommand(parcel.createByteArray(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (felicaResultInfoByteArrayExecuteFelicaCommand != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoByteArrayExecuteFelicaCommand.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 31:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoKeyInformationArray keyVersionV2 = getKeyVersionV2(parcel.createIntArray(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    if (keyVersionV2 != null) {
                        parcel2.writeInt(1);
                        keyVersionV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 32:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoLogin = login(parcel.readString(), parcel.readString(), ILoginEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoLogin != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoLogin.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 33:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoString currentAccountHash = getCurrentAccountHash();
                    parcel2.writeNoException();
                    if (currentAccountHash != null) {
                        parcel2.writeInt(1);
                        currentAccountHash.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 34:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoClearMfiAccount = clearMfiAccount();
                    parcel2.writeNoException();
                    if (felicaResultInfoClearMfiAccount != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoClearMfiAccount.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 35:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoSeInfo seInfomation = getSeInfomation();
                    parcel2.writeNoException();
                    if (seInfomation != null) {
                        parcel2.writeInt(1);
                        seInfomation.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 36:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoLogout = logout(parcel.readInt() != 0, ILogoutEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoLogout != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoLogout.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 37:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardList = getCardList(ICardListEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardList != null) {
                        parcel2.writeInt(1);
                        cardList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 38:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardAdditionalInfoList = getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardAdditionalInfoList != null) {
                        parcel2.writeInt(1);
                        cardAdditionalInfoList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 39:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoIssueCard = issueCard(parcel.readString(), ICardIssueEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoIssueCard != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoIssueCard.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 40:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoEnable = enable(parcel.readInt() != 0 ? CardInfo.CREATOR.createFromParcel(parcel) : null, ICardEnableEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoEnable != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoEnable.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 41:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoDisable = disable(parcel.readInt() != 0 ? CardInfo.CREATOR.createFromParcel(parcel) : null, ICardDisableEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoDisable != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoDisable.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 42:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoDelete = delete(parcel.readInt() != 0 ? CardInfo.CREATOR.createFromParcel(parcel) : null, parcel.readString(), ICardDeleteEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoDelete != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoDelete.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 43:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoCancelCardOperation = cancelCardOperation();
                    parcel2.writeNoException();
                    if (felicaResultInfoCancelCardOperation != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoCancelCardOperation.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 44:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoStart = start(parcel.readString(), parcel.readInt() != 0 ? DeviceList.CREATOR.createFromParcel(parcel) : null, IFSCEventListener.Stub.asInterface(parcel.readStrongBinder()), asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoStart != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoStart.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 45:
                    parcel.enforceInterface(DESCRIPTOR);
                    stop();
                    parcel2.writeNoException();
                    return true;
                case 46:
                    parcel.enforceInterface(DESCRIPTOR);
                    notifyResult(parcel.createByteArray());
                    parcel2.writeNoException();
                    return true;
                case 47:
                    parcel.enforceInterface(DESCRIPTOR);
                    notifyError(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 48:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoSilentStartForMfiAdmin = silentStartForMfiAdmin(parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readInt(), ISilentStartForMfiAdminEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoSilentStartForMfiAdmin != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSilentStartForMfiAdmin.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 49:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo linkageDataList = getLinkageDataList(parcel.readInt(), parcel.createStringArray(), ILinkageDataListEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (linkageDataList != null) {
                        parcel2.writeInt(1);
                        linkageDataList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 50:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoInitialize = initialize(parcel.readString(), IInitializedEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoInitialize != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoInitialize.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 51:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardListV2 = getCardListV2(IPipeEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardListV2 != null) {
                        parcel2.writeInt(1);
                        cardListV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 52:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardAdditionalInfoListV2 = getCardAdditionalInfoListV2(IPipeEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardAdditionalInfoListV2 != null) {
                        parcel2.writeInt(1);
                        cardAdditionalInfoListV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 53:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoSilentStart = silentStart(parcel.readString(), parcel.readString(), parcel.readInt(), ISilentStartEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoSilentStart != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSilentStart.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 54:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt felicaResultInfoIntCheckChipFormatting = checkChipFormatting();
                    parcel2.writeNoException();
                    if (felicaResultInfoIntCheckChipFormatting != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoIntCheckChipFormatting.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 55:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoIssueCardV2 = issueCardV2(parcel.readString(), ICardIssueV2EventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoIssueCardV2 != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoIssueCardV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 56:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoEnableV2 = enableV2(parcel.readString(), ICardEnableV2EventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoEnableV2 != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoEnableV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 57:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoDisableV2 = disableV2(parcel.readString(), ICardDisableV2EventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoDisableV2 != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoDisableV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 58:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoDeleteV2 = deleteV2(parcel.readString(), parcel.readString(), ICardDeleteV2EventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoDeleteV2 != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoDeleteV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 59:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoIssueCardWithOtp = issueCardWithOtp(parcel.readString(), parcel.readString(), ICardIssueV2EventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoIssueCardWithOtp != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoIssueCardWithOtp.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 60:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoAccess = access(parcel.readString(), parcel.readString(), ICardAccessEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoAccess != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoAccess.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 61:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardInfoListWithSpStatus = getCardInfoListWithSpStatus(parcel.readString(), IPipeEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardInfoListWithSpStatus != null) {
                        parcel2.writeInt(1);
                        cardInfoListWithSpStatus.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 62:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardAdditionalInfoListWithCidList = getCardAdditionalInfoListWithCidList(parcel.createStringArray(), IPipeEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardAdditionalInfoListWithCidList != null) {
                        parcel2.writeInt(1);
                        cardAdditionalInfoListWithCidList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 63:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoSaveDelete = saveDelete(parcel.readString(), parcel.readString(), ICardReissuableDeleteEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoSaveDelete != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSaveDelete.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 64:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoSelectWithCid = selectWithCid(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    if (felicaResultInfoSelectWithCid != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSelectWithCid.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 65:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoStringArray localCidList = getLocalCidList();
                    parcel2.writeNoException();
                    if (localCidList != null) {
                        parcel2.writeInt(1);
                        localCidList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 66:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt felicaResultInfoIntIdentifyService = identifyService();
                    parcel2.writeNoException();
                    if (felicaResultInfoIntIdentifyService != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoIntIdentifyService.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 67:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoSilentStartForMfiAdminV2 = silentStartForMfiAdminV2(parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readInt(), parcel.readInt(), ISilentStartForMfiAdminEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoSilentStartForMfiAdminV2 != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSilentStartForMfiAdminV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 68:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt unsupportMfiService1CardPosition = getUnsupportMfiService1CardPosition();
                    parcel2.writeNoException();
                    if (unsupportMfiService1CardPosition != null) {
                        parcel2.writeInt(1);
                        unsupportMfiService1CardPosition.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 69:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoCancelMfiOffline = cancelMfiOffline();
                    parcel2.writeNoException();
                    if (felicaResultInfoCancelMfiOffline != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoCancelMfiOffline.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 70:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoStringArray localCidListV2 = getLocalCidListV2();
                    parcel2.writeNoException();
                    if (localCidListV2 != null) {
                        parcel2.writeInt(1);
                        localCidListV2.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 71:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardListV3 = getCardListV3(IDataListEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardListV3 != null) {
                        parcel2.writeInt(1);
                        cardListV3.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 72:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardInfoListWithSpStatusV3 = getCardInfoListWithSpStatusV3(parcel.readString(), IDataListEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardInfoListWithSpStatusV3 != null) {
                        parcel2.writeInt(1);
                        cardInfoListWithSpStatusV3.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 73:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cardAdditionalInfoListV3 = getCardAdditionalInfoListV3(parcel.createStringArray(), IDataListEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cardAdditionalInfoListV3 != null) {
                        parcel2.writeInt(1);
                        cardAdditionalInfoListV3.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 74:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoProvisionServerOperation = provisionServerOperation(IServerOperationEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoProvisionServerOperation != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoProvisionServerOperation.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 75:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoStringArray localPartialCardInfoList = getLocalPartialCardInfoList(parcel.readString());
                    parcel2.writeNoException();
                    if (localPartialCardInfoList != null) {
                        parcel2.writeInt(1);
                        localPartialCardInfoList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 76:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoExistUnsupportMfiService1Card = existUnsupportMfiService1Card(parcel.readString(), IUnsupportMfiService1CardExistEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoExistUnsupportMfiService1Card != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoExistUnsupportMfiService1Card.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 77:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoDeleteUnsupportMfiService1Card = deleteUnsupportMfiService1Card(parcel.readString(), IUnsupportMfiService1CardDeleteEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoDeleteUnsupportMfiService1Card != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoDeleteUnsupportMfiService1Card.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 78:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfoInt felicaResultInfoIntExistEmptySlot = existEmptySlot();
                    parcel2.writeNoException();
                    if (felicaResultInfoIntExistEmptySlot != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoIntExistEmptySlot.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 79:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo cachedCardList = getCachedCardList(IDataListEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (cachedCardList != null) {
                        parcel2.writeInt(1);
                        cachedCardList.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 80:
                    parcel.enforceInterface(DESCRIPTOR);
                    FelicaResultInfo felicaResultInfoEnableCachedCard = enableCachedCard(parcel.readString(), ICachedCardEnableEventCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    if (felicaResultInfoEnableCachedCard != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoEnableCachedCard.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IMfiFelica {
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo activateFelica(String str, IFelicaEventListener iFelicaEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iFelicaEventListener != null ? iFelicaEventListener.asBinder() : null);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] iArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoBlockCountInformationArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getContainerId(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getContainerIssueInformation(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getICCode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray getIDm() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getInterface() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getKeyVersion(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoNodeInformation getNodeInformation(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoNodeInformation.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoNodeInformation.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoBoolean getRFSState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoBoolean.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getSystemCode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoIntArray getSystemCodeList(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoIntArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo inactivateFelica() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo open() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (pushSegmentParcelableWrapper != null) {
                        parcelObtain.writeInt(1);
                        pushSegmentParcelableWrapper.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoDataArray read(BlockList blockList, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (blockList != null) {
                        parcelObtain.writeInt(1);
                        blockList.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoDataArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo reset() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo select(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo selectWithTarget(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedArray(privacySettingDataArr, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo write(BlockDataList blockDataList, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (blockDataList != null) {
                        parcelObtain.writeInt(1);
                        blockDataList.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iFelicaPushAppNotificationListener != null ? iFelicaPushAppNotificationListener.asBinder() : null);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setNodeCodeSize(int i, int i2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo checkOnlineAccess() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo setSelectTimeout(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getSelectTimeout() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo cancelOffline() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoByteArray executeFelicaCommand(byte[] bArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] iArr, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeIntArray(iArr);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoKeyInformationArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo login(String str, String str2, ILoginEventCallback iLoginEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iLoginEventCallback != null ? iLoginEventCallback.asBinder() : null);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoString getCurrentAccountHash() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoString.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo clearMfiAccount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoSeInfo getSeInfomation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoSeInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo logout(boolean z, ILogoutEventCallback iLogoutEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeStrongBinder(iLogoutEventCallback != null ? iLogoutEventCallback.asBinder() : null);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardList(ICardListEventCallback iCardListEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iCardListEventCallback != null ? iCardListEventCallback.asBinder() : null);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoList(ICardAdditionalInfoListEventCallback iCardAdditionalInfoListEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iCardAdditionalInfoListEventCallback != null ? iCardAdditionalInfoListEventCallback.asBinder() : null);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo issueCard(String str, ICardIssueEventCallback iCardIssueEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iCardIssueEventCallback != null ? iCardIssueEventCallback.asBinder() : null);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo enable(CardInfo cardInfo, ICardEnableEventCallback iCardEnableEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cardInfo != null) {
                        parcelObtain.writeInt(1);
                        cardInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iCardEnableEventCallback != null ? iCardEnableEventCallback.asBinder() : null);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo disable(CardInfo cardInfo, ICardDisableEventCallback iCardDisableEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cardInfo != null) {
                        parcelObtain.writeInt(1);
                        cardInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iCardDisableEventCallback != null ? iCardDisableEventCallback.asBinder() : null);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo delete(CardInfo cardInfo, String str, ICardDeleteEventCallback iCardDeleteEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (cardInfo != null) {
                        parcelObtain.writeInt(1);
                        cardInfo.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iCardDeleteEventCallback != null ? iCardDeleteEventCallback.asBinder() : null);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo cancelCardOperation() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo start(String str, DeviceList deviceList, IFSCEventListener iFSCEventListener, IMfiFelica iMfiFelica) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    if (deviceList != null) {
                        parcelObtain.writeInt(1);
                        deviceList.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iFSCEventListener != null ? iFSCEventListener.asBinder() : null);
                    parcelObtain.writeStrongBinder(iMfiFelica != null ? iMfiFelica.asBinder() : null);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public void notifyResult(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public void notifyError(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo silentStartForMfiAdmin(String str, String str2, boolean z, int i, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iSilentStartForMfiAdminEventCallback != null ? iSilentStartForMfiAdminEventCallback.asBinder() : null);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getLinkageDataList(int i, String[] strArr, ILinkageDataListEventCallback iLinkageDataListEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongBinder(iLinkageDataListEventCallback != null ? iLinkageDataListEventCallback.asBinder() : null);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo initialize(String str, IInitializedEventCallback iInitializedEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iInitializedEventCallback != null ? iInitializedEventCallback.asBinder() : null);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardListV2(IPipeEventCallback iPipeEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iPipeEventCallback != null ? iPipeEventCallback.asBinder() : null);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoListV2(IPipeEventCallback iPipeEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iPipeEventCallback != null ? iPipeEventCallback.asBinder() : null);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo silentStart(String str, String str2, int i, ISilentStartEventCallback iSilentStartEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStrongBinder(iSilentStartEventCallback != null ? iSilentStartEventCallback.asBinder() : null);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt checkChipFormatting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo issueCardV2(String str, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iCardIssueV2EventCallback != null ? iCardIssueV2EventCallback.asBinder() : null);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo enableV2(String str, ICardEnableV2EventCallback iCardEnableV2EventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iCardEnableV2EventCallback != null ? iCardEnableV2EventCallback.asBinder() : null);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo disableV2(String str, ICardDisableV2EventCallback iCardDisableV2EventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iCardDisableV2EventCallback != null ? iCardDisableV2EventCallback.asBinder() : null);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo deleteV2(String str, String str2, ICardDeleteV2EventCallback iCardDeleteV2EventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iCardDeleteV2EventCallback != null ? iCardDeleteV2EventCallback.asBinder() : null);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo issueCardWithOtp(String str, String str2, ICardIssueV2EventCallback iCardIssueV2EventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iCardIssueV2EventCallback != null ? iCardIssueV2EventCallback.asBinder() : null);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo access(String str, String str2, ICardAccessEventCallback iCardAccessEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iCardAccessEventCallback != null ? iCardAccessEventCallback.asBinder() : null);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardInfoListWithSpStatus(String str, IPipeEventCallback iPipeEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iPipeEventCallback != null ? iPipeEventCallback.asBinder() : null);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoListWithCidList(String[] strArr, IPipeEventCallback iPipeEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongBinder(iPipeEventCallback != null ? iPipeEventCallback.asBinder() : null);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo saveDelete(String str, String str2, ICardReissuableDeleteEventCallback iCardReissuableDeleteEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeStrongBinder(iCardReissuableDeleteEventCallback != null ? iCardReissuableDeleteEventCallback.asBinder() : null);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo selectWithCid(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoStringArray getLocalCidList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoStringArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt identifyService() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo silentStartForMfiAdminV2(String str, String str2, boolean z, int i, int i2, ISilentStartForMfiAdminEventCallback iSilentStartForMfiAdminEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongBinder(iSilentStartForMfiAdminEventCallback != null ? iSilentStartForMfiAdminEventCallback.asBinder() : null);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt getUnsupportMfiService1CardPosition() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo cancelMfiOffline() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoStringArray getLocalCidListV2() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoStringArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardListV3(IDataListEventCallback iDataListEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iDataListEventCallback != null ? iDataListEventCallback.asBinder() : null);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardInfoListWithSpStatusV3(String str, IDataListEventCallback iDataListEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iDataListEventCallback != null ? iDataListEventCallback.asBinder() : null);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCardAdditionalInfoListV3(String[] strArr, IDataListEventCallback iDataListEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongBinder(iDataListEventCallback != null ? iDataListEventCallback.asBinder() : null);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo provisionServerOperation(IServerOperationEventCallback iServerOperationEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iServerOperationEventCallback != null ? iServerOperationEventCallback.asBinder() : null);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoStringArray getLocalPartialCardInfoList(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoStringArray.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo existUnsupportMfiService1Card(String str, IUnsupportMfiService1CardExistEventCallback iUnsupportMfiService1CardExistEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iUnsupportMfiService1CardExistEventCallback != null ? iUnsupportMfiService1CardExistEventCallback.asBinder() : null);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo deleteUnsupportMfiService1Card(String str, IUnsupportMfiService1CardDeleteEventCallback iUnsupportMfiService1CardDeleteEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iUnsupportMfiService1CardDeleteEventCallback != null ? iUnsupportMfiService1CardDeleteEventCallback.asBinder() : null);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfoInt existEmptySlot() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo getCachedCardList(IDataListEventCallback iDataListEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iDataListEventCallback != null ? iDataListEventCallback.asBinder() : null);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiFelica
            public FelicaResultInfo enableCachedCard(String str, ICachedCardEnableEventCallback iCachedCardEnableEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iCachedCardEnableEventCallback != null ? iCachedCardEnableEventCallback.asBinder() : null);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
