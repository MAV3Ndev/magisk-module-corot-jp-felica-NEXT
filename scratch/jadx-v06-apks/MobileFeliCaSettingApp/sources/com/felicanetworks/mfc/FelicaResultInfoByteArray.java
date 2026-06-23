package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoByteArray extends FelicaResultInfoType<byte[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoByteArray> CREATOR = new Parcelable.Creator<FelicaResultInfoByteArray>() { // from class: com.felicanetworks.mfc.FelicaResultInfoByteArray.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoByteArray createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoByteArray[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray[i];
        }
    };
    private static final int EMPTY_BYTE_ARRAY = -1;

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoByteArray(byte[] bArr) {
        super(bArr);
        LogMgr.log(4, "%s : value = %s", "001", bArr);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoByteArray(int i, String str) {
        super(i, str);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s", "001", Integer.valueOf(i), str);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoByteArray(int i, String str, int i2, int i3) {
        super(i, str, i2, i3);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d", "001", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoByteArray(int i, String str, int i2, int i3, int i4, int i5) {
        super(i, str, i2, i3, i4, i5);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, statusFlag1 = %d, statusFlag2 = %d", "001", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v3, types: [E, byte[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        int i = parcel.readInt();
        if (i > -1) {
            LogMgr.log(7, "%s", "001");
            this.value = new byte[i];
            parcel.readByteArray((byte[]) this.value);
        }
        LogMgr.log(6, "%s : value = %s", "999", this.value);
    }

    private FelicaResultInfoByteArray(Parcel parcel) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        readFromParcel(parcel);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        if (this.value == 0) {
            LogMgr.log(7, "%s", "001");
            parcel.writeInt(-1);
        } else {
            LogMgr.log(7, "%s", "002");
            parcel.writeInt(((byte[]) this.value).length);
            parcel.writeByteArray((byte[]) this.value);
        }
        LogMgr.log(4, "%s", "999");
    }
}
