package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Pair;
import android.util.SparseArray;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.internal.Preconditions;
import org.checkerframework.checker.nullness.qual.EnsuresNonNull;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzhg extends zzje {
    static final Pair zza = new Pair("", 0L);
    public zzhe zzb;
    public final zzhd zzc;
    public final zzhd zzd;
    public final zzhf zze;
    public final zzhd zzf;
    public final zzhb zzg;
    public final zzhf zzh;
    public final zzhc zzi;
    public final zzhb zzj;
    public final zzhd zzk;
    public final zzhd zzl;
    public boolean zzm;
    public final zzhb zzn;
    public final zzhb zzo;
    public final zzhd zzp;
    public final zzhf zzq;
    public final zzhf zzr;
    public final zzhd zzs;
    public final zzhc zzt;
    private SharedPreferences zzv;
    private SharedPreferences zzw;
    private String zzx;
    private boolean zzy;
    private long zzz;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzhg(zzib zzibVar) {
        super(zzibVar);
        this.zzf = new zzhd(this, "session_timeout", 1800000L);
        this.zzg = new zzhb(this, "start_new_session", true);
        this.zzk = new zzhd(this, "last_pause_time", 0L);
        this.zzl = new zzhd(this, "session_id", 0L);
        this.zzh = new zzhf(this, "non_personalized_ads", null);
        this.zzi = new zzhc(this, "last_received_uri_timestamps_by_source", null);
        this.zzj = new zzhb(this, "allow_remote_dynamite", false);
        this.zzc = new zzhd(this, "first_open_time", 0L);
        this.zzd = new zzhd(this, "app_install_time", 0L);
        this.zze = new zzhf(this, "app_instance_id", null);
        this.zzn = new zzhb(this, "app_backgrounded", false);
        this.zzo = new zzhb(this, "deep_link_retrieval_complete", false);
        this.zzp = new zzhd(this, "deep_link_retrieval_attempts", 0L);
        this.zzq = new zzhf(this, "firebase_feature_rollouts", null);
        this.zzr = new zzhf(this, "deferred_attribution_cache", null);
        this.zzs = new zzhd(this, "deferred_attribution_cache_timestamp", 0L);
        this.zzt = new zzhc(this, "default_event_parameters", null);
    }

    @Override // com.google.android.gms.measurement.internal.zzje
    protected final boolean zza() {
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final Pair zzb(String str) {
        zzg();
        if (!zzl().zzo(zzjj.AD_STORAGE)) {
            return new Pair("", false);
        }
        zzib zzibVar = this.zzu;
        long jElapsedRealtime = zzibVar.zzaZ().elapsedRealtime();
        String str2 = this.zzx;
        if (str2 != null && jElapsedRealtime < this.zzz) {
            return new Pair(str2, Boolean.valueOf(this.zzy));
        }
        this.zzz = jElapsedRealtime + zzibVar.zzc().zzl(str, zzfx.zza);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        try {
            AdvertisingIdClient.Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(zzibVar.zzaY());
            this.zzx = "";
            String id = advertisingIdInfo.getId();
            if (id != null) {
                this.zzx = id;
            }
            this.zzy = advertisingIdInfo.isLimitAdTrackingEnabled();
        } catch (Exception e) {
            this.zzu.zzaV().zzj().zzb("Unable to get advertising id", e);
            this.zzx = "";
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(this.zzx, Boolean.valueOf(this.zzy));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.measurement.internal.zzje
    @EnsuresNonNull.List({@EnsuresNonNull({"this.preferences"}), @EnsuresNonNull({"this.monitoringSample"})})
    protected final void zzba() {
        zzib zzibVar = this.zzu;
        SharedPreferences sharedPreferences = zzibVar.zzaY().getSharedPreferences("com.google.android.gms.measurement.prefs", 0);
        this.zzv = sharedPreferences;
        boolean z = sharedPreferences.getBoolean("has_been_opened", false);
        this.zzm = z;
        if (!z) {
            SharedPreferences.Editor editorEdit = this.zzv.edit();
            editorEdit.putBoolean("has_been_opened", true);
            editorEdit.apply();
        }
        zzibVar.zzc();
        this.zzb = new zzhe(this, "health_monitor", Math.max(0L, ((Long) zzfx.zzc.zzb(null)).longValue()), null);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final SharedPreferences zzd() {
        zzg();
        zzw();
        Preconditions.checkNotNull(this.zzv);
        return this.zzv;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final SharedPreferences zze() {
        zzg();
        zzw();
        if (this.zzw == null) {
            zzib zzibVar = this.zzu;
            String packageName = zzibVar.zzaY().getPackageName();
            String.valueOf(packageName);
            String strValueOf = String.valueOf(packageName);
            zzgr zzgrVarZzk = zzibVar.zzaV().zzk();
            String strConcat = strValueOf.concat("_preferences");
            zzgrVarZzk.zzb("Default prefs file", strConcat);
            this.zzw = zzibVar.zzaY().getSharedPreferences(strConcat, 0);
        }
        return this.zzw;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final SparseArray zzf() {
        Bundle bundleZza = this.zzi.zza();
        int[] intArray = bundleZza.getIntArray("uriSources");
        long[] longArray = bundleZza.getLongArray("uriTimestamps");
        if (intArray == null || longArray == null) {
            return new SparseArray();
        }
        if (intArray.length != longArray.length) {
            this.zzu.zzaV().zzb().zza("Trigger URI source and timestamp array lengths do not match");
            return new SparseArray();
        }
        SparseArray sparseArray = new SparseArray();
        for (int i = 0; i < intArray.length; i++) {
            sparseArray.put(intArray[i], Long.valueOf(longArray[i]));
        }
        return sparseArray;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzh(Boolean bool) {
        zzg();
        SharedPreferences.Editor editorEdit = zzd().edit();
        if (bool != null) {
            editorEdit.putBoolean("measurement_enabled", bool.booleanValue());
        } else {
            editorEdit.remove("measurement_enabled");
        }
        editorEdit.apply();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final Boolean zzi() {
        zzg();
        if (zzd().contains("measurement_enabled")) {
            return Boolean.valueOf(zzd().getBoolean("measurement_enabled", true));
        }
        return null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final zzaz zzj() {
        zzg();
        return zzaz.zzg(zzd().getString("dma_consent_settings", null));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzk(int i) {
        return zzjk.zzu(i, zzd().getInt("consent_source", 100));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final zzjk zzl() {
        zzg();
        return zzjk.zzf(zzd().getString("consent_settings", "G1"), zzd().getInt("consent_source", 100));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    protected final boolean zzm(zzoc zzocVar) {
        zzg();
        String string = zzd().getString("stored_tcf_param", "");
        String strZza = zzocVar.zza();
        if (strZza.equals(string)) {
            return false;
        }
        SharedPreferences.Editor editorEdit = zzd().edit();
        editorEdit.putString("stored_tcf_param", strZza);
        editorEdit.apply();
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzn(boolean z) {
        zzg();
        this.zzu.zzaV().zzk().zzb("App measurement setting deferred collection", Boolean.valueOf(z));
        SharedPreferences.Editor editorEdit = zzd().edit();
        editorEdit.putBoolean("deferred_analytics_collection", z);
        editorEdit.apply();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzo() {
        SharedPreferences sharedPreferences = this.zzv;
        if (sharedPreferences == null) {
            return false;
        }
        return sharedPreferences.contains("deferred_analytics_collection");
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzp(long j) {
        return j - this.zzf.zza() > this.zzk.zza();
    }
}
