package com.felicanetworks.mfc;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: IFSC.java */
/* JADX INFO: loaded from: classes.dex */
class h0 implements j0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private IBinder f92a;

    h0(IBinder iBinder) {
        this.f92a = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.f92a;
    }

    @Override // com.felicanetworks.mfc.j0
    public void f(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFSC");
            parcelObtain.writeString(str);
            this.f92a.transact(4, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.j0
    public void n() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFSC");
            this.f92a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.j0
    public FelicaResultInfo y(String str, DeviceList deviceList, l0 l0Var, o0 o0Var) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFSC");
            parcelObtain.writeString(str);
            if (deviceList != null) {
                parcelObtain.writeInt(1);
                deviceList.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            parcelObtain.writeStrongBinder(l0Var != null ? l0Var.asBinder() : null);
            parcelObtain.writeStrongBinder(o0Var != null ? o0Var.asBinder() : null);
            this.f92a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
