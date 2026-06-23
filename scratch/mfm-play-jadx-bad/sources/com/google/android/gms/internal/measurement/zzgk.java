package com.google.android.gms.internal.measurement;

import java.util.Collections;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzgk extends zzma implements zznm {
    private zzgk() {
        throw null;
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0004: CONSTRUCTOR 
  (wrap:com.google.android.gms.internal.measurement.zzgl:0x0000: SGET  A[MD:():com.google.android.gms.internal.measurement.zzgl (m), WRAPPED] (LINE:1) com.google.android.gms.internal.measurement.zzgl.zzu com.google.android.gms.internal.measurement.zzgl)
 A[MD:(MessageType extends com.google.android.gms.internal.measurement.zzme<MessageType, BuilderType>):void (m)] (LINE:1) call: com.google.android.gms.internal.measurement.zzma.<init>(com.google.android.gms.internal.measurement.zzme):void type: SUPER */
    /* JADX DEBUG: Class process forced to load method for inline: com.google.android.gms.internal.measurement.zzgl.zzw():com.google.android.gms.internal.measurement.zzgl */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* synthetic */ zzgk(byte[] bArr) {
        super(zzgl.zzu);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int zza() {
        return ((zzgl) this.zza).zzf();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzgj zzb(int i) {
        return ((zzgl) this.zza).zzg(i);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzgk zzc(int i, zzgi zzgiVar) {
        zzaX();
        ((zzgl) this.zza).zzt(i, (zzgj) zzgiVar.zzbc());
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final List zzd() {
        return Collections.unmodifiableList(((zzgl) this.zza).zzh());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzgk zze() {
        zzaX();
        ((zzgl) this.zza).zzu();
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final zzgk zzf() {
        zzaX();
        ((zzgl) this.zza).zzv();
        return this;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final List zzg() {
        return Collections.unmodifiableList(((zzgl) this.zza).zzk());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String zzh() {
        return ((zzgl) this.zza).zzm();
    }
}
