package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoKeyInformationArray extends FelicaResultInfoType<KeyInformation[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoKeyInformationArray> CREATOR = new Parcelable.Creator<FelicaResultInfoKeyInformationArray>() { // from class: com.felicanetworks.mfc.FelicaResultInfoKeyInformationArray.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoKeyInformationArray createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoKeyInformationArray(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoKeyInformationArray[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoKeyInformationArray[i];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoKeyInformationArray(KeyInformation[] keyInformationArr) {
        super(keyInformationArr);
        LogMgr.log(4, "%s value=%s", "000", keyInformationArr);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoKeyInformationArray(int i, String str) {
        super(i, str);
        LogMgr.log(4, "%s exceptionType=%d message=%s", "000", Integer.valueOf(i), str);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoKeyInformationArray(int i, String str, int i2, int i3) {
        super(i, str, i2, i3);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoKeyInformationArray(int i, String str, int i2, int i3, int i4, int i5) {
        super(i, str, i2, i3, i4, i5);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d statusFlag1=%d statusFlag2=%d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v4, types: [E, com.felicanetworks.mfc.KeyInformation[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        super.readFromParcel(parcel);
        Parcelable[] parcelableArray = parcel.readParcelableArray(KeyInformation.class.getClassLoader());
        if (parcelableArray != null) {
            this.value = new KeyInformation[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                ((KeyInformation[]) this.value)[i] = (KeyInformation) parcelableArray[i];
            }
        }
        LogMgr.log(6, "999");
    }

    private FelicaResultInfoKeyInformationArray(Parcel parcel) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        LogMgr.log(7, "001 length=%d", Integer.valueOf(this.value != 0 ? ((KeyInformation[]) this.value).length : -1));
        super.writeToParcel(parcel, i);
        parcel.writeParcelableArray((Parcelable[]) this.value, i);
        LogMgr.log(4, "999");
    }
}
