package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Felica;
import com.felicanetworks.mfc.PushStartAppSegment;
import com.felicanetworks.mfc.PushStartBrowserSegment;
import java.util.ArrayList;

/* JADX INFO: compiled from: Push.java */
/* JADX INFO: loaded from: classes.dex */
public class q0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private h0 f171a;

    public q0(h0 h0Var) {
        this.f171a = h0Var;
    }

    public void a(String str, String str2, String[] strArr, g0 g0Var) throws e {
        try {
            PushStartAppSegment pushStartAppSegment = new PushStartAppSegment(str, str2, strArr);
            if (this.f171a.g().u()) {
                b0.v();
                throw null;
            }
            Felica felicaO = this.f171a.g().o();
            if (felicaO == null) {
                throw new c1(o0.class, "Polling Failed");
            }
            felicaO.D();
            felicaO.E(pushStartAppSegment);
            g0Var.a(new ArrayList());
        } catch (com.felicanetworks.mfc.x e) {
            throw j.b(e, null);
        }
    }

    public void b(String str, String str2, g0 g0Var) throws e {
        try {
            PushStartBrowserSegment pushStartBrowserSegment = new PushStartBrowserSegment(str, str2);
            if (this.f171a.g().u()) {
                b0.v();
                throw null;
            }
            Felica felicaO = this.f171a.g().o();
            if (felicaO == null) {
                throw new c1(o0.class, "Polling Failed");
            }
            felicaO.D();
            felicaO.E(pushStartBrowserSegment);
            g0Var.a(new ArrayList());
        } catch (com.felicanetworks.mfc.x e) {
            throw j.b(e, null);
        }
    }
}
