package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzjz implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzli zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzjz(zzli zzliVar, long j) {
        this.zza = j;
        Objects.requireNonNull(zzliVar);
        this.zzb = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzib zzibVar = this.zzb.zzu;
        zzhd zzhdVar = zzibVar.zzd().zzf;
        long j = this.zza;
        zzhdVar.zzb(j);
        zzibVar.zzaV().zzj().zzb("Session timeout duration set", Long.valueOf(j));
    }
}
