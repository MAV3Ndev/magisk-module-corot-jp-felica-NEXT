package com.google.android.gms.internal.pay;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import java.lang.ref.WeakReference;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzz extends zzy {
    private final WeakReference zza;
    private final int zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public zzz(Activity activity, int i) {
        this.zza = new WeakReference(activity);
        this.zzb = i;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.pay.zzy, com.google.android.gms.internal.pay.zzf
    public final void zzh(Status status) {
        Activity activity = (Activity) this.zza.get();
        if (activity == null) {
            Log.d("PayClientImpl", "Ignoring onPendingIntent, Activity is gone");
            return;
        }
        if (status.hasResolution()) {
            try {
                status.startResolutionForResult(activity, this.zzb);
                return;
            } catch (IntentSender.SendIntentException e) {
                Log.w("PayClientImpl", "Exception starting pending intent", e);
            }
        }
        PendingIntent pendingIntentCreatePendingResult = activity.createPendingResult(this.zzb, new Intent(), 1073741824);
        if (pendingIntentCreatePendingResult == null) {
            Log.w("PayClientImpl", "Null pending result returned for onPendingIntent");
            return;
        }
        try {
            pendingIntentCreatePendingResult.send(status.isSuccess() ? -1 : status.getStatusCode());
        } catch (PendingIntent.CanceledException e2) {
            Log.w("PayClientImpl", "Exception setting pending result", e2);
        }
    }
}
