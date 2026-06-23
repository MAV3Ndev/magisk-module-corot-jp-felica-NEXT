package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zznn implements Runnable {
    final /* synthetic */ zzpf zza;
    final /* synthetic */ Runnable zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zznn(zzns zznsVar, zzpf zzpfVar, Runnable runnable) {
        this.zza = zzpfVar;
        this.zzb = runnable;
        Objects.requireNonNull(zznsVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzpf zzpfVar = this.zza;
        zzpfVar.zzY();
        zzpfVar.zzX(this.zzb);
        zzpfVar.zzM();
    }
}
