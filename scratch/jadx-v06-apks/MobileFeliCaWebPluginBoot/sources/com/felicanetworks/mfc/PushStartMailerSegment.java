package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PushStartMailerSegment extends PushSegment {
    public static final Parcelable.Creator CREATOR = new n1();
    private String[] c;
    private String[] d;
    private String e;
    private String f;
    private String g;

    /* synthetic */ PushStartMailerSegment(Parcel parcel, n1 n1Var) {
        this(parcel);
    }

    private void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (parcel == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.f88a = 3;
        this.c = parcel.createStringArray();
        this.d = parcel.createStringArray();
        this.e = parcel.readString();
        this.f = parcel.readString();
        this.g = parcel.readString();
        b();
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public void b() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (a() == 3) {
            com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        } else {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        parcel.writeStringArray(this.c);
        parcel.writeStringArray(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
        parcel.writeString(this.g);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    private PushStartMailerSegment(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
