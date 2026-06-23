package com.felicanetworks.mfw.a.cmn;

/* JADX INFO: compiled from: FelicaChkUtil.java */
/* JADX INFO: loaded from: classes.dex */
public class f0 {
    public static boolean a(long j) {
        return e(j) && (j & 30) == 0;
    }

    public static boolean b(long j) {
        if (e(j)) {
            return (j & 1) != 1 || a(j);
        }
        return false;
    }

    public static boolean c(long j) {
        if (e(j)) {
            return (j & 12) == 12 || (j & 44) == 44;
        }
        return false;
    }

    public static boolean d(long j) {
        return j >= 4096 && j <= 8191;
    }

    public static boolean e(long j) {
        long j2 = j & 63;
        if ((30 & j2) == 0) {
            return true;
        }
        long j3 = j2 & 24;
        return j3 == 8 || j3 == 16;
    }

    public static boolean f(long j) {
        return e(j) && (j & 32) == 32;
    }

    public static boolean g(long j) {
        long j2 = j & 63;
        return j2 == 19 || j2 == 51;
    }

    public static boolean h(long j) {
        if (e(j)) {
            return (j & 10) == 10 || (j & 42) == 42 || (j & 22) == 22;
        }
        return false;
    }

    public static boolean i(long j) {
        return j == Long.parseLong("FFFFFFFF", 16);
    }
}
