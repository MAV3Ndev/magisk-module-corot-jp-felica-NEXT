package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzlq {
    static final zzlq zza = new zzlq(true);
    public static final /* synthetic */ int zzb = 0;
    private static volatile boolean zzc = false;
    private static volatile zzlq zzd;
    private final Map zze;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzlq() {
        this.zze = new HashMap();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static zzlq zza() {
        zzlq zzlqVar = zzd;
        if (zzlqVar != null) {
            return zzlqVar;
        }
        synchronized (zzlq.class) {
            zzlq zzlqVar2 = zzd;
            if (zzlqVar2 != null) {
                return zzlqVar2;
            }
            int i = zznt.zza;
            zzlq zzlqVarZzb = zzly.zzb(zzlq.class);
            zzd = zzlqVarZzb;
            return zzlqVarZzb;
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzmd zzb(zznl zznlVar, int i) {
        return (zzmd) this.zze.get(new zzlp(zznlVar, i));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    zzlq(boolean z) {
        this.zze = Collections.EMPTY_MAP;
    }
}
