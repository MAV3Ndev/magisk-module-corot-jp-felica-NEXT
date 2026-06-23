package com.felicanetworks.mfc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: compiled from: MfiClientAccess.java */
/* JADX INFO: loaded from: classes.dex */
class w0 implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ x0 f121a;

    w0(x0 x0Var) {
        this.f121a = x0Var;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        int i;
        w wVar;
        AppInfo appInfoB;
        String str;
        com.felicanetworks.mfc.s1.a.c(7, "%s %s", "000", componentName.getClassName());
        synchronized (this.f121a) {
            this.f121a.g = com.felicanetworks.mfc.mfi.m.E(iBinder);
            this.f121a.i.b();
            i = 1;
            wVar = null;
            try {
                if (this.f121a.b != null) {
                    try {
                        try {
                            t0.a(this.f121a.g.D(this.f121a.f123a, this.f121a.h));
                        } catch (x e) {
                            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                            int iE = e.e();
                            if (iE != 39) {
                                if (iE != 42) {
                                    com.felicanetworks.mfc.s1.a.d(2, "%s FelicaException id:%d type:%d", "702", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                                } else {
                                    com.felicanetworks.mfc.s1.a.d(2, "%s FelicaException id:%d type:%d", "701", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                                }
                                str = null;
                                appInfoB = null;
                            } else {
                                String message = e.getMessage();
                                appInfoB = e.b();
                                com.felicanetworks.mfc.s1.a.f(2, "%s FelicaException id:%d type:%d pid%d", "700", Integer.valueOf(e.a()), Integer.valueOf(e.e()), message, Integer.valueOf(appInfoB.a()));
                                str = message;
                                i = 7;
                            }
                            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                            com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
                            w wVar2 = this.f121a.b;
                            this.f121a.b = null;
                            this.f121a.E();
                            wVar = wVar2;
                        }
                    } catch (Exception e2) {
                        com.felicanetworks.mfc.s1.a.c(2, "%s Exception %s", "703", e2.getMessage());
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
                        w wVar3 = this.f121a.b;
                        this.f121a.b = null;
                        this.f121a.E();
                        appInfoB = null;
                        wVar = wVar3;
                        str = null;
                    }
                } else {
                    com.felicanetworks.mfc.s1.a.b(2, "%s", "704");
                    this.f121a.E();
                }
                str = null;
                appInfoB = null;
            } finally {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            }
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "004");
        if (wVar != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s Do the callback", "005");
            wVar.v(i, str, appInfoB);
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        w wVar;
        q qVar;
        com.felicanetworks.mfc.mfi.a aVar;
        com.felicanetworks.mfc.mfi.a aVar2;
        com.felicanetworks.mfc.s1.a.c(7, "%s %s", "000", componentName);
        synchronized (this.f121a) {
            if (this.f121a.b != null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                wVar = this.f121a.b;
                this.f121a.b = null;
            } else {
                wVar = null;
            }
            if (this.f121a.c != null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                qVar = this.f121a.c;
                this.f121a.c = null;
            } else {
                qVar = null;
            }
            if (this.f121a.d != null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
                aVar = this.f121a.d;
                this.f121a.d = null;
            } else {
                aVar = null;
            }
            if (this.f121a.e != null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "004");
                aVar2 = this.f121a.e;
                this.f121a.e = null;
            } else {
                aVar2 = null;
            }
            this.f121a.E();
        }
        if (wVar != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "004");
            wVar.v(1, "Unknown error.", null);
        }
        if (qVar != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "005");
            qVar.h(1, "Unknown error.");
        }
        if (aVar != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "006");
            aVar.a(200, "Unknown error.");
        }
        if (aVar2 != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "007");
            aVar2.a(200, "Unknown error.");
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }
}
