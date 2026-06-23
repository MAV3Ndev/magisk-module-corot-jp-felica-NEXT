package com.google.android.gms.internal.pay;

import android.app.PendingIntent;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzu extends zzy {
    final /* synthetic */ ActivityResultLauncher zza;
    final /* synthetic */ TaskCompletionSource zzb;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzu(zzx zzxVar, ActivityResultLauncher activityResultLauncher, TaskCompletionSource taskCompletionSource) {
        this.zza = activityResultLauncher;
        this.zzb = taskCompletionSource;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.pay.zzy, com.google.android.gms.internal.pay.zzf
    public final void zzh(Status status) {
        if (status.isSuccess() && status.hasResolution() && this.zza != null) {
            this.zza.launch(new IntentSenderRequest.Builder(((PendingIntent) Preconditions.checkNotNull(status.getResolution())).getIntentSender()).build());
        }
        TaskUtil.trySetResultOrApiException(status, null, this.zzb);
    }
}
