package com.google.android.gms.pay;

import android.app.Activity;
import android.content.Context;
import com.google.android.gms.common.api.Api;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public class Pay {
    static final Api.ClientKey zza;
    static final Api.ClientKey zzb;
    static final Api.AbstractClientBuilder zzc;
    static final Api.AbstractClientBuilder zzd;
    public static final Api zze;
    public static final Api zzf;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    static {
        Api.ClientKey clientKey = new Api.ClientKey();
        zza = clientKey;
        Api.ClientKey clientKey2 = new Api.ClientKey();
        zzb = clientKey2;
        zzbk zzbkVar = new zzbk();
        zzc = zzbkVar;
        zzbl zzblVar = new zzbl();
        zzd = zzblVar;
        zze = new Api("Pay.API", zzbkVar, clientKey);
        zzf = new Api("Pay.THIRD_PARTY_API", zzblVar, clientKey2);
    }

    private Pay() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static PayClient getClient(Activity activity) {
        return new com.google.android.gms.internal.pay.zzx(activity);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public static PayClient getClient(Context context) {
        return new com.google.android.gms.internal.pay.zzx(context);
    }
}
