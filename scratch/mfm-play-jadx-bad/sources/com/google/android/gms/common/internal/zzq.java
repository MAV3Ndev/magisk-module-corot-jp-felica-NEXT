package com.google.android.gms.common.internal;

import android.content.ComponentName;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import androidx.core.os.EnvironmentCompat;

/* JADX INFO: compiled from: com.google.android.gms:play-services-basement@@18.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzq implements Handler.Callback {
    final /* synthetic */ zzs zza;

    /* JADX DEBUG: Marked for inline */
    /* JADX DEBUG: Method not inlined, still used in: [com.google.android.gms.common.internal.zzs.<init>(android.content.Context, android.os.Looper, java.util.concurrent.Executor):void] */
    /* synthetic */ zzq(zzs zzsVar, zzr zzrVar) {
        this.zza = zzsVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // android.os.Handler.Callback
    public final boolean handleMessage(Message message) {
        int i = message.what;
        if (i == 0) {
            synchronized (this.zza.zzb) {
                zzo zzoVar = (zzo) message.obj;
                zzp zzpVar = (zzp) this.zza.zzb.get(zzoVar);
                if (zzpVar != null && zzpVar.zzi()) {
                    if (zzpVar.zzj()) {
                        zzpVar.zzg("GmsClientSupervisor");
                    }
                    this.zza.zzb.remove(zzoVar);
                }
            }
            return true;
        }
        if (i != 1) {
            return false;
        }
        synchronized (this.zza.zzb) {
            zzo zzoVar2 = (zzo) message.obj;
            zzp zzpVar2 = (zzp) this.zza.zzb.get(zzoVar2);
            if (zzpVar2 != null && zzpVar2.zza() == 3) {
                Log.e("GmsClientSupervisor", "Timeout waiting for ServiceConnection callback " + String.valueOf(zzoVar2), new Exception());
                ComponentName componentNameZzb = zzpVar2.zzb();
                if (componentNameZzb == null) {
                    componentNameZzb = zzoVar2.zza();
                }
                if (componentNameZzb == null) {
                    String strZzc = zzoVar2.zzc();
                    Preconditions.checkNotNull(strZzc);
                    String str = strZzc;
                    componentNameZzb = new ComponentName(strZzc, EnvironmentCompat.MEDIA_UNKNOWN);
                }
                zzpVar2.onServiceDisconnected(componentNameZzb);
            }
        }
        return true;
    }
}
