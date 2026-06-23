package a.a.a.a.a;

import a.a.a.a.c.j1;
import a.a.a.a.c.o1;
import a.a.a.a.c.p1;

/* JADX INFO: compiled from: CommonProcs.java */
/* JADX INFO: loaded from: classes.dex */
class m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private a.a.a.a.c.e f9a;
    private int b;
    private a.a.a.a.c.f c;
    final /* synthetic */ n d;

    private m(n nVar) {
        this.d = nVar;
        this.f9a = ((j1) this.d).c.c();
        this.c = new l(this);
    }

    public boolean b() {
        return this.b == -1;
    }

    public o1 c() {
        this.f9a.u(this.c);
        this.f9a.v("R201");
        this.f9a.x();
        return this.b == 0 ? (o1) this.f9a.q("R201") : new o1();
    }

    public p1 d() {
        this.f9a.u(this.c);
        this.f9a.v("R001");
        this.f9a.x();
        if (this.b != 0) {
            return new p1();
        }
        p1 p1Var = (p1) this.f9a.q("R001");
        return p1Var == null ? new p1() : p1Var;
    }

    public void e(o1 o1Var) {
        this.f9a.u(this.c);
        this.f9a.w("R201", o1Var);
        this.f9a.x();
    }

    public void f(p1 p1Var) {
        this.f9a.u(this.c);
        this.f9a.w("R001", p1Var);
        this.f9a.x();
    }
}
