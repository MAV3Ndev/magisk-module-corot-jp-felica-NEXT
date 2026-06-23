package com.google.android.gms.internal.pay;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.internal.ConnectionCallbacks;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.ClientSettings;
import com.google.android.gms.common.internal.GmsClient;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzaa extends GmsClient {
    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzaa(Context context, Looper looper, ClientSettings clientSettings, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 198, clientSettings, connectionCallbacks, onConnectionFailedListener);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final /* synthetic */ IInterface createServiceInterface(IBinder iBinder) {
        if (iBinder == null) {
            return null;
        }
        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.google.android.gms.pay.internal.IPayService");
        return iInterfaceQueryLocalInterface instanceof zzd ? (zzd) iInterfaceQueryLocalInterface : new zzd(iBinder);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final Feature[] getApiFeatures() {
        return new Feature[]{com.google.android.gms.pay.zzn.zza, com.google.android.gms.pay.zzn.zzb, com.google.android.gms.pay.zzn.zzS, com.google.android.gms.pay.zzn.zzd, com.google.android.gms.pay.zzn.zze, com.google.android.gms.pay.zzn.zzf, com.google.android.gms.pay.zzn.zzg, com.google.android.gms.pay.zzn.zzh, com.google.android.gms.pay.zzn.zzi, com.google.android.gms.pay.zzn.zzj, com.google.android.gms.pay.zzn.zzk, com.google.android.gms.pay.zzn.zzl, com.google.android.gms.pay.zzn.zzm, com.google.android.gms.pay.zzn.zzn, com.google.android.gms.pay.zzn.zzp, com.google.android.gms.pay.zzn.zzo, com.google.android.gms.pay.zzn.zzq, com.google.android.gms.pay.zzn.zzu, com.google.android.gms.pay.zzn.zzt, com.google.android.gms.pay.zzn.zzc, com.google.android.gms.pay.zzn.zzv, com.google.android.gms.pay.zzn.zzw, com.google.android.gms.pay.zzn.zzx, com.google.android.gms.pay.zzn.zzz, com.google.android.gms.pay.zzn.zzA, com.google.android.gms.pay.zzn.zzD, com.google.android.gms.pay.zzn.zzB, com.google.android.gms.pay.zzn.zzC, com.google.android.gms.pay.zzn.zzF, com.google.android.gms.pay.zzn.zzE, com.google.android.gms.pay.zzn.zzI, com.google.android.gms.pay.zzn.zzJ, com.google.android.gms.pay.zzn.zzK, com.google.android.gms.pay.zzn.zzL, com.google.android.gms.pay.zzn.zzN, com.google.android.gms.pay.zzn.zzO, com.google.android.gms.pay.zzn.zzP, com.google.android.gms.pay.zzn.zzR, com.google.android.gms.pay.zzn.zzT, com.google.android.gms.pay.zzn.zzU, com.google.android.gms.pay.zzn.zzV, com.google.android.gms.pay.zzn.zzW, com.google.android.gms.pay.zzn.zzX, com.google.android.gms.pay.zzn.zzG, com.google.android.gms.pay.zzn.zzY, com.google.android.gms.pay.zzn.zzZ, com.google.android.gms.pay.zzn.zzaa, com.google.android.gms.pay.zzn.zzab, com.google.android.gms.pay.zzn.zzac, com.google.android.gms.pay.zzn.zzae, com.google.android.gms.pay.zzn.zzaf, com.google.android.gms.pay.zzn.zzag, com.google.android.gms.pay.zzn.zzy, com.google.android.gms.pay.zzn.zzr, com.google.android.gms.pay.zzn.zzah, com.google.android.gms.pay.zzn.zzH, com.google.android.gms.pay.zzn.zzQ, com.google.android.gms.pay.zzn.zzai, com.google.android.gms.pay.zzn.zzaj, com.google.android.gms.pay.zzn.zzak, com.google.android.gms.pay.zzn.zzal, com.google.android.gms.pay.zzn.zzan, com.google.android.gms.pay.zzn.zzam, com.google.android.gms.pay.zzn.zzao, com.google.android.gms.pay.zzn.zzap, com.google.android.gms.pay.zzn.zzs, com.google.android.gms.pay.zzn.zzaq, com.google.android.gms.pay.zzn.zzar, com.google.android.gms.pay.zzn.zzas, com.google.android.gms.pay.zzn.zzat, com.google.android.gms.pay.zzn.zzau, com.google.android.gms.pay.zzn.zzav, com.google.android.gms.pay.zzn.zzax, com.google.android.gms.pay.zzn.zzaw, com.google.android.gms.pay.zzn.zzad, com.google.android.gms.pay.zzn.zzay, com.google.android.gms.pay.zzn.zzaz, com.google.android.gms.pay.zzn.zzaA, com.google.android.gms.pay.zzn.zzaB};
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient, com.google.android.gms.common.api.Api.Client
    public final int getMinApkVersion() {
        return 17895000;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getServiceDescriptor() {
        return "com.google.android.gms.pay.internal.IPayService";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final String getStartServiceAction() {
        return "com.google.android.gms.pay.service.BIND";
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    protected final boolean getUseDynamicLookup() {
        return true;
    }

    @Override // com.google.android.gms.common.internal.BaseGmsClient
    public final boolean usesClientTelemetry() {
        return true;
    }
}
