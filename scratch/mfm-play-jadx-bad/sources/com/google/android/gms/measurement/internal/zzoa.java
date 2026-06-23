package com.google.android.gms.measurement.internal;

import android.app.ActivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzoa {
    final /* synthetic */ zzob zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzoa(zzob zzobVar) {
        Objects.requireNonNull(zzobVar);
        this.zza = zzobVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zza() {
        zzob zzobVar = this.zza;
        zzobVar.zzg();
        zzib zzibVar = zzobVar.zzu;
        if (zzibVar.zzd().zzp(zzibVar.zzaZ().currentTimeMillis())) {
            zzibVar.zzd().zzg.zzb(true);
            ActivityManager.RunningAppProcessInfo runningAppProcessInfo = new ActivityManager.RunningAppProcessInfo();
            ActivityManager.getMyMemoryState(runningAppProcessInfo);
            if (runningAppProcessInfo.importance == 100) {
                zzibVar.zzaV().zzk().zza("Detected application was in foreground");
                zzc(zzibVar.zzaZ().currentTimeMillis(), false);
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzb(long j, boolean z) {
        zzob zzobVar = this.zza;
        zzobVar.zzg();
        zzobVar.zzj();
        zzib zzibVar = zzobVar.zzu;
        if (zzibVar.zzd().zzp(j)) {
            zzibVar.zzd().zzg.zzb(true);
            zzobVar.zzu.zzv().zzi();
        }
        zzibVar.zzd().zzk.zzb(j);
        if (zzibVar.zzd().zzg.zza()) {
            zzc(j, z);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzc(long j, boolean z) {
        zzob zzobVar = this.zza;
        zzobVar.zzg();
        if (zzobVar.zzu.zzB()) {
            zzib zzibVar = zzobVar.zzu;
            zzibVar.zzd().zzk.zzb(j);
            zzibVar.zzaV().zzk().zzb("Session started, time", Long.valueOf(zzibVar.zzaZ().elapsedRealtime()));
            long j2 = j / 1000;
            zzib zzibVar2 = zzobVar.zzu;
            Long lValueOf = Long.valueOf(j2);
            zzibVar2.zzj().zzN(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_sid", lValueOf, j);
            zzhd zzhdVar = zzibVar.zzd().zzl;
            lValueOf.getClass();
            zzhdVar.zzb(j2);
            zzibVar.zzd().zzg.zzb(false);
            Bundle bundle = new Bundle();
            lValueOf.getClass();
            bundle.putLong("_sid", j2);
            zzibVar2.zzj().zzG(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_s", j, bundle);
            String strZza = zzibVar.zzd().zzq.zza();
            if (TextUtils.isEmpty(strZza)) {
                return;
            }
            Bundle bundle2 = new Bundle();
            bundle2.putString("_ffr", strZza);
            zzibVar2.zzj().zzG(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ssr", j, bundle2);
        }
    }
}
