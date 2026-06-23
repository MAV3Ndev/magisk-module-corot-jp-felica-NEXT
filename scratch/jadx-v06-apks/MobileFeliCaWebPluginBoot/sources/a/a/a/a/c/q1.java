package a.a.a.a.c;

/* JADX INFO: compiled from: ResOptrSetting.java */
/* JADX INFO: loaded from: classes.dex */
public class q1 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f48a;
    private boolean b;
    private l1 c;

    public String a() {
        return this.f48a;
    }

    public l1 b() {
        return this.c;
    }

    public boolean c() {
        return this.b;
    }

    public void d(String str) {
        this.f48a = str;
    }

    public void e(boolean z) {
        this.b = z;
    }

    public void f(l1 l1Var) {
        this.c = l1Var;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ResOptrSetting id = " + this.f48a);
        stringBuffer.append(", isRead = " + this.b);
        stringBuffer.append(", resData = " + this.c);
        return stringBuffer.toString();
    }
}
