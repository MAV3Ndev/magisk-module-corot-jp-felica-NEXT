package com.felicanetworks.mfw.a.view;

import android.content.Context;
import android.content.DialogInterface;

/* JADX INFO: compiled from: CustomAlertDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final g f195a;

    public j(Context context) {
        this.f195a = new g(context);
    }

    public k a() {
        k kVar = new k(this.f195a.f192a);
        this.f195a.a(kVar.f196a);
        kVar.setCancelable(this.f195a.n);
        kVar.setOnCancelListener(this.f195a.o);
        DialogInterface.OnKeyListener onKeyListener = this.f195a.p;
        if (onKeyListener != null) {
            kVar.setOnKeyListener(onKeyListener);
        }
        return kVar;
    }
}
