package com.google.android.gms.internal.pay;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.RemoteException;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.UnsupportedApiCallException;
import com.google.android.gms.common.api.internal.RemoteCall;
import com.google.android.gms.common.api.internal.TaskApiCall;
import com.google.android.gms.pay.EmoneyReadiness;
import com.google.android.gms.pay.Pay;
import com.google.android.gms.pay.PayClient;
import com.google.android.gms.pay.zzae;
import com.google.android.gms.pay.zzag;
import com.google.android.gms.pay.zzaw;
import com.google.android.gms.pay.zzba;
import com.google.android.gms.pay.zzbx;
import com.google.android.gms.pay.zzcb;
import com.google.android.gms.pay.zzcd;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzx extends GoogleApi implements PayClient {
    public static final /* synthetic */ int zza = 0;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzx(Activity activity) {
        super(activity, (Api<Api.ApiOptions.NoOptions>) Pay.zzf, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final Task<EmoneyReadiness> checkReadinessForEmoney(final String str, final String str2) {
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzn
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r4v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzg zzgVar = (zzg) ((zzab) obj).getService();
                com.google.android.gms.pay.zze zzeVar = new com.google.android.gms.pay.zze();
                zzeVar.zzb(str);
                zzeVar.zza(str2);
                zzgVar.zzc(zzeVar.zzc(), new zzt(this.zza, (TaskCompletionSource) obj2));
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzay).setAutoResolveMissingFeatures(false).setMethodKey(7337).build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final Task<Integer> getPayApiAvailabilityStatus(int i) {
        com.google.android.gms.pay.zzw zzwVar = new com.google.android.gms.pay.zzw();
        zzwVar.zza(i);
        final com.google.android.gms.pay.zzy zzyVarZzb = zzwVar.zzb();
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzo
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                int i2 = zzx.zza;
                ((zzg) ((zzab) obj).getService()).zzd(zzyVarZzb, new zzw((TaskCompletionSource) obj2));
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzg).setAutoResolveMissingFeatures(false).setMethodKey(7289).build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final Task<PendingIntent> getPendingIntentForWalletOnWear(String str, int i) {
        zzae zzaeVar = new zzae();
        zzaeVar.zza(i);
        zzaeVar.zzb(str);
        final zzag zzagVarZzc = zzaeVar.zzc();
        return doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzm
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r3v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                ((zzg) ((zzab) obj).getService()).zze(zzagVarZzc, new zzr(this.zza, (TaskCompletionSource) obj2));
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzaj).setAutoResolveMissingFeatures(false).setMethodKey(7319).build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final PayClient.ProductName getProductName() {
        ResolveInfo resolveInfoResolveActivity = getApplicationContext().getPackageManager().resolveActivity(new Intent("com.google.android.gms.pay.brand.WALLET").setPackage("com.google.android.gms"), 65536);
        return (resolveInfoResolveActivity == null || resolveInfoResolveActivity.activityInfo == null || !resolveInfoResolveActivity.activityInfo.exported) ? PayClient.ProductName.GOOGLE_PAY : PayClient.ProductName.GOOGLE_WALLET;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final Task<Void> notifyCardTapEvent(final String str) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzp
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r4v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzg zzgVar = (zzg) ((zzab) obj).getService();
                zzaw zzawVar = new zzaw();
                zzawVar.zza(str);
                zzgVar.zzf(zzawVar.zzb(), new zzs(this.zza, (TaskCompletionSource) obj2));
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzaw).setAutoResolveMissingFeatures(false).setMethodKey(7334).build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final Task<Void> notifyEmoneyCardStatusUpdate(final String str) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzi
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r4v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzg zzgVar = (zzg) ((zzab) obj).getService();
                zzba zzbaVar = new zzba();
                zzbaVar.zza(str);
                zzgVar.zzg(zzbaVar.zzb(), new zzv(this.zza, (TaskCompletionSource) obj2));
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzaA).setAutoResolveMissingFeatures(false).setMethodKey(7339).build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final Task<Void> pushEmoneyCard(final String str, final ActivityResultLauncher<IntentSenderRequest> activityResultLauncher) {
        return doWrite(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzq
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r5v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                zzg zzgVar = (zzg) ((zzab) obj).getService();
                zzbx zzbxVar = new zzbx();
                zzbxVar.zza(str);
                zzgVar.zzh(zzbxVar.zzb(), new zzu(this.zza, activityResultLauncher, (TaskCompletionSource) obj2));
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzaz).setAutoResolveMissingFeatures(false).setMethodKey(7338).build());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final void savePasses(String str, Activity activity, int i) {
        zzcb zzcbVar = new zzcb();
        zzcbVar.zza(str);
        final zzcd zzcdVarZzc = zzcbVar.zzc();
        final zzz zzzVar = new zzz(activity, i);
        doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzh
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                int i2 = zzx.zza;
                ((zzg) ((zzab) obj).getService()).zzi(zzcdVarZzc, zzzVar);
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzB).setAutoResolveMissingFeatures(false).setMethodKey(7288).build()).addOnFailureListener(new OnFailureListener() { // from class: com.google.android.gms.internal.pay.zzj
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) throws RemoteException {
                int i2 = zzx.zza;
                zzz zzzVar2 = zzzVar;
                if (exc instanceof UnsupportedApiCallException) {
                    zzzVar2.zzh(new Status(1));
                } else {
                    zzzVar2.zzh(new Status(3));
                }
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.pay.PayClient
    public final void savePassesJwt(String str, Activity activity, int i) {
        zzcb zzcbVar = new zzcb();
        zzcbVar.zzb(str);
        final zzcd zzcdVarZzc = zzcbVar.zzc();
        final zzz zzzVar = new zzz(activity, i);
        doRead(TaskApiCall.builder().run(new RemoteCall() { // from class: com.google.android.gms.internal.pay.zzk
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            /* JADX DEBUG: Multi-variable search result rejected for r2v2, resolved type: T */
            /* JADX WARN: Multi-variable type inference failed */
            @Override // com.google.android.gms.common.api.internal.RemoteCall
            public final void accept(Object obj, Object obj2) throws RemoteException {
                int i2 = zzx.zza;
                ((zzg) ((zzab) obj).getService()).zzi(zzcdVarZzc, zzzVar);
            }
        }).setFeatures(com.google.android.gms.pay.zzn.zzC).setAutoResolveMissingFeatures(false).setMethodKey(7295).build()).addOnFailureListener(new OnFailureListener() { // from class: com.google.android.gms.internal.pay.zzl
            /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
            @Override // com.google.android.gms.tasks.OnFailureListener
            public final void onFailure(Exception exc) throws RemoteException {
                int i2 = zzx.zza;
                zzz zzzVar2 = zzzVar;
                if (exc instanceof UnsupportedApiCallException) {
                    zzzVar2.zzh(new Status(1));
                } else {
                    zzzVar2.zzh(new Status(3));
                }
            }
        });
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 2 */
    public zzx(Context context) {
        super(context, (Api<Api.ApiOptions.NoOptions>) Pay.zzf, Api.ApiOptions.NO_OPTIONS, GoogleApi.Settings.DEFAULT_SETTINGS);
    }
}
