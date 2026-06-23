package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zznw implements Runnable {
    final long zza;
    final long zzb;
    final /* synthetic */ zznx zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zznw(zznx zznxVar, long j, long j2) {
        Objects.requireNonNull(zznxVar);
        this.zzc = zznxVar;
        this.zza = j;
        this.zzb = j2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        this.zzc.zza.zzu.zzaW().zzj(new Runnable() { // from class: com.google.android.gms.measurement.internal.zznv
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // java.lang.Runnable
            public final /* synthetic */ void run() {
                zznw zznwVar = this.zza;
                zzob zzobVar = zznwVar.zzc.zza;
                zzobVar.zzg();
                zzib zzibVar = zzobVar.zzu;
                zzibVar.zzaV().zzj().zza("Application going to the background");
                zzibVar.zzd().zzn.zzb(true);
                zzobVar.zzh(true);
                if (!zzibVar.zzc().zzv()) {
                    long j = zznwVar.zzb;
                    zznz zznzVar = zzobVar.zzb;
                    zznzVar.zzd(false, false, j);
                    zznzVar.zzb(j);
                }
                zzibVar.zzaV().zzi().zzb("Application backgrounded at: timestamp_millis", Long.valueOf(zznwVar.zza));
                zzib zzibVar2 = zzobVar.zzu;
                zzli zzliVarZzj = zzibVar2.zzj();
                zzliVarZzj.zzg();
                zzib zzibVar3 = zzliVarZzj.zzu;
                zzliVarZzj.zzb();
                zznk zznkVarZzt = zzibVar3.zzt();
                zznkVarZzt.zzg();
                zznkVarZzt.zzb();
                if (!zznkVarZzt.zzK() || zznkVarZzt.zzu.zzk().zzah() >= 242600) {
                    zzibVar3.zzt().zzF();
                }
                if (zzibVar.zzc().zzp(null, zzfx.zzaN)) {
                    long jZzl = zzibVar.zzk().zzaa(zzibVar.zzaY().getPackageName(), zzibVar.zzc().zzz()) ? 1000L : zzibVar.zzc().zzl(zzibVar.zzaY().getPackageName(), zzfx.zzD);
                    zzibVar.zzaV().zzk().zzb("[sgtm] Scheduling batch upload with minimum latency in millis", Long.valueOf(jZzl));
                    zzibVar2.zzx().zzh(jZzl);
                }
            }
        });
    }
}
