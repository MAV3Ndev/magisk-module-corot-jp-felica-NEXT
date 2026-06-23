package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: FelicaResultInfoSeInfo.java */
/* JADX INFO: loaded from: classes.dex */
final class d implements Parcelable.Creator {
    d() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public FelicaResultInfoSeInfo createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(4, "%s : in = %s", "000", parcel);
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
        return new FelicaResultInfoSeInfo(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public FelicaResultInfoSeInfo[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.c(4, "%s : size = %d", "000", Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(4, "%s", "999");
        return new FelicaResultInfoSeInfo[i];
    }
}
