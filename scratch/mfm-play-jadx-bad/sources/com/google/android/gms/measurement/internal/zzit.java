package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzit implements Runnable {
    final /* synthetic */ zzpk zza;
    final /* synthetic */ zzr zzb;
    final /* synthetic */ zzjc zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzit(zzjc zzjcVar, zzpk zzpkVar, zzr zzrVar) {
        this.zza = zzpkVar;
        this.zzb = zzrVar;
        Objects.requireNonNull(zzjcVar);
        this.zzc = zzjcVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzjc zzjcVar = this.zzc;
        zzjcVar.zzL().zzY();
        zzpk zzpkVar = this.zza;
        if (zzpkVar.zza() != null) {
            zzjcVar.zzL().zzab(zzpkVar, this.zzb);
        } else {
            zzr zzrVar = this.zzb;
            zzjcVar.zzL().zzac(zzpkVar.zzb, zzrVar);
        }
    }
}
