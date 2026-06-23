package com.felicanetworks.mfw.a.cmn;

import android.os.Build;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/* JADX INFO: compiled from: NwConUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class k0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    public static final Integer f161a = 60000;

    private k0() {
    }

    public static String a(byte[] bArr, String str) throws IOException {
        for (byte b : bArr) {
            if (d((char) b)) {
                throw new IOException("failed to encode.");
            }
        }
        try {
            return Charset.forName(str).newDecoder().decode(ByteBuffer.wrap(bArr)).toString();
        } catch (Exception unused) {
            throw new IOException("failed to encode.");
        }
    }

    public static String b(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new c1(k0.class, "encode", e);
        }
    }

    public static String c() {
        StringBuilder sb = new StringBuilder();
        String strC = p0.a().c("application.version");
        String strG = b0.g();
        String strC2 = k.e().c();
        String str = Build.MODEL;
        sb.append("MobileFeliCaWebPlugin/" + strC);
        sb.append(" (Android " + strG + "; " + strC2 + "; " + str + ")");
        return sb.toString();
    }

    public static boolean d(char c) {
        if (c >= 0 && c <= '\t') {
            return true;
        }
        if (11 > c || c > '\f') {
            return (14 <= c && c <= 31) || c == 127;
        }
        return true;
    }
}
