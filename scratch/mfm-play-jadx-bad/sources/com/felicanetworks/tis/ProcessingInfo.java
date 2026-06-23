package com.felicanetworks.tis;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.work.Data;
import com.felicanetworks.tis.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class ProcessingInfo implements Parcelable {
    public static final Parcelable.Creator<ProcessingInfo> CREATOR = new Parcelable.Creator<ProcessingInfo>() { // from class: com.felicanetworks.tis.ProcessingInfo.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProcessingInfo createFromParcel(Parcel parcel) {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999");
            return new ProcessingInfo(parcel);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ProcessingInfo[] newArray(int i) {
            LogMgr.log(5, "000");
            LogMgr.log(5, "999");
            return new ProcessingInfo[i];
        }
    };
    private String mAction;
    private byte[] mAid;
    private byte[] mEvent;
    private String mReaderName;
    private boolean mSetting;

    public enum Key {
        KEY_ACTION,
        KEY_READER_NAME,
        KEY_EVENT,
        KEY_AID,
        KEY_SETTING
    }

    protected ProcessingInfo(Data data) {
        this.mAction = null;
        this.mReaderName = null;
        this.mEvent = null;
        this.mAid = null;
        LogMgr.log(5, "000");
        this.mAction = data.getString(Key.KEY_ACTION.name());
        this.mReaderName = data.getString(Key.KEY_READER_NAME.name());
        this.mEvent = data.getByteArray(Key.KEY_EVENT.name());
        this.mAid = data.getByteArray(Key.KEY_AID.name());
        this.mSetting = data.getBoolean(Key.KEY_SETTING.name(), false);
        LogMgr.log(5, "999");
    }

    public String getAction() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999 mAction:" + this.mAction);
        return this.mAction;
    }

    public String getReaderName() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999 mReaderName:" + this.mReaderName);
        return this.mReaderName;
    }

    public byte[] getEvent() {
        LogMgr.log(5, "000");
        LogMgr.logArray(6, "mEvent: ", this.mEvent);
        LogMgr.log(5, "999");
        return this.mEvent;
    }

    public byte[] getAid() {
        LogMgr.log(5, "000");
        LogMgr.logArray(6, "mAid: ", this.mAid);
        LogMgr.log(5, "999");
        return this.mAid;
    }

    public boolean getSetting() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999 mSetting:" + this.mSetting);
        return this.mSetting;
    }

    protected ProcessingInfo(Parcel parcel) {
        this.mAction = null;
        this.mReaderName = null;
        this.mEvent = null;
        this.mAid = null;
        LogMgr.log(5, "000");
        readFromParcel(parcel);
        LogMgr.log(5, "999");
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        LogMgr.log(5, "000");
        LogMgr.log(5, "999");
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(5, "000");
        parcel.writeString(this.mAction);
        parcel.writeString(this.mReaderName);
        byte[] bArr = this.mEvent;
        if (bArr == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(bArr.length);
            parcel.writeByteArray(this.mEvent);
        }
        byte[] bArr2 = this.mAid;
        if (bArr2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(bArr2.length);
            parcel.writeByteArray(this.mAid);
        }
        parcel.writeInt(this.mSetting ? 1 : 0);
        LogMgr.log(5, "999");
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(5, "000");
        this.mAction = parcel.readString();
        this.mReaderName = parcel.readString();
        int i = parcel.readInt();
        if (i != 0) {
            byte[] bArr = new byte[i];
            this.mEvent = bArr;
            parcel.readByteArray(bArr);
        }
        int i2 = parcel.readInt();
        if (i2 != 0) {
            byte[] bArr2 = new byte[i2];
            this.mAid = bArr2;
            parcel.readByteArray(bArr2);
        }
        this.mSetting = parcel.readInt() != 0;
        LogMgr.log(5, "999");
    }
}
