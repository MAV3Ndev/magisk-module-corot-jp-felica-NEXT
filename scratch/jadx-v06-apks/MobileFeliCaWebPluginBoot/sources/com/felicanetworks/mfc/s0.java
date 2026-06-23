package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: KeyInformation.java */
/* JADX INFO: loaded from: classes.dex */
final class s0 implements Parcelable.Creator {
    s0() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public KeyInformation createFromParcel(Parcel parcel) {
        return new KeyInformation(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public KeyInformation[] newArray(int i) {
        return new KeyInformation[i];
    }
}
