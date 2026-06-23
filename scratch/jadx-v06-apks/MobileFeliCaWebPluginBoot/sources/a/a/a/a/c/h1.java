package a.a.a.a.c;

import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: PermitOptr.java */
/* JADX INFO: loaded from: classes.dex */
public class h1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private com.felicanetworks.mfw.a.cmn.y0 f31a;
    private x0 b;
    private i1 c;
    private com.felicanetworks.mfw.a.cmn.h0 d;

    public h1(com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        this.d = h0Var;
    }

    private x0 A(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        com.felicanetworks.mfw.a.cmn.f fVar3 = fVarArrA[1];
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA2 = fVar2.a();
        int length = fVarArrA2.length;
        if (length != 8 && length != 9) {
            throw new c1(this, 1100);
        }
        com.felicanetworks.mfw.a.cmn.f fVar4 = fVarArrA2[0];
        com.felicanetworks.mfw.a.cmn.f fVar5 = fVarArrA2[1];
        com.felicanetworks.mfw.a.cmn.f fVar6 = fVarArrA2[2];
        com.felicanetworks.mfw.a.cmn.f fVar7 = fVarArrA2[3];
        com.felicanetworks.mfw.a.cmn.f fVar8 = fVarArrA2[4];
        com.felicanetworks.mfw.a.cmn.f fVar9 = fVarArrA2[5];
        com.felicanetworks.mfw.a.cmn.f fVar10 = fVarArrA2[6];
        com.felicanetworks.mfw.a.cmn.f fVar11 = fVarArrA2[7];
        if (fVar5.e() != 22) {
            throw new c1(this, 1101);
        }
        if (fVar7.e() != 22) {
            throw new c1(this, 1102);
        }
        if (fVar9.e() != 48) {
            throw new c1(this, 1103);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA3 = fVar9.a();
        if (fVar9.a() == null || fVar9.a().length != 2) {
            throw new c1(this, 1104);
        }
        com.felicanetworks.mfw.a.cmn.f fVar12 = fVarArrA3[0];
        if (fVar12.e() != 24) {
            throw new c1(this, 1105);
        }
        com.felicanetworks.mfw.a.cmn.f fVar13 = fVarArrA3[1];
        if (fVar13.e() != 24) {
            throw new c1(this, 1106);
        }
        if (fVar10.e() != 22) {
            throw new c1(this, 1107);
        }
        if (fVar11.e() != 22) {
            throw new c1(this, 1108);
        }
        x0 x0Var = new x0();
        x0Var.t(new String(fVar4.b()));
        x0Var.o(new String(fVar5.b()));
        x0Var.n(new String(fVar6.b()));
        x0Var.q(new String(fVar7.b()));
        x0Var.k(new String(fVar8.b()));
        x0Var.m(new String(fVar12.b()));
        x0Var.l(new String(fVar13.b()));
        x0Var.r(new String(fVar10.b()));
        x0Var.p(new String(fVar11.b()));
        x0Var.s(fVar3.b());
        if (length == 9) {
            com.felicanetworks.mfw.a.cmn.f fVar14 = fVarArrA2[8];
            if (fVar14.e() != 48) {
                throw new c1(this, 1109);
            }
            com.felicanetworks.mfw.a.cmn.f[] fVarArrA4 = fVar14.a();
            if (fVarArrA4 == null || fVarArrA4.length == 0) {
                throw new c1(this, 1110);
            }
            for (com.felicanetworks.mfw.a.cmn.f fVar15 : fVarArrA4) {
                x0Var.a(v(fVar15));
            }
        }
        return x0Var;
    }

    private int B(String str) throws c1 {
        if (!com.felicanetworks.mfw.a.cmn.b1.r(str, 1, 8)) {
            throw new c1(this, 8101);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(str)) {
            throw new c1(this, 8102);
        }
        int i = Integer.parseInt(str);
        if (i <= com.felicanetworks.mfw.a.cmn.p0.a().b("rvction.limit")) {
            return i;
        }
        throw new c1(this, 8103);
    }

    private n1 C(com.felicanetworks.mfw.a.cmn.y0 y0Var) throws Throwable {
        String[] strArrF;
        ArrayList arrayListD = com.felicanetworks.mfw.a.cmn.b1.d(y0Var.b());
        int size = arrayListD.size();
        if (size < 3) {
            throw new c1(this, 8100);
        }
        int iB = B((String) arrayListD.get(0));
        int iD = D((String) arrayListD.get(1));
        int iE = E((String) arrayListD.get(2));
        if (size == 4) {
            if (com.felicanetworks.mfw.a.cmn.b1.l((String) arrayListD.get(3))) {
                throw new c1(this, 8110);
            }
            throw new c1(this, 8111);
        }
        if (size > 4) {
            for (int i = 4; i < arrayListD.size(); i++) {
                if (com.felicanetworks.mfw.a.cmn.b1.l((String) arrayListD.get(i))) {
                    throw new c1(this, 8110);
                }
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.l((String) arrayListD.get(3))) {
                throw new c1(this, 8111);
            }
            strArrF = F(arrayListD);
            if (iE != strArrF.length) {
                throw new c1(this, 8114);
            }
        } else {
            if (iE != 0) {
                throw new c1(this, 8114);
            }
            strArrF = new String[0];
        }
        n1 n1Var = new n1();
        n1Var.m(iB);
        n1Var.n(iD);
        n1Var.r(iE);
        n1Var.o(strArrF);
        return n1Var;
    }

    private int D(String str) throws c1 {
        if (!com.felicanetworks.mfw.a.cmn.b1.r(str, 1, 8)) {
            throw new c1(this, 8104);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(str)) {
            throw new c1(this, 8105);
        }
        int i = Integer.parseInt(str);
        if (i <= com.felicanetworks.mfw.a.cmn.p0.a().b("rvction.term")) {
            return i;
        }
        throw new c1(this, 8106);
    }

    private int E(String str) throws c1 {
        if (!com.felicanetworks.mfw.a.cmn.b1.r(str, 1, 4)) {
            throw new c1(this, 8107);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.k(str)) {
            throw new c1(this, 8108);
        }
        int i = Integer.parseInt(str);
        if (i <= com.felicanetworks.mfw.a.cmn.p0.a().b("rvction.serial.number.limit")) {
            return i;
        }
        throw new c1(this, 8109);
    }

    private String[] F(List list) throws c1 {
        String[] strArr = new String[list.size() - 4];
        for (int i = 0; i < list.size() - 4; i++) {
            String str = (String) list.get(i + 4);
            if (!com.felicanetworks.mfw.a.cmn.b1.q(str, 12)) {
                throw new c1(this, 8112);
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.j(str)) {
                throw new c1(this, 8113);
            }
            strArr[i] = str;
        }
        return strArr;
    }

    private String G(String str) {
        return str.startsWith("http://") ? str.substring(7) : str.startsWith("https://") ? str.substring(8) : str;
    }

    private void H(int i, int i2, String str) {
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("rescode", String.valueOf(i)));
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("errpos", "h0" + String.valueOf(i2)));
            new b1(this).b("I007", str, r1.a(arrayList));
        } catch (c1 unused) {
        }
    }

    private void K(t0 t0Var) throws c1 {
        if (!com.felicanetworks.mfw.a.cmn.b1.q(t0Var.c(), 4) || !com.felicanetworks.mfw.a.cmn.b1.m(t0Var.c())) {
            throw new c1(this, 1179);
        }
        for (int i = 0; i < t0Var.d(); i++) {
            r0 r0VarB = t0Var.b(i);
            if (!com.felicanetworks.mfw.a.cmn.b1.q(r0VarB.a(), 8) || !com.felicanetworks.mfw.a.cmn.b1.m(r0VarB.a())) {
                throw new c1(this, 1180);
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.q(r0VarB.b(), 8) || !com.felicanetworks.mfw.a.cmn.b1.m(r0VarB.b())) {
                throw new c1(this, 1181);
            }
        }
    }

    private void L(o0 o0Var) throws c1 {
        if (o0Var.a().equals("0001")) {
            String strB = ((h) o0Var).b();
            if (!com.felicanetworks.mfw.a.cmn.b1.q(strB, 6) || !com.felicanetworks.mfw.a.cmn.b1.m(strB)) {
                throw new c1(this, 1170);
            }
            return;
        }
        if (o0Var.a().equals("0002")) {
            M((p0) o0Var);
            return;
        }
        if (o0Var.a().equals("0003")) {
            if (!com.felicanetworks.mfw.a.cmn.b1.F(((g) o0Var).b())) {
                throw new c1(this, 1182);
            }
        } else if (o0Var.a().equals("0004") && ((w0) o0Var).b().length != 32) {
            throw new c1(this, 1183);
        }
    }

    private void M(p0 p0Var) throws c1 {
        if (p0Var.d().length != 2) {
            throw new c1(this, 1177);
        }
        if (p0Var.e().length != 2) {
            throw new c1(this, 1178);
        }
        for (int i = 0; i < p0Var.f(); i++) {
            K(p0Var.c(i));
        }
    }

    private void N(x0 x0Var) throws c1 {
        if (!com.felicanetworks.mfw.a.cmn.b1.q(x0Var.g(), 2) || !com.felicanetworks.mfw.a.cmn.b1.k(x0Var.g())) {
            throw new c1(this, 1171);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(x0Var.i(), 12) || !com.felicanetworks.mfw.a.cmn.b1.j(x0Var.i())) {
            throw new c1(this, 1172);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(x0Var.f(), 19) || !com.felicanetworks.mfw.a.cmn.x.g(x0Var.f())) {
            throw new c1(this, 1173);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(x0Var.e(), 19) || !com.felicanetworks.mfw.a.cmn.x.g(x0Var.e())) {
            throw new c1(this, 1174);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.q(x0Var.j(), 8) || !com.felicanetworks.mfw.a.cmn.b1.j(x0Var.j())) {
            throw new c1(this, 1175);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.r(x0Var.h(), 0, 255)) {
            throw new c1(this, 1176);
        }
        for (int i = 0; i < x0Var.b(); i++) {
            L(x0Var.c(i));
        }
    }

    private void O(com.felicanetworks.mfw.a.cmn.f fVar, com.felicanetworks.mfw.a.cmn.f fVar2) throws c1 {
        if (fVar.e() != 22) {
            throw new c1(this, 1021);
        }
        if (fVar.b().length != 6 || !com.felicanetworks.mfw.a.cmn.b1.j(new String(fVar.b()))) {
            throw new c1(this, 1022);
        }
        if (fVar2.e() != 22) {
            throw new c1(this, 1023);
        }
        if (fVar2.b().length != 4 || !com.felicanetworks.mfw.a.cmn.b1.m(new String(fVar2.b()))) {
            throw new c1(this, 1024);
        }
    }

    private void P(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1002);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || fVarArrA.length != 2) {
            throw new c1(this, 1003);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        com.felicanetworks.mfw.a.cmn.f fVar3 = fVarArrA[1];
        if (fVar2.e() != 48 || fVar3.e() != 3) {
            throw new c1(this, 1004);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA2 = fVar2.a();
        if (fVarArrA2 == null || fVarArrA2.length == 0) {
            throw new c1(this, 1010);
        }
        if (fVarArrA2.length < 5) {
            throw new c1(this, 1020);
        }
        R(fVarArrA2[0]);
        O(fVarArrA2[2], fVarArrA2[4]);
    }

    private void R(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 22) {
            throw new c1(this, 1011);
        }
        if (!s(fVar.b())) {
            throw new c1(this, 1012);
        }
    }

    private void S(x0 x0Var) throws c1 {
        if (x0Var.b() != 3 || x0Var.d("0001") == null || x0Var.d("0002") == null || x0Var.d("0003") == null) {
            throw new c1(this, 1195);
        }
        if (!((h) x0Var.d("0001")).b().equals(com.felicanetworks.mfw.a.cmn.k.e().c())) {
            throw new c1(this, 3001);
        }
        if (!x0Var.g().equals(com.felicanetworks.mfw.a.cmn.p0.a().c("permit.type"))) {
            throw new c1(this, 3002);
        }
        String strH = x0Var.h();
        if (com.felicanetworks.mfw.a.cmn.b1.l(strH)) {
            throw new c1(this, 1192);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.D(strH)) {
            throw new c1(this, 1193);
        }
    }

    private void T(x0 x0Var) throws c1 {
        long jCurrentTimeMillis = System.currentTimeMillis();
        long jP = com.felicanetworks.mfw.a.cmn.x.p(x0Var.f());
        long jP2 = com.felicanetworks.mfw.a.cmn.x.p(x0Var.e());
        if (jCurrentTimeMillis < jP || jP2 < jCurrentTimeMillis) {
            throw new c1(this, 3000);
        }
    }

    private void U(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        g1 g1Var = new g1(this);
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVar.a()[0];
        byte[] bArrJ = j(new String(fVar2.a()[2].b()), new String(fVar2.a()[4].b()));
        try {
            if (!g1Var.d(d(fVar2), c(fVar.a()[1]), bArrJ)) {
                throw new c1(this, 1031);
            }
        } catch (Exception unused) {
            throw new c1(this, 1031);
        }
    }

    private x0 b(String str) throws c1 {
        com.felicanetworks.mfw.a.cmn.f fVarH = h(i(str));
        P(fVarH);
        U(fVarH);
        x0 x0VarA = A(fVarH);
        N(x0VarA);
        return x0VarA;
    }

    private byte[] c(com.felicanetworks.mfw.a.cmn.f fVar) {
        byte[] bArrB = fVar.b();
        if (bArrB.length == 0 || bArrB[0] != 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[bArrB.length - 1];
        System.arraycopy(bArrB, 1, bArr, 0, bArrB.length - 1);
        return bArr;
    }

    private byte[] d(com.felicanetworks.mfw.a.cmn.f fVar) {
        byte[] bArrD = fVar.d();
        byte[] bArrB = fVar.b();
        byte[] bArr = new byte[bArrD.length + bArrB.length];
        System.arraycopy(bArrD, 0, bArr, 0, bArrD.length);
        System.arraycopy(bArrB, 0, bArr, bArrD.length, bArrB.length);
        return bArr;
    }

    /* JADX WARN: Removed duplicated region for block: B:34:0x0082  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private a.a.a.a.c.z0 e(java.lang.String r7) throws java.lang.Throwable {
        /*
            r6 = this;
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            com.felicanetworks.mfw.a.cmn.h0 r1 = r6.d
            a.a.a.a.c.f0 r1 = r1.d()
            java.util.ArrayList r2 = new java.util.ArrayList
            r2.<init>()
            int r7 = r1.H(r7, r0, r2)
            if (r7 != 0) goto L95
            a.a.a.a.c.z0 r7 = new a.a.a.a.c.z0
            r1 = 0
            r7.<init>(r6)
            r1 = 0
            r2 = r1
        L1e:
            int r3 = r0.size()
            if (r1 >= r3) goto L8a
            java.lang.Object r3 = r0.get(r1)
            java.lang.String r3 = (java.lang.String) r3
            java.lang.String r4 = "permit"
            boolean r4 = r3.equals(r4)
            r5 = 1
            if (r4 != 0) goto L7c
            java.lang.String r4 = "permit "
            boolean r4 = r3.startsWith(r4)
            if (r4 == 0) goto L3c
            goto L7c
        L3c:
            java.lang.String r4 = "url"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L71
            java.lang.String r4 = "url "
            boolean r4 = r3.startsWith(r4)
            if (r4 == 0) goto L4d
            goto L71
        L4d:
            java.lang.String r4 = "mode"
            boolean r4 = r3.equals(r4)
            if (r4 != 0) goto L66
            java.lang.String r4 = "mode "
            boolean r4 = r3.startsWith(r4)
            if (r4 == 0) goto L5e
            goto L66
        L5e:
            a.a.a.a.c.c1 r7 = new a.a.a.a.c.c1
            r0 = 2211(0x8a3, float:3.098E-42)
            r7.<init>(r6, r0)
            throw r7
        L66:
            boolean r4 = r7.d()
            if (r4 == 0) goto L6d
            goto L82
        L6d:
            r7.g(r1, r3)
            goto L87
        L71:
            boolean r4 = r7.f()
            if (r4 == 0) goto L78
            goto L82
        L78:
            r7.i(r1, r3)
            goto L87
        L7c:
            boolean r4 = r7.e()
            if (r4 == 0) goto L84
        L82:
            r2 = r5
            goto L87
        L84:
            r7.h(r1, r3)
        L87:
            int r1 = r1 + 1
            goto L1e
        L8a:
            if (r2 != 0) goto L8d
            return r7
        L8d:
            a.a.a.a.c.c1 r7 = new a.a.a.a.c.c1
            r0 = 2212(0x8a4, float:3.1E-42)
            r7.<init>(r6, r0)
            throw r7
        L95:
            a.a.a.a.c.c1 r0 = new a.a.a.a.c.c1
            r0.<init>(r6, r7)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: a.a.a.a.c.h1.e(java.lang.String):a.a.a.a.c.z0");
    }

    private com.felicanetworks.mfw.a.cmn.y0 f(String str) {
        return new b1(this).b("I007", str, r1.a(new ArrayList()));
    }

    private com.felicanetworks.mfw.a.cmn.f h(String str) throws c1 {
        try {
            try {
                com.felicanetworks.mfw.a.cmn.f fVarD = com.felicanetworks.mfw.a.cmn.g.d(com.felicanetworks.mfw.a.cmn.i.a(str));
                if (fVarD != null) {
                    return fVarD.a()[0];
                }
                throw new c1(this, 1001);
            } catch (Exception unused) {
                throw new c1(this, 1001);
            }
        } catch (Exception unused2) {
            throw new c1(this, 1000);
        }
    }

    private String i(String str) throws c1 {
        if (str == null) {
            throw new c1(this, 2221);
        }
        int iIndexOf = str.indexOf(32);
        if (iIndexOf == -1 || str.indexOf(44) != -1) {
            throw new c1(this, 2222);
        }
        String strSubstring = str.substring(iIndexOf + 1);
        if (com.felicanetworks.mfw.a.cmn.b1.l(strSubstring)) {
            throw new c1(this, 2223);
        }
        if (com.felicanetworks.mfw.a.cmn.b1.p(strSubstring)) {
            return strSubstring;
        }
        throw new c1(this, 2225);
    }

    private byte[] j(String str, String str2) throws c1 {
        try {
            byte[] bArrC = new g1(this).c(str, str2);
            if (bArrC != null) {
                return bArrC;
            }
            throw new c1(this, 1030);
        } catch (Exception unused) {
            throw new c1(this, 1031);
        }
    }

    private com.felicanetworks.mfw.a.cmn.y0 l(n1 n1Var, String str, long j) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ai=" + com.felicanetworks.mfw.a.cmn.p0.a().c("application.id"));
        stringBuffer.append("&av=" + com.felicanetworks.mfw.a.cmn.p0.a().c("application.version"));
        String strB = this.d.j().b();
        if (strB != null) {
            stringBuffer.append("&idm=" + strB);
        }
        if (n1Var.k() == null || n1Var.k().equals("")) {
            stringBuffer.append("&ct=0");
            stringBuffer.append("&mct=0");
            stringBuffer.append("&il=0");
            stringBuffer.append("&mil=0");
        } else {
            stringBuffer.append("&ct=" + String.valueOf(n1Var.d()));
            stringBuffer.append("&mct=" + String.valueOf(n1Var.e()));
            long jCurrentTimeMillis = (System.currentTimeMillis() - j) / 60000;
            if (jCurrentTimeMillis < 0 || 99999999 < jCurrentTimeMillis) {
                stringBuffer.append("&il=0");
            } else {
                stringBuffer.append("&il=" + String.valueOf(jCurrentTimeMillis));
            }
            stringBuffer.append("&mil=" + String.valueOf(n1Var.f()));
        }
        if (n1Var.a()) {
            stringBuffer.append("&desc=fail-safe");
        }
        return new b1(this).b("I003", str, stringBuffer.toString());
    }

    private String m(x0 x0Var) throws c1 {
        if (x0Var.b() == 0) {
            throw new c1(this, 1194);
        }
        o0 o0VarD = x0Var.d("0003");
        if (o0VarD != null) {
            return ((g) o0VarD).b();
        }
        throw new c1(this, 1196);
    }

    private String n(String str) throws c1 {
        if (str == null) {
            throw new c1(this, 2241);
        }
        int iIndexOf = str.indexOf(32);
        if (iIndexOf == -1 || str.indexOf(44) != -1) {
            throw new c1(this, 2242);
        }
        String strSubstring = str.substring(iIndexOf + 1);
        if (com.felicanetworks.mfw.a.cmn.b1.l(strSubstring)) {
            throw new c1(this, 2243);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.r(strSubstring, 0, 255)) {
            throw new c1(this, 2244);
        }
        if (com.felicanetworks.mfw.a.cmn.b1.D(strSubstring)) {
            return strSubstring;
        }
        throw new c1(this, 2246);
    }

    private boolean o(n1 n1Var, x0 x0Var) {
        String strH;
        if (n1Var.k() == null || (strH = n1Var.h()) == null || !com.felicanetworks.mfw.a.cmn.b1.N(strH).equals(com.felicanetworks.mfw.a.cmn.b1.N(x0Var.h()))) {
            return true;
        }
        int iD = n1Var.d();
        int iE = n1Var.e();
        return iD == -1 || iE == -1 || iD > iE || iD == 0;
    }

    private boolean p(n1 n1Var, long j) {
        return System.currentTimeMillis() > j + (((long) n1Var.f()) * 60000);
    }

    private boolean q(int i) {
        if (i == 3010) {
            return true;
        }
        switch (i) {
            case 3000:
            case 3001:
            case 3002:
                return true;
            default:
                return false;
        }
    }

    private boolean r(x0 x0Var, n1 n1Var) {
        String strI = x0Var.i();
        String[] strArrG = n1Var.g();
        if (strArrG == null) {
            return true;
        }
        for (String str : strArrG) {
            if (str.equals(strI)) {
                return false;
            }
        }
        return true;
    }

    private boolean s(byte[] bArr) {
        return new String(bArr).equals(com.felicanetworks.mfw.a.cmn.p0.a().c("permit.version"));
    }

    private o0 t(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1150);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || fVarArrA.length != 1) {
            throw new c1(this, 1151);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        if (fVar2.e() != 22) {
            throw new c1(this, 1152);
        }
        g gVar = new g();
        gVar.c(new String(fVar2.b()));
        return gVar;
    }

    private o0 u(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1120);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || fVarArrA.length != 1) {
            throw new c1(this, 1121);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        if (fVar2.e() != 22) {
            throw new c1(this, 1122);
        }
        h hVar = new h();
        hVar.c(new String(fVar2.b()));
        return hVar;
    }

    private o0 v(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1111);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || fVarArrA.length != 2) {
            throw new c1(this, 1112);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        if (fVar2.e() != 22) {
            throw new c1(this, 1113);
        }
        com.felicanetworks.mfw.a.cmn.f fVar3 = fVarArrA[1];
        if (fVar3.e() != 4) {
            throw new c1(this, 1114);
        }
        com.felicanetworks.mfw.a.cmn.f fVarD = com.felicanetworks.mfw.a.cmn.g.d(fVar3.b());
        if (fVarD == null) {
            throw new c1(this, 1116);
        }
        com.felicanetworks.mfw.a.cmn.f fVar4 = fVarD.a()[0];
        String str = new String(fVar2.b());
        if (str.equals("0001")) {
            return u(fVar4);
        }
        if (str.equals("0002")) {
            return w(fVar4);
        }
        if (str.equals("0003")) {
            return t(fVar4);
        }
        if (str.equals("0004")) {
            return z(fVar4);
        }
        throw new c1(this, 1115);
    }

    private o0 w(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1130);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || !(fVarArrA.length == 2 || fVarArrA.length == 3)) {
            throw new c1(this, 1131);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        com.felicanetworks.mfw.a.cmn.f fVar3 = fVarArrA[1];
        if (fVar2.e() != 4 || fVar3.e() != 4) {
            throw new c1(this, 1132);
        }
        p0 p0Var = new p0();
        p0Var.h(fVar2.b());
        p0Var.g(fVar3.b());
        if (fVarArrA.length == 3) {
            com.felicanetworks.mfw.a.cmn.f fVar4 = fVarArrA[2];
            if (fVar4.e() != 48) {
                throw new c1(this, 1133);
            }
            com.felicanetworks.mfw.a.cmn.f[] fVarArrA2 = fVar4.a();
            if (fVarArrA2 == null || fVarArrA2.length == 0) {
                throw new c1(this, 1140);
            }
            for (com.felicanetworks.mfw.a.cmn.f fVar5 : fVarArrA2) {
                p0Var.b(y(fVar5));
            }
        }
        return p0Var;
    }

    private r0 x(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1145);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || fVarArrA.length != 2) {
            throw new c1(this, 1146);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        com.felicanetworks.mfw.a.cmn.f fVar3 = fVarArrA[1];
        if (fVar2.e() != 22 || fVar3.e() != 22) {
            throw new c1(this, 1147);
        }
        r0 r0Var = new r0();
        r0Var.c(new String(fVar2.b()));
        r0Var.d(new String(fVar3.b()));
        return r0Var;
    }

    private t0 y(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1141);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || fVarArrA.length != 2) {
            throw new c1(this, 1142);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        com.felicanetworks.mfw.a.cmn.f fVar3 = fVarArrA[1];
        if (fVar2.e() != 22 || fVar3.e() != 48) {
            throw new c1(this, 1143);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA2 = fVar3.a();
        if (fVarArrA2 == null || fVarArrA2.length == 0) {
            throw new c1(this, 1144);
        }
        t0 t0Var = new t0();
        t0Var.e(new String(fVar2.b()));
        for (com.felicanetworks.mfw.a.cmn.f fVar4 : fVarArrA2) {
            t0Var.a(x(fVar4));
        }
        return t0Var;
    }

    private o0 z(com.felicanetworks.mfw.a.cmn.f fVar) throws c1 {
        if (fVar.e() != 48) {
            throw new c1(this, 1160);
        }
        com.felicanetworks.mfw.a.cmn.f[] fVarArrA = fVar.a();
        if (fVarArrA == null || fVarArrA.length != 1) {
            throw new c1(this, 1161);
        }
        com.felicanetworks.mfw.a.cmn.f fVar2 = fVarArrA[0];
        if (fVar2.e() != 4) {
            throw new c1(this, 1162);
        }
        w0 w0Var = new w0();
        w0Var.c(fVar2.b());
        return w0Var;
    }

    public void I(i1 i1Var) {
        if (i1Var != null) {
            this.c = i1Var;
            return;
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Illegal argument.");
        stringBuffer.append(" [listener = " + i1Var + "]");
        throw new com.felicanetworks.mfw.a.cmn.c1(h1.class, "setListener", stringBuffer.toString());
    }

    /* JADX WARN: Removed duplicated region for block: B:54:0x0159  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public void J(java.lang.String r12) throws java.lang.Throwable {
        /*
            Method dump skipped, instruction units count: 428
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: a.a.a.a.c.h1.J(java.lang.String):void");
    }

    public boolean Q(String str, String str2) {
        if (str == null || str2 == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("Ilegal argument.");
            stringBuffer.append(" [targetUrl = " + str);
            stringBuffer.append(", urlLimit = " + str2 + "]");
            throw new com.felicanetworks.mfw.a.cmn.c1(h1.class, "validateUrl", stringBuffer.toString());
        }
        String strN = com.felicanetworks.mfw.a.cmn.b1.N(str);
        try {
            str2 = com.felicanetworks.mfw.a.cmn.b1.L(str2);
        } catch (com.felicanetworks.mfw.a.cmn.c1 unused) {
        }
        String strG = G(strN);
        int iIndexOf = strG.indexOf(35);
        if (iIndexOf != -1) {
            strG = strG.substring(0, iIndexOf);
        }
        int iIndexOf2 = str2.indexOf(47);
        int iIndexOf3 = str2.indexOf(58);
        if (iIndexOf2 == -1 && iIndexOf3 == -1) {
            int iIndexOf4 = strG.indexOf(58);
            if (iIndexOf4 != -1) {
                strG = strG.substring(0, iIndexOf4);
            }
            int iIndexOf5 = strG.indexOf(47);
            if (iIndexOf5 != -1) {
                strG = strG.substring(0, iIndexOf5);
            }
            if ("/".concat(strG).endsWith("/".concat(str2))) {
                return true;
            }
            return ".".concat(strG).endsWith(".".concat(str2));
        }
        if (iIndexOf2 != -1 || iIndexOf3 == -1) {
            if (!str2.endsWith("/")) {
                str2 = str2 + "/";
            }
            return strG.startsWith(str2);
        }
        int iIndexOf6 = strG.indexOf(47);
        if (iIndexOf6 != -1) {
            strG = strG.substring(0, iIndexOf6);
        }
        if ("/".concat(strG).endsWith("/".concat(str2))) {
            return true;
        }
        return ".".concat(strG).endsWith(".".concat(str2));
    }

    public x0 g() {
        return this.b;
    }

    public com.felicanetworks.mfw.a.cmn.y0 k() {
        return this.f31a;
    }
}
