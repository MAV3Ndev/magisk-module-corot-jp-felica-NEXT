package com.google.android.gms.common.api.internal;

import com.google.android.gms.common.api.internal.GoogleApiManager;

/* JADX INFO: loaded from: classes2.dex */
final class zabk implements Runnable {
    private final /* synthetic */ GoogleApiManager.zaa zaix;

    zabk(GoogleApiManager.zaa zaaVar) {
        this.zaix = zaaVar;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.zaix.zabh();
    }
}
