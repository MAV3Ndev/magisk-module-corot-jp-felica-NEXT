package com.google.android.gms.measurement.internal;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.measurement.zzqu;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzis implements Callable {
    final /* synthetic */ zzbg zza;
    final /* synthetic */ String zzb;
    final /* synthetic */ zzjc zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzis(zzjc zzjcVar, zzbg zzbgVar, String str) {
        this.zza = zzbgVar;
        this.zzb = str;
        Objects.requireNonNull(zzjcVar);
        this.zzc = zzjcVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.util.concurrent.Callable
    public final /* bridge */ /* synthetic */ Object call() throws Exception {
        zzlo zzloVar;
        zzh zzhVarZzu;
        byte[] bArr;
        zzpf zzpfVar;
        zzpm zzpmVar;
        zzpf zzpfVar2;
        zzh zzhVar;
        zzib zzibVar;
        com.google.android.gms.internal.measurement.zzic zzicVar;
        Bundle bundle;
        String str;
        boolean z;
        Object obj;
        com.google.android.gms.internal.measurement.zzhz zzhzVar;
        zzbc zzbcVarZza;
        long j;
        byte[] bArr2;
        zzjc zzjcVar = this.zzc;
        zzjcVar.zzL().zzY();
        zzlo zzloVarZzn = zzjcVar.zzL().zzn();
        zzloVarZzn.zzg();
        zzib zzibVar2 = zzloVarZzn.zzu;
        zzib.zzL();
        zzbg zzbgVar = this.zza;
        Preconditions.checkNotNull(zzbgVar);
        String str2 = this.zzb;
        Preconditions.checkNotEmpty(str2);
        String str3 = zzbgVar.zza;
        if (!"_iap".equals(str3) && !"_iapx".equals(str3)) {
            zzloVarZzn.zzu.zzaV().zzj().zzc("Generating a payload for this event is not available. package_name, event_name", str2, str3);
            return null;
        }
        zzpf zzpfVar3 = zzloVarZzn.zzg;
        com.google.android.gms.internal.measurement.zzhz zzhzVarZzh = com.google.android.gms.internal.measurement.zzib.zzh();
        zzpfVar3.zzj().zzb();
        try {
            zzhVarZzu = zzpfVar3.zzj().zzu(str2);
        } catch (Throwable th) {
            th = th;
            zzloVar = zzloVarZzn;
        }
        if (zzhVarZzu != null) {
            if (zzhVarZzu.zzD()) {
                com.google.android.gms.internal.measurement.zzic zzicVarZzaE = com.google.android.gms.internal.measurement.zzid.zzaE();
                zzicVarZzaE.zza(1);
                zzicVarZzaE.zzC("android");
                if (!TextUtils.isEmpty(zzhVarZzu.zzc())) {
                    zzicVarZzaE.zzL(zzhVarZzu.zzc());
                }
                if (!TextUtils.isEmpty(zzhVarZzu.zzv())) {
                    zzicVarZzaE.zzJ((String) Preconditions.checkNotNull(zzhVarZzu.zzv()));
                }
                if (!TextUtils.isEmpty(zzhVarZzu.zzr())) {
                    zzicVarZzaE.zzM((String) Preconditions.checkNotNull(zzhVarZzu.zzr()));
                }
                if (zzhVarZzu.zzt() != -2147483648L) {
                    zzicVarZzaE.zzaj((int) zzhVarZzu.zzt());
                }
                zzicVarZzaE.zzN(zzhVarZzu.zzx());
                zzicVarZzaE.zzar(zzhVarZzu.zzB());
                String strZzf = zzhVarZzu.zzf();
                if (!TextUtils.isEmpty(strZzf)) {
                    zzicVarZzaE.zzad(strZzf);
                }
                zzicVarZzaE.zzay(zzhVarZzu.zzak());
                zzjk zzjkVarZzB = zzloVarZzn.zzg.zzB(str2);
                zzicVarZzaE.zzY(zzhVarZzu.zzz());
                if (zzibVar2.zzB() && zzloVarZzn.zzu.zzc().zzC(zzicVarZzaE.zzK()) && zzjkVarZzB.zzo(zzjj.AD_STORAGE) && !TextUtils.isEmpty(null)) {
                    zzicVarZzaE.zzam(null);
                }
                zzicVarZzaE.zzat(zzjkVarZzB.zzk());
                if (zzjkVarZzB.zzo(zzjj.AD_STORAGE) && zzhVarZzu.zzac()) {
                    Pair pairZzc = zzpfVar3.zzq().zzc(zzhVarZzu.zzc(), zzjkVarZzB);
                    if (zzhVarZzu.zzac() && !TextUtils.isEmpty((CharSequence) pairZzc.first)) {
                        try {
                            zzicVarZzaE.zzQ(zzlo.zzc((String) pairZzc.first, Long.toString(zzbgVar.zzd)));
                            if (pairZzc.second != null) {
                                zzicVarZzaE.zzT(((Boolean) pairZzc.second).booleanValue());
                            }
                        } catch (SecurityException e) {
                            zzloVarZzn.zzu.zzaV().zzj().zzb("Resettable device id encryption failed", e.getMessage());
                            bArr = new byte[0];
                            zzpfVar = zzloVarZzn.zzg;
                            zzpfVar.zzj().zzd();
                            return bArr;
                        }
                    }
                }
                zzib zzibVar3 = zzloVarZzn.zzu;
                zzibVar3.zzu().zzw();
                zzicVarZzaE.zzF(Build.MODEL);
                zzibVar3.zzu().zzw();
                zzicVarZzaE.zzE(Build.VERSION.RELEASE);
                zzicVarZzaE.zzI((int) zzibVar3.zzu().zzb());
                zzicVarZzaE.zzH(zzibVar3.zzu().zzc());
                try {
                    if (zzjkVarZzB.zzo(zzjj.ANALYTICS_STORAGE) && zzhVarZzu.zzd() != null) {
                        zzicVarZzaE.zzW(zzlo.zzc((String) Preconditions.checkNotNull(zzhVarZzu.zzd()), Long.toString(zzbgVar.zzd)));
                    }
                    if (!TextUtils.isEmpty(zzhVarZzu.zzl())) {
                        zzicVarZzaE.zzah((String) Preconditions.checkNotNull(zzhVarZzu.zzl()));
                    }
                    String strZzc = zzhVarZzu.zzc();
                    zzpf zzpfVar4 = zzloVarZzn.zzg;
                    List listZzn = zzpfVar4.zzj().zzn(strZzc);
                    Iterator it = listZzn.iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            zzpmVar = null;
                            break;
                        }
                        zzpmVar = (zzpm) it.next();
                        if ("_lte".equals(zzpmVar.zzc)) {
                            break;
                        }
                    }
                    if (zzpmVar == null || zzpmVar.zze == null) {
                        zzpm zzpmVar2 = new zzpm(strZzc, DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_lte", zzloVarZzn.zzu.zzaZ().currentTimeMillis(), 0L);
                        listZzn.add(zzpmVar2);
                        zzpfVar4.zzj().zzl(zzpmVar2);
                    }
                    com.google.android.gms.internal.measurement.zziu[] zziuVarArr = new com.google.android.gms.internal.measurement.zziu[listZzn.size()];
                    for (int i = 0; i < listZzn.size(); i++) {
                        com.google.android.gms.internal.measurement.zzit zzitVarZzm = com.google.android.gms.internal.measurement.zziu.zzm();
                        zzitVarZzm.zzb(((zzpm) listZzn.get(i)).zzc);
                        zzitVarZzm.zza(((zzpm) listZzn.get(i)).zzd);
                        zzpfVar4.zzp().zzc(zzitVarZzm, ((zzpm) listZzn.get(i)).zze);
                        zziuVarArr[i] = (com.google.android.gms.internal.measurement.zziu) zzitVarZzm.zzbc();
                    }
                    zzicVarZzaE.zzq(Arrays.asList(zziuVarArr));
                    zzpf zzpfVar5 = zzloVarZzn.zzg;
                    zzpfVar5.zzI(zzhVarZzu, zzicVarZzaE);
                    zzpfVar5.zzJ(zzhVarZzu, zzicVarZzaE);
                    zzgu zzguVarZza = zzgu.zza(zzbgVar);
                    zzib zzibVar4 = zzloVarZzn.zzu;
                    zzpo zzpoVarZzk = zzibVar4.zzk();
                    Bundle bundle2 = zzguVarZza.zzd;
                    zzpoVarZzk.zzI(bundle2, zzpfVar4.zzj().zzW(str2));
                    zzibVar4.zzk().zzG(zzguVarZza, zzibVar4.zzc().zzd(str2));
                    bundle2.putLong("_c", 1L);
                    zzibVar4.zzaV().zzj().zza("Marking in-app purchase as real-time");
                    bundle2.putLong("_r", 1L);
                    String str4 = zzbgVar.zzc;
                    bundle2.putString("_o", str4);
                    if (zzibVar4.zzk().zzaa(zzicVarZzaE.zzK(), zzhVarZzu.zzay())) {
                        zzibVar4.zzk().zzM(bundle2, "_dbg", 1L);
                        zzibVar4.zzk().zzM(bundle2, "_r", 1L);
                    }
                    zzav zzavVarZzj = zzpfVar4.zzj();
                    String str5 = zzbgVar.zza;
                    zzbc zzbcVarZzf = zzavVarZzj.zzf(str2, str5);
                    if (zzbcVarZzf == null) {
                        zzhVar = zzhVarZzu;
                        zzibVar = zzibVar4;
                        zzicVar = zzicVarZzaE;
                        zzpfVar2 = zzpfVar5;
                        obj = null;
                        bundle = bundle2;
                        str = str4;
                        zzbcVarZza = new zzbc(str2, str5, 0L, 0L, 0L, zzbgVar.zzd, 0L, null, null, null, null);
                        z = true;
                        j = 0;
                        zzhzVar = zzhzVarZzh;
                    } else {
                        zzpfVar2 = zzpfVar5;
                        zzhVar = zzhVarZzu;
                        zzibVar = zzibVar4;
                        zzicVar = zzicVarZzaE;
                        bundle = bundle2;
                        str = str4;
                        z = true;
                        obj = null;
                        zzhzVar = zzhzVarZzh;
                        long j2 = zzbcVarZzf.zzf;
                        zzbcVarZza = zzbcVarZzf.zza(zzbgVar.zzd);
                        j = j2;
                    }
                    zzbc zzbcVar = zzbcVarZza;
                    zzpfVar4.zzj().zzh(zzbcVar);
                    zzib zzibVar5 = zzloVarZzn.zzu;
                    long j3 = zzbgVar.zzd;
                    boolean z2 = z;
                    zzh zzhVar2 = zzhVar;
                    com.google.android.gms.internal.measurement.zzic zzicVar2 = zzicVar;
                    zzpf zzpfVar6 = zzpfVar2;
                    try {
                        zzbb zzbbVar = new zzbb(zzibVar5, str, str2, str5, j3, j, bundle);
                        com.google.android.gms.internal.measurement.zzhr zzhrVarZzk = com.google.android.gms.internal.measurement.zzhs.zzk();
                        zzhrVarZzk.zzo(zzbbVar.zzd);
                        zzhrVarZzk.zzl(zzbbVar.zzb);
                        zzhrVarZzk.zzq(zzbbVar.zze);
                        zzbe zzbeVar = zzbbVar.zzf;
                        zzbd zzbdVar = new zzbd(zzbeVar);
                        while (zzbdVar.hasNext()) {
                            String next = zzbdVar.next();
                            String str6 = next;
                            com.google.android.gms.internal.measurement.zzhv zzhvVarZzn = com.google.android.gms.internal.measurement.zzhw.zzn();
                            zzhvVarZzn.zzb(next);
                            Object objZza = zzbeVar.zza(next);
                            if (objZza != null) {
                                zzpfVar4.zzp().zzd(zzhvVarZzn, objZza);
                                zzhrVarZzk.zzg(zzhvVarZzn);
                            }
                        }
                        zzicVar2.zzg(zzhrVarZzk);
                        com.google.android.gms.internal.measurement.zzie zzieVarZza = com.google.android.gms.internal.measurement.zzig.zza();
                        com.google.android.gms.internal.measurement.zzht zzhtVarZza = com.google.android.gms.internal.measurement.zzhu.zza();
                        zzhtVarZza.zzb(zzbcVar.zzc);
                        zzhtVarZza.zza(str5);
                        zzieVarZza.zza(zzhtVarZza);
                        zzicVar2.zzap(zzieVarZza);
                        zzicVar2.zzaf(zzpfVar4.zzm().zzb(zzhVar2.zzc(), Collections.EMPTY_LIST, zzicVar2.zzk(), Long.valueOf(zzhrVarZzk.zzn()), Long.valueOf(zzhrVarZzk.zzn()), false));
                        if (zzhrVarZzk.zzm()) {
                            zzicVar2.zzv(zzhrVarZzk.zzn());
                            zzicVar2.zzx(zzhrVarZzk.zzn());
                        }
                        long jZzp = zzhVar2.zzp();
                        if (jZzp != 0) {
                            zzicVar2.zzA(jZzp);
                        }
                        long jZzn = zzhVar2.zzn();
                        if (jZzn != 0) {
                            zzicVar2.zzy(jZzn);
                        } else if (jZzp != 0) {
                            zzicVar2.zzy(jZzp);
                        }
                        String strZzh = zzhVar2.zzh();
                        zzqu.zza();
                        if (zzibVar.zzc().zzp(str2, zzfx.zzaM) && strZzh != null) {
                            zzicVar2.zzau(strZzh);
                        }
                        zzhVar2.zzL();
                        zzicVar2.zzZ((int) zzhVar2.zzG());
                        zzibVar.zzc().zzi();
                        zzicVar2.zzO(130000L);
                        zzicVar2.zzs(zzibVar.zzaZ().currentTimeMillis());
                        zzicVar2.zzae(z2);
                        zzpfVar6.zzS(zzicVar2.zzK(), zzicVar2);
                        zzhzVar.zze(zzicVar2);
                        zzhVar2.zzo(zzicVar2.zzu());
                        zzhVar2.zzq(zzicVar2.zzw());
                        zzpfVar4.zzj().zzv(zzhVar2, false, false);
                        zzpfVar4.zzj().zzc();
                        zzpfVar4.zzj().zzd();
                        try {
                            return zzpfVar4.zzp().zzv(((com.google.android.gms.internal.measurement.zzib) zzhzVar.zzbc()).zzcc());
                        } catch (IOException e2) {
                            zzloVarZzn.zzu.zzaV().zzb().zzc("Data loss. Failed to bundle and serialize. appId", zzgt.zzl(str2), e2);
                            return obj;
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        zzloVar = zzloVarZzn;
                    }
                } catch (SecurityException e3) {
                    zzloVar = zzloVarZzn;
                    try {
                        zzloVar.zzu.zzaV().zzj().zzb("app instance id encryption failed", e3.getMessage());
                        bArr = new byte[0];
                        zzpfVar = zzloVar.zzg;
                        zzpfVar.zzj().zzd();
                        return bArr;
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
            } else {
                zzloVarZzn.zzu.zzaV().zzj().zzb("Log and bundle disabled. package_name", str2);
                bArr2 = new byte[0];
            }
            zzloVar.zzg.zzj().zzd();
            throw th;
        }
        zzloVarZzn.zzu.zzaV().zzj().zzb("Log and bundle not available. package_name", str2);
        bArr2 = new byte[0];
        zzpfVar3.zzj().zzd();
        return bArr2;
    }
}
