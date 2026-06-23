package a.a.a.a.c;

/* JADX INFO: compiled from: AppStartParam.java */
/* JADX INFO: loaded from: classes.dex */
public class a {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f12a;
    private int b;
    private String c;
    private String d;

    public static int a(String str) {
        if (com.felicanetworks.mfw.a.cmn.b1.J(str).length > 254) {
            return 2000;
        }
        if (com.felicanetworks.mfw.a.cmn.b1.m(str)) {
            return !com.felicanetworks.mfw.a.cmn.b1.D(new String(com.felicanetworks.mfw.a.cmn.b1.I(str))) ? 2002 : 0;
        }
        return 2001;
    }

    public static int b(String str) {
        if (com.felicanetworks.mfw.a.cmn.b1.J(str).length > 255) {
            return 2000;
        }
        return !com.felicanetworks.mfw.a.cmn.b1.D(str) ? 2002 : 0;
    }

    public int c() {
        return this.f12a;
    }

    public String d() {
        return this.c;
    }

    public int e() {
        return this.b;
    }

    public String f() {
        return this.d;
    }

    public void g(int i) {
        this.f12a = i;
    }

    public void h(String str) {
        this.c = str;
    }

    public void i(int i) {
        this.b = i;
    }

    public void j(String str) {
        this.d = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("AppstartParam category = " + this.f12a);
        stringBuffer.append(", param = " + this.c);
        stringBuffer.append(", startType = " + this.b);
        stringBuffer.append(", uri = " + this.d);
        return stringBuffer.toString();
    }
}
