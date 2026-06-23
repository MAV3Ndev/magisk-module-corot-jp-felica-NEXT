package com.google.android.gms.measurement.internal;

import android.content.pm.PackageManager;
import android.util.Pair;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zznm extends zzor {
    public final zzhd zza;
    public final zzhd zzb;
    public final zzhd zzc;
    public final zzhd zzd;
    public final zzhd zze;
    public final zzhd zzf;
    private final Map zzh;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zznm(zzpf zzpfVar) {
        super(zzpfVar);
        this.zzh = new HashMap();
        zzhg zzhgVarZzd = this.zzu.zzd();
        Objects.requireNonNull(zzhgVarZzd);
        this.zza = new zzhd(zzhgVarZzd, "last_delete_stale", 0L);
        zzhg zzhgVarZzd2 = this.zzu.zzd();
        Objects.requireNonNull(zzhgVarZzd2);
        this.zzb = new zzhd(zzhgVarZzd2, "last_delete_stale_batch", 0L);
        zzhg zzhgVarZzd3 = this.zzu.zzd();
        Objects.requireNonNull(zzhgVarZzd3);
        this.zzc = new zzhd(zzhgVarZzd3, "backoff", 0L);
        zzhg zzhgVarZzd4 = this.zzu.zzd();
        Objects.requireNonNull(zzhgVarZzd4);
        this.zzd = new zzhd(zzhgVarZzd4, "last_upload", 0L);
        zzhg zzhgVarZzd5 = this.zzu.zzd();
        Objects.requireNonNull(zzhgVarZzd5);
        this.zze = new zzhd(zzhgVarZzd5, "last_upload_attempt", 0L);
        zzhg zzhgVarZzd6 = this.zzu.zzd();
        Objects.requireNonNull(zzhgVarZzd6);
        this.zzf = new zzhd(zzhgVarZzd6, "midnight_offset", 0L);
    }

    @Override // com.google.android.gms.measurement.internal.zzor
    protected final boolean zzbb() {
        return false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final Pair zzc(String str, zzjk zzjkVar) {
        return zzjkVar.zzo(zzjj.AD_STORAGE) ? zzd(str) : new Pair("", false);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Deprecated
    final Pair zzd(String str) {
        zznl zznlVar;
        AdvertisingIdClient.Info advertisingIdInfo;
        zzg();
        zzib zzibVar = this.zzu;
        long jElapsedRealtime = zzibVar.zzaZ().elapsedRealtime();
        zznl zznlVar2 = (zznl) this.zzh.get(str);
        if (zznlVar2 != null && jElapsedRealtime < zznlVar2.zzc) {
            return new Pair(zznlVar2.zza, Boolean.valueOf(zznlVar2.zzb));
        }
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(true);
        long jZzl = zzibVar.zzc().zzl(str, zzfx.zza) + jElapsedRealtime;
        try {
            try {
                advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(zzibVar.zzaY());
            } catch (PackageManager.NameNotFoundException unused) {
                advertisingIdInfo = null;
                if (zznlVar2 != null && jElapsedRealtime < zznlVar2.zzc + this.zzu.zzc().zzl(str, zzfx.zzb)) {
                    return new Pair(zznlVar2.zza, Boolean.valueOf(zznlVar2.zzb));
                }
            }
        } catch (Exception e) {
            this.zzu.zzaV().zzj().zzb("Unable to get advertising id", e);
            zznlVar = new zznl("", false, jZzl);
        }
        if (advertisingIdInfo == null) {
            return new Pair("00000000-0000-0000-0000-000000000000", false);
        }
        String id = advertisingIdInfo.getId();
        zznlVar = id != null ? new zznl(id, advertisingIdInfo.isLimitAdTrackingEnabled(), jZzl) : new zznl("", advertisingIdInfo.isLimitAdTrackingEnabled(), jZzl);
        this.zzh.put(str, zznlVar);
        AdvertisingIdClient.setShouldSkipGmsCoreVersionCheck(false);
        return new Pair(zznlVar.zza, Boolean.valueOf(zznlVar.zzb));
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Deprecated
    final String zzf(String str, boolean z) {
        zzg();
        String str2 = z ? (String) zzd(str).first : "00000000-0000-0000-0000-000000000000";
        MessageDigest messageDigestZzO = zzpo.zzO();
        if (messageDigestZzO == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new BigInteger(1, messageDigestZzO.digest(str2.getBytes())));
    }
}
