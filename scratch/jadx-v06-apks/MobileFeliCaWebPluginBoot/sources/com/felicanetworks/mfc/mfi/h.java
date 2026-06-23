package com.felicanetworks.mfc.mfi;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: ILoginEventCallback.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class h extends Binder implements i {
    public h() {
        attachInterface(this, "com.felicanetworks.mfc.mfi.ILoginEventCallback");
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.ILoginEventCallback");
            b();
            return true;
        }
        if (i == 2) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.ILoginEventCallback");
            a(parcel.readInt(), parcel.readString());
            return true;
        }
        if (i != 1598968902) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel2.writeString("com.felicanetworks.mfc.mfi.ILoginEventCallback");
        return true;
    }
}
