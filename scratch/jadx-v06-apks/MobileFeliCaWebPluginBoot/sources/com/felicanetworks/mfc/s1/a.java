package com.felicanetworks.mfc.s1;

import com.felicanetworks.mfc.mfi.g;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: LogMgr.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    static Map f116a;
    private static final Object[] b;
    private static volatile Object[] c;

    static {
        int i = g.f104a;
        HashMap map = new HashMap();
        f116a = map;
        map.put("com.felicanetworks.mfc.Felica", 7);
        f116a.put("com.felicanetworks.mfc.FSC", 7);
        f116a.put("com.felicanetworks.mfc.PushSegmentParcelableWrapper", 7);
        f116a.put("com.felicanetworks.mfc.PushSegmentParcelableWrapper$1", 7);
        b = new Object[0];
        c = new Object[8];
    }

    public static void a(int i, String str) {
        h(i, str, b, 0L);
    }

    public static void b(int i, String str, Object obj) {
        c[0] = obj;
        h(i, str, c, 1L);
    }

    public static void c(int i, String str, Object obj, Object obj2) {
        c[0] = obj;
        c[1] = obj2;
        h(i, str, c, 2L);
    }

    public static void d(int i, String str, Object obj, Object obj2, Object obj3) {
        c[0] = obj;
        c[1] = obj2;
        c[2] = obj3;
        h(i, str, c, 3L);
    }

    public static void e(int i, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        c[0] = obj;
        c[1] = obj2;
        c[2] = obj3;
        c[3] = obj4;
        h(i, str, c, 4L);
    }

    public static void f(int i, String str, Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        c[0] = obj;
        c[1] = obj2;
        c[2] = obj3;
        c[3] = obj4;
        c[4] = obj5;
        h(i, str, c, 5L);
    }

    public static void g(int i, String str, Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7) {
        c[0] = obj;
        c[1] = obj2;
        c[2] = obj3;
        c[3] = obj4;
        c[4] = obj5;
        c[5] = obj6;
        c[6] = obj7;
        h(i, str, c, 7L);
    }

    protected static void h(int i, String str, Object[] objArr, long j) {
    }

    public static void i(int i, Throwable th) {
    }
}
