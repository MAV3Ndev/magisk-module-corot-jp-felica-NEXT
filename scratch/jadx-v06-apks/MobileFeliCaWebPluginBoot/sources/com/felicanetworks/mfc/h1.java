package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: PushIntentSegment.java */
/* JADX INFO: loaded from: classes.dex */
final class h1 implements Parcelable.Creator {
    h1() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public PushIntentSegment createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushIntentSegment(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public PushIntentSegment[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.c(6, "%s size=%d", "000", Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushIntentSegment[i];
    }
}
