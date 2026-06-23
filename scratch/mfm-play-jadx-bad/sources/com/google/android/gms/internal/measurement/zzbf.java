package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzbf implements zzbe {
    private final zzg zza;
    private final String zzb;

    public zzbf(zzg zzgVar, String str) {
        this.zza = zzgVar;
        this.zzb = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzbe
    public final zzg zza(zzao zzaoVar) {
        zzg zzgVar = this.zza;
        zzgVar.zzf(this.zzb, zzaoVar);
        return zzgVar;
    }
}
