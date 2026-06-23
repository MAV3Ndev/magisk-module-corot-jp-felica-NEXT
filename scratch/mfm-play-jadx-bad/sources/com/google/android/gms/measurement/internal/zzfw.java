package com.google.android.gms.measurement.internal;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzfw {
    private static final Object zze = new Object();
    private final String zza;
    private final zzbn zzb;
    private final Object zzc;
    private final Object zzd = new Object();
    private volatile Object zzf = null;
    private volatile Object zzg = null;

    /* synthetic */ zzfw(String str, Object obj, Object obj2, zzbn zzbnVar, byte[] bArr) {
        this.zza = str;
        this.zzc = obj;
        this.zzb = zzbnVar;
    }

    public final String zza() {
        return this.zza;
    }

    /* JADX DEBUG: Class process forced to load method for inline: com.google.android.gms.measurement.internal.zzfx.zzb():java.util.List */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:55:0x005f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object zzb(Object obj) {
        zzbn zzbnVar;
        synchronized (this.zzd) {
        }
        if (obj != null) {
            return obj;
        }
        if (zzfr.zza == null) {
            return this.zzc;
        }
        synchronized (zze) {
            if (zzae.zza()) {
                return this.zzg == null ? this.zzc : this.zzg;
            }
            try {
            } catch (SecurityException unused) {
            }
            for (zzfw zzfwVar : zzfx.zzbk) {
                if (zzae.zza()) {
                    throw new IllegalStateException("Refreshing flag cache must be done on a worker thread.");
                }
                Object objZza = null;
                try {
                    zzbn zzbnVar2 = zzfwVar.zzb;
                    if (zzbnVar2 != null) {
                        objZza = zzbnVar2.zza();
                    }
                } catch (IllegalStateException unused2) {
                }
                synchronized (zze) {
                    zzfwVar.zzg = objZza;
                }
                zzbnVar = this.zzb;
                if (zzbnVar != null) {
                    try {
                        return zzbnVar.zza();
                    } catch (IllegalStateException | SecurityException unused3) {
                    }
                }
                return this.zzc;
            }
            zzbnVar = this.zzb;
            if (zzbnVar != null) {
            }
            return this.zzc;
        }
    }
}
