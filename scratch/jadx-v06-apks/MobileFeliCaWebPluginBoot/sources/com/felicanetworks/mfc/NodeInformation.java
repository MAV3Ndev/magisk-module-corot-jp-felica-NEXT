package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class NodeInformation implements Parcelable {
    public static final Parcelable.Creator CREATOR = new y0();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected AreaInformation[] f80a;
    protected int[] b;

    /* synthetic */ NodeInformation(Parcel parcel, y0 y0Var) {
        this(parcel);
    }

    protected void a(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        Parcelable[] parcelableArray = parcel.readParcelableArray(AreaInformation.class.getClassLoader());
        if (parcelableArray != null) {
            this.f80a = new AreaInformation[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                this.f80a[i] = (AreaInformation) parcelableArray[i];
            }
        }
        int i2 = parcel.readInt();
        if (i2 >= 0) {
            int[] iArr = new int[i2];
            this.b = iArr;
            parcel.readIntArray(iArr);
        }
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        parcel.writeParcelableArray(this.f80a, i);
        int[] iArr = this.b;
        parcel.writeInt(iArr != null ? iArr.length : 0);
        parcel.writeIntArray(this.b);
        com.felicanetworks.mfc.s1.a.a(7, "999");
    }

    private NodeInformation(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        a(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
