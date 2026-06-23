package com.google.android.gms.common.api.internal;

import android.os.Bundle;
import com.google.android.gms.common.ConnectionResult;

/* JADX INFO: compiled from: com.google.android.gms:play-services-base@@18.4.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zax implements zabz {
    final /* synthetic */ zaaa zaa;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.gms.common.api.internal.zaaa.<init>(android.content.Context, com.google.android.gms.common.api.internal.zabe, java.util.concurrent.locks.Lock, android.os.Looper, com.google.android.gms.common.GoogleApiAvailabilityLight, java.util.Map, java.util.Map, com.google.android.gms.common.internal.ClientSettings, com.google.android.gms.common.api.Api$AbstractClientBuilder, com.google.android.gms.common.api.Api$Client, java.util.ArrayList, java.util.ArrayList, java.util.Map, java.util.Map):void] */
    /* synthetic */ zax(zaaa zaaaVar, zaw zawVar) {
        this.zaa = zaaaVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zaa(ConnectionResult connectionResult) {
        this.zaa.zam.lock();
        try {
            this.zaa.zaj = connectionResult;
            zaaa.zap(this.zaa);
        } finally {
            this.zaa.zam.unlock();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zab(Bundle bundle) {
        this.zaa.zam.lock();
        try {
            zaaa.zao(this.zaa, bundle);
            this.zaa.zaj = ConnectionResult.RESULT_SUCCESS;
            zaaa.zap(this.zaa);
        } finally {
            this.zaa.zam.unlock();
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // com.google.android.gms.common.api.internal.zabz
    public final void zac(int i, boolean z) {
        this.zaa.zam.lock();
        try {
            zaaa zaaaVar = this.zaa;
            if (zaaaVar.zal || zaaaVar.zak == null || !zaaaVar.zak.isSuccess()) {
                this.zaa.zal = false;
                zaaa.zan(this.zaa, i, z);
            } else {
                this.zaa.zal = true;
                this.zaa.zae.onConnectionSuspended(i);
            }
        } finally {
            this.zaa.zam.unlock();
        }
    }
}
