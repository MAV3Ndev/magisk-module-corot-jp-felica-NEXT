package com.google.android.gms.internal.fido;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzaq implements zzbp {
    public static final /* synthetic */ int zza = 0;
    private static volatile zzaj zzb = new zzaj(zzap.zza);
    private final String zzd;
    private final Object zze;
    private volatile zzao zzf;
    private volatile Object zzh;
    private final zzak zzi;
    private volatile int zzg = -1;
    private final String zzc = "com.google.android.gms.fido";

    zzaq(String str, String str2, Object obj, zzak zzakVar, boolean z) {
        this.zzd = str2;
        this.zze = obj;
        this.zzi = zzakVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.fido.zzbp
    public final Object zza() {
        zzae.zza();
        synchronized (this) {
            zzae.zzb();
            int i = zzan.zza;
            throw null;
        }
    }
}
