package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public enum zzji {
    STORAGE(zzjj.AD_STORAGE, zzjj.ANALYTICS_STORAGE),
    DMA(zzjj.AD_USER_DATA);

    private final zzjj[] zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzji(zzjj... zzjjVarArr) {
        this.zzc = zzjjVarArr;
    }

    public final zzjj[] zza() {
        return this.zzc;
    }

    final /* synthetic */ zzjj[] zzb() {
        return this.zzc;
    }
}
