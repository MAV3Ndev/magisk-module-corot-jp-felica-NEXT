package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class a0 extends x implements m {
    final /* synthetic */ f0 h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private a0(f0 f0Var) {
        super(f0Var);
        this.h = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "rw_select";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        String str = strArr[3];
        this.h.p = true;
        this.h.f25a.c(-1, 101, str);
        try {
            return super.f(a(), strArr, g0Var, com.felicanetworks.mfw.a.cmn.p0.a().b("command.rw.try.time") * 1000, true);
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.h.x(4218, e) : e.c() == 1 ? this.h.x(4219, e) : e.c() == 3 ? this.h.x(4211, e) : this.h.x(4210, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 4) {
            return 2501;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
            return 2502;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[0], 4)) {
            return 2503;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[0])) {
            return 2504;
        }
        if ("80CD".equalsIgnoreCase(strArr[0]) || "FE0F".equalsIgnoreCase(strArr[0])) {
            return 2505;
        }
        if ("FF".equalsIgnoreCase(strArr[0].substring(0, 2)) || "FF".equalsIgnoreCase(strArr[0].substring(2))) {
            return 2506;
        }
        if (!this.h.C(strArr[0])) {
            return 2507;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.l(strArr[1])) {
            if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[1], 16)) {
                return 2508;
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.m(strArr[1])) {
                return 2509;
            }
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.l(strArr[2])) {
            if (!com.felicanetworks.mfw.a.cmn.b1.r(strArr[2], 1, 4)) {
                return 2510;
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.k(strArr[2])) {
                return 2511;
            }
            if (Integer.parseInt(strArr[2]) < 1 || Integer.parseInt(strArr[2]) > 3000) {
                return 2512;
            }
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.l(strArr[3]) && !com.felicanetworks.mfw.a.cmn.b1.A(strArr[3], 1, 32)) {
            return 2513;
        }
        this.h.b = strArr[0];
        return 0;
    }

    @Override // a.a.a.a.c.x
    public h0 g(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) throws com.felicanetworks.mfw.a.cmn.e {
        this.h.o.l().d(Integer.parseInt(strArr[0], 16), false, "".equals(strArr[2]) ? -1 : Integer.parseInt(strArr[2]), g0Var);
        return null;
    }
}
