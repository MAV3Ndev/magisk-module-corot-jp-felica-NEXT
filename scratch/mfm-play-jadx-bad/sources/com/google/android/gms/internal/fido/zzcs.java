package com.google.android.gms.internal.fido;

import java.util.Comparator;

/* JADX INFO: compiled from: com.google.android.gms:play-services-fido@@21.0.0 */
/* JADX INFO: loaded from: classes3.dex */
public abstract class zzcs implements Comparator {
    protected zzcs() {
    }

    @Override // java.util.Comparator
    public abstract int compare(Object obj, Object obj2);

    public zzcs zza() {
        return new zzcx(this);
    }
}
