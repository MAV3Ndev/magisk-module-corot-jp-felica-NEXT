package com.felicanetworks.mfw.a.boot;

/* JADX INFO: compiled from: SysErrView.java */
/* JADX INFO: loaded from: classes.dex */
class t implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ u f139a;

    t(u uVar) {
        this.f139a = uVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.f139a.c.show();
    }
}
