package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: FelicaResultInfoStringArray.java */
/* JADX INFO: loaded from: classes.dex */
final class f implements Parcelable.Creator {
    f() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public FelicaResultInfoStringArray createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.a(4, "000 : in = " + parcel);
        com.felicanetworks.mfc.s1.a.a(4, "999");
        return new FelicaResultInfoStringArray(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public FelicaResultInfoStringArray[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.a(4, "000 : size = " + i);
        com.felicanetworks.mfc.s1.a.a(4, "999");
        return new FelicaResultInfoStringArray[i];
    }
}
