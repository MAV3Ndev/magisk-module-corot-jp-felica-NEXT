package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ICardDisableEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.ICardDisableEventCallback";

    public static class Default implements ICardDisableEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.ICardDisableEventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardDisableEventCallback
        public void onSuccess(CardInfo cardInfo) throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onSuccess(CardInfo cardInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ICardDisableEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ICardDisableEventCallback.DESCRIPTOR);
        }

        public static ICardDisableEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(ICardDisableEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICardDisableEventCallback)) {
                return (ICardDisableEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICardDisableEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICardDisableEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess((CardInfo) _Parcel.readTypedObject(data, CardInfo.CREATOR));
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements ICardDisableEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICardDisableEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.ICardDisableEventCallback
            public void onSuccess(CardInfo cardInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardDisableEventCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, cardInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ICardDisableEventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardDisableEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(id);
                    parcelObtain.writeString(msg);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
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
