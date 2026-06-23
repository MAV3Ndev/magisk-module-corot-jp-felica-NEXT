package com.felicanetworks.mfc;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Looper;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: MfiClientAccess.java */
/* JADX INFO: loaded from: classes.dex */
public class x0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f123a;
    private w b;
    private q c;
    private com.felicanetworks.mfc.mfi.a d;
    private com.felicanetworks.mfc.mfi.a e;
    private w0 f = new w0(this);
    private com.felicanetworks.mfc.mfi.n g = null;
    private q0 h = new v0(this);
    private u0 i = new u0(this, Looper.myLooper());
    private WeakReference j;

    x0(Felica felica) {
        this.j = null;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        this.j = new WeakReference(felica);
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean s() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (this.g != null || this.b == null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            return false;
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
        return true;
    }

    public synchronized boolean A() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return this.c != null;
    }

    public synchronized void B(q qVar) {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        this.c = qVar;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public synchronized void C(com.felicanetworks.mfc.mfi.t0 t0Var, com.felicanetworks.mfc.mfi.b0 b0Var) {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public synchronized com.felicanetworks.mfc.mfi.a D() {
        com.felicanetworks.mfc.mfi.a aVar;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        aVar = this.d;
        this.d = null;
        this.e = null;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return aVar;
    }

    protected void E() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        try {
            try {
                Felica felica = (Felica) this.j.get();
                if (felica != null) {
                    com.felicanetworks.mfc.s1.a.b(6, "%s", "001");
                    felica.unbindService(this.f);
                } else {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Failed to unbindMfiClient. felica is null.");
                }
            } catch (Exception unused) {
                com.felicanetworks.mfc.s1.a.b(6, "%s", "002");
            }
            u();
            com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        } catch (Throwable th) {
            u();
            throw th;
        }
    }

    synchronized void o(String str, w wVar) {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (wVar == null) {
            throw new IllegalArgumentException("The specified Listener is null.");
        }
        t();
        this.f123a = str;
        this.b = wVar;
        try {
            p();
            com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        } catch (Exception unused) {
            com.felicanetworks.mfc.s1.a.b(2, "%s can not bind to MFI", "700");
            this.b = null;
            throw new x(1, 47, "can not bind to MFI");
        }
    }

    protected void p() throws x {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.felicanetworks.mfm.main", "com.felicanetworks.mfc.mfi.FelicaAdapter"));
        Felica felica = (Felica) this.j.get();
        if (felica == null) {
            com.felicanetworks.mfc.s1.a.c(3, "%s %s", "700", "Failed to connect for MFI Client Service. felica is null.");
            throw new x(1, 47);
        }
        if (!r1.b(felica, "BE:51:DB:F4:FE:C8:9B:D3:28:46:45:7B:13:B7:30:08:76:AF:55:94:D2:87:4D:EE:02:69:04:96:5A:E4:A6:CB", "com.felicanetworks.mfm.main")) {
            com.felicanetworks.mfc.s1.a.c(3, "%s %s", "701", "Failed to connect for MFI Client Service. AppCertHash check failed.");
            throw new x(1, 47);
        }
        if (felica.bindService(intent, this.f, 1)) {
            this.i.a(10000);
            com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        } else {
            com.felicanetworks.mfc.s1.a.c(3, "%s %s", "702", "Failed to connect for MFI Client Service");
            felica.unbindService(this.f);
            throw new x(1, 47);
        }
    }

    public void q() throws com.felicanetworks.mfc.mfi.k0 {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (((Felica) this.j.get()) == null || this.g == null || this.b != null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            throw new com.felicanetworks.mfc.mfi.k0(2, 152, null);
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    boolean r() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (this.g == null && this.b == null) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            return false;
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
        return true;
    }

    void t() throws x {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (this.b != null) {
            throw new x(2, 49);
        }
        if (this.g != null) {
            throw new x(2, 42);
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    protected void u() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        this.f123a = null;
        this.g = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.i.b();
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public synchronized void v() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        this.c = null;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public synchronized void w() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    void x() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        try {
            synchronized (this) {
                com.felicanetworks.mfc.s1.a.b(6, "%s", "001");
                try {
                    if (this.g != null) {
                        com.felicanetworks.mfc.s1.a.b(6, "%s", "002");
                        this.g.m();
                        this.g.r();
                    }
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(6, "%s %s", "003", e.getMessage());
                }
                E();
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(6, "%s %s", "004", e2.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    public synchronized com.felicanetworks.mfc.mfi.n y() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
        return this.g;
    }

    synchronized void z() {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (r()) {
            if (s()) {
                E();
                return;
            }
            try {
                t0.a(this.g.r());
                E();
                com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
            } catch (x e) {
                com.felicanetworks.mfc.s1.a.e(2, "%s %s id:%d type:%d", "700", e.toString(), Integer.valueOf(e.a()), Integer.valueOf(e.e()));
                if (e.a() != 2 || e.e() != 5) {
                    throw e;
                }
                com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            } catch (Exception e2) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Other Exception");
                com.felicanetworks.mfc.s1.a.i(7, e2);
                throw new x(1, 47);
            }
        }
    }
}
