package com.felicanetworks.mfw.a.cmn;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class m extends ArrayAdapter {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ CustomAlertController$RecycleListView f164a;
    final /* synthetic */ r b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    m(r rVar, Context context, int i, int i2, CharSequence[] charSequenceArr, CustomAlertController$RecycleListView customAlertController$RecycleListView) {
        super(context, i, i2, charSequenceArr);
        this.b = rVar;
        this.f164a = customAlertController$RecycleListView;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view2 = super.getView(i, view, viewGroup);
        boolean[] zArr = this.b.z;
        if (zArr != null && zArr[i]) {
            this.f164a.setItemChecked(i, true);
        }
        return view2;
    }
}
