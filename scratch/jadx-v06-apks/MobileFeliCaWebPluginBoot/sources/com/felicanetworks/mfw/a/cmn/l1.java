package com.felicanetworks.mfw.a.cmn;

import com.felicanetworks.mfc.BlockDataList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: compiled from: WriteWithoutEncryption.java */
/* JADX INFO: loaded from: classes.dex */
public class l1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private h0 f163a;

    public l1(h0 h0Var) {
        this.f163a = h0Var;
    }

    private static BlockDataList a(f1 f1Var, int i, String str) {
        BlockDataList blockDataList = new BlockDataList();
        Iterator it = h(str).iterator();
        while (it.hasNext()) {
            blockDataList.a(f1Var.a(i, (String) it.next()));
            i++;
        }
        return blockDataList;
    }

    private static f1 c(int i, int i2) {
        int i3 = i & 63;
        if (f0.c(i)) {
            return new g1(i);
        }
        if (g(i2, i3)) {
            return new k1(i);
        }
        if (f(i2, i3)) {
            return new i1(i);
        }
        if (e(i2, i3)) {
            return new j1(i);
        }
        if (d(i2)) {
            return new h1(i);
        }
        throw new c1(l1.class, "getCreator", "Unknown Service.");
    }

    private static boolean d(int i) {
        return i == 1;
    }

    private static boolean e(int i, int i2) {
        return i == 0 && (i2 == 19 || i2 == 21);
    }

    private static boolean f(int i, int i2) {
        return i == 0 && i2 == 17;
    }

    private static boolean g(int i, int i2) {
        return i == 0 && i2 == 9;
    }

    private static List h(String str) {
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (true) {
            int i2 = i * 32;
            if (i2 >= str.length()) {
                return arrayList;
            }
            i++;
            arrayList.add(str.substring(i2, i * 32));
        }
    }

    public void b(int i, int i2, int i3, int i4, String str, g0 g0Var) throws e {
        try {
            this.f163a.e().c().L(a(c(i, i4), i2, str));
            g0Var.a(new ArrayList());
        } catch (com.felicanetworks.mfc.x e) {
            throw j.b(e, e.getMessage());
        }
    }
}
