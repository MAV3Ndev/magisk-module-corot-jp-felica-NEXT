package com.google.android.gms.internal.measurement;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-base@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzoq extends IllegalArgumentException {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzoq(int i, int i2) {
        super(zzkv.zza(i2, i, (byte) 32, "Unpaired surrogate at index ", " of "));
    }
}
