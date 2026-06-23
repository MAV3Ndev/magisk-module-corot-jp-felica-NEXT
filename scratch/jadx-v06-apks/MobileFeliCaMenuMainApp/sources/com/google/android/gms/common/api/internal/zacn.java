package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Result;

/* JADX INFO: loaded from: classes2.dex */
final class zacn implements Runnable {
    private final /* synthetic */ Result zaku;
    private final /* synthetic */ zacm zakv;

    zacn(zacm zacmVar, Result result) {
        this.zakv = zacmVar;
        this.zaku = result;
    }

    @Override // java.lang.Runnable
    public final void run() {
        try {
            try {
                BasePendingResult.zadm.set(true);
                this.zakv.zaks.sendMessage(this.zakv.zaks.obtainMessage(0, this.zakv.zakn.onSuccess(this.zaku)));
                BasePendingResult.zadm.set(false);
                zacm zacmVar = this.zakv;
                zacm.zab(this.zaku);
                GoogleApiClient googleApiClient = (GoogleApiClient) this.zakv.zadp.get();
                if (googleApiClient != null) {
                    googleApiClient.zab(this.zakv);
                }
            } catch (RuntimeException e) {
                this.zakv.zaks.sendMessage(this.zakv.zaks.obtainMessage(1, e));
                BasePendingResult.zadm.set(false);
                zacm zacmVar2 = this.zakv;
                zacm.zab(this.zaku);
                GoogleApiClient googleApiClient2 = (GoogleApiClient) this.zakv.zadp.get();
                if (googleApiClient2 != null) {
                    googleApiClient2.zab(this.zakv);
                }
            }
        } catch (Throwable th) {
            BasePendingResult.zadm.set(false);
            zacm zacmVar3 = this.zakv;
            zacm.zab(this.zaku);
            GoogleApiClient googleApiClient3 = (GoogleApiClient) this.zakv.zadp.get();
            if (googleApiClient3 != null) {
                googleApiClient3.zab(this.zakv);
            }
            throw th;
        }
    }
}
