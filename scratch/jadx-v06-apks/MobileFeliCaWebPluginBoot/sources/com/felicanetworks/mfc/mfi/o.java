package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: IServerOperationEventCallback.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class o extends Binder implements p {
    public o() {
        attachInterface(this, "com.felicanetworks.mfc.mfi.IServerOperationEventCallback");
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.IServerOperationEventCallback");
            b();
            parcel2.writeNoException();
            return true;
        }
        if (i != 2) {
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("com.felicanetworks.mfc.mfi.IServerOperationEventCallback");
            return true;
        }
        parcel.enforceInterface("com.felicanetworks.mfc.mfi.IServerOperationEventCallback");
        a(parcel.readInt(), parcel.readString());
        parcel2.writeNoException();
        return true;
    }
}
