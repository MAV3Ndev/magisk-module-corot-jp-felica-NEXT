package a.a.a.a.c;

/* JADX INFO: compiled from: PermitOptr.java */
/* JADX INFO: loaded from: classes.dex */
class b1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f16a;
    private c b;
    final /* synthetic */ h1 c;

    private b1(h1 h1Var) {
        this.c = h1Var;
        this.b = new a1(this);
    }

    private int c() {
        return this.f16a;
    }

    public com.felicanetworks.mfw.a.cmn.y0 b(String str, String str2, String str3) throws c1 {
        b bVarB = this.c.d.b();
        bVarB.h(this.b);
        bVarB.j(str, str3, str2);
        bVarB.k();
        int iC = c();
        if (iC == 0) {
            return bVarB.f();
        }
        throw new c1(this.c, iC);
    }
}
