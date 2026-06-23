package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: Asn1Object.java */
/* JADX INFO: loaded from: classes.dex */
public class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private byte f153a;
    private int b = 0;
    private byte[] c = null;
    private byte[] d = null;
    private f[] e = null;

    public f(byte b) {
        this.f153a = (byte) 0;
        this.f153a = b;
    }

    public f[] a() {
        return this.e;
    }

    public byte[] b() {
        return this.d;
    }

    public int c() {
        return this.b;
    }

    public byte[] d() {
        return this.c;
    }

    public int e() {
        return this.f153a;
    }

    public void f(f[] fVarArr) {
        this.e = fVarArr;
    }

    public void g(byte[] bArr) {
        this.d = bArr;
    }

    public void h(int i) {
        this.b = i;
    }

    public void i(byte[] bArr) {
        this.c = bArr;
    }
}
