package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class LinkageInfo implements Parcelable {
    public static final int APP_GET_KIND_BROWSER = 1;
    public static final int APP_GET_KIND_STORE = 2;
    public static final Parcelable.Creator<LinkageInfo> CREATOR = new Parcelable.Creator<LinkageInfo>() { // from class: com.felicanetworks.mfc.mfi.LinkageInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkageInfo createFromParcel(Parcel parcel) {
            return new LinkageInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkageInfo[] newArray(int i) {
            return new LinkageInfo[i];
        }
    };
    public static final int LINKAGE_KIND_APPLICATION = 2;
    public static final int LINKAGE_KIND_INFO_SITE = 1;
    public static final int LINKAGE_KIND_SERVICE_PROVIDER_SITE = 3;
    protected String mAppCallerInfo;
    protected int mAppGetKind;
    protected String mAppGetUrl;
    protected String mAppIdentifiableInfo;
    protected int mLinkageKind;
    protected String mLinkageWebsiteURL;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public LinkageInfo(int i, String str, String str2, int i2, String str3, String str4) {
        this.mLinkageKind = i;
        this.mAppIdentifiableInfo = str;
        this.mAppCallerInfo = str2;
        this.mAppGetKind = i2;
        this.mAppGetUrl = str3;
        this.mLinkageWebsiteURL = str4;
    }

    protected LinkageInfo(Parcel parcel) {
        this.mLinkageKind = parcel.readInt();
        this.mAppIdentifiableInfo = parcel.readString();
        this.mAppCallerInfo = parcel.readString();
        this.mAppGetKind = parcel.readInt();
        this.mAppGetUrl = parcel.readString();
        this.mLinkageWebsiteURL = parcel.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mLinkageKind);
        parcel.writeString(this.mAppIdentifiableInfo);
        parcel.writeString(this.mAppCallerInfo);
        parcel.writeInt(this.mAppGetKind);
        parcel.writeString(this.mAppGetUrl);
        parcel.writeString(this.mLinkageWebsiteURL);
    }

    public int getLinkageKind() {
        return this.mLinkageKind;
    }

    public void setLinkageKind(int i) {
        this.mLinkageKind = i;
    }

    public String getAppIdentifiableInfo() {
        return this.mAppIdentifiableInfo;
    }

    public void setAppIdentifiableInfo(String str) {
        this.mAppIdentifiableInfo = str;
    }

    public String getAppCallerInfo() {
        return this.mAppCallerInfo;
    }

    public void setAppCallerInfo(String str) {
        this.mAppCallerInfo = str;
    }

    public int getAppGetKind() {
        return this.mAppGetKind;
    }

    public void setAppGetKind(int i) {
        this.mAppGetKind = i;
    }

    public String getAppGetUrl() {
        return this.mAppGetUrl;
    }

    public void setAppGetUrl(String str) {
        this.mAppGetUrl = str;
    }

    public String getLinkageWebsiteURL() {
        return this.mLinkageWebsiteURL;
    }

    public void setLinkageWebsiteURL(String str) {
        this.mLinkageWebsiteURL = str;
    }
}
