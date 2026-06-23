package com.google.android.gms.measurement.internal;

import android.util.Log;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzgq implements Runnable {
    final /* synthetic */ int zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ Object zzc;
    final /* synthetic */ Object zzd;
    final /* synthetic */ Object zze;
    final /* synthetic */ zzgt zzf;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzgq(zzgt zzgtVar, int i, String str, Object obj, Object obj2, Object obj3) {
        this.zza = i;
        this.zzb = str;
        this.zzc = obj;
        this.zzd = obj2;
        this.zze = obj3;
        Objects.requireNonNull(zzgtVar);
        this.zzf = zzgtVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        zzgt zzgtVar = this.zzf;
        zzhg zzhgVarZzd = zzgtVar.zzu.zzd();
        if (!zzhgVarZzd.zzv()) {
            Log.println(6, zzgtVar.zzn(), "Persisted config not initialized. Not logging error/warn");
            return;
        }
        if (zzgtVar.zzr() == 0) {
            zzib zzibVar = zzgtVar.zzu;
            if (zzibVar.zzc().zzj()) {
                zzibVar.zzaU();
                zzgtVar.zzs('C');
            } else {
                zzibVar.zzaU();
                zzgtVar.zzs('c');
            }
        }
        if (zzgtVar.zzt() < 0) {
            zzgtVar.zzu.zzc().zzi();
            zzgtVar.zzu(130000L);
        }
        int i = this.zza;
        char cZzr = zzgtVar.zzr();
        long jZzt = zzgtVar.zzt();
        String str = this.zzb;
        Object obj = this.zzc;
        Object obj2 = this.zzd;
        Object obj3 = this.zze;
        char cCharAt = "01VDIWEA?".charAt(i);
        String strZzo = zzgt.zzo(true, str, obj, obj2, obj3);
        int length = String.valueOf(cCharAt).length();
        StringBuilder sb = new StringBuilder(length + 1 + String.valueOf(cZzr).length() + String.valueOf(jZzt).length() + 1 + strZzo.length());
        sb.append("2");
        sb.append(cCharAt);
        sb.append(cZzr);
        sb.append(jZzt);
        sb.append(":");
        sb.append(strZzo);
        String string = sb.toString();
        if (string.length() > 1024) {
            string = str.substring(0, 1024);
        }
        zzhe zzheVar = zzhgVarZzd.zzb;
        if (zzheVar != null) {
            zzheVar.zza(string, 1L);
        }
    }
}
