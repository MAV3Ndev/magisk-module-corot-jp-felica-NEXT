package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class PushStartAppSegment extends PushSegment {
    public static final Parcelable.Creator<PushStartAppSegment> CREATOR = new Parcelable.Creator<PushStartAppSegment>() { // from class: com.felicanetworks.mfc.PushStartAppSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartAppSegment createFromParcel(Parcel parcel) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartAppSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartAppSegment[] newArray(int i) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartAppSegment[i];
        }
    };
    private String appIdentificationCode;
    private String[] appStartupParam;
    private String appURL;

    public PushStartAppSegment(String str, String str2, String[] strArr) throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        this.activateType = 1;
        this.appURL = str;
        this.appIdentificationCode = str2;
        this.appStartupParam = strArr;
        LogMgr.log(4, "%s appURL=%s", "001", str);
        LogMgr.log(4, "%s appIdentificationCode=%s", "001", this.appIdentificationCode);
        String[] strArr2 = this.appStartupParam;
        if (strArr2 != null) {
            for (String str3 : strArr2) {
                LogMgr.log(4, "%s appStartupParam=%s", "001", str3);
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

    public void setAppURL(String str) {
        LogMgr.log(6, "%s appURL=%s", "000", str);
        LogMgr.log(6, "%s", "999");
        this.appURL = str;
    }

    public void setAppIdentificationCode(String str) throws IllegalArgumentException {
        LogMgr.log(6, "%s appIdentificationCode=%s", "000", str);
        if (str == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.appIdentificationCode = str;
        LogMgr.log(6, "%s", "999");
    }

    public void setAppStartupParam(String[] strArr) {
        LogMgr.log(6, "%s", "000");
        this.appStartupParam = strArr;
        LogMgr.log(6, "%s", "999");
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s", "000");
        this.activateType = 1;
        if (parcel == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.appURL = parcel.readString();
        this.appIdentificationCode = parcel.readString();
        this.appStartupParam = parcel.createStringArray();
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushStartAppSegment(Parcel parcel) {
        LogMgr.log(6, "%s", "000");
        readFromParcel(parcel);
        LogMgr.log(6, "%s", "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s", "999");
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(6, "%s", "000");
        parcel.writeString(this.appURL);
        parcel.writeString(this.appIdentificationCode);
        parcel.writeStringArray(this.appStartupParam);
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
