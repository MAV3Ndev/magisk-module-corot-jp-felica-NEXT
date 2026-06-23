package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: PushStartBrowserSegment.java */
/* JADX INFO: loaded from: classes.dex */
final class l1 implements Parcelable.Creator {
    l1() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public PushStartBrowserSegment createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushStartBrowserSegment(parcel, (l1) null);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public PushStartBrowserSegment[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return new PushStartBrowserSegment[i];
    }
}
