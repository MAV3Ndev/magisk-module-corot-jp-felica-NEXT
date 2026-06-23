package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class Block implements Parcelable {
    public static final Parcelable.Creator CREATOR = new c();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f68a;
    private int b;
    private int c;

    /* synthetic */ Block(Parcel parcel, c cVar) {
        this(parcel);
    }

    private void b(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.c = parcel.readInt();
        this.f68a = parcel.readInt();
        this.b = parcel.readInt();
        com.felicanetworks.mfc.s1.a.d(6, "001 serviceCode=%d type=%d blockNo=%d", Integer.valueOf(this.f68a), Integer.valueOf(this.b), Integer.valueOf(this.c));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    public int a() {
        return this.b;
    }

    public void c(int i) {
        q1.e().c(i);
        this.c = i;
    }

    public void d(int i) {
        this.b = q1.e().d(i);
        this.f68a = i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.d(6, "001 serviceCode=%d type=%d blockNo=%d", Integer.valueOf(this.f68a), Integer.valueOf(this.b), Integer.valueOf(this.c));
        parcel.writeInt(this.c);
        parcel.writeInt(this.f68a);
        parcel.writeInt(this.b);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public Block(int i, int i2) {
        d(i);
        c(i2);
    }

    private Block(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        b(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
