package com.felicanetworks.mfm.playIntegrity.util;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class StringUtil {
    private static final String CHARSET_UTF8 = "UTF-8";

    public static boolean isValidChar(char c, char[] cArr) {
        for (char c2 : cArr) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    public static String bytesToHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length);
        for (byte b : bArr) {
            int i = b & 255;
            if (i < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Integer.toHexString(i));
        }
        return stringBuffer.toString().toUpperCase(Locale.ENGLISH);
    }

    public static String intToHexString(int i, int i2) {
        if (i2 < 1 || 8 < i2) {
            return null;
        }
        String hexString = Integer.toHexString(i);
        int length = hexString.length();
        if (length < i2) {
            hexString = zeroPadding(hexString, i2);
        } else if (length > i2) {
            hexString = hexString.substring(length - i2, length);
        }
        return hexString.toUpperCase(Locale.ENGLISH);
    }

    public static byte[] hexToByteArray(String str) throws NumberFormatException {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return new byte[0];
        }
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }
        int length = str.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Long.parseLong(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static byte[] toUTF8ByteArrays(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return new byte[0];
        }
        try {
            return str.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static String toUTF8String(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        try {
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static int hexToInteger(String str) throws NumberFormatException {
        return Integer.parseInt(str, 16);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String padding(String str, char c, int i) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.setLength(0);
        stringBuffer.append(str);
        for (int length = stringBuffer.length(); length < i; length++) {
            stringBuffer.insert(0, c);
        }
        return stringBuffer.toString();
    }

    public static String zeroPadding(String str, int i) {
        return padding(str, '0', i);
    }
}
