package com.felicanetworks.mfw.a.view;

import android.os.Message;
import android.view.View;

/* JADX INFO: compiled from: CustomAlertController.java */
/* JADX INFO: loaded from: classes.dex */
class a implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ i f187a;

    a(i iVar) {
        this.f187a = iVar;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        Message messageObtain = (view != this.f187a.m || this.f187a.o == null) ? (view != this.f187a.p || this.f187a.r == null) ? (view != this.f187a.s || this.f187a.u == null) ? null : Message.obtain(this.f187a.u) : Message.obtain(this.f187a.r) : Message.obtain(this.f187a.o);
        if (messageObtain != null) {
            messageObtain.sendToTarget();
        }
        this.f187a.F.obtainMessage(1, this.f187a.b).sendToTarget();
    }
}
