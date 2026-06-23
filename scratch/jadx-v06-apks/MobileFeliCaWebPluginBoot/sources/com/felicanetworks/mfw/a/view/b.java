package com.felicanetworks.mfw.a.view;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class b extends ArrayAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ CustomAlertController$RecycleListView f188a;
    final /* synthetic */ g b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    b(g gVar, Context context, int i, int i2, CharSequence[] charSequenceArr, CustomAlertController$RecycleListView customAlertController$RecycleListView) {
        super(context, i, i2, charSequenceArr);
        this.b = gVar;
        this.f188a = customAlertController$RecycleListView;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        boolean[] zArr = this.b.z;
        if (zArr != null && zArr[i]) {
            this.f188a.setItemChecked(i, true);
        }
        return view2;
    }
}
