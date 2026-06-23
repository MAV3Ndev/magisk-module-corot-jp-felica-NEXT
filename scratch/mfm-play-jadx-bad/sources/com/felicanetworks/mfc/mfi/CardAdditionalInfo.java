package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes3.dex */
public class CardAdditionalInfo implements Parcelable {
    public static final Parcelable.Creator<CardAdditionalInfo> CREATOR = new Parcelable.Creator<CardAdditionalInfo>() { // from class: com.felicanetworks.mfc.mfi.CardAdditionalInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardAdditionalInfo createFromParcel(Parcel in) {
            return new CardAdditionalInfo(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardAdditionalInfo[] newArray(int size) {
            return new CardAdditionalInfo[size];
        }
    };
    protected JSONObject mAdditionalInfo;
    protected String mAdditionalInfoHash;
    protected String mCardType;
    protected String mCid;
    protected String mIssuerId;
    protected ArrayList<LinkageInfo> mLinkageInfoList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CardAdditionalInfo(String cid, ArrayList<LinkageInfo> linkageInfoList) {
        this.mCid = cid;
        this.mLinkageInfoList = linkageInfoList;
    }

    public CardAdditionalInfo(String cid, String cardType, String issuerId, JSONObject additionalInfo) {
        this.mCid = cid;
        this.mCardType = cardType;
        this.mIssuerId = issuerId;
        this.mAdditionalInfo = additionalInfo;
    }

    protected CardAdditionalInfo(Parcel in) {
        this.mCid = in.readString();
        this.mLinkageInfoList = in.createTypedArrayList(LinkageInfo.CREATOR);
    }

    public CardAdditionalInfo(String cid, String cardType, String issuerId, JSONObject additionalInfo, String additionalInfoHash) {
        this.mCid = cid;
        this.mCardType = cardType;
        this.mIssuerId = issuerId;
        this.mAdditionalInfo = additionalInfo;
        this.mAdditionalInfoHash = additionalInfoHash;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mCid);
        parcel.writeTypedList(this.mLinkageInfoList);
    }

    public String getCid() {
        return this.mCid;
    }

    public String getCardType() {
        return this.mCardType;
    }

    public String getIssuerId() {
        return this.mIssuerId;
    }

    public JSONObject getAdditionalInfo() {
        return this.mAdditionalInfo;
    }

    public String getAdditionalInfoHash() {
        return this.mAdditionalInfoHash;
    }
}
