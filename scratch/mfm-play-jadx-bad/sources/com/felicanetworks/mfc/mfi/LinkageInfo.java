package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class LinkageInfo implements Parcelable {
    public static final Parcelable.Creator<LinkageInfo> CREATOR = new Parcelable.Creator<LinkageInfo>() { // from class: com.felicanetworks.mfc.mfi.LinkageInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkageInfo createFromParcel(Parcel in) {
            return new LinkageInfo(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public LinkageInfo[] newArray(int size) {
            return new LinkageInfo[size];
        }
    };
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

    public LinkageInfo(int linkageKind, String appIdentifiableInfo, String appCallerInfo, int appGetKind, String appGetUrl, String linkageWebsiteURL) {
        this.mLinkageKind = linkageKind;
        this.mAppIdentifiableInfo = appIdentifiableInfo;
        this.mAppCallerInfo = appCallerInfo;
        this.mAppGetKind = appGetKind;
        this.mAppGetUrl = appGetUrl;
        this.mLinkageWebsiteURL = linkageWebsiteURL;
    }

    protected LinkageInfo(Parcel in) {
        this.mLinkageKind = in.readInt();
        this.mAppIdentifiableInfo = in.readString();
        this.mAppCallerInfo = in.readString();
        this.mAppGetKind = in.readInt();
        this.mAppGetUrl = in.readString();
        this.mLinkageWebsiteURL = in.readString();
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

    public void setLinkageKind(int linkageKind) {
        this.mLinkageKind = linkageKind;
    }

    public String getAppIdentifiableInfo() {
        return this.mAppIdentifiableInfo;
    }

    public void setAppIdentifiableInfo(String appIdentifiableInfo) {
        this.mAppIdentifiableInfo = appIdentifiableInfo;
    }

    public String getAppCallerInfo() {
        return this.mAppCallerInfo;
    }

    public void setAppCallerInfo(String appCallerInfo) {
        this.mAppCallerInfo = appCallerInfo;
    }

    public int getAppGetKind() {
        return this.mAppGetKind;
    }

    public void setAppGetKind(int appGetKind) {
        this.mAppGetKind = appGetKind;
    }

    public String getAppGetUrl() {
        return this.mAppGetUrl;
    }

    public void setAppGetUrl(String appGetUrl) {
        this.mAppGetUrl = appGetUrl;
    }

    public String getLinkageWebsiteURL() {
        return this.mLinkageWebsiteURL;
    }

    public void setLinkageWebsiteURL(String linkageWebsiteURL) {
        this.mLinkageWebsiteURL = linkageWebsiteURL;
    }
}
