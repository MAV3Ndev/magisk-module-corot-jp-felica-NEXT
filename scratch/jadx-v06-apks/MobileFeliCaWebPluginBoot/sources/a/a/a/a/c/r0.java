package a.a.a.a.c;

/* JADX INFO: compiled from: NodeCodeRange.java */
/* JADX INFO: loaded from: classes.dex */
public class r0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f50a;
    private String b;

    public String a() {
        return this.f50a;
    }

    public String b() {
        return this.b;
    }

    public void c(String str) {
        this.f50a = str;
    }

    public void d(String str) {
        this.b = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NodeCodeRange lowerNode = " + this.f50a);
        stringBuffer.append(", upperNode = " + this.b);
        return stringBuffer.toString();
    }
}
