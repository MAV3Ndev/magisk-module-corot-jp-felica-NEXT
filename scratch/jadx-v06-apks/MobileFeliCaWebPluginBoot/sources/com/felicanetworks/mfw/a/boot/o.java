package com.felicanetworks.mfw.a.boot;

import a.a.a.a.c.l0;
import a.a.a.a.c.n0;
import com.felicanetworks.mfw.a.cmn.b1;
import com.felicanetworks.mfw.a.cmn.c1;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/* JADX INFO: compiled from: PrblmAnalyzeLogCrtr.java */
/* JADX INFO: loaded from: classes.dex */
public class o {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private l0 f136a;

    public o(l0 l0Var) {
        this.f136a = l0Var;
    }

    private byte[] b(Throwable th) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(th.toString());
        stringBuffer.append("\r\n");
        for (StackTraceElement stackTraceElement : (!(th instanceof c1) || th.getCause() == null) ? th.getStackTrace() : th.getCause().getStackTrace()) {
            stringBuffer.append(stackTraceElement.toString());
            stringBuffer.append("\r\n");
        }
        return b1.J(stringBuffer.toString());
    }

    private byte[] c() {
        int iJ;
        byte[] bArr = new byte[0];
        l0 l0Var = this.f136a;
        if (l0Var == null || (iJ = l0Var.j()) == 0) {
            return bArr;
        }
        StringBuffer stringBuffer = new StringBuffer();
        n0 n0VarH = this.f136a.h();
        stringBuffer.append(this.f136a.i(iJ - 1).c() + "; ");
        if (n0VarH != null) {
            stringBuffer.append(n0VarH.c() + "; ");
            stringBuffer.append(n0VarH.a() + "; ");
            stringBuffer.append(n0VarH.b() + "\r\n");
        }
        for (int i = iJ - 2; i >= 0; i += -1) {
            stringBuffer.append(this.f136a.i(i).c() + ";\r\n");
        }
        return b1.J(stringBuffer.toString());
    }

    private byte[] d(byte[] bArr, int i) {
        if (bArr.length == i) {
            return bArr;
        }
        byte[] bArr2 = new byte[i];
        if (bArr.length < i) {
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
            for (int length = bArr.length; length < i; length++) {
                bArr2[length] = 32;
            }
        } else {
            System.arraycopy(bArr, 0, bArr2, 0, i);
        }
        return bArr2;
    }

    public byte[] a(Throwable th) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] bArrB = b(th);
            byte[] bArrC = c();
            byteArrayOutputStream.write(new byte[]{1});
            byteArrayOutputStream.write(d(bArrB, 512));
            byteArrayOutputStream.write(d(bArrC, 512));
            return byteArrayOutputStream.toByteArray();
        } catch (IOException unused) {
            return new byte[0];
        }
    }
}
