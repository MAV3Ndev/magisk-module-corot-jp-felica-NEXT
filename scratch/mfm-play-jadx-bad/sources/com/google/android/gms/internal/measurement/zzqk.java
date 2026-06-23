package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzqk implements zzqj {
    public static final zzkl zza;
    public static final zzkl zzb;
    public static final zzkl zzc;
    public static final zzkl zzd;
    public static final zzkl zze;
    public static final zzkl zzf;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        zzkf zzkfVarZzb = new zzkf(zzka.zza("com.google.android.gms.measurement")).zza().zzb();
        zza = zzkfVarZzb.zzd("measurement.test.boolean_flag", false);
        zzb = zzkfVarZzb.zzc("measurement.test.cached_long_flag", -1L);
        zzc = zzkfVarZzb.zze("measurement.test.double_flag", -3.0d);
        zzd = zzkfVarZzb.zzc("measurement.test.int_flag", -2L);
        zze = zzkfVarZzb.zzc("measurement.test.long_flag", -1L);
        zzf = zzkfVarZzb.zzf("measurement.test.string_flag", "---");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqj
    public final boolean zza() {
        return ((Boolean) zza.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqj
    public final long zzb() {
        return ((Long) zzb.zzd()).longValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqj
    public final double zzc() {
        return ((Double) zzc.zzd()).doubleValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqj
    public final long zzd() {
        return ((Long) zzd.zzd()).longValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqj
    public final long zze() {
        return ((Long) zze.zzd()).longValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqj
    public final String zzf() {
        return (String) zzf.zzd();
    }
}
