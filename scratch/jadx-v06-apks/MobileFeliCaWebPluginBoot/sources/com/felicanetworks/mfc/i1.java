package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: PushNotifyAppSegment.java */
/* JADX INFO: loaded from: classes.dex */
final class i1 implements Parcelable.Creator {
    i1() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public PushNotifyAppSegment createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushNotifyAppSegment(parcel, null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public PushNotifyAppSegment[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushNotifyAppSegment[i];
    }
}
