package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface IFelicaPushAppNotificationListener extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.IFelicaPushAppNotificationListener";

    public static class Default implements IFelicaPushAppNotificationListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFelicaPushAppNotificationListener
        public void pushAppNotified(PushNotifyAppSegment pushNotificationAppSegment) throws RemoteException {
        }
    }

    void pushAppNotified(PushNotifyAppSegment pushNotificationAppSegment) throws RemoteException;

    public static abstract class Stub extends Binder implements IFelicaPushAppNotificationListener {
        static final int TRANSACTION_pushAppNotified = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IFelicaPushAppNotificationListener.DESCRIPTOR);
        }

        public static IFelicaPushAppNotificationListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IFelicaPushAppNotificationListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFelicaPushAppNotificationListener)) {
                return (IFelicaPushAppNotificationListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IFelicaPushAppNotificationListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFelicaPushAppNotificationListener.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                pushAppNotified((PushNotifyAppSegment) _Parcel.readTypedObject(data, PushNotifyAppSegment.CREATOR));
                return true;
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements IFelicaPushAppNotificationListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFelicaPushAppNotificationListener.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.IFelicaPushAppNotificationListener
            public void pushAppNotified(PushNotifyAppSegment pushNotificationAppSegment) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFelicaPushAppNotificationListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, pushNotificationAppSegment, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
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
