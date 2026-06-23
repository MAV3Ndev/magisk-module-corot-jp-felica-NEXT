package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.felicanetworks.mfc.IFSCEventListener;
import com.felicanetworks.mfc.IFelica;

/* JADX INFO: loaded from: classes3.dex */
public interface IFSC extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.IFSC";

    public static class Default implements IFSC {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFSC
        public void notifyError(String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.IFSC
        public void notifyResult(byte[] result) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.IFSC
        public FelicaResultInfo start(String url, DeviceList deviceList, IFSCEventListener fscEventListener, IFelica felica) throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFSC
        public void stop() throws RemoteException {
        }
    }

    void notifyError(String msg) throws RemoteException;

    void notifyResult(byte[] result) throws RemoteException;

    FelicaResultInfo start(String url, DeviceList deviceList, IFSCEventListener fscEventListener, IFelica felica) throws RemoteException;

    void stop() throws RemoteException;

    public static abstract class Stub extends Binder implements IFSC {
        static final int TRANSACTION_notifyError = 4;
        static final int TRANSACTION_notifyResult = 3;
        static final int TRANSACTION_start = 1;
        static final int TRANSACTION_stop = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IFSC.DESCRIPTOR);
        }

        public static IFSC asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IFSC.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFSC)) {
                return (IFSC) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IFSC.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFSC.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                FelicaResultInfo felicaResultInfoStart = start(data.readString(), (DeviceList) _Parcel.readTypedObject(data, DeviceList.CREATOR), IFSCEventListener.Stub.asInterface(data.readStrongBinder()), IFelica.Stub.asInterface(data.readStrongBinder()));
                reply.writeNoException();
                _Parcel.writeTypedObject(reply, felicaResultInfoStart, 1);
            } else if (code == 2) {
                stop();
                reply.writeNoException();
            } else if (code == 3) {
                notifyResult(data.createByteArray());
                reply.writeNoException();
            } else if (code == 4) {
                notifyError(data.readString());
                reply.writeNoException();
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IFSC {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFSC.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.IFSC
            public FelicaResultInfo start(String url, DeviceList deviceList, IFSCEventListener fscEventListener, IFelica felica) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFSC.DESCRIPTOR);
                    parcelObtain.writeString(url);
                    _Parcel.writeTypedObject(parcelObtain, deviceList, 0);
                    parcelObtain.writeStrongInterface(fscEventListener);
                    parcelObtain.writeStrongInterface(felica);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (FelicaResultInfo) _Parcel.readTypedObject(parcelObtain2, FelicaResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFSC
            public void stop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFSC.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFSC
            public void notifyResult(byte[] result) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFSC.DESCRIPTOR);
                    parcelObtain.writeByteArray(result);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFSC
            public void notifyError(String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFSC.DESCRIPTOR);
                    parcelObtain.writeString(msg);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
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
