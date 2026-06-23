package com.felicanetworks.mfc;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class FelicaResultInfoNodeInformation extends FelicaResultInfoType<NodeInformation> implements Parcelable {
    public static final Parcelable.Creator<FelicaResultInfoNodeInformation> CREATOR = new Parcelable.Creator<FelicaResultInfoNodeInformation>() { // from class: com.felicanetworks.mfc.FelicaResultInfoNodeInformation.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoNodeInformation createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoNodeInformation(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FelicaResultInfoNodeInformation[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new FelicaResultInfoNodeInformation[size];
        }
    };

    @Override // com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FelicaResultInfoNodeInformation(NodeInformation value) {
        super(value);
        LogMgr.log(4, "%s value=%s", "000", value);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoNodeInformation(int exceptionType, String message) {
        super(exceptionType, message);
        LogMgr.log(4, "%s exceptionType=%d message=%s", "000", Integer.valueOf(exceptionType), message);
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoNodeInformation(int exceptionType, String message, int id, int type) {
        super(exceptionType, message, id, type);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type));
        LogMgr.log(4, "%s", "999");
    }

    public FelicaResultInfoNodeInformation(int exceptionType, String message, int id, int type, int statusFlag1, int statusFlag2) {
        super(exceptionType, message, id, type, statusFlag1, statusFlag2);
        LogMgr.log(4, "%s exceptionType=%d message=%s id=%d type=%d statusFlag1=%d statusFlag2=%d", "000", Integer.valueOf(exceptionType), message, Integer.valueOf(id), Integer.valueOf(type), Integer.valueOf(statusFlag1), Integer.valueOf(statusFlag2));
        LogMgr.log(4, "%s", "999");
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [E, android.os.Parcelable] */
    /* JADX WARN: Type inference failed for: r4v3, types: [E, java.lang.Object] */
    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo
    protected void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        super.readFromParcel(in);
        if (Build.VERSION.SDK_INT >= 33) {
            this.value = in.readParcelable(NodeInformation.class.getClassLoader(), NodeInformation.class);
        } else {
            this.value = in.readParcelable(NodeInformation.class.getClassLoader());
        }
        LogMgr.log(6, "999");
    }

    private FelicaResultInfoNodeInformation(Parcel in) {
        super(null);
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // com.felicanetworks.mfc.FelicaResultInfoType, com.felicanetworks.mfc.FelicaResultInfo, com.felicanetworks.mfc.ResultInfo, android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        super.writeToParcel(out, flag);
        out.writeParcelable((Parcelable) this.value, flag);
        LogMgr.log(4, "999");
    }
}
