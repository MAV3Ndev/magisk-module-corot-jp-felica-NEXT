package com.felicanetworks.tis.util;

import com.google.common.base.Ascii;
import java.util.Arrays;

/* JADX INFO: loaded from: classes3.dex */
public class ByteBufferMgr {
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public static int getValueFromByteArray(boolean z, byte[] bArr) {
        int i = 0;
        try {
            if (z) {
                int i2 = 0;
                while (i < bArr.length) {
                    i2 |= (bArr[i] & 255) << (i * 8);
                    i++;
                }
                return i2;
            }
            int i3 = 0;
            while (i < bArr.length) {
                i3 = (i3 << 8) + (bArr[i] & 255);
                i++;
            }
            return i3;
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    public static int getValueFromByteArray(boolean z, byte[] bArr, int i, int i2) {
        int i3 = i2 + i;
        try {
            if (i3 > bArr.length) {
                throw new IllegalArgumentException();
            }
            return getValueFromByteArray(z, Arrays.copyOfRange(bArr, i, i3));
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    public static int getBCDValueFromByteArray(boolean z, byte[] bArr) {
        try {
            if (z) {
                byte[] bArr2 = new byte[bArr.length];
                for (int i = 0; i < bArr.length; i++) {
                    bArr2[i] = bArr[(bArr.length - 1) - i];
                }
                return Integer.parseInt(bytesToHex(bArr2));
            }
            return Integer.parseInt(bytesToHex(bArr));
        } catch (Exception unused) {
            throw new IllegalArgumentException("Invalid byte data");
        }
    }

    public static String bytesToHex(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        char[] cArr = new char[bArr.length * 2];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = i * 2;
            char[] cArr2 = HEX_ARRAY;
            cArr[i2] = cArr2[(b & 255) >>> 4];
            cArr[i2 + 1] = cArr2[b & Ascii.SI];
        }
        return new String(cArr);
    }
}
