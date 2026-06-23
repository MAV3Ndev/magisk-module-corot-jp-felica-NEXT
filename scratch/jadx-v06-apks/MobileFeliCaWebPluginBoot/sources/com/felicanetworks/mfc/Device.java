package com.felicanetworks.mfc;

import android.os.Parcel;
import android.os.Parcelable;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;

/* JADX INFO: loaded from: classes.dex */
public class Device implements Parcelable {
    public static final Parcelable.Creator CREATOR = new j();

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f75a;
    private String b;

    /* synthetic */ Device(Parcel parcel, j jVar) {
        this(parcel);
    }

    private void a(String str) {
        try {
            ByteBuffer byteBufferEncode = Charset.forName("ISO-8859-1").newEncoder().encode(CharBuffer.wrap(str));
            int iLimit = byteBufferEncode.limit();
            byte[] bArr = new byte[iLimit];
            byteBufferEncode.get(bArr);
            if (iLimit < 1 || iLimit > 255) {
                throw new IllegalArgumentException("The specified type/name is null or contains an invalid character, otherwise the length is out of range.");
            }
            for (int i = 0; i < iLimit; i++) {
                int i2 = bArr[i] & 255;
                if (i2 < 33 || i2 > 126) {
                    throw new IllegalArgumentException("The specified type/name is null or contains an invalid character, otherwise the length is out of range.");
                }
            }
        } catch (IllegalArgumentException e) {
            throw e;
        } catch (Exception unused) {
            throw new IllegalArgumentException("The specified type/name is null or contains an invalid character, otherwise the length is out of range.");
        }
    }

    private void b(Parcel parcel) {
        this.f75a = parcel.readString();
        this.b = parcel.readString();
    }

    private void c(String str) {
        a(str);
        this.b = str;
    }

    private void d(String str) {
        a(str);
        this.f75a = str;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f75a);
        parcel.writeString(this.b);
    }

    Device(String str, String str2) {
        d(str);
        c(str2);
    }

    private Device(Parcel parcel) {
        b(parcel);
    }
}
