package com.felicanetworks.mfc;

/* JADX INFO: compiled from: FelicaException.java */
/* JADX INFO: loaded from: classes.dex */
public class x extends Exception {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f122a;
    private int b;
    private int c;
    private int d;
    protected AppInfo e;

    public x(int i, int i2) {
        com.felicanetworks.mfc.s1.a.d(5, "%s id=%d type=%d", "000", Integer.valueOf(i), Integer.valueOf(i2));
        this.f122a = i;
        this.b = i2;
        com.felicanetworks.mfc.s1.a.b(5, "%s", "999");
    }

    public int a() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s id=%d", "999", Integer.valueOf(this.f122a));
        return this.f122a;
    }

    public AppInfo b() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s otherAppInfo=%s", "999", this.e);
        return this.e;
    }

    public int c() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s statusFlag1=%d", "999", Integer.valueOf(this.c));
        return this.c;
    }

    public int d() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s statusFlag2=%d", "999", Integer.valueOf(this.d));
        return this.d;
    }

    public int e() {
        com.felicanetworks.mfc.s1.a.b(6, "%s", "000");
        com.felicanetworks.mfc.s1.a.c(6, "%s type=%d", "999", Integer.valueOf(this.b));
        return this.b;
    }

    public x(int i, int i2, String str) {
        super(str);
        com.felicanetworks.mfc.s1.a.e(5, "%s id=%d type=%d message=%s", "000", Integer.valueOf(i), Integer.valueOf(i2), str);
        this.f122a = i;
        this.b = i2;
        com.felicanetworks.mfc.s1.a.b(5, "%s", "999");
    }

    public x(int i, int i2, AppInfo appInfo, int i3, int i4, String str) {
        super(str);
        com.felicanetworks.mfc.s1.a.g(5, "%s id=%d type=%d otherAppPID=%d statusFlag1=%d statusFlag2=%d msg=%s", "000", Integer.valueOf(i), Integer.valueOf(i2), appInfo, Integer.valueOf(i3), Integer.valueOf(i4), str);
        this.f122a = i;
        this.b = i2;
        this.e = appInfo;
        this.c = i3;
        this.d = i4;
        com.felicanetworks.mfc.s1.a.b(5, "%s", "999");
    }
}
