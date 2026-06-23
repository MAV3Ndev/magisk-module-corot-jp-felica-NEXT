package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class v implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f56a;

    private v(f0 f0Var) {
        this.f56a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "read";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        int i = (int) Long.parseLong(strArr[0], 16);
        int i2 = Integer.parseInt(strArr[1]);
        int i3 = Integer.parseInt(strArr[2]);
        this.f56a.f25a.c(-1, 100, null);
        try {
            this.f56a.o.n().b(i, i2, i3, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 8 ? this.f56a.x(4132, e) : e.c() == 2 ? this.f56a.x(4138, e) : e.c() == 1 ? this.f56a.x(4139, e) : this.f56a.x(4130, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 3) {
            return 2431;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2432;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 8)) {
            return 2433;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2434;
        }
        f0 f0Var = this.f56a;
        if (!f0Var.B(f0Var.b, strArr[0])) {
            return 2435;
        }
        if (!com.felicanetworks.mfw.a.cmn.f0.e(Long.parseLong(strArr[0], 16))) {
            return 2436;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.f(Long.parseLong(strArr[0], 16))) {
            return 2437;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.b(Long.parseLong(strArr[0], 16))) {
            return 2438;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[1])) {
            return 2439;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[1], 2)) {
            return 2440;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(strArr[1])) {
            return 2441;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[2])) {
            return 2442;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[2], 2)) {
            return 2443;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.k(strArr[2])) {
            return (Integer.parseInt(strArr[2]) < 1 || Integer.parseInt(strArr[2]) > 14) ? 2445 : 0;
        }
        return 2444;
    }
}
