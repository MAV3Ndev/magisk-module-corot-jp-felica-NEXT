package com.google.android.gms.internal.fido;

import java.util.Collections;
import java.util.Comparator;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzfi {
    private static final Comparator zza = new zzfa();
    private static final Comparator zzb = new zzfb();
    private static final zzfi zzc = new zzfi(new zzfg(Collections.EMPTY_LIST));
    private final zzfg zzd;

    private zzfi(zzfg zzfgVar) {
        this.zzd = zzfgVar;
    }

    public static zzfi zza() {
        return zzc;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final boolean equals(@NullableDecl Object obj) {
        return (obj instanceof zzfi) && ((zzfi) obj).zzd.equals(this.zzd);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final int hashCode() {
        return ~this.zzd.hashCode();
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public final String toString() {
        return this.zzd.toString();
    }
}
