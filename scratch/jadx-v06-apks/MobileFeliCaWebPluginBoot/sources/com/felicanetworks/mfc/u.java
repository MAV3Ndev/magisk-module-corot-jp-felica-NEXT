package com.felicanetworks.mfc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: compiled from: Felica.java */
/* JADX INFO: loaded from: classes.dex */
class u implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ Felica f118a;

    u(Felica felica) {
        this.f118a = felica;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        int i;
        w wVar;
        AppInfo appInfo;
        AppInfo appInfo2;
        com.felicanetworks.mfc.s1.a.c(3, "%s %s", "000", componentName.getClassName());
        synchronized (this.f118a) {
            this.f118a.i = n0.E(iBinder);
            this.f118a.e.b();
            i = 1;
            if (this.f118a.d != null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                try {
                    try {
                        t0.a(this.f118a.i.z(this.f118a.g, this.f118a.h));
                    } finally {
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "010");
                        this.f118a.g = null;
                    }
                } catch (x e) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                    int iE = e.e();
                    if (iE != 39) {
                        if (iE != 42) {
                            com.felicanetworks.mfc.s1.a.d(2, "%s FelicaException id:%d type:%d", "702", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                        } else {
                            com.felicanetworks.mfc.s1.a.d(2, "%s FelicaException id:%d type:%d", "701", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                        }
                        appInfo2 = null;
                    } else {
                        AppInfo appInfoB = e.b();
                        com.felicanetworks.mfc.s1.a.f(2, "%s FelicaException id:%d type:%d pid%d", "700", Integer.valueOf(e.a()), Integer.valueOf(e.e()), null, Integer.valueOf(appInfoB.a()));
                        appInfo2 = appInfoB;
                        i = 7;
                    }
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "010");
                    this.f118a.g = null;
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "011");
                    w wVar2 = this.f118a.d;
                    this.f118a.d = null;
                    this.f118a.K();
                    appInfo = appInfo2;
                    wVar = wVar2;
                } catch (Exception e2) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s Exception %s", "703", e2.getMessage());
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "010");
                    this.f118a.g = null;
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "011");
                    wVar = this.f118a.d;
                    this.f118a.d = null;
                    this.f118a.K();
                    appInfo = null;
                }
            } else {
                com.felicanetworks.mfc.s1.a.b(2, "%s", "704");
                this.f118a.K();
            }
            appInfo = null;
            wVar = null;
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "700");
        if (wVar != null) {
            com.felicanetworks.mfc.s1.a.b(3, "%s Do the callback", "020");
            wVar.v(i, null, appInfo);
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        w wVar;
        com.felicanetworks.mfc.s1.a.c(3, "%s %s", "000", componentName);
        synchronized (this.f118a) {
            if (this.f118a.d != null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                wVar = this.f118a.d;
                this.f118a.d = null;
            } else {
                wVar = null;
            }
            this.f118a.K();
        }
        if (wVar != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            wVar.v(1, "Unknown error.", null);
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
