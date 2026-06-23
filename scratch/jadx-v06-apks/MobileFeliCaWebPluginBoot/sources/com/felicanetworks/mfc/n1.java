package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: PushStartMailerSegment.java */
/* JADX INFO: loaded from: classes.dex */
final class n1 implements Parcelable.Creator {
    n1() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public PushStartMailerSegment createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushStartMailerSegment(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public PushStartMailerSegment[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.c(6, "%s size=%d", "000", Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushStartMailerSegment[i];
    }
}
