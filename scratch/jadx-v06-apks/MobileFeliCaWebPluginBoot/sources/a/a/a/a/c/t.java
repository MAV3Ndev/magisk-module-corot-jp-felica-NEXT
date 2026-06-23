package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class t extends x implements m {
    final /* synthetic */ f0 h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private t(f0 f0Var) {
        super(f0Var);
        this.h = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "push_start_app";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        try {
            String str = strArr[3];
            this.h.p = true;
            if (this.h.h == 1) {
                this.h.f25a.c(-1, 105, str);
                return super.f(a(), strArr, g0Var, com.felicanetworks.mfw.a.cmn.p0.a().b("command.rw.try.time") * 1000, true);
            }
            this.h.f25a.c(-1, 103, str);
            return super.f(a(), strArr, g0Var, com.felicanetworks.mfw.a.cmn.p0.a().b("command.push.try.time") * 1000, false);
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.h.x(4418, e) : e.c() == 1 ? this.h.x(4419, e) : this.h.x(4410, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 4) {
            return 2581;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.J(strArr[0] + strArr[2]).length > 176) {
            return 2582;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.l(strArr[0]) && !com.felicanetworks.mfw.a.cmn.b1.o(strArr[0])) {
            return 2583;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(strArr[1], 6)) {
            return 2584;
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.o(strArr[1])) {
            return 2585;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[2]) || com.felicanetworks.mfw.a.cmn.b1.o(strArr[2])) {
            return (com.felicanetworks.mfw.a.cmn.b1.l(strArr[3]) || com.felicanetworks.mfw.a.cmn.b1.A(strArr[3], 1, 32)) ? 0 : 2587;
        }
        return 2586;
    }

    @Override // a.a.a.a.c.x
    public h0 g(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) throws com.felicanetworks.mfw.a.cmn.e {
        this.h.o.m().a(strArr[0], strArr[1], com.felicanetworks.mfw.a.cmn.b1.e(strArr[2], " "), g0Var);
        return null;
    }
}
