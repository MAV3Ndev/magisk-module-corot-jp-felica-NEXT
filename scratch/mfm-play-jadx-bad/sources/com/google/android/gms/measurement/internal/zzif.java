package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzif implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ long zzd;
    final /* synthetic */ zzjc zze;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzif(zzjc zzjcVar, String str, String str2, String str3, long j) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = str3;
        this.zzd = j;
        Objects.requireNonNull(zzjcVar);
        this.zze = zzjcVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        String str = this.zza;
        if (str == null) {
            zzjc zzjcVar = this.zze;
            zzjcVar.zzL().zzas(this.zzb, null);
        } else {
            zzlt zzltVar = new zzlt(this.zzc, str, this.zzd);
            zzjc zzjcVar2 = this.zze;
            zzjcVar2.zzL().zzas(this.zzb, zzltVar);
        }
    }
}
