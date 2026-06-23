package com.google.android.gms.internal.fido;

import java.util.Comparator;
import java.util.Objects;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzgl {
    static final String zza;
    static final Comparator zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        Comparator comparator;
        String strConcat = String.valueOf(zzgl.class.getName()).concat("$UnsafeComparator");
        zza = strConcat;
        try {
            comparator = (Comparator) ((Object[]) Objects.requireNonNull(Class.forName(strConcat).getEnumConstants()))[0];
        } catch (Throwable unused) {
            comparator = zzgk.INSTANCE;
        }
        zzb = comparator;
    }

    zzgl() {
    }
}
