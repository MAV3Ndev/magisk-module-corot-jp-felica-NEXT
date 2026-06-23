package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ICardKeyMigrateEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback";

    public static class Default implements ICardKeyMigrateEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback
        public void onError(int type, String msg, String unfinishedCardInfoJson) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback
        public void onSuccess(String migratedCardInfoJson) throws RemoteException {
        }
    }

    void onError(int type, String msg, String unfinishedCardInfoJson) throws RemoteException;

    void onSuccess(String migratedCardInfoJson) throws RemoteException;

    public static abstract class Stub extends Binder implements ICardKeyMigrateEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ICardKeyMigrateEventCallback.DESCRIPTOR);
        }

        public static ICardKeyMigrateEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(ICardKeyMigrateEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICardKeyMigrateEventCallback)) {
                return (ICardKeyMigrateEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICardKeyMigrateEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICardKeyMigrateEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess(data.readString());
            } else if (code == 2) {
                onError(data.readInt(), data.readString(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements ICardKeyMigrateEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICardKeyMigrateEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback
            public void onSuccess(String migratedCardInfoJson) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardKeyMigrateEventCallback.DESCRIPTOR);
                    parcelObtain.writeString(migratedCardInfoJson);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ICardKeyMigrateEventCallback
            public void onError(int type, String msg, String unfinishedCardInfoJson) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardKeyMigrateEventCallback.DESCRIPTOR);
                    parcelObtain.writeInt(type);
                    parcelObtain.writeString(msg);
                    parcelObtain.writeString(unfinishedCardInfoJson);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
