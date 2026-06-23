package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class n implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f41a;

    private n(f0 f0Var) {
        this.f41a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "get_keyversion";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        int i = (int) Long.parseLong(strArr[0], 16);
        this.f41a.f25a.c(-1, 100, null);
        try {
            this.f41a.o.o().a(i, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.f41a.x(4128, e) : e.c() == 1 ? this.f41a.x(4129, e) : this.f41a.x(4120, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 1) {
            return 2421;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2422;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 8)) {
            return 2423;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2424;
        }
        f0 f0Var = this.f41a;
        if (f0Var.B(f0Var.b, strArr[0])) {
            return (com.felicanetworks.mfw.a.cmn.f0.i(Long.parseLong(strArr[0], 16)) || com.felicanetworks.mfw.a.cmn.f0.e(Long.parseLong(strArr[0], 16))) ? 0 : 2426;
        }
        return 2425;
    }
}
