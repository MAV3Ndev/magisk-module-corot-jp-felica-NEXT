package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class d0 implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f20a;

    private d0(f0 f0Var) {
        this.f20a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "start";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        String str = strArr[0];
        this.f20a.f25a.c(-1, 100, null);
        try {
            this.f20a.o.i().b(str, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.f20a.x(4318, e) : this.f20a.x(4310, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 1) {
            return 2481;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2482;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.J(strArr[0]).length > 255) {
            return 2483;
        }
        return !com.felicanetworks.mfw.a.cmn.b1.D(strArr[0]) ? 2484 : 0;
    }
}
