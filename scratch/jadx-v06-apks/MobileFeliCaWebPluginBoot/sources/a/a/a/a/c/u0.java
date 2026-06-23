package a.a.a.a.c;

import android.view.View;

/* JADX INFO: compiled from: OnMyClickListener.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class u0 implements View.OnClickListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.felicanetworks.mfw.a.cmn.h0 f55a;

    public u0(com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        this.f55a = h0Var;
    }

    public abstract void a(View view);

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        try {
            a(view);
        } catch (Exception e) {
            this.f55a.g().r(e);
        }
    }
}
