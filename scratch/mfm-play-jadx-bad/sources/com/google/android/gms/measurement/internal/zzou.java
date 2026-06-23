package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzou implements Runnable {
    final /* synthetic */ zzpg zza;
    final /* synthetic */ zzpf zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzou(zzpf zzpfVar, zzpg zzpgVar) {
        this.zza = zzpgVar;
        Objects.requireNonNull(zzpfVar);
        this.zzb = zzpfVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzpf zzpfVar = this.zzb;
        zzpfVar.zzat(this.zza);
        zzpfVar.zzc();
    }
}
