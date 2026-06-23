package com.google.android.gms.internal.measurement;

import java.util.List;
import java.util.Map;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzl extends zzal {
    private final zzab zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzl(zzab zzabVar) {
        this.zzb = zzabVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0051  */
    @Override // com.google.android.gms.internal.measurement.zzal, com.google.android.gms.internal.measurement.zzao
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final zzao zzcA(String str, zzg zzgVar, List list) {
        byte b;
        switch (str.hashCode()) {
            case 21624207:
                b = str.equals("getEventName") ? (byte) 0 : (byte) -1;
                break;
            case 45521504:
                if (str.equals("getTimestamp")) {
                    b = 3;
                    break;
                }
                break;
            case 146575578:
                if (str.equals("getParamValue")) {
                    b = 1;
                    break;
                }
                break;
            case 700587132:
                if (str.equals("getParams")) {
                    b = 2;
                    break;
                }
                break;
            case 920706790:
                if (str.equals("setParamValue")) {
                    b = 5;
                    break;
                }
                break;
            case 1570616835:
                if (str.equals("setEventName")) {
                    b = 4;
                    break;
                }
                break;
        }
        if (b == 0) {
            zzh.zza("getEventName", 0, list);
            return new zzas(this.zzb.zzc().zzb());
        }
        if (b == 1) {
            zzh.zza("getParamValue", 1, list);
            return zzi.zza(this.zzb.zzc().zze(zzgVar.zza((zzao) list.get(0)).zzc()));
        }
        if (b == 2) {
            zzh.zza("getParams", 0, list);
            Map mapZzf = this.zzb.zzc().zzf();
            zzal zzalVar = new zzal();
            for (String str2 : mapZzf.keySet()) {
                zzalVar.zzm(str2, zzi.zza(mapZzf.get(str2)));
            }
            return zzalVar;
        }
        if (b == 3) {
            zzh.zza("getTimestamp", 0, list);
            return new zzah(Double.valueOf(this.zzb.zzc().zza()));
        }
        if (b != 4) {
            if (b != 5) {
                return super.zzcA(str, zzgVar, list);
            }
            zzh.zza("setParamValue", 2, list);
            String strZzc = zzgVar.zza((zzao) list.get(0)).zzc();
            zzao zzaoVarZza = zzgVar.zza((zzao) list.get(1));
            this.zzb.zzc().zzd(strZzc, zzh.zzj(zzaoVarZza));
            return zzaoVarZza;
        }
        zzh.zza("setEventName", 1, list);
        zzao zzaoVarZza2 = zzgVar.zza((zzao) list.get(0));
        if (zzf.equals(zzaoVarZza2) || zzg.equals(zzaoVarZza2)) {
            throw new IllegalArgumentException("Illegal event name");
        }
        this.zzb.zzc().zzc(zzaoVarZza2.zzc());
        return new zzas(zzaoVarZza2.zzc());
    }
}
