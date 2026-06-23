package com.felicanetworks.tcap;

import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;

/* JADX INFO: loaded from: classes.dex */
public class Device implements Parcelable {
    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() { // from class: com.felicanetworks.tcap.Device.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Device createFromParcel(Parcel parcel) {
            return new Device(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Device[] newArray(int i) {
            return new Device[i];
        }
    };
    private static final String EXC_INVALID_PARAMETER = "The specified type/name is null or contains an invalid character, otherwise the length is out of range.";
    private static final int MAX_CHAR = 126;
    private static final int MAX_LENGTH = 255;
    private static final int MIN_CHAR = 33;
    private static final int MIN_LENGTH = 1;
    private String mName;
    private String mType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Device(String str, String str2) throws IllegalArgumentException {
        setType(str);
        setName(str2);
    }

    public String getType() {
        return this.mType;
    }

    private void setType(String str) throws IllegalArgumentException {
        checkString(str);
        this.mType = str;
    }

    public String getName() {
        return this.mName;
    }

    private void setName(String str) throws IllegalArgumentException {
        checkString(str);
        this.mName = str;
    }

    private void checkString(String str) throws IllegalArgumentException {
        try {
            ByteBuffer byteBufferEncode = StandardCharsets.ISO_8859_1.newEncoder().encode(CharBuffer.wrap(str));
            byte[] bArr = new byte[byteBufferEncode.limit()];
            byteBufferEncode.get(bArr);
            if (bArr.length < 1 || bArr.length > 255) {
                throw new IllegalArgumentException(EXC_INVALID_PARAMETER);
            }
            for (byte b : bArr) {
                int i = b & 255;
                if (i < 33 || i > MAX_CHAR) {
                    throw new IllegalArgumentException(EXC_INVALID_PARAMETER);
                }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            throw new IllegalArgumentException(EXC_INVALID_PARAMETER);
        }
    }

    private void readFromParcel(Parcel parcel) {
        this.mType = parcel.readString();
        this.mName = parcel.readString();
    }

    private Device(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mType);
        parcel.writeString(this.mName);
    }

    public void checkFormat() throws IllegalArgumentException {
        checkString(this.mType);
        checkString(this.mName);
    }
}
