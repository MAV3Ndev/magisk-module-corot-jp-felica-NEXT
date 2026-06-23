package a.a.a.a.c;

/* JADX INFO: compiled from: ResDataVerUpConfir.java */
/* JADX INFO: loaded from: classes.dex */
public class p1 extends l1 {
    private String b = null;
    private int c = -1;
    private int d = -1;
    private int e = -1;
    private String f = null;
    private String g = null;

    public String d() {
        return this.g;
    }

    public int e() {
        return this.e;
    }

    public int f() {
        return this.d;
    }

    public int g() {
        return this.c;
    }

    public String h() {
        return this.f;
    }

    public String i() {
        return this.b;
    }

    public void j(String str) {
        this.g = str;
    }

    public void k(int i) {
        this.e = i;
    }

    public void l(int i) {
        this.d = i;
    }

    public void m(int i) {
        this.c = i;
    }

    public void n(String str) {
        this.f = str;
    }

    public void o(String str) {
        this.b = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResDataVerUpConfir verUpConfirDate = " + this.b);
        stringBuffer.append(", offlineVerNumLimit = " + this.c);
        stringBuffer.append(", offlineVerNum = " + this.d);
        stringBuffer.append(", offlineVerData = " + this.e);
        stringBuffer.append(", offlineVerUpReqDate = " + this.f);
        stringBuffer.append(", lastPlatformVersion = " + this.g);
        return stringBuffer.toString();
    }
}
