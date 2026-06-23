package com.felicanetworks.tis;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ITapInteractionService extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.tis.ITapInteractionService";

    public static class Default implements ITapInteractionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.tis.ITapInteractionService
        public void executeProcessing(ProcessingInfo processingInfo) throws RemoteException {
        }
    }

    void executeProcessing(ProcessingInfo processingInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ITapInteractionService {
        static final int TRANSACTION_executeProcessing = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ITapInteractionService.DESCRIPTOR);
        }

        public static ITapInteractionService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ITapInteractionService.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ITapInteractionService)) {
                return (ITapInteractionService) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ITapInteractionService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ITapInteractionService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                executeProcessing((ProcessingInfo) _Parcel.readTypedObject(parcel, ProcessingInfo.CREATOR));
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ITapInteractionService {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ITapInteractionService.DESCRIPTOR;
            }

            @Override // com.felicanetworks.tis.ITapInteractionService
            public void executeProcessing(ProcessingInfo processingInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ITapInteractionService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, processingInfo, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
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
