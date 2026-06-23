package com.google.android.gms.measurement.internal;

import android.os.Bundle;
import android.os.IInterface;
import android.os.RemoteException;
import java.util.List;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public interface zzga extends IInterface {
    void zzA(zzr zzrVar) throws RemoteException;

    void zzB(zzr zzrVar, zzon zzonVar, zzgg zzggVar) throws RemoteException;

    void zzC(zzr zzrVar, zzaf zzafVar) throws RemoteException;

    void zzD(zzr zzrVar, Bundle bundle, zzgd zzgdVar) throws RemoteException;

    void zze(zzbg zzbgVar, zzr zzrVar) throws RemoteException;

    void zzf(zzpk zzpkVar, zzr zzrVar) throws RemoteException;

    void zzg(zzr zzrVar) throws RemoteException;

    void zzh(zzbg zzbgVar, String str, String str2) throws RemoteException;

    void zzi(zzr zzrVar) throws RemoteException;

    List zzj(zzr zzrVar, boolean z) throws RemoteException;

    byte[] zzk(zzbg zzbgVar, String str) throws RemoteException;

    void zzl(long j, String str, String str2, String str3) throws RemoteException;

    String zzm(zzr zzrVar) throws RemoteException;

    void zzn(zzah zzahVar, zzr zzrVar) throws RemoteException;

    void zzo(zzah zzahVar) throws RemoteException;

    List zzp(String str, String str2, boolean z, zzr zzrVar) throws RemoteException;

    List zzq(String str, String str2, String str3, boolean z) throws RemoteException;

    List zzr(String str, String str2, zzr zzrVar) throws RemoteException;

    List zzs(String str, String str2, String str3) throws RemoteException;

    void zzt(zzr zzrVar) throws RemoteException;

    void zzu(Bundle bundle, zzr zzrVar) throws RemoteException;

    void zzv(zzr zzrVar) throws RemoteException;

    zzao zzw(zzr zzrVar) throws RemoteException;

    List zzx(zzr zzrVar, Bundle bundle) throws RemoteException;

    void zzy(zzr zzrVar) throws RemoteException;

    void zzz(zzr zzrVar) throws RemoteException;
}
