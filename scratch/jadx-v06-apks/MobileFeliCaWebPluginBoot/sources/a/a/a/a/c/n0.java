package a.a.a.a.c;

/* JADX INFO: compiled from: Event.java */
/* JADX INFO: loaded from: classes.dex */
public class n0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private int f42a;
    private String b;
    private Object c;
    private Object d;

    public Object a() {
        return this.c;
    }

    public Object b() {
        return this.d;
    }

    public int c() {
        return this.f42a;
    }

    public String d() {
        return this.b;
    }

    public void e(Object obj) {
        this.c = obj;
    }

    public void f(Object obj) {
        this.d = obj;
    }

    public void g(int i) {
        this.f42a = i;
    }

    public void h(String str) {
        this.b = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Event id = " + this.f42a);
        stringBuffer.append(", targetInstanceId = " + this.b);
        stringBuffer.append(", arg1 = " + this.c);
        stringBuffer.append(", arg2 = " + this.d);
        return stringBuffer.toString();
    }
}
