package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class BlockData implements Parcelable {
    public static final Parcelable.Creator CREATOR = new e();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Block f70a;
    private Data b;

    /* synthetic */ BlockData(Parcel parcel, e eVar) {
        this(parcel);
    }

    private void a(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f70a = (Block) parcel.readParcelable(Block.class.getClassLoader());
        this.b = (Data) parcel.readParcelable(Data.class.getClassLoader());
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    public void b(Block block, Data data) {
        q1.e().b(block, data);
        this.f70a = block;
        this.b = data;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeParcelable(this.f70a, i);
        parcel.writeParcelable(this.b, i);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public BlockData(Block block, Data data) {
        b(block, data);
    }

    private BlockData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        a(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
