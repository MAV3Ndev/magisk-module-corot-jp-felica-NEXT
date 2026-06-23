package com.google.android.gms.internal.auth;

import android.content.Context;
import android.net.Uri;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-base@@18.0.10 */
/* JADX INFO: loaded from: classes3.dex */
public abstract class zzdc {
    public static final /* synthetic */ int zzd = 0;

    @Nullable
    private static volatile zzda zze = null;
    private static volatile boolean zzf = false;
    final zzcz zzb;
    final String zzc;
    private final Object zzj;
    private volatile int zzk = -1;
    private volatile Object zzl;
    private static final Object zza = new Object();
    private static final AtomicReference zzg = new AtomicReference();
    private static final zzde zzh = new zzde(zzcu.zza);
    private static final AtomicInteger zzi = new AtomicInteger();

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* synthetic */ zzdc(zzcz zzczVar, String str, Object obj, boolean z, zzdb zzdbVar) {
        if (zzczVar.zza == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zzb = zzczVar;
        this.zzc = str;
        this.zzj = obj;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzc() {
        zzi.incrementAndGet();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzd(final Context context) {
        if (zze != null || context == null) {
            return;
        }
        Object obj = zza;
        synchronized (obj) {
            if (zze == null) {
                synchronized (obj) {
                    zzda zzdaVar = zze;
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext != null) {
                        context = applicationContext;
                    }
                    if (zzdaVar == null || zzdaVar.zza() != context) {
                        zzcg.zzd();
                        zzdd.zzc();
                        zzco.zze();
                        zze = new zzcd(context, zzdo.zza(new zzdj() { // from class: com.google.android.gms.internal.auth.zzct
                            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
                            @Override // com.google.android.gms.internal.auth.zzdj
                            public final Object zza() {
                                Context context2 = context;
                                int i = zzdc.zzd;
                                return zzcp.zza(context2);
                            }
                        }));
                        zzi.incrementAndGet();
                    }
                }
            }
        }
    }

    abstract Object zza(Object obj);

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003b A[PHI: r2
  0x003b: PHI (r2v1 com.google.android.gms.internal.auth.zzdh) = (r2v0 com.google.android.gms.internal.auth.zzdh), (r2v5 com.google.android.gms.internal.auth.zzdh) binds: [B:8:0x0016, B:10:0x0026] A[DONT_GENERATE, DONT_INLINE]] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object zzb() {
        String strZza;
        Object objZzb;
        int i = zzi.get();
        if (this.zzk < i) {
            synchronized (this) {
                if (this.zzk < i) {
                    zzda zzdaVar = zze;
                    zzdh zzdhVarZzc = zzdh.zzc();
                    Object objZza = null;
                    if (zzdaVar != null) {
                        zzdhVarZzc = (zzdh) zzdaVar.zzb().zza();
                        if (zzdhVarZzc.zzb()) {
                            zzci zzciVar = (zzci) zzdhVarZzc.zza();
                            zzcz zzczVar = this.zzb;
                            strZza = zzciVar.zza(zzczVar.zza, null, zzczVar.zzc, this.zzc);
                        } else {
                            strZza = null;
                        }
                        if (zzdaVar == null) {
                            throw new IllegalStateException("Must call PhenotypeFlag.init() first");
                        }
                        Uri uri = this.zzb.zza;
                        zzcl zzclVarZza = uri != null ? zzcq.zza(zzdaVar.zza(), uri) ? zzcg.zza(zzdaVar.zza().getContentResolver(), this.zzb.zza, new Runnable() { // from class: com.google.android.gms.internal.auth.zzcs
                            @Override // java.lang.Runnable
                            public final void run() {
                                zzdc.zzc();
                            }
                        }) : null : zzdd.zza(zzdaVar.zza(), null, new Runnable() { // from class: com.google.android.gms.internal.auth.zzcs
                            @Override // java.lang.Runnable
                            public final void run() {
                                zzdc.zzc();
                            }
                        });
                        Object objZza2 = (zzclVarZza == null || (objZzb = zzclVarZza.zzb(this.zzc)) == null) ? null : zza(objZzb);
                        if (objZza2 == null) {
                            if (!this.zzb.zzd) {
                                String strZzc = zzco.zza(zzdaVar.zza()).zzb(this.zzb.zzd ? null : this.zzc);
                                if (strZzc != null) {
                                    objZza = zza(strZzc);
                                }
                            }
                            objZza2 = objZza == null ? this.zzj : objZza;
                        }
                        if (zzdhVarZzc.zzb()) {
                            objZza2 = strZza == null ? this.zzj : zza(strZza);
                        }
                        this.zzl = objZza2;
                        this.zzk = i;
                    }
                }
            }
        }
        return this.zzl;
    }
}
