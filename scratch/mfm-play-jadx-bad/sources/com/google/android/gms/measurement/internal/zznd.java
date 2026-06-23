package com.google.android.gms.measurement.internal;

import com.google.android.gms.common.ConnectionResult;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zznd implements Runnable {
    final /* synthetic */ ConnectionResult zza;
    final /* synthetic */ zzne zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zznd(zzne zzneVar, ConnectionResult connectionResult) {
        this.zza = connectionResult;
        Objects.requireNonNull(zzneVar);
        this.zzb = zzneVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zznk zznkVar = this.zzb.zza;
        zznkVar.zzaa(null);
        if (this.zza.getErrorCode() != 7777) {
            zznkVar.zzX();
            return;
        }
        if (zznkVar.zzab() == null) {
            zznkVar.zzac(Executors.newScheduledThreadPool(1));
        }
        zznkVar.zzab().schedule(new Runnable() { // from class: com.google.android.gms.measurement.internal.zznb
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                final zznk zznkVar2 = this.zza.zzb.zza;
                zzhy zzhyVarZzaW = zznkVar2.zzu.zzaW();
                Objects.requireNonNull(zznkVar2);
                zzhyVarZzaW.zzj(new Runnable() { // from class: com.google.android.gms.measurement.internal.zznc
                    @Override // java.lang.Runnable
                    public final /* synthetic */ void run() {
                        zznkVar2.zzI();
                    }
                });
            }
        }, ((Long) zzfx.zzZ.zzb(null)).longValue(), TimeUnit.MILLISECONDS);
    }
}
