package com.felicanetworks.mfm.messenger;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.felicanetworks.mfm.messenger.IGetTokenResult;

/* JADX INFO: loaded from: classes3.dex */
public interface IFirebaseApi extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfm.messenger.IFirebaseApi";

    public static class Default implements IFirebaseApi {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
        public void deleteInstallId() throws RemoteException {
        }

        @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
        public IGetTokenResult getToken() throws RemoteException {
            return null;
        }

        @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
        public void initializeApp() throws RemoteException {
        }

        @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
        public boolean isAutoInitEnabled() throws RemoteException {
            return false;
        }

        @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
        public void setAutoInitEnabled(boolean z) throws RemoteException {
        }
    }

    void deleteInstallId() throws RemoteException;

    IGetTokenResult getToken() throws RemoteException;

    void initializeApp() throws RemoteException;

    boolean isAutoInitEnabled() throws RemoteException;

    void setAutoInitEnabled(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements IFirebaseApi {
        static final int TRANSACTION_deleteInstallId = 5;
        static final int TRANSACTION_getToken = 4;
        static final int TRANSACTION_initializeApp = 1;
        static final int TRANSACTION_isAutoInitEnabled = 2;
        static final int TRANSACTION_setAutoInitEnabled = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IFirebaseApi.DESCRIPTOR);
        }

        public static IFirebaseApi asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IFirebaseApi.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFirebaseApi)) {
                return (IFirebaseApi) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFirebaseApi.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFirebaseApi.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                initializeApp();
                parcel2.writeNoException();
            } else if (i == 2) {
                boolean zIsAutoInitEnabled = isAutoInitEnabled();
                parcel2.writeNoException();
                parcel2.writeInt(zIsAutoInitEnabled ? 1 : 0);
            } else if (i == 3) {
                setAutoInitEnabled(parcel.readInt() != 0);
                parcel2.writeNoException();
            } else if (i == 4) {
                IGetTokenResult token = getToken();
                parcel2.writeNoException();
                parcel2.writeStrongInterface(token);
            } else if (i == 5) {
                deleteInstallId();
                parcel2.writeNoException();
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IFirebaseApi {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFirebaseApi.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
            public void initializeApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirebaseApi.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
            public boolean isAutoInitEnabled() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirebaseApi.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
            public void setAutoInitEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirebaseApi.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
            public IGetTokenResult getToken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirebaseApi.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IGetTokenResult.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
            public void deleteInstallId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFirebaseApi.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }
}
