package com.felicanetworks.mfm.messenger;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface IGetTokenResult extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfm.messenger.IGetTokenResult";

    public static class Default implements IGetTokenResult {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfm.messenger.IGetTokenResult
        public String getToken() throws RemoteException {
            return null;
        }
    }

    String getToken() throws RemoteException;

    public static abstract class Stub extends Binder implements IGetTokenResult {
        static final int TRANSACTION_getToken = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IGetTokenResult.DESCRIPTOR);
        }

        public static IGetTokenResult asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IGetTokenResult.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IGetTokenResult)) {
                return (IGetTokenResult) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGetTokenResult.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGetTokenResult.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String token = getToken();
                parcel2.writeNoException();
                parcel2.writeString(token);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IGetTokenResult {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGetTokenResult.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfm.messenger.IGetTokenResult
            public String getToken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IGetTokenResult.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
