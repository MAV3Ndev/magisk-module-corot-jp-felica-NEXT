package a.a.a.a.c;

import java.util.Timer;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
abstract class x {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f60a;
    private boolean b;
    private boolean c;
    private int d;
    private boolean e;
    private Timer f;
    final /* synthetic */ f0 g;

    private x(f0 f0Var) {
        this.g = f0Var;
        this.f60a = false;
        this.b = false;
        this.c = false;
    }

    private void h() {
        synchronized (this) {
            this.c = false;
        }
    }

    public boolean e() {
        while (true) {
            Thread.yield();
            synchronized (this) {
                if (!this.c) {
                    return this.b;
                }
            }
        }
    }

    public h0 f(String str, String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var, int i, boolean z) {
        h0 h0Var;
        try {
            Timer timer = new Timer(false);
            this.f = timer;
            j jVar = null;
            long j = i;
            timer.schedule(new w(this), j);
            while (true) {
                Thread.yield();
                synchronized (this) {
                    if (this.b) {
                        try {
                            this.e = true;
                            while (this.e) {
                                wait();
                            }
                            if (this.d == 0) {
                                this.b = false;
                                this.f60a = false;
                                this.f.cancel();
                                Timer timer2 = new Timer(false);
                                this.f = timer2;
                                timer2.schedule(new w(this), j);
                            } else if (this.d == 1) {
                                this.b = false;
                                this.f60a = false;
                                h0Var = new h0();
                                h0Var.e(str);
                                h0Var.f("0200");
                            } else {
                                this.b = false;
                                this.f60a = false;
                                h0Var = new h0();
                                h0Var.e(str);
                                h0Var.f("0100");
                            }
                        } catch (InterruptedException unused) {
                            if (!this.g.o.g().u()) {
                                throw new com.felicanetworks.mfw.a.cmn.c1(x.class, "execute");
                            }
                            com.felicanetworks.mfw.a.cmn.b0.v();
                            throw null;
                        }
                    } else {
                        try {
                            synchronized (this) {
                                this.c = true;
                            }
                            g(strArr, g0Var);
                            this.g.p = false;
                            this.f.purge();
                            synchronized (this) {
                                this.b = false;
                                this.c = false;
                            }
                            h();
                            return null;
                        } catch (com.felicanetworks.mfw.a.cmn.e e) {
                            try {
                                if (e.c() != 1) {
                                    synchronized (this) {
                                        this.c = false;
                                        this.b = false;
                                        throw e;
                                    }
                                }
                                synchronized (this) {
                                    this.c = false;
                                    if (this.f60a && !this.b) {
                                        this.f.purge();
                                        if (!z) {
                                            throw e;
                                        }
                                        this.g.f25a.d();
                                        synchronized (this) {
                                            try {
                                                this.e = true;
                                                while (this.e) {
                                                    wait();
                                                }
                                                if (this.d != 0) {
                                                    if (this.d == 1) {
                                                        synchronized (this) {
                                                            this.b = false;
                                                            this.f60a = false;
                                                            h0Var = new h0();
                                                            h0Var.e(str);
                                                            h0Var.f("0200");
                                                        }
                                                    } else {
                                                        synchronized (this) {
                                                            this.b = false;
                                                            this.f60a = false;
                                                            h0Var = new h0();
                                                            h0Var.e(str);
                                                            h0Var.f("0100");
                                                        }
                                                    }
                                                    h();
                                                    return h0Var;
                                                }
                                                synchronized (this) {
                                                    this.b = false;
                                                    this.f60a = false;
                                                    this.f.cancel();
                                                    Timer timer3 = new Timer(false);
                                                    this.f = timer3;
                                                    timer3.schedule(new w(this), j);
                                                }
                                            } catch (InterruptedException unused2) {
                                                if (!this.g.o.g().u()) {
                                                    throw new com.felicanetworks.mfw.a.cmn.c1(x.class, "execute");
                                                }
                                                com.felicanetworks.mfw.a.cmn.b0.v();
                                                throw null;
                                            }
                                        }
                                    }
                                    h();
                                }
                            } catch (Throwable th) {
                                h();
                                throw th;
                            }
                        }
                    }
                }
            }
        } finally {
            this.g.p = false;
            this.f.cancel();
        }
    }

    public abstract h0 g(String[] strArr, com.felicanetworks.mfw.a.cmn.g0 g0Var);

    public void i() {
        synchronized (this) {
            this.e = false;
            this.f60a = false;
            this.b = false;
        }
    }

    public void j(int i) {
        this.d = i;
        synchronized (this) {
            this.e = false;
            notifyAll();
        }
    }

    public void k() {
        synchronized (this) {
            this.b = true;
        }
        this.f.purge();
    }
}
