package a.a.a.a.a;

import a.a.a.a.c.h1;
import a.a.a.a.c.i1;
import a.a.a.a.c.j1;
import a.a.a.a.c.x0;
import com.felicanetworks.mfw.a.cmn.y0;

/* JADX INFO: compiled from: CmdDataProcs.java */
/* JADX INFO: loaded from: classes.dex */
class f {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private x0 f4a;
    private y0 b;
    private int c;
    private i1 d;
    final /* synthetic */ g e;

    private f(g gVar) {
        this.e = gVar;
        this.d = new e(this);
    }

    public void d(String str) throws Throwable {
        h1 h1VarK = ((j1) this.e).c.k();
        h1VarK.I(this.d);
        h1VarK.J(str);
        this.b = h1VarK.k();
        this.f4a = h1VarK.g();
        if (this.c != 0) {
            throw new b(this.e, this.c);
        }
    }
}
