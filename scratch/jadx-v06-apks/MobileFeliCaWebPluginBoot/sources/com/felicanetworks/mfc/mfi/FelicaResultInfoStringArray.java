package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.FelicaResultInfoType;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoStringArray extends FelicaResultInfoType implements Parcelable {
    public static final Parcelable.Creator CREATOR = new f();

    /* synthetic */ FelicaResultInfoStringArray(Parcel parcel, f fVar) {
        this(parcel);
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.a(6, "000 : in = " + parcel);
        super.c(parcel);
        int i = parcel.readInt();
        if (i >= 0) {
            com.felicanetworks.mfc.s1.a.a(7, "001 length=" + i);
            String[] strArr = new String[i];
            this.h = strArr;
            parcel.readStringArray(strArr);
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
        com.felicanetworks.mfc.s1.a.a(4, "000 : out = " + parcel + ", flag = " + i);
        super.writeToParcel(parcel, i);
        if (this.h != null) {
            com.felicanetworks.mfc.s1.a.a(7, "001 value is not null");
            length = ((String[]) this.h).length;
        } else {
            length = -1;
        }
        com.felicanetworks.mfc.s1.a.a(6, "002 length=" + length);
        parcel.writeInt(length);
        parcel.writeStringArray((String[]) this.h);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    private FelicaResultInfoStringArray(Parcel parcel) {
        super(null);
        com.felicanetworks.mfc.s1.a.a(6, "000 : in = " + parcel);
        c(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
