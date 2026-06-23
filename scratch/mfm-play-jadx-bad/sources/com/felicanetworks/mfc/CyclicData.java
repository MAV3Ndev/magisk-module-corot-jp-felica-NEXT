package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;

/* JADX INFO: loaded from: classes3.dex */
public class CyclicData extends Data {
    public static final Parcelable.Creator<CyclicData> CREATOR = new Parcelable.Creator<CyclicData>() { // from class: com.felicanetworks.mfc.CyclicData.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CyclicData createFromParcel(Parcel in) {
            LogMgr.log(4, "%s : in = %s", "000", in);
            LogMgr.log(4, "%s", "999");
            return new CyclicData(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public CyclicData[] newArray(int size) {
            LogMgr.log(4, "%s : size = %d", "000", Integer.valueOf(size));
            LogMgr.log(4, "%s", "999");
            return new CyclicData[size];
        }
    };
    public static final int TYPE = 2;
    private byte[] bytes;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // com.felicanetworks.mfc.Data
    public int getType() {
        return 2;
    }

    public CyclicData(byte[] bytes) throws IllegalArgumentException {
        setBytes(bytes);
    }

    public byte[] getBytes() {
        return this.bytes;
    }

    public void setBytes(byte[] bytes) throws IllegalArgumentException {
        checkBytes(bytes);
        this.bytes = bytes;
    }

    private void readFromParcel(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        int bytesLength = getBytesLength();
        byte[] bArr = new byte[bytesLength];
        in.readByteArray(bArr);
        setBytes(bArr);
        LogMgr.log(6, "001 bytes.length=%d bytes[]:%d %d %d ... %d", Integer.valueOf(bytesLength), Byte.valueOf(bArr[0]), Byte.valueOf(bArr[1]), Byte.valueOf(bArr[2]), Byte.valueOf(bArr[bytesLength - 1]));
        LogMgr.log(6, "999");
    }

    private CyclicData(Parcel in) {
        LogMgr.log(6, "%s : in = %s", "000", in);
        readFromParcel(in);
        LogMgr.log(6, "999");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flag) {
        LogMgr.log(4, "%s : out = %s, flag = %d", "000", out, Integer.valueOf(flag));
        Integer numValueOf = Integer.valueOf(this.bytes.length);
        Byte bValueOf = Byte.valueOf(this.bytes[0]);
        Byte bValueOf2 = Byte.valueOf(this.bytes[1]);
        Byte bValueOf3 = Byte.valueOf(this.bytes[2]);
        byte[] bArr = this.bytes;
        LogMgr.log(6, "001 bytes.length=%d bytes[]:%d %d %d ... %d", numValueOf, bValueOf, bValueOf2, bValueOf3, Byte.valueOf(bArr[bArr.length - 1]));
        out.writeByteArray(this.bytes);
        LogMgr.log(4, "999");
    }

    @Override // com.felicanetworks.mfc.Data
    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "000");
        checkBytes(this.bytes);
        LogMgr.log(4, "999");
    }
}
