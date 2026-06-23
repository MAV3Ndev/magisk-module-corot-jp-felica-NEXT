package a.a.a.a.a;

import a.a.a.a.c.u0;
import a.a.a.a.c.u1;
import android.view.View;
import com.felicanetworks.mfw.a.cmn.p0;

/* JADX INFO: compiled from: MsgNtfyView.java */
/* JADX INFO: loaded from: classes.dex */
class a0 extends u0 {
    final /* synthetic */ m0 b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    a0(m0 m0Var, com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        super(h0Var);
        this.b = m0Var;
    }

    @Override // a.a.a.a.c.u0
    public void a(View view) {
        synchronized (this) {
            if (((u1) this.b).e) {
                return;
            }
            ((u1) this.b).e = true;
            ((u1) this.b).f.g().G(p0.a().c("format.felica.package.name"));
            ((u1) this.b).f.f().m(this.b.c(), 28675, null, null, false);
            if (((u1) this.b).f.g().m() != 1) {
                ((u1) this.b).f.g().w(6104);
            }
            ((u1) this.b).f.g().d();
        }
    }
}
