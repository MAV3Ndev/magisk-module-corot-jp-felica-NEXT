package com.felicanetworks.mfc;

/* JADX INFO: compiled from: MfiClientAccess.java */
/* JADX INFO: loaded from: classes.dex */
class v0 extends p0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ x0 f120a;

    v0(x0 x0Var) {
        this.f120a = x0Var;
    }

    @Override // com.felicanetworks.mfc.q0
    public void v(int i, String str, AppInfo appInfo) {
        w wVar;
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        synchronized (this.f120a) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            wVar = this.f120a.b;
            this.f120a.b = null;
            try {
                this.f120a.E();
            } catch (Exception e) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", e.getMessage());
            }
        }
        if (wVar != null) {
            try {
                com.felicanetworks.mfc.s1.a.e(7, "%s %s %d %s", "002", "FelicaEventListener#errorOccurred", Integer.valueOf(i), str);
                if (appInfo != null) {
                    com.felicanetworks.mfc.s1.a.d(7, "%s %s %d", "003", "FelicaEventListener#errorOccurred", Integer.valueOf(appInfo.a()));
                }
                wVar.v(i, str, appInfo);
            } catch (Exception e2) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", e2.getMessage());
            }
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.q0
    public void w() {
        w wVar;
        com.felicanetworks.mfc.s1.a.c(7, "%s %s", "000", "FelicaEventListener#finished");
        try {
            synchronized (this.f120a) {
                wVar = null;
                if (this.f120a.b != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    w wVar2 = this.f120a.b;
                    this.f120a.b = null;
                    wVar = wVar2;
                } else {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                    this.f120a.E();
                }
            }
            if (wVar != null) {
                try {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "003");
                    wVar.w();
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", e.getMessage());
                }
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", e2.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
    }
}
