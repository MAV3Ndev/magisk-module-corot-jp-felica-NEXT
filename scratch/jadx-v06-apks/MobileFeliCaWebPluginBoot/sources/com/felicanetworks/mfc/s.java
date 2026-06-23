package com.felicanetworks.mfc;

/* JADX INFO: compiled from: Felica.java */
/* JADX INFO: loaded from: classes.dex */
class s extends p0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ Felica f115a;

    s(Felica felica) {
        this.f115a = felica;
    }

    @Override // com.felicanetworks.mfc.q0
    public void v(int i, String str, AppInfo appInfo) {
        w wVar;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        synchronized (this.f115a) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
            wVar = this.f115a.d;
            this.f115a.d = null;
            try {
                this.f115a.K();
            } catch (Exception e) {
                com.felicanetworks.mfc.s1.a.c(1, "%s %s", "900", e.getMessage());
            }
        }
        if (wVar != null) {
            try {
                com.felicanetworks.mfc.s1.a.e(7, "%s %s %d %s", "002", "FelicaEventListener#errorOccurred", Integer.valueOf(i), str);
                if (appInfo != null) {
                    com.felicanetworks.mfc.s1.a.d(3, "%s %s %d", "003", "FelicaEventListener#errorOccurred", Integer.valueOf(appInfo.a()));
                }
                wVar.v(i, str, appInfo);
            } catch (Exception e2) {
                com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", e2.getMessage());
            }
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    @Override // com.felicanetworks.mfc.q0
    public void w() {
        w wVar;
        com.felicanetworks.mfc.s1.a.c(3, "%s %s", "000", "FelicaEventListener#finished");
        try {
            synchronized (this.f115a) {
                wVar = null;
                if (this.f115a.d != null) {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "001");
                    w wVar2 = this.f115a.d;
                    this.f115a.d = null;
                    wVar = wVar2;
                } else {
                    com.felicanetworks.mfc.s1.a.b(7, "%s", "002");
                    this.f115a.K();
                }
            }
            if (wVar != null) {
                try {
                    com.felicanetworks.mfc.s1.a.b(3, "%s", "003");
                    wVar.w();
                } catch (Exception e) {
                    com.felicanetworks.mfc.s1.a.c(2, "%s %s", "700", e.getMessage());
                }
            }
        } catch (Exception e2) {
            com.felicanetworks.mfc.s1.a.c(1, "%s %s", "900", e2.getMessage());
        }
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
