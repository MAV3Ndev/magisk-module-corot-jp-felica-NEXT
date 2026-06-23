package com.felicanetworks.mfc.mfi.util;

import android.util.Base64;

/* JADX INFO: loaded from: classes.dex */
public class Base64Util {
    public static String encode(byte[] bArr) {
        if (bArr == null) {
            return null;
        }
        return Base64.encodeToString(bArr, 11);
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        return encode(StringUtil.toUTF8ByteArrays(str));
    }

    public static byte[] decode(String str) {
        if (str == null) {
            return null;
        }
        return Base64.decode(str, 11);
    }

    public static String decodeToUTF8String(String str) {
        if (str == null) {
            return null;
        }
        return StringUtil.toUTF8String(decode(str));
    }
}
