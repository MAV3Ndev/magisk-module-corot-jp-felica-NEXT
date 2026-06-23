package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class PushStartBrowserSegment extends PushSegment {
    public static final Parcelable.Creator<PushStartBrowserSegment> CREATOR = new Parcelable.Creator<PushStartBrowserSegment>() { // from class: com.felicanetworks.mfc.PushStartBrowserSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartBrowserSegment createFromParcel(Parcel parcel) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartBrowserSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartBrowserSegment[] newArray(int i) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartBrowserSegment[i];
        }
    };
    private String browserStartupParam;
    private String url;

    public PushStartBrowserSegment(String str, String str2) throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        if (str == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = 2;
        this.url = str;
        this.browserStartupParam = str2;
        LogMgr.log(4, "%s url=%s", "001", this.url);
        LogMgr.log(4, "%s browserStartupParam=%s", "001", this.browserStartupParam);
        LogMgr.log(6, "%s", "999");
    }

    public String getURL() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.url);
        return this.url;
    }

    public String getBrowserStartupParam() {
        LogMgr.log(6, "%s", "000");
        LogMgr.log(6, "%s return=%s", "999", this.browserStartupParam);
        return this.browserStartupParam;
    }

    public void setURL(String str) throws IllegalArgumentException {
        LogMgr.log(6, "%s url=%s", "000", str);
        if (str == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.url = str;
        LogMgr.log(6, "%s", "999");
    }

    public void setBrowserStartupParam(String str) {
        LogMgr.log(6, "%s browserStartupParam=%s", "000", str);
        this.browserStartupParam = str;
        LogMgr.log(6, "%s", "999");
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s", "000");
        if (parcel == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = 2;
        this.url = parcel.readString();
        this.browserStartupParam = parcel.readString();
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushStartBrowserSegment(Parcel parcel) {
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
        parcel.writeString(this.url);
        parcel.writeString(this.browserStartupParam);
        LogMgr.log(6, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.PushSegment
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        if (getType() != 2 || this.url == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        LogMgr.log(6, "%s", "999");
    }
}
