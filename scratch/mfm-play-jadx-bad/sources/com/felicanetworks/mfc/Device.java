package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import com.felicanetworks.mfc.util.LogMgr;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes3.dex */
public class Device implements Parcelable {
    public static final Parcelable.Creator<Device> CREATOR = new Parcelable.Creator<Device>() { // from class: com.felicanetworks.mfc.Device.1
        /* JADX DEBUG: Method merged with bridge method: createFromParcel(Landroid/os/Parcel;)Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Device createFromParcel(Parcel in) {
            return new Device(in);
        }

        /* JADX DEBUG: Method merged with bridge method: newArray(I)[Ljava/lang/Object; */
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Device[] newArray(int size) {
            return new Device[size];
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

    Device(String type, String name) throws IllegalArgumentException {
        setType(type);
        setName(name);
    }

    public String getType() {
        return this.type;
    }

    private void setType(String type) throws IllegalArgumentException {
        checkString(type);
        this.type = type;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) throws IllegalArgumentException {
        checkString(name);
        this.name = name;
    }

    private void checkString(String str) throws IllegalArgumentException {
        try {
            ByteBuffer byteBufferEncode = Charset.forName(ENCODING).newEncoder().encode(CharBuffer.wrap(str));
            int iLimit = byteBufferEncode.limit();
            byte[] bArr = new byte[iLimit];
            byteBufferEncode.get(bArr);
            if (iLimit < 1 || iLimit > 255) {
                throw new IllegalArgumentException(EXC_INVALID_PARAMETER);
            }
            for (int i = 0; i < iLimit; i++) {
                int i2 = bArr[i] & 255;
                if (i2 < 33 || i2 > 126) {
                    throw new IllegalArgumentException(EXC_INVALID_PARAMETER);
                }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            throw new IllegalArgumentException(EXC_INVALID_PARAMETER);
        }
    }

    private void readFromParcel(Parcel in) {
        this.type = in.readString();
        this.name = in.readString();
    }

    private Device(Parcel in) {
        readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(this.type);
        out.writeString(this.name);
    }

    public void checkFormat() throws IllegalArgumentException {
        LogMgr.log(4, "%s", "000");
        checkString(this.type);
        checkString(this.name);
        LogMgr.log(4, "%s", "999");
    }
}
