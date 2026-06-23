package a.a.a.a.c;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
public class r {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f49a;
    private c b = new q(this);
    final /* synthetic */ f0 c;

    public r(f0 f0Var) {
        this.c = f0Var;
    }

    public com.felicanetworks.mfw.a.cmn.y0 b(String str, String str2, String str3) {
        b bVarB = this.c.o.b();
        bVarB.h(this.b);
        bVarB.j(str, str2, str3);
        bVarB.k();
        if (this.f49a == 0) {
            return bVarB.f();
        }
        throw new o(this.c, this.f49a);
    }
}
