package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: DataStak.java */
/* JADX INFO: loaded from: classes.dex */
public class w {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a1[] f180a;
    private int b;

    public void a(int i) {
        if (i > 0) {
            this.f180a = new a1[i];
            this.b = 0;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [capacity = " + i + "]");
        throw new c1(w.class, "create", stringBuffer.toString());
    }

    public a1 b(int i) {
        a1[] a1VarArr = this.f180a;
        if (a1VarArr != null) {
            return a1VarArr[i];
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal state. Call crate() first.");
        throw new c1(w.class, "get", stringBuffer.toString());
    }

    public int c() {
        if (this.f180a != null) {
            return this.b;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal state. Call crate() first.");
        throw new c1(w.class, "getCount", stringBuffer.toString());
    }

    public a1 d() {
        a1[] a1VarArr = this.f180a;
        if (a1VarArr == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Call crate() first.");
            throw new c1(w.class, "peek", stringBuffer.toString());
        }
        int i = this.b;
        if (i == 0) {
            return null;
        }
        return a1VarArr[i - 1];
    }

    public a1 e() {
        if (this.f180a == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Call crate() first.");
            throw new c1(w.class, "pop", stringBuffer.toString());
        }
        if (this.b == 0) {
            return null;
        }
        a1 a1VarD = d();
        a1[] a1VarArr = this.f180a;
        int i = this.b;
        this.b = i - 1;
        a1VarArr[i] = null;
        return a1VarD;
    }

    public boolean f(a1 a1Var) {
        a1[] a1VarArr = this.f180a;
        if (a1VarArr == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Call crate() first.");
            throw new c1(w.class, "push", stringBuffer.toString());
        }
        if (a1Var != null) {
            int length = a1VarArr.length;
            int i = this.b;
            if (length == i) {
                return false;
            }
            this.b = i + 1;
            a1VarArr[i] = a1Var;
            return true;
        }
        StringBuffer stringBuffer2 = new StringBuffer();
        stringBuffer2.append("Illegal argument.");
        stringBuffer2.append(" [element = " + a1Var + "]");
        throw new c1(w.class, "push", stringBuffer2.toString());
    }

    public void g() {
        if (this.f180a == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Illegal state. Call crate() first.");
            throw new c1(w.class, "release", stringBuffer.toString());
        }
        for (int i = 0; i < this.b; i++) {
            this.f180a[i] = null;
        }
        this.b = 0;
    }
}
