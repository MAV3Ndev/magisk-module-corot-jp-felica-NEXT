package com.felicanetworks.mfw.a.boot;

import a.a.a.a.c.u1;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.felicanetworks.mfw.a.cmn.b0;
import com.felicanetworks.mfw.a.cmn.c1;
import com.felicanetworks.mfw.a.cmn.h0;
import com.felicanetworks.mfw.a.cmn.w0;
import com.felicanetworks.mfw.a.cmn.x0;

/* JADX INFO: loaded from: classes.dex */
public class Applet extends Activity implements x0 {
    private h0 g;

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    protected d f124a = null;
    protected boolean b = false;
    private u c = null;
    private m d = null;
    private boolean e = true;
    Thread f = null;
    private boolean h = false;
    protected volatile boolean i = false;

    @Override // com.felicanetworks.mfw.a.cmn.x0
    public void a() {
    }

    @Override // com.felicanetworks.mfw.a.cmn.x0
    public void b(byte[] bArr, int i) {
    }

    protected void d() {
        b0 b0VarG;
        try {
            try {
                this.i = true;
                if (!this.e && this.f != null) {
                    this.f.interrupt();
                    this.g.f().f();
                }
                if (this.d != null) {
                    this.d.d();
                }
                if (this.c != null) {
                    this.c.d();
                }
            } catch (Exception unused) {
            }
        } catch (Exception unused2) {
            if (!this.e) {
                b0VarG = this.g.g();
            }
        } catch (Throwable th) {
            try {
                if (!this.e) {
                    this.g.g().a();
                }
            } catch (Exception unused3) {
            }
            n.d(this);
            throw th;
        }
        if (!this.e) {
            b0VarG = this.g.g();
            b0VarG.a();
        }
        n.d(this);
    }

    public void e(Exception exc) {
        u uVar;
        synchronized (this) {
            if (this.c == null || !this.c.e()) {
                try {
                    w0.l("3", new o(this.g.f()).a(exc), this, this.g.g().h());
                    uVar = this.c;
                } catch (Exception unused) {
                    uVar = this.c;
                } catch (Throwable th) {
                    this.c.f();
                    throw th;
                }
                uVar.f();
            }
        }
    }

    public boolean f() {
        return this.i;
    }

    @Override // android.app.Activity
    public void finish() {
        synchronized (this) {
            if (isFinishing()) {
                return;
            }
            super.finish();
            d();
        }
    }

    protected void g(Bundle bundle) {
        super.onCreate(bundle);
    }

    protected void h() {
        super.onPause();
    }

    protected void i() {
        super.onResume();
    }

    public d j(Intent intent, int i) {
        this.b = true;
        this.f124a = new d(this, i);
        try {
            startActivityForResult(intent, i);
            try {
                synchronized (this.f124a) {
                    while (this.b) {
                        this.f124a.wait();
                    }
                }
                return this.f124a;
            } catch (InterruptedException e) {
                throw new c1(getClass(), "fail waiting at #startActivityWithResult", e);
            }
        } catch (ActivityNotFoundException e2) {
            this.b = false;
            throw e2;
        }
    }

    @Override // android.app.Activity
    protected void onActivityResult(int i, int i2, Intent intent) {
        d dVar = this.f124a;
        if (dVar == null) {
            return;
        }
        try {
            this.b = false;
            if (dVar.c() == i) {
                this.f124a.c = i2;
                this.f124a.f129a = true;
            } else {
                this.f124a.f129a = false;
            }
            synchronized (this.f124a) {
                this.f124a.notify();
            }
        } catch (Exception e) {
            e(e);
        }
    }

    @Override // android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.onCreate(bundle);
            n.a(this);
            requestWindowFeature(1);
            h0 h0Var = new h0();
            this.g = h0Var;
            this.c = new u(this, h0Var);
            this.g.g().s(this);
            synchronized (n.class) {
                if (n.c()) {
                    if (n.b(this)) {
                        this.d = new m(this.g, 6833, null);
                    } else {
                        this.d = new m(this.g, 6233, null);
                    }
                    new Handler(Looper.getMainLooper()).post(new b(this));
                    return;
                }
                if (!isTaskRoot()) {
                    this.d = new m(this.g, 6832, null);
                    new Handler(Looper.getMainLooper()).post(new c(this));
                    return;
                }
                this.g.g().A();
                this.h = true;
                this.e = false;
                Thread thread = new Thread(this.g.f());
                this.f = thread;
                thread.start();
            }
        } catch (Exception e) {
            e(e);
        }
    }

    @Override // android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        d();
    }

    @Override // android.app.Activity
    public void onPause() {
        u uVar;
        u1 u1VarK;
        super.onPause();
        if (isFinishing()) {
            return;
        }
        if (this.e || ((uVar = this.c) != null && uVar.e())) {
            finish();
            return;
        }
        String strC = null;
        if (!this.e && (u1VarK = this.g.f().k()) != null) {
            strC = u1VarK.c();
        }
        if ("D013".equals(strC) || "D012".equals(strC) || "D027".equals(strC) || "D028".equals(strC)) {
            finish();
        } else {
            this.h = false;
        }
    }

    @Override // android.app.Activity
    public void onResume() {
        try {
            super.onResume();
            if (this.b) {
                this.b = false;
                this.f124a.f129a = false;
                synchronized (this.f124a) {
                    this.f124a.notify();
                }
            }
            if (!this.e && !this.h) {
                this.g.g().A();
            }
            this.h = false;
        } catch (Exception e) {
            e(e);
        }
    }
}
