package com.felicanetworks.semc.util;

import java.nio.charset.StandardCharsets;
import java.util.Locale;

/* JADX INFO: loaded from: classes3.dex */
public class StringUtil {
    public static String bytesToHexString(byte[] bArr) {
        LogMgrUtil.log(7, "000");
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(byteToHexString(b));
        }
        String string = sb.toString();
        LogMgrUtil.log(7, "999");
        return string;
    }

    public static String byteToHexString(byte b) {
        String hexString;
        int i = b & 255;
        if (i < 16) {
            hexString = "0" + Integer.toHexString(i);
        } else {
            hexString = Integer.toHexString(i);
        }
        return hexString.toUpperCase(Locale.ENGLISH);
    }

    public static byte[] hexToByteArray(String str) throws NumberFormatException {
        LogMgrUtil.log(7, "000");
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
        LogMgrUtil.log(7, "999");
        return bArr;
    }

    public static byte[] toUTF8ByteArrays(String str) {
        LogMgrUtil.log(7, "000");
        if (str == null) {
            return null;
        }
        if (str.equals("")) {
            return new byte[0];
        }
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        LogMgrUtil.log(7, "999");
        return bytes;
    }

    public static String toUTF8String(byte[] bArr) {
        LogMgrUtil.log(7, "000");
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        String str = new String(bArr, StandardCharsets.UTF_8);
        LogMgrUtil.log(7, "999");
        return str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static String padding(String str, char c, int i) {
        LogMgrUtil.log(7, "000");
        StringBuilder sb = new StringBuilder();
        sb.setLength(0);
        sb.append(str);
        for (int length = sb.length(); length < i; length++) {
            sb.insert(0, c);
        }
        LogMgrUtil.log(7, "999");
        return sb.toString();
    }
}
