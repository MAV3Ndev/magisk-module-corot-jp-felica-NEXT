package com.felicanetworks.mfc.mfi;

/* JADX INFO: compiled from: MfiClient.java */
/* JADX INFO: loaded from: classes.dex */
class i0 extends u {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ j0 f107a;

    i0(j0 j0Var) {
        this.f107a = j0Var;
    }

    @Override // com.felicanetworks.mfc.mfi.v
    public void a(int i, String str) {
        a aVarD = null;
        try {
            aVarD = this.f107a.f108a.D();
            if (aVarD instanceof r0) {
                com.felicanetworks.mfc.s1.a.a(6, "001");
                try {
                    ((r0) aVarD).a(i, str);
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.b(2, "700", e.getMessage());
                }
            } else {
                com.felicanetworks.mfc.s1.a.a(6, "002");
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.b(1, "800", e2.getMessage());
            if (aVarD != null) {
                try {
                    aVarD.a(200, "Unknown error.");
                } catch (Exception unused) {
                    com.felicanetworks.mfc.s1.a.b(2, "701", e2.getMessage());
                }
            }
        }
    }

    @Override // com.felicanetworks.mfc.mfi.v
    public void b() {
        com.felicanetworks.mfc.s1.a.a(3, "000");
        a aVarD = null;
        try {
            aVarD = this.f107a.f108a.D();
            if (aVarD == null || !(aVarD instanceof r0)) {
                com.felicanetworks.mfc.s1.a.a(6, "002");
            } else {
                com.felicanetworks.mfc.s1.a.a(6, "001");
                try {
                    ((r0) aVarD).b();
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.a(2, "700" + e.getMessage());
                }
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.b(1, "800", e2.getMessage());
            if (aVarD != null) {
                try {
                    aVarD.a(200, "Unknown error.");
                } catch (Exception unused) {
                    com.felicanetworks.mfc.s1.a.b(2, "701", e2.getMessage());
                }
            }
        }
        com.felicanetworks.mfc.s1.a.a(3, "999");
    }
}
