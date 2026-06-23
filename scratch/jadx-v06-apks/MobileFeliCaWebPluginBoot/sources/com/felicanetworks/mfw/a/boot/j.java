package com.felicanetworks.mfw.a.boot;

import android.content.DialogInterface;

/* JADX INFO: compiled from: BootActivityForMain.java */
/* JADX INFO: loaded from: classes.dex */
class j implements DialogInterface.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ BootActivityForMain f133a;

    private j(BootActivityForMain bootActivityForMain) {
        this.f133a = bootActivityForMain;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        if (this.f133a.isFinishing()) {
            return;
        }
        this.f133a.finish();
    }
}
