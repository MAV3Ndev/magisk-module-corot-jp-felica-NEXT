package com.google.android.gms.measurement.internal;

import android.util.Log;
import androidx.collection.ArrayMap;
import com.google.android.gms.internal.measurement.zzpq;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzaa extends zzab {
    final /* synthetic */ zzad zza;
    private final com.google.android.gms.internal.measurement.zzff zzh;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzaa(zzad zzadVar, String str, int i, com.google.android.gms.internal.measurement.zzff zzffVar) {
        super(str, i);
        Objects.requireNonNull(zzadVar);
        this.zza = zzadVar;
        this.zzh = zzffVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzab
    final int zza() {
        return this.zzh.zzb();
    }

    @Override // com.google.android.gms.measurement.internal.zzab
    final boolean zzb() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzab
    final boolean zzc() {
        return this.zzh.zzg();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX DEBUG: Multi-variable search result rejected for r14v1, resolved type: V */
    /* JADX DEBUG: Multi-variable search result rejected for r1v17, resolved type: java.lang.Object[] */
    /* JADX DEBUG: Multi-variable search result rejected for r1v6, resolved type: java.lang.Object[] */
    /* JADX DEBUG: Multi-variable search result rejected for r1v7, resolved type: java.lang.Object[] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ea  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    final boolean zzd(Long l, Long l2, com.google.android.gms.internal.measurement.zzhs zzhsVar, long j, zzbc zzbcVar, boolean z) {
        Boolean boolZzi;
        zzpq.zza();
        zzad zzadVar = this.zza;
        zzib zzibVar = zzadVar.zzu;
        zzal zzalVarZzc = zzibVar.zzc();
        String str = this.zzb;
        boolean zZzp = zzalVarZzc.zzp(str, zzfx.zzaF);
        com.google.android.gms.internal.measurement.zzff zzffVar = this.zzh;
        long j2 = zzffVar.zzm() ? zzbcVar.zze : j;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        bool = null;
        Boolean bool = null;
        if (Log.isLoggable(zzibVar.zzaV().zzn(), 2)) {
            zzibVar.zzaV().zzk().zzd("Evaluating filter. audience, filter, event", Integer.valueOf(this.zzc), zzffVar.zza() ? Integer.valueOf(zzffVar.zzb()) : null, zzibVar.zzl().zza(zzffVar.zzc()));
            zzibVar.zzaV().zzk().zzb("Filter definition", zzadVar.zzg.zzp().zzj(zzffVar));
        }
        if (!zzffVar.zza() || zzffVar.zzb() > 256) {
            zzibVar.zzaV().zze().zzc("Invalid event filter ID. appId, id", zzgt.zzl(str), String.valueOf(zzffVar.zza() ? Integer.valueOf(zzffVar.zzb()) : null));
            return false;
        }
        Object[] objArr = zzffVar.zzi() || zzffVar.zzj() || zzffVar.zzm();
        if (z && objArr != true) {
            zzibVar.zzaV().zzk().zzc("Event filter already evaluated true and it is not associated with an enhanced audience. audience ID, filter ID", Integer.valueOf(this.zzc), zzffVar.zza() ? Integer.valueOf(zzffVar.zzb()) : null);
            return true;
        }
        String strZzd = zzhsVar.zzd();
        if (!zzffVar.zzg()) {
            HashSet hashSet = new HashSet();
            Iterator it = zzffVar.zzd().iterator();
            while (true) {
                if (!it.hasNext()) {
                    ArrayMap arrayMap = new ArrayMap();
                    Iterator it2 = zzhsVar.zza().iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            Iterator it3 = zzffVar.zzd().iterator();
                            while (true) {
                                if (!it3.hasNext()) {
                                    bool = true;
                                    break;
                                }
                                com.google.android.gms.internal.measurement.zzfh zzfhVar = (com.google.android.gms.internal.measurement.zzfh) it3.next();
                                boolean z2 = zzfhVar.zze() && zzfhVar.zzf();
                                String strZzh = zzfhVar.zzh();
                                if (strZzh.isEmpty()) {
                                    zzibVar.zzaV().zze().zzb("Event has empty param name. event", zzibVar.zzl().zza(strZzd));
                                    break;
                                }
                                V v = arrayMap.get(strZzh);
                                if (v instanceof Long) {
                                    if (!zzfhVar.zzc()) {
                                        zzibVar.zzaV().zze().zzc("No number filter for long param. event, param", zzibVar.zzl().zza(strZzd), zzibVar.zzl().zzb(strZzh));
                                        break;
                                    }
                                    Boolean boolZzg = zzg(((Long) v).longValue(), zzfhVar.zzd());
                                    if (boolZzg == null) {
                                        break;
                                    }
                                    if (boolZzg.booleanValue() == z2) {
                                        bool = false;
                                        break;
                                    }
                                } else if (v instanceof Double) {
                                    if (!zzfhVar.zzc()) {
                                        zzibVar.zzaV().zze().zzc("No number filter for double param. event, param", zzibVar.zzl().zza(strZzd), zzibVar.zzl().zzb(strZzh));
                                        break;
                                    }
                                    Boolean boolZzh = zzh(((Double) v).doubleValue(), zzfhVar.zzd());
                                    if (boolZzh == null) {
                                        break;
                                    }
                                    if (boolZzh.booleanValue() == z2) {
                                        bool = false;
                                        break;
                                    }
                                } else if (v instanceof String) {
                                    if (!zzfhVar.zza()) {
                                        if (!zzfhVar.zzc()) {
                                            zzibVar.zzaV().zze().zzc("No filter for String param. event, param", zzibVar.zzl().zza(strZzd), zzibVar.zzl().zzb(strZzh));
                                            break;
                                        }
                                        String str2 = (String) v;
                                        if (!zzpj.zzm(str2)) {
                                            zzibVar.zzaV().zze().zzc("Invalid param value for number filter. event, param", zzibVar.zzl().zza(strZzd), zzibVar.zzl().zzb(strZzh));
                                            break;
                                        }
                                        boolZzi = zzi(str2, zzfhVar.zzd());
                                    } else {
                                        boolZzi = zzf((String) v, zzfhVar.zzb(), zzibVar.zzaV());
                                    }
                                    if (boolZzi == null) {
                                        break;
                                    }
                                    if (boolZzi.booleanValue() == z2) {
                                        bool = false;
                                        break;
                                    }
                                } else if (v == 0) {
                                    zzibVar.zzaV().zzk().zzc("Missing param for filter. event, param", zzibVar.zzl().zza(strZzd), zzibVar.zzl().zzb(strZzh));
                                    bool = false;
                                } else {
                                    zzibVar.zzaV().zze().zzc("Unknown param type. event, param", zzibVar.zzl().zza(strZzd), zzibVar.zzl().zzb(strZzh));
                                }
                            }
                        } else {
                            com.google.android.gms.internal.measurement.zzhw zzhwVar = (com.google.android.gms.internal.measurement.zzhw) it2.next();
                            if (hashSet.contains(zzhwVar.zzb())) {
                                if (!zzhwVar.zze()) {
                                    if (!zzhwVar.zzi()) {
                                        if (!zzhwVar.zzc()) {
                                            zzibVar.zzaV().zze().zzc("Unknown value for param. event, param", zzibVar.zzl().zza(strZzd), zzibVar.zzl().zzb(zzhwVar.zzb()));
                                            break;
                                        }
                                        arrayMap.put(zzhwVar.zzb(), zzhwVar.zzd());
                                    } else {
                                        arrayMap.put(zzhwVar.zzb(), zzhwVar.zzi() ? Double.valueOf(zzhwVar.zzj()) : null);
                                    }
                                } else {
                                    arrayMap.put(zzhwVar.zzb(), zzhwVar.zze() ? Long.valueOf(zzhwVar.zzf()) : null);
                                }
                            }
                        }
                    }
                } else {
                    com.google.android.gms.internal.measurement.zzfh zzfhVar2 = (com.google.android.gms.internal.measurement.zzfh) it.next();
                    if (zzfhVar2.zzh().isEmpty()) {
                        zzibVar.zzaV().zze().zzb("null or empty param name in filter. event", zzibVar.zzl().zza(strZzd));
                        break;
                    }
                    hashSet.add(zzfhVar2.zzh());
                }
            }
        } else {
            Boolean boolZzg2 = zzg(j2, zzffVar.zzh());
            if (boolZzg2 != null) {
                if (!boolZzg2.booleanValue()) {
                    bool = false;
                }
            }
        }
        zzibVar.zzaV().zzk().zzb("Event filter result", bool == null ? "null" : bool);
        if (bool == null) {
            return false;
        }
        this.zzd = true;
        if (!bool.booleanValue()) {
            return true;
        }
        this.zze = true;
        if (objArr != false && zzhsVar.zze()) {
            Long lValueOf = Long.valueOf(zzhsVar.zzf());
            if (zzffVar.zzj()) {
                if (zZzp && zzffVar.zzg()) {
                    lValueOf = l;
                }
                this.zzg = lValueOf;
            } else {
                if (zZzp && zzffVar.zzg()) {
                    lValueOf = l2;
                }
                this.zzf = lValueOf;
            }
        }
        return true;
    }
}
