package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzny extends zzay {
    final /* synthetic */ zznz zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzny(zznz zznzVar, zzjf zzjfVar) {
        super(zzjfVar);
        Objects.requireNonNull(zznzVar);
        this.zza = zznzVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzay
    public final void zza() {
        zznz zznzVar = this.zza;
        zzob zzobVar = zznzVar.zzc;
        zzobVar.zzg();
        zzib zzibVar = zzobVar.zzu;
        zznzVar.zzd(false, false, zzibVar.zzaZ().elapsedRealtime());
        zzobVar.zzu.zzw().zzc(zzibVar.zzaZ().elapsedRealtime());
    }
}
