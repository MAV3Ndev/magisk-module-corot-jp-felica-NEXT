package com.google.android.gms.auth.api.signin;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.internal.PendingResultUtil;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth@@21.3.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zba implements PendingResultUtil.ResultConverter {
    private zba() {
        throw null;
    }

    /* synthetic */ zba(zbb zbbVar) {
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.common.internal.PendingResultUtil.ResultConverter
    public final /* synthetic */ Object convert(Result result) {
        return ((GoogleSignInResult) result).getSignInAccount();
    }
}
