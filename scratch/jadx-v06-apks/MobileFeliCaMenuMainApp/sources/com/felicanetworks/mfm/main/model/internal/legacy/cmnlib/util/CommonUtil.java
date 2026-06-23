package com.felicanetworks.mfm.main.model.internal.legacy.cmnlib.util;

import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class CommonUtil {
    private static final String HEX_CHARS = "0123456789ABCDEF";

    public static Date getSystemTime() {
        return new Date();
    }

    public static byte[] hexStringToBin(String str) {
        if (str == null || (str.length() & 1) == 1) {
            throw new IllegalArgumentException(str);
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            char cCharAt = str.charAt(i2);
            char cCharAt2 = str.charAt(i2 + 1);
            int iIndexOf = HEX_CHARS.indexOf(cCharAt);
            int iIndexOf2 = HEX_CHARS.indexOf(cCharAt2);
            if (iIndexOf < 0 || iIndexOf2 < 0) {
                throw new IllegalArgumentException("Invalid hex char." + str);
            }
            bArr[i] = (byte) (iIndexOf2 | (iIndexOf << 4));
        }
        return bArr;
    }

    public static String intToHexString(int i, int i2) {
        if (8 > i2) {
            int i3 = (8 - i2) * 4;
            i = (i << i3) >>> i3;
        }
        return String.format(Locale.US, "%0" + i2 + "X", Integer.valueOf(i));
    }

    public static String intToDecString(int i, int i2) {
        StringBuffer stringBuffer = new StringBuffer(String.format("%0" + i2 + "d", Integer.valueOf(i)));
        return stringBuffer.substring(stringBuffer.length() - i2, stringBuffer.length());
    }

    public static String toHexString(byte b) {
        return new String(new char[]{HEX_CHARS.charAt((b >> 4) & 15), HEX_CHARS.charAt(b & 15)});
    }

    public static String binToHexString(byte[] bArr) {
        if (bArr == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            stringBuffer.append(toHexString(b));
        }
        return stringBuffer.toString();
    }

    public static int toInt(byte[] bArr) {
        int i = 0;
        int i2 = 0;
        for (int i3 = 3; i3 >= 0; i3--) {
            i += (bArr[i3] & 255) << i2;
            i2 += 8;
        }
        return i;
    }

    public static int[] hexStringListToIntList(String[] strArr) {
        int[] iArr = new int[strArr.length];
        for (int i = 0; i < strArr.length; i++) {
            iArr[i] = toInt(hexStringToBin(strArr[i]));
        }
        return iArr;
    }

    public static byte[] toAsciiArray(int i, int i2) {
        byte[] bArr = new byte[i2];
        String strIntToDecString = intToDecString(i, i2);
        for (int i3 = 0; i3 < i2; i3++) {
            bArr[i3] = (byte) strIntToDecString.charAt(i3);
        }
        return bArr;
    }

    public static int hexStringToInt(String str) {
        byte[] bArrHexStringToBin = hexStringToBin(str);
        byte[] bArr = new byte[4];
        for (int i = 0; i < bArrHexStringToBin.length; i++) {
            bArr[(4 - bArrHexStringToBin.length) + i] = (byte) (bArrHexStringToBin[i] & 255);
        }
        return toInt(bArr);
    }

    public static byte[] changeEndian(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        for (int i = 0; i < bArr.length; i++) {
            bArr2[(bArr.length - i) - 1] = bArr[i];
        }
        return bArr2;
    }

    public static String getClassShortName(String str) {
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf < 0 ? str : str.substring(iLastIndexOf + 1);
    }
}
