package com.felicanetworks.mfw.i.cmn;

import com.felicanetworks.mfw.i.fbl.Property;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/* JADX INFO: loaded from: classes.dex */
public class StringUtil {
    public static char[] sDec = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static char[] sHex = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static char[] sAlpha = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static char[] sDecAndAlpha = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    private static final char[] FLAGMENT_CHARS = {'!', '$', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', '=', '?', '@', '_', '~'};
    private static final char[] URL_CHARS = {'!', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '?', '@', '_', '~'};
    private static final char[] BASE64_CHARS = {'+', '/'};
    private static final char[] TOP_DOMAIN_CHARS = {'-'};
    private static final char[] VALID_CHARS = concatChars(sDecAndAlpha, new char[]{'.', '-', ':'});
    private static StringBuffer sBuff = new StringBuffer();
    private static ArrayList sList = new ArrayList();

    public static char[] concatChars(char[] cArr, char[] cArr2) {
        char[] cArr3 = new char[cArr.length + cArr2.length];
        System.arraycopy(cArr, 0, cArr3, 0, cArr.length);
        System.arraycopy(cArr2, 0, cArr3, cArr.length, cArr2.length);
        return cArr3;
    }

    public static boolean isValidString(String str, char[] cArr) {
        for (int i = 0; i < str.length(); i++) {
            if (!isValidChar(str.charAt(i), cArr)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidChar(char c, char[] cArr) {
        for (char c2 : cArr) {
            if (c == c2) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDecString(String str) {
        return isValidString(str, sDec);
    }

    public static boolean isHexString(String str) {
        return isValidString(str, sHex);
    }

    public static boolean isAlpha(String str) {
        return isValidString(str, sAlpha);
    }

    public static boolean isDecOrAlpha(String str) {
        return isValidString(str, sDecAndAlpha);
    }

    public static boolean isValidBase64(String str) {
        if (str == null || str.length() == 0 || str.length() % 4 != 0) {
            return false;
        }
        char cCharAt = str.charAt(str.length() - 2);
        char cCharAt2 = str.charAt(str.length() - 1);
        if (cCharAt == '=') {
            if (cCharAt2 != '=') {
                return false;
            }
            str = str.substring(0, str.length() - 2);
        } else if (cCharAt2 == '=') {
            str = str.substring(0, str.length() - 1);
        }
        return isValidString(str, concatChars(sDecAndAlpha, BASE64_CHARS));
    }

    public static boolean isValidAttribute(String str) {
        return isValidString(str, concatChars(sDecAndAlpha, new char[]{' ', '!', '#', '$', '%', '&', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', ']', '^', '_', '`', '{', '|', '}', '~'}));
    }

    public static boolean isValidURL(String str) {
        int iIndexOf = str.indexOf(35);
        if (iIndexOf != -1) {
            if (!isValidFlagment(str.substring(iIndexOf + 1))) {
                return false;
            }
            str = str.substring(0, iIndexOf);
        }
        return isValidURLIgnoreFlagment(str);
    }

    private static boolean isValidFlagment(String str) {
        return isValidString(str, concatChars(sDecAndAlpha, FLAGMENT_CHARS));
    }

    private static boolean isValidURLIgnoreFlagment(String str) {
        if (!str.startsWith("http://") && !str.startsWith("https://")) {
            return false;
        }
        String strSubstring = str.substring(str.indexOf("://") + 3);
        if (!isValidUrlCharacter(strSubstring) || !isValidPercent(strSubstring)) {
            return false;
        }
        int iIndexOf = strSubstring.indexOf(47);
        if (iIndexOf != -1) {
            strSubstring = strSubstring.substring(0, iIndexOf);
        }
        return isValidHostAndPort(strSubstring);
    }

    public static boolean isValidUrlCharacter(String str) {
        return isValidString(str, concatChars(sDecAndAlpha, URL_CHARS));
    }

    private static boolean isValidPercent(String str) {
        int iIndexOf = str.indexOf(37);
        if (iIndexOf != -1) {
            return str.length() >= iIndexOf + 2 && isHexString(str.substring(iIndexOf + 1, iIndexOf + 3));
        }
        return true;
    }

    private static boolean isValidHostAndPort(String str) {
        if (!isVaildHostAndPortCharacter(str)) {
            return false;
        }
        int iIndexOf = str.indexOf(58);
        if (iIndexOf != -1) {
            if (str.lastIndexOf(58) != iIndexOf) {
                return false;
            }
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1);
            if (!isValidHost(strSubstring) || !isValidPort(strSubstring2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isVaildHostAndPortCharacter(String str) {
        return isValidString(str, VALID_CHARS);
    }

    private static boolean isValidHost(String str) {
        String[] strArrCreateSeparated = createSeparated(str, ".");
        if (strArrCreateSeparated.length != 0 && !isValidTopLabel(strArrCreateSeparated[strArrCreateSeparated.length - 1])) {
            return false;
        }
        for (int i = 0; i < strArrCreateSeparated.length - 1; i++) {
            if (!isValidDomainLabel(strArrCreateSeparated[i])) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidTopLabel(String str) {
        return !isEmpty(str) && isValidChar(str.charAt(0), sAlpha) && isValidChar(str.charAt(str.length() - 1), sDecAndAlpha) && isValidString(str, concatChars(sDecAndAlpha, TOP_DOMAIN_CHARS));
    }

    private static boolean isValidDomainLabel(String str) {
        if (isEmpty(str)) {
            return false;
        }
        return isValidChar(str.charAt(0), sDecAndAlpha) && isValidChar(str.charAt(str.length() - 1), sDecAndAlpha) && isValidString(str, concatChars(sDecAndAlpha, TOP_DOMAIN_CHARS));
    }

    private static boolean isValidPort(String str) {
        return !isEmpty(str) && isDecString(str);
    }

    public static boolean isAllZero(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return false;
        }
        for (byte b : bArr) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    public static String toHexString(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return Property.URL_VERUP_SITE;
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b : bArr) {
            int i = b & 255;
            if (i < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Integer.toHexString(i));
        }
        return stringBuffer.toString().toUpperCase();
    }

    public static byte[] toByteArray(String str) {
        if (str == null) {
            return null;
        }
        if (str.equals(Property.URL_VERUP_SITE)) {
            return new byte[0];
        }
        if (str.length() % 2 != 0) {
            str = "0" + str;
        }
        byte[] bArr = new byte[str.length() / 2];
        for (int i = 0; i < bArr.length; i++) {
            int i2 = i * 2;
            bArr[i] = (byte) Long.parseLong(str.substring(i2, i2 + 2), 16);
        }
        return bArr;
    }

    public static String padding(String str, char c, int i) {
        sBuff.setLength(0);
        sBuff.append(str);
        for (int length = sBuff.length(); length < i; length++) {
            sBuff.insert(0, c);
        }
        return sBuff.toString();
    }

    public static String zeroPadding(String str, int i) {
        return padding(str, '0', i);
    }

    public static boolean isValidLength(String str, int i) {
        return str.length() == i;
    }

    public static boolean isValidBetweenLength(String str, int i, int i2) {
        return str.length() >= i && str.length() <= i2;
    }

    public static boolean isEmpty(String str) {
        return Property.URL_VERUP_SITE.equals(str);
    }

    public static String[] createSeparated(String str, String str2) {
        if (str == null) {
            return null;
        }
        if (Property.URL_VERUP_SITE.equals(str2) || str2 == null) {
            return new String[]{str};
        }
        sList.clear();
        int iIndexOf = str.indexOf(str2, 0);
        if (iIndexOf < 0) {
            sList.add(str);
        } else {
            int iIndexOf2 = iIndexOf;
            int length = 0;
            while (length <= str.length()) {
                sList.add(str.substring(length, iIndexOf2));
                length = str2.length() + iIndexOf2;
                iIndexOf2 = str.indexOf(str2, length);
                if (iIndexOf2 < 0) {
                    iIndexOf2 = str.length();
                }
            }
        }
        String[] strArr = new String[sList.size()];
        for (int i = 0; i < sList.size(); i++) {
            strArr[i] = (String) sList.get(i);
        }
        return strArr;
    }

    public static ArrayList createLineList(String str) throws Throwable {
        BufferedReader bufferedReader = null;
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            if (Property.URL_VERUP_SITE.equals(str)) {
                arrayList.add(Property.URL_VERUP_SITE);
                return arrayList;
            }
            try {
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(str.getBytes())));
                while (true) {
                    try {
                        String line = bufferedReader2.readLine();
                        if (line != null) {
                            arrayList.add(line);
                        } else {
                            closeQuietly(bufferedReader2);
                            return arrayList;
                        }
                    } catch (IOException unused) {
                        throw new SysException((Class<?>) StringUtil.class, "createLineList", "Could not createLineList. [target = " + str + "]");
                    } catch (Throwable th) {
                        th = th;
                        bufferedReader = bufferedReader2;
                        closeQuietly(bufferedReader);
                        throw th;
                    }
                }
            } catch (IOException unused2) {
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static void closeQuietly(BufferedReader bufferedReader) {
        if (bufferedReader != null) {
            try {
                bufferedReader.close();
            } catch (Exception unused) {
            }
        }
    }

    public static Item[] createYamlItems(String str) {
        if (str == null) {
            return null;
        }
        if (str.endsWith("\r\n")) {
            str = str.substring(0, str.length() - 2);
        }
        String[] strArrCreateSeparated = createSeparated(str, "\r\n");
        Item[] itemArr = new Item[strArrCreateSeparated.length];
        for (int i = 0; i < strArrCreateSeparated.length; i++) {
            String str2 = strArrCreateSeparated[i];
            if (-1 == str2.indexOf(": ")) {
                return null;
            }
            String strSubstring = str2.substring(0, str2.indexOf(": "));
            String strSubstring2 = str2.substring(str2.indexOf(": ") + 2);
            if (!strSubstring2.startsWith("\"") || !strSubstring2.endsWith("\"")) {
                return null;
            }
            itemArr[i] = new Item(strSubstring, strSubstring2.substring(1, strSubstring2.length() - 1));
        }
        return itemArr;
    }

    public static boolean contains(String[] strArr, String str) {
        if (strArr != null && str != null) {
            for (String str2 : strArr) {
                if (str.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }
}
