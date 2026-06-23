package com.felicanetworks.mfw.a.cmn;

import java.util.ArrayList;
import java.util.Locale;

/* JADX INFO: compiled from: RequestService.java */
/* JADX INFO: loaded from: classes.dex */
public class t0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private h0 f177a;

    public t0(h0 h0Var) {
        this.f177a = h0Var;
    }

    private static boolean b(com.felicanetworks.mfc.x xVar) {
        return xVar.a() == 4 && xVar.e() == 11;
    }

    public void a(int i, g0 g0Var) throws e {
        try {
            int iY = this.f177a.e().c().y(i);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new i0("keyversion", b1.P(Integer.toHexString(iY).toUpperCase(Locale.US), 4)));
            g0Var.a(arrayList);
        } catch (com.felicanetworks.mfc.x e) {
            if (!b(e)) {
                throw j.b(e, e.getMessage());
            }
            ArrayList arrayList2 = new ArrayList();
            arrayList2.add(new i0("keyversion", "FFFF"));
            g0Var.a(arrayList2);
        }
    }
}
