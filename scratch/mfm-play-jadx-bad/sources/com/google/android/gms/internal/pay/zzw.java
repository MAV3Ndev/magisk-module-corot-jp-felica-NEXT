package com.google.android.gms.internal.pay;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.TaskUtil;
import com.google.android.gms.tasks.TaskCompletionSource;

/* JADX INFO: compiled from: com.google.android.gms:play-services-pay@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzw extends zzy {
    final /* synthetic */ TaskCompletionSource zza;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzw(TaskCompletionSource taskCompletionSource) {
        this.zza = taskCompletionSource;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.internal.pay.zzy, com.google.android.gms.internal.pay.zzf
    public final void zzi(Status status, int i) {
        if (status.isSuccess() && i == 3) {
            TaskUtil.trySetResultOrApiException(Status.RESULT_INTERNAL_ERROR, null, this.zza);
        } else if (i == 1) {
            TaskUtil.trySetResultOrApiException(status, 2, this.zza);
        } else {
            TaskUtil.trySetResultOrApiException(status, Integer.valueOf(i), this.zza);
        }
    }
}
