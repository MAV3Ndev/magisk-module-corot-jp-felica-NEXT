package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class CardAdditionalInfo implements Parcelable {
    public static final Parcelable.Creator CREATOR = new b();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected String f96a;
    protected ArrayList b;

    protected CardAdditionalInfo(Parcel parcel) {
        this.f96a = parcel.readString();
        this.b = parcel.createTypedArrayList(LinkageInfo.CREATOR);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f96a);
        parcel.writeTypedList(this.b);
    }
}
