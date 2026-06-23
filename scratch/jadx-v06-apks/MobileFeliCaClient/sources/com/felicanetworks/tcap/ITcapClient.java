package com.felicanetworks.tcap;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.tcap.ITcapClientEventListener;

/* JADX INFO: loaded from: classes.dex */
public interface ITcapClient extends IInterface {
    void notifyError(String str) throws RemoteException;

    void notifyResult(byte[] bArr) throws RemoteException;

    FelicaResultInfo start(String str, DeviceList deviceList, ITcapClientEventListener iTcapClientEventListener, FelicaDeviceList felicaDeviceList, IBinder iBinder) throws RemoteException;

    void stop() throws RemoteException;

    public static abstract class Stub extends Binder implements ITcapClient {
        private static final String DESCRIPTOR = "com.felicanetworks.tcap.ITcapClient";
        static final int TRANSACTION_notifyError = 4;
        static final int TRANSACTION_notifyResult = 3;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITcapClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITcapClient)) {
                return (ITcapClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                FelicaResultInfo felicaResultInfoStart = start(parcel.readString(), parcel.readInt() != 0 ? DeviceList.CREATOR.createFromParcel(parcel) : null, ITcapClientEventListener.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt() != 0 ? FelicaDeviceList.CREATOR.createFromParcel(parcel) : null, parcel.readStrongBinder());
                parcel2.writeNoException();
                if (felicaResultInfoStart != null) {
                    parcel2.writeInt(1);
                    felicaResultInfoStart.writeToParcel(parcel2, 1);
                } else {
                    parcel2.writeInt(0);
                }
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                stop();
                parcel2.writeNoException();
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                notifyResult(parcel.createByteArray());
                parcel2.writeNoException();
                return true;
            }
            if (i != 4) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            notifyError(parcel.readString());
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements ITcapClient {
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

            @Override // com.felicanetworks.tcap.ITcapClient
            public FelicaResultInfo start(String str, DeviceList deviceList, ITcapClientEventListener iTcapClientEventListener, FelicaDeviceList felicaDeviceList, IBinder iBinder) throws RemoteException {
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
                    parcelObtain.writeStrongBinder(iTcapClientEventListener != null ? iTcapClientEventListener.asBinder() : null);
                    if (felicaDeviceList != null) {
                        parcelObtain.writeInt(1);
                        felicaDeviceList.writeToParcel(parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.tcap.ITcapClient
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.tcap.ITcapClient
            public void notifyResult(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.tcap.ITcapClient
            public void notifyError(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
