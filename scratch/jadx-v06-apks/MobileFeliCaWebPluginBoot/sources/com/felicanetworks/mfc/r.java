package com.felicanetworks.mfc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: compiled from: Felica.java */
/* JADX INFO: loaded from: classes.dex */
class r extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ Felica f114a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    r(Felica felica, Looper looper) {
        super(looper);
        this.f114a = felica;
    }

    void a(int i) {
        com.felicanetworks.mfc.s1.a.c(3, "%s timeout=%d", "000", Integer.valueOf(i));
        if (i > 0) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            sendMessageDelayed(this.f114a.e.obtainMessage(1), i);
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    void b() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        removeMessages(1);
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    @Override // android.os.Handler
    public void handleMessage(Message message) {
        w wVar;
        com.felicanetworks.mfc.s1.a.c(3, "%s what=%d", "000", Integer.valueOf(message.what));
        if (message.what == 1) {
            com.felicanetworks.mfc.s1.a.c(2, "%s bind timeout connecting=%b", "800", Boolean.valueOf(this.f114a.o()));
            synchronized (this.f114a) {
                if (this.f114a.o()) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    wVar = this.f114a.d;
                    this.f114a.d = null;
                    this.f114a.K();
                } else {
                    wVar = null;
                }
            }
            if (wVar != null) {
                com.felicanetworks.mfc.s1.a.b(3, "%s Do the callback", "010");
                wVar.v(1, "Bind timeout.", null);
            }
        }
        super.handleMessage(message);
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
