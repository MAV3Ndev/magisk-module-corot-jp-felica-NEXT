package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: IFSCEventListener.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class k0 extends Binder implements l0 {
    public k0() {
        attachInterface(this, "com.felicanetworks.mfc.IFSCEventListener");
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1) {
            parcel.enforceInterface("com.felicanetworks.mfc.IFSCEventListener");
            h(parcel.readInt(), parcel.readString());
            return true;
        }
        if (i == 2) {
            parcel.enforceInterface("com.felicanetworks.mfc.IFSCEventListener");
            q(parcel.readInt());
            return true;
        }
        if (i == 3) {
            parcel.enforceInterface("com.felicanetworks.mfc.IFSCEventListener");
            g(parcel.readInt(), parcel.readString(), parcel.createByteArray());
            return true;
        }
        if (i != 1598968902) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel2.writeString("com.felicanetworks.mfc.IFSCEventListener");
        return true;
    }
}
