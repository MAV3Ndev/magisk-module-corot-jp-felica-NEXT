package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class FelicaResultInfoBlockCountInformationArray extends FelicaResultInfoType<BlockCountInformation[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoBlockCountInformationArray> CREATOR = new Parcelable.Creator<FelicaResultInfoBlockCountInformationArray>() { // from class: com.felicanetworks.mfc.FelicaResultInfoBlockCountInformationArray.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoBlockCountInformationArray createFromParcel(Parcel parcel) {
            LogMgr.log(4, "%s : in = %s", "000", parcel);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoBlockCountInformationArray(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoBlockCountInformationArray[] newArray(int i) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(i));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoBlockCountInformationArray[i];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoBlockCountInformationArray(BlockCountInformation[] blockCountInformationArr) {
        super(blockCountInformationArr);
        LogMgr.log(4, "%s : value = %s", "000", blockCountInformationArr);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoBlockCountInformationArray(int i, String str) {
        super(i, str);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s", "000", Integer.valueOf(i), str);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoBlockCountInformationArray(int i, String str, int i2, int i3) {
        super(i, str, i2, i3);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoBlockCountInformationArray(int i, String str, int i2, int i3, int i4, int i5) {
        super(i, str, i2, i3, i4, i5);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, statusFlag1 = %d, statusFlag2 = %d", "000", Integer.valueOf(i), str, Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v3, types: [E, com.felicanetworks.mfc.BlockCountInformation[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel parcel) {
        super.readFromParcel(parcel);
        LogMgr.log(6, "%s : in = %s", "000", parcel);
        Parcelable[] parcelableArray = parcel.readParcelableArray(BlockCountInformation.class.getClassLoader());
        if (parcelableArray != null) {
            LogMgr.log(7, "%s", "001");
            this.value = new BlockCountInformation[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                ((BlockCountInformation[]) this.value)[i] = (BlockCountInformation) parcelableArray[i];
            }
        }
        LogMgr.log(6, "%s", "999");
    }

    private FelicaResultInfoBlockCountInformationArray(Parcel parcel) {
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
        parcel.writeParcelableArray((Parcelable[]) this.value, i);
        LogMgr.log(4, "%s", "999");
    }
}
