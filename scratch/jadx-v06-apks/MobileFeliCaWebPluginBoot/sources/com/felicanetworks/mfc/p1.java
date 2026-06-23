package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: ResultInfo.java */
/* JADX INFO: loaded from: classes.dex */
final class p1 implements Parcelable.Creator {
    p1() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public ResultInfo createFromParcel(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(4, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(4, "%s : in = %s", "999", parcel);
        return new ResultInfo(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public ResultInfo[] newArray(int i) {
        com.felicanetworks.mfc.s1.a.b(4, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(4, "%s : size = %d", "999", Integer.valueOf(i));
        return new ResultInfo[i];
    }
}
