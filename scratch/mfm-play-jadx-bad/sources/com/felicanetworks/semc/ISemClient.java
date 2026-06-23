package com.felicanetworks.semc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import com.felicanetworks.semc.ISemClientEventListener;

/* JADX INFO: loaded from: classes3.dex */
public interface ISemClient extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.semc.ISemClient";

    public static class Default implements ISemClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.semc.ISemClient
        public SemClientResultInfo callSemClientApi(SemClientApiInfo semClientApiInfo, ISemClientEventListener iSemClientEventListener, int i) throws RemoteException {
            return null;
        }
    }

    SemClientResultInfo callSemClientApi(SemClientApiInfo semClientApiInfo, ISemClientEventListener iSemClientEventListener, int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemClient {
        static final int TRANSACTION_callSemClientApi = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISemClient.DESCRIPTOR);
        }

        public static ISemClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemClient.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemClient)) {
                return (ISemClient) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                SemClientResultInfo semClientResultInfoCallSemClientApi = callSemClientApi((SemClientApiInfo) _Parcel.readTypedObject(parcel, SemClientApiInfo.CREATOR), ISemClientEventListener.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, semClientResultInfoCallSemClientApi, 1);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemClient {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemClient.DESCRIPTOR;
            }

            @Override // com.felicanetworks.semc.ISemClient
            public SemClientResultInfo callSemClientApi(SemClientApiInfo semClientApiInfo, ISemClientEventListener iSemClientEventListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemClient.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, semClientApiInfo, 0);
                    parcelObtain.writeStrongInterface(iSemClientEventListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (SemClientResultInfo) _Parcel.readTypedObject(parcelObtain2, SemClientResultInfo.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t != null) {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            } else {
                parcel.writeInt(0);
            }
        }
    }
}
