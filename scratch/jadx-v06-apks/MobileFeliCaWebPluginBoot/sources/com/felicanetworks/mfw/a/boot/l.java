package com.felicanetworks.mfw.a.boot;

import a.a.a.a.c.v0;
import android.content.DialogInterface;
import com.felicanetworks.mfw.a.cmn.h0;

/* JADX INFO: compiled from: ErrView.java */
/* JADX INFO: loaded from: classes.dex */
class l extends v0 {
    final /* synthetic */ m b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    l(m mVar, h0 h0Var) {
        super(h0Var);
        this.b = mVar;
    }

    @Override // a.a.a.a.c.v0
    protected void a(DialogInterface dialogInterface) {
        if (this.b.b) {
            return;
        }
        this.b.d.g().d();
    }
}
