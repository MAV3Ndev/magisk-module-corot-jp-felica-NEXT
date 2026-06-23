package a.a.a.a.a;

import a.a.a.a.c.u1;
import a.a.a.a.c.v0;
import android.content.DialogInterface;

/* JADX INFO: compiled from: ErrView.java */
/* JADX INFO: loaded from: classes.dex */
class q extends v0 {
    final /* synthetic */ r b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    q(r rVar, com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        super(h0Var);
        this.b = rVar;
    }

    @Override // a.a.a.a.c.v0
    protected void a(DialogInterface dialogInterface) {
        if (((u1) this.b).e) {
            return;
        }
        if ((((u1) this.b).f.g().m() != 1 && (this.b.h == 6102 || this.b.h == 6101)) || this.b.h == 6105) {
            ((u1) this.b).f.g().w(this.b.h);
        }
        ((u1) this.b).f.g().d();
    }
}
