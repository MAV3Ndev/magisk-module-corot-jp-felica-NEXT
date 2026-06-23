package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzhz implements Runnable {
    final /* synthetic */ zzjr zza;
    final /* synthetic */ zzib zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzhz(zzib zzibVar, zzjr zzjrVar) {
        this.zza = zzjrVar;
        Objects.requireNonNull(zzibVar);
        this.zzb = zzibVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzib zzibVar = this.zzb;
        zzjr zzjrVar = this.zza;
        zzibVar.zzK(zzjrVar);
        zzibVar.zza(zzjrVar.zzd);
    }
}
