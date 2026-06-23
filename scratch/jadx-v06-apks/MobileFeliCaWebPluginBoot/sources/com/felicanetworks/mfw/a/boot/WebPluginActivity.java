package com.felicanetworks.mfw.a.boot;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import com.felicanetworks.mfw.a.cmn.h0;
import com.felicanetworks.mfw.a.cmn.w0;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class WebPluginActivity extends Applet {
    private h0 l;
    private boolean j = false;
    Thread k = null;
    private int m = 0;
    private Intent n = null;

    @Override // com.felicanetworks.mfw.a.boot.Applet
    public void d() {
        try {
            this.i = true;
            if (!this.j) {
                this.k.interrupt();
                this.l.f().f();
            }
        } catch (Exception unused) {
        } catch (Throwable th) {
            n.d(this);
            throw th;
        }
        n.d(this);
    }

    @Override // com.felicanetworks.mfw.a.boot.Applet
    public void e(Exception exc) {
        synchronized (this) {
            try {
                w0.l("3", new o(this.l.f()).a(exc), this, this.l.g().h());
                n(1);
                setIntent(null);
            } catch (Exception unused) {
                n(1);
                setIntent(null);
            } catch (Throwable th) {
                n(1);
                setIntent(null);
                finish();
                throw th;
            }
            finish();
        }
    }

    @Override // com.felicanetworks.mfw.a.boot.Applet, android.app.Activity
    public void finish() {
        setResult(this.m, this.n);
        super.finish();
    }

    public void k(int i) {
        n(i);
    }

    public void l(int i, String str) {
        n(i);
        Intent intent = new Intent();
        intent.setData(Uri.parse(str));
        setIntent(intent);
    }

    public void m(int i, List list) {
        n(i);
        Intent intent = new Intent();
        intent.setData(null);
        intent.putExtra("com.felicanetworks.mfw.a.main.CONFLICT_LIST", list != null ? (String[]) list.toArray(new String[0]) : new String[0]);
        setIntent(intent);
    }

    public void n(int i) {
        this.m = i;
    }

    @Override // com.felicanetworks.mfw.a.boot.Applet, android.app.Activity
    public void onCreate(Bundle bundle) {
        try {
            super.g(bundle);
            n.a(this);
            requestWindowFeature(1);
            h0 h0Var = new h0();
            this.l = h0Var;
            h0Var.g().t(this);
            synchronized (n.class) {
                if (!n.c()) {
                    if (!isTaskRoot()) {
                        Thread thread = new Thread(this.l.f());
                        this.k = thread;
                        thread.start();
                        return;
                    } else {
                        this.j = true;
                        this.m = 6220;
                        this.n = null;
                        finish();
                        return;
                    }
                }
                this.j = true;
                if (n.b(this)) {
                    this.m = 6833;
                    this.n = null;
                } else {
                    Intent intent = new Intent();
                    intent.setData(null);
                    intent.putExtra("com.felicanetworks.mfw.a.main.CONFLICT_LIST", new String[]{""});
                    this.m = 6233;
                    this.n = intent;
                }
                finish();
            }
        } catch (Exception e) {
            e(e);
        }
    }

    @Override // com.felicanetworks.mfw.a.boot.Applet, android.app.Activity
    public void onPause() {
        super.h();
    }

    @Override // com.felicanetworks.mfw.a.boot.Applet, android.app.Activity
    public void onResume() {
        try {
            super.i();
            if (this.b) {
                this.b = false;
                this.f124a.f(false);
                synchronized (this.f124a) {
                    this.f124a.notify();
                }
            }
        } catch (Exception e) {
            e(e);
        }
    }

    @Override // android.app.Activity
    public void setIntent(Intent intent) {
        this.n = intent;
    }
}
