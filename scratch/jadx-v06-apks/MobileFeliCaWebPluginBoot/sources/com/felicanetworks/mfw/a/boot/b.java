package com.felicanetworks.mfw.a.boot;

/* JADX INFO: compiled from: Applet.java */
/* JADX INFO: loaded from: classes.dex */
class b implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ Applet f127a;

    b(Applet applet) {
        this.f127a = applet;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f127a.d.e();
    }
}
