package com.google.android.gms.internal.measurement;

import android.os.RemoteException;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-sdk-api@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzdv extends zzeq {
    final /* synthetic */ Runnable zza;
    final /* synthetic */ zzfb zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzdv(zzfb zzfbVar, Runnable runnable) {
        super(zzfbVar, true);
        this.zza = runnable;
        Objects.requireNonNull(zzfbVar);
        this.zzb = zzfbVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.measurement.zzeq
    final void zza() throws RemoteException {
        ((zzcr) Preconditions.checkNotNull(this.zzb.zzQ())).retrieveAndUploadBatches(new zzdu(this, this.zza));
    }
}
