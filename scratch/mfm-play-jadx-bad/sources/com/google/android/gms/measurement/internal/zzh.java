package com.google.android.gms.measurement.internal;

import android.text.TextUtils;
import com.google.android.gms.common.internal.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzh {
    private Long zzA;
    private long zzB;
    private String zzC;
    private int zzD;
    private int zzE;
    private long zzF;
    private String zzG;
    private byte[] zzH;
    private int zzI;
    private long zzJ;
    private long zzK;
    private long zzL;
    private long zzM;
    private long zzN;
    private long zzO;
    private String zzP;
    private boolean zzQ;
    private long zzR;
    private long zzS;
    private final zzib zza;
    private final String zzb;
    private String zzc;
    private String zzd;
    private String zze;
    private String zzf;
    private long zzg;
    private long zzh;
    private long zzi;
    private String zzj;
    private long zzk;
    private String zzl;
    private long zzm;
    private long zzn;
    private boolean zzo;
    private boolean zzp;
    private Boolean zzq;
    private long zzr;
    private List zzs;
    private String zzt;
    private boolean zzu;
    private long zzv;
    private long zzw;
    private int zzx;
    private boolean zzy;
    private Long zzz;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzh(zzib zzibVar, String str) {
        Preconditions.checkNotNull(zzibVar);
        Preconditions.checkNotEmpty(str);
        this.zza = zzibVar;
        this.zzb = str;
        zzibVar.zzaW().zzg();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzA(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzn != j;
        this.zzn = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzB() {
        this.zza.zzaW().zzg();
        return this.zzr;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzC(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzr != j;
        this.zzr = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzD() {
        this.zza.zzaW().zzg();
        return this.zzo;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzE(boolean z) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzo != z;
        this.zzo = z;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzF(long j) {
        Preconditions.checkArgument(j >= 0);
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzg != j;
        this.zzg = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzG() {
        this.zza.zzaW().zzg();
        return this.zzg;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzH() {
        this.zza.zzaW().zzg();
        return this.zzR;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzI(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzR != j;
        this.zzR = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzJ() {
        this.zza.zzaW().zzg();
        return this.zzS;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzK(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzS != j;
        this.zzS = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzL() {
        zzib zzibVar = this.zza;
        zzibVar.zzaW().zzg();
        long j = this.zzg + 1;
        if (j > 2147483647L) {
            zzibVar.zzaV().zze().zzb("Bundle index overflow. appId", zzgt.zzl(this.zzb));
            j = 0;
        }
        this.zzQ = true;
        this.zzg = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzM(long j) {
        zzib zzibVar = this.zza;
        zzibVar.zzaW().zzg();
        long j2 = this.zzg + j;
        if (j2 > 2147483647L) {
            zzibVar.zzaV().zze().zzb("Bundle index overflow. appId", zzgt.zzl(this.zzb));
            j2 = (-1) + j;
        }
        long j3 = this.zzF + 1;
        if (j3 > 2147483647L) {
            zzibVar.zzaV().zze().zzb("Delivery index overflow. appId", zzgt.zzl(this.zzb));
            j3 = 0;
        }
        this.zzQ = true;
        this.zzg = j2;
        this.zzF = j3;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzN() {
        this.zza.zzaW().zzg();
        return this.zzJ;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzO(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzJ != j;
        this.zzJ = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzP() {
        this.zza.zzaW().zzg();
        return this.zzK;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzQ(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzK != j;
        this.zzK = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzR() {
        this.zza.zzaW().zzg();
        return this.zzL;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzS(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzL != j;
        this.zzL = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzT() {
        this.zza.zzaW().zzg();
        return this.zzM;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzU(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzM != j;
        this.zzM = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzV() {
        this.zza.zzaW().zzg();
        return this.zzO;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzW(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzO != j;
        this.zzO = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzX() {
        this.zza.zzaW().zzg();
        return this.zzN;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzY(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzN != j;
        this.zzN = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzZ() {
        this.zza.zzaW().zzg();
        return this.zzP;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zza() {
        this.zza.zzaW().zzg();
        return this.zzQ;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzaA() {
        this.zza.zzaW().zzg();
        return this.zzD;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaB(int i) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzD != i;
        this.zzD = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzaC() {
        this.zza.zzaW().zzg();
        return this.zzE;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaD(int i) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzE != i;
        this.zzE = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaE(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzF != j;
        this.zzF = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzaF() {
        this.zza.zzaW().zzg();
        return this.zzF;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaG(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzG != str;
        this.zzG = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzaH() {
        this.zza.zzaW().zzg();
        return this.zzG;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaI(byte[] bArr) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzH != bArr;
        this.zzH = bArr;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final byte[] zzaJ() {
        this.zza.zzaW().zzg();
        return this.zzH;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaK(int i) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzI != i;
        this.zzI = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzaL() {
        this.zza.zzaW().zzg();
        return this.zzI;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzaa() {
        this.zza.zzaW().zzg();
        String str = this.zzP;
        zzab(null);
        return str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzab(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzP, str);
        this.zzP = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzac() {
        this.zza.zzaW().zzg();
        return this.zzp;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzad(boolean z) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzp != z;
        this.zzp = z;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Boolean zzae() {
        this.zza.zzaW().zzg();
        return this.zzq;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaf(Boolean bool) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzq, bool);
        this.zzq = bool;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final List zzag() {
        this.zza.zzaW().zzg();
        return this.zzs;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzah(List list) {
        this.zza.zzaW().zzg();
        if (Objects.equals(this.zzs, list)) {
            return;
        }
        this.zzQ = true;
        this.zzs = list != null ? new ArrayList(list) : null;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzai() {
        this.zza.zzaW().zzg();
        return this.zzu;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaj(boolean z) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzu != z;
        this.zzu = z;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzak() {
        this.zza.zzaW().zzg();
        return this.zzv;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzal(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzv != j;
        this.zzv = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzam() {
        this.zza.zzaW().zzg();
        return this.zzw;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzan(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzw != j;
        this.zzw = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzao() {
        this.zza.zzaW().zzg();
        return this.zzx;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzap(int i) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzx != i;
        this.zzx = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean zzaq() {
        this.zza.zzaW().zzg();
        return this.zzy;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzar(boolean z) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzy != z;
        this.zzy = z;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Long zzas() {
        this.zza.zzaW().zzg();
        return this.zzz;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzat(Long l) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzz, l);
        this.zzz = l;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final Long zzau() {
        this.zza.zzaW().zzg();
        return this.zzA;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzav(Long l) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzA, l);
        this.zzA = l;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzaw() {
        this.zza.zzaW().zzg();
        return this.zzB;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzax(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzB != j;
        this.zzB = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzay() {
        this.zza.zzaW().zzg();
        return this.zzC;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzaz(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzC != str;
        this.zzC = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzb() {
        this.zza.zzaW().zzg();
        this.zzQ = false;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzc() {
        this.zza.zzaW().zzg();
        return this.zzb;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzd() {
        this.zza.zzaW().zzg();
        return this.zzc;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zze(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzc, str);
        this.zzc = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzf() {
        this.zza.zzaW().zzg();
        return this.zzd;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzg(String str) {
        this.zza.zzaW().zzg();
        if (true == TextUtils.isEmpty(str)) {
            str = null;
        }
        this.zzQ |= true ^ Objects.equals(this.zzd, str);
        this.zzd = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzh() {
        this.zza.zzaW().zzg();
        return this.zzt;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzi(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzt, str);
        this.zzt = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzj() {
        this.zza.zzaW().zzg();
        return this.zze;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzk(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zze, str);
        this.zze = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzl() {
        this.zza.zzaW().zzg();
        return this.zzf;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzm(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzf, str);
        this.zzf = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzn() {
        this.zza.zzaW().zzg();
        return this.zzh;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzo(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzh != j;
        this.zzh = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzp() {
        this.zza.zzaW().zzg();
        return this.zzi;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzq(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzi != j;
        this.zzi = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzr() {
        this.zza.zzaW().zzg();
        return this.zzj;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzs(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzj, str);
        this.zzj = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzt() {
        this.zza.zzaW().zzg();
        return this.zzk;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzu(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzk != j;
        this.zzk = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzv() {
        this.zza.zzaW().zzg();
        return this.zzl;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzw(String str) {
        this.zza.zzaW().zzg();
        this.zzQ |= !Objects.equals(this.zzl, str);
        this.zzl = str;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzx() {
        this.zza.zzaW().zzg();
        return this.zzm;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final void zzy(long j) {
        this.zza.zzaW().zzg();
        this.zzQ |= this.zzm != j;
        this.zzm = j;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final long zzz() {
        this.zza.zzaW().zzg();
        return this.zzn;
    }
}
