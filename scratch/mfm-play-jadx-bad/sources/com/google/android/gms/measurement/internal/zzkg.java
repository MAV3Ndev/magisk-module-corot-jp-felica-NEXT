package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzkg implements Runnable {
    final /* synthetic */ long zza;
    final /* synthetic */ zzli zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzkg(zzli zzliVar, long j) {
        this.zza = j;
        Objects.requireNonNull(zzliVar);
        this.zzb = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzli zzliVar = this.zzb;
        zzliVar.zzg();
        zzliVar.zzb();
        zzib zzibVar = zzliVar.zzu;
        zzibVar.zzaV().zzj().zza("Resetting analytics data (FE)");
        zzib zzibVar2 = zzliVar.zzu;
        zzob zzobVarZzh = zzibVar2.zzh();
        zzobVarZzh.zzg();
        zzoa zzoaVar = zzobVarZzh.zza;
        zzobVarZzh.zzb.zzc();
        zzibVar2.zzv().zzi();
        boolean z = !zzliVar.zzu.zzB();
        zzhg zzhgVarZzd = zzibVar.zzd();
        zzhgVarZzd.zzc.zzb(this.zza);
        zzib zzibVar3 = zzhgVarZzd.zzu;
        if (!TextUtils.isEmpty(zzibVar3.zzd().zzq.zza())) {
            zzhgVarZzd.zzq.zzb(null);
        }
        zzhgVarZzd.zzk.zzb(0L);
        zzhgVarZzd.zzl.zzb(0L);
        if (!zzibVar3.zzc().zzt()) {
            zzhgVarZzd.zzn(z);
        }
        zzhgVarZzd.zzr.zzb(null);
        zzhgVarZzd.zzs.zzb(0L);
        zzhgVarZzd.zzt.zzb(null);
        zzibVar2.zzt().zzB();
        zzibVar2.zzh().zza.zza();
        zzliVar.zzc = z;
        zzibVar2.zzt().zzC(new AtomicReference());
    }
}
