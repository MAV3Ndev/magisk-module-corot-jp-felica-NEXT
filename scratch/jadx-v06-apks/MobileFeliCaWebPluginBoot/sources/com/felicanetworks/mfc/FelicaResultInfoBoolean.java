package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoBoolean extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new a0();

    /* synthetic */ FelicaResultInfoBoolean(Parcel parcel, a0 a0Var) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        super.c(parcel);
        int i = parcel.readInt();
        if (i >= 0) {
            com.felicanetworks.mfc.s1.a.b(7, "001 intValue=%d", Integer.valueOf(i));
            this.h = Boolean.valueOf(i != 0);
        }
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v2, types: [int] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.os.Parcel, java.lang.Object] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ?? BooleanValue;
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        super.writeToParcel(parcel, i);
        if (this.h != null) {
            com.felicanetworks.mfc.s1.a.a(7, "001 value is not null");
            BooleanValue = ((Boolean) this.h).booleanValue();
        } else {
            BooleanValue = -1;
        }
        com.felicanetworks.mfc.s1.a.b(6, "002 intValue=%d", Integer.valueOf((int) BooleanValue));
        parcel.writeInt(BooleanValue);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    private FelicaResultInfoBoolean(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
