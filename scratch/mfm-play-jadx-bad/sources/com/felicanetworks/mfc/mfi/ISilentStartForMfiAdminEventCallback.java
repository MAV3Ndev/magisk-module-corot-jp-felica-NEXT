package com.felicanetworks.mfc.mfi;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ISilentStartForMfiAdminEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback";

    public static class Default implements ISilentStartForMfiAdminEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onRequestActivity(Intent intent) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onSuccess() throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
        public void onSuccessNoLogin() throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onRequestActivity(Intent intent) throws RemoteException;

    void onSuccess() throws RemoteException;

    void onSuccessNoLogin() throws RemoteException;

    public static abstract class Stub extends Binder implements ISilentStartForMfiAdminEventCallback {
        static final int TRANSACTION_onError = 4;
        static final int TRANSACTION_onRequestActivity = 3;
        static final int TRANSACTION_onSuccess = 1;
        static final int TRANSACTION_onSuccessNoLogin = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
        }

        public static ISilentStartForMfiAdminEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISilentStartForMfiAdminEventCallback)) {
                return (ISilentStartForMfiAdminEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess();
            } else if (code == 2) {
                onSuccessNoLogin();
            } else if (code == 3) {
                onRequestActivity((Intent) _Parcel.readTypedObject(data, Intent.CREATOR));
            } else if (code == 4) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements ISilentStartForMfiAdminEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISilentStartForMfiAdminEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
            public void onSuccess() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
            public void onSuccessNoLogin() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
            public void onRequestActivity(Intent intent) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, intent, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ISilentStartForMfiAdminEventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISilentStartForMfiAdminEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(id);
                    parcelObtain.writeString(msg);
                    this.mRemote.transact(4, parcelObtain, null, 1);
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
