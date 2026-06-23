package com.google.android.gms.measurement.internal;

import java.util.Map;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzow implements zzgv {
    final /* synthetic */ String zza;
    final /* synthetic */ zzpi zzb;
    final /* synthetic */ zzpf zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzow(zzpf zzpfVar, String str, zzpi zzpiVar) {
        this.zza = str;
        this.zzb = zzpiVar;
        Objects.requireNonNull(zzpfVar);
        this.zzc = zzpfVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzgv
    public final void zza(String str, int i, Throwable th, byte[] bArr, Map map) {
        this.zzc.zzQ(this.zza, i, th, bArr, this.zzb);
    }
}
