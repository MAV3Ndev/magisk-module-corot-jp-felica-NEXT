package com.felicanetworks.mfc;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

/* JADX INFO: compiled from: FSC.java */
/* JADX INFO: loaded from: classes.dex */
class m extends Handler {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ FSC f94a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    m(FSC fsc, Looper looper) {
        super(looper);
        this.f94a = fsc;
    }

    void a(int i) {
        com.felicanetworks.mfc.s1.a.c(3, "%s timeout=%d", "000", Integer.valueOf(i));
        if (i > 0) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            sendMessageDelayed(this.f94a.c.obtainMessage(1), i);
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
        com.felicanetworks.mfc.s1.a.c(3, "%s what=%d", "000", Integer.valueOf(message.what));
        if (message.what == 1) {
            com.felicanetworks.mfc.s1.a.c(2, "%s bind timeout online=%b", "800", Boolean.valueOf(this.f94a.e));
            q qVar = null;
            synchronized (this.f94a) {
                if (this.f94a.e && this.f94a.i == null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    qVar = this.f94a.b;
                    this.f94a.f = true;
                    this.f94a.e = false;
                }
            }
            if (qVar != null) {
                com.felicanetworks.mfc.s1.a.b(3, "%s Do the callback", "010");
                qVar.h(1, "Bind timeout.");
            }
        }
        super.handleMessage(message);
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
