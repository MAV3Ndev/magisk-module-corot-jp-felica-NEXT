package com.google.android.gms.measurement.internal;

import android.content.SharedPreferences;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzhe {
    final String zza;
    final /* synthetic */ zzhg zzb;
    private final String zzc;
    private final String zzd;
    private final long zze;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* synthetic */ zzhe(zzhg zzhgVar, String str, long j, byte[] bArr) {
        Objects.requireNonNull(zzhgVar);
        this.zzb = zzhgVar;
        Preconditions.checkNotEmpty("health_monitor");
        Preconditions.checkArgument(j > 0);
        this.zza = "health_monitor:start";
        this.zzc = "health_monitor:count";
        this.zzd = "health_monitor:value";
        this.zze = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final void zzc() {
        zzhg zzhgVar = this.zzb;
        zzhgVar.zzg();
        long jCurrentTimeMillis = zzhgVar.zzu.zzaZ().currentTimeMillis();
        SharedPreferences.Editor editorEdit = zzhgVar.zzd().edit();
        editorEdit.remove(this.zzc);
        editorEdit.remove(this.zzd);
        editorEdit.putLong(this.zza, jCurrentTimeMillis);
        editorEdit.apply();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private final long zzd() {
        return this.zzb.zzd().getLong(this.zza, 0L);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zza(String str, long j) {
        zzhg zzhgVar = this.zzb;
        zzhgVar.zzg();
        if (zzd() == 0) {
            zzc();
        }
        if (str == null) {
            str = "";
        }
        SharedPreferences sharedPreferencesZzd = zzhgVar.zzd();
        String str2 = this.zzc;
        long j2 = sharedPreferencesZzd.getLong(str2, 0L);
        if (j2 <= 0) {
            SharedPreferences.Editor editorEdit = zzhgVar.zzd().edit();
            editorEdit.putString(this.zzd, str);
            editorEdit.putLong(str2, 1L);
            editorEdit.apply();
            return;
        }
        long jNextLong = zzhgVar.zzu.zzk().zzf().nextLong() & Long.MAX_VALUE;
        long j3 = j2 + 1;
        long j4 = Long.MAX_VALUE / j3;
        SharedPreferences.Editor editorEdit2 = zzhgVar.zzd().edit();
        if (jNextLong < j4) {
            editorEdit2.putString(this.zzd, str);
        }
        editorEdit2.putLong(str2, j3);
        editorEdit2.apply();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Pair zzb() {
        long jAbs;
        zzhg zzhgVar = this.zzb;
        zzhgVar.zzg();
        zzhgVar.zzg();
        long jZzd = zzd();
        if (jZzd == 0) {
            zzc();
            jAbs = 0;
        } else {
            jAbs = Math.abs(jZzd - zzhgVar.zzu.zzaZ().currentTimeMillis());
        }
        long j = this.zze;
        if (jAbs < j) {
            return null;
        }
        if (jAbs > j + j) {
            zzc();
            return null;
        }
        String string = zzhgVar.zzd().getString(this.zzd, null);
        long j2 = zzhgVar.zzd().getLong(this.zzc, 0L);
        zzc();
        return (string == null || j2 <= 0) ? zzhg.zza : new Pair(string, Long.valueOf(j2));
    }
}
