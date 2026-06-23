package com.google.android.gms.internal.measurement;

import com.felicanetworks.semc.fcm.CloudMessagingWorker;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zznt {
    public static final /* synthetic */ int zza = 0;
    private static final zznt zzb = new zznt();
    private final ConcurrentMap zzd = new ConcurrentHashMap();
    private final zznx zzc = new zznc();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private zznt() {
    }

    public static zznt zza() {
        return zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zznw zzb(Class cls) {
        zzmo.zza(cls, CloudMessagingWorker.EXT_KEY_MESSAGE_TYPE);
        ConcurrentMap concurrentMap = this.zzd;
        zznw zznwVarZza = (zznw) concurrentMap.get(cls);
        if (zznwVarZza == null) {
            zznwVarZza = this.zzc.zza(cls);
            zzmo.zza(cls, CloudMessagingWorker.EXT_KEY_MESSAGE_TYPE);
            zznw zznwVar = (zznw) concurrentMap.putIfAbsent(cls, zznwVarZza);
            if (zznwVar != null) {
                return zznwVar;
            }
        }
        return zznwVarZza;
    }
}
