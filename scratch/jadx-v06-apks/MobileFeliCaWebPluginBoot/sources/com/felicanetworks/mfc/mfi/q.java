package com.felicanetworks.mfc.mfi;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: ISilentStartEventCallback.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class q extends Binder implements r {
    public q() {
        attachInterface(this, "com.felicanetworks.mfc.mfi.ISilentStartEventCallback");
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this;
    }

    @Override // android.os.Binder
    public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
        if (i == 1) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.ISilentStartEventCallback");
            b();
            return true;
        }
        if (i == 2) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.ISilentStartEventCallback");
            c(parcel.readInt() != 0 ? (Intent) Intent.CREATOR.createFromParcel(parcel) : null);
            return true;
        }
        if (i == 3) {
            parcel.enforceInterface("com.felicanetworks.mfc.mfi.ISilentStartEventCallback");
            a(parcel.readInt(), parcel.readString());
            return true;
        }
        if (i != 1598968902) {
            return super.onTransact(i, parcel, parcel2, i2);
        }
        parcel2.writeString("com.felicanetworks.mfc.mfi.ISilentStartEventCallback");
        return true;
    }
}
