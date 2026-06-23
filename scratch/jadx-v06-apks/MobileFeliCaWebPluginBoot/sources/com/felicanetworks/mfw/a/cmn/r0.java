package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: Queue.java */
/* JADX INFO: loaded from: classes.dex */
public class r0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f173a;
    private int b;
    private Object[] c;

    private int c(int i) {
        return (i + 1) % this.c.length;
    }

    public void a(int i) {
        if (i > 0) {
            this.c = new Object[i + 1];
            this.f173a = 0;
            this.b = 0;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [capacity = " + i + "]");
        throw new c1(r0.class, "create", stringBuffer.toString());
    }

    public int b() {
        int i = this.f173a;
        int i2 = this.b;
        return i <= i2 ? i2 - i : i2 + (this.c.length - i);
    }

    public boolean d(Object obj) {
        if (this.c == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Call create first.");
            throw new c1(r0.class, "offer", stringBuffer.toString());
        }
        if (obj != null) {
            if (c(this.b) == this.f173a) {
                return false;
            }
            Object[] objArr = this.c;
            int i = this.b;
            objArr[i] = obj;
            this.b = c(i);
            return true;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Illegal argument.");
        stringBuffer2.append(" [element = " + obj + "]");
        throw new c1(r0.class, "offer", stringBuffer2.toString());
    }

    public Object e() {
        Object[] objArr = this.c;
        if (objArr == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Call create first.");
            throw new c1(r0.class, "peek", stringBuffer.toString());
        }
        int i = this.f173a;
        if (i == this.b) {
            return null;
        }
        return objArr[i];
    }

    public Object f() {
        if (this.c == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Call create first.");
            throw new c1(r0.class, "poll", stringBuffer.toString());
        }
        Object objE = e();
        int i = this.f173a;
        if (i != this.b) {
            this.c[i] = null;
            this.f173a = c(i);
        }
        return objE;
    }
}
