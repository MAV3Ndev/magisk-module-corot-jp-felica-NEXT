package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: AppFelicaException.java */
/* JADX INFO: loaded from: classes.dex */
public class e extends Exception {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f150a;
    private int b;
    private int c;

    public e(int i, int i2, int i3, String str) {
        super(str);
        this.f150a = 0;
        this.b = 0;
        this.c = i;
        this.f150a = i2;
        this.b = i3;
    }

    public int a() {
        return this.f150a;
    }

    public int b() {
        return this.b;
    }

    public int c() {
        return this.c;
    }

    public e(int i, String str) {
        super(str);
        this.f150a = 0;
        this.b = 0;
        this.c = i;
    }
}
