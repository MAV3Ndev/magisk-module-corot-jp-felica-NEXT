package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.FelicaResultInfoType;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoSeInfo extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new d();

    /* synthetic */ FelicaResultInfoSeInfo(Parcel parcel, d dVar) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        super.c(parcel);
        this.h = parcel.readParcelable(SeInfo.class.getClassLoader());
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        super.writeToParcel(parcel, i);
        parcel.writeParcelable((Parcelable) this.h, i);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    private FelicaResultInfoSeInfo(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
