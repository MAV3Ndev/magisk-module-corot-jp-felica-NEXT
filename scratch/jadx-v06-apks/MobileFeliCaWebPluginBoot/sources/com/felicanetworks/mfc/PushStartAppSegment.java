package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PushStartAppSegment extends PushSegment {
    public static final Parcelable.Creator CREATOR = new k1();
    private String c;
    private String d;
    private String[] e;

    /* synthetic */ PushStartAppSegment(Parcel parcel, k1 k1Var) {
        this(parcel);
    }

    private void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        this.f88a = 1;
        if (parcel == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.c = parcel.readString();
        this.d = parcel.readString();
        this.e = parcel.createStringArray();
        b();
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public void b() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (a() != 1 || this.d == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
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
        parcel.writeStringArray(this.e);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public PushStartAppSegment(String str, String str2, String[] strArr) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        this.f88a = 1;
        this.c = str;
        this.d = str2;
        this.e = strArr;
        com.felicanetworks.mfc.s1.a.c(4, "%s appURL=%s", "001", str);
        com.felicanetworks.mfc.s1.a.c(4, "%s appIdentificationCode=%s", "001", this.d);
        String[] strArr2 = this.e;
        if (strArr2 != null) {
            for (String str3 : strArr2) {
                com.felicanetworks.mfc.s1.a.c(4, "%s appStartupParam=%s", "001", str3);
            }
        }
        b();
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    private PushStartAppSegment(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
