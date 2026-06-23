package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzhz extends zzma implements zznm {
    private zzhz() {
        throw null;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0004: CONSTRUCTOR 
  (wrap:com.google.android.gms.internal.measurement.zzib:0x0000: SGET  A[MD:():com.google.android.gms.internal.measurement.zzib (m), WRAPPED] (LINE:1) com.google.android.gms.internal.measurement.zzib.zzh com.google.android.gms.internal.measurement.zzib)
 A[MD:(MessageType extends com.google.android.gms.internal.measurement.zzme<MessageType, BuilderType>):void (m)] (LINE:1) call: com.google.android.gms.internal.measurement.zzma.<init>(com.google.android.gms.internal.measurement.zzme):void type: SUPER */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* synthetic */ zzhz(byte[] bArr) {
        super(zzib.zzh);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final List zza() {
        return Collections.unmodifiableList(((zzib) this.zza).zza());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zzb() {
        return ((zzib) this.zza).zzb();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzid zzc(int i) {
        return ((zzib) this.zza).zzc(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzhz zzd(int i, zzic zzicVar) {
        zzaX();
        ((zzib) this.zza).zzj(i, (zzid) zzicVar.zzbc());
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzhz zze(zzic zzicVar) {
        zzaX();
        ((zzib) this.zza).zzk((zzid) zzicVar.zzbc());
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzhz zzf(Iterable iterable) {
        zzaX();
        ((zzib) this.zza).zzm(iterable);
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzhz zzg() {
        zzaX();
        ((zzib) this.zza).zzn();
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzh() {
        return ((zzib) this.zza).zze();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzhz zzi(String str) {
        zzaX();
        ((zzib) this.zza).zzo(str);
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzhz zzj(String str) {
        zzaX();
        ((zzib) this.zza).zzp(str);
        return this;
    }
}
