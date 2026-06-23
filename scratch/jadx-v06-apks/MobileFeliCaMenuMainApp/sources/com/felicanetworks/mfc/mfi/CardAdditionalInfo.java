package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class CardAdditionalInfo implements Parcelable {
    public static final Parcelable.Creator<CardAdditionalInfo> CREATOR = new Parcelable.Creator<CardAdditionalInfo>() { // from class: com.felicanetworks.mfc.mfi.CardAdditionalInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardAdditionalInfo createFromParcel(Parcel parcel) {
            return new CardAdditionalInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CardAdditionalInfo[] newArray(int i) {
            return new CardAdditionalInfo[i];
        }
    };
    protected JSONObject mAdditionalInfo;
    protected String mCardType;
    protected String mCid;
    protected String mIssuerId;
    protected ArrayList<LinkageInfo> mLinkageInfoList;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public CardAdditionalInfo(String str, ArrayList<LinkageInfo> arrayList) {
        this.mCid = str;
        this.mLinkageInfoList = arrayList;
    }

    public CardAdditionalInfo(String str, String str2, String str3, JSONObject jSONObject) {
        this.mCid = str;
        this.mCardType = str2;
        this.mIssuerId = str3;
        this.mAdditionalInfo = jSONObject;
    }

    protected CardAdditionalInfo(Parcel parcel) {
        this.mCid = parcel.readString();
        this.mLinkageInfoList = parcel.createTypedArrayList(LinkageInfo.CREATOR);
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
}
