package com.felicanetworks.mfc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/* JADX INFO: compiled from: FSC.java */
/* JADX INFO: loaded from: classes.dex */
class p implements ServiceConnection {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ FSC f112a;

    p(FSC fsc) {
        this.f112a = fsc;
    }

    @Override // android.content.ServiceConnection
    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
        o0 o0VarX;
        n nVarS;
        q qVar;
        String str;
        String str2;
        com.felicanetworks.mfc.s1.a.c(3, "%s %s", "000", componentName.getClassName());
        synchronized (this.f112a) {
            this.f112a.i = i0.E(iBinder);
            this.f112a.c.b();
            try {
                if (this.f112a.f) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "020");
                    q qVar2 = this.f112a.b;
                    n nVar = new n(this.f112a, 2, "Interrupted.");
                    this.f112a.f = false;
                    this.f112a.e = false;
                    qVar = qVar2;
                    nVarS = nVar;
                    o0VarX = null;
                } else {
                    try {
                        com.felicanetworks.mfc.s1.a.b(7, "%s", "010");
                        String str3 = this.f112a.g;
                        if (this.f112a.f77a == null) {
                            com.felicanetworks.mfc.s1.a.c(1, "%s %s", "800", "Felica hasn't been set.");
                            throw new x(2, 24);
                        }
                        o0VarX = this.f112a.f77a.x();
                        try {
                            if (o0VarX == null) {
                                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "703", "IFelica instance is not found.");
                                throw new x(2, 1);
                            }
                            t0.a(this.f112a.i.y(str3, this.f112a.d, this.f112a.h, o0VarX));
                            nVarS = null;
                            qVar = null;
                        } catch (x e) {
                            e = e;
                            com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "702", "FelicaException", Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                            nVarS = this.f112a.r(e);
                            if (nVarS != null) {
                                com.felicanetworks.mfc.s1.a.b(7, "%s", "014");
                                qVar = this.f112a.b;
                                this.f112a.e = false;
                            } else {
                                qVar = null;
                            }
                            str = "%s";
                            str2 = "010";
                            com.felicanetworks.mfc.s1.a.b(7, str, str2);
                        } catch (IllegalArgumentException e2) {
                            e = e2;
                            com.felicanetworks.mfc.s1.a.d(2, "%s %s msg:%s", "701", "IllegalArgumentException", e.getMessage());
                            nVarS = this.f112a.t(e);
                            if (nVarS != null) {
                                com.felicanetworks.mfc.s1.a.b(7, "%s", "014");
                                qVar = this.f112a.b;
                                this.f112a.e = false;
                            } else {
                                qVar = null;
                            }
                            str = "%s";
                            str2 = "010";
                            com.felicanetworks.mfc.s1.a.b(7, str, str2);
                        } catch (Exception e3) {
                            e = e3;
                            com.felicanetworks.mfc.s1.a.d(2, "%s %s msg:%s", "709", "Exception", e.getMessage());
                            nVarS = this.f112a.s(e);
                            if (nVarS != null) {
                                com.felicanetworks.mfc.s1.a.b(7, "%s", "014");
                                qVar = this.f112a.b;
                                this.f112a.e = false;
                            } else {
                                qVar = null;
                            }
                            str = "%s";
                            str2 = "010";
                            com.felicanetworks.mfc.s1.a.b(7, str, str2);
                        }
                    } catch (x e4) {
                        e = e4;
                        o0VarX = null;
                    } catch (IllegalArgumentException e5) {
                        e = e5;
                        o0VarX = null;
                    } catch (Exception e6) {
                        e = e6;
                        o0VarX = null;
                    }
                }
                this.f112a.g = null;
            } finally {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "010");
            }
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "030");
        if (qVar != null) {
            if (o0VarX == null || nVarS.b() != 1 || nVarS.a() == null || !nVarS.a().equals("FeliCa chip is not opened yet.")) {
                com.felicanetworks.mfc.s1.a.d(3, "%s Doing the callback. type:%d, msg:%s", "031", Integer.valueOf(nVarS.b()), nVarS.a());
                qVar.h(nVarS.b(), nVarS.a());
            } else {
                com.felicanetworks.mfc.s1.a.b(7, "%s break call back.", "011");
            }
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    @Override // android.content.ServiceConnection
    public void onServiceDisconnected(ComponentName componentName) {
        q qVar;
        com.felicanetworks.mfc.s1.a.c(3, "%s %s", "000", componentName);
        synchronized (this.f112a) {
            if (this.f112a.e) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                qVar = this.f112a.b;
            } else {
                qVar = null;
            }
            this.f112a.B();
        }
        if (qVar != null) {
            com.felicanetworks.mfc.s1.a.e(3, "%s %s id:%d msg:%s", "002", "Client Listener Call", 1, "Unknown error.");
            qVar.h(1, "Unknown error.");
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
