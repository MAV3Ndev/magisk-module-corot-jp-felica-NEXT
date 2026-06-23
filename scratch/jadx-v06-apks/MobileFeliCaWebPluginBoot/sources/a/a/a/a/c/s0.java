package a.a.a.a.c;

/* JADX INFO: compiled from: NwConOptrSetting.java */
/* JADX INFO: loaded from: classes.dex */
public class s0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f52a;
    private String b;
    private String c;

    public String a() {
        return this.b;
    }

    public String b() {
        return this.f52a;
    }

    public String c() {
        return this.c;
    }

    public void d(String str) {
        this.b = str;
    }

    public void e(String str) {
        this.f52a = str;
    }

    public void f(String str) {
        this.c = str;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("NwConOptrSetting id = " + this.f52a);
        stringBuffer.append(", data = " + this.b);
        stringBuffer.append(", url = " + this.c);
        return stringBuffer.toString();
    }
}
