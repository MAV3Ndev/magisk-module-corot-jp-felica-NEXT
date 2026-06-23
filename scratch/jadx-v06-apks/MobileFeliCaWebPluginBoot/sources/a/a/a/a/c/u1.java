package a.a.a.a.c;

/* JADX INFO: compiled from: ViewOriginal.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class u1 extends s1 {
    private boolean c;
    protected com.felicanetworks.mfw.a.cmn.v d;
    protected boolean e;
    protected com.felicanetworks.mfw.a.cmn.h0 f;

    public u1(com.felicanetworks.mfw.a.cmn.h0 h0Var, String str) {
        super(str);
        this.c = false;
        this.d = null;
        this.e = false;
        super.f(true);
        this.f = h0Var;
    }

    @Override // a.a.a.a.c.s1
    public void a(int i, Object obj, Object obj2) {
    }

    @Override // a.a.a.a.c.s1
    public void b() {
    }

    @Override // a.a.a.a.c.s1
    public void e(String str, Object obj, Object obj2) {
        if (this.f.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        l(str, obj, obj2);
        this.f.f().o(this);
    }

    @Override // a.a.a.a.c.s1
    public void g(Object obj, Object obj2) {
        if (this.f.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        n(obj, obj2);
        this.f.f().o(this);
    }

    @Override // a.a.a.a.c.s1
    public void h() {
    }

    public void i() {
        com.felicanetworks.mfw.a.cmn.v vVar = this.d;
        if (vVar != null) {
            vVar.dismiss();
        }
    }

    protected boolean j() {
        return this.c;
    }

    protected abstract void k();

    public abstract void l(String str, Object obj, Object obj2);

    protected void m(boolean z) {
        this.c = z;
    }

    public abstract void n(Object obj, Object obj2);
}
