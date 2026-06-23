package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoInt extends FelicaResultInfoType<Integer> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoInt> CREATOR = new Parcelable.Creator<FelicaResultInfoInt>() { // from class: com.felicanetworks.mfc.FelicaResultInfoInt.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoInt createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoInt(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoInt[] newArray(int size) {
            LogMgr.log(4, "%s : size = %s", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoInt[size];
        }
    };
    private static final int VALUE_IS_EMPTY = 0;
    private static final int VALUE_IS_NOTEMPTY = 1;

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoInt(Integer value) {
        super(value);
        LogMgr.log(4, "%s : value = %s", "000", value);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoInt(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s", "000", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoInt(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoInt(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, statusFlag1 = %d, statusFlag = %d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [E, java.lang.Integer] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        super.readFromParcel(in);
        if (in.readInt() == 1) {
            LogMgr.log(7, "%s", "001");
            this.value = Integer.valueOf(in.readInt());
        }
        LogMgr.log(6, "%s : value = %s", "999", this.value);
    }

    private FelicaResultInfoInt(Parcel in) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v5, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        super.writeToParcel(out, flag);
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        if (this.value == 0) {
            LogMgr.log(7, "%s", "001");
            out.writeInt(0);
        } else {
            LogMgr.log(7, "%s", "002");
            out.writeInt(1);
            out.writeInt(((Integer) this.value).intValue());
        }
        LogMgr.log(4, "%s", "999");
    }
}
