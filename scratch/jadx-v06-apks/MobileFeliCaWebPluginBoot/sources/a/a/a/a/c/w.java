package a.a.a.a.c;

import java.util.TimerTask;

/* JADX INFO: compiled from: CmdOptr.java */
/* JADX INFO: loaded from: classes.dex */
class w extends TimerTask {

    /* JADX INFO: renamed from: a, reason: collision with root package name */
    final /* synthetic */ x f58a;

    private w(x xVar) {
        this.f58a = xVar;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public void run() {
        synchronized (this) {
            this.f58a.f60a = true;
        }
    }
}
