package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzqz implements zzqy {
    public static final zzkl zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        zzkf zzkfVarZzb = new zzkf(zzka.zza("com.google.android.gms.measurement")).zza().zzb();
        zza = zzkfVarZzb.zzd("measurement.client.sessions.enable_fix_background_engagement", false);
        zzkfVarZzb.zzd("measurement.client.sessions.enable_pause_engagement_in_background", true);
        zzkfVarZzb.zzc("measurement.id.client.sessions.enable_fix_background_engagement", 0L);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqy
    public final boolean zza() {
        return ((Boolean) zza.zzd()).booleanValue();
    }
}
