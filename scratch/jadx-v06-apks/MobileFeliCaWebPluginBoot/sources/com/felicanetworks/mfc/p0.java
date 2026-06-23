package com.felicanetworks.mfc;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: IFelicaEventListener.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class p0 extends Binder implements q0 {
    public p0() {
        attachInterface(this, "com.felicanetworks.mfc.IFelicaEventListener");
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1) {
            parcel.enforceInterface("com.felicanetworks.mfc.IFelicaEventListener");
            w();
            return true;
        }
        if (i == 2) {
            parcel.enforceInterface("com.felicanetworks.mfc.IFelicaEventListener");
            v(parcel.readInt(), parcel.readString(), parcel.readInt() != 0 ? (AppInfo) AppInfo.CREATOR.createFromParcel(parcel) : null);
            return true;
        }
        if (i != 1598968902) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel2.writeString("com.felicanetworks.mfc.IFelicaEventListener");
        return true;
    }
}
