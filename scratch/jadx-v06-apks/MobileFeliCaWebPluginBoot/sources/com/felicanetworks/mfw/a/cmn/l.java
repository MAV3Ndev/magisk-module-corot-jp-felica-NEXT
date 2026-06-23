package com.felicanetworks.mfw.a.cmn;

import android.os.Message;
import android.view.View;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class l implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ t f162a;

    l(t tVar) {
        this.f162a = tVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Message messageObtain = (view != this.f162a.m || this.f162a.o == null) ? (view != this.f162a.p || this.f162a.r == null) ? (view != this.f162a.s || this.f162a.u == null) ? null : Message.obtain(this.f162a.u) : Message.obtain(this.f162a.r) : Message.obtain(this.f162a.o);
        if (messageObtain != null) {
            messageObtain.sendToTarget();
        }
        this.f162a.F.obtainMessage(1, this.f162a.b).sendToTarget();
    }
}
