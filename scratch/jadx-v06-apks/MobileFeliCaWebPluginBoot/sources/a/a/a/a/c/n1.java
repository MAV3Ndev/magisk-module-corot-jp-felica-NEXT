package a.a.a.a.c;

/* JADX INFO: compiled from: ResDataPermitRvction.java */
/* JADX INFO: loaded from: classes.dex */
public class n1 extends l1 {
    private int b = -1;
    private int c = -1;
    private int d = -1;
    private String e = null;
    private int f = -1;
    private String g = null;
    private int h = -1;
    private String[] i = new String[0];

    public int d() {
        return this.c;
    }

    public int e() {
        return this.b;
    }

    public int f() {
        return this.d;
    }

    public String[] g() {
        return this.i;
    }

    public String h() {
        return this.g;
    }

    public int i() {
        return this.f;
    }

    public int j() {
        return this.h;
    }

    public String k() {
        return this.e;
    }

    public void l(int i) {
        this.c = i;
    }

    public void m(int i) {
        this.b = i;
    }

    public void n(int i) {
        this.d = i;
    }

    public void o(String[] strArr) {
        this.i = strArr;
    }

    public void p(String str) {
        this.g = str;
    }

    public void q(int i) {
        this.f = i;
    }

    public void r(int i) {
        this.h = i;
    }

    public void s(String str) {
        this.e = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataPermitRvction offlineRvctionNumLimit = " + this.b);
        stringBuffer.append(", offlineRvctionNum = " + this.c);
        stringBuffer.append(", offlineRvctionTerm = " + this.d);
        stringBuffer.append(", updateDate = " + this.e);
        stringBuffer.append(", rvctionPointSize = " + this.f);
        stringBuffer.append(", rvctionPoint = " + this.g);
        stringBuffer.append(", serialNumCount = " + this.h);
        for (int i = 0; i < this.i.length; i++) {
            stringBuffer.append(", rvctionList[" + i + "] = " + this.i[i]);
        }
        return stringBuffer.toString();
    }
}
