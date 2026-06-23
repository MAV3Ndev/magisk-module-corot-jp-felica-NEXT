package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class LinkageInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new y();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected int f98a;
    protected String b;
    protected String c;
    protected int d;
    protected String e;
    protected String f;

    protected LinkageInfo(Parcel parcel) {
        this.f98a = parcel.readInt();
        this.b = parcel.readString();
        this.c = parcel.readString();
        this.d = parcel.readInt();
        this.e = parcel.readString();
        this.f = parcel.readString();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f98a);
        parcel.writeString(this.b);
        parcel.writeString(this.c);
        parcel.writeInt(this.d);
        parcel.writeString(this.e);
        parcel.writeString(this.f);
    }
}
