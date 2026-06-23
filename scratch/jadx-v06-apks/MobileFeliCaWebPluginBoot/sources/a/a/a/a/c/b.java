package a.a.a.a.c;

import java.io.IOException;
import java.util.ArrayList;

/* JADX INFO: compiled from: BizNwConOptr.java */
/* JADX INFO: loaded from: classes.dex */
public class b implements com.felicanetworks.mfw.a.cmn.l0 {
    private static final String g = com.felicanetworks.mfw.a.cmn.p0.a().c("url.verup.confir.req");
    private static final String h = com.felicanetworks.mfw.a.cmn.p0.a().c("url.prblm.analyze.log.send");

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.felicanetworks.mfw.a.cmn.y0 f14a;
    private c b;
    private com.felicanetworks.mfw.a.cmn.h0 e;
    private ArrayList c = new ArrayList();
    private ArrayList d = new ArrayList();
    private volatile com.felicanetworks.mfw.a.cmn.j0 f = null;

    public b(com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        this.e = h0Var;
    }

    private int b(String str, String str2) {
        return 0;
    }

    private void c() {
        s0 s0Var = (s0) this.d.get(0);
        String strB = s0Var.b();
        try {
            if (strB.equals("I001") || strB.equals("I003")) {
                com.felicanetworks.mfw.a.cmn.n0.e(e(), s0Var.c(), s0Var.a(), this);
            } else if (strB.equals("I005") || strB.equals("I007") || strB.equals("I009") || strB.equals("I011")) {
                com.felicanetworks.mfw.a.cmn.n0.f(e(), s0Var.c(), s0Var.a(), this);
            }
        } catch (IOException e) {
            d();
            this.d.remove(0);
            if ("failed to encode.".equals(e.getMessage())) {
                if (strB.equals("I001")) {
                    this.b.a(strB, 8010);
                } else if (strB.equals("I003")) {
                    this.b.a(strB, 8011);
                } else if (strB.equals("I005")) {
                    this.b.a(strB, 8012);
                } else if (strB.equals("I007")) {
                    this.b.a(strB, 8013);
                } else if (strB.equals("I009")) {
                    this.b.a(strB, 8014);
                } else if (strB.equals("I011")) {
                    this.b.a(strB, 8015);
                }
            } else if (strB.equals("I001")) {
                this.b.a(strB, 8000);
            } else if (strB.equals("I003")) {
                this.b.a(strB, 8001);
            } else if (strB.equals("I005")) {
                this.b.a(strB, 8002);
            } else if (strB.equals("I007")) {
                this.b.a(strB, 8003);
            } else if (strB.equals("I009")) {
                this.b.a(strB, 8004);
            } else if (strB.equals("I011")) {
                this.b.a(strB, 8005);
            }
            this.b = null;
        } catch (InterruptedException e2) {
            d();
            if (!this.e.g().u()) {
                throw new com.felicanetworks.mfw.a.cmn.c1(b.class, "executeCommunication", e2);
            }
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        } catch (RuntimeException e3) {
            d();
            throw e3;
        }
    }

    private com.felicanetworks.mfw.a.cmn.j0 e() {
        com.felicanetworks.mfw.a.cmn.j0 j0Var;
        synchronized (this) {
            if (this.f == null) {
                this.f = com.felicanetworks.mfw.a.cmn.n0.b();
            }
            j0Var = this.f;
        }
        return j0Var;
    }

    private void g(com.felicanetworks.mfw.a.cmn.j0 j0Var) {
        synchronized (this) {
            this.f = j0Var;
        }
    }

    @Override // com.felicanetworks.mfw.a.cmn.l0
    public void a(com.felicanetworks.mfw.a.cmn.y0 y0Var) {
        String strB = ((s0) this.d.get(0)).b();
        this.f14a = y0Var;
        int iB = b(strB, y0Var.a());
        this.d.remove(0);
        if (iB != 0) {
            this.b.a(strB, iB);
            this.b = null;
            return;
        }
        this.b.a(strB, 0);
        if (this.d.size() < 1) {
            this.b = null;
        } else {
            c();
        }
    }

    public void d() {
        com.felicanetworks.mfw.a.cmn.j0 j0VarE = e();
        if (j0VarE != null) {
            g(null);
            com.felicanetworks.mfw.a.cmn.n0.d(j0VarE);
        }
    }

    public com.felicanetworks.mfw.a.cmn.y0 f() {
        return this.f14a;
    }

    public void h(c cVar) {
        this.b = cVar;
    }

    public void i(String str, String str2) {
        if (str.equals("I001")) {
            j(str, str2, g);
        } else if (str.equals("I005")) {
            j(str, str2, h);
        }
    }

    public void j(String str, String str2, String str3) {
        s0 s0Var = new s0();
        s0Var.e(str);
        s0Var.d(str2);
        s0Var.f(str3);
        this.c.add(s0Var);
    }

    public void k() {
        if (this.c.size() > 0) {
            this.d.clear();
            this.d.addAll(this.c);
            this.c.clear();
        }
        c();
    }
}
