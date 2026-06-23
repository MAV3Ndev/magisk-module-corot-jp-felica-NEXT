package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class AppInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new a();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f66a;

    /* synthetic */ AppInfo(Parcel parcel, a aVar) {
        this(parcel);
    }

    private void b(Parcel parcel) {
        this.f66a = parcel.readInt();
    }

    public int a() {
        return this.f66a;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.f66a);
    }

    private AppInfo(Parcel parcel) {
        b(parcel);
    }
}
