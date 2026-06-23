package com.felicanetworks.semc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface ISemClientEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.semc.ISemClientEventListener";

    public static class Default implements ISemClientEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.semc.ISemClientEventListener
        public void onEventNotify(SemClientNotifyEventInfo semClientNotifyEventInfo) throws RemoteException {
        }
    }

    void onEventNotify(SemClientNotifyEventInfo semClientNotifyEventInfo) throws RemoteException;

    public static abstract class Stub extends Binder implements ISemClientEventListener {
        static final int TRANSACTION_onEventNotify = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, ISemClientEventListener.DESCRIPTOR);
        }

        public static ISemClientEventListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISemClientEventListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISemClientEventListener)) {
                return (ISemClientEventListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISemClientEventListener.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISemClientEventListener.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onEventNotify((SemClientNotifyEventInfo) _Parcel.readTypedObject(parcel, SemClientNotifyEventInfo.CREATOR));
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISemClientEventListener {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISemClientEventListener.DESCRIPTOR;
            }

            @Override // com.felicanetworks.semc.ISemClientEventListener
            public void onEventNotify(SemClientNotifyEventInfo semClientNotifyEventInfo) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(ISemClientEventListener.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, semClientNotifyEventInfo, 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
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
