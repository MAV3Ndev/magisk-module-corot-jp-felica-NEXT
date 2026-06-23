package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzax implements Runnable {
    final /* synthetic */ zzjf zza;
    final /* synthetic */ zzay zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzax(zzay zzayVar, zzjf zzjfVar) {
        this.zza = zzjfVar;
        Objects.requireNonNull(zzayVar);
        this.zzb = zzayVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzjf zzjfVar = this.zza;
        zzjfVar.zzaU();
        if (zzae.zza()) {
            zzjfVar.zzaW().zzj(this);
            return;
        }
        zzay zzayVar = this.zzb;
        boolean zZzc = zzayVar.zzc();
        zzayVar.zze(0L);
        if (zZzc) {
            zzayVar.zza();
        }
    }
}
