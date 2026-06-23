package com.felicanetworks.mfc.mfi;

/* JADX INFO: compiled from: MfiClient.java */
/* JADX INFO: loaded from: classes.dex */
class c0 extends h {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ j0 f100a;

    c0(j0 j0Var) {
        this.f100a = j0Var;
    }

    @Override // com.felicanetworks.mfc.mfi.i
    public void a(int i, String str) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            a aVarD = this.f100a.f108a.D();
            if (aVarD != null) {
                com.felicanetworks.mfc.s1.a.b(6, "%s", "001");
                try {
                    aVarD.a(i, str);
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", e.getMessage());
                }
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(1, "%s %s", "800", e2.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.mfi.i
    public void b() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        a aVar = null;
        try {
            a aVarD = this.f100a.f108a.D();
            try {
                t0 t0Var = new t0(this.f100a.f108a);
                this.f100a.f108a.C(t0Var, null);
                if (aVarD == null || !(aVarD instanceof p0)) {
                    com.felicanetworks.mfc.s1.a.b(6, "%s", "002");
                } else {
                    com.felicanetworks.mfc.s1.a.b(6, "%s", "001");
                    try {
                        ((p0) aVarD).d(t0Var);
                    } catch (Exception e) {
                        com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", e.getMessage());
                    }
                }
            } catch (Exception e2) {
                e = e2;
                aVar = aVarD;
                com.felicanetworks.mfc.s1.a.c(1, "%s %s", "800", e.getMessage());
                if (aVar != null) {
                    try {
                        aVar.a(200, "Unknown error.");
                    } catch (Exception unused) {
                        com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", e.getMessage());
                    }
                }
            }
        } catch (Exception e3) {
            e = e3;
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
