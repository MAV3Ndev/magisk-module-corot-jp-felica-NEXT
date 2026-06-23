package a.a.a.a.c;

import android.content.DialogInterface;

/* JADX INFO: compiled from: OnMyDismissListener.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class v0 implements DialogInterface.OnDismissListener {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.felicanetworks.mfw.a.cmn.h0 f57a;

    public v0(com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        this.f57a = h0Var;
    }

    protected abstract void a(DialogInterface dialogInterface);

    @Override // android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        try {
            a(dialogInterface);
        } catch (Exception e) {
            this.f57a.g().r(e);
        }
    }
}
