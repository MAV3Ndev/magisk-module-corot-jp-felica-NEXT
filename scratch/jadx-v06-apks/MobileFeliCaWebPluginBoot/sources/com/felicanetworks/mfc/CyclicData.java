package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class CyclicData extends Data {
    public static final Parcelable.Creator CREATOR = new h();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private byte[] f73a;

    /* synthetic */ CyclicData(Parcel parcel, h hVar) {
        this(parcel);
    }

    private void e(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        int iB = b();
        byte[] bArr = new byte[iB];
        parcel.readByteArray(bArr);
        f(bArr);
        com.felicanetworks.mfc.s1.a.f(6, "001 bytes.length=%d bytes[]:%d %d %d ... %d", Integer.valueOf(iB), Byte.valueOf(bArr[0]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[iB - 1]));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public int c() {
        return 2;
    }

    public byte[] d() {
        return this.f73a;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void f(byte[] bArr) {
        a(bArr);
        this.f73a = bArr;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        Integer numValueOf = Integer.valueOf(this.f73a.length);
        Byte bValueOf = Byte.valueOf(this.f73a[0]);
        Byte bValueOf2 = Byte.valueOf(this.f73a[1]);
        Byte bValueOf3 = Byte.valueOf(this.f73a[2]);
        byte[] bArr = this.f73a;
        com.felicanetworks.mfc.s1.a.f(6, "001 bytes.length=%d bytes[]:%d %d %d ... %d", numValueOf, bValueOf, bValueOf2, bValueOf3, Byte.valueOf(bArr[bArr.length - 1]));
        parcel.writeByteArray(this.f73a);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public CyclicData(byte[] bArr) {
        f(bArr);
    }

    private CyclicData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        e(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
