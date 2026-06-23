package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import java.util.Map;

/* JADX INFO: loaded from: classes3.dex */
public interface IMfiTosDataGetEventCallback extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.mfi.IMfiTosDataGetEventCallback";

    public static class Default implements IMfiTosDataGetEventCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiTosDataGetEventCallback
        public void onError(int id, String msg) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.mfi.IMfiTosDataGetEventCallback
        public void onSuccess(Map mfiTosDataJsonMap, boolean isMfiTosAgreed) throws RemoteException {
        }
    }

    void onError(int id, String msg) throws RemoteException;

    void onSuccess(Map mfiTosDataJsonMap, boolean isMfiTosAgreed) throws RemoteException;

    public static abstract class Stub extends Binder implements IMfiTosDataGetEventCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onSuccess = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IMfiTosDataGetEventCallback.DESCRIPTOR);
        }

        public static IMfiTosDataGetEventCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IMfiTosDataGetEventCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IMfiTosDataGetEventCallback)) {
                return (IMfiTosDataGetEventCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IMfiTosDataGetEventCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IMfiTosDataGetEventCallback.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                onSuccess(data.readHashMap(getClass().getClassLoader()), data.readInt() != 0);
            } else if (code == 2) {
                onError(data.readInt(), data.readString());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IMfiTosDataGetEventCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IMfiTosDataGetEventCallback.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiTosDataGetEventCallback
            public void onSuccess(Map map, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiTosDataGetEventCallback.DESCRIPTOR);
                    parcelObtain.writeMap(map);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.mfi.IMfiTosDataGetEventCallback
            public void onError(int id, String msg) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IMfiTosDataGetEventCallback.DESCRIPTOR);
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
