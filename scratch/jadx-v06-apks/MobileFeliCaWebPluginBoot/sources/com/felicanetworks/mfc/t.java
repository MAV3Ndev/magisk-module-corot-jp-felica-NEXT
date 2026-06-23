package com.felicanetworks.mfc;

import android.os.Binder;

/* JADX INFO: compiled from: Felica.java */
/* JADX INFO: loaded from: classes.dex */
public class t extends Binder {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ Felica f117a;

    public t(Felica felica) {
        this.f117a = felica;
    }

    public Felica a() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        return this.f117a;
    }
}
