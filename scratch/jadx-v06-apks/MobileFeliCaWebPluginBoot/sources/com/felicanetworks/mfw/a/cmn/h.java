package com.felicanetworks.mfw.a.cmn;

import java.io.IOException;
import java.io.OutputStream;

/* JADX INFO: compiled from: Base64Util.java */
/* JADX INFO: loaded from: classes.dex */
class h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected final byte[] f154a = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};
    protected byte b = 61;
    protected final byte[] c = new byte[128];

    public h() {
        d();
    }

    private int b(OutputStream outputStream, char c, char c2, char c3, char c4) throws IOException {
        char c5 = this.b;
        if (c3 == c5) {
            byte[] bArr = this.c;
            byte b = bArr[c];
            outputStream.write((bArr[c2] >> 4) | (b << 2));
            return 1;
        }
        if (c4 == c5) {
            byte[] bArr2 = this.c;
            byte b2 = bArr2[c];
            byte b3 = bArr2[c2];
            byte b4 = bArr2[c3];
            outputStream.write((b2 << 2) | (b3 >> 4));
            outputStream.write((b4 >> 2) | (b3 << 4));
            return 2;
        }
        byte[] bArr3 = this.c;
        byte b5 = bArr3[c];
        byte b6 = bArr3[c2];
        byte b7 = bArr3[c3];
        byte b8 = bArr3[c4];
        outputStream.write((b5 << 2) | (b6 >> 4));
        outputStream.write((b6 << 4) | (b7 >> 2));
        outputStream.write(b8 | (b7 << 6));
        return 3;
    }

    private boolean c(char c) {
        return c == '\n' || c == '\r' || c == '\t' || c == ' ';
    }

    private int e(String str, int i, int i2) {
        while (i < i2 && c(str.charAt(i))) {
            i++;
        }
        return i;
    }

    public int a(String str, OutputStream outputStream) throws IOException {
        int length = str.length();
        while (length > 0 && c(str.charAt(length - 1))) {
            length--;
        }
        int i = length - 4;
        int i2 = 0;
        int iE = e(str, 0, i);
        while (iE < i) {
            int i3 = iE + 1;
            byte b = this.c[str.charAt(iE)];
            int iE2 = e(str, i3, i);
            int i4 = iE2 + 1;
            byte b2 = this.c[str.charAt(iE2)];
            int iE3 = e(str, i4, i);
            int i5 = iE3 + 1;
            byte b3 = this.c[str.charAt(iE3)];
            int iE4 = e(str, i5, i);
            int i6 = iE4 + 1;
            byte b4 = this.c[str.charAt(iE4)];
            outputStream.write((b << 2) | (b2 >> 4));
            outputStream.write((b2 << 4) | (b3 >> 2));
            outputStream.write((b3 << 6) | b4);
            i2 += 3;
            iE = e(str, i6, i);
        }
        return i2 + b(outputStream, str.charAt(i), str.charAt(length - 3), str.charAt(length - 2), str.charAt(length - 1));
    }

    protected void d() {
        int i = 0;
        while (true) {
            byte[] bArr = this.f154a;
            if (i >= bArr.length) {
                return;
            }
            this.c[bArr[i]] = (byte) i;
            i++;
        }
    }
}
