package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class SeInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new l0();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f99a;
    private String b;
    private String c;
    private String d;

    protected SeInfo(Parcel parcel) {
        this.f99a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.c(7, "001 seId=%d sepId=%d", this.f99a, this.b);
        parcel.writeString(this.f99a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeString(this.d);
        com.felicanetworks.mfc.s1.a.a(7, "999");
    }
}
