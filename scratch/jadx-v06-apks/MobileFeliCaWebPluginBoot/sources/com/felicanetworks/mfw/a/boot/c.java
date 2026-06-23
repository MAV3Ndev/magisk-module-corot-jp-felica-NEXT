package com.felicanetworks.mfw.a.boot;

/* JADX INFO: compiled from: Applet.java */
/* JADX INFO: loaded from: classes.dex */
class c implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ Applet f128a;

    c(Applet applet) {
        this.f128a = applet;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f128a.d.e();
    }
}
