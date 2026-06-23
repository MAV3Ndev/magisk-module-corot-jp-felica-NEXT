package a.a.a.a.a;

import a.a.a.a.c.u0;
import a.a.a.a.c.u1;
import android.view.View;

/* JADX INFO: compiled from: InProcessView.java */
/* JADX INFO: loaded from: classes.dex */
class t extends u0 {
    final /* synthetic */ v b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    t(v vVar, com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        super(h0Var);
        this.b = vVar;
    }

    @Override // a.a.a.a.c.u0
    public void a(View view) {
        synchronized (this) {
            if (((u1) this.b).e) {
                return;
            }
            ((u1) this.b).e = true;
            ((u1) this.b).f.f().m("01", 36864, null, null, false);
        }
    }
}
