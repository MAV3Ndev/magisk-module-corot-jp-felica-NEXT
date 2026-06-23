package com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util;

import com.google.common.base.Ascii;
import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public final class CommonUtil {
    private static final String HEX_CHARS = "0123456789ABCDEF";

    public static Date getSystemTime() {
        return new Date();
    }

    public static byte[] hexStringToBin(String value) {
        if (value == null || (value.length() & 1) == 1) {
            throw new IllegalArgumentException(value);
        }
        int length = value.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            char cCharAt = value.charAt(i2);
            char cCharAt2 = value.charAt(i2 + 1);
            int iIndexOf = HEX_CHARS.indexOf(cCharAt);
            int iIndexOf2 = HEX_CHARS.indexOf(cCharAt2);
            if (iIndexOf < 0 || iIndexOf2 < 0) {
                throw new IllegalArgumentException("Invalid hex char." + value);
            }
            bArr[i] = (byte) (iIndexOf2 | (iIndexOf << 4));
        }
        return bArr;
    }

    public static String intToHexString(int value, int digits) {
        if (8 > digits) {
            int i = (8 - digits) * 4;
            value = (value << i) >>> i;
        }
        return String.format(Locale.US, "%0" + digits + "X", Integer.valueOf(value));
    }

    public static String intToDecString(int value, int digits) {
        StringBuffer stringBuffer = new StringBuffer(String.format("%0" + digits + "d", Integer.valueOf(value)));
        return stringBuffer.substring(stringBuffer.length() - digits, stringBuffer.length());
    }

    public static String toHexString(byte b) {
        return new String(new char[]{HEX_CHARS.charAt((b >> 4) & 15), HEX_CHARS.charAt(b & Ascii.SI)});
    }

    public static String binToHexString(byte[] bytes) {
        if (bytes == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bytes.length * 2);
        for (byte b : bytes) {
            stringBuffer.append(toHexString(b));
        }
        return stringBuffer.toString();
    }

    public static int toInt(byte[] b) {
        int i = 0;
        int i2 = 0;
        for (int i3 = 3; i3 >= 0; i3--) {
            i += (b[i3] & 255) << i2;
            i2 += 8;
        }
        return i;
    }

    public static int[] hexStringListToIntList(String[] values) {
        int[] iArr = new int[values.length];
        for (int i = 0; i < values.length; i++) {
            iArr[i] = toInt(hexStringToBin(values[i]));
        }
        return iArr;
    }

    public static byte[] toAsciiArray(int val, int digits) {
        byte[] bArr = new byte[digits];
        String strIntToDecString = intToDecString(val, digits);
        for (int i = 0; i < digits; i++) {
            bArr[i] = (byte) strIntToDecString.charAt(i);
        }
        return bArr;
    }

    public static int hexStringToInt(String value) {
        byte[] bArrHexStringToBin = hexStringToBin(value);
        byte[] bArr = new byte[4];
        for (int i = 0; i < bArrHexStringToBin.length; i++) {
            bArr[(4 - bArrHexStringToBin.length) + i] = (byte) (bArrHexStringToBin[i] & 255);
        }
        return toInt(bArr);
    }

    public static byte[] changeEndian(byte[] b) {
        byte[] bArr = new byte[b.length];
        for (int i = 0; i < b.length; i++) {
            bArr[(b.length - i) - 1] = b[i];
        }
        return bArr;
    }

    public static String getClassShortName(String className) {
        int iLastIndexOf = className.lastIndexOf(46);
        return iLastIndexOf < 0 ? className : className.substring(iLastIndexOf + 1);
    }
}
