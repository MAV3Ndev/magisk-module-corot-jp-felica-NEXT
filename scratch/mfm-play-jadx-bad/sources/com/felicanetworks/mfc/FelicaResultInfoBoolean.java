package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoBoolean extends FelicaResultInfoType<Boolean> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoBoolean> CREATOR = new Parcelable.Creator<FelicaResultInfoBoolean>() { // from class: com.felicanetworks.mfc.FelicaResultInfoBoolean.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoBoolean createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoBoolean(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoBoolean[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoBoolean[size];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoBoolean(Boolean value) {
        super(value);
        LogMgr.log(4, "%s value=%s", "000", value);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoBoolean(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s exceptionType=%d message=%s", "000", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoBoolean(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoBoolean(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d statusFlag1=%d statusFlag2=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Type inference failed for: r5v5, types: [E, java.lang.Boolean] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        super.readFromParcel(in);
        int i = in.readInt();
        if (i >= 0) {
            LogMgr.log(7, "001 intValue=%d", Integer.valueOf(i));
            this.value = Boolean.valueOf(i != 0);
        }
        LogMgr.log(6, "999");
    }

    private FelicaResultInfoBoolean(Parcel in) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX DEBUG: Type inference failed for r6v1. Raw type applied. Possible types: E */
    /* JADX DEBUG: Type inference failed for r6v5. Raw type applied. Possible types: E */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.os.Parcel, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r6v2 */
    /* JADX WARN: Type inference failed for: r6v3, types: [int] */
    /* JADX WARN: Type inference failed for: r6v8 */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        ?? BooleanValue;
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", parcel, Integer.valueOf(i));
        super.writeToParcel(parcel, i);
        if (this.value != null) {
            LogMgr.log(7, "001 value is not null");
            BooleanValue = ((Boolean) this.value).booleanValue();
        } else {
            BooleanValue = -1;
        }
        LogMgr.log(6, "002 intValue=%d", Integer.valueOf((int) BooleanValue));
        parcel.writeInt(BooleanValue);
        LogMgr.log(4, "999");
    }
}
