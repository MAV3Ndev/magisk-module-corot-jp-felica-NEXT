package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzlu implements Runnable {
    final /* synthetic */ Bundle zza;
    final /* synthetic */ zzlt zzb;
    final /* synthetic */ zzlt zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ zzma zze;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzlu(zzma zzmaVar, Bundle bundle, zzlt zzltVar, zzlt zzltVar2, long j) {
        this.zza = bundle;
        this.zzb = zzltVar;
        this.zzc = zzltVar2;
        this.zzd = j;
        Objects.requireNonNull(zzmaVar);
        this.zze = zzmaVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        this.zze.zzt(this.zza, this.zzb, this.zzc, this.zzd);
    }
}
