package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzoz implements Runnable {
    final /* synthetic */ String zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ Bundle zzc;
    final /* synthetic */ zzpa zzd;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzoz(zzpa zzpaVar, String str, String str2, Bundle bundle) {
        this.zza = str;
        this.zzb = str2;
        this.zzc = bundle;
        Objects.requireNonNull(zzpaVar);
        this.zzd = zzpaVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzpf zzpfVar = this.zzd.zza;
        zzpo zzpoVarZzt = zzpfVar.zzt();
        long jCurrentTimeMillis = zzpfVar.zzaZ().currentTimeMillis();
        String str = this.zza;
        zzpfVar.zzD((zzbg) Preconditions.checkNotNull(zzpoVarZzt.zzac(str, this.zzb, this.zzc, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, jCurrentTimeMillis, false, true)), str);
    }
}
