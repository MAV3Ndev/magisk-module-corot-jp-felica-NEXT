package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface IUnsupportMfiService1CardExistEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback";

    public static class Default implements IUnsupportMfiService1CardExistEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback
        public void onError(int type, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback
        public void onSuccess(boolean exist, String localPartialCardInfoJson) throws RemoteException {
        }
    }

    void onError(int type, String msg) throws RemoteException;

    void onSuccess(boolean exist, String localPartialCardInfoJson) throws RemoteException;

    public static abstract class Stub extends Binder implements IUnsupportMfiService1CardExistEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IUnsupportMfiService1CardExistEventCallback.DESCRIPTOR);
        }

        public static IUnsupportMfiService1CardExistEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IUnsupportMfiService1CardExistEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IUnsupportMfiService1CardExistEventCallback)) {
                return (IUnsupportMfiService1CardExistEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IUnsupportMfiService1CardExistEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IUnsupportMfiService1CardExistEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess(data.readInt() != 0, data.readString());
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IUnsupportMfiService1CardExistEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IUnsupportMfiService1CardExistEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback
            public void onSuccess(boolean z, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUnsupportMfiService1CardExistEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback
            public void onError(int type, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IUnsupportMfiService1CardExistEventCallback.DESCRIPTOR);
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
