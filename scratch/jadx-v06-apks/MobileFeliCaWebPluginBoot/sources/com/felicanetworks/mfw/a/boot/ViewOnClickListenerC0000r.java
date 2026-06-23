package com.felicanetworks.mfw.a.boot;

import android.view.View;
import com.felicanetworks.mfw.a.cmn.h0;

/* JADX INFO: renamed from: com.felicanetworks.mfw.a.boot.r, reason: case insensitive filesystem */
/* JADX INFO: compiled from: SysErrView.java */
/* JADX INFO: loaded from: classes.dex */
class ViewOnClickListenerC0000r implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ h0 f137a;
    final /* synthetic */ u b;

    ViewOnClickListenerC0000r(u uVar, h0 h0Var) {
        this.b = uVar;
        this.f137a = h0Var;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        synchronized (this) {
            if (this.b.b) {
                return;
            }
            this.b.b = true;
            this.f137a.g().d();
        }
    }
}
