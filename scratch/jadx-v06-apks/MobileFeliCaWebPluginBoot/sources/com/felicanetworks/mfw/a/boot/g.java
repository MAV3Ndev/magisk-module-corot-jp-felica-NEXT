package com.felicanetworks.mfw.a.boot;

import android.content.DialogInterface;

/* JADX INFO: compiled from: BootActivity.java */
/* JADX INFO: loaded from: classes.dex */
class g implements DialogInterface.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ BootActivity f131a;

    private g(BootActivity bootActivity) {
        this.f131a = bootActivity;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        if (this.f131a.isFinishing()) {
            return;
        }
        this.f131a.finish();
    }
}
