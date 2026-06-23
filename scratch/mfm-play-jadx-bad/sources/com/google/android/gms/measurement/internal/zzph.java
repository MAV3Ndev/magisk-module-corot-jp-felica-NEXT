package com.google.android.gms.measurement.internal;

import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzph {
    private long zza;
    private com.google.android.gms.internal.measurement.zzib zzb;
    private String zzc;
    private Map zzd;
    private zzlr zze;
    private long zzf;
    private long zzg;
    private long zzh;
    private int zzi;

    zzph() {
    }

    public final zzpi zza() {
        return new zzpi(this.zza, this.zzb, this.zzc, this.zzd, this.zze, this.zzf, this.zzg, this.zzh, this.zzi, null);
    }

    public final zzph zzb(long j) {
        this.zza = j;
        return this;
    }

    public final zzph zzc(com.google.android.gms.internal.measurement.zzib zzibVar) {
        this.zzb = zzibVar;
        return this;
    }

    public final zzph zzd(String str) {
        this.zzc = str;
        return this;
    }

    public final zzph zze(Map map) {
        this.zzd = map;
        return this;
    }

    public final zzph zzf(zzlr zzlrVar) {
        this.zze = zzlrVar;
        return this;
    }

    public final zzph zzg(long j) {
        this.zzf = j;
        return this;
    }

    public final zzph zzh(long j) {
        this.zzg = j;
        return this;
    }

    public final zzph zzi(long j) {
        this.zzh = j;
        return this;
    }

    public final zzph zzj(int i) {
        this.zzi = i;
        return this;
    }
}
