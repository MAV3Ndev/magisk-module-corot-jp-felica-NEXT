package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.FelicaResultInfoType;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoStringArray extends FelicaResultInfoType<String[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoStringArray> CREATOR = new Parcelable.Creator<FelicaResultInfoStringArray>() { // from class: com.felicanetworks.mfc.mfi.FelicaResultInfoStringArray.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoStringArray createFromParcel(Parcel in) {
            LogMgr.log(4, "000 : in = " + in);
            LogMgr.log(4, "999");
            return new FelicaResultInfoStringArray(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoStringArray[] newArray(int size) {
            LogMgr.log(4, "000 : size = " + size);
            LogMgr.log(4, "999");
            return new FelicaResultInfoStringArray[size];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoStringArray(String[] value) {
        super(value);
        LogMgr.log(4, "000  value=" + value);
        LogMgr.log(4, "999");
    }

    public FelicaResultInfoStringArray(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "000  exceptionType=" + exceptionType + " message=" + message);
        LogMgr.log(4, "999");
    }

    public FelicaResultInfoStringArray(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "000  exceptionType=" + exceptionType + " message=" + message + " id=" + id + " type=" + type);
        LogMgr.log(4, "999");
    }

    public FelicaResultInfoStringArray(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "000  exceptionType=" + exceptionType + " message=" + message + " id=" + id + " type=" + type + " statusFlag1=" + statusFlag1 + " statusFlag2=" + statusFlag2);
        LogMgr.log(4, "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v4, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v3, types: [E, java.lang.String[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "000 : in = " + in);
        super.readFromParcel(in);
        int i = in.readInt();
        if (i >= 0) {
            LogMgr.log(7, "001 length=" + i);
            this.value = new String[i];
            in.readStringArray((String[]) this.value);
        }
        LogMgr.log(6, "999");
    }

    private FelicaResultInfoStringArray(Parcel in) {
        super(null);
        LogMgr.log(6, "000 : in = " + in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r4v4, resolved type: E */
    /* JADX DEBUG: Multi-variable search result rejected for r4v7, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        int length;
        LogMgr.log(4, "000 : out = " + out + ", flag = " + flag);
        super.writeToParcel(out, flag);
        if (this.value != 0) {
            LogMgr.log(7, "001 value is not null");
            length = ((String[]) this.value).length;
        } else {
            length = -1;
        }
        LogMgr.log(6, "002 length=" + length);
        out.writeInt(length);
        out.writeStringArray((String[]) this.value);
        LogMgr.log(6, "999");
    }
}
