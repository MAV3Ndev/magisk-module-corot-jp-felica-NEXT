package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzma extends zzg {
    protected zzlt zza;
    private volatile zzlt zzb;
    private volatile zzlt zzc;
    private final Map zzd;
    private com.google.android.gms.internal.measurement.zzdf zze;
    private volatile boolean zzf;
    private volatile zzlt zzg;
    private zzlt zzh;
    private boolean zzi;
    private final Object zzj;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzma(zzib zzibVar) {
        super(zzibVar);
        this.zzj = new Object();
        this.zzd = new ConcurrentHashMap();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzA(zzlt zzltVar, boolean z, long j) {
        zzib zzibVar = this.zzu;
        zzib zzibVar2 = this.zzu;
        zzibVar2.zzw().zzc(zzibVar.zzaZ().elapsedRealtime());
        if (!zzibVar2.zzh().zzb.zzd(zzltVar != null && zzltVar.zzd, z, j) || zzltVar == null) {
            return;
        }
        zzltVar.zzd = false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final zzlt zzB(com.google.android.gms.internal.measurement.zzdf zzdfVar) {
        Preconditions.checkNotNull(zzdfVar);
        Integer numValueOf = Integer.valueOf(zzdfVar.zza);
        Map map = this.zzd;
        zzlt zzltVar = (zzlt) map.get(numValueOf);
        if (zzltVar == null) {
            zzlt zzltVar2 = new zzlt(null, zzi(zzdfVar.zzb, "Activity"), this.zzu.zzk().zzd());
            map.put(numValueOf, zzltVar2);
            zzltVar = zzltVar2;
        }
        return this.zzg != null ? this.zzg : zzltVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzy(String str, zzlt zzltVar, boolean z) {
        zzlt zzltVar2;
        zzlt zzltVar3 = this.zzb == null ? this.zzc : this.zzb;
        if (zzltVar.zzb == null) {
            zzltVar2 = new zzlt(zzltVar.zza, str != null ? zzi(str, "Activity") : null, zzltVar.zzc, zzltVar.zze, zzltVar.zzf);
        } else {
            zzltVar2 = zzltVar;
        }
        this.zzc = this.zzb;
        this.zzb = zzltVar2;
        zzib zzibVar = this.zzu;
        zzibVar.zzaW().zzj(new zzlv(this, zzltVar2, zzltVar3, zzibVar.zzaZ().elapsedRealtime(), z));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:12:0x002d  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00b3  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void zzz(zzlt zzltVar, zzlt zzltVar2, long j, boolean z, Bundle bundle) {
        boolean z2;
        zzg();
        boolean z3 = false;
        if (zzltVar2 != null) {
            z2 = (zzltVar2.zzc == zzltVar.zzc && Objects.equals(zzltVar2.zzb, zzltVar.zzb) && Objects.equals(zzltVar2.zza, zzltVar.zza)) ? false : true;
        }
        if (z && this.zza != null) {
            z3 = true;
        }
        if (z2) {
            Bundle bundle2 = bundle != null ? new Bundle(bundle) : new Bundle();
            zzpo.zzav(zzltVar, bundle2, true);
            if (zzltVar2 != null) {
                String str = zzltVar2.zza;
                if (str != null) {
                    bundle2.putString("_pn", str);
                }
                String str2 = zzltVar2.zzb;
                if (str2 != null) {
                    bundle2.putString("_pc", str2);
                }
                bundle2.putLong("_pi", zzltVar2.zzc);
            }
            if (z3) {
                zznz zznzVar = this.zzu.zzh().zzb;
                long j2 = j - zznzVar.zzb;
                zznzVar.zzb = j;
                if (j2 > 0) {
                    this.zzu.zzk().zzak(bundle2, j2);
                }
            }
            zzib zzibVar = this.zzu;
            if (!zzibVar.zzc().zzv()) {
                bundle2.putLong("_mst", 1L);
            }
            boolean z4 = zzltVar.zze;
            String str3 = true != z4 ? DebugKt.DEBUG_PROPERTY_VALUE_AUTO : "app";
            long jCurrentTimeMillis = zzibVar.zzaZ().currentTimeMillis();
            if (z4) {
                long j3 = zzltVar.zzf;
                long j4 = j3 == 0 ? jCurrentTimeMillis : j3;
                this.zzu.zzj().zzG(str3, "_vs", j4, bundle2);
            }
        }
        if (z3) {
            zzA(this.zza, true, j);
        }
        this.zza = zzltVar;
        if (zzltVar.zze) {
            this.zzh = zzltVar;
        }
        this.zzu.zzt().zzG(zzltVar);
    }

    @Override // com.google.android.gms.measurement.internal.zzg
    protected final boolean zze() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzlt zzh(boolean z) {
        zzb();
        zzg();
        if (!z) {
            return this.zza;
        }
        zzlt zzltVar = this.zza;
        return zzltVar != null ? zzltVar : this.zzh;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final String zzi(String str, String str2) {
        if (str == null) {
            return "Activity";
        }
        String[] strArrSplit = str.split("\\.");
        int length = strArrSplit.length;
        String str3 = length > 0 ? strArrSplit[length - 1] : "";
        zzib zzibVar = this.zzu;
        return str3.length() > zzibVar.zzc().zze(null, false) ? str3.substring(0, zzibVar.zzc().zze(null, false)) : str3;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzj(Bundle bundle, long j) {
        synchronized (this.zzj) {
            if (!this.zzi) {
                this.zzu.zzaV().zzh().zza("Cannot log screen view event when the app is in the background.");
                return;
            }
            String string = bundle.getString(FirebaseAnalytics.Param.SCREEN_NAME);
            if (string != null && (string.length() <= 0 || string.length() > this.zzu.zzc().zze(null, false))) {
                this.zzu.zzaV().zzh().zzb("Invalid screen name length for screen view. Length", Integer.valueOf(string.length()));
                return;
            }
            String string2 = bundle.getString(FirebaseAnalytics.Param.SCREEN_CLASS);
            if (string2 != null && (string2.length() <= 0 || string2.length() > this.zzu.zzc().zze(null, false))) {
                this.zzu.zzaV().zzh().zzb("Invalid screen class length for screen view. Length", Integer.valueOf(string2.length()));
                return;
            }
            if (string2 == null) {
                com.google.android.gms.internal.measurement.zzdf zzdfVar = this.zze;
                string2 = zzdfVar != null ? zzi(zzdfVar.zzb, "Activity") : "Activity";
            }
            zzlt zzltVar = this.zzb;
            if (this.zzf && zzltVar != null) {
                this.zzf = false;
                boolean zEquals = Objects.equals(zzltVar.zzb, string2);
                boolean zEquals2 = Objects.equals(zzltVar.zza, string);
                if (zEquals && zEquals2) {
                    this.zzu.zzaV().zzh().zza("Ignoring call to log screen view event with duplicate parameters.");
                    return;
                }
            }
            zzib zzibVar = this.zzu;
            zzibVar.zzaV().zzk().zzc("Logging screen view with name, class", string == null ? "null" : string, string2 == null ? "null" : string2);
            zzlt zzltVar2 = this.zzb == null ? this.zzc : this.zzb;
            zzlt zzltVar3 = new zzlt(string, string2, zzibVar.zzk().zzd(), true, j);
            this.zzb = zzltVar3;
            this.zzc = zzltVar2;
            this.zzg = zzltVar3;
            zzibVar.zzaW().zzj(new zzlu(this, bundle, zzltVar3, zzltVar2, zzibVar.zzaZ().elapsedRealtime()));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Deprecated
    public final void zzk(com.google.android.gms.internal.measurement.zzdf zzdfVar, String str, String str2) {
        zzib zzibVar = this.zzu;
        if (!zzibVar.zzc().zzv()) {
            zzibVar.zzaV().zzh().zza("setCurrentScreen cannot be called while screen reporting is disabled.");
            return;
        }
        zzlt zzltVar = this.zzb;
        if (zzltVar == null) {
            zzibVar.zzaV().zzh().zza("setCurrentScreen cannot be called while no activity active");
            return;
        }
        Map map = this.zzd;
        Integer numValueOf = Integer.valueOf(zzdfVar.zza);
        if (map.get(numValueOf) == null) {
            zzibVar.zzaV().zzh().zza("setCurrentScreen must be called with an activity in the activity lifecycle");
            return;
        }
        if (str2 == null) {
            str2 = zzi(zzdfVar.zzb, "Activity");
        }
        String str3 = zzltVar.zzb;
        String str4 = zzltVar.zza;
        boolean zEquals = Objects.equals(str3, str2);
        boolean zEquals2 = Objects.equals(str4, str);
        if (zEquals && zEquals2) {
            zzibVar.zzaV().zzh().zza("setCurrentScreen cannot be called with the same class and name");
            return;
        }
        if (str != null && (str.length() <= 0 || str.length() > zzibVar.zzc().zze(null, false))) {
            zzibVar.zzaV().zzh().zzb("Invalid screen name length in setCurrentScreen. Length", Integer.valueOf(str.length()));
            return;
        }
        if (str2 != null && (str2.length() <= 0 || str2.length() > zzibVar.zzc().zze(null, false))) {
            zzibVar.zzaV().zzh().zzb("Invalid class name length in setCurrentScreen. Length", Integer.valueOf(str2.length()));
            return;
        }
        zzibVar.zzaV().zzk().zzc("Setting current screen to name, class", str == null ? "null" : str, str2);
        zzlt zzltVar2 = new zzlt(str, str2, zzibVar.zzk().zzd());
        map.put(numValueOf, zzltVar2);
        zzy(zzdfVar.zzb, zzltVar2, true);
    }

    public final zzlt zzl() {
        return this.zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzm(com.google.android.gms.internal.measurement.zzdf zzdfVar, Bundle bundle) {
        Bundle bundle2;
        if (!this.zzu.zzc().zzv() || bundle == null || (bundle2 = bundle.getBundle("com.google.app_measurement.screen_service")) == null) {
            return;
        }
        this.zzd.put(Integer.valueOf(zzdfVar.zza), new zzlt(bundle2.getString("name"), bundle2.getString("referrer_name"), bundle2.getLong("id")));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzn(com.google.android.gms.internal.measurement.zzdf zzdfVar) {
        Object obj = this.zzj;
        synchronized (obj) {
            this.zzi = true;
            if (!Objects.equals(zzdfVar, this.zze)) {
                synchronized (obj) {
                    this.zze = zzdfVar;
                    this.zzf = false;
                    zzib zzibVar = this.zzu;
                    if (zzibVar.zzc().zzv()) {
                        this.zzg = null;
                        zzibVar.zzaW().zzj(new zzlz(this));
                    }
                }
            }
        }
        zzib zzibVar2 = this.zzu;
        if (!zzibVar2.zzc().zzv()) {
            this.zzb = this.zzg;
            zzibVar2.zzaW().zzj(new zzlw(this));
            return;
        }
        zzy(zzdfVar.zzb, zzB(zzdfVar), false);
        zzd zzdVarZzw = this.zzu.zzw();
        zzib zzibVar3 = zzdVarZzw.zzu;
        zzibVar3.zzaW().zzj(new zzc(zzdVarZzw, zzibVar3.zzaZ().elapsedRealtime()));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzp(com.google.android.gms.internal.measurement.zzdf zzdfVar) {
        synchronized (this.zzj) {
            this.zzi = false;
            this.zzf = true;
        }
        zzib zzibVar = this.zzu;
        long jElapsedRealtime = zzibVar.zzaZ().elapsedRealtime();
        if (!zzibVar.zzc().zzv()) {
            this.zzb = null;
            zzibVar.zzaW().zzj(new zzlx(this, jElapsedRealtime));
        } else {
            zzlt zzltVarZzB = zzB(zzdfVar);
            this.zzc = this.zzb;
            this.zzb = null;
            zzibVar.zzaW().zzj(new zzly(this, zzltVarZzB, jElapsedRealtime));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzq(com.google.android.gms.internal.measurement.zzdf zzdfVar, Bundle bundle) {
        zzlt zzltVar;
        if (!this.zzu.zzc().zzv() || bundle == null || (zzltVar = (zzlt) this.zzd.get(Integer.valueOf(zzdfVar.zza))) == null) {
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putLong("id", zzltVar.zzc);
        bundle2.putString("name", zzltVar.zza);
        bundle2.putString("referrer_name", zzltVar.zzb);
        bundle.putBundle("com.google.app_measurement.screen_service", bundle2);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzs(com.google.android.gms.internal.measurement.zzdf zzdfVar) {
        synchronized (this.zzj) {
            if (Objects.equals(this.zze, zzdfVar)) {
                this.zze = null;
            }
        }
        if (this.zzu.zzc().zzv()) {
            this.zzd.remove(Integer.valueOf(zzdfVar.zza));
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final /* synthetic */ void zzt(Bundle bundle, zzlt zzltVar, zzlt zzltVar2, long j) {
        bundle.remove(FirebaseAnalytics.Param.SCREEN_NAME);
        bundle.remove(FirebaseAnalytics.Param.SCREEN_CLASS);
        zzz(zzltVar, zzltVar2, j, true, this.zzu.zzk().zzF(null, FirebaseAnalytics.Event.SCREEN_VIEW, bundle, null, false));
    }

    final /* synthetic */ void zzu(zzlt zzltVar, zzlt zzltVar2, long j, boolean z, Bundle bundle) {
        zzz(zzltVar, zzltVar2, j, z, null);
    }

    final /* synthetic */ void zzv(zzlt zzltVar, boolean z, long j) {
        zzA(zzltVar, false, j);
    }

    final /* synthetic */ zzlt zzw() {
        return this.zzh;
    }

    final /* synthetic */ void zzx(zzlt zzltVar) {
        this.zzh = null;
    }
}
