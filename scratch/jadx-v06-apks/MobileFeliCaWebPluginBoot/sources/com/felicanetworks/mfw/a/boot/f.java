package com.felicanetworks.mfw.a.boot;

import android.view.View;

/* JADX INFO: compiled from: BootActivity.java */
/* JADX INFO: loaded from: classes.dex */
class f implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ BootActivity f130a;

    private f(BootActivity bootActivity) {
        this.f130a = bootActivity;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.f130a.finish();
    }
}
