package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: RespData.java */
/* JADX INFO: loaded from: classes.dex */
public class y0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f183a;
    private String b;
    private String c;

    public String a() {
        return this.c;
    }

    public String b() {
        return this.b;
    }

    public int c() {
        return this.f183a;
    }

    public void d(String str) {
        this.c = str;
    }

    public void e(String str) {
        this.b = str;
    }

    public void f(int i) {
        this.f183a = i;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("RespData statusCode = " + this.f183a);
        stringBuffer.append(", messageBody = " + this.b);
        stringBuffer.append(", contentType = " + this.c);
        return stringBuffer.toString();
    }
}
