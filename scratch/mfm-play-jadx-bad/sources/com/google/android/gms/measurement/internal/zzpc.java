package com.google.android.gms.measurement.internal;

import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzpc {
    final String zza;
    long zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    private zzpc(zzpf zzpfVar, String str) {
        Objects.requireNonNull(zzpfVar);
        this.zza = str;
        this.zzb = zzpfVar.zzaZ().elapsedRealtime();
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0000: CONSTRUCTOR (r1v0 com.google.android.gms.measurement.internal.zzpf), (r2v0 java.lang.String) A[MD:(com.google.android.gms.measurement.internal.zzpf, java.lang.String):void (m)] call: com.google.android.gms.measurement.internal.zzpc.<init>(com.google.android.gms.measurement.internal.zzpf, java.lang.String):void type: THIS */
    /* synthetic */ zzpc(zzpf zzpfVar, String str, byte[] bArr) {
        this(zzpfVar, str);
    }

    /* JADX DEBUG: Can't inline method, not implemented redirect type for insn: 0x0008: CONSTRUCTOR 
  (r1v0 com.google.android.gms.measurement.internal.zzpf)
  (wrap:java.lang.String:0x0004: INVOKE 
  (wrap:com.google.android.gms.measurement.internal.zzpo:0x0000: INVOKE (r1v0 com.google.android.gms.measurement.internal.zzpf) VIRTUAL call: com.google.android.gms.measurement.internal.zzpf.zzt():com.google.android.gms.measurement.internal.zzpo A[MD:():com.google.android.gms.measurement.internal.zzpo (m), WRAPPED] (LINE:1))
 VIRTUAL call: com.google.android.gms.measurement.internal.zzpo.zzaw():java.lang.String A[MD:():java.lang.String (m), WRAPPED] (LINE:1))
 A[MD:(com.google.android.gms.measurement.internal.zzpf, java.lang.String):void (m)] (LINE:1) call: com.google.android.gms.measurement.internal.zzpc.<init>(com.google.android.gms.measurement.internal.zzpf, java.lang.String):void type: THIS */
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    /* synthetic */ zzpc(zzpf zzpfVar, byte[] bArr) {
        this(zzpfVar, zzpfVar.zzt().zzaw());
    }
}
