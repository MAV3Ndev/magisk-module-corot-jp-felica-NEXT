package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoByteArray extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new b0();

    /* synthetic */ FelicaResultInfoByteArray(Parcel parcel, b0 b0Var) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        super.c(parcel);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        int i = parcel.readInt();
        if (i > -1) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            byte[] bArr = new byte[i];
            this.h = bArr;
            parcel.readByteArray(bArr);
        }
        com.felicanetworks.mfc.s1.a.c(6, "%s : value = %s", "999", this.h);
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        if (this.h == null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            parcel.writeInt(-1);
        } else {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            parcel.writeInt(((byte[]) this.h).length);
            parcel.writeByteArray((byte[]) this.h);
        }
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
    }

    private FelicaResultInfoByteArray(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
