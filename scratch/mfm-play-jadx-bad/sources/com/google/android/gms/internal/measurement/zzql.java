package com.google.android.gms.internal.measurement;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.checkerframework.dataflow.qual.SideEffectFree;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzql implements Supplier {
    private static final zzql zza = new zzql();
    private final Supplier zzb = Suppliers.ofInstance(new zzqn());

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zza() {
        zza.get().zza();
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zzb() {
        return zza.get().zzb();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zzc() {
        return zza.get().zzc();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zzd() {
        return zza.get().zzd();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zze() {
        return zza.get().zze();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zzf() {
        return zza.get().zzf();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zzg() {
        return zza.get().zzg();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zzh() {
        return zza.get().zzh();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @SideEffectFree
    public static boolean zzi() {
        return zza.get().zzi();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Method merged with bridge method: get()Ljava/lang/Object; */
    @Override // com.google.common.base.Supplier
    /* JADX INFO: renamed from: zzj, reason: merged with bridge method [inline-methods] */
    public final zzqm get() {
        return (zzqm) this.zzb.get();
    }
}
