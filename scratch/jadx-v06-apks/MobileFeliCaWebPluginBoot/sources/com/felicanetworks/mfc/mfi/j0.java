package com.felicanetworks.mfc.mfi;

import com.felicanetworks.mfc.x0;
import java.util.ArrayList;
import java.util.Collections;

/* JADX INFO: compiled from: MfiClient.java */
/* JADX INFO: loaded from: classes.dex */
public class j0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private final x0 f108a;

    static {
        ArrayList arrayList = new ArrayList();
        arrayList.add("Google");
        Collections.unmodifiableList(arrayList);
    }

    public j0(x0 x0Var) {
        new c0(this);
        new d0(this);
        new e0(this);
        new f0(this);
        new g0(this);
        new h0(this);
        new i0(this);
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        this.f108a = x0Var;
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }

    public void b() {
        com.felicanetworks.mfc.s1.a.b(3, "%s", "000");
        com.felicanetworks.mfc.s1.a.b(3, "%s", "999");
    }
}
