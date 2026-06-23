package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.internal.Preconditions;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzpd {
    private final zzpf zza;
    private int zzb = 1;
    private long zzc = zzd();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzpd(zzpf zzpfVar) {
        this.zza = zzpfVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final long zzd() {
        zzpf zzpfVar = this.zza;
        Preconditions.checkNotNull(zzpfVar);
        long jLongValue = ((Long) zzfx.zzu.zzb(null)).longValue();
        long jLongValue2 = ((Long) zzfx.zzv.zzb(null)).longValue();
        for (int i = 1; i < this.zzb; i++) {
            jLongValue += jLongValue;
            if (jLongValue >= jLongValue2) {
                break;
            }
        }
        return zzpfVar.zzaZ().currentTimeMillis() + Math.min(jLongValue, jLongValue2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zza() {
        this.zzb++;
        this.zzc = zzd();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzb() {
        return this.zza.zzaZ().currentTimeMillis() >= this.zzc;
    }

    final /* synthetic */ long zzc() {
        return this.zzc;
    }
}
