package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Felica;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: compiled from: Polling.java */
/* JADX INFO: loaded from: classes.dex */
public class o0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private h0 f168a;

    public o0(h0 h0Var) {
        this.f168a = h0Var;
    }

    private static byte[] a(byte[] bArr) {
        return new byte[]{bArr[1], bArr[0]};
    }

    private Felica b(boolean z, int i, int i2) throws com.felicanetworks.mfc.x {
        if (this.f168a.g().u()) {
            b0.v();
            throw null;
        }
        Felica felicaO = this.f168a.g().o();
        if (felicaO == null) {
            throw new c1(o0.class, "Polling Failed");
        }
        felicaO.D();
        if (z) {
            felicaO.H(0, i);
        } else {
            felicaO.H(1, i);
            if (i2 != -1) {
                felicaO.J(i2);
            }
        }
        if (e(a(felicaO.v()))) {
            try {
                felicaO.I(4);
            } catch (com.felicanetworks.mfc.x e) {
                if (e.e() == 7) {
                    felicaO.t();
                }
                throw e;
            }
        }
        return felicaO;
    }

    private static List c(int i, Felica felica) {
        ArrayList arrayList = new ArrayList();
        new ArrayList();
        arrayList.add(new i0("syscode", b1.P(Integer.toHexString(i).toUpperCase(Locale.US), 4)));
        arrayList.add(new i0("idm", b1.K(felica.w())));
        byte[] bArrA = a(felica.v());
        arrayList.add(new i0("iccode", b1.K(bArrA)));
        if (e(bArrA)) {
            arrayList.add(new i0("container_issue_info", b1.K(felica.u())));
        }
        return arrayList;
    }

    private static boolean e(byte[] bArr) {
        return f0.d(Long.parseLong(b1.K(bArr), 16));
    }

    public void d(int i, boolean z, int i2, g0 g0Var) throws e {
        try {
            Felica felicaB = b(z, i, i2);
            this.f168a.e().e(felicaB, i);
            g0Var.a(c(i, felicaB));
        } catch (com.felicanetworks.mfc.x e) {
            throw j.b(e, e.getMessage());
        }
    }
}
