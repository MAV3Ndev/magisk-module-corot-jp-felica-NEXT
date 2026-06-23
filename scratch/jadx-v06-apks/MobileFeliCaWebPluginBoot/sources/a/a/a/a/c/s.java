package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class s implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f51a;

    private s(f0 f0Var) {
        this.f51a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "onfelica_start";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        this.f51a.f25a.c(-1, 100, null);
        try {
            String[] strArrW = this.f51a.w(new r(this.f51a).b("I009", strArr[2], strArr[0]).b());
            if (String.valueOf(4321).equals(strArrW[0])) {
                h0 h0Var = new h0();
                h0Var.e(this.f51a.g);
                h0Var.f(String.valueOf(4321));
                h0Var.a(new com.felicanetworks.mfw.a.cmn.i0("authfincode", strArrW[3]));
                return h0Var;
            }
            if (String.valueOf(4322).equals(strArrW[0])) {
                h0 h0Var2 = new h0();
                h0Var2.e(this.f51a.g);
                h0Var2.f(String.valueOf(4322));
                return h0Var2;
            }
            try {
                String str = strArrW[1];
                this.f51a.l = strArrW[2];
                this.f51a.o.i().b(str, this.f51a.n);
                return null;
            } catch (com.felicanetworks.mfw.a.cmn.e e) {
                return e.c() == 2 ? this.f51a.x(4338, e) : this.f51a.x(4330, e);
            }
        } catch (o e2) {
            h0 h0Var3 = new h0();
            h0Var3.e(this.f51a.g);
            h0Var3.f(String.valueOf(e2.a()));
            return h0Var3;
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 3) {
            return 2491;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2492;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.J(strArr[0]).length > 255) {
            return 2493;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.D(strArr[0])) {
            return 2494;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.l(strArr[1])) {
            if (com.felicanetworks.mfw.a.cmn.b1.J(strArr[1]).length > 255) {
                return 2495;
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.D(strArr[1])) {
                return 2496;
            }
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[2])) {
            return 2497;
        }
        return !com.felicanetworks.mfw.a.cmn.b1.B(strArr[2], com.felicanetworks.mfw.a.cmn.b1.b(com.felicanetworks.mfw.a.cmn.b1.e, new char[]{'=', '&'})) ? 2498 : 0;
    }
}
