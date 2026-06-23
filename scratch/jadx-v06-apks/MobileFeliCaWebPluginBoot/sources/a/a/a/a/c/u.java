package a.a.a.a.c;

import java.io.UnsupportedEncodingException;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class u extends x implements m {
    final /* synthetic */ f0 h;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    private u(f0 f0Var) {
        super(f0Var);
        this.h = f0Var;
    }

    @Override // a.a.a.a.c.m
    public String a() {
        return "push_start_browser";
    }

    @Override // a.a.a.a.c.m
    public h0 b(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) {
        try {
            String str = strArr[2];
            this.h.p = true;
            if (this.h.h == 1) {
                this.h.f25a.c(-1, 105, str);
                return super.f(a(), strArr, g0Var, com.felicanetworks.mfw.a.cmn.p0.a().b("command.rw.try.time") * 1000, true);
            }
            this.h.f25a.c(-1, 103, str);
            return super.f(a(), strArr, g0Var, com.felicanetworks.mfw.a.cmn.p0.a().b("command.push.try.time") * 1000, false);
        } catch (com.felicanetworks.mfw.a.cmn.e e) {
            return e.c() == 2 ? this.h.x(4428, e) : e.c() == 1 ? this.h.x(4429, e) : this.h.x(4420, e);
        }
    }

    @Override // a.a.a.a.c.m
    public int c(String[] strArr) {
        if (strArr.length != 3) {
            return 2591;
        }
        try {
            if (com.felicanetworks.mfw.a.cmn.b1.J(strArr[0]).length + strArr[1].getBytes("Shift_JIS").length > 184) {
                return 2592;
            }
            if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[0])) {
                return 2593;
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.o(strArr[0])) {
                return 2594;
            }
            if (com.felicanetworks.mfw.a.cmn.b1.l(strArr[1]) || !(strArr[1].contains(String.valueOf('\"')) || strArr[1].contains(String.valueOf('\'')) || strArr[1].contains(String.valueOf('\\')))) {
                return (com.felicanetworks.mfw.a.cmn.b1.l(strArr[2]) || com.felicanetworks.mfw.a.cmn.b1.A(strArr[2], 1, 32)) ? 0 : 2596;
            }
            return 2595;
        } catch (UnsupportedEncodingException e) {
            throw new com.felicanetworks.mfw.a.cmn.c1(u.class, "encoding err", e);
        }
    }

    @Override // a.a.a.a.c.x
    public h0 g(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var) throws com.felicanetworks.mfw.a.cmn.e {
        this.h.o.m().b(strArr[0], strArr[1], g0Var);
        return null;
    }
}
