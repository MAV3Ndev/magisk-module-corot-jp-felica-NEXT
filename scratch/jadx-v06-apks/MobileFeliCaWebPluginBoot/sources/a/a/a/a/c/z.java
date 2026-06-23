package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class z implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f63a;

    private z(f0 f0Var) {
        this.f63a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "rw_read";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        int i = (int) Long.parseLong(strArr[0], 16);
        int i2 = Integer.parseInt(strArr[1]);
        int i3 = Integer.parseInt(strArr[2]);
        this.f63a.f25a.c(-1, 101, null);
        try {
            this.f63a.o.n().b(i, i2, i3, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.f63a.x(4238, e) : e.c() == 1 ? this.f63a.x(4239, e) : this.f63a.x(4230, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 3) {
            return 2531;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2532;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 8)) {
            return 2533;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2534;
        }
        f0 f0Var = this.f63a;
        if (!f0Var.B(f0Var.b, strArr[0])) {
            return 2535;
        }
        if (!com.felicanetworks.mfw.a.cmn.f0.e(Long.parseLong(strArr[0], 16))) {
            return 2536;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.f(Long.parseLong(strArr[0], 16))) {
            return 2537;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.b(Long.parseLong(strArr[0], 16))) {
            return 2538;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[1])) {
            return 2540;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[1], 2)) {
            return 2541;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(strArr[1])) {
            return 2542;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[2])) {
            return 2543;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[2], 2)) {
            return 2544;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.k(strArr[2])) {
            return (Integer.parseInt(strArr[2]) < 1 || Integer.parseInt(strArr[2]) > 12) ? 2546 : 0;
        }
        return 2545;
    }
}
