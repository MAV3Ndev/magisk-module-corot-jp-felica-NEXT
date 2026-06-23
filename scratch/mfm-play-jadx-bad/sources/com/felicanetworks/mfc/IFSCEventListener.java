package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes3.dex */
public interface IFSCEventListener extends IInterface {
    public static final String DESCRIPTOR = "com.felicanetworks.mfc.IFSCEventListener";

    public static class Default implements IFSCEventListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.felicanetworks.mfc.IFSCEventListener
        public void errorOccurred(int type, String message) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.IFSCEventListener
        public void finished(int status) throws RemoteException {
        }

        @Override // com.felicanetworks.mfc.IFSCEventListener
        public void operationRequested(int deviceID, String param, byte[] data) throws RemoteException {
        }
    }

    void errorOccurred(int type, String message) throws RemoteException;

    void finished(int status) throws RemoteException;

    void operationRequested(int deviceID, String param, byte[] data) throws RemoteException;

    public static abstract class Stub extends Binder implements IFSCEventListener {
        static final int TRANSACTION_errorOccurred = 1;
        static final int TRANSACTION_finished = 2;
        static final int TRANSACTION_operationRequested = 3;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IFSCEventListener.DESCRIPTOR);
        }

        public static IFSCEventListener asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = obj.queryLocalInterface(IFSCEventListener.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IFSCEventListener)) {
                return (IFSCEventListener) iInterfaceQueryLocalInterface;
            }
            return new Proxy(obj);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IFSCEventListener.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IFSCEventListener.DESCRIPTOR);
                return true;
            }
            if (code == 1) {
                errorOccurred(data.readInt(), data.readString());
            } else if (code == 2) {
                finished(data.readInt());
            } else if (code == 3) {
                operationRequested(data.readInt(), data.readString(), data.createByteArray());
            } else {
                return super.onTransact(code, data, reply, flags);
            }
            return true;
        }

        private static class Proxy implements IFSCEventListener {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFSCEventListener.DESCRIPTOR;
            }

            @Override // com.felicanetworks.mfc.IFSCEventListener
            public void errorOccurred(int type, String message) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFSCEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(type);
                    parcelObtain.writeString(message);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFSCEventListener
            public void finished(int status) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFSCEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(status);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.felicanetworks.mfc.IFSCEventListener
            public void operationRequested(int deviceID, String param, byte[] data) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IFSCEventListener.DESCRIPTOR);
                    parcelObtain.writeInt(deviceID);
                    parcelObtain.writeString(param);
                    parcelObtain.writeByteArray(data);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
