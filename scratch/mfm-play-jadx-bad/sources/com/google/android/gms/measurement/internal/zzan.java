package com.google.android.gms.measurement.internal;

import java.util.EnumMap;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzan {
    private final EnumMap zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzan() {
        this.zza = new EnumMap(zzjj.class);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static zzan zzd(String str) {
        EnumMap enumMap = new EnumMap(zzjj.class);
        if (str.length() >= zzjj.values().length) {
            int i = 0;
            if (str.charAt(0) == '1') {
                zzjj[] zzjjVarArrValues = zzjj.values();
                int length = zzjjVarArrValues.length;
                int i2 = 1;
                while (i < length) {
                    enumMap.put(zzjjVarArrValues[i], zzam.zza(str.charAt(i2)));
                    i++;
                    i2++;
                }
                return new zzan(enumMap);
            }
        }
        return new zzan();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String toString() {
        StringBuilder sb = new StringBuilder("1");
        for (zzjj zzjjVar : zzjj.values()) {
            zzam zzamVar = (zzam) this.zza.get(zzjjVar);
            if (zzamVar == null) {
                zzamVar = zzam.UNSET;
            }
            sb.append(zzamVar.zzb());
        }
        return sb.toString();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzam zza(zzjj zzjjVar) {
        zzam zzamVar = (zzam) this.zza.get(zzjjVar);
        return zzamVar == null ? zzam.UNSET : zzamVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* JADX WARN: Removed duplicated region for block: B:14:0x001b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void zzb(zzjj zzjjVar, int i) {
        zzam zzamVar = zzam.UNSET;
        if (i == -30) {
            zzamVar = zzam.TCF;
        } else if (i == -20) {
            zzamVar = zzam.API;
        } else if (i == -10) {
            zzamVar = zzam.MANIFEST;
        } else if (i != 0) {
            if (i == 30) {
                zzamVar = zzam.INITIALIZATION;
            }
        }
        this.zza.put(zzjjVar, zzamVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzc(zzjj zzjjVar, zzam zzamVar) {
        this.zza.put(zzjjVar, zzamVar);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    private zzan(EnumMap enumMap) {
        EnumMap enumMap2 = new EnumMap(zzjj.class);
        this.zza = enumMap2;
        enumMap2.putAll(enumMap);
    }
}
