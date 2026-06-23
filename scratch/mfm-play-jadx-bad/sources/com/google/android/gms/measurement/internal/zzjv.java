package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.util.SparseArray;
import com.google.common.util.concurrent.FutureCallback;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzjv implements FutureCallback {
    final /* synthetic */ zzog zza;
    final /* synthetic */ zzli zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzjv(zzli zzliVar, zzog zzogVar) {
        this.zza = zzogVar;
        Objects.requireNonNull(zzliVar);
        this.zzb = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zza() {
        zzib zzibVar = this.zzb.zzu;
        SparseArray sparseArrayZzf = zzibVar.zzd().zzf();
        zzog zzogVar = this.zza;
        sparseArrayZzf.put(zzogVar.zzc, Long.valueOf(zzogVar.zzb));
        zzhg zzhgVarZzd = zzibVar.zzd();
        int[] iArr = new int[sparseArrayZzf.size()];
        long[] jArr = new long[sparseArrayZzf.size()];
        for (int i = 0; i < sparseArrayZzf.size(); i++) {
            iArr[i] = sparseArrayZzf.keyAt(i);
            jArr[i] = ((Long) sparseArrayZzf.valueAt(i)).longValue();
        }
        Bundle bundle = new Bundle();
        bundle.putIntArray("uriSources", iArr);
        bundle.putLongArray("uriTimestamps", jArr);
        zzhgVarZzd.zzi.zzb(bundle);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.common.util.concurrent.FutureCallback
    public final void onFailure(Throwable th) {
        zzli zzliVar = this.zzb;
        zzliVar.zzg();
        zzliVar.zzal(false);
        zzib zzibVar = zzliVar.zzu;
        int iZzap = (zzibVar.zzc().zzp(null, zzfx.zzaT) ? zzliVar.zzap(th) : 2) - 1;
        if (iZzap == 0) {
            zzibVar.zzaV().zze().zzc("registerTriggerAsync failed with retriable error. Will try later. App ID, throwable", zzgt.zzl(zzliVar.zzu.zzv().zzj()), zzgt.zzl(th.toString()));
            zzliVar.zzan(1);
            zzliVar.zzy().add(this.zza);
            return;
        }
        if (iZzap != 1) {
            zzibVar.zzaV().zzb().zzc("registerTriggerAsync failed. Dropping URI. App ID, Throwable", zzgt.zzl(zzliVar.zzu.zzv().zzj()), th);
            zza();
            zzliVar.zzan(1);
            zzliVar.zzz();
            return;
        }
        zzliVar.zzy().add(this.zza);
        if (zzliVar.zzam() > ((Integer) zzfx.zzaw.zzb(null)).intValue()) {
            zzliVar.zzan(1);
            zzibVar.zzaV().zze().zzc("registerTriggerAsync failed. May try later. App ID, throwable", zzgt.zzl(zzliVar.zzu.zzv().zzj()), zzgt.zzl(th.toString()));
        } else {
            zzibVar.zzaV().zze().zzd("registerTriggerAsync failed. App ID, delay in seconds, throwable", zzgt.zzl(zzliVar.zzu.zzv().zzj()), zzgt.zzl(String.valueOf(zzliVar.zzam())), zzgt.zzl(th.toString()));
            zzliVar.zzah(zzliVar.zzam());
            int iZzam = zzliVar.zzam();
            zzliVar.zzan(iZzam + iZzam);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.common.util.concurrent.FutureCallback
    public final void onSuccess(Object obj) {
        zzli zzliVar = this.zzb;
        zzliVar.zzg();
        zza();
        zzliVar.zzal(false);
        zzliVar.zzan(1);
        zzliVar.zzu.zzaV().zzj().zzb("Successfully registered trigger URI", this.zza.zza);
        zzliVar.zzz();
    }
}
