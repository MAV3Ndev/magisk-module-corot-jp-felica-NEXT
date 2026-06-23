package a.a.a.a.a;

import a.a.a.a.c.u1;
import a.a.a.a.c.v0;
import android.content.DialogInterface;

/* JADX INFO: compiled from: MsgNtfyView.java */
/* JADX INFO: loaded from: classes.dex */
class c0 extends v0 {
    final /* synthetic */ m0 b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    c0(m0 m0Var, com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        super(h0Var);
        this.b = m0Var;
    }

    @Override // a.a.a.a.c.v0
    protected void a(DialogInterface dialogInterface) {
        if (((u1) this.b).e) {
            return;
        }
        if (((u1) this.b).f.g().m() != 1) {
            ((u1) this.b).f.g().w(6104);
        }
        ((u1) this.b).f.g().d();
    }
}
