package a.a.a.a.a;

import a.a.a.a.c.u1;
import a.a.a.a.c.v0;
import android.content.DialogInterface;

/* JADX INFO: compiled from: MsgBoxView.java */
/* JADX INFO: loaded from: classes.dex */
class y extends v0 {
    final /* synthetic */ z b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    y(z zVar, com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        super(h0Var);
        this.b = zVar;
    }

    @Override // a.a.a.a.c.v0
    protected void a(DialogInterface dialogInterface) {
        if (((u1) this.b).e) {
            return;
        }
        ((u1) this.b).f.f().m(this.b.c(), 28929, "02", null, false);
    }
}
