package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class RandomData extends Data {
    public static final Parcelable.Creator CREATOR = new o1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private byte[] f90a;

    /* synthetic */ RandomData(Parcel parcel, o1 o1Var) {
        this(parcel);
    }

    private void e(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        byte[] bArr = new byte[b()];
        this.f90a = bArr;
        parcel.readByteArray(bArr);
        Integer numValueOf = Integer.valueOf(this.f90a.length);
        Byte bValueOf = Byte.valueOf(this.f90a[0]);
        Byte bValueOf2 = Byte.valueOf(this.f90a[1]);
        Byte bValueOf3 = Byte.valueOf(this.f90a[2]);
        byte[] bArr2 = this.f90a;
        com.felicanetworks.mfc.s1.a.f(6, "001 bytes.length=%d bytes[]:%d %d %d ... %d", numValueOf, bValueOf, bValueOf2, bValueOf3, Byte.valueOf(bArr2[bArr2.length - 1]));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public int c() {
        return 1;
    }

    public byte[] d() {
        return this.f90a;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void f(byte[] bArr) {
        a(bArr);
        this.f90a = bArr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        Integer numValueOf = Integer.valueOf(this.f90a.length);
        Byte bValueOf = Byte.valueOf(this.f90a[0]);
        Byte bValueOf2 = Byte.valueOf(this.f90a[1]);
        Byte bValueOf3 = Byte.valueOf(this.f90a[2]);
        byte[] bArr = this.f90a;
        com.felicanetworks.mfc.s1.a.f(6, "001 bytes.length=%d bytes[]:%d %d %d ... %d", numValueOf, bValueOf, bValueOf2, bValueOf3, Byte.valueOf(bArr[bArr.length - 1]));
        parcel.writeByteArray(this.f90a);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public RandomData(byte[] bArr) {
        f(bArr);
    }

    private RandomData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        e(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
