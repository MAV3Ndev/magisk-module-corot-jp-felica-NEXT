package com.felicanetworks.mfc.mfi;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class MfiTosData implements Parcelable {
    public static final Parcelable.Creator<MfiTosData> CREATOR = new Parcelable.Creator<MfiTosData>() { // from class: com.felicanetworks.mfc.mfi.MfiTosData.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MfiTosData createFromParcel(Parcel in) {
            return new MfiTosData(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public MfiTosData[] newArray(int size) {
            return new MfiTosData[size];
        }
    };
    private final String mHtmlText;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public MfiTosData(String htmlText) {
        this.mHtmlText = htmlText;
    }

    protected MfiTosData(Parcel in) {
        this.mHtmlText = in.readString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = " + out + ", flag = " + flag);
        out.writeString(this.mHtmlText);
        LogMgr.log(7, "999");
    }

    public String getHtmlText() {
        return this.mHtmlText;
    }
}
