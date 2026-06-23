package com.google.android.gms.internal.auth;

import com.felicanetworks.semc.fcm.CloudMessagingWorker;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.10 */
/* JADX INFO: loaded from: classes3.dex */
final class zzgf {
    private static final zzgf zza = new zzgf();
    private final ConcurrentMap zzc = new ConcurrentHashMap();
    private final zzgj zzb = new zzfp();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private zzgf() {
    }

    public static zzgf zza() {
        return zza;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzgi zzb(Class cls) {
        zzfa.zzc(cls, CloudMessagingWorker.EXT_KEY_MESSAGE_TYPE);
        zzgi zzgiVar = (zzgi) this.zzc.get(cls);
        if (zzgiVar != null) {
            return zzgiVar;
        }
        zzgi zzgiVarZza = this.zzb.zza(cls);
        zzfa.zzc(cls, CloudMessagingWorker.EXT_KEY_MESSAGE_TYPE);
        zzgi zzgiVar2 = (zzgi) this.zzc.putIfAbsent(cls, zzgiVarZza);
        return zzgiVar2 == null ? zzgiVarZza : zzgiVar2;
    }
}
