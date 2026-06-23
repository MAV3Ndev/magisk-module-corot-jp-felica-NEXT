package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.FSC;
import com.felicanetworks.mfc.Felica;

/* JADX INFO: compiled from: FSCStart.java */
/* JADX INFO: loaded from: classes.dex */
public class d0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private h0 f149a;

    public d0(h0 h0Var) {
        this.f149a = h0Var;
    }

    public synchronized void b(String str, g0 g0Var) {
        try {
            Felica felicaC = this.f149a.e().c();
            FSC fscN = this.f149a.g().n();
            if (felicaC != null && fscN != null) {
                new c0(this, g0Var).a(fscN, felicaC, str);
            }
            if (!this.f149a.g().u()) {
                throw new c1(d0.class, "execute");
            }
            b0.v();
            throw null;
        } catch (com.felicanetworks.mfc.x e) {
            throw j.b(e, null);
        }
    }
}
