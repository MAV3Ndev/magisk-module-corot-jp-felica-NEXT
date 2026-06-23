package com.felicanetworks.mfw.a.cmn;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class o implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ t f167a;
    final /* synthetic */ r b;

    o(r rVar, t tVar) {
        this.b = rVar;
        this.f167a = tVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.b.s.onClick(this.f167a.b, i);
        if (this.b.B) {
            return;
        }
        this.f167a.b.dismiss();
    }
}
