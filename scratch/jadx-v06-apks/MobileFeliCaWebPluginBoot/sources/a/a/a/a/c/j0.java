package a.a.a.a.c;

/* JADX INFO: compiled from: Ctrler.java */
/* JADX INFO: loaded from: classes.dex */
class j0 implements Runnable {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ u1 f34a;
    final /* synthetic */ l0 b;

    j0(l0 l0Var, u1 u1Var) {
        this.b = l0Var;
        this.f34a = u1Var;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.b.f.g().u()) {
            return;
        }
        this.f34a.k();
        this.b.d();
    }
}
