package com.google.android.gms.measurement.internal;

import android.text.TextUtils;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zze {
    private final zzjh zza;

    zze(zzjh zzjhVar) {
        this.zza = zzjhVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static zze zzc(String str) {
        return new zze((TextUtils.isEmpty(str) || str.length() > 1) ? zzjh.UNINITIALIZED : zzjk.zzj(str.charAt(0)));
    }

    final zzjh zza() {
        return this.zza;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final String zzb() {
        return String.valueOf(zzjk.zzm(this.zza));
    }
}
