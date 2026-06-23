package a.a.a.a.c;

import com.felicanetworks.mfw.a.boot.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
public class f0 {
    private static final String[] q = {"select", "get_keyversion", "read", "write", "start", "onfelica_start", "rw_select", "rw_get_keyversion", "rw_read", "rw_write", "push_start_app", "push_start_browser"};

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private g0 f25a;
    private String b;
    private int c;
    private String d;
    private t0[] e;
    private i f;
    private String g;
    private int h;
    private boolean j;
    private int k;
    private String l;
    private m[] m;
    private com.felicanetworks.mfw.a.cmn.h0 o;
    private List i = new ArrayList();
    private p n = new p(this);
    private volatile boolean p = false;

    public f0(com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        j jVar = null;
        this.o = h0Var;
        m[] mVarArr = new m[12];
        this.m = mVarArr;
        mVarArr[0] = new c0(this);
        this.m[1] = new n(this);
        this.m[2] = new v(this);
        this.m[3] = new e0(this);
        this.m[4] = new d0(this);
        this.m[5] = new s(this);
        this.m[6] = new a0(this);
        this.m[7] = new y(this);
        this.m[8] = new z(this);
        this.m[9] = new b0(this);
        this.m[10] = new t(this);
        this.m[11] = new u(this);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean B(String str, String str2) {
        int i = 0;
        while (true) {
            t0[] t0VarArr = this.e;
            if (i >= t0VarArr.length) {
                return false;
            }
            t0 t0Var = t0VarArr[i];
            if (str.equalsIgnoreCase(t0Var.c())) {
                for (int i2 = 0; i2 < t0Var.d(); i2++) {
                    r0 r0VarB = t0Var.b(i2);
                    String strA = r0VarB.a();
                    String strB = r0VarB.b();
                    if (Long.parseLong(str2, 16) >= Long.parseLong(strA, 16) && Long.parseLong(str2, 16) <= Long.parseLong(strB, 16)) {
                        return true;
                    }
                }
            }
            i++;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean C(String str) {
        int i = 0;
        while (true) {
            t0[] t0VarArr = this.e;
            if (i >= t0VarArr.length) {
                return false;
            }
            if (str.equalsIgnoreCase(t0VarArr[i].c())) {
                return true;
            }
            i++;
        }
    }

    private boolean D(String str) {
        return "push_start_app".equals(str) || "push_start_browser".equals(str);
    }

    private boolean E(String str) {
        return "rw_select".equals(str) || "rw_get_keyversion".equals(str) || "rw_read".equals(str) || "rw_write".equals(str);
    }

    private ArrayList F() {
        String strP = com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(this.h), 2);
        h0 h0Var = (h0) this.i.get(r2.size() - 1);
        ArrayList arrayList = new ArrayList();
        if ("0".equals(h0Var.c())) {
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("rescode", "0000"));
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("errpos", "000"));
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("mode", this.f.c()));
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("resnum", strP));
        } else {
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("rescode", h0Var.c()));
            if ("0100".equals(h0Var.c()) || "0200".equals(h0Var.c())) {
                arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("errpos", "000"));
            } else {
                arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("errpos", "c" + strP));
            }
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("mode", this.f.c()));
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("resnum", strP));
        }
        int i = 0;
        while (i < this.i.size()) {
            h0 h0Var2 = (h0) this.i.get(i);
            StringBuilder sb = new StringBuilder();
            sb.append("res");
            i++;
            sb.append(com.felicanetworks.mfw.a.cmn.b1.P(Integer.toString(i), 2));
            String string = sb.toString();
            arrayList.add(new com.felicanetworks.mfw.a.cmn.i0(string, h0Var2.b()));
            if ("0".equals(h0Var2.c())) {
                arrayList.add(new com.felicanetworks.mfw.a.cmn.i0(string + ".result", "0"));
            } else if ("0100".equals(h0Var2.c()) || "0200".equals(h0Var2.c())) {
                arrayList.add(new com.felicanetworks.mfw.a.cmn.i0(string + ".result", "0"));
            } else {
                arrayList.add(new com.felicanetworks.mfw.a.cmn.i0(string + ".result", "1"));
            }
            ArrayList arrayListD = h0Var2.d();
            for (int i2 = 0; i2 < arrayListD.size(); i2++) {
                com.felicanetworks.mfw.a.cmn.i0 i0Var = (com.felicanetworks.mfw.a.cmn.i0) arrayListD.get(i2);
                arrayList.add(new com.felicanetworks.mfw.a.cmn.i0(string + "." + i0Var.a(), i0Var.b()));
            }
        }
        return arrayList;
    }

    private ArrayList G(String str, String str2) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("rescode", str));
        if (str2 == null) {
            str2 = "000";
        }
        arrayList.add(new com.felicanetworks.mfw.a.cmn.i0("errpos", str2));
        return arrayList;
    }

    private void o(com.felicanetworks.mfw.a.cmn.y0 y0Var) throws Throwable {
        ArrayList arrayList;
        String strSubstring;
        this.k++;
        this.f = new i();
        ArrayList arrayList2 = new ArrayList();
        ArrayList arrayList3 = new ArrayList();
        int iH = H(y0Var.b(), arrayList2, arrayList3);
        if (iH != 0) {
            throw new l(this, iH);
        }
        for (int i = 0; i < arrayList2.size(); i++) {
            String strSubstring2 = (String) arrayList2.get(i);
            int iIndexOf = strSubstring2.indexOf(" ");
            if (iIndexOf >= 0) {
                strSubstring2 = strSubstring2.substring(0, iIndexOf);
            }
            if (!"permit".equals(strSubstring2) && !"url".equals(strSubstring2) && !"mode".equals(strSubstring2)) {
                throw new l(this, 2211);
            }
        }
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        while (i2 < arrayList2.size()) {
            String str4 = (String) arrayList2.get(i2);
            int iIndexOf2 = str4.indexOf(" ");
            if (iIndexOf2 < 0) {
                arrayList = arrayList2;
                strSubstring = null;
            } else {
                arrayList = arrayList2;
                String strSubstring3 = str4.substring(0, iIndexOf2);
                strSubstring = str4.substring(iIndexOf2 + 1);
                str4 = strSubstring3;
            }
            String str5 = strSubstring;
            if ("permit".equals(str4)) {
                if (i4 != 0) {
                    throw new l(this, 2212);
                }
                i4++;
                str = str5;
            } else if ("mode".equals(str4)) {
                if (i5 != 0) {
                    throw new l(this, 2212);
                }
                i5++;
                str3 = str5;
            } else if (!"url".equals(str4)) {
                continue;
            } else {
                if (i3 != 0) {
                    throw new l(this, 2212);
                }
                i3++;
                str2 = str5;
            }
            i2++;
            arrayList2 = arrayList;
        }
        if (i3 == 0) {
            throw new l(this, 2241);
        }
        if (this.k != 1) {
            u(str2);
        }
        if (this.k > 1 && i4 != 0) {
            throw new l(this, 2224);
        }
        if (i5 == 0) {
            throw new l(this, 2231);
        }
        if (str3 == null || str3.indexOf(",") != -1) {
            throw new l(this, 2232);
        }
        if (!"farewell".equals(str3) && !"felica_card".equals(str3) && !"felica_rw".equals(str3)) {
            throw new l(this, 2233);
        }
        this.f.f(str);
        this.f.e(str3);
        this.f.g(str2);
        if (arrayList3.size() == 0 && !"farewell".equals(this.f.c())) {
            throw new l(this, 2202);
        }
        if (this.k >= com.felicanetworks.mfw.a.cmn.p0.a().b("max.commanddata.count") && !"farewell".equals(this.f.c())) {
            throw new k(this, 3100, null);
        }
        for (int i6 = 0; i6 < arrayList3.size(); i6++) {
            String str6 = (String) arrayList3.get(i6);
            if (str6.indexOf(" ") < 0) {
                q0 q0Var = new q0();
                q0Var.c(str6);
                q0Var.d(new String[0]);
                this.f.a(q0Var);
            } else {
                String strSubstring4 = str6.substring(0, str6.indexOf(" "));
                String strSubstring5 = str6.substring(str6.indexOf(" ") + 1);
                q0 q0Var2 = new q0();
                q0Var2.c(strSubstring4);
                q0Var2.d(com.felicanetworks.mfw.a.cmn.b1.e(strSubstring5, ","));
                this.f.a(q0Var2);
            }
        }
        if (("felica_card".equals(this.f.c()) && this.f.b().size() > 20) || (("felica_rw".equals(this.f.c()) && this.f.b().size() > 20) || ("farewell".equals(this.f.c()) && this.f.b().size() > 0))) {
            throw new l(this, 2239);
        }
    }

    private void q() throws k {
        int i;
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int i4 = 1;
            if (i3 >= this.f.b().size()) {
                if ("felica_card".equals(this.f.c())) {
                    for (int i5 = 0; i5 < this.f.b().size(); i5++) {
                        if (!com.felicanetworks.mfw.a.cmn.b1.c(new String[]{"select", "get_keyversion", "read", "write", "start", "onfelica_start"}, ((q0) this.f.b().get(i5)).a())) {
                            throw new k(this, 2403, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i5 + 1), 2));
                        }
                    }
                } else {
                    for (int i6 = 0; i6 < this.f.b().size(); i6++) {
                        if (!com.felicanetworks.mfw.a.cmn.b1.c(new String[]{"rw_select", "rw_get_keyversion", "rw_read", "rw_write", "push_start_app", "push_start_browser"}, ((q0) this.f.b().get(i6)).a())) {
                            throw new k(this, 2403, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i6 + 1), 2));
                        }
                    }
                }
                String[] strArr = {"select", "get_keyversion", "read", "write"};
                String[] strArr2 = {"start", "onfelica_start"};
                String[] strArr3 = {"rw_select", "rw_get_keyversion", "rw_read", "rw_write", "push_start_app", "push_start_browser"};
                String strP = com.felicanetworks.mfw.a.cmn.b1.P(Integer.toBinaryString(this.c), 3);
                int i7 = 0;
                while (i7 < this.f.b().size()) {
                    String strA = ((q0) this.f.b().get(i7)).a();
                    int i8 = ("1".equals(strP.substring(i2, i4)) && com.felicanetworks.mfw.a.cmn.b1.c(strArr3, strA)) ? i4 : 0;
                    if ("1".equals(strP.substring(i4, 2)) && com.felicanetworks.mfw.a.cmn.b1.c(strArr2, strA)) {
                        i8 = i4;
                    }
                    if ("1".equals(strP.substring(2, 3)) && com.felicanetworks.mfw.a.cmn.b1.c(strArr, strA)) {
                        i8 = 1;
                    }
                    if (i8 == 0) {
                        throw new k(this, 2404, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i7 + 1), 2));
                    }
                    i7++;
                    i2 = 0;
                    i4 = 1;
                }
                for (int i9 = 0; i9 < this.f.b().size(); i9++) {
                    if (!com.felicanetworks.mfw.a.cmn.b1.c(q, ((q0) this.f.b().get(i9)).a())) {
                        throw new k(this, 2405, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i9 + 1), 2));
                    }
                }
                String strA2 = ((q0) this.f.b().get(0)).a();
                if (!"select".equals(strA2) && !"rw_select".equals(strA2) && !"push_start_app".equals(strA2) && !"push_start_browser".equals(strA2)) {
                    throw new k(this, 2406, "c01");
                }
                int i10 = 0;
                for (int i11 = 0; i11 < this.f.b().size(); i11++) {
                    String strA3 = ((q0) this.f.b().get(i11)).a();
                    if (("start".equals(strA3) || "onfelica_start".equals(strA3)) && (i10 = i10 + 1) > 1) {
                        throw new k(this, 2407, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i11 + 1), 2));
                    }
                }
                int i12 = 0;
                int i13 = 0;
                for (int i14 = 0; i14 < this.f.b().size(); i14++) {
                    String strA4 = ((q0) this.f.b().get(i14)).a();
                    if ("push_start_app".equals(strA4) || "push_start_browser".equals(strA4)) {
                        i12++;
                        if (i12 > 1) {
                            throw new k(this, 2408, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i14 + 1), 2));
                        }
                        i13 = i14;
                    }
                }
                if (i12 > 0 && (i = i13 + 1) != this.f.b().size()) {
                    throw new k(this, 2409, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i), 2));
                }
                for (int i15 = 0; i15 < this.f.b().size(); i15++) {
                    q0 q0Var = (q0) this.f.b().get(i15);
                    int iC = y(q0Var.a()).c(q0Var.b());
                    if (iC != 0) {
                        throw new k(this, iC, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i15 + 1), 2));
                    }
                }
                return;
            }
            if (!com.felicanetworks.mfw.a.cmn.b1.c(new String[]{"select", "get_keyversion", "read", "write", "start", "onfelica_start", "rw_select", "rw_get_keyversion", "rw_read", "rw_write", "push_start_app", "push_start_browser"}, ((q0) this.f.b().get(i3)).a())) {
                throw new k(this, 2402, "c" + com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i3 + 1), 2));
            }
            i3++;
        }
    }

    private com.felicanetworks.mfw.a.cmn.i0[] r(com.felicanetworks.mfw.a.cmn.i0[] i0VarArr) {
        int i;
        String strB = null;
        String strB2 = null;
        String strB3 = null;
        String strB4 = null;
        String strB5 = null;
        if (i0VarArr != null) {
            i = 0;
            for (com.felicanetworks.mfw.a.cmn.i0 i0Var : i0VarArr) {
                if ("rescode".equals(i0Var.a())) {
                    i++;
                    strB = i0Var.b();
                } else if ("queryfincode".equals(i0Var.a())) {
                    i++;
                    strB3 = i0Var.b();
                } else if ("pflag".equals(i0Var.a())) {
                    i++;
                    strB2 = i0Var.b();
                } else if ("clientfincode".equals(i0Var.a())) {
                    i++;
                    strB4 = i0Var.b();
                } else if ("desc".equals(i0Var.a())) {
                    i++;
                    strB5 = i0Var.b();
                }
            }
        } else {
            i = 0;
        }
        if (i != 5) {
            return null;
        }
        if ((!"OK".equals(strB) || !"0".equals(strB2) || !com.felicanetworks.mfw.a.cmn.b1.q(strB3, 0) || !com.felicanetworks.mfw.a.cmn.b1.r(strB4, 1, 4) || com.felicanetworks.mfw.a.cmn.b1.J(strB5).length > 1024) && ((!"OK".equals(strB) || !"1".equals(strB2) || !com.felicanetworks.mfw.a.cmn.b1.q(strB3, 0) || !com.felicanetworks.mfw.a.cmn.b1.q(strB4, 0) || !com.felicanetworks.mfw.a.cmn.b1.q(strB5, 0)) && (!"NG".equals(strB) || !com.felicanetworks.mfw.a.cmn.b1.q(strB2, 0) || !com.felicanetworks.mfw.a.cmn.b1.q(strB3, 4) || !com.felicanetworks.mfw.a.cmn.b1.q(strB4, 0) || !com.felicanetworks.mfw.a.cmn.b1.q(strB5, 0)))) {
            return null;
        }
        if ((strB3.length() == 0 || com.felicanetworks.mfw.a.cmn.b1.i(strB3)) && ((strB4.length() == 0 || com.felicanetworks.mfw.a.cmn.b1.i(strB4)) && (strB5.length() == 0 || com.felicanetworks.mfw.a.cmn.b1.o(strB5)))) {
            return i0VarArr;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:57:0x00e7, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private int s(java.util.List r13) {
        /*
            Method dump skipped, instruction units count: 236
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: a.a.a.a.c.f0.s(java.util.List):int");
    }

    private int t(List list) {
        for (int i = 0; i < list.size(); i++) {
            com.felicanetworks.mfw.a.cmn.i0 i0Var = (com.felicanetworks.mfw.a.cmn.i0) list.get(i);
            if ("iccode".equals(i0Var.a())) {
                if (!com.felicanetworks.mfw.a.cmn.f0.d(Long.parseLong(i0Var.b(), 16))) {
                    return 4611;
                }
                this.j = true;
            }
        }
        return 0;
    }

    private void u(String str) throws l {
        if (str == null || str.indexOf(",") != -1) {
            throw new l(this, 2242);
        }
        if ("".equals(str)) {
            throw new l(this, 2243);
        }
        if (com.felicanetworks.mfw.a.cmn.b1.J(str).length < 1 || 255 < com.felicanetworks.mfw.a.cmn.b1.J(str).length) {
            throw new l(this, 2244);
        }
        if (!com.felicanetworks.mfw.a.cmn.b1.D(str)) {
            throw new l(this, 2246);
        }
        if (!this.o.k().Q(str, this.d)) {
            throw new l(this, 2247);
        }
    }

    private int v(List list) {
        if ("rw_select".equals(this.g)) {
            return s(list);
        }
        if ("select".equals(this.g)) {
            return t(list);
        }
        if ("start".equals(this.g)) {
            if (list == null) {
                return 4310;
            }
            if (list.size() == 0) {
                return 4319;
            }
        } else if ("onfelica_start".equals(this.g)) {
            if (list == null) {
                return 4330;
            }
            if (list.size() == 0) {
                return 4339;
            }
        }
        return 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String[] w(String str) {
        int i;
        String strB;
        String strB2;
        String strB3;
        String strB4;
        String strB5;
        com.felicanetworks.mfw.a.cmn.i0[] i0VarArrF = com.felicanetworks.mfw.a.cmn.b1.f(str);
        if (i0VarArrF != null) {
            i = 0;
            strB = null;
            strB2 = null;
            strB3 = null;
            strB4 = null;
            strB5 = null;
            for (com.felicanetworks.mfw.a.cmn.i0 i0Var : i0VarArrF) {
                if ("rescode".equals(i0Var.a())) {
                    i++;
                    strB = i0Var.b();
                } else if ("surl".equals(i0Var.a())) {
                    i++;
                    strB5 = i0Var.b();
                } else if ("trnid".equals(i0Var.a())) {
                    i++;
                    strB3 = i0Var.b();
                } else if ("queryid".equals(i0Var.a())) {
                    i++;
                    strB4 = i0Var.b();
                } else if ("authfincode".equals(i0Var.a())) {
                    i++;
                    strB2 = i0Var.b();
                }
            }
        } else {
            i = 0;
            strB = null;
            strB2 = null;
            strB3 = null;
            strB4 = null;
            strB5 = null;
        }
        if (i != 5) {
            return new String[]{String.valueOf(4322), null, null, null};
        }
        if ((!"OK".equals(strB) || !com.felicanetworks.mfw.a.cmn.b1.q(strB2, 0) || !com.felicanetworks.mfw.a.cmn.b1.q(strB3, 14) || !com.felicanetworks.mfw.a.cmn.b1.q(strB4, 32) || !com.felicanetworks.mfw.a.cmn.b1.r(strB5, 1, 255)) && (!"NG".equals(strB) || !com.felicanetworks.mfw.a.cmn.b1.q(strB2, 4) || !com.felicanetworks.mfw.a.cmn.b1.q(strB3, 0) || !com.felicanetworks.mfw.a.cmn.b1.q(strB4, 0) || !com.felicanetworks.mfw.a.cmn.b1.q(strB5, 0))) {
            return new String[]{String.valueOf(4322), null, null, null};
        }
        if ((strB2.length() != 0 && !com.felicanetworks.mfw.a.cmn.b1.i(strB2)) || ((strB3.length() != 0 && !com.felicanetworks.mfw.a.cmn.b1.i(strB3)) || ((strB4.length() != 0 && !com.felicanetworks.mfw.a.cmn.b1.i(strB4)) || (strB5.length() != 0 && !com.felicanetworks.mfw.a.cmn.b1.D(strB5))))) {
            return new String[]{String.valueOf(4322), null, null, null};
        }
        if ("NG".equals(strB)) {
            return new String[]{String.valueOf(4321), null, null, strB2};
        }
        return new String[]{"0", strB5, "trnid=" + strB3 + "&queryid=" + strB4, null};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public h0 x(int i, com.felicanetworks.mfw.a.cmn.e eVar) {
        h0 h0Var = new h0();
        h0Var.e(this.g);
        h0Var.f(String.valueOf(i));
        if (eVar.a() != 0) {
            h0Var.a(new com.felicanetworks.mfw.a.cmn.i0("status_flag1", com.felicanetworks.mfw.a.cmn.b1.P(Integer.toHexString(eVar.a()).toUpperCase(Locale.US), 2)));
        }
        if (eVar.b() != 0) {
            h0Var.a(new com.felicanetworks.mfw.a.cmn.i0("status_flag2", com.felicanetworks.mfw.a.cmn.b1.P(Integer.toHexString(eVar.b()).toUpperCase(Locale.US), 2)));
        }
        return h0Var;
    }

    private m y(String str) {
        int i = 0;
        while (true) {
            m[] mVarArr = this.m;
            if (i >= mVarArr.length) {
                return null;
            }
            m mVar = mVarArr[i];
            if (mVar.a().equals(str)) {
                return mVar;
            }
            i++;
        }
    }

    public String A() {
        i iVar = this.f;
        if (iVar == null) {
            return null;
        }
        return iVar.d();
    }

    public int H(String str, ArrayList arrayList, ArrayList arrayList2) throws Throwable {
        if (str == null || arrayList == null || arrayList2 == null) {
            throw new com.felicanetworks.mfw.a.cmn.c1(f0.class, "purseRespData", "Illegal argument. [cmdDataStr = " + str + " headerLineStrList = " + arrayList + "cmdLineStrList = " + arrayList2 + "]");
        }
        arrayList.clear();
        arrayList2.clear();
        ArrayList arrayListD = com.felicanetworks.mfw.a.cmn.b1.d(str);
        boolean z = false;
        for (int i = 0; i < arrayListD.size(); i++) {
            String str2 = (String) arrayListD.get(i);
            if ("".equals(str2)) {
                if (z) {
                    return 2201;
                }
                z = true;
            } else if (z) {
                arrayList2.add(str2);
            } else {
                arrayList.add(str2);
            }
        }
        return (z && arrayList2.size() == 0) ? 2201 : 0;
    }

    public void I(int i) {
        q0 q0Var = (q0) this.f.b().get(this.h - 1);
        if (y(q0Var.a()) instanceof a0) {
            ((a0) y(q0Var.a())).j(i);
        } else if (y(q0Var.a()) instanceof t) {
            ((t) y(q0Var.a())).j(i);
        } else if (y(q0Var.a()) instanceof u) {
            ((u) y(q0Var.a())).j(i);
        }
    }

    public void J(g0 g0Var) {
        this.f25a = g0Var;
    }

    public void K(x0 x0Var, com.felicanetworks.mfw.a.cmn.y0 y0Var) throws Throwable {
        this.b = null;
        int iA = 0;
        this.c = 0;
        this.e = null;
        this.f = null;
        this.g = null;
        this.h = 0;
        this.i.clear();
        this.j = false;
        this.k = 0;
        this.l = null;
        p0 p0Var = (p0) x0Var.d("0002");
        this.e = new t0[p0Var.f()];
        for (int i = 0; i < p0Var.f(); i++) {
            this.e[i] = p0Var.c(i);
        }
        this.c = Integer.parseInt(com.felicanetworks.mfw.a.cmn.b1.K(p0Var.d()), 16) & 7;
        this.d = ((g) x0Var.d("0003")).b();
        while (true) {
            this.f = null;
            this.g = null;
            this.h = 0;
            this.i.clear();
            this.j = false;
            ((x) y("rw_select")).i();
            ((x) y("push_start_app")).i();
            ((x) y("push_start_browser")).i();
            this.f25a.e(-1);
            try {
                o(y0Var);
                this.f25a.e(0);
            } catch (k e) {
                this.f25a.e(e.a());
                if (e.a() == 3100) {
                    this.f25a.b(-1);
                    try {
                        new r(this).b("I007", r1.a(G(String.valueOf(e.a()), e.b())), this.f.d());
                        this.f25a.b(0);
                    } catch (o e2) {
                        this.f25a.b(e2.a());
                    }
                    iA = e.a();
                    break;
                }
                String strA = r1.a(G(String.valueOf(e.a()), e.b()));
                this.f25a.b(-1);
                try {
                    y0Var = new r(this).b("I007", strA, this.f.d());
                    this.f25a.b(0);
                } catch (o e3) {
                    this.f25a.b(e3.a());
                    iA = e3.a();
                }
            } catch (l e4) {
                this.f25a.e(e4.a());
                iA = e4.a();
            }
            if ("farewell".equals(this.f.c())) {
                break;
            }
            this.f25a.a(-1);
            try {
                q();
                this.f25a.a(0);
                while (true) {
                    if (this.h == this.f.b().size()) {
                        break;
                    }
                    this.h++;
                    q0 q0Var = (q0) this.f.b().get(this.h - 1);
                    String strA2 = q0Var.a();
                    this.g = strA2;
                    h0 h0VarB = y(strA2).b(q0Var.b(), this.n);
                    if (h0VarB != null) {
                        if (E(this.g)) {
                            if ("0100".equals(h0VarB.c()) || "0200".equals(h0VarB.c()) || String.valueOf(4210).equals(h0VarB.c()) || String.valueOf(4218).equals(h0VarB.c()) || String.valueOf(4219).equals(h0VarB.c()) || String.valueOf(4211).equals(h0VarB.c())) {
                                this.f25a.c(Integer.parseInt(h0VarB.c()), 102, this.o.g().q(R.string.msg_catch_stop));
                            } else {
                                this.f25a.c(Integer.parseInt(h0VarB.c()), 101, null);
                            }
                        } else if (!D(this.g)) {
                            this.f25a.c(Integer.parseInt(h0VarB.c()), 100, null);
                        } else if (this.h == 1) {
                            this.f25a.c(Integer.parseInt(h0VarB.c()), 106, this.o.g().q(R.string.msg_catch_stop));
                        } else {
                            this.f25a.c(Integer.parseInt(h0VarB.c()), 104, this.o.g().q(R.string.msg_catch_stop));
                        }
                        this.i.add(h0VarB);
                    } else {
                        ArrayList arrayListB = this.n.b();
                        int iV = v(arrayListB);
                        if (iV != 0) {
                            if (E(this.g)) {
                                if (iV == 4711 || iV == 4712) {
                                    this.f25a.c(iV, 102, this.o.g().q(R.string.msg_catch_stop));
                                } else {
                                    this.f25a.c(iV, 101, null);
                                }
                            } else if (!D(this.g)) {
                                this.f25a.c(iV, 100, null);
                            } else if (this.h == 1) {
                                this.f25a.c(iV, 106, this.o.g().q(R.string.msg_catch_stop));
                            } else {
                                this.f25a.c(iV, 104, this.o.g().q(R.string.msg_catch_stop));
                            }
                            h0 h0Var = new h0();
                            h0Var.e(this.g);
                            h0Var.f(String.valueOf(iV));
                            if (arrayListB != null) {
                                for (int i2 = 0; i2 < arrayListB.size(); i2++) {
                                    h0Var.a((com.felicanetworks.mfw.a.cmn.i0) arrayListB.get(i2));
                                }
                            }
                            this.i.add(h0Var);
                        } else {
                            h0 h0Var2 = new h0();
                            h0Var2.e(this.g);
                            h0Var2.f("0");
                            for (int i3 = 0; i3 < arrayListB.size(); i3++) {
                                h0Var2.a((com.felicanetworks.mfw.a.cmn.i0) arrayListB.get(i3));
                            }
                            this.i.add(h0Var2);
                            if ("onfelica_start".equals(this.g)) {
                                String str = ((q0) this.f.b().get(this.h - 1)).b()[1];
                                if (!"".equals(str)) {
                                    try {
                                        com.felicanetworks.mfw.a.cmn.i0[] i0VarArrR = r(com.felicanetworks.mfw.a.cmn.b1.f(new r(this).b("I011", this.l, str).b()));
                                        if (i0VarArrR == null) {
                                            List list = this.i;
                                            ((h0) list.get(list.size() - 1)).f(String.valueOf(4341));
                                            this.f25a.c(4341, 100, null);
                                            break;
                                        } else {
                                            List list2 = this.i;
                                            h0 h0Var3 = (h0) list2.get(list2.size() - 1);
                                            for (com.felicanetworks.mfw.a.cmn.i0 i0Var : i0VarArrR) {
                                                h0Var3.a(i0Var);
                                            }
                                        }
                                    } catch (o e5) {
                                        this.f25a.c(e5.a(), 100, null);
                                        List list3 = this.i;
                                        ((h0) list3.get(list3.size() - 1)).f(String.valueOf(e5.a()));
                                    }
                                }
                            }
                            if ("rw_select".equals(this.g)) {
                                this.f25a.c(0, 101, "");
                            } else if (E(this.g)) {
                                this.f25a.c(0, 101, null);
                            } else if (!D(this.g)) {
                                this.f25a.c(0, 100, null);
                            } else if (this.h == 1) {
                                this.f25a.c(0, 105, "");
                            } else {
                                this.f25a.c(0, 103, "");
                            }
                        }
                    }
                }
                this.o.e().a();
                this.f25a.b(-1);
                try {
                    y0Var = new r(this).b("I007", r1.a(F()), this.f.d());
                    this.f25a.b(0);
                } catch (o e6) {
                    this.f25a.b(e6.a());
                    iA = e6.a();
                }
            } catch (k e7) {
                this.f25a.a(e7.a());
                String strA3 = r1.a(G(String.valueOf(e7.a()), e7.b()));
                this.f25a.b(-1);
                try {
                    y0Var = new r(this).b("I007", strA3, this.f.d());
                    this.f25a.b(0);
                } catch (o e8) {
                    this.f25a.b(e8.a());
                    iA = e8.a();
                }
            }
        }
        this.f25a.f(iA, null);
    }

    public void L() {
        i iVar = this.f;
        if (iVar == null || iVar.b().size() == 0) {
            return;
        }
        if ("rw_select".equals(this.g) || "push_start_app".equals(this.g) || "push_start_browser".equals(this.g)) {
            ((x) y(((q0) this.f.b().get(this.h - 1)).a())).k();
        }
    }

    public boolean p() {
        i iVar = this.f;
        if (iVar == null || iVar.b().size() == 0) {
            return false;
        }
        if ("rw_select".equals(this.g) || "push_start_app".equals(this.g) || "push_start_browser".equals(this.g)) {
            return ((x) y(((q0) this.f.b().get(this.h - 1)).a())).e();
        }
        return false;
    }

    public boolean z() {
        return this.p;
    }
}
