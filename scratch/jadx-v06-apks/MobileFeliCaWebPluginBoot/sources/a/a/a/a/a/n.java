package a.a.a.a.a;

import a.a.a.a.c.j1;
import a.a.a.a.c.k1;
import a.a.a.a.c.o1;
import a.a.a.a.c.p1;
import com.felicanetworks.mfw.a.boot.R;
import com.felicanetworks.mfw.a.cmn.b1;
import com.felicanetworks.mfw.a.cmn.c1;
import com.felicanetworks.mfw.a.cmn.p0;
import com.felicanetworks.mfw.a.cmn.y0;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: CommonProcs.java */
/* JADX INFO: loaded from: classes.dex */
public class n extends j1 {
    private int d;
    private String e;
    private m f;
    private k g;
    private boolean h;
    private String i;
    private List j;

    public n(com.felicanetworks.mfw.a.cmn.h0 h0Var, String str) {
        super(h0Var, str);
        this.d = 0;
        h hVar = null;
        this.f = new m(this);
        this.g = new k(this);
        this.h = false;
        this.i = "2";
        this.j = null;
    }

    private void A() {
        try {
            o1 o1VarC = this.f.c();
            if (v(o1VarC)) {
                this.g.d(p(o1VarC));
                if (this.g.c()) {
                    return;
                }
                o1 o1Var = new o1();
                o1Var.c("R201");
                this.f.e(o1Var);
            }
        } catch (c1 unused) {
        }
    }

    private void B(int i) {
        this.c.g().w(i);
        this.c.g().d();
    }

    private void C(int i, List list) {
        this.c.g().y(i, this.j);
        this.c.g().d();
    }

    private void m(int i) {
        this.c.f().b(new r(this.c, "D013"), Integer.valueOf(i), String.valueOf(i));
    }

    private int n(ArrayList arrayList) {
        int size = arrayList.size();
        if (size < 4 || 5 < size) {
            return 8201;
        }
        if (size == 5 && b1.l((String) arrayList.get(4))) {
            return 8201;
        }
        String str = (String) arrayList.get(0);
        String str2 = (String) arrayList.get(1);
        String str3 = (String) arrayList.get(2);
        String str4 = (String) arrayList.get(3);
        if (!b1.r(str, 1, 8)) {
            return 8202;
        }
        if (!b1.k(str)) {
            return 8203;
        }
        if (Integer.parseInt(str, 10) > p0.a().b("offline.verup.eval.count.limit")) {
            return 8204;
        }
        if (!b1.r(str2, 1, 8)) {
            return 8205;
        }
        if (!b1.k(str2)) {
            return 8206;
        }
        if (Integer.parseInt(str2, 10) > p0.a().b("offline.verup.eval.term")) {
            return 8207;
        }
        if (b1.l(str3)) {
            return ("0".equals(str4) || "1".equals(str4)) ? 0 : 8209;
        }
        return 8208;
    }

    private boolean o(String str, String str2) {
        return str2 == null || !str.equals(str2);
    }

