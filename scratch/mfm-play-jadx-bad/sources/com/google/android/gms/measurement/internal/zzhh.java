package com.google.android.gms.measurement.internal;

import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.common.stats.ConnectionTracker;
import com.google.firebase.messaging.Constants;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import kotlinx.coroutines.DebugKt;

/* JADX INFO: compiled from: com.google.android.gms:play-services-measurement@@22.5.0 */
/* JADX INFO: loaded from: classes3.dex */
final class zzhh implements Runnable {
    final /* synthetic */ com.google.android.gms.internal.measurement.zzbq zza;
    final /* synthetic */ ServiceConnection zzb;
    final /* synthetic */ zzhi zzc;

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    zzhh(zzhi zzhiVar, com.google.android.gms.internal.measurement.zzbq zzbqVar, ServiceConnection serviceConnection) {
        this.zza = zzbqVar;
        this.zzb = serviceConnection;
        Objects.requireNonNull(zzhiVar);
        this.zzc = zzhiVar;
    }

    /* JADX DEBUG: Don't trust debug lines info. Lines numbers was adjusted: min line is 1 */
    @Override // java.lang.Runnable
    public final void run() {
        Bundle bundleZze;
        zzhi zzhiVar = this.zzc;
        zzhj zzhjVar = zzhiVar.zza;
        zzib zzibVar = zzhjVar.zza;
        zzibVar.zzaW().zzg();
        Bundle bundle = new Bundle();
        String strZza = zzhiVar.zza();
        bundle.putString("package_name", strZza);
        try {
            bundleZze = this.zza.zze(bundle);
        } catch (Exception e) {
            zzhjVar.zza.zzaV().zzb().zzb("Exception occurred while retrieving the Install Referrer", e.getMessage());
        }
        if (bundleZze == null) {
            zzibVar.zzaV().zzb().zza("Install Referrer Service returned a null response");
            bundleZze = null;
        }
        zzib zzibVar2 = zzhjVar.zza;
        zzibVar2.zzaW().zzg();
        zzib.zzL();
        if (bundleZze != null) {
            long j = bundleZze.getLong("install_begin_timestamp_seconds", 0L) * 1000;
            if (j == 0) {
                zzibVar2.zzaV().zze().zza("Service response is missing Install Referrer install timestamp");
            } else {
                String string = bundleZze.getString("install_referrer");
                if (string == null || string.isEmpty()) {
                    zzibVar2.zzaV().zzb().zza("No referrer defined in Install Referrer response");
                } else {
                    zzibVar2.zzaV().zzk().zzb("InstallReferrer API result", string);
                    Bundle bundleZzi = zzibVar2.zzk().zzi(Uri.parse("?".concat(string)));
                    if (bundleZzi == null) {
                        zzibVar2.zzaV().zzb().zza("No campaign params defined in Install Referrer result");
                    } else {
                        List listAsList = Arrays.asList(((String) zzfx.zzbh.zzb(null)).split(","));
                        Iterator<String> it = bundleZzi.keySet().iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                break;
                            }
                            if (listAsList.contains(it.next())) {
                                long j2 = bundleZze.getLong("referrer_click_timestamp_server_seconds", 0L) * 1000;
                                if (j2 > 0) {
                                    bundleZzi.putLong("click_timestamp", j2);
                                }
                            }
                        }
                        if (j == zzibVar2.zzd().zzd.zza()) {
                            zzibVar2.zzaV().zzk().zza("Logging Install Referrer campaign from module while it may have already been logged.");
                        }
                        if (zzibVar2.zzB()) {
                            zzibVar2.zzd().zzd.zzb(j);
                            zzibVar2.zzaV().zzk().zzb("Logging Install Referrer campaign from gmscore with ", "referrer API v2");
                            bundleZzi.putString("_cis", "referrer API v2");
                            zzibVar2.zzj().zzI(DebugKt.DEBUG_PROPERTY_VALUE_AUTO, Constants.ScionAnalytics.EVENT_FIREBASE_CAMPAIGN, bundleZzi, strZza);
                        }
                    }
                }
            }
        }
        ConnectionTracker.getInstance().unbindService(zzibVar2.zzaY(), this.zzb);
    }
}
