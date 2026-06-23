package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class BlockDataList implements Parcelable {
    public static final Parcelable.Creator CREATOR = new f();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Vector f71a;

    /* synthetic */ BlockDataList(Parcel parcel, f fVar) {
        this(parcel);
    }

    private void b(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        parcel.readList(this.f71a, BlockData.class.getClassLoader());
        com.felicanetworks.mfc.s1.a.b(7, "001 blockDataList len=%d", Integer.valueOf(this.f71a.size()));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    public int a(BlockData blockData) {
        if (blockData == null) {
            throw new IllegalArgumentException("The specified BlockData is null.");
        }
        this.f71a.addElement(blockData);
        return this.f71a.size() - 1;
    }

    public int c() {
        return this.f71a.size();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(6, "001 blockDataList len=%d", Integer.valueOf(this.f71a.size()));
        parcel.writeList(this.f71a);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public BlockDataList() {
        this.f71a = new Vector();
    }

    private BlockDataList(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f71a = new Vector();
        b(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
