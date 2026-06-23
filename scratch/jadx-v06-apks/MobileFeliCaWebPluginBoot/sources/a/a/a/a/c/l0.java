package a.a.a.a.c;

import android.os.Handler;
import android.os.Looper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: Ctrler.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class l0 implements Runnable {
    private static String i = "instanceDataStakLock";
    private static String j = "eventQueueLock";
    private com.felicanetworks.mfw.a.cmn.a1 c;
    private n0 e;
    protected com.felicanetworks.mfw.a.cmn.h0 f;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.felicanetworks.mfw.a.cmn.w f38a = new com.felicanetworks.mfw.a.cmn.w();
    private com.felicanetworks.mfw.a.cmn.r0 b = new com.felicanetworks.mfw.a.cmn.r0();
    private List d = new ArrayList();
    private Handler g = new i0(this, Looper.getMainLooper());
    private boolean h = false;

    /* JADX INFO: Access modifiers changed from: private */
    public void d() {
        synchronized (this.d) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ((u1) it.next()).i();
            }
            this.d.clear();
        }
    }

    private u1 g() {
        synchronized (i) {
            int iC = this.f38a.c();
            for (int i2 = 0; i2 < iC; i2++) {
                s1 s1Var = (s1) this.f38a.d();
                if (s1Var.d()) {
                    u1 u1Var = (u1) s1Var;
                    if (u1Var.j()) {
                        return u1Var;
                    }
                }
                s1 s1Var2 = (s1) this.f38a.e();
                s1Var2.b();
                if (s1Var2.d()) {
                    synchronized (this.d) {
                        this.d.add((u1) s1Var2);
                    }
                }
            }
            return null;
        }
    }

    private void p(s1 s1Var) {
        s1 s1Var2;
        synchronized (i) {
            s1 s1Var3 = (s1) this.f38a.d();
            if (s1Var3 == null) {
                this.f38a.f(s1Var);
                return;
            }
            s1 s1Var4 = null;
            if (s1Var3.d() || !s1Var.d()) {
                s1Var2 = (s1Var3.d() || s1Var.d()) ? null : (s1) this.f38a.e();
            } else {
                s1 s1Var5 = (s1) this.f38a.e();
                this.c = s1Var5;
                s1Var4 = s1Var5;
                s1Var2 = null;
            }
            this.f38a.f(s1Var);
            if (s1Var4 != null) {
                s1Var4.h();
            } else if (s1Var2 != null) {
                s1Var2.b();
            }
        }
    }

    public void b(s1 s1Var, Object obj, Object obj2) {
        s1 s1Var2;
        synchronized (i) {
            p(s1Var);
            s1Var2 = (!s1Var.d() || this.f38a.c() < 2) ? null : (s1) this.f38a.b(this.f38a.c() - 2);
        }
        if (s1Var2 != null) {
            s1Var2.h();
        }
        s1Var.g(obj, obj2);
    }

    public void c(s1 s1Var, Object obj, Object obj2, boolean z) {
        if (!z) {
            b(s1Var, obj, obj2);
            return;
        }
        p(s1Var);
        n0 n0Var = new n0();
        n0Var.g(28928);
        n0Var.h(s1Var.c());
        n0Var.e(obj);
        n0Var.f(obj2);
        synchronized (j) {
            this.b.d(n0Var);
        }
    }

    public void e() {
        this.f38a.a(20);
        this.b.a(20);
        m("03", 28672, 0, null, true);
        while (!this.h) {
            Thread.yield();
            n0 n0Var = null;
            synchronized (j) {
                if (this.b.b() > 0) {
                    n0Var = (n0) this.b.f();
                    this.e = n0Var;
                }
            }
            if (n0Var != null) {
                l(n0Var);
            }
        }
        synchronized (i) {
            for (int i2 = 0; i2 < this.f38a.c(); i2++) {
                ((s1) this.f38a.b(i2)).b();
            }
            this.f38a.g();
        }
    }

    public void f() {
        synchronized (i) {
            for (int i2 = 0; i2 < this.f38a.c(); i2++) {
                try {
                    if (this.f38a.b(i2) instanceof u1) {
                        ((u1) this.f38a.b(i2)).i();
                    }
                } catch (Exception unused) {
                }
            }
        }
        this.h = true;
        this.g.removeMessages(0);
    }

    public n0 h() {
        return this.e;
    }

    public s1 i(int i2) {
        return (s1) this.f38a.b(i2);
    }

    public int j() {
        try {
            return this.f38a.c();
        } catch (com.felicanetworks.mfw.a.cmn.c1 unused) {
            return 0;
        }
    }

    public u1 k() {
        synchronized (i) {
            try {
                try {
                    for (int iC = this.f38a.c() - 1; iC >= 0; iC--) {
                        s1 s1Var = (s1) this.f38a.b(iC);
                        if (s1Var.d()) {
                            return (u1) s1Var;
                        }
                    }
                    return null;
                } catch (com.felicanetworks.mfw.a.cmn.c1 unused) {
                    return null;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void l(n0 n0Var) {
        s1 s1Var;
        s1 s1Var2;
        s1 s1Var3;
        s1 s1Var4;
        s1 s1Var5;
        String strD = n0Var.d();
        int i2 = 0;
        if (strD.equals("03")) {
            if (n0Var.c() == 28672) {
                synchronized (i) {
                    int iC = this.f38a.c();
                    while (i2 < iC) {
                        ((s1) this.f38a.e()).b();
                        i2++;
                    }
                }
                q(((Integer) n0Var.a()).intValue());
                return;
            }
            return;
        }
        s1 s1Var6 = null;
        if (n0Var.c() == 28928) {
            synchronized (i) {
                s1Var5 = (s1) this.f38a.d();
                if (s1Var5.d() && this.f38a.c() >= 2) {
                    s1Var6 = (s1) this.f38a.b(this.f38a.c() - 2);
                }
            }
            if (s1Var6 != null) {
                s1Var6.h();
            }
            s1Var5.g(n0Var.a(), n0Var.b());
            return;
        }
        if (n0Var.c() == 28929) {
            synchronized (i) {
                s1Var3 = (s1) this.f38a.e();
                s1Var4 = (s1) this.f38a.d();
            }
            s1Var3.b();
            if (s1Var3.d()) {
                synchronized (this.d) {
                    this.d.add((u1) s1Var3);
                }
            }
            s1Var4.e(s1Var3.c(), n0Var.a(), n0Var.b());
            return;
        }
        if (n0Var.c() == 28932) {
            s1 s1Var7 = (s1) this.f38a.e();
            s1Var7.b();
            if (s1Var7.d()) {
                synchronized (this.d) {
                    this.d.add((u1) s1Var7);
                }
            }
            g().e(s1Var7.c(), n0Var.a(), n0Var.b());
            return;
        }
        if (n0Var.c() == 28673) {
            synchronized (i) {
                s1Var = (s1) this.f38a.e();
                s1Var2 = (s1) this.f38a.d();
            }
            this.c = s1Var;
            s1Var.h();
            s1Var2.e(s1Var.c(), null, null);
            return;
        }
        if (n0Var.c() == 28674) {
            synchronized (i) {
                this.f38a.f(this.c);
            }
            ((s1) this.c).e(n0Var.d(), n0Var.a(), n0Var.b());
            return;
        }
        if (n0Var.c() == 28675) {
            ((s1) this.c).b();
            this.c = null;
            return;
        }
        synchronized (i) {
            while (i2 < this.f38a.c()) {
                s1 s1Var8 = (s1) this.f38a.b(i2);
                if (strD.equals(s1Var8.c())) {
                    s1Var6 = s1Var8;
                }
                i2++;
            }
        }
        s1Var6.a(n0Var.c(), n0Var.a(), n0Var.b());
    }

    public void m(String str, int i2, Object obj, Object obj2, boolean z) {
        synchronized (i) {
            if ("00".equals(str)) {
                str = ((s1) this.f38a.b(this.f38a.c() - 2)).c();
            } else if ("01".equals(str)) {
                str = ((s1) this.f38a.b(this.f38a.c() - 1)).c();
            } else if ("02".equals(str)) {
                str = ((s1) this.f38a.b(0)).c();
            }
        }
        n0 n0Var = new n0();
        n0Var.g(i2);
        n0Var.h(str);
        n0Var.e(obj);
        n0Var.f(obj2);
        if (!z) {
            this.e = n0Var;
            l(n0Var);
        } else {
            synchronized (j) {
                this.b.d(n0Var);
            }
        }
    }

    public void n(a.a.a.a.a.v vVar, String str, String str2) {
        this.g.post(new k0(this, vVar, str, str2));
    }

    public void o(u1 u1Var) {
        this.g.post(new j0(this, u1Var));
    }

    public abstract void q(int i2);

    @Override // java.lang.Runnable
    public void run() {
        this.h = false;
        try {
            e();
        } catch (Exception e) {
            try {
                this.f.g().r(e);
            } catch (Exception unused) {
            }
            for (int i2 = 0; i2 < this.f38a.c(); i2++) {
                try {
                    ((s1) this.f38a.b(i2)).b();
                } catch (Exception unused2) {
                }
            }
            this.f38a.g();
        }
        try {
            this.f.a().e();
        } catch (Exception unused3) {
        }
        try {
            this.f.g().f();
        } catch (Exception unused4) {
        }
        try {
            this.f.b().d();
        } catch (Exception unused5) {
        }
    }
}
