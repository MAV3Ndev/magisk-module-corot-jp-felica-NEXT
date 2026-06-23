package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoInt extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new d0();

    /* synthetic */ FelicaResultInfoInt(Parcel parcel, d0 d0Var) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        super.c(parcel);
        if (parcel.readInt() == 1) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            this.h = Integer.valueOf(parcel.readInt());
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
            parcel.writeInt(0);
        } else {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            parcel.writeInt(1);
            parcel.writeInt(((Integer) this.h).intValue());
        }
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
    }

    private FelicaResultInfoInt(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
