package com.felicanetworks.mfc.mfi;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface IGoogleTosGetEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IGoogleTosGetEventCallback";

    public static class Default implements IGoogleTosGetEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IGoogleTosGetEventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IGoogleTosGetEventCallback
        public void onSuccess(Intent intent) throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onSuccess(Intent intent) throws RemoteException;

    public static abstract class Stub extends Binder implements IGoogleTosGetEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IGoogleTosGetEventCallback.DESCRIPTOR);
        }

        public static IGoogleTosGetEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IGoogleTosGetEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGoogleTosGetEventCallback)) {
                return (IGoogleTosGetEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IGoogleTosGetEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGoogleTosGetEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess((Intent) _Parcel.readTypedObject(data, Intent.CREATOR));
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IGoogleTosGetEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGoogleTosGetEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.IGoogleTosGetEventCallback
            public void onSuccess(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGoogleTosGetEventCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, intent, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IGoogleTosGetEventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGoogleTosGetEventCallback.DESCRIPTOR);
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
