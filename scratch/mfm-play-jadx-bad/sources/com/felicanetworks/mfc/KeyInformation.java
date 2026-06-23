package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class KeyInformation implements Parcelable {
    public static final int AES128 = 79;
    public static final int AES128_DES112 = 67;
    public static final int AES128_DES56 = 65;
    public static final Parcelable.Creator<KeyInformation> CREATOR = new Parcelable.Creator<KeyInformation>() { // from class: com.felicanetworks.mfc.KeyInformation.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyInformation createFromParcel(Parcel in) {
            return new KeyInformation(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyInformation[] newArray(int size) {
            return new KeyInformation[size];
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

    public KeyInformation(int keyKind, int aesVersion, int desVersion) {
        this.mKeyKind = keyKind;
        this.mAesVersion = aesVersion;
        this.mDesVersion = desVersion;
    }

    public Integer getAesVersion() {
        int i = this.mKeyKind;
        if (i == 65 || i == 67 || i == 79) {
            return Integer.valueOf(this.mAesVersion);
        }
        return null;
    }

    public Integer getDesVersion() {
        int i = this.mKeyKind;
        if (i == 47 || i == 63 || i == 65 || i == 67) {
            return Integer.valueOf(this.mDesVersion);
        }
        return null;
    }

    private void readFromParcel(Parcel in) {
        this.mKeyKind = in.readInt();
        this.mAesVersion = in.readInt();
        this.mDesVersion = in.readInt();
    }

    private KeyInformation(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.mKeyKind);
        out.writeInt(this.mAesVersion);
        out.writeInt(this.mDesVersion);
    }
}
