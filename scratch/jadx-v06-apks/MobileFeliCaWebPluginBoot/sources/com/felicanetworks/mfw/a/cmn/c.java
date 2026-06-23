package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.AppInfo;
import com.felicanetworks.mfc.Felica;
import java.util.List;

/* JADX INFO: compiled from: Activator.java */
/* JADX INFO: loaded from: classes.dex */
class c implements com.felicanetworks.mfc.w {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private boolean f145a;
    private int b;
    private AppInfo c;
    private boolean d;
    final /* synthetic */ d e;

    private c(d dVar) {
        this.e = dVar;
    }

    public synchronized void a(Felica felica, List list) {
        try {
            felica.j((String[]) list.toArray(new String[0]), this);
            while (!this.d) {
                wait();
            }
        } catch (InterruptedException e) {
            if (!this.e.f148a.g().u()) {
                throw new c1(getClass(), "activate", e);
            }
            b0.v();
            throw null;
        }
    }

    public int b() {
        return this.b;
    }

    public AppInfo c() {
        return this.c;
    }

    public boolean d() {
        return this.f145a;
    }

    @Override // com.felicanetworks.mfc.w
    public synchronized void v(int i, String str, AppInfo appInfo) {
        this.f145a = true;
        this.d = true;
        this.b = i;
        this.c = appInfo;
        notify();
    }

    @Override // com.felicanetworks.mfc.w
    public synchronized void w() {
        this.d = true;
        notify();
    }
}
