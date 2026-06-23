package com.google.android.gms.measurement.internal;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import com.google.firebase.messaging.Constants;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement-impl@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzkw implements Runnable {
    final /* synthetic */ boolean zza;
    final /* synthetic */ Uri zzb;
    final /* synthetic */ String zzc;
    final /* synthetic */ String zzd;
    final /* synthetic */ zzkx zze;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzkw(zzkx zzkxVar, boolean z, Uri uri, String str, String str2) {
        this.zza = z;
        this.zzb = uri;
        this.zzc = str;
        this.zzd = str2;
        Objects.requireNonNull(zzkxVar);
        this.zze = zzkxVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        Bundle bundleZzi;
        zzib zzibVar;
        Bundle bundleZzi2;
        zzkx zzkxVar = this.zze;
        zzli zzliVar = zzkxVar.zza;
        zzliVar.zzg();
        String str = this.zzd;
        Uri uri = this.zzb;
        try {
            zzib zzibVar2 = zzliVar.zzu;
            zzpo zzpoVarZzk = zzibVar2.zzk();
            if (TextUtils.isEmpty(str)) {
                bundleZzi = null;
            } else if (str.contains("gclid") || str.contains("gbraid") || str.contains("utm_campaign") || str.contains("utm_source") || str.contains("utm_medium") || str.contains("utm_id") || str.contains("dclid") || str.contains("srsltid") || str.contains("sfmc_id")) {
                String.valueOf(str);
                bundleZzi = zzpoVarZzk.zzi(Uri.parse("https://google.com/search?".concat(String.valueOf(str))));
                if (bundleZzi != null) {
                    bundleZzi.putString("_cis", "referrer");
                }
            } else {
                zzpoVarZzk.zzu.zzaV().zzj().zza("Activity created with data 'referrer' without required params");
                bundleZzi = null;
            }
            String str2 = this.zzc;
            if (!this.zza || (bundleZzi2 = zzibVar2.zzk().zzi(uri)) == null) {
                zzibVar = zzibVar2;
            } else {
                bundleZzi2.putString("_cis", "intent");
                if (bundleZzi2.containsKey("gclid") || bundleZzi == null || !bundleZzi.containsKey("gclid")) {
                    zzibVar = zzibVar2;
                } else {
                    zzibVar = zzibVar2;
                    bundleZzi2.putString("_cer", String.format("gclid=%s", bundleZzi.getString("gclid")));
                }
                zzliVar.zzF(str2, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundleZzi2);
                zzliVar.zzb.zzb(str2, bundleZzi2);
            }
            if (TextUtils.isEmpty(str)) {
                return;
            }
            zzibVar.zzaV().zzj().zzb("Activity created with referrer", str);
            if (zzibVar.zzc().zzp(null, zzfx.zzaG)) {
                if (bundleZzi != null) {
                    zzliVar.zzF(str2, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundleZzi);
                    zzliVar.zzb.zzb(str2, bundleZzi);
                } else {
                    zzibVar.zzaV().zzj().zzb("Referrer does not contain valid parameters", str);
                }
                zzliVar.zzK(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ldl", null, true);
                return;
            }
            if (!str.contains("gclid") || (!str.contains("utm_campaign") && !str.contains("utm_source") && !str.contains("utm_medium") && !str.contains("utm_term") && !str.contains("utm_content"))) {
                zzibVar.zzaV().zzj().zza("Activity created with data 'referrer' without required params");
            } else {
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                zzliVar.zzK(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, "_ldl", str, true);
            }
        } catch (RuntimeException e) {
            zzkxVar.zza.zzu.zzaV().zzb().zzb("Throwable caught in handleReferrerForOnActivityCreated", e);
        }
    }
}
