package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PushNotifyAppSegment extends PushSegment {
    public static final Parcelable.Creator CREATOR = new i1();
    private String c;
    private String[] d;

    /* synthetic */ PushNotifyAppSegment(Parcel parcel, i1 i1Var) {
        this(parcel);
    }

    private void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (parcel == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.f88a = 6;
        this.c = parcel.readString();
        this.d = parcel.createStringArray();
        b();
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public void b() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (a() == 6) {
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
        parcel.writeString(this.c);
        parcel.writeStringArray(this.d);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    private PushNotifyAppSegment(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
