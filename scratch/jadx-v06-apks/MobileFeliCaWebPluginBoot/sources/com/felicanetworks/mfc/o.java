package com.felicanetworks.mfc;

import android.os.Binder;

/* JADX INFO: compiled from: FSC.java */
/* JADX INFO: loaded from: classes.dex */
public class o extends Binder {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ FSC f111a;

    public o(FSC fsc) {
        this.f111a = fsc;
    }

    public FSC a() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        return this.f111a;
    }
}
