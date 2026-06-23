package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzkt implements Runnable {
    final /* synthetic */ zzjk zza;
    final /* synthetic */ long zzb;
    final /* synthetic */ boolean zzc;
    final /* synthetic */ zzli zzd;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzkt(zzli zzliVar, zzjk zzjkVar, long j, boolean z) {
        this.zza = zzjkVar;
        this.zzb = j;
        this.zzc = z;
        Objects.requireNonNull(zzliVar);
        this.zzd = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzli zzliVar = this.zzd;
        zzjk zzjkVar = this.zza;
        zzliVar.zzA(zzjkVar);
        zzliVar.zzaj(zzjkVar, this.zzb, true, this.zzc);
    }
}
