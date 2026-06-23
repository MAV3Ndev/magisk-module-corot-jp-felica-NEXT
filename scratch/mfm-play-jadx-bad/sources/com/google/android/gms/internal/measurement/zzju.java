package com.google.android.gms.internal.measurement;

import android.os.Binder;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
interface zzju {

    /* JADX INFO: renamed from: com.google.android.gms.internal.measurement.zzju$-CC, reason: invalid class name */
    /* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
    public final /* synthetic */ class CC {
        /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
        public static Object zzg(zzjt zzjtVar) {
            try {
                return zzjtVar.zza();
            } catch (SecurityException unused) {
                long jClearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    return zzjtVar.zza();
                } finally {
                    Binder.restoreCallingIdentity(jClearCallingIdentity);
                }
            }
        }
    }

    Object zze(String str);
}
