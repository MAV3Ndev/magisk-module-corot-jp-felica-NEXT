package com.felicanetworks.mfw.a.cmn;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class p implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ CustomAlertController$RecycleListView f169a;
    final /* synthetic */ t b;
    final /* synthetic */ r c;

    p(r rVar, CustomAlertController$RecycleListView customAlertController$RecycleListView, t tVar) {
        this.c = rVar;
        this.f169a = customAlertController$RecycleListView;
        this.b = tVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        boolean[] zArr = this.c.z;
        if (zArr != null) {
            zArr[i] = this.f169a.isItemChecked(i);
        }
        this.c.D.onClick(this.b.b, i, this.f169a.isItemChecked(i));
    }
}
