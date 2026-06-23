package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzks implements Runnable {
    final /* synthetic */ zzaz zza;
    final /* synthetic */ zzli zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzks(zzli zzliVar, zzaz zzazVar) {
        this.zza = zzazVar;
        Objects.requireNonNull(zzliVar);
        this.zzb = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzli zzliVar = this.zzb;
        zzib zzibVar = zzliVar.zzu;
        zzhg zzhgVarZzd = zzibVar.zzd();
        zzib zzibVar2 = zzhgVarZzd.zzu;
        zzhgVarZzd.zzg();
        zzaz zzazVarZzj = zzhgVarZzd.zzj();
        zzaz zzazVar = this.zza;
        if (!zzjk.zzu(zzazVar.zzb(), zzazVarZzj.zzb())) {
            zzibVar.zzaV().zzi().zzb("Lower precedence consent source ignored, proposed source", Integer.valueOf(zzazVar.zzb()));
            return;
        }
        SharedPreferences.Editor editorEdit = zzhgVarZzd.zzd().edit();
        editorEdit.putString("dma_consent_settings", zzazVar.zze());
        editorEdit.apply();
        zzibVar.zzaV().zzk().zzb("Setting DMA consent(FE)", zzazVar);
        zzib zzibVar3 = zzliVar.zzu;
        if (zzibVar3.zzt().zzP()) {
            zzibVar3.zzt().zzl();
        } else {
            zzibVar3.zzt().zzj(false);
        }
    }
}
