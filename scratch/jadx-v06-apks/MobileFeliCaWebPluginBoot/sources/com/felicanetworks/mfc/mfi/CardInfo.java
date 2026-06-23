package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class CardInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new c();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected String f97a;
    protected String b;
    protected String c;
    protected int d;
    protected int e;
    protected int f;

    protected CardInfo(Parcel parcel) {
        this.f97a = parcel.readString();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readInt();
        this.f = parcel.readInt();
        this.e = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f97a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d);
        parcel.writeInt(this.f);
        parcel.writeInt(this.e);
    }
}
