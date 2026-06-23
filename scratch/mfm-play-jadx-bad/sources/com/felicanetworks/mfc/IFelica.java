package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFelicaEventListener;
import com.felicanetworks.mfc.IFelicaPushAppNotificationListener;

/* JADX INFO: loaded from: classes3.dex */
public interface IFelica extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.IFelica";

    public static class Default implements IFelica {
        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo activateFelica(String[] permitList, IFelicaEventListener listener) throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo cancelOffline() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo checkOnlineAccess() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo close() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoByteArray executeFelicaCommand(byte[] commandPacket, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoByteArray getContainerId(int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoByteArray getICCode() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoByteArray getIDm() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoInt getInterface() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoBoolean getRFSState() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoInt getSelectTimeout() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoInt getSystemCode() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo inactivateFelica() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo open() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfoDataArray read(BlockList blockList, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo reset() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo select(int systemCode) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo selectWithCid(int systemCode, String cid) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo selectWithTarget(int target, int systemCode) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retry) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo setSelectTimeout(int timeout) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelica
        public FelicaResultInfo write(BlockDataList blockDataList, int timeout, int retry) throws RemoteException {
            return null;
        }
    }

    FelicaResultInfo activateFelica(String[] permitList, IFelicaEventListener listener) throws RemoteException;

    FelicaResultInfo cancelOffline() throws RemoteException;

    FelicaResultInfo checkOnlineAccess() throws RemoteException;

    FelicaResultInfo close() throws RemoteException;

    FelicaResultInfoByteArray executeFelicaCommand(byte[] commandPacket, int timeout, int retry) throws RemoteException;

    FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] nodeCodeList, int timeout, int retry) throws RemoteException;

    FelicaResultInfoByteArray getContainerId(int timeout, int retry) throws RemoteException;

    FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry) throws RemoteException;

    FelicaResultInfoByteArray getICCode() throws RemoteException;

    FelicaResultInfoByteArray getIDm() throws RemoteException;

    FelicaResultInfoInt getInterface() throws RemoteException;

    FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry) throws RemoteException;

    FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] nodeCodeList, int timeout, int retry) throws RemoteException;

    FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException;

    FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException;

    FelicaResultInfoBoolean getRFSState() throws RemoteException;

    FelicaResultInfoInt getSelectTimeout() throws RemoteException;

    FelicaResultInfoInt getSystemCode() throws RemoteException;

    FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry) throws RemoteException;

    FelicaResultInfo inactivateFelica() throws RemoteException;

    FelicaResultInfo open() throws RemoteException;

    FelicaResultInfo push(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) throws RemoteException;

    FelicaResultInfoDataArray read(BlockList blockList, int timeout, int retry) throws RemoteException;

    FelicaResultInfo reset() throws RemoteException;

    FelicaResultInfo select(int systemCode) throws RemoteException;

    FelicaResultInfo selectWithCid(int systemCode, String cid) throws RemoteException;

    FelicaResultInfo selectWithTarget(int target, int systemCode) throws RemoteException;

    FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry) throws RemoteException;

    FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retry) throws RemoteException;

    FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws RemoteException;

    FelicaResultInfo setSelectTimeout(int timeout) throws RemoteException;

    FelicaResultInfo write(BlockDataList blockDataList, int timeout, int retry) throws RemoteException;

    public static abstract class Stub extends Binder implements IFelica {
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
            attachInterface(this, IFelica.DESCRIPTOR);
        }

        public static IFelica asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IFelica.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFelica)) {
                return (IFelica) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IFelica.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFelica.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    FelicaResultInfo felicaResultInfoActivateFelica = activateFelica(data.createStringArray(), IFelicaEventListener.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoActivateFelica, 1);
                    return true;
                case 2:
                    FelicaResultInfo felicaResultInfoClose = close();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoClose, 1);
                    return true;
                case 3:
                    FelicaResultInfoBlockCountInformationArray blockCountInformation = getBlockCountInformation(data.createIntArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, blockCountInformation, 1);
                    return true;
                case 4:
                    FelicaResultInfoByteArray containerId = getContainerId(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, containerId, 1);
                    return true;
                case 5:
                    FelicaResultInfoByteArray containerIssueInformation = getContainerIssueInformation(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, containerIssueInformation, 1);
                    return true;
                case 6:
                    FelicaResultInfoByteArray iCCode = getICCode();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, iCCode, 1);
                    return true;
                case 7:
                    FelicaResultInfoByteArray iDm = getIDm();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, iDm, 1);
                    return true;
                case 8:
                    FelicaResultInfoInt felicaResultInfoInt = getInterface();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoInt, 1);
                    return true;
                case 9:
                    FelicaResultInfoInt keyVersion = getKeyVersion(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, keyVersion, 1);
                    return true;
                case 10:
                    FelicaResultInfoNodeInformation nodeInformation = getNodeInformation(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, nodeInformation, 1);
                    return true;
                case 11:
                    FelicaResultInfoNodeInformation privacyNodeInformation = getPrivacyNodeInformation(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, privacyNodeInformation, 1);
                    return true;
                case 12:
                    FelicaResultInfoBoolean rFSState = getRFSState();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, rFSState, 1);
                    return true;
                case 13:
                    FelicaResultInfoInt systemCode = getSystemCode();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, systemCode, 1);
                    return true;
                case 14:
                    FelicaResultInfoIntArray systemCodeList = getSystemCodeList(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, systemCodeList, 1);
                    return true;
                case 15:
                    FelicaResultInfo felicaResultInfoInactivateFelica = inactivateFelica();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoInactivateFelica, 1);
                    return true;
                case 16:
                    FelicaResultInfo felicaResultInfoOpen = open();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoOpen, 1);
                    return true;
                case 17:
                    FelicaResultInfo felicaResultInfoPush = push((PushSegmentParcelableWrapper) _Parcel.readTypedObject(data, PushSegmentParcelableWrapper.CREATOR));
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoPush, 1);
                    return true;
                case 18:
                    FelicaResultInfoDataArray felicaResultInfoDataArray = read((BlockList) _Parcel.readTypedObject(data, BlockList.CREATOR), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoDataArray, 1);
                    return true;
                case 19:
                    FelicaResultInfo felicaResultInfoReset = reset();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoReset, 1);
                    return true;
                case 20:
                    FelicaResultInfo felicaResultInfoSelect = select(data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSelect, 1);
                    return true;
                case 21:
                    FelicaResultInfo felicaResultInfoSelectWithTarget = selectWithTarget(data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSelectWithTarget, 1);
                    return true;
                case 22:
                    FelicaResultInfo privacy = setPrivacy((PrivacySettingData[]) data.createTypedArray(PrivacySettingData.CREATOR), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, privacy, 1);
                    return true;
                case 23:
                    FelicaResultInfo felicaResultInfoWrite = write((BlockDataList) _Parcel.readTypedObject(data, BlockDataList.CREATOR), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoWrite, 1);
                    return true;
                case 24:
                    FelicaResultInfo pushNotificationListener = setPushNotificationListener(IFelicaPushAppNotificationListener.Stub.asInterface(data.readStrongBinder()), data.readString());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, pushNotificationListener, 1);
                    return true;
                case 25:
                    FelicaResultInfo nodeCodeSize = setNodeCodeSize(data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, nodeCodeSize, 1);
                    return true;
                case 26:
                    FelicaResultInfo felicaResultInfoCheckOnlineAccess = checkOnlineAccess();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoCheckOnlineAccess, 1);
                    return true;
                case 27:
                    FelicaResultInfo selectTimeout = setSelectTimeout(data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, selectTimeout, 1);
                    return true;
                case 28:
                    FelicaResultInfoInt selectTimeout2 = getSelectTimeout();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, selectTimeout2, 1);
                    return true;
                case 29:
                    FelicaResultInfo felicaResultInfoCancelOffline = cancelOffline();
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoCancelOffline, 1);
                    return true;
                case 30:
                    FelicaResultInfoByteArray felicaResultInfoByteArrayExecuteFelicaCommand = executeFelicaCommand(data.createByteArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoByteArrayExecuteFelicaCommand, 1);
                    return true;
                case 31:
                    FelicaResultInfoKeyInformationArray keyVersionV2 = getKeyVersionV2(data.createIntArray(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, keyVersionV2, 1);
                    return true;
                case 32:
                    FelicaResultInfo felicaResultInfoSelectWithCid = selectWithCid(data.readInt(), data.readString());
                    reply.writeNoException();
                    _Parcel.writeTypedObject(reply, felicaResultInfoSelectWithCid, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IFelica {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFelica.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo activateFelica(String[] permitList, IFelicaEventListener listener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeStringArray(permitList);
                    parcelObtain.writeStrongInterface(listener);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoBlockCountInformationArray getBlockCountInformation(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeIntArray(nodeCodeList);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBlockCountInformationArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBlockCountInformationArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoByteArray getContainerId(int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoByteArray getContainerIssueInformation(int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoInt getKeyVersion(int serviceCode, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(serviceCode);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoNodeInformation getNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(parentAreaCode);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoNodeInformation) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoNodeInformation.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoNodeInformation getPrivacyNodeInformation(int parentAreaCode, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(parentAreaCode);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoNodeInformation) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoNodeInformation.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoBoolean) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoBoolean.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoIntArray getSystemCodeList(int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoIntArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoIntArray.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, pushSegmentParcelableWrapper, 0);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoDataArray read(BlockList blockList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, blockList, 0);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoDataArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoDataArray.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo select(int systemCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(systemCode);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo selectWithTarget(int target, int systemCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(target);
                    parcelObtain.writeInt(systemCode);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo setPrivacy(PrivacySettingData[] privacySettingData, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeTypedArray(privacySettingData, 0);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo write(BlockDataList blockDataList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, blockDataList, 0);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo setPushNotificationListener(IFelicaPushAppNotificationListener listener, String appIdentificationCode) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(listener);
                    parcelObtain.writeString(appIdentificationCode);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo setNodeCodeSize(int nodeCodeSize, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(nodeCodeSize);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo setSelectTimeout(int timeout) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(timeout);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoInt) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoInt.CREATOR);
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
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoByteArray executeFelicaCommand(byte[] commandPacket, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeByteArray(commandPacket);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoByteArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoByteArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfoKeyInformationArray getKeyVersionV2(int[] nodeCodeList, int timeout, int retry) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeIntArray(nodeCodeList);
                    parcelObtain.writeInt(timeout);
                    parcelObtain.writeInt(retry);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfoKeyInformationArray) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfoKeyInformationArray.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelica
            public FelicaResultInfo selectWithCid(int systemCode, String cid) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelica.DESCRIPTOR);
                    parcelObtain.writeInt(systemCode);
                    parcelObtain.writeString(cid);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> c) {
            if (parcel.readInt() != 0) {
                return c.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T value, int parcelableFlags) {
            if (value != null) {
                parcel.writeInt(1);
                value.writeToParcel(parcel, parcelableFlags);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
