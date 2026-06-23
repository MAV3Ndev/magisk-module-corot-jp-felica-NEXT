package com.felicanetworks.mfc;

import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: compiled from: IFelica.java */
/* JADX INFO: loaded from: classes.dex */
class m0 implements o0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private IBinder f95a;

    m0(IBinder iBinder) {
        this.f95a = iBinder;
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.f95a;
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo d(BlockDataList blockDataList, int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            if (blockDataList != null) {
                parcelObtain.writeInt(1);
                blockDataList.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f95a.transact(23, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo e() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            this.f95a.transact(26, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfoByteArray i() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            this.f95a.transact(6, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoByteArray) FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo j(int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f95a.transact(21, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfoByteArray k() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            this.f95a.transact(7, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoByteArray) FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo l(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            if (pushSegmentParcelableWrapper != null) {
                parcelObtain.writeInt(1);
                pushSegmentParcelableWrapper.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f95a.transact(17, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo m() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            this.f95a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo o(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            parcelObtain.writeInt(i);
            this.f95a.transact(20, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfoByteArray p(int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f95a.transact(5, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoByteArray) FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo r() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            this.f95a.transact(15, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfoInt s(int i, int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f95a.transact(9, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoInt) FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfoDataArray t(BlockList blockList, int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            if (blockList != null) {
                parcelObtain.writeInt(1);
                blockList.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f95a.transact(18, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoDataArray) FelicaResultInfoDataArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo u() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            this.f95a.transact(16, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo x(int i, int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f95a.transact(25, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.o0
    public FelicaResultInfo z(String[] strArr, q0 q0Var) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.IFelica");
            parcelObtain.writeStringArray(strArr);
            parcelObtain.writeStrongBinder(q0Var != null ? q0Var.asBinder() : null);
            this.f95a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
