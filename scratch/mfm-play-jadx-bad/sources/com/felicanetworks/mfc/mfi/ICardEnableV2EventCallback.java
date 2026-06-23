package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ICardEnableV2EventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback";

    public static class Default implements ICardEnableV2EventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback
        public void onSuccess(String enableCardInfoJson, String disableCardInfoJson) throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onSuccess(String enableCardInfoJson, String disableCardInfoJson) throws RemoteException;

    public static abstract class Stub extends Binder implements ICardEnableV2EventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ICardEnableV2EventCallback.DESCRIPTOR);
        }

        public static ICardEnableV2EventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(ICardEnableV2EventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICardEnableV2EventCallback)) {
                return (ICardEnableV2EventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICardEnableV2EventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICardEnableV2EventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess(data.readString(), data.readString());
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements ICardEnableV2EventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICardEnableV2EventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback
            public void onSuccess(String enableCardInfoJson, String disableCardInfoJson) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardEnableV2EventCallback.DESCRIPTOR);
                    parcelObtain.writeString(enableCardInfoJson);
                    parcelObtain.writeString(disableCardInfoJson);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ICardEnableV2EventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardEnableV2EventCallback.DESCRIPTOR);
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
