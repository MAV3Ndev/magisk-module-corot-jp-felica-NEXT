package a.a.a.a.c;

/* JADX INFO: compiled from: Ctrler.java */
/* JADX INFO: loaded from: classes.dex */
class k0 implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ a.a.a.a.a.v f36a;
    final /* synthetic */ String b;
    final /* synthetic */ String c;
    final /* synthetic */ l0 d;

    k0(l0 l0Var, a.a.a.a.a.v vVar, String str, String str2) {
        this.d = l0Var;
        this.f36a = vVar;
        this.b = str;
        this.c = str2;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.d.f.g().u()) {
            return;
        }
        this.f36a.x(this.b, this.c);
        this.d.d();
    }
}
