package com.google.android.gms.measurement.internal;

import android.os.RemoteException;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzkl implements Runnable {
    final /* synthetic */ com.google.android.gms.internal.measurement.zzcu zza;
    final /* synthetic */ zzli zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzkl(zzli zzliVar, com.google.android.gms.internal.measurement.zzcu zzcuVar) {
        this.zza = zzcuVar;
        Objects.requireNonNull(zzliVar);
        this.zzb = zzliVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:13:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x006d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        Long lValueOf;
        zzli zzliVar = this.zzb;
        zzib zzibVar = zzliVar.zzu.zzh().zzu;
        if (zzibVar.zzd().zzl().zzo(zzjj.ANALYTICS_STORAGE)) {
            if (!zzibVar.zzd().zzp(zzibVar.zzaZ().currentTimeMillis()) && zzibVar.zzd().zzl.zza() != 0) {
                lValueOf = Long.valueOf(zzibVar.zzd().zzl.zza());
            }
            if (lValueOf == null) {
                zzliVar.zzu.zzk().zzam(this.zza, lValueOf.longValue());
                return;
            } else {
                try {
                    this.zza.zzb(null);
                    return;
                } catch (RemoteException e) {
                    this.zzb.zzu.zzaV().zzb().zzb("getSessionId failed with exception", e);
                    return;
                }
            }
        }
        zzibVar.zzaV().zzh().zza("Analytics storage consent denied; will not get session id");
        lValueOf = null;
        if (lValueOf == null) {
        }
    }
}
