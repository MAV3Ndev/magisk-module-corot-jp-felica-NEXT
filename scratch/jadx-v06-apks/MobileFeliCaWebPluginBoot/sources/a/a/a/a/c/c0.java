package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class c0 implements m {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ f0 f17a;

    private c0(f0 f0Var) {
        this.f17a = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "select";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        String str = strArr[0];
        this.f17a.f25a.c(-1, 100, strArr[1]);
        try {
            this.f17a.o.l().d(Integer.parseInt(str, 16), true, 0, g0Var);
            return null;
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.f17a.x(4118, e) : e.c() == 1 ? this.f17a.x(4119, e) : e.c() == 3 ? this.f17a.x(4111, e) : this.f17a.x(4110, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 2) {
            return 2411;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2412;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 4)) {
            return 2413;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2414;
        }
        if ("80CD".equalsIgnoreCase(strArr[0]) || "FE0F".equalsIgnoreCase(strArr[0])) {
            return 2415;
        }
        if ("FF".equalsIgnoreCase(strArr[0].substring(0, 2)) || "FF".equalsIgnoreCase(strArr[0].substring(2))) {
            return 2416;
        }
        if (!this.f17a.C(strArr[0])) {
            return 2417;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.l(strArr[1]) && !com.felicanetworks.mfw.a.cmn.b1.A(strArr[1], 1, 32)) {
            return 2418;
        }
        this.f17a.b = strArr[0];
        return 0;
    }
}
