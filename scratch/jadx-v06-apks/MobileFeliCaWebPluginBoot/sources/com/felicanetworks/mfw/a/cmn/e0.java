package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Felica;

/* JADX INFO: compiled from: FelicaChipUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class e0 {
    private static final int d = Integer.parseInt("FE00", 16);

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f151a;
    private boolean b = true;
    private h0 c;

    public e0(h0 h0Var) {
        this.c = h0Var;
    }

    private void a(Felica felica) {
        if (felica != null) {
            try {
                felica.t();
            } catch (com.felicanetworks.mfc.x unused) {
            }
        }
    }

    public String b() {
        Felica felicaO;
        if (!this.b) {
            return this.f151a;
        }
        this.b = false;
        Felica felica = null;
        try {
            if (this.c.g().u()) {
                b0.v();
                throw null;
            }
            felicaO = this.c.g().o();
            try {
                if (felicaO == null) {
                    throw new c1(e0.class, "isLocked");
                }
                felicaO.D();
                felicaO.H(0, d);
                String strK = b1.K(felicaO.w());
                this.f151a = strK;
                a(felicaO);
                return strK;
            } catch (com.felicanetworks.mfc.x unused) {
                a(felicaO);
                return null;
            } catch (Throwable th) {
                th = th;
                felica = felicaO;
                a(felica);
                throw th;
            }
        } catch (com.felicanetworks.mfc.x unused2) {
            felicaO = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    public boolean c() {
        boolean z;
        Felica felicaO = null;
        try {
            try {
            } catch (com.felicanetworks.mfc.x e) {
                int iE = e.e();
                if (iE == 31) {
                    throw new e(14, e.getMessage());
                }
                if (iE != 55) {
                    throw new e(4, e.getMessage());
                }
                z = true;
            }
            if (this.c.g().u()) {
                b0.v();
                throw null;
            }
            felicaO = this.c.g().o();
            if (felicaO == null) {
                throw new c1(e0.class, "isLocked");
            }
            felicaO.D();
            z = false;
            return z;
        } finally {
            a(null);
        }
    }
}
