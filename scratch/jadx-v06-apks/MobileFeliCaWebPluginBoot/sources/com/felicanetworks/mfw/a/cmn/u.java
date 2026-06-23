package com.felicanetworks.mfw.a.cmn;

import android.content.Context;
import android.content.DialogInterface;

/* JADX INFO: compiled from: CustomAlertDialog.java */
/* JADX INFO: loaded from: classes.dex */
public class u {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final r f178a;

    public u(Context context) {
        this.f178a = new r(context);
    }

    public v a() {
        v vVar = new v(this.f178a.f172a);
        this.f178a.a(vVar.f179a);
        vVar.setCancelable(this.f178a.n);
        vVar.setOnCancelListener(this.f178a.o);
        DialogInterface.OnKeyListener onKeyListener = this.f178a.p;
        if (onKeyListener != null) {
            vVar.setOnKeyListener(onKeyListener);
        }
        return vVar;
    }
}
