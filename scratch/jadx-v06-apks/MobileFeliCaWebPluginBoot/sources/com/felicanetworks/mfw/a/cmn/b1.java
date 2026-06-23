package com.felicanetworks.mfw.a.cmn;

import android.content.res.AssetFileDescriptor;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* JADX INFO: compiled from: StringUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class b1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static String f144a = "utf-8";
    public static final char[] b = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    public static final char[] c = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'A', 'B', 'C', 'D', 'E', 'F'};
    public static final char[] d = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] e = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
    public static final char[] f = {' ', '!', '#', '$', '%', '&', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '<', '=', '>', '?', '@', '[', ']', '^', '_', '`', '{', '|', '}', '~'};
    public static final char[] g = {' ', '!', '\"', '#', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', ':', ';', '<', '=', '>', '?', '@', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '[', '\\', ']', '^', '_', '`', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '{', '|', '}', '~'};
    public static final char[] h = {'!', '$', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', '=', '?', '@', '_', '~'};
    public static final char[] i = {'!', '$', '%', '&', '\'', '(', ')', '*', '+', ',', '-', '.', '/', ':', ';', '=', '?', '@', '_', '~'};

    public static boolean A(String str, int i2, int i3) {
        int iCodePointCount = str.codePointCount(0, str.length());
        return i2 <= iCodePointCount && iCodePointCount <= i3;
    }

    public static boolean B(String str, char[] cArr) {
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (!s(str.charAt(i2), cArr)) {
                return false;
            }
        }
        return true;
    }

    private static boolean C(String str) {
        return !l(str) && s(str.charAt(0), d) && s(str.charAt(str.length() - 1), e) && B(str, b(e, new char[]{'-'}));
    }

    public static boolean D(String str) {
        int iIndexOf = str.indexOf(35);
        if (iIndexOf != -1) {
            if (!u(str.substring(iIndexOf + 1))) {
                return false;
            }
            str = str.substring(0, iIndexOf);
        }
        return E(str);
    }

    private static boolean E(String str) {
        if (!str.regionMatches(true, 0, "http://", 0, 7) && !str.regionMatches(true, 0, "https://", 0, 8)) {
            return false;
        }
        String strSubstring = str.substring(str.indexOf("://") + 3);
        if (!F(strSubstring) || !x(strSubstring)) {
            return false;
        }
        int iIndexOf = strSubstring.indexOf(47);
        if (iIndexOf != -1) {
            strSubstring = strSubstring.substring(0, iIndexOf);
        }
        return w(strSubstring);
    }

    public static boolean F(String str) {
        return B(str, b(e, i));
    }

    public static String G(String str, char c2, int i2) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(str);
        for (int length = stringBuffer.length(); length < i2; length++) {
            stringBuffer.insert(0, c2);
        }
        return stringBuffer.toString();
    }

    public static String H(List list, String str) {
        StringBuilder sb = new StringBuilder();
        if (!list.isEmpty()) {
            sb.append("\n" + str);
            Iterator it = list.iterator();
            while (it.hasNext()) {
                sb.append("\n[" + ((String) it.next()) + "]");
            }
        }
        return sb.toString();
    }

    public static byte[] I(String str) {
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
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 2;
            bArr[i2] = (byte) Long.parseLong(str.substring(i3, i3 + 2), 16);
        }
        return bArr;
    }

    public static byte[] J(String str) {
        try {
            return str.getBytes(f144a);
        } catch (UnsupportedEncodingException unused) {
            throw new c1(b1.class, "toBytes");
        }
    }

    public static String K(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        if (bArr.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer(bArr.length * 2);
        for (byte b2 : bArr) {
            int i2 = b2 & 255;
            if (i2 < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Integer.toHexString(i2));
        }
        return stringBuffer.toString().toUpperCase(Locale.US);
    }

    public static String L(String str) {
        return N("http://" + str).substring(7);
    }

    public static String M(String str) {
        if (!D(str)) {
            throw new c1(b1.class, "toLowerCaseScheme");
        }
        int iIndexOf = str.indexOf("://") + 3;
        return str.substring(0, iIndexOf).toLowerCase(Locale.US) + str.substring(iIndexOf);
    }

    public static String N(String str) {
        if (!D(str)) {
            throw new c1(b1.class, "toLowerCaseURL");
        }
        int iIndexOf = str.indexOf("/", 8);
        if (iIndexOf <= 0) {
            return str.toLowerCase(Locale.US);
        }
        StringBuffer stringBuffer = new StringBuffer(str.substring(0, iIndexOf).toLowerCase(Locale.US));
        stringBuffer.append(str.substring(iIndexOf));
        return stringBuffer.toString();
    }

    public static String O(String str) {
        if (!str.regionMatches(true, 0, "http://", 0, 7) && !str.regionMatches(true, 0, "https://", 0, 8)) {
            return "";
        }
        int iIndexOf = str.indexOf(35);
        if (iIndexOf != -1) {
            str = str.substring(0, iIndexOf);
        }
        int iIndexOf2 = str.indexOf("://") + 3;
        int iIndexOf3 = str.substring(iIndexOf2).indexOf(47);
        return iIndexOf3 != -1 ? str.substring(0, iIndexOf2 + iIndexOf3) : str;
    }

    public static String P(String str, int i2) {
        return G(str, '0', i2);
    }

    private static void a(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception unused) {
            }
        }
    }

    public static char[] b(char[] cArr, char[] cArr2) {
        char[] cArr3 = new char[cArr.length + cArr2.length];
        System.arraycopy(cArr, 0, cArr3, 0, cArr.length);
        System.arraycopy(cArr2, 0, cArr3, cArr.length, cArr2.length);
        return cArr3;
    }

    public static boolean c(String[] strArr, String str) {
        if (strArr != null && str != null) {
            for (String str2 : strArr) {
                if (str.equals(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static ArrayList d(String str) throws Throwable {
        IOException e2;
        AssetFileDescriptor.AutoCloseInputStream autoCloseInputStream = 0;
        if (str == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            if ("".equals(str)) {
                arrayList.add("");
                return arrayList;
            }
            try {
                BufferedReader bufferedReader = new BufferedReader(new StringReader(str));
                while (true) {
                    try {
                        String line = bufferedReader.readLine();
                        if (line == null) {
                            a(bufferedReader);
                            return arrayList;
                        }
                        arrayList.add(line);
                    } catch (IOException e3) {
                        e2 = e3;
                        throw new c1(b1.class, "createLineList", "Could not createLineList. [target = " + str + "]", e2);
                    }
                }
            } catch (IOException e4) {
                e2 = e4;
            } catch (Throwable th) {
                th = th;
                a(autoCloseInputStream);
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
            autoCloseInputStream = "";
        }
    }

    public static String[] e(String str, String str2) {
        if (str == null) {
            return null;
        }
        if ("".equals(str2) || str2 == null) {
            return new String[]{str};
        }
        ArrayList arrayList = new ArrayList();
        int iIndexOf = str.indexOf(str2, 0);
        if (iIndexOf < 0) {
            arrayList.add(str);
        } else {
            int iCodePointCount = 0;
            while (iCodePointCount <= str.codePointCount(0, str.length())) {
                arrayList.add(str.substring(iCodePointCount, iIndexOf));
                iCodePointCount = str2.codePointCount(0, str2.length()) + iIndexOf;
                iIndexOf = str.indexOf(str2, iCodePointCount);
                if (iIndexOf < 0) {
                    iIndexOf = str.codePointCount(0, str.length());
                }
            }
        }
        String[] strArr = new String[arrayList.size()];
        for (int i2 = 0; i2 < arrayList.size(); i2++) {
            strArr[i2] = (String) arrayList.get(i2);
        }
        return strArr;
    }

    public static i0[] f(String str) {
        if (str == null) {
            return null;
        }
        if (str.endsWith("\r\n")) {
            str = str.substring(0, str.codePointCount(0, str.length()) - 2);
        }
        String[] strArrE = e(str, "\r\n");
        i0[] i0VarArr = new i0[strArrE.length];
        String[] strArr = new String[strArrE.length];
        for (int i2 = 0; i2 < strArrE.length; i2++) {
            String str2 = strArrE[i2];
            if (-1 == str2.indexOf(": ")) {
                return null;
            }
            String strSubstring = str2.substring(0, str2.indexOf(": "));
            String strSubstring2 = str2.substring(str2.indexOf(": ") + ": ".codePointCount(0, 2));
            if (!strSubstring2.startsWith("\"") || !strSubstring2.endsWith("\"") || c(strArr, strSubstring)) {
                return null;
            }
            strArr[i2] = strSubstring;
            i0VarArr[i2] = new i0(strSubstring, strSubstring2.substring(1, strSubstring2.length() - 1));
        }
        return i0VarArr;
    }

    public static String g(String str, String str2) {
        if (str == null || str2 == null) {
            return null;
        }
        if (str2.startsWith(str + ":")) {
            return str2.substring(str2.indexOf(":") + 1);
        }
        return null;
    }

    public static String h(String str, int i2) {
        if (str == null || i2 < 1) {
            throw new c1(b1.class, "insertBreak", "invalidParameters");
        }
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = d(str).iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            int i3 = 0;
            int iCodePointCount = str2.codePointCount(0, str2.length());
            int i4 = 0;
            while (i3 < iCodePointCount) {
                int i5 = i3 + 1;
                String strSubstring = str2.substring(i3, i5);
                i4 = J(strSubstring).length == 1 ? i4 + 1 : i4 + 2;
                if (i4 > i2) {
                    stringBuffer.append("\n");
                    i4 = J(strSubstring).length == 1 ? 1 : 2;
                }
                stringBuffer.append(strSubstring);
                i3 = i5;
            }
            if (it.hasNext()) {
                stringBuffer.append("\n");
            }
        }
        if (str.endsWith("\r") || str.endsWith("\n")) {
            stringBuffer.append("\n");
        }
        return stringBuffer.toString();
    }

    public static boolean i(String str) {
        return B(str, g);
    }

    public static boolean j(String str) {
        return B(str, e);
    }

    public static boolean k(String str) {
        return B(str, b);
    }

    public static boolean l(String str) {
        return "".equals(str);
    }

    public static boolean m(String str) {
        return B(str, c);
    }

    private static boolean n(String str) {
        return B(str, b(e, new char[]{'.', '-', ':'}));
    }

    public static boolean o(String str) {
        return B(str, b(e, f));
    }

    public static boolean p(String str) {
        return str != null && str.length() != 0 && str.matches("[0-9a-zA-Z+/]+[=]?[=]?") && str.length() % 4 == 0;
    }

    public static boolean q(String str, int i2) {
        return J(str).length == i2;
    }

    public static boolean r(String str, int i2, int i3) {
        int length = J(str).length;
        return i2 <= length && length <= i3;
    }

    public static boolean s(char c2, char[] cArr) {
        for (char c3 : cArr) {
            if (c2 == c3) {
                return true;
            }
        }
        return false;
    }

    private static boolean t(String str) {
        if (l(str)) {
            return false;
        }
        return s(str.charAt(0), e) && s(str.charAt(str.length() - 1), e) && B(str, b(e, new char[]{'-'}));
    }

    private static boolean u(String str) {
        return B(str, b(e, h));
    }

    private static boolean v(String str) {
        String[] strArrE = e(str, ".");
        if (strArrE.length != 0 && !C(strArrE[strArrE.length - 1])) {
            return false;
        }
        for (int i2 = 0; i2 < strArrE.length - 1; i2++) {
            if (!t(strArrE[i2])) {
                return false;
            }
        }
        return true;
    }

    private static boolean w(String str) {
        if (!n(str)) {
            return false;
        }
        int iIndexOf = str.indexOf(58);
        if (iIndexOf != -1) {
            if (str.lastIndexOf(58) != iIndexOf) {
                return false;
            }
            String strSubstring = str.substring(0, iIndexOf);
            String strSubstring2 = str.substring(iIndexOf + 1);
            if (!v(strSubstring) || !y(strSubstring2)) {
                return false;
            }
        } else if (!v(str)) {
            return false;
        }
        return true;
    }

    private static boolean x(String str) {
        int iIndexOf = str.indexOf(37);
        while (iIndexOf != -1) {
            if (str.substring(iIndexOf).length() < 3) {
                return false;
            }
            int i2 = iIndexOf + 1;
            int i3 = iIndexOf + 3;
            if (!m(str.substring(i2, i3))) {
                return false;
            }
            iIndexOf = str.indexOf(37, i3);
        }
        return true;
    }

    private static boolean y(String str) {
        return !l(str) && k(str) && str.length() < 6 && z(Integer.parseInt(str));
    }

    private static boolean z(int i2) {
        return p0.a().b("url.port.upperbound") >= i2 && i2 >= p0.a().b("url.port.lowerbound");
    }
}
