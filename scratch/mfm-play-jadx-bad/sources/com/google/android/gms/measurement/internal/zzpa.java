package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.text.TextUtils;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzpa implements zzpn {
    final /* synthetic */ zzpf zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzpa(zzpf zzpfVar) {
        Objects.requireNonNull(zzpfVar);
        this.zza = zzpfVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzpn
    public final void zza(String str, String str2, Bundle bundle) {
        if (!TextUtils.isEmpty(str)) {
            this.zza.zzaW().zzj(new zzoz(this, str, str2, bundle));
            return;
        }
        zzpf zzpfVar = this.zza;
        if (zzpfVar.zzaw() != null) {
            zzpfVar.zzaw().zzaV().zzb().zzb("AppId not known when logging event", str2);
        }
    }
}
