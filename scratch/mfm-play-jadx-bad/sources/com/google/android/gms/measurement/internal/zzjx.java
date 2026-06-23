package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzjx implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ zzli zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzjx(zzli zzliVar, boolean z) {
        this.zza = z;
        Objects.requireNonNull(zzliVar);
        this.zzb = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzli zzliVar = this.zzb;
        zzib zzibVar = zzliVar.zzu;
        boolean zZzB = zzibVar.zzB();
        boolean zZzA = zzibVar.zzA();
        boolean z = this.zza;
        zzibVar.zzz(z);
        if (zZzA == z) {
            zzibVar.zzaV().zzk().zzb("Default data collection state already set to", Boolean.valueOf(z));
        }
        if (zzibVar.zzB() == zZzB || zzibVar.zzB() != zzibVar.zzA()) {
            zzibVar.zzaV().zzh().zzc("Default data collection is different than actual status", Boolean.valueOf(z), Boolean.valueOf(zZzB));
        }
        zzliVar.zzak();
    }
}
