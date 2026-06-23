package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: LinkageInfo.java */
/* JADX INFO: loaded from: classes.dex */
final class y implements Parcelable.Creator {
    y() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public LinkageInfo createFromParcel(Parcel parcel) {
        return new LinkageInfo(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public LinkageInfo[] newArray(int i) {
        return new LinkageInfo[i];
    }
}
