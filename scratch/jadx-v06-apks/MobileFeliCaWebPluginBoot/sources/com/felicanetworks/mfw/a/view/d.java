package com.felicanetworks.mfw.a.view;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class d implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ i f190a;
    final /* synthetic */ g b;

    d(g gVar, i iVar) {
        this.b = gVar;
        this.f190a = iVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        this.b.s.onClick(this.f190a.b, i);
        if (this.b.B) {
            return;
        }
        this.f190a.b.dismiss();
    }
}
