package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Vector;

/* JADX INFO: loaded from: classes.dex */
public class DataList implements Parcelable {
    public static final Parcelable.Creator CREATOR = new i();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Vector f74a;

    /* synthetic */ DataList(Parcel parcel, i iVar) {
        this(parcel);
    }

    public void a(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        parcel.readList(this.f74a, Data.class.getClassLoader());
        com.felicanetworks.mfc.s1.a.b(6, "001 dataList len=%d", Integer.valueOf(this.f74a.size()));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(6, "001 dataList len=%d", Integer.valueOf(this.f74a.size()));
        parcel.writeList(this.f74a);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    public DataList() {
        this.f74a = new Vector();
    }

    private DataList(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f74a = new Vector();
        a(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
