package a.a.a.a.c;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: BizResOptr.java */
/* JADX INFO: loaded from: classes.dex */
public class e implements com.felicanetworks.mfw.a.cmn.x0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private byte[] f22a;
    private List b = new ArrayList();
    private List c = new ArrayList();
    private f d;
    private com.felicanetworks.mfw.a.cmn.h0 e;
    private m1 f;

    public e(com.felicanetworks.mfw.a.cmn.h0 h0Var) {
        this.e = h0Var;
    }

    private void A(n1 n1Var) {
        byte[] bArr = {0};
        int iE = n1Var.e();
        int iD = n1Var.d();
        int iF = n1Var.f();
        String strK = n1Var.k();
        int i = n1Var.i();
        String strH = n1Var.h();
        int iJ = n1Var.j();
        String[] strArrG = n1Var.g();
        if (iE == -1 || iD == -1 || iF == -1 || strK == null || i == -1 || strH == null || iJ == -1) {
            e("2");
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(bArr);
            byteArrayOutputStream.write(y(iE, 8));
            byteArrayOutputStream.write(y(iD, 8));
            byteArrayOutputStream.write(y(iF, 8));
            byteArrayOutputStream.write(h(strK.getBytes(), 12));
            byteArrayOutputStream.write(y(i, 3));
            byteArrayOutputStream.write(h(strH.getBytes(), 255));
            byteArrayOutputStream.write(y(iJ, 4));
            for (String str : strArrG) {
                byteArrayOutputStream.write(h(str.getBytes(), 12));
            }
            byteArrayOutputStream.write(new byte[2400 - (strArrG.length * 12)]);
        } catch (IOException unused) {
        }
        z("2", byteArrayOutputStream.toByteArray());
    }

    private void B(o1 o1Var) {
        int iE = o1Var.e();
        String strD = o1Var.d();
        if (iE == -1 || strD == null) {
            e("3");
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(y(iE, 1));
            byteArrayOutputStream.write(i(strD.getBytes(), 1024, (byte) 32));
        } catch (IOException unused) {
        }
        z("3", byteArrayOutputStream.toByteArray());
    }

    private void C(p1 p1Var) {
        byte[] bArr = {0};
        String strI = p1Var.i();
        int iG = p1Var.g();
        int iF = p1Var.f();
        int iE = p1Var.e();
        String strH = p1Var.h();
        String strD = p1Var.d();
        if (strI == null || iG == -1 || iF == -1 || iE == -1 || strH == null || strD == null) {
            e("1");
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            byteArrayOutputStream.write(bArr);
            byteArrayOutputStream.write(h(strI.getBytes(), 12));
            byteArrayOutputStream.write(y(iG, 8));
            byteArrayOutputStream.write(y(iF, 8));
            byteArrayOutputStream.write(y(iE, 8));
            byteArrayOutputStream.write(h(strH.getBytes(), 12));
            byteArrayOutputStream.write(h(strD.getBytes(), 30));
        } catch (IOException unused) {
        }
        z("1", byteArrayOutputStream.toByteArray());
    }

    private void e(String str) {
        if (this.e.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        com.felicanetworks.mfw.a.cmn.w0.l(str, new byte[1025], this, this.e.g().h());
    }

    private byte[] f(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i2];
        System.arraycopy(bArr, i, bArr2, 0, i2);
        return bArr2;
    }

    private void g() {
        if (this.e.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        q1 q1Var = (q1) this.c.get(0);
        String strA = q1Var.a();
        if (!q1Var.c()) {
            if (strA.equals("R001")) {
                C((p1) q1Var.b());
            } else if (strA.equals("R101")) {
                A((n1) q1Var.b());
            }
            if (strA.equals("R201")) {
                B((o1) q1Var.b());
                return;
            }
            return;
        }
        try {
            if (strA.equals("R001")) {
                com.felicanetworks.mfw.a.cmn.w0.i("1", this, this.e.g().h());
            } else if (strA.equals("R101")) {
                com.felicanetworks.mfw.a.cmn.w0.i("2", this, this.e.g().h());
            } else if (strA.equals("R201")) {
                com.felicanetworks.mfw.a.cmn.w0.i("3", this, this.e.g().h());
            }
        } catch (com.felicanetworks.mfw.a.cmn.u0 unused) {
            String strA2 = ((q1) this.c.get(0)).a();
            this.c.remove(0);
            this.d.b(strA2, -1);
            this.d = null;
        }
    }

    private byte[] h(byte[] bArr, int i) {
        return i(bArr, i, (byte) 0);
    }

    private byte[] i(byte[] bArr, int i, byte b) {
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        for (int length = bArr.length; length < i; length++) {
            bArr2[length] = b;
        }
        return bArr2;
    }

    private void j(com.felicanetworks.mfw.a.cmn.x0 x0Var) {
        byte[] bArrI = com.felicanetworks.mfw.a.cmn.b1.I(com.felicanetworks.mfw.a.cmn.p0.a().c("perm.area.format.versionup"));
        com.felicanetworks.mfw.a.cmn.w0.d(this.e.g().h());
        com.felicanetworks.mfw.a.cmn.w0.l("0", bArrI, x0Var, this.e.g().h());
    }

    private int k(byte[] bArr, int i, int i2) {
        byte[] bArrF = f(bArr, i, i2);
        if (s(bArrF)) {
            return -1;
        }
        return Integer.parseInt(new String(t(bArrF)));
    }

    private l1 l(byte[] bArr) {
        o1 o1Var = new o1();
        o1Var.c("R201");
        o1Var.g(f(bArr, 0, 1)[0]);
        o1Var.f(p(bArr, 1, 1024));
        return o1Var;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public l1 m(byte[] bArr) {
        m1 m1Var = new m1();
        m1Var.c("R000");
        m1Var.e(bArr[0]);
        return m1Var;
    }

    private l1 n(byte[] bArr) {
        n1 n1Var = new n1();
        n1Var.c("R101");
        n1Var.m(k(bArr, 1, 8));
        n1Var.l(k(bArr, 9, 8));
        n1Var.n(k(bArr, 17, 8));
        n1Var.s(p(bArr, 25, 12));
        n1Var.q(k(bArr, 37, 3));
        n1Var.p(p(bArr, 40, 255));
        int iK = k(bArr, 295, 4);
        if (iK != -1) {
            n1Var.r(iK);
            String[] strArr = new String[iK];
            byte[] bArrF = f(bArr, 299, 2400);
            for (int i = 0; i < iK; i++) {
                strArr[i] = p(bArrF, i * 12, 12);
            }
            n1Var.o(strArr);
        }
        return n1Var;
    }

    private l1 o(byte[] bArr) {
        p1 p1Var = new p1();
        p1Var.c("R001");
        p1Var.o(p(bArr, 1, 12));
        p1Var.m(k(bArr, 13, 8));
        p1Var.l(k(bArr, 21, 8));
        p1Var.k(k(bArr, 29, 8));
        p1Var.n(p(bArr, 37, 12));
        p1Var.j(p(bArr, 49, 30));
        return p1Var;
    }

    private String p(byte[] bArr, int i, int i2) {
        byte[] bArrF = f(bArr, i, i2);
        if (s(bArrF)) {
            return null;
        }
        return new String(t(bArrF));
    }

    private boolean s(byte[] bArr) {
        for (byte b : bArr) {
            if (b != 0) {
                return false;
            }
        }
        return true;
    }

    private byte[] t(byte[] bArr) {
        for (int i = 0; i < bArr.length; i++) {
            if (bArr[i] == 0) {
                byte[] bArr2 = new byte[i];
                System.arraycopy(bArr, 0, bArr2, 0, i);
                return bArr2;
            }
        }
        return bArr;
    }

    private byte[] y(int i, int i2) {
        return com.felicanetworks.mfw.a.cmn.b1.P(String.valueOf(i), i2).getBytes();
    }

    private void z(String str, byte[] bArr) {
        if (this.e.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        com.felicanetworks.mfw.a.cmn.w0.l(str, bArr, this, this.e.g().h());
    }

    @Override // com.felicanetworks.mfw.a.cmn.x0
    public void a() {
        if (this.e.g().u()) {
            com.felicanetworks.mfw.a.cmn.b0.v();
            throw null;
        }
        q1 q1Var = (q1) this.c.get(0);
        this.c.remove(0);
        this.d.a(q1Var.a(), 0);
        if (this.c.size() < 1) {
            this.d = null;
        } else {
            g();
        }
    }

    @Override // com.felicanetworks.mfw.a.cmn.x0
    public void b(byte[] bArr, int i) {
        String strA = ((q1) this.c.get(0)).a();
        this.f22a = bArr;
        this.c.remove(0);
        this.d.b(strA, 0);
        if (this.c.size() < 1) {
            this.d = null;
        } else {
            g();
        }
    }

    public l1 q(String str) {
        if (str.equals("R001")) {
            p1 p1Var = (p1) o(this.f22a);
            p1Var.b(false);
            return p1Var;
        }
        if (str.equals("R101")) {
            n1 n1Var = (n1) n(this.f22a);
            n1Var.b(false);
            return n1Var;
        }
        if (!str.equals("R201")) {
            return null;
        }
        o1 o1Var = (o1) l(this.f22a);
        o1Var.b(false);
        return o1Var;
    }

    public void r() {
        d dVar = new d(this);
        try {
            com.felicanetworks.mfw.a.cmn.w0.i("0", dVar, this.e.g().h());
            if (com.felicanetworks.mfw.a.cmn.b1.I(com.felicanetworks.mfw.a.cmn.p0.a().c("perm.area.format.versionup"))[0] != this.f.d()) {
                j(dVar);
            }
        } catch (com.felicanetworks.mfw.a.cmn.u0 unused) {
            j(dVar);
        }
    }

    public void u(f fVar) {
        this.d = fVar;
    }

    public void v(String str) {
        q1 q1Var = new q1();
        q1Var.d(str);
        q1Var.e(true);
        this.b.add(q1Var);
    }

    public void w(String str, l1 l1Var) {
        q1 q1Var = new q1();
        q1Var.d(str);
        q1Var.f(l1Var);
        this.b.add(q1Var);
    }

    public void x() {
        if (this.b.size() > 0) {
            this.c.clear();
            this.c.addAll(this.b);
            this.b.clear();
        }
        g();
    }
}
