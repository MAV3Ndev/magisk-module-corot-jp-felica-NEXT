package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzqh implements zzqg {
    public static final zzkl zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        zzkf zzkfVarZzb = new zzkf(zzka.zza("com.google.android.gms.measurement")).zza().zzb();
        zzkfVarZzb.zzd("measurement.sdk.collection.enable_extend_user_property_size", true);
        zza = zzkfVarZzb.zzd("measurement.sdk.collection.last_deep_link_referrer_campaign2", false);
        zzkfVarZzb.zzc("measurement.id.sdk.collection.last_deep_link_referrer2", 0L);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzqg
    public final boolean zza() {
        return ((Boolean) zza.zzd()).booleanValue();
    }
}
