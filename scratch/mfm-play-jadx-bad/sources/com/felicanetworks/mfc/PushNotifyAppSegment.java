package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PushNotifyAppSegment extends PushSegment {
    public static final Parcelable.Creator<PushNotifyAppSegment> CREATOR = new Parcelable.Creator<PushNotifyAppSegment>() { // from class: com.felicanetworks.mfc.PushNotifyAppSegment.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushNotifyAppSegment createFromParcel(Parcel in) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushNotifyAppSegment(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushNotifyAppSegment[] newArray(int size) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushNotifyAppSegment[size];
        }
    };
    private String appIdentificationCode;
    private String[] appNotificationParam;

    public PushNotifyAppSegment(String appIdentificationCode, String[] appNotificationParam) {
        LogMgr.log(6, "%s", "000");
        this.activateType = 6;
        this.appIdentificationCode = appIdentificationCode;
        this.appNotificationParam = appNotificationParam;
        LogMgr.log(4, "%s appIdentificationCode=%s", "001", appIdentificationCode);
        LogMgr.log(4, "%s appNotificationParam=%s", "001", this.appNotificationParam);
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    public String getAppIdentificationCode() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.appIdentificationCode);
        return this.appIdentificationCode;
    }

    public String[] getAppNotificationParam() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return this.appNotificationParam;
    }

    public void setAppIdentificationCode(String appIdentificationCode) {
        LogMgr.log(6, "%s appIdentificationCode=%s", "000", appIdentificationCode);
        LogMgr.log(6, "%s", "999");
        this.appIdentificationCode = appIdentificationCode;
    }

    public void setAppNotificationParam(String[] appNotificationParam) {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        this.appNotificationParam = appNotificationParam;
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s", "000");
        if (in == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = 6;
        this.appIdentificationCode = in.readString();
        this.appNotificationParam = in.createStringArray();
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushNotifyAppSegment(Parcel in) {
        LogMgr.log(6, "%s", "000");
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(6, "%s", "000");
        out.writeString(this.appIdentificationCode);
        out.writeStringArray(this.appNotificationParam);
        LogMgr.log(6, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.PushSegment
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        if (getType() != 6) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        LogMgr.log(6, "%s", "999");
    }
}
