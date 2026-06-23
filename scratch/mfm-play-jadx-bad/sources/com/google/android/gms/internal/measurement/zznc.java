package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zznc implements zznx {
    private static final zznj zzb = new zzna();
    private final zznj zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zznc() {
        zznj zznjVar = zzb;
        int i = zznt.zza;
        zznb zznbVar = new zznb(zzlz.zza(), zznjVar);
        byte[] bArr = zzmo.zzb;
        this.zza = zznbVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zznx
    public final zznw zza(Class cls) {
        int i = zzny.zza;
        if (!zzme.class.isAssignableFrom(cls)) {
            int i2 = zznt.zza;
        }
        zzni zzniVarZzc = this.zza.zzc(cls);
        if (zzniVarZzc.zza()) {
            int i3 = zznt.zza;
            return zznp.zzg(zzny.zzA(), zzlt.zza(), zzniVarZzc.zzb());
        }
        int i4 = zznt.zza;
        return zzno.zzl(cls, zzniVarZzc, zznr.zza(), zzmy.zza(), zzny.zzA(), zzniVarZzc.zzc() + (-1) != 1 ? zzlt.zza() : null, zznh.zza());
    }
}
