package com.google.android.gms.common.internal;

import java.util.concurrent.ExecutorService;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class CallbackExecutor {
    private CallbackExecutor() {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    public static ExecutorService executorService() {
        return zzj.zza;
    }
}
