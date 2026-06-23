package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class CardInfo implements Parcelable {
    public static final String CID_UNSUPPORT_MFI_SERVICE_1 = "D00000000000000000000000000000000000000000000000000000000000001";
    public static final Parcelable.Creator<CardInfo> CREATOR = new Parcelable.Creator<CardInfo>() { // from class: com.felicanetworks.mfc.mfi.CardInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardInfo createFromParcel(Parcel parcel) {
            return new CardInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardInfo[] newArray(int i) {
            return new CardInfo[i];
        }
    };
    public static final int POSITION_BACKGROUND = 1;
    public static final int POSITION_FOREGROUND = 0;
    public static final int POSITION_NOT_APPLICABLE = 3;
    public static final int POSITION_NOT_EXIST = 4;
    public static final int POSITION_PENDING = 2;
    public static final int STATUS_ACTIVE = 1;
    public static final int STATUS_DELETED = 3;
    public static final int STATUS_IN_PROCESS = 0;
    public static final int STATUS_LOST = 2;
    public static final int STATUS_UNKNOWN = 4;
    public static final int TASK_DELETE = 4;
    public static final int TASK_DISABLE = 2;
    public static final int TASK_ENABLE = 1;
    public static final int TASK_ISSUE = 0;
    public static final int TASK_NONE = -1;
    public static final int TASK_PERMANENT_DELETE = 3;
    public static final int TASK_SE_ACCESS = 5;
    protected String mAdditionalInfoHash;
    protected String mCardCategory;
    protected String mCid;
    protected int mPosition;
    protected boolean mReissuePossibility;
    protected String mServiceId;
    protected String mServiceType;
    protected int mState;
    protected int mTask;
    protected String mWalletAppId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CardInfo(String str, String str2, String str3, int i, int i2, int i3) {
        this.mCid = str;
        this.mServiceId = str2;
        this.mWalletAppId = str3;
        this.mState = i;
        this.mPosition = i2;
        this.mTask = i3;
    }

    public CardInfo(String str, String str2, String str3, int i, int i2, int i3, boolean z, String str4, String str5, String str6) {
        this(str, str2, str3, i, i2, i3);
        this.mReissuePossibility = z;
        this.mServiceType = str4;
        this.mAdditionalInfoHash = str5;
        this.mCardCategory = str6;
    }

    protected CardInfo(Parcel parcel) {
        this.mCid = parcel.readString();
        this.mServiceId = parcel.readString();
        this.mWalletAppId = parcel.readString();
        this.mState = parcel.readInt();
        this.mPosition = parcel.readInt();
        this.mTask = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCid);
        parcel.writeString(this.mServiceId);
        parcel.writeString(this.mWalletAppId);
        parcel.writeInt(this.mState);
        parcel.writeInt(this.mPosition);
        parcel.writeInt(this.mTask);
    }

    public String getCid() {
        return this.mCid;
    }

    public String getServiceId() {
        return this.mServiceId;
    }

    public String getWalletAppId() {
        return this.mWalletAppId;
    }

    public int getCardStatus() {
        return this.mState;
    }

    public int getTask() {
        return this.mTask;
    }

    public int getCardPosition() {
        return this.mPosition;
    }

    public boolean getReissuePossibility() {
        return this.mReissuePossibility;
    }

    public String getServiceType() {
        return this.mServiceType;
    }

    public String getAdditionalInfoHash() {
        return this.mAdditionalInfoHash;
    }

    public String getCardCategory() {
        return this.mCardCategory;
    }
}
