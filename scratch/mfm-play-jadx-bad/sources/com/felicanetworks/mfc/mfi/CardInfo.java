package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes3.dex */
public class CardInfo implements Parcelable {
    public static final String CID_UNSUPPORT_MFI_SERVICE_1 = "D00000000000000000000000000000000000000000000000000000000000001";
    public static final Parcelable.Creator<CardInfo> CREATOR = new Parcelable.Creator<CardInfo>() { // from class: com.felicanetworks.mfc.mfi.CardInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardInfo createFromParcel(Parcel in) {
            return new CardInfo(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardInfo[] newArray(int size) {
            return new CardInfo[size];
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
    protected String mCardType;
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

    public CardInfo(String cid, String serviceId, String walletAppId, int state, int position, int task) {
        this.mCid = cid;
        this.mServiceId = serviceId;
        this.mWalletAppId = walletAppId;
        this.mState = state;
        this.mPosition = position;
        this.mTask = task;
    }

    public CardInfo(String cid, String serviceId, String walletAppId, int state, int position, int task, boolean reissuePossibility, String serviceType, String additionalInfoHash, String cardCategory, String cardType) {
        this(cid, serviceId, walletAppId, state, position, task);
        this.mReissuePossibility = reissuePossibility;
        this.mServiceType = serviceType;
        this.mAdditionalInfoHash = additionalInfoHash;
        this.mCardCategory = cardCategory;
        this.mCardType = cardType;
    }

    protected CardInfo(Parcel in) {
        this.mCid = in.readString();
        this.mServiceId = in.readString();
        this.mWalletAppId = in.readString();
        this.mState = in.readInt();
        this.mPosition = in.readInt();
        this.mTask = in.readInt();
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

    public String getCardType() {
        return this.mCardType;
    }
}
