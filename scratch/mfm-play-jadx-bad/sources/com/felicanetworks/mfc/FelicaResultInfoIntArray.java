package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoIntArray extends FelicaResultInfoType<int[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoIntArray> CREATOR = new Parcelable.Creator<FelicaResultInfoIntArray>() { // from class: com.felicanetworks.mfc.FelicaResultInfoIntArray.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoIntArray createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoIntArray(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoIntArray[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoIntArray[size];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoIntArray(int[] value) {
        super(value);
        LogMgr.log(4, "%s value=%s", "000", value);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoIntArray(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s exceptionType=%d message=%s", "000", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoIntArray(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoIntArray(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d statusFlag1=%d statusFlag2=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v3, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v2, types: [E, int[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        super.readFromParcel(in);
        int i = in.readInt();
        if (i >= 0) {
            LogMgr.log(7, "001 length=%d", Integer.valueOf(i));
            this.value = new int[i];
            in.readIntArray((int[]) this.value);
        }
        LogMgr.log(6, "999");
    }

    private FelicaResultInfoIntArray(Parcel in) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v4, resolved type: E */
    /* JADX DEBUG: Multi-variable search result rejected for r6v7, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        int length;
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        super.writeToParcel(out, flag);
        if (this.value != 0) {
            LogMgr.log(7, "001 value is not null");
            length = ((int[]) this.value).length;
        } else {
            length = -1;
        }
        LogMgr.log(6, "002 length=%d", Integer.valueOf(length));
        out.writeInt(length);
        out.writeIntArray((int[]) this.value);
        LogMgr.log(6, "999");
    }
}
