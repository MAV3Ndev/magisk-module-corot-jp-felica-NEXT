package com.felicanetworks.mfc.mfi;

/* JADX INFO: compiled from: MfiClient.java */
/* JADX INFO: loaded from: classes.dex */
class e0 extends j {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ j0 f102a;

    e0(j0 j0Var) {
        this.f102a = j0Var;
    }

    @Override // com.felicanetworks.mfc.mfi.k
    public void a(int i, String str) {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        try {
            a aVarD = this.f102a.f108a.D();
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

    @Override // com.felicanetworks.mfc.mfi.k
    public void b() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        a aVarD = null;
        try {
            aVarD = this.f102a.f108a.D();
            this.f102a.f108a.w();
            if (aVarD == null || !(aVarD instanceof q0)) {
                com.felicanetworks.mfc.s1.a.b(6, "%s", "002");
            } else {
                com.felicanetworks.mfc.s1.a.b(6, "%s", "001");
                try {
                    ((q0) aVarD).b();
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", e.getMessage());
                }
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(1, "%s %s", "800", e2.getMessage());
            if (aVarD != null) {
                try {
                    aVarD.a(200, "Unknown error.");
                } catch (Exception unused) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", e2.getMessage());
                }
            }
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
