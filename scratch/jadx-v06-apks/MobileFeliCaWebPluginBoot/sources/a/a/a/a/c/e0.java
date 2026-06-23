package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class e0 implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f23a;

    private e0(f0 f0Var) {
        this.f23a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "write";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        int i = (int) Long.parseLong(strArr[0], 16);
        int i2 = Integer.parseInt(strArr[1]);
        int i3 = Integer.parseInt(strArr[2]);
        int i4 = Integer.parseInt(strArr[3]);
        String str = strArr[4];
        this.f23a.f25a.c(-1, 100, null);
        try {
            this.f23a.o.p().b(i, i2, i3, i4, str, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 8 ? this.f23a.x(4142, e) : e.c() == 11 ? this.f23a.x(4145, e) : e.c() == 12 ? this.f23a.x(4146, e) : e.c() == 2 ? this.f23a.x(4148, e) : e.c() == 1 ? this.f23a.x(4149, e) : this.f23a.x(4140, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 5) {
            return 2451;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2452;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 8)) {
            return 2453;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2454;
        }
        f0 f0Var = this.f23a;
        if (!f0Var.B(f0Var.b, strArr[0])) {
            return 2455;
        }
        if (!com.felicanetworks.mfw.a.cmn.f0.e(Long.parseLong(strArr[0], 16))) {
            return 2456;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.f(Long.parseLong(strArr[0], 16))) {
            return 2457;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.b(Long.parseLong(strArr[0], 16))) {
            return 2458;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.h(Long.parseLong(strArr[0], 16))) {
            return 2459;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[1])) {
            return 2460;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[1], 2)) {
            return 2461;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(strArr[1])) {
            return 2462;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.c(Long.parseLong(strArr[0], 16)) && !"00".equals(strArr[1])) {
            return 2463;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[2])) {
            return 2464;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[2], 2)) {
            return 2465;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(strArr[2])) {
            return 2466;
        }
        if (Integer.parseInt(strArr[2]) < 1 || Integer.parseInt(strArr[2]) > 10) {
            return 2467;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[3])) {
            return 2468;
        }
        if (!"0".equals(strArr[3]) && !"1".equals(strArr[3])) {
            return 2469;
        }
        if ("1".equals(strArr[3]) && !com.felicanetworks.mfw.a.cmn.f0.g(Long.parseLong(strArr[0], 16))) {
            return 2471;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[4])) {
            return 2472;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.q(strArr[4], Integer.parseInt(strArr[2]) * 32)) {
            return !com.felicanetworks.mfw.a.cmn.b1.m(strArr[4]) ? 2474 : 0;
        }
        return 2473;
    }
}
