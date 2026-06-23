package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoIntArray extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new e0();

    /* synthetic */ FelicaResultInfoIntArray(Parcel parcel, e0 e0Var) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        super.c(parcel);
        int i = parcel.readInt();
        if (i >= 0) {
            com.felicanetworks.mfc.s1.a.b(7, "001 length=%d", Integer.valueOf(i));
            int[] iArr = new int[i];
            this.h = iArr;
            parcel.readIntArray(iArr);
        }
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int length;
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        super.writeToParcel(parcel, i);
        if (this.h != null) {
            com.felicanetworks.mfc.s1.a.a(7, "001 value is not null");
            length = ((int[]) this.h).length;
        } else {
            length = -1;
        }
        com.felicanetworks.mfc.s1.a.b(6, "002 length=%d", Integer.valueOf(length));
        parcel.writeInt(length);
        parcel.writeIntArray((int[]) this.h);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    private FelicaResultInfoIntArray(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
