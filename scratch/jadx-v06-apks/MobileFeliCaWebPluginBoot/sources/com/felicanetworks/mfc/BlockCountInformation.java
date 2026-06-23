package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BlockCountInformation implements Parcelable {
    public static final Parcelable.Creator CREATOR = new d();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected int f69a;
    protected int b;

    /* synthetic */ BlockCountInformation(Parcel parcel, d dVar) {
        this(parcel);
    }

    protected void a(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f69a = parcel.readInt();
        this.b = parcel.readInt();
        com.felicanetworks.mfc.s1.a.c(7, "001 assignedBlocks=%d emptyBlocks=%d", Integer.valueOf(this.f69a), Integer.valueOf(this.b));
        com.felicanetworks.mfc.s1.a.a(7, "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.c(7, "001 assignedBlocks=%d emptyBlocks=%d", Integer.valueOf(this.f69a), Integer.valueOf(this.b));
        parcel.writeInt(this.f69a);
        parcel.writeInt(this.b);
        com.felicanetworks.mfc.s1.a.a(7, "999");
    }

    private BlockCountInformation(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        a(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
