package com.felicanetworks.mfc.mfi;

import android.os.IBinder;
import android.os.Parcel;
import com.felicanetworks.mfc.BlockDataList;
import com.felicanetworks.mfc.BlockList;
import com.felicanetworks.mfc.DeviceList;
import com.felicanetworks.mfc.FelicaResultInfo;
import com.felicanetworks.mfc.FelicaResultInfoByteArray;
import com.felicanetworks.mfc.FelicaResultInfoDataArray;
import com.felicanetworks.mfc.FelicaResultInfoInt;
import com.felicanetworks.mfc.PushSegmentParcelableWrapper;

/* JADX INFO: compiled from: IMfiFelica.java */
/* JADX INFO: loaded from: classes.dex */
class l implements n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private IBinder f109a;

    l(IBinder iBinder) {
        this.f109a = iBinder;
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo C(String str, DeviceList deviceList, com.felicanetworks.mfc.l0 l0Var, n nVar) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeString(str);
            if (deviceList != null) {
                parcelObtain.writeInt(1);
                deviceList.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            parcelObtain.writeStrongBinder(l0Var != null ? l0Var.asBinder() : null);
            parcelObtain.writeStrongBinder(nVar != null ? nVar.asBinder() : null);
            this.f109a.transact(44, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo D(String str, com.felicanetworks.mfc.q0 q0Var) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeString(str);
            parcelObtain.writeStrongBinder(q0Var != null ? q0Var.asBinder() : null);
            this.f109a.transact(1, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // android.os.IInterface
    public IBinder asBinder() {
        return this.f109a;
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo d(BlockDataList blockDataList, int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            if (blockDataList != null) {
                parcelObtain.writeInt(1);
                blockDataList.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f109a.transact(23, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo e() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            this.f109a.transact(26, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public void f(String str) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeString(str);
            this.f109a.transact(47, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfoByteArray i() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            this.f109a.transact(6, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoByteArray) FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo j(int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f109a.transact(21, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfoByteArray k() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            this.f109a.transact(7, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoByteArray) FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo l(PushSegmentParcelableWrapper pushSegmentParcelableWrapper) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            if (pushSegmentParcelableWrapper != null) {
                parcelObtain.writeInt(1);
                pushSegmentParcelableWrapper.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            this.f109a.transact(17, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo m() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            this.f109a.transact(2, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public void n() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            this.f109a.transact(45, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo o(int i) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeInt(i);
            this.f109a.transact(20, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfoByteArray p(int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f109a.transact(5, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoByteArray) FelicaResultInfoByteArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo r() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            this.f109a.transact(15, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfoInt s(int i, int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f109a.transact(9, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoInt) FelicaResultInfoInt.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfoDataArray t(BlockList blockList, int i, int i2) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            if (blockList != null) {
                parcelObtain.writeInt(1);
                blockList.writeToParcel(parcelObtain, 0);
            } else {
                parcelObtain.writeInt(0);
            }
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            this.f109a.transact(18, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfoDataArray) FelicaResultInfoDataArray.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo u() {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            this.f109a.transact(16, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // com.felicanetworks.mfc.mfi.n
    public FelicaResultInfo x(int i, int i2, int i3) {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.felicanetworks.mfc.mfi.IMfiFelica");
            parcelObtain.writeInt(i);
            parcelObtain.writeInt(i2);
            parcelObtain.writeInt(i3);
            this.f109a.transact(25, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0 ? (FelicaResultInfo) FelicaResultInfo.CREATOR.createFromParcel(parcelObtain2) : null;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
