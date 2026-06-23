package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoBlockCountInformationArray extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new z();

    /* synthetic */ FelicaResultInfoBlockCountInformationArray(Parcel parcel, z zVar) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        super.c(parcel);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        Parcelable[] parcelableArray = parcel.readParcelableArray(BlockCountInformation.class.getClassLoader());
        if (parcelableArray != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            this.h = new BlockCountInformation[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                ((BlockCountInformation[]) this.h)[i] = (BlockCountInformation) parcelableArray[i];
            }
        }
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeParcelableArray((Parcelable[]) this.h, i);
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
    }

    private FelicaResultInfoBlockCountInformationArray(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
