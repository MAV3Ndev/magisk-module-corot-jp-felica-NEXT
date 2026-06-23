package com.felicanetworks.mfw.a.cmn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: compiled from: Base64Util.java */
/* JADX INFO: loaded from: classes.dex */
public class i {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private static final h f156a = new h();

    public static byte[] a(String str) {
        if (str != null && b1.p(str)) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                f156a.a(str, byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            } catch (IOException unused) {
                throw new c1(i.class, "decode");
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [target = " + str + "]");
        throw new c1(i.class, "decode", stringBuffer.toString());
    }
}
