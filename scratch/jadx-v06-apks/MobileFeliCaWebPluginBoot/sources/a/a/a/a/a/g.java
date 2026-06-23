package a.a.a.a.a;

import a.a.a.a.c.j1;
import com.felicanetworks.mfw.a.boot.R;
import com.felicanetworks.mfw.a.cmn.b1;
import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: CmdDataProcs.java */
/* JADX INFO: loaded from: classes.dex */
public class g extends j1 {
    private List d;

    public g(com.felicanetworks.mfw.a.cmn.h0 h0Var, String str) {
        super(h0Var, str);
        this.d = null;
    }

    private void p(int i) {
        this.c.f().b(new r(this.c, "D013"), Integer.valueOf(i), String.valueOf(i));
    }

    private void q(int i) {
        if (this.c.g().m() != 1) {
            t(i);
        } else {
            this.c.f().b(new r(this.c, "D013"), Integer.valueOf(i), String.valueOf(i));
        }
    }

    private void r(int i, String str) {
        if (this.c.g().m() == 1) {
            this.c.f().b(new r(this.c, "D013"), Integer.valueOf(i), str);
        } else if (i == 4010) {
            v(i, this.d);
        } else {
            t(i);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void s() {
        this.c.f().c(new m0(this.c, "D010"), null, null, false);
    }

    private void t(int i) {
        this.c.g().w(i);
        this.c.g().d();
    }

    private void u(int i, String str) {
        this.c.g().x(i, str);
        this.c.g().d();
    }

    private void v(int i, List list) {
        this.c.g().y(i, list);
        this.c.g().d();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void w(int i) {
        if (i != 0) {
            this.c.a().e();
            q(i);
        } else {
            if (this.c.g().m() != 1) {
                u(-1, this.c.d().A());
                return;
            }
            try {
                if (this.c.g().C().e() == 1) {
                    this.c.g().D(this.c.d().A());
                } else {
                    this.c.g().E(this.c.d().A());
                }
                com.felicanetworks.mfw.a.cmn.b0.v();
                throw null;
            } catch (IOException unused) {
                q(6100);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void x(int i, int i2, String str) {
        if (str != null && i2 == 101) {
            if (i == -1) {
                this.c.f().m("00", 29184, "003", str, false);
                return;
            } else {
                if (i == 0) {
                    this.c.f().m("00", 29184, "004", str, false);
                    return;
                }
                return;
            }
        }
        if (str != null && i2 == 100) {
            this.c.f().m("00", 29184, "002", str, false);
            return;
        }
        if (str != null && i2 == 102) {
            this.c.f().m("00", 29184, "002", str, false);
            return;
        }
        if (str != null && i2 == 105) {
            if (i == -1) {
                this.c.f().m("00", 29184, "005", str, false);
                return;
            } else {
                if (i == 0) {
                    this.c.f().m("00", 29184, "004", str, false);
                    return;
                }
                return;
            }
        }
        if (str != null && i2 == 106) {
            this.c.f().m("00", 29184, "002", str, false);
            return;
        }
        if (str != null && i2 == 103) {
            this.c.f().m("00", 29184, "004", str, false);
        } else {
            if (str == null || i2 != 104) {
                return;
            }
            this.c.f().m("00", 29184, "004", str, false);
        }
    }

    @Override // a.a.a.a.c.j1, a.a.a.a.c.s1
    public void a(int i, Object obj, Object obj2) {
        if (this.c.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        if (i == 36864 && this.c.d().z()) {
            this.c.d().L();
            if (this.c.d().p()) {
                this.c.f().c(new z(this.c, "D011"), null, null, false);
            }
        }
    }

    @Override // a.a.a.a.c.j1, a.a.a.a.c.s1
    public void h() {
        if (this.c.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        this.c.d().L();
    }

    @Override // a.a.a.a.c.j1
    public void i(String str, Object obj, Object obj2) {
        if (this.c.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        if ("D007".equals(str)) {
            this.c.d().I(((Integer) obj).intValue());
        }
    }

    @Override // a.a.a.a.c.j1
    public void j(Object obj, Object obj2) throws Throwable {
        a aVar = null;
        if (this.c.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        try {
            this.c.a().b();
            String strD = this.c.g().C().d();
            if (strD == null || strD.length() == 0) {
                throw new b(this, 6834);
            }
            int iM = this.c.g().m();
            int iA = iM == 1 ? a.a.a.a.c.a.a(strD) : a.a.a.a.c.a.b(strD);
            if (iA != 0) {
                throw new b(this, iA);
            }
            if (iM == 1) {
                strD = new String(b1.I(strD));
            }
            f fVar = new f(this);
            fVar.d(strD);
            new d(this).a(fVar.f4a, fVar.b);
        } catch (b e) {
            this.c.a().e();
            q(e.f0a);
        } catch (a.a.a.a.b.b e2) {
            int iA2 = e2.a();
            if (iA2 == 1) {
                p(6102);
            } else if (iA2 == 3) {
                this.c.f().c(new m0(this.c, "D027"), null, null, true);
            } else {
                com.felicanetworks.mfw.a.cmn.b0.v();
                throw null;
            }
        } catch (com.felicanetworks.mfw.a.cmn.a e3) {
            if (e3.c() != 21) {
                q(4011);
                return;
            }
            String strH = b1.H(e3.d(), this.c.g().q(R.string.activate_busy_label));
            this.d = e3.d();
            r(4010, strH);
        } catch (com.felicanetworks.mfw.a.cmn.e unused) {
            q(4011);
        }
    }
}
