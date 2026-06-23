package com.felicanetworks.mfm.messenger;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.felicanetworks.mfm.messenger.IGetTokenResult;

/* JADX INFO: loaded from: classes.dex */
public interface IFirebaseApi extends IInterface {

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
        private static final String DESCRIPTOR = "com.felicanetworks.mfm.messenger.IFirebaseApi";
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
            attachInterface(this, DESCRIPTOR);
        }

        public static IFirebaseApi asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFirebaseApi)) {
                return (IFirebaseApi) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface(DESCRIPTOR);
                initializeApp();
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface(DESCRIPTOR);
                boolean zIsAutoInitEnabled = isAutoInitEnabled();
                parcel2.writeNoException();
                parcel2.writeInt(zIsAutoInitEnabled ? 1 : 0);
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface(DESCRIPTOR);
                setAutoInitEnabled(parcel.readInt() != 0);
                parcel2.writeNoException();
                return true;
            }
            if (i == 4) {
                parcel.enforceInterface(DESCRIPTOR);
                IGetTokenResult token = getToken();
                parcel2.writeNoException();
                parcel2.writeStrongBinder(token != null ? token.asBinder() : null);
                return true;
            }
            if (i != 5) {
                if (i == 1598968902) {
                    parcel2.writeString(DESCRIPTOR);
                    return true;
                }
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel.enforceInterface(DESCRIPTOR);
            deleteInstallId();
            parcel2.writeNoException();
            return true;
        }

        private static class Proxy implements IFirebaseApi {
            public static IFirebaseApi sDefaultImpl;
            private IBinder mRemote;

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.felicanetworks.mfm.messenger.IFirebaseApi
            public void initializeApp() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(1, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().initializeApp();
                    } else {
                        parcelObtain2.readException();
                    }
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
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(2, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().isAutoInitEnabled();
                    }
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
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(z ? 1 : 0);
                    if (!this.mRemote.transact(3, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().setAutoInitEnabled(z);
                    } else {
                        parcelObtain2.readException();
                    }
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
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(4, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        return Stub.getDefaultImpl().getToken();
                    }
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
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (!this.mRemote.transact(5, parcelObtain, parcelObtain2, 0) && Stub.getDefaultImpl() != null) {
                        Stub.getDefaultImpl().deleteInstallId();
                    } else {
                        parcelObtain2.readException();
                    }
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static boolean setDefaultImpl(IFirebaseApi iFirebaseApi) {
            if (Proxy.sDefaultImpl != null) {
                throw new IllegalStateException("setDefaultImpl() called twice");
            }
            if (iFirebaseApi == null) {
                return false;
            }
            Proxy.sDefaultImpl = iFirebaseApi;
            return true;
        }

        public static IFirebaseApi getDefaultImpl() {
            return Proxy.sDefaultImpl;
        }
    }
}
