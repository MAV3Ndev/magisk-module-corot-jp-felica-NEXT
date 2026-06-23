package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzlv implements Runnable {
    final /* synthetic */ zzlt zza;
    final /* synthetic */ zzlt zzb;
    final /* synthetic */ long zzc;
    final /* synthetic */ boolean zzd;
    final /* synthetic */ zzma zze;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzlv(zzma zzmaVar, zzlt zzltVar, zzlt zzltVar2, long j, boolean z) {
        this.zza = zzltVar;
        this.zzb = zzltVar2;
        this.zzc = j;
        this.zzd = z;
        Objects.requireNonNull(zzmaVar);
        this.zze = zzmaVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        this.zze.zzu(this.zza, this.zzb, this.zzc, this.zzd, null);
    }
}
