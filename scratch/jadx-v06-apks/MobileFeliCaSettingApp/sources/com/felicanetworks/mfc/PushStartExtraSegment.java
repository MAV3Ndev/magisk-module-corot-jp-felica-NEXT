package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes.dex */
public class PushStartExtraSegment extends PushSegment {
    public static final Parcelable.Creator<PushStartExtraSegment> CREATOR = new Parcelable.Creator<PushStartExtraSegment>() { // from class: com.felicanetworks.mfc.PushStartExtraSegment.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartExtraSegment createFromParcel(Parcel parcel) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartExtraSegment(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PushStartExtraSegment[] newArray(int i) {
            LogMgr.log(6, "%s", "000");
            LogMgr.log(6, "%s", "999");
            return new PushStartExtraSegment[i];
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

    public PushStartExtraSegment(byte b, byte[] bArr) {
        this.activateType = b;
        this.pushData = bArr;
    }

    public byte getControlInfo() {
        return (byte) this.activateType;
    }

    public byte[] getSegmentParameter() {
        return this.pushData;
    }

    private void readFromParcel(Parcel parcel) {
        LogMgr.log(6, "%s", "000");
        if (parcel == null) {
            LogMgr.log(1, "%s", "700");
            throw new IllegalArgumentException();
        }
        this.activateType = parcel.readInt();
        this.pushData = new byte[parcel.readInt()];
        parcel.readByteArray(this.pushData);
        checkFormat();
        LogMgr.log(6, "%s", "999");
    }

    private PushStartExtraSegment(Parcel parcel) {
        LogMgr.log(6, "%s", "000");
        readFromParcel(parcel);
        LogMgr.log(6, "%s", "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        LogMgr.log(6, "%s", "000");
        parcel.writeInt(this.activateType);
        parcel.writeInt(this.pushData.length);
        parcel.writeByteArray(this.pushData);
        LogMgr.log(6, "%s", "999");
    }
}
