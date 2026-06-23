package com.felicanetworks.mfc.mfi;

import org.json.JSONException;

/* JADX INFO: compiled from: MfiClient.java */
/* JADX INFO: loaded from: classes.dex */
class h0 extends w {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ j0 f106a;

    h0(j0 j0Var) {
        this.f106a = j0Var;
    }

    @Override // com.felicanetworks.mfc.mfi.x
    public void A(boolean z, String str) {
        a aVarD;
        com.felicanetworks.mfc.s1.a.a(3, "000");
        a aVar = null;
        a aVar2 = null;
        try {
            aVarD = this.f106a.f108a.D();
        } catch (JSONException e) {
            e = e;
        } catch (Exception e2) {
            e = e2;
        }
        try {
            if (aVarD instanceof s0) {
                com.felicanetworks.mfc.s1.a.a(6, "001");
                try {
                    ((s0) aVarD).e(z, str != null ? new a0(str).a() : null);
                } catch (Exception e3) {
                    com.felicanetworks.mfc.s1.a.b(2, "700", e3.getMessage());
                }
            } else {
                com.felicanetworks.mfc.s1.a.a(6, "002");
            }
        } catch (JSONException e4) {
            e = e4;
            aVar2 = aVarD;
            com.felicanetworks.mfc.s1.a.b(1, "800", e.getMessage());
            if (aVar2 != null) {
                try {
                    aVar2.a(200, "Json parse error.");
                } catch (Exception unused) {
                    com.felicanetworks.mfc.s1.a.b(2, "701", e.getMessage());
                }
            }
        } catch (Exception e5) {
            e = e5;
            aVar = aVarD;
            com.felicanetworks.mfc.s1.a.b(1, "801", e.getMessage());
            if (aVar != null) {
                try {
                    aVar.a(200, "Unknown error.");
                } catch (Exception unused2) {
                    com.felicanetworks.mfc.s1.a.b(2, "701", e.getMessage());
                }
            }
        }
        com.felicanetworks.mfc.s1.a.a(3, "999");
    }

    @Override // com.felicanetworks.mfc.mfi.x
    public void a(int i, String str) {
        a aVarD = null;
        try {
            aVarD = this.f106a.f108a.D();
            if (aVarD instanceof s0) {
                com.felicanetworks.mfc.s1.a.a(6, "001");
                try {
                    ((s0) aVarD).a(i, str);
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
}
