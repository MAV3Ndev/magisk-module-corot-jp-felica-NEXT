package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface IFelicaEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.IFelicaEventListener";

    public static class Default implements IFelicaEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelicaEventListener
        public void errorOccurred(int id, String msg, AppInfo otherAppInfo) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.IFelicaEventListener
        public void finished() throws RemoteException {
        }
    }

    void errorOccurred(int id, String msg, AppInfo otherAppInfo) throws RemoteException;

    void finished() throws RemoteException;

    public static abstract class Stub extends Binder implements IFelicaEventListener {
        static final int TRANSACTION_errorOccurred = 2;
        static final int TRANSACTION_finished = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IFelicaEventListener.DESCRIPTOR);
        }

        public static IFelicaEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IFelicaEventListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFelicaEventListener)) {
                return (IFelicaEventListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IFelicaEventListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFelicaEventListener.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                finished();
            } else if (code == 2) {
                errorOccurred(data.readInt(), data.readString(), (AppInfo) _Parcel.readTypedObject(data, AppInfo.CREATOR));
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IFelicaEventListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFelicaEventListener.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.IFelicaEventListener
            public void finished() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelicaEventListener.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFelicaEventListener
            public void errorOccurred(int id, String msg, AppInfo otherAppInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelicaEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(id);
                    parcelObtain.writeString(msg);
                    _Parcel.writeTypedObject(parcelObtain, otherAppInfo, 0);
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
