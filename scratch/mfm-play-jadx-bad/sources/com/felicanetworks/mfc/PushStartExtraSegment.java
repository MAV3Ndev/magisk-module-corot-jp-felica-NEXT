package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class PushStartExtraSegment extends PushSegment {
    public static final Parcelable.Creator<PushStartExtraSegment> CREATOR = new Parcelable.Creator<PushStartExtraSegment>() { // from class: com.felicanetworks.mfc.PushStartExtraSegment.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartExtraSegment createFromParcel(Parcel in) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartExtraSegment(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartExtraSegment[] newArray(int size) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartExtraSegment[size];
        }
    };

    @Override // com.felicanetworks.mfc.PushSegment
    public void checkFormat() throws IllegalArgumentException {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private PushStartExtraSegment() {
    }

    public PushStartExtraSegment(byte startControlInfo, byte[] segmentParam) {
        this.activateType = startControlInfo;
        this.pushData = segmentParam;
    }

    public byte getControlInfo() {
        return (byte) this.activateType;
    }

    public byte[] getSegmentParameter() {
        return this.pushData;
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s", "000");
        if (in == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = in.readInt();
        this.pushData = new byte[in.readInt()];
        in.readByteArray(this.pushData);
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushStartExtraSegment(Parcel in) {
        LogMgr.log(6, "%s", "000");
        readFromParcel(in);
        LogMgr.log(6, "%s", "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        LogMgr.log(6, "%s", "000");
        dest.writeInt(this.activateType);
        dest.writeInt(this.pushData.length);
        dest.writeByteArray(this.pushData);
        LogMgr.log(6, "%s", "999");
    }
}
