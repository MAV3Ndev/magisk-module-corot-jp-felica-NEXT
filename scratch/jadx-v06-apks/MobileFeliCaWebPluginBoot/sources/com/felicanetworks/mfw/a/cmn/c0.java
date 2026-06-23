package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.DeviceList;
import com.felicanetworks.mfc.FSC;
import com.felicanetworks.mfc.Felica;
import java.util.ArrayList;

/* JADX INFO: compiled from: FSCStart.java */
/* JADX INFO: loaded from: classes.dex */
class c0 implements com.felicanetworks.mfc.q {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private g0 f146a;
    private boolean b;
    private boolean c;
    final /* synthetic */ d0 d;

    public c0(d0 d0Var, g0 g0Var) {
        this.d = d0Var;
        this.f146a = g0Var;
    }

    public synchronized void a(FSC fsc, Felica felica, String str) {
        try {
            felica.I(2);
            fsc.x(felica);
            fsc.v(new DeviceList());
            fsc.w(this);
            fsc.y(str);
            while (!this.c) {
                wait();
            }
        } catch (InterruptedException e) {
            try {
                fsc.z();
            } catch (com.felicanetworks.mfc.x unused) {
            }
            if (!this.d.f149a.g().u()) {
                throw new c1(getClass(), "start", e);
            }
            b0.v();
            throw null;
        }
    }

    @Override // com.felicanetworks.mfc.q
    public byte[] g(int i, String str, byte[] bArr) {
        synchronized (this) {
            this.b = true;
        }
        throw new RuntimeException();
    }

    @Override // com.felicanetworks.mfc.q
    public synchronized void h(int i, String str) {
        this.c = true;
        if (this.b) {
            this.f146a.a(new ArrayList());
        } else {
            this.f146a.a(null);
        }
        notify();
    }

    @Override // com.felicanetworks.mfc.q
    public synchronized void q(int i) {
        this.c = true;
        this.d.f149a.e().d();
        ArrayList arrayList = new ArrayList();
        arrayList.add(new i0("endcode", String.valueOf(i)));
        this.f146a.a(arrayList);
        notify();
    }
}
