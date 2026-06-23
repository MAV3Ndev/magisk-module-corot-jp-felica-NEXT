package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PushStartBrowserSegment extends PushSegment {
    public static final Parcelable.Creator CREATOR = new l1();
    private String c;
    private String d;

    /* synthetic */ PushStartBrowserSegment(Parcel parcel, l1 l1Var) {
        this(parcel);
    }

    private void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (parcel == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.f88a = 2;
        this.c = parcel.readString();
        this.d = parcel.readString();
        b();
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public void b() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (a() != 2 || this.c == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
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
        parcel.writeString(this.d);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public PushStartBrowserSegment(String str, String str2) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (str == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.f88a = 2;
        this.c = str;
        this.d = str2;
        com.felicanetworks.mfc.s1.a.c(4, "%s url=%s", "001", str);
        com.felicanetworks.mfc.s1.a.c(4, "%s browserStartupParam=%s", "001", this.d);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    private PushStartBrowserSegment(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
