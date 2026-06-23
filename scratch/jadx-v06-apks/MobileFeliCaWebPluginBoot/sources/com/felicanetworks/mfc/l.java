package com.felicanetworks.mfc;

/* JADX INFO: compiled from: FSC.java */
/* JADX INFO: loaded from: classes.dex */
class l extends k0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ FSC f93a;

    l(FSC fsc) {
        this.f93a = fsc;
    }

    @Override // com.felicanetworks.mfc.l0
    public void g(int i, String str, byte[] bArr) {
        com.felicanetworks.mfc.s1.a.e(3, "%s DIB:%d param:%s data:%s", "020", Integer.valueOf(i), str, bArr);
        try {
            this.f93a.b.g(i, str, bArr);
            throw null;
        } catch (Exception e) {
            com.felicanetworks.mfc.s1.a.d(3, "%s %s:%s", "020", "Client operation is failed", e.getMessage());
            try {
                if (this.f93a.u()) {
                    this.f93a.f77a.z().y().f(e.getMessage());
                } else {
                    this.f93a.i.f(e.getMessage());
                }
            } catch (Exception unused) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "Remote Access failed");
            }
            com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
        }
    }

    @Override // com.felicanetworks.mfc.l0
    public void h(int i, String str) {
        q qVar;
        com.felicanetworks.mfc.s1.a.d(3, "%s type:%d, message:%s", "000", Integer.valueOf(i), str);
        try {
            synchronized (this.f93a) {
                if (this.f93a.b != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    qVar = this.f93a.b;
                } else {
                    qVar = null;
                }
                this.f93a.A();
            }
            if (qVar == null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            } else {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
                if (i == 100) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "004");
                } else {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "005");
                    qVar.h(i, str);
                }
            }
        } catch (Exception e) {
            com.felicanetworks.mfc.s1.a.c(2, "%s msg:%s", "700", e.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.l0
    public void q(int i) {
        q qVar;
        com.felicanetworks.mfc.s1.a.c(3, "%s status:%d", "000", Integer.valueOf(i));
        try {
            synchronized (this.f93a) {
                if (this.f93a.b != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    qVar = this.f93a.b;
                } else {
                    qVar = null;
                }
                this.f93a.A();
            }
            if (qVar == null) {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
            } else {
                com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
                qVar.q(i);
            }
        } catch (Exception e) {
            com.felicanetworks.mfc.s1.a.c(2, "%s msg:%s", "700", e.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
