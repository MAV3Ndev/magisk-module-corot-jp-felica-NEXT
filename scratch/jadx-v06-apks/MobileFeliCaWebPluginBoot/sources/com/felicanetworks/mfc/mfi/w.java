package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: IUnsupportMfiService1CardExistEventCallback.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class w extends Binder implements x {
    public w() {
        attachInterface(this, "com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback");
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback");
            A(parcel.readInt() != 0, parcel.readString());
            return true;
        }
        if (i == 2) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback");
            a(parcel.readInt(), parcel.readString());
            return true;
        }
        if (i != 1598968902) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel2.writeString("com.felicanetworks.mfc.mfi.IUnsupportMfiService1CardExistEventCallback");
        return true;
    }
}
