package com.google.android.gms.measurement.internal;

import java.util.Objects;
import java.util.concurrent.Callable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzic implements Callable {
    final /* synthetic */ String zza;
    final /* synthetic */ zzjc zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzic(zzjc zzjcVar, String str) {
        this.zza = str;
        Objects.requireNonNull(zzjcVar);
        this.zzb = zzjcVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzjc zzjcVar = this.zzb;
        zzjcVar.zzL().zzY();
        return zzjcVar.zzL().zzj().zzn(this.zza);
    }
}
