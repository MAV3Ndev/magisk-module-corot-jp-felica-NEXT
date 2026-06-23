package com.google.android.play.integrity.internal;

/* JADX INFO: compiled from: com.google.android.play:integrity@@1.4.0 */
/* JADX INFO: loaded from: classes3.dex */
final class x extends t {
    final /* synthetic */ ae a;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    x(ae aeVar) {
        this.a = aeVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.play.integrity.internal.t
    public final void b() {
        synchronized (this.a.g) {
            if (this.a.m.get() > 0 && this.a.m.decrementAndGet() > 0) {
                this.a.c.d("Leaving the connection open for other ongoing calls.", new Object[0]);
                return;
            }
            ae aeVar = this.a;
            if (aeVar.o != null) {
                aeVar.c.d("Unbind from service.", new Object[0]);
                ae aeVar2 = this.a;
                aeVar2.b.unbindService(aeVar2.n);
                this.a.h = false;
                this.a.o = null;
                this.a.n = null;
            }
            this.a.x();
        }
    }
}
