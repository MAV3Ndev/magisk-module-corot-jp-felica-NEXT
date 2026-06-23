package com.felicanetworks.mfc;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PushIntentSegment extends PushSegment {
    public static final Parcelable.Creator CREATOR = new h1();
    private Intent c;

    /* synthetic */ PushIntentSegment(Parcel parcel, h1 h1Var) {
        this(parcel);
    }

    private void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (parcel == null) {
            com.felicanetworks.mfc.s1.a.b(6, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.f88a = 1;
        this.c = (Intent) parcel.readParcelable(Intent.class.getClassLoader());
        b();
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public void b() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (this.c == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s intent == null", "800");
            throw new IllegalArgumentException();
        }
        if (a() == 1) {
            com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        } else {
            com.felicanetworks.mfc.s1.a.b(1, "%s activateType != 0x01", "801");
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
        parcel.writeParcelable(this.c, i);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    private PushIntentSegment(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
