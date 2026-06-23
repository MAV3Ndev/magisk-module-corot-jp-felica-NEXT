package a.a.a.a.a;

import a.a.a.a.c.u0;
import a.a.a.a.c.u1;
import android.view.View;

/* JADX INFO: compiled from: MsgBoxView.java */
/* JADX INFO: loaded from: classes.dex */
class x extends u0 {
    final /* synthetic */ z b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    x(z zVar, com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        super(h0Var);
        this.b = zVar;
    }

    @Override // a.a.a.a.c.u0
    public void a(View view) {
        synchronized (this) {
            if (((u1) this.b).e) {
                return;
            }
            ((u1) this.b).e = true;
            ((u1) this.b).f.f().m(this.b.c(), 28929, "02", null, false);
        }
    }
}
