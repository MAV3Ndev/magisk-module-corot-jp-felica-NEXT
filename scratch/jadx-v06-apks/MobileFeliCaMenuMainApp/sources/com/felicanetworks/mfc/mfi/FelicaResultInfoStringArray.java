package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.FelicaResultInfoType;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoStringArray extends FelicaResultInfoType<String[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoStringArray> CREATOR = new Parcelable.Creator<FelicaResultInfoStringArray>() { // from class: com.felicanetworks.mfc.mfi.FelicaResultInfoStringArray.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoStringArray createFromParcel(Parcel parcel) {
            LogMgr.log(4, "000 : in = " + parcel);
            LogMgr.log(4, "999");
            return new FelicaResultInfoStringArray(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoStringArray[] newArray(int i) {
            LogMgr.log(4, "000 : size = " + i);
            LogMgr.log(4, "999");
            return new FelicaResultInfoStringArray[i];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoStringArray(String[] strArr) {
        super(strArr);
        LogMgr.log(4, "000  value=" + strArr);
        LogMgr.log(4, "999");
    }

    public FelicaResultInfoStringArray(int i, String str) {
        super(i, str);
        LogMgr.log(4, "000  exceptionType=" + i + " message=" + str);
        LogMgr.log(4, "999");
    }

    public FelicaResultInfoStringArray(int i, String str, int i2, int i3) {
        super(i, str, i2, i3);
        LogMgr.log(4, "000  exceptionType=" + i + " message=" + str + " id=" + i2 + " type=" + i3);
        LogMgr.log(4, "999");
    }

    public FelicaResultInfoStringArray(int i, String str, int i2, int i3, int i4, int i5) {
        super(i, str, i2, i3, i4, i5);
        LogMgr.log(4, "000  exceptionType=" + i + " message=" + str + " id=" + i2 + " type=" + i3 + " statusFlag1=" + i4 + " statusFlag2=" + i5);
        LogMgr.log(4, "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [E, java.lang.String[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "000 : in = " + parcel);
        super.readFromParcel(parcel);
        int i = parcel.readInt();
        if (i >= 0) {
            LogMgr.log(7, "001 length=" + i);
            this.value = new String[i];
            parcel.readStringArray((String[]) this.value);
        }
        LogMgr.log(6, "999");
    }

    private FelicaResultInfoStringArray(Parcel parcel) {
        super(null);
        LogMgr.log(6, "000 : in = " + parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int length;
        LogMgr.log(4, "000 : out = " + parcel + ", flag = " + i);
        super.writeToParcel(parcel, i);
        if (this.value != 0) {
            LogMgr.log(7, "001 value is not null");
            length = ((String[]) this.value).length;
        } else {
            length = -1;
        }
        LogMgr.log(6, "002 length=" + length);
        parcel.writeInt(length);
        parcel.writeStringArray((String[]) this.value);
        LogMgr.log(6, "999");
    }
}
