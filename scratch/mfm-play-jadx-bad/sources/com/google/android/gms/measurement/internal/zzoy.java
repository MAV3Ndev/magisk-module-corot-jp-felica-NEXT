package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzoy implements Callable {
    final /* synthetic */ zzr zza;
    final /* synthetic */ zzpf zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzoy(zzpf zzpfVar, zzr zzrVar) {
        this.zza = zzrVar;
        Objects.requireNonNull(zzpfVar);
        this.zzb = zzpfVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzr zzrVar = this.zza;
        String str = (String) Preconditions.checkNotNull(zzrVar.zza);
        zzpf zzpfVar = this.zzb;
        zzjk zzjkVarZzB = zzpfVar.zzB(str);
        zzjj zzjjVar = zzjj.ANALYTICS_STORAGE;
        if (zzjkVarZzB.zzo(zzjjVar) && zzjk.zzf(zzrVar.zzs, 100).zzo(zzjjVar)) {
            return zzpfVar.zzan(zzrVar).zzd();
        }
        zzpfVar.zzaV().zzk().zza("Analytics storage consent denied. Returning null app instance id");
        return null;
    }
}
