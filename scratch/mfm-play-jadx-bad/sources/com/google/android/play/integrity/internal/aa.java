package com.google.android.play.integrity.internal;

import android.os.IBinder;
import android.os.IInterface;
import java.util.Iterator;

/* JADX INFO: compiled from: com.google.android.play:integrity@@1.4.0 */
/* JADX INFO: loaded from: classes3.dex */
final class aa extends t {
    final /* synthetic */ IBinder a;
    final /* synthetic */ ad b;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    aa(ad adVar, IBinder iBinder) {
        this.a = iBinder;
        this.b = adVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.play.integrity.internal.t
    public final void b() {
        this.b.a.o = (IInterface) this.b.a.j.a(this.a);
        ae.r(this.b.a);
        this.b.a.h = false;
        Iterator it = this.b.a.e.iterator();
        while (it.hasNext()) {
            ((Runnable) it.next()).run();
        }
        this.b.a.e.clear();
    }
}
