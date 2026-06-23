package a.a.a.a.c;

/* JADX INFO: compiled from: PermitOptr.java */
/* JADX INFO: loaded from: classes.dex */
class e1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f24a;
    private f b;
    final /* synthetic */ h1 c;

    private e1(h1 h1Var) {
        this.c = h1Var;
        this.b = new d1(this);
    }

    private l1 b(String str) {
        e eVarC = this.c.d.c();
        eVarC.u(this.b);
        eVarC.v(str);
        eVarC.x();
        if (this.f24a == 0) {
            return eVarC.q(str);
        }
        return null;
    }

    private void e(String str, l1 l1Var) {
        e eVarC = this.c.d.c();
        eVarC.u(this.b);
        eVarC.w(str, l1Var);
        eVarC.x();
    }

    public n1 c() {
        l1 l1VarB = b("R101");
        return l1VarB == null ? new n1() : (n1) l1VarB;
    }

    public void d(n1 n1Var) {
        e("R101", n1Var);
    }
}
