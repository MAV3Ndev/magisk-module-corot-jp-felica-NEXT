package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoByteArray extends FelicaResultInfoType<byte[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoByteArray> CREATOR = new Parcelable.Creator<FelicaResultInfoByteArray>() { // from class: com.felicanetworks.mfc.FelicaResultInfoByteArray.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoByteArray createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoByteArray[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoByteArray[size];
        }
    };
    private static final int EMPTY_BYTE_ARRAY = -1;

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoByteArray(byte[] value) {
        super(value);
        LogMgr.log(4, "%s : value = %s", "001", value);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoByteArray(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s", "001", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoByteArray(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d", "001", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoByteArray(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "%s : exceptionType = %d, message = %s, id = %d, type = %d, statusFlag1 = %d, statusFlag2 = %d", "001", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v4, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [E, byte[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        super.readFromParcel(in);
        LogMgr.log(6, "%s : in = %s", "000", in);
        int i = in.readInt();
        if (i > -1) {
            LogMgr.log(7, "%s", "001");
            this.value = new byte[i];
            in.readByteArray((byte[]) this.value);
        }
        LogMgr.log(6, "%s : value = %s", "999", this.value);
    }

    private FelicaResultInfoByteArray(Parcel in) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v4, resolved type: E */
    /* JADX DEBUG: Multi-variable search result rejected for r5v7, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        super.writeToParcel(out, flag);
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        if (this.value == 0) {
            LogMgr.log(7, "%s", "001");
            out.writeInt(-1);
        } else {
            LogMgr.log(7, "%s", "002");
            out.writeInt(((byte[]) this.value).length);
            out.writeByteArray((byte[]) this.value);
        }
        LogMgr.log(4, "%s", "999");
    }
}