    private String p(o1 o1Var) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ai=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(p0.a().c("application.id")));
        stringBuffer.append("&av=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(p0.a().c("application.version")));
        if (this.e != null) {
            stringBuffer.append("&idm=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(this.e));
        }
        stringBuffer.append("&d=");
        String strD = o1Var.d();
        String strB = com.felicanetworks.mfw.a.cmn.k0.b(strD.substring(0, 512));
        if (strB.length() > 512) {
            strB = strB.substring(0, 512);
        }
        stringBuffer.append(strB);
        String strB2 = com.felicanetworks.mfw.a.cmn.k0.b(strD.substring(512));
        if (strB2.length() > 512) {
            strB2 = strB2.substring(0, 512);
        }
        stringBuffer.append(strB2);
        return stringBuffer.toString();
    }

    private String q(p1 p1Var) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ai=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(p0.a().c("application.id")));
        stringBuffer.append("&av=");
        stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(p0.a().c("application.version")));
        if (this.e != null) {
            stringBuffer.append("&idm=");
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(this.e));
        }
        stringBuffer.append("&ct=");
        if (p1Var.f() == -1) {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b("0"));
        } else {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(String.valueOf(p1Var.f())));
        }
        stringBuffer.append("&mct=");
        if (p1Var.g() == -1) {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b("0"));
        } else {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(String.valueOf(p1Var.g())));
        }
        stringBuffer.append("&il=");
        if (p1Var.h() == null) {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b("0"));
        } else {
            long jCurrentTimeMillis = (System.currentTimeMillis() - com.felicanetworks.mfw.a.cmn.x.o(p1Var.h())) / 60000;
            if (jCurrentTimeMillis < 0 || 99999999 < jCurrentTimeMillis) {
                stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b("0"));
            } else {
                stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(String.valueOf(jCurrentTimeMillis)));
            }
        }
        stringBuffer.append("&mil=");
        if (p1Var.e() == -1) {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b("0"));
        } else {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(String.valueOf(p1Var.e())));
        }
        stringBuffer.append("&lpv=");
        if (p1Var.d() == null) {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b("0"));
        } else {
            stringBuffer.append(com.felicanetworks.mfw.a.cmn.k0.b(String.valueOf(p1Var.d())));
        }
        if (p1Var.a()) {
            stringBuffer.append("&desc=" + com.felicanetworks.mfw.a.cmn.k0.b("fail-safe"));
        }
        return stringBuffer.toString();
    }

    private void r(int i) {
        if (this.c.g().m() != 1) {
            B(i);
        } else {
            this.c.f().b(new r(this.c, "D013"), Integer.valueOf(i), String.valueOf(i));
        }
    }

    private void s(int i, String str) {
        if (this.c.g().m() == 1) {
            this.c.f().b(new r(this.c, "D013"), Integer.valueOf(i), str);
        } else if (i == 4010) {
            C(i, this.j);
        } else {
            B(i);
        }
    }

    private void t() throws i {
        int iM = this.c.g().m();
        try {
            if (this.c.g().F() != 1) {
                if (iM == 2) {
                    this.c.g().w(6210);
                }
                com.felicanetworks.mfw.a.cmn.b0.v();
                throw null;
            }
        } catch (IOException e) {
            if ("failed starting FeliCaMenuApp".equals(e.getMessage())) {
                throw new i(this, 6101);
            }
            if (iM == 2) {
                this.c.g().w(6210);
            }
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
    }

    private String u() {
        if (p0.a().c("application.id").equals("0204")) {
            this.i = "3";
        }
        String strA = k1.a(this.e, this.c.g().C(), this.i);
        return p0.a().c("url.intermediate.page") + "?" + strA;
    }

    private boolean v(o1 o1Var) {
        return o1Var.e() == 1;
    }

    private boolean w() throws i {
        try {
            this.c.a().b();
            return true;
        } catch (com.felicanetworks.mfw.a.cmn.a e) {
            int iC = e.c();
            if (iC != 21) {
                if (iC == 22) {
                    return false;
                }
                throw new i(this, 4011);
            }
            String strH = b1.H(e.d(), this.c.g().q(R.string.activate_busy_label));
            this.j = e.d();
            throw new i(this, 4010, strH);
        } catch (com.felicanetworks.mfw.a.cmn.e unused) {
            throw new i(this, 4011);
        }
    }

    private boolean x() throws i {
        try {
            return this.c.j().c();
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            int iC = e.c();
            if (iC == 3) {
                throw new i(this, 4000);
            }
            if (iC == 4) {
                throw new i(this, 4009);
            }
            if (iC == 14) {
                this.h = true;
            }
            return false;
        }
    }

    private boolean y() throws Throwable {
        p1 p1VarD = this.f.d();
        String strG = com.felicanetworks.mfw.a.cmn.b0.g();
        if (strG.length() > 30) {
            strG = strG.substring(0, 30);
        }
        boolean zO = o(strG, p1VarD.d());
        if (this.f.b() || p1VarD.i() == null || zO || p1VarD.f() > p1VarD.g() || p1VarD.f() == 0) {
            y0 y0VarE = this.g.e(q(p1VarD));
            if (this.g.c()) {
                throw new i(this, this.g.b());
            }
            ArrayList arrayListD = b1.d(y0VarE.b());
            int iN = n(arrayListD);
            if (iN != 0) {
                throw new i(this, iN);
            }
            if (Integer.parseInt((String) arrayListD.get(3)) == 1) {
                return true;
            }
            long jCurrentTimeMillis = System.currentTimeMillis() + (Long.parseLong((String) arrayListD.get(1)) * 60000);
            p1VarD.n(com.felicanetworks.mfw.a.cmn.x.c());
            p1VarD.c("R001");
            p1VarD.o(com.felicanetworks.mfw.a.cmn.x.n(jCurrentTimeMillis));
            p1VarD.m(Integer.parseInt((String) arrayListD.get(0)));
            p1VarD.l(1);
            p1VarD.k(Integer.parseInt((String) arrayListD.get(1)));
            p1VarD.j(strG);
            this.f.f(p1VarD);
            return false;
        }
        if (z(p1VarD)) {
            if (99999999 <= p1VarD.f()) {
                p1VarD.l(0);
            } else {
                p1VarD.l(p1VarD.f() + 1);
            }
            this.f.f(p1VarD);
            return false;
        }
        y0 y0VarE2 = this.g.e(q(p1VarD));
        if (this.g.c()) {
            if (99999999 <= p1VarD.f()) {
                p1VarD.l(0);
            } else {
                p1VarD.l(p1VarD.f() + 1);
            }
            this.f.f(p1VarD);
            return false;
        }
        ArrayList arrayListD2 = b1.d(y0VarE2.b());
        if (n(arrayListD2) != 0) {
            if (99999999 <= p1VarD.f()) {
                p1VarD.l(0);
            } else {
                p1VarD.l(p1VarD.f() + 1);
            }
            this.f.f(p1VarD);
            return false;
        }
        if (Integer.parseInt((String) arrayListD2.get(3)) == 1) {
            return true;
        }
        long jCurrentTimeMillis2 = System.currentTimeMillis() + (Long.parseLong((String) arrayListD2.get(1)) * 60000);
        p1VarD.n(com.felicanetworks.mfw.a.cmn.x.c());
        p1VarD.c("R001");
        p1VarD.o(com.felicanetworks.mfw.a.cmn.x.n(jCurrentTimeMillis2));
        p1VarD.m(Integer.parseInt((String) arrayListD2.get(0)));
        p1VarD.l(1);
        p1VarD.k(Integer.parseInt((String) arrayListD2.get(1)));
        p1VarD.j(strG);
        this.f.f(p1VarD);
        return false;
    }

    private boolean z(p1 p1Var) {
        return com.felicanetworks.mfw.a.cmn.x.o(p1Var.i()) > System.currentTimeMillis();
    }

    @Override // a.a.a.a.c.j1
    public void i(String str, Object obj, Object obj2) {
        try {
            if (this.d != 1) {
                return;
            }
            this.c.g().D(u());
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        } catch (IOException unused) {
            r(6100);
        }
    }

    @Override // a.a.a.a.c.j1
    public void j(Object obj, Object obj2) {
        if (this.c.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        this.c.c().r();
        try {
            if (w()) {
                try {
                    if (x()) {
                        throw new i(this, 4000);
                    }
                    this.e = this.c.j().b();
                } finally {
                    this.c.a().e();
                }
            }
            if (!y()) {
                A();
                if (this.h) {
                    t();
                }
                this.c.f().c(new g(this.c, "S002"), null, null, true);
                return;
            }
            this.d = 1;
            if (this.c.g().m() == 1) {
                this.c.f().c(new m0(this.c, "D012"), null, null, true);
            } else {
                this.c.g().w(6200);
                this.c.g().d();
            }
        } catch (i e) {
            if (e.b() != null) {
                s(e.a(), e.b());
            } else {
                r(e.a());
            }
        } catch (a.a.a.a.b.b e2) {
            int iA = e2.a();
            if (iA == 1) {
                m(6102);
                return;
            }
            if (iA == 2) {
                m(6101);
                return;
            }
            if (iA == 3) {
                this.c.f().c(new m0(this.c, "D027"), null, null, true);
            } else if (iA == 4) {
                this.c.f().c(new m0(this.c, "D028"), null, null, true);
            } else if (iA == 5) {
                m(6105);
            } else {
                com.felicanetworks.mfw.a.cmn.b0.v();
                throw null;
            }
        }
    }
}
