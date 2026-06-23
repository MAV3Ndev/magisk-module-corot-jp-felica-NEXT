package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: FelicaResultInfoInt.java */
/* JADX INFO: loaded from: classes.dex */
final class d0 implements Parcelable.Creator {
    d0() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public FelicaResultInfoInt createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(4, "%s : in = %s", "000", parcel);
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
        return new FelicaResultInfoInt(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public FelicaResultInfoInt[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.c(4, "%s : size = %s", "000", Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
        return new FelicaResultInfoInt[i];
    }
}
