package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class KeyInformation implements Parcelable {
    public static final int AES128 = 79;
    public static final int AES128_DES112 = 67;
    public static final int AES128_DES56 = 65;
    public static final Parcelable.Creator<KeyInformation> CREATOR = new Parcelable.Creator<KeyInformation>() { // from class: com.felicanetworks.mfc.KeyInformation.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyInformation createFromParcel(Parcel parcel) {
            return new KeyInformation(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyInformation[] newArray(int i) {
            return new KeyInformation[i];
        }
    };
    public static final int DES112 = 63;
    public static final int DES56 = 47;
    private int mAesVersion;
    private int mDesVersion;
    private int mKeyKind;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public KeyInformation(int i, int i2, int i3) {
        this.mKeyKind = i;
        this.mAesVersion = i2;
        this.mDesVersion = i3;
    }

    public Integer getAesVersion() {
        int i = this.mKeyKind;
        if (i == 47 || i == 63 || !(i == 65 || i == 67 || i == 79)) {
            return null;
        }
        return Integer.valueOf(this.mAesVersion);
    }

    public Integer getDesVersion() {
        int i = this.mKeyKind;
        if (i == 47 || i == 63 || i == 65 || i == 67) {
            return Integer.valueOf(this.mDesVersion);
        }
        return null;
    }

    private void readFromParcel(Parcel parcel) {
        this.mKeyKind = parcel.readInt();
        this.mAesVersion = parcel.readInt();
        this.mDesVersion = parcel.readInt();
    }

    private KeyInformation(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mKeyKind);
        parcel.writeInt(this.mAesVersion);
        parcel.writeInt(this.mDesVersion);
    }
}
