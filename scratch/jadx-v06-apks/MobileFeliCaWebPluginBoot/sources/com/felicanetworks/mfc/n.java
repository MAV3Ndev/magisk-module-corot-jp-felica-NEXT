package com.felicanetworks.mfc;

/* JADX INFO: compiled from: FSC.java */
/* JADX INFO: loaded from: classes.dex */
class n {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    int f110a;
    String b;

    public n(FSC fsc, int i, String str) {
        com.felicanetworks.mfc.s1.a.d(6, "%s %d msg:%s", "000", Integer.valueOf(i), str);
        this.f110a = i;
        this.b = str;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public String a() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return this.b;
    }

    public int b() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return this.f110a;
    }
}
