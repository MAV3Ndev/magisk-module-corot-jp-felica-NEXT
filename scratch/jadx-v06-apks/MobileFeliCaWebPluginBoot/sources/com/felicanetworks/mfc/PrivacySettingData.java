package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class PrivacySettingData implements Parcelable {
    public static final Parcelable.Creator CREATOR = new c1();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected int f84a;
    protected boolean b;

    /* synthetic */ PrivacySettingData(Parcel parcel, c1 c1Var) {
        this(parcel);
    }

    private void a(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        this.f84a = parcel.readInt();
        this.b = parcel.readInt() != 0;
        com.felicanetworks.mfc.s1.a.c(6, "001 nodeCode=%d privacySetting=%b", Integer.valueOf(this.f84a), Boolean.valueOf(this.b));
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        com.felicanetworks.mfc.s1.a.d(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        com.felicanetworks.mfc.s1.a.c(6, "001 nodeCode=%d privacySetting=%b", Integer.valueOf(this.f84a), Boolean.valueOf(this.b));
        parcel.writeInt(this.f84a);
        parcel.writeInt(this.b ? 1 : 0);
        com.felicanetworks.mfc.s1.a.a(4, "999");
    }

    private PrivacySettingData(Parcel parcel) {
        com.felicanetworks.mfc.s1.a.c(6, "%s : in = %s", "000", parcel);
        a(parcel);
        com.felicanetworks.mfc.s1.a.a(6, "999");
    }
}
