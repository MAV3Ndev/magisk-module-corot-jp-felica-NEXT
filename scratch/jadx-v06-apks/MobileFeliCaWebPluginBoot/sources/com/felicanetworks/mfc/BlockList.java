package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class BlockList implements Parcelable {
    public static final Parcelable.Creator CREATOR = new g();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Vector f72a;

    /* synthetic */ BlockList(Parcel parcel, g gVar) {
        this(parcel);
    }

    private void c(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        parcel.readList(this.f72a, Block.class.getClassLoader());
        com.felicanetworks.mfc.s1.a.b(6, "001 blockList len=%d", Integer.valueOf(this.f72a.size()));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    public int a(Block block) {
        return b(block, true);
    }

    int b(Block block, boolean z) {
        q1.e().a(block, z);
        this.f72a.addElement(block);
        return this.f72a.size() - 1;
    }

    public int d() {
        return this.f72a.size();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(6, "001 blockList len=%d", Integer.valueOf(this.f72a.size()));
        parcel.writeList(this.f72a);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public BlockList() {
        this.f72a = new Vector();
    }

    private BlockList(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f72a = new Vector();
        c(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
