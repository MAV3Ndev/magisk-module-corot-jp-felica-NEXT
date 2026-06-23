package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.net.Uri;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import javax.annotation.Nullable;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public abstract class zzkl {
    public static final /* synthetic */ int zzc = 0;
    private static final Object zzd = new Object();

    @Nullable
    private static volatile zzkg zze = null;
    private static volatile boolean zzf = false;
    private static final AtomicInteger zzh;
    final zzkf zza;
    final String zzb;
    private Object zzg;
    private volatile int zzi = -1;
    private volatile Object zzj;
    private volatile boolean zzk;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        new AtomicReference();
        Preconditions.checkNotNull(zzkj.zza, "BuildInfo must be non-null");
        zzh = new AtomicInteger();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* synthetic */ zzkl(zzkf zzkfVar, String str, Object obj, boolean z, byte[] bArr) {
        if (zzkfVar.zza == null) {
            throw new IllegalArgumentException("Must pass a valid SharedPreferences file name or ContentProvider URI");
        }
        this.zza = zzkfVar;
        this.zzb = str;
        this.zzg = obj;
        this.zzk = false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzb(final Context context) {
        if (zze != null || context == null) {
            return;
        }
        Object obj = zzd;
        synchronized (obj) {
            if (zze == null) {
                synchronized (obj) {
                    zzkg zzkgVar = zze;
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext != null) {
                        context = applicationContext;
                    }
                    if (zzkgVar == null || zzkgVar.zza() != context) {
                        if (zzkgVar != null) {
                            zzjq.zzd();
                            zzkn.zzb();
                            zzjx.zzc();
                        }
                        zze = new zzjn(context, Suppliers.memoize(new Supplier() { // from class: com.google.android.gms.internal.measurement.zzkk
                            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
                            @Override // com.google.common.base.Supplier
                            public final /* synthetic */ Object get() {
                                int i = zzkl.zzc;
                                return zzjy.zza(context);
                            }
                        }));
                        zzh.incrementAndGet();
                    }
                }
            }
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzc() {
        zzh.incrementAndGet();
    }

    @Nullable
    abstract Object zza(Object obj);

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:16:0x004a  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x004c  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0058 A[Catch: all -> 0x00c9, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0018, B:11:0x001e, B:13:0x0034, B:18:0x004d, B:20:0x0058, B:22:0x0062, B:26:0x0085, B:28:0x008d, B:40:0x00b4, B:43:0x00bc, B:44:0x00bf, B:45:0x00c3, B:32:0x0096, B:34:0x009a, B:36:0x00aa, B:38:0x00b0, B:24:0x0073, B:46:0x00c7), top: B:53:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0073 A[Catch: all -> 0x00c9, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0018, B:11:0x001e, B:13:0x0034, B:18:0x004d, B:20:0x0058, B:22:0x0062, B:26:0x0085, B:28:0x008d, B:40:0x00b4, B:43:0x00bc, B:44:0x00bf, B:45:0x00c3, B:32:0x0096, B:34:0x009a, B:36:0x00aa, B:38:0x00b0, B:24:0x0073, B:46:0x00c7), top: B:53:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0095  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0096 A[Catch: all -> 0x00c9, TryCatch #0 {, blocks: (B:5:0x000b, B:7:0x000f, B:9:0x0018, B:11:0x001e, B:13:0x0034, B:18:0x004d, B:20:0x0058, B:22:0x0062, B:26:0x0085, B:28:0x008d, B:40:0x00b4, B:43:0x00bc, B:44:0x00bf, B:45:0x00c3, B:32:0x0096, B:34:0x009a, B:36:0x00aa, B:38:0x00b0, B:24:0x0073, B:46:0x00c7), top: B:53:0x000b }] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00ba  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object zzd() {
        String strZza;
        Object objZza;
        String strZzb;
        Object objZze;
        int i = zzh.get();
        if (this.zzi < i) {
            synchronized (this) {
                if (this.zzi < i) {
                    zzkg zzkgVar = zze;
                    Optional optionalAbsent = Optional.absent();
                    Object objZza2 = null;
                    if (zzkgVar != null && zzkgVar.zzb() != null) {
                        optionalAbsent = (Optional) ((Supplier) Preconditions.checkNotNull(zzkgVar.zzb())).get();
                        if (optionalAbsent.isPresent()) {
                            zzjs zzjsVar = (zzjs) optionalAbsent.get();
                            zzkf zzkfVar = this.zza;
                            strZza = zzjsVar.zza(zzkfVar.zza, null, zzkfVar.zzc, this.zzb);
                        }
                        Preconditions.checkState(zzkgVar == null, "Must call PhenotypeFlagInitializer.maybeInit() first");
                        zzkf zzkfVar2 = this.zza;
                        Uri uri = zzkfVar2.zza;
                        zzju zzjuVarZza = uri == null ? zzjz.zza(zzkgVar.zza(), uri) ? zzjq.zza(zzkgVar.zza().getContentResolver(), uri, zzki.zza) : null : zzkn.zza(zzkgVar.zza(), (String) Preconditions.checkNotNull(null), zzkh.zza);
                        objZza = (zzjuVarZza != null || (objZze = zzjuVarZza.zze(this.zzb)) == null) ? null : zza(objZze);
                        if (objZza == null) {
                            if (!zzkfVar2.zzd && (strZzb = zzjx.zza(zzkgVar.zza()).zze(this.zzb)) != null) {
                                objZza2 = zza(strZzb);
                            }
                            objZza = objZza2 == null ? this.zzg : objZza2;
                        }
                        if (optionalAbsent.isPresent()) {
                            objZza = strZza == null ? this.zzg : zza(strZza);
                        }
                        this.zzj = objZza;
                        this.zzi = i;
                    }
                    strZza = null;
                    Preconditions.checkState(zzkgVar == null, "Must call PhenotypeFlagInitializer.maybeInit() first");
                    zzkf zzkfVar22 = this.zza;
                    Uri uri2 = zzkfVar22.zza;
                    if (uri2 == null) {
                    }
                    if (zzjuVarZza != null) {
                    }
                    if (objZza == null) {
                    }
                    if (optionalAbsent.isPresent()) {
                    }
                    this.zzj = objZza;
                    this.zzi = i;
                }
            }
        }
        return this.zzj;
    }
}
