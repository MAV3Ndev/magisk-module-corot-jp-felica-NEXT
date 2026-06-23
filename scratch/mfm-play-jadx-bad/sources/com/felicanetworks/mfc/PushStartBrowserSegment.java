package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PushStartBrowserSegment extends PushSegment {
    public static final Parcelable.Creator<PushStartBrowserSegment> CREATOR = new Parcelable.Creator<PushStartBrowserSegment>() { // from class: com.felicanetworks.mfc.PushStartBrowserSegment.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartBrowserSegment createFromParcel(Parcel in) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartBrowserSegment(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartBrowserSegment[] newArray(int size) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartBrowserSegment[size];
        }
    };
    private String browserStartupParam;
    private String url;

    public PushStartBrowserSegment(String url, String browserStartupParam) throws IllegalArgumentException {
        LogMgr.log(6, "%s", "000");
        if (url == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = 2;
        this.url = url;
        this.browserStartupParam = browserStartupParam;
        LogMgr.log(4, "%s url=%s", "001", url);
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

    public void setURL(String url) throws IllegalArgumentException {
        LogMgr.log(6, "%s url=%s", "000", url);
        if (url == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.url = url;
        LogMgr.log(6, "%s", "999");
    }

    public void setBrowserStartupParam(String browserStartupParam) {
        LogMgr.log(6, "%s browserStartupParam=%s", "000", browserStartupParam);
        this.browserStartupParam = browserStartupParam;
        LogMgr.log(6, "%s", "999");
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s", "000");
        if (in == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = 2;
        this.url = in.readString();
        this.browserStartupParam = in.readString();
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushStartBrowserSegment(Parcel in) {
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
        out.writeString(this.url);
        out.writeString(this.browserStartupParam);
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
