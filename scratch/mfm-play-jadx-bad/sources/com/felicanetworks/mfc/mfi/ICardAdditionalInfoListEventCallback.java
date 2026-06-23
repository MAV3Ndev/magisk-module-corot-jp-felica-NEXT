package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ICardAdditionalInfoListEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback";

    public static class Default implements ICardAdditionalInfoListEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback
        public void onSuccess(CardAdditionalInfo[] cards) throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onSuccess(CardAdditionalInfo[] cards) throws RemoteException;

    public static abstract class Stub extends Binder implements ICardAdditionalInfoListEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ICardAdditionalInfoListEventCallback.DESCRIPTOR);
        }

        public static ICardAdditionalInfoListEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(ICardAdditionalInfoListEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ICardAdditionalInfoListEventCallback)) {
                return (ICardAdditionalInfoListEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(ICardAdditionalInfoListEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICardAdditionalInfoListEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess((CardAdditionalInfo[]) data.createTypedArray(CardAdditionalInfo.CREATOR));
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements ICardAdditionalInfoListEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICardAdditionalInfoListEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback
            public void onSuccess(CardAdditionalInfo[] cards) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardAdditionalInfoListEventCallback.DESCRIPTOR);
                    parcelObtain.writeTypedArray(cards, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.ICardAdditionalInfoListEventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ICardAdditionalInfoListEventCallback.DESCRIPTOR);
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
