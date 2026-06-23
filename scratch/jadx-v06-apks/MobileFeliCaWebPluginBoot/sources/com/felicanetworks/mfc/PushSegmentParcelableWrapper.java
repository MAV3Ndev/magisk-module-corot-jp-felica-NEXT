package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PushSegmentParcelableWrapper implements Parcelable {
    public static final Parcelable.Creator CREATOR = new j1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected PushSegment f89a;

    /* synthetic */ PushSegmentParcelableWrapper(Parcel parcel, j1 j1Var) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.b(6, "%s dst", "000");
        parcel.writeParcelable(this.f89a, i);
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    public PushSegmentParcelableWrapper(PushSegment pushSegment) {
        com.felicanetworks.mfc.s1.a.c(6, "%s pushSegment=%s", "000", pushSegment);
        this.f89a = pushSegment;
        com.felicanetworks.mfc.s1.a.b(6, "%s", "999");
    }

    private PushSegmentParcelableWrapper(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.b(6, "%s in", "000");
        PushSegment pushSegment = (PushSegment) parcel.readParcelable(PushSegment.class.getClassLoader());
        this.f89a = pushSegment;
        com.felicanetworks.mfc.s1.a.c(6, "%s pushSegment=%s", "999", pushSegment);
    }
}
