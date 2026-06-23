package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoDataArray extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new c0();

    /* synthetic */ FelicaResultInfoDataArray(Parcel parcel, c0 c0Var) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        super.c(parcel);
        Parcelable[] parcelableArray = parcel.readParcelableArray(Data.class.getClassLoader());
        if (parcelableArray != null) {
            com.felicanetworks.mfc.s1.a.b(7, "001 length=%d", Integer.valueOf(parcelableArray.length));
            this.h = new Data[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                ((Data[]) this.h)[i] = (Data) parcelableArray[i];
            }
        }
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        Object obj = this.h;
        com.felicanetworks.mfc.s1.a.b(7, "001 length=%d", Integer.valueOf(obj != null ? ((Data[]) obj).length : -1));
        super.writeToParcel(parcel, i);
        parcel.writeParcelableArray((Parcelable[]) this.h, i);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    private FelicaResultInfoDataArray(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
