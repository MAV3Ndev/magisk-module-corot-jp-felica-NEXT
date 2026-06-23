package com.felicanetworks.mfw.a.view;

import android.view.View;
import android.widget.AdapterView;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class e implements AdapterView.OnItemClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ CustomAlertController$RecycleListView f191a;
    final /* synthetic */ i b;
    final /* synthetic */ g c;

    e(g gVar, CustomAlertController$RecycleListView customAlertController$RecycleListView, i iVar) {
        this.c = gVar;
        this.f191a = customAlertController$RecycleListView;
        this.b = iVar;
    }

    @Override // android.widget.AdapterView.OnItemClickListener
    public void onItemClick(AdapterView adapterView, View view, int i, long j) {
        boolean[] zArr = this.c.z;
        if (zArr != null) {
            zArr[i] = this.f191a.isItemChecked(i);
        }
        this.c.D.onClick(this.b.b, i, this.f191a.isItemChecked(i));
    }
}
