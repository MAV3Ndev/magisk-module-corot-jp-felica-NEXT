package com.felicanetworks.mfc.mfi;

import android.content.Intent;

/* JADX INFO: compiled from: MfiClient.java */
/* JADX INFO: loaded from: classes.dex */
class d0 extends q {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ j0 f101a;

    d0(j0 j0Var) {
        this.f101a = j0Var;
    }

    @Override // com.felicanetworks.mfc.mfi.r
    public void a(int i, String str) {
        com.felicanetworks.mfc.s1.a.a(3, "000");
        try {
            a aVarD = this.f101a.f108a.D();
            if (aVarD != null) {
                com.felicanetworks.mfc.s1.a.a(6, "001");
                try {
                    aVarD.a(i, str);
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.a(2, "700 " + e.getMessage());
                }
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.a(1, "800 " + e2.getMessage());
        }
        com.felicanetworks.mfc.s1.a.a(3, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.r
    public void b() {
        a aVarD;
        com.felicanetworks.mfc.s1.a.a(3, "000");
        a aVar = null;
        try {
            aVarD = this.f101a.f108a.D();
        } catch (Exception e) {
            e = e;
        }
        try {
            t0 t0Var = new t0(this.f101a.f108a);
            this.f101a.f108a.C(t0Var, null);
            if (aVarD == null || !(aVarD instanceof n0)) {
                com.felicanetworks.mfc.s1.a.a(6, "002");
            } else {
                com.felicanetworks.mfc.s1.a.a(6, "001");
                try {
                    ((n0) aVarD).d(t0Var);
                } catch (Exception e2) {
                    com.felicanetworks.mfc.s1.a.a(2, "700 " + e2.getMessage());
                }
            }
        } catch (Exception e3) {
            e = e3;
            aVar = aVarD;
            com.felicanetworks.mfc.s1.a.a(1, "800 " + e.getMessage());
            if (aVar != null) {
                try {
                    aVar.a(200, "Unknown error.");
                } catch (Exception unused) {
                    com.felicanetworks.mfc.s1.a.a(2, "701 " + e.getMessage());
                }
            }
        }
        com.felicanetworks.mfc.s1.a.a(3, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.r
    public void c(Intent intent) {
        a aVarD = null;
        try {
            aVarD = this.f101a.f108a.D();
            if (aVarD == null || !(aVarD instanceof n0)) {
                com.felicanetworks.mfc.s1.a.a(6, "002");
            } else {
                com.felicanetworks.mfc.s1.a.a(6, "001");
                try {
                    ((n0) aVarD).c(intent);
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.a(2, "700 " + e.getMessage());
                }
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.a(1, "800 " + e2.getMessage());
            if (aVarD != null) {
                try {
                    aVarD.a(200, "Unknown error.");
                } catch (Exception unused) {
                    com.felicanetworks.mfc.s1.a.a(2, "701 " + e2.getMessage());
                }
            }
        }
        com.felicanetworks.mfc.s1.a.a(3, "999");
    }
}
