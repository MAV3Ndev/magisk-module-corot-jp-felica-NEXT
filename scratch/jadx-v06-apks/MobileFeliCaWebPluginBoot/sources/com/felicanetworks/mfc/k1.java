package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: PushStartAppSegment.java */
/* JADX INFO: loaded from: classes.dex */
final class k1 implements Parcelable.Creator {
    k1() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public PushStartAppSegment createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushStartAppSegment(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public PushStartAppSegment[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushStartAppSegment[i];
    }
}
