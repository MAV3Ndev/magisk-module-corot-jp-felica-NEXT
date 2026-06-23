package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PushStartAppSegment extends PushSegment {
    public static final Parcelable.Creator<PushStartAppSegment> CREATOR = new Parcelable.Creator<PushStartAppSegment>() { // from class: com.felicanetworks.mfc.PushStartAppSegment.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartAppSegment createFromParcel(Parcel in) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartAppSegment(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartAppSegment[] newArray(int size) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartAppSegment[size];
        }
    };
    private String appIdentificationCode;
    private String[] appStartupParam;
    private String appURL;

    public PushStartAppSegment(String appURL, String appIdentificationCode, String[] appStartupParam) throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        this.activateType = 1;
        this.appURL = appURL;
        this.appIdentificationCode = appIdentificationCode;
        this.appStartupParam = appStartupParam;
        LogMgr.log(4, "%s appURL=%s", "001", appURL);
        LogMgr.log(4, "%s appIdentificationCode=%s", "001", this.appIdentificationCode);
        String[] strArr = this.appStartupParam;
        if (strArr != null) {
            for (String str : strArr) {
                LogMgr.log(4, "%s appStartupParam=%s", "001", str);
            }
        }
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    public String getAppURL() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.appURL);
        return this.appURL;
    }

    public String getAppIdentificationCode() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.appIdentificationCode);
        return this.appIdentificationCode;
    }

    public String[] getAppStartupParam() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return this.appStartupParam;
    }

    public void setAppURL(String appURL) {
        LogMgr.log(6, "%s appURL=%s", "000", appURL);
        LogMgr.log(6, "%s", "999");
        this.appURL = appURL;
    }

    public void setAppIdentificationCode(String appIdentificationCode) throws IllegalArgumentException {
        LogMgr.log(6, "%s appIdentificationCode=%s", "000", appIdentificationCode);
        if (appIdentificationCode == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.appIdentificationCode = appIdentificationCode;
        LogMgr.log(6, "%s", "999");
    }

    public void setAppStartupParam(String[] appStartupParam) {
        LogMgr.log(6, "%s", "000");
        this.appStartupParam = appStartupParam;
        LogMgr.log(6, "%s", "999");
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s", "000");
        this.activateType = 1;
        if (in == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.appURL = in.readString();
        this.appIdentificationCode = in.readString();
        this.appStartupParam = in.createStringArray();
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushStartAppSegment(Parcel in) {
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
        out.writeString(this.appURL);
        out.writeString(this.appIdentificationCode);
        out.writeStringArray(this.appStartupParam);
        LogMgr.log(6, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.PushSegment
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        if (getType() != 1 || this.appIdentificationCode == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        LogMgr.log(6, "%s", "000");
    }
}
