package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class b0 implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f15a;

    private b0(f0 f0Var) {
        this.f15a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "rw_write";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        int i = (int) Long.parseLong(strArr[0], 16);
        int i2 = Integer.parseInt(strArr[1]);
        int i3 = Integer.parseInt(strArr[2]);
        int i4 = Integer.parseInt(strArr[3]);
        String str = strArr[4];
        this.f15a.f25a.c(-1, 101, null);
        try {
            this.f15a.o.p().b(i, i2, i3, i4, str, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.f15a.x(4248, e) : e.c() == 1 ? this.f15a.x(4249, e) : this.f15a.x(4240, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 5) {
            return 2551;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2552;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 8)) {
            return 2553;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2554;
        }
        f0 f0Var = this.f15a;
        if (!f0Var.B(f0Var.b, strArr[0])) {
            return 2555;
        }
        if (!com.felicanetworks.mfw.a.cmn.f0.e(Long.parseLong(strArr[0], 16))) {
            return 2556;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.f(Long.parseLong(strArr[0], 16))) {
            return 2557;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.b(Long.parseLong(strArr[0], 16))) {
            return 2558;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.h(Long.parseLong(strArr[0], 16))) {
            return 2559;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[1])) {
            return 2561;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[1], 2)) {
            return 2562;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(strArr[1])) {
            return 2563;
        }
        if (com.felicanetworks.mfw.a.cmn.f0.c(Long.parseLong(strArr[0], 16)) && !"00".equals(strArr[1])) {
            return 2564;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[2])) {
            return 2565;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[2], 2)) {
            return 2566;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(strArr[2])) {
            return 2567;
        }
        if (Integer.parseInt(strArr[2]) < 1 || Integer.parseInt(strArr[2]) > 8) {
            return 2568;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[3])) {
            return 2569;
        }
        if (!"0".equals(strArr[3]) && !"1".equals(strArr[3])) {
            return 2570;
        }
        if ("1".equals(strArr[3]) && !com.felicanetworks.mfw.a.cmn.f0.g(Long.parseLong(strArr[0], 16))) {
            return 2572;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[4])) {
            return 2573;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.q(strArr[4], Integer.parseInt(strArr[2]) * 32)) {
            return !com.felicanetworks.mfw.a.cmn.b1.m(strArr[4]) ? 2575 : 0;
        }
        return 2574;
    }
}
