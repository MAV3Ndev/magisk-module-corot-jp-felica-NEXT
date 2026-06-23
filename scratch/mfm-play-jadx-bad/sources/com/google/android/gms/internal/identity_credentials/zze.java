package com.google.android.gms.internal.identity_credentials;

import com.google.android.gms.common.Feature;

/* JADX INFO: compiled from: com.google.android.gms:play-services-identity-credentials@@16.0.0-alpha02 */
/* JADX INFO: loaded from: classes3.dex */
public final class zze {
    public static final Feature zza;
    public static final Feature zzb;
    public static final Feature zzc;
    public static final Feature[] zzd;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        Feature feature = new Feature("GET_CREDENTIAL", 1L);
        zza = feature;
        Feature feature2 = new Feature("CREDENTIAL_REGISTRY", 1L);
        zzb = feature2;
        Feature feature3 = new Feature("CLEAR_REGISTRY", 1L);
        zzc = feature3;
        zzd = new Feature[]{feature, feature2, feature3};
    }
}
