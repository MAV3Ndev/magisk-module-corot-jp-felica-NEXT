package com.google.android.gms.measurement.internal;

import java.util.List;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzhm implements com.google.android.gms.internal.measurement.zzr {
    final /* synthetic */ zzhs zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzhm(zzhs zzhsVar) {
        Objects.requireNonNull(zzhsVar);
        this.zza = zzhsVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzr
    public final void zza(int i, String str, List list, boolean z, boolean z2) {
        int i2 = i - 1;
        zzgr zzgrVarZzi = i2 != 0 ? i2 != 1 ? i2 != 3 ? i2 != 4 ? this.zza.zzu.zzaV().zzi() : z ? this.zza.zzu.zzaV().zzf() : !z2 ? this.zza.zzu.zzaV().zzh() : this.zza.zzu.zzaV().zze() : this.zza.zzu.zzaV().zzk() : z ? this.zza.zzu.zzaV().zzc() : !z2 ? this.zza.zzu.zzaV().zzd() : this.zza.zzu.zzaV().zzb() : this.zza.zzu.zzaV().zzj();
        int size = list.size();
        if (size == 1) {
            zzgrVarZzi.zzb(str, list.get(0));
            return;
        }
        if (size == 2) {
            zzgrVarZzi.zzc(str, list.get(0), list.get(1));
        } else if (size != 3) {
            zzgrVarZzi.zza(str);
        } else {
            zzgrVarZzi.zzd(str, list.get(0), list.get(1), list.get(2));
        }
    }
}
