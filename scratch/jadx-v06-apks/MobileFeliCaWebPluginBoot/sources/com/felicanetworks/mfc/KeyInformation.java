package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class KeyInformation implements Parcelable {
    public static final Parcelable.Creator CREATOR = new s0();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f79a;
    private int b;
    private int c;

    /* synthetic */ KeyInformation(Parcel parcel, s0 s0Var) {
        this(parcel);
    }

    private void a(Parcel parcel) {
        this.f79a = parcel.readInt();
        this.b = parcel.readInt();
        this.c = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f79a);
        parcel.writeInt(this.b);
        parcel.writeInt(this.c);
    }

    private KeyInformation(Parcel parcel) {
        a(parcel);
    }
}
