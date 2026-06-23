package com.felicanetworks.mfc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: compiled from: MfiClientAccess.java */
/* JADX INFO: loaded from: classes.dex */
class u0 extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ x0 f119a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    u0(x0 x0Var, Looper looper) {
        super(looper);
        this.f119a = x0Var;
    }

    void a(int i) {
        com.felicanetworks.mfc.s1.a.c(7, "%s timeout=%d", "000", Integer.valueOf(i));
        if (i > 0) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            sendMessageDelayed(this.f119a.i.obtainMessage(1), i);
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    void b() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        removeMessages(1);
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        w wVar;
        com.felicanetworks.mfc.s1.a.c(7, "%s what=%d", "000", Integer.valueOf(message.what));
        if (message.what == 1) {
            com.felicanetworks.mfc.s1.a.c(2, "%s bind timeout connecting=%b", "700", Boolean.valueOf(this.f119a.s()));
            synchronized (this.f119a) {
                if (this.f119a.s()) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    wVar = this.f119a.b;
                    this.f119a.b = null;
                    this.f119a.E();
                } else {
                    wVar = null;
                }
            }
            if (wVar != null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s Do the callback", "002");
                wVar.v(1, "Bind timeout.", null);
            }
        }
        super.handleMessage(message);
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }
}
