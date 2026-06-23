package a.a.a.a.c;

/* JADX INFO: compiled from: PermitOptr.java */
/* JADX INFO: loaded from: classes.dex */
class z0 {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    private String f64a;
    private int b;
    private String c;
    private int d;
    private int e;

    private z0(h1 h1Var) {
        this.b = -1;
        this.d = -1;
        this.e = -1;
    }

    public int a() {
        return this.b;
    }

    public String b() {
        return this.f64a;
    }

    public String c() {
        return this.c;
    }

    public boolean d() {
        return this.e != -1;
    }

    public boolean e() {
        return this.b != -1;
    }

    public boolean f() {
        return this.d != -1;
    }

    public void g(int i, String str) {
        this.e = i;
    }

    public void h(int i, String str) {
        this.f64a = str;
        this.b = i;
    }

    public void i(int i, String str) {
        this.c = str;
        this.d = i;
    }
}
