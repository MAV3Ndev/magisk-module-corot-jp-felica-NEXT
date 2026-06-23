package com.felicanetworks.mfw.a.boot;

import android.content.DialogInterface;
import com.felicanetworks.mfw.a.cmn.h0;

/* JADX INFO: compiled from: SysErrView.java */
/* JADX INFO: loaded from: classes.dex */
class s implements DialogInterface.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ h0 f138a;
    final /* synthetic */ u b;

    s(u uVar, h0 h0Var) {
        this.b = uVar;
        this.f138a = h0Var;
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        if (this.b.b) {
            return;
        }
        this.f138a.g().d();
    }
}
