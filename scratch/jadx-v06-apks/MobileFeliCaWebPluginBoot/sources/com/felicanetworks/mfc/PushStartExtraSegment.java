package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PushStartExtraSegment extends PushSegment {
    public static final Parcelable.Creator CREATOR = new m1();

    /* synthetic */ PushStartExtraSegment(Parcel parcel, m1 m1Var) {
        this(parcel);
    }

    private void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        if (parcel == null) {
            com.felicanetworks.mfc.s1.a.b(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.f88a = parcel.readInt();
        byte[] bArr = new byte[parcel.readInt()];
        this.b = bArr;
        parcel.readByteArray(bArr);
        b();
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public void b() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        parcel.writeInt(this.f88a);
        parcel.writeInt(this.b.length);
        parcel.writeByteArray(this.b);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    private PushStartExtraSegment() {
    }

    private PushStartExtraSegment(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        c(parcel);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }
}
