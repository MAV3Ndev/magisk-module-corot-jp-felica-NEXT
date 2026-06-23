package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public interface IRemainedCardsEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback";

    public static class Default implements IRemainedCardsEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback
        public void onError(int type, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback
        public void onSuccess(List<String> cardInfoJsonStringListToDelete, List<String> cardInfoJsonStringListToDisable) throws RemoteException {
        }
    }

    void onError(int type, String msg) throws RemoteException;

    void onSuccess(List<String> cardInfoJsonStringListToDelete, List<String> cardInfoJsonStringListToDisable) throws RemoteException;

    public static abstract class Stub extends Binder implements IRemainedCardsEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IRemainedCardsEventCallback.DESCRIPTOR);
        }

        public static IRemainedCardsEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IRemainedCardsEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IRemainedCardsEventCallback)) {
                return (IRemainedCardsEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IRemainedCardsEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IRemainedCardsEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess(data.createStringArrayList(), data.createStringArrayList());
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IRemainedCardsEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IRemainedCardsEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback
            public void onSuccess(List<String> cardInfoJsonStringListToDelete, List<String> cardInfoJsonStringListToDisable) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemainedCardsEventCallback.DESCRIPTOR);
                    parcelObtain.writeStringList(cardInfoJsonStringListToDelete);
                    parcelObtain.writeStringList(cardInfoJsonStringListToDisable);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IRemainedCardsEventCallback
            public void onError(int type, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IRemainedCardsEventCallback.DESCRIPTOR);
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
