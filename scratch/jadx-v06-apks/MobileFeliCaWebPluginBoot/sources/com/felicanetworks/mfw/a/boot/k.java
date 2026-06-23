package com.felicanetworks.mfw.a.boot;

import a.a.a.a.c.u0;
import android.view.View;
import com.felicanetworks.mfw.a.cmn.h0;

/* JADX INFO: compiled from: ErrView.java */
/* JADX INFO: loaded from: classes.dex */
class k extends u0 {
    final /* synthetic */ m b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    k(m mVar, h0 h0Var) {
        super(h0Var);
        this.b = mVar;
    }

    @Override // a.a.a.a.c.u0
    public void a(View view) {
        synchronized (this) {
            if (this.b.b) {
                return;
            }
            this.b.b = true;
            this.b.d.g().d();
        }
    }
}
