package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.Felica;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: Activator.java */
/* JADX INFO: loaded from: classes.dex */
public class d {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private h0 f148a;

    public d(h0 h0Var) {
        this.f148a = h0Var;
    }

    private static List c() {
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        sb.append("permit/pmt_");
        int i = 0;
        sb.append(0);
        sb.append(".txt");
        String strD = d(sb.toString());
        while (strD != null) {
            arrayList.add(strD);
            StringBuilder sb2 = new StringBuilder();
            sb2.append("permit/pmt_");
            i++;
            sb2.append(i);
            sb2.append(".txt");
            strD = d(sb2.toString());
        }
        return arrayList;
    }

    private static String d(String str) {
        int iH = w0.h(str);
        if (iH == 0) {
            return null;
        }
        byte[] bArr = new byte[iH];
        w0.j(str, 0, iH, bArr);
        return new String(bArr);
    }

    public void b() {
        b bVar = null;
        try {
            if (this.f148a.g().b(p0.a().c("mfc.package.name")) == 3) {
                throw new a.a.a.a.b.b(1);
            }
            Felica felicaO = this.f148a.g().o();
            if (felicaO == null) {
                if (!this.f148a.g().u()) {
                    throw new c1(d.class, "activateFelica");
                }
                b0.v();
                throw null;
            }
            c cVar = new c(this);
            cVar.a(felicaO, c());
            if (cVar.d()) {
                int iB = cVar.b();
                if (iB == 4) {
                    throw new a(22, (String) null);
                }
                if (iB != 7) {
                    throw new a(20, (String) null);
                }
                throw new a(this.f148a.g().l(cVar.c().a()), (String) null);
            }
        } catch (com.felicanetworks.mfc.x e) {
            throw j.b(e, null);
        } catch (IllegalArgumentException unused) {
            throw new a.a.a.a.b.b(3);
        }
    }

    public void e() {
        Felica felicaO = this.f148a.g().o();
        if (felicaO != null) {
            try {
                felicaO.t();
            } catch (com.felicanetworks.mfc.x unused) {
            }
            try {
                felicaO.A();
            } catch (com.felicanetworks.mfc.x unused2) {
            }
        }
    }
}
