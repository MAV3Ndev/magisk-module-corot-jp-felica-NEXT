package com.felicanetworks.mfc.mfi.util;

import android.util.Base64;

/* JADX INFO: loaded from: classes3.dex */
public class Base64Util {
    public static String encode(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        return Base64.encodeToString(bytes, 11);
    }

    public static String encode(String str) {
        if (str == null) {
            return null;
        }
        return encode(StringUtil.toUTF8ByteArrays(str));
    }

    public static byte[] decode(String data) {
        if (data == null) {
            return null;
        }
        return Base64.decode(data, 11);
    }

    public static String decodeToUTF8String(String data) {
        if (data == null) {
            return null;
        }
        return StringUtil.toUTF8String(decode(data));
    }
}
