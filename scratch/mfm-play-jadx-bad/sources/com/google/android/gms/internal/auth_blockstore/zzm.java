package com.google.android.gms.internal.auth_blockstore;

import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.gms.auth.blockstore.RetrieveBytesResponse;
import com.google.android.gms.common.api.Status;

/* JADX INFO: compiled from: com.google.android.gms:play-services-auth-blockstore@@16.4.0 */
/* JADX INFO: loaded from: classes3.dex */
public interface zzm extends IInterface {
    void zza(Status status, byte[] bArr) throws RemoteException;

    void zzb(Status status, RetrieveBytesResponse retrieveBytesResponse) throws RemoteException;
}
