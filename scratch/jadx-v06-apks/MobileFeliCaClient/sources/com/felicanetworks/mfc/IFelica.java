package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;

/* JADX INFO: loaded from: classes.dex */
public interface IFelica extends IInterface {
    FelicaResultInfo activateFelica(String[] strArr, IFelicaEventListener iFelicaEventListener) throws RemoteException;

    FelicaResultInfo cancelOffline() throws RemoteException;

    FelicaResultInfo checkOnlineAccess() throws RemoteException;

    FelicaResultInfo close() throws RemoteException;

    FelicaResultInfoByteArray executeFelicaCommand(byte[] bArr, int i, int i2) throws RemoteException;

    FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] iArr, int i, int i2) throws RemoteException;

    FelicaResultInfoByteArray getContainerId(int i, int i2) throws RemoteException;

    FelicaResultInfoByteArray getContainerIssueInformation(int i, int i2) throws RemoteException;

    FelicaResultInfoByteArray getICCode() throws RemoteException;

    FelicaResultInfoByteArray getIDm() throws RemoteException;

    FelicaResultInfoInt getInterface() throws RemoteException;

    FelicaResultInfoInt getKeyVersion(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] iArr, int i, int i2) throws RemoteException;

    FelicaResultInfoNodeInformation getNodeInformation(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfoNodeInformation getPrivacyNodeInformation(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfoBoolean getRFSState() throws RemoteException;

    FelicaResultInfoInt getSelectTimeout() throws RemoteException;

    FelicaResultInfoInt getSystemCode() throws RemoteException;

    FelicaResultInfoIntArray getSystemCodeList(int i, int i2) throws RemoteException;

    FelicaResultInfo inactivateFelica() throws RemoteException;

    FelicaResultInfo open() throws RemoteException;

    FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException;

    FelicaResultInfoDataArray read(BlockList blockList, int i, int i2) throws RemoteException;

    FelicaResultInfo reset() throws RemoteException;

    FelicaResultInfo select(int i) throws RemoteException;

    FelicaResultInfo selectWithCid(int i, String str) throws RemoteException;

    FelicaResultInfo selectWithTarget(int i, int i2) throws RemoteException;

    FelicaResultInfo setNodeCodeSize(int i, int i2, int i3) throws RemoteException;

    FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingDataArr, int i, int i2) throws RemoteException;

    FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener iFelicaPushAppNotificationListener, String str) throws RemoteException;

    FelicaResultInfo setSelectTimeout(int i) throws RemoteException;

    FelicaResultInfo write(BlockDataList blockDataList, int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements IFelica {
        private static final String DESCRIPTOR = "com.felicanetworks.mfc.IFelica";
        static final int TRANSACTION_activateFelica = 1;
        static final int TRANSACTION_cancelOffline = 29;
        static final int TRANSACTION_checkOnlineAccess = 26;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_executeFelicaCommand = 30;
        static final int TRANSACTION_getBlockCountInformation = 3;
        static final int TRANSACTION_getContainerId = 4;
        static final int TRANSACTION_getContainerIssueInformation = 5;
        static final int TRANSACTION_getICCode = 6;
        static final int TRANSACTION_getIDm = 7;
        static final int TRANSACTION_getInterface = 8;
        static final int TRANSACTION_getKeyVersion = 9;
        static final int TRANSACTION_getKeyVersionV2 = 31;
        static final int TRANSACTION_getNodeInformation = 10;
        static final int TRANSACTION_getPrivacyNodeInformation = 11;
        static final int TRANSACTION_getRFSState = 12;
        static final int TRANSACTION_getSelectTimeout = 28;
        static final int TRANSACTION_getSystemCode = 13;
        static final int TRANSACTION_getSystemCodeList = 14;
        static final int TRANSACTION_inactivateFelica = 15;
        static final int TRANSACTION_open = 16;
        static final int TRANSACTION_push = 17;
        static final int TRANSACTION_read = 18;
        static final int TRANSACTION_reset = 19;
        static final int TRANSACTION_select = 20;
        static final int TRANSACTION_selectWithCid = 32;
        static final int TRANSACTION_selectWithTarget = 21;
        static final int TRANSACTION_setNodeCodeSize = 25;
        static final int TRANSACTION_setPrivacy = 22;
        static final int TRANSACTION_setPushNotificationListener = 24;
        static final int TRANSACTION_setSelectTimeout = 27;
        static final int TRANSACTION_write = 23;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFelica asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFelica)) {
                return (IFelica) iInterfaceQueryLocalInterface;
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
                    FelicaResultInfo felicaResultInfoActivateFelica = activateFelica(parcel.createStringArray(), IFelicaEventListener.Stub.asInterface(parcel.readStrongBinder()));
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
                    FelicaResultInfo felicaResultInfoSelectWithCid = selectWithCid(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    if (felicaResultInfoSelectWithCid != null) {
                        parcel2.writeInt(1);
                        felicaResultInfoSelectWithCid.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IFelica {
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

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo activateFelica(String[] strArr, IFelicaEventListener iFelicaEventListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeStrongBinder(iFelicaEventListener != null ? iFelicaEventListener.asBinder() : null);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
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

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo selectWithCid(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
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
