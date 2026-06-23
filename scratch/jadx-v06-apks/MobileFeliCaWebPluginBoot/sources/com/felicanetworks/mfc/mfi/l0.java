package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: SeInfo.java */
/* JADX INFO: loaded from: classes.dex */
final class l0 implements Parcelable.Creator {
    l0() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public SeInfo createFromParcel(Parcel parcel) {
        return new SeInfo(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public SeInfo[] newArray(int i) {
        return new SeInfo[i];
    }
}
