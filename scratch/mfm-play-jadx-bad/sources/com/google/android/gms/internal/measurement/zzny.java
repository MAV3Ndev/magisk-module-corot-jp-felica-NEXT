package com.google.android.gms.internal.measurement;

import java.io.IOException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzny {
    public static final /* synthetic */ int zza = 0;
    private static final zzoh zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        int i = zznt.zza;
        zzb = new zzoj();
    }

    public static zzoh zzA() {
        return zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static boolean zzB(Object obj, Object obj2) {
        if (obj != obj2) {
            return obj != null && obj.equals(obj2);
        }
        return true;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzC(zzlr zzlrVar, Object obj, Object obj2) {
        if (((zzmb) obj2).zzb.zza.isEmpty()) {
            return;
        }
        throw null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static void zzD(zzoh zzohVar, Object obj, Object obj2) {
        zzme zzmeVar = (zzme) obj;
        zzoi zzoiVarZzc = zzmeVar.zzc;
        zzoi zzoiVar = ((zzme) obj2).zzc;
        if (!zzoi.zza().equals(zzoiVar)) {
            if (zzoi.zza().equals(zzoiVarZzc)) {
                zzoiVarZzc = zzoi.zzc(zzoiVarZzc, zzoiVar);
            } else {
                zzoiVarZzc.zzl(zzoiVar);
            }
        }
        zzmeVar.zzc = zzoiVarZzc;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static Object zzE(Object obj, int i, int i2, Object obj2, zzoh zzohVar) {
        if (obj2 == null) {
            obj2 = zzohVar.zza(obj);
        }
        ((zzoi) obj2).zzk(i << 3, Long.valueOf(i2));
        return obj2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zza(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzC(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzb(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzB(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzc(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzy(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzd(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzz(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zze(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzL(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzf(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzA(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzg(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzJ(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzh(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzw(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzi(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzH(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzj(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzK(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzk(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzx(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzl(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzI(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzm(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzD(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static void zzn(int i, List list, zzou zzouVar, boolean z) throws IOException {
        if (list == null || list.isEmpty()) {
            return;
        }
        zzouVar.zzE(i, list, z);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzo(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzmz)) {
            int iZzA = 0;
            while (i < size) {
                iZzA += zzll.zzA(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzA;
        }
        zzmz zzmzVar = (zzmz) list;
        int iZzA2 = 0;
        while (i < size) {
            iZzA2 += zzll.zzA(zzmzVar.zzc(i));
            i++;
        }
        return iZzA2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzp(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzmz)) {
            int iZzA = 0;
            while (i < size) {
                iZzA += zzll.zzA(((Long) list.get(i)).longValue());
                i++;
            }
            return iZzA;
        }
        zzmz zzmzVar = (zzmz) list;
        int iZzA2 = 0;
        while (i < size) {
            iZzA2 += zzll.zzA(zzmzVar.zzc(i));
            i++;
        }
        return iZzA2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzq(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzmz)) {
            int iZzA = 0;
            while (i < size) {
                long jLongValue = ((Long) list.get(i)).longValue();
                iZzA += zzll.zzA((jLongValue >> 63) ^ (jLongValue + jLongValue));
                i++;
            }
            return iZzA;
        }
        zzmz zzmzVar = (zzmz) list;
        int iZzA2 = 0;
        while (i < size) {
            long jZzc = zzmzVar.zzc(i);
            iZzA2 += zzll.zzA((jZzc >> 63) ^ (jZzc + jZzc));
            i++;
        }
        return iZzA2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzr(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzmf)) {
            int iZzA = 0;
            while (i < size) {
                iZzA += zzll.zzA(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzA;
        }
        zzmf zzmfVar = (zzmf) list;
        int iZzA2 = 0;
        while (i < size) {
            iZzA2 += zzll.zzA(zzmfVar.zzf(i));
            i++;
        }
        return iZzA2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzs(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzmf)) {
            int iZzA = 0;
            while (i < size) {
                iZzA += zzll.zzA(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzA;
        }
        zzmf zzmfVar = (zzmf) list;
        int iZzA2 = 0;
        while (i < size) {
            iZzA2 += zzll.zzA(zzmfVar.zzf(i));
            i++;
        }
        return iZzA2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzt(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzmf)) {
            int iZzz = 0;
            while (i < size) {
                iZzz += zzll.zzz(((Integer) list.get(i)).intValue());
                i++;
            }
            return iZzz;
        }
        zzmf zzmfVar = (zzmf) list;
        int iZzz2 = 0;
        while (i < size) {
            iZzz2 += zzll.zzz(zzmfVar.zzf(i));
            i++;
        }
        return iZzz2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzu(List list) {
        int size = list.size();
        int i = 0;
        if (size == 0) {
            return 0;
        }
        if (!(list instanceof zzmf)) {
            int iZzz = 0;
            while (i < size) {
                int iIntValue = ((Integer) list.get(i)).intValue();
                iZzz += zzll.zzz((iIntValue >> 31) ^ (iIntValue + iIntValue));
                i++;
            }
            return iZzz;
        }
        zzmf zzmfVar = (zzmf) list;
        int iZzz2 = 0;
        while (i < size) {
            int iZzf = zzmfVar.zzf(i);
            iZzz2 += zzll.zzz((iZzf >> 31) ^ (iZzf + iZzf));
            i++;
        }
        return iZzz2;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzv(List list) {
        return list.size() * 4;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzw(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzll.zzz(i << 3) + 4);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzx(List list) {
        return list.size() * 8;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzy(int i, List list, boolean z) {
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        return size * (zzll.zzz(i << 3) + 8);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static int zzz(int i, Object obj, zznw zznwVar) {
        int i2 = i << 3;
        if (!(obj instanceof zzmv)) {
            return zzll.zzz(i2) + zzll.zzD((zznl) obj, zznwVar);
        }
        int iZzz = zzll.zzz(i2);
        int iZzb = ((zzmv) obj).zzb();
        return iZzz + zzll.zzz(iZzb) + iZzb;
    }
}
