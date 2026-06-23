package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import com.google.firebase.messaging.Constants;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
public final class zzx {
    private final zzib zza;

    public zzx(zzib zzibVar) {
        this.zza = zzibVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zza() {
        zzib zzibVar = this.zza;
        zzibVar.zzaW().zzg();
        if (zze()) {
            if (zzd()) {
                zzibVar.zzd().zzr.zzb(null);
                Bundle bundle = new Bundle();
                bundle.putString("source", "(not set)");
                bundle.putString("medium", "(not set)");
                bundle.putString("_cis", "intent");
                bundle.putLong("_cc", 1L);
                zzibVar.zzj().zzF(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_cmpx", bundle);
            } else {
                String strZza = zzibVar.zzd().zzr.zza();
                if (TextUtils.isEmpty(strZza)) {
                    zzibVar.zzaV().zzc().zza("Cache still valid but referrer not found");
                } else {
                    long jZza = zzibVar.zzd().zzs.zza() / 3600000;
                    Uri uri = Uri.parse(strZza);
                    Bundle bundle2 = new Bundle();
                    Pair pair = new Pair(uri.getPath(), bundle2);
                    for (String str : uri.getQueryParameterNames()) {
                        bundle2.putString(str, uri.getQueryParameter(str));
                    }
                    ((Bundle) pair.second).putLong("_cc", (jZza - 1) * 3600000);
                    zzibVar.zzj().zzF(pair.first == null ? "app" : (String) pair.first, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, (Bundle) pair.second);
                }
                zzibVar.zzd().zzr.zzb(null);
            }
            zzibVar.zzd().zzs.zzb(0L);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzb(String str, Bundle bundle) {
        String string;
        zzib zzibVar = this.zza;
        zzibVar.zzaW().zzg();
        if (zzibVar.zzB()) {
            return;
        }
        if (bundle.isEmpty()) {
            string = null;
        } else {
            if (true == str.isEmpty()) {
                str = DebugKt.DEBUG_PROPERTY_VALUE_AUTO;
            }
            Uri.Builder builder = new Uri.Builder();
            builder.path(str);
            for (String str2 : bundle.keySet()) {
                builder.appendQueryParameter(str2, bundle.getString(str2));
            }
            string = builder.build().toString();
        }
        if (TextUtils.isEmpty(string)) {
            return;
        }
        zzibVar.zzd().zzr.zzb(string);
        zzibVar.zzd().zzs.zzb(zzibVar.zzaZ().currentTimeMillis());
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final void zzc() {
        if (zze() && zzd()) {
            this.zza.zzd().zzr.zzb(null);
        }
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zzd() {
        if (!zze()) {
            return false;
        }
        zzib zzibVar = this.zza;
        return zzibVar.zzaZ().currentTimeMillis() - zzibVar.zzd().zzs.zza() > zzibVar.zzc().zzl(null, zzfx.zzaj);
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    final boolean zze() {
        return this.zza.zzd().zzs.zza() > 0;
    }
}
