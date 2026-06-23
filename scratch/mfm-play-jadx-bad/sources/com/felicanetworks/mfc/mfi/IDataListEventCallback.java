package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface IDataListEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IDataListEventCallback";

    public static class Default implements IDataListEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onFinished(int size) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
        public void onPart(List<String> partialDataList, boolean more) throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onFinished(int size) throws RemoteException;

    void onPart(List<String> partialDataList, boolean more) throws RemoteException;

    public static abstract class Stub extends Binder implements IDataListEventCallback {
        static final int TRANSACTION_onError = 3;
        static final int TRANSACTION_onFinished = 2;
        static final int TRANSACTION_onPart = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IDataListEventCallback.DESCRIPTOR);
        }

        public static IDataListEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IDataListEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IDataListEventCallback)) {
                return (IDataListEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IDataListEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDataListEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onPart(data.createStringArrayList(), data.readInt() != 0);
                reply.writeNoException();
            } else if (code == 2) {
                onFinished(data.readInt());
            } else if (code == 3) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IDataListEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDataListEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
            public void onPart(List<String> list, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDataListEventCallback.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
            public void onFinished(int size) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDataListEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(size);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IDataListEventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IDataListEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(id);
                    parcelObtain.writeString(msg);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
