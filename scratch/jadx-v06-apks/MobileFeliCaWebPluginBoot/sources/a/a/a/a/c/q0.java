package a.a.a.a.c;

/* JADX INFO: compiled from: LineData.java */
/* JADX INFO: loaded from: classes.dex */
public class q0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f47a;
    private String[] b;

    public String a() {
        return this.f47a;
    }

    public String[] b() {
        return this.b;
    }

    public void c(String str) {
        this.f47a = str;
    }

    public void d(String[] strArr) {
        this.b = strArr;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("LineData name = " + this.f47a);
        if (this.b != null) {
            for (int i = 0; i < this.b.length; i++) {
                stringBuffer.append("params[" + i + "] = " + this.b[i]);
            }
        }
        return stringBuffer.toString();
    }
}
