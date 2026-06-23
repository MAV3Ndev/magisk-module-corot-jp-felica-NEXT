package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzqn implements zzqm {
    public static final zzkl zza;
    public static final zzkl zzb;
    public static final zzkl zzc;
    public static final zzkl zzd;
    public static final zzkl zze;
    public static final zzkl zzf;
    public static final zzkl zzg;
    public static final zzkl zzh;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        zzkf zzkfVarZzb = new zzkf(zzka.zza("com.google.android.gms.measurement")).zza().zzb();
        zzkfVarZzb.zzd("measurement.rb.attribution.ad_campaign_info", true);
        zzkfVarZzb.zzd("measurement.rb.attribution.service.bundle_on_backgrounded", true);
        zza = zzkfVarZzb.zzd("measurement.rb.attribution.client2", true);
        zzb = zzkfVarZzb.zzd("measurement.rb.attribution.followup1.service", false);
        zzkfVarZzb.zzd("measurement.rb.attribution.client.get_trigger_uris_async", true);
        zzc = zzkfVarZzb.zzd("measurement.rb.attribution.service.trigger_uris_high_priority", true);
        zzkfVarZzb.zzd("measurement.rb.attribution.index_out_of_bounds_fix", true);
        zzd = zzkfVarZzb.zzd("measurement.rb.attribution.service.enable_max_trigger_uris_queried_at_once", true);
        zze = zzkfVarZzb.zzd("measurement.rb.attribution.retry_disposition", false);
        zzf = zzkfVarZzb.zzd("measurement.rb.attribution.service", true);
        zzg = zzkfVarZzb.zzd("measurement.rb.attribution.enable_trigger_redaction", true);
        zzh = zzkfVarZzb.zzd("measurement.rb.attribution.uuid_generation", true);
        zzkfVarZzb.zzc("measurement.id.rb.attribution.retry_disposition", 0L);
        zzkfVarZzb.zzd("measurement.rb.attribution.improved_retry", true);
    }

    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zza() {
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zzb() {
        return ((Boolean) zza.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zzc() {
        return ((Boolean) zzb.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zzd() {
        return ((Boolean) zzc.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zze() {
        return ((Boolean) zzd.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zzf() {
        return ((Boolean) zze.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zzg() {
        return ((Boolean) zzf.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zzh() {
        return ((Boolean) zzg.zzd()).booleanValue();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqm
    public final boolean zzi() {
        return ((Boolean) zzh.zzd()).booleanValue();
    }
}
