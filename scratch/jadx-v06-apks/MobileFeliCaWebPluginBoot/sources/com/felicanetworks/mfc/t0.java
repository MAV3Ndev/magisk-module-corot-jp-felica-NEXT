package com.felicanetworks.mfc;

/* JADX INFO: compiled from: MfcUtil.java */
/* JADX INFO: loaded from: classes.dex */
class t0 {
    public static void a(FelicaResultInfo felicaResultInfo) throws Exception {
        com.felicanetworks.mfc.s1.a.b(7, "%s", "000");
        if (felicaResultInfo == null) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "704", "Result is null!");
            throw new Exception("Illegal Response");
        }
        com.felicanetworks.mfc.s1.a.c(7, "%s %d", "002", Integer.valueOf(felicaResultInfo.a()));
        int iA = felicaResultInfo.a();
        if (iA == 0) {
            com.felicanetworks.mfc.s1.a.b(7, "%s", "999");
            return;
        }
        if (iA == 1) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "701", "FelicaException");
            com.felicanetworks.mfc.s1.a.f(2, "id:%d type:%d appId:%d flg1:%d flg2:%d ", Integer.valueOf(felicaResultInfo.d()), Integer.valueOf(felicaResultInfo.h()), felicaResultInfo.e(), Integer.valueOf(felicaResultInfo.f()), Integer.valueOf(felicaResultInfo.g()));
            if (felicaResultInfo.d() != 2 || felicaResultInfo.h() != 153) {
                throw new x(felicaResultInfo.d(), felicaResultInfo.h(), felicaResultInfo.e(), felicaResultInfo.f(), felicaResultInfo.g(), felicaResultInfo.b());
            }
            throw new com.felicanetworks.mfc.mfi.k0(2, 153, felicaResultInfo.b());
        }
        if (iA == 32) {
            com.felicanetworks.mfc.s1.a.d(2, "%s %s %s", "702", "IllegalArgumentException", felicaResultInfo.b());
            throw new IllegalArgumentException(felicaResultInfo.b());
        }
        if (iA != 34) {
            com.felicanetworks.mfc.s1.a.c(2, "%s %s", "703", "Illegal ExceptionType");
            throw new Exception("Unknown error.");
        }
        com.felicanetworks.mfc.s1.a.d(2, "%s %s %s", "704", "NumberFormatException", felicaResultInfo.b());
        throw new NumberFormatException();
    }
}
