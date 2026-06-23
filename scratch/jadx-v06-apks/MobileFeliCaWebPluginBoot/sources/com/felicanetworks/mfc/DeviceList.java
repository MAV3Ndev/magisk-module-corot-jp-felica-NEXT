package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Enumeration;
import java.util.Hashtable;

/* JADX INFO: loaded from: classes.dex */
public class DeviceList implements Parcelable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private Hashtable f76a;
    private int b;
    private static final Device c = new Device("FeliCa", "R/W");
    private static final Device d = new Device("Generic", "Display");
    public static final Parcelable.Creator CREATOR = new k();

    /* synthetic */ DeviceList(Parcel parcel, k kVar) {
        this(parcel);
    }

    private void a(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f76a = new Hashtable();
        int i = parcel.readInt();
        for (int i2 = 0; i2 < i; i2++) {
            this.f76a.put(Integer.valueOf(parcel.readInt()), (Device) parcel.readParcelable(Device.class.getClassLoader()));
        }
        this.b = parcel.readInt();
        com.felicanetworks.mfc.s1.a.b(6, "001 deviceList len=%d", Integer.valueOf(this.f76a.size()));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    private void b() {
        this.f76a.clear();
        this.f76a.put(new Integer(1), c);
        this.f76a.put(new Integer(2), d);
        this.b = 4096;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.b(6, "001 deviceList len=%d", Integer.valueOf(this.f76a.size()));
        parcel.writeInt(this.f76a.size());
        Enumeration enumerationKeys = this.f76a.keys();
        while (enumerationKeys.hasMoreElements()) {
            Integer num = (Integer) enumerationKeys.nextElement();
            Device device = (Device) this.f76a.get(num);
            parcel.writeInt(num.intValue());
            parcel.writeParcelable(device, i);
        }
        parcel.writeInt(this.b);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    public DeviceList() {
        this.f76a = new Hashtable();
        b();
    }

    private DeviceList(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        a(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
