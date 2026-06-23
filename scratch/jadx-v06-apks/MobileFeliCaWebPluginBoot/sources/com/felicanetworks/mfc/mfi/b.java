package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: compiled from: CardAdditionalInfo.java */
/* JADX INFO: loaded from: classes.dex */
final class b implements Parcelable.Creator {
    b() {
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public CardAdditionalInfo createFromParcel(Parcel parcel) {
        return new CardAdditionalInfo(parcel);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: b, reason: merged with bridge method [inline-methods] */
    public CardAdditionalInfo[] newArray(int i) {
        return new CardAdditionalInfo[i];
    }
}
