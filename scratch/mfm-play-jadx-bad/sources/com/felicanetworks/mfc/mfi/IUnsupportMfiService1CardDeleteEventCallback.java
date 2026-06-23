package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface IUnsupportMfiService1CardDeleteEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback";

    public static class Default implements IUnsupportMfiService1CardDeleteEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback
        public void onError(int type, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback
        public void onSuccess() throws RemoteException {
        }
    }

    void onError(int type, String msg) throws RemoteException;

    void onSuccess() throws RemoteException;

    public static abstract class Stub extends Binder implements IUnsupportMfiService1CardDeleteEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IUnsupportMfiService1CardDeleteEventCallback.DESCRIPTOR);
        }

        public static IUnsupportMfiService1CardDeleteEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IUnsupportMfiService1CardDeleteEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUnsupportMfiService1CardDeleteEventCallback)) {
                return (IUnsupportMfiService1CardDeleteEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IUnsupportMfiService1CardDeleteEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUnsupportMfiService1CardDeleteEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess();
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IUnsupportMfiService1CardDeleteEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnsupportMfiService1CardDeleteEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback
            public void onSuccess() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUnsupportMfiService1CardDeleteEventCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardDeleteEventCallback
            public void onError(int type, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUnsupportMfiService1CardDeleteEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(type);
                    parcelObtain.writeString(msg);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
