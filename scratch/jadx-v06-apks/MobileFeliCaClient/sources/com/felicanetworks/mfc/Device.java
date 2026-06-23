package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public class Device implements Parcelable {
    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() { // from class: com.felicanetworks.mfc.Device.1
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
    private static final String ENCODING = "ISO-8859-1";
    private static final String EXC_INVALID_PARAMETER = "The specified type/name is null or contains an invalid character, otherwise the length is out of range.";
    private static final int MAX_CHAR = 126;
    private static final int MAX_LENGTH = 255;
    private static final int MIN_CHAR = 33;
    private static final int MIN_LENGTH = 1;
    private String name;
    private String type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    Device(String str, String str2) throws IllegalArgumentException {
        setType(str);
        setName(str2);
    }

    public String getType() {
        return this.type;
    }

    private void setType(String str) throws IllegalArgumentException {
        checkString(str);
        this.type = str;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String str) throws IllegalArgumentException {
        checkString(str);
        this.name = str;
    }

    private void checkString(String str) throws IllegalArgumentException {
        try {
            ByteBuffer byteBufferEncode = Charset.forName(ENCODING).newEncoder().encode(CharBuffer.wrap(str));
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
        this.type = parcel.readString();
        this.name = parcel.readString();
    }

    private Device(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.type);
        parcel.writeString(this.name);
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        checkString(this.type);
        checkString(this.name);
        LogMgr.log(4, "%s", "999");
    }
}
