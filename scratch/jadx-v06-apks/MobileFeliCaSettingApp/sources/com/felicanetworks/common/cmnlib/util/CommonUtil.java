package com.felicanetworks.common.cmnlib.util;

import java.util.Date;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
public final class CommonUtil {
    private static final String HEX_CHARS = "0123456789ABCDEF";

    public static Date getSystemTime() {
        return new Date();
    }

    public static String paddingZero(String str, int i) {
        if (str.length() >= i) {
            return str;
        }
        int length = i - str.length();
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < length; i2++) {
            stringBuffer.append("0");
        }
        return ((Object) stringBuffer) + str;
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

    public static String unescape(String str) throws IllegalArgumentException {
        int i;
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < str.length(); i2 = i) {
            int i3 = i2 + 1;
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\\') {
                i = i3 + 1;
                char cCharAt2 = str.charAt(i3);
                if (cCharAt2 == 'u') {
                    int i4 = 0;
                    int i5 = 0;
                    while (i4 < 4) {
                        int i6 = i + 1;
                        char cCharAt3 = str.charAt(i);
                        switch (cCharAt3) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                i5 = ((i5 << 4) + cCharAt3) - 48;
                                break;
                            default:
                                switch (cCharAt3) {
                                    case 'A':
                                    case 'B':
                                    case 'C':
                                    case 'D':
                                    case 'E':
                                    case 'F':
                                        i5 = (((i5 << 4) + 10) + cCharAt3) - 65;
                                        break;
                                    default:
                                        switch (cCharAt3) {
                                            case 'a':
                                            case 'b':
                                            case 'c':
                                            case 'd':
                                            case 'e':
                                            case 'f':
                                                i5 = (((i5 << 4) + 10) + cCharAt3) - 97;
                                                break;
                                            default:
                                                throw new IllegalArgumentException("Malformed \\uxxxx encoding.");
                                        }
                                        break;
                                }
                                break;
                        }
                        i4++;
                        i = i6;
                    }
                    cCharAt = (char) i5;
                } else if (cCharAt2 == '\\') {
                    cCharAt = '\\';
                } else if (cCharAt2 == 'n') {
                    cCharAt = '\n';
                } else if (cCharAt2 == 'r') {
                    cCharAt = '\r';
                } else if (cCharAt2 != 't') {
                    stringBuffer.append(cCharAt);
                    cCharAt = cCharAt2;
                } else {
                    cCharAt = '\t';
                }
            } else {
                i = i3;
            }
            stringBuffer.append(cCharAt);
        }
        return stringBuffer.toString();
    }
}
