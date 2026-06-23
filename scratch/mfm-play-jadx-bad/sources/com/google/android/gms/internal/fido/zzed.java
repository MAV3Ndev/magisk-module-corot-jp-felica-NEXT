package com.google.android.gms.internal.fido;

import java.util.Iterator;
import java.util.Set;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzed {
    private static final zzdz zza = new zzeb();
    private static final zzdy zzb = new zzec();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static zzdv zza(Set set) {
        zzdv zzdvVar = new zzdv(zza, null);
        zzdvVar.zza(zzb);
        Iterator it = set.iterator();
        while (it.hasNext()) {
            zzdvVar.zzg((zzdk) it.next());
        }
        return zzdvVar;
    }
}
