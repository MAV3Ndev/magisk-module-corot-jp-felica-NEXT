package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ILinkageDataListEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback";

    public static class Default implements ILinkageDataListEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
        public void onSuccess(String[] linkageData) throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onSuccess(String[] linkageData) throws RemoteException;

    public static abstract class Stub extends Binder implements ILinkageDataListEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ILinkageDataListEventCallback.DESCRIPTOR);
        }

        public static ILinkageDataListEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(ILinkageDataListEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ILinkageDataListEventCallback)) {
                return (ILinkageDataListEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ILinkageDataListEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ILinkageDataListEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess(data.createStringArray());
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements ILinkageDataListEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ILinkageDataListEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
            public void onSuccess(String[] linkageData) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILinkageDataListEventCallback.DESCRIPTOR);
                    parcelObtain.writeStringArray(linkageData);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ILinkageDataListEventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ILinkageDataListEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(id);
                    parcelObtain.writeString(msg);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
