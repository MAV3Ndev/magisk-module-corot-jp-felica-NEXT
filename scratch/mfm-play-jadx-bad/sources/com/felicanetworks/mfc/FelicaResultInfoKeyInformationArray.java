package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoKeyInformationArray extends FelicaResultInfoType<KeyInformation[]> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoKeyInformationArray> CREATOR = new Parcelable.Creator<FelicaResultInfoKeyInformationArray>() { // from class: com.felicanetworks.mfc.FelicaResultInfoKeyInformationArray.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoKeyInformationArray createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoKeyInformationArray(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoKeyInformationArray[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoKeyInformationArray[size];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoKeyInformationArray(KeyInformation[] value) {
        super(value);
        LogMgr.log(4, "%s value=%s", "000", value);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoKeyInformationArray(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s exceptionType=%d message=%s", "000", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoKeyInformationArray(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoKeyInformationArray(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d statusFlag1=%d statusFlag2=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r1v3, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v5, types: [E, com.felicanetworks.mfc.KeyInformation[]] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        Parcelable[] parcelableArray;
        LogMgr.log(6, "%s : in = %s", "000", in);
        super.readFromParcel(in);
        if (Build.VERSION.SDK_INT >= 33) {
            parcelableArray = (Parcelable[]) in.readParcelableArray(KeyInformation.class.getClassLoader(), KeyInformation.class);
        } else {
            parcelableArray = in.readParcelableArray(KeyInformation.class.getClassLoader());
        }
        if (parcelableArray != null) {
            this.value = new KeyInformation[parcelableArray.length];
            for (int i = 0; i < parcelableArray.length; i++) {
                ((KeyInformation[]) this.value)[i] = (KeyInformation) parcelableArray[i];
            }
        }
        LogMgr.log(6, "999");
    }

    private FelicaResultInfoKeyInformationArray(Parcel in) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    /* JADX DEBUG: Multi-variable search result rejected for r0v5, resolved type: E */
    /* JADX DEBUG: Multi-variable search result rejected for r0v7, resolved type: E */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        LogMgr.log(7, "001 length=%d", Integer.valueOf(this.value != 0 ? ((KeyInformation[]) this.value).length : -1));
        super.writeToParcel(out, flag);
        out.writeParcelableArray((KeyInformation[]) this.value, flag);
        LogMgr.log(4, "999");
    }
}
