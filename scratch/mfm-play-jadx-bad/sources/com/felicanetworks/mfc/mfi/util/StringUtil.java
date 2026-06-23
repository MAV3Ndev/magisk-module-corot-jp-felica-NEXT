package com.felicanetworks.mfc.mfi.util;

import com.felicanetworks.mfc.util.LogMgr;
import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.regex.Pattern;

/* JADX INFO: loaded from: classes3.dex */
public class StringUtil {
    public static final int BOTH_VERSION_EQUAL = 0;
    private static final String CHARSET_UTF8 = "UTF-8";
    public static final int FIRST_VERSION_LOW = -1;
    public static final int SECOND_VERSION_LOW = 1;
    private static final String VERSION_DELIMITER = "\\.";

    public static boolean isValidChar(char target, char[] validChars) {
        for (char c : validChars) {
            if (target == c) {
                return true;
            }
        }
        return false;
    }

    public static String bytesToHexString(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        if (bytes.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bytes.length);
        for (byte b : bytes) {
            int i = b & 255;
            if (i < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Integer.toHexString(i));
        }
        return stringBuffer.toString().toUpperCase(Locale.ENGLISH);
    }

    public static String intToHexString(int target, int digit) {
        if (digit < 1 || 8 < digit) {
            return null;
        }
        String hexString = Integer.toHexString(target);
        int length = hexString.length();
        if (length < digit) {
            hexString = zeroPadding(hexString, digit);
        } else if (length > digit) {
            hexString = hexString.substring(length - digit, length);
        }
        return hexString.toUpperCase(Locale.ENGLISH);
    }

    public static byte[] hexToByteArray(String target) throws NumberFormatException {
        if (target == null) {
            return null;
        }
        if (target.equals("")) {
            return new byte[0];
        }
        if (target.length() % 2 != 0) {
            target = "0" + target;
        }
        int length = target.length() / 2;
        byte[] bArr = new byte[length];
        for (int i = 0; i < length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Long.parseLong(target.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static byte[] toUTF8ByteArrays(String target) {
        if (target == null) {
            return null;
        }
        if (target.equals("")) {
            return new byte[0];
        }
        try {
            return target.getBytes("UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static String toUTF8String(byte[] target) {
        if (target == null) {
            return null;
        }
        if (target.length == 0) {
            return "";
        }
        try {
            return new String(target, "UTF-8");
        } catch (UnsupportedEncodingException unused) {
            return null;
        }
    }

    public static int hexToInteger(String target) throws NumberFormatException {
        return Integer.parseInt(target, 16);
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String padding(String target, char adjuster, int length) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.setLength(0);
        stringBuffer.append(target);
        for (int length2 = stringBuffer.length(); length2 < length; length2++) {
            stringBuffer.insert(0, adjuster);
        }
        return stringBuffer.toString();
    }

    public static String zeroPadding(String target, int length) {
        return padding(target, '0', length);
    }

    public static int versionCompare(String firstVersion, String secondVersion) throws NumberFormatException {
        if (firstVersion == null || secondVersion == null) {
            LogMgr.log(2, "700", "Could not get MFC Version.");
            throw new NumberFormatException();
        }
        if (firstVersion.equals(secondVersion)) {
            return 0;
        }
        String[] strArrSplit = firstVersion.split(VERSION_DELIMITER);
        String[] strArrSplit2 = secondVersion.split(VERSION_DELIMITER);
        int iMax = Math.max(strArrSplit.length, strArrSplit2.length);
        int i = 0;
        while (i < iMax) {
            int i2 = i < strArrSplit.length ? Integer.parseInt(strArrSplit[i]) : 0;
            int i3 = i < strArrSplit2.length ? Integer.parseInt(strArrSplit2[i]) : 0;
            if (i2 < i3) {
                return -1;
            }
            if (i2 > i3) {
                return 1;
            }
            i++;
        }
        return 0;
    }

    public static boolean checkPattern(String value, String pattern) {
        return Pattern.compile(pattern).matcher(value).find();
    }
}
