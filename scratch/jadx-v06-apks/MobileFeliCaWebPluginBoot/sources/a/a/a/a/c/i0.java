package a.a.a.a.c;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: compiled from: Ctrler.java */
/* JADX INFO: loaded from: classes.dex */
class i0 extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f33a;
    final /* synthetic */ l0 b;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    i0(l0 l0Var, Looper looper) {
        super(looper);
        this.b = l0Var;
        this.f33a = false;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        try {
            if (this.f33a) {
                return;
            }
            super.dispatchMessage(message);
        } catch (Exception e) {
            this.f33a = true;
            this.b.f.g().r(e);
        }
    }
}
