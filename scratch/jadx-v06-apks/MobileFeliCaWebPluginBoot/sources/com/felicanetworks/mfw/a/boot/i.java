package com.felicanetworks.mfw.a.boot;

import android.view.View;

/* JADX INFO: compiled from: BootActivityForMain.java */
/* JADX INFO: loaded from: classes.dex */
class i implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ BootActivityForMain f132a;

    private i(BootActivityForMain bootActivityForMain) {
        this.f132a = bootActivityForMain;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        this.f132a.finish();
    }
}
