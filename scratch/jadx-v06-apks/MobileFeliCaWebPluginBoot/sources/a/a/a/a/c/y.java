package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class y implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f62a;

    private y(f0 f0Var) {
        this.f62a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "rw_get_keyversion";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        String str = strArr[0];
        if (!this.f62a.j && com.felicanetworks.mfw.a.cmn.f0.i(Long.parseLong(str, 16))) {
            str = "0000FFFF";
        }
        int i = (int) Long.parseLong(str, 16);
        this.f62a.f25a.c(-1, 101, null);
        try {
            this.f62a.o.o().a(i, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.f62a.x(4228, e) : e.c() == 1 ? this.f62a.x(4229, e) : this.f62a.x(4220, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 1) {
            return 2521;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2522;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 8)) {
            return 2523;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2524;
        }
        f0 f0Var = this.f62a;
        if (f0Var.B(f0Var.b, strArr[0])) {
            return (com.felicanetworks.mfw.a.cmn.f0.i(Long.parseLong(strArr[0], 16)) || com.felicanetworks.mfw.a.cmn.f0.e(Long.parseLong(strArr[0], 16))) ? 0 : 2526;
        }
        return 2525;
    }
}
